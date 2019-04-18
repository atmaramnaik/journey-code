package io.github.atmaramnaik.journey.hosted.services;
import io.github.atmaramnaik.journey.hosted.model.Session;
import io.github.atmaramnaik.journey.journey.JourneyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SessionManagementService {
    public SessionManagementService() {
//        users.put("<DM User Name>",new Session(new AtomicBoolean(false)));
    }

    @Autowired
    JourneyManager journeyManager;

    Map<String, Session> users=new HashMap<>();
    public Session getSession(String user){
        return users.get(user);
    }
    public boolean sessionStarted(String user){
        try {
            Session session = users.get(user);
            if (session.isStarted.get()) {
                return true;
            }
        } catch (NullPointerException ex){
            return false;
        }
        return false;
    }

    @Async("asyncExecutor")
    public void startSession(String input,String user){
        Session session = users.get(user);
        if(session==null){
            session=new Session(new AtomicBoolean(false));
            synchronized (users) {
                users.put(user, session);
            }
        }
        session.startSession();
        startJourney(input,user);
    }
    public void endSession(String user){
        Session session=users.get(user);
        session.isStarted.set(false);
    }
    public void startJourney(String input,String user){
        boolean exp=false;
        try {
            journeyManager.start(input,users.get(user).webIO);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            exp=true;
        } finally {
            endSession(user);
        }
        if(exp){
            users.get(user).webIO.getWriter().write("Something went wrong");
            users.get(user).webIO.getWriter().doneWriting();
        }
    }
}

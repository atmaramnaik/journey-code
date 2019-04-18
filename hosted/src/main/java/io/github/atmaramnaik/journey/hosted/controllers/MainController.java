package io.github.atmaramnaik.journey.hosted.controllers;
import io.github.atmaramnaik.journey.hosted.io.Feed;
import io.github.atmaramnaik.journey.hosted.model.Message;
import io.github.atmaramnaik.journey.hosted.model.Session;
import io.github.atmaramnaik.journey.hosted.model.User;
import io.github.atmaramnaik.journey.hosted.services.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class MainController {
    @Autowired
    private SessionManagementService sessionManagementService;

    @PostMapping(path = "/read")
    public List<Feed> read(@RequestBody User user){
        return Session.readAll(sessionManagementService.getSession(user.getName()).outputFeed);
    }
    @PostMapping(path = "/write")
    public void write(@RequestBody Message message)
    {
        if(!sessionManagementService.sessionStarted(message.getUser())) {
            sessionManagementService.startSession(message.getMessage(),message.getUser());
        } else {
            List<Feed> iFeed=sessionManagementService.getSession(message.getUser()).inputFeed;
            synchronized (iFeed) {
                iFeed.add(Feed.message(message.getMessage()));
                iFeed.notifyAll();
            }
        }
    }
}

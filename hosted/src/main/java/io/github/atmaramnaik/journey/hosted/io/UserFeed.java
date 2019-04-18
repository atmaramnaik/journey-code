package io.github.atmaramnaik.journey.hosted.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class UserFeed {
    public static UserFeed userInputFeed=new UserFeed();
    public static UserFeed userOutputFeed=new UserFeed();
    HashMap<String, List<Feed>> feeds=new HashMap<>();
    public void add(String user,Feed feed){
        if(!feeds.containsKey(user)){
            feeds.put(user,new ArrayList<>());
        }
        feeds.get(user).add(feed);
    }
    public List<Feed> read(String user){
        List<Feed> ret;
        if(feeds.containsKey(user)){
            ret=feeds.get(user);
            feeds.put(user,new ArrayList<>());
        } else {
            ret = new ArrayList<>();
        }
        return ret;
    }
    public Feed readOne(String user){
        Feed ret=null;
        if(feeds.containsKey(user) && feeds.get(user).size() >0){
            ret=feeds.get(user).get(0);
            feeds.get(user).remove(0);
        }
        return ret;
    }
}

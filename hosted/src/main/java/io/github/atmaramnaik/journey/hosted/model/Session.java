package io.github.atmaramnaik.journey.hosted.model;

import io.github.atmaramnaik.journey.hosted.io.Feed;
import io.github.atmaramnaik.journey.hosted.io.WebIO;
import io.github.atmaramnaik.journey.hosted.io.WebReader;
import io.github.atmaramnaik.journey.hosted.io.WebWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Session {
    public AtomicBoolean isStarted;
    public WebIO webIO=null;
    public final List<Feed> inputFeed;
    public final List<Feed> outputFeed;
    public Session(AtomicBoolean isStarted) {
        this.isStarted = isStarted;
        inputFeed=new ArrayList<>();
        outputFeed=new ArrayList<>();
    }
    public void startSession(){
        isStarted.set(true);
        synchronized (inputFeed){
            inputFeed.clear();
        }
        synchronized (outputFeed){
            outputFeed.clear();
        }
        webIO=new WebIO(new WebWriter(outputFeed),new WebReader(inputFeed));
    }

    public static Feed readOneFeed(List<Feed> inputFeed) {
        Feed ret = null;
        synchronized (inputFeed) {
            if (inputFeed.size() > 0) {
                ret = inputFeed.get(0);
                inputFeed.remove(0);
            }
        }
        return ret;
    }
    public static List<Feed> readAll(List<Feed> feed) {
        List<Feed> ret = new ArrayList<>();
        synchronized (feed) {
            ret.addAll(feed);
            feed.clear();
        }
        return ret;
    }


}

package io.github.atmaramnaik.journey.hosted.io;

import io.github.atmaramnaik.journey.hosted.model.Session;
import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.io.Reader;
import io.github.atmaramnaik.journey.template.io.Writer;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WebReader implements Reader {
    public String user;
    boolean isReading;
    Writer writer;
    final List<Feed> feeds;

    public WebReader(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public void startReading() {
        this.isReading=true;
        if(this.writer.isWriting()) {
            writer.doneWriting();
        }
    }

    @Override
    public void doneReading() {
        this.isReading=false;
        if(!this.writer.isWriting()) {
            writer.startWriting();
        }
    }

    @Override
    public boolean isReading() {
        return isReading=false;
    }
    @Override
    public <T extends ValueHolder> T read(Class<T> tClass) {
        this.isReading=true;
        try {
            T objectHolder=tClass.getConstructor().newInstance();
            Feed feed=null;
            while(feed==null) {
                int retry=0;
                if(this.writer.isWriting()){
                    this.startReading();
                }
                try {
                    synchronized (feeds) {
                        while (feeds.size() == 0 && retry < 100) {
                            try {
                                feeds.wait(1000);
                            }catch (InterruptedException ex){
                                ex.printStackTrace();
                            }
                            retry++;
                        }
                        feed = Session.readOneFeed(feeds);
                        if(feed==null){
                            throw new RuntimeException("Taking too long ");
                        }
                        objectHolder.deSerialize(feed.getMessage());
                    }
                } catch (DeSerializationException ex){
                    this.getWriter().write(ex.getMessage() + " Please re enter\n");
                    feed=null;
                }
            }
            return objectHolder;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Writer getWriter() {
        return writer;
    }

    @Override
    public void setWriter(Writer writer) {
        this.writer=writer;
    }
}

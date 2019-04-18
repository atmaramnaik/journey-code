package io.github.atmaramnaik.journey.hosted.io;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.io.Reader;
import io.github.atmaramnaik.journey.template.io.Writer;

import java.util.List;

public class WebWriter implements Writer {
    public String user;
    boolean isWriting=false;
    Reader reader;
    Feed currentFeed=Feed.message("");
    final List<Feed> feeds;

    public WebWriter(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public boolean isWriting() {
        return isWriting;
    }

    @Override
    public void startWriting() {
        isWriting=true;
        if(this.reader.isReading()) {
            reader.doneReading();
        }
    }
    @Override
    public void doneWriting() {
        if(isWriting) {
            synchronized (feeds) {
                feeds.add(currentFeed);
            }
            currentFeed=Feed.message("");
            isWriting = false;
        }
        if(!this.reader.isReading()) {
            reader.startReading();
        }
    }
    public void ensureWrite(){
        if(this.reader.isReading()){
            this.startWriting();
        }
        this.isWriting=true;
    }
    @Override
    public <T extends ValueHolder> void write(T obj) {
        ensureWrite();
        String string=obj.serialize();
        currentFeed.setMessage(currentFeed.getMessage()+string);
    }
    @Override
    public void write(String string) {
        ensureWrite();
        currentFeed.setMessage(currentFeed.getMessage()+string);
    }
    @Override
    public Reader getReader() {
        return reader;
    }

    @Override
    public void setReader(Reader reader) {
        this.reader=reader;
    }
}

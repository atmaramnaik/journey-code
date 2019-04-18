package io.github.atmaramnaik.journey.hosted.io;

import lombok.Getter;
import lombok.Setter;

public class Feed {
    public enum Type {
        MESSAGE,
        CONTROL
    }

    @Getter
    private Type type;

    @Getter
    @Setter
    private String message;

    @Getter
    private String control;

    public static Feed message(String message){
        Feed feed=new Feed();
        feed.type=Type.MESSAGE;
        feed.message=message;
        return feed;
    }
    public static Feed done(String control){
        Feed feed=new Feed();
        feed.type=Type.CONTROL;
        feed.control=control;
        return feed;
    }
}

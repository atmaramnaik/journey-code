package io.github.atmaramnaik.journey.core.io;

import io.github.atmaramnaik.journey.core.data.value.ValueHolder;

public interface Writer {
    <T extends ValueHolder> void write(T obj);
    void write(String string);
    Reader getReader();
    void setReader(Reader reader);
    void startWriting();
    void doneWriting();
    boolean isWriting();
}

package io.github.atmaramnaik.journey.template.io;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

public interface Writer {
    <T extends ValueHolder> void write(T obj);
    void write(String string);
    Reader getReader();
    void setReader(Reader reader);
    void startWriting();
    void doneWriting();
    boolean isWriting();
}

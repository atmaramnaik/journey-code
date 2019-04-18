package io.github.atmaramnaik.journey.template.io;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

public interface Reader {
    <T extends ValueHolder> T read(Class<T> tClass);
    Writer getWriter();
    void setWriter(Writer writer);
    void startReading();
    void doneReading();
    boolean isReading();
}

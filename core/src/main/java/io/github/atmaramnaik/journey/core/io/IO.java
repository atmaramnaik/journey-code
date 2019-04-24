package io.github.atmaramnaik.journey.core.io;

import lombok.Getter;

public class IO {
    @Getter
    Writer writer;
    @Getter
    Reader reader;

    public IO(Writer writer, Reader reader) {
        this.writer = writer;
        this.reader = reader;
        this.writer.setReader(reader);
        this.reader.setWriter(writer);
    }
}

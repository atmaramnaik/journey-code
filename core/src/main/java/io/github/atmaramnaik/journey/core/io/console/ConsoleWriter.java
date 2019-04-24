package io.github.atmaramnaik.journey.core.io.console;

import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.io.Reader;
import io.github.atmaramnaik.journey.core.io.Writer;

import java.io.PrintStream;

public class ConsoleWriter implements Writer {
    @Override
    public boolean isWriting() {
        return false;
    }
    @Override
    public void startWriting() {

    }

    @Override
    public void doneWriting() {

    }

    Reader reader;
    PrintStream printStream=System.out;

    @Override
    public Reader getReader() {
        return reader;
    }

    @Override
    public void setReader(Reader reader) {
        this.reader=reader;
    }


    public ConsoleWriter() {
    }

    public ConsoleWriter(PrintStream printStream) {
        this.printStream = printStream;
    }
    @Override
    public <T extends ValueHolder> void write(T obj) {
        printStream.print(obj.serialize());
    }
    @Override
    public void write(String string) {
        printStream.print(string);
    }
}

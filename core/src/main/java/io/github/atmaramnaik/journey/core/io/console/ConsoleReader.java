package io.github.atmaramnaik.journey.core.io.console;

import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.io.Reader;
import io.github.atmaramnaik.journey.core.io.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public class ConsoleReader implements Reader {
    @Override
    public boolean isReading() {
        return false;
    }

    @Override
    public void startReading() {

    }

    @Override
    public void doneReading() {

    }

    Writer writer;
    InputStream inputStream=System.in;
    BufferedReader reader;

    @Override
    public void setWriter(Writer writer) {
        this.writer=writer;
    }
    public ConsoleReader() {
        reader=new BufferedReader(new InputStreamReader(inputStream));
    }

    public ConsoleReader(InputStream inputStream) {
        this.inputStream = inputStream;
        reader=new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public Writer getWriter() {
        return writer;
    }
    public ConsoleReader(ConsoleWriter consoleWriter) {
        this.writer = consoleWriter;
    }
    @Override
    public <T extends ValueHolder> T read(Class<T> tClass) {

        try {
            T objectHolder=tClass.getConstructor().newInstance();
            boolean valueGiven=false;
            while(!valueGiven) {
                try {
                    objectHolder.deSerialize(reader.readLine());
                    valueGiven=true;
                } catch (DeSerializationException ex){
                    this.getWriter().write(ex.getMessage() + " Please re enter\n");
                }
            }
            return objectHolder;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

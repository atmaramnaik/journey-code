package io.github.atmaramnaik.journey.template.io;

import io.github.atmaramnaik.journey.template.io.console.ConsoleReader;
import io.github.atmaramnaik.journey.template.io.console.ConsoleWriter;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class Helper {
    public static class MockIO extends IO{

        public MockIO(Writer writer, Reader reader) {
            super(writer, reader);
        }
    }
    public static MockIO aMockIO(String input, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        InputStream stubInputStream = IOUtils.toInputStream(input,"UTF-8");
        ConsoleWriter consoleWriter=new ConsoleWriter(new PrintStream(byteArrayOutputStream));
        ConsoleReader consoleReader=new ConsoleReader(stubInputStream);
        MockIO mockIO=new MockIO(consoleWriter,consoleReader);
        return mockIO;
    }
}

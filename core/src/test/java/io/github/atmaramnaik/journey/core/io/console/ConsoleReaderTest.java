package io.github.atmaramnaik.journey.core.io.console;

import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.io.console.ConsoleReader;
import io.github.atmaramnaik.journey.core.io.console.ConsoleWriter;
import org.apache.commons.io.IOUtils;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleReaderTest{
    public static class MockValueHolder extends ValueHolder<String> {

        @Override
        public void deSerialize(String string) throws DeSerializationException {
            this.value="{"+string+"}";
            if(string.equals("Hello")){
                throw new DeSerializationException("Wrong value");
            }
        }

        @Override
        public String serialize() {
            return null;
        }

        @Override
        public String jsonSerialize() {
            return null;
        }
    }
    @Test
    public void shouldReadValidSerializable() throws IOException {
        InputStream stubInputStream = IOUtils.toInputStream("World","UTF-8");
        ConsoleReader consoleReader=new ConsoleReader(stubInputStream);
        MockValueHolder valueHolder=consoleReader.read(MockValueHolder.class);
        assertThat(valueHolder.getValue()).isEqualTo("{World}");
    }
    @Test
    public void shouldReadFirstInvalidSerializable() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ConsoleWriter consoleWriter=new ConsoleWriter(new PrintStream(byteArrayOutputStream));
        InputStream stubInputStream = IOUtils.toInputStream("Hello\nWorld","UTF-8");
        ConsoleReader consoleReader=new ConsoleReader(stubInputStream);
        consoleReader.setWriter(consoleWriter);
        MockValueHolder valueHolder=consoleReader.read(MockValueHolder.class);
        assertThat(valueHolder.getValue()).isEqualTo("{World}");
        String message=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(message).isEqualTo("Wrong value Please re enter\n");
    }
}

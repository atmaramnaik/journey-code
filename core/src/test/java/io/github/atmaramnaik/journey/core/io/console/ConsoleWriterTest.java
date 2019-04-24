package io.github.atmaramnaik.journey.core.io.console;

import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.io.console.ConsoleWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThat;
public class ConsoleWriterTest{
    class MockHolder extends ValueHolder<String> {
        @Override
        public void deSerialize(String string) throws DeSerializationException {

        }

        @Override
        public String serialize() {
            return "Hello";
        }

        @Override
        public String jsonSerialize() {
            return null;
        }
    }
    @Test
    public void shouldWriteValidSerializable(){

        final ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ConsoleWriter consoleWriter=new ConsoleWriter(new PrintStream(byteArrayOutputStream));
        consoleWriter.write(new MockHolder());
        String data=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(data).isEqualTo("Hello");
    }
    @Test
    public void shouldWriteValidString(){

        final ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ConsoleWriter consoleWriter=new ConsoleWriter(new PrintStream(byteArrayOutputStream));
        consoleWriter.write("Hello");
        String data=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(data).isEqualTo("Hello");
    }
}

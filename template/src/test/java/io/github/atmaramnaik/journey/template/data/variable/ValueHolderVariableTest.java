package io.github.atmaramnaik.journey.template.data.variable;

import io.github.atmaramnaik.journey.template.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.template.io.Helper;
import io.github.atmaramnaik.journey.template.io.console.ConsoleReader;
import io.github.atmaramnaik.journey.template.io.console.ConsoleWriter;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueHolderVariableTest {

    @Test
    public void shouldReadValueHolder() throws IOException {
        InputStream stubInputStream = IOUtils.toInputStream("Hello\n123","UTF-8");
        final ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ConsoleWriter consoleWriter=new ConsoleWriter(new PrintStream(byteArrayOutputStream));
        ConsoleReader consoleReader=new ConsoleReader(stubInputStream);
        ValueHolderVariable<Integer> valueHolderVariable=new ValueHolderVariable<>(IntegerHolder.class);
        assertThat(valueHolderVariable.read(new Helper.MockIO(consoleWriter,consoleReader))).isEqualTo(123);
        String data=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(data).isEqualTo(" of type Integer\n" +
                "Value given is not valid Integer Please re enter\n");
    }
}

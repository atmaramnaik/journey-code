package io.github.atmaramnaik.journey.template;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.io.Helper;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static io.github.atmaramnaik.journey.core.io.Helper.aMockIO;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonObjectTemplateTest {
    @Test
    public void shouldGetBooleanOnFill() throws DeSerializationException, IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Helper.MockIO mockIO=aMockIO("123\nHello\n",byteArrayOutputStream);
        Template<Json> valueT= Template.object().with("name",Template.value("Atmaram"));
        Context context=new Context();
        valueT.process(context,mockIO);
        String finalVal=valueT.process(context,mockIO).jsonSerialize();
        assertThat(finalVal).isEqualTo("{" +
                "\"name\":\"Atmaram\"" +
                "}");

    }

}

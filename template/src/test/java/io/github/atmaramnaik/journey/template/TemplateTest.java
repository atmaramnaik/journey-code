package io.github.atmaramnaik.journey.template;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.io.Helper;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableJsonTemplate;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static io.github.atmaramnaik.journey.core.io.Helper.aMockIO;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateTest {
    @Test
    public void shouldParseTemplate() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Helper.MockIO mockIO=aMockIO("1\n@Pune\nAtmaram\n",byteArrayOutputStream);
        HashMap<String,Object> data=new HashMap<>();
        Context context=new Context();
        context.pour(data);
        Template<Json> traverseResult = Template.json("<vals>[{\"@name\":$<Name|String>,\"place\":$<Place|String>,\"yes\":true,\"zero\":null}]</vals>");
        String finalVal=traverseResult.process(context,mockIO).jsonSerialize();
        assertThat(finalVal).isEqualTo("[{\"zero\":null,\"@name\":\"Atmaram\",\"yes\":true,\"place\":\"@Pune\"}]");
    }
    @Test
    public void JSONTest() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Helper.MockIO mockIO=aMockIO("1\n@Pune\nAtmaram\n",byteArrayOutputStream);
        Template<Json> traverseResult=Template.json(getClass().getClassLoader().getResourceAsStream ("abc.t"));
        String finalVal=traverseResult.process(new Context(),mockIO).jsonSerialize();
        assertThat(finalVal).isEqualTo("{\"name\":\"Atmaram\"}");
    }
    @Test
    public void ExtractableJasonTest() throws IOException, DeSerializationException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Helper.MockIO mockIO=aMockIO("1\n@Pune\nAtmaram\n",byteArrayOutputStream);
        ExtractableJsonTemplate<Json> response=Template.xJson("{\"name\":$<Name|String>,\"Age\":$<Age|Integer>}");
        Json dataObject=response.deserialize("{\"name\":\"Atmaram\",\"Age\":34}");
        Context context=new Context();
        response.process(context,mockIO,dataObject);
        assertThat(context.getCurrentContext().keySet().size()).isEqualTo(2);
    }
}

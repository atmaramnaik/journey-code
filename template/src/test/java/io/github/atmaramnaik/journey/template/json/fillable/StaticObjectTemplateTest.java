package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.io.Helper;
import io.github.atmaramnaik.journey.core.io.console.ConsoleIO;
import io.github.atmaramnaik.journey.template.Template;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableDynamicJsonArrayTemplate;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableVariableExpressionTemplate;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static io.github.atmaramnaik.journey.core.io.Helper.aMockIO;
import static io.github.atmaramnaik.journey.template.Template.var;
import static org.assertj.core.api.Assertions.assertThat;

public class StaticObjectTemplateTest {
    @Test
    public void shouldGetBooleanOnFill() throws DeSerializationException, IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Helper.MockIO mockIO=aMockIO("123\nHello\n",byteArrayOutputStream);
        Template<Json> valueT= Template.value(true);
        Context context=new Context();
        valueT.process(context,mockIO);
        String finalVal=valueT.process(context,mockIO).jsonSerialize();
        assertThat(finalVal).isEqualTo("true");

    }
}

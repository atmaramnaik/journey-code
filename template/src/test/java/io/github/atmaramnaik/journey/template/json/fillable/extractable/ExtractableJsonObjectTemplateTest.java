package io.github.atmaramnaik.journey.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.io.console.ConsoleIO;
import io.github.atmaramnaik.journey.template.Template;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableJsonArrayTemplate;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableJsonObjectTemplate;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableVariableExpressionTemplate;
import org.junit.Test;

import java.util.Arrays;

import static io.github.atmaramnaik.journey.template.Template.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtractableJsonObjectTemplateTest {
    @Test
    public void shouldExtractStringInObject() throws DeSerializationException {
        ExtractableJsonObjectTemplate extractableJsonObjectTemplate= Template.object("name",new ExtractableVariableExpressionTemplate(var("name").ofType(String.class)));
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("{\"name\":\"India\"}");
        extractableJsonObjectTemplate.process(context,new ConsoleIO(),jsonHolder.getValue());
        assertThat(context.get("name")).isEqualTo("India");
    }
    @Test
    public void shouldExtractStringAndLongInObject() throws DeSerializationException {
        ExtractableJsonObjectTemplate extractableJsonObjectTemplate=object("name",new ExtractableVariableExpressionTemplate(var("name").ofType(Long.class)));
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("{\"name\":123}");
        extractableJsonObjectTemplate.process(context,new ConsoleIO(),jsonHolder.getValue());
        assertThat(context.get("name")).isEqualTo(123L);
    }

    @Test
    public void shouldExtractArrayInObject() throws DeSerializationException {
        ExtractableJsonArrayTemplate extractableJsonArrayTemplate=
                new ExtractableJsonArrayTemplate(
                        Arrays.asList(
                                new ExtractableVariableExpressionTemplate(var("country1").ofType(String.class)),
                                new ExtractableVariableExpressionTemplate(var("country2").ofType(String.class))
                        )
                );

        ExtractableJsonObjectTemplate extractableJsonObjectTemplate=object("name",extractableJsonArrayTemplate);//new ExtractableJsonObjectTemplate(values);
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("{\"name\":[\"India\",\"China\"]}");
        extractableJsonObjectTemplate.process(context,new ConsoleIO(),jsonHolder.getValue());
        assertThat(context.get("country1")).isEqualTo("India");
        assertThat(context.get("country2")).isEqualTo("China");
    }
}

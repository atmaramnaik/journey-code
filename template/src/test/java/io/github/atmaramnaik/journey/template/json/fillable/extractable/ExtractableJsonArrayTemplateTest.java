package io.github.atmaramnaik.journey.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.io.console.ConsoleIO;
import io.github.atmaramnaik.journey.template.template.json.fillable.extractable.ExtractableJsonArrayTemplate;
import io.github.atmaramnaik.journey.template.template.json.fillable.extractable.ExtractableVariableExpressionTemplate;
import org.junit.Test;

import java.util.Arrays;
import static io.github.atmaramnaik.journey.template.template.Template.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtractableJsonArrayTemplateTest {
    @Test
    public void shouldExtractStringInArray() throws DeSerializationException {
        ExtractableJsonArrayTemplate extractableJsonArrayTemplate=
                new ExtractableJsonArrayTemplate(
                        Arrays.asList(
                                new ExtractableVariableExpressionTemplate(
                                        var("country").ofType(String.class)
                                ),
                                new ExtractableVariableExpressionTemplate(
                                        var("place").ofType(String.class)
                                )
                        )
                );
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[\"India\",\"Humpy\"]");
        extractableJsonArrayTemplate.process(context,new ConsoleIO(),jsonHolder.getValue());
        assertThat(context.get("country")).isEqualTo("India");
        assertThat(context.get("place")).isEqualTo("Humpy");
    }
    @Test
    public void shouldExtractStringAndLongInArray() throws DeSerializationException {
        ExtractableJsonArrayTemplate extractableJsonArrayTemplate=
                new ExtractableJsonArrayTemplate(
                        Arrays.asList(
                                new ExtractableVariableExpressionTemplate(
                                        var("country").ofType(String.class)
                                ),
                                new ExtractableVariableExpressionTemplate(
                                        var("place").ofType(Long.class)
                                )
                        )
                );
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[\"India\",123]");
        extractableJsonArrayTemplate.process(context,new ConsoleIO(),jsonHolder.getValue());
        assertThat(context.get("country")).isEqualTo("India");
        assertThat(context.get("place")).isEqualTo(123L);
    }

    @Test
    public void shouldExtractObjectInArray() throws DeSerializationException {

        ExtractableJsonArrayTemplate extractableJsonArrayTemplate=
                new ExtractableJsonArrayTemplate(
                        Arrays.asList(
                                object("name",new ExtractableVariableExpressionTemplate(var("name").ofType(String.class)))
                        )
                );
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[{\"name\":\"India\"}]");
        extractableJsonArrayTemplate.process(context,new ConsoleIO(),jsonHolder.getValue());
        assertThat(context.get("name")).isEqualTo("India");
    }
}

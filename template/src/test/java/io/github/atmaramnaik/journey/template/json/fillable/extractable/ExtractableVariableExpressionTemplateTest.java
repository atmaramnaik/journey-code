package io.github.atmaramnaik.journey.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.data.value.primitive.StringHolder;
import io.github.atmaramnaik.journey.core.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.core.io.console.ConsoleIO;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableVariableExpressionTemplate;
import org.junit.Test;
import static io.github.atmaramnaik.journey.template.Template.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtractableVariableExpressionTemplateTest {
    @Test
    public void shouldExtractString(){
        ExtractableVariableExpressionTemplate extractableVariableExpressionTemplate=new ExtractableVariableExpressionTemplate(var("country").ofType(String.class));
        Context context=new Context();
        ValueHolder<String> valueHolder=ValueHolder.getNewValueHolderFor("India");
        extractableVariableExpressionTemplate.process(context,new ConsoleIO(),valueHolder);
        assertThat(context.get("country")).isEqualTo("India");
    }
    @Test
    public void shouldExtractLong(){
        ExtractableVariableExpressionTemplate extractableVariableExpressionTemplate=new ExtractableVariableExpressionTemplate(var("country").ofType(Long.class));
        Context context=new Context();
        ValueHolder<Long> valueHolder=ValueHolder.getNewValueHolderFor(123L);
        extractableVariableExpressionTemplate.process(context,new ConsoleIO(),valueHolder);
        assertThat(context.get("country")).isEqualTo(123L);
    }
    @Test
    public void shouldExtractArray() throws DeSerializationException {
        ExtractableVariableExpressionTemplate extractableVariableExpressionTemplate=new ExtractableVariableExpressionTemplate(var("country").ofType(Json.class));
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[\"Abc\",\"PQR\"]");
        extractableVariableExpressionTemplate.process(context,new ConsoleIO(),jsonHolder);
        Json country=(Json) context.get("country");
        assertThat(country).isInstanceOf(JsonArray.class);
        JsonArray array=(JsonArray)country;
        assertThat(array.get(0)).isInstanceOf(StringHolder.class);
        //assertThat().isEqualTo(123L);
    }
}

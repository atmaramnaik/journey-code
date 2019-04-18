package io.github.atmaramnaik.journey.template.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.template.io.console.ConsoleIO;
import org.junit.Test;

import java.util.ArrayList;

import static io.github.atmaramnaik.journey.template.template.Template.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtractableDynamicJsonArrayTemplateTest {
    @Test
    public void shouldExtractStringInArrayOfArray() throws DeSerializationException {
        ExtractableDynamicJsonArrayTemplate extractableDynamicJsonArrayTemplate=
                new ExtractableDynamicJsonArrayTemplate(
                        "places",
                        new ExtractableDynamicJsonArrayTemplate(
                                "locations",
                                new ExtractableVariableExpressionTemplate(
                                        var("country").ofType(String.class)
                                )
                        )
                );
        Context context=new Context();
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[[\"India\",\"Humpy\"],[\"India\",\"Humpy\"]]");
        extractableDynamicJsonArrayTemplate.process(context,new ConsoleIO(),jsonHolder);
        assertThat(context.get("places")).isInstanceOf(ArrayList.class);
    }

}

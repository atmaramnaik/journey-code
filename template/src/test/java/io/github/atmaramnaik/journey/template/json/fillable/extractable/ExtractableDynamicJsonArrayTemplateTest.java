package io.github.atmaramnaik.journey.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.io.console.ConsoleIO;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableDynamicJsonArrayTemplate;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.ExtractableVariableExpressionTemplate;
import org.junit.Test;

import java.util.ArrayList;

import static io.github.atmaramnaik.journey.template.Template.*;
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

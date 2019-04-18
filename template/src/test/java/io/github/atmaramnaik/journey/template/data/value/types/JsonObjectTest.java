package io.github.atmaramnaik.journey.template.data.value.types;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.data.value.custom.JsonHolder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonObjectTest {
    @Test
    public void shouldSerializeJsonObjectWithOneValue(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("name",ValueHolder.getNewValueHolderFor(12));
        assertThat(jsonObject.jsonSerialize()).isEqualTo("{\"name\":12}");
    }
    @Test
    public void shouldSerializeJsonObjectWithMultipleValue(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("name",ValueHolder.getNewValueHolderFor(12));
        jsonObject.put("age",ValueHolder.getNewValueHolderFor("123"));
        assertThat(jsonObject.jsonSerialize()).isEqualTo("{\"name\":12,\"age\":\"123\"}");
    }
    @Test
    public void shouldSerializeJsonObjectWithJsonObject(){
        JsonObject jsonObjectOuter=new JsonObject();
        JsonObject inner=new JsonObject();
        inner.put("First Name",ValueHolder.getNewValueHolderFor("Atmaram"));
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.setValue(inner);
        jsonObjectOuter.put("name",jsonHolder);
        assertThat(jsonObjectOuter.jsonSerialize()).isEqualTo("{\"name\":{\"First Name\":\"Atmaram\"}}");
    }
    @Test
    public void shouldSerializeJsonObjectWithJsonArray(){
        JsonObject jsonObjectOuter=new JsonObject();
        JsonArray inner=new JsonArray();
        inner.add(ValueHolder.getNewValueHolderFor("Atmaram"));
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.setValue(inner);
        jsonObjectOuter.put("name",jsonHolder);
        assertThat(jsonObjectOuter.jsonSerialize()).isEqualTo("{\"name\":[\"Atmaram\"]}");
    }
}

package io.github.atmaramnaik.journey.template.data.value;

import io.github.atmaramnaik.journey.template.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.template.data.value.types.JsonObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueHolderTest {
    //getNewValueHolderForType
    @Test
    public void shouldGetValueHolderForJson(){
        ValueHolder<Json> valueHolder=ValueHolder.getNewValueHolderForType(Json.class);
        assertThat(valueHolder).isNotNull();
    }
    @Test
    public void shouldGetValueHolderForString(){
        ValueHolder<String> valueHolder=ValueHolder.getNewValueHolderForType(String.class);
        assertThat(valueHolder).isNotNull();
    }
    //getNewValueHolderForType
    @Test
    public void shouldGetValueHolderForJsonObject(){
        JsonObject jsonObject=new JsonObject();
        ValueHolder<Json> valueHolder=ValueHolder.getNewValueHolderFor(jsonObject);
        assertThat(valueHolder).isNotNull();
    }
    @Test
    public void shouldGetValueHolderForJsonArray(){
        JsonArray jsonArray =new JsonArray();
        ValueHolder<Json> valueHolder=ValueHolder.getNewValueHolderFor(jsonArray);
        assertThat(valueHolder).isNotNull();
    }
}

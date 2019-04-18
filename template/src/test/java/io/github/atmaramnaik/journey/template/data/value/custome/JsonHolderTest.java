package io.github.atmaramnaik.journey.template.data.value.custome;

import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.template.data.value.primitive.LongHolder;
import io.github.atmaramnaik.journey.template.data.value.primitive.StringHolder;
import io.github.atmaramnaik.journey.template.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.template.data.value.types.JsonObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonHolderTest {
    @Test
    public void shouldGetJsonFromPrimitiveLong() throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("123");
        assertThat(jsonHolder.getValue()).isInstanceOf(LongHolder.class);
        LongHolder longHolder=(LongHolder)jsonHolder.getValue();
        assertThat(longHolder.getValue()).isEqualTo(123);
    }
    @Test
    public void shouldGetJsonFromPrimitiveString() throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("\"123\"");
        assertThat(jsonHolder.getValue()).isInstanceOf(StringHolder.class);
        StringHolder stringHolder=(StringHolder)jsonHolder.getValue();
        assertThat(stringHolder.getValue()).isEqualTo("123");
    }
    @Test
    public void shouldGetJsonFromObject() throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("{\"name\":123}");
        assertThat(jsonHolder.getValue()).isInstanceOf(JsonObject.class);
        JsonObject jsonObject=(JsonObject) jsonHolder.getValue();
        assertThat(jsonObject.get("name")).isInstanceOf(LongHolder.class);
        assertThat(((LongHolder)jsonObject.get("name")).getValue()).isEqualTo(123);
    }
    @Test
    public void shouldGetJsonFromArray() throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[\"123\"]");
        assertThat(jsonHolder.getValue()).isInstanceOf(JsonArray.class);
        JsonArray jsonArray=(JsonArray) jsonHolder.getValue();
        assertThat(jsonArray.get(0)).isInstanceOf(StringHolder.class);
        assertThat(((StringHolder)jsonArray.get(0)).getValue()).isEqualTo("123");
    }
    @Test
    public void shouldGetJsonFromNestedObjectArray() throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize("[{\"name\":123}]");
        assertThat(jsonHolder.getValue()).isInstanceOf(JsonArray.class);
        JsonArray jsonArray=(JsonArray) jsonHolder.getValue();
        assertThat(jsonArray.get(0)).isInstanceOf(JsonHolder.class);
    }
}

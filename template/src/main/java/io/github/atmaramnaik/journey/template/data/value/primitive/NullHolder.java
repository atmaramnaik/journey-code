package io.github.atmaramnaik.journey.template.data.value.primitive;

import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

public class NullHolder  extends ValueHolder<Object> {
    @Override
    public String jsonSerialize() {
        return "null";
    }

    @Override
    public void deSerialize(String string) throws DeSerializationException {
        this.value=null;
    }

    @Override
    public String serialize() {
        return "null";
    }
}

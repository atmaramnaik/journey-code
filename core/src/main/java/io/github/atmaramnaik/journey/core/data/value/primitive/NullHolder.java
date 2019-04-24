package io.github.atmaramnaik.journey.core.data.value.primitive;

import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;

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

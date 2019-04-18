package io.github.atmaramnaik.journey.template.data.value.primitive;

import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

public class ObjectHolder extends ValueHolder<Object> {
    @Override
    public void deSerialize(String from) throws DeSerializationException {
        this.value=from;
    }
    @Override
    public String serialize() {
        return this.value.toString();
    }

    @Override
    public String jsonSerialize() {
        return "\""+serialize()+"\"";
    }
}

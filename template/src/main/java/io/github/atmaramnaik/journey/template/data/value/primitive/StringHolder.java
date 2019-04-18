package io.github.atmaramnaik.journey.template.data.value.primitive;

import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

public class StringHolder extends ValueHolder<String> {
    @Override
    public void deSerialize(String from) throws DeSerializationException {
        this.value=from;
    }
    @Override
    public String serialize() {
        return this.value;
    }

    @Override
    public String jsonSerialize() {
        return "\""+serialize()+"\"";
    }
}

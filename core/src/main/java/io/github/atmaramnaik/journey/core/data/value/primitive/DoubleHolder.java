package io.github.atmaramnaik.journey.core.data.value.primitive;


import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;

public class DoubleHolder extends ValueHolder<Double> {
    @Override
    public void deSerialize(String from) throws DeSerializationException {
        try {
            this.value=Double.parseDouble(from);
        } catch (NumberFormatException ex){
            throw new DeSerializationException("Value given is not valid Double");
        }
    }

    @Override
    public String serialize() {
        return this.value.toString();
    }

    @Override
    public String jsonSerialize() {
        return serialize();
    }
}

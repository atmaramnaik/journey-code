package io.github.atmaramnaik.journey.core.data.value;

public interface Serializable {
    void deSerialize(String string) throws DeSerializationException;
    String serialize();
}

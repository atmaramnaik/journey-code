package io.github.atmaramnaik.journey.template.data.value;

public interface Serializable {
    void deSerialize(String string) throws DeSerializationException;
    String serialize();
}

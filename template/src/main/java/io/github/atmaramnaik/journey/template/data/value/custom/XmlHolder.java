package io.github.atmaramnaik.journey.template.data.value.custom;

import io.github.atmaramnaik.journey.template.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.data.value.types.Xml;

public class XmlHolder extends ValueHolder<Xml> {
    @Override
    public void deSerialize(String string) throws DeSerializationException {
        Xml xml=new Xml();
        xml.setData(string);
        this.value=xml;
    }
    @Override
    public String serialize() {
        return "<a>"+this.value.getData()+"</a>";
    }

    @Override
    public String jsonSerialize() {
        return null;
    }
}

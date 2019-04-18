package io.github.atmaramnaik.journey.template.data.variable;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.io.IO;
import lombok.Getter;
public class ValueHolderVariable<K> implements Readable<K> {
    @Getter
    Class<? extends ValueHolder<K>> valueHolderClass;
    public ValueHolderVariable(Class<? extends ValueHolder<K>> valueHolderClass) {
        this.valueHolderClass = valueHolderClass;
    }
    @Override
    public K read(IO io) {
        io.getWriter().write(" of type "+ValueHolder.getTypeFor(valueHolderClass).getSimpleName()+"\n");
        return io.getReader().read(valueHolderClass).getValue();
    }
}

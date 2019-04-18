package io.github.atmaramnaik.journey.template.template.expression.operations;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.data.value.primitive.StringHolder;
import io.github.atmaramnaik.journey.template.template.expression.Operation;

import java.util.List;

public class UUID implements Operation {
    @Override
    public ValueHolder operate(List<ValueHolder> valueHolders) {
        StringHolder stringHolder=new StringHolder();
        String value = java.util.UUID.randomUUID().toString();
        stringHolder.setValue(value);
        return stringHolder;
    }
}

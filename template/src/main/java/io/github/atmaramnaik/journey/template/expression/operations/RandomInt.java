package io.github.atmaramnaik.journey.template.template.expression.operations;

import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.template.template.expression.Operation;

import java.util.List;

public class RandomInt implements Operation {
    @Override
    public ValueHolder operate(List<ValueHolder> valueHolders) {
        IntegerHolder integerHolder=new IntegerHolder();
        integerHolder.setValue(1000);
        return integerHolder;
    }
}

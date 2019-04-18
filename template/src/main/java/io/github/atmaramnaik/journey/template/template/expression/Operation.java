package io.github.atmaramnaik.journey.template.template.expression;

import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

import java.util.List;

@FunctionalInterface
public interface Operation {
    ValueHolder operate(List<ValueHolder> valueHolders);
}

package io.github.atmaramnaik.journey.template.expression;

import io.github.atmaramnaik.journey.core.data.value.ValueHolder;

import java.util.List;

@FunctionalInterface
public interface Operation {
    ValueHolder operate(List<ValueHolder> valueHolders);
}

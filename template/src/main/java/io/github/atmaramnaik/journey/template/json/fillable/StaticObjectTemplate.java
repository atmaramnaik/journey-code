package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.Template;

import java.util.HashMap;

public class StaticObjectTemplate<T> implements Template<Json> {
    T value;

    public StaticObjectTemplate(T value) {
        this.value = value;
    }

    @Override
    public Json fillReturnValue(Context context) {
        return ValueHolder.getNewValueHolderFor(value);
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return new HashMapVariable(new HashMap<>());
    }
}

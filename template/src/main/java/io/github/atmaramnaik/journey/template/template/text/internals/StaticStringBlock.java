package io.github.atmaramnaik.journey.template.template.text.internals;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.variable.HashMapVariable;
import lombok.Getter;

import java.util.HashMap;

public class StaticStringBlock implements Block{
    @Getter
    String value;

    public StaticStringBlock(String value) {
        this.value = value;
    }

    @Override
    public String fill(Context context) {
        return value;
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return new HashMapVariable(new HashMap<>());
    }
}

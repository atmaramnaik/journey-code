package io.github.atmaramnaik.journey.template.template.expression;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.data.variable.HashMapVariable;

public interface Expression {
    ValueHolder fill(Context context);
    HashMapVariable getRequiredDataVariables(Context context);

}

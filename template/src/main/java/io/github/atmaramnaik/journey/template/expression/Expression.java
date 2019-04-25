package io.github.atmaramnaik.journey.template.expression;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;

public interface Expression {
    ValueHolder fill(Context context);
    HashMapVariable getRequiredDataVariables(Context context);

}

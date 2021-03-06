package io.github.atmaramnaik.journey.template.text.internals;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.expression.Expression;

public class ExpressionBlock implements Block {
    Expression expression;

    public ExpressionBlock(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String fill(Context context) {
        return expression.fill(context).serialize();
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return expression.getRequiredDataVariables(context);
    }
}

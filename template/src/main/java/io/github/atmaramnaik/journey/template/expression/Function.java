package io.github.atmaramnaik.journey.template.expression;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Function implements Expression {
    Operation operation;
    List<Expression> expressions=new ArrayList<>();

    public Function(Operation operation) {
        this.operation = operation;
    }

    public Function(Operation operation, List<Expression> expressions) {
        this.operation = operation;
        this.expressions = expressions;
    }

    public Function withArgumet(Expression expression) {
        this.expressions.add(expression);
        return this;
    }

    @Override
    public ValueHolder fill(Context context) {
        List<ValueHolder> valueHolders=new ArrayList<>();
        for (Expression expression:
             expressions) {
            valueHolders.add(expression.fill(context));
        }
        return operation.operate(valueHolders);
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        for (Expression expression:
             expressions) {
            hashMapVariable.getDataDescription().putAll(expression.getRequiredDataVariables(context).getDataDescription());
        }
        return hashMapVariable;
    }
}

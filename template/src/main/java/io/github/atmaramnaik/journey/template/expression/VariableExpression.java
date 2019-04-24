package io.github.atmaramnaik.journey.template.template.expression;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.data.variable.ValueHolderVariable;
import lombok.Getter;

import java.util.HashMap;

public class VariableExpression implements Expression{
    @Getter
    String variableName;
    @Getter
    Class<?> variableType;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    public VariableExpression ofType(Class<?> variableType){
        this.variableType=variableType;
        return this;
    }

    public VariableExpression(String variableName, Class<?> variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public ValueHolder fill(Context context) {
        Object data = context.get(variableName);
        return ValueHolder.getNewValueHolderFor(data);
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        if(context.getCurrentContext().containsKey(variableName)){
            return new HashMapVariable(new HashMap<>());
        } else {
            ValueHolder valueHolder=ValueHolder.getNewValueHolderForType(variableType);
            ValueHolderVariable valueHolderVariable=new ValueHolderVariable(valueHolder.getClass());
            HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
            hashMapVariable.getDataDescription().put(variableName,valueHolderVariable);
            return hashMapVariable;
        }
    }
}

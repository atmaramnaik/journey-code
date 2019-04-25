package io.github.atmaramnaik.journey.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.expression.VariableExpression;

import java.util.HashMap;

public class ExtractableVariableExpressionTemplate implements ExtractableJsonTemplate<Json> {
    @Override
    public ValueHolder deserialize(String data) throws DeSerializationException {
        ValueHolder valueHolder=ValueHolder.getNewValueHolderForType(variableExpression.getVariableType());
        valueHolder.deSerialize(data);
        return valueHolder;
    }

    VariableExpression variableExpression;

    public ExtractableVariableExpressionTemplate(VariableExpression variableExpression) {
        this.variableExpression = variableExpression;
    }

    @Override
    public HashMap<String, Object> extract(Context context, Json data) {
        HashMap<String,Object> hashMap=new HashMap<>();
        if(data instanceof ValueHolder) {
            hashMap.put(variableExpression.getVariableName(), ((ValueHolder)data).getValue());
        }
        return hashMap;
    }

    @Override
    public ExtractableVariableExpressionTemplate fillReturnValue(Context context) {
        return this;
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return new HashMapVariable(new HashMap<>());
    }
}

package io.github.atmaramnaik.journey.template.template.text;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.template.Extractable;
import io.github.atmaramnaik.journey.template.template.expression.VariableExpression;

import java.util.HashMap;

public class SingleVariableTextTemplate extends Text implements Extractable<String> {
    @Override
    public HashMap<String, Object> process(Context context, IO io, String dataJson) {
        HashMap<String,Object> data= extract(context,dataJson);
        context.pour(data);
        return data;
    }

    @Override
    public String deserialize(String data) throws DeSerializationException {
        return data;
    }
    VariableExpression variableExpression;

    public SingleVariableTextTemplate(VariableExpression variableExpression) {
        this.variableExpression = variableExpression;
    }

    @Override
    public String fillReturnValue(Context context) {
        return variableExpression.fill(context).serialize();
    }



    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return variableExpression.getRequiredDataVariables(context);
    }
    @Override
    public HashMap<String, Object> extract(Context context, String data) {
        HashMap<String,Object> hmData=new HashMap<>();
        hmData.put(variableExpression.getVariableName(),data);
        return hmData;
    }
}

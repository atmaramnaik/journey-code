package io.github.atmaramnaik.journey.template.template.json.fillable;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.Json;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.data.variable.ChoiseVariable;
import io.github.atmaramnaik.journey.template.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.template.Template;

import java.util.HashMap;
import java.util.List;

public class ChooseTemplate implements Template<Json> {
    String chooseVariable;
    String chooseShowValue;
    Template<Json> innerTemplate;

    public ChooseTemplate(String chooseVariable, String chooseShowValue, Template<Json> innerTemplate) {
        this.chooseVariable = chooseVariable;
        this.chooseShowValue = chooseShowValue;
        this.innerTemplate = innerTemplate;
    }

    Integer choice=null;

    @Override
    public ValueHolder<Json> process(Context context, IO io) {
        choice=(new ChoiseVariable((List<HashMap<String, Object>>)context.get(chooseVariable),chooseShowValue)).read(io);
        HashMapVariable requiredData=getRequiredDataVariables(context);
        context.pour(requiredData.read(io));
        return fill(context);
    }

    @Override
    public Json fillReturnValue(Context context) {
        return innerTemplate.fill(context.getFromListItem(chooseVariable,choice));
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        hashMapVariable.getDataDescription().putAll(innerTemplate.getRequiredDataVariables(context.getFromListItem(chooseVariable,choice)).getDataDescription());
        return hashMapVariable;
    }
}

package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.Template;

import java.util.HashMap;
import java.util.List;

public class JsonArrayTemplate implements Template<Json> {
    protected List<Template<Json>> elements;
    public JsonArrayTemplate with(Template<Json> element){
        elements.add(element);
        return this;
    }
    public JsonArrayTemplate(List<Template<Json>> elements) {
        this.elements = elements;
    }
    @Override
    public Json fillReturnValue(Context context) {
        JsonArray jsonArray=new JsonArray();
        for (Template<Json> element:
             elements) {
            jsonArray.add(element.fill(context));
        }
        return jsonArray;
    }
    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return getHashMapVariable(context, elements);
    }

    public static HashMapVariable getHashMapVariable(Context context, List<? extends Template<Json>> elements) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        for (Template<Json> element:
                elements) {
            hashMapVariable.getDataDescription().putAll(element.getRequiredDataVariables(context).getDataDescription());
        }
        return hashMapVariable;
    }
}

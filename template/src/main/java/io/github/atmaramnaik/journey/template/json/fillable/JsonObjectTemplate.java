package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.types.JsonObject;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.Template;

import static io.github.atmaramnaik.journey.template.Template.*;
import java.util.HashMap;

public class JsonObjectTemplate implements Template<Json> {
    protected HashMap<Template<Json>, Template<Json>> pairs;

    public JsonObjectTemplate(HashMap<Template<Json>, Template<Json>> pairs) {
        this.pairs = pairs;
    }

    public JsonObjectTemplate with(Template<Json> key,Template<Json> value){
        pairs.put(key,value);
        return this;
    }
    public JsonObjectTemplate with(String key,Template<Json> value){
        pairs.put(Template.value(key),value);
        return this;
    }
    public JsonObjectTemplate with(String key,Object value){
        pairs.put(Template.value(key),value(value));
        return this;
    }
    @Override
    public Json fillReturnValue(Context context) {
        JsonObject jsonObject=new JsonObject();
        for (Template<Json> key:
                pairs.keySet()) {
            jsonObject.put(key.fillReturnValue(context).jsonSerialize().replaceAll("^\"|\"$", ""),pairs.get(key).fill(context));
        }
        return jsonObject;
    }
    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        for (Template<Json> key:
                pairs.keySet()) {
            hashMapVariable.getDataDescription().putAll(key.getRequiredDataVariables(context).getDataDescription());
            hashMapVariable.getDataDescription().putAll(pairs.get(key).getRequiredDataVariables(context).getDataDescription());
        }
        return hashMapVariable;
    }
}

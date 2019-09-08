package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.types.JsonObject;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.Template;
import io.github.atmaramnaik.journey.template.text.Text;

import static io.github.atmaramnaik.journey.template.Template.*;
import java.util.HashMap;

public class JsonObjectTemplate implements Template<Json> {
    protected HashMap<Text, Template<Json>> pairs;

    public JsonObjectTemplate(HashMap<Text, Template<Json>> pairs) {
        this.pairs = pairs;
    }
    public JsonObjectTemplate with(Text key,Template<Json> value){
        pairs.put(key,value);
        return this;
    }
    public JsonObjectTemplate with(String key,Template<Json> value){
        pairs.put(text(string(key)),value);
        return this;
    }
    public JsonObjectTemplate with(String key,Object value){
        pairs.put(text(string(key)),value(value));
        return this;
    }
    @Override
    public Json fillReturnValue(Context context) {
        JsonObject jsonObject=new JsonObject();
        for (Text key:
                pairs.keySet()) {
            jsonObject.put(key.fillReturnValue(context),pairs.get(key).fill(context));
        }
        return jsonObject;
    }
    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        for (Text key:
                pairs.keySet()) {
            hashMapVariable.getDataDescription().putAll(key.getRequiredDataVariables(context).getDataDescription());
            hashMapVariable.getDataDescription().putAll(pairs.get(key).getRequiredDataVariables(context).getDataDescription());
        }
        return hashMapVariable;
    }
}

package io.github.atmaramnaik.journey.template.json.fillable.extractable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.data.value.types.JsonObject;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import static io.github.atmaramnaik.journey.template.Template.*;
import io.github.atmaramnaik.journey.template.text.Text;

import java.util.HashMap;

import static io.github.atmaramnaik.journey.template.Template.text;

public class ExtractableJsonObjectTemplate implements ExtractableJsonTemplate<Json> {
    private ExtractableJsonObjectTemplate() {
    }

    protected HashMap<Text,ExtractableJsonTemplate<Json>> pairs=new HashMap<>();
    @Override
    public ExtractableJsonObjectTemplate fillReturnValue(Context context) {
        ExtractableJsonObjectTemplate extractableJsonObjectTemplate=new ExtractableJsonObjectTemplate();
        for (Text key:
                pairs.keySet()) {
            extractableJsonObjectTemplate.with(key.fillReturnValue(context),pairs.get(key).fillReturnValue(context));

        }
        return extractableJsonObjectTemplate;
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

    public ExtractableJsonObjectTemplate with(String key, ExtractableJsonTemplate<Json> value){
        pairs.put(text(string(key)),value);
        return this;
    }
    public ExtractableJsonObjectTemplate with(Text key, ExtractableJsonTemplate<Json> value){
        pairs.put(key,value);
        return this;
    }

    @Override
    public Json deserialize(String data) throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize(data);
        return jsonHolder.getValue();
    }
    public ExtractableJsonObjectTemplate(HashMap<Text,ExtractableJsonTemplate<Json>> pairs) {
        this.pairs=pairs;
    }

    public ExtractableJsonObjectTemplate(Text key,ExtractableJsonTemplate<Json> value) {
        this.pairs=new HashMap<>();
        this.with(key,value);
    }
    public ExtractableJsonObjectTemplate(String key,ExtractableJsonTemplate<Json> value) {
        this.pairs=new HashMap<>();
        this.with(key,value);
    }

    @Override
    public HashMap<String, Object> extract(Context context, Json data) {
        HashMap<String,Object> hashMap=new HashMap<>();
        JsonObject jsonObject=new JsonObject();
        if(data instanceof JsonObject){
            jsonObject=(JsonObject) data;
        } else if(data instanceof JsonHolder){
            jsonObject=(JsonObject) ((JsonHolder)data).getValue();
        }
        for (Text key:
                pairs.keySet()) {
            String strKey=key.fillReturnValue(context);
            hashMap.putAll(pairs.get(key).extract(context,jsonObject.get(strKey)));
        }
        return hashMap;
    }
}

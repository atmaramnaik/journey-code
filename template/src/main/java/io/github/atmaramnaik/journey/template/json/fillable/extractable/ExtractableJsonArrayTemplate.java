package io.github.atmaramnaik.journey.template.template.json.fillable.extractable;
import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExtractableJsonArrayTemplate implements ExtractableJsonTemplate<Json> {
    protected List<ExtractableJsonTemplate<Json>> elements=new ArrayList<>();
    @Override
    public ExtractableJsonArrayTemplate fillReturnValue(Context context) {
        ExtractableJsonArrayTemplate extractableJsonArrayTemplate=new ExtractableJsonArrayTemplate(new ArrayList<>());
        for (ExtractableJsonTemplate<Json> element:
                elements) {
            extractableJsonArrayTemplate.elements.add(element.fillReturnValue(context));
        }
        return extractableJsonArrayTemplate;
    }
    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        for (ExtractableJsonTemplate<Json> element:
                elements) {
            hashMapVariable.getDataDescription().putAll(element.getRequiredDataVariables(context).getDataDescription());
        }
        return hashMapVariable;
    }

    public ExtractableJsonArrayTemplate(List<ExtractableJsonTemplate<Json>> elements) {
        this.elements = elements;
    }
    public ExtractableJsonArrayTemplate(ExtractableJsonTemplate<Json> element) {
        this.elements.add(element);
    }
    @Override
    public Json deserialize(String data) throws DeSerializationException {
        JsonHolder jsonHolder=new JsonHolder();
        jsonHolder.deSerialize(data);
        return jsonHolder.getValue();
    }
    @Override
    public HashMap<String, Object> extract(Context context, Json data) {
        HashMap<String,Object> hashMap=new HashMap<>();
        if (data instanceof JsonArray){
            JsonArray array=(JsonArray) data;
            for (int i=0;i<this.elements.size();i++){
                hashMap.putAll(this.elements.get(i).extract(context,array.get(i)));
            }
        } else if(data instanceof JsonHolder){
            JsonArray array=(JsonArray) ((JsonHolder) data).getValue();
            for (int i=0;i<this.elements.size();i++){
                hashMap.putAll(this.elements.get(i).extract(context,array.get(i)));
            }
        }
        return hashMap;
    }
}

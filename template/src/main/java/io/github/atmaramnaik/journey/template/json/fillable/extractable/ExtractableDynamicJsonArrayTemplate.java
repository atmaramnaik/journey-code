package io.github.atmaramnaik.journey.template.json.fillable.extractable;
import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExtractableDynamicJsonArrayTemplate implements ExtractableJsonTemplate<Json> {
    @Override
    public ExtractableDynamicJsonArrayTemplate fillReturnValue(Context context) {
        ExtractableDynamicJsonArrayTemplate extractableDynamicJsonArrayTemplate=new ExtractableDynamicJsonArrayTemplate(listVariable);
        extractableDynamicJsonArrayTemplate.elementTemplate = this.elementTemplate.fillReturnValue(context);
        return extractableDynamicJsonArrayTemplate;
    }
    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return new HashMapVariable(new HashMap<>());
    }
    @Getter
    protected String listVariable=null;

    @Getter
    protected ExtractableJsonTemplate<Json> elementTemplate=null;

    public ExtractableDynamicJsonArrayTemplate(String listVariable) {
        this.listVariable = listVariable;
    }

    public ExtractableDynamicJsonArrayTemplate(String listVariable, ExtractableJsonTemplate<Json> elementTemplate) {
        this.listVariable = listVariable;
        this.elementTemplate = elementTemplate;
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
        List<HashMap<String,Object>> list=new ArrayList<>();
        if (data instanceof JsonArray){
            JsonArray array=(JsonArray) data;
            for (int i=0;i<array.size();i++){
                list.add(elementTemplate.extract(context,array.get(i)));
            }
        } else if(data instanceof JsonHolder){
            JsonArray array=(JsonArray) ((JsonHolder) data).getValue();
            for (int i=0;i<array.size();i++){
                list.add(elementTemplate.extract(context,array.get(i)));
            }
        }
        hashMap.put(listVariable,list);
        return hashMap;
    }
}

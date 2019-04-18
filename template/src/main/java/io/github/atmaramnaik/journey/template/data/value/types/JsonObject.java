package io.github.atmaramnaik.journey.template.data.value.types;

import io.github.atmaramnaik.journey.template.data.value.Json;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

import java.util.HashMap;

public class JsonObject extends HashMap<String, ValueHolder> implements Json {
    @Override
    public String jsonSerialize() {
        String finalStr="{";
        boolean first=true;
        for (String key:
             keySet()) {
            if(!first){
                finalStr+=",";
            }
            ValueHolder valueHolder=get(key);
            finalStr+="\""+key+"\":"+(valueHolder.jsonSerialize());
            first=false;
        }
        finalStr+="}";
        return finalStr;
    }
}

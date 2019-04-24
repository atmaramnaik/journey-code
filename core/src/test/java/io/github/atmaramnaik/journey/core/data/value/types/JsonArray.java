package io.github.atmaramnaik.journey.core.data.value.types;

import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;

import java.util.ArrayList;

public class JsonArray extends ArrayList<ValueHolder> implements Json {
    public String jsonSerialize() {
        String finalStr="[";
        boolean first=true;
        for (ValueHolder valueHolder: this) {
            if(!first){
                finalStr+=",";
            }
            finalStr+=(valueHolder.getValue() instanceof String?"\""+valueHolder.serialize()+"\"":valueHolder.serialize());
            first=false;
        }
        finalStr+="]";
        return finalStr;
    }
}

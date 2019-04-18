package io.github.atmaramnaik.journey.template.data.value.types;

import io.github.atmaramnaik.journey.template.data.value.Json;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;

import java.util.ArrayList;

public class JsonArray extends ArrayList<ValueHolder> implements Json {
    @Override
    public String jsonSerialize() {
        String finalStr="[";
        boolean first=true;
        for (ValueHolder valueHolder: this) {
            if(!first){
                finalStr+=",";
            }
            finalStr+=(valueHolder.jsonSerialize());
            first=false;
        }
        finalStr+="]";
        return finalStr;
    }
}

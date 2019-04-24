package io.github.atmaramnaik.journey.core.data.value.custom;

import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.primitive.NullHolder;
import io.github.atmaramnaik.journey.core.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.core.data.value.types.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Set;


public class JsonHolder extends ValueHolder<Json> {
    @Override
    public String jsonSerialize() {
        return this.value.jsonSerialize();
    }

    @Override
    public void deSerialize(String string) throws DeSerializationException {
        JSONParser jsonParser=new JSONParser();
        try {
            Object object=jsonParser.parse(string);
            if(object instanceof JSONAware){
                this.value = jsonFrom((JSONAware)object);
            } else {
                this.value = jsonFrom(object);
            }
        } catch (ParseException e) {
            throw new DeSerializationException("Value provided is not valid JSON");
        }
    }
    private Json jsonFrom(JSONAware jsonAware){
        if (jsonAware instanceof JSONObject){
            return jsonFrom((JSONObject)jsonAware);
        }
        if (jsonAware instanceof JSONArray){
            return jsonFrom((JSONArray) jsonAware);
        }
        return null;
    }
    private JsonObject jsonFrom(JSONObject jsonObject){
        JsonObject ret=new JsonObject();
        for (String key:(Set<String>)jsonObject.keySet()){
            ret.put(key,jsonFrom(jsonObject.get(key)));
        }
        return ret;
    }
    private JsonArray jsonFrom(JSONArray jsonArray){
        JsonArray ret=new JsonArray();
        for (Object item:jsonArray){
            ret.add(jsonFrom(item));
        }
        return ret;
    }
    private <T> ValueHolder jsonFrom(T object){
        if(object==null){
            NullHolder nullHolder=new NullHolder();
            nullHolder.setValue(null);
            return nullHolder;
        }
        if(object instanceof JSONAware){
            JsonHolder jsonHolder=new JsonHolder();
            jsonHolder.setValue(jsonFrom((JSONAware)object));
            return jsonHolder;
        }
        return ValueHolder.getNewValueHolderFor(object);
    }
    @Override
    public String serialize() {
        return this.value.jsonSerialize();
    }
}

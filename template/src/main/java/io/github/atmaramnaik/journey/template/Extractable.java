package io.github.atmaramnaik.journey.template.template;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.io.IO;

import java.util.HashMap;

public interface Extractable<T> {
    HashMap<String,Object> extract(Context context, T data);
    default HashMap<String,Object> process(Context context, IO io, T data){
        HashMap<String,Object> hashMap=extract(context,data);
        context.pour(hashMap);
        return hashMap;
    }
    T deserialize(String data) throws DeSerializationException;
}

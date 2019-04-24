package io.github.atmaramnaik.journey.core.data.variable;

import io.github.atmaramnaik.journey.core.io.IO;
import lombok.Getter;

import java.util.HashMap;

public class HashMapVariable implements Readable<HashMap<String,Object>> {
    @Getter
    HashMap<String, Readable> dataDescription;

    public HashMapVariable(HashMap<String, Readable> dataDescription) {
        this.dataDescription = dataDescription;
    }
    @Override
    public HashMap<String,Object> read(IO io) {
        HashMap<String,Object> data=new HashMap<>();
        for (String key:
             dataDescription.keySet()) {
            synchronized (io) {
                io.getWriter().write("Please provide value for " + key);
                data.put(key, dataDescription.get(key).read(io));
            }
        }
        return data;
    }
}

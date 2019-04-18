package io.github.atmaramnaik.journey.template.template.json.fillable.extractable;
import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.Json;
import io.github.atmaramnaik.journey.template.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.template.Extractable;
import io.github.atmaramnaik.journey.template.template.Template;

import java.util.HashMap;

public interface ExtractableJsonTemplate<T extends Json> extends Template<ExtractableJsonTemplate<T>>, Extractable<T> {
    @Override
    default HashMap<String,Object> process(Context context, IO io, T dataJson){
        HashMapVariable requiredData=getRequiredDataVariables(context);
        context.pour(requiredData.read(io));
        HashMap<String,Object> data= fillReturnValue(context).extract(context,dataJson);
        context.pour(data);
        return data;
    }
}

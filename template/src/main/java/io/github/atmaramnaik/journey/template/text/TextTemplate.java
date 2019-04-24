package io.github.atmaramnaik.journey.template.template.text;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.template.text.internals.Block;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextTemplate extends Text{
    @Getter
    private List<Block> blocks=new ArrayList<>();
    @Override
    public String fillReturnValue(Context context) {
        String finalStr="";
        for (Block block:
             blocks) {
            finalStr+=block.fill(context);
        }
        return finalStr;
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return new HashMapVariable(new HashMap<>());
    }

    @Override
    public ValueHolder<String> process(Context context, IO io) {
        HashMapVariable requiredData=getRequiredDataVariables(context,io);
        context.pour(requiredData.read(io));
        return fill(context);
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context, IO io) {
        HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
        for (Block block:
                blocks) {
            hashMapVariable.getDataDescription().putAll(block.getRequiredDataVariables(context,io).getDataDescription());
        }
        return hashMapVariable;
    }
}

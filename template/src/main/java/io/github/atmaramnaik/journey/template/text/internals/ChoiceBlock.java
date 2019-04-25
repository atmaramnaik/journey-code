package io.github.atmaramnaik.journey.template.text.internals;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.variable.ChoiseVariable;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.io.IO;

import java.util.HashMap;
import java.util.List;

public class ChoiceBlock implements Block{
    String chooseVariable;
    String chooseShowValue;
    Block innerBlock;

    public ChoiceBlock(String chooseVariable, String chooseShowValue, Block innerBlock) {
        this.chooseVariable = chooseVariable;
        this.chooseShowValue = chooseShowValue;
        this.innerBlock = innerBlock;
    }

    Integer choice=null;
    @Override
    public String fill(Context context) {
        return innerBlock.fill(context.getFromListItem(chooseVariable,choice));
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context, IO io) {
        choice=(new ChoiseVariable((List<HashMap<String, Object>>)context.get(chooseVariable),chooseShowValue)).read(io);
        HashMapVariable requiredData=getRequiredDataVariables(context);
        return requiredData;
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return innerBlock.getRequiredDataVariables(context.getFromListItem(chooseVariable,choice));
    }
}

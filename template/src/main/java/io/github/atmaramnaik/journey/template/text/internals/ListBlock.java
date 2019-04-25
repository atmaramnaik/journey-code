package io.github.atmaramnaik.journey.template.text.internals;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.data.variable.ListEveryVariable;
import io.github.atmaramnaik.journey.core.data.variable.UserProvidedSizeListVariable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListBlock  implements Block{
    @Getter
    String listVariable;

    @Getter
    List<Block> innerBlocks=new ArrayList<>();

    private ListBlock() {
    }

    public ListBlock(String listVariable) {
        this.listVariable = listVariable;
    }
    public ListBlock with(Block innerblock){
        innerBlocks.add(innerblock);
        return this;
    }
    @Override
    public String fill(Context context) {
        List<Object> list=context.getList(listVariable);
        String finalStr="";
        for(int i=0;i<list.size();i++){
            for (Block block:
                 innerBlocks) {
                finalStr+=block.fill(context.getFromListItem(listVariable,i));
            }
        }
        return finalStr;
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        if(context.hasList(listVariable)){
            List<Object> list=context.getList(listVariable);
            ListEveryVariable listEveryVariable=new ListEveryVariable(list.size());
            for(int i=0;i<list.size();i++){
                HashMapVariable innerVariableMap=new HashMapVariable(new HashMap<>());
                for (Block block:
                        innerBlocks) {
                    innerVariableMap.getDataDescription().putAll(block.getRequiredDataVariables(context.getFromListItem(listVariable,i)).getDataDescription());
                }
                listEveryVariable.getInnerVariables().add(innerVariableMap);
            }
            HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
            hashMapVariable.getDataDescription().put(listVariable,listEveryVariable);
            return hashMapVariable;
        } else {
            HashMapVariable innerVariableMap=new HashMapVariable(new HashMap<>());
            for (Block block:
                    innerBlocks) {
                innerVariableMap.getDataDescription().putAll(block.getRequiredDataVariables(context).getDataDescription());
            }
            UserProvidedSizeListVariable userProvidedSizeListVariable=new UserProvidedSizeListVariable(innerVariableMap);
            HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
            hashMapVariable.getDataDescription().put(listVariable,userProvidedSizeListVariable);
            return hashMapVariable;
        }
    }
}

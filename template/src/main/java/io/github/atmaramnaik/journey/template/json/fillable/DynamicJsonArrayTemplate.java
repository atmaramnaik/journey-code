package io.github.atmaramnaik.journey.template.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.types.JsonArray;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.data.variable.ListEveryVariable;
import io.github.atmaramnaik.journey.core.data.variable.UserProvidedSizeListVariable;
import io.github.atmaramnaik.journey.template.template.Template;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

public class DynamicJsonArrayTemplate implements Template<Json> {
    @Getter
    protected String listVariable=null;

    @Getter
    protected Template<Json> elementTemplate=null;

    public DynamicJsonArrayTemplate(String listVariable) {
        this.listVariable = listVariable;
    }

    public DynamicJsonArrayTemplate(String listVariable, Template<Json> elementTemplate) {
        this.listVariable = listVariable;
        this.elementTemplate = elementTemplate;
    }

    @Override
    public JsonArray fillReturnValue(Context context) {
        return fillDynamicArray(context, listVariable, elementTemplate);
    }

    public static JsonArray fillDynamicArray(Context context, String listVariable, Template<Json> elementTemplate) {
        List<Object> list=context.getList(listVariable);
        JsonArray jsonArray=new JsonArray();
        for(int i=0;i<list.size();i++){
            jsonArray.add(elementTemplate.fill(context.getFromListItem(listVariable,i)));
        }
        return jsonArray;
    }

    @Override
    public HashMapVariable getRequiredDataVariables(Context context) {
        return getHashMapVariable(context, listVariable, elementTemplate);
    }

    public static HashMapVariable getHashMapVariable(Context context, String listVariable, Template<Json> elementTemplate) {
        if(context.hasList(listVariable)){
            List<Object> list=context.getList(listVariable);
            ListEveryVariable listEveryVariable=new ListEveryVariable(list.size());
            for(int i=0;i<list.size();i++){
                HashMapVariable innerVariableMap=new HashMapVariable(new HashMap<>());
                innerVariableMap.getDataDescription().putAll(elementTemplate.getRequiredDataVariables(context.getFromListItem(listVariable,i)).getDataDescription());
                listEveryVariable.getInnerVariables().add(innerVariableMap);
            }
            HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
            hashMapVariable.getDataDescription().put(listVariable,listEveryVariable);
            return hashMapVariable;
        } else {
            HashMapVariable innerVariableMap=new HashMapVariable(new HashMap<>());
            innerVariableMap.getDataDescription().putAll(elementTemplate.getRequiredDataVariables(context).getDataDescription());
            UserProvidedSizeListVariable userProvidedSizeListVariable=new UserProvidedSizeListVariable(innerVariableMap);
            HashMapVariable hashMapVariable=new HashMapVariable(new HashMap<>());
            hashMapVariable.getDataDescription().put(listVariable,userProvidedSizeListVariable);
            return hashMapVariable;
        }
    }
}

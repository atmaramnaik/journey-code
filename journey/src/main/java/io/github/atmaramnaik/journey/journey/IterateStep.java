package io.github.atmaramnaik.journey.journey;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IterateStep implements Step {
    protected String variable;
    protected HashMap<String, Text> variables=new HashMap<>();
    protected Step step;

    public IterateStep(String variable, Step step) {
        this.variable = variable;
        this.step = step;
    }

    public IterateStep addVariable(String name,Text text){
        variables.put(name,text);
        return this;
    }

    @Override
    public void perform(Context context, IO io) {
        ensure(context,io);
        doOperation(context,io);
    }

    public void doOperation(Context context, IO io){
        List<Object> list=context.getList(variable);

        for(int i=0;i<list.size();i++){
            Context new_context=context.getFromListItem(variable,i);
            HashMap<String,Object> data=new HashMap<>();
            for (String variable:variables.keySet()){
                data.put(variable,variables.get(variable).process(new_context,io).getValue());
            }
            new_context.pour(data);
            step.perform(new_context,io);
        }
    }

    public void ensure(Context context,IO io){
        List<Object> list=context.getList(variable);
        if(list==null){
            List<HashMap<String,Object>> dataList=new ArrayList<>();
            IntegerHolder integerHolder;
            synchronized (io) {
                io.getWriter().write("Enter size of list as Integer");
                integerHolder = io.getReader().read(IntegerHolder.class);
            }
            for(int i=0;i<integerHolder.getValue();i++){
                dataList.add(new HashMap<>());
            }
            HashMap<String,Object> dataToAdd=new HashMap<>();
            dataToAdd.put(variable,dataList);
            context.pour(dataToAdd);
        }
    }
}

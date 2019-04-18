package io.github.atmaramnaik.journey.journey;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.template.io.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoopStep  implements Step{
    protected String variable;
    protected Step step;
    public LoopStep(String variable, Step step) {
        this.variable = variable;
        this.step = step;
    }
    @Override
    public void perform(Context context, IO io) {
        ensure(context,io);
        doOperation(context,io);
    }

    public void doOperation(Context context, IO io){
        List<Object> list=context.getList(variable);
        for(int i=0;i<list.size();i++){
            step.perform(context.getFromListItem(variable,i),io);
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

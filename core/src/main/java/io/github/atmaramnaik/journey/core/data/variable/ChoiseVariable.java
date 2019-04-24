package io.github.atmaramnaik.journey.core.data.variable;

import io.github.atmaramnaik.journey.core.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.core.io.IO;

import java.util.HashMap;
import java.util.List;

public class ChoiseVariable implements Readable<Integer> {
    List<HashMap<String,Object>> chooseFrom;
    String displayVariable;

    public ChoiseVariable(List<HashMap<String, Object>> chooseFrom, String displayVariable) {
        this.chooseFrom = chooseFrom;
        this.displayVariable = displayVariable;
    }

    @Override
    public Integer read(IO io) {
        ValueHolderVariable<Integer> valueHolderVariable=new ValueHolderVariable(IntegerHolder.class);
        for(int i=0;i<chooseFrom.size();i++){
            io.getWriter().write((i+1)+") "+chooseFrom.get(i).get(displayVariable)+"\n");
        }
        io.getWriter().write("Input your choice ");
        return valueHolderVariable.read(io)-1;
    }
}

package io.github.atmaramnaik.journey.template.data.variable;

import io.github.atmaramnaik.journey.template.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.template.io.IO;

import java.util.ArrayList;
import java.util.List;

public class UserProvidedSizeListVariable<T> implements Readable<List<T>> {
    Readable<T> innerVariable;

    public UserProvidedSizeListVariable(Readable<T> innerVariable) {
        this.innerVariable = innerVariable;
    }
    @Override
    public List<T> read(IO io) {
        List<T> list=new ArrayList<>();
        io.getWriter().write("\nEnter size of list as Integer\n");
        int size=io.getReader().read(IntegerHolder.class).getValue();
        for(int i = 0; i<size; i++){
            list.add(innerVariable.read(io));
        }
        return list;
    }
}

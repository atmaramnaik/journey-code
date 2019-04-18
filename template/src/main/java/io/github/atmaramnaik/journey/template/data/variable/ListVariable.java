package io.github.atmaramnaik.journey.template.data.variable;

import io.github.atmaramnaik.journey.template.io.IO;

import java.util.ArrayList;
import java.util.List;

public class ListVariable<T> implements Readable<List<T>> {
    Readable<T> innerVariable;
    int count;

    public ListVariable(Readable<T> innerVariable, int count) {
        this.innerVariable = innerVariable;
        this.count = count;
    }
    @Override
    public List<T> read(IO io) {
        List<T> list=new ArrayList<>();
        for(int i=0;i<count;i++){
            io.getWriter().write("\nEnter "+i+"th element");
            list.add(innerVariable.read(io));
        }
        return list;
    }
}

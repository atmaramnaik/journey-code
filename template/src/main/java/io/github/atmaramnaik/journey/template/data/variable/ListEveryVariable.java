package io.github.atmaramnaik.journey.template.data.variable;

import io.github.atmaramnaik.journey.template.io.IO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ListEveryVariable<T> implements Readable<List<T>> {
    @Getter
    List<Readable<T>> innerVariables;
    int count;

    public ListEveryVariable(List<Readable<T>> innerVariables, int count) {
        this.innerVariables = innerVariables;
        this.count = count;
    }

    public ListEveryVariable(int count) {
        this.count = count;
        this.innerVariables=new ArrayList<>();
    }

    @Override
    public List<T> read(IO io) {
        List<T> list=new ArrayList<>();
        for(int i=0;i<count;i++){
            io.getWriter().write("\nEnter "+i+"th element");
            list.add(innerVariables.get(i).read(io));
        }
        return list;
    }
}
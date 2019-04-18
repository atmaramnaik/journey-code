package io.github.atmaramnaik.journey.journey;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.IO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelLoopStep extends LoopStep {
    public ParallelLoopStep(String variable, Step step) {
        super(variable, step);
    }
    @Override
    public void doOperation(Context context, IO io) {
        List<Object> list=context.getList(variable);
        ExecutorService executorService= Executors.newFixedThreadPool(list.size());
        List<CompletableFuture<Void>> all=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            final int index=i;
            CompletableFuture<Void> future=CompletableFuture.supplyAsync(()->{
                    step.perform(context.getFromListItem(variable, index), io);
                return null;
            },executorService);
            all.add(future);
        }
        try {
            CompletableFuture.allOf(all.toArray(new CompletableFuture[all.size()])).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

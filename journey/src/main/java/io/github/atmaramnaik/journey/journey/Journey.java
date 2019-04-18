package io.github.atmaramnaik.journey.journey;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.template.text.TextTemplate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Journey extends SequenceStep{
    @Setter
    @Getter
    String name;
    TextTemplate finalResponse=null;

    private Journey() {
    }

    public Journey(Step step) {
        this.add(step);
    }

    public Journey with(Step step){
        this.add(step);
        return this;
    }
    public Journey responding(TextTemplate textTemplate){
        this.finalResponse=textTemplate;
        return this;
    }

    public Journey as(String name){
        this.name=name;
        return this;
    }

    public Journey(TextTemplate finalResponse) {
        this.finalResponse=finalResponse;
    }

    public Journey(List<Step> steps) {
        this.addAll(steps);
    }
    public void execute(Context context, IO io){
        io.getWriter().write("I think you want to "+this.name+"\n");
        perform(context,io);
        if(finalResponse!=null) {
            String strFinalResp = finalResponse.process(context, io).getValue();
            io.getWriter().write(strFinalResp);
        }
        io.getWriter().doneWriting();
    }

    @Override
    public void perform(Context context, IO io) {
        for (Step step:
                this) {
            step.perform(context,io);
        }
    }
    public static Journey journey(){
        return new Journey();
    }
}

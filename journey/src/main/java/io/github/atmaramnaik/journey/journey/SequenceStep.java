package io.github.atmaramnaik.journey.journey;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.IO;

import java.util.ArrayList;

public class SequenceStep extends ArrayList<Step> implements Step {
    @Override
    public void perform(Context context, IO io) {
        for (Step step:
                this) {
            step.perform(context,io);
        }
    }
    public SequenceStep with(Step step){
        this.add(step);
        return this;
    }
}

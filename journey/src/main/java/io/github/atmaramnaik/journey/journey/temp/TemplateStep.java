package io.github.atmaramnaik.journey.journey.temp;

import io.github.atmaramnaik.journey.journey.Step;
import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.template.Template;

public class TemplateStep implements Step {
    Template template;

    public TemplateStep(Template template) {
        this.template = template;
    }

    @Override
    public void perform(Context context, IO io) {
        template.process(context,io);
    }
}

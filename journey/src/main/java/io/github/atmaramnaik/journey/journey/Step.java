package io.github.atmaramnaik.journey.journey;
import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.IO;

public interface Step {
    void perform(Context context, IO io);
}

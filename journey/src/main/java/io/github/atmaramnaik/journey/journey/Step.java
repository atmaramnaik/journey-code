package io.github.atmaramnaik.journey.journey;
import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.io.IO;

public interface Step {
    void perform(Context context, IO io);
}

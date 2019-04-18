package io.github.atmaramnaik.journey.template.template.text.internals;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.io.IO;

public interface Block {
    String fill(Context context);
    HashMapVariable getRequiredDataVariables(Context context);
    default HashMapVariable getRequiredDataVariables(Context context, IO io){
        return getRequiredDataVariables(context);
    }
}

package io.github.atmaramnaik.journey.template.template.text.internals;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.io.IO;

public interface Block {
    String fill(Context context);
    HashMapVariable getRequiredDataVariables(Context context);
    default HashMapVariable getRequiredDataVariables(Context context, IO io){
        return getRequiredDataVariables(context);
    }
}

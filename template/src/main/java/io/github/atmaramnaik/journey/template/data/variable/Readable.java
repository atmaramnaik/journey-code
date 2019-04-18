package io.github.atmaramnaik.journey.template.data.variable;

import io.github.atmaramnaik.journey.template.io.IO;

public interface Readable<T> {
    T read(IO io);
}

package io.github.atmaramnaik.journey.core.data.variable;

import io.github.atmaramnaik.journey.core.io.IO;

public interface Readable<T> {
    T read(IO io);
}

package io.github.atmaramnaik.journey.core.io.console;

import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.core.io.Reader;
import io.github.atmaramnaik.journey.core.io.Writer;

public class ConsoleIO extends IO {
    private ConsoleIO(Writer writer, Reader reader) {
        super(writer, reader);
    }
    public ConsoleIO() {
        super(new ConsoleWriter(),new ConsoleReader());
    }
}

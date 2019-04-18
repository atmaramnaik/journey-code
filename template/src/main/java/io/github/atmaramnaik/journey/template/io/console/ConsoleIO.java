package io.github.atmaramnaik.journey.template.io.console;

import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.io.Reader;
import io.github.atmaramnaik.journey.template.io.Writer;

public class ConsoleIO extends IO {
    private ConsoleIO(Writer writer, Reader reader) {
        super(writer, reader);
    }
    public ConsoleIO() {
        super(new ConsoleWriter(),new ConsoleReader());
    }
}

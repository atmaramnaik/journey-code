package io.github.atmaramnaik.journey.hosted.io;

import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.io.Reader;
import io.github.atmaramnaik.journey.template.io.Writer;

public class WebIO extends IO {
    private WebIO(Writer writer, Reader reader) {
        super(writer, reader);
    }
    public WebIO(WebWriter writer,WebReader reader) {
        this((Writer) writer,(Reader) reader);
    }
}

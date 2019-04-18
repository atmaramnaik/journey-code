package io.github.atmaramnaik.journey.http.rest.steps;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.Helper;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RestStepTest {
    @Test
    public void shouldGet() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Helper.MockIO mockIO=aMockIO("2\n100526\n100527\n",byteArrayOutputStream);
        Context context=new Context();
        RestStep restStep= RestStep.get(
                text(string("https://quotes.rest/qod")))
        .capture(object("contents",object("quotes",array(object("quote",xVar("quote"))))));
        restStep.perform(context,mockIO);
    }
}

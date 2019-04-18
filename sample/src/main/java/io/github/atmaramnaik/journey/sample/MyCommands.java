package io.github.atmaramnaik.journey.sample;
import io.github.atmaramnaik.journey.coded.Journey;

import static io.github.atmaramnaik.journey.journey.Journey.journey;
import static io.github.atmaramnaik.journey.http.rest.steps.RestStep.*;
import static io.github.atmaramnaik.journey.template.template.Template.*;

public class MyCommands {

    @Journey(name = "Get Random Quote")
    public io.github.atmaramnaik.journey.journey.Journey getRandomQuote(){
        return journey()
                .with(
                        get(
                                text(string("https://quotes.rest/qod")))
                                .capture(object("contents",object("quotes",array(object("quote",xVar("quote"))))))
                ).responding(text(
                        string("Quote for now is: "),
                        textEx(var("quote").ofType(String.class))
                        )
                )
                .as("Get Random Quote");
    }

    @Journey(name = "Get Random joke")
    public io.github.atmaramnaik.journey.journey.Journey getRandomJoke(){
        return journey()
                .with(
                        get(text(string("http://api.icndb.com/jokes/random")))
                                .capture(object("value",object("joke",xVar("joke"))))
                )
                .responding(text(
                        string("Joke for now is: "),
                        textEx(var("joke").ofType(String.class))
                )).as("Get Random joke");
    }
}

package io.github.atmaramnaik.journey.sample;
import io.github.atmaramnaik.journey.coded.JourneyMethod;
import io.github.atmaramnaik.journey.journey.Journey;

import static io.github.atmaramnaik.journey.journey.Journey.journey;
import static io.github.atmaramnaik.journey.http.rest.steps.RestStep.*;
import static io.github.atmaramnaik.journey.template.Template.*;

//@Journey(name = "Hello")
public class MyCommands {

    @JourneyMethod(name = "Get Random Quote")
    public Journey getRandomQuote(){
        return journey()
                .with(
                        get(
                                text(string("https://quotes.rest/qod"))
                        ).capture(object("contents",object("quotes",array(object("quote",xVar("quote"))))))
                )
                .responding(text(
                        string("Quote for now is: "),
                        textEx(var("quote").ofType(String.class))
                        )
                );
    }

    @JourneyMethod(name = "Get Random joke")
    public Journey getRandomJoke(){
        return journey()
                .with(
                        get(text(string("http://api.icndb.com/jokes/random")))
                                .capture(object("value",object("joke",xVar("joke"))))
                )
                .responding(text(
                        string("Joke for now is: "),
                        textEx(var("joke").ofType(String.class))
                ));
    }
}

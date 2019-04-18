package io.github.atmaramnaik.journey.http.rest.steps.core;

import io.github.atmaramnaik.journey.http.rest.steps.RestStep;
import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.io.IO;
import io.github.atmaramnaik.journey.template.template.text.Text;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;


public class GetStep extends RestStep {
    public GetStep(Text url) {
        super(url);
    }

    @Override
    protected GetRequest getHttpRequest(Context context, IO io) {
        return Unirest.get(url.process(context,io).serialize());
    }


}

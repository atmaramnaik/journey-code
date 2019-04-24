package io.github.atmaramnaik.journey.http.rest.steps.core;

import io.github.atmaramnaik.journey.http.rest.steps.RestStepWithBody;
import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.template.Template;
import io.github.atmaramnaik.journey.template.template.text.Text;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class DeleteStep<Q> extends RestStepWithBody<Q> {
    public DeleteStep(Text url, Template<Q> body) {
        super(url, body);
    }
    @Override
    protected HttpRequestWithBody getHttpRequestWithBody(Context context, IO io) {
        return Unirest.delete(url.process(context,io).serialize());
    }
}

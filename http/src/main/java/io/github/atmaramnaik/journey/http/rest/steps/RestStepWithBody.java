package io.github.atmaramnaik.journey.http.rest.steps;
import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.Template;
import io.github.atmaramnaik.journey.template.text.Text;
import com.mashape.unirest.request.HttpRequestWithBody;

public abstract class RestStepWithBody<Q> extends RestStep {
    Template<Q> body;

    public RestStepWithBody(Text url, Template<Q> body) {
        super(url);
        this.body = body;
    }
    @Override
    protected HttpRequestWithBody getHttpRequest(Context context, IO io) {
        HttpRequestWithBody httpRequestWithBody=getHttpRequestWithBody(context,io);
        httpRequestWithBody.body(body.process(context, io).serialize());
        return httpRequestWithBody;
    }
    protected abstract HttpRequestWithBody getHttpRequestWithBody(Context context, IO io);
}

package io.github.atmaramnaik.journey.http.rest.steps;

import io.github.atmaramnaik.journey.http.rest.steps.core.*;
import io.github.atmaramnaik.journey.http.rest.steps.core.*;
import io.github.atmaramnaik.journey.journey.Step;
import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.Extractable;
import io.github.atmaramnaik.journey.template.Template;
import io.github.atmaramnaik.journey.template.text.Text;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import static io.github.atmaramnaik.journey.template.Template.*;
import lombok.Getter;

import java.util.HashMap;

public abstract class RestStep<T> implements Step {
    protected Text url;
    protected HashMap<String,Text> headers=new HashMap<>();
    protected HashMap<String,String> responseHeaders=new HashMap<>();
    public RestStep<T> withHeader(String header,Text headerTemplate){
        headers.put(header,headerTemplate);
        return this;
    }
    public RestStep<T> withHeader(String header,String value){
        headers.put(header,text(string(value)));
        return this;
    }
    @Getter
    protected Extractable<T> response=null;

    public RestStep(Text url) {
        this.url = url;
    }

    public RestStep<T> capture(Extractable<T> response){
        this.response=response;
        return this;
    }

    public RestStep<T> captureHeader(String headerName,String variableName) {
        this.responseHeaders.put(headerName,variableName);
        return this;
    }

    protected abstract HttpRequest getHttpRequest(Context context, IO io);
    @Override
    public void perform(Context context, IO io) {
        HttpRequest httpRequest=getHttpRequest(context,io);
        for (String key:
                headers.keySet()) {
            httpRequest.header(key,headers.get(key).process(context,io).serialize());
        }
        try {
            HttpResponse<String> sResponse=httpRequest.asString();
            if(response!=null){
                try {
                    T dataObject=response.deserialize(sResponse.getBody());
                    response.process(context,io,dataObject);
                } catch (DeSerializationException e) {
                    e.printStackTrace();
                }
            }
            HashMap<String,Object> capturedHeaders=new HashMap<>();
            for (String header:responseHeaders.keySet()){
                capturedHeaders.put(responseHeaders.get(header),sResponse.getHeaders().getFirst(header));
            }
            context.pour(capturedHeaders);
        } catch (UnirestException e) {
            throw new RuntimeException("Something went wrong",e);
        }
    }
    public static <Q> PostStep<Q> post(Text url, Template<Q> body){
        PostStep<Q> postStep=new PostStep<>(url,body);
        return postStep;
    }
    public static <Q> PatchStep<Q> patch(Text url, Template<Q> body){
        PatchStep<Q> patchStep=new PatchStep<>(url,body);
        return patchStep;
    }
    public static <Q> DeleteStep<Q> delete(Text url, Template<Q> body){
        DeleteStep<Q> deleteStep=new DeleteStep<>(url,body);
        return deleteStep;
    }
    public static <Q> PutStep<Q> put(Text url, Template<Q> body){
        PutStep<Q> putStep=new PutStep<>(url,body);
        return putStep;
    }
    public static GetStep get(Text url){
        GetStep getStep=new GetStep(url);
        return getStep;
    }
}

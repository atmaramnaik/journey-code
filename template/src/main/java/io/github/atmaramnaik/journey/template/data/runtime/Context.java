package io.github.atmaramnaik.journey.template.data.runtime;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private Map<String,Object> ref;
    @Getter
    Map<String,Object> currentContext=new HashMap<>();

    public Context() {
        ref=currentContext;
    }

    public Context(Map<String, Object> currentContext) {
        this.currentContext = currentContext;
        ref=currentContext;
    }

    private Context newFromthis(){
        Context context=new Context();
        context.currentContext.putAll(currentContext);
        return context;
    }
    public void pour(Map<String,Object> data){
        pourTo(ref,data);
        this.currentContext.putAll(data);
    }
    private void pourTo(Map<String,Object> to,Map<String,Object> pourThis){
        for (String key:
             pourThis.keySet()) {
            if(to.containsKey(key)){
                Object pourToObject=to.get(key);
                if(pourToObject instanceof Map){
                    pourTo((Map<String,Object>)pourToObject,(Map<String,Object>)pourThis.get(key));
                } else if(pourToObject instanceof List){
                    pourTo((List<Map<String,Object>>)pourToObject,(List<Map<String,Object>>)pourThis.get(key));
                }
            }
        }
    }
    private void pourTo(List<Map<String,Object>> to,List<Map<String,Object>> pourThis){
        if(to.size()>pourThis.size()){
            for (int i=0;i<pourThis.size();i++){
                pourTo(to.get(i),pourThis.get(i));
            }
        } else {
            for (int i=0;i<to.size();i++){
                pourTo(to.get(i),pourThis.get(i));
            }
            for (int i=to.size();i<pourThis.size();i++){
                to.add(pourThis.get(i));
            }
        }
    }
    public List<Object> getList(String name){
        return (List<Object>)currentContext.get(name);
    }
    public boolean hasList(String name){
        try {
            List<Object> list = (List<Object>) currentContext.get(name);
            if(list!=null) {
                return true;
            }
            return false;
        }catch (Exception ex){
            return false;
        }
    }

    public Object get(String name){
        return currentContext.get(name);
    }
    public Context getFromListItem(String variable,int index){
        List<Object> member=getList(variable);
        Object item=member.get(index);
        Context newContext=newFromthis();
        if(item instanceof Map){
            Map<String,Object> mapItem=(Map<String,Object>)item;
            newContext.ref=mapItem;
            newContext.currentContext.putAll(mapItem);
        } else {
            Map<String,Object> mapItem=new HashMap<>();
            mapItem.put("&this",item);
            newContext.currentContext.putAll(mapItem);
        }
        return newContext;
    }
}

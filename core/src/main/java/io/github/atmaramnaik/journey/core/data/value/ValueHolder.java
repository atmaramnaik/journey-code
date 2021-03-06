package io.github.atmaramnaik.journey.core.data.value;

import io.github.atmaramnaik.journey.core.data.value.custom.JsonHolder;
import io.github.atmaramnaik.journey.core.data.value.custom.XmlHolder;
import io.github.atmaramnaik.journey.core.data.value.primitive.*;
import io.github.atmaramnaik.journey.core.data.value.types.Xml;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ValueHolder<T> implements Serializable,Json {
    public static Map<Class,Class<? extends ValueHolder>> valueHolderMap=new LinkedHashMap<>();
    public static Map<Class,Class<? extends ValueHolder>> fallbackValueHolderMap =new LinkedHashMap<>();
    static Class<? extends ValueHolder> getAppropriateValueHolder(Class<?> tClass){
        for (Class<?> target:
                valueHolderMap.keySet()) {
            if(target.isAssignableFrom(tClass)){
                return valueHolderMap.get(target);
            }
        }
        for (Class<?> target:
                fallbackValueHolderMap.keySet()) {
            if(target.isAssignableFrom(tClass)){
                return fallbackValueHolderMap.get(target);
            }
        }
        return null;
    }
    static {
        valueHolderMap.put(Long.class, LongHolder.class);
        valueHolderMap.put(Integer.class, IntegerHolder.class);
        valueHolderMap.put(Boolean.class, BooleanHolder.class);
        valueHolderMap.put(Float.class, FloatHolder.class);
        valueHolderMap.put(Double.class, DoubleHolder.class);
        valueHolderMap.put(String.class, StringHolder.class);
        fallbackValueHolderMap.put(Xml.class, XmlHolder.class);
        fallbackValueHolderMap.put(Json.class, JsonHolder.class);
        fallbackValueHolderMap.put(Object.class, ObjectHolder.class);
    }
    @Getter
    @Setter
    protected T value;

    public ValueHolder() {
    }
    public static Class<?> getTypeFor(Class<? extends ValueHolder> valueholderClass){
        for (Class key:valueHolderMap.keySet()){
            if(valueHolderMap.get(key).equals(valueholderClass)){
                return key;
            }
        }
        return String.class;
    }
    public static <T> ValueHolder getNewValueHolderFor(T data){
        if(data==null){
            return new NullHolder();
        }
        ValueHolder valueHolder= null;
        try {
            valueHolder = getAppropriateValueHolder(data.getClass()).getConstructor().newInstance();
            valueHolder.setValue(data);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return valueHolder;
    }
    public static <T> ValueHolder getNewValueHolderForType(Class<T> tClass){
        ValueHolder valueHolder= null;
        try {
            valueHolder = getAppropriateValueHolder(tClass).getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
        if(valueHolder==null)
        {
            valueHolder=new ObjectHolder();
        }
        return valueHolder;
    }
}

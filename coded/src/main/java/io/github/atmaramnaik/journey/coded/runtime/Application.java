package io.github.atmaramnaik.journey.coded.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Application {
    public void run(String input){
        try {
            Method method=Class.forName(this.getClass().getPackage().getName()+".Starter").getMethod("start",String.class);
            method.invoke(null,input);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

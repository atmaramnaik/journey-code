package io.github.atmaramnaik.journey.coded;

import io.github.atmaramnaik.journey.journey.Journey;
import io.github.atmaramnaik.journey.journey.JourneyManager;
import io.github.atmaramnaik.journey.core.io.IO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JourneyExecutor {
    public static void runProgram(String input, JourneyRegistry registry, IO io){
        JourneyManager journeyManager=new JourneyManager();
        for (JourneyRegistryEntry entry:registry.entries){
            try {
                Class<?> clazz=Class.forName(entry.enclosingClass);
                Constructor<?> ctr=clazz.getConstructor();
                Object obj=ctr.newInstance();
                Journey journey=((Journey)clazz.getMethod(entry.methodName).invoke(obj));
                if(journey.getName()==null || journey.getName().equals("")){
                    journey.setName(entry.name);
                }
                journeyManager.add(journey);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        journeyManager.start(input,io);
    }
}

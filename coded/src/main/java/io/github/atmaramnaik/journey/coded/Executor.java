package io.github.atmaramnaik.journey.coded;

import io.github.atmaramnaik.journey.journey.Journey;
import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.template.io.console.ConsoleIO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Executor {
    public static void runProgram(JourneyRegistry registry){
        ConsoleIO consoleIO=new ConsoleIO();
        int counter=0;
        for (JourneyRegistryEntry entry:
             registry.entries) {
            consoleIO.getWriter().write(counter + 1 + ")" + entry.name + "\n");
            counter++;
        }
        consoleIO.getWriter().write("Select:");
        IntegerHolder integerHolder=consoleIO.getReader().read(IntegerHolder.class);
        //BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        int index=integerHolder.getValue();
        if(registry.entries.size()>index){
            JourneyRegistryEntry entry=registry.entries.get(index);
            //System.out.println("Executing journey: "+entry.name);
            try {
                Class<?> clazz=Class.forName(entry.enclosingClass);
                Constructor<?> ctr=clazz.getConstructor();
                Object obj=ctr.newInstance();
                Journey journey=((Journey)clazz.getMethod(entry.methodName).invoke(obj));
                journey.setName(entry.name);
                journey.execute(new Context(),consoleIO);
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
    }
}

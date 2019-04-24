package io.github.atmaramnaik.journey.coded.runtime;

import io.github.atmaramnaik.journey.coded.JourneyMethod;
import io.github.atmaramnaik.journey.coded.JourneyExecutor;
import io.github.atmaramnaik.journey.coded.JourneyRegistry;
import io.github.atmaramnaik.journey.coded.JourneyRegistryEntry;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.core.io.console.ConsoleIO;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import java.lang.reflect.Method;
import java.util.Set;

public abstract class Application {
    JourneyRegistry journeyRegistry=null;
    private void initJourneyRegistry() {
        if(journeyRegistry==null) {
            journeyRegistry=new JourneyRegistry();
            Set<Method> methods = new Reflections(this.getClass().getPackage().getName(), new MethodAnnotationsScanner()).getMethodsAnnotatedWith(JourneyMethod.class);
            for (Method method : methods
            ) {
                JourneyMethod annotation = method.getAnnotation(JourneyMethod.class);
                journeyRegistry.add(new JourneyRegistryEntry(annotation.name(), method.getDeclaringClass().getName(), method.getName()));
            }
        }
    }
    public void run(String input, IO io){
        initJourneyRegistry();
        JourneyExecutor.runProgram(input,journeyRegistry,io);
    }
    public void run(String input){
        run(input, new ConsoleIO());
    }
}

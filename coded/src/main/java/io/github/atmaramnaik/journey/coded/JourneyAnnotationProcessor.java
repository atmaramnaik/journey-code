package io.github.atmaramnaik.journey.coded;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@SupportedAnnotationTypes("com.atmaram.journey.coded.Journey")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class JourneyAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> annotedElementsApplication=roundEnvironment.getElementsAnnotatedWith(Application.class);
        String genPack="";
        for (Element annotedElement:annotedElementsApplication
             ) {
            TypeElement typeElement=(TypeElement)annotedElement;
            genPack=((PackageElement)typeElement.getEnclosingElement()).getQualifiedName().toString();
        }
        Set<? extends Element> annotedElements=roundEnvironment.getElementsAnnotatedWith(Journey.class);
        Set<ExecutableElement> annotedMethods=ElementFilter.methodsIn(annotedElements);
        String sPackage="com.atmaram.journey.coded";
        List<String> commands=new ArrayList<>();
        for (ExecutableElement annotedMethod:annotedMethods
             ) {
            String name=annotedMethod.getAnnotation(Journey.class).name();

            String add="journeyRegistry.add(new JourneyRegistryEntry(\""+name+"\",\""+((TypeElement)annotedMethod.getEnclosingElement()).getQualifiedName().toString()+"\",\""+annotedMethod.getSimpleName().toString()+"\"));";
            ((TypeElement)annotedMethod.getEnclosingElement()).getQualifiedName();
//            try {
//                sPackage = ((PackageElement) annotedMethod.getEnclosingElement().getEnclosingElement()).getQualifiedName().toString();
//            }catch (Exception ex){
//
//            }
            commands.add(add);
        }
        StringBuilder builder=new StringBuilder();
        builder
                .append("package "+genPack+";")
                .append("import "+sPackage+".*;")
                .append("public class Starter {")
                .append("public static JourneyRegistry journeyRegistry=new JourneyRegistry();")
                .append("public static void start(){")
                .append(String.join(" ",commands))
                .append("Executor.runProgram(journeyRegistry);")
                .append("}")
                .append("}");

        try{
            JavaFileObject javaFileObject=processingEnv.getFiler().createSourceFile(sPackage+".Starter");
            Writer writer=javaFileObject.openWriter();
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }
}

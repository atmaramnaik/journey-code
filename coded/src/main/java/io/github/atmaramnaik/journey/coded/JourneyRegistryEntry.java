package io.github.atmaramnaik.journey.coded;

public class JourneyRegistryEntry {
    String name;
    String enclosingClass;
    String methodName;

    public JourneyRegistryEntry(String name, String enclosingClass, String methodName) {
        this.name = name;
        this.enclosingClass = enclosingClass;
        this.methodName = methodName;
    }
}

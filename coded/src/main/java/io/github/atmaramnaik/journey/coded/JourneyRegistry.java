package io.github.atmaramnaik.journey.coded;

import java.util.ArrayList;
import java.util.List;

public class JourneyRegistry {
    public List<JourneyRegistryEntry> entries=new ArrayList<>();
    public void add(JourneyRegistryEntry entry){
           entries.add(entry);
    }
}

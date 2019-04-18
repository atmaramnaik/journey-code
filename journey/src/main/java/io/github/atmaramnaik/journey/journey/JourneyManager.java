package io.github.atmaramnaik.journey.journey;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.template.io.IO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JourneyManager {
    List<Journey> journeys=new ArrayList<>();
    public JourneyManager add(Journey journey){
        journeys.add(journey);
        return this;
    }
    private Journey match(String input,IO io){
        List<String> lFilter= Arrays.asList(input.split(" "));
        List<Integer> ranks=journeys.stream().map((journey)->{
            int rank=0;
            for (int i = 0; i < lFilter.size(); i++) {
                if (journey.getName().toLowerCase().contains(lFilter.get(i).toLowerCase()))
                    rank++;
            }
            return rank;
        }).collect(Collectors.toList());
        int topRank=ranks.stream().reduce(0,(one,two)->one>two?one:two);
        List<Journey> filtered_journeys = new ArrayList<>();
        for(int i=0;i<journeys.size();i++){
            if(ranks.get(i)==topRank){
                filtered_journeys.add(journeys.get(i));
            }
        }
        int iCommand = 0;
        synchronized (io) {
            if (filtered_journeys.size() > 1) {
                io.getWriter().write("What you want to do: \n");

                for (int i = 0; i < filtered_journeys.size(); i++) {
                    io.getWriter().write(i + 1 + ") " + filtered_journeys.get(i).getName() + "\n");
                }
                io.getWriter().write("Enter Number: ");
                iCommand = io.getReader().read(IntegerHolder.class).getValue() - 1;
            }
        }
        if (filtered_journeys.size() == 0) {
            io.getWriter().write("No Matching Journey");
            return null;
        }
        return filtered_journeys.get(iCommand);
    }
    public void start(String input, IO io){
        Journey journey=match(input,io);
        if(journey!=null) {
            journey.execute(new Context(), io);
        }
    }
}

package io.github.atmaramnaik.journey.sample;

import io.github.atmaramnaik.journey.coded.runtime.Application;
@io.github.atmaramnaik.journey.coded.Application
public class Main extends Application{
    public static void main(String[] args){
        new Main().run(String.join(" ",args));
    }
}

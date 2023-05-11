package org.example.Task2;

import java.util.Random;

public class Singleton {
    private static Singleton instance;
    private byte[] weight;
    private Singleton(){
        weight = new byte[100000];
        new Random().nextBytes(weight);
    }
    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}

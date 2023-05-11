package org.example.Task2;

import org.example.Task2.MATtest;
import org.example.Task2.Singleton;

public class Main2sem {
    public static void main(String[] args){
        MATtest test = new MATtest(Singleton.getInstance());
        test.run();
    }
}
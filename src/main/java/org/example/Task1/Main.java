package org.example.Task1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setOut(new PrintStream(new FileOutputStream("log_file_class.csv")));

        var data = new ArrayList<A>(20000);
        while(true){
            var A = new A();
            data.add(A);

            var total = Runtime.getRuntime().totalMemory();
            var free = Runtime.getRuntime().freeMemory();
            var max = Runtime.getRuntime().maxMemory();
            System.out.print(total);
            System.out.print(",");
            System.out.print(free);
            System.out.print(",");
            System.out.print(max);
            System.out.print(",");
            System.out.print(total-free);
            System.out.print(",");
            System.out.println(max-total+free);

        }
//        System.out.println(data.size());

    }
}
//136314880
//134326160
//-21024
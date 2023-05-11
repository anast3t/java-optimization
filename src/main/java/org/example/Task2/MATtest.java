package org.example.Task2;

import org.example.Task2.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class MATtest {
    private final Singleton singletonRef;

    private class Bean {
        byte[] bytearray;
        List<Integer> arrayList;
        Bean selfReference;

        public Bean() {
            bytearray = new byte[100000];
            new Random().nextBytes(bytearray);
            arrayList = Stream.generate(new Random()::nextInt).limit(100000).toList();
            selfReference = this;
        }
    }

    private class thread extends Thread {
        private Bean bean;
        private Singleton _singletonRef;

        public thread() {
            _singletonRef = singletonRef;
            bean = new Bean();
        }

        @Override
        public void run() {
            System.out.print("Running ");
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("Stopping ");
            System.out.println(Thread.currentThread().getName());
        }
    }

    public MATtest(Singleton singletonRef) {
        this.singletonRef = singletonRef;
    }

    public void run(){
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Thread thread = new thread();
            threads.add(thread);
            thread.start();
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }
}

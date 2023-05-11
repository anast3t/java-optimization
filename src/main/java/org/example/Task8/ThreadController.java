package org.example.Task8;

import java.util.ArrayList;

public class ThreadController {
    ArrayList<Thread> threads = new ArrayList<>(0);

    public void start(int maxThreads) throws InterruptedException {
        double degree = 0;
        for(;;){
            degree+=2;
            double rad = Math.toRadians(degree);
            double sin = Math.sin(rad);
            int needThreads = ((int) Math.ceil(sin*maxThreads/2)) + maxThreads/2;
            System.out.println(needThreads);
            while(needThreads > threads.size()){
//                System.out.println("Adding thread");
                Thread thread = new Thread(){
                    boolean active = true;
                    public void kill(){
                        this.active = false;
                    }
                    @Override
                    public void run() {
                        while(active){
                            try {
//                                System.out.println(this.getName() + " still alive");
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                this.active = false;
                                System.out.println(this.getName() + " stopped");
                            }
                        }
                    }
                };
                thread.start();
                threads.add(thread);
            }
            while(needThreads < threads.size()){
                if(threads.size() > 0){
//                    System.out.println("Removing thread cause:" + needThreads);
                    threads.remove(threads.size() - 1).interrupt();
                }
            }
            Thread.sleep(50);
        }
    }
}

package com.agataporwit;

import java.util.Random;

import static com.agataporwit.MonitorThreads.buffer;
import static com.agataporwit.MonitorThreads.next_in;

public class MonitorProducer extends Thread {

    private static final Random RandomK1 = new Random();
    private static final Random RandomT1 = new Random();
    public String threadID;

    public MonitorProducer(String name) {
        threadID = name;
    }

    @Override
    public void run() {

        try {
            while (true) {
                //get random number k1
                int k1 = (int) RandomK1.nextGaussian() * 2 + 5;
                int i = 0;
                synchronized (this) {
                    for (i = 0; i <= k1; i++) {
                        buffer[(next_in + i) % buffer.length] += 1;
                        next_in = (next_in + i) % buffer.length;
                    }
                }
                int t1 = (int) RandomT1.nextGaussian() * 500 + 1000;
                Thread.sleep(t1);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }

}

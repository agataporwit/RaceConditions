package com.agataporwit;

import java.util.Random;
import java.util.concurrent.Semaphore;

import static com.agataporwit.SemaphoreThreads.buffer;
import static com.agataporwit.SemaphoreThreads.next_in;

public class SemaphoreProducer extends Thread {

    Semaphore semaphore;
    String threadID;

    private static final Random RandomK1 = new Random();
    private static final Random RandomT1 = new Random();

    public SemaphoreProducer(Semaphore semaphore, String name) {
        super(name);
        this.semaphore = semaphore;
        this.threadID = name;

    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this.threadID + " WAITING ... ");
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int k1 = (int) RandomK1.nextGaussian() * 2 + 5;
            int i;
            for (i = 0; i <= k1; i++) {
                buffer[(next_in + i) % buffer.length] += 1;
                next_in = (next_in + i) % buffer.length;
            }
            System.out.println(this.threadID + " RELEASE ");
            semaphore.release();

            try {
            int t1 = (int) RandomT1.nextGaussian() * 500 + 1000;
            Thread.sleep(t1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

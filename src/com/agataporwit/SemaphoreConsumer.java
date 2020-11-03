package com.agataporwit;

import java.util.Random;
import java.util.concurrent.Semaphore;
import static com.agataporwit.SemaphoreThreads.buffer;
import static com.agataporwit.SemaphoreThreads.next_out;


public class SemaphoreConsumer extends Thread{
    private static final Random RandomK2 = new Random();
    private static final Random RandomT2 = new Random();
    Semaphore semaphore;
    String threadID;

    public SemaphoreConsumer(Semaphore semaphore, String name){
        super(name);
        this.semaphore = semaphore;
        this.threadID = name;
    }
    @Override
    public void run() {

        while (true) {
            try {
                //get random number and sleep
                int t2 = (int) RandomT2.nextGaussian() * 500 + 1000;
                Thread.sleep(t2);
                //get semaphores
                System.out.println(this.threadID + " WAITING <(@_@)>");
                semaphore.acquire();
            } catch (InterruptedException e) {
                System.out.println(" <(._.)> " + e);
            }
            System.out.println(this.threadID + " PERMISSION GRANTED <(._.)>");

            int k2 = (int) RandomK2.nextGaussian() * 2 + 5;

            int j = 0;
            for (j = 0; j <= k2; j++) {
                int data = buffer[(next_out + j) % buffer.length];
                buffer[(next_out + j) % buffer.length] = 0;

                if (data > 1) {
                    System.out.println("  READING BUFFER MAGIC " + data + " POTION READY, BYE");
                    semaphore.release();
                   System.exit(0);
                }

                next_out = (next_out + j) % buffer.length;
            }
            for (int i = 0; i < buffer.length; i++) {
                System.out.println(" BREWING POTIONS " + i + " POTIONS STORED " + buffer[i]);
            }
            System.out.println(this.threadID + " RELEASE PERMIT ");
            semaphore.release();

        }
    }

}

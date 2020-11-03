package com.agataporwit;

import java.util.Random;

import static com.agataporwit.MonitorThreads.buffer;
import static com.agataporwit.MonitorThreads.next_out;

public class MonitorConsumer extends Thread {

    private static final Random RandomK2 = new Random();
    private static final Random RandomT2 = new Random();
    public String threadID;

    public MonitorConsumer(String name) {
        threadID = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int t2 = (int) RandomT2.nextGaussian() * 500 + 1000;
                Thread.sleep(t2);
                int k2 = (int) RandomK2.nextGaussian()  * 2 + 5;
                synchronized (this) {
                    for (int j = 0; j <= k2; j++) {
                        int data = buffer[(next_out + j) % buffer.length];
                        buffer[(next_out + j) % buffer.length] = 0;
                        if (data > 1) {
                            System.out.println(" READING BUFFER MAGIC " + data + " FROM BOOK OF SPELLS " + j + " BYE ");
                            //thread
                            System.exit(0);
                        }
                        next_out = (next_out + j) % buffer.length;
                    }
                }

                for (int i = 0; i < buffer.length; i++) {
                    System.out.println("BREWING POTIONS " + i + " POTION STORED:  " + buffer[i]);
                }
            }

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

package com.agataporwit;


import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicThreads {
    private static final Random RandomK1 = new Random();
    private static final Random RandomT1 = new Random();
    private static final Random RandomK2 = new Random();
    private static final Random RandomT2 = new Random();

    protected static AtomicIntegerArray buffer;
    protected static int next_in;
    protected static int next_out;

    public AtomicThreads(int n) {
        buffer = new AtomicIntegerArray(n);
        next_in = 0;
        next_out = 0;
    }

    public static void main(String[] args) throws InterruptedException {

        AtomicThreads test = new AtomicThreads(20);
        final int bufferSize = buffer.length();

        //The Producer
        new Thread(() -> {
            try {
                while (true) {
                    int k1 = (int) RandomK1.nextGaussian() * 2 + 5;
                    int i = 0;
                    for (i = 0; i <= k1; i++) {
                        buffer.getAndIncrement((next_in + i) % bufferSize);
                        next_in = (next_in + i) % bufferSize;
                    }

                    int t1 = (int) RandomT1.nextGaussian() * 300 + 1000;
                    Thread.sleep(t1);
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }).start();
//The consumer
        new Thread(() -> {
            try {
                while (true) {
                    int t2 = (int) RandomT2.nextGaussian() * 300 + 1000;
                    Thread.sleep(t2);
                    int k2 = (int) RandomK2.nextGaussian() * 2 + 5;
                    for (int j = 0; j <= k2; j++) {
                        int data = buffer.get((next_out + j) % bufferSize);
                        buffer.getAndSet((next_out + j) % bufferSize, 0);
                        if (data > 1) {
                            System.out.println(" READING BUFFER MAGIC " + data + " POSITION " + j + " BYE ");

                            System.exit(0);
                        }
                        next_out = (next_out + j) % bufferSize;
                    }
                    for (int i = 0; i < bufferSize; i++) {
                        System.out.println(" BREWING POTIONS " + i + " POTION STORED " + buffer.get(i));
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }).start();
    }
}
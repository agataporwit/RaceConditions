package com.agataporwit;

import java.util.concurrent.Semaphore;

public class SemaphoreThreads {
    protected static int [] buffer;
    protected static int next_in;
    protected static int next_out;

    public SemaphoreThreads(int n){
        buffer = new int[n];
        next_in = 0;
        next_out = 0;
    }

    public static void main(String[] args) throws InterruptedException {

        //create a semaphore pool with one permit
        Semaphore semaphore = new Semaphore(1, true);
        SemaphoreThreads semaphoreCheck = new SemaphoreThreads(20);
        SemaphoreProducer producer = new SemaphoreProducer(semaphore, " POTION PRODUCER ");
        SemaphoreConsumer consumer = new SemaphoreConsumer(semaphore, " POTION CONSUMER");

        producer.start();
        consumer.start();
    }

}

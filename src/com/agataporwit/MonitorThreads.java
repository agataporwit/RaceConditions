package com.agataporwit;

public class MonitorThreads {

    //Random number generators


    //circular buffer
    protected static int[] buffer;
    protected static int next_in;
    protected static int next_out;

    public MonitorThreads(int n) {
        buffer = new int[n];
        next_in = 0;
        next_out = 0;
    }

    public static void main(String[] args) {

        MonitorThreads test = new MonitorThreads(40);
        MonitorProducer producer = new MonitorProducer(" POTION PRODUCER ");
        MonitorConsumer consumer = new MonitorConsumer(" POTION CONSUMER ");
        //consumer thread

        producer.start();
        consumer.start();
    }

}

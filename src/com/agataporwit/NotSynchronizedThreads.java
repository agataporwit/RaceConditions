package com.agataporwit;

import java.util.Random;

import static java.lang.Thread.sleep;


public class NotSynchronizedThreads {
    private static final Random RandomK1 = new Random();
    private static final Random RandomT1 = new Random();
    private static final Random RandomK2 = new Random();
    private static final Random RandomT2 = new Random();

    protected static int[] buffer;
    protected static int next_in;
    protected static int next_out;

    public NotSynchronizedThreads(int n){
//The buffer is a large array of n integers, initialized to all zeros.
        buffer = new int [n];
        next_in = 0;
        next_out =0;

    }
    public NotSynchronizedThreads(int n, int k1, int t1, int k2, int t2){
        buffer = new int[n];
        next_in = 0;
        next_out = 0;

    }
    public static void main(String[] args) throws InterruptedException{
        NotSynchronizedThreads noSynchronisedRace = new NotSynchronizedThreads(40);

        new Thread(()->{
            try {
                while (true){
        int k1 = (int) RandomK1.nextGaussian()+5 * 2;
        int i;
        for (i =0; i <= k1; i++){
            buffer[(next_in + 1) % buffer.length] += 1;
            next_in = (next_in + i) % buffer.length;
            //t1 random number generating
            int t1 =(int) RandomT1.nextGaussian() * 500 + 4000;
            sleep(t1);
        }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();

        //consumer

        new Thread(() ->{
            try {
                while (true){
                    int t2 = (int) RandomT2.nextGaussian() * 500 + 4000;
                    sleep(t2);
                    int k2 = (int) RandomK2.nextGaussian() + 5 * 2;
                    int j;
                    for (j = 0; j<=k2; j++){
                        int data = buffer[(next_out + j )% buffer.length];
                        buffer[(next_out + j) % buffer.length] = 0;
                        if(data >1){
                            System.out.println(" READING BUFFER MAGIC " + data + " FROM BOOK OF SPELLS  " + j + " BYE");
                            System.exit(0);
                        }
                        next_out = (next_out + j) % buffer.length;
                    }
                    for(int i=0; i< buffer.length; i++){
                        System.out.println(" BREWING POTIONS " + i + " POTIONS STORED " + buffer[i]);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}

# RaceConditions
Race conditions

Project objective

Implement the solution to the bounded buffer problem from the section titled Semaphores, but without any P or V operations. Observe and eliminate a race condition, which refers to a situation where multiple processes manipulate some shared data concurrently and the outcome depends on the order of execution.
Description

        The buffer is a large array of n integers, initialized to all zeros.
        The producer and the consumer are separate concurrent threads in a process.
        The producer executes short bursts of random duration. During each burst of length k1, the producer adds a 1 to the next k1 slots of the buffer, modulo n.
        The consumer also executes short bursts of random duration. During each burst of length k2, the consumer reads the next k2 slots and resets each to 0.
        If any slot contains a number greater than 1, then a race condition has been detected: The consumer was unable to keep up and thus the producer has 
        added a 1 to a slot that has not yet been reset.
        Both producer and consumer sleep periodically for random time intervals to emulate unpredictable execution speeds.

producer thread:

while (1)
   get random number k1
   for i from 0 to k1
      buffer[next_in + k1 mod n] += 1
   next_in = next_in + k1 mod n
   get random number t1
   sleep for t1 seconds


consumer thread:

while(1) 
   get random number t2
   sleep for t2 seconds
   get random number k2
   for i from 0 to k2
      data = buffer[next_out + k2 mod n]
      if (data > 1) exit and report race condition
      next_out = next_out + k2 mod n

Requirements
Make these values configurable:

        the size of the shared buffer
        the number of elements to load into the shared buffer on each iteration of the while(1) loop
        the number of elements to unload from the shared buffer on each iteration of the while(1) loop
        the length of time the producer thread sleeps 
        the length of time the consumer thread sleeps
        for the first implementation, do not include any synchronization mechanisms
        for the second implementation, use the Java Semaphore class to synchronize the threads and avoid the race condition
        for the third implementation, use Java atomic data types (e.g., AtomicIntegerArray, AtomicInteger)
        for the fourth implementation, use Java Monitors
        place each of these variations in their own class

Assignment

       1. With the initial implementation (the one with no synchronization) experiment with different values of n, k, and t until a race condition is observed.
       2.Provide three additional implementations, using various synchronization methods


###############################################################################################################################################
Exercise 4.2.4: Details of the bounded buffer problem.
Assume the declarations:

int buffer[n];
int data;
int next_in = next_out = 0;

(a)

Expand the pseudocode statements "place data into buffer" and "remove data from buffer" of the bounded buffer code by showing the specific changes to the above variables.

Exercise 4.2.5: The bounded buffer problem without semaphores.

Modify the pseudocode for the bounded buffer problem from the previous exercise without using semaphores for synchronization. Instead, the consumer uses a while loop to wait in case all buffers are empty. Similarly, the producer uses a while loop to wait if no buffer slots are available for writing. To prevent the producer from overtaking the consumer, enforce that at most n - 1 buffer slots can be full. Thus the condition (next_in == next_out) is true only when all slots are empty.

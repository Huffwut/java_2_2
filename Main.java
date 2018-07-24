package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    static int tweetCount = 0;
    public static void main(String[] args) throws Exception {
        WriteInterface writer = new FileWriter("data.txt");
        FLineReader reader = new LineReader("data.txt");
        Tweet tweet = new Tweet();
        ReadNotification read = new ReadNotification(writer, reader);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
        Future future = executorService.scheduleWithFixedDelay(read, 10, 60, TimeUnit.SECONDS);

        while (!bufferedReader.readLine().equals("q")){
            String comm = bufferedReader.readLine();
            tweet.tweetonTwitter(comm);
           //System.out.println(comm);
        }
        System.out.println("quitting");
        future.cancel(true);
        executorService.shutdownNow();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("quitting 2");
        //writer.close();
    }
}

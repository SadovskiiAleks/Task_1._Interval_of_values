package org.example;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        List<Future> futures = new ArrayList<>();
        final ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);

        long startTs = System.currentTimeMillis(); // start time
        for (String text : texts) {
            futures.add(threadPool.submit(new GivedCode(text)));
        }

        int max = 0;
        for (Future future : futures) {
            max = Math.max((int) future.get(), max);
        }
        System.out.println("Max value is - " + max);
        threadPool.shutdown();

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
package io.github.bewaremypower;

import java.util.concurrent.*;

public class Starter {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        BackgroundConsumer consumer = new BackgroundConsumer();
        executor.execute(consumer);

        try (ConsoleProducer producer = new ConsoleProducer()) {
            producer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        consumer.halt();
        try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
        }
    }
}

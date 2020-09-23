package io.github.bewaremypower;

import io.github.bewaremypower.wrapper.ConfiguredConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class BackgroundConsumer extends ConfiguredConsumer implements Runnable {
    private volatile boolean running = false;
    private final long maxNumMessages;

    public BackgroundConsumer() {
        super();
        maxNumMessages = Long.parseLong(getProperty("max.num.messages"));
    }

    @Override
    public void run() {
        long index = 0;
        running = true;
        while (running && (maxNumMessages <= 0 || index < maxNumMessages)) {
            ConsumerRecords<String, String> records = poll();
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("[" + index + "] " + record);
                index++;
            }
        }
        try {
            close();
        } catch (Exception e) {
            System.err.println("Failed to close consumer: " + e.getMessage());
        }
    }

    public void halt() {
        running = false;
    }
}

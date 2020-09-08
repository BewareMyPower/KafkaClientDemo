package io.github.bewaremypower;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class BackgroundConsumer implements Runnable {
    private volatile boolean running = false;
    private KafkaConsumer<String, String> consumer;
    private long pollTimeoutMs;
    private long maxNumMessages;

    BackgroundConsumer() {
        try {
            Config.loadKafkaConfig();
            Properties properties = Config.getConsumerProperties();
            consumer = new KafkaConsumer<>(properties);
            pollTimeoutMs = Long.parseLong(properties.getProperty("poll.timeout.ms"));
            maxNumMessages = Long.parseLong(properties.getProperty("max.num.messages"));
        } catch (IOException e) {
            System.out.println("Failed to create consumer: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        long index = 0;
        consumer.subscribe(Collections.singletonList(Config.getTopic()));
        running = true;
        while (running && (maxNumMessages <= 0 || index < maxNumMessages)) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(pollTimeoutMs));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("[" + index + "] " + record);
                index++;
            }
        }
        consumer.close();
    }

    public void halt() {
        running = false;
    }
}

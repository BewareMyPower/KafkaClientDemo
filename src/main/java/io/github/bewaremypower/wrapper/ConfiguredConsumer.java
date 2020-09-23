package io.github.bewaremypower.wrapper;

import io.github.bewaremypower.Config;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

/**
 * A wrapper of Kafka consumer with the config from local properties file
 */
public class ConfiguredConsumer implements AutoCloseable {
    private KafkaConsumer<String, String> consumer;
    private long pollTimeoutMs;
    private Properties properties;

    public ConfiguredConsumer() {
        try {
            Config.loadKafkaConfig();
            properties = Config.getConsumerProperties();
            consumer = new KafkaConsumer<>(properties);
            pollTimeoutMs = Long.parseLong(properties.getProperty("poll.timeout.ms"));
            consumer.subscribe(Collections.singleton(Config.getTopic()));
        } catch (IOException e) {
            System.out.println("Failed to create consumer: " + e.getMessage());
        }
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void close() throws Exception {
        consumer.close();
    }

    public ConsumerRecords<String, String> poll() {
        // Just for compatibility with client <= 2.0
        return consumer.poll(pollTimeoutMs);
    }
}

package io.github.bewaremypower.wrapper;

import io.github.bewaremypower.Config;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * A wrapper of Kafka producer with the config from local properties file
 */
public class ConfiguredProducer implements AutoCloseable {
    private KafkaProducer<String, String> producer;
    private String topic;
    private Callback callback = null;

    public ConfiguredProducer() {
        try {
            Config.loadKafkaConfig();
            producer = new KafkaProducer<>(Config.getProducerProperties());
            topic = Config.getTopic();
        } catch (IOException e) {
            System.out.println("Failed to create producer: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        producer.close();
    }

    public void flush() {
        producer.flush();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Future<RecordMetadata> send(String value, String key) {
        return producer.send(new ProducerRecord<>(topic, key, value), callback);
    }

    public Future<RecordMetadata> send(String value) {
        return producer.send(new ProducerRecord<>(topic, value), callback);
    }
}

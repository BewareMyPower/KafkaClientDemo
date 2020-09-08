package io.github.bewaremypower;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleProducer implements AutoCloseable {
    private KafkaProducer<String, String> producer;
    private String topic;

    ConsoleProducer() {
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

    void run() {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String line = stdin.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("Send line: " + line);
                // TODO: parse key from line
                producer.send(new ProducerRecord<>(topic, line));
            }
        } catch (IOException e) {
            System.out.println("Failed to read: " + e.getMessage());
        }
        producer.flush();
        System.out.println("Producer flushed");
    }
}

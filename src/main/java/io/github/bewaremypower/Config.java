package io.github.bewaremypower;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static String topic = null;

    public static String getTopic() {
        return topic;
    }

    @NotNull
    public static void loadKafkaConfig() throws IOException {
        Properties properties = getProperties("kafka.configFile", "kafka.properties");
        topic = properties.getProperty("topic");
    }

    @NotNull
    public static Properties getProducerProperties() throws IOException {
        return getProperties("producer.configFile", "producer.properties");
    }

    @NotNull
    public static Properties getConsumerProperties() throws IOException {
        return getProperties("consumer.configFile", "consumer.properties");
    }

    @NotNull
    private static Properties getProperties(String configPathArgName, String defaultFileName) throws IOException {
        Properties properties = new Properties();
        String configPath = System.getProperty(configPathArgName);
        if (configPath != null) {
            properties.load(new FileReader(configPath));
        } else {
            properties.load(Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream(defaultFileName)));
        }
        return properties;
    }
}

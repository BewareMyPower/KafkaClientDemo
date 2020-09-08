## KafkaClientDemo

Kafka client demos.

## Usage

Build the jar with dependencies and run:

```bash
$ mvn clean package
$ cd targets
$ java -jar KafkaClientDemo-1.0-SNAPSHOT.jar
```

If you want to change the properties files and rerun, use Maven to run the class:

```bash
$ mvn compile
# Modify *.properties in target/classes/
$ mvn exec:java -Dexec.mainClass=io.github.bewaremypower.Starter
```

## Config

Here're three properties files in [resources](src/main/resources/) directory.

In `kafka.properties` you can configure the topic name.

The `producer.properties` and `consumer.properties` are the properties to construct [`KafkaProducer`](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html) and [`KafkaConsumer`](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html).

In addition, the `consumer.properties` has extra properties:

| Key                | Value                                                        |
| ------------------ | ------------------------------------------------------------ |
| `poll.timeout.ms`  | The poll timeout in milliseconds each time the consumer call `poll` |
| `max.num.messages` | The maximum number of messages that the consumer could receive |

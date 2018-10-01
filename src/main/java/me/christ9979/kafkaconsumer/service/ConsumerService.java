package me.christ9979.kafkaconsumer.service;

import me.christ9979.kafkaconsumer.component.ConsumerCreator;
import me.christ9979.kafkaconsumer.constant.KafkaProperties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private ConsumerCreator consumerCreator;

    public void runConsumer() {

        Consumer<Long, String> consumer = consumerCreator.createConsumer();

        int noMessageFound = 0;

        while (true) {

            ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);

            if (consumerRecords.count() == 0) {
                ++noMessageFound;
                if (noMessageFound > kafkaProperties.getMaxRetryCount())
                    break;
                else
                    continue;
            }

            consumerRecords.forEach(record -> {
                System.out.println("Record key: " + record.key());
                System.out.println("Record value: " + record.value());
                System.out.println("Record partition: " + record.partition());
                System.out.println("Record offset: " + record.offset());

            });

            consumer.commitAsync();
        }

        consumer.close();
    }
}

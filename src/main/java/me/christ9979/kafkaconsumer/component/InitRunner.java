package me.christ9979.kafkaconsumer.component;

import me.christ9979.kafkaconsumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements CommandLineRunner {

    @Autowired
    private ConsumerService consumerService;

    @Override
    public void run(String... args) throws Exception {

        consumerService.runConsumer();
    }
}

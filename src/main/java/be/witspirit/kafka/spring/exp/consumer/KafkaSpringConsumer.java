package be.witspirit.kafka.spring.exp.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Instant;

@SpringBootApplication
@EnableKafka
public class KafkaSpringConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSpringConsumer.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringConsumer.class, args);
    }


    @KafkaListener(topics = "partUpdates")
    public void listen(String message) {
        LOG.info("Received Message : " + message);
    }
}

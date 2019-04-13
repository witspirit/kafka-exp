package be.witspirit.kafka.spring.exp.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.function.SupplierUtils;

import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SpringBootApplication
public class KafkaSpringPublisher implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSpringPublisher.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringPublisher.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i < 1000; i++) {
            ListenableFuture<SendResult<String, String>> partUpdates = kafkaTemplate.send("partUpdates", "partA-"+i+"@" + Instant.now());
            // Instead of waiting using a get, this could be done async and then messages could continue to be produced while we await confirmation from the broker.
            // For this tiny experiment, it's probably easier to KISS
            SendResult<String, String> sendResult = partUpdates.get();
            LOG.info("SendResult: " + sendResult);
        }
    }
}

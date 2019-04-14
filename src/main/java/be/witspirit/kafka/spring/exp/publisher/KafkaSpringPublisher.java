package be.witspirit.kafka.spring.exp.publisher;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class KafkaSpringPublisher implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSpringPublisher.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringPublisher.class, args);
    }

    @Bean
    public NewTopic partUpdates() {
        return new NewTopic("partUpdates", 20, (short) 1);
    }


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void run(String... args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            for (String partId : new String[]{"A", "B", "C", "D", "E", "F", "G"}) {

                String message = String.format("part%s-%4d@%s", partId, i, Instant.now());
                String key = partId; // Will be used to consistently assign a key to the same partition
                ListenableFuture<SendResult<String, String>> partUpdates = kafkaTemplate.send("partUpdates", key, message);
                // Instead of waiting using a get, this could be done async and then messages could continue to be produced while we await confirmation from the broker.
                // For this tiny experiment, it's probably easier to KISS
                SendResult<String, String> sendResult = partUpdates.get();
                LOG.info("SendResult: " + sendResult);

            }
        }
    }
}

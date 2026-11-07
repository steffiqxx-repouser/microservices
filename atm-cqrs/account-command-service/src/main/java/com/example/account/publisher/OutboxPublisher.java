package com.example.account.publisher;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.account.entity.OutboxEvent;
import com.example.account.repository.OutboxRepository;

@Component
public class OutboxPublisher {

    private final OutboxRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(
            OutboxRepository repository,
            KafkaTemplate<String, String> kafkaTemplate) {

        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 2000)
    public void publishEvents() {

        List<OutboxEvent> events =
            repository.findTop100ByPublishedFalseOrderByCreatedAtAsc();

        for (OutboxEvent event : events) {

            try {
                kafkaTemplate.send(
                    "atm-account-events",
                    event.getId().toString(),
                    event.getPayload()
                ).get();

                event.setPublished(true);
                repository.save(event);

            } catch (Exception ex) {
                // Leave event unpublished.
                // It will be retried in a later poll.
            }
        }
    }
}

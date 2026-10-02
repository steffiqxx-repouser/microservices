package com.example.outbox.publisher;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.outbox.domain.OutboxEvent;
import com.example.outbox.infrastructure.OutboxEventRepository;

@Component
public class OutboxPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(OutboxPublisher.class);

    private final OutboxEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(
            OutboxEventRepository repository,
            KafkaTemplate<String, String> kafkaTemplate) {

        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 1000)
    public void publish() {

        List<OutboxEvent> events =
                repository.findTop100ByPublishedAtIsNullOrderByCreatedAtAsc();

        for (OutboxEvent event : events) {

            try {

                kafkaTemplate
                        .send(
                                "document-events",
                                event.getAggregateId().toString(),
                                event.getPayload()
                        )
                        .get();

                event.markPublished();

                repository.save(event);

                log.info(
                        "Published outbox event {}",
                        event.getId()
                );

            } catch (Exception e) {

                log.error(
                        "Failed to publish outbox event {}",
                        event.getId(),
                        e
                );
            }
        }
    }
}

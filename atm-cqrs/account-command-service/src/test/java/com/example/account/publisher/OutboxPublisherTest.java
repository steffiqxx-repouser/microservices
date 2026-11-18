package com.example.account.publisher;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.example.account.entity.OutboxEvent;
import com.example.account.repository.OutboxRepository;

@ExtendWith(MockitoExtension.class)
class OutboxPublisherTest {

    @Mock private OutboxRepository repository;
    @Mock private KafkaTemplate<String, String> kafkaTemplate;

    private OutboxPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new OutboxPublisher(repository, kafkaTemplate);
    }

    @Test
    void publishEventsDoesNothingWhenOutboxIsEmpty() {
        when(repository.findTop100ByPublishedFalseOrderByCreatedAtAsc())
            .thenReturn(List.of());

        publisher.publishEvents();

        verify(kafkaTemplate, never()).send(anyString(), anyString(), anyString());
    }

    @Test
    void publishEventsMarksEventPublishedAfterSuccessfulSend() {
        OutboxEvent event = unpublishedEvent();
        when(repository.findTop100ByPublishedFalseOrderByCreatedAtAsc())
            .thenReturn(List.of(event));
        when(kafkaTemplate.send(
            "atm-account-events",
            event.getId().toString(),
            event.getPayload()
        )).thenReturn(CompletableFuture.completedFuture(null));

        publisher.publishEvents();

        org.junit.jupiter.api.Assertions.assertTrue(event.isPublished());
        verify(repository).save(event);
    }

    @Test
    void publishEventsLeavesEventUnpublishedAfterSendFailure() {
        OutboxEvent event = unpublishedEvent();
        CompletableFuture<SendResult<String, String>> failed =
            new CompletableFuture<>();
        failed.completeExceptionally(new IllegalStateException("Kafka unavailable"));

        when(repository.findTop100ByPublishedFalseOrderByCreatedAtAsc())
            .thenReturn(List.of(event));
        when(kafkaTemplate.send(
            "atm-account-events",
            event.getId().toString(),
            event.getPayload()
        )).thenReturn(failed);

        publisher.publishEvents();

        org.junit.jupiter.api.Assertions.assertFalse(event.isPublished());
        verify(repository, never()).save(event);
    }

    private OutboxEvent unpublishedEvent() {
        OutboxEvent event = new OutboxEvent();
        event.setId(UUID.randomUUID());
        event.setPayload("{\"eventType\":\"ACCOUNT_DEBITED\"}");
        event.setEventType("ACCOUNT_DEBITED");
        event.setPublished(false);
        event.setCreatedAt(LocalDateTime.now());
        return event;
    }
}

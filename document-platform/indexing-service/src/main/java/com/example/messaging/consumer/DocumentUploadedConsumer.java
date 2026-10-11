package com.example.messaging.consumer;

import com.example.indexing.service.DocumentIndexingService;
import com.example.messaging.event.DocumentUploadedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentUploadedConsumer {

    private final DocumentIndexingService indexingService;

    public DocumentUploadedConsumer(
            DocumentIndexingService indexingService
    ) {
        this.indexingService = indexingService;
    }

    @KafkaListener(
            topics = "${kafka.topics.document-events}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "documentUploadedKafkaListenerContainerFactory"
    )
    public void consume(DocumentUploadedEvent event) {

        indexingService.index(event);
    }
}

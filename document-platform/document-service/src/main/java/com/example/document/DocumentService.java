package com.example.document;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.document.api.DocumentUploaded;
import com.example.document.domain.Document;
import com.example.document.repository.DocumentRepository;
import com.example.outbox.domain.OutboxEvent;
import com.example.outbox.infrastructure.OutboxEventRepository;

import jakarta.transaction.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public DocumentService(
            DocumentRepository documentRepository,
            OutboxEventRepository outboxRepository,
            ObjectMapper objectMapper) {

        this.documentRepository = documentRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UUID upload(
            String filename,
            String storageKey) {

        UUID documentId = UUID.randomUUID();

        Document document = new Document(
                documentId,
                filename,
                storageKey,
                Instant.now()
        );

        documentRepository.save(document);

        UUID eventId = UUID.randomUUID();

        DocumentUploaded event = new DocumentUploaded(
                eventId,
                documentId,
                filename,
                storageKey,
                Instant.now()
        );

        String payload;

        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (JacksonException e) {
            throw new IllegalStateException(
                    "Failed to serialize DocumentUploaded event",
                    e
            );
        }

        OutboxEvent outboxEvent = new OutboxEvent(
                eventId,
                "Document",
                documentId,
                "DocumentUploaded",
                payload,
                Instant.now()
        );

        outboxRepository.save(outboxEvent);

        return documentId;
    }
}

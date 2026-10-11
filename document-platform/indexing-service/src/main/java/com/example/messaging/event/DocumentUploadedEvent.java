package com.example.messaging.event;

import java.time.Instant;
import java.util.UUID;

public record DocumentUploadedEvent(
        UUID eventId,
        UUID documentId,
        String fileName,
        String contentType,
        String storageKey,
        Instant occurredAt
) {
}

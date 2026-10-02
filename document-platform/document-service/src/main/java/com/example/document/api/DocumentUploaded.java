package com.example.document.api;

import java.time.Instant;
import java.util.UUID;

public record DocumentUploaded(
        UUID eventId,
        UUID documentId,
        String filename,
        String storageKey,
        Instant occurredAt
) {
}

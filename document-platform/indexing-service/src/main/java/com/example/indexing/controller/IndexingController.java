package com.example.indexing.controller;



import com.example.indexing.infrastructure.IndexedDocumentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/indexing")
public class IndexingController {

    private final IndexedDocumentRepository repository;

    public IndexingController(
            IndexedDocumentRepository repository
    ) {
        this.repository = repository;
    }

    @GetMapping("/{documentId}")
    public IndexingStatusResponse getStatus(
            @PathVariable UUID documentId
    ) {

        return repository.findByDocumentId(documentId)
                .map(document ->
                        new IndexingStatusResponse(
                                document.getDocumentId(),
                                document.getStatus(),
                                document.getIndexedAt(),
                                document.getFailureReason()
                        )
                )
                .orElseThrow();
    }

    public record IndexingStatusResponse(
            UUID documentId,
            Object status,
            java.time.Instant indexedAt,
            String failureReason
    ) {
    }
}

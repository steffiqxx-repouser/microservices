package com.example.indexing.service;

//package com.example.indexing.indexing.application;

import com.example.indexing.domain.IndexedDocument;
import com.example.indexing.infrastructure.IndexedDocumentRepository;
import com.example.indexing.infrastructure.SearchIndexClient;
import com.example.inbox.domain.ProcessedEvent;
import com.example.inbox.infrastructure.ProcessedEventRepository;
import com.example.messaging.event.DocumentUploadedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentIndexingService {

    private final ProcessedEventRepository processedEventRepository;
    private final IndexedDocumentRepository indexedDocumentRepository;
    private final SearchIndexClient searchIndexClient;

    public DocumentIndexingService(
            ProcessedEventRepository processedEventRepository,
            IndexedDocumentRepository indexedDocumentRepository,
            SearchIndexClient searchIndexClient
    ) {
        this.processedEventRepository = processedEventRepository;
        this.indexedDocumentRepository = indexedDocumentRepository;
        this.searchIndexClient = searchIndexClient;
    }

    @Transactional
    public void index(DocumentUploadedEvent event) {

        if (processedEventRepository.existsById(event.eventId())) {
            return;
        }

        var indexedDocument =
                indexedDocumentRepository
                        .findByDocumentId(event.documentId())
                        .orElseGet(() ->
                                IndexedDocument.pending(
                                        event.documentId(),
                                        event.fileName(),
                                        event.contentType(),
                                        event.storageKey()
                                )
                        );

        indexedDocumentRepository.save(indexedDocument);

        searchIndexClient.index(indexedDocument);

        indexedDocument.markIndexed();

        indexedDocumentRepository.save(indexedDocument);

        processedEventRepository.save(
                ProcessedEvent.of(event.eventId())
        );
    }
}

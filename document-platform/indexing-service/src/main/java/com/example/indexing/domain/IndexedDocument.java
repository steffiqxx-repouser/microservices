package com.example.indexing.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "indexed_document",
        indexes = {
                @Index(
                        name = "idx_indexed_document_document_id",
                        columnList = "document_id",
                        unique = true
                )
        }
)
public class IndexedDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "document_id", nullable = false, unique = true)
    private UUID documentId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IndexStatus status;

    @Column(name = "indexed_at")
    private Instant indexedAt;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected IndexedDocument() {
    }

    private IndexedDocument(
            UUID documentId,
            String fileName,
            String contentType,
            String storageKey
    ) {
        this.documentId = documentId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.storageKey = storageKey;
        this.status = IndexStatus.PENDING;

        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public static IndexedDocument pending(
            UUID documentId,
            String fileName,
            String contentType,
            String storageKey
    ) {
        return new IndexedDocument(
                documentId,
                fileName,
                contentType,
                storageKey
        );
    }

    public void markIndexed() {
        this.status = IndexStatus.INDEXED;
        this.indexedAt = Instant.now();
        this.failureReason = null;
        this.updatedAt = Instant.now();
    }

    public void markFailed(String reason) {
        this.status = IndexStatus.FAILED;
        this.failureReason = reason;
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public IndexStatus getStatus() {
        return status;
    }

    public Instant getIndexedAt() {
        return indexedAt;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

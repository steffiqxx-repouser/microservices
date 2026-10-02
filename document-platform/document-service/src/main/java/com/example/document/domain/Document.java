package com.example.document.domain;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    private UUID id;

    private String filename;

    private String storageKey;

    private Instant createdAt;

    protected Document() {
    }

    public Document(
            UUID id,
            String filename,
            String storageKey,
            Instant createdAt) {

        this.id = id;
        this.filename = filename;
        this.storageKey = storageKey;
        this.createdAt = createdAt;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

    // getters
}


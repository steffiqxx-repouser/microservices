package com.example.indexing.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.indexing.domain.IndexedDocument;

public interface IndexedDocumentRepository extends JpaRepository<IndexedDocument, UUID> {

	Optional<IndexedDocument> findByDocumentId(UUID documentId);

	boolean existsByDocumentId(UUID documentId);
}

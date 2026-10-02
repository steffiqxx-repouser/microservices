package com.example.document.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.document.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<UUID> upload(
            @RequestParam String filename) {

        String storageKey =
                "documents/" + UUID.randomUUID();

        UUID documentId =
                documentService.upload(filename, storageKey);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(documentId);
    }
}


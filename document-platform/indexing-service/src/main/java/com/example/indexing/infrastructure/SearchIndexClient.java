package com.example.indexing.infrastructure;

import com.example.indexing.domain.IndexedDocument;

public interface SearchIndexClient {

    void index(IndexedDocument document);
}

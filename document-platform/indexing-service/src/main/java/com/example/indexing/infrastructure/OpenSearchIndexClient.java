package com.example.indexing.infrastructure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;

import com.example.indexing.domain.IndexedDocument;

public class OpenSearchIndexClient implements SearchIndexClient {

	private static final String INDEX_NAME = "documents";

    private final OpenSearchClient client;

    public OpenSearchIndexClient(OpenSearchClient client) {
        this.client = client;
    }

    @Override
    public void index(IndexedDocument document) {

        var source = new HashMap<String, Object>();

        source.put("documentId", document.getDocumentId().toString());
        source.put("fileName", document.getFileName());
        source.put("contentType", document.getContentType());
        source.put("storageKey", document.getStorageKey());
        source.put("indexedAt", document.getIndexedAt());

        try {
            var request = new IndexRequest.Builder<Map<String, Object>>()
                    .index(INDEX_NAME)
                    .id(document.getDocumentId().toString())
                    .document(source)
                    .build();

            client.index(request);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to index document " + document.getDocumentId(),
                    exception
            );
        }
    }

}

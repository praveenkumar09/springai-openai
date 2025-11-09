package com.praveen.springai_openai.rag;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

public class WebSearchDocumentRetriever implements DocumentRetriever {
    private static final Logger logger = LoggerFactory.getLogger(WebSearchDocumentRetriever.class);
    private static final String TAVILY_API_KEY = "TAVILY_API_KEY";
    private static final String TAVILY_API_URL = "https://api.tavily.com/search";
    private static final int DEFAULT_RESULT_LIMIT = 5;
    private final int resultLimit;
    private final RestClient restClient;

    public WebSearchDocumentRetriever(RestClient.Builder restClientBuilder, int resultLimit) {
        Assert.notNull(restClientBuilder, "restClientBuilder must not be null");
        String apiKey = System.getenv(TAVILY_API_KEY);
        Assert.hasText(apiKey, "TAVILY_API_KEY must be set");
        this.restClient = restClientBuilder
                .baseUrl(TAVILY_API_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION,"Bearer " + apiKey)
                .build();
        if(resultLimit <= 0){
            throw new IllegalArgumentException("resultLimit must be greater than 0");
        }
        this.resultLimit = resultLimit;
    }

    @Override
    public List<Document> retrieve(Query query) {
        Assert.notNull(query, "query must not be null");

        String q = query.text();
        Assert.hasText(q, "query must not be empty");

        TavilyResponsePayload responsePayload = restClient
                .post()
                .body(new TavilyRequestPayload(q, "advanced", resultLimit))
                .retrieve()
                .body(TavilyResponsePayload.class);

        if(responsePayload == null || CollectionUtils.isEmpty(responsePayload.results())){
            return List.of();
        }

        List<Document> docs = new ArrayList<>(responsePayload.results().size());
        for(TavilyResponsePayload.Hit hit: responsePayload.results()){
            Document doc = Document.builder()
                    .text(hit.content())
                    .metadata("title", hit.title())
                    .metadata("url", hit.url())
                    .score(hit.score())
                    .build();
            docs.add(doc);
        }
        return docs;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TavilyRequestPayload(String query, String searchDepth, int maxResults) {}

    record TavilyResponsePayload(List<Hit> results) {
        record Hit(String title, String url, String content, Double score) {}
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RestClient.Builder clientBuilder;
        private int resultLimit = DEFAULT_RESULT_LIMIT;

        private Builder() {}

        public Builder restClientBuilder(RestClient.Builder clientBuilder) {
            this.clientBuilder = clientBuilder;
            return this;
        }

        public Builder maxResults(int maxResults) {
            if (maxResults <= 0) {
                throw new IllegalArgumentException("maxResults must be greater than 0");
            }
            this.resultLimit = maxResults;
            return this;
        }

        public WebSearchDocumentRetriever build() {
            return new WebSearchDocumentRetriever(clientBuilder, resultLimit);
        }
    }
}

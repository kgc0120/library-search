package com.library;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NaverClient {
    private final RestClient restClient;
    private final String naverUrl;
    private final String clientId;
    private final String clientSecret;

    public NaverClient(@Value("${external.naver.url:NOT_FOUND}") String naverUrl,
                       @Value("${external.naver.headers.client-id}") String clientId,
                       @Value("${external.naver.headers.client-secret}") String clientSecret) {
        this.restClient = RestClient.create();
        this.naverUrl = naverUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String search(String query) {
        String uriString = UriComponentsBuilder.fromHttpUrl(naverUrl + "/v1/search/book.json")
                .queryParam("query", query)
                .queryParam("display", 1)
                .queryParam("start", 1)
                .toUriString();

        return restClient.get()
                .uri(uriString)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .body(String.class);
    }


}

package com.ecoharvest.deliveryorder.client;

import com.ecoharvest.deliveryorder.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductClient {
    private final WebClient webClient;

    @Autowired
    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://5fholgbq0b.execute-api.us-east-1.amazonaws.com/product-service").build();
    }

    public Product getProductDetails(String productId) {
        return webClient.get()
                .uri("/api/product/{productId}", productId)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}

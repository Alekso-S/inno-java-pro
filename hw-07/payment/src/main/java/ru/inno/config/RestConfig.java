package ru.inno.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import ru.inno.api.dto.ApiResponseError;
import ru.inno.exception.ProductNotFoundEx;

import java.io.IOException;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(ResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8081")
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    public ResponseErrorHandler errorHandler(ObjectMapper objectMapper) {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull ClientHttpResponse response) throws IOException {
                return response.getStatusCode().isError();
            }

            @Override
            public void handleError(@NonNull ClientHttpResponse response) throws IOException {
                if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    ApiResponseError responseError = objectMapper.readValue(response.getBody(), ApiResponseError.class);
                    System.out.println(responseError.getMessage());
                    throw new ProductNotFoundEx();
                } else {
                    System.out.println(response.getStatusCode());
                    throw new RuntimeException(response.getStatusCode().toString());
                }
            }
        };
    }
}

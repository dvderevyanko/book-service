package com.book.shared;

import com.book.adapter.output.repository.SpringPostgresAuthorRepository;
import com.book.adapter.output.repository.SpringPostgresBookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("book_db")
            .withUsername("postgres")
            .withPassword("postgres");

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected SpringPostgresBookRepository bookRepository;

    @Autowired
    protected SpringPostgresAuthorRepository authorRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    static {
        postgres.start();
    }

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    public <T> ResponseEntity<T> get(String url, Class<T> cl) {
        return execute(url, null, HttpMethod.GET, cl);
    }

    public <T> ResponseEntity<T> post(String url, Object body, Class<T> cl) {
        return execute(url, body, HttpMethod.POST, cl);
    }

    public <T> ResponseEntity<T> patch(String url, Object body, Class<T> cl) {
        return execute(url, body, HttpMethod.PATCH, cl);
    }

    private <T> ResponseEntity<T> execute(String url, Object body, HttpMethod method, Class<T> cl) {
        HttpEntity<String> entity;
        try {
            String bodyString = body != null ? objectMapper.writeValueAsString(body) : null;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            entity = new HttpEntity<>(bodyString, headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return restTemplate.exchange(
                url,
                method,
                entity,
                cl
        );
    }

    public void resetDb() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }
}
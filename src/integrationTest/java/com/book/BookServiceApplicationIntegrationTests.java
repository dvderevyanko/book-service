package com.book;

import com.book.shared.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookServiceApplicationIntegrationTests extends IntegrationTest {
    @Test
    void contextLoad() {

    }
}
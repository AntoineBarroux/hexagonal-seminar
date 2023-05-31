package com.liksi.hexagonal.seminar;

import okhttp3.mockwebserver.MockResponse;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractIntegrationTest {

    protected MockResponse createMockResponseFromFixture(final String fileName) {
        try {
            return new MockResponse()
                    .setResponseCode(200)
                    .setBody(Files.readString(Paths.get(getClass().getClassLoader().getResource("fixtures/" + fileName).toURI())))
                    .setHeader("Content-Type", "application/json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected MockResponse createMockResponse400FromFixture(final String fileName) {
        try {
            return new MockResponse()
                    .setResponseCode(400)
                    .setBody(Files.readString(Paths.get(getClass().getClassLoader().getResource("fixtures/" + fileName).toURI())))
                    .setHeader("Content-Type", "application/json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

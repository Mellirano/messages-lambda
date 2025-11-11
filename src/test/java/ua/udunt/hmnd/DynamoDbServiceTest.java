package ua.udunt.hmnd;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.udunt.hmnd.service.DynamoDbService;

import java.time.Instant;

@Slf4j
@QuarkusTest
public class DynamoDbServiceTest {

    @Inject
    DynamoDbService dynamoDbService;

    @Test
    public void addItemTest() {
        String uuid = dynamoDbService
                .addItem(
                        "Hello World!",
                        Instant.now().plusSeconds(10),
                        "Mr Test",
                        "arn:aws:sns:eu-west-1:003034548932:messages-topic",
                        "c170d6b3-7d7a-44a0-8f0b-c38fb9e8ad8c"
                ).join();

        Assertions.assertNotNull(uuid, "UUID must not be null");
        Assertions.assertFalse(uuid.isBlank(), "UUID must not be blank");
        log.info("Created message with uuid: {}", uuid);
    }

}

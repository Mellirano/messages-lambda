package ua.udunt.hmnd.service;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import ua.udunt.hmnd.support.DynamoDbItemBuilder;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Startup
@ApplicationScoped
public class DynamoDbService {

    @ConfigProperty(name = "TABLE_NAME")
    String tableName;

    @Inject
    DynamoDbAsyncClient dynamoDbAsyncClient;

    public CompletableFuture<String> addItem(String message,
                                             Instant ttl,
                                             String sender,
                                             String topicArn,
                                             String subscriberId) {

        Map<String, AttributeValue> item = DynamoDbItemBuilder.builder()
                .message(message)
                .ttl(ttl)
                .sender(sender)
                .topicArn(topicArn)
                .subscriberId(subscriberId)
                .build();

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        return dynamoDbAsyncClient.putItem(request)
                .whenComplete((resp, ex) -> {
                    if (ex != null) {
                        log.error("Failed to put item {} into {}", item.get("uuid").s(), tableName, ex);
                    } else {
                        log.info("Successfully stored message {} in {}", item.get("uuid").s(), tableName);
                    }
                })
                .thenApply(resp -> item.get("uuid").s());
    }

}

package ua.udunt.hmnd.support;

import lombok.Builder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DynamoDbItemBuilder {

    @Builder
    public static Map<String, AttributeValue> buildItem(String message,
                                                        Instant ttl,
                                                        String sender,
                                                        String topicArn,
                                                        String subscriberId) {

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("uuid", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        item.put("message", AttributeValue.builder().s(message).build());
        item.put("sender", AttributeValue.builder().s(sender).build());
        item.put("topicArn", AttributeValue.builder().s(topicArn).build());
        item.put("subscriberId", AttributeValue.builder().s(subscriberId).build());
        item.put("ttl", AttributeValue.builder().n(Long.toString(ttl.getEpochSecond())).build());
        return item;
    }

}

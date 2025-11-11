package ua.udunt.hmnd.support;

import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import ua.udunt.hmnd.model.Message;

import java.util.Map;
import java.util.Optional;

public class MessageBuilder {

    public static Message build(Map<String, AttributeValue> image) {
        return Message.builder()
                .uuid(Optional.ofNullable(image.get("uuid"))
                        .map(AttributeValue::getS)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid uuid value")))
                .body(Optional.ofNullable(image.get("message"))
                        .map(AttributeValue::getS)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid message value")))
                .sender(Optional.ofNullable(image.get("sender"))
                        .map(AttributeValue::getS)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid sender value")))
                .topicArn(Optional.ofNullable(image.get("topicArn"))
                        .map(AttributeValue::getS)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid topicArn")))
                .subscriberId(Optional.ofNullable(image.get("subscriberId"))
                        .map(AttributeValue::getS)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid subscriberId value")))
                .ttl(Optional.ofNullable(image.get("ttl"))
                        .map(a -> TtlFormatter.formatTtl(a.getN()))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid ttl value")))
                .build();
    }

}

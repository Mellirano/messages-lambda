package ua.udunt.hmnd.service;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import ua.udunt.hmnd.model.Message;

import java.util.Map;

@Slf4j
@Startup
@ApplicationScoped
public class SnsService {

    @Inject
    SnsClient snsClient;

    public void sendNotification(String eventName, Message message) {
        String snsMessageTitle;
        String snsMessage;
        if ("REMOVE".equals(eventName)) {
            snsMessageTitle = "Delayed message";
            snsMessage = String.format("You received a delayed message from user: %s.\nMessage: %s", message.getSender(), message.getBody());
        } else {
            snsMessageTitle = "New message";
            snsMessage = String.format("User: %s registered new message. Be ready to receive it at: %s", message.getSender(), message.getTtl());
        }
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(snsMessage)
                    .subject(snsMessageTitle)
                    .topicArn(message.getTopicArn())
                    .messageAttributes(Map.of(
                            "recipientId", MessageAttributeValue.builder()
                                    .dataType("String")
                                    .stringValue(message.getSubscriberId())
                                    .build()
                    ))
                    .build();

            PublishResponse res = snsClient.publish(request);
            log.info("Published SNS message: {}", res.messageId());
        } catch (Exception e) {
            log.error("Failed to publish SNS message for uuid: {}", message.getUuid(), e);
        }
    }

}

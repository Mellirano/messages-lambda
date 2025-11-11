package ua.udunt.hmnd.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @NonNull
    private String uuid;

    @NonNull
    private String body;

    @NonNull
    private String sender;

    @NonNull
    private String topicArn;

    @NonNull
    private String subscriberId;

    @NonNull
    private String ttl;

    public boolean isValid() {
        return isNotBlank(uuid)
                && isNotBlank(body)
                && isNotBlank(sender)
                && isNotBlank(topicArn)
                && isNotBlank(subscriberId);
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

}

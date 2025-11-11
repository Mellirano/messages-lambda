package ua.udunt.hmnd.support;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TtlFormatter {

    private static final DateTimeFormatter TTL_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    public static String formatTtl(String ttlEpochSeconds) {
        if (ttlEpochSeconds == null || ttlEpochSeconds.isBlank()) {
            return "unknown";
        }
        try {
            long epochSeconds = Long.parseLong(ttlEpochSeconds);
            Instant instant = Instant.ofEpochSecond(epochSeconds);
            return TTL_FORMATTER.format(instant);
        } catch (NumberFormatException e) {
            log.warn("Invalid TTL value: {}", ttlEpochSeconds, e);
            return ttlEpochSeconds;
        }
    }

}

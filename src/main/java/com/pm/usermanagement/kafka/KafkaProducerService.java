package com.pm.usermanagement.kafka;

import com.pm.usermanagement.dto.responseDTO;
import com.pm.usermanagement.event.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.Instant;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate; // this is used for bad
    private static final String TOPIC = "user-events";

    public KafkaProducerService(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(String eventType, responseDTO user) {
        String eventId = UUID.randomUUID().toString();

        UserEvent event = new UserEvent(
                eventId,
                eventType,
                user,
                Instant.now(),
                "trace-" + UUID.randomUUID() // placeholder
        );

        // Async send with callback (Best Practice)
        kafkaTemplate.send(TOPIC, user.getId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Event published successfully: " + eventType
                                + " | Partition: " + result.getRecordMetadata().partition());
                    } else {
                        System.err.println("❌ Failed to publish event: " + ex.getMessage());
                        // TODO: Add retry logic or DLQ later
                    }
                });
    }
}
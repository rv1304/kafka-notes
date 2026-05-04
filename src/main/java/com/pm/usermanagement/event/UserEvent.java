package com.pm.usermanagement.event;


import com.pm.usermanagement.dto.responseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor

// this is a pojo class ( plain old java object)
// it only have private field , default constructor and getter and getter setter


// Why we use @AllArgsConstructor
//Without the All-Args constructor, if you wanted to create a new UserEvent to send to Kafka, you would have to write code like this:
//UserEvent event = new UserEvent(); // Using the No-Args constructor
        //event.setEventId("123");
        //event.setEventType("CREATE");
        //event.setUser(someDto);
        //event.setTimestamp(Instant.now());
        //event.setTraceId("abc-xyz");
        //With @AllArgsConstructor, you can do it in one single line:

//UserEvent event = new UserEvent("123", "CREATE", someDto, Instant.now(), "abc-xyz");
public class UserEvent {

    private String eventId;
    private String eventType;
    private responseDTO user;
    private Instant timestamp;
    private String traceId;
}
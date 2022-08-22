package com.sekarre.helpcentercore.DTO.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.helpcentercore.domain.enums.EventType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.sekarre.helpcentercore.util.DateUtil.DATE_TIME_FORMAT;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO implements Serializable {

    private Long id;
    private Long userId;
    private String message;
    private String destinationId;
    private EventType eventType;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}

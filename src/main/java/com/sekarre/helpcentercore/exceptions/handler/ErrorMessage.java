package com.sekarre.helpcentercore.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static com.sekarre.helpcentercore.util.DateUtil.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {

    private String cause;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
}

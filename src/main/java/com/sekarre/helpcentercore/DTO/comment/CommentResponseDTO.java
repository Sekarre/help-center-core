package com.sekarre.helpcentercore.DTO.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import lombok.*;

import java.time.LocalDateTime;

import static com.sekarre.helpcentercore.util.DateUtil.DATE_TIME_FORMAT;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class CommentResponseDTO {

    private Long id;
    private String fullName;
    private String content;
    private CommentResponseDTO replyComment;
    private IssueStatus issueStatus;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}

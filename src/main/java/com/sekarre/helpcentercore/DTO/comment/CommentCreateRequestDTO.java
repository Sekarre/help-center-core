package com.sekarre.helpcentercore.DTO.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class CommentCreateRequestDTO {

    @NotBlank
    private String content;
    private Long replyCommentId;
}

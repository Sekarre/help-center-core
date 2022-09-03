package com.sekarre.helpcentercore.DTO.issue;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class IssueStatusChangeDTO {

    @NotBlank
    private String status;
    private CommentCreateRequestDTO comment;
}

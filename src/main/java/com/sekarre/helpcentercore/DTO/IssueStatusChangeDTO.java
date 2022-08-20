package com.sekarre.helpcentercore.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueStatusChangeDTO {

    @NotBlank
    private String status;
    private CommentCreateRequestDTO comment;
}

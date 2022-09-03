package com.sekarre.helpcentercore.factories;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.domain.Comment;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;

import static com.sekarre.helpcentercore.factories.IssueMockFactory.getIssueMock;
import static com.sekarre.helpcentercore.util.DateUtil.getCurrentDateTime;

public class CommentMockFactory {

    public static CommentResponseDTO getCommentResponseDTOMock() {
        return CommentResponseDTO.builder()
                .id(1L)
                .fullName("Full name")
                .content("Content")
                .replyComment(null)
                .issueStatus(IssueStatus.PENDING)
                .createdAt(getCurrentDateTime())
                .build();
    }

    public static Comment getCommentMock() {
        return Comment.builder()
                .id(1L)
                .fullName("Full name")
                .content("Content")
                .userId(1L)
                .replyComment(Comment.builder().build())
                .issueStatus(IssueStatus.PENDING)
                .issue(getIssueMock())
                .createdAt(getCurrentDateTime())
                .build();
    }

    public static CommentCreateRequestDTO getCommentCreateRequestDTOMock() {
        return CommentCreateRequestDTO.builder()
                .content("Content")
                .replyCommentId(1L)
                .build();
    }
}

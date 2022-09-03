package com.sekarre.helpcentercore.services.comment;


import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.domain.Issue;

import java.util.List;

public interface CommentService {

    List<CommentResponseDTO> getAllIssueComments(Long issueId);

    void createNewComment(CommentCreateRequestDTO commentCreateRequestDTO, Long issueId);

    void createNewCommentWithStatusChanged(IssueStatusChangeDTO issueStatusChangeDTO, Issue issue);
}

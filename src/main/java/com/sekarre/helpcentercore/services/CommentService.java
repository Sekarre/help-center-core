package com.sekarre.helpcentercore.services;


import com.sekarre.helpcentercore.DTO.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.CommentResponseDTO;
import com.sekarre.helpcentercore.DTO.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.domain.Issue;

import java.util.List;

public interface CommentService {

    List<CommentResponseDTO> getAllIssueComments(Long issueId);

    void createNewComment(CommentCreateRequestDTO commentCreateRequestDTO, Long issueId);

    void createNewCommentWithStatusChanged(IssueStatusChangeDTO issueStatusChangeDTO, Issue issue);
}

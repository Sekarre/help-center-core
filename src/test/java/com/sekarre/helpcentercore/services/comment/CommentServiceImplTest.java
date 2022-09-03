package com.sekarre.helpcentercore.services.comment;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import com.sekarre.helpcentercore.SecurityContextMockSetup;
import com.sekarre.helpcentercore.domain.Comment;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.mappers.CommentMapper;
import com.sekarre.helpcentercore.repositories.CommentRepository;
import com.sekarre.helpcentercore.repositories.IssueRepository;
import com.sekarre.helpcentercore.services.notification.NotificationSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sekarre.helpcentercore.factories.CommentMockFactory.*;
import static com.sekarre.helpcentercore.factories.IssueMockFactory.getIssueMock;
import static com.sekarre.helpcentercore.factories.IssueMockFactory.getIssueStatusChangeDTOMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceImplTest extends SecurityContextMockSetup {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private NotificationSender notificationSender;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, commentMapper, issueRepository, notificationSender);
    }

    @Test
    void should_return_all_issue_comments() {
        //given
        final Long issueId = 1L;
        final Comment comment = getCommentMock();
        final CommentResponseDTO commentResponseDTO = getCommentResponseDTOMock();
        when(commentRepository.findAllByIssueId(any(Long.class))).thenReturn(Collections.singletonList(comment));
        when(commentMapper.mapCommentToCommentDTO(any(Comment.class))).thenReturn(commentResponseDTO);

        //when
        List<CommentResponseDTO> result = commentService.getAllIssueComments(issueId);

        //then
        assertNotNull(result);
        assertEquals(result.get(0), commentResponseDTO, "CommentResponseDTO is not equal to result CommentResponseDTO");
        verify(commentRepository, times(1)).findAllByIssueId(issueId);
        verify(commentMapper, times(1)).mapCommentToCommentDTO(comment);
    }

    @Test
    void should_create_new_comment_WITH_reply_comment() {
        //given
        final Long issueId = 1L;
        final Long replyCommentId = 1L;
        final Comment comment = getCommentMock();
        final Issue issue = getIssueMock();
        final CommentCreateRequestDTO commentCreateRequestDTO = getCommentCreateRequestDTOMock();
        commentCreateRequestDTO.setReplyCommentId(replyCommentId);
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(comment));
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));
        when(commentMapper.mapCommentCreateRequestDTOToComment(any(CommentCreateRequestDTO.class))).thenReturn(comment);
        when(commentRepository.findAllByIssueId(any(Long.class))).thenReturn(Collections.singletonList(comment));

        //when
       commentService.createNewComment(commentCreateRequestDTO, issueId);

        //then
        verify(issueRepository, times(1)).findById(issueId);
        verify(commentRepository, times(1)).findById(commentCreateRequestDTO.getReplyCommentId());
        verify(commentMapper, times(1)).mapCommentCreateRequestDTOToComment(commentCreateRequestDTO);
        verify(commentRepository, times(1)).save(comment);
        verify(issueRepository, times(1)).save(issue);
        verify(notificationSender, times(1)).sendNotification(any(NotificationQueueDTO.class));
    }

    @Test
    void should_create_new_comment_WITHOUT_reply_comment() {
        //given
        final Long issueId = 1L;
        final Comment comment = getCommentMock();
        final Issue issue = getIssueMock();
        final CommentCreateRequestDTO commentCreateRequestDTO = getCommentCreateRequestDTOMock();
        commentCreateRequestDTO.setReplyCommentId(null);
        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(comment));
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));
        when(commentMapper.mapCommentCreateRequestDTOToComment(any(CommentCreateRequestDTO.class))).thenReturn(comment);
        when(commentRepository.findAllByIssueId(any(Long.class))).thenReturn(Collections.singletonList(comment));

        //when
        commentService.createNewComment(commentCreateRequestDTO, issueId);

        //then
        verify(issueRepository, times(1)).findById(issueId);
        verify(commentRepository, times(0)).findById(any(Long.class));
        verify(commentMapper, times(1)).mapCommentCreateRequestDTOToComment(commentCreateRequestDTO);
        verify(commentRepository, times(1)).save(comment);
        verify(issueRepository, times(1)).save(issue);
        verify(notificationSender, times(1)).sendNotification(any(NotificationQueueDTO.class));
    }

    @Test
    void should_create_new_comment_with_status_changed_WITHOUT_comment() {
        //given
        final Issue issue = getIssueMock();
        final IssueStatusChangeDTO issueStatusChangeDTO = getIssueStatusChangeDTOMock();
        final CommentCreateRequestDTO commentCreateRequestDTO = getCommentCreateRequestDTOMock();
        issueStatusChangeDTO.setStatus("");
        final Comment comment = getCommentMock();
        when(commentMapper.mapCommentCreateRequestDTOToComment(any(CommentCreateRequestDTO.class))).thenReturn(comment);

        //when
        commentService.createNewCommentWithStatusChanged(issueStatusChangeDTO, issue);

        //then
        verify(commentRepository, times(1)).save(comment);
        verify(commentMapper, times(1)).mapCommentCreateRequestDTOToComment(commentCreateRequestDTO);
        verify(notificationSender, times(1)).sendNotification(any(NotificationQueueDTO.class));
    }

    @Test
    void should_create_new_comment_with_status_changed_WITH_comment() {
        //given
        final Issue issue = getIssueMock();
        final IssueStatusChangeDTO issueStatusChangeDTO = getIssueStatusChangeDTOMock();
        final CommentCreateRequestDTO commentCreateRequestDTO = getCommentCreateRequestDTOMock();
        final Comment comment = getCommentMock();
        when(commentMapper.mapCommentCreateRequestDTOToComment(any(CommentCreateRequestDTO.class))).thenReturn(comment);

        //when
        commentService.createNewCommentWithStatusChanged(issueStatusChangeDTO, issue);

        //then
        verify(commentRepository, times(2)).save(any(Comment.class));
        verify(commentMapper, times(2)).mapCommentCreateRequestDTOToComment(commentCreateRequestDTO);
        verify(notificationSender, times(2)).sendNotification(any(NotificationQueueDTO.class));
    }
}
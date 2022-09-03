package com.sekarre.helpcentercore.controllers;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.services.comment.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.sekarre.helpcentercore.factories.CommentMockFactory.getCommentCreateRequestDTOMock;
import static com.sekarre.helpcentercore.factories.CommentMockFactory.getCommentResponseDTOMock;
import static com.sekarre.helpcentercore.util.DateUtil.getDateTimeFormatted;
import static com.sekarre.helpcentercore.testutil.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest {

    @Mock
    private CommentService commentService;
    private MockMvc mockMvc;

    private CommentController commentController;
    private static final String BASE_URL = "/api/v1/comments/";
    private static final Long issueId = 1L;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentService);

        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void should_return_all_issue_comments_with_OK_status() throws Exception {
        //given
        final CommentResponseDTO commentResponseDTO = getCommentResponseDTOMock();
        when(commentService.getAllIssueComments(any(Long.class))).thenReturn(Collections.singletonList(commentResponseDTO));

        //when + then
        mockMvc.perform(get(BASE_URL + issueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(commentResponseDTO.getId()))
                .andExpect(jsonPath("$[0].fullName").value(commentResponseDTO.getFullName()))
                .andExpect(jsonPath("$[0].content").value(commentResponseDTO.getContent()))
                .andExpect(jsonPath("$[0].replyComment").value(commentResponseDTO.getReplyComment()))
                .andExpect(jsonPath("$[0].issueStatus").value(commentResponseDTO.getIssueStatus().name()))
                .andExpect(jsonPath("$[0].createdAt").value(getDateTimeFormatted(commentResponseDTO.getCreatedAt())));

        verify(commentService, times(1)).getAllIssueComments(issueId);
    }

    @Test
    void should_create_new_comment_and_return_OK_status() throws Exception {
        //given
        final CommentCreateRequestDTO commentCreateRequestDTO = getCommentCreateRequestDTOMock();
        byte[] content = convertObjectToJsonBytes(commentCreateRequestDTO);

        //when + then
        mockMvc.perform(post(BASE_URL + issueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        verify(commentService, times(1)).createNewComment(commentCreateRequestDTO, issueId);
    }
}
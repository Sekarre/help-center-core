package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.domain.Comment;
import com.sekarre.helpcentercore.testutil.JUnitMessageGenerator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.sekarre.helpcentercore.factories.CommentMockFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);
    private JUnitMessageGenerator<?, ?> messageGenerator;

    @Test
    public void should_map_Comment_to_CommentResponseDTO() {
        messageGenerator = new JUnitMessageGenerator<>(Comment.class, CommentResponseDTO.class);

        //given
        final Comment from = getCommentMock();

        //when
        CommentResponseDTO result = commentMapper.mapCommentToCommentDTO(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getFullName(), result.getFullName(),
                messageGenerator.getMessage("fullName", from.getFullName(), "fullName", result.getFullName()));
        assertEquals(from.getContent(), result.getContent(),
                messageGenerator.getMessage("content", from.getContent(), "content", result.getContent()));
        assertEquals(from.getIssueStatus(), result.getIssueStatus(),
                messageGenerator.getMessage("issueStatus", from.getIssueStatus(), "issueStatus", result.getIssueStatus()));
        assertEquals(from.getCreatedAt(), result.getCreatedAt(),
                messageGenerator.getMessage("createdAt", from.getCreatedAt(), "createdAt", result.getCreatedAt()));
    }

    @Test
    public void should_map_CommentResponseDTO_to_Comment() {
        messageGenerator = new JUnitMessageGenerator<>(CommentResponseDTO.class, Comment.class);

        //given
        final CommentResponseDTO from = getCommentResponseDTOMock();

        //when
        Comment result = commentMapper.mapCommentResponseDTOToComment(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getFullName(), result.getFullName(),
                messageGenerator.getMessage("fullName", from.getFullName(), "fullName", result.getFullName()));
        assertEquals(from.getContent(), result.getContent(),
                messageGenerator.getMessage("content", from.getContent(), "content", result.getContent()));
        assertEquals(from.getIssueStatus(), result.getIssueStatus(),
                messageGenerator.getMessage("issueStatus", from.getIssueStatus(), "issueStatus", result.getIssueStatus()));
        assertEquals(from.getCreatedAt(), result.getCreatedAt(),
                messageGenerator.getMessage("createdAt", from.getCreatedAt(), "createdAt", result.getCreatedAt()));
    }

    @Test
    public void should_map_CommentCreateRequestDTO_to_Comment() {
        messageGenerator = new JUnitMessageGenerator<>(CommentCreateRequestDTO.class, Comment.class);

        //given
        final CommentCreateRequestDTO from = getCommentCreateRequestDTOMock();

        //when
        Comment result = commentMapper.mapCommentCreateRequestDTOToComment(from);

        //then
        assertEquals(from.getContent(), result.getContent(),
                messageGenerator.getMessage("content", from.getContent(), "content", result.getContent()));
    }
}
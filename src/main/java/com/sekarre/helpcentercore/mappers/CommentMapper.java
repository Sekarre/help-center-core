package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.domain.Comment;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class CommentMapper {

    public abstract CommentResponseDTO mapCommentToCommentDTO(Comment comment);
    public abstract Comment mapCommentResponseDTOToComment(CommentResponseDTO commentResponseDTO);
    public abstract Comment mapCommentCreateRequestDTOToComment(CommentCreateRequestDTO commentCreateRequestDTO);
}

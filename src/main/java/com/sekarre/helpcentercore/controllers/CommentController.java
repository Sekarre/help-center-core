package com.sekarre.helpcentercore.controllers;

import com.sekarre.helpcentercore.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.comment.CommentResponseDTO;
import com.sekarre.helpcentercore.security.perms.CommentPermission;
import com.sekarre.helpcentercore.services.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sekarre.helpcentercore.controllers.CommentController.BASE_COMMENT_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = BASE_COMMENT_URL)
public class CommentController {

    public static final String BASE_COMMENT_URL = "/api/v1/comments";
    private final CommentService commentService;

    @CommentPermission
    @GetMapping("/{issueId}")
    public ResponseEntity<List<CommentResponseDTO>> getAllIssueComments(@PathVariable Long issueId) {
        return ResponseEntity.ok(commentService.getAllIssueComments(issueId));
    }

    @CommentPermission
    @PostMapping("/{issueId}")
    public ResponseEntity<?> createNewComment(@PathVariable Long issueId,
                                              @RequestBody @Valid CommentCreateRequestDTO commentCreateRequestDTO) {
        commentService.createNewComment(commentCreateRequestDTO, issueId);
        return ResponseEntity.ok().build();
    }
}

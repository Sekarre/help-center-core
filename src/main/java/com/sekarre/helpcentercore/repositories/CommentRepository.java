package com.sekarre.helpcentercore.repositories;


import com.sekarre.helpcentercore.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByIssueId(Long issueId);
}

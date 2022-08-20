package com.sekarre.helpcentercore.repositories;

import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("select i from Issue i left join fetch i.participants left join i.author where i.id = ?1")
    Optional<Issue> findByIdWithParticipantsAndAuthor(Long issuesId);

    Optional<Issue> findByAuthorId(Long userId);

    List<Issue> findAllByAuthorId(Long userId);

    List<Issue> findAllByIssueStatus(IssueStatus issueStatus);

    List<Issue> findAllByParticipantsContaining(User user);

    List<Issue> findAllByIssueStatusAndParticipantsContaining(IssueStatus issueStatus, User user);
}

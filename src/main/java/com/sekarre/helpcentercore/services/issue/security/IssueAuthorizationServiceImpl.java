package com.sekarre.helpcentercore.services.issue.security;

import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.exceptions.issue.IssueAuthorizationException;
import com.sekarre.helpcentercore.exceptions.issue.IssueNotFoundException;
import com.sekarre.helpcentercore.repositories.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.getCurrentUser;


@RequiredArgsConstructor
@Slf4j
@Service
public class IssueAuthorizationServiceImpl implements IssueAuthorizationService {

    private final IssueRepository issueRepository;

    @Override
    public boolean checkIfUserAuthorizedToIssue(Long issueId) {
        Issue issue = getIssueById(issueId);
        if (!issue.getParticipants().contains(getCurrentUser()) && !issue.getAuthor().equals(getCurrentUser())) {
            throw new IssueAuthorizationException("User is not authorized to issue with id: " + issueId);
        }
        return true;
    }

    @Override
    public boolean checkIfUserAuthorizedToIssueComment(Long issueId) {
        Issue issue = getIssueById(issueId);
        if (!issue.getParticipants().contains(getCurrentUser()) && !issue.getAuthor().equals(getCurrentUser())) {
            throw new IssueAuthorizationException("User is not authorized to comment in issue with id: " + issueId);
        }
        return true;
    }

    private Issue getIssueById(Long issueId) {
        return issueRepository.findByIdWithParticipantsAndAuthor(issueId)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }
}

package com.sekarre.helpcentercore.services.issue.security;

import com.sekarre.helpcentercore.SecurityContextMockSetup;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.exceptions.issue.IssueAuthorizationException;
import com.sekarre.helpcentercore.repositories.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static com.sekarre.helpcentercore.factories.IssueMockFactory.getIssueMock;
import static com.sekarre.helpcentercore.factories.UserMockFactory.getCurrentUserMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IssueAuthorizationServiceTest extends SecurityContextMockSetup {

    @Mock
    private IssueRepository issueRepository;

    private IssueAuthorizationService issueAuthorizationService;
    private static final Long issueId = 1L;

    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        issueAuthorizationService = new IssueAuthorizationServiceImpl(issueRepository);
    }

    @Test
    void should_check_if_user_is_authorized_to_issue_and_return_true() {
        //given
        final Issue issue = getIssueMock();
        issue.addParticipant(getCurrentUserMock());
        when(issueRepository.findByIdWithParticipantsAndAuthor(any(Long.class))).thenReturn(Optional.of(issue));

        //when
        boolean result = issueAuthorizationService.checkIfUserAuthorizedToIssue(issueId);

        //then
        assertTrue(result);
        verify(issueRepository, times(1)).findByIdWithParticipantsAndAuthor(issueId);
    }

    @Test
    void should_check_if_user_is_authorized_to_issue_and_throw_IssueAuthorizationException() {
        //given
        final Issue issue = getIssueMock();
        issue.setParticipants(new HashSet<>());
        when(issueRepository.findByIdWithParticipantsAndAuthor(any(Long.class))).thenReturn(Optional.of(issue));

        //when
        assertThrows(IssueAuthorizationException.class, () -> issueAuthorizationService.checkIfUserAuthorizedToIssue(issueId));

        //then
        verify(issueRepository, times(1)).findByIdWithParticipantsAndAuthor(issueId);
    }

    @Test
    void should_check_if_user_is_authorized_to_issue_comment_and_return_true() {
        //given
        final Issue issue = getIssueMock();
        issue.addParticipant(getCurrentUserMock());
        when(issueRepository.findByIdWithParticipantsAndAuthor(any(Long.class))).thenReturn(Optional.of(issue));

        //when
        boolean result = issueAuthorizationService.checkIfUserAuthorizedToIssue(issueId);

        //then
        assertTrue(result);
        verify(issueRepository, times(1)).findByIdWithParticipantsAndAuthor(issueId);
    }

    @Test
    void should_check_if_user_is_authorized_to_issue_comment_and_throw_IssueAuthorizationException() {
        //given
        final Issue issue = getIssueMock();
        issue.setParticipants(new HashSet<>());
        when(issueRepository.findByIdWithParticipantsAndAuthor(any(Long.class))).thenReturn(Optional.of(issue));

        //when
        assertThrows(IssueAuthorizationException.class, () -> issueAuthorizationService.checkIfUserAuthorizedToIssue(issueId));

        //then
        verify(issueRepository, times(1)).findByIdWithParticipantsAndAuthor(issueId);
    }
}
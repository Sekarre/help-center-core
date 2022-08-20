package com.sekarre.helpcentercore.services;


import com.sekarre.helpcentercore.DTO.*;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;

import java.util.List;

public interface IssueService {

    List<IssueTypeDTO> getAllIssueTypes();

    List<String> getIssueStatuses();

    IssueDTO getUserIssue();

    List<IssueDTO> getAllUserIssues();

    void createNewIssue(IssueDTO issueDTO);

    void changeIssueStatus(Long issueId, IssueStatusChangeDTO issueStatusChangeDTO);

    void addUsersToIssue(Long issueId, Long[] usersId);

    List<UserDTO> getIssueParticipants(Long issueId);

    List<IssueDTO> getAllIssuesWithStatus(IssueStatus status);

    GroupedByStatusIssueDTO getAllIssuesGrouped();

    IssueDTO getIssueById(Long issueId);

    Issue getIssueEntityById(Long issueId);
}

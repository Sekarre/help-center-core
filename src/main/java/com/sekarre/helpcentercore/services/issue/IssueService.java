package com.sekarre.helpcentercore.services.issue;


import com.sekarre.helpcentercore.DTO.*;
import com.sekarre.helpcentercore.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;

import java.util.List;

public interface IssueService {

    List<IssueTypeDTO> getAllIssueTypes();

    List<String> getIssueStatuses();

    void createNewIssue(IssueDTO issueDTO);

    void changeIssueStatus(Long issueId, IssueStatusChangeDTO issueStatusChangeDTO);

    void addUsersToIssue(Long issueId, Long[] usersId);

    List<UserDTO> getIssueParticipants(Long issueId);

    List<IssueDTO> getAllIssuesWithStatus(IssueStatus status);

    GroupedByStatusIssueDTO getAllIssuesGrouped();

    IssueDTO getIssueById(Long issueId);

    Issue getIssueEntityById(Long issueId);

    void deleteIssue(Long issueId);
}

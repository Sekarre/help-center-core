package com.sekarre.helpcentercore.services.impl;

import com.sekarre.helpcentercore.DTO.*;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.IssueType;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import com.sekarre.helpcentercore.domain.enums.RoleName;
import com.sekarre.helpcentercore.exceptions.issue.IssueNotFoundException;
import com.sekarre.helpcentercore.mappers.IssueMapper;
import com.sekarre.helpcentercore.repositories.IssueRepository;
import com.sekarre.helpcentercore.repositories.IssueTypeRepository;
import com.sekarre.helpcentercore.services.ChatService;
import com.sekarre.helpcentercore.services.CommentService;
import com.sekarre.helpcentercore.services.IssueService;
import com.sekarre.helpcentercore.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.checkForRole;
import static com.sekarre.helpcentercore.security.UserDetailsHelper.getCurrentUser;


@Slf4j
@RequiredArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssueMapper issueMapper;
    private final CommentService commentService;
    private final UserService userService;
    private final ChatService chatService;
//    private final EventEmitterService eventEmitterService;
//    private final EventNotificationService eventNotificationService;

    @Override
    public List<IssueTypeDTO> getAllIssueTypes() {
        return issueTypeRepository.findAll().stream()
                .map(issueMapper::mapIssueTypeToIssueTypeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getIssueStatuses() {
        return Arrays.stream(IssueStatus.values())
                .map(Objects::toString)
                .collect(Collectors.toList());
    }

    @Override
    public IssueDTO getUserIssue() {
        return issueRepository.findByAuthorId(getCurrentUser().getId())
                .map(issueMapper::mapIssueToIssueDTO)
                .orElse(null);
    }

    @Override
    public List<IssueDTO> getAllUserIssues() {
        return issueRepository.findAllByAuthorId(getCurrentUser().getId()).stream()
                .map(issueMapper::mapIssueToIssueDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewIssue(IssueDTO issueDTO) {
        User supportUser = userService.getAvailableSupportUser();
        Issue issue = issueMapper.mapIssueDTOToIssue(issueDTO);
        issue.setAuthor(getCurrentUser());
        issue.addParticipant(getCurrentUser());
        issue.addParticipant(supportUser);
        issue.setIssueType(getIssueTypeById(issueDTO.getIssueTypeId()));
        issue.setIssueStatus(IssueStatus.PENDING);
        issue.setChat(chatService.getChat(issueDTO, supportUser));
        Issue savedIssue = issueRepository.save(issue);
//        eventEmitterService.sendNewEventMessage(
//                EventType.ASSIGNED_TO_ISSUE, savedIssue.getId().toString(), new Long[]{supportUser.getId()});
    }

    @Override
    public void changeIssueStatus(Long issueId, IssueStatusChangeDTO issueStatusChangeDTO) {
        IssueStatus newIssueStatus = IssueStatus.valueOf(issueStatusChangeDTO.getStatus());
        Issue issue = getIssueEntityById(issueId);
        if (issue.getIssueStatus().equals(newIssueStatus)) {
            return;
        }
        commentService.createNewCommentWithStatusChanged(issueStatusChangeDTO, issue);
        issue.setIssueStatus(newIssueStatus);
        issueRepository.save(issue);
    }

    @Override
    public void addUsersToIssue(Long issueId, Long[] usersId) {
        Issue issue = getIssueEntityById(issueId);
        for (Long userId : usersId) {
            issue.addParticipant(userService.getUserById(userId));
        }
        issueRepository.save(issue);
    }

    @Override
    public List<UserDTO> getIssueParticipants(Long issueId) {
        return userService.getParticipantsByIssue(getIssueEntityById(issueId));
    }

    @Override
    public List<IssueDTO> getAllIssuesWithStatus(IssueStatus status) {
        if (checkForRole(RoleName.ADMIN)) {
            return issueRepository.findAll().stream()
                    .map(issueMapper::mapIssueToIssueDTO)
                    .collect(Collectors.toList());
        }
        if (Objects.isNull(status)) {
            return issueRepository.findAllByParticipantsContaining(getCurrentUser()).stream()
                    .map(issueMapper::mapIssueToIssueDTO)
                    .collect(Collectors.toList());
        }
        return issueRepository.findAllByIssueStatusAndParticipantsContaining(status, getCurrentUser()).stream()
                .map(issueMapper::mapIssueToIssueDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupedByStatusIssueDTO getAllIssuesGrouped() {
        GroupedByStatusIssueDTO groupedByStatusIssueDTO = new GroupedByStatusIssueDTO();
        if (checkForRole(RoleName.ADMIN)) {
            issueRepository.findAll().stream()
                    .map(issueMapper::mapIssueToIssueDTO)
                    .forEach(groupedByStatusIssueDTO::addIssueDTO);
        } else {
            issueRepository.findAllByParticipantsContaining(getCurrentUser()).stream()
                    .map(issueMapper::mapIssueToIssueDTO)
                    .forEach(groupedByStatusIssueDTO::addIssueDTO);
        }
        return groupedByStatusIssueDTO;
    }

    @Override
    public IssueDTO getIssueById(Long issueId) {
        IssueDTO issueDTO = issueRepository.findById(issueId)
                .map(issueMapper::mapIssueToIssueDTO)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
//        eventNotificationService.markNotificationAsRead(
//                issueId.toString(), EventType.ASSIGNED_TO_ISSUE, EventType.NEW_ISSUE, EventType.NEW_ISSUE_COMMENT);
        return issueDTO;
    }

    @Override
    public Issue getIssueEntityById(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }

    private IssueType getIssueTypeById(Long issueTypeId) {
        return issueTypeRepository.findById(issueTypeId)
                .orElseThrow(() -> new IssueNotFoundException("IssueType with id: " + issueTypeId + " not found"));
    }
}

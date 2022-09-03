package com.sekarre.helpcentercore.services.issue;

import com.sekarre.helpcentercore.DTO.*;
import com.sekarre.helpcentercore.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.IssueType;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.domain.enums.EventType;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import com.sekarre.helpcentercore.domain.enums.RoleName;
import com.sekarre.helpcentercore.exceptions.issue.IssueNotFoundException;
import com.sekarre.helpcentercore.mappers.IssueMapper;
import com.sekarre.helpcentercore.repositories.IssueRepository;
import com.sekarre.helpcentercore.repositories.IssueTypeRepository;
import com.sekarre.helpcentercore.services.chat.ChatService;
import com.sekarre.helpcentercore.services.comment.CommentService;
import com.sekarre.helpcentercore.services.user.UserService;
import com.sekarre.helpcentercore.services.notification.NotificationSender;
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
    private final NotificationSender notificationSender;

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
        sendNotificationToSupportUser(supportUser, savedIssue);
    }

    private void sendNotificationToSupportUser(User supportUser, Issue savedIssue) {
        notificationSender.sendNotification(NotificationQueueDTO.builder()
                .eventType(EventType.ASSIGNED_TO_ISSUE.name())
                .destinationId(String.valueOf(savedIssue.getId()))
                .userId(supportUser.getId())
                .build());
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
        return issueRepository.findById(issueId)
                .map(issueMapper::mapIssueToIssueDTO)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }

    @Override
    public Issue getIssueEntityById(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }

    @Override
    public void deleteIssue(Long issueId) {
        issueRepository.deleteById(issueId);
    }

    private IssueType getIssueTypeById(Long issueTypeId) {
        return issueTypeRepository.findById(issueTypeId)
                .orElseThrow(() -> new IssueNotFoundException("IssueType with id: " + issueTypeId + " not found"));
    }
}

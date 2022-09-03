package com.sekarre.helpcentercore.factories;

import com.sekarre.helpcentercore.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.Comment;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.IssueType;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import com.sekarre.helpcentercore.domain.enums.IssueTypeName;

import java.util.Collections;
import java.util.HashSet;

import static com.sekarre.helpcentercore.factories.UserMockFactory.getDefaultUserMock;
import static com.sekarre.helpcentercore.util.DateUtil.getCurrentDateTime;

public class IssueMockFactory {

    public static IssueTypeDTO getIssueTypeDTOMock() {
        return IssueTypeDTO.builder()
                .id("Id")
                .name("Name")
                .build();
    }

    public static IssueType getIssueTypeMock() {
        return IssueType.builder()
                .id(1L)
                .name(IssueTypeName.GAME_ISSUE)
                .isAvailable(true)
                .build();
    }

    public static IssueDTO getIssueDTOMock() {
        return IssueDTO.builder()
                .id(1L)
                .title("Title")
                .firstName("First name")
                .lastName("Last name")
                .email("Email")
                .issue("Issue")
                .issueStatus(IssueStatus.PENDING)
                .issueTypeId(1L)
                .channelId("Channel id")
                .newNotificationsCount(2)
                .createdAt(getCurrentDateTime())
                .updatedAt(getCurrentDateTime())
                .build();
    }

    public static Issue getIssueMock() {
        return Issue.builder()
                .id(1L)
                .title("Title")
                .issue("Issue")
                .issueStatus(IssueStatus.PENDING)
                .createdAt(getCurrentDateTime())
                .updatedAt(getCurrentDateTime())
                .comments(Collections.singletonList(Comment.builder().build()))
                .chat(Chat.builder().build())
                .issueType(getIssueTypeMock())
                .author(getDefaultUserMock())
                .participants(new HashSet<>(){{
                    add(getDefaultUserMock());
                }})
                .build();
    }

    public static IssueStatusChangeDTO getIssueStatusChangeDTOMock() {
        return IssueStatusChangeDTO.builder()
                .status(IssueStatus.CLOSED.name())
                .comment(CommentMockFactory.getCommentCreateRequestDTOMock())
                .build();
    }

    public static GroupedByStatusIssueDTO getGroupedByStatusIssueDTOMock() {
        return GroupedByStatusIssueDTO.builder()
                .closedIssues(Collections.singletonList(getIssueDTOMock()))
                .escalatingIssues(Collections.singletonList(getIssueDTOMock()))
                .infoRequiredIssues(Collections.singletonList(getIssueDTOMock()))
                .pendingIssues(Collections.singletonList(getIssueDTOMock()))
                .build();
    }
}

package com.sekarre.helpcentercore.services.issue;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.DTO.notification.NotificationQueueDTO;
import com.sekarre.helpcentercore.SecurityContextMockSetup;
import com.sekarre.helpcentercore.domain.*;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import com.sekarre.helpcentercore.domain.enums.RoleName;
import com.sekarre.helpcentercore.mappers.IssueMapper;
import com.sekarre.helpcentercore.repositories.IssueRepository;
import com.sekarre.helpcentercore.repositories.IssueTypeRepository;
import com.sekarre.helpcentercore.services.chat.ChatService;
import com.sekarre.helpcentercore.services.comment.CommentService;
import com.sekarre.helpcentercore.services.notification.NotificationSender;
import com.sekarre.helpcentercore.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.sekarre.helpcentercore.factories.ChatMockFactory.getChatMock;
import static com.sekarre.helpcentercore.factories.IssueMockFactory.*;
import static com.sekarre.helpcentercore.factories.UserMockFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IssueServiceImplTest extends SecurityContextMockSetup {

    @Mock
    private IssueRepository issueRepository;
    @Mock
    private IssueTypeRepository issueTypeRepository;
    @Mock
    private IssueMapper issueMapper;
    @Mock
    private CommentService commentService;
    @Mock
    private UserService userService;
    @Mock
    private ChatService chatService;
    @Mock
    private NotificationSender notificationSender;

    private IssueService issueService;

    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        issueService = new IssueServiceImpl(issueRepository, issueTypeRepository, issueMapper, commentService, userService,
                chatService, notificationSender);
    }

    @Test
    void should_return_all_issue_types() {
        //given
        final IssueTypeDTO issueTypeDTO = getIssueTypeDTOMock();
        final IssueType issueType = getIssueTypeMock();
        when(issueTypeRepository.findAll()).thenReturn(Collections.singletonList(issueType));
        when(issueMapper.mapIssueTypeToIssueTypeDTO(any(IssueType.class))).thenReturn(issueTypeDTO);

        //when
        List<IssueTypeDTO> result = issueService.getAllIssueTypes();

        //then
        assertNotNull(result);
        assertEquals(issueTypeDTO, result.get(0), "IssueTypeDTO is not equal to result IssueType");
        verify(issueTypeRepository, times(1)).findAll();
        verify(issueMapper, times(1)).mapIssueTypeToIssueTypeDTO(issueType);
    }

    @Test
    void should_return_all_issue_statuses() {
        //given
        final List<String> issueStatuses = Arrays.stream(IssueStatus.values()).map(Objects::toString).toList();

        //when
        List<String> result = issueService.getIssueStatuses();

        //then
        assertNotNull(result);
        assertEquals(result, issueStatuses, "IssueStatus is not equal to result IssueStatus");
    }

    @Test
    void should_create_new_issue() {
        //given
        final User user = getDefaultUserMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        final Issue issue = getIssueMock();
        final IssueType issueType = getIssueTypeMock();
        final Chat chat = getChatMock();
        when(userService.getAvailableSupportUser()).thenReturn(user);
        when(issueMapper.mapIssueDTOToIssue(any(IssueDTO.class))).thenReturn(issue);
        when(issueTypeRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issueType));
        when(chatService.getChat(any(IssueDTO.class), any(User.class))).thenReturn(chat);
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        //when
        issueService.createNewIssue(issueDTO);

        //then
        verify(userService, times(1)).getAvailableSupportUser();
        verify(issueMapper, times(1)).mapIssueDTOToIssue(issueDTO);
        verify(issueTypeRepository, times(1)).findById(issueDTO.getIssueTypeId());
        verify(chatService, times(1)).getChat(issueDTO, user);
        verify(issueRepository, times(1)).save(issue);
        verify(notificationSender, times(1)).sendNotification(any(NotificationQueueDTO.class));
    }

    @Test
    void should_change_issue_status() {
        //given
        final Long issueId = 1L;
        final IssueStatusChangeDTO issueStatusChangeDTO = getIssueStatusChangeDTOMock();
        final Issue issue = getIssueMock();
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));

        //when
        issueService.changeIssueStatus(issueId, issueStatusChangeDTO);

        //then
        verify(issueRepository, times(1)).findById(issueId);
        verify(commentService, times(1)).createNewCommentWithStatusChanged(issueStatusChangeDTO, issue);
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    void should_add_users_to_issue() {
        //given
        final Long issueId = 1L;
        final Long[] usersIds = {1L, 2L, 3L};
        final Issue issue = getIssueMock();
        final User user = getDefaultUserMock();
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));
        when(userService.getUserById(any(Long.class))).thenReturn(user);

        //when
        issueService.addUsersToIssue(issueId, usersIds);

        //then
        verify(issueRepository, times(1)).findById(issueId);
        verify(userService, times(3)).getUserById(any(Long.class));
        verify(userService, times(1)).getUserById(usersIds[0]);
        verify(userService, times(1)).getUserById(usersIds[1]);
        verify(userService, times(1)).getUserById(usersIds[2]);
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    void should_return_issue_all_participants() {
        //given
        final Long issueId = 1L;
        final Issue issue = getIssueMock();
        final UserDTO userDTO = getUserDTOMock();
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));
        when(userService.getParticipantsByIssue(any(Issue.class))).thenReturn(Collections.singletonList(userDTO));

        //when
        List<UserDTO> result = issueService.getIssueParticipants(issueId);

        //then
        assertNotNull(result);
        assertEquals(userDTO, result.get(0), "UserDTO is not equal to result UserDTO");
        verify(userService, times(1)).getParticipantsByIssue(issue);
        verify(issueRepository, times(1)).findById(issueId);
    }

    @Test
    void should_return_all_issues_for_admin() {
        //given
        final Issue issue = getIssueMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        setUpSecurityContext(getUserWithRolesMock(Collections.singleton(Role.builder().name(RoleName.ADMIN).build())));
        when(issueRepository.findAll()).thenReturn(Collections.singletonList(issue));
        when(issueMapper.mapIssueToIssueDTO(any(Issue.class))).thenReturn(issueDTO);

        //when
        List<IssueDTO> result = issueService.getAllIssuesWithStatus(null);

        //then
        assertNotNull(result);
        assertEquals(issueDTO, result.get(0), "IssueDTO is not equal to result IssueDTO");
        verify(issueRepository, times(1)).findAll();
        verify(issueMapper, times(1)).mapIssueToIssueDTO(issue);
    }

    @Test
    void should_return_all_issues_WITH_given_status() {
        //given
        final User user = getUserWithRolesMock(Collections.singleton(Role.builder().name(RoleName.BASIC).build()));
        final IssueStatus issueStatus = IssueStatus.PENDING;
        final Issue issue = getIssueMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        setUpSecurityContext(user);
        when(issueRepository.findAllByIssueStatusAndParticipantsContaining(any(IssueStatus.class), any(User.class))).thenReturn(Collections.singletonList(issue));
        when(issueMapper.mapIssueToIssueDTO(any(Issue.class))).thenReturn(issueDTO);

        //when
        List<IssueDTO> result = issueService.getAllIssuesWithStatus(issueStatus);

        //then
        assertNotNull(result);
        assertEquals(issueDTO, result.get(0), "IssueDTO is not equal to result IssueDTO");
        verify(issueRepository, times(1)).findAllByIssueStatusAndParticipantsContaining(issueStatus, user);
        verify(issueMapper, times(1)).mapIssueToIssueDTO(issue);
    }

    @Test
    void should_return_all_issues_WITHOUT_given_status() {
        //given
        final User user = getUserWithRolesMock(Collections.singleton(Role.builder().name(RoleName.BASIC).build()));
        final Issue issue = getIssueMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        setUpSecurityContext(user);
        when(issueRepository.findAllByParticipantsContaining(any(User.class))).thenReturn(Collections.singletonList(issue));
        when(issueMapper.mapIssueToIssueDTO(any(Issue.class))).thenReturn(issueDTO);

        //when
        List<IssueDTO> result = issueService.getAllIssuesWithStatus(null);

        //then
        assertNotNull(result);
        assertEquals(issueDTO, result.get(0), "IssueDTO is not equal to result IssueDTO");
        verify(issueRepository, times(1)).findAllByParticipantsContaining(user);
        verify(issueMapper, times(1)).mapIssueToIssueDTO(issue);
    }


    @Test
    void should_return_all_issues_grouped_for_admin() {
        //given
        final User user = getUserWithRolesMock(Collections.singleton(Role.builder().name(RoleName.ADMIN).build()));
        final Issue issue = getIssueMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        setUpSecurityContext(user);
        when(issueRepository.findAll()).thenReturn(Collections.singletonList(issue));
        when(issueMapper.mapIssueToIssueDTO(any(Issue.class))).thenReturn(issueDTO);

        //when
        GroupedByStatusIssueDTO result = issueService.getAllIssuesGrouped();

        //then
        assertNotNull(result);
        verify(issueRepository, times(1)).findAll();
        verify(issueMapper, times(1)).mapIssueToIssueDTO(issue);
    }

    @Test
    void should_return_all_issues_grouped() {
        //given
        final User user = getUserWithRolesMock(Collections.singleton(Role.builder().name(RoleName.BASIC).build()));
        final Issue issue = getIssueMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        setUpSecurityContext(user);
        when(issueRepository.findAllByParticipantsContaining(any(User.class))).thenReturn(Collections.singletonList(issue));
        when(issueMapper.mapIssueToIssueDTO(any(Issue.class))).thenReturn(issueDTO);

        //when
        GroupedByStatusIssueDTO result = issueService.getAllIssuesGrouped();

        //then
        assertNotNull(result);
        verify(issueRepository, times(1)).findAllByParticipantsContaining(user);
        verify(issueMapper, times(1)).mapIssueToIssueDTO(issue);
    }

    @Test
    void should_return_issue_by_id() {
        //given
        final Long issueId = 1L;
        final Issue issue = getIssueMock();
        final IssueDTO issueDTO = getIssueDTOMock();
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));
        when(issueMapper.mapIssueToIssueDTO(any(Issue.class))).thenReturn(issueDTO);

        //when
        IssueDTO result = issueService.getIssueById(issueId);

        //then
        assertNotNull(result);
        assertEquals(issueDTO, result, "IssueDTO is not equal to result IssueDTO");
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueMapper, times(1)).mapIssueToIssueDTO(issue);
    }

    @Test
    void should_return_issue_entity_by_id() {
        //given
        final Long issueId = 1L;
        final Issue issue = getIssueMock();
        when(issueRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(issue));

        //when
        Issue result = issueService.getIssueEntityById(issueId);

        //then
        assertNotNull(result);
        assertEquals(issue, result, "Issue is not equal to result Issue");
        verify(issueRepository, times(1)).findById(issueId);
    }

    @Test
    void should_delete_issue() {
        //given
        final Long issueId = 1L;

        //when
        issueService.deleteIssue(issueId);

        //then
        verify(issueRepository, times(1)).deleteById(issueId);
    }
}
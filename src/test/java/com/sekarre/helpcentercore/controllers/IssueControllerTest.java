package com.sekarre.helpcentercore.controllers;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.services.issue.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.sekarre.helpcentercore.factories.IssueMockFactory.*;
import static com.sekarre.helpcentercore.factories.UserMockFactory.getUserDTOMock;
import static com.sekarre.helpcentercore.util.DateUtil.getDateTimeFormatted;
import static com.sekarre.helpcentercore.testutil.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IssueControllerTest {

    @Mock
    private IssueService issueService;
    private MockMvc mockMvc;

    private IssueController issueController;
    private static final String BASE_URL = "/api/v1/issues/";
    private static final Long issueId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        issueController = new IssueController(issueService);

        mockMvc = MockMvcBuilders.standaloneSetup(issueController).build();
    }

    @Test
    void should_return_issue_statuses_with_OK_status() throws Exception {
        //given
        final List<String> issueStatuses = List.of("status 1", "status 2");
        when(issueService.getIssueStatuses()).thenReturn(issueStatuses);

        //when + then
        mockMvc.perform(get(BASE_URL + "issue-statuses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(issueStatuses.get(0)))
                .andExpect(jsonPath("$[1]").value(issueStatuses.get(1)));

        verify(issueService, times(1)).getIssueStatuses();
    }

    @Test
    void should_return_issue_types_with_OK_status() throws Exception {
        //given
        final IssueTypeDTO issueTypeDTO = getIssueTypeDTOMock();
        when(issueService.getAllIssueTypes()).thenReturn(Collections.singletonList(issueTypeDTO));

        //when + then
        mockMvc.perform(get(BASE_URL + "types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(issueTypeDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(issueTypeDTO.getName()));

        verify(issueService, times(1)).getAllIssueTypes();
    }

    @Test
    void should_create_new_issue_and_return_CREATED_status() throws Exception {
        //given
        final IssueDTO issueDTO = getIssueDTOMock();
        byte[] content = convertObjectToJsonBytes(issueDTO);

        //when + then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());

        verify(issueService, times(1)).createNewIssue(any(IssueDTO.class));
    }

    @Test
    void should_change_issue_status_and_return_OK_status() throws Exception {
        //given
        final IssueStatusChangeDTO issueStatusChangeDTO = getIssueStatusChangeDTOMock();
        byte[] content = convertObjectToJsonBytes(issueStatusChangeDTO);

        //when + then
        mockMvc.perform(patch(BASE_URL + issueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        verify(issueService, times(1)).changeIssueStatus(issueId, issueStatusChangeDTO);
    }

    @Test
    void should_add_users_to_issue_and_return_OK_status() throws Exception {
        //given
        final Long[] usersIds = {1L, 2L, 3L};
        byte[] content = convertObjectToJsonBytes(usersIds);

        //when + then
        mockMvc.perform(put(BASE_URL + issueId + "/user-add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        verify(issueService, times(1)).addUsersToIssue(issueId, usersIds);
    }

    @Test
    void should_return_issue_participants_with_OK_status() throws Exception {
        //given
        final UserDTO userDTO = getUserDTOMock();
        when(issueService.getIssueParticipants(any(Long.class))).thenReturn(Collections.singletonList(userDTO));

        //when + then
        mockMvc.perform(get(BASE_URL + issueId + "/participants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDTO.getId()))
                .andExpect(jsonPath("$[0].fullName").value(userDTO.getFullName()))
                .andExpect(jsonPath("$[0].roleName").value(userDTO.getRoleName()))
                .andExpect(jsonPath("$[0].specialization").value(userDTO.getSpecialization()));

        verify(issueService, times(1)).getIssueParticipants(issueId);
    }

    @Test
    void should_return_all_issues_with_OK_status() throws Exception {
        //given
        final IssueDTO issueDTO = getIssueDTOMock();
        when(issueService.getAllIssuesWithStatus(any())).thenReturn(Collections.singletonList(issueDTO));

        //when + then
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(issueDTO.getId()))
                .andExpect(jsonPath("$[0].title").value(issueDTO.getTitle()))
                .andExpect(jsonPath("$[0].firstName").value(issueDTO.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(issueDTO.getLastName()))
                .andExpect(jsonPath("$[0].email").value(issueDTO.getEmail()))
                .andExpect(jsonPath("$[0].issue").value(issueDTO.getIssue()))
                .andExpect(jsonPath("$[0].issueStatus").value(issueDTO.getIssueStatus().name()))
                .andExpect(jsonPath("$[0].issueTypeId").value(issueDTO.getIssueTypeId()))
                .andExpect(jsonPath("$[0].channelId").value(issueDTO.getChannelId()))
                .andExpect(jsonPath("$[0].newNotificationsCount").value(issueDTO.getNewNotificationsCount()))
                .andExpect(jsonPath("$[0].createdAt").value(getDateTimeFormatted(issueDTO.getCreatedAt())))
                .andExpect(jsonPath("$[0].updatedAt").value(getDateTimeFormatted(issueDTO.getUpdatedAt())));

        verify(issueService, times(1)).getAllIssuesWithStatus(null);
    }

    @Test
    void should_return_all_issues_grouped_with_OK_status() throws Exception {
        //given
        final GroupedByStatusIssueDTO groupedByStatusIssueDTO = getGroupedByStatusIssueDTOMock();
        when(issueService.getAllIssuesGrouped()).thenReturn(groupedByStatusIssueDTO);

        //when + then
        mockMvc.perform(get(BASE_URL + "grouped"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingIssues").isNotEmpty())
                .andExpect(jsonPath("$.escalatingIssues").isNotEmpty())
                .andExpect(jsonPath("$.infoRequiredIssues").isNotEmpty())
                .andExpect(jsonPath("$.closedIssues").isNotEmpty());

        verify(issueService, times(1)).getAllIssuesGrouped();
    }

    @Test
    void should_return_issue_with_OK_status() throws Exception {
        //given
        final IssueDTO issueDTO = getIssueDTOMock();
        when(issueService.getIssueById(any(Long.class))).thenReturn(issueDTO);

        //when + then
        mockMvc.perform(get(BASE_URL + issueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(issueDTO.getId()))
                .andExpect(jsonPath("$.title").value(issueDTO.getTitle()))
                .andExpect(jsonPath("$.firstName").value(issueDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(issueDTO.getLastName()))
                .andExpect(jsonPath("$.email").value(issueDTO.getEmail()))
                .andExpect(jsonPath("$.issue").value(issueDTO.getIssue()))
                .andExpect(jsonPath("$.issueStatus").value(issueDTO.getIssueStatus().name()))
                .andExpect(jsonPath("$.issueTypeId").value(issueDTO.getIssueTypeId()))
                .andExpect(jsonPath("$.channelId").value(issueDTO.getChannelId()))
                .andExpect(jsonPath("$.newNotificationsCount").value(issueDTO.getNewNotificationsCount()))
                .andExpect(jsonPath("$.createdAt").value(getDateTimeFormatted(issueDTO.getCreatedAt())))
                .andExpect(jsonPath("$.updatedAt").value(getDateTimeFormatted(issueDTO.getUpdatedAt())));

        verify(issueService, times(1)).getIssueById(issueId);
    }

    @Test
    void should_return_delete_issue_and_return_ACCEPTED_status() throws Exception {
        //when + then
        mockMvc.perform(delete(BASE_URL + issueId))
                .andExpect(status().isAccepted());

        verify(issueService, times(1)).deleteIssue(issueId);
    }
}
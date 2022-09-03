package com.sekarre.helpcentercore.controllers;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.sekarre.helpcentercore.factories.UserMockFactory.getUserDTOMock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    private UserService userService;
    private MockMvc mockMvc;

    private UserController userController;
    private static final String BASE_URL = "/api/v1/users/";
    private static final String roleName = "ADMIN";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void should_return_users_with_OK_status() throws Exception {
        //given
        final UserDTO userDTO = getUserDTOMock();
        when(userService.getUsersByRoleName(any(String.class))).thenReturn(Collections.singletonList(userDTO));

        //when + then
        mockMvc.perform(get(BASE_URL)
                        .param("roleName", roleName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDTO.getId()))
                .andExpect(jsonPath("$[0].fullName").value(userDTO.getFullName()))
                .andExpect(jsonPath("$[0].roleName").value(userDTO.getRoleName()))
                .andExpect(jsonPath("$[0].specialization").value(userDTO.getSpecialization()));

        verify(userService, times(1)).getUsersByRoleName(roleName);
    }

    @Test
    void should_return_unique_users_for_issue_with_OK_status() throws Exception {
        //given
        final Long issueId = 1L;
        final UserDTO userDTO = getUserDTOMock();
        when(userService.getUsersByRoleNameAndNotInIssue(any(String.class), any(Long.class))).thenReturn(Collections.singletonList(userDTO));

        //when + then
        mockMvc.perform(get(BASE_URL + "issue")
                        .param("roleName", roleName)
                        .param("issueId", String.valueOf(issueId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDTO.getId()))
                .andExpect(jsonPath("$[0].fullName").value(userDTO.getFullName()))
                .andExpect(jsonPath("$[0].roleName").value(userDTO.getRoleName()))
                .andExpect(jsonPath("$[0].specialization").value(userDTO.getSpecialization()));

        verify(userService, times(1)).getUsersByRoleNameAndNotInIssue(roleName, issueId);
    }
}
package com.sekarre.helpcentercore.services;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.mappers.UserMapper;
import com.sekarre.helpcentercore.repositories.UserRepository;
import com.sekarre.helpcentercore.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sekarre.helpcentercore.factories.IssueMockFactory.getIssueMock;
import static com.sekarre.helpcentercore.factories.UserMockFactory.getDefaultUserMock;
import static com.sekarre.helpcentercore.factories.UserMockFactory.getUserDTOMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void should_return_users_by_role_name() {
        //given
        final String roleName = "Admin";
        final UserDTO userDTO = getUserDTOMock();
        final User user = getDefaultUserMock();
        when(userRepository.findAllByRoleName(any(String.class))).thenReturn(Collections.singleton(user));
        when(userMapper.mapUserToUserDTO(any(User.class))).thenReturn(userDTO);

        //when
        List<UserDTO> response = userService.getUsersByRoleName(roleName);

        //then
        assertNotNull(response);
        assertEquals(response.get(0), userDTO, "UserDTO is not equal to response UserDTO");
        verify(userRepository, times(1)).findAllByRoleName(roleName);
        verify(userMapper, times(1)).mapUserToUserDTO(user);
    }

    @Test
    void should_return_users_by_role_name_and_not_in_issue() {
        //given
        final String roleName = "Admin";
        final Long issueId = 1L;
        final UserDTO userDTO = getUserDTOMock();
        final User user = getDefaultUserMock();
        when(userRepository.findAllByRoleNameAndIssueIdNotEqual(any(String.class), any(Long.class))).thenReturn(Collections.singleton(user));
        when(userMapper.mapUserToUserDTO(any(User.class))).thenReturn(userDTO);

        //when
        List<UserDTO> response = userService.getUsersByRoleNameAndNotInIssue(roleName, issueId);

        //then
        assertNotNull(response);
        assertEquals(response.get(0), userDTO, "UserDTO is not equal to response UserDTO");
        verify(userRepository, times(1)).findAllByRoleNameAndIssueIdNotEqual(roleName, issueId);
        verify(userMapper, times(1)).mapUserToUserDTO(user);
    }


    @Test
    void should_return_user_by_id() {
        //given
        final Long userId = 1L;
        final User user = getDefaultUserMock();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));

        //when
        User response = userService.getUserById(userId);

        //then
        assertNotNull(response);
        assertEquals(response, user, "User is not equal to response User");
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    void should_return_participants_by_issue() {
        //given
        final UserDTO userDTO = getUserDTOMock();
        final Issue issue = getIssueMock();
        when(userMapper.mapUserToUserDTO(any(User.class))).thenReturn(userDTO);

        //when
        List<UserDTO> response = userService.getParticipantsByIssue(issue);

        //then
        assertNotNull(response);
        assertEquals(response.get(0), userDTO, "UserDTO is not equal to response UserDTO");
        verify(userMapper, times(1)).mapUserToUserDTO(issue.getParticipants().toArray(new User[]{})[0]);
    }


    @Test
    void should_return_available_support_user() {
        //given
        final User user = getDefaultUserMock();
        when(userRepository.findUsersWithLeastIssuesAndMatchingSpecialization()).thenReturn(Optional.ofNullable(user));

        //when
        User response = userService.getAvailableSupportUser();

        //then
        assertNotNull(response);
        assertEquals(response, user, "User is not equal to response User");
        verify(userRepository, times(1)).findUsersWithLeastIssuesAndMatchingSpecialization();
    }
}
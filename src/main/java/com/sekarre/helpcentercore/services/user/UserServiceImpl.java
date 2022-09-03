package com.sekarre.helpcentercore.services.user;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.exceptions.user.UserNotFoundException;
import com.sekarre.helpcentercore.mappers.UserMapper;
import com.sekarre.helpcentercore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getUsersByRoleName(String roleName) {
        return userRepository.findAllByRoleName(roleName).stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByRoleNameAndNotInIssue(String roleName, Long issueId) {
        return userRepository.findAllByRoleNameAndIssueIdNotEqual(roleName, issueId).stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
    }

    @Override
    public List<UserDTO> getParticipantsByIssue(Issue issue) {
        return issue.getParticipants().stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public User getAvailableSupportUser() {
        return userRepository.findUsersWithLeastIssuesAndMatchingSpecialization()
                .orElseThrow(() -> new IllegalStateException("Cannot find available users"));
    }
}

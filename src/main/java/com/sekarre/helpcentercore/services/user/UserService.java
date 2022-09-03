package com.sekarre.helpcentercore.services.user;


import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsersByRoleName(String roleName);

    List<UserDTO> getUsersByRoleNameAndNotInIssue(String roleName, Long issueId);

    User getUserById(Long userId);

    List<UserDTO> getParticipantsByIssue(Issue issue);

    User getAvailableSupportUser();
}

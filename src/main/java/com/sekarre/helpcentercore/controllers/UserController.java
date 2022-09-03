package com.sekarre.helpcentercore.controllers;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sekarre.helpcentercore.controllers.UserController.BASE_AUTH_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_AUTH_URL)
public class UserController {

    public static final String BASE_AUTH_URL = "/api/v1/users";

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam String roleName) {
        return ResponseEntity.ok(userService.getUsersByRoleName(roleName));
    }

    @GetMapping("/issue")
    public ResponseEntity<List<UserDTO>> getUniqueUsersForIssue(@RequestParam String roleName, @RequestParam Long issueId) {
        return ResponseEntity.ok(userService.getUsersByRoleNameAndNotInIssue(roleName, issueId));
    }
}

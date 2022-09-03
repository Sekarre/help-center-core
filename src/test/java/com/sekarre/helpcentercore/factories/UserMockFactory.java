package com.sekarre.helpcentercore.factories;


import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.domain.Role;
import com.sekarre.helpcentercore.domain.User;

import java.util.Set;

public class UserMockFactory {

    public static User getDefaultUserMock() {
        return User.builder()
                .id(1L)
                .username("Default User")
                .password("password")
                .build();
    }

    public static User getCurrentUserMock() {
        return User.builder()
                .id(2L)
                .username("Current User")
                .password("password")
                .build();
    }

    public static User getUserWithRolesMock(Set<Role> roles) {
        return User.builder()
                .id(3L)
                .roles(roles)
                .username("Current User")
                .password("password")
                .build();
    }

    public static UserDTO getUserDTOMock() {
        return UserDTO.builder()
                .id(1L)
                .fullName("Full name")
                .roleName("Basic")
                .specialization("IT")
                .build();
    }
}
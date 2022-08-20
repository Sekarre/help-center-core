package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.domain.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public abstract UserDTO mapUserToUserDTO(User user);
}

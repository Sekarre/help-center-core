package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.UserDTO;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.testutil.JUnitMessageGenerator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.sekarre.helpcentercore.factories.UserMockFactory.getDefaultUserMock;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private JUnitMessageGenerator<?, ?> messageGenerator;

    @Test
    public void should_map_Comment_to_CommentResponseDTO() {
        messageGenerator = new JUnitMessageGenerator<>(User.class, UserDTO.class);

        //given
        final User from = getDefaultUserMock();

        //when
        UserDTO result = userMapper.mapUserToUserDTO(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getFullName(), result.getFullName(),
                messageGenerator.getMessage("fullName", from.getFullName(), "fullName", result.getFullName()));
    }
}
package com.sekarre.helpcentercore.factories;

import com.sekarre.helpcentercore.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.chat.ChatInfoDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.User;

import java.util.stream.Stream;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.getCurrentUser;

public class ChatMockFactory {

    public static Chat getChatMock() {
        return Chat.builder()
                .id(1L)
                .channelId("Channel id")
                .channelName("Channel name")
                .build();
    }

    public static ChatInfoDTO getChatInfoDTOMock() {
        return ChatInfoDTO.builder()
                .id(1L)
                .channelId("Channel id")
                .channelName("Channel name")
                .build();
    }

    public static ChatCreateRequestDTO getChatCreateRequestDTOMock(IssueDTO issueDTO, User supportUser) {
        return ChatCreateRequestDTO.builder()
                .channelName(issueDTO.getTitle())
                .usersId(Stream.of(getCurrentUser(), supportUser).map(User::getId).toArray(Long[]::new))
                .build();
    }
}

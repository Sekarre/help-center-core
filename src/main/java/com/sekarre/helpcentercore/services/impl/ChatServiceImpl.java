package com.sekarre.helpcentercore.services.impl;

import com.sekarre.helpcentercore.DTO.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.IssueDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.mappers.ChatMapper;
import com.sekarre.helpcentercore.services.ChatService;
import com.sekarre.helpcentercore.services.feignclients.ChatServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.getCurrentUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatServiceClient chatServiceClient;
    private final ChatMapper chatMapper;

    @Override
    public Chat getChat(IssueDTO issueDTO, User supportUser) {
        return chatMapper.mapChatInfoDtoToChat(chatServiceClient.createNewChat(
                ChatCreateRequestDTO.builder()
                        .channelName(issueDTO.getTitle())
                        .usersId(Stream.of(getCurrentUser(), supportUser).map(User::getId).toArray(Long[]::new))
                        .build()));
    }
}

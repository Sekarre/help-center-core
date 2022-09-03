package com.sekarre.helpcentercore.services.chat;

import com.sekarre.helpcentercore.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.mappers.ChatMapper;
import com.sekarre.helpcentercore.feignclients.ChatFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static com.sekarre.helpcentercore.security.UserDetailsHelper.getCurrentUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatFeignClient chatFeignClient;
    private final ChatMapper chatMapper;

    @Override
    public Chat getChat(IssueDTO issueDTO, User supportUser) {
        return chatMapper.mapChatInfoDTOToChat(chatFeignClient.createNewChat(
                ChatCreateRequestDTO.builder()
                        .channelName(issueDTO.getTitle())
                        .usersId(Stream.of(getCurrentUser(), supportUser).map(User::getId).toArray(Long[]::new))
                        .build()));
    }
}

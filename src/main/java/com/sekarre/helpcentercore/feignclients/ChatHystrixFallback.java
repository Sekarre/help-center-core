package com.sekarre.helpcentercore.feignclients;

import com.sekarre.helpcentercore.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.chat.ChatInfoDTO;
import com.sekarre.helpcentercore.exceptions.feign.ChatFeignClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class ChatHystrixFallback implements ChatFeignClient {

    @Override
    public ChatInfoDTO createNewChat(ChatCreateRequestDTO chatCreateRequestDTO) {
        log.error("Couldn't create chat for channel: " + chatCreateRequestDTO.getChannelName() +
                " and usersIds: " + Arrays.toString(chatCreateRequestDTO.getUsersId()));
        throw new ChatFeignClientException("Chat feign exception for: " + chatCreateRequestDTO);
    }
}

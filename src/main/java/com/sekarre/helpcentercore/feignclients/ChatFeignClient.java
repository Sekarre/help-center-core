package com.sekarre.helpcentercore.feignclients;

import com.sekarre.helpcentercore.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.chat.ChatInfoDTO;
import com.sekarre.helpcentercore.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "help-center-chat", configuration = FeignClientInterceptorConfig.class)
public interface ChatFeignClient {

    @PostMapping(value = "/api/v1/chat-info")
    ChatInfoDTO createNewChat(ChatCreateRequestDTO chatCreateRequestDTO);
}

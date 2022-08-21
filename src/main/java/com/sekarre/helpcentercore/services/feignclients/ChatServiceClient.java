package com.sekarre.helpcentercore.services.feignclients;

import com.sekarre.helpcentercore.DTO.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.ChatInfoDTO;
import com.sekarre.helpcentercore.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "help-center-chat", configuration = FeignClientInterceptorConfig.class)
public interface ChatServiceClient {

    @PostMapping(value = "/api/v1/chat-info")
    ChatInfoDTO createNewChat(ChatCreateRequestDTO chatCreateRequestDTO);
}

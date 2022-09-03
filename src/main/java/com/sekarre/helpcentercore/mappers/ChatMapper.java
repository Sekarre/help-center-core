package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.chat.ChatInfoDTO;
import com.sekarre.helpcentercore.domain.Chat;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class ChatMapper {

    public abstract Chat mapChatInfoDTOToChat(ChatInfoDTO chatInfoDTO);
}

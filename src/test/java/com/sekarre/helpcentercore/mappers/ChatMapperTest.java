package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.chat.ChatInfoDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.testutil.JUnitMessageGenerator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.sekarre.helpcentercore.factories.ChatMockFactory.getChatInfoDTOMock;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatMapperTest {

    private final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);
    private JUnitMessageGenerator<?, ?> messageGenerator;

    @Test
    public void should_map_ChatInfoDTO_to_Chat() {
        messageGenerator = new JUnitMessageGenerator<>(ChatInfoDTO.class, Chat.class);

        //given
        final ChatInfoDTO from = getChatInfoDTOMock();

        //when
        Chat result = chatMapper.mapChatInfoDTOToChat(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getChannelName(), result.getChannelName(),
                messageGenerator.getMessage("channelName", from.getChannelName(), "channelName", result.getChannelName()));
        assertEquals(from.getChannelId(), result.getChannelId(),
                messageGenerator.getMessage("channelId", from.getChannelId(), "channelId", result.getChannelId()));
    }
}
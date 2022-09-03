package com.sekarre.helpcentercore.services.chat;

import com.sekarre.helpcentercore.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.chat.ChatInfoDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.SecurityContextMockSetup;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.feignclients.ChatFeignClient;
import com.sekarre.helpcentercore.mappers.ChatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.sekarre.helpcentercore.factories.ChatMockFactory.*;
import static com.sekarre.helpcentercore.factories.IssueMockFactory.getIssueDTOMock;
import static com.sekarre.helpcentercore.factories.UserMockFactory.getDefaultUserMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChatServiceImplTest extends SecurityContextMockSetup {

    @Mock
    private ChatFeignClient chatFeignClient;
    @Mock
    private ChatMapper chatMapper;

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        chatService = new ChatServiceImpl(chatFeignClient, chatMapper);
    }

    @Test
    void should_return_chat() {
        //given
        final IssueDTO issueDTO = getIssueDTOMock();
        final ChatInfoDTO chatInfoDTO = getChatInfoDTOMock();
        final Chat chat = getChatMock();
        final User supportUser = getDefaultUserMock();
        final ChatCreateRequestDTO chatCreateRequestDTO = getChatCreateRequestDTOMock(issueDTO, supportUser);
        when(chatFeignClient.createNewChat(any(ChatCreateRequestDTO.class))).thenReturn(chatInfoDTO);
        when(chatMapper.mapChatInfoDTOToChat(any(ChatInfoDTO.class))).thenReturn(chat);

        //when
        Chat result = chatService.getChat(issueDTO, supportUser);

        //then
        assertNotNull(result);
        assertEquals(result, chat, "Chat is not equal to result Chat");
        verify(chatFeignClient, times(1)).createNewChat(chatCreateRequestDTO);
        verify(chatMapper, times(1)).mapChatInfoDTOToChat(chatInfoDTO);
    }
}
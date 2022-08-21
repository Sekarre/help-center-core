package com.sekarre.helpcentercore.services;

import com.sekarre.helpcentercore.DTO.ChatCreateRequestDTO;
import com.sekarre.helpcentercore.DTO.IssueDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.User;

public interface ChatService {

    Chat getChat(IssueDTO issueDTO, User supportUser);
}

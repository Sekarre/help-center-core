package com.sekarre.helpcentercore.services.chat;

import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.domain.Chat;
import com.sekarre.helpcentercore.domain.User;

public interface ChatService {

    Chat getChat(IssueDTO issueDTO, User supportUser);
}

package com.sekarre.helpcentercore.DTO.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatCreateRequestDTO {

    private String channelName;
    private Long[] usersId;
}

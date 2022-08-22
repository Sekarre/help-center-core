package com.sekarre.helpcentercore.DTO.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatInfoDTO {

    private Long id;
    private String channelName;
    private String channelId;
}

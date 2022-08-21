package com.sekarre.helpcentercore.DTO;

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

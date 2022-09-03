package com.sekarre.helpcentercore.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class UserDTO {

    private Long id;
    private String fullName;
    private String roleName;
    private String specialization;
}

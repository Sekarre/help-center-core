package com.sekarre.helpcentercore.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id", "channelId", "createdAt"})
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String channelName;

    @NotBlank
    private String channelId;

    @OneToOne(mappedBy = "chat", fetch = FetchType.LAZY)
    private Issue issue;

    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id")
    private User adminUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "Chat_User",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        if (users.contains(user)) {
            return;
        }
        this.users.add(user);
    }
}

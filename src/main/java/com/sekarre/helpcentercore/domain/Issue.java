package com.sekarre.helpcentercore.domain;

import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id", "title", "issue", "issueStatus", "issueType"})
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String issue;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IssueStatus issueStatus = IssueStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.MERGE)
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "chat_id")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Chat chat;

    @JoinColumn(name = "issue_type_id")
    @ManyToOne
    private IssueType issueType;

    @JoinColumn(name = "author_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User author;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "Issue_User",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @OrderBy(value = "id asc")
    private Set<User> participants = new LinkedHashSet<>();

    public void addParticipant(User user) {
        if (user == null || this.participants.contains(user)) {
            return;
        }
        this.participants.add(user);
    }
}

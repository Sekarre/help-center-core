package com.sekarre.helpcentercore.DTO.issue;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class GroupedByStatusIssueDTO {

    private List<IssueDTO> pendingIssues = new ArrayList<>();
    private List<IssueDTO> escalatingIssues = new ArrayList<>();
    private List<IssueDTO> infoRequiredIssues = new ArrayList<>();
    private List<IssueDTO> closedIssues = new ArrayList<>();


    public void addIssueDTO(IssueDTO issueDTO) {
        if (Objects.isNull(issueDTO)) {
            return;
        }
        switch (issueDTO.getIssueStatus()) {
            case PENDING -> pendingIssues.add(issueDTO);
            case CLOSED -> closedIssues.add(issueDTO);
            case ESCALATING -> escalatingIssues.add(issueDTO);
            case INFO_REQUIRED -> infoRequiredIssues.add(issueDTO);
        }
    }
}

package com.sekarre.helpcentercore.controllers;

import com.sekarre.helpcentercore.DTO.*;
import com.sekarre.helpcentercore.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.domain.enums.IssueStatus;
import com.sekarre.helpcentercore.security.perms.AdminPermission;
import com.sekarre.helpcentercore.security.perms.IssuePermission;
import com.sekarre.helpcentercore.services.issue.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sekarre.helpcentercore.controllers.IssueController.BASE_ISSUE_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = BASE_ISSUE_URL)
public class IssueController {

    public static final String BASE_ISSUE_URL = "/api/v1/issues";
    private final IssueService issueService;

    @GetMapping("/issue-statuses")
    public ResponseEntity<List<String>> getIssueStatuses() {
        return ResponseEntity.ok(issueService.getIssueStatuses());
    }

    @GetMapping("/types")
    public ResponseEntity<List<IssueTypeDTO>> getIssueTypes() {
        return ResponseEntity.ok(issueService.getAllIssueTypes());
    }

    @PostMapping
    public ResponseEntity<?> createNewIssue(@RequestBody @Valid IssueDTO issueDTO) {
        issueService.createNewIssue(issueDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @IssuePermission
    @PatchMapping("/{issueId}")
    public ResponseEntity<?> changeIssueStatus(@PathVariable Long issueId,
                                               @RequestBody @Valid IssueStatusChangeDTO issueStatusChangeDTO) {
        issueService.changeIssueStatus(issueId, issueStatusChangeDTO);
        return ResponseEntity.ok().build();
    }

    @IssuePermission
    @PutMapping("/{issueId}/user-add")
    public ResponseEntity<?> addUsersToIssue(@PathVariable Long issueId, @RequestBody Long[] usersId) {
        issueService.addUsersToIssue(issueId, usersId);
        return ResponseEntity.ok().build();
    }

    @IssuePermission
    @GetMapping("/{issueId}/participants")
    public ResponseEntity<List<UserDTO>> getIssueParticipants(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.getIssueParticipants(issueId));
    }

    @GetMapping
    public ResponseEntity<List<IssueDTO>> getAllIssues(@RequestParam(required = false) IssueStatus status) {
        return ResponseEntity.ok(issueService.getAllIssuesWithStatus(status));
    }

    @GetMapping("/grouped")
    public ResponseEntity<GroupedByStatusIssueDTO> getAllIssuesGrouped() {
        return ResponseEntity.ok(issueService.getAllIssuesGrouped());
    }

    @IssuePermission
    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @AdminPermission
    @DeleteMapping("/{issueId}")
    public ResponseEntity<?> deleteIssue(@PathVariable Long issueId) {
        issueService.deleteIssue(issueId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

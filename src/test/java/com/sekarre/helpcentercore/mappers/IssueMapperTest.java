package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.IssueType;
import com.sekarre.helpcentercore.testutil.JUnitMessageGenerator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.sekarre.helpcentercore.factories.IssueMockFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class IssueMapperTest {

    private final IssueMapper issueMapper = Mappers.getMapper(IssueMapper.class);
    private JUnitMessageGenerator<?, ?> messageGenerator;

    @Test
    public void should_map_IssueType_to_IssueTypeDTO() {
        messageGenerator = new JUnitMessageGenerator<>(IssueType.class, IssueTypeDTO.class);

        //given
        final IssueType from = getIssueTypeMock();

        //when
        IssueTypeDTO result = issueMapper.mapIssueTypeToIssueTypeDTO(from);

        //then
        assertEquals(String.valueOf(from.getId()), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getName().name(), result.getName(),
                messageGenerator.getMessage("name", from.getName().name(), "name", result.getName()));
    }

    @Test
    public void should_map_basic_fields_from_Issue_to_IssueDTO() {
        messageGenerator = new JUnitMessageGenerator<>(Issue.class, IssueDTO.class);

        //given
        final Issue from = getIssueMock();

        //when
        IssueDTO result = issueMapper.mapIssueToIssueDTO(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getTitle(), result.getTitle(),
                messageGenerator.getMessage("title", from.getTitle(), "title", result.getTitle()));
        assertEquals(from.getIssueStatus(), result.getIssueStatus(),
                messageGenerator.getMessage("issueStatus", from.getIssueStatus(), "issueStatus", result.getIssueStatus()));
        assertEquals(from.getIssue(), result.getIssue(),
                messageGenerator.getMessage("issue", from.getIssue(), "issue", result.getIssue()));
        assertEquals(from.getCreatedAt(), result.getCreatedAt(),
                messageGenerator.getMessage("createdAt", from.getCreatedAt(), "createdAt", result.getCreatedAt()));
        assertEquals(from.getUpdatedAt(), result.getUpdatedAt(),
                messageGenerator.getMessage("updatedAt", from.getUpdatedAt(), "updatedAt", result.getUpdatedAt()));
    }

    @Test
    public void should_map_author_fields_from_Issue_to_IssueDTO() {
        messageGenerator = new JUnitMessageGenerator<>(Issue.class, IssueDTO.class);

        //given
        final Issue from = getIssueMock();

        //when
        IssueDTO result = issueMapper.mapIssueToIssueDTO(from);

        //then
        assertEquals(from.getAuthor().getFirstName(), result.getFirstName(),
                messageGenerator.getMessage("author.firstName", from.getAuthor().getFirstName(), "firstName", result.getFirstName()));
        assertEquals(from.getAuthor().getLastName(), result.getLastName(),
                messageGenerator.getMessage("author.lastName", from.getAuthor().getLastName(), "lastName", result.getLastName()));
        assertEquals(from.getAuthor().getEmail(), result.getEmail(),
                messageGenerator.getMessage("author.email", from.getAuthor().getEmail(), "email", result.getEmail()));
    }

    @Test
    public void should_map_chat_and_issue_type_fields_from_Issue_to_IssueDTO() {
        messageGenerator = new JUnitMessageGenerator<>(Issue.class, IssueDTO.class);

        //given
        final Issue from = getIssueMock();

        //when
        IssueDTO result = issueMapper.mapIssueToIssueDTO(from);

        //then
        assertEquals(from.getIssueType().getId(), result.getIssueTypeId(),
                messageGenerator.getMessage("issueType.id", from.getIssueType().getId(), "issueTypeId", result.getIssueTypeId()));
        assertEquals(from.getChat().getChannelId(), result.getChannelId(),
                messageGenerator.getMessage("chat.channelId", from.getChat().getChannelId(), "channelId", result.getChannelId()));
    }

    @Test
    public void should_map_IssueDTO_to_Issue() {
        messageGenerator = new JUnitMessageGenerator<>(IssueDTO.class, Issue.class);

        //given
        final IssueDTO from = getIssueDTOMock();

        //when
        Issue result = issueMapper.mapIssueDTOToIssue(from);

        //then
        assertEquals(from.getId(), result.getId(),
                messageGenerator.getMessage("id", from.getId(), "id", result.getId()));
        assertEquals(from.getTitle(), result.getTitle(),
                messageGenerator.getMessage("title", from.getTitle(), "title", result.getTitle()));
        assertEquals(from.getIssueStatus(), result.getIssueStatus(),
                messageGenerator.getMessage("issueStatus", from.getIssueStatus(), "issueStatus", result.getIssueStatus()));
        assertEquals(from.getIssue(), result.getIssue(),
                messageGenerator.getMessage("issue", from.getIssue(), "issue", result.getIssue()));
        assertEquals(from.getCreatedAt(), result.getCreatedAt(),
                messageGenerator.getMessage("createdAt", from.getCreatedAt(), "createdAt", result.getCreatedAt()));
        assertEquals(from.getUpdatedAt(), result.getUpdatedAt(),
                messageGenerator.getMessage("updatedAt", from.getUpdatedAt(), "updatedAt", result.getUpdatedAt()));
    }
}
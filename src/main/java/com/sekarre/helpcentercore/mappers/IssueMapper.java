package com.sekarre.helpcentercore.mappers;

import com.sekarre.helpcentercore.DTO.issue.IssueDTO;
import com.sekarre.helpcentercore.DTO.issue.IssueTypeDTO;
import com.sekarre.helpcentercore.domain.Issue;
import com.sekarre.helpcentercore.domain.IssueType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class IssueMapper {

    public abstract IssueTypeDTO mapIssueTypeToIssueTypeDTO(IssueType issueType);

    @Mapping(target = "issueTypeId", source = "issue.issueType.id")
    @Mapping(target = "channelId", source = "issue.chat.channelId")
    @Mapping(target = "firstName", source = "issue.author.firstName")
    @Mapping(target = "lastName", source = "issue.author.lastName")
    @Mapping(target = "email", source = "issue.author.email")
    public abstract IssueDTO mapIssueToIssueDTO(Issue issue);

    public abstract Issue mapIssueDTOToIssue(IssueDTO issueDTO);
}

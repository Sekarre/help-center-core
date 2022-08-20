package com.sekarre.helpcentercore.factories;


import com.sekarre.helpcentercore.domain.enums.IssueStatus;

public class StatusChangedCommentFactory {

    public static String getStatusChangedComment(IssueStatus issueStatus) {
        switch (issueStatus) {
            case PENDING -> {
                return "Status changed to PENDING.";
            }
            case CLOSED -> {
                return "Status changed to CLOSED.";
            }
            case ESCALATING -> {
                return "Status changed to ESCALATING.";
            }
            case INFO_REQUIRED -> {
                return "Status changed to INFO REQUIRED.";
            }
            default -> {
                return "";
            }
        }
    }
}

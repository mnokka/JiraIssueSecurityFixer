# JiraIssueSecurityFixer
Jira server: POC to fix Atlassian "None" selection bug in Issue Security. None selection gives read access everyone. This listener force sets it back to wished (hardcoded) security level. This bug can be fixed also by making Security Level field as "required", this solution gives red error message to user when None is being selected.

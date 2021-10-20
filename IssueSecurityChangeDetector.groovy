/*
 October 2021 mika.nokka1@gmail.com
 
 POC to investigate following:
 
 If user selects "None" in Jira Issue Security selector, how to set
 it back to wished security level
 
 This is attemp to fix Atlassian bug: 
 https://jira.atlassian.com/browse/JRASERVER-5332?error=login_required&error_description=Login+required&state=ba72a4be-530e-48d7-8608-8714f4684242
 
 Tnx to Adaptavist library

*/

// TODO clean imports
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.customfields.option.LazyLoadedOption
import org.apache.log4j.Logger
import org.apache.log4j.Level
import com.atlassian.jira.issue.link.IssueLinkTypeManager
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import java.sql.Timestamp;

import com.atlassian.jira.issue.ModifiedValue;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;

import com.atlassian.jira.security.roles.ProjectRoleManager
import com.atlassian.mail.queue.SingleMailQueueItem
import com.atlassian.mail.MailException
import com.atlassian.mail.Email

import com.atlassian.jira.issue.security.IssueSecurityLevelManager


def userManager=ComponentAccessor.getUserManager()
def mailServerManager = ComponentAccessor.getMailServerManager()
def mailServer = mailServerManager.getDefaultSMTPMailServer()

// CONFIGURATIONS:
def ToBeTracked="mikanokka" // just hardcoded for POC
// END OF CONFIGURATIONS




// set logging to Jira log
def log = Logger.getLogger("IssueSecurityFixer") // change for customer system
log.setLevel(Level.DEBUG)  // DEBUG INFO
 
log.debug("---------- IssueSecurityFixer started -----------")



def util = ComponentAccessor.getUserUtil()
whoisthis2=ComponentAccessor.getJiraAuthenticationContext().getUser()
log.debug("Script run as a user: {$whoisthis2}")

log.debug "Something changed in issue: ${issue}"
//assignee=issue.assignee
//log.debug "Assignee in issue: ${assignee}"
//log.debug "Tracked one: ${ToBeTracked}"
//auser=userManager.getUserByName(ToBeTracked)
//log.debug "Tracked one via Usermanager: ${auser}"

def securityLevelManager = ComponentAccessor.getComponent(IssueSecurityLevelManager)
def securityLevelId = issue.securityLevelId

def securityLevelName=securityLevelManager.getIssueSecurityName(securityLevelId)
log.debug("Issue Security: ${securityLevelName}")


log.debug("---------- IssueSecurityFixer ended -----------")


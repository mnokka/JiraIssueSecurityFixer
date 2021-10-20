/*
 October 2021 mika.nokka1@gmail.com
 
 POC to investigate following:
 
 If user selects "None" in Jira Issue Security selector, how to set
 it back to wished security level
 
 NOTE: Currently Security level is just force set to prove it is possible to do
 
 This is attemp to fix Atlassian bug: 
 https://jira.atlassian.com/browse/JRASERVER-5332?error=login_required&error_description=Login+required&state=ba72a4be-530e-48d7-8608-8714f4684242
 
 Tnx to Adaptavist library


Used hardcode IDs for development Jira
Issue Security Level ID: 10102
*/

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import org.apache.log4j.Logger
import org.apache.log4j.Level
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField

import com.atlassian.jira.issue.ModifiedValue;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;

import com.atlassian.jira.issue.security.IssueSecurityLevelManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.IssueInputParametersImpl
import com.atlassian.jira.event.type.EventDispatchOption

def userManager=ComponentAccessor.getUserManager()
def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def issueManager= ComponentAccessor.issueManager
def issueService = ComponentAccessor.issueService

// set logging to Jira log
def log = Logger.getLogger("IssueSecurityFixer") // change for customer system
log.setLevel(Level.DEBUG)  // DEBUG INFO
 
log.debug("---------- IssueSecurityFixer started -----------")


def util = ComponentAccessor.getUserUtil()
whoisthis2=ComponentAccessor.getJiraAuthenticationContext().getUser()
log.debug("Script run as a user: {$whoisthis2}")
log.debug "Something changed in issue: ${issue}"


def securityLevelManager = ComponentAccessor.getComponent(IssueSecurityLevelManager)
def securityLevelId = issue.securityLevelId


log.debug("Issue Security Level ID: ${securityLevelId}")
def securityLevelName=securityLevelManager.getIssueSecurityName(securityLevelId)
log.debug("Current Issue Security: ${securityLevelName}")

// force sets IssueSecurityLevel to this ID ok
def issueSecurityLevelId=10102

def issueInputParameters = new IssueInputParametersImpl()
issueInputParameters.setSecurityLevelId(issueSecurityLevelId)

def updateValidationResult = issueService.validateUpdate(loggedInUser, issue.id, issueInputParameters)
assert updateValidationResult.valid : updateValidationResult.errorCollection

def issueUpdateResult = issueService.update(loggedInUser, updateValidationResult, EventDispatchOption.ISSUE_UPDATED, false)
assert issueUpdateResult.valid : issueUpdateResult.errorCollection



log.debug("---------- IssueSecurityFixer ended -----------")


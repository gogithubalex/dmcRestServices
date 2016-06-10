package org.dmc.services.web.controllers.legacy;

import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;

import javax.xml.ws.http.HTTPException;

import org.dmc.services.data.entities.legacy.CompanyVideo;
import org.dmc.services.data.entities.legacy.Discussion;
import org.dmc.services.data.entities.legacy.Id;
import org.dmc.services.data.entities.legacy.PostProjectJoinRequest;
import org.dmc.services.data.entities.legacy.PostProjectTag;
import org.dmc.services.data.entities.legacy.Project;
import org.dmc.services.data.entities.legacy.ProjectCreateRequest;
import org.dmc.services.data.entities.legacy.ProjectJoinRequest;
import org.dmc.services.data.entities.legacy.ProjectTag;
import org.dmc.services.data.entities.legacy.unimplemented.GetProjectTag;
import org.dmc.services.data.entities.legacy.unimplemented.IndividualDiscussion;
import org.dmc.services.data.entities.legacy.unimplemented.ProjectDocument;
import org.dmc.services.data.repositories.legacy.DiscussionListDao;
import org.dmc.services.data.repositories.legacy.ProjectDao;
import org.dmc.services.data.repositories.legacy.ProjectMemberDao;
import org.dmc.services.logging.ServiceLogger;
import org.dmc.services.web.exceptions.DMCServiceException;
import org.dmc.services.web.exceptions.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
public class ProjectController {

	private final String logTag = ProjectController.class.getName();
	private DiscussionListDao discussionListDao = new DiscussionListDao();
	private ProjectDao project = new ProjectDao();
	private ProjectDao projectDao = new ProjectDao();
	private ProjectMemberDao projectMemberDao = new ProjectMemberDao();

	@RequestMapping(value = "/projects/{projectID}", method = RequestMethod.GET)
	public Project getProject(@PathVariable("projectID") int projectID, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {

		ServiceLogger.log(logTag, "In getProject, projectID: " + projectID + " as user " + userEPPN);
		return project.getProject(projectID, userEPPN);
	}

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public ArrayList<Project> getProjectList(@RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
		ServiceLogger.log(logTag, "In getProjectList as user " + userEPPN);
		return project.getProjectList(userEPPN);
	}

	@RequestMapping(value = "projects/all", method = RequestMethod.GET)
	public ArrayList<Project> getProjectList(
	        @RequestParam(value="_order", required=false) String order,
	        @RequestParam(value="_sort", required=false) String sort,
            @RequestParam(value="_start", required=false) Integer start,
            @RequestParam(value="_limit", required=false) Integer limit,
	        @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
        ServiceLogger.log(logTag, "In getProjectList as user " + userEPPN);
        // @TODO: expand to handle arguments and change return type to a ResponseEntity to match new approach for error handling
        return project.getProjectList(userEPPN);
	}
	
	// leaving this as an example of how to work with parameters to URL
	// instead of json, but json is probably preferable
	@RequestMapping(value = "/projects/createWithParameter", method = RequestMethod.POST)
	@ResponseBody
	public Id createProject(@RequestParam("projectname") String projectname, @RequestParam("unixname") String unixname, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN)
			throws Exception {

		ServiceLogger.log(logTag, "In createProject: " + projectname + ", " + unixname + " as user " + userEPPN);

		long dueDate = 0;
		return project.createProject(projectname, unixname, projectname, Project.PRIVATE, userEPPN, dueDate);
	}

	@RequestMapping(value = "/projects/oldcreate", method = RequestMethod.POST, headers = { "Content-type=text/plain" })
	public Id createProject(@RequestBody String payload, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) throws Exception {

		ServiceLogger.log(logTag, "In createProject: " + payload + " as user " + userEPPN);
		return project.createProject(payload, userEPPN);
	}

	@RequestMapping(value = "/projects/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Id> createProject(@RequestBody ProjectCreateRequest payload, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) throws Exception {
		ServiceLogger.log(logTag, "In createProject: " + payload + " as user " + userEPPN);

		return new ResponseEntity<Id>(project.createProject(payload, userEPPN), HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/role/update", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public String updateRole(@RequestParam(value="id",
	 * defaultValue="-1") int id) { System.out.println("In createRole role: " +
	 * id);
	 * 
	 * 
	 * //RoleDao.createRole updates the Role in the database identified by id
	 * using the provided POST params //it creates an instance of this role i.e
	 * new Role(param.id, param.name, param.title.....) //this controller in
	 * turn returns this updated Role instance to the reques using spring's
	 * Jackson which //converts the response to JSON
	 * 
	 * return RoleDao.updateRole(params); }
	 */

	@RequestMapping(value = "/projects_join_requests", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<ProjectJoinRequest>> getProjectsJoinRequests(@RequestParam(value = "projectId", required = false) ArrayList<String> projects,
			@RequestParam(value = "profileId", required = false) ArrayList<String> profiles, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) throws Exception {
		ServiceLogger.log(logTag, "In getProjectsJoinRequests: as user " + userEPPN);

		return new ResponseEntity<ArrayList<ProjectJoinRequest>>(project.getProjectJoinRequest(projects, profiles, userEPPN), HttpStatus.OK);
	}

	@RequestMapping(value = "/projects_join_requests", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ProjectJoinRequest> createProjectJoinRequest(@RequestBody PostProjectJoinRequest payload, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN)
			throws Exception {
		ServiceLogger.log(logTag, "In createProjectJoinRequest: " + payload + " as user " + userEPPN);
		return new ResponseEntity<ProjectJoinRequest>(project.createProjectJoinRequest(payload, userEPPN), HttpStatus.OK);
	}

	@RequestMapping(value = "/projects/{projectId}/projects_join_requests", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<ProjectJoinRequest>> getProjectJoinRequests(@PathVariable("projectId") String projectId,
			@RequestParam(value = "profileId", required = false) ArrayList<String> profiles, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) throws Exception {
		ServiceLogger.log(logTag, "In getProjectsJoinRequests: as user " + userEPPN);

		ArrayList<String> projects = new ArrayList<String>();
		projects.add(projectId);
		return new ResponseEntity<ArrayList<ProjectJoinRequest>>(project.getProjectJoinRequest(projects, profiles, userEPPN), HttpStatus.OK);
	}

	@RequestMapping(value = "/profiles/{profileId}/projects_join_requests", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<ProjectJoinRequest>> getProfileJoinRequests(@PathVariable("profileId") String profileId,
			@RequestParam(value = "projectId", required = false) ArrayList<String> projects, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) throws Exception {
		ServiceLogger.log(logTag, "In getProjectsJoinRequests: as user " + userEPPN);

		ArrayList<String> profiles = new ArrayList<String>();
		profiles.add(profileId);
		return new ResponseEntity<ArrayList<ProjectJoinRequest>>(project.getProjectJoinRequest(projects, profiles, userEPPN), HttpStatus.OK);
	}

	@RequestMapping(value = "/projects_join_requests/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteProjectJoinRequests(@PathVariable("id") String id, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) throws Exception {
		ServiceLogger.log(logTag, "In deleteProjectJoinRequests: for id " + id + " as user " + userEPPN);

		try {
			ServiceLogger.log(logTag, "In deleteProjectJoinRequests: for id " + id + " as user " + userEPPN);

			boolean ok = project.deleteProjectRequest(id, userEPPN);
			if (ok) {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<ErrorMessage>(new ErrorMessage("failure to delete project join request"), HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			ServiceLogger.log(logTag, "caught exception: for id " + id + " as user " + userEPPN + " " + e.getMessage());

			if (e.getMessage().equals("you are not allowed to delete this request")) {
				return new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
			} else if (e.getMessage().equals("invalid id")) {
				return new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()), HttpStatus.FORBIDDEN);
			} else {
				throw e;
			}
		}
	}

	@RequestMapping(value = "/projects/{projectID}/projects_tags", method = RequestMethod.GET)
	public ArrayList<ProjectTag> getProjectTagList(@PathVariable("projectID") int projectID, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
		ServiceLogger.log(logTag, "In getProjectTagList as user " + userEPPN);

		return project.getProjectTagList(projectID, userEPPN);
	}

	@RequestMapping(value = "/projects_tags", produces = { "application/json", "text/html" }, method = RequestMethod.POST)
	public ResponseEntity<GetProjectTag> projectsTagsPost(

	@RequestBody PostProjectTag body) {
		// do some magic!
		return new ResponseEntity<GetProjectTag>(HttpStatus.NOT_IMPLEMENTED);
	}

	/**
	 * Return Project Discussions
	 **/
	@RequestMapping(value = "/projects/{projectID}/all-discussions", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity getDiscussions(@PathVariable("projectID") int projectID, @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN,
			@RequestParam(value = "_limit", defaultValue = "100") int limit, @RequestParam(value = "_order", defaultValue = "DESC") String order,
			@RequestParam(value = "_sort", defaultValue = "time_posted") String sort) {

		ServiceLogger.log(logTag, "getDiscussions, userEPPN: " + userEPPN);
		int statusCode = HttpStatus.OK.value();
		ArrayList<Discussion> discussions = null;

		try {
			discussions = discussionListDao.getDiscussionList(userEPPN, projectID, limit, order, sort);
			return new ResponseEntity<ArrayList<Discussion>>(discussions, HttpStatus.valueOf(statusCode));
		} catch (HTTPException e) {
			ServiceLogger.log(logTag, e.getMessage());
			statusCode = e.getStatusCode();
			ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
		}
	}

	@ExceptionHandler(Exception.class)
	public ErrorMessage handleException(Exception ex) {
		// prepare responseEntity
		ErrorMessage result = new ErrorMessage.ErrorMessageBuilder(ex.getMessage()).build();
		ServiceLogger.log(logTag, result.getMessage());
		return result;
	}

	/**	
     *
     **/
	@RequestMapping(value = "/projects/{projectID}/project_documents", produces = { "application/json", "text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDocument>> projectsProjectIDProjectDocumentsGet(@PathVariable("projectID") String projectID,
			@RequestParam(value = "projectDocumentId", required = true) String projectDocumentId, @RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "order", required = false) String order, @RequestParam(value = "sort", required = false) String sort) {
		// do some magic!
		return new ResponseEntity<List<ProjectDocument>>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/projects/{projectID}/following_discussions", produces = { "application/json", "text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<IndividualDiscussion>> projectsProjectIDFollowingDiscussionsGet(@PathVariable("projectID") String projectID,
			@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "order", required = false) String order, @RequestParam(value = "sort", required = false) String sort) {
		// do some magic!
		return new ResponseEntity<List<IndividualDiscussion>>(HttpStatus.NOT_IMPLEMENTED);
	}

	/**
	 * Update Project
	 * @param id
	 * @param project
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/projects/{id}", method = RequestMethod.PATCH, produces = { "application/json" })
	public ResponseEntity updateProject(@PathVariable("id") int id, @RequestBody Project project, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		ServiceLogger.log(logTag, "updateProject, project: " + project.toString());

		int httpStatusCode = HttpStatus.OK.value();
		Id updatedId = null;
		
		try {
			updatedId = projectDao.updateProject(id, project, userEPPN);
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		}

		return new ResponseEntity<Id>(updatedId, HttpStatus.valueOf(httpStatusCode));
	}

}

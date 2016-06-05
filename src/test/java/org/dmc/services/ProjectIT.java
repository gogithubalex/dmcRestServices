package org.dmc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.response.ValidatableResponse;

import org.json.JSONObject;
import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.dmc.services.data.entities.legacy.Discussion;
import org.dmc.services.data.entities.legacy.PostProjectJoinRequest;
import org.dmc.services.data.entities.legacy.PostProjectTag;
import org.dmc.services.data.entities.legacy.Project;
import org.dmc.services.data.entities.legacy.ProjectCreateRequest;
import org.dmc.services.data.entities.legacy.ProjectJoinRequest;
import org.dmc.services.data.entities.legacy.ProjectMember;
import org.dmc.services.logging.ServiceLogger;

//@Ignore
public class ProjectIT extends BaseIT {

	private static final String PROJECT_DISCUSSIONS_RESOURCE = "/projects/{projectID}/all-discussions";
	private static final String PROJECT_UPDATE_RESOURCE = "/projects/{id}";
    private static final String PROJECT_GET_ALL_RESOURCE = "/projects/all";
	
	// Member  
	private static final String MEMBER_ACCEPT_RESOURCE = "/projects/{projectId}/accept/{memberId}";
	private static final String MEMBER_REJECT_RESOURCE = "/projects/{projectId}/reject/{memberId}";
	
    private final String logTag = ProjectIT.class.getName();
    private DiscussionIT discussionIT = new DiscussionIT();
	private Integer createdId = null;
	String randomEPPN = UUID.randomUUID().toString();

	private String projectId = "1";
	
    @Test
	public void testProject6() {
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects/6").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectSchema.json"));
	}
	
	@Test
	public void testProject5(){
		
		//TODO: need to update to another demo project
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects/6").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectSchema.json"));		
	}
	
	// this tests could do check more about what tests are returned
	@Test
	public void testProjectList(){
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectListSchema.json"));
	}

    // this tests could do check more about what tests are returned
    @Test
    public void testProjectListAll(){
        given().
            header("AJP_eppn", userEPPN).
            param("_order", "ASC").
            param("_sort", "most_recent").
            param("_start", 0).
        expect().
            statusCode(200).
        when().
            get(PROJECT_GET_ALL_RESOURCE).
        then().
            body(matchesJsonSchemaInClasspath("Schemas/projectListSchema.json"));
    }

	@Test
	public void testProjectCreateJsonString() {
		
		String unique = UUID.randomUUID().toString().substring(0, 24);
		
		JSONObject json = new JSONObject();
		json.put("projectname", "junit" + unique);
		json.put("unixname", "junit" + unique);
        ServiceLogger.log(logTag, "testProjectCreateJsonString: json = " + json.toString());
        
		this.createdId = given().
			header("AJP_eppn", userEPPN).
			body(json.toString()).
		expect().
			statusCode(200).
		when().
			post("/projects/oldcreate").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/idSchema.json")).
		extract().
			path("id");
	}
	

	// see as an example to configure the object https://github.com/jayway/rest-assured/wiki/Usage#serialization
	@Test
	public void testProjectCreateJsonObject()
	  throws IOException {
		//JSONObject json = new JSONObject();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String unique = format.format(date);
		//json.put("description", "junit testProjectCreateJsonObject " + unique);
		//json.put("name", "junitjson" + unique);

		ProjectCreateRequest json = new ProjectCreateRequest();
		json.setDescription("junit testProjectCreateJsonObject " + unique);
		json.setTitle("junitjson" + unique);

		ServiceLogger.log(logTag, "testProjectCreateJsonObject: json = " + json.toString());
		given().
			header("Content-type", "application/json").
			header("AJP_eppn", userEPPN).
			body(json).
		expect().
			statusCode(200).
		when().
			post("/projects/create").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/idSchema.json"));
	}

	@Test
	public void ftestProjectCreateFailOnDuplicate(){
		JSONObject json = new JSONObject();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String unique = format.format(date);

		json.put("projectname", "junitTestdup" + unique);
		json.put("unixname", "junitdup" + unique);
        ServiceLogger.log(logTag, "testProjectCreateFailOnDuplicate: json = " + json.toString());

		// first time should work
		given().
			header("Content-type", "text/plain").
			header("AJP_eppn", userEPPN).
			body(json.toString()).
		expect().
			statusCode(200).
		when().
			post("/projects/oldcreate").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/idSchema.json"));

        ServiceLogger.log(logTag, "testProjectCreateFailOnDuplicate: try to create again");
		// second time should fail, because unixname is a duplicate
		given().
			header("Content-type", "text/plain").
			header("AJP_eppn", userEPPN).
			body(json.toString()).
		expect().
			statusCode(200).
		when().
			post("/projects/oldcreate").
		then().
			log().all().
			body(matchesJsonSchemaInClasspath("Schemas/errorSchema.json"));
	}

	@Test
	public void testProjectCreateFailOnDuplicateJson()
	  throws IOException {
		//JSONObject json = new JSONObject();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String unique = format.format(date);

		//json.put("name", "junitTestJsondup" + unique);
		//json.put("description", "junitdup json testing " + unique);
		ProjectCreateRequest json = new ProjectCreateRequest();
		json.setDescription("junitdup json testing " + unique);
		json.setTitle("junitTestJsondup" + unique);

        ServiceLogger.log(logTag, "testProjectCreateFailOnDuplicateJson: json = " + json.toString());

		// first time should work
		given().
			header("Content-type", "application/json").
			header("AJP_eppn", userEPPN).
			body(json).
		expect().
			statusCode(200).
		when().
			post("/projects/create").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/idSchema.json"));

        ServiceLogger.log(logTag, "testProjectCreateFailOnDuplicateJson: try to create again");
		// second time should fail, because unixname is a duplicate
		given().
			header("Content-type", "application/json").
			header("AJP_eppn", userEPPN).
			body(json).
		expect().
			statusCode(200).
		when().
			post("/projects/create").
		then().
			log().all().
			body(matchesJsonSchemaInClasspath("Schemas/errorSchema.json"));
	}

	@Test
	public void testProjectTypeChecks(){
		assertEquals(1, Project.IsPublic("Public"));
		assertEquals(0, Project.IsPublic("PRIVATE"));
	}
	
    @Test
	public void testAllProjectDiscussions() {
    	
    	ObjectMapper mapper = new ObjectMapper();
    	this.testProjectCreateJsonString();

    	if (this.createdId != null) {
        	Integer discussionId = discussionIT.createDiscussion(this.createdId);
        	if (discussionId != null) {
    			JsonNode discussions =
    		        given()
    		        .header("Content-type", "application/json")
    		        .header("AJP_eppn", randomEPPN)
    		        .expect()
    		        .statusCode(200)
    		        .when()
    		        .get(PROJECT_DISCUSSIONS_RESOURCE, this.createdId)
    		        .as(JsonNode.class);
                
    			try {
					ArrayList<Discussion> discussionList = 
							mapper.readValue(mapper.treeAsTokens(discussions), 
							new TypeReference<ArrayList<Discussion>>() {});

					for (Discussion discussion : discussionList) {
						assertTrue("Discussion belongs to project", discussion.getProjectId().equals(this.createdId.toString()));
					}
					
    			} catch (Exception e) {
    				ServiceLogger.log(logTag, e.getMessage());
    			}
        	}
    	}
	}

    @Test
	public void testGetProject6Tags() {
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects/6/projects_tags").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectTagListSchema.json"));
	}
	
	@Test
	public void testProject6Members(){
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects/6/projects_members").
		then().
			log().all().
			body(matchesJsonSchemaInClasspath("Schemas/projectMemberListSchema.json"));		
		
	}
	
	
	@Test
	public void testProjectJoinRequests(){
		ValidatableResponse response =  
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects_join_requests").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectJoinRequestListSchema.json"));
		
		ServiceLogger.log(logTag, "testProjectJoinRequests " + response.extract().asString());
		// based on data loaded in gforge.psql
		// this is true only for a clean db
		//String expectedResponseAsString = "[{\"id\":\"6-102-102\",\"projectId\":\"6\",\"profileId\":\"102\"},{\"id\":\"6-111-102\",\"projectId\":\"6\",\"profileId\":\"111\"},{\"id\":\"6-111-111\",\"projectId\":\"6\",\"profileId\":\"111\"},{\"id\":\"4-111-111\",\"projectId\":\"4\",\"profileId\":\"111\"}]";
		//assertTrue(expectedResponseAsString.equals(response.extract().asString()));

		JSONArray jsonArray = new JSONArray(response.extract().asString());
	    ArrayList<ProjectJoinRequest> list = new ArrayList<ProjectJoinRequest>();

	    for (int i = 0; i < jsonArray.length(); i++) {
	    	JSONObject json = jsonArray.getJSONObject(i);
	    	ProjectJoinRequest item = new ProjectJoinRequest();
	    	item.setId(json.getString("id"));
	    	item.setProjectId(json.getString("projectId"));
	    	item.setProfileId(json.getString("profileId"));
	    	list.add(item);
	    }

		ProjectJoinRequest expected = new ProjectJoinRequest();
		expected.setId("6-102-102");
		expected.setProjectId("6");
		expected.setProfileId("102");
		assertTrue(list.contains(expected));
		expected.setId("6-111-102");
		expected.setProjectId("6");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
		expected.setId("6-111-111");
		expected.setProjectId("6");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
		expected.setId("4-111-111");
		expected.setProjectId("4");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
	}		

	@Test
	public void testProjectJoinRequestsWithParamList(){
		ValidatableResponse response =  
		given().
			header("AJP_eppn", userEPPN).
			param("projectId", "4,6").
			param("profileId", "145").
		expect().
			statusCode(200).
		when().
			get("/projects_join_requests").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectJoinRequestListSchema.json"));
		
		ServiceLogger.log(logTag, "testProjectJoinRequests " + response.extract().asString());
		// based on data loaded in gforge.psql
		String expectedResponseAsString = "[]";
		assertTrue(expectedResponseAsString.equals(response.extract().asString()));

		JSONArray jsonArray = new JSONArray(response.extract().asString());
		assertTrue(0 == jsonArray.length());
	}

	@Test
	public void testProjectJoinRequestsProject6(){
		ValidatableResponse response =  
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/projects/6/projects_join_requests").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectJoinRequestListSchema.json"));
		
		ServiceLogger.log(logTag, "testProjectJoinRequestsProject6" + response.extract().asString());
		// based on data loaded in gforge.psql
		// this is only true for a clean db 
		//String expectedResponseAsString = "[{\"id\":\"6-102-102\",\"projectId\":\"6\",\"profileId\":\"102\"},{\"id\":\"6-111-102\",\"projectId\":\"6\",\"profileId\":\"111\"},{\"id\":\"6-111-111\",\"projectId\":\"6\",\"profileId\":\"111\"}]";
		//assertTrue(expectedResponseAsString.equals(response.extract().asString()));

		JSONArray jsonArray = new JSONArray(response.extract().asString());
	    ArrayList<ProjectJoinRequest> list = new ArrayList<ProjectJoinRequest>();

	    for (int i = 0; i < jsonArray.length(); i++) {
	    	JSONObject json = jsonArray.getJSONObject(i);
	    	ProjectJoinRequest item = new ProjectJoinRequest();
	    	item.setId(json.getString("id"));
	    	item.setProjectId(json.getString("projectId"));
	    	item.setProfileId(json.getString("profileId"));
	    	list.add(item);
	    }

		ProjectJoinRequest expected = new ProjectJoinRequest();
		expected.setId("6-102-102");
		expected.setProjectId("6");
		expected.setProfileId("102");
		assertTrue(list.contains(expected));
		expected.setId("6-111-102");
		expected.setProjectId("6");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
		expected.setId("6-111-111");
		expected.setProjectId("6");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
		// this one should not appear because it is project 4
		expected.setId("4-111-111");
		expected.setProjectId("4");
		expected.setProfileId("111");
		assertFalse(list.contains(expected));
	}

	@Test
	public void testProjectJoinRequestsProfile111(){
		ValidatableResponse response =  
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(200).
		when().
			get("/profiles/111/projects_join_requests").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectJoinRequestListSchema.json"));
		
		ServiceLogger.log(logTag, "testProjectJoinRequestsProfile111" + response.extract().asString());
		// based on data loaded in gforge.psql
		String expectedResponseAsString = "[{\"id\":\"6-111-102\",\"projectId\":\"6\",\"profileId\":\"111\"},{\"id\":\"6-111-111\",\"projectId\":\"6\",\"profileId\":\"111\"},{\"id\":\"4-111-111\",\"projectId\":\"4\",\"profileId\":\"111\"}]";
		assertTrue(expectedResponseAsString.equals(response.extract().asString()));

		JSONArray jsonArray = new JSONArray(response.extract().asString());
	    ArrayList<ProjectJoinRequest> list = new ArrayList<ProjectJoinRequest>();

	    for (int i = 0; i < jsonArray.length(); i++) {
	    	JSONObject json = jsonArray.getJSONObject(i);
	    	ProjectJoinRequest item = new ProjectJoinRequest();
	    	item.setId(json.getString("id"));
	    	item.setProjectId(json.getString("projectId"));
	    	item.setProfileId(json.getString("profileId"));
	    	list.add(item);
	    }

		ProjectJoinRequest expected = new ProjectJoinRequest();
		expected.setId("6-102-102");
		expected.setProjectId("6");
		expected.setProfileId("102");
		// this one should not appear because it is profile 102
		assertFalse(list.contains(expected));
		expected.setId("6-111-102");
		expected.setProjectId("6");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
		expected.setId("6-111-111");
		expected.setProjectId("6");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
		expected.setId("4-111-111");
		expected.setProjectId("4");
		expected.setProfileId("111");
		assertTrue(list.contains(expected));
	}
	
	@Test
	public void testCreateAndDeleteProjectJoinRequests(){
		PostProjectJoinRequest json = new PostProjectJoinRequest();
		json.setProjectId("4");
		json.setProfileId("110");

		String id = 
		given().
			header("Content-type", "application/json").
			header("AJP_eppn", userEPPN).
			body(json).
		expect().
			statusCode(200).
		when().
			post("/projects_join_requests").
		then().
			body(matchesJsonSchemaInClasspath("Schemas/projectJoinRequestSchema.json")).
			extract().path("id");

		// forbidden
		String invalid_id = "12-44-102-483";
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(403).
		when().
			delete("/projects_join_requests/" + invalid_id);

		// unauthorized
		given().
			header("AJP_eppn", "testUser").
		expect().
			statusCode(401).
		when().
			delete("/projects_join_requests/" + id);

		// should be successful
		given().
			header("AJP_eppn", userEPPN).
		expect().
			statusCode(204).
		when().
			delete("/projects_join_requests/" + id);

		// forbidden - because it is already deleted
		given().
			header("AJP_eppn", userEPPN).
			param("id", id).
		expect().
			statusCode(403).
		when().
			delete("/projects_join_requests/" + id);

	}

	
	
	/**
	 * test case for post /projects_tags
	 */
	@Test
	public void testProjectPost_ProjectTag(){
		PostProjectTag obj =null;
		ObjectMapper mapper = new ObjectMapper();
		String postedProjectTagsJSONString = null;
		try {
			postedProjectTagsJSONString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		given().
        header("Content-type", "application/json").
        header("AJP_eppn", userEPPN).
        body(postedProjectTagsJSONString).
	expect().
        statusCode(HttpStatus.NOT_IMPLEMENTED.value()).
	when().
        post("/projects_tags");
	}
	
	
	/**
	 * test case for DELETE /projects_tags/{projectTagid}
	 */
	@Test
	public void testProjectDelete_ProjectTag(){
		given().
		header("AJP_eppn", userEPPN).
		expect().
		statusCode(HttpStatus.NOT_IMPLEMENTED.value()).
		when().delete("/projects_tags/" + projectId);
	}
	
	
	@Test
	public void testProject_ProjectDocuments() {
		given().header("AJP_eppn", userEPPN).expect().statusCode(200).when()
		.get("/projects/"+ projectId + "/project_documents");
	}
	
	/**
	 * test case for GET /projects/{projectID}/following_discussions
	 */
	
	@Test
	public void testProject_FollowingDiscussion() {
		given().header("AJP_eppn", userEPPN).expect().statusCode(HttpStatus.NOT_IMPLEMENTED.value()).when()
		.get("/projects/"+ projectId + "/following_discussions");
	}
	
	/**
	 * PATCH /projects/{projectId}
	 */
	@Test
	public void testProjectPatch() {

		int updatedId = -1;
		
		this.testProjectCreateJsonString();
		if (this.createdId != null) {
			JSONObject json = new JSONObject();
			json.put("title", "test project title update");
			json.put("description", "test project description update");
			json.put("dueDate", 0);

			updatedId = given()
				.header("Content-type", "application/json")
				.header("AJP_eppn", randomEPPN)
				.body(json.toString())
			.expect()
				.statusCode(200)
			.when()
				.patch(PROJECT_UPDATE_RESOURCE, this.createdId).then()
				.body(matchesJsonSchemaInClasspath("Schemas/idSchema.json")).extract().path("id");
			
			assertTrue("Updated project is the one identified in URL param", updatedId == this.createdId);
		}
	}
	
    /**
     * PATCH /projects/{projectId}/accept/{memberId}
     */
    @Test
    public void testProjectMemberAcceptAfterProjectCreate() {

        String adminUser = "fforgeadmin";

        this.testProjectCreateJsonString();
        if (this.createdId != null) {
            given()
                .header("Content-type", "application/json")
                .header("AJP_eppn", adminUser)
            .expect()
                .statusCode(200)
            .when()
                .patch(MEMBER_ACCEPT_RESOURCE, this.createdId, adminUser)
                .asString();

        }
    }

	/**
	 * PATCH /projects/{projectId}/accept/{memberId}
	 */
	@Test
	public void testProjectMemberAcceptNoRequest() {

		String adminUser = "fforgeadmin";
		String testUser = "testUser";
		
		this.testProjectCreateJsonString();
		if (this.createdId != null) {
			String response = given()
				.header("Content-type", "application/json")
				.header("AJP_eppn", adminUser)
			.expect()
				.statusCode(404)
			.when()
				.patch(MEMBER_ACCEPT_RESOURCE, this.createdId, testUser)
				.asString();
			
			assertTrue("No Existing Request", response.contains("no existing request to join the project"));
		}
	}
	
	/**
	 * PATCH /projects/{projectId}/accept/{memberId}
	 */
	@Test
	public void testProjectMemberAcceptNotAdmin() {

		String adminUser = "fforgeadmin";
		
		this.testProjectCreateJsonString();
		if (this.createdId != null) {
			String response = given()
				.header("Content-type", "application/json")
				.header("AJP_eppn", randomEPPN)
			.expect()
				.statusCode(403)
			.when()
				.patch(MEMBER_ACCEPT_RESOURCE, this.createdId, adminUser)
				.asString();
			
			assertTrue("Not Admin",response.contains("does not have permission to accept members"));
		}
	}
	
	/**
	 * PATCH /projects/{projectId}/reject/{memberId}
	 */
	@Test
	public void testProjectMemberRejectOnlyAdmin() {

		String adminUser = "fforgeadmin";
		
		this.testProjectCreateJsonString();
		if (this.createdId != null) {
			String response = given()
				.header("Content-type", "application/json")
				.header("AJP_eppn", adminUser)
			.expect()
				.statusCode(403)
			.when()
				.delete(MEMBER_REJECT_RESOURCE, this.createdId, adminUser)
				.asString();
			
			assertTrue("No Existing Request", response.contains("is the only Admin of project"));
		}
	}
	
	/**
	 * PATCH /projects/{projectId}/reject/{memberId}
	 */
	@Test
	public void testProjectMemberRejecttNotAdmin() {

		String adminUser = "fforgeadmin";
		
		this.testProjectCreateJsonString();
		if (this.createdId != null) {
			String response = given()
				.header("Content-type", "application/json")
				.header("AJP_eppn", randomEPPN)
			.expect()
				.statusCode(403)
			.when()
				.delete(MEMBER_REJECT_RESOURCE, this.createdId, adminUser)
				.asString();
			
			assertTrue("Not Admin", response.contains("not have permission to remove members"));
		}
	}

}

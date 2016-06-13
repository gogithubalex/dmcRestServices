package org.dmc.services;

import org.apache.http.HttpStatus;
import org.dmc.services.company.CompanyDao;
import org.dmc.services.company.Company;
import org.dmc.services.utility.TestUserUtil;
import org.dmc.services.sharedattributes.FeatureImage;

import org.json.JSONObject;
import org.junit.*;
import org.junit.Test;


import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * Created by 200005921 on 3/30/2016.
 */
public class CompanyAdminIT  extends BaseIT {

    private static final String COMPANY_CREATE_RESOURCE = "/companies/create";
    private static final String COMPANY_ADD_ADMIN_RESOURCE = "/companies/{id}/admin/{userId}";
    private static final String COMPANY_ADD_MEMBER_RESOURCE = "/companies/{id}/member/{userId}";

    private static final String COMPANY_UPDATE_RESOURCE = "/companies/{id}";
    private static final String COMPANY_GET_RESOURCE = "/companies/{id}";



    private static final String logTag = CompanyAdminIT.class.getName();

    private int ownerUserId = -1;
    private String ownerEPPN;

    private int memberUserId = -1;
    private String memberEPPN;

    private int nonmemberUserId = -1;
    private String nonmemberEPPN;

    private CompanyDao companyDao = new CompanyDao();

    @Before
    public void setupData () {

        ServiceLogger.log(logTag, "starting setupData");
        String unique = TestUserUtil.generateTime();

        // set up user as owner of company
        ownerEPPN = "userEPPN" + unique;
        String ownerGivenName = "userGivenName" + unique;
        String ownerSurName= "userSurName" + unique;
        String ownerDisplayName = "userDisplayName" + unique;
        String ownerEmail = "userEmail" + unique;

        ownerUserId = createUser(ownerEPPN,ownerGivenName, ownerSurName, ownerDisplayName, ownerEmail);

        // add company
        companyId =  createCompany(ownerEPPN);

        // Create a member user
        String unique2 = TestUserUtil.generateTime();
        memberEPPN = "userEPPN" + unique2;
        String memberGivenName = "userGivenName" + unique2;
        String memberSurName= "userSurName" + unique2;
        String memberDisplayName = "userDisplayName" + unique2;
        String memberEmail = "userEmail" + unique2;

        memberUserId = createUser(memberEPPN,memberGivenName, memberSurName, memberDisplayName, memberEmail);

        companyDao.addMember(companyId, memberUserId, ownerEPPN);

        // Create a user that is not a member of the company
        String unique3 = TestUserUtil.generateTime();
        nonmemberEPPN = "userEPPN" + unique3;
        String member3GivenName = "userGivenName" + unique3;
        String member3SurName= "userSurName" + unique3;
        String member3DisplayName = "userDisplayName" + unique3;
        String member3Email = "userEmail" + unique3;

        nonmemberUserId = createUser(nonmemberEPPN,member3GivenName, member3SurName, member3DisplayName, member3Email);
    }


    public static int createUser(String userEPPN, String userGivenName, String userSurName, String userDisplayName, String userEmail){

        Integer id =
                given().
                        header("Content-type", "text/plain").
                        header("AJP_eppn", userEPPN).
                        header("AJP_givenName", userGivenName).
                        header("AJP_sn", userSurName).
                        header("AJP_displayName", userDisplayName).
                        header("AJP_mail", userEmail).
                        expect().
                        statusCode(200).
                        when().
                        post("/users/create").
                        then().
                        body(matchesJsonSchemaInClasspath("Schemas/idSchema.json")).
                        extract().path("id");

        // check return value > 0
        assertTrue("Added user: " + "userEPPN" + userEPPN + " Valid user ID must be greater then zero.  Value is " + id + ".", id > 0);
        // could also check email syntax

        return id.intValue();
    }

    private int companyId = -1;

    public int  createCompany (String ownerEPPN ) {
        String json = createCompanyFixture(ownerEPPN);
        int companyId = given()
                .body(json)
                .header("AJP_eppn", ownerEPPN)
				.header("Content-type", "application/json")
                .expect()
                .statusCode(200)
                .when()
                .post(COMPANY_CREATE_RESOURCE)
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/idSchema.json"))
                .extract()
                .path("id");

        return companyId;
    }

    public String createCompanyFixture(String ownerName) {
		Company company = new Company();
		
		//		company.setId(Integer.toString(id));
		company.setAccountId(Integer.toString(1001));
		company.setName("test name");
		company.setLocation("test location");
		company.setDescription("test description");
		company.setDivision("test division");
		company.setIndustry("test industry");
		company.setNAICSCode("test NAICSCode");
		company.setRDFocus("test RDFocus");
		company.setCustomers("test customers");
		company.setAwardsReceived("test awardsReceived");
		company.setTechnicalExpertise("test technicalExpertise");
		company.setToolsSoftwareEquipmentMachines("test toolsSoftwareEquipmentMachines");
		company.setPastCollaborations("test postCollaborations");
		company.setPastProjects("test pastProjects");
		company.setUpcomingProjectInterests("test upcomingProjectInterests");
		company.setCollaborationInterests("test collaborationInterests");
		company.setAddress("test address , test address");
		company.setCity("test city");
		
		// Todo:				company.setState(state);
		// 		json.put("state", "test state");
		
		company.setZipCode("test zipCode");
		company.setTwitter("test twitter");
		company.setLinkedIn("test linkedIn");
		company.setWebsite("test website");
		company.setMethodCommunication("test methodCommunication");
		company.setEmail("test email");
		company.setPhone("test phone");
		company.setCategoryTier(30);
		company.setDateJoined("test dateJoined");
		company.setReasonJoining("test reasonJoining");
		company.setFeatureImage(new FeatureImage("feature_image_thumb.jpg", "feature_image_large.jpg"));
		company.setLogoImage("test logoImage");
		company.setFollow(true);
		company.setFavoritesCount(1002);
		company.setIsOwner(false);
		company.setOwner(ownerName);
		
		ObjectMapper mapper = new ObjectMapper();
		String companyJSONString = null;
		try {
			companyJSONString = mapper.writeValueAsString(company);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companyJSONString;
    }

    public String createUpdateCompanyFixture(String ownerName) {
        Company company = new Company();

        //		company.setId(Integer.toString(id));
        company.setAccountId(Integer.toString(1001));
        company.setName("update name");
        company.setLocation("update location");
        company.setDescription("update description");
        company.setDivision("update division");
        company.setIndustry("update industry");
        company.setNAICSCode("update NAICSCode");
        company.setRDFocus("update RDFocus");
        company.setCustomers("update customers");
        company.setAwardsReceived("update awardsReceived");
        company.setTechnicalExpertise("update technicalExpertise");
        company.setToolsSoftwareEquipmentMachines("update toolsSoftwareEquipmentMachines");
        company.setPastCollaborations("update postCollaborations");
        company.setPastProjects("update pastProjects");
        company.setUpcomingProjectInterests("update upcomingProjectInterests");
        company.setCollaborationInterests("update collaborationInterests");
        company.setAddress("update address , update address");
        company.setCity("update city");

        // Todo:				company.setState(state);
        // 		json.put("state", "update state");

        company.setZipCode("update zipCode");
        company.setTwitter("update twitter");
        company.setLinkedIn("update linkedIn");
        company.setWebsite("update website");
        company.setMethodCommunication("update methodCommunication");
        company.setEmail("update email");
        company.setPhone("update phone");
        company.setCategoryTier(30);
        company.setDateJoined("update dateJoined");
        company.setReasonJoining("update reasonJoining");
        company.setFeatureImage(new FeatureImage("feature_image_thumb.jpg", "feature_image_large.jpg"));
        company.setLogoImage("update logoImage");
        company.setFollow(true);
        company.setFavoritesCount(1002);
        company.setIsOwner(false);
        company.setOwner(ownerName);

        ObjectMapper mapper = new ObjectMapper();
        String companyJSONString = null;
        try {
            companyJSONString = mapper.writeValueAsString(company);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return companyJSONString;
    }

    @Test
    public void testAddAdminSuccess () {

        given().
                header("Content-type", "application/json").
                header("AJP_eppn", ownerEPPN).
                expect().statusCode(200).
                when().
                post(COMPANY_ADD_ADMIN_RESOURCE, Integer.toString(companyId), Integer.toString(memberUserId));

    }

    @Test
    public void testAddAdminNonMember () {

        given().
                header("Content-type", "application/json").
                header("AJP_eppn", ownerEPPN).
                expect().statusCode(HttpStatus.SC_FORBIDDEN).  // FORBIDDEN
                when().
                post(COMPANY_ADD_ADMIN_RESOURCE, Integer.toString(companyId), Integer.toString(nonmemberUserId));

    }

    @Test
    public void testAddMemberSuccess () {

        // Create a member user
        String unique2 = TestUserUtil.generateTime();
        String memberEPPN = "userEPPN" + unique2;
        String memberGivenName = "userGivenName" + unique2;
        String memberSurName= "userSurName" + unique2;
        String memberDisplayName = "userDisplayName" + unique2;
        String memberEmail = "userEmail" + unique2;

        int memberUserId = createUser(memberEPPN,memberGivenName, memberSurName, memberDisplayName, memberEmail);

        given().
                header("Content-type", "application/json").
                header("AJP_eppn", ownerEPPN).
                expect().statusCode(200).
                when().
                post(COMPANY_ADD_MEMBER_RESOURCE, Integer.toString(companyId), Integer.toString(memberUserId));

    }

    @Test
    public void testAddMemberFailNonAdmin () {

        // Create a member user
        String unique2 = TestUserUtil.generateTime();
        String memberEPPN = "userEPPN" + unique2;
        String memberGivenName = "userGivenName" + unique2;
        String memberSurName= "userSurName" + unique2;
        String memberDisplayName = "userDisplayName" + unique2;
        String memberEmail = "userEmail" + unique2;

        int memberUserId = createUser(memberEPPN,memberGivenName, memberSurName, memberDisplayName, memberEmail);

        given().
                header("Content-type", "application/json").
                header("AJP_eppn", nonmemberEPPN).
                expect().statusCode(HttpStatus.SC_FORBIDDEN).  // FORBIDDEN
                when().
                post(COMPANY_ADD_MEMBER_RESOURCE, Integer.toString(companyId), Integer.toString(memberUserId));

    }

    @Test 
    public void updateCompanyByAdmin () {
        String json = createUpdateCompanyFixture(ownerEPPN);
        given().
                body(json).
                header("AJP_eppn", ownerEPPN).
                header("Content-type", "application/json").
                expect().
                statusCode(200).
                when().
                patch(COMPANY_UPDATE_RESOURCE, companyId).
                then().
                body(matchesJsonSchemaInClasspath("Schemas/idSchema.json"));

        Company returnedCompany =
                given().
                        header("Content-type", "application/json").
                        header("AJP_eppn", ownerEPPN).
                        expect().
                        statusCode(200).  //HttpStatus.OK.value()
                        when().
                        get(COMPANY_GET_RESOURCE, companyId).
                        as(Company.class);

        assertTrue("Expected to retrieve company with id = " + companyId, returnedCompany != null);
        assertTrue("Expected division to contain string 'update'" + companyId, returnedCompany.getDivision().indexOf("update") >= 0);

    }

    @Test
    public void updateCompanyByNonAdmin () {
        String json = createUpdateCompanyFixture(ownerEPPN);
        given().
                body(json).
                header("AJP_eppn", nonmemberEPPN).
                header("Content-type", "application/json").
                expect().
                statusCode(HttpStatus.SC_FORBIDDEN).
                when().
                patch(COMPANY_UPDATE_RESOURCE, companyId);
    }
}

package org.dmc.services.web.controllers.legacy;

import org.dmc.services.data.entities.legacy.Company;
import org.dmc.services.data.entities.legacy.CompanyVideo;
import org.dmc.services.data.entities.legacy.Component;
import org.dmc.services.data.entities.legacy.Id;
import org.dmc.services.data.entities.legacy.Service;
import org.dmc.services.data.entities.legacy.User;
import org.dmc.services.data.entities.legacy.unimplemented.CompanyHistory;
import org.dmc.services.data.entities.legacy.unimplemented.CompanyImage;
import org.dmc.services.data.entities.legacy.unimplemented.CompanyReview;
import org.dmc.services.data.entities.legacy.unimplemented.CompanySkillImage;
import org.dmc.services.data.entities.legacy.unimplemented.InlineResponse201;
import org.dmc.services.data.entities.legacy.unimplemented.ServiceFeatured;
import org.dmc.services.data.repositories.legacy.CompanyDao;
import org.dmc.services.data.repositories.legacy.CompanySkillDao;
import org.dmc.services.data.repositories.legacy.CompanyVideoDao;
import org.dmc.services.logging.ServiceLogger;
import org.dmc.services.web.exceptions.DMCServiceException;
import org.dmc.services.web.exceptions.ErrorMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.http.HTTPException;

@RestController
public class CompanyController {

    private final String logTag = CompanyController.class.getName();

    private CompanyDao companyDao = new CompanyDao();
    private CompanySkillDao skillDao = new CompanySkillDao();
    private CompanyVideoDao videoDao = new CompanyVideoDao();

    /**
     Return a list of companies
     **/
    @RequestMapping(value = "/companies", method = RequestMethod.GET, produces = { "application/json"})
    public ResponseEntity getCompanies(@RequestHeader(value="AJP_eppn", defaultValue="testUser") String userEPPN) {
    	ServiceLogger.log(logTag, "getCompanys, userEPPN: " + userEPPN);
    	int statusCode = HttpStatus.OK.value();
    	ArrayList<Company> companies = null;
    	
    	try {
            companies = companyDao.getCompanies(userEPPN);
            return new ResponseEntity<ArrayList<Company>>(companies, HttpStatus.valueOf(statusCode));
    	} catch (HTTPException e) {
    		ServiceLogger.log(logTag, e.getMessage());
    		statusCode = e.getStatusCode();
    		ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
    		return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
    	}
    }
    
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET, produces = { "application/json"})
    public ResponseEntity getCompany(@PathVariable("id") int id, @RequestHeader(value="AJP_eppn", defaultValue="testUser") String userEPPN) {
    	ServiceLogger.log(logTag, "getCompany, id: " + id);
    	int statusCode = HttpStatus.OK.value();
    	Company company = null; 
    	
    	try {
    	  company = companyDao.getCompany(id, userEPPN);
    	  return new ResponseEntity<Company>(company, HttpStatus.valueOf(statusCode));
    	} catch (HTTPException e) {
    		ServiceLogger.log(logTag, e.getMessage());
    		statusCode = e.getStatusCode();
    		ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
    		return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
    	}
    }
    
    
    
    @RequestMapping(value = "/companies/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Id createCompany(@RequestBody Company company, @RequestHeader(value="AJP_eppn", defaultValue="testUser") String userEPPN) {
    	ServiceLogger.log(logTag, "CreateCompany, Payload: " + company);
    	return companyDao.createCompany(company, userEPPN);
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PATCH, produces = { "application/json" })
    public ResponseEntity updateCompany(@PathVariable("id") int id,
    					@RequestBody Company company,
    					@RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
    	
    	ServiceLogger.log(logTag, "UpdateCompany, ID: " + id + " Payload: " + company);

        int statusCode = HttpStatus.OK.value();
        Id retrievedId = null;
        
        try {
            retrievedId = companyDao.updateCompany(id, company, userEPPN);
            return new ResponseEntity<Id>(retrievedId, HttpStatus.valueOf(statusCode));
        } catch(HTTPException e) {
    		statusCode = e.getStatusCode();
    		ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
    		return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
        }  
    }
    
    @RequestMapping(value = "/companies/{id}/delete", method = RequestMethod.DELETE)
    public Id deleteCompany(@PathVariable("id") int id, @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
    	ServiceLogger.log(logTag, "deleteCompany, id: " + id);
    	return  companyDao.deleteCompany(id, userEPPN);
    }

    /**
	 * Retrieve company videos
	 * 
	 * @param id
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/companies/{id}/company_videos", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity getCompanyVideos(@PathVariable("id") int id, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "getCompanyVideos, userEPPN: " + userEPPN);
		int statusCode = HttpStatus.OK.value();
		ArrayList<CompanyVideo> videos = null;

		try {
			videos = videoDao.getCompanyVideos(id, userEPPN);
			return new ResponseEntity<ArrayList<CompanyVideo>>(videos, HttpStatus.valueOf(statusCode));
		} catch (HTTPException e) {
			ServiceLogger.log(logTag, e.getMessage());
			statusCode = e.getStatusCode();
			ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
		}
	}
	
    /**
	 * Create a company video
	 * @param video
	 * @param userEPPN
	 * @return
	 */
	
	@RequestMapping(value = "/company_videos", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity createCompanyVideo(@RequestBody CompanyVideo video, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "createCompanyVideo");
		int statusCode = HttpStatus.OK.value();
		Id id = null;

		try {
			id = videoDao.createCompanyVideo(video, userEPPN);
			return new ResponseEntity<Id>(id, HttpStatus.valueOf(statusCode));
		} catch (HTTPException e) {
			statusCode = e.getStatusCode();
			ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
		}
	}
	
	/** 
	 * Delete a company video
	 * @param id
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/company_videos/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public ResponseEntity deleteCompanyVideos(@PathVariable("id") int videoId, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "deleteCompanyVideos, userEPPN: " + userEPPN);
		int statusCode = HttpStatus.OK.value();
		Id id = null;

		try {
			id = videoDao.deleteCompanyVideo(-1, videoId, userEPPN);
			return new ResponseEntity<Id>(id, HttpStatus.valueOf(statusCode));
		} catch (HTTPException e) {
			ServiceLogger.log(logTag, e.getMessage());
			statusCode = e.getStatusCode();
			ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
		}
	}
	
	/**
	 * Update Company Video
	 * @param id
	 * @param video
	 * @param userEPPN
	 * @return
	 */
    @RequestMapping(value = "/company_videos/{id}", method = RequestMethod.PATCH, produces = { "application/json" })
    public ResponseEntity updateCompanyVideo(@PathVariable("id") int id,
                            @RequestBody CompanyVideo video,
                            @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
    	ServiceLogger.log(logTag, "updateCompanyVideo, video: " + video.toString());

        int httpStatusCode = HttpStatus.OK.value();
        Id updatedId = null;
        
        try {
            updatedId = videoDao.updateCompanyVideo(id, video, userEPPN);
        } catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		} 
        
        return new ResponseEntity<Id>(updatedId, HttpStatus.valueOf(httpStatusCode));        
    }

    /**
     * Add an administrator for a company
     * @param id
     * @param userId
     * @param userEPPN
     * @return id of the organization_admin entry or -9999
     */
    @RequestMapping(value = "/companies/{id}/admin/{userId}", method = RequestMethod.POST)
    public ResponseEntity addCompanyAdministrator (@PathVariable("id") int id, @PathVariable("userId") int userId, @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
	ServiceLogger.log(logTag, "addCompanyAdministrator, id: " + id + ", userId: " + userId);
	
	int statusCode = HttpStatus.OK.value();
	Id retrievedId = null;
	
	try {
	    retrievedId = companyDao.addAdministrator(id, userId, userEPPN);
	    return new ResponseEntity<Id>(retrievedId, HttpStatus.valueOf(statusCode));
	} catch (HTTPException e) {
	    statusCode = e.getStatusCode();
	    ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
	    return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
	}
    }

    /**
     * Add an member for a company
     * @param id
     * @param userId
     * @param userEPPN
     * @return id of the organization_user entry or -9999
     */
    @RequestMapping(value = "/companies/{id}/member/{userId}", method = RequestMethod.POST)
    public ResponseEntity addCompanyMember (@PathVariable("id") int id, @PathVariable("userId") int userId, @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
	ServiceLogger.log(logTag, "addCompanyMember, id: " + id + ", userId: " + userId);
	
	int statusCode = HttpStatus.OK.value();
	Id retrievedId = null;
	
	try {
	    retrievedId = companyDao.addMember(id, userId, userEPPN);
	    return new ResponseEntity<Id>(retrievedId, HttpStatus.valueOf(statusCode));
	} catch (HTTPException e) {
	    statusCode = e.getStatusCode();
	    ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
	    return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
	}
    }

	// /companies/{companyID}/company_members
	@RequestMapping(value = "/companies/{companyID}/company_members", method = RequestMethod.GET)
	public ResponseEntity getCompanyMembers (@PathVariable("companyID") int companyID, @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
		ServiceLogger.log(logTag, "getCompanyMembers, companyID: " + companyID);
		int statusCode = HttpStatus.OK.value();

		try {
			List<User> members = companyDao.getCompanyMembers(companyID, userEPPN);
			return new ResponseEntity<List<User>>(members, HttpStatus.valueOf(statusCode));
		} catch (HTTPException e) {
			statusCode = e.getStatusCode();
			ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(e.getMessage()).build();
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
		}
	}
	
	
	 @RequestMapping(value = "/companies/{companyID}/company_images", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<CompanyImage>> companiesCompanyIDCompanyImagesGet(
			@PathVariable("companyID") String companyID){
			      // do some magic!
			      return new ResponseEntity<List<CompanyImage>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 @RequestMapping(value = "/company_images/{imageID}", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.PATCH)
			  public ResponseEntity<CompanyImage> companyImagesImageIDPatch(
			@PathVariable("imageID") String imageID,
			@RequestBody CompanyImage image){
			      // do some magic!
			      return new ResponseEntity<CompanyImage>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 
	 @RequestMapping(value = "/companies/{companyID}/company_history", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<CompanyHistory>> companiesCompanyIDCompanyHistoryGet(
			@PathVariable("companyID") String companyID){
			      // do some magic!
			      return new ResponseEntity<List<CompanyHistory>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 @RequestMapping(value = "/companies/{companyID}/company_skill_images", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<CompanySkillImage>> companiesCompanyIDCompanySkillImagesGet(
			@PathVariable("companyID") String companyID){
			      // do some magic!
			      return new ResponseEntity<List<CompanySkillImage>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 @RequestMapping(value = "/company_skill_images/{imageID}", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.PATCH)
			  public ResponseEntity<CompanySkillImage> companySkillImagesImageIDPatch(
			@PathVariable("imageID") String imageID,
			@RequestBody CompanySkillImage image){
			      // do some magic!
			      return new ResponseEntity<CompanySkillImage>(HttpStatus.NOT_IMPLEMENTED);
			  }
	

	 @RequestMapping(value = "/companies/{companyID}/company_reviews", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<CompanyReview>> companiesCompanyIDCompanyReviewsGet(
			@PathVariable("companyID") String companyID,
			@RequestParam(value = "reviewId", required = true) String reviewId,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "rating", required = false) Integer rating,
			@RequestParam(value = "status", required = false) Boolean status){
			      // do some magic!
			      return new ResponseEntity<List<CompanyReview>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 @RequestMapping(value = "/company_reviews", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.POST)
			  public ResponseEntity<CompanyReview> companyReviewsPost(
					  @RequestBody CompanyReview body
			){
			      // do some magic!
			      return new ResponseEntity<CompanyReview>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 @RequestMapping(value = "/companies/{companyID}/company_services", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<Service>> companiesCompanyIDCompanyServicesGet(
			 @PathVariable("companyID") String companyID,
			 @RequestParam(value = "limit", required = false) Integer limit,
			 @RequestParam(value = "order", required = false) String order,
			 @RequestParam(value = "start", required = false) Integer start,
			 @RequestParam(value = "sort", required = false) String sort,
			 @RequestParam(value = "titleLike", required = false) String titleLike,
			 @RequestParam(value = "serviceType", required = false) String serviceType,
			 @RequestParam(value = "authors", required = false) List<Integer> authors,
			 @RequestParam(value = "ratings", required = false) List<String> ratings,
			 @RequestParam(value = "favorites", required = false) String favorites,
			 @RequestParam(value = "dates", required = false) List<String> dates)
			 {
			      // do some magic!
			      return new ResponseEntity<List<Service>>(HttpStatus.NOT_IMPLEMENTED);
			  }

	 
	 @RequestMapping(value = "/companies/{companyID}/company_components", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<Component>> companiesCompanyIDCompanyComponentsGet(
			@PathVariable("companyID") String companyID,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "titleLike", required = false) String titleLike,
			@RequestParam(value = "serviceType", required = false) String serviceType,
			@RequestParam(value = "authors", required = false) List<Integer> authors,
			@RequestParam(value = "ratings", required = false) List<String> ratings,
			@RequestParam(value = "favorites", required = false) String favorites,
			@RequestParam(value = "dates", required = false) List<String> dates){
			      // do some magic!
			      return new ResponseEntity<List<Component>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 @RequestMapping(value = "/companies/{companyID}/company_featured", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<ServiceFeatured>> companiesCompanyIDCompanyFeaturedGet(
			@PathVariable("companyID") String companyID,
			@RequestParam(value = "order", required = true) String order,
			@RequestParam(value = "sort", required = true) String sort){
			      // do some magic!
			      return new ResponseEntity<List<ServiceFeatured>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 @RequestMapping(value = "/companies/{companyID}/new", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.GET)
			  public ResponseEntity<List<Service>> companiesCompanyIDNewGet(
			@PathVariable("companyID") String companyID,
			@RequestParam(value = "order", required = true) String order,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "limit", required = true) String limit){
			      // do some magic!
			      return new ResponseEntity<List<Service>>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 @RequestMapping(value = "/company/follow", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.POST)
			  public ResponseEntity<InlineResponse201> companyFollowPost(
				@RequestParam(value = "accountId", required = true) Integer accountId,
			    @RequestParam(value = "companyId", required = true) Integer companyId){
			      // do some magic!
			      return new ResponseEntity<InlineResponse201>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
	 
	 @RequestMapping(value = "/company/unfollow/{followed_companiId}", 
			    produces = { "application/json", "text/html" }, 
			    method = RequestMethod.DELETE)
			  public ResponseEntity<Void> companyUnfollowFollowedCompaniIdDelete(
			@PathVariable("followedCompaniId") Integer followedCompaniId){
			      // do some magic!
			      return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
			  }
	 
}
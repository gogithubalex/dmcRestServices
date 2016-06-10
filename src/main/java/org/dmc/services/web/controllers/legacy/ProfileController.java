package org.dmc.services.web.controllers.legacy;

import org.dmc.services.data.entities.legacy.Id;
import org.dmc.services.data.entities.legacy.Profile;
import org.dmc.services.data.entities.legacy.unimplemented.FollowingMemeber;
import org.dmc.services.data.entities.legacy.unimplemented.ProfileHistory;
import org.dmc.services.data.entities.legacy.unimplemented.ProfileReview;
import org.dmc.services.data.repositories.legacy.ProfileDao;
import org.dmc.services.logging.ServiceLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import javax.xml.ws.http.HTTPException;

@RestController
public class ProfileController {

	private final String logTag = ProfileController.class.getName();
	
    private ProfileDao profileDao = new ProfileDao(); 
	
    @RequestMapping(value = "/profiles/{id}", method = RequestMethod.GET)
    public ResponseEntity<Profile> getProfile(@PathVariable("id") int id) {
    	ServiceLogger.log(logTag, "getProfile, id: " + id);
        
        int httpStatusCode = HttpStatus.OK.value();
        Profile profile = null;
        
        try{
            profile = profileDao.getProfile(id);
        } catch(HTTPException httpException) {
            httpStatusCode = httpException.getStatusCode();
        }
        
        return new ResponseEntity<Profile>(profile, HttpStatus.valueOf(httpStatusCode));
    }
    
    @RequestMapping(value = "/profiles", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Id> createProfile(@RequestBody Profile profile,
                                            @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
        ServiceLogger.log(logTag, "createProfile, profile: " + profile.toString());
        
        int httpStatusCode = HttpStatus.OK.value();
        Id retrivedId = null;
        
        try{
            retrivedId = profileDao.createProfile(profile, userEPPN);
        } catch(HTTPException httpException) {
            httpStatusCode = httpException.getStatusCode();
        }
        
        return new ResponseEntity<Id>(retrivedId, HttpStatus.valueOf(httpStatusCode));
    }
    
    
    @RequestMapping(value = "/profiles/{id}", method = RequestMethod.PATCH, produces = { "application/json" })
    public ResponseEntity<Id> updateProfile(@PathVariable("id") int id,
                            @RequestBody Profile profile,
                            @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
    	ServiceLogger.log(logTag, "updateProfile, profile: " + profile.toString());
        
        int httpStatusCode = HttpStatus.OK.value();
        Id retrivedId = null;
        
        try{
            retrivedId = profileDao.updateProfile(id, profile, userEPPN);
        } catch(HTTPException httpException) {
            httpStatusCode = httpException.getStatusCode();
        }
        
        return new ResponseEntity<Id>(retrivedId, HttpStatus.valueOf(httpStatusCode));        
    }
    
    
    @RequestMapping(value = "/profiles/{id}/delete", method = RequestMethod.GET)
    public Id deleteProfile(@PathVariable("id") int id,
                            @RequestHeader(value="AJP_eppn", required=true) String userEPPN) {
    	ServiceLogger.log(logTag, "deleteProfile, id: " + id);
    	return profileDao.deleteProfile(id, userEPPN);
    }
    
 /////newly added methods
    @RequestMapping(value = "/profiles", 
      produces = { "application/json", "text/html" }, 
      method = RequestMethod.GET)
    public ResponseEntity<List<Profile>> profilesGet(
    		@RequestParam(value = "limit", required = false) Integer limit,
      @RequestParam(value = "order", required = false) String order,
      @RequestParam(value = "sort", required = false) String sort,
      @RequestParam(value = "id", required = false) List<String> id){
        // do some magic!
        return new ResponseEntity<List<Profile>>(HttpStatus.NOT_IMPLEMENTED);
    }

    
    
    @RequestMapping(value = "/profiles/{profileID}/profile_history", 
    	    produces = { "application/json", "text/html" }, 
    	    method = RequestMethod.GET)
    	  public ResponseEntity<List<ProfileHistory>> profilesProfileIDProfileHistoryGet(
    	@PathVariable("profileID") String profileID,
    	    @RequestParam(value = "section", required = false) String section,
    	   @RequestParam(value = "limit", required = false) Integer limit) {
    	      // do some magic!
    	      return new ResponseEntity<List<ProfileHistory>>(HttpStatus.NOT_IMPLEMENTED);
    	  }
    
    
    @RequestMapping(value = "/profiles/{profileID}/profile_reviews", 
    	    produces = { "application/json", "text/html" }, 
    	    method = RequestMethod.GET)
    	  public ResponseEntity<List<ProfileReview>> profilesProfileIDProfileReviewsGet(
    	@PathVariable("profileID") String profileID,
    	@RequestParam(value = "reviewId", required = true) String reviewId,
    	@RequestParam(value = "limit", required = false) Integer limit,
    	 @RequestParam(value = "order", required = false) String order,
    	 @RequestParam(value = "sort", required = false) String sort,
    	 @RequestParam(value = "rating", required = false) Integer rating,
    	@RequestParam(value = "status", required = false) Boolean status){
    	      // do some magic!
    	      return new ResponseEntity<List<ProfileReview>>(HttpStatus.NOT_IMPLEMENTED);
    	  }
    
    
    @RequestMapping(value = "/profiles/{profileId}/following_members", 
    	    produces = { "application/json", "text/html" }, 
    	    method = RequestMethod.GET)
    	  public ResponseEntity<List<FollowingMemeber>> profilesProfileIdFollowingMembersGet(
    	@PathVariable("profileId") String profileId,
    	 @RequestParam(value = "limit", required = false) Integer limit,
    	 @RequestParam(value = "order", required = false) String order,
    	@RequestParam(value = "sort", required = false) String sort){
    	      // do some magic!
    	      return new ResponseEntity<List<FollowingMemeber>>(HttpStatus.NOT_IMPLEMENTED);
    	  }
    
}
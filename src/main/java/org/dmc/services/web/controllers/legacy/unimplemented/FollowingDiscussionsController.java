package org.dmc.services.web.controllers.legacy.unimplemented;


import org.dmc.services.data.entities.legacy.IndividualDiscussion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping(value = "/following_discussions", produces = {APPLICATION_JSON_VALUE})
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-08T14:26:00.636Z")
public class FollowingDiscussionsController {
  

  @RequestMapping(value = "", 
    produces = { "application/json", "text/html" }, 
    method = RequestMethod.GET)
  public ResponseEntity<List<IndividualDiscussion>> followingDiscussionsGet(){
      // do some magic!
      return new ResponseEntity<List<IndividualDiscussion>>(HttpStatus.NOT_IMPLEMENTED);
  }

  

  
}

package org.dmc.services.web.controllers.legacy.unimplemented;

import org.dmc.services.data.entities.legacy.FaqCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping(value = "/faq_categories", produces = {APPLICATION_JSON_VALUE})
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-07T17:42:57.404Z")
public class FaqCategoriesController {
  
  @RequestMapping(value = "", 
    produces = { "application/json", "text/html" }, 
    method = RequestMethod.GET)
  public ResponseEntity<List<FaqCategory>> faqCategoriesGet(
	@RequestParam(value = "titleLike", required = false) String titleLike,
    @RequestParam(value = "limit", required = false) Integer limit,
    @RequestParam(value = "order", required = false) String order,
    @RequestParam(value = "sort", required = false) String sort){
      // do some magic!
      return new ResponseEntity<List<FaqCategory>>(HttpStatus.NOT_IMPLEMENTED);
  }

  

 
  @RequestMapping(value = "/{id}", 
    produces = { "application/json", "text/html" }, 
    method = RequestMethod.GET)
  public ResponseEntity<FaqCategory> faqCategoriesIdGet(
 @PathVariable("id") String id){
      // do some magic!
      return new ResponseEntity<FaqCategory>(HttpStatus.NOT_IMPLEMENTED);
  }

  

  
}

package org.dmc.services.web.controllers.legacy.unimplemented;


import java.math.BigDecimal;
import java.util.List;

import org.dmc.services.data.entities.legacy.PostServiceInputPosition;
import org.dmc.services.data.entities.legacy.ServiceInputPosition;
import org.dmc.services.data.entities.legacy.ServiceInputsPositions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping(value = "/input-positions", produces = {APPLICATION_JSON_VALUE})
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-08T14:26:00.636Z")
public class InputPositionsApi {
  
  @RequestMapping(value = "/{positionInputId}", 
    produces = { "application/json", "text/html" }, 
    method = RequestMethod.DELETE)
  public ResponseEntity<Void> inputPositionsPositionInputIdDelete(
@PathVariable("positionInputId") BigDecimal positionInputId){
      // do some magic!
      return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }

 

  @RequestMapping(value = "/{positionInputId}", 
    produces = { "application/json", "text/html" }, 
    method = RequestMethod.PATCH)
  public ResponseEntity<ServiceInputsPositions> inputPositionsPositionInputIdPatch(
@PathVariable("positionInputId") BigDecimal positionInputId,
     @RequestBody List<ServiceInputPosition> positions){
      // do some magic!
      return new ResponseEntity<ServiceInputsPositions>(HttpStatus.NOT_IMPLEMENTED);
  }

  

  @RequestMapping(value = "", 
    produces = { "application/json", "text/html" }, 
    method = RequestMethod.POST)
  public ResponseEntity<ServiceInputsPositions> inputPositionsPost(
@RequestBody List<PostServiceInputPosition> inputsPositions){
      // do some magic!
      return new ResponseEntity<ServiceInputsPositions>(HttpStatus.NOT_IMPLEMENTED);
  }

  

  
}

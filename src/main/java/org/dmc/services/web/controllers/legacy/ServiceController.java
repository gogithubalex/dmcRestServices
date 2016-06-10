package org.dmc.services.web.controllers.legacy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dmc.services.data.entities.legacy.ArraySpecifications;
import org.dmc.services.data.entities.legacy.GetDomeInterface;
import org.dmc.services.data.entities.legacy.Id;
import org.dmc.services.data.entities.legacy.ProjectJoinRequest;
import org.dmc.services.data.entities.legacy.Service;
import org.dmc.services.data.entities.legacy.ServiceImages;
import org.dmc.services.data.entities.legacy.ServiceSpecifications;
import org.dmc.services.data.entities.legacy.ServiceTag;
import org.dmc.services.data.entities.legacy.unimplemented.ServiceAuthor;
import org.dmc.services.data.entities.legacy.unimplemented.ServiceDocument;
import org.dmc.services.data.entities.legacy.unimplemented.ServiceHistory;
import org.dmc.services.data.entities.legacy.unimplemented.ServiceInputsPositions;
import org.dmc.services.data.entities.legacy.unimplemented.ServiceStats;
import org.dmc.services.data.repositories.legacy.DomeInterfacesDao;
import org.dmc.services.data.repositories.legacy.ServiceDao;
import org.dmc.services.data.repositories.legacy.ServiceImagesDao;
import org.dmc.services.data.repositories.legacy.ServiceSpecificationDao;
import org.dmc.services.data.repositories.legacy.ServiceTagsDao;
import org.dmc.services.logging.ServiceLogger;
import org.dmc.services.web.exceptions.DMCError;
import org.dmc.services.web.exceptions.DMCServiceException;
import org.dmc.services.web.exceptions.ErrorMessage;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

	private final String logTag = ServiceController.class.getName();

	private ServiceDao serviceDao = new ServiceDao();
	private ServiceImagesDao serviceImagesDao = new ServiceImagesDao();
	private ServiceTagsDao serviceTagsDao = new ServiceTagsDao();
	private ServiceSpecificationDao specificationDao = new ServiceSpecificationDao();

	@RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getService(@PathVariable("id") int id,
	        @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
		ServiceLogger.log(logTag, "getService, id: " + id);
		try {
		    return new ResponseEntity<Service>(serviceDao.getService(id, userEPPN), HttpStatus.OK);
		} catch (DMCServiceException e) {
		    ServiceLogger.logException(logTag, e);
		    return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		}
	}

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public ResponseEntity<?> getServiceList() {
        try {
            ServiceLogger.log(logTag, "In getServiceList");
            return new ResponseEntity<ArrayList<Service>>(serviceDao.getServiceList(), HttpStatus.OK);
        } catch (DMCServiceException e) {
            ServiceLogger.logException(logTag, e);
            return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
        }
	}

	@RequestMapping(value = "/projects/{projectId}/services", method = RequestMethod.GET)
	public ResponseEntity<?> getServiceList(@PathVariable("projectId") int projectId) {
        try {
            ServiceLogger.log(logTag, "In getServiceList, projectId = " + projectId);
            return new ResponseEntity<ArrayList<Service>>(serviceDao.getServiceList(projectId), HttpStatus.OK);
        } catch (DMCServiceException e) {
            ServiceLogger.logException(logTag, e);
            return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
        }
	}

	@RequestMapping(value = "/components/{componentId}/services", method = RequestMethod.GET)
	public ResponseEntity<?> getServiceByComponentList(@PathVariable("componentId") int componentId) {
        try {
            ServiceLogger.log(logTag, "In getServiceByComponentList, componentId = " + componentId);
            return new ResponseEntity<ArrayList<Service>>(serviceDao.getServiceByComponentList(componentId), HttpStatus.OK);
        } catch (DMCServiceException e) {
            ServiceLogger.logException(logTag, e);
            return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
        }
	}

	@RequestMapping(value = "/services", produces = { "application/json", "text/html" }, method = RequestMethod.POST)
	public ResponseEntity<?> postService(@RequestBody Service body,
	        @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
        try {
            ServiceLogger.log(logTag, "In createService");
            return new ResponseEntity<Service>(serviceDao.createService(body, userEPPN), HttpStatus.OK);
        } catch (DMCServiceException e) {
            ServiceLogger.logException(logTag, e);
            return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
        }
	}

	@RequestMapping(value = "/services/{serviceID}", produces = { "application/json",
			"text/html" }, method = RequestMethod.PATCH)
	public ResponseEntity<?> servicesServiceIDPatch(@PathVariable("serviceID") String serviceID,
			@RequestBody Service service,
			@RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
        try {
            ServiceLogger.log(logTag, "In patchService, serviceID = " + serviceID);
            return new ResponseEntity<Service>(serviceDao.patchService(serviceID, service, userEPPN), HttpStatus.OK);
        } catch (DMCServiceException e) {
            ServiceLogger.logException(logTag, e);
            return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
        }
	}

	@RequestMapping(value = "/services/{serviceID}/service_authors", produces = { "application/json",
			"text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<ServiceAuthor>> servicesServiceIDServiceAuthorsGet(
			@PathVariable("serviceID") String serviceID) {
		// do some magic!
		return new ResponseEntity<List<ServiceAuthor>>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/services/{serviceID}/service_documents", produces = { "application/json",
			"text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<ServiceDocument>> servicesServiceIDServiceDocumentsGet(
			@PathVariable("serviceID") String serviceID,
			@RequestParam(value = "serviceDocumentId", required = false) String serviceDocumentId,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "sort", required = false) String sort) {
		// do some magic!
		return new ResponseEntity<List<ServiceDocument>>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/services/{serviceID}/service_history", produces = { "application/json",
			"text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<ServiceHistory>> servicesServiceIDServiceHistoryGet(
			@PathVariable("serviceID") String serviceID,
			@RequestParam(value = "period", required = false) String period,
			@RequestParam(value = "section", required = false) String section) {
		// do some magic!
		return new ResponseEntity<List<ServiceHistory>>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/services/{serviceID}/service_images", produces = { "application/json", "text/html" }, method = RequestMethod.GET)
	public ResponseEntity getServiceImages(@PathVariable("serviceID") int serviceID, @RequestHeader(value="AJP_eppn", defaultValue="testUser") String userEPPN) {
		ServiceLogger.log(logTag, "In GET ServiceImage by User " + userEPPN);
			int statusCode = HttpStatus.OK.value();
			ArrayList<ServiceImages> imageList = null;
			try{
				imageList = serviceImagesDao.getServiceImages(serviceID);
			} catch(DMCServiceException e) {
                return new ResponseEntity<String>(e.getMessage(), e.getHttpStatusCode());
	        }
			return new ResponseEntity<ArrayList<ServiceImages>>(imageList, HttpStatus.valueOf(statusCode));

	}

	@RequestMapping(value = "/services/{serviceID}/service_tags", produces = { "application/json","text/html" }, method = RequestMethod.GET)
	public ResponseEntity servicesServiceIDServiceTagsGet(@PathVariable("serviceID") String serviceID,
		@RequestParam(value = "limit", required = false) Integer limit,
		@RequestParam(value = "order", required = false) String order,
		@RequestParam(value = "sort", required = false) String sort) {
	// do some magic!
	int statusCode = HttpStatus.OK.value();
	String userEPPN = null;
	List<ServiceTag> tags = null;
	try {
		tags = serviceTagsDao.getServiceListByServiceId(Integer.parseInt(serviceID), userEPPN);
	} catch (Exception ex) {
		statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ErrorMessage error = new ErrorMessage.ErrorMessageBuilder(ex.getMessage()).build();
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.valueOf(statusCode));
	}

	return new ResponseEntity<List<ServiceTag>>(tags, HttpStatus.valueOf(statusCode));

	}



	@RequestMapping(value = "/services/{serviceID}/services_statistic", produces = { "application/json",
			"text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<ServiceStats>> servicesServiceIDServicesStatisticGet(
			@PathVariable("serviceID") String serviceID) {
		// do some magic!
		return new ResponseEntity<List<ServiceStats>>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/services/{serviceId}/dome-interfaces", produces = { "application/json",
			"text/html" }, method = RequestMethod.GET)
	public ResponseEntity servicesServiceIdDomeInterfacesGet(
			@PathVariable("serviceId") BigDecimal serviceId,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "sort", required = false) String sort) {
		
		DomeInterfacesDao domeInterfacesDao = new DomeInterfacesDao();
		
		try {
			ServiceLogger.log(logTag, "In getServiceIdDomeInterfaces, serviceId = " + serviceId);
			return new ResponseEntity<List<GetDomeInterface>>(domeInterfacesDao.getDomeInterfacesFromServiceId(serviceId, limit, order, sort), HttpStatus.OK);
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		}
		
	}

	@RequestMapping(value = "/services/{serviceId}/input-positions", produces = { "application/json",
			"text/html" }, method = RequestMethod.GET)
	public ResponseEntity<List<ServiceInputsPositions>> servicesServiceIdInputPositionsGet(
			@PathVariable("serviceId") BigDecimal serviceId) {
		// do some magic!
		return new ResponseEntity<List<ServiceInputsPositions>>(HttpStatus.NOT_IMPLEMENTED);
	}

    /**
	 * Create Service Specification
	 * @param video
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/service/{serviceID}/specifications", method = RequestMethod.POST, produces = { "application/json", "text/html" })
	public ResponseEntity<?> createServiceSpecification(@PathVariable("serviceID") String serviceIdText, 
	        @RequestBody ServiceSpecifications spec, 
	        @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "createServiceSpecification");
		ArrayList<Integer> ids = null;
		ArrayList<ServiceSpecifications> specs = new ArrayList<ServiceSpecifications>();
		specs.add(spec);

		try {
		    int serviceId = -1;
		    try {
		        serviceId = Integer.parseInt(serviceIdText);
		    } catch (NumberFormatException e) {
		        throw new DMCServiceException(DMCError.ServiceInterfaceNotMatch, "unable to parse " + serviceIdText + " as an integer");
		    }
			ids = specificationDao.createServiceSpecifications(serviceId, specs, userEPPN);
			return new ResponseEntity<ArrayList<Integer>>(ids, HttpStatus.valueOf(HttpStatus.OK.value()));
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		} 
	}
	
    /**
	 * Create Service Specifications
	 * @param video
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/array_specifications", method = RequestMethod.POST, produces = { "application/json", "text/html" })
	public ResponseEntity<?> createServiceSpecifications(@RequestBody ArrayList<ServiceSpecifications> specs, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "createServiceSpecifications, Specs:" + specs);
		ArrayList<Integer> ids = null;

		try {
			ids = specificationDao.createServiceSpecifications(-1, specs, userEPPN);
			return new ResponseEntity<ArrayList<Integer>>(ids, HttpStatus.valueOf(HttpStatus.OK.value()));
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		} 
	}
	
    /**
	 * Update Service Specification
	 * @param video
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/specifications/{specificationId}", method = RequestMethod.PATCH, produces = { "application/json", "text/html" })
	@ResponseBody
	public ResponseEntity updateServiceSpecification(@PathVariable("specificationId") int specId, @RequestBody ServiceSpecifications spec, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "updateServiceSpecification");
		Id id = null;

		try {
			id = specificationDao.updateServiceSpecification(specId, spec, userEPPN);
			return new ResponseEntity<Id>(id, HttpStatus.valueOf(HttpStatus.OK.value()));
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		} 
	}
	
    /**
	 * Retrieve service Specifications for A Services
	 * @param id
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/services/{serviceId}/specifications", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> getServiceSpecifications(@PathVariable("id") int id, @RequestHeader(value = "AJP_eppn", required = true) String userEPPN) {
		
		ServiceLogger.log(logTag, "getServiceSpecifications, userEPPN: " + userEPPN);
		ArrayList<ServiceSpecifications> specs = null;

		try {
			specs = specificationDao.getServiceSpecifications(-1, -1, null, null, userEPPN);
			return new ResponseEntity<ArrayList<ServiceSpecifications>>(specs, HttpStatus.valueOf(HttpStatus.OK.value()));
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		} 
	}
	
    /**
	 * Retrieve service Specifications
	 * @param id
	 * @param userEPPN
	 * @return
	 */
	@RequestMapping(value = "/array_specifications", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> getServiceSpecifications(@RequestHeader(value = "AJP_eppn", required = true) String userEPPN,
				@RequestParam(value="_limit", defaultValue="1000", required = false) int limit,
				@RequestParam(value="_order", defaultValue="DESC", required = false) String order,
				@RequestParam(value="_sort", defaultValue="id", required = false) String sort) {
		
		ServiceLogger.log(logTag, "getServiceSpecifications, userEPPN: " + userEPPN);
		ArrayList<ServiceSpecifications> specs = null;

		try {
			specs = specificationDao.getServiceSpecifications(-1, limit, order, sort, userEPPN);
			return new ResponseEntity<ArrayList<ServiceSpecifications>>(specs, HttpStatus.valueOf(HttpStatus.OK.value()));
		} catch (DMCServiceException e) {
			ServiceLogger.logException(logTag, e);
			return new ResponseEntity<String>(e.getErrorMessage(), e.getHttpStatusCode());
		} 
	}

}

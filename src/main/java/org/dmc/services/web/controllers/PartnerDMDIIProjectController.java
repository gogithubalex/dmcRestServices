package org.dmc.services.web.controllers;

import java.util.List;

import org.dmc.services.data.models.DMDIIProjectModel;
import org.dmc.services.logging.ServiceLogger;
import org.dmc.services.service.DMDIIProjectService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartnerDMDIIProjectController {
	
	private final String logTag = PartnerDMDIIProjectController.class.getName();
	
	DMDIIProjectService dmdiiProjectService;

	@RequestMapping(value = "/partnerdmdiiprojects/{partnerID}", method = RequestMethod.GET)
	public List<DMDIIProjectModel> getAllPartnerDmdiiProjects(@PathVariable("partnerID") Integer partnerID) {
		ServiceLogger.log(logTag, "In getAllPartnerDmdiiProjects as partner " + partnerID);
		
		return dmdiiProjectService.findDmdiiProjectsByPrimeOrganizationId(partnerID);
	}
}

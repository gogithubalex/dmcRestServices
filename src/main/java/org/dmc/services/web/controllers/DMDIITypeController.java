package org.dmc.services.web.controllers;

import java.util.List;

import javax.inject.Inject;

import org.dmc.services.data.entities.DMDIIType;
import org.dmc.services.service.DMDIITypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMDIITypeController {

	@Inject
	private DMDIITypeService dmdiiTypeService;
	
	@RequestMapping(value = "/dmdiiType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DMDIIType> findAll() {
		return dmdiiTypeService.findAll();
	}
	
}

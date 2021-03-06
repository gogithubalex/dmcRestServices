package org.dmc.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dmc.services.data.models.DMDIIProjectModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMDIIProjectController {
	
	private final String logTag = DMDIIProjectController.class.getName();
	
	@Inject
	private DMDIIProjectService dmdiiProjectService;

	@RequestMapping(value = "/dmdiiprojects/member", params = {"page", "pageSize"}, method = RequestMethod.GET)
	public List<DMDIIProjectModel> getDmdiiProjectsByDMDIIMemberId(@RequestParam("dmdiiMemberId") Integer dmdiiMemberId,
																		@RequestParam("page") Integer page,
																		@RequestParam("pageSize") Integer pageSize) {
		ServiceLogger.log(logTag, "In getDmdiiProjectsByDMDIIMemberId as member " + dmdiiMemberId);
		
		return dmdiiProjectService.findDmdiiProjectsByPrimeOrganizationId(dmdiiMemberId, page, pageSize);
	}
	
	@RequestMapping(value = "dmdiiProject/{id}", method = RequestMethod.GET)
	public @ResponseBody DMDIIProjectModel getDMDIIProject(@PathVariable Integer id) {
		return dmdiiProjectService.findOne(id);
	}
	
	@RequestMapping(value = "/dmdiiprojects/awardDate", params = {"page", "pageSize"}, method = RequestMethod.GET)
	public List<DMDIIProjectModel> getDMDIIProjectsByAwardedDate(@RequestParam("awardedDate") Date awardedDate,
																	@RequestParam("page") Integer page,
																	@RequestParam("pageSize") Integer pageSize) {
		ServiceLogger.log(logTag, "In getDMDIIProjectsByStartDate: " + awardedDate);
		
		return dmdiiProjectService.findDMDIIProjectsByAwardedDate(awardedDate, page, pageSize);
	}
	
	@RequestMapping(value = "/dmdiiprojects/status", params = {"page", "pageSize"}, method = RequestMethod.GET)
	public List<DMDIIProjectModel> getDMDIIProjectsByStatusId(@RequestParam("statusId") Integer statusId,
																		@RequestParam("page") Integer page,
																		@RequestParam("pageSize") Integer pageSize) {
		ServiceLogger.log(logTag, "In getDMDIIProjectsByStatusId: " + statusId);
		
		return dmdiiProjectService.findDMDIIProjectsByStatusId(statusId, page, pageSize);
	}
	
	@RequestMapping(value = "/dmdiiprojects", params = {"page", "pageSize"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DMDIIProjectModel> getDMDIIProjects(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
		ServiceLogger.log(logTag, "In getAllDMDIIProjects");
		
		return dmdiiProjectService.findPage(page, pageSize);
	}
	
	@RequestMapping(value = "/dmdiiprojects/search", params = {"title", "page", "pageSize"}, method = RequestMethod.GET)
	public List<DMDIIProjectModel> getDmdiiProjectsByTitle(@RequestParam("title") String title,
															@RequestParam("page") Integer page,
															@RequestParam("pageSize") Integer pageSize) {
		ServiceLogger.log(logTag, "In getDmdiiProjectsByTitle: " + title);
		
		return dmdiiProjectService.findByTitle(title, page, pageSize);
	}
}

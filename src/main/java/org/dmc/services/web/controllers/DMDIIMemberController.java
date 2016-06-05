package org.dmc.services.web.controllers;

import java.util.List;

import javax.inject.Inject;

import org.dmc.services.data.entities.DMDIIMember;
import org.dmc.services.service.DMDIIMemberService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DMDIIMemberController {
	
	@Inject
	private DMDIIMemberService dmdiiMemberService;
	
	@RequestMapping(value = "/dmdiiMember", params = {"page", "pageSize"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DMDIIMember> getPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
		return dmdiiMemberService.findPage(page, pageSize);
	}
	
	@RequestMapping(value = "/dmdiiMember/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DMDIIMember getMember(@PathVariable Integer id) {
		return dmdiiMemberService.findOne(id);
	}
	
	@RequestMapping(value = "/dmdiiMember/type/{typeId}", params = {"page", "pageSize"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<DMDIIMember> getMemberByType(@PathVariable Integer typeId, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
		return dmdiiMemberService.findByTypeId(typeId, page, pageSize);
	}

}

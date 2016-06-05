package org.dmc.services.service;

import java.util.List;

import javax.inject.Inject;

import org.dmc.services.data.entities.DMDIIMember;
import org.dmc.services.data.repositories.DMDIIMemberDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DMDIIMemberService {

	@Inject
	private DMDIIMemberDao dmdiiMemberDao;
	
	public List<DMDIIMember> findPage(Integer pageNumber, Integer pageSize) {
		Page<DMDIIMember> page = dmdiiMemberDao.findAll(new PageRequest(pageNumber, pageSize));
		return page.getContent();
	}
	
	public DMDIIMember findOne(Integer id) {
		return dmdiiMemberDao.findOne(id);
	}
	
	public List<DMDIIMember> findByTypeId(Integer typeId, Integer pageNumber, Integer pageSize) {
		Page<DMDIIMember> page = dmdiiMemberDao.findByDmdiiTypeId(new PageRequest(pageNumber, pageSize), typeId);
		return page.getContent();
	}
	
}

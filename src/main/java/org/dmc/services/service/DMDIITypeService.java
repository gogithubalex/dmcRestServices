package org.dmc.services.service;

import java.util.List;

import javax.inject.Inject;

import org.dmc.services.data.entities.DMDIIType;
import org.dmc.services.data.repositories.DMDIITypeDao;
import org.springframework.stereotype.Service;

@Service
public class DMDIITypeService {

	@Inject
	private DMDIITypeDao dmdiiTypeDao;
	
	public List<DMDIIType> findAll() {
		return dmdiiTypeDao.findAll();
	}
	
}

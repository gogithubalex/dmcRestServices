package org.dmc.services.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.dmc.services.data.entities.DMDIIProject;
import org.dmc.services.data.mappers.Mapper;
import org.dmc.services.data.mappers.MapperFactory;
import org.dmc.services.data.models.DMDIIProjectModel;
import org.dmc.services.data.repositories.DMDIIProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class PartnerDMDIIProjectService {
	
	@Inject
	private DMDIIProjectRepository dmdiiProjectRepository;
	
	@Inject
	private MapperFactory mapperFactory;
	
	private Mapper<DMDIIProject, DMDIIProjectModel> mapper;
	
	@PostConstruct
	private void postConstruct() {
		this.mapper = mapperFactory.mapperFor(DMDIIProject.class, DMDIIProjectModel.class);
	}
	
	public List<DMDIIProjectModel> findDmdiiProjectsByPartnerIdAndIsActive(Integer partnerId) {
		List<DMDIIProject> dmdiiEntities = Collections.emptyList();
		
		if(partnerId != null) {
			dmdiiEntities = dmdiiProjectRepository.findByPrimeOrganizationId(partnerId);
		}
		
		return (List<DMDIIProjectModel>) mapper.mapToModel(dmdiiEntities);
	}
}

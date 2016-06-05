package org.dmc.services.data.mappers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.dmc.services.data.entities.BaseEntity;
import org.dmc.services.data.models.BaseModel;

public abstract class AbstractMapper<T extends BaseEntity, S extends BaseModel> implements Mapper<T, S> {

	@Inject
	protected MapperFactory mapperFactory;
	
	@PostConstruct
	private void init() {
		mapperFactory.registerMapper(this);
	}
	
}

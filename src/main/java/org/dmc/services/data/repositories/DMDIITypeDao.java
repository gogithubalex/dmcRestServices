package org.dmc.services.data.repositories;

import java.util.List;

import org.dmc.services.data.entities.DMDIIType;
import org.springframework.data.repository.CrudRepository;

public interface DMDIITypeDao extends CrudRepository<DMDIIType, Integer> {

	List<DMDIIType> findAll();
	
}

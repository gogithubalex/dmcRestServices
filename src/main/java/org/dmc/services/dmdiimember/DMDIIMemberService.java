package org.dmc.services.dmdiimember;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dmc.services.data.entities.DMDIIMember;
import org.dmc.services.data.entities.Organization;
import org.dmc.services.data.entities.QDMDIIMember;
import org.dmc.services.data.entities.QDMDIIProject;
import org.dmc.services.data.mappers.Mapper;
import org.dmc.services.data.mappers.MapperFactory;
import org.dmc.services.data.models.DMDIIMemberModel;
import org.dmc.services.data.models.OrganizationModel;
import org.dmc.services.exceptions.InvalidFilterParameterException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.ExpressionUtils;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.query.ListSubQuery;

@Service
public class DMDIIMemberService {

	@Inject
	private DMDIIMemberDao dmdiiMemberDao;

	@Inject
	private MapperFactory mapperFactory;

	@Inject
	private OrganizationService organizationService;

	public DMDIIMemberModel findOne(Integer id) {
		Mapper<DMDIIMember, DMDIIMemberModel> mapper = mapperFactory.mapperFor(DMDIIMember.class, DMDIIMemberModel.class);
		return mapper.mapToModel(dmdiiMemberDao.findOne(id));
	}

	public List<DMDIIMemberModel> findByName(String name, Integer pageNumber, Integer pageSize) {
		Mapper<DMDIIMember, DMDIIMemberModel> mapper = mapperFactory.mapperFor(DMDIIMember.class, DMDIIMemberModel.class);
		return mapper.mapToModel(dmdiiMemberDao.findByOrganizationNameLikeIgnoreCase(new PageRequest(pageNumber, pageSize), "%"+name+"%").getContent());
	}

	public DMDIIMemberModel save(DMDIIMemberModel memberModel) {
		Mapper<DMDIIMember, DMDIIMemberModel> memberMapper = mapperFactory.mapperFor(DMDIIMember.class, DMDIIMemberModel.class);
		Mapper<Organization, OrganizationModel> orgMapper = mapperFactory.mapperFor(Organization.class, OrganizationModel.class);

		Organization organization = orgMapper.mapToEntity(organizationService.save(memberModel.getOrganization()));

		DMDIIMember memberEntity = memberMapper.mapToEntity(memberModel);
		Organization organizationEntity = orgMapper.mapToEntity(organizationService.save(memberModel.getOrganization()));
		memberEntity.setOrganization(organizationEntity);

		memberEntity = dmdiiMemberDao.save(memberEntity);

		return memberMapper.mapToModel(memberEntity);
	}

	public List<DMDIIMemberModel> filter(Map filterParams, Integer pageNumber, Integer pageSize) throws InvalidFilterParameterException {
		Mapper<DMDIIMember, DMDIIMemberModel> mapper = mapperFactory.mapperFor(DMDIIMember.class, DMDIIMemberModel.class);
		Predicate where = ExpressionUtils.allOf(getFilterExpressions(filterParams));
		return mapper.mapToModel(dmdiiMemberDao.findAll(where, new PageRequest(pageNumber, pageSize)).getContent());
	}

	private Collection<Predicate> getFilterExpressions(Map<String, String> filterParams) throws InvalidFilterParameterException {
		Collection<Predicate> expressions = new ArrayList<Predicate>();

		expressions.add(categoryIdFilter(filterParams.get("categoryId")));
		expressions.add(tierFilter(filterParams.get("tier")));
		expressions.add(hasActiveProjectsFilter(filterParams.get("hasActiveProjects")));

		return expressions;
	}

	private Predicate categoryIdFilter(String categoryId) throws InvalidFilterParameterException {
		if (categoryId == null) {
			return null;
		}

		Integer categoryIdInt = null;
		try {
			categoryIdInt = Integer.parseInt(categoryId);
		} catch (NumberFormatException e) {
			throw new InvalidFilterParameterException("categoryId", Integer.class);
		}

		return QDMDIIMember.dMDIIMember.dmdiiType().dmdiiTypeCategory().id.eq(categoryIdInt);
	}

	private Predicate tierFilter(String tier) throws InvalidFilterParameterException {
		if (tier == null) {
			return null;
		}

		Integer tierInt = null;
		try {
			tierInt = Integer.parseInt(tier);
		} catch (NumberFormatException e) {
			throw new InvalidFilterParameterException("tier", Integer.class);
		}

		return QDMDIIMember.dMDIIMember.dmdiiType().tier.eq(tierInt);
	}

	private Predicate hasActiveProjectsFilter(String hasActiveProjects) {
		if (hasActiveProjects == null) {
			return null;
		}

		Date today = new Date();
		QDMDIIProject qdmdiiProject = QDMDIIProject.dMDIIProject;
		ListSubQuery subQuery = new JPASubQuery().from(qdmdiiProject).where(qdmdiiProject.awardedDate.before(today), qdmdiiProject.endDate.after(today)).list(qdmdiiProject.id);
		if (Boolean.valueOf(hasActiveProjects)) {
			return QDMDIIMember.dMDIIMember.projects.any().in(subQuery);
		} else {
			return new BooleanBuilder().and(QDMDIIMember.dMDIIMember.projects.any().in(subQuery)).not().getValue();
		}
	}

}

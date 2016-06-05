package org.dmc.services.data.repositories.legacy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dmc.services.data.config.DBConnector;
import org.dmc.services.data.entities.legacy.CompanyProfileChange;
import org.dmc.services.data.entities.legacy.CompanySkill;
import org.dmc.services.data.util.CompanyUserUtil;
import org.dmc.services.logging.ServiceLogger;
import org.dmc.services.web.exceptions.DMCError;
import org.dmc.services.web.exceptions.DMCServiceException;

import java.util.ArrayList;


public class CompanySkillDao {

	private final String logTag = CompanySkillDao.class.getName();
	private ResultSet resultSet;

	public ArrayList<CompanySkill> getCompanySkills(String userEPPN,
			int companyID) throws DMCServiceException {
		ArrayList<CompanySkill> skills = new ArrayList<CompanySkill>();
		try {
			if (!CompanyUserUtil.isDMDIIMember(userEPPN)) {
				ServiceLogger.log(this.logTag, "User: " + userEPPN
						+ " is not DMDII org member.");
				throw new DMCServiceException(
						DMCError.NotDMDIIMember, "User: " + userEPPN
								+ " is not DMDII org member.");
			} else {
				String query = "select * from organization_skill where organization_id = ?";
				PreparedStatement preparedStatement = DBConnector
						.prepareStatement(query);
				preparedStatement.setInt(1, companyID);
				this.resultSet = preparedStatement.executeQuery();
				while (this.resultSet.next()) {
					String id = (new Integer(this.resultSet.getInt("id")))
							.toString();
					String company_id = (new Integer(
							this.resultSet.getInt("organization_id")))
							.toString();
					String skill = this.resultSet.getString("skill");
					CompanySkill s = new CompanySkill();
					s.setId(id);
					s.setCompanyId(company_id);
					s.setName(skill);
					skills.add(s);
				}
			}
		} catch (SQLException e) {
			ServiceLogger.log(this.logTag, e.getMessage());
			throw new DMCServiceException(DMCError.OtherSQLError,
					"SQLException: \n" + e.getMessage());
		}
		return skills;
	}

	public int createCompanySkill(CompanySkill s, String userEPPN) throws DMCServiceException {
		int org_id = new Integer(s.getCompanyId()).intValue();
		int result=-1;
		try {			
			
			if (!CompanyUserUtil.isAdmin(userEPPN, org_id)) {
				ServiceLogger.log(this.logTag, "User: " + userEPPN + " is not admin user of org:." + org_id);
				throw new DMCServiceException(DMCError.NotAdminUser, "User: " + userEPPN + " is not admin user of org:." + org_id);
			}
			else
			{
			String query = "insert into organization_skill (organization_id, skill) values (?,?)";
			PreparedStatement preparedStatement = DBConnector
					.prepareStatement(query);

			preparedStatement.setInt(1,
					(new Integer(s.getCompanyId())).intValue());
			preparedStatement.setString(2, s.getName());
			int rCreate = preparedStatement.executeUpdate();

			String queryId = "select max(id) max_id from organization_skill";
			PreparedStatement preparedStatement1 = DBConnector
					.prepareStatement(queryId);
			ResultSet r=preparedStatement1.executeQuery();
			r.next();
			result=r.getInt("max_id");
			
			ServiceLogger.log(
					this.logTag,
					"User: " + userEPPN + " added skill set:"
							+ s.getName() + " into:" + s.getCompanyId() + " with id:" + result);
			}
		}

		catch (SQLException e) {
			ServiceLogger.log(this.logTag, "SQLException: " + e.toString());
			throw new DMCServiceException(DMCError.OtherSQLError,"SQLException: " + e.toString());
		}
		
		try {
			//TODO: for insert return the index as the results.
			CompanyProfileChange.profChangeAddItem(userEPPN, org_id, "organization_skill", "-1", s.getName());
		}
		catch (Exception e)
		{
			ServiceLogger.log(this.logTag, "Error in insert to change log: " + e.toString());
			throw new DMCServiceException(DMCError.CanNotInsertChangeLog, e.toString());
		}
		return result;
	}

	public int deleteCompanySkills(String sId, String userEPPN)
			throws DMCServiceException {
		int skillId = new Integer(sId).intValue();
		int org_id;
		int result;
		try {
			
			String querq_org = "select organization_id from organization_skill where id = ?";
			PreparedStatement preparedStatementOrg = DBConnector
					.prepareStatement(querq_org);
			preparedStatementOrg.setInt(1, skillId);
			ResultSet orgSet = preparedStatementOrg.executeQuery();

			if (!orgSet.next()) {
				ServiceLogger.log(this.logTag, "Skill id: " + sId
						+ " does not exist.");
				throw new DMCServiceException(DMCError.CompanySkillSetNotExist, "Skill id: " + sId + " does not exist.");
			} else
				org_id = orgSet.getInt("organization_id");

			if (!CompanyUserUtil.isAdmin(userEPPN, org_id)) {
				ServiceLogger.log(this.logTag, "User: " + userEPPN
						+ " is not admin user of org:." + org_id);
				throw new DMCServiceException(DMCError.NotAdminUser, "User: " + userEPPN
						+ " is not admin user of org:." + org_id);
			}

			String query = "delete from organization_skill where id=?";
			PreparedStatement preparedStatement = DBConnector
					.prepareStatement(query);
			preparedStatement.setInt(1, skillId);
			ServiceLogger.log(this.logTag, "User: " + userEPPN
					+ " tried to delete skill with id:" + skillId);
			result = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			ServiceLogger.log(this.logTag, e.getMessage());
			throw new DMCServiceException(DMCError.OtherSQLError,e.getMessage());
		}
		
        // Insert to change log
		try {
		 CompanyProfileChange.profChangeDeleteItem(userEPPN, org_id,"organization_skill", sId, "Skills");
		}
		catch (SQLException e)
		{
			throw new DMCServiceException(DMCError.CanNotInsertChangeLog, e.getMessage());
		}
		return result;
	}

}
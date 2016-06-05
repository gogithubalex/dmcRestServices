package org.dmc.services.data.repositories.legacy;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dmc.services.data.config.DBConnector;
import org.dmc.services.data.entities.legacy.Role;
import org.dmc.services.logging.ServiceLogger;

public class RoleDao {

	private final String logTag = RoleDao.class.getName();
	private ResultSet resultSet;

	public RoleDao() {}

	public Role getRole(int id) {
		ServiceLogger.log(logTag, "In Role DAO" + id);
		try {
			resultSet = DBConnector.executeQuery("SELECT * FROM role WHERE role_id = " + id);
			while (resultSet.next()) {
				String role_name = resultSet.getString("role_name");
				ServiceLogger.log(logTag, "Role Name - " + role_name);
				return new Role(id, role_name);
			}
		} catch (SQLException e) {
			ServiceLogger.log(logTag, e.getMessage());
		}
		return null;
	}

}
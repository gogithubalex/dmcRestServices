package org.dmc.services.data.repositories.legacy;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dmc.services.data.config.DBConnector;
import org.dmc.services.data.entities.legacy.Discussion;
import org.dmc.services.logging.ServiceLogger;
import org.springframework.http.HttpStatus;

import javax.xml.ws.http.HTTPException;

public class DiscussionListDao {

	private final String logTag = DiscussionListDao.class.getName();
	private ResultSet resultSet;

	public ArrayList<Discussion> getDiscussionList(String userEPPN, int projectIdFilter, int limit, String order, String sort) throws HTTPException {

		ArrayList<Discussion> discussions = new ArrayList<Discussion>();
        
        try {
        	
        	String query = "SELECT * FROM forum_messages ";
        	if (projectIdFilter != -1) {
        		query += "WHERE topic_id = " + projectIdFilter;
        	}
        	query += "ORDER BY " + sort + " " + order + " LIMIT " + limit;
            resultSet = DBConnector.executeQuery(query);

            while (resultSet.next()) {
                String id = String.valueOf(resultSet.getInt("message_id"));
                String title  = resultSet.getString("body");
                String message = resultSet.getString("body");
                String createdBy = resultSet.getString("reply_to");
                BigDecimal createdAt = new java.math.BigDecimal(String.valueOf(resultSet.getInt("time_posted")));
                String accountId = String.valueOf(resultSet.getInt("user_id"));
                String projectId = String.valueOf(resultSet.getInt("topic_id"));

                Discussion discussion = new Discussion.DiscussionBuilder(id, title)
                	.message(message)
                	.createdBy(createdBy)
                	.createdAt(createdAt)
                	.accountId(accountId)
                	.projectId(projectId)
                .build();
                
                discussions.add(discussion);
            }
        } catch (SQLException e) {
            ServiceLogger.log(logTag, e.getMessage());
            throw new HTTPException(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        return discussions;		
	}
}

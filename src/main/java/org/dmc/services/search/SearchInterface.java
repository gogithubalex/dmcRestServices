package org.dmc.services.search;

import org.dmc.services.data.entities.legacy.Company;
import org.dmc.services.data.entities.legacy.Component;
import org.dmc.services.data.entities.legacy.Project;
import org.dmc.services.data.entities.legacy.Service;
import org.dmc.services.data.entities.legacy.User;

import java.util.List;

/**
 * Created by 200005921 on 2/3/2016.
 */
public interface SearchInterface {

    /**
     * Search all collections
     * @param query
     * @return
     * @throws SearchException
     */
    public SearchResult search (String query, String userEPPN) throws SearchException;

    /**
     * Search components
     * @param query
     * @return
     * @throws SearchException
     */
    public List<Component> searchComponents (String query, String userEPPN) throws SearchException;

    /**
     * Search services
     * @param query
     * @return
     * @throws SearchException
     */
    public List<Service> searchServices (String query, String userEPPN) throws SearchException;

    /**
     * Search projects
     * @param query
     * @return
     * @throws SearchException
     */
    public List<Project> searchProjects (String query, String userEPPN) throws SearchException;

    /**
     * Search users
     * @param query
     * @return
     * @throws SearchException
     */
    public List<User> searchUsers (String query, String userEPPN) throws SearchException;

    /**
     * Search companies
     * @param query
     * @return
     * @throws SearchException
     */
    public List<Company> searchCompanies (String query, String userEPPN) throws SearchException;
}

package org.dmc.services.web.controllers.legacy;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.xml.ws.http.HTTPException;

import org.dmc.services.data.entities.legacy.UserAccountServer;
import org.dmc.services.data.repositories.legacy.AccountServersDao;
import org.dmc.services.logging.ServiceLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/account_servers", produces = { APPLICATION_JSON_VALUE })
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringMVCServerCodegen", date = "2016-04-04T18:05:28.094Z")
public class AccountServersController {
	
	private final String logTag = AccountServersController.class.getName();
	private AccountServersDao accountServersDao = new AccountServersDao();
	
	@RequestMapping(value = "", produces = { "application/json", "text/html" }, method = RequestMethod.POST)
	public ResponseEntity<UserAccountServer> accountServersServerIDPost(@RequestBody UserAccountServer accountServer,
																		@RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {

		ServiceLogger.log(logTag, "accountServersServerIDPost, userEPPN: " + userEPPN);
		
		int httpStatusCode = HttpStatus.OK.value();
		UserAccountServer userAccountServer = null;
		
		try {
			userAccountServer = accountServersDao.postUserAccountServer(accountServer, userEPPN);
		} catch (HTTPException httpException) {
			httpStatusCode = httpException.getStatusCode();
		}
		
		return new ResponseEntity<UserAccountServer>(userAccountServer, HttpStatus.valueOf(httpStatusCode));
	}
	
	
	@RequestMapping(value = "/{serverID}", produces = { "application/json", "text/html" }, method = RequestMethod.DELETE)
	public ResponseEntity<Void> accountServersServerIDDelete(@PathVariable("serverID") String serverID,
															 @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
		
		ServiceLogger.log(logTag, "accountServersServerIDDelete, userEPPN: " + userEPPN + " and server id " + serverID);
		
		int httpStatusCode = HttpStatus.OK.value();
		
		try {
			accountServersDao.deleteUserAccountServer(Integer.parseInt(serverID), userEPPN);
		} catch (HTTPException httpException) {
			httpStatusCode = httpException.getStatusCode();
		}

		return new ResponseEntity<Void>(HttpStatus.valueOf(httpStatusCode));
	}

	
	@RequestMapping(value = "/{serverID}", produces = { "application/json", "text/html" }, method = RequestMethod.GET)
	public ResponseEntity<UserAccountServer> accountServersServerIDGet(@PathVariable("serverID") String serverID,
																	   @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
		ServiceLogger.log(logTag, "accountServersServerIDGet, userEPPN: " + userEPPN + " and server id " + serverID);
		
		int httpStatusCode = HttpStatus.OK.value();
		UserAccountServer userAccountServer = null;
		
		try {
			userAccountServer = accountServersDao.getUserAccountServer(Integer.parseInt(serverID), userEPPN);
		} catch (HTTPException httpException) {
			httpStatusCode = httpException.getStatusCode();
		}
		
		return new ResponseEntity<UserAccountServer>(userAccountServer, HttpStatus.valueOf(httpStatusCode));
	}

	
	@RequestMapping(value = "/{serverID}", produces = { "application/json", "text/html" },method = RequestMethod.PATCH)
	public ResponseEntity<UserAccountServer> accountServersServerIDPatch(@PathVariable("serverID") String serverID,
																		 @RequestBody UserAccountServer server,
																		 @RequestHeader(value = "AJP_eppn", defaultValue = "testUser") String userEPPN) {
		
		ServiceLogger.log(logTag, "accountServersServerIDPatch, userEPPN: " + userEPPN);
		
		int httpStatusCode = HttpStatus.OK.value();
		UserAccountServer userAccountServer = null;
		
		try {
			userAccountServer = accountServersDao.patchUserAccountServer(serverID, server, userEPPN);
		} catch (HTTPException httpException) {
			httpStatusCode = httpException.getStatusCode();
		}
		
		return new ResponseEntity<UserAccountServer>(userAccountServer, HttpStatus.valueOf(httpStatusCode));
	}

}

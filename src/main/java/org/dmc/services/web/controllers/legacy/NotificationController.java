package org.dmc.services.web.controllers.legacy;

import org.dmc.services.data.entities.legacy.Notification;
import org.dmc.services.data.repositories.legacy.NotificationDao;
import org.dmc.services.logging.ServiceLogger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class NotificationController {
	
	private final String logTag = "NOTIFICATION_CONTROLLER";
	private NotificationDao notification = new NotificationDao();
	
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public ArrayList<Notification> getNotifications(@PathVariable("period") String period,
                                                    @PathVariable("type") String type,
                                                    @RequestHeader(value="AJP_eppn", defaultValue="testUser") String userEPPN) {
		ServiceLogger.log(logTag, "getNotifications: " + userEPPN);
		return notification.getNotificationsList(period, type, userEPPN);
	}
	
//    @RequestMapping(value = "notifications-statistic", method = RequestMethod.GET)
//    @ResponseBody
//    public Id getNotificationsStatistics(@RequestHeader(value="AJP_eppn", defaultValue="testUser") String userEPPN) {
//    	ServiceLogger.log(logTag, "Payload: " + payload);
//    	
//    	return notification.getStatistics(userEPPN);
//    }
	
	
	
	
}

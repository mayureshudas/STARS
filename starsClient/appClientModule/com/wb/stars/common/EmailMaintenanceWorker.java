package com.wb.stars.common;

import java.util.LinkedList;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.wb.stars.common.ejb.emailMaintenance.EmailMaintenanceManagerRemote;
import com.wb.stars.common.ejb.episodeScheduling.EpisodeSchedulingManagerRemote;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.utils.JNDINames;

public class EmailMaintenanceWorker {
	private static StarsLogger logger =
			(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	
	@EJB
	private EmailMaintenanceManagerRemote emailMaintenanceManager;
	
	
	public EmailMaintenanceWorker()
	{
		try
		{
			Context ctxt = new InitialContext();
			//emailMaintenanceManager = (EmailMaintenanceManagerRemote)ctxt.lookup(JNDINames.EMAIL_MAINTENANCE_BUSINESS_REMOTE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public LinkedList<Object> getEmailDetails() {
		// TODO Auto-generated method stub
		return  emailMaintenanceManager.getEmailDetails();
	}
	

}

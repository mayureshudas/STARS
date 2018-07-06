package com.wb.stars.reports;

import javax.naming.NamingException;

import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.reports.ejb.ReportsFacadeHome;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBException;

public class ReportHomes {

	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	/**
	 * provides names for JNDI lookups
	 */
	
	public static ReportsFacadeHome getReportRemoteHome()
		throws WBException {
		try {
			return (ReportsFacadeHome) EJBHomeFactory.getFactory().lookUpHome(
				JNDINames.REPORTS_FACADE_HOME ,
				ReportsFacadeHome.class);
		} catch (NamingException e) {
			logger.error("ReportHomes.class.getReportsFacadeHome()", e);
			throw new WBException(e);
		}

	}

	/**
	 * class only allows static members to be exposed
	 */
	private ReportHomes() {

	}

}

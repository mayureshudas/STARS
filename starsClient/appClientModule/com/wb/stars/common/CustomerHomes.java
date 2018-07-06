package com.wb.stars.common;

import javax.naming.NamingException;

import com.wb.stars.common.ejb.product.ProductManagerHome;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBException;

public class CustomerHomes {

	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	/**
	 * provides names for JNDI lookups
	 */
	// public final static String CUSTOMER_MANAGER = new String ( "java:comp/env/ejb/CustomerManagerHome" );

	public static ProductManagerHome getProductRemoteHome()
		throws WBException {
		try {
			return (ProductManagerHome) EJBHomeFactory.getFactory().lookUpHome(
				JNDINames.PRODUCT_MANAGER,
				ProductManagerHome.class);
		} catch (NamingException e) {
			logger.error("CustomerHomes.class.getProductRemoteHome()", e);
			throw new WBException(e);
		}

	}

	/**
	 * class only allows static members to be exposed
	 */
	private CustomerHomes() {

	}

}

package com.wb.stars.security;

import javax.naming.NamingException;

import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemoteHome;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBException;



/**
 * provides names for JNDI lookups
 */
public class SecurityHomes
{
	private static StarsLogger logger =
									(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	
	
	// public final static String SECURITY_FACADE = new String ( "java:comp/env/ejb/SecurityFacadeRemoteHome" );
	
	
	/**
	 * returns the home interface for the security facade
	 */
	public static SecurityFacadeRemoteHome getSecurityFacadeHome()  throws WBException
	{
		try
		{
			return (SecurityFacadeRemoteHome)EJBHomeFactory.getFactory().lookUpHome( JNDINames.SECURITY_FACADE , SecurityFacadeRemoteHome.class );
		}
		catch ( NamingException e )
		{
			logger.error("SecurityHomes.class", e );
			//WBLogger.getReference().logError( SecurityHomes.class, e );
			throw new WBException( e );
		}
		
	}
	
	
	/**
	 * class only allows static members to be exposed
	 */
	private SecurityHomes()
	{
		
	}
}


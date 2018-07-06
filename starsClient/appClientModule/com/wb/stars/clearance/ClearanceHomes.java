package com.wb.stars.clearance;

import javax.naming.NamingException;

import com.wb.stars.clearance.ejb.clearanceFacade.ClearanceFacadeHome;
import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBException;


public abstract class ClearanceHomes 
{

	private static StarsLogger logger = (StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
/**
 * provides names for JNDI lookups
 */
	
	
	// public final static String CLEARANCE_FACADE = new String ( "java:comp/env/ejb/ClearanceFacadeHome" );
	
	
	/**
	 * returns the home interface for the security facade
	 */
	public static ClearanceFacadeHome getClearanceFacadeHome()  throws WBException
	{
		try
		{
			return (ClearanceFacadeHome)EJBHomeFactory.getFactory().lookUpHome( JNDINames.CLEARANCE_FACADE , ClearanceFacadeHome.class );
		}
		catch ( NamingException e )
		{
			logger.error( "Error in lookup ", e );
			//WBLogger.getReference().logError( ClearanceHomes.class, e );
			throw new WBException( e );
		}
		
	}
	
	
	/**
	 * class only allows static members to be exposed
	 */
	private ClearanceHomes()
	{
		
	}


	

}


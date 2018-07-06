package com.wb.stars.contracts;


import javax.naming.NamingException;

import com.wb.stars.common.Constants;
import com.wb.stars.contracts.ejb.ChangeOrderContractsFacadeRemoteHome;
import com.wb.stars.contracts.ejb.ContractsFacadeBMPRemoteHome;
import com.wb.stars.contracts.ejb.ContractsFacadeRemoteHome;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBException;


public class ContractsHomes
{

	private static StarsLogger logger = (StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	/**
	 * provides names for JNDI lookups
	 */

	
	
	/**
	 * returns the home interface for the security facade
	 */
	public static ContractsFacadeRemoteHome getRemoteHome()  throws WBException
	{
		try
		{
			return (ContractsFacadeRemoteHome)EJBHomeFactory.getFactory().lookUpHome( JNDINames.CONTRACT_FACADE , ContractsFacadeRemoteHome.class );
		}
		catch ( NamingException e )
		{
			logger.error( "Error in lookup ", e );
			throw new WBException( e );
		}
		
	}
	
	
	
	
	
	public static ContractsFacadeBMPRemoteHome getBMPRemoteHome()  throws WBException
	{
		try
		{
			return (ContractsFacadeBMPRemoteHome)EJBHomeFactory.getFactory().lookUpHome( JNDINames.CONTRACT_FACADE_BMP , ContractsFacadeBMPRemoteHome.class );
		}
		catch ( NamingException e )
		{
			logger.error( "Error in lookup ", e );
			throw new WBException( e );
		}
		
	}
	
	
	
	
	public static ChangeOrderContractsFacadeRemoteHome getChangeOrderContractsFacadeRemoteHome()  throws WBException
	{
		try
		{
			return (ChangeOrderContractsFacadeRemoteHome)EJBHomeFactory.getFactory().lookUpHome( JNDINames.CHANGE_ORDER_CONTRACT_FACADE, ChangeOrderContractsFacadeRemoteHome.class );
		}
		catch ( NamingException e )
		{
			logger.error( "Error in lookup ", e );
			throw new WBException( e );
		}
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * class only allows static members to be exposed
	 */
	private ContractsHomes()
	{
		
	}

	








}



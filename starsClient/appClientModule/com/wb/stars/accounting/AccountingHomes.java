package com.wb.stars.accounting;

import javax.naming.NamingException;

import com.wb.stars.accounting.ejb.AccountingFacadeHome;
import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBLogger;

public class AccountingHomes
{
	/**
	 * provides names for JNDI lookups
	 */
	public final static String ACCOUNTING_FACADE = new String ( "ejb/com/wb/stars/accounting/ejb/AccountingFacadeHome" );
	
	
	/**
	 * returns the home interface for the security facade
	 */
	public static AccountingFacadeHome getRemoteHome()  throws WBException
	{
		try
		{
			return (AccountingFacadeHome)EJBHomeFactory.getFactory().lookUpHome( ACCOUNTING_FACADE , AccountingFacadeHome.class );
		}
		catch ( NamingException e )
		{
			WBLogger.getReference().logError( AccountingHomes.class, e );
			throw new WBException( e );
		}
		
	}
}


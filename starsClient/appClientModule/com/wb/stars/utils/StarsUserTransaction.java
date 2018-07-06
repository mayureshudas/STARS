
/*
 *Copyright (c) 2004 Warner Bros. All Rights Reserved.
 *
 *This software is the confidential and propriety information of
 *Warner Bros. ("Confidential Information"). You shall not
 *disclose such Confidential Information and shall use it only in
 *accordance with the terms of the license agreement you entered into
 *with Warner Bros.
 *
 *CopyrightVersion 1.0
 *
 *Revision List
 *
 *------------------------------------------------------------------------
 *Date      	    Author                        Remarks
 *------------------------------------------------------------------------
 *05/23/205         Deepika_Kuhite               The class is a wrapper of UserTransaction class of JTA 
 */
package com.wb.stars.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;

public class StarsUserTransaction
{
	private static Context ctx = null;
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	private static StarsLogger eventLogger =
			(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);	

	private static boolean isInitialized = false;
	private UserTransaction myUserTransaction = null;

	/**
	 * Private constructor of StarsUserTransaction, stops creating objects outside of the class
	 * @param ut
	 */
	private StarsUserTransaction(UserTransaction ut)
	{
		myUserTransaction = ut;
	}

	/**
	 * Factory method that returns wrapper object for JTA user transaction object
	 * @return StarsUserTransaction(object of this class)
	 * @throws WBException
	 */
	public static StarsUserTransaction getObject() throws WBException
	{
		UserTransaction userTrans = null;

		try
		{
			if (!isInitialized)
			{
				synchronized (StarsUserTransaction.class)
				{
					ctx = new InitialContext();
					isInitialized = true;
				}
			}

			userTrans =
				(UserTransaction) ctx.lookup("java:comp/UserTransaction");

//			userTrans.setTransactionTimeout(10 * 60);
			return new StarsUserTransaction(userTrans);

		}
		catch (NamingException e)
		{
			logger.error("NamingException:", e);
			throw new WBException(e);

		}
//		catch (SystemException se)
//		{
//			logger.error("SystemException:", se);
//			throw new WBException(se);
//		}
	}


	/**
	 * This method invokes begin of UserTransaction
	 * @throws WBException
	 */
	public void begin(
		String className,
		String user,
		String txName,
		String data)
		throws WBException
	{
		try
		{
			String logString = "";
			if (user != null)
			{
				logString += "User: " + user;
			}
			logString += " User transaction begins - ID: "
				+ myUserTransaction.hashCode();
			if (className != null)
			{
				logString += " Called from: " + className;
			}
			if (txName != null)
			{
				logString += " Transaction: " + txName;
			}
			if (data != null)
			{
				logString += " data: " + data;
			}
			eventLogger.logEvent(logString);
			myUserTransaction.begin();
		}
		catch (NotSupportedException e)
		{
			logger.error("NotSupportedException:", e);
			throw new WBException(e);
		}
		catch (SystemException e)
		{
			logger.error("SystemException:", e);
			throw new WBException(e);
		}
	}

	/**
	 * This method invokes begin of UserTransaction
	 * @throws WBException
	 */
	public void begin() throws WBException
	{
		begin(null, null, null, null);
	}

	/**
	 * This method invokes commit of UserTransaction
	 * @throws WBException
	 */
	public void commit() throws WBException
	{
		commit(null);
	}

	/**
	 * This method invokes commit of UserTransaction
	 * @throws WBException
	 */
	public void commit(String user) throws WBException
	{
		try
		{
			myUserTransaction.commit();
			String logString = "";
			if (user != null)
			{
				logString += "User: " + user;
			}
			logString += " User transaction commited - ID: " + myUserTransaction.hashCode();			
			eventLogger.logEvent(logString);
			myUserTransaction=null;
		
		}
		catch (SecurityException e)
		{
			logger.error("SecurityException:", e);
			throw new WBException(e);
		}
		catch (IllegalStateException e)
		{
			logger.error("IllegalStateException:", e);
			throw new WBException(e);
		}
		catch (RollbackException e)
		{
			logger.error("RollbackException:", e);
			throw new WBException(e);
		}
		catch (HeuristicMixedException e)
		{
			logger.error("HeuristicMixedException:", e);
			throw new WBException(e);
		}
		catch (HeuristicRollbackException e)
		{
			logger.error("HeuristicRollbackException:", e);
			throw new WBException(e);
		}
		catch (SystemException e)
		{
			logger.error("SystemException:", e);
			throw new WBException(e);
		}
	}
	/**
	 * This method invokes rollback of UserTransaction
	 * @throws WBException
	 */
	public void rollback() throws WBException
	{
		rollback(null,null);
	}
	
	/**
	 * This method invokes rollback of UserTransaction
	 * @throws WBException
	 */
	public void rollback(String user, String data) throws WBException
	{
		try
		{
			myUserTransaction.rollback();
			String logString = "";
			if (user != null)
			{
				logString += "User: " + user;
			}
			logString += " User transaction Rolled Back - ID: " + myUserTransaction.hashCode();	
			if (data != null)
			{
				logString += " data: " + data;
			}
			eventLogger.logEvent(logString);
			myUserTransaction=null;
			
		}
		catch (IllegalStateException e)
		{
			logger.error("IllegalStateException:", e);
			throw new WBException(e);
		}
		catch (SecurityException e)
		{
			logger.error("SecurityException:", e);
			throw new WBException(e);
		}
		catch (SystemException e)
		{
			logger.error("SystemException:", e);
			throw new WBException(e);
		}
	}
}

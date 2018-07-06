/*
 * Created on Jul 25, 2008
 *
 *
 *Copyright (c) 2001 Warner Bros. All Rights Reserved.
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
 * ------------------------------------------------------------------------
 * Date      		Author                  Remarks
 * ------------------------------------------------------------------------
 *Jul 25, 2008     Satya Suma				Created
 *
 *
 **/
package com.wb.stars.techOps;

//import java.rmi.RemoteException;
import java.util.LinkedList;

//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.naming.NamingException;

import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;
//import com.wb.stars.ejb.utils.EJBHomeFactory;
//import com.wb.stars.techOps.ejb.TechOpsFacadeHome;
import com.wb.stars.techOps.ejb.TechOpsFacadeRemote;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCalendar;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;

/**
 * @author Satya_Suma
 * Added as part of SC55
 * TechOpsWorker ver 1.0 - A worker class to delegate method calls from web layer to facade layer
 */
public class TechOpsWorker {
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	//@EJB
	private TechOpsFacadeRemote techOpsFacadeRemote;

	// EJB3 annotation added, directly calling Business interface
	public TechOpsWorker() throws WBException {
		try {
			//get a reference to the security object
			//this.techOpsFacadeRemote = getTechOpsFacadeRemote();
			Context ctxt = new InitialContext();
			this.techOpsFacadeRemote = (TechOpsFacadeRemote) ctxt.lookup(JNDINames.TECHOPS_FACADE_BUSINESS_REMOTE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
		logger.info(Constants.CREATED);
	}

	/*public TechOpsFacadeRemote getTechOpsFacadeRemote() throws Exception {
		logger.info(Constants.START);

		if (techOpsFacadeRemote == null) {
			try {
				// call to create a default instance of the session bean
				techOpsFacadeRemote = getTechOpsFacadeHome().create();

			} catch (RemoteException re) {
				logger.error(re.getMessage(), re);
				throw new Exception(re.getMessage());
			} catch (javax.naming.NamingException ne) {
				logger.error(ne.getMessage(), ne);
				throw new Exception(ne.getMessage());

			} catch (Exception ce) {
				logger.error(ce.getMessage(), ce);
				throw new Exception(ce.getMessage());
			}
		}
		logger.info(Constants.END);
		return techOpsFacadeRemote;
	}*/

	/*private TechOpsFacadeHome getTechOpsFacadeHome() throws NamingException {
		logger.info(Constants.START);
		TechOpsFacadeHome qrhome =
			(TechOpsFacadeHome) EJBHomeFactory.getFactory().lookUpHome(
				JNDINames.TECHOPS_FACADE,
				TechOpsFacadeHome.class);
		logger.info(Constants.END);
		return qrhome;
	}*/

	public boolean chkSwitchStatus() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return techOpsFacadeRemote.chkSwitchStatus();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateSwitchStatus(String status) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			techOpsFacadeRemote.updateSwitchStatus(status);
			logger.info(Constants.END);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean isValidProduct(String productName,long season)
		throws WBException, WBSQLException {
		try {
			return techOpsFacadeRemote.isValidProduct(productName,season);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public WBCalendar retrieveFileDetails(String fileName)
		throws WBException, WBSQLException {
		try {
			return techOpsFacadeRemote.retrieveFileDetails(fileName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void insertFileDetails(LinkedList<Object> insertFileList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			techOpsFacadeRemote.insertFileDetails(insertFileList);
			logger.info(Constants.END);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateFileDetails(LinkedList<Object> updateFileList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			techOpsFacadeRemote.updateFileDetails(updateFileList);
			logger.info(Constants.END);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public int getProductId(String productName)
		throws WBException, WBSQLException {
		try {
			return techOpsFacadeRemote.getProductId(productName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	public String getStarsProdName(String prodName,long season)
		throws WBException, WBSQLException {
		try {
			return techOpsFacadeRemote.getStarsProdName(prodName,season);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean isDetailsAlreadyPresent(long prodId, long season)
		throws WBException, WBSQLException {
		try {
			return techOpsFacadeRemote.isDetailsAlreadyPresent(prodId, season);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void removeTechOpsDetails(long prodId, long season)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			techOpsFacadeRemote.removeTechOpsDetails(prodId, season);
			logger.info(Constants.END);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void insertTechOpsDetails(LinkedList<Object> filesDataList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			techOpsFacadeRemote.insertTechOpsDetails(filesDataList);
			logger.info(Constants.END);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
}

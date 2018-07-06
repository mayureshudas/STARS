/*
 * 	Copyright (c) 2004 Warner Bros. All Rights Reserved.
 * 
 * 	This software is the confidential and proprietary information of 
 * 	Warner Bros. ("Confidential Information").  You shall not
 * 	disclose such Confidential Information and shall use it only in
 * 	accordance with the terms of the license agreement you    entered into
 * 	with Warner Bros.
 * 	Copyright Version 1.0
 *	
 *  Revision List
 *	
 *  --------------------------------------------------------------------------
 *  Date		   Author				Remarks
 *  -------------------------------------------------------------------------- 
 *  17-Dec-2004	   Nageswararao_C		Created 	  
 *	 
 */
package com.wb.stars.tablemaintenance;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Vector;

//import javax.ejb.CreateException;
//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.wb.stars.common.Constants;
//import com.wb.stars.common.CustomerHomes;
import com.wb.stars.common.ejb.product.ProductManagerRemote;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;

/**
 * Manages Interactions with EJB 
 * @author Infosys (Nageswararao Cheedella)
 */
public class TableMaintenanceWorker
{
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/
	
	//@EJB
	private ProductManagerRemote productManager;

	// EJB3 annotation added, directly calling Business interface
	public TableMaintenanceWorker() throws WBException
	{
		try
		{
		//productManager = getRemote();
		Context ctxt = new InitialContext();
		productManager = (ProductManagerRemote) ctxt.lookup(JNDINames.PRODUCT_MANAGER_BUSINESS_REMOTE);
		}
		catch ( NamingException e )
		{
			logger.error("ProductManagerRemote.class", e );
			throw new WBException( e );
		}
		logger.info(Constants.CREATED);
	}

	/*public ProductManagerRemote getRemote() throws WBException
	{
		logger.info(Constants.START);

		if (productManager == null)
		{
			try
			{
				productManager = CustomerHomes.getProductRemoteHome().create();
			}
			catch (RemoteException re)
			{
				logger.error(re);
				throw new WBEJBException(re);
			}
			catch (CreateException re)
			{
				logger.error(re);
				throw new WBEJBException(re);
			}
		}
		logger.info(Constants.END);
		return productManager;

	}*/

	/**
	 * This method gets the list of look up tables from database depending on the 
	 * access for the role of the logged user on the list of tables. 
	 * @param roleId
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public LinkedList<Object> getTablesList(Long roleId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getTablesList(roleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method gets the list of records in table from database
	 * @param tableId
	 * @return Vector
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public Vector<Object> getTableRecordsList(long tableId,String tableName)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getTableRecordsList(tableId,tableName);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method gets the list of drop down records for foreign key update
	 * for a table from database
	 * @param fieldId
	 * @return LinkedList
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public LinkedList<Object> getDropDownRecordsList(long fieldId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getDropDownRecordsList(fieldId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method save the updated record to the database
	 * @param maintenanceTableFieldsVO
	 * @param tableId
	 * @param tableName
	 * @param oldPKValue
	 * @param userId
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public void saveTableRecord(
		MaintenanceTableFieldsVO maintenanceTableFieldsVO,
		long tableId,
		String tableName,
		String oldPKValue,
		long userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			productManager.saveTableRecord(
				maintenanceTableFieldsVO,
				tableId,
				tableName,
				oldPKValue,
				userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method add a new record to the database
	 * @param maintenanceTableFieldsVO
	 * @param tableId
	 * @param tableName
	 * @param userId
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public void addNewTableRecord(
		MaintenanceTableFieldsVO maintenanceTableFieldsVO,
		long tableId,
		String tableName,
		long userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			productManager.addNewTableRecord(
				maintenanceTableFieldsVO,
				tableId,
				tableName,
				userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method deletes record from the database
	 * @param maintenanceTableFieldsVO
	 * @param tableId
	 * @param tableName
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public void deleteRecord(
		MaintenanceTableFieldsVO maintenanceTableFieldsVO,
		long tableId,
		String tableName)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			productManager.deleteRecord(
				maintenanceTableFieldsVO,
				tableId,
				tableName);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method gets the role and table wise security(table access) data.
	 * @param tableId
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public CodeMaintenanceSecurityViewVO getCodeMaintenanceSecurityViewVO(long tableId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return productManager.getCodeMaintenanceSecurityViewVO(tableId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * updates the role wise table security details.
	 * @param viewVO
	 * @param userId
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public void updateCodeMaintenanceSecurityViewVO(
		CodeMaintenanceSecurityViewVO viewVO,
		Long userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			productManager.updateCodeMaintenanceSecurityViewVO(viewVO, userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * This method gets the column details of the specified table.
	 * @param tableName
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public LinkedList<Object> getTableColumnDetailsList(String tableName)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return productManager.getTableColumnDetailsList(tableName);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * method checks whether a table is part of code maintenance or not.
	 * @param tableName
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public boolean isTablePresent(String tableName)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return productManager.isTablePresent(tableName);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * method to delete a table from code maintenance.
	 * @param tableName
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public void deleteTable(String tableName)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			productManager.deleteTable(tableName);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * method to add table to code maintenance.
	 * @param tableName
	 * @param userId
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public void addTable(
		String tableName,
		LinkedList<Object> columnDetailsList,
		long userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			productManager.addTable(tableName, columnDetailsList, userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	
	/**
	 * This method will be called from code maintenance security screen to 
	 * load the screen with role wise table wise access details.
	 * @param roleId
	 * @param tableId
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public long getRoleTableAccess(long roleId, long tableId)
		throws WBException, WBSQLException, RemoteException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getRoleTableAccess(roleId, tableId);
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"error occured",
				new String[] { this.getClass().getName()},
				e);
		}
	}

	/**
	 * 
	 * @param tableId
	 * @return primaryKeyString
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public String getPrimaryKeyString(long tableId)
		throws WBException, WBSQLException, RemoteException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getPrimaryKeyString(tableId);
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"error occured",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 */
	public MaintenanceTableDropDownsVO getForeignKeyTableName(long fieldId)
		throws WBException, WBSQLException, RemoteException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getForeignKeyTableName(fieldId);
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"error occured",
				new String[] { this.getClass().getName()},
				e);
		}
	}


}

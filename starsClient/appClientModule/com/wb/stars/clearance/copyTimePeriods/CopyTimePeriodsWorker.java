/**	
 *  Name	       : CopyTimePeriodsWorker.java
 *  Type	       : Java Class
 *  Description   : Worker class for Copy Time Periods enhancement
 *  History       : CopyTimePeriodsWorker.java
 ***********************************************************************
  	                         MODIFICATION LOG                                                                                                     
 ***********************************************************************	
 *   Ver	   Date		      Modified By                    Description     
 *   0.1	11-Nov-2017			Bhamathi						Created
 */
package com.wb.stars.clearance.copyTimePeriods;

import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.wb.stars.clearance.ejb.copyTimePeriods.CopyTimePeriodsRemote;
import com.wb.stars.clearance.timeperiod.TimePeriodHeaderKey;
import com.wb.stars.clearance.vo.PotentialTPReportResultsVO;
import com.wb.stars.clearance.vo.PotentialTPReportVO;
import com.wb.stars.common.Constants;
import com.wb.stars.common.simple.ProgramSeasonCustKey;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.security.UserVO;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;

public class CopyTimePeriodsWorker {
	
	private static StarsLogger logger =
			(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

		/*private static StarsLogger eventsLogger =
			(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

		/**
		 * Manages interaction with CopyTimePeriods beans
		 */

		//@EJB
		private CopyTimePeriodsRemote m_copyTimePeriods;

		// EJB3 annotation added, directly calling Business interface
		public CopyTimePeriodsWorker() throws WBException {
			try {
				//get a reference to the security object
				Context ctxt = new InitialContext();
				m_copyTimePeriods = (CopyTimePeriodsRemote) ctxt.lookup(JNDINames.COPY_TIME_PERIODS_BUSINESS_REMOTE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBException(e);
			}
			logger.info(Constants.CREATED);
		}
		
		/**
		 * Method to get Products / Season list eligible for Mass Copy TimePeriods Screen.
		 * returns List of  Products / Season
		 */
		public LinkedList<Object> getProductsSeasonforMassCopy() throws WBException, WBSQLException {
			logger.info(Constants.START);
			LinkedList<Object> productsSeasonList = null;
			try {

				productsSeasonList = m_copyTimePeriods.getProductsSeasonforMassCopy();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return productsSeasonList;
		}
		
		/**
		 * Method to call the procedure for copying the time periods 
		 * for selected list of Products / Season list in the Mass Copy TimePeriods Screen.
		 * @param LinkedList<Object> - Selected List of  Products / Season for TP copy
		 * @return LinkedList<Object> - Returns List of  Products / Station not copied
		 */
		public LinkedList<Object> copyTPsSelectedProductsSeason(LinkedList<Object> selectedList) 
				throws WBException, WBSQLException {
			logger.info(Constants.START);
			LinkedList<Object> productsStationList = null;
			try 
			{
				productsStationList = m_copyTimePeriods.copyTPsSelectedProductsSeason(selectedList);
			} 
			catch (Exception e) 
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return productsStationList;
		}
		
		/**
		 * Method for fetching the data from the Preview/Staging tables for Potential Time Periods screen
		 * @param ProgramSeasonCustKey key having Program,Season,Customer Id
		 * @return LinkedList<Object> - list of TimePeriodEntryViewVOs
		 */
		public LinkedList<Object>  getPotentialTPEntryViewVOs(ProgramSeasonCustKey key) 
				throws  WBException, WBSQLException{
			logger.info(Constants.START);
			LinkedList<Object> potentialTPEntryViewVOList = null;
			try {
				potentialTPEntryViewVOList = m_copyTimePeriods.getPotentialTPEntryViewVOs(key);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return potentialTPEntryViewVOList;
		}
		
		/**
		 * Method for checking if Contract has undergone changes for Potential Time Periods screen
		 * @param ProgramSeasonCustKey key having Program,Season,Customer Id
		 * @return boolean - true if contract has changes
		 */
		public boolean checkContractChanges(ProgramSeasonCustKey key)throws  WBException, WBSQLException {
			logger.info(Constants.START);
			boolean isContractChange = false;
			try {
				isContractChange = m_copyTimePeriods.checkContractChanges(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return isContractChange;
		}

		/**
		 * Method for checking if ATPH/ATPL tables has undergone changes for Potential Time Periods screen
		 * @param ProgramSeasonCustKey key having Program,Season,Customer Id
		 * @return boolean - true if ATPH/ATPL tables has changes
		 */
		public boolean checkCopyTPChanges(ProgramSeasonCustKey key)throws  WBException, WBSQLException {
			logger.info(Constants.START);
			boolean isCopyTPChange = false;
			try {
				isCopyTPChange = m_copyTimePeriods.checkCopyTPChanges(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return isCopyTPChange;
		}	
		
		/**
		 * Method for checking the records are copied in ATPH/ATPL tables after 'Save' in Potential Time Periods screen
		 * @param ProgramSeasonCustKey key having Program,Season,Customer Id
		 * @return boolean - true if records are copied in ATPH/ATPL tables after 'Save'
		 */
		public boolean checkCopyTPRecordsActual(ProgramSeasonCustKey key)throws  WBException, WBSQLException  {
			logger.info(Constants.START);
			boolean isCopyTPRecords = false;
			try {
				isCopyTPRecords = m_copyTimePeriods.checkCopyTPRecordsActual(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return isCopyTPRecords;
		}
		
		/**
		 * Method for marking the records inactive in PTPH/PTPL Staging tables for Potential Time Periods screen
		 * @param ProgramSeasonCustKey key having Program,Season,Customer Id
		 * @param boolean contractChanges - true if contract has Changes 
		 * @param boolean timePeriodChanges - true if timePeriod has Changes 
		 */
		public void deletePotentialTPRecords(ProgramSeasonCustKey key,
				boolean contractChanges, boolean timePeriodChanges, boolean overWrite)
						throws  WBException, WBSQLException  {
			logger.info(Constants.START);
			try {
				m_copyTimePeriods.deletePotentialTPRecords(key,contractChanges,timePeriodChanges,overWrite);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
		}
		
		/**
		 * Method to get Season list eligible for PotentialTPReport Screen.
		 * returns List of Season
		 */
		
	//public List<PotentialTPReportVO> getSeasonforPotentialTPReport()throws WBException,WBSQLException
		/*public List <Object> getSeasonforPotentialTPReport()throws WBException,WBSQLException
		{
			logger.info(Constants.START);
			//List<PotentialTPReportVO> potentialTPReportSeasonList = null;
			List <Object> potentialTPReportSeasonList =null;
			
			try{
				potentialTPReportSeasonList = m_copyTimePeriods.getSeasonforPotentialTPReport();
			}
			catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return potentialTPReportSeasonList;
			
		}
		public List <Object> getProductforPotentialTPReport()throws WBException,WBSQLException
		{
			logger.info(Constants.START);
			//List<PotentialTPReportVO> potentialTPReportSeasonList = null;
			List <Object> potentialTPReportProductList =null;
			
			try{
				potentialTPReportProductList = m_copyTimePeriods.getProductforPotentialTPReport();
			}
			catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return potentialTPReportProductList;
		}
		public List <Object> getStationforPotentialTPReport(String param)throws WBException,WBSQLException
		{
			logger.info(Constants.START);
			//List<PotentialTPReportVO> potentialTPReportSeasonList = null;
			List <Object> potentialTPReportStationList =null;
			
			try{
				potentialTPReportStationList = m_copyTimePeriods.getStationforPotentialTPReport(param);
			}
			catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
			return potentialTPReportStationList;
		}
		//Method to fetch all the product while selecting selectedSeasonList
		public LinkedList<Object> getAllProduct(LinkedList<Object> selectedSeasonList)
				throws WBException, WBSQLException {
				try {
					LinkedList<Object> list = m_copyTimePeriods.getAllProducts(selectedSeasonList);
					logger.info(Constants.END);
					return list;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new WBEJBException(e);
				}

			}
		//Method to fetch all the Station while selecting selectedProductList
		public LinkedList<Object> getAllStation(LinkedList<Object> selectedProductsList,LinkedList<Object> selectedSeasonList)throws WBException, WBSQLException {
			
			try {
				LinkedList<Object> list = m_copyTimePeriods.getAllStations(selectedProductsList,selectedSeasonList);
				logger.info(Constants.END);
				return list;
				}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}

		}*/
	/**
	 * Method to get Potential TPR Report based on the Seasons/Products/MarketRanks/Stations Selected
	 * @return LinkedList<PotentialTPReportResultsVO> - Returns List of  PotentialTPReportResultsVO objects
	 */	
	public LinkedList<PotentialTPReportResultsVO> getPotentialTPReportSearchResult(PotentialTPReportVO criteriaVO)throws WBSQLException, WBException
		{
		
		try
		{
			return m_copyTimePeriods.getPotentialTPReportSearchResult(criteriaVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		
		}
	
	/**
	 * Method to get Season list for the PTP Copy time period Report.
	 * @return LinkedList<Object> - Returns List of  Seasons
	 */
	public LinkedList<Object> loadSeasonList() throws WBException, WBSQLException{
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_copyTimePeriods.loadSeasonList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/**
	 * Method to get Products list for the PTP Copy time period Report.
	 * @return LinkedList<Object> - Returns List of  Products
	 */
	public LinkedList<Object> loadProductFromSeasonData(String seasons) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_copyTimePeriods.loadProductFromSeasonData(seasons);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * Method to get Stations list for the PTP Copy time period Report Screen based on the Seasons/Products/MarketRanks Selected
	 * @return LinkedList<Object> - Returns List of  Stations
	 */	
	public LinkedList<Object> loadStationsData(PotentialTPReportVO criteriaVO) throws WBException, WBSQLException{
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_copyTimePeriods.loadStationsData(criteriaVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/** This Method is for Copy Time Periods Enhancement
	 * This will be called when 'Remove' button is used on the Preview screen to remove a TP
	 * @param TimePeriodHeaderKey headerKey, UserVO userVO
	 * @return 
	 */
	public void removeTimePeriods(TimePeriodHeaderKey headerKey, UserVO userVO) 
			throws  WBException, WBSQLException  {
		logger.info(Constants.START);
		try {
			m_copyTimePeriods.removeTimePeriods(headerKey,userVO) ;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/**
	 * Method to get Season n+1 Product name for the confirmation popup 
	 * in  Mass Copy TimePeriods Screen.
	 * @param LinkedList<Object> selectedList - List of  Products name for season N
	 * @return LinkedList<Object> - List of  Products name for  season N+1
	 */
	public LinkedList<Object> getSeasonN1ProductList(LinkedList<Object> selectedList) 
			throws  WBException, WBSQLException  {
		logger.info(Constants.START);
		LinkedList<Object> list = null;
		try {
			list = m_copyTimePeriods.getSeasonN1ProductList(selectedList) ;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return list;
	}
	
	/**
	 * Method for refreshing the data from the Preview/Staging tables for Potential Time Periods screen
	 * @param ProgramSeasonCustKey key having Program,Season,Customer Id
	 * @return LinkedList<Object> - list of TimePeriodEntryViewVOs
	 */
	public LinkedList<Object>  refreshPotentialTPEntryViewVOs(ProgramSeasonCustKey key) 
			throws  WBException, WBSQLException{
		logger.info(Constants.START);
		LinkedList<Object> potentialTPEntryViewVOList = null;
		try {
			potentialTPEntryViewVOList = m_copyTimePeriods.refreshPotentialTPEntryViewVOs(key);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return potentialTPEntryViewVOList;
	}
	
}

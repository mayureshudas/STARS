/*
 * 	Copyright (c) 2004 Warner Bros. All Rights Reserved.
 * 
 * 	This software is the confidential and proprietary information of 
 * 	Warner Bros. ("Confidential Information").  You shall not
 * 	disclose such Confidential Information and shall use it only in
 * 	accordance with the terms of the license agreement you entered into
 * 	with Warner Bros.
 * 	Copyright Version 1.0
 *	
 *	Author: Infosys(Siva Puvvada) 
 *	Date: Dec 22, 2004 
 *	Modtime:  
 *	Revision:  
 *	History: ReportsWorker.java 
 *  Purpose: 
 */
package com.wb.stars.reports;

//import java.rmi.RemoteException;
import java.util.LinkedList;

//import javax.ejb.CreateException;
//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.reports.ejb.ReportsFacade;
//import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemote;
import com.wb.stars.utils.DropDownRecord;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;

public class ReportsWorker
{
	//@EJB
	private ReportsFacade reportsFacade;

	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	// EJB3 annotation added, directly calling Business interface
	public ReportsWorker() throws WBException
	{
		try
		{
			//reportsFacade = getRemote();
			Context ctxt = new InitialContext();
			reportsFacade = (ReportsFacade) ctxt.lookup(JNDINames.REPORTS_FACADE_BUSINESS_REMOTE);
		}
		catch ( NamingException e )
		{
			logger.error("ReportsFacade.class", e );
			throw new WBException( e );
		}
	}

	/*public ReportsFacade getRemote() throws WBException
	{

		if (reportsFacade == null)
		{
			try
			{
				reportsFacade = ReportHomes.getReportRemoteHome().create();
			}
			catch (RemoteException re)
			{

				throw new WBEJBException(re);
			}
			catch (CreateException re)
			{

				throw new WBEJBException(re);
			}
		}

		return reportsFacade;

	}*/

	public OrderHeaderDetailsReportsVO getOrderHeaderDetails(
		long orderHeaderId,
		int productType,
		long productId)
		throws WBException, WBSQLException
	{

		try
		{
			return reportsFacade.getOrderHeaderDetails(
				orderHeaderId,
				productType,
				productId);
		}

		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getDealPointSearchResults(ContractListingSearchCriteria contractListingSearchCriteria)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getDealPointSearchResults(
				contractListingSearchCriteria);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//code Added by Infosys(rajeev) for SC22
	public LinkedList<Object> getNoticeDateDetails(long orderHeaderId, long productId)
			throws WBSQLException, WBException
		{
			try
			{
				return reportsFacade.getNoticeDateDetails(
				orderHeaderId, productId);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
		}
   //End of code added for SC22
	//Code modified to fix CQ 1552(Passing an additional argument selectedFeaturesList)
	public LinkedList<Object> getDealPointReportData(
		LinkedList<Object> orderHeaderIds,
		LinkedList<Object> productIds,LinkedList<DropDownRecord> selectedFeaturesList)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getDealPointReportData(
				orderHeaderIds,
				productIds,selectedFeaturesList);
		}

		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of code modified to fix CQ 1552
	

	public int getProductType(long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getProductType(productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public NewOrderSummaryHeaderVO getNewSummaryReportHeaderONInfo(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getNewSummaryReportHeaderONInfo(
				orderHeaderId,
				productId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public NewSummaryReportFRHeaderVO getNewSummaryReportHeaderFRInfo(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getNewSummaryReportHeaderFRInfo(
				orderHeaderId,
				productId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public NewSummaryReportVOLHeaderVO getNewSummaryReportHeaderVolInfo(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getNewSummaryReportHeaderVolInfo(
				orderHeaderId,
				productId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getNewSummarySTBs(long orderHeaderId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getNewSummarySTBs(orderHeaderId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<OrderRunTermsVO> getPaymentDetails(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getPaymentDetails(orderHeaderId, productId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getVolPaymentDetails(long orderHeaderId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getVolPaymentDetails(orderHeaderId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderDeliveryOptions(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOrderDeliveryOptions(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<OrderTierDetailsVO> getTimePeriods(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getTimePeriods(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getVolTimePeriods(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getVolTimePeriods(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getAddlEpsTimePeriods(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlEpsTimePeriods(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getAddlTimePeriods(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getAddlTimePeriods(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getChangeOrders(long orderHeaderId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getChangeOrders(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getVolSalesTerms(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getVolSalesTerms(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getAddlEpsByProdSeason(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlEpsByProdSeason(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getInitialOrderEpisodes(
		long orderHeaderId,
		long productId,
		int episodeType)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getInitialOrderEpisodes(
				orderHeaderId,
				productId,
				episodeType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getRunsByEpisode(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getRunsByEpisode(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getLicFeeByEpisode(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getLicFeeByEpisode(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderFeatures(
		long orderHeaderId,
		long productId,
		int featureType)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOrderFeatures(
				orderHeaderId,
				productId,
				featureType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getNewSummaryReportFeatures(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getNewSummaryReportFeatures(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public NewSummaryReportFooterVO getNewOrderSummaryReportFooter(
		long orderHeaderId,
		long productId,
		boolean isContract)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getNewOrderSummaryReportFooter(
				orderHeaderId,
				productId,
				isContract);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderSTBs(
		long orderHeaderId,
		boolean needCityOfLic,
		boolean needType)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getOrderSTBs(
				orderHeaderId,
				needCityOfLic,
				needType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderProvisions(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getOrderProvisions(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderProvisionsON(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getOrderProvisionsON(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getNewSummaryReportComments(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getNewSummaryReportComments(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	

	public LinkedList<Object> getSelectedRuns(
		long orderHeaderId,
		long productId,
		boolean isMultipleRun)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getSelectedRuns(
				orderHeaderId,
				productId,
				isMultipleRun);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getAddlSelectedRuns(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlSelectedRuns(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getOrderContacts(long orderHeaderId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOrderContacts(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderSyndexDetails(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getOrderSyndexDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderLicStations(
		long orderHeaderId,
		long productId,
		boolean needCityOfLic,
		boolean needType)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOrderLicStations(
				orderHeaderId,
				productId,
				needCityOfLic,
				needType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AddlEpsLicFeeVO getAddlEpisFee(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlEpisFee(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ContractStatusVO getContractStatus(long orderHeaderId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getContractStatus(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ContractDatesVO getContractDates(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getContractDates(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getContractHistory(long orderHeaderId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getContractHistory(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getProvisions(long orderHeaderId, long productId)
		throws WBException, WBSQLException
	{
		try
		{
			return reportsFacade.getProvisions(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getAutoRenewalComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAutoRenewalComments(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderBackToBackProvisionsVO getBackToBackProvisionDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getBackToBackProvisionDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getCoOpGrantProvisionDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getCoOpGrantProvisionDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderFavoredNationProvisionVO getFavoredNationDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getFavoredNationDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getFRNComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getFRNComments(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getOnceMovedComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOnceMovedComments(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderOptionToDelayProvisionVO getOptionToDelayDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOptionToDelayDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OptionToLicenseVO getOptionToLicenseDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOptionToLicenseDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOptionToRenewDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getOptionToRenewDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PrePlayViewVO getPrePlayFeatures(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getPrePlayFeatures(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getPutAgreementComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getPutAgreementComments(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getRatingProvisionDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getRatingProvisionDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public RatingsRecaptureVO getRecaptureProvisionDetails(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getRecaptureProvisionDetails(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getSRTComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{

		try
		{
			return reportsFacade.getSRTComments(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getAddlEpisTPComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlEpisTPComments(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getAddlLicFeeComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlLicFeeComments(
				orderHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getAddlTermsComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlTermsComments(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getContractComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getContractComments(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getProductComments(long orderHeaderId, long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getProductComments(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getChangeOrderDetails(long orderHeaderId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getChangeOrderDetails(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getSelectedRun(
		long orderHeaderId,
		long productId,
		int isWknd)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getSelectedRun(
				orderHeaderId,
				productId,
				isWknd);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderSelectedRunsVO getPricePerSubject(
		long orderHeaderId,
		long productId)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getPricePerSubject(orderHeaderId, productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*public String getAddlSelectedRun(
		long orderHeaderId,
		long productId,
		int isWknd)
		throws WBSQLException, WBException
	{
		try
		{
			return reportsFacade.getAddlSelectedRun(
				orderHeaderId,
				productId,
				isWknd);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}*/
	//CQ3307 by XDSRINIV-swapping of products on Misc tab-starts
	public long getOrderDetailsId(long orderHeaderId, long productId)
	  throws WBSQLException{
		try{
			return reportsFacade.getOrderDetailsId(orderHeaderId,productId);
		}
  	
		catch(Exception e)
				{
				  throw new WBSQLException(
					  "errors.excep.system.component",
					  new String[] { this.getClass().getName()},
					  e);
				}
		}//CQ3307 by XDSRINIV-swapping of products on Misc tab-ends
}
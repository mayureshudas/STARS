/* Modified By     : Infosys (Prasanth Nandanuru)
 * Modified Date   : 4-Nov-2004
 * Purpose         : Added Error Handlers in
 * public/default/protected methods for exception handling*/

/* Added By   : Infosys(J Ananth Prakash)
	 * Added Date : 13-Jan-2005
	 * SR Number  : 249
	 * Purpose    : To get the barter type ID of the passed barter
*/

package com.wb.stars.clearance;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.wb.stars.clearance.ejb.clearanceFacade.ClearanceFacadeRemote;
import com.wb.stars.clearance.timeperiod.TimePeriodHeaderKey;
import com.wb.stars.clearance.timeperiod.TimePeriodHeaderVO;
import com.wb.stars.clearance.vo.ActualContractualViewVO;
import com.wb.stars.clearance.vo.PostAirExceptionViewVO;
import com.wb.stars.clearance.vo.PreAirExceptionViewVO;
import com.wb.stars.common.AbstractProductVO;
import com.wb.stars.common.BarterVO;
import com.wb.stars.common.Constants;
import com.wb.stars.common.CustomerKey;
import com.wb.stars.common.EventKey;
import com.wb.stars.common.EventVO;
import com.wb.stars.common.PreemptKey;
import com.wb.stars.common.StationMasterKey;
import com.wb.stars.common.StationMasterVO;
import com.wb.stars.common.SyndicationSeasonVO;
import com.wb.stars.common.simple.AVCKey;
import com.wb.stars.common.simple.ProgramSeasonCustKey;
import com.wb.stars.contracts.OrderDetailsVO;
import com.wb.stars.contracts.OrderHeaderVO;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.customerMaint.DmaData;
import com.wb.stars.discrepancies.DiscrepancyKey;
import com.wb.stars.discrepancies.DiscrepancyVO;
//import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemote;
import com.wb.stars.test.CustomerTestView;
//import com.wb.stars.utils.DropDownRecord;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCalendar;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;

public class ClearanceWorker {
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	/**
	 * Manages interaction with Clearance beans
	 */

	//@EJB
	private ClearanceFacadeRemote m_clearance;

	// EJB3 annotation added, directly calling Business interface
	public ClearanceWorker() throws WBException {
		try {
			//get a reference to the security object
			//m_clearance = getClearanceFacadeRemote();
			Context ctxt = new InitialContext();
			m_clearance = (ClearanceFacadeRemote) ctxt.lookup(JNDINames.CLEARANCE_FACADE_BUSINESS_REMOTE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
		logger.info(Constants.CREATED);
	}

	/**
	 * returns a list of all roles
	*/

	/*	public LinkedList getAllEvent() throws WBException
		{
	
			try
				{
				return m_clearance.getAllEvent();
	
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}*/

	/**
	 * returns a list of all Events
	*/

	/******************************** Start Rebecca's Events ****************************************************/
	public LinkedList<Object> getEventList(boolean presentOnly)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getEventList(presentOnly);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of Added code by Infosys (Puneet)
	* for UAT Defect No: 70 (S1Usr00001188)*/

	public long getAllStates(long eventId) throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getAllStates(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of Added code by Infosys (Puneet)
	 * for UAT Defect No: 70 (S1Usr00001188)*/

	public void deleteEvent(EventKey key) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.deleteEvent(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public EventVO createEvent(EventVO eventVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.createEvent(eventVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	//Code added to fix CQ 1552(Added this method to fetch all the features for the selected products)
	public LinkedList<Object> getAllFeatures(LinkedList<Object> selectedProductsList)
		throws WBException, WBSQLException {
		try {
			LinkedList<Object> list = m_clearance.getAllFeatures(selectedProductsList);
			logger.info(Constants.END);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//End of code added to fix CQ 1552

	/*
	 * Modified By      : Infosys (Puneet Chopra)
	 * Modified Date    : 15-Feb-2005
	 * SR Number        : 195,198,200
	 * Purpose          : To fix SR#198
	 * */
	public String getAllStatesChecked(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			return m_clearance.getAllStatesChecked(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//End of added code

	//Code added by DSiva to fix CQ S1Usr00000846
	public boolean isRunDateMandatory(long runDatesId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			return m_clearance.isRunDateMandatory(runDatesId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of code added by DSiva to fix CQ S1Usr00000846

	public void updateEvent(EventVO eventVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.updateEvent(eventVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	//Code added for CQ 416

	public void setAVCStatus(String status)
		throws WBException, WBSQLException {
		try {
			m_clearance.setAVCStatus(status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	public void setAVCStatusStarted() throws WBException, WBSQLException {
		try {
			m_clearance.setAVCStatusStarted();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	//Commenting below method as direct call to generateMovieAVCExceptions is made for AVC batch job
	/*	public LinkedList getOrderDetailsFeaturesList(long orderDetailsId,long featureId, WBCalendar startdt, WBCalendar enddt) throws WBException,WBSQLException
		{
			try
			 {
				return m_clearance.getOrderDetailsFeaturesList(orderDetailsId,featureId, startdt, enddt);
			 }
			catch(RemoteException e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
	
	
		}
	*/
	public void generateMovieAVCExceptionsForEachWindow(
		long progId,
		long featureId,
		long season,
		long customerId)
		throws WBException {
		try {
			m_clearance.generateMovieAVCExceptionsForEachWindow(
				progId,
				featureId,
				season,
				customerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public String fetchBatchJobStatus() throws WBException, WBSQLException {
		try {
			return m_clearance.fetchBatchJobStatus();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getAVCRecords() throws WBException, WBSQLException {

		try {
			return m_clearance.getAVCRecords();
		} catch (WBSQLException e) {
			throw e;
		} catch (WBException e) {
			throw e;
		} catch (Exception e) {
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}

	}

	//End of code added for CQ 416

	public EventVO getEvent(EventKey key) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getEvent(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void saveEventTimePeriodList(EventVO eventVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.saveEventTimePeriodList(eventVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public LinkedList<Object> getAllAffiliations() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getAllAffiliations();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void saveAffiliationList(String[] array, long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.saveAffiliationList(array, eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public LinkedList<Object> retrieveMarketsForState(
		String stateCode,
		boolean needDropDowns)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.retrieveMarketsForState(
				stateCode,
				needDropDowns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getAllMarkets() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			LinkedList<Object> list = m_clearance.getAllMarkets();
			logger.info(Constants.END);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getEventMarkets(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getEventMarkets(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*
	 * Modified By      : Infosys (Deepika Kuhite)
	 * Modified Date    : 07-NOV-2004,20-Nov-2004,9-Dec-2004,15-Dec-2004
	 * SR Number        : 195,198,200,209,211,184,318,270,279.
	 * Purpose          : Three new methods are added for SR#195,198,200
	 *						One new method added to fix SR#209,211
	 *                    Two new methods added for SR#184.
	 *                   One new method added for SR#318.
	 *                   One new method added for SR#270.
	 * 					 SR#257-The method isOutsideAllowedAiringWindow modified.
	 *                   Sr#279-One new method generateMovieAVCExceptions added
	 * */

	/*code added for SR#195,198,200*/
	//This method is required to get markets for an event.
	//This method overrides an existing method.
	public LinkedList<Object> getEventMarkets(long eventId, boolean needAsDropdown)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getEventMarkets(eventId, needAsDropdown);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	//This method is required to delete markets of an event from EVENT_MARKET table.
	public void deleteEventMarkets(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.deleteEventMarkets(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	//This method is required to delete time periods for an event
	//from EVENT_MARKET_TIME_PERIOD table.
	public void deleteEventMarketTimePeriod(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.deleteEventMarketTimePeriod(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}
	/*end of code added for SR#195,198,200*/

	public LinkedList<Object> getEventMarkets(long eventId, String stateCode)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getEventMarkets(eventId, stateCode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void saveEventMarkets(LinkedList<Object> eventMarketsList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.saveEventMarkets(eventMarketsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	/* Added by (Infosys) Jitendra to reduce redundant calls to EventLoad */
	public LinkedList<Object> getEventMarketTimePeriods(EventVO eventVO, long dmaId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getEventMarketTimePeriods(eventVO, dmaId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getEventMarketTimePeriods(long eventId, long dmaId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getEventMarketTimePeriods(eventId, dmaId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void deleteEventMarkets(LinkedList<Object> eventMarketsList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.deleteEventMarkets(eventMarketsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public void saveEventMarketTimePeriods(
		long eventId,
		long dmaId,
		LinkedList<Object> eventMarketTimePeriodsList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.saveEventMarketTimePeriods(
				eventId,
				dmaId,
				eventMarketTimePeriodsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}
	/******************************** End Rebecca's Events ****************************************************/
	/**
	 * returns a list of all Past Events
	*/
	/*	public LinkedList getPastEventList() throws WBException
		{
			try
				{
				return new LinkedList();
			}
			catch (RemoteException e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
	
		}*/

	// Insert New Event

	/*	public EventKey getNewEvent(EventListCreateVO m_vo) throws WBException
		{
	
			try
				{
				return m_clearance.getnewEvent(m_vo);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}*/

	// EJB3 annotation added, directly calling Business interface
	/*public ClearanceFacadeRemote getClearanceFacadeRemote() throws Exception {
		logger.info(Constants.START);

		if (m_clearance == null) {
			try {
				m_clearance = ClearanceHomes.getClearanceFacadeHome().create();
			} catch (RemoteException re) {
				logger.error(re);
				throw new Exception(re.getMessage());
			} catch (Exception ce) {
				logger.error(ce);
				// 	WBLogger.logtoFile().error(ce );
				throw new Exception(ce.getMessage());
			}
		}
		logger.info(Constants.END);
		return m_clearance;

	}*/

	// Time Period

	/*	public LinkedList geteventtimeperiod(EventTimePeriodVO mvo) throws WBException
		{
			LinkedList lltimeperiod = null;
			try
				{
				lltimeperiod = m_clearance.geteventtimeperiod(mvo);
				if (lltimeperiod != null)
					{
					return lltimeperiod;
				}
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
			return lltimeperiod;
		}*/

	// Insert       Event Time Period
	/*	public void createeventtimeperiod(LinkedList lltimeperiod) throws WBException
		{
	
			try
				{
				//				m_clearance.createeventtimeperiod(lltimeperiod);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*	public void updateeventtimeperiod(LinkedList lltimeperiod) throws WBException
		{
	
			try
				{
				//				m_clearance.updateeventtimeperiod(lltimeperiod);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	
		// Event Affiliation
		// get EventAffiliation List
	*/
	/*	public LinkedList getEventAffiliationList(EventAffiliationVO m_affl)
			throws WBException
		{
	
			try
				{
				return m_clearance.getEventAffiliationList(m_affl);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*	public void createEventAffiliationList(LinkedList lltimeperiod)
			throws WBException
		{
			try
				{
				m_clearance.createEventAffiliationList(lltimeperiod);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
	
		}*/

	/*	public void updateEventAffiliationList(LinkedList lltimeperiod)
			throws WBException
		{
			try
				{
				m_clearance.updateEventAffiliationList(lltimeperiod);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	// Event States Market
	/*	public void createEventStateMarketList(LinkedList ll) throws WBException
		{
	
			try
				{
				m_clearance.createEventStateMarketList(ll);
	
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
	
		}
	*/
	/*	public void updateEventStateMarketList(LinkedList ll) throws WBException
		{
	
			try
				{
				m_clearance.updateEventStateMarketList(ll);
	
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*	public String createneweventname(EventListCreateVO eventlist)
			throws WBException
		{
			try
				{
				//				return m_clearance.createneweventname(eventlist);
				return "";
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
	
		}
	*/
	// Event Time Zone
	/*	public LinkedList geteventtimezone(
			EventTimeZoneVO etzvo,
			long eventid,
			long dmaid)
			throws WBException
		{
	
			try
				{
				return m_clearance.geteventtimezone(etzvo, eventid, dmaid);
	
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*	public void createeventtimezone(LinkedList lltimezone, String streventid)
			throws WBException
		{
	
			try
				{
				//				m_clearance.createeventtimezone(lltimezone,streventid);
	
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*	public LinkedList getstation(long dmaid) throws WBException
		{
			try
				{
				//				return m_clearance.getstation(dmaid);
				return new LinkedList();
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*	public void updateeventtimezone(LinkedList lltimezone, String streventid)
			throws WBException
		{
			try
				{
				//			m_clearance.updateEventTimeZone(lltimezone,streventid);
			}
			catch (Exception e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBException(e);
			}
		}
	*/
	/*******************  PREAIR, POSTAIR, AVC  *******************************/

	/*	public LinkedList getDiscrepancyList()
		 throws WBException, WBSQLException
		{
	
			try
				{
				return m_clearance.getDiscrepancyList(); //uncomment later
				//return null;
			}
			catch (RemoteException e)
				{
				WBLogger.error("ClearanceWorker.class", e);
				throw new WBEJBException(e);
			}
		}
	*/
	//Method added by Infosys(Sirish) for fetching the inbound count in inbound_file_log table
	public int getInboundFileCount(WBCalendar startDateOfWeek)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getInboundFileCount(startDateOfWeek);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of method added
	public DiscrepancyVO getDiscrepancy(DiscrepancyKey key)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getDiscrepancy(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getDiscrepancy(
		ProgramSeasonCustKey key,
		WBCalendar startDate,
		String discrepancySource)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getDiscrepancy(
				key,
				startDate,
				discrepancySource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public DiscrepancyVO getDiscrepancy(
		ProgramSeasonCustKey key,
		WBCalendar date,
		WBCalendar time,
		String discrepancySource)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getDiscrepancy(
				key,
				date,
				time,
				discrepancySource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateDiscrepancy(
		com.wb.stars.discrepancies.DiscrepancyVO vo,
		boolean clearDiscrepancy)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			m_clearance.updateDiscrepancy(vo, clearDiscrepancy);
			//uncomment later
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/*Modified by Infosys(Sirish) for fixing SR 370_2 for LMA req.*/
	public LinkedList<PreAirExceptionViewVO> getPreAirExceptionView(
		boolean includeFeatures,
		CustomerKey key,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreAirExceptionsByCustomer(
				includeFeatures,
				key,
				cntStnAndProductsMap);
			//uncomment later
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of modified method*/

	/*Modified by Infosys(Sirish) for fixing SR 370_2 for LMA req.*/
	public LinkedList<Object> getPreAirUndoResolutionList(
		CustomerKey key,
		boolean includeFeatures,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			/*	return m_clearance.getPreAirUndoResolutionList(
					key,
					includeFeatures);*/
			return m_clearance.getPreAirUndoResolutionList(
				key,
				includeFeatures,
				cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of modified method*/
	public PreemptionVO createPreemption(PreemptionVO vo)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.createPreemption(vo);
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updatePreemption(PreemptionVO vo)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {

			m_clearance.updatePreemption(vo);
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//Code added by Infy(Sarbani) for CQ1446
	public void updatePreemption(LinkedList<Object> list)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {

			m_clearance.updatePreemption(list);
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//End of code added by Infy(Sarbani)
	public void createMakeGood(MakeGoodVO vo)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			m_clearance.createMakeGood(vo);
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void createAdditionalTelecast(AdditionalTelecastVO additionalTelecast)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			m_clearance.createAdditionalTelecast(additionalTelecast);
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/*Modified by Infosys(Sirish); passing cntStnAndProductsMap also as a parameter to the method*/
	public PostAirExceptionViewVO getPostAirExceptionView(
		CustomerKey key,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			//return m_clearance.getPostAirExceptionsByCustomer(key);
			return m_clearance.getPostAirExceptionsByCustomer(
				key,
				cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//code added for CQ 2240 for fetching all preemptions and make goods in one go
	public HashMap<Long, PreemptionVO> getPreemptionVOsMap(String programPreemptionIdsList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionVOsMap(programPreemptionIdsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//end of code added for CQ 2240

	/*End of modified method*/
	/*Modified by Infosys(Sirish); passing cntStnAndProductsMap also as a parameter to the method*/
	public LinkedList<Object> getPostAirUndoResolutionList(
		CustomerKey key,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			//return m_clearance.getPostAirUndoResolutionList(key);
			return m_clearance.getPostAirUndoResolutionList(
				key,
				cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of modified method*/
	public PostAirExceptionVO savePostAirException(PostAirExceptionVO postAirExceptionVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.savePostAirExceptionVO(postAirExceptionVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> retrievePostAirRequest(boolean includePast)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.retrievePostAirRequest(includePast);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PostAirRequestVO retrievePostAirRequestVO(long postAirRequestId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.retrievePostAirRequestVO(postAirRequestId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void removePostAirRequestVO(long postAirRequestId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.removePostAirRequestVO(postAirRequestId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public PostAirRequestVO savePostAirRequest(PostAirRequestVO postAirRequestVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.savePostAirRequest(postAirRequestVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*Modified by Infosys(Sirish) for fixing SR 370_2 for LMA req.*/
	public LinkedList<ActualContractualViewVO> getActualContractualView(
		CustomerKey key,
		boolean missingTPOnly,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);

			//return m_clearance.getActualContractualByCustomer(
			//	key,
			//	missingTPOnly);

			return m_clearance.getActualContractualByCustomer(
				key,
				missingTPOnly,
				cntStnAndProductsMap);
			//uncomment later
			//return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of modified for fixing SR 370_2*/

	public void updatePreAir(PreAirVO preAirVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			m_clearance.updatePreAir(preAirVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public PreAirVO getPreAirVO(long preAirId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreAirVO(preAirId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PreAirVO getPreAirVO(
		long progId,
		long custId,
		WBCalendar date,
		WBCalendar time)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreAirVO(progId, custId, date, time);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateEventExceptions(EventExceptionsVO eventExceptionsVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			m_clearance.updateEventExceptions(eventExceptionsVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/*Modified by Infosys(Sirish) for fixing SR 370_2 for LMA req.*/
	public LinkedList<ActualContractualViewVO> getAllTimePeriods(
		CustomerKey key,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			//return m_clearance.getAllTimePeriods(key);
			return m_clearance.getAllTimePeriods(key, cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//code added for CQ 2240 to overload the method getDiscrepancyProductStations for filtering volume
	//products and products with season less than minimum season with pre air discrepancies
	public HashMap<Long, String> getDiscrepancyProductStations(
		long customerId,
		boolean isFromPreAir)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getDiscrepancyProductStations(
				customerId,
				isFromPreAir);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//end of code added for CQ 2240
	/*End of modified for fixing SR 370_2*/
	/* Modified By    : Infosys (Sirish C Reddy)
	 * Modified Date  : 07-Apr-2005
	 * SR Number      : 380_2
	 * Purpose        : Calling the stored procedure for fetching the combination of
	 * 					contracted station id and list of products.
	 *
	 */
	public HashMap<Long, String> getDiscrepancyProductStations(long customerId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getDiscrepancyProductStations(customerId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of added method*/
	/******************* END PREAIR, POSTAIR, AVC  *******************************/

	/******************* START PREEMPTION  *******************************/
	//Code added by Infy(Sarbani) to pass another parameter for proper updation of preemptions on preemption management screen
	//Code modified by Infy(sarbani) to remove the parameter added earlier
	public PreemptionViewVO getPreemptionView(ProgramSeasonCustKey key)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionView(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PreemptionViewVO getPreemptionView(CustomerKey key)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionView(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//Method added by Infy(Sarbani) for SR#370.2
	//Infosys:CQ 2230 Changed the parameter from CustomerKey to PreemptKey
	public PreemptionViewVO getPreemptionView(
		PreemptKey key,
		HashMap<Object, Object> cntStnAndProductsMap)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionView(key, cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of method added by Infy(Sarbani)

	public PreemptionVO getPreemptionVO(PreemptionKey key)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionVO(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//Method added by Infosys(Sirish) for LMA req
	public LinkedList<Object> getPreemptionVO(long mkPreemptId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionVO(mkPreemptId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of code added

	//Method added by Infy to enter correct mk_Good_nbr to program_preemption table
	public PreemptionVO getPreemptionVO(
		String customerId,
		String progId,
		String progSeas,
		WBCalendar premptDate,
		WBCalendar premptTime,
		long mkGoodNbr)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getPreemptionVO(
				customerId,
				progId,
				progSeas,
				premptDate,
				premptTime,
				mkGoodNbr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of method added by Infy
	/******************* END PREEMPTION  *******************************/

	/******************* START EVENT MANAGMENT  *******************************/
	/*Modified the method by Infosys(Sirish) for fixing SR 370_2 for LMA req.*/
	public EventExceptionViewVO getEventExceptionView(
		CustomerKey key,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			//return m_clearance.getEventExceptionView(key);
			return m_clearance.getEventExceptionView(key, cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of modified method for fixing SR 370_2

	public EventExceptionsVO getEventExceptionsVO(EventExceptionsKey key)
		throws WBException, WBSQLException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getEventExceptionsVO(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*Modified for LMA req SR 370_2*/
	public LinkedList<Object> getEventExceptionUndoResolutionList(
		CustomerKey key,
		HashMap<Long, String> cntStnAndProductsMap)
		throws WBException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getEventExceptionUndoResolutionList(
				key,
				cntStnAndProductsMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/*End of modified method*/
	/******************* END EVENT MANAGMENT  *******************************/

	/******************* START EVENT PROCESSING  *******************************/
	public void generateExceptionListForEvent(long eventId)
		throws WBException {
		logger.info(Constants.START);

		try {
			m_clearance.generateExceptionListForEvent(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void generateEventExceptionByCust(long custId) throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.generateEventExceptionByCust(custId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/** Added By   : Infosys(Prasanth)
	 *  Added Date : 29-Dec-2004
	 *  Purpose    : To invoke Event Engine as a stored Procedure
	*/

	// Modified by (Puneet) to update orderHeaderId in Event_Exception table
	/*Modified by Infosys(Sirish) for SR 370_2*/
	public void generateEventExceptionByCust(
		long custId,
		String stations,
		long progId,
		long programmeId)
		throws WBException {
		logger.info(Constants.START);
		try {
			/*
			m_clearance.generateEventExceptionByCust(
				custId,
				progId,
				programmeId);*/

			m_clearance.generateEventExceptionByCust(
				custId,
				stations,
				progId,
				programmeId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//End of modified method
	public void deleteEventExceptionList(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.deleteEventExceptionList(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void blockTimePeriod(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.blockTimePeriod(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void deleteBlockedTimePeriods(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.deleteBlockedTimePeriods(eventId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/******************* END EVENT PROCESSING  *******************************/

	/******************* START PREEMPTION PROCESSING  *******************************/
	//CQ 3196 - Code added by Bhuvaneshwari for providing error message on Duplicate Preemptions Starts
	public EventVO checkForEventConflict(
		long custId,
		WBCalendar airDate,
		WBCalendar airTime)
	//Commented to revert CQ3196 String program_length)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.checkForEventConflict(custId, airDate, airTime);
			//Commented to revert CQ3196	program_length);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String checkForProgramConflict(long custId,
	//Commented to revert CQ3196	String program_length,
	WBCalendar airDate, WBCalendar airTime)
			throws WBException, WBSQLException {
	
			try {
				logger.info(Constants.END);
				return m_clearance.checkForProgramConflict(
					custId,
					airDate,
					airTime);
			} catch (WBException e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
	}
	//Commented to revert CQ3196
	/*public String checkForProgramConflict(
		long custId,
		String program_length,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {

		try {
			logger.info(Constants.END);
			return m_clearance.checkForProgramConflict(
				custId,
				program_length,
				airDate,
				airTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//CQ 3196 - Ends
	*/
	/* Modified By   : Infosys(Kshitindra Jain)
	 * Modified Date : 01-March-2005
	 * SR Number     : 249
	 * Purpose       : To get the conflicting schedules
	 */

	public LinkedList<Object> checkForProgramConflict(
		long custId,
		WBCalendar airDate,
		WBCalendar airTime,
		String dummy)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.checkForProgramConflict(
				custId,
				airDate,
				airTime,
				dummy);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* Modified By   : Infosys(Kshitindra Jain)
	 * Modified Date : 2-March-2005
	 * SR Number     : 249
	 * Purpose       : To update the make good airing from premption screen additional telecast
	 */

	public void updateMakeGood(long premptLineNbr, MakeGoodVO makeGoodVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.updateMakeGood(premptLineNbr, makeGoodVO);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*End of code added for SR#249*/

	public boolean validAiringOfProgram(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.validAiringOfProgram(
				custId,
				progId,
				season,
				airDate,
				airTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean barterIsFull(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.barterIsFull(
				custId,
				progId,
				season,
				airDate,
				airTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean barterIsFullForHiatus(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.barterIsFullForHiatus(
				custId,
				progId,
				season,
				airDate,
				airTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean isProductDoubleBarter(long progId, long custId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isProductDoubleBarter(progId, custId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long getContractForProductStation(long progId, long customerId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getContractForProductStation(progId, customerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean isDateTimeContracted(
		long orderDetailsId,
		WBCalendar date,
		WBCalendar time)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isDateTimeContracted(orderDetailsId, date, time);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//The method modified for SR#257.
	//Name changed to isOutsideAllowedAiringWindowTP from isOutsideAllowedAiringWindow
	//parameters changed from WBCalendar,WBCalendar,WBCalendar to OrderHeaderVO,
	//WBCalendar,WBCalendar,long.
	public boolean isOutsideAllowedAiringWindowTP(
		OrderHeaderVO orderHeaderVO,
		WBCalendar mgDate,
		WBCalendar mgTime,
		long progId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isOutsideAllowedAiringWindowTP(
				orderHeaderVO,
				mgDate,
				mgTime,
				progId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//end of method modified for SR#257.

	public void addProgramToSchedule(ScheduleVO scheduleVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.addProgramToSchedule(scheduleVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/** Code added by Infosys(Siva Puvvada) for SR#396 */
	public String addBarterToProgram(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime,
		String barterMins,
		String finalBarterType)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.addBarterToProgram(
				custId,
				progId,
				season,
				airDate,
				airTime,
				barterMins,
				finalBarterType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info(Constants.END);
			throw new WBEJBException(e);
		}

	}
	/** End of code added for SR# 396 */

	public String addBarterToProgram(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime,
		String newBarterCd)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.addBarterToProgram(
				custId,
				progId,
				season,
				airDate,
				airTime,
				newBarterCd);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info(Constants.END);
			throw new WBEJBException(e);
		}

	}

	public String addBarterToProgramForHiatus(
		long custId,
		long airCustId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime,
		String newBarterCd)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.addBarterToProgramForHiatus(
				custId,
				airCustId,
				progId,
				season,
				airDate,
				airTime,
				newBarterCd);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info(Constants.END);
			throw new WBEJBException(e);
		}

	}

	public void preemptProgram(ScheduleVO scheduleVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.preemptProgram(scheduleVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void undoPreemptProgram(
		long progId,
		long season,
		long custId,
		long preemptID)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.undoPreemptProgram(progId, season, custId, preemptID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//Passing one more parameter by Infy(Sarbani)
	//Code modified by Infy(Sarbani) to fix CQ#1619
	public void undoDoubleBarter(PreemptionVO preemptionVO)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.undoDoubleBarter(preemptionVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//End of code modified for CQ#1619
	public void undoDoubleBarterForHiatus(
		long custId,
		long airCustId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime,
		double barterMins)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.undoDoubleBarterForHiatus(
				custId,
				airCustId,
				progId,
				season,
				airDate,
				airTime,
				barterMins);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void undoAdditionalTelecast(
		long progId,
		long season,
		long custId,
		WBCalendar date,
		WBCalendar time)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.undoAdditionalTelecast(
				progId,
				season,
				custId,
				date,
				time);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public ScheduleVO searchSchedule(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchSchedule(
				custId,
				progId,
				season,
				airDate,
				airTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ScheduleVO searchSchedule(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime,
		boolean isActive)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchSchedule(
				custId,
				progId,
				season,
				airDate,
				airTime,
				isActive);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//Method added by Infy(Sarbani) for CQ 2058.
	//Infosys(11/01): Added tmPeriodNbr as another parameter for CQ 1797
	public ScheduleVO searchSchedule(
		ProgramSeasonCustKey key,
		WBCalendar premptDate,
		long statNbr,
		long lineNbr,
		long tmPeriodNbr,
		boolean shwBrtrFlg)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchSchedule(
				key,
				premptDate,
				statNbr,
				lineNbr,
				tmPeriodNbr,
				shwBrtrFlg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of method added by Infy(Sarbani)

	//Method added by Infy(Sarbani) for CQ#1619
	public ScheduleVO searchSchedule(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		long statNbr,
		boolean isActive)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchSchedule(
				custId,
				progId,
				season,
				airDate,
				statNbr,
				isActive);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//Method added by Infy(Sarbani) for CQ 1822
	public ScheduleVO searchSchedule(long preemptionId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchSchedule(preemptionId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End of method added by Infy(Sarbani)
	/**
	 *
	 * @param searchParaVO
	 * @return
	 * @throws WBException
	 * @throws WBSQLException
	 *
	 * The method modified for SR#399 By Deepika
	 */
	public ScheduleVO searchScheduleForHiatus(ScheduleVO searchParaVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchScheduleForHiatus(searchParaVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End Of Code

	public BarterVO searchBarter(long barterTypeId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchBarter(barterTypeId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//Code Added to fix SR 249
	public int searchBarterCode(String barterCode)
		throws WBException, WBSQLException, RemoteException {
		logger.info(Constants.START);
		logger.info(Constants.END);
		return m_clearance.searchBarterCode(barterCode);

	}
	//End of code to fix SR 249
	public void updateSchedule(
		long progId,
		long season,
		long premptCustId,
		long mkCustId,
		long preemptID,
		MakeGoodVO makeGoodVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.updateSchedule(
				progId,
				season,
				premptCustId,
				mkCustId,
				preemptID,
				makeGoodVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/******************* END PREEMPTION PROCESSING  *******************************/

	/******************* START SCHEDULE PROCESSING  *******************************/
	public void generateSchedule(LinkedList<Object> scheduleEntryList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateSchedule(scheduleEntryList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void generateSchedule() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateSchedule();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void generateMovieSchedule() throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.generateMovieSchedule();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void generateSchedule(long progId, long seas, long custId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateSchedule(progId, seas, custId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public ScheduleVO getScheduleVO(ScheduleKey key)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getScheduleVO(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateSchedule(ScheduleVO scheduleVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.updateSchedule(scheduleVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void deleteTPFromSchedule(LinkedList<Object> scheduleEntryList)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.deleteTPFromSchedule(scheduleEntryList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/******************* END SCHEDULE PROCESSING  *******************************/

	/******************* START PRE AIR PROCESSING  *******************************/
	/*Modified by Infosys(Sirish) for fixing SR 370_2 */
	public void generatePreAirExceptions(
		long progId,
		boolean isFeature,
		long season,
		long customerId,
		WBCalendar startOfWeek,
		String stations,
		boolean isFromEnginesMenu)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			/*m_clearance.generatePreAirExceptions(
				progId,
				isFeature,
				season,
				customerId,
				startOfWeek);*/
			m_clearance.generatePreAirExceptions(
				progId,
				isFeature,
				season,
				customerId,
				startOfWeek,
				stations,
				isFromEnginesMenu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/*End of modified method*/

	public void generatePreAirExceptions(WBCalendar startOfWeek)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generatePreAirExceptions(startOfWeek);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/******************* END PRE AIR PROCESSING  *******************************/

	/******************* START AVC PROCESSING  *******************************/

	public LinkedList<String> generateActualContractualExceptions(
		long progId,
		long season,
		long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.generateActualContractualExceptions(
				progId,
				season,
				customerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//START: Added code for CQ1931 to run AVC for program and season from engines screen
	public void generateActualContractualExceptions(long progId, long season)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.generateActualContractualExceptions(progId, season);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//END: Code added for 1931

	/* Modified By   : Infosys(Puneet Chopra)
	 * Modified Date : 09-Feb-2005
	 * SR Number  	 : 39
	 * Purpose       : To update Schedule table with latest
	 *                 orderHeaderId on running of AVC engine
	*/

	//Modified By Deepika on 12-Feb-2005 to update for Volume Products.
	public void updateOrderHeaderIdONFR(long progId, long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.updateOrderHeaderIdONFR(progId, customerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateOrderHeaderIdVOL(long progId, long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.updateOrderHeaderIdVOL(progId, customerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//End of added code

	public LinkedList<Object> generateMovieAVCExceptions(
		long progId,
		long featureId,
		long season,
		long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.generateMovieAVCExceptions(
				progId,
				featureId,
				season,
				customerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void generateMovieAVCExceptions()
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateMovieAVCExceptions();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void generateActualContractualExceptions()
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateActualContractualExceptions();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/******************* END AVC PROCESSING  *******************************/

	/******************* START POST AIR PROCESSING  *******************************/
	public void generatePostAirExceptions(
		long progId,
		boolean isFeature,
		long season,
		long customerId,
		WBCalendar startOfWeek)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.generatePostAirExceptions(
				progId,
				isFeature,
				season,
				customerId,
				startOfWeek);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void generatePostAirExceptions(WBCalendar startOfWeek)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.generatePostAirExceptions(startOfWeek);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	/******************* END PRE AIR PROCESSING  *******************************/

	public LinkedList<Object> searchStationMaster()
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.searchStationMaster();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*	public void validatePreAir()
		{
			try
			{
				m_clearance.validatePreAir();
			}
			catch (RemoteException e)
			{
				WBLogger.getReference().logError(getClass(), e);
			}
	
		}
	
		public void validatePostAir()
		{
			try
			{
				m_clearance.validatePostAir();
			}
			catch (RemoteException e)
			{
				WBLogger.getReference().logError(getClass(), e);
			}
	
		}
	
		public void validateContract()
		{
			try
			{
				m_clearance.validateContract();
			}
			catch (RemoteException e)
			{
				WBLogger.getReference().logError(getClass(), e);
			}
	
		}
	*/
	public void createCustomer(CustomerTestView customerView)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.createCustomer(customerView.getCustomer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void updateCustomer(CustomerTestView customerView)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.updateCustomer(customerView.getCustomer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void deleteCustomer(CustomerTestView customerView)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.deleteCustomer(customerView.getCustomer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public CustomerTestView loadCustomer(long customerId) throws WBException {
		logger.info(Constants.START);
		try {
			CustomerTestView view = new CustomerTestView();
			view.setCustomer(m_clearance.loadCustomer(customerId));
			logger.info(Constants.END);
			return view;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AbstractProductVO getProductVO(long productId)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getProductVO(productId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationMasterVO findCustomerByPK(StationMasterKey key)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.findCustomerByPK(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findFeaturesById(long featureId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.findFeaturesById(featureId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long getVolumeForFeatureId(
		long featureId,
		long stationMasterId,
		WBCalendar date)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getVolumeForFeatureId(
				featureId,
				stationMasterId,
				date);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//Code modified by Infosys(Sarbani) to return all the records as Liknked List
	public LinkedList<Object> findTimePeriodConflict(
		long airCustId,
		String dow,
		WBCalendar startTime,
		WBCalendar endTime,
		WBCalendar effDate,
		WBCalendar endDate)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.findTimePeriodConflict(
				airCustId,
				dow,
				startTime,
				endTime,
				effDate,
				endDate);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//	Changed the return type to LinkedList from MovieActualTimeVO on 02/07/2005 By Deepika.
	public LinkedList<Object> findMovieTimePeriodConflict(
		long airCustId,
		WBCalendar startTime,
		WBCalendar endTime,
		WBCalendar airDate,
		long progSeas)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.findMovieTimePeriodConflict(
				airCustId,
				startTime,
				endTime,
				airDate,
				progSeas);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public TimePeriodHeaderVO findFutureTimePeriodConflicts(
		long progId,
		long season,
		long custId,
		WBCalendar effDate)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.findFutureTimePeriodConflicts(
				progId,
				season,
				custId,
				effDate);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getStationSalespeople(long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getStationSalespeople(customerId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//CQ 3115 - Code added by Bhuvaneshwari Starts

	public LinkedList<Object> getStationSalesContacts(long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getStationSalesContacts(customerId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//CQ 3115 - Ends

	public LinkedList<Object> getStationContacts(long customerId) throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getStationContacts(customerId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public DmaData getStationRankAndNTI(long customerId) throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getStationRankAndNTI(customerId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* Added By   : Infosys(Pavani Kunchala)
	 * Added Date : 27-Dec-2004
	 * SR Number  : 269,269.1,336
	 * Purpose    : To display the Syndication Seasons for the product selected
	*/
	// code added to fix SR 269,269.1,336
	public LinkedList<Object> getSyndicationSeasons(long productId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getSyndicationSeasons(productId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//	Added getSeasonsForProduct() for SR#269
	public LinkedList<Object> getSeasonsForProduct(long productId)
		throws WBException, WBSQLException, RemoteException {
		logger.info(Constants.START);

		logger.info(Constants.END);
		return m_clearance.getSeasonsForProduct(productId);

	}
	//	end of code added to fix SR 269,269.1,336

	public SyndicationSeasonVO getSyndicationSeason(
		long productId,
		long season)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			LinkedList<Object> syndicationSeasonList =
				m_clearance.getSyndicationSeason(productId, season);
			if (syndicationSeasonList.size() > 0) {
				logger.info(Constants.END);
				return (SyndicationSeasonVO) syndicationSeasonList.getFirst();
			}

			logger.info(Constants.END);
			return new SyndicationSeasonVO();
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<OrderHeaderVO> getOrderHeaderForProductStation(
		long productId,
		long custId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getOrderHeaderForProductStation(
				productId,
				custId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void createBoTrgrClr(BoTrgrClrVO boTrgrClrVO)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.createBoTrgrClr(boTrgrClrVO);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/**
	 * Business Objects Functions
	 */
	public void callBOBaseProduct(
		long progId,
		long progSeas,
		long stationMasterId,
		long orderHeaderId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callBOBaseProduct(
				progId,
				progSeas,
				stationMasterId,
				orderHeaderId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void callProductBOUpdatesFromTPSave(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callProductBOUpdatesFromTPSave(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void callFeatureBOUpdatesFromTPSave(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callFeatureBOUpdatesFromTPSave(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callProductBOUpdatesFromPreAir(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callProductBOUpdatesFromPreAir(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callFeatureBOUpdatesFromPreAir(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callFeatureBOUpdatesFromPreAir(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callProductBOUpdatesFromPostAir(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callProductBOUpdatesFromPostAir(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callFeatureBOUpdatesFromPostAir(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callFeatureBOUpdatesFromPostAir(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callProductBOUpdatesFromPreemption(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callProductBOUpdatesFromPreemption(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callFeatureBOUpdatesFromPreemption(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callFeatureBOUpdatesFromPreemption(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callProductBOUpdatesFromEventException(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callProductBOUpdatesFromEventException(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void callFeatureBOUpdatesFromEventException(
		long progId,
		long progSeas,
		long stationMasterId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callFeatureBOUpdatesFromEventException(
				progId,
				progSeas,
				stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	public void callBOUpdatesFromEventWizard(long eventId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.callBOUpdatesFromEventWizard(eventId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> getActivePreAirWeek()
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getActivePreAirWeek();
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> exportNielsen(NielsenExportLogVO vo)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.exportNielsen(vo);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*
	 * Modified By      : Infosys (Kranthi Pavan)
	 * Modified Date    : 15-Nov-2004
	 * SR Number        : 293
	 * Purpose          : To show the division name along with Sales Person name
	 **/
	/*Modified code by Infosys for CQ:48*/
	public LinkedList<Object> getDivisionName(String salesPersonName, long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getDivisionName(salesPersonName, customerId);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}
	/*End of Modified code by Infosys for CQ:48*/
	//end of Code added for SR#293

	// code added to fix SR#209,211
	// To show conflicts of features with ON/FR
	public LinkedList<Object> findMovieTimePeriodConflictWithONFR(
		long airCustId,
		WBCalendar startTime,
		WBCalendar endTime,
		WBCalendar dateEff,
		WBCalendar dateEnd,
		String dow)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.findMovieTimePeriodConflictWithONFR(
				airCustId,
				startTime,
				endTime,
				dateEff,
				dateEnd,
				dow);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}

	// end of code added to fix SR#209,211

	//code added for SR#279.
	//This method is added to generate AVC Exceptions
	//for product,station and season.

	public void generateMovieAVCExceptions(
		long progId,
		long season,
		long customerId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.generateMovieAVCExceptions(progId, season, customerId);

		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}
	//end of code added for SR#279.

	//Added code by Infosys (Punet) for SR#176

	public void generateMovieAVCExceptions(
		LinkedList<Object> modifiedFeatureList,
		long progSeas,
		long customerId,
		long programId)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.generateMovieAVCExceptions(
				modifiedFeatureList,
				progSeas,
				customerId,
				programId);

		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}

	//End of added code

	//code added for SR#270.
	//This method retrieves hiatus info for a given product,station and season.
	public LinkedList<Object> getHiatusInfo(long progId, long custId, long season)
		throws WBException {
		logger.info(Constants.START);

		try {
			logger.info(Constants.END);
			return m_clearance.getHiatusInfo(progId, custId, season);

		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}

	}
	//end of code added for SR#270.

	/*
	 * Modified By      : Infosys (Kranthi Pavan)
	 * Modified Date    : 10-Dec-2004
	 * SR Number        : 176
	 * Purpose          : Included the changes to manually clear AVC Discrepancies
	**/

	public void clearAvcDiscrepancy(DiscrepancyVO vo) throws WBException {
		try {
			logger.info(Constants.END);
			m_clearance.clearAvcDiscrepancy(vo);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}
	//end of code added for SR#176

	//code added for SR#184.
	//To find the maximum end date of the windows available for a feature.

	/*Commenting the below method as a new method getMaxDateForWindowsSeason is
	*  created by Siva on  10/23/06 for CQ 2216*/

	/*	public WBCalendar getMaxDateForWindows(long orderDetailsId, long featureId)
			throws WBException
		{
			try
			{
				logger.info(Constants.END);
				return m_clearance.getMaxDateForWindows(orderDetailsId, featureId);
			}
			catch (RemoteException e)
			{
				logger.error("ClearanceWorker.class", e);
				throw new WBEJBException(e);
			}
	
		} */

	//This method is required to find the orderDetailsId for a product and station.

	public LinkedList<Object> getOrderDetailsByProductStation(
		long productId,
		long custId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getOrderDetailsByProductStation(
				productId,
				custId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//end of code added for SR#184.

	/*
	 * Modified By      : Infosys (Kranthi Pavan)
	 * Modified Date    : 18-Dec-2004
	 * SR Number        : 273
	 * Purpose          : Added new method to remove redundant records in the schedule table upon
	 * 					  save on PTP screen
	**/
	public void deleteSchedule(LinkedList<Object> hiatusDaysList) throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			m_clearance.deleteSchedule(hiatusDaysList);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//End of code added for SR#273

	/*
	 * Modified By      : Infosys (Kranthi Pavan)
	 * Modified Date    : 18-Dec-2004
	 * SR Number        : 23,39,210
	 * Purpose          : Run AVC Engine upon change in the field order/contract details
	**/

	public AVCKey fetchProductStationDetails(AVCKey AVCkey)
		throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.fetchProductStationDetails(AVCkey);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getCurrentSyndicationSeason(long productId)
		throws WBException, RemoteException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getCurrentSyndicationSeason(productId);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}

	/*Added code by Infosys (Puneet) for SR#39*/
	public LinkedList<Object> getCurrentOrNextSynSeason(long productId)
		throws WBException, RemoteException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getCurrentOrNextSynSeason(productId);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}

	//End of added code

	public boolean isTPPresentforONFR(long progId, long custId, long progSeas)
		throws WBException, RemoteException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isTPPresentforONFR(progId, custId, progSeas);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}
	//End of code added for SR#39

	//added for SR#257.
	public OrderDetailsVO getOrderDetailsForHeader(
		long orderHeaderId,
		long progId)
		throws WBException, RemoteException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getOrderDetailsForHeader(orderHeaderId, progId);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}
	}
	//end of code added for SR#257.

	/*
	 * Modified By      : Infosys (Ravi Kumar)
	 * Modified Date    : 19-Jan-2005
	 * SR Number        : 356
	 * Purpose          : To incorporate 'Program will not be Preempted' functionality
	**/

	// Start of code added for SR 356
	public ScheduleVO getPreemptedAiringOfProgram(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getPreemptedAiringOfProgram(
				custId,
				progId,
				season,
				airDate,
				airTime);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}
	//Overloaded method added by Infosys(Sirish) for fixing S1Usr00001836
	public ScheduleVO getPreemptedAiringOfProgram(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime,
		long preemptionId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getPreemptedAiringOfProgram(
				custId,
				progId,
				season,
				airDate,
				airTime,
				preemptionId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	//Added one more parameter to the method by Infy(Sarbani) for SR#370.4
	public void revokeSchedule(ScheduleVO scheduleVO, boolean status)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.revokeSchedule(scheduleVO, status);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}
	// End of code added for SR 356

	//Added By Deepika on 31/01/2005
	public long getMaxStatForFeature(
		long station,
		long product,
		long season,
		long feature)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getMaxStatForFeature(
				station,
				product,
				season,
				feature);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}
	//End of code added.

	//Added By Deepika on 02/04/2005
	public WBCalendar getMaxDateForFeatureRule(long productId, long featureId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getMaxDateForFeatureRule(productId, featureId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}

	//End of Code Added.

	/*     Modified By   : Infosys(Kshitindra Jain)
		 * Modified Date : 18-Feb-2005
		 * SR Number     : 249
		 * Purpose       : To get the schedule id for preemption id
	
	*/

	public long getScheduleIdByPreemption(long preemptionId)
		throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			return m_clearance.getScheduleIdByPreemption(preemptionId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	/*End of code added by Kshitindra*/

	//code added for CQ 2216 to fetch the maximum end date of the rules for a given season
	public WBCalendar getMaxDateForFeatureRuleSeason(
		long productId,
		long featureId,
		WBCalendar syndStartDate,
		WBCalendar syndEndDate)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getMaxDateForFeatureRuleSeason(
				productId,
				featureId,
				syndStartDate,
				syndEndDate);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}

	//code added for CQ 2216 to fetch the maximum end date of the feature windows for a given season
	public WBCalendar getMaxDateForWindowsSeason(
		long orderDetailsId,
		long featureId,
		WBCalendar syndStartDate,
		WBCalendar syndEndDate)
		throws WBException {
		try {
			logger.info(Constants.END);
			return m_clearance.getMaxDateForWindowsSeason(
				orderDetailsId,
				featureId,
				syndStartDate,
				syndEndDate);
		} catch (WBEJBException e) {
			logger.error("ClearanceWorker.class", e);
			throw new WBEJBException(e);
		}

	}
	//end of code added for CQ 2216

	//Method added by Infy(Sarbani) for LMA req.
	public MakeGoodVO getMakeGoodVO(
		long customerId,
		long progId,
		long season,
		WBCalendar mkDate,
		WBCalendar mkTime)
		throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			return m_clearance.getMakeGoodVO(
				customerId,
				progId,
				season,
				mkDate,
				mkTime);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	//Added by Infosys(Sirish) for LMA req.
	public OrderHeaderVO getContractDetails(long progId, long stationId)
		throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			return m_clearance.getContractDetails(progId, stationId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}
	//End of added method
	//Code added by Vemula Sumalatha for SC59
	public OrderHeaderVO getContractDetails(
		ProgramSeasonCustKey key,
		SyndicationSeasonVO vo)
		throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			return m_clearance.getContractDetails(key, vo);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}
	//End of code added by Vemula Sumalatha for SC59

	//	Method Added by Prasanth for Refreshing MQT MAX_TM_PERIOD
	public void refreshMaxTmPeriod() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {

			m_clearance.refreshMaxTmPeriod();
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	// End of added method
	// Code added by Infosys(Siva Puvvada) for SR#397
	public boolean isBarterPresent(long productId, String barterCode)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isBarterPresent(productId, barterCode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//End of code added by Infosys(Siva Puvvada) for SR#397

	//	Code added by Infosys(Siva Puvvada) for SR#395
	public String checkForConflict(
		WBCalendar preemptDate,
		WBCalendar preemptTime,
		long customerId,
		long seasonId)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.checkForConflict(
				preemptDate,
				preemptTime,
				customerId,
				seasonId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//End of code added by Infosys(Siva Puvvada) for SR#395

	//Code added by Infosys(Sarbani) for SR#370.1
	public LinkedList<Object> getStationType(long customerId, long progId)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.getStationType(customerId, progId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//End of code added by Infosys(Sarbani) for SR#370.1
	//Code Added to Fix DNA
	public long getRunNbrForRunDatesId(long runDatesId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getRunNbrForRunDatesId(runDatesId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	public long getOrderDetailsFeaturesId(long runDatesId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getOrderDetailsFeaturesId(runDatesId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}
	//End of Code

	public String getBarter(long barter) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getBarter(barter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	// Start of code chnages for CQ923
	public void updateClearanceForDeSelLicStn(
		long orderDetailsId,
		long stationMasterId,
		long lmaId)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.updateClearanceForDeSelLicStn(
				orderDetailsId,
				stationMasterId,
				lmaId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}
	//End of code chnages for CQ923

	/*
	 * Added By      : Infosys (Ravi)
	 * Added Date    : 29-Apr-2005
	 * SR Number     : 370_4
	 * Purpose       : Calls DB2 SP to update all non-contracted clearace data when new contact is created
	**/
	public void updateClearanceWithNewContractData(
		long orderHeaderId,
		long stationMasterId)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.updateClearanceWithNewContractData(
				orderHeaderId,
				stationMasterId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}
	//	End of code added by Infosys (Ravi)

	public boolean validAiringOfProgramForHiatus(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.validAiringOfProgramForHiatus(
				custId,
				progId,
				season,
				airDate,
				airTime);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	/*
	 * Added By      : Infosys
	 * Added Date    : 20-Apr-2006
	 * CQ Number     : 923
	**/
	public LinkedList<Object> updateClearanceContractWithRelatedStation(
		LinkedList<Object> relatedlst,
		long stationMasterId)
		throws WBException {
		logger.info(Constants.START);
		LinkedList<Object> prodstnlst = null;
		try {
			prodstnlst =
				m_clearance.updateClearanceContractWithRelatedStation(
					relatedlst,
					stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

		return prodstnlst;
	}
	//	End of code added by Infosys

	/**
	 * 04/14/05 Infosys(Pavan) Changed the mass AVC Run to iterate over products.
	 */
	public LinkedList<String> getDistinctProductForTimePeriods()
		throws WBException, WBSQLException, Exception {
		logger.info(Constants.START);
		try {
			return m_clearance.getDistinctProductForTimePeriods();
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}

	public LinkedList<Object> getDistinctMovieTPProducts()
		throws WBException, WBSQLException, Exception {
		logger.info(Constants.START);
		try {
			return m_clearance.getDistinctMovieTPProducts();
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}
	/**
	 * End of code added for mass AVC Run
	 */

	/**
		 * 04/14/2005 Added by Infosys (Pavan) for mass AVC Run
		 * @param progId
		 * @throws WBException
		 * @throws WBSQLException
		 */
	public void generateMovieAVCExceptions(long progId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateMovieAVCExceptions(progId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/**
		 * 04/14/2005 Added by Infosys (Pavan) for mass AVC Run
		 * @param progId
		 * @throws WBException
		 * @throws WBSQLException
		 */
	public void generateActualContractualExceptions(long progId)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			m_clearance.generateActualContractualExceptions(progId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/**
	 * @author Deepika_Kuhite
	 *
	 */

	public void generateScheduleONFR(
		long progId,
		long custId,
		long season,
		long statNbr,
		long tpNumber,
		long productType)
		throws WBSQLException, WBException, Exception {
		logger.info(Constants.START);
		try {
			m_clearance.generateScheduleONFR(
				progId,
				custId,
				season,
				statNbr,
				tpNumber,
				productType);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public void generateScheduleVOL(
		long volumeProductId,
		long featureId,
		long season,
		long customerId,
		long statNbr,
		long timePeriodNbr)
		throws WBSQLException, WBException, Exception {

		logger.info(Constants.START);
		try {
			m_clearance.generateScheduleVOL(
				volumeProductId,
				featureId,
				season,
				customerId,
				statNbr,
				timePeriodNbr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	/** Code added by Infosys(Siva Puvvada) for CQ#397 */
	public PreemptionVO getChangeBarterPreemptionVO(
		String customerId,
		String progId,
		String progSeas,
		WBCalendar premptDate,
		WBCalendar premptTime)
		throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			return m_clearance.getChangeBarterPreemptionVO(
				customerId,
				progId,
				progSeas,
				premptDate,
				premptTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/** End of code added  for CQ#397 */

	/*
	 * Added By      : Infosys (Sundeep)
	 * Added Date    : 19-Jul-2005
	 * CQ Number     : 1832
	 * Purpose       : Calls DB2 SP to list conflicts on remove
	**/
	public LinkedList<Object> getRemoveConflictsONFR(TimePeriodHeaderKey headerKey)
		throws WBException, WBSQLException, RemoteException {
		logger.info(Constants.START);
		try {
			return m_clearance.getRemoveConflictsONFR(headerKey);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getRemoveConflictsVOL(
		long volumeProductId,
		long featureId,
		long season,
		long customerId,
		long statNbr,
		long timePeriodNbr)
		throws WBException, WBSQLException, RemoteException {
		logger.info(Constants.START);
		try {
			return m_clearance.getRemoveConflictsVOL(
				volumeProductId,
				featureId,
				season,
				customerId,
				statNbr,
				timePeriodNbr);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//	End of code added by Infosys (Sundeep)

	/**
	 * @author Deepika_Kuhite
	 * @param 	WBCalendar startDate,
				WBCalendar endDate,
				long progId,
				long contractedStation,
				long season,
				long stat,
				String onlyMakeGood
	 * Added method for SR#399.
	 *
	 */
	public LinkedList<Object> generateScheduleOnHiatusRevoke(
		WBCalendar startDate,
		WBCalendar endDate,
		long progId,
		long contractedStation,
		long season,
		long stat,
		String onlyMakeGood)
		throws WBException {
		logger.info(Constants.START);
		try {
			return m_clearance.generateScheduleOnHiatusRevoke(
				startDate,
				endDate,
				progId,
				contractedStation,
				season,
				stat,
				onlyMakeGood);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * @author Deepika_Kuhite
	 * @param 	long progId,
				long custId,
				long season,
				long statNbr,
				long timePeriodNbr,
				WBCalendar effectiveDate,
				WBCalendar endDate
	 * Added method for SR#399.
	 *
	 */
	public void updateScheduleWithTmPeriodNbr(
		long progId,
		long custId,
		long season,
		long statNbr,
		long timePeriodNbr,
		WBCalendar effectiveDate,
		WBCalendar endDate)
		throws WBException {
		logger.info(Constants.START);
		try {
			m_clearance.updateScheduleWithTmPeriodNbr(
				progId,
				custId,
				season,
				statNbr,
				timePeriodNbr,
				effectiveDate,
				endDate);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/*
	 * End Of added code.
	 */

	/*Code added By (Infosys)Deepika for CQ1698*/
	public boolean activeTPsForStationExist(long stationMasterId)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.activeTPsForStationExist(stationMasterId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/*End Of added code.*/

	/*
	 * Added By   	: Infosys (Nageswararao_C)
	 * Added Date 	: 29-Nov-2005
	 * SR Number   : NA
	 * CQ#        	: 1882
	 * Purpose     : To enable the program will not be preempted option for
	 * 			  preemptions made on event which are deleted at a later stage.
	 */

	/**
	 * Checks whether an event is present or not
	 * @param eventId
	 * @return
	 * @throws WBSQLException
	 * @throws WBException
	 */
	public boolean isEventExists(long eventId)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isEventExists(eventId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*
				* Added By   	: Infosys (KshitindraK_Jain)
				* Added Date 	: 23-Jan-2006
				* SR Number   : NA
				* CQ#        	:STARS00001982
				* Purpose     : To enable the program will not be preempted option for
				* 			     preemptions made on preair when the pre air data is reimported .
				*/

	/**
		* Checks whether an event is present or not
		* @param eventId
		* @return
		* @throws WBSQLException
		* @throws WBException
		*/
	public boolean isPreAirPreemptionInvalid(long preemptionId)
		throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return m_clearance.isPreAirPreemptionInvalid(preemptionId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//END of code added towards STARS00001982

	//END of code added towards CQ1882

	//code added by Siva for CQ 2438

	//SC 16: LTC only for current time period number
	public void inactivateScheduleLTC(
		long progId,
		long custId,
		long season,
		long statNbr,
		long tmPeriodNbr,
		WBCalendar ltcDate)
		throws WBSQLException, WBEJBException {
		logger.info(Constants.START);
		try {

			m_clearance.inactivateScheduleLTC(
				progId,
				custId,
				season,
				statNbr,
				tmPeriodNbr,
				ltcDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} 
		/*catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}*/
		logger.info(Constants.END);

	}

	public void activateScheduleForLTCOverrides(
		long progId,
		long custId,
		long season,
		long statNbr,
		long timePeriodNbr,
		long productType,
		WBCalendar ltcDate)
		throws WBSQLException, WBEJBException {
		logger.info(Constants.START);
		try {

			m_clearance.activateScheduleForLTCOverrides(
				progId,
				custId,
				season,
				statNbr,
				timePeriodNbr,
				productType,
				ltcDate);
		} 
		/*catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}*/ 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}
	//end of code added
	//CQ 2274: Code added to get LMAs added in Clearance
	public HashMap<Object, Object> getClearanceLMARecursive(long customerId, long progId)
		throws WBSQLException, RemoteException {
		try {
			return m_clearance.getClearanceLMARecursive(customerId, progId);
		} catch (WBSQLException e) {
			logger.error("Error occurred: ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error occurred: ", e);
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	//CQ 2274: End of Code added to get LMAs added in Clearance
	//CQ 2274: Create Email Request
	public void createEmailRequest(
		long emailTypeId,
		String mailSubject,
		String mailBody,
		long createdId)
		throws WBSQLException, RemoteException {
		m_clearance.createEmailRequest(
			emailTypeId,
			mailSubject,
			mailBody,
			createdId);
	}
	//CQ 2274: Create Email Request Ends
	/*Code added by Infosys (Suma) for SC 16 on 04/15/2008 */
	public void updateActiveTPNbr(TimePeriodHeaderKey key) throws WBException {
		logger.info(Constants.START);
		try {

			m_clearance.updateActiveTPNbr(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//End of Code added by Infosys (Suma) for SC 16
	//CQ #2060-Starts
	public boolean isPreemptionPresent(
		long stationMasterId,
		long progID,
		long progSeas,
		WBCalendar premptDate,
		WBCalendar airTime)
		throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			return m_clearance.isPreemptionPresent(
				stationMasterId,
				progID,
				progSeas,
				premptDate,
				airTime);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}
	}
	//CQ #2060-Ends
	//Code added by Vemula Sumalatha for SC68 
	public int getArchivedSeason() throws WBSQLException, WBException {
		logger.info(Constants.START);
		try {
			return m_clearance.getArchivedSeason();
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} finally {
			logger.info(Constants.END);
		}

	}
	//End of code added by Vemula Sumalatha for SC68 
	
	/*
	 * Code for Pre-Air AutoPopulate -START
	 * Added by Bhamathi Pai 
	 * Date: 3 Jul 2017
	 */

	/**
	 * Method to search for schedules for given program Id, programSeason, CustomerId(station).
	 * @ProgramSeasonCustKey key 
	 * @WBCalendar calDate - date for which schedule is required 
	 * @WBCalendar calTime 
	 */
	public ScheduleVO searchSchedule(ProgramSeasonCustKey pKey,WBCalendar calDate,WBCalendar calTime)
			throws WBException, WBSQLException {
			logger.info(Constants.START);
			try {
				logger.info(Constants.END);
				return m_clearance.searchSchedule(pKey,calDate, calTime);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
	}
	
	/**
	 * Method to search for schedules in Related Station (licensee station) 
	 * for given program Id, programSeason, CustomerId(station).
	 * @ProgramSeasonCustKey key 
	 * @WBCalendar calDate - date for which schedule is required 
	 * @WBCalendar calTime
	 */
	public ScheduleVO searchRelatedStationSchedule(ProgramSeasonCustKey pKey,WBCalendar calDate,WBCalendar calTime)
			throws WBException, WBSQLException {
			logger.info(Constants.START);
			try {
				logger.info(Constants.END);
				return m_clearance.searchRelatedStationSchedule(pKey,calDate, calTime);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
	}
	
	/**
	 * Method to search TimePeriods in Station/Related Station
	 * for given program Id, programSeason, CustomerId(station).
	 * @ProgramSeasonCustKey key 
	 * @WBCalendar calDate - date for which TimePeriod is required 
	 * @WBCalendar calTime 
	 */
	public ScheduleVO searchTimePeriods(ProgramSeasonCustKey pKey,WBCalendar calDate,WBCalendar calTime)
			throws WBException, WBSQLException {
			logger.info(Constants.START);
			try {
				logger.info(Constants.END);
				return m_clearance.searchTimePeriods(pKey,calDate, calTime);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
	}
	
	/**
	 * Method to search Closest TimePeriods in Station/Related Station (licensee station) 
	 * for given program Id, programSeason, CustomerId(station).
	 * @ProgramSeasonCustKey key 
	 * @WBCalendar calDate - date for which TimePeriod is required 
	 * @WBCalendar calTime
	 */
	public ScheduleVO searchClosestTimePeriods(ProgramSeasonCustKey pKey,WBCalendar calDate,WBCalendar calTime)
			throws WBException, WBSQLException {
			logger.info(Constants.START);
			try {
				logger.info(Constants.END);
				return m_clearance.searchClosestTimePeriods(pKey,calDate, calTime);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
	}
	
	/*
	 * Code for Pre-Air AutoPopulate -END
	 */
}
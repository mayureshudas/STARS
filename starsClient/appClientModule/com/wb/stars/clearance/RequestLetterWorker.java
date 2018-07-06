/* Modified By     : Infosys ( Prasanth Nandanuru ) 
 * Modified Date   : 4-Nov-2004  
 * Purpose         : Added Error Handlers in 
 * public/default/protected methods for exception handling*/


package com.wb.stars.clearance;

//import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;

//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.naming.NamingException;

//import com.wb.stars.clearance.ejb.clearanceFacade.ClearanceFacadeHome;
import com.wb.stars.clearance.ejb.clearanceFacade.ClearanceFacadeRemote;
import com.wb.stars.clearance.timeperiod.TimePeriodEntryViewVO;
import com.wb.stars.common.AbstractProductVO;
import com.wb.stars.common.Constants;
import com.wb.stars.common.CustomerVO;
import com.wb.stars.common.ProductVO;
import com.wb.stars.common.StationMasterKey;
import com.wb.stars.common.StationMasterVO;
import com.wb.stars.common.SyndicationSeasonVO;
import com.wb.stars.common.simple.OrderDatesVO;
import com.wb.stars.common.simple.ProgramSeasonCustKey;
import com.wb.stars.common.simple.SeasonVO;
import com.wb.stars.control.log.StarsLogger;
//import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.utils.DropDownRecord;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCalendar;
import com.wb.stars.utils.WBException;

public class RequestLetterWorker
{
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	/**
	 * Manages interaction with Clearance beans
	 */

	//private ClearanceFacadeRemote m_clearance = null;

	//final String EJBTIERCONTROLLER_EJBHOME = "ejb/com/wb/stars/clearance/ejb/clearanceFacade/ClearanceFacadeHome";
	//final String EJBTIERCONTROLLER_EJBHOME = "ejb/com/wb/stars/clearance/ejb/facade/ClearanceFacadeHome";
	//@EJB
	private ClearanceFacadeRemote m_Clearance;

	// EJB3 annotation added, directly calling Business interface
	public RequestLetterWorker() throws WBException
	{

		try
		{
			//get a reference to the security object
			//this.m_Clearance = getClearanceFacadeRemote();
			Context ctxt = new InitialContext();
			this.m_Clearance = (ClearanceFacadeRemote) ctxt.lookup(JNDINames.CLEARANCE_FACADE_BUSINESS_REMOTE);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
		logger.info(Constants.CREATED);

	}

	// EJB3 annotation added, directly calling Business interface
	/*public ClearanceFacadeRemote getClearanceFacadeRemote() throws Exception
	{
		logger.info(Constants.START);

		if (m_Clearance == null)
		{
			try
			{
				m_Clearance = getClearanceFacadeHome().create();

			}
			catch (RemoteException re)
			{
				logger.error(re.getMessage(), re);
				throw new Exception(re.getMessage());
			}
			catch (javax.naming.NamingException ne)
			{
				logger.error(ne.getMessage(), ne);
				throw new Exception(ne.getMessage());

			}
			catch (Exception ce)
			{
				logger.error(ce.getMessage(), ce);
				throw new Exception(ce.getMessage());
			}
		}
		logger.info(Constants.END);
		return m_Clearance;
	}*/

	// EJB3 annotation added, directly calling Business interface
	/*private ClearanceFacadeHome getClearanceFacadeHome() throws NamingException
	{
		logger.info(Constants.START);

		ClearanceFacadeHome qrhome =
			(ClearanceFacadeHome) EJBHomeFactory.getFactory().lookUpHome(
				JNDINames.CLEARANCE_FACADE,
				ClearanceFacadeHome.class);
		logger.info(Constants.END);
		return qrhome;

	}*/

	public LinkedList<Object> findProductsBySeason(SeasonVO seasonVO)
		throws WBException
	{
		logger.info(Constants.START);

		try
		{

			/*		  LinkedList result = new LinkedList();
					  Iterator i = m_Clearance.findProductsBySeason(seasonVO).iterator();
					  while(i.hasNext())
					  {
					  	ProductVO productVO = (ProductVO) i.next();
					  	result.add(new DropDownRecord((new Long(productVO.getID())).toString(),productVO.getName()));
					  }
			*/
			logger.info(Constants.END);
			return m_Clearance.findProductsBySeason(seasonVO);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}

	}
	public LinkedList<Object> findProductsByPackage(long packageID, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		logger.info(Constants.END);
		return new LinkedList<Object>();
		//		try{
		//		  LinkedList result = new LinkedList();
		//		  Iterator i = m_Clearance.findProductsByPackage(new PackageVO(packageID,0), depth).iterator();
		//		  while(i.hasNext())
		//		  {
		//		  	ProductVO productVO = (ProductVO) i.next();
		//		  	result.add(new DropDownRecord((new Long(productVO.getID())).toString(),productVO.getName()));
		//		  }
		//		  return result;
		//		}catch(Exception re){
		//			throw new WBException(re);
		//		}
	}

	public LinkedList<Object> findPackagesByAvlDate(WBCalendar date, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		logger.info(Constants.END);
		return new LinkedList<Object>();
		//		try{
		//		  LinkedList result = new LinkedList();
		//		  Iterator i = m_Clearance.findPackagesByAvlDate(date, depth).iterator();
		//		  while(i.hasNext())
		//		  {
		//		  	PackageVO packageVO = (PackageVO) i.next();
		//		  	result.add(new DropDownRecord((new Long(packageVO.getProductID())).toString(),packageVO.getPackageName()));
		//		  }
		//		  return result;
		//		}catch(Exception re){
		//			WBLogger.error("RequestLetterWorker.", re);
		//			throw new WBException(re);
		//		}
		//
	}

	public LinkedList<Object> findAllCustomers(boolean hasMarketInfo)
		throws WBException
	{
		logger.info(Constants.START);
		LinkedList<Object> result = new LinkedList<Object>();
		try
		{

			LinkedList<Object> allCustomers =
				m_Clearance.findAllCustomers(hasMarketInfo);
			Iterator<Object> i = allCustomers.iterator();
			while (i.hasNext())
			{
				CustomerVO customerVO = (CustomerVO) i.next();
				StringBuffer sb = new StringBuffer(customerVO.getCallLetter());
				sb.append(" (");
				sb.append(customerVO.getDmaDesc());
				sb.append(")");
				result.add(
					new DropDownRecord(
						(new Long(customerVO.getID())).toString(),
						sb.toString()));
			}

		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
		}
		logger.info(Constants.END);
		return result;
	}
	
	/*Code added By Infosys(Deepika) for CQ1698*/
	public LinkedList<Object> findAllActiveCustomers()
		throws WBException
	{
		logger.info(Constants.START);
		LinkedList<Object> result = new LinkedList<Object>();
		try
		{

			LinkedList<Object> allCustomers =
				m_Clearance.findAllActiveCustomers();
			Iterator<Object> i = allCustomers.iterator();
			while (i.hasNext())
			{
				CustomerVO customerVO = (CustomerVO) i.next();
				StringBuffer sb = new StringBuffer(customerVO.getCallLetter());
				sb.append(" (");
				sb.append(customerVO.getDmaDesc());
				sb.append(")");
				result.add(
					new DropDownRecord(
						(new Long(customerVO.getID())).toString(),
						sb.toString()));
			}

		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
		}
		logger.info(Constants.END);
		return result;
	}
	/*End of added code*/

	public CustomerVO findCustomerByID(CustomerVO customerVO, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findCustomerByID(customerVO, depth);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public CustomerVO findCustomerByID(long customerID, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findCustomerByID(
				new CustomerVO(customerID, null),
				depth);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public CustomerVO findCustomerByID(String sCustomerID, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);

			return m_Clearance.findCustomerByID(
				new CustomerVO(Long.parseLong(sCustomerID), null),
				depth);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	/*	public LinkedList findContractsByCustomer(CustomerVO customerVO) throws WBException{
			try{
				return m_Clearance.findContractsByCustomer( customerVO);
			}catch(java.rmi.RemoteException re){
				throw new WBException(re);
			}
		}
		public LinkedList findContractsByProduct(ProductVO productVO) throws WBException{
			try{
				return m_Clearance.findContractsByProduct(productVO);
			}catch(java.rmi.RemoteException re){
				throw new WBException(re);
			}
		}
	*/
	public RequestLetterVO submitLetterRequest(RequestLetterVO reqestLetterVO)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);

			return m_Clearance.submitLetterRequest(reqestLetterVO);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public RequestLetterVO generateRequestLetters(RequestLetterVO reqestLetterVO)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.generateRequestLetters(reqestLetterVO);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<DropDownRecord> findProductsByIDs(String[] productIDs, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			LinkedList<DropDownRecord> result = new LinkedList<DropDownRecord>();
			Iterator<Object> i =
				m_Clearance.findProductsByIDs(productIDs, depth).iterator();
			while (i.hasNext())
			{
				ProductVO productVO = (ProductVO) i.next();
				result.add(
					new DropDownRecord(
						(new Long(productVO.getID())).toString(),
						productVO.getName()));
			}
			logger.info(Constants.END);
			return result;
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}

	}

	public LinkedList<DropDownRecord> findCustomersByIDs(String[] customersIDs, int depth)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			LinkedList<DropDownRecord> result = new LinkedList<DropDownRecord>();
			Iterator<Object> i =
				m_Clearance.findCustomersByIDs(customersIDs, depth).iterator();
			while (i.hasNext())
			{
				CustomerVO customerVO = (CustomerVO) i.next();
				StringBuffer sb = new StringBuffer(customerVO.getCallLetter());
				sb.append(" (");
				sb.append(customerVO.getDmaDesc());
				sb.append(")");
				result.add(
					new DropDownRecord(
						(new Long(customerVO.getID())).toString(),
						sb.toString()));
			}
			logger.info(Constants.END);
			return result;
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}

	}

	public LinkedList<Object> getCurrentlyActiveProducts(long seasonId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);

			return m_Clearance.getCurrentlyActiveProducts(seasonId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getCurrentlyActiveVolumes() throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getCurrentlyActiveVolumes();
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> findVolumeFeaturesByProductId(long productId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findVolumeFeaturesByProductId(productId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> findContractedStationsWithNoTP(
		long productId,
		long seasonId,
		boolean isFeatures)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findContractedStationsWithNoTP(
				productId,
				seasonId,
				isFeatures);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

    // Added method passing an extra argument Product Rule Id
	public LinkedList<Object> findContractedStationsWithNoTP(
		long productId,
		long seasonId,
		boolean isFeatures, long productRuleId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findContractedStationsWithNoTP(
				productId,
				seasonId,
				isFeatures, productRuleId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public AbstractProductVO findProduct(long productId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getProductVO(productId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public StationMasterVO findStation(long stationId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findCustomerByPK(
				new StationMasterKey(stationId));
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getProductTelecasts(long productId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getProductTelecasts(productId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<TimePeriodEntryViewVO> getContractDefaults(
		ProgramSeasonCustKey key,
		long runType)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getContractDefaults(key, runType);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getFeatureWindowsForVolume(
		long productId,
		WBCalendar date)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getFeatureWindowsForVolume(productId, date);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getFeatureIdForProductRule(long productRuleId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getFeatureIdForProductRule(productRuleId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> findProductRule(long productRuleId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			boolean needTimePeriods = true;
			logger.info(Constants.END);
			return m_Clearance.findProductRule(productRuleId, needTimePeriods);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> findProductTerm(long productTermId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findProductTerm(productTermId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> findFeaturesById(long featureId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.findFeaturesById(featureId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getProductRunDescriptions(
		long productId,
		long customerId,
		long seasonId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getProductRunDescriptions(
				productId,
				customerId,
				seasonId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}
	public LinkedList<Object> getFeatureRunDescriptions(
		long productId,
		long featureId,
		long customerId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getFeatureRunDescriptions(
				productId,
				featureId,
				customerId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public ValidatorVO getValidatorForProduct(long productId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getValidatorForProduct(productId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public ValidatorVO getValidatorForMarket(long dmaId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getValidatorForMarket(dmaId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public ValidatorVO getValidatorForUserId(long userId) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getValidatorForUserId(userId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	/* Added By   : Infosys(Nageswararao_C)
	 * Added Date : 04-Jan-2005
	 * SR Number  : 299
	 * Purpose    : To fetch email IDs for sending the time period request letters
	 */
	public LinkedList<Object> getStationContactEmailsList(String stationCode)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getStationContactEmailsList(stationCode);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getStationContactFaxNumbersList(String stationCode)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getStationContactFaxNumbersList(stationCode);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public LinkedList<Object> getStationContactMailList(String stationCode)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getStationContactMailList(stationCode);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public void createNewTPRequestLetter(TimePeriodRequestLetterTransmitVO requestLetterVO)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			m_Clearance.createNewTPRequestLetter(requestLetterVO);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}
	public void sendTimePeriodEmails(String imagesPath) throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			m_Clearance.sendTimePeriodEmails(imagesPath);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}
	//End of code added to fix SR#299

	public SyndicationSeasonVO getSeasonInfoForProduct(
		String seasonId,
		long productId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getSeasonInfoForProduct(seasonId, productId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}
	public OrderDatesVO getOrderDatesVO(long productId, long stationMasterId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getOrderDatesVO(productId, stationMasterId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public OrderDatesVO getOrderDatesVOForEndDate(
		long productId,
		long stationMasterId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getOrderDatesVOForEndDate(
				productId,
				stationMasterId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public OrderDatesVO getOrderDatesVOForFeatures(
		long featureId,
		long productId,
		long stationMasterId,
		long productRuleId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getOrderDatesVOForFeatures(
				featureId,
				productId,
				stationMasterId,
				productRuleId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}
	public WBCalendar getExtendedTermEndDate(
		long productId,
		long stationMasterId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getExtendedTermEndDate(
				productId,
				stationMasterId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}
	}

	public String getNoOfEpisodesForON(long productId, String seasonId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return m_Clearance.getNoOfEpisodesForON(productId, seasonId);
		}
		catch (Exception re)
		{
			logger.error(re.getMessage(), re);
			throw new WBException(re);
		}

	}

	//START Added by Infosys (Nageswararao_C) to fix SR#350
	/**
	 * This method gives the start and end date for a syndication season
	 * @param seasonId
	 * @throws WBException
	 */
	public LinkedList<Object> getStartAndEndSyndicationSeasonDate(String seasonId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getStartAndEndSyndicationSeasonDate(seasonId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}

	public String getTVRating(long ratingCode) throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getTVRating(ratingCode);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<Object> getContractSummaryInfo(long productid) throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getContractSummaryInfo(productid);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<Object> getPreferredRulesList(
		long productId,
		String distributionSystemInClause)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getPreferredRulesList(
				productId,
				distributionSystemInClause);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<Object> getBarterSplitVOList(long productId, String inClause)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getBarterSplitVOList(productId, inClause);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<Object> getPreferredRulesList(long productRuleId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getPreferredRulesList(productRuleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<Object> getBarterSplitVOList(long productRuleId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getBarterSplitVOList(productRuleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<Object> getPreferredTimePeriods(long productRuleId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getPreferredTimePeriods(productRuleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	//END Added by Infosys (Nageswararao_C) to fix SR#350

	public LinkedList<Object> getProductRuleSimpleVO(long productRuleId)
		throws WBException
	{

		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getProductRuleSimpleVO(productRuleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	public LinkedList<TimePeriodRequestLetterTransmitVO> sendInstantEmails(
		LinkedList<TimePeriodRequestLetterTransmitVO> instantEmailsList,
		String imagesPath)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.sendInstantEmails(instantEmailsList, imagesPath);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}
	
	//Code added to fix SR#352
	public String getProductIdForFeature(String featureId) throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getProductIdForFeature(featureId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}
	//end of code added.
	
	public LinkedList<Object> getProductRulesForSameWindow(long productCode,WBCalendar startDate,WBCalendar endDate) throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_Clearance.getProductRulesForSameWindow(productCode,startDate,endDate);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}

}

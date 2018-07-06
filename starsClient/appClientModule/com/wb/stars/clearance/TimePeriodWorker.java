/* Modified By     : Infosys (Prasanth Nandanuru)
 * Modified Date   : 4-Nov-2004
 * Purpose         : Added Error Handlers in
 * public/default/protected methods for exception handling*/

package com.wb.stars.clearance;

import java.rmi.RemoteException;
import java.util.LinkedList;

//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.naming.NamingException;

import com.wb.stars.clearance.dao.db2.commands.MovieActualTimeWhereClause;
import com.wb.stars.clearance.dao.db2.commands.TimePeriodWhereClause;
//import com.wb.stars.clearance.ejb.clearanceFacade.ClearanceFacadeHome;
import com.wb.stars.clearance.ejb.clearanceFacade.ClearanceFacadeRemote;
import com.wb.stars.clearance.timeperiod.FutureTPKey;
import com.wb.stars.clearance.timeperiod.ProductNoContractVO;
import com.wb.stars.clearance.timeperiod.ProductStationCommentVO;
import com.wb.stars.clearance.timeperiod.TimePeriodEntryViewVO;
import com.wb.stars.clearance.timeperiod.TimePeriodHeaderKey;
import com.wb.stars.clearance.timeperiod.TimePeriodHeaderVO;
import com.wb.stars.clearance.timeperiod.TimePeriodMoviesKey;
import com.wb.stars.clearance.vo.PendingLMARelChangesVO;
import com.wb.stars.common.Constants;
import com.wb.stars.common.CustomerKey;
import com.wb.stars.common.StationMasterKey;
import com.wb.stars.common.StationMasterVO;
import com.wb.stars.common.SyndicationSeasonVO;
import com.wb.stars.common.dao.db2.commands.VolumeFeaturesLD;
import com.wb.stars.common.dao.db2.commands.VolumeFeaturesWhereClause;
import com.wb.stars.common.simple.ProgramSeasonCustKey;
import com.wb.stars.contracts.OrderHeaderVO;
import com.wb.stars.control.log.StarsLogger;
//import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.security.UserVO;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCalendar;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;

public class TimePeriodWorker
{
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	/**
	 * Manages interaction with Clearance beans
	 */

	//private ClearanceFacadeRemote m_clearance = null;

	//final String CLEARANCE_FACADE_HOME = "ejb/com/wb/stars/clearance/ejb/clearanceFacade/ClearanceFacadeHome";
	//final String CLEARANCE_FACADE_HOME = "java:comp/env/ejb/ClearanceFacadeHome";
	//@EJB
	private ClearanceFacadeRemote clearanceFacadeRemote;

	// EJB3 annotation added, directly calling Business interface
	public TimePeriodWorker() throws WBException
	{
		try
		{
			//get a reference to the security object
			//this.clearanceFacadeRemote = getClearanceFacadeRemote();
			Context ctxt = new InitialContext();
			this.clearanceFacadeRemote = (ClearanceFacadeRemote) ctxt.lookup(JNDINames.CLEARANCE_FACADE_BUSINESS_REMOTE);
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

		if (clearanceFacadeRemote == null)
		{
			try
			{
				clearanceFacadeRemote = getClearanceFacadeHome().create();

			}
			catch (RemoteException re)
			{
				//WBLogger.logtoFile().error(re );
				logger.error(re.getMessage(), re);
				throw new Exception(re.getMessage());
			}
			catch (javax.naming.NamingException ne)
			{
				//	WBLogger.logtoFile().error(ne );
				logger.error(ne.getMessage(), ne);
				throw new Exception(ne.getMessage());

			}
			catch (Exception ce)
			{

				logger.error(ce.getMessage(), ce);
				// 	WBLogger.logtoFile().error(ce );
				throw new Exception(ce.getMessage());
			}
		}
		logger.info(Constants.END);
		return clearanceFacadeRemote;
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

	//Modified the return type to long by Infy(Sarbani) for SR#370.1
	public long findIsProductContracted(ProgramSeasonCustKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.findIsProductContracted(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//Parameters passed modified by Infy(Sarbani) for SR#370.4
	public LinkedList<Object> findTimePeriodAssociations(ProgramSeasonCustKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.findTimePeriodAssociations(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findContractAssociations(long programId, long customerId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.findContractAssociations(
				programId,
				customerId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getTimePeriodEntryViewVOs(ProgramSeasonCustKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getTimePeriodEntryViewVOs(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* Modified By    : Infosys (Puneet Chopra)
	 * Modified Date  : 15-Dec-2004
	 * SR Number      : 295
	 * Purpose        : Added code for SR#295
	 * */

	// Added getInactiveTimePeriodEntryViewVOs() method for OF/FR products

	public LinkedList<Object> getInactiveTimePeriodEntryViewVOs(
		ProgramSeasonCustKey key,
		String futDateStr,
		String hisDateStr,
		String checkBoxState)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return clearanceFacadeRemote.getInactiveTimePeriodEntryViewVOs(
				key,
				futDateStr,
				hisDateStr,
				checkBoxState);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//	Added getInactiveTimePeriodMoviesViewVOs() method for Volume products
	public LinkedList<Object> getInactiveTimePeriodMoviesViewVOs(
		TimePeriodMoviesKey key,
		String futDateStr,
		String hisDateStr,
		String checkBoxState)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			LinkedList<Object> lst =
				clearanceFacadeRemote.getInactiveTimePeriodMoviesViewVOs(
					key,
					futDateStr,
					hisDateStr,
					checkBoxState);
			logger.info(Constants.END);
			return lst;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*End of added code for SR#295*/

	public LinkedList<TimePeriodEntryViewVO> getContractDefaults(
		ProgramSeasonCustKey key,
		long runType)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getContractDefaults(key, runType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getProductDefaults(ProgramSeasonCustKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getProductDefaults(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// This method gets the all the movies

	public LinkedList<Object> getTimePeriodMoviesViewVOs(TimePeriodMoviesKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();

			LinkedList<Object> lst =
				clearanceFacadeRemote.getTimePeriodMoviesViewVOs(key);
			logger.info(Constants.END);
			return lst;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void createUpdateTimePeriod(
		LinkedList<Object> timeperiodViewVO,
		ProgramSeasonCustKey programSeasonCustKey,
		boolean isFeature)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.createUpdateTimePeriod(
				timeperiodViewVO,
				programSeasonCustKey,
				isFeature);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> findProductsByCustomer(CustomerKey customerKey)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();

			return clearanceFacadeRemote.findProductsByCustomer(
				customerKey,
				true);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//changed the return type from void to LinkedList for SR#265.
	public LinkedList<Object> findTimePeriodHeader(
		ProgramSeasonCustKey programSeasonCustKey,
		long tpNumber,
		long statNamber)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			logger.info(Constants.END);
			return clearanceFacadeRemote.findTimePeriodHeader(
				programSeasonCustKey,
				tpNumber,
				statNamber);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	//changed the return type from void to LInkedList for SR#265.
	public LinkedList<Object> removeMovieTimePeriod(
		ProgramSeasonCustKey programSeasonCustKey,
		long featureId,
		long statNamber)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			logger.info(Constants.END);
			return clearanceFacadeRemote.removeMovieTimePeriod(
				programSeasonCustKey,
				featureId,
				statNamber);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void createProductNoContract(ProductNoContractVO vo)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.createProductNoContract(vo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void createLmaNoContract(LmaNoContractVO vo)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.createLmaNoContract(vo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void removeProductNoContract(ProductNoContractVO vo)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.removeProductNoContract(vo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> findProductRestrictionBarter(long productId)
	{
		logger.info(Constants.START);
		logger.info(Constants.END);
		//		try
		//		{
		//			clearanceFacadeRemote = getClearanceFacadeRemote()
		//			clearanceFacadeRemote.findProductRestrictionBarter(productId);
		//		}
		//		catch (Exception e)	{
		//			WBLogger.error("Caught Exception ",e);
		//			throw new WBException(e.getMessage());
		//		}
		return new LinkedList<Object>();
	}

	public LinkedList<Object> searchVolumeFeaturesByWhere(
		long productId,
		boolean needFeatures,
		boolean needFeatureWindows)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			VolumeFeaturesWhereClause featuresWhere =
				new VolumeFeaturesWhereClause();
			featuresWhere.setProductId(productId);
			VolumeFeaturesLD volumeFeaturesLD = new VolumeFeaturesLD();
			volumeFeaturesLD.setNeedFeatures(needFeatures);
			logger.info(Constants.END);
			return clearanceFacadeRemote.searchVolumeFeaturesByWhere(
				featuresWhere,
				volumeFeaturesLD);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findFeaturesDefaults(
		String[] featuresIds,
		long productId,
		long season,
		long customerId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.findFeaturesDefaults(
				featuresIds,
				productId,
				season,
				customerId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getTimePeriodStationComments(ProgramSeasonCustKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getTimePeriodStationComments(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void saveTimePeriodProductStationComments(ProductStationCommentVO vo)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.saveTimePeriodProductStationComments(vo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> getTimePeriodProductStationComments(
		long progId,
		long season,
		long stationId,
		long contractedSationId,
		boolean ptpNotesFlg)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			//CQ 2178: Changed signature
			//CQ 2424: Flag added to differentiate between PTP Notes and ProductStationComments
			return clearanceFacadeRemote.getTimePeriodProductStationComments(
				progId,
				season,
				stationId,
				contractedSationId,
				ptpNotesFlg);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findProductRule(
		long productRuleId,
		boolean needTimePeriods)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.findProductRule(
				productRuleId,
				needTimePeriods);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//Passed another parameter by Infy(Sarbani) to remove Volume products
	public boolean findIfTimePeriodsExist(
		ProgramSeasonCustKey pKey,
		long productType)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.findIfTimePeriodsExist(
				pKey,
				productType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean validAiringOfProgram(
		long custId,
		long progId,
		long season,
		WBCalendar airDate,
		WBCalendar airTime)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.validAiringOfProgram(
				custId,
				progId,
				season,
				airDate,
				airTime);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getFeatureForProductRule(long productRuleId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getFeatureForProductRule(
				productRuleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long getMaxEvenStatNbr(
		long progId,
		long season,
		long stationMasterId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getMaxEvenStatNbr(
				progId,
				season,
				stationMasterId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	public long getMaxOddStatNbr(
		long progId,
		long season,
		long stationMasterId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//clearanceFacadeRemote = getClearanceFacadeRemote();
			return clearanceFacadeRemote.getMaxOddStatNbr(
				progId,
				season,
				stationMasterId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationMasterVO findCustomerByPK(StationMasterKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return clearanceFacadeRemote.findCustomerByPK(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findTimePeriodHeaderByWhere(
		long progId,
		long seas,
		long custId,
		long statNbr,
		long timePeriodNbr)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			TimePeriodWhereClause where = new TimePeriodWhereClause();
			where.setByProgramSeasAndCust(progId, seas, custId);
			where.setStatNbr(statNbr);			
			where.setTimePeriodNbr(timePeriodNbr);
			logger.info(Constants.END);
			return clearanceFacadeRemote.findTimePeriodHeaderByWhere(where);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findMovieTimePeriodByWhere(
		long progId,
		long featureId,
		long seas,
		long custId,
		long statNbr,
		long timePeriodNbr)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			MovieActualTimeWhereClause where = new MovieActualTimeWhereClause();
			where.setProductId(progId);
			where.setFeatureId(featureId);
			where.setSeason(seas);
			where.setStationMasterId(custId);
			where.setStatNumber(statNbr);
			//where.setTimePeriodNumber(timePeriodNbr);
			logger.info(Constants.END);
			return clearanceFacadeRemote.findMovieTimePeriodByWhere(where);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SyndicationSeasonVO getSyndicationSeason(
		long productId,
		long season)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			LinkedList<Object> syndicationSeasonList =
				clearanceFacadeRemote.getSyndicationSeason(productId, season);

			if (syndicationSeasonList.size() > 0)
			{
				logger.info(Constants.END);
				return (SyndicationSeasonVO) syndicationSeasonList.getFirst();
			}

			logger.info(Constants.END);
			return new SyndicationSeasonVO();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long getContractForProductStation(long progId, long customerId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return clearanceFacadeRemote.getContractForProductStation(
				progId,
				customerId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderHeaderVO getOrderHeaderVO(long progId, long customerId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			LinkedList<OrderHeaderVO> orderHeaderList =
				clearanceFacadeRemote.getOrderHeaderForProductStation(
					progId,
					customerId);

			if (orderHeaderList.size() > 0)
			{
				logger.info(Constants.END);
				return (OrderHeaderVO) orderHeaderList.getFirst();
			}

			logger.info(Constants.END);
			return null;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* Modified By    : Infosys (Sarbani Pal)
	 * Modified Date  : 04-Jan-2005
	 * SR Number      : 263
	 * Purpose        : Added code for SR#263
	 * */

	//Added two new methods
	public TimePeriodHeaderKey getTimePeriodHeaderKey(ProgramSeasonCustKey key)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return clearanceFacadeRemote.getTimePeriodHeaderKey(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public TimePeriodHeaderVO getTimePeriodDetails(TimePeriodHeaderKey timePeriodHeaderKey)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return clearanceFacadeRemote.getTimePeriodDetails(
				timePeriodHeaderKey);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* Code Added By Deepika for SR#370.*/
	//	Code modified by Vemula Sumalatha for SC59
	public OrderHeaderVO getContractDetails(ProgramSeasonCustKey key, SyndicationSeasonVO vo)
		throws WBSQLException, WBException
	//	End of Code modified by Vemula Sumalatha for SC59
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			//	Code modified by Vemula Sumalatha for SC59
			return clearanceFacadeRemote.getContractDetails(
				key,
				vo);
			//	End of Code modified by Vemula Sumalatha for SC59
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/* End Of Code Added By Deepika for SR#370.*/
	//Code Added By Sarbani for SR#370.3
	public long getContractedStation(ProgramSeasonCustKey key)
		throws WBSQLException, WBException
	{
		logger.info(Constants.START);
		try
		{

			logger.info(Constants.END);
			return clearanceFacadeRemote.getContractedStation(key);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End Of Code Added By Sarbani for SR#370.3
	//Method added by Infy(Sarbani) to delete record from LMA_NO_CONTRACT on remove product
	public void removeLmaNoContract(ProgramSeasonCustKey pKey)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.removeLmaNoContract(pKey);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}
	//End of method added by Infy(Sarbani)

	//below method modified for CQ#1814; added uservo
	public void removeONFR(
		ProgramSeasonCustKey prodCustKey,
		long statNbrForRemove,
		long tpNumberForRemove,
		long productTypeId,
		LinkedList<Object> listOfSchedulesToUpdate,
		UserVO userVO)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);
		try
		{
			clearanceFacadeRemote.removeONFR(
				prodCustKey,
				statNbrForRemove,
				tpNumberForRemove,
				productTypeId,
				listOfSchedulesToUpdate,
				userVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void removeVOL(
		ProgramSeasonCustKey prodCustKey,
		long statNbrForRemove,
		long tpNumberForRemove,
		long productTypeId,
		long featureId)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);
		try
		{
			clearanceFacadeRemote.removeVOL(
				prodCustKey,
				statNbrForRemove,
				tpNumberForRemove,
				productTypeId,
				featureId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public long getMaxTimePeriod(
		long progId,
		long season,
		long custId,
		long statNbr)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);

		try
		{
			logger.info(Constants.END);
			return clearanceFacadeRemote.getMaxTimePeriod(
				progId,
				season,
				custId,
				statNbr);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public long getMaxMovieTimePeriod(
		ProgramSeasonCustKey programSeasonCustKey,
		long featureId,
		long statNbr)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);

		try
		{
			logger.info(Constants.END);
			return clearanceFacadeRemote.getMaxMovieTimePeriod(
				programSeasonCustKey,
				featureId,
				statNbr);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	//Infosys (Pavan) for CQ 3 1853
	public LinkedList<Object> getMinEffDateMaxEndDate(TimePeriodHeaderKey tpKey)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);

		try
		{
			logger.info(Constants.END);
			return clearanceFacadeRemote.getMinEffDateMaxEndDate(tpKey);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 *
	 * @param listOfMasterValueObject
	 * @param progId
	 * @param contractedStation
	 * @param progSeas
	 * @return
	 * @throws WBSQLException
	 * @throws WBException
	 * @throws Exception
	 * Added for CQ1859
	 */
	public LinkedList<Object> findTimePeriodConflicts(
		LinkedList<Object> listOfMasterValueObject,
		long progId,
		long contractedStation,
		long progSeas)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);

		try
		{
			logger.info(Constants.END);
			return clearanceFacadeRemote.findTimePeriodConflicts(
				listOfMasterValueObject,
				progId,
				contractedStation,
				progSeas);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/**
	 * End Of added code.
	 */

	/**
	 * Added by Deepika for CQ1637
	 */
	public boolean findIsProductAdded(ProgramSeasonCustKey programSeasonCustKey)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);

		try
		{
			logger.info(Constants.END);
			return clearanceFacadeRemote.findIsProductAdded(
				programSeasonCustKey);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/**
	 * End Of added code.
	 */
	/*
	 * Added By   	 : Infosys (Nageswararao_C)
	 * Added Date 	 : 25-Oct-2005
	 * SR Number     : NA
	 * CQ#        	 : 1777
	 * Purpose       : To prevent the user from overriding LTC and remove in the case of inactivate stations
	 */

	/**
	 * @param beansWithLtcOverridden
	 * @param contractedStationId
	 * @param progId
	 * @param progSeas
	 * @throws WBSQLException
	 * @throws WBException
	 * @throws Exception
	 */
	public LinkedList<Object> ltcOverrideInactiveStations(
		LinkedList<Object> beansWithLtcOverridden,
		long contractedStationId,
		long progId,
		long progSeas)
		throws WBSQLException, WBException, Exception
	{
		logger.info(Constants.START);

		try
		{
			logger.info(Constants.END);
			return clearanceFacadeRemote.ltcOverrideInactiveStations(
				beansWithLtcOverridden,
				contractedStationId,
				progId,
				progSeas);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	//End code added for 1777

	//(Arun) CQ1711 Deleting an LMA
	public void deleteLmaNoContract(
		LmaNoContractVO vo,
		LinkedList<Object> list,
		String detachAction)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			//clearanceFacadeRemote = getClearanceFacadeRemote();
			clearanceFacadeRemote.deleteLmaNoContract(vo, list, detachAction);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	//	(Arun) CQ1711 Deleting an LMA
	public LinkedList<Object> validateLmaNoContract(
		LmaNoContractVO vo,
		LinkedList<Object> list,
		String detachAction)
		throws WBException, WBSQLException
	{
		LinkedList<Object> validationData = new LinkedList<Object>();
		logger.info(Constants.START);
		try
		{

			validationData =
				clearanceFacadeRemote.validateLmaNoContract(
					vo,
					list,
					detachAction);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		finally
		{
			logger.info(Constants.END);
		}
		return validationData;
	}

	//(Arun) Code for getting Pending LMA Relationship Data CQ2251
	public LinkedList<Object> getPendingLMARelChangesData()
		throws WBException, WBSQLException
	{
		try
		{
			return clearanceFacadeRemote.getPendingLMARelChangesData();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		finally
		{
			logger.info(Constants.END);
		}

	}
	//Code to update table PENDING_LMA_CHANGES

	public void updatePendingLMARelChangesData(PendingLMARelChangesVO vo)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			clearanceFacadeRemote.updatePendingLMARelChangesData(vo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public void addLmaToContract(
		ProductNoContractVO vo,
		long contractedStationId,
		long productTypeId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			clearanceFacadeRemote.addLmaToContract(
				vo,
				contractedStationId,
				productTypeId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public void createPendingLMARelChangesData(PendingLMARelChangesVO vo)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			clearanceFacadeRemote.createPendingLMARelChangesData(vo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}
	public boolean hasActiveTimePeriods(LmaNoContractVO vo, long stationId)
		throws WBSQLException, RemoteException
	{
		try
		{
			return clearanceFacadeRemote.hasActiveTimePeriods(vo, stationId);
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	//Method to return if the station and product & season is present in Lma_no_contract table.
	//Method added by Bojja Kishore on 22nd March 2007 for 2251
	public boolean isLmaNoContract(ProgramSeasonCustKey key)
		throws WBSQLException, RemoteException
	{
		try
		{
			return clearanceFacadeRemote.isLmaNoContract(key);
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	public boolean volumeHasActiveTimePeriods(LmaNoContractVO vo, long stationId)
			throws WBSQLException, RemoteException
		{
			try
			{
				return clearanceFacadeRemote.volumeHasActiveTimePeriods(vo, stationId);
			}
			catch (Exception e)
			{
				throw new WBSQLException(
					"errors.excep.system.component",
					new String[] { this.getClass().getName()},
					e);
			}
		}
		
	//	CQ 2274: Add LMA Validation
	public boolean isLMAActiveLicStation(long contractedStationId, long progId, long seasonId, long lmaId) throws WBSQLException, RemoteException
	{
		try
		{			
			return clearanceFacadeRemote.isLMAActiveLicStation(contractedStationId,progId,seasonId,lmaId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			throw new WBSQLException(Constants.ERRORS_SQL_QUERY,new String[]{this.getClass().getName()},e);	
		}
	}
	//	CQ 2274: Ends
	
	//SC 16 Get Future time periods to load the screen
	public LinkedList<TimePeriodEntryViewVO> getFutureTPViewVOs(TimePeriodHeaderKey key)
		throws WBSQLException, RemoteException {
		try {
			return clearanceFacadeRemote.getFutureTPViewVOs(key);
		} catch (Exception e) {
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	
	//Save or remove Future Time Periods.
	public void saveRemoveFutureTP(FutureTPKey futureTPKey) throws WBSQLException, WBException, RemoteException
	{
		try 
		{
			clearanceFacadeRemote.saveRemoveFutureTP(futureTPKey);
			
		} catch (WBSQLException e) {
			logger.error(e);
			throw e;			
		}
	}
	//SC 16:  Get history time periods
	public String getHistoryRunDesc(TimePeriodHeaderKey tpKey) throws WBSQLException, RemoteException 
	{
		try 
		{
			return clearanceFacadeRemote.getHistoryRunDesc(tpKey);
		} catch (Exception e) {
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}

	//Check if the switch on LOOKUP_RUN_AVC_ENGINE is turned on	
	public boolean beginAVCEngineRun()
	{
		try
		{
			return clearanceFacadeRemote.beginAVCEngineRun();
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	
	//Get the produt,station and seasons list from AVC_BATCH_PROCESS_STATUS table
	public LinkedList<Object> fetchProgCustSeasList()
	{
		try
		{
			return clearanceFacadeRemote.fetchProgCustSeasList();
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
	}
	
	//Update the status on AVC_BATCH_PROCESS_STATUS table and turn off switch if needed
	public void updateAVCEngineStatus(ProgramSeasonCustKey key, String avcStatus,boolean avcComplete)
	{
		try
		{
			clearanceFacadeRemote.updateAVCEngineStatus(key,avcStatus,avcComplete);
		}
		catch (Exception e)
		{
			throw new WBSQLException(
				"errors.excep.system.component",
				new String[] { this.getClass().getName()},
				e);
		}
		
	}
	
	
	//SC 16 Ends

	/***
	 * Begin : CQ2826 : Marketing Comments
	 */
	
	public LinkedList<Object> getMarketingComments(
			long progId,
			long season,
			long stationId,
			long contractedSationId,
			int marketingCommentsFlag)
			throws WBException, WBSQLException{
			try{
				//Begin : CQ3031
				return clearanceFacadeRemote.getMarketingComments(
					progId,
					season,
					stationId,
					contractedSationId,
					marketingCommentsFlag);
				//End : CQ3031
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
		}
	/***
	* End : CQ2826 : Marketing Comments
	*/		
	/***
	 * Begin : CQ2826 : Marketing Comments
	 */
	public void updateMarketingComments(ProductStationCommentVO vo)
			throws WBException, WBSQLException
		{
			logger.info(Constants.START);
			try
			{

				//clearanceFacadeRemote = getClearanceFacadeRemote();
				clearanceFacadeRemote.updateMarketingComments(vo);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
			logger.info(Constants.END);
		}
	/***
	 * End : CQ2826 : Marketing Comments
	 */		
	
}

/* Modified By     : Infosys (Prasanth Nandanuru)
 * Modified Date   : 4-Nov-2004
 * Purpose         : Added Error Handlers in
 * * public/default/protected methods for exception handling

  Modified By      : Infosys (Sarbani Pal)
  Modified Date    : 22-Nov-2004
  SR Number		   : 75
  Purpose          : Passing one more parameter to method
					 updateCreateSalesTermsVOLViewVO().
 */

/*
 Modified By      : Infosys (Siva Naga)
 Modified Date    : 02-Dec-2004
 Purpose          : Code modified for SR #111
 */

package com.wb.stars.contracts;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

//import javax.ejb.CreateException;
//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.wb.stars.common.AddlTermsNoticeDateVO;
import com.wb.stars.common.CommentVO;
import com.wb.stars.common.Constants;
import com.wb.stars.common.ContractSearchVO;
import com.wb.stars.common.FeatureVO;
import com.wb.stars.common.OrderRunsSeasonsTermsVO;
import com.wb.stars.common.ProductTermLD;
import com.wb.stars.common.dao.db2.DB2CallLetterListDAO; //Code added  for CQ2824 by XDSRINIV
import com.wb.stars.common.dao.db2.commands.LookupSeasonLD;
import com.wb.stars.common.dao.db2.commands.LookupSeasonWhereClause;
import com.wb.stars.common.dao.db2.commands.ProductLD;
import com.wb.stars.common.dao.db2.commands.ProductTermWhereClause;
import com.wb.stars.common.dao.db2.commands.ProductWhereClause;
import com.wb.stars.common.dao.db2.commands.VolumeFeaturesLD;
import com.wb.stars.common.dao.db2.commands.VolumeFeaturesWhereClause;
import com.wb.stars.contracts.checklist.CheckListViewVO;
import com.wb.stars.contracts.checklist.OrderChecklistItemsVO;
import com.wb.stars.contracts.combineDiscombine.CombineViewVO;
import com.wb.stars.contracts.combineDiscombine.GroupInfoViewVO;
import com.wb.stars.contracts.combineDiscombine.SeparateViewVO;
import com.wb.stars.contracts.dao.db2.commands.OrderContactsLD;
import com.wb.stars.contracts.dao.db2.commands.OrderDetailsLD;
import com.wb.stars.contracts.dao.db2.commands.OrderDetailsWhereClause;
//import com.wb.stars.contracts.ejb.ContractsFacadeBMPRemote;
import com.wb.stars.contracts.ejb.ContractsFacadeRemote;
import com.wb.stars.contracts.fieldOrderUpdate.AdditionalProvisionsView;
import com.wb.stars.contracts.fieldOrderUpdate.AdditionalTermsView;
import com.wb.stars.contracts.fieldOrderUpdate.AutoRenewalView;
import com.wb.stars.contracts.fieldOrderUpdate.BackToBackView;
import com.wb.stars.contracts.fieldOrderUpdate.CoOpGrantView;
import com.wb.stars.contracts.fieldOrderUpdate.EpisodesViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.FavoredNationView;
import com.wb.stars.contracts.fieldOrderUpdate.FeaturesViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.FirstRightOfNegotiationView;
import com.wb.stars.contracts.fieldOrderUpdate.LicensedStationsViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.OnceMovedView;
import com.wb.stars.contracts.fieldOrderUpdate.OptionDelayView;
import com.wb.stars.contracts.fieldOrderUpdate.OptionToLicenseView;
import com.wb.stars.contracts.fieldOrderUpdate.OptionToRenewView;
import com.wb.stars.contracts.fieldOrderUpdate.OrderDetailsViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.PaymentTermsViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.PrePlayView;
import com.wb.stars.contracts.fieldOrderUpdate.ProductTemplatesView;
import com.wb.stars.contracts.fieldOrderUpdate.PutAgreementView;
import com.wb.stars.contracts.fieldOrderUpdate.RecapturableView;
import com.wb.stars.contracts.fieldOrderUpdate.RenewSeasonsView;
import com.wb.stars.contracts.fieldOrderUpdate.RunViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.StationMasterViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.StationViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.StationsRightToTerminateView;
import com.wb.stars.contracts.fieldOrderUpdate.SyndexViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.TerminateRatingsProvisionsView;
import com.wb.stars.contracts.fieldOrderUpdate.addlEps.AdditionalEpisodesTPViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.addlEps.licFee.AdditionalEpisodesLicFeeViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.deliveryOptions.DeliveryOptionsViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.provisions.RatingsProvisionsViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.ProductAddlSeasonEpiSelRunDatesViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.ProductInitialSelRunDatsViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.ProductONFRViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.SalesTermsDefaults;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.SalesTermsONFRViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.SalesTermsSelectedRunHistoryViewVO;
import com.wb.stars.contracts.fieldOrderUpdate.salesTerms.SalesTermsVOLViewVO;
import com.wb.stars.contracts.groupFieldOrderWizard.StationGroupTemplateView;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.customerMaint.AddStationView;
import com.wb.stars.customerMaint.ContactView;
import com.wb.stars.customerMaint.CustomerMaintVO;
import com.wb.stars.customerMaint.DMAView;
import com.wb.stars.customerMaint.DivisionView;
import com.wb.stars.customerMaint.ProducerView;
import com.wb.stars.customerMaint.RelatedStationView;
import com.wb.stars.customerMaint.StationCallLetterAndGroupView;
import com.wb.stars.customerMaint.StationDetailsView;
import com.wb.stars.customerMaint.StationGroupDetailsView;
import com.wb.stars.customerMaint.StationGroupSummaryView;
import com.wb.stars.customerMaint.StationGroupView;
import com.wb.stars.customerMaint.StationMasterMaintVO;
import com.wb.stars.customerMaint.StationRelationshipsHistoryVO;
import com.wb.stars.customerMaint.StationSummaryView;
import com.wb.stars.customerMaint.StationTemplatesView;
//import com.wb.stars.reports.ReportHomes;
//import com.wb.stars.reports.ejb.ReportsFacade;
import com.wb.stars.security.UserVO;
//import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemote;
import com.wb.stars.securityMaint.AccessLevelsMaintVO;
import com.wb.stars.securityMaint.UserView;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCalendar;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBMoney;
import com.wb.stars.utils.WBSQLException;
import com.wb.stars.utils.dao.ValueObject;

public class ContractsWorker {

	/**
	 * Manages interaction with Clearance beans
	 */
	private static StarsLogger logger = (StarsLogger) StarsLogger
			.getLogger(Constants.STARS_APP_LOGGER);

	// private static StarsLogger eventsLogger =
	// (StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);

	// @EJB
	private ContractsFacadeRemote contract;

	// @EJB
	// private ReportsFacade reportsFacade;

	// EJB3 annotation added, directly calling Business interface
	public ContractsWorker() throws WBException {

		// contract = getRemote();
		try {
			Context ctxt = new InitialContext();
			contract = (ContractsFacadeRemote) ctxt
					.lookup(JNDINames.CONTRACT_FACADE_BUSINESS_REMOTE);
		} catch (NamingException e) {
			logger.error("ContractsFacadeRemote.class", e);
			throw new WBException(e);
		}
		logger.info(Constants.CREATED);
	}

	/*
	 * public ContractsFacadeRemote getRemote() throws WBException {
	 * logger.info(Constants.START); if (contract == null) { try { contract =
	 * ContractsHomes.getRemoteHome().create(); } catch (RemoteException e) {
	 * logger.error(e.getMessage(), e); throw new WBEJBException(e); } catch
	 * (CreateException e) { logger.error(e.getMessage(), e); throw new
	 * WBEJBException(e); } } logger.info(Constants.END); return contract;
	 * 
	 * }
	 */

	// EJB3 annotation added, directly calling Business interface
	/*
	 * public ReportsFacade getReportsRemote() throws WBException {
	 * 
	 * if (reportsFacade == null) { try { reportsFacade =
	 * ReportHomes.getReportRemoteHome().create(); } catch (RemoteException re)
	 * {
	 * 
	 * throw new WBEJBException(re); } catch (CreateException re) {
	 * 
	 * throw new WBEJBException(re); } }
	 * 
	 * return reportsFacade;
	 * 
	 * }
	 */
	/*
	 * Modified By : Infosys (Siva Prasad Dhulipala) Modified Date : 10-Dec-2004
	 * SR Number : 77 Purpose : To add the methods
	 * inactivatePaymentTerms(),getOrderDetailsForOrderHeader() and
	 * getSourceOrderDetailsForOrderHeader()
	 */

	// Code added to fix SR #77
	public void inactivatePaymentTerms(long orderDetailsId) throws Exception {
		try {
			contract.inactivatePaymentTerms(orderDetailsId);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}

	}

	public long getOrderHeadersForDeleteCO(String contractNumber)
			throws RemoteException, WBSQLException {
		return contract.getOrderHeadersForDeleteCO(contractNumber);
	}

	// Modified the parameter for CQ 1728
	public LinkedList<Object> getOrderHeaderForCanCO(long orderHeaderId)
			throws RemoteException, WBSQLException {
		return contract.getOrderHeaderForCanCO(orderHeaderId);
	}

	public void activatePaymentTerms(long orderDetailsId) throws Exception {
		try {
			contract.activatePaymentTerms(orderDetailsId);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}

	}

	// Code added by DSiva on 04/20/2006 for CQ 923 to fetch History records for
	// a related station
	public LinkedList<Object> getHistoryForRelatedStation(long stationMasterId,
			long stationRelationshipsId, String callLetter)
			throws WBSQLException {
		try {
			// CQ 923 REPLACED relatedStationId with stationRelationshipsId
			/*
			 * return contract.getHistoryForRelatedStation( stationMasterId,
			 * relatedStationId, callLetter);
			 */
			return contract.getHistoryForRelatedStation(stationMasterId,
					stationRelationshipsId, callLetter);

			// return new LinkedList<Object>();
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Method added by DSiva on 04/20/2006 for CQ 923 to add a record to history
	public void performAdd(long stationRelationshipsId) throws WBSQLException {
		try {
			contract.performAdd(stationRelationshipsId);
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Method added by DSiva on 04/20/2006 for CQ 923 to delete a record from
	// history
	public void deleteRowFromHistory(long stationRelHistoryId) throws Exception {
		try {
			contract.deleteRowFromHistory(stationRelHistoryId);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}

	}

	// Method added by DSiva on 04/20/2006 for CQ 923 to update history for a
	// related station
	public void updateStationRelationshipsHistory(
			StationRelationshipsHistoryVO stationRelationshipsHistoryVO)
			throws Exception {
		try {
			contract
					.updateStationRelationshipsHistory(stationRelationshipsHistoryVO);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}

	}

	// End of code added for CQ 923

	// Code added for CQ 416
	public void updateRunDescription() throws RemoteException, WBSQLException {
		try {
			contract.updateRunDescription();
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// End of Code changes for CQ 416

	public LinkedList<Object> getOrderDetailsForOrderHeader(long orderHeaderId,
			boolean isGroupFieldOrder) throws RemoteException, Exception {
		try {
			return contract.getOrderDetailsForOrderHeader(orderHeaderId,
					isGroupFieldOrder);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	public LinkedList<Object> getSourceOrderDetailsForOrderHeader(
			long orderHeaderId, boolean isGroupContract)
			throws RemoteException, Exception {
		try {
			return contract.getSourceOrderDetailsForOrderHeader(orderHeaderId,
					isGroupContract);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// End of code added to fix SR #77

	// EJB3 annotation added, directly calling Business interface
	/*
	 * public ContractsFacadeBMPRemote getBMPRemote() throws WBException {
	 * logger.info(Constants.START);
	 * 
	 * try { logger.info(Constants.END); return
	 * ContractsHomes.getBMPRemoteHome().create(); } catch (RemoteException e) {
	 * logger.error(e.getMessage(), e); throw new WBEJBException(e); } catch
	 * (CreateException e) { logger.error(e.getMessage(), e); throw new
	 * WBEJBException(e); }
	 * 
	 * }
	 */

	public AdditionalProvisionsView getProvisionsView(OrderDetailsVO details)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProvisionsView(details);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public AdditionalProvisionsView getProvisionsView(OrderHeaderKey headerKey)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProvisionsView(headerKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AdditionalProvisionsView saveProvisionsView(
			AdditionalProvisionsView view) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.saveProvisionsView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> searchSeason() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.searchSeason();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> searchSeason(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			LookupSeasonWhereClause where = new LookupSeasonWhereClause();
			where.setParams("orderDetailsId=" + orderDetailsId);
			where.needFirstSindication(true);
			where.setActive(true);
			LookupSeasonLD ld = new LookupSeasonLD();
			ld.setDropDownRecords(true);
			logger.info(Constants.END);
			return contract.searchSeason(where, ld);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderDetailsViewVO getOrderDetailsViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderDetailsViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code added to fix SR #111

	public OrderDetailsViewVO getOrderDetailsViewVO(long orderId,
			boolean fromGroupWizard) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderDetailsViewVO(orderId, fromGroupWizard);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// End of code added to fix SR #111

	public OrderDetailsViewVO getGroupDetailsViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getGroupDetailsViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void saveGroupRuns(LinkedList<Object> stationsRunsList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveGroupRuns(stationsRunsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> getGroupSalesTermsProductsAndStationsViewVO(
			long orderId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract
					.getGroupSalesTermsProductsAndStationsViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getGroupStationsViewVOList(long groupOrderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getGroupStationsViewVOList(groupOrderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long updateOrderDetailsViewVO(OrderDetailsViewVO orderDetailsViewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		logger.info(Constants.END);
		// WBLogger.info(" Gigen - Setting ContractsScreenDefaults to new ");
		// code added to default sales terms properly in FO
		if (orderDetailsViewVO.isFromGFO()) {
			ContractsScreenDefaults conScreenDef = new ContractsScreenDefaults();
			conScreenDef.setFromGFO(true);
			return updateOrderDetailsViewVO(orderDetailsViewVO, conScreenDef);
		}
		// End of code added to default sales terms properly in FO
		else {
			return updateOrderDetailsViewVO(orderDetailsViewVO,
					new ContractsScreenDefaults());

		}

	}

	public long updateOrderDetailsViewVO(OrderDetailsViewVO orderDetailsViewVO,
			ContractsScreenDefaults contractsScreenDefaults)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		long orderHeaderId = -1;
		try {

			// orderHeaderId =
			// getxBMPRemote().updateFromOrderDetailsViewVO(orderDetailsViewVO,contractsScreenDefaults
			// );

			orderHeaderId = contract.updateFromOrderDetailsViewVO(
					orderDetailsViewVO, contractsScreenDefaults);

		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return orderHeaderId;
	}

	// Code added to fix SR #172

	public boolean checkHasActiveStations(long stationGroupId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.checkHasActiveStations(stationGroupId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// End of code added to fix SR #172
	public void updateOrderDetails(LinkedList<Object> orderDetailsList,
			ContractsScreenDefaults contractsScreenDefaults)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateOrderDetails(orderDetailsList,
					contractsScreenDefaults);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/************** FIELD ORDER WIZARD BEGIN ******************/
	public OrderDetailsViewVO createOrderDetailsViewVO(long productType,
			boolean includeExpiredProducts) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createOrderDetailsViewVO(productType,
					includeExpiredProducts);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LicensedStationsViewVO getLicensedStations(long orderHeaderId,
			boolean includeSateTransBoost) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getLicensedStations(orderHeaderId,
					includeSateTransBoost);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void putLicensedStations(LicensedStationsViewVO viewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.putLicensedStations(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public OrderDetailsViewVO copyFieldOrder(long sourceOrderHeaderId,
			long stationMasterId, boolean copyLicenseFees,
			// CQ2816
			long copyAVCDeactivate) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.copyFieldOrder(sourceOrderHeaderId,
					stationMasterId, copyLicenseFees,
					// CQ2816
					copyAVCDeactivate);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Method overloaded for CQ 1668 - Field Order Copy to change call letters
	public OrderDetailsViewVO copyFieldOrder(long sourceOrderHeaderId,
			long stationMasterId, boolean copyLicenseFees,
			boolean copyAndCancelFlag) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.copyFieldOrder(sourceOrderHeaderId,
					stationMasterId, copyLicenseFees, copyAndCancelFlag);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code Modified by Infosys(rajeev) for SC37
	public OrderDetailsViewVO copyFieldOrderTemplate(long sourceOrderHeaderId,
			LinkedList<Object> orderTemplateMarketList, String description,
			long productId, boolean copySourceTemplatetoNewTemplate, long userId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			String action = null;
			if (copySourceTemplatetoNewTemplate) {
				action = "notforNewProduct";
			} else {
				action = "forNewProduct";
			}
			logger.info(Constants.END);
			return contract.copyFieldOrderTemplate(sourceOrderHeaderId,
					orderTemplateMarketList, description, productId, action,
					userId);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code ended for SC37

	public ProductTemplatesView getProductTemplatesView(
			LinkedList<Object> productCodeList) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProductTemplatesView(productCodeList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/************** EPISODES BEGIN ******************/
	public EpisodesViewVO getEpisodesViewVO(long orderId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getEpisodesViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateDeleteODRunsEpisodesFromViewVO(
			EpisodesViewVO episodesViewVO) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateDeleteODRunsEpisodes(episodesViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void updateDeleteODFeeEpisodesFromViewVO(
			EpisodesViewVO episodesViewVO) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateDeleteODFeeEpisodes(episodesViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void createODEpisodes(OrderDetailsVO orderDetailsVO,
			int isOriginalEpisode, boolean isTypeON) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.createODEpisodes(orderDetailsVO, isOriginalEpisode,
					isTypeON);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public EpisodesViewVO refreshODRunsEpisodes(EpisodesViewVO episodesViewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.refreshODRunsEpisodes(episodesViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/************** EPISODES END ******************/

	/************** RATINGS PROVISIONS BEGIN ******************/
	public RatingsProvisionsViewVO getRatingsProvisionsViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getRatingsProvisionsViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public OrderRatingsProvisionsVO put(OrderRatingsProvisionsVO vo)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.put(vo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ValueObject getRatingVO(long orderRatingsProvisionsID,
			ValueObject voIdentifier) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getRatingVO(orderRatingsProvisionsID, voIdentifier);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ValueObject putRatingVO(ValueObject vo) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putRatingVO(vo);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public CommentVO getCommentVO(String tableName, long recordID)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getCommentVO(tableName, recordID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public CommentVO putCommentVO(CommentVO comment) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (comment.getOldComment() == null
					&& (comment.getComment() == null || comment.getComment()
							.trim().length() == 0)) {
				comment.resetNew();
				comment.resetChanged();
				comment.resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putCommentVO(comment);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getTierViewList(long orderDetailsId,
			long orderRatingsProvisionsId, int type, boolean isStripFlg)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getTierViewList(orderDetailsId,
					orderRatingsProvisionsId, type, isStripFlg);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void putRatingsProvisionsList(LinkedList<Object> list)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.putRatingsProvisionsList(list);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/************** RATINGS PROVISIONS END ******************/

	public LinkedList<Object> getFieldOrderIndex() throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getFieldOrderIndex();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderHeaderVO findFieldOrderByPK(OrderHeaderKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findFieldOrderByPK(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AutoRenewalView getAutoRenewalView(OrderDetailsKey detailsKey)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getAutoRenewalView(detailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AutoRenewalView putAutoRenewalView(AutoRenewalView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0))

			{
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putAutoRenewalView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationsRightToTerminateView getStationsRightToTerminateView(
			OrderDetailsKey detailsKey) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationsRightToTerminateView(detailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationsRightToTerminateView putStationsRightToTerminateView(
			StationsRightToTerminateView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putStationsRightToTerminateView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OnceMovedView getOnceMovedView(OrderDetailsKey detailsKey)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOnceMovedView(detailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OnceMovedView putOnceMovedView(OnceMovedView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putOnceMovedView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OptionDelayView getOptionDelayView(OrderDetailsKey detailsKey)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOptionDelayView(detailsKey);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OptionDelayView putOptionDelayView(OptionDelayView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putOptionDelayView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public TerminateRatingsProvisionsView getTerminateRatingsProvisionsView(
			OrderDetailsKey detailsKey) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getTerminateRatingsProvisionsView(detailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public TerminateRatingsProvisionsView putTerminateRatingsProvisionsView(
			TerminateRatingsProvisionsView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putTerminateRatingsProvisionsView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public FirstRightOfNegotiationView getFirstRightOfNegotiationView(
			OrderDetailsKey detailsKey) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getFirstRightOfNegotiationView(detailsKey);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public FirstRightOfNegotiationView putFirstRightOfNegotiationView(
			FirstRightOfNegotiationView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putFirstRightOfNegotiationView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PutAgreementView getPutAgreementView(OrderDetailsKey detailsKey)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPutAgreementView(detailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public PutAgreementView putPutAgreementView(PutAgreementView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putPutAgreementView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public AdditionalTermsView getAdditionalTermsView(OrderDetailsKey detailsKey)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getAdditionalTermsView(detailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public AdditionalTermsView putAdditionalTermsView(AdditionalTermsView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putAdditionalTermsView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public BackToBackView getBackToBackView(OrderDetailsKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getBackToBackView(key);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public BackToBackView putBackToBackView(BackToBackView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putBackToBackView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public OptionToLicenseView getOptionToLicenseView(OrderDetailsKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOptionToLicenseView(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public OptionToLicenseView putOptionToLicenseView(OptionToLicenseView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putOptionToLicenseView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public OptionToRenewView getOptionToRenewView(OrderDetailsKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOptionToRenewView(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public OptionToRenewView putOptionToRenewView(OptionToRenewView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			boolean chkComment = false;
			if ((view.getCommentVO().getComment() != null && view
					.getCommentVO().getComment().trim().length() != 0)
					|| view.getCommentVO().getOldComment() != null) {
				chkComment = true;
			} else {
				view.getCommentVO().resetNew();
			}
			if (view.getRenewSeasonsList().size() > 0) {
				for (Iterator<Object> i = view.getRenewSeasonsList().iterator(); i
						.hasNext();) {
					RenewSeasonsView renewSeasonsView = (RenewSeasonsView) i
							.next();
					if ((renewSeasonsView.getSeasonCommentVO().getComment() != null && renewSeasonsView
							.getSeasonCommentVO().getComment().trim().length() != 0)
							|| renewSeasonsView.getSeasonCommentVO()
									.getOldComment() != null) {
						if (!chkComment) {
							view.getCommentVO().resetNew();
							view.getCommentVO().resetChanged();
							view.getCommentVO().resetDeleted();
						}
					} else if (chkComment) {
						renewSeasonsView.getSeasonCommentVO().resetNew();
					} else {
						view.getCommentVO().resetNew();
						view.getCommentVO().resetChanged();
						view.getCommentVO().resetDeleted();
						renewSeasonsView.getSeasonCommentVO().resetNew();
						renewSeasonsView.getSeasonCommentVO().resetChanged();
						renewSeasonsView.getSeasonCommentVO().resetDeleted();
					}
				}
			}

			// End of code added for CQ 2719
			return contract.putOptionToRenewView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public PrePlayView getPrePlayView(OrderDetailsKey key) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPrePlayView(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public PrePlayView putPrePlayView(PrePlayView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putPrePlayView(view);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public RecapturableView getRecapturableView(OrderDetailsKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getRecapturableView(key);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public RecapturableView putRecapturableView(RecapturableView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putRecapturableView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public FavoredNationView getFavoredNationView(OrderDetailsKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getFavoredNationView(key);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public FavoredNationView putFavoredNationView(FavoredNationView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			if (view.getCommentVO().getOldComment() == null
					&& (view.getCommentVO().getComment() == null || view
							.getCommentVO().getComment().trim().length() == 0)) {
				view.getCommentVO().resetNew();
				view.getCommentVO().resetChanged();
				view.getCommentVO().resetDeleted();
			}
			// End of code added for CQ 2719
			return contract.putFavoredNationView(view);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> getCoOpGrantView(OrderDetailsKey key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getCoOpGrantView(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> putCoOpGrantView(LinkedList<Object> viewList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Code added for CQ 2719
			// LinkedList<Object> newViewList = new LinkedList<Object>();
			for (Iterator<Object> i = viewList.iterator(); i.hasNext();) {
				CoOpGrantView view = (CoOpGrantView) i.next();
				if (view.getCommentVO().getOldComment() == null
						&& (view.getCommentVO().getComment() == null || view
								.getCommentVO().getComment().trim().length() == 0)) {
					view.getCommentVO().resetNew();
					view.getCommentVO().resetChanged();
					view.getCommentVO().resetDeleted();
				}
			}
			// End of code added for CQ 2719
			return contract.putCoOpGrantView(viewList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/************************ Beginning Syndex Functions ******************************************/
	public SyndexViewVO getSyndexViewVO(OrderHeaderKey key) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSyndexViewVO(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderDetailsSyndexVO getOrderDetailsSyndexVO(
			long orderDetailsSyndexId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderDetailsSyndexVO(orderDetailsSyndexId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateOrderDetailsSyndex(
			OrderDetailsSyndexVO orderDetailsSyndexVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateOrderDetailsSyndex(orderDetailsSyndexVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public OrderDetailsSyndexVO createOrderDetailsSyndex(
			OrderDetailsSyndexVO orderDetailsSyndexVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createOrderDetailsSyndex(orderDetailsSyndexVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderDetailsSyndexVO saveOrderDetailsSyndex(
			OrderDetailsSyndexVO orderDetailsSyndexVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.saveOrderDetailsSyndex(orderDetailsSyndexVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/************************ End Syndex Functions ******************************************/

	/******** Begin Sales Terms ****************/

	public SalesTermsVOLViewVO getSalesTermsVOLViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSalesTermsVOLViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SalesTermsDefaults getSalesTermsDefaults(
			OrderDetailsFeaturesKey orderDetailsFeaturesKey,
			boolean defaultLicStation) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSalesTermsDefaults(orderDetailsFeaturesKey,
					defaultLicStation);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SalesTermsDefaults getSalesTermsDefaults(
			OrderDetailsKey orderDetailsKey) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSalesTermsDefaults(orderDetailsKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SalesTermsDefaults getSalesTermsDefaults(
			OrderDetailsKey orderDetailsKey, boolean isStrip)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSalesTermsDefaults(orderDetailsKey, isStrip);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SalesTermsONFRViewVO getSalesTermsONFRViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSalesTermsONFRViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SalesTermsONFRViewVO updateCreateSalesTermsONFRView(
			SalesTermsONFRViewVO salesTermsViewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateCreateSalesTermsONFRView(salesTermsViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	// code added to show the status of checkbox properly when creating an FO
	// from template for CQ#661
	public SalesTermsONFRViewVO updateCreateSalesTermsONFRView(
			SalesTermsONFRViewVO salesTermsViewVO, String fromTemplate,
			long detailsId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateCreateSalesTermsONFRView(salesTermsViewVO,
					fromTemplate, detailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void createSelectedRunHistory(
			SalesTermsSelectedRunHistoryViewVO historyViewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.createSelectedRunHistory(historyViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void createAddlEpsSelectedRunHistory(
			SalesTermsSelectedRunHistoryViewVO historyViewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.createAddlEpsSelectedRunHistory(historyViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void createSalesTerms(ProductONFRViewVO viewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.createSalesTerms(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	// REMOVE THIS
	// public SalesTermsViewVO getSalesTermsViewVO(OrderDetailsKey detailsKey)
	// throws WBException , WBSQLException
	// {
	// try
	// {
	// return contract.getSalesTermsViewVO(detailsKey);
	// }
	// catch (WBEJBException e)
	// {
	// throw new WBEJBException(e);
	// }
	// }

	/* Update or Create SalesTermViewVO */
	// public SalesTermsViewVO updateCreateSalesTermsView(SalesTermsViewVO
	// salesTermsViewVO) throws WBException , WBSQLException
	// {
	// try{
	// return contract.updateCreateSalesTermsView(salesTermsViewVO);
	// }catch(WBEJBException e)
	// {
	// throw new WBEJBException(e);
	// }
	//
	// }
	// Passing one more parameter to the method to fix SR#75
	public SalesTermsVOLViewVO updateCreateSalesTermsVOLViewVO(
			SalesTermsVOLViewVO salesTermsVOLViewVO, long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			// Passing one more parameter to the method to fix SR#75
			return contract.updateCreateSalesTermsVOLViewVO(
					salesTermsVOLViewVO, orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/* Sales Terms Allocation by Features */
	public SalesTermsVOLViewVO getSalesTermsABFViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSalesTermsABFViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> findTiersByOrderDetailsFeaturesId(
			long orderDetailsId, long orderDetailsFeatuesId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findTiersByOrderDetailsFeaturesId(orderDetailsId,
					orderDetailsFeatuesId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public SalesTermsONFRViewVO updateCreateAllocationByFeatures(
			SalesTermsONFRViewVO salesTermsViewONFRVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract
					.updateCreateAllocationByFeatures(salesTermsViewONFRVO);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findStripOrWeekEndTierList(long orderDetailsId,
			boolean stripOrWeekEnd) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findStripOrWeekEndTierList(orderDetailsId,
					stripOrWeekEnd);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/******** End of Sales Terms ****************/

	/********* Begin. For License Fee Calculation *************/

	public OrderHeaderVO getOrderHeaderForLicenseFee(long orderHeaderId,
			boolean isONFR) throws WBException, WBSQLException {
		logger.info(Constants.START);
		OrderHeaderVO orderHeaderVO = new OrderHeaderVO();

		try {
			orderHeaderVO = contract.getOrderHeaderForLicenseFee(orderHeaderId,
					isONFR);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return orderHeaderVO;
	}

	public OrderHeaderVO getOrderHeaderForSelectedRun(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		OrderHeaderVO orderHeaderVO = null;

		try {
			orderHeaderVO = contract
					.getOrderHeaderForSelectedRun(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return orderHeaderVO;
	}

	public void saveSelectedRunDatesForInitial(long orderHeaderId,
			HashMap<Object, Object> selectedRunDatesMap) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveSelectedRunDatesForInitial(orderHeaderId,
					selectedRunDatesMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void saveSelectedRunDatesForAddlEps(long orderHeaderId,
			HashMap<Object, Object> selectedRunDatesMap) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveSelectedRunDatesForAddlEps(orderHeaderId,
					selectedRunDatesMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public OrderHeaderVO getOrderHeaderForSelectedRunAddlEps(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		OrderHeaderVO orderHeaderVO = null;

		try {
			orderHeaderVO = contract
					.getOrderHeaderForSelectedRunAddlEps(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return orderHeaderVO;
	}

	public ProductInitialSelRunDatsViewVO getSelectedRunDatesForDetails(
			long orderDetailsId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		ProductInitialSelRunDatsViewVO viewVO = new ProductInitialSelRunDatsViewVO();
		try {
			viewVO = contract.getSelectedRunDatesForDetails(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return viewVO;
	}

	public void saveSelectedRunDatesForDetails(
			ProductInitialSelRunDatsViewVO viewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveSelectedRunDatesForDetails(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public ProductAddlSeasonEpiSelRunDatesViewVO getSelectedRunDatesForDetailsAddlTimePeriods(
			long orderDetailsId, long detailsAddlTimePeriodsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		ProductAddlSeasonEpiSelRunDatesViewVO viewVO = new ProductAddlSeasonEpiSelRunDatesViewVO();
		try {
			viewVO = contract.getSelectedRunDatesForDetailsAddlTimePeriods(
					orderDetailsId, detailsAddlTimePeriodsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return viewVO;
	}

	public void saveSelectedRunDatesForDetailsAddlTimePeriods(
			ProductAddlSeasonEpiSelRunDatesViewVO viewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveSelectedRunDatesForDetailsAddlTimePeriods(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void saveOrderInitialLicenseFee(long orderHeaderId,
			double totalLicFee, HashMap<Object, Object> prdLicFeeMap)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveOrderInitialLicenseFee(orderHeaderId, totalLicFee,
					prdLicFeeMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	// Code Added to fix Lic Fee in Sales Terms
	public void saveOrderInitialLicenseFee(long orderHeaderId,
			double totalLicFee, double totalAdllLiceFee,
			HashMap<Object, Object> prdLicFeeMap) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveOrderInitialLicenseFee(orderHeaderId, totalLicFee,
					totalAdllLiceFee, prdLicFeeMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	// End of code

	public OrderHeaderVO getOrderHeaderForAddlEpsLicenseFee(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		OrderHeaderVO orderHeaderVO = new OrderHeaderVO();

		try {
			orderHeaderVO = contract
					.getOrderHeaderForAddlEpsLicenseFee(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
		return orderHeaderVO;
	}

	// CQ 2968 - Code added by Bhuvaneshwari to Allow for partial input on the
	// Unequal Payments Screen Starts

	public long getOrderHeaderForSourceOrderHeader(long sourceOrderHeaderId)
			throws WBSQLException {
		long order_header_id = 0;
		try {
			OrderManager orderManager = new OrderManager();
			order_header_id = orderManager
					.getOrderHeaderForSourceOrderHeader(sourceOrderHeaderId);

		} catch (Exception e) {
			logger.error("Error occurred: " + e.getMessage());
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return order_header_id;
	}

	// CQ 2968 Ends

	public void updateOrderHeaderAndDetailsLicFee(long orderHeaderId,
			double orderTotalLicFee, HashMap<Object, Object> detailLicFeeMap,
			boolean isInitial) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateOrderHeaderAndDetailsLicFee(orderHeaderId,
					orderTotalLicFee, detailLicFeeMap, isInitial);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/********* End. For License Fee Calculation *************/

	public OrderDetailsVO getOrderDetailsByProductId(OrderHeaderKey headerKey,
			long productId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			OrderDetailsWhereClause detailsWhere = new OrderDetailsWhereClause();
			detailsWhere.setProductId(productId);
			detailsWhere.setHeaderId(headerKey.getOrderHeaderId());
			logger.info(Constants.END);
			return (OrderDetailsVO) contract.findOrderDetailsByWhere(
					detailsWhere, new OrderDetailsLD()).getFirst();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/************************ Beginning FieldOrderUpdate Features Functions ******************************************/
	public LinkedList<Object> createFeaturesViewVO(long key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createFeaturesViewVO(new OrderHeaderKey(key),
					new OrderDetailsLD());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> createGroupWizardFeaturesViewVO(long key)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createGroupWizardFeaturesViewVO(new OrderHeaderKey(
					key), new OrderDetailsLD());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public WBMoney getOrderLicenseFee(long fieldOrderId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderLicenseFee(fieldOrderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> createRunsViewVO(long key) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createRunsViewVO(new OrderHeaderKey(key),
					new OrderDetailsLD());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateFeaturesViewVO(FeaturesViewVO featuresViewVO,
			boolean fromWizard, ContractsScreenDefaults contractsScreenDefaults)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateFeaturesViewVO(featuresViewVO, fromWizard,
					contractsScreenDefaults);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void updateFeaturesViewVO(LinkedList<Object> viewVOs,
			boolean fromWizard, ContractsScreenDefaults contractsScreenDefaults)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateFeaturesViewVO(viewVOs, fromWizard,
					contractsScreenDefaults);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void updateGroupFeaturesViewVO(LinkedList<Object> viewVOs,
			boolean fromWizard, ContractsScreenDefaults contractsScreenDefaults)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateGroupFeaturesViewVO(viewVOs, fromWizard,
					contractsScreenDefaults);
		} catch (WBEJBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> getVolumeFeatures(long productId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getVolumeFeatures(productId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void updateFeaturesOrderLicFee(long orderHeaderId,
			WBMoney orderTotalLicFee) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateFeaturesOrderLicFee(orderHeaderId, orderTotalLicFee);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/************************ End FieldOrderUpdate Features Functions ******************************************/

	/*********** Begin FieldOrderUpdate Additional Episodes methods *******************************/

	// Additional Episodes Time Periods
	public AdditionalEpisodesTPViewVO getAdditionalEpisodesTPViewVO(long orderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getAdditionalEpisodesTPViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AdditionalEpisodesTPViewVO saveAdditionalEpisodesTPViewVO(
			AdditionalEpisodesTPViewVO viewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.saveAdditionalEpisodesTPViewVO(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Additional Episodes License Fee
	public AdditionalEpisodesLicFeeViewVO getAdditionalEpisodesLicFeeViewVO(
			long orderId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getAdditionalEpisodesLicFeeViewVO(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AdditionalEpisodesLicFeeViewVO saveAdditionalEpisodesLicFeeViewVO(
			AdditionalEpisodesLicFeeViewVO viewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.saveAdditionalEpisodesLicFeeViewVO(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*********** End FieldOrderUpdate Additional Episodes methods *******************************/

	/************************ Begin FieldOrderUpdate Payment Functions ******************************************/
	public PaymentTermsViewVO getPaymentTermsViewVO(long orderId,
			boolean initial) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPaymentTermsViewVO(orderId, initial);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PaymentTermsViewVO getPaymentTermsViewVO(long orderId,
			long orderDetailsId, boolean initial) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPaymentTermsViewVO(orderId, orderDetailsId,
					initial);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PaymentTermsViewVO getPaymentTermsViewVO(long orderId,
			long orderDetailsId, long runId, boolean initial)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPaymentTermsViewVO(orderId, orderDetailsId,
					runId, initial);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public PaymentTermsViewVO getPaymentTermsViewVO(long orderId,
			long orderDetailsId, long orderRunsSeasonsTermsId, int runNumber,
			boolean initial, int showAllRuns, long paymentForTypeId,
			boolean showFirstAvailable) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPaymentTermsViewVO(orderId, orderDetailsId,
					orderRunsSeasonsTermsId, runNumber, initial, showAllRuns,
					paymentForTypeId, showFirstAvailable);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void deletePaymentTerm(long orderId, String orderType,
			long orderDetailsId, long orderRunsSeasonsTermsId, int runNumber,
			boolean initial, int showAllRuns, long paymentForTypeId,
			boolean showFirstAvailable, UserVO userVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			contract.deletePaymentTerm(orderId, orderType, orderDetailsId,
					orderRunsSeasonsTermsId, runNumber, initial, showAllRuns,
					paymentForTypeId, showFirstAvailable, userVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// CQ 2821: Delete initial if only initial is changed else only additional
	public void saveOrderRunsTerms(PaymentTermsViewVO viewVO,
			long orderDetailsId, long orderRunsSeasonsTermsId, long isInitial)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveOrderRunsTerms(viewVO, orderDetailsId,
					orderRunsSeasonsTermsId, isInitial);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	// CQ 2821: Delete initial if only initial is changed else only additional
	public void saveOrderRunsTerms(PaymentTermsViewVO viewVO,
			long orderDetailsId, long orderRunsSeasonsTermsId,
			long paymentForTypeId, long isInitial) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveOrderRunsTerms(viewVO, orderDetailsId,
					orderRunsSeasonsTermsId, paymentForTypeId, isInitial);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public long saveOrderRunsSeasonsTerms(OrderRunsSeasonsTermsVO vo)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.saveOrderRunsSeasonsTerms(vo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderRunsSeasonsTerms(long orderDetailsId,
			int runNumber, boolean initial, int showAllRuns)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderRunsSeasonsTerms(orderDetailsId, runNumber,
					initial, showAllRuns);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getOrderRunsSeasonsTerms(long orderDetailsId,
			int runNumber, boolean initial, int showAllRuns,
			long paymentForTypeId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderRunsSeasonsTerms(orderDetailsId, runNumber,
					initial, showAllRuns, paymentForTypeId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public OrderRunsSeasonsTermsVO getOrderRunsSeasonsTerms(
			long orderRunsSeasonsTermsId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getOrderRunsSeasonsTerms(orderRunsSeasonsTermsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getProductionSeasons(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProductionSeasons(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getUnequalPayments(long orderRunsTermsId,
			boolean isDownpayment) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getUnequalPayments(orderRunsTermsId, isDownpayment);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void saveUnequalPayments(long orderRunsTermsId,
			LinkedList<Object> paymentList, long userId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveUnequalPayments(orderRunsTermsId, paymentList, userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void processPaymentTerms(long orderDetailsId, long userId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.processPaymentTerms(orderDetailsId, userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void processPaymentTermsByHeader(long orderHeaderId, long userId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.processPaymentTermsByHeader(orderHeaderId, userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void setPaymentTermsToUnprocessed(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.setPaymentTermsToUnprocessed(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void setPaymentTermsToUnprocessedByHeader(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.setPaymentTermsToUnprocessedByHeader(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void deleteUnequalPayment(long unequalPaymentId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.deleteUnequalPayment(unequalPaymentId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void deleteOrderRunsSeasonsTerms(long orderRunsSeasonsTermsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.deleteOrderRunsSeasonsTerms(orderRunsSeasonsTermsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	/************************ End FieldOrderUpdate Payment Functions ******************************************/

	/* Delivery Options */

	public DeliveryOptionsViewVO getDeliveryOptionsViewVO(
			OrderHeaderKey orderHeaderKey) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getDeliveryOptionsViewVO(orderHeaderKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public DeliveryOptionsViewVO createUpdateDeliveryOptions(
			DeliveryOptionsViewVO deliveryOptionsViewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createUpdateDeliveryOptions(deliveryOptionsViewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public OrderDetailsVO getOrderDetailsByDetail(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			OrderDetailsWhereClause detailsWhere = new OrderDetailsWhereClause();
			detailsWhere.setOrderDetailsId(orderDetailsId);
			logger.info(Constants.END);
			return (OrderDetailsVO) contract.findOrderDetailsByWhere(
					detailsWhere, new OrderDetailsLD()).getFirst();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/* CheckList */
	public long runCheckList(long orderId, int submissionType, int userId,
			String runType, String orderIdType) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.runCheckList(orderId, submissionType, userId,
					runType, orderIdType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public CheckListViewVO runCheckList(long productId, int submissionType,
			int userId, String runType, String orderIdType, long dmaId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.runCheckList(productId, submissionType, userId,
					runType, orderIdType, dmaId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*
	 * Modified By : Infosys (Siva Prasad Dhulipala) Modified Date : 29-Nov-2004
	 * SR Number : 37 Purpose : To pass station call letters for checking
	 * conflicts with existing sales
	 */
	// Code added to fix SR #37
	public CheckListViewVO runCheckList(long productId, int submissionType,
			int userId, String runType, String orderIdType, long dmaId,
			String callLetters) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.runCheckList(productId, submissionType, userId,
					runType, orderIdType, dmaId, callLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	// End of code added to fix SR #37
	public CheckListViewVO findCheckListViewVO(long orderId, String runType,
			String orderIdType) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findCheckListViewVO(orderId, runType, orderIdType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public CheckListViewVO findCheckListViewVO(long orderCheckListId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findCheckListViewVO(orderCheckListId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> findCheckedListByOrderCheckListId(
			long orderCheckListId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findCheckedListByOrderCheckListId(orderCheckListId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void updateOverRideReason(long orderCheckListItemsId, long userId,
			String overRideReason) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateOverRideReason(orderCheckListItemsId, userId,
					overRideReason);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);

	}

	public OrderChecklistItemsVO findCheckListItemsVO(long orderCheckListItemsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findCheckListItemsVO(orderCheckListItemsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/* Group Field Order Wizard */
	public StationGroupTemplateView loadStationGroupTemplates(
			long stationGroupId, long groupOrderHeaderId,
			LinkedList<Object> selectedProductList) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.loadStationGroupTemplates(stationGroupId,
					groupOrderHeaderId, selectedProductList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*
	 * Code added for SR#111 to default Group Salesperson on Group Field Order
	 * Wizard
	 */
	public LinkedList<Object> loadDefaultGroupSalesPersonList(
			long stationGroupId, LinkedList<Object> selectedProductList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.loadDefaultGroupSalesPersonList(stationGroupId,
					selectedProductList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* End of code added for SR#111 */
	/* Group Field Order Wizard SalesTerms Volume */
	public LinkedList<Object> getFeaturesForSelectedStations(
			LinkedList<Object> selectedOrderDetailsFeaturesId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract
					.getFeaturesForSelectedStations(selectedOrderDetailsFeaturesId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/************** Stations data layer */
	public DivisionView getDivisionView(long divisionId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getDivisionView(divisionId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public DivisionView putDivisionView(DivisionView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putDivisionView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ProducerView getProducerView(long producerId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProducerView(producerId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ProducerView putProducerView(ProducerView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putProducerView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ContactView getNewContactView(long contactType) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getNewContactView(contactType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public UserView getNewUserView() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getNewUserView();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getMenuItemsFromDb(long userId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getMenuItemsFromDb(userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public AccessLevelsMaintVO getAccessLevelsMaintVO(long userId,
			long dataObjectId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getAccessLevelsMaintVO(userId, dataObjectId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public UserView getUserView(long userId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getUserView(userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public UserView putUserView(UserView view) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putUserView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public DMAView getDMAView(long dmaId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getDMAView(dmaId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public DMAView putDMAView(DMAView view) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putDMAView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public HashMap<Object, Object> validateStationGroupView(
			StationGroupView view, String ignoreGroupName) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.validateStationGroupView(view, ignoreGroupName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationGroupView getStationGroupView(long groupId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationGroupView(groupId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationGroupView putStationGroupView(StationGroupView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putStationGroupView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationTemplatesView getStationTemplatesView(long stationId,
			long dmaId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationTemplatesView(stationId, dmaId);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public RelatedStationView updateRelatedStationView(RelatedStationView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateRelatedStationView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public StationDetailsView getStationDetailsView(long stationId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationDetailsView(stationId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public StationDetailsView putStationDetailsView(StationDetailsView view,
			long stationId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putStationDetailsView(view, stationId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationGroupSummaryView getStationGroupSummaryView(
			long stationGroupId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationGroupSummaryView(stationGroupId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationGroupSummaryView putStationGroupSummaryView(
			StationGroupSummaryView view, long stationGroupId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putStationGroupSummaryView(view, stationGroupId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationGroupDetailsView getStationGroupDetailsView(
			long stationGroupId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationGroupDetailsView(stationGroupId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationGroupDetailsView putStationGroupDetailsView(
			StationGroupDetailsView view, long stationGroupId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putStationGroupDetailsView(view, stationGroupId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public HashMap<String, Comparable> validateAddStationView(
			AddStationView view, String ignoreCallLetters) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.validateAddStationView(view, ignoreCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public Integer getMarketIdByShortName(String shortName) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getMarketIdByShortName(shortName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public StationSummaryView getStationSummaryView(long stationId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationSummaryView(stationId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public StationSummaryView refreshStationSummaryView(StationSummaryView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.refreshStationSummaryView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public StationSummaryView putStationSummaryView(StationSummaryView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putStationSummaryView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> loadDropdownData(String dropdownName)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.loadDropdownData(dropdownName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ContactView getContactView(long contactType, long parentId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getContactView(contactType, parentId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ContactView getContactViewByContactId(long contactId, long parentId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getContactViewByContactId(contactId, parentId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ContactView putContactView(ContactView view, long contactType,
			long parentId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putContactView(view, contactType, parentId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationCallLetterAndGroupView getStationCallLetterAndGroupView(
			long stationMasterId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationCallLetterAndGroupView(stationMasterId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public AddStationView getAddStationView() throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getAddStationView();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public AddStationView putAddStationView(AddStationView view)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putAddStationView(view);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public RunViewVO updateOrderDetailsFeatures(RunViewVO viewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateOrderDetailsFeatures(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> updateOrderDetailsFeatures(
			LinkedList<Object> viewVOs, long orderHeaderId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateOrderDetailsFeatures(viewVOs, orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> findProductTermsByFeatures(
			LinkedList<Object> ids, long orderDetailsId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			ProductTermWhereClause where = new ProductTermWhereClause();
			where.setOrderDetailsId(orderDetailsId);
			where.setIds(ids);
			ProductTermLD ld = new ProductTermLD();
			ld.setNeedRules(true);
			ld.setNeedDistinct(true);
			ld.setNeedFeatures(true);
			logger.info(Constants.END);
			return contract.findProductTermByFeatures(where, ld);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findMissingBarterTimePeriods(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findMissingBarterTimePeriods(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> findMissingBarterTimePeriods(long orderDetailsId,
			String type) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findMissingBarterTimePeriods(orderDetailsId, type);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public StationViewVO createStationViewVO(long key) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createStationViewVO(new OrderHeaderKey(key),
					new OrderContactsLD());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public StationViewVO updateStationViewVO(StationViewVO stationViewVO,
			boolean fromWizard) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateStationViewVO(stationViewVO, fromWizard);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long getNextIndex(String sequenceName) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getNextIndex(sequenceName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findOrderDetailsByWhere(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			OrderDetailsWhereClause where = new OrderDetailsWhereClause();
			where.setHeaderId(orderHeaderId);
			OrderDetailsLD ld = new OrderDetailsLD();
			ld.setNeedAddlTerms(true);
			logger.info(Constants.END);
			return contract.findOrderDetailsByWhere(where, ld);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void massUpdateProvisions(LinkedList<Object> CommentBoxList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.massUpdateProvisions(CommentBoxList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> findOrderHeaderByFeatureId(long productId,
			long featureId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findOrderHeaderByFeatureId(productId, featureId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getVolumes() throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			ProductWhereClause where = new ProductWhereClause();
			where.setProductType(3);
			ProductLD productLD = new ProductLD();
			productLD.setNeedAsDropDowns(true);
			logger.info(Constants.END);
			return contract.findProductByWhere(where, productLD);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getProductsWithoutVolumes() throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			ProductWhereClause where = new ProductWhereClause();
			where.setProductTypeExclude(3);
			ProductLD productLD = new ProductLD();
			productLD.setNeedAsDropDowns(true);
			logger.info(Constants.END);
			return contract.findProductByWhere(where, productLD);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getProductVO(long productId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			ProductWhereClause where = new ProductWhereClause();
			where.setProductId(productId);
			ProductLD productLD = new ProductLD();
			logger.info(Constants.END);
			return contract.findProductByWhere(where, productLD);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getFeaturesForVolumeId(long productId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			VolumeFeaturesWhereClause where = new VolumeFeaturesWhereClause();
			where.setProductId(productId);
			VolumeFeaturesLD volumeFeaturesLD = new VolumeFeaturesLD();
			volumeFeaturesLD.setNeedDropDowns(true);
			logger.info(Constants.END);
			return contract.findFeaturesByWhere(where, volumeFeaturesLD);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getProductRuleByFeature(long productId,
			long featureId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProductRuleByFeature(productId, featureId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void modifyFeatureAvailability(long productId, long featureId,
			LinkedList<Object> productRuleList, LinkedList<Object> contractList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.modifyFeatureAvailability(productId, featureId,
					productRuleList, contractList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<Object> findOrderHeaderByProductId(long productId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findOrderHeaderByProductId(productId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> findProductEpisodesForMassUpdate(long productId,
			long distributionSystemId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.findProductEpisodesForMassUpdate(productId,
					distributionSystemId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/************ CONTRACT CREATION *****************************************************/

	public LinkedList<Object> getCreateContractViewList(
			LinkedList<Object> groupHeaderIdList,
			LinkedList<Object> orderDetailsIdList) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getCreateContractViewList(groupHeaderIdList,
					orderDetailsIdList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public long createNewSingleContract(
			LinkedList<Object> createContractViewList) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createNewSingleContract(createContractViewList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long combineWithExistingContract(long existingContractId,
			LinkedList<Object> createContractViewList, boolean asAmendment,
			WBCalendar effectiveDate, String reason) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.combineWithExistingContract(existingContractId,
					createContractViewList, asAmendment, effectiveDate, reason);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long createNewGroupContract(LinkedList<Object> createContractViewList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createNewGroupContract(createContractViewList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code added to fix SR #98

	public long createNewGroupContract(
			LinkedList<Object> createContractViewList,
			LinkedList<Object> groupOrderHeaderIdsList) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.createNewGroupContract(createContractViewList,
					groupOrderHeaderIdsList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//
	// Combine and Separate Functionality.
	//

	public long combineFieldOrders(CombineViewVO viewVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.combineFieldOrders(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public GroupInfoViewVO getGroupOrderInfo(long selectedGrpHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getGroupOrderInfo(selectedGrpHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getStationOrdersForGroup(long groupHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStationOrdersForGroup(groupHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> separateFieldOrders(SeparateViewVO viewVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.separateFieldOrders(viewVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void addAdditionalEpisodes(long productId,
			LinkedList<Object> productEpisodeList,
			LinkedList<Object> contractList) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.addAdditionalEpisodes(productId, productEpisodeList,
					contractList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public LinkedList<FeatureVO> loadVolumeSummaryViewVO(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.loadVolumeSummaryViewVO(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> loadSummaryONFRViewVO(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.loadSummaryONFRViewVO(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> loadIsSelectedHistory(long orderDetailsId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.loadIsSelectedHistory(orderDetailsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void saveIsSelectedHistory(LinkedList<Object> isSelectedHistoryList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.saveIsSelectedHistory(isSelectedHistoryList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public void deleteIsSelectedHistory(long isSelectedHistoryId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.deleteIsSelectedHistory(isSelectedHistoryId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public ContractHeaderVO getContractHeaderVO(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getContractHeaderVO(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getVOLSummaryList(long productId,
			long groupHeaderId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getVOLSummaryList(productId, groupHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void adjustGroupOrder(long groupHeaderId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.adjustGroupOrder(groupHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
		logger.info(Constants.END);
	}

	public StationMasterViewVO searchStationMaster(String callLetters)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.searchStationMaster(callLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/*
	 * Modified By : Infosys(Kshitindra Jain) Modified Date : 25-Feb-2005 SR
	 * Number : 260 Purpose : To conside the criterion mentioned in SR260 for
	 * validate button on PTP screen
	 */
	public StationMasterViewVO searchStationMaster(String callLetters,
			String userId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.searchStationMaster(callLetters, userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* End of code added for SR#260 */

	public Object test(LinkedList<Object> parameterList) throws WBException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.test(parameterList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}

	/*
	 * Modified By : Infosys (Kranthi Pavan) Modified Date : 15-Nov-2004 SR
	 * Number : 297 Purpose : To show the Contract Term details upon clicking
	 * Contract Term hyperlink on Permanent Time Period Validation screen
	 */
	public boolean isAdditionalEpisodesPresent(long orderHeaderId,
			String prodName) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract
					.isAdditionalEpisodesPresent(orderHeaderId, prodName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	// end of Code added for SR#297

	/* Code added By Infosys(Deepika) for CQ1698 */
	public boolean stationWithCallLetterExist(String callLetter)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.stationWithCallLetterExist(callLetter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/* End Of added Code */

	public String[] getLicensedStations(long orderDetailsId)
			throws WBSQLException {

		try {
			return contract.getLicensedStations(orderDetailsId);
		} catch (WBSQLException e) {
			logger.error("SQL Error occurred: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error occurred : ", e);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Code added for STARS00001728 by Madhuri on 09/20/06

	public LinkedList<Object> getOHIdForSourceDetId(long sourceOdId)
			throws RemoteException, Exception {
		try {
			return contract.getOHIdForSourceDetId(sourceOdId);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Code added for STARS00001728 by Madhuri on 09/20/06 ends

	// Code added for CQ1728 to prevent the user from splitting a field order
	// into multiple contracts
	public long areAllProductsSelected(LinkedList<Object> ohIdList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.areAllProductsSelected(ohIdList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public long getStatusForFOId(long ohId) throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getStatusForFOId(ohId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public void updateFoforCancel(long foId, long prevOrderStatusId)
			throws Exception {
		try {
			contract.updateFoforCancel(foId, prevOrderStatusId);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}

	}

	// Code added for CQ1728 ends
	// Code added for CQ1728 to check whether the field order is a part of the
	// cancelled contract
	public long isFoPartOfCancelledContract(long fieldOrderHeaderId)
			throws WBException, WBSQLException {

		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.isFoPartOfCancelledContract(fieldOrderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	// Code added for STARS00001668 by Madhuri
	public long isLMAPossible(String primaryCallLetters, String newCallLetters)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.isLMAPossible(primaryCallLetters, newCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void insertCallLetterChanges(ChangeCallLetterVO changeCallLetterVO)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			contract.insertCallLetterChanges(changeCallLetterVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> retrieveCallLetters(long orderHeaderId)
			throws WBSQLException {
		try {
			return contract.retrieveCallLetters(orderHeaderId);
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Code added for STARS00001668 by Madhuri ends
	// (Arun) Code for CQ 1668
	public boolean getRatingsProvisionsStationSelected(long orderHeaderId,
			long sourceOrderHeaderId) throws WBSQLException, Exception {
		logger.info(Constants.START);
		try {
			boolean ratingsProvisionLicStationsDeleted = contract
					.getRatingsProvisionsStationSelected(orderHeaderId,
							sourceOrderHeaderId);
			return ratingsProvisionLicStationsDeleted;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code for CQ 1668 Ends

	// SR #2345-Updating the table on basis of whether a CO is updated,deleted
	// or cancelled
	public void updateContractProductHistory(long userId, long orderHeaderId,
			String changeOrderAction) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateContractProductHistory(userId, orderHeaderId,
					changeOrderAction);
			logger.info(Constants.END);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// SR #2345-End

	// CQ #2430-Starts
	public void updateRunDescriptionForFOCopy(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			contract.updateRunDescriptionForFOCopy(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// CQ #2430-Ends

	// CQ #542-Starts
	/*
	 * Get the station details for the searched station from the lookup and
	 * prepare a dropdown record
	 */
	public HashMap<Object, Object> getSearchedStationDetails(
			long searchedStationId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSearchedStationDetails(searchedStationId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// CQ #542-Ends

	/*
	 * Code added by Infosys (rajeev) for SC29-Add Create Contract Button to
	 * Details screen on 07/16/2007 Purpose: To create a contract from
	 * FieldOrder details Page
	 */

	public LinkedList<Object> getFieldOrderDetails(String fieldOrderId)
			throws RemoteException, WBSQLException {
		try {
			return contract.getFieldOrderDetails(fieldOrderId);

		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}

	}

	/*
	 * End of Code added by Infosys (rajeev) for SC29-Add Create Contract Button
	 * to Details screen
	 */
	// code added by Infosys(rajeev) for SC22
	public long putNoticeDetails(long noticeId,
			AddlTermsNoticeDateVO noticeDateVO) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.putNoticeDetails(noticeId, noticeDateVO);

		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public LinkedList<Object> getNoticeDetails(String orderDeatilsId,
			String productId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getNoticeDetails(orderDeatilsId, productId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public void deleteNoticeDetails(long noticeId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			contract.deleteNoticeDetails(noticeId);

		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}

	}

	// Code Ended for SC22
	// CQ 2274: Code added to get station Call letters
	public String getStationCallLetters(String stationMasterIds)
			throws RemoteException, WBSQLException {
		try {
			return contract.getStationCallLetters(stationMasterIds);
		} catch (WBSQLException e) {
			throw e;
		}
	}

	// CQ 2274: End of Added Code.

	/*
	 * Code added by Infosys (Madhumita) for SC14 on 08/22/2007 Purpose:---- For
	 * Duplicate and Active STARS Call Letter Validation
	 */

	public boolean tvDataCallLetterAlreadyExists(String tvDataCallLetters,
			String callLetters) throws WBSQLException, Exception {
		logger.info(Constants.START
				+ "tvDataCallLetterAlreadyExists in Contracts Worker");
		try {
			return contract.tvDataCallLetterAlreadyExists(tvDataCallLetters,
					callLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public boolean isCallLetterPresent(String tvDataCallLetters)
			throws WBSQLException, Exception {
		logger
				.info(Constants.START
						+ "isCallLetterPresent in Contracts Worker");
		try {
			return contract.isCallLetterPresent(tvDataCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/**
	 * 
	 * @return Call Letter
	 */
	public String callLetter(String tvDataCallLetters) throws WBSQLException,
			Exception {
		logger.info(Constants.START + "callLetter in ContractsWorker");
		try {
			return contract.callLetter(tvDataCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String dbValue(String callLetters) throws WBSQLException, Exception {
		logger.info(Constants.START + "callLetter in ContractsWorker");
		try {
			return contract.dbValue(callLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* End of code change for SC14 */
	/* Code added by Infosys (Suma) for SC41 on 22/09/2007 */
	public ContractSearchVO getLatestOrderId(ContractSearchVO contractsearchvo)
			throws WBSQLException, Exception {
		logger.info(Constants.START + "Orderid");
		try {
			return contract.getLatestOrderId(contractsearchvo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	// End of Code added by Infosys (Suma) for SC41
	// CQ 2434: Method Added to save stationHeader properties (viz. licensee
	// name, IsInactive and IsInterCompany info) to DB.
	public void saveHeaderInfo(StationMasterMaintVO stationMasterMaintVO,
			CustomerMaintVO customerMaintVO, long userId)
			throws RemoteException, WBSQLException {
		try {
			contract.saveHeaderInfo(stationMasterMaintVO, customerMaintVO,
					userId);
		} catch (Exception e) {
			logger.error(e);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// CQ 2434: End of added method.

	// CQ 2140: Delete Comments when a Ratings Provision is deleted
	public void deleteCommentVO(long orderDetailsId, long ratingsProvisionNbr)
			throws WBSQLException, RemoteException {
		logger.info(Constants.START);
		try {
			contract.deleteCommentVO(orderDetailsId, ratingsProvisionNbr);
		} catch (WBSQLException e) {
			logger.error(e);
			throw e;
		} finally {
			logger.info(Constants.END);
		}
	}

	// CQ 2140: End of added code
	/* Code added by Infosys (Suma) for SC35 on 19/12/2007 */
	public void updateActiveFlagAsInactive(
			LinkedList<Object> createContractViewList) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateActiveFlagAsInactive(createContractViewList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// End of Code added by Infosys (Suma) for SC35
	// Code added by Infosys (Rajeev ) for SC40
	public String getEmailId(String userId) throws WBSQLException, Exception {
		logger.info(Constants.START + "Email Id in ContractsWorker");
		try {
			return contract.getEmailId(userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public void createEmailRequest(long emailTypeId, String mailSubject,
			String mailBody, long createdId, String emailId) throws Exception {
		logger.info(Constants.START + "Email Id in ContractsWorker");
		try {
			contract.createEmailRequest(emailTypeId, mailSubject, mailBody,
					createdId, emailId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code Ended
	// CQ #2423-Starts
	// Code Modified for SC38(MIS CQ167) on 04/04/2008
	public void updateContStAndDates(long sourceOrderHeaderId,
			long newOrderHeaderId, String action) throws RemoteException,
			WBSQLException {
		try {
			// Code Modified for SC38(MIS CQ167) on 04/04/2008
			contract.updateContStAndDates(sourceOrderHeaderId,
					newOrderHeaderId, action);
			// Code ended for SC38
		} catch (Exception e) {
			logger.error(e);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// CQ #2423-Ends
	// Code Added by Infosys(rajeev) for Sc38
	public LinkedList<Object> separateFieldOrderOrContract(
			long sourceOrderHeaderId, LinkedList<Object> productList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.separateFieldOrderOrContract(sourceOrderHeaderId,
					productList);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public LinkedList<Object> getSourceOrderDetails(long orderHeaderId,
			long productId) throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getSourceOrderDetails(orderHeaderId, productId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public long getFieldOrderId(long orderDetailsId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getFieldOrderId(orderDetailsId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public int getPendingCOCount(long orderHeaderId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getPendingCOCount(orderHeaderId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public int getProductType(long productId) throws WBSQLException,
			WBException {
		try {
			return contract.getProductType(productId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public int getLicFeeProcAconngDtExists(long orderHeaderId, int productTypeId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getLicFeeProcAconngDtExists(orderHeaderId,
					productTypeId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public String getproductName(long productId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getproductName(productId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	// Code Ended for SC38

	/* Start of Code added by Infosys(Madhumita) for SC52 - 8-Nov-2007 */
	public boolean nielsenCallLettersAlreadyExist(String nielsenCallLetters,
			String callLetters) throws WBSQLException, Exception {
		logger.info(Constants.START
				+ "nielsenCallLettersAlreadyExists in Contracts Worker");
		try {
			return contract.nielsenCallLettersAlreadyExist(nielsenCallLetters,
					callLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * 
	 * @return Call Letter
	 */
	public String callLetter1(String nielsenCallLetters) throws WBSQLException,
			Exception {
		logger.info(Constants.START + "callLetter in ContractsWorker");
		try {
			return contract.callLetter1(nielsenCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* End of Code added by Infosys(Madhumita) for SC52 - 8-Nov-2007 */

	/* Code added by Infosys(Omprakash) for CQ2173 */
	public boolean ITSCallLettersAlreadyExist(String ITSCallLetters,
			String callLetters) throws WBSQLException, Exception {
		logger.info(Constants.START
				+ "ITSCallLettersAlreadyExists in Contracts Worker");
		try {
			return contract.ITSCallLettersAlreadyExist(ITSCallLetters,
					callLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * 
	 * @return Call Letter
	 */
	public String callLetterITS(String ITSCallLetters) throws WBSQLException,
			Exception {
		logger.info(Constants.START + "callLetterITS in ContractsWorker");
		try {
			return contract.callLetterITS(ITSCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/* End of Code added for CQ2173 */

	/* Code added by Infosys (Suma) for SC05 on 02/17/2008 */
	// CQ 2607: Saving without change order.
	public void saveSelectedRunDateChange(long orderHeaderId,
			boolean isFromChangeOrder) throws WBException, WBSQLException {
		OrderManager orderManager = new OrderManager();
		orderManager
				.saveSelectedRunDateChange(orderHeaderId, isFromChangeOrder);
	}

	// End of Code added by Infosys (Suma) for SC05
	// Code added by Infosys(Rajeev) for SC38 on 03/08/2008
	public LinkedList<Object> getProductsList(long orderHeaderId)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.getProductsList(orderHeaderId);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	// End of Code added by Infosys (Rajeev) for SC38
	// Code added by Infosys (Rajeev) for SC63 on 10/06/2008
	public LinkedList<Object> putAssociatedNielsenCallLetters(
			LinkedList<Object> associatedNielsenCallLettersList)
			throws WBException, WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract
					.putAssociatedNielsenCallLetters(associatedNielsenCallLettersList);
		} catch (WBException e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		} catch (Exception exception) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	/*
	 * Code added by Infosys (Rajeev) for SC63 on 12/06/2008 Purpose:---- For
	 * Duplicate and Active STARS Call Letter Validation
	 */

	public boolean validateAssocCallLetterAlreadyExists(
			String assocCallLetters, String callLetters, long assocCallLettersId)
			throws WBSQLException, Exception {
		logger.info(Constants.START
				+ "validateAssocCallLetterAlreadyExists in Contracts Worker");
		try {
			return contract.validateAssocCallLetterAlreadyExists(
					assocCallLetters, callLetters, assocCallLettersId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public String getCallLetter(String assocCallLetters) throws WBSQLException,
			Exception {
		logger.info(Constants.START + "callLetter in ContractsWorker");
		try {
			return contract.getCallLetter(assocCallLetters);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// Code ended for SC63 on 12/06/2008

	// CQ #2575-Starts
	public void updateContractDates(long addedProdFOOhid,
			LinkedList<Object> addedProdList, long contractOhid)
			throws RemoteException, WBSQLException {
		try {
			contract.updateContractDates(addedProdFOOhid, addedProdList,
					contractOhid);
		} catch (Exception e) {
			logger.error(e);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Cancels the FO when CO is cancelled for added products
	public void cancelFOForAddedProd(LinkedList<Object> addedProdOdid)
			throws RemoteException, WBSQLException {
		try {
			contract.cancelFOForAddedProd(addedProdOdid);
		} catch (Exception e) {
			logger.error(e);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// CQ #2575-Ends

	// CQ #2082-To check whether User ID exists in database
	public boolean existsLogonName(String logName) throws RemoteException,
			WBSQLException {
		try {
			return contract.existsLogonName(logName);
		} catch (Exception e) {
			logger.error(e);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// CQ #2082-Ends

	// CQ #2567 Starts
	public void createEmailRequest(long emailTypeId, String mailSubject,
			String mailBody, String toEmailId, String ccEmailId)
			throws Exception {
		logger.info(Constants.START + "Email Id in ContractsWorker");
		try {
			contract.createEmailRequest(emailTypeId, mailSubject, mailBody,
					toEmailId, ccEmailId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// CQ #2567 Ends
	// CQ #2684 Starts
	public StationTemplatesView updateStationMasterComments(
			StationTemplatesView view, long stationId) throws WBException,
			WBSQLException {
		logger.info(Constants.START);
		try {
			logger.info(Constants.END);
			return contract.updateStationMasterComments(view, stationId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// CQ #2684 Ends
	// CQ 2770: Starts
	public void updatepaymentScheduleDnPaymentDates(long orderHeaderId)
			throws WBException, WBSQLException {
		try {
			contract.updatepaymentScheduleDnPaymentDates(orderHeaderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// CQ 2770: Ends
	// Code added for CQ 2831
	public LinkedList<Object> getBreakupDetails(String orderDetailsId,
			int isOriginal) throws WBException, WBSQLException {
		try {
			return contract.getBreakupDetails(orderDetailsId, isOriginal);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	// End of code added for CQ 2831
	// COSMOS 10: Create Order Line Items
	public void createOrderLineItems(long orderHeaderId, long orderDetailsId,
			long userId) throws WBSQLException {
		logger.info(Constants.START);
		try {
			contract
					.createOrderLineItems(orderHeaderId, orderDetailsId, userId);
			logger.info(Constants.END);
		} catch (WBSQLException wbSQLException) {
			logger.error("SQL Error occurred: " + wbSQLException.getMessage());
			throw wbSQLException;
		} catch (Exception exception) {
			logger.error("Error occurred: ", exception);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public void addNewLineItem(long orderHeaderId, long orderDetailsId,
			long seasonId, String term, long userId) throws WBSQLException {
		logger.info(Constants.START);
		try {
			contract.addNewLineItem(orderHeaderId, orderDetailsId, seasonId,
					term, userId);
			logger.info(Constants.END);
		} catch (WBSQLException wbSQLException) {
			logger.error("SQL Error occurred: " + wbSQLException.getMessage());
			throw wbSQLException;
		} catch (Exception exception) {
			logger.error("Error occurred: ", exception);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public void deleteLineItem(long orderHeaderId, long orderDetailsId,
			long seasonId, String term, long userId) throws WBSQLException {
		logger.info(Constants.START);
		try {
			contract.deleteLineItem(orderHeaderId, orderDetailsId, seasonId,
					term, userId);
			logger.info(Constants.END);
		} catch (WBSQLException wbSQLException) {
			logger.error("SQL Error occurred: " + wbSQLException.getMessage());
			throw wbSQLException;
		} catch (Exception exception) {
			logger.error("Error occurred: ", exception);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public void regenerateInitialTermLineItems(long orderHeaderId,
			long orderDetailsId, String term, long userID)
			throws WBSQLException {
		logger.info(Constants.START);
		try {
			contract.regenerateInitialTermLineItems(orderHeaderId,
					orderDetailsId, term, userID);
			logger.info(Constants.END);
		} catch (WBSQLException wbSQLException) {
			logger.error("SQL Error occurred: " + wbSQLException.getMessage());
			throw wbSQLException;
		} catch (Exception exception) {
			logger.error("Error occurred: ", exception);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public void updateCashFlg(long orderDetailsId, long userId)
			throws WBSQLException {
		logger.info(Constants.START);
		try {
			contract.updateCashFlg(orderDetailsId, userId);
			logger.info(Constants.END);
		} catch (WBSQLException wbSQLException) {
			logger.error("SQL Error occurred: " + wbSQLException.getMessage());
			throw wbSQLException;
		} catch (Exception exception) {
			logger.error("Error occurred: ", exception);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	public long newLineItemAlreadyExists(long orderDetailsId, long seasonId)
			throws WBSQLException {
		logger.info(Constants.START);
		try {
			return contract.newLineItemAlreadyExists(orderDetailsId, seasonId);
		} catch (WBSQLException wbSQLException) {
			logger.error("SQL Error occurred: " + wbSQLException.getMessage());
			throw wbSQLException;
		} catch (Exception exception) {
			logger.error("Error occurred: ", exception);
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, exception);
		}
	}

	// COSMOS 10 Ends
	// CQ 2863:start
	public long getMaxWindowNumber(long orderHeaderId) throws WBSQLException {
		try {
			return contract.getMaxWindowNumber(orderHeaderId);
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	public long getMaxWinNumber(long orderHeaderId) throws WBSQLException {
		try {
			return contract.getMaxWinNumber(orderHeaderId);
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	public long getMaxWinNumberForFO(long orderHeaderId) throws WBSQLException {
		try {
			return contract.getMaxWinNumberForFO(orderHeaderId);
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// CQ 2863:end

	// Code added by Infosys (Bala) for CQ 2892 on 04/07/2010 : Start
	public void populateOrderIdentifier(long orderHeaderId,
			long srcOrderHeaderId, WBCalendar effectiveDate,
			String contractAction, long userId, String accessedFrom)
			throws WBSQLException {
		try {
			contract.populateOrderIdentifier(orderHeaderId, srcOrderHeaderId,
					effectiveDate, contractAction, userId, accessedFrom);
		} catch (Exception e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	// Code added by Infosys (Bala) for CQ 2892 on 04/07/2010 : End

	// CQ3195
	public void updateOITimeStampForComments(long userId, long orderHeaderID)
			throws RemoteException, WBSQLException {
		try {
			contract.ContractCommentOrderHeaderTimeStampUpdate(userId,
					orderHeaderID);
		} catch (WBSQLException e) {
			throw new WBSQLException("errors.excep.system.component",
					new String[] { this.getClass().getName() }, e);
		}
	}

	//Code added for CQ2824 by XDSRINIV for populating Stationdroplist- Begins
  public LinkedList<Object> populateCallletterList(long productId, long contractStatus)
  throws WBSQLException{
	  try
	  {
		  LinkedList<Object> callletterlist = new LinkedList<Object>();
		  DB2CallLetterListDAO dB2CallLetterListDAO = new DB2CallLetterListDAO();
		  callletterlist = dB2CallLetterListDAO.populateStationList(productId,contractStatus);//Added the contractStatus condition for QC607 by XDSRINIV - Product and Station dropdown going out of sync
		  return callletterlist;
	  }
		catch(Exception e)
		{
		  throw new WBSQLException(
			  "errors.excep.system.component",
			  new String[] { this.getClass().getName()},
			  e);
		}
	}
	// Code added for CQ2824 by XDSRINIV for populating Stationdroplist- Ends

	//MDV
	public LinkedList <Object> getOrderHeaderForCanCO1(String ccontractNumber)
	throws RemoteException, WBSQLException
{
	return contract.getOrderHeaderForCanCO1(ccontractNumber);
}
	//MDV
}

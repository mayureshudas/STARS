package com.wb.stars.accounting;

//import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.wb.stars.accounting.ejb.AccountingFacadeRemote;
import com.wb.stars.common.LookupCreditStatusTypeVO;
import com.wb.stars.customerMaint.CustomerMaintVO;
import com.wb.stars.customerMaint.DmaData;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCalendar;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBFinderException;
import com.wb.stars.utils.WBLogger;
//import com.wb.stars.utils.WBLogger;
import com.wb.stars.utils.WBSQLException;

public class AccountingWorker
{
	//@EJB
	private AccountingFacadeRemote facade;

	// EJB3 annotation added, directly calling Business interface
	public AccountingWorker() throws WBException
	{
		try
		{
			//facade = getRemote();
			Context ctxt = new InitialContext();
			facade = (AccountingFacadeRemote) ctxt.lookup(JNDINames.ACCOUNTING_FACADE_BUSINESS_REMOTE);
		}
		catch ( NamingException e )
		{
			WBLogger.getReference().logError(AccountingFacadeRemote.class, e );
			throw new WBException( e );
		}
		
	}

	/*public AccountingFacadeRemote getRemote() throws WBException
	{

		if (facade == null)
			{
			try
			{
				facade = AccountingHomes.getRemoteHome().create();
			}
			catch (RemoteException re)
			{
				throw new WBEJBException(re);
			}
			catch (Exception ce)
			{
				WBLogger.error(""+getClass(), ce);
				throw new WBException(ce.getMessage());
			}
		}
		return facade;

	}*/
	
	public LinkedList<Object> searchBatch(long batchTypeId,
									long batchNumber,
								    String startDate,
								    String startDateOperator,
								    String endDate,
								    String endDateOperator,
								    long batchPeriodId,
								    long createdById,
								    long batchSortId,
								    long batchStatusId,
								    long customerId,
								    long customerTypeId,
								    double amount,
								    String amountOperator) throws WBSQLException,WBException
	{
		try {
			LinkedList<Object> result=null;
			result=facade.searchBatch(batchTypeId,
					batchNumber,
					startDate,
					startDateOperator,
					endDate,
					endDateOperator,
					batchPeriodId,
					createdById,
					batchSortId,
					batchStatusId,
					customerId,
					customerTypeId,
					amount,
					amountOperator);
			return result;
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	
	public void saveTransactionBatch(BatchVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveTransactionBatch(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void savePaymentBatch(BatchVO vo) throws WBSQLException,WBException
	{
		try {
			facade.savePaymentBatch(vo);	
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public void saveAdjustmentBatch(BatchVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveAdjustmentBatch(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void savePayment(TransactionHeaderVO vo) throws WBSQLException,WBException
	{
		try {
			facade.savePayment(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}


	public PeriodDatesVO getPeriodDatesByDate(WBCalendar date) throws WBSQLException,WBException
	{
		try {
			return facade.getPeriodDatesByDate(date);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public PeriodDatesVO getPeriodDatesById(long periodDatesId) throws WBSQLException,WBException
	{
		try {
			return facade.getPeriodDatesById(periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public BatchVO getBatchById(long batchId) throws WBSQLException,WBException
	{
		try {
			return facade.getBatchById(batchId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public BatchVO getBatchById(long batchId, boolean loadRecursively) throws WBSQLException,WBException
	{
		try {
			return facade.getBatchById(batchId,loadRecursively);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public BatchVO getBatchById(long batchId, boolean loadRecursively, int type)     throws WBSQLException,WBException
	{
		try {
			return facade.getBatchById(batchId,loadRecursively, type);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public TransactionHeaderVO getPaymentById(long transactionHeaderId)	 throws WBSQLException,WBException
	{
		try {
			return facade.getPaymentById(transactionHeaderId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public TransactionHeaderVO getPaymentByDetailId(long transactionDetailId)	 throws WBSQLException,WBException
	{
		try {
			return facade.getPaymentByDetailId(transactionDetailId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public TransactionDetailVO getTransactionDetailById(long transactionDetailId) throws WBSQLException,WBException
	{
		try {
			return facade.getTransactionDetailById(transactionDetailId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> loadCustomerTransactions(long paymentHeaderId,long customerId, boolean loadAppliedOnly,WBCalendar transactionDate) throws WBSQLException,WBException
	{
		try {
			return facade.loadCustomerTransactions(paymentHeaderId,customerId,loadAppliedOnly,transactionDate);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public HashMap<String, String> validateContractNumber(String contractNumber, long productId, long customerId) throws WBSQLException,WBException
	{
		try {
			return facade.validateContractNumber(contractNumber,productId,customerId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public ArDefaultsVO loadCurrentARDefaults()  throws WBSQLException,WBException
	{
		try {
			return facade.loadCurrentArDefaults();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public void saveArDefaults(ArDefaultsVO vo)  throws WBSQLException,WBException
	{
		try {
			facade.saveArDefaults(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> loadProfitCenterMap() throws WBSQLException,WBException
	{
		try {
			return facade.loadProfitCenterMap();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveProfitCenterMap(LinkedList<Object> map) throws WBSQLException,WBException
	{
		try {
			facade.saveProfitCenterMap(map);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> loadTransactionMap() throws WBSQLException,WBException
	{
		try {
			return facade.loadTransactionMap();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveTransactionMap(LinkedList<Object> map) throws WBSQLException,WBException
	{
		try {
			facade.saveTransactionMap(map);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public CreditStatusVO loadCreditStatus() throws WBSQLException,WBException
	{
		try {
			return facade.loadCreditStatus();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public CreditStatusVO loadCreditStatus(int activeStatusCode) throws WBSQLException,WBException
	{
		try {
			return facade.loadCreditStatus(activeStatusCode);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public LookupCreditStatusTypeVO saveNewCreditStatus(LookupCreditStatusTypeVO vo) throws WBSQLException,WBException
	{
		try {
			return facade.saveNewCreditStatus(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveCreditStatus(CreditStatusVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveCreditStatus(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public FinanceChargeVO loadFinanceCharge(int activeFinanceCode) throws WBSQLException,WBException
	{
		try {
			return facade.loadFinanceCharge(activeFinanceCode);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public FinanceChargeVO saveNewFinanceCharge(FinanceChargeVO vo) throws WBSQLException,WBException
	{
		try {
			return facade.saveNewFinanceCharge(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveFinanceCharge(FinanceChargeVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveFinanceCharge(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public TaxSetupVO loadTax(int activeTaxCode) throws WBSQLException,WBException
	{
		try {
			return facade.loadTax(activeTaxCode);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveTax(TaxSetupVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveTax(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public TaxVO loadTaxGroups(int activeGroupCode) throws WBSQLException,WBException
	{
		try {
			return facade.loadTaxGroups(activeGroupCode);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}	

	public void saveNewTaxGroup(TaxGroupVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveNewTaxGroup(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveTaxGroups(TaxVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveTaxGroups(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}		

	public AutoAdjustmentsVO loadAutoAdjustments(int activeAdjustmentCode) throws WBSQLException,WBException
	{
		try {
			return facade.loadAutoAdjustments(activeAdjustmentCode);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public AutoAdjustmentsVO saveNewAutoAdjustment(AutoAdjustmentsVO vo) throws WBSQLException,WBException
	{
		try {
			return facade.saveNewAutoAdjustment(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void saveAutoAdjustments(AutoAdjustmentsVO vo) throws WBSQLException,WBException
	{
		try {
			facade.saveAutoAdjustments(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public Vector<Object> getTableNames() throws WBSQLException,WBException
	{
		try {
			return facade.getTableNames();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public Vector<Object> getTable(ParameterValues parameterValues) throws WBSQLException,WBException
	{
		try {
			return facade.getTable(parameterValues);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public Hashtable<String,String> getRow(ParameterValues parameterValues) throws WBSQLException,WBException
	{
		try {
			return facade.getRow(parameterValues);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public Vector<Object> getColumnNames(ParameterValues parameterValues) throws WBSQLException,WBException
	{
		try {
			return facade.getColumnNames(parameterValues);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public String insertNewRow(ParameterValues parameterValues) throws WBSQLException,WBException
	{
		try {
			return facade.insertNewRow(parameterValues);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public String updateTable(ParameterValues parameterValues) throws WBSQLException,WBException
	{
		try {
			return facade.updateTable(parameterValues);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void ageAccounts() throws WBSQLException,WBException
	{
		try {
			facade.ageAccounts();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void ageAccounts(long periodDatesId,String agingDate) throws WBSQLException,WBException
	{
		try {
			facade.ageAccounts(periodDatesId,agingDate);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void applyFinanceCharges(String financeChargeDate) throws WBSQLException,WBException
	{
		try {
			facade.applyFinanceCharges(financeChargeDate);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void applyFinanceCharges() throws WBSQLException,WBException
	{
		try {
			facade.applyFinanceCharges();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void applyAutoAdjustments() throws WBSQLException,WBException
	{
		try {
			facade.applyAutoAdjustments();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}								

	public void generateInvoices() throws WBSQLException,WBException
	{
		try {
			facade.generateInvoices();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void doEarning() throws WBSQLException,WBException
	{
		try {
			facade.doEarning();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void doPaymentAllocation() throws WBSQLException,WBException
	{
		try {
			facade.doPaymentAllocation();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void generateInvoices(long periodDatesId) throws WBSQLException,WBException
	{
		try {
			facade.generateInvoices(periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void doEarning(long periodDatesId) throws WBSQLException,WBException
	{
		try {
			facade.doEarning(periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void doPaymentAllocation(long periodDatesId) throws WBSQLException,WBException
	{
		try {
			facade.doPaymentAllocation(periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void outputInvoices(long periodDatesId) throws WBSQLException,WBException
	{
		try {
			facade.outputInvoices(periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void outputDunningLetters(long periodDatesId,String creationDate) throws WBSQLException,WBException
	{
		try {
			facade.outputDunningLetters(periodDatesId,creationDate);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public void outputStatements(String creationDate,long periodDatesId) throws WBSQLException,WBException
	{
		try {
			facade.outputStatements(creationDate,periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public FacilityView getFacilityView(long facilityId) throws  WBSQLException, WBException {
		try {
			return facade.getFacilityView(facilityId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public FacilityView putFacilityView(FacilityView view)  throws WBSQLException, WBException {
		try {
			return facade.putFacilityView(view);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public CollectionsCommentsView getCollectionsCommentsView(long customerId, boolean isLoadAll, int sortBy)  throws WBSQLException, WBException {
		try {
			return facade.getCollectionsCommentsView(customerId, isLoadAll, sortBy);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public CollectionsCommentsView putCollectionsCommentsView(CollectionsCommentsView view)  throws WBSQLException, WBException {
		try {
			return facade.putCollectionsCommentsView(view);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> putAssetSecuritizationViewList(LinkedList<Object> assetSecuritizationViewList, long userId)  throws WBSQLException, WBException {
		try {
			return facade.putAssetSecuritizationViewList(assetSecuritizationViewList, userId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getStationContacts(long stationMasterId) throws WBException
	{
		try {
			return facade.getStationContacts(stationMasterId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public DmaData getStationDMAData(long stationMasterId) throws WBException
	{
		try {
			return facade.getStationDMAData(stationMasterId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}

	public LinkedList<Object> getStationSalespeople(long stationMasterId) throws WBException
	{
		try {
			return facade.getStationSalespeople(stationMasterId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getNonPersonelStationContacts(long stationMasterId, long contactType) throws WBException	
	{
		try {
			return facade.getNonPersonelStationContacts(stationMasterId,contactType);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getCollectionsAnalystForStation(long stationMasterId,boolean loadPhones, boolean loadEmails) throws WBException
	{
		try {
			return facade.getCollectionsAnalystForStation(stationMasterId,loadPhones, loadEmails);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public CustomerAgingTotalsVO getCustomerAgingTotals(long customerId,long periodDatesId) throws WBException
	{
		try {
			return facade.getCustomerAgingTotals(customerId,periodDatesId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> loadCustomerTransactions(long customerId, boolean loadOpenOnly, int sortBy,WBCalendar earliestPostDate)  throws WBSQLException,WBException
	{
		try {
			return facade.loadCustomerTransactions(customerId,loadOpenOnly,sortBy,earliestPostDate);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> loadCustomerPayments(long customerId, boolean loadOnlyOpenTran,boolean loadOnlyAppliedTran, WBCalendar earliestPostDate, int sortBy)  throws WBSQLException,WBException
	{
		try {
			return facade.loadCustomerPayments(customerId,loadOnlyOpenTran,loadOnlyAppliedTran,earliestPostDate,sortBy);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getStationContracts(long stationMasterId, long customerId, boolean loadCollectionsReserve, boolean loadOrderRunsTerms, int sortBy)  throws WBException
	{
		try {
			return facade.getStationContracts(stationMasterId,customerId,loadCollectionsReserve,loadOrderRunsTerms, sortBy);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> findProductsByWhere(long stationMasterId, boolean needDropDowns) throws WBFinderException, WBSQLException, WBException
	{
		try {
			return facade.findProductsByWhere(stationMasterId,needDropDowns);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public LinkedList<Object> getStationContractsDropDown(long stationMasterId)  throws WBException
	{
		try {
			return facade.getStationContractsDropDown(stationMasterId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public void saveCollectionsReserve(long stationMasterId, long stationGroupId, long customerId, int reserveTypeId,String reserveAmount,int applyToTypeId, String reservePct, int pctTypeId, long contractProductId, long orderHeaderId, long agingBucketsId, boolean isOtt) throws WBException
	{
		try {
			facade.saveCollectionsReserve(stationMasterId,stationGroupId,customerId,reserveTypeId,reserveAmount,applyToTypeId,reservePct, pctTypeId, contractProductId, orderHeaderId, agingBucketsId, isOtt);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public void saveCollectionsReserveVO(CollectionsReserveVO collectionsReserveVO) throws WBException
	{
		try {
			facade.saveCollectionsReserveVO(collectionsReserveVO);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public CustomerMaintVO getCustomerMaintVO(long customerId) throws WBException
	{
		try {
			return facade.getCustomerMaintVO(customerId);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public void saveCustomerMaintVO(CustomerMaintVO vo) throws WBException
	{
		try {
			facade.saveCustomerMaintVO(vo);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public PeriodDatesVO getFirstOpenPeriodDates()  throws WBSQLException,WBException
	{
		try {
			return facade.getFirstOpenPeriodDates();
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public TransactionHeaderVO getTransactionHeaderById(long transactionHeaderId, boolean loadRecursively, int type)  throws WBSQLException,WBException
	{
		try {
			return facade.getTransactionHeaderById(transactionHeaderId,loadRecursively,type);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public TransactionDetailVO getContractPayment(long customerId,long orderHeaderId,long productId,String contractNumber) throws WBException
	{
		try {
			return facade.getContractPayment(customerId,orderHeaderId,productId,contractNumber);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
	public void saveTransactionDetailVO(TransactionDetailVO tdVO) throws WBException
	{
		try {
			facade.saveTransactionDetailVO(tdVO);
		} catch (Exception e) {
			throw new WBEJBException(e);
		}
	}
}

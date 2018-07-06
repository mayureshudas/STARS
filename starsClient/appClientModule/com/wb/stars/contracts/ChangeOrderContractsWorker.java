/* Modified By     : Infosys (Prasanth Nandanuru) 
 * Modified Date   : 4-Nov-2004  
 * Purpose         : Added Error Handlers in 
 * public/default/protected methods for exception handling*/

package com.wb.stars.contracts;

//import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.wb.stars.common.Constants;
import com.wb.stars.contracts.coAmend.ChangeOrderView;
import com.wb.stars.contracts.contractUpdate.ContractStatusViewVO;
import com.wb.stars.contracts.ejb.ChangeOrderContractsFacadeRemote;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.security.UserVO;
//import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemote;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.StarsUserTransaction;
import com.wb.stars.utils.WBException;

public class ChangeOrderContractsWorker
{

	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	//@EJB
	private ChangeOrderContractsFacadeRemote changeOrderContract;

	// EJB3 annotation added, directly calling Business interface
	public ChangeOrderContractsWorker()
	{

		try
		{
			//changeOrderContract = getRemote();
			Context ctxt = new InitialContext();
			changeOrderContract = (ChangeOrderContractsFacadeRemote) ctxt.lookup(JNDINames.CHANGE_ORDER_CONTRACT_FACADE_BUSINESS_REMOTE);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		logger.info(Constants.CREATED);
	}

	/*private ChangeOrderContractsFacadeRemote getRemote() throws Exception
	{
		logger.info(Constants.START);
		if (changeOrderContract == null)
		{
			try
			{
				changeOrderContract =
					ContractsHomes
						.getChangeOrderContractsFacadeRemoteHome()
						.create();
			}
			catch (RemoteException re)
			{
				logger.error(re);
				throw new Exception(re.getMessage());
			}
			catch (Exception ce)
			{
				logger.error(ce);
				throw new Exception(ce.getMessage());
			}
		}
		logger.info(Constants.END);
		return changeOrderContract;

	}*/
	
	/* Overloaded getContractStatusView() to pass param fetchAllUsers 
	 * to display both active & inactive users for in 
	 * Contract Update >Status tab >Addl Order Info for CQ#1807*/
	public ContractStatusViewVO getContractStatusView(
		long orderId,
		long systemOrderTypeIdToView)
		throws WBException
	{
		return getContractStatusView(orderId, systemOrderTypeIdToView, false);
	}
	
	
	//Code added for CQ 923
	public HashMap<Object, Object> getDeselectedLicensedStations(long contractOrderHeaderId,long changeOrderHeaderId) throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return changeOrderContract.getDeselectedLicensedStations(contractOrderHeaderId,changeOrderHeaderId);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
		
	}
	//End of code added for CQ 923

	public ContractStatusViewVO getContractStatusView(
		long orderId,
		long systemOrderTypeIdToView,
		boolean fetchAllUsers)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return changeOrderContract.getContractStatusView(
				orderId,
				systemOrderTypeIdToView,
				fetchAllUsers);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}

	}
	//End of added code	
	
	/** * Modified By     : Infosys (Prasanth Nandanuru) 
		 * Modified Date   : 19-Mar-2005  
		 * Purpose         : To avoid dead lock scenario on CONTRACT_DATES table 
	**/
	public ContractStatusViewVO putContractStatusView(ContractStatusViewVO contractStatusViewVO)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);

			/* Commented by prasanth (Infosys)
			return changeOrderContract.putContractStatusView(
				contractStatusViewVO);
			*/

			contractStatusViewVO =
				changeOrderContract.putContractStatusView(contractStatusViewVO);

			return changeOrderContract.getContractStatusView(
				contractStatusViewVO.getOrderHeaderId(),
				contractStatusViewVO
					.getContractStatusesVO()
					.getSystemOrderTypeId());

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
	}

	public LinkedList<Object> getGroupDetailsLicenseFeeViewList(
		long groupHeaderId,
		long productId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return changeOrderContract.getGroupDetailsLicenseFeeViewList(
				groupHeaderId,
				productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}

	}

	public void massUpdateContractComments(
		long groupHeaderId,
		boolean overrideFlg)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			changeOrderContract.massUpdateContractComments(
				groupHeaderId,
				overrideFlg);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
		logger.info(Constants.END);
	}

	public LinkedList<ChangeOrderView> getChangeOrderViewList(long orderHeaderId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return changeOrderContract.getChangeOrderViewList(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}

	}

	/** Modified By     : Infosys (Prasanth Nandanuru) 
	 *  Modified Date   : 19-Mar-2005  
	 *  Purpose         : To avoid dead lock scenario on CONTRACT_DATES table 
	**/
	public long putChangeOrderViewList(LinkedList<ChangeOrderView> changeOrderViewList)
		throws WBException
	{
		logger.info(Constants.START);
		long orderHeaderIdToDisplay = -1;
		try
		{
			logger.info(Constants.END);

			/* Commented by Prasanth(Infosys)
			 return changeOrderContract.putChangeOrderViewList(
				changeOrderViewList);
			*/
			
			for (Iterator<ChangeOrderView> i = changeOrderViewList.iterator(); i.hasNext();)
			{
				ChangeOrderView view = (ChangeOrderView) i.next();

				orderHeaderIdToDisplay =
					changeOrderContract.putChangeOrderViewList(view);
				
				//Added code to fix CQ 1778
				if(orderHeaderIdToDisplay>0)
					return orderHeaderIdToDisplay;
				//End of code added

			}
			return orderHeaderIdToDisplay;

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
	}

	/**
	 * Gets the amendmentdocumentViewList
	 * @return Returns a LinkedList
	 */
	public LinkedList<Object> getAmendmentDocumentViewList(long changeOrderId)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return changeOrderContract.getAmendmentDocumentViewList(
				changeOrderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
	}
	/**
	 * Sets the amendmentdocumentViewList
	 * @param amendmentdocumentViewList The amendmentdocumentViewList to set
	 */
	public void putAmendmentDocumentViewList(LinkedList<Object> amendmentDocumentViewList)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			changeOrderContract.putAmendmentDocumentViewList(
				amendmentDocumentViewList);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
		logger.info(Constants.END);
	}

	/** Method Modified  By :Infosys (Prasanth Nandanuru) 
	  *Date           : 19-Mar-2005  
	  *Purpose        : To avoid dead lock scenario on ORDER_HEADER and CHANGE_ORDER tables
	  *                 
	  *  
   ****/

//	Modified the method for STARS00001728 on 31Aug06
	public long createChangeOrder(UserVO userVO, long orderHeaderId,boolean isUndoCancel,boolean isCallLetterChange)
		 throws WBException
	 {
		 logger.info(Constants.START);
		 StarsUserTransaction starsUserTrans = null;
		 try
		 {
			 logger.info(Constants.END);
			 /*Prasanth commented to split(create/select and update) as two separate calls to avoid DL on ORDER_HEADER table
			 //return changeOrderContract.createChangeOrder(userId, orderHeaderId);
			  * 
			 */
			
			 //Code added by Infosys (Nageswararao_C) for Transaction Control.
			 starsUserTrans=StarsUserTransaction.getObject();
			 starsUserTrans.begin(this.getClass().getName(),userVO.getUserName(),"Create Change Order","OrderHeaderId="+orderHeaderId);
	
			orderHeaderId=changeOrderContract.createChangeOrder(userVO.getID(), orderHeaderId,isUndoCancel,isCallLetterChange);
					
			 changeOrderContract.updateOrderHeaderFromOrderDetails(orderHeaderId);
			
			 starsUserTrans.commit(userVO.getUserName());
			 //End of code added.
			 return orderHeaderId;
			
			 
		 }
			
		 catch (WBException e)
		 {
			 logger.error("Error occurred: " + e.getMessage());
			 //Tx rolled back because of some exception.
			 if (starsUserTrans!= null )
			 {
				 starsUserTrans.rollback(userVO.getUserName(),"orderHeaderId="+orderHeaderId);
			 }
			 throw e;
		 }
		 catch (Exception e)
		 {
			 logger.error("Error occurred : ", e);
			 //Tx rolled back because of some exception.
			 if (starsUserTrans!= null )
			 {
				 starsUserTrans.rollback(userVO.getUserName(),"orderHeaderId="+orderHeaderId);
			 }
			 throw new WBException(
				 "errors.excep.system.component",
				 new String[] { this.getClass().getName()},
				 e);
		 }			

		 //End of code added for Txn Control					


	 }

	//	Modified for STARS00001728
	public void applyChangeOrder(long userId, long changeOrderHeaderId, String changeOrderAction)
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			changeOrderContract.applyChangeOrder(userId, changeOrderHeaderId,changeOrderAction);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}
		logger.info(Constants.END);
	}

//	Code added for STARS00001728 by Madhuri on 31/08/2006 
	public LinkedList<Object> getContractStatusId(long orderHeaderId) 
		throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return changeOrderContract.getContractStatusId(orderHeaderId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e.getMessage());
		}

	}
//	Code added for STARS00001728 by Madhuri on 31/08/2006 ends

}

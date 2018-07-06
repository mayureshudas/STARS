/*
 * ClientController.java
 *
 * Created on January 8, 2002, 3:25 PM
*/
/* 
 *	Modified By    : Infosys (Prasanth Nandanuru) 
 *	Modified Date  : 29-Sep-2004 
 *  SR Number      : Error Handling 
 *  Purpose        : Added extra data members for embedding WBException /Exception 
*/

package com.wb.stars.control;

import java.io.Serializable;
//import java.rmi.RemoteException;
import java.util.HashMap;
//import java.util.Hashtable;
import java.util.LinkedList;

//import javax.ejb.CreateException;
//import javax.ejb.EJB;
//import javax.ejb.EJBException;
import javax.ejb.Remove;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.naming.NamingException;

import com.wb.stars.clearance.ClearanceWorker;
import com.wb.stars.common.Constants;
import com.wb.stars.control.ejb.EJBTierControllerRemote;
//import com.wb.stars.control.ejb.EJBTierControllerRemoteHome;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.ejb.dropdown.DropDownCacheServiceRemote;
//import com.wb.stars.ejb.dropdown.DropDownCacheServiceRemoteHome;
import com.wb.stars.ejb.search.SearchValueListHandlerRemote;
//import com.wb.stars.ejb.search.SearchValueListHandlerRemoteHome;
//import com.wb.stars.ejb.utils.EJBHomeFactory;
import com.wb.stars.reports.ContractListingSearchCriteria;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBIteratorException;
import com.wb.stars.utils.WBSQLException;
import com.wb.stars.utils.dao.ValueObject;

public class ClientController implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3815192325541596582L;

	//final String SEARCH_VALUELISTHANDLER = "ejb/SearchValueListHandler";
	//@EJB
	private SearchValueListHandlerRemote searchValueListHandlerEJB;
	
	//@EJB
	private DropDownCacheServiceRemote dropDownCacheService;

	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	//final String EJBTIERCONTROLLER_EJBHOME = "EJBTierController";
	// private HttpSession session;
	//@EJB
	private EJBTierControllerRemote ejbTierController;

	public ClientController()
	{

		try {

			//EJBTierControllerRemote ejbTierController = getEJBTierController();
			//searchValueListHandlerEJB = getSearchValueListHandlerEJB();
			Context ctxt = new InitialContext();
			ejbTierController = (EJBTierControllerRemote) ctxt.lookup(JNDINames.EJBTIERCONTROLLER_BUSINESS_REMOTE);
			searchValueListHandlerEJB = (SearchValueListHandlerRemote) ctxt.lookup(JNDINames.SEARCH_VALUELISTHANDLER_BUSINESS_REMOTE);
			dropDownCacheService = (DropDownCacheServiceRemote) ctxt.lookup(JNDINames.DROP_DOWN_CACHE_SERVICE_BUSINESS_REMOTE);
			
		} catch (Exception e) {
			// WBLogger.logtoFile().error(e );
			logger.error(e.getMessage(), e);
		}

	}

	/*private SearchValueListHandlerRemoteHome getSearchValueListHandlerHome()
		throws NamingException
	{

		SearchValueListHandlerRemoteHome qrhome =
			(SearchValueListHandlerRemoteHome) EJBHomeFactory
				.getFactory()
				.lookUpHome(
				JNDINames.SEARCH_VALUELISTHANDLER,
				SearchValueListHandlerRemoteHome.class);
		return qrhome;

	}*/

	// EJB3 annotation added, directly calling Business interface
	/*private EJBTierControllerRemoteHome getEJBTierControllerHome()
		throws NamingException
	{

		EJBTierControllerRemoteHome qrhome =
			(EJBTierControllerRemoteHome) EJBHomeFactory
				.getFactory()
				.lookUpHome(
				JNDINames.EJBTIERCONTROLLER_EJBHOME,
				EJBTierControllerRemoteHome.class);
		return qrhome;

	}*/

//	private LinkedList<Object> getLinkedList(String modelName, Hashtable<Object, Object> params)
//	{
//		LinkedList<Object> ll = null;
//		//EJBTierControllerRemote ejbTierController = null;
//		try
//		{
//			if(ejbTierController != null){
//				ll = ejbTierController.getLinkedList(modelName, params);
//			}
//		}
//		catch (Exception e)
//		{
//			logger.error("Caught Error", e);
//		}
//		return ll;
//
//	}

	/*
		public synchronized GenericModel getGenericModel(
			String modelName,
			Hashtable params) {
			return new GenericModel(getLinkedList(modelName, params));
		}
	
		public synchronized GenericModel getGenericModel(String modelName) {
			return new GenericModel(getLinkedList(modelName, null));
		}
	*/

	// EJB3 annotation added, directly calling Business interface
	/*public SearchValueListHandlerRemote getSearchValueListHandlerEJB()
		throws WBException //
	{

		if (searchValueListHandlerEJB == null)
		{
			try
			{
				searchValueListHandlerEJB =
					getSearchValueListHandlerHome().create();

			}
			catch (RemoteException re)
			{
				logger.error("Caught Error", re);
				throw new WBException(re.getMessage());
			}
			catch (javax.naming.NamingException ne)
			{
				//	WBLogger.logtoFile().error(ne );
				logger.error("Caught Error", ne);
				throw new WBException(ne.getMessage());

			}
			catch (Exception ce)
			{
				// 	WBLogger.logtoFile().error(ce );
				logger.error("Caught Error", ce);
				throw new WBException(ce.getMessage());
			}
		}
		return searchValueListHandlerEJB;
	}*/

	/*public EJBTierControllerRemote getEJBTierController() throws WBException
	{

		EJBTierControllerRemote ejbTierController = null;
		try
		{
			ejbTierController = getEJBTierControllerHome().create();

		}
		catch (RemoteException re)
		{
			//WBLogger.logtoFile().error(re );
			logger.error("Caught Error", re);
			throw new WBException(re.getMessage());
		}
		catch (javax.naming.NamingException ne)
		{
			//	WBLogger.logtoFile().error(ne );
			logger.error("Caught Error", ne);
			throw new WBException(ne.getMessage());

		}
		catch (Exception ce)
		{
			// 	WBLogger.logtoFile().error(ce );
			logger.error("Caught Error", ce);
			throw new WBException(ce.getMessage());
		}

		return ejbTierController;

	}*/
	
	public SearchValueListHandlerRemote getSearchValueListHandlerRemoteEJB()
	throws WBException 
	{
		if (searchValueListHandlerEJB == null)
		{
			try
			{
				Context ctxt = new InitialContext();
				searchValueListHandlerEJB = (SearchValueListHandlerRemote) ctxt.lookup(JNDINames.SEARCH_VALUELISTHANDLER_BUSINESS_REMOTE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return searchValueListHandlerEJB;
	}

	public LinkedList<Object> getNextElementsOffset(int startIndex, int offSet)
		throws WBException
	{
		try
		{

			return getSearchValueListHandlerRemoteEJB().getNextElementsOffset(
				startIndex,
				offSet);

		}
		catch (Exception e)
		{
			//	WBLogger.logtoFile().error(e );
			logger.error("Caught Error", e);
			throw new WBException(" Failed to get Model");
		}
	}

	public LinkedList<Object> getPreviousElements(int count) throws Exception
	{

		try
		{

			return getSearchValueListHandlerRemoteEJB().getPreviousElements(count);

		}
		catch (Exception e)
		{
			//	WBLogger.logtoFile().error(e );
			logger.error("Caught Error", e);
			throw new WBException(" Failed to get Model");
		}

	}

	public LinkedList<Object> getNextElements(int count) throws Exception
	{
		try
		{

			return getSearchValueListHandlerRemoteEJB().getNextElements(count);

		}
		catch (Exception e)
		{
			//		WBLogger.logtoFile().error(e );
			logger.error("Caught Error", e);
			throw new WBException(" Failed to get NextElemnts in Search");
		}

	}

	public int executeSearch(
		HashMap<Object, Object> searchCriteria,
		String searchCode,
		String roleName,
		String userId,
		String sourcePage)
		throws WBException
	{
		try
		{
			if (searchValueListHandlerEJB != null)
			{

				try
				{
					closeSearchAction();
				}
				catch (Exception e)
				{
				}
				finally
				{
					searchValueListHandlerEJB = null;
				}

			}
			/*
			 * Modified By   : Infosys(Kshitindra Jain)
			 * Modified Date : 10-Dec-2004
			 * SR Number	 : 260
			 * Purpose		 : To get all the stations for only those markets that are assigned to the user
			 */
			return getSearchValueListHandlerRemoteEJB().executeSearch(
				searchCriteria,
				searchCode,
				roleName,
				userId,
				sourcePage);

			/* End of modified code for SR#260 */

		}
		catch (Exception e)
		{
			logger.error("Caught Error", e);
			throw new WBException(e.getLocalizedMessage());
		}

	}

	public ValueObject getElement(int currentIndex) throws WBException
	{
		try
		{
			return getSearchValueListHandlerRemoteEJB().getElement(currentIndex);

		}
		
		catch (WBIteratorException e)
				{
					logger.info("Caught Error", e);
					throw new WBIteratorException(" Failed to get Model");
				}
		catch (Exception e)
		{
			logger.error("Caught Error", e);
			throw new WBException(" Failed to get Model");
		}

	}

	public ValueObject getSearchMetaData(String searchCode) throws WBException
	{
		try
		{
			return getSearchValueListHandlerRemoteEJB().getSearchMetaData(searchCode);

		}
		catch (Exception e)
		{
			//	WBLogger.logtoFile().error(e );
			logger.error("Caught Error", e);
			throw new WBException(" Failed to get Model");
		}

	}

	@Remove
	public boolean closeSearchAction()
	{
		boolean success = false;
		try
		{

			if (searchValueListHandlerEJB != null)
			{
				//searchValueListHandlerEJB.remove(); //EJB3 Annotations added - by WAS Upgrade team
				searchValueListHandlerEJB = null;
			}

		}
		catch (Exception e)
		{
			/*Code added for SC41 defect fix on 17/12/2007 
			 * Purpose : To remove the EJB whenever the session for the Stateful bean is timedout*/
			searchValueListHandlerEJB = null;
			//	End of Code added for SC41 defect fix
			logger.error("closeSearchAction Failed ", e);
		}

		return success;
	}

	/************* DropDownCacheService *************/

	// EJB3 annotation added, directly calling Business interface
	/*private DropDownCacheServiceRemoteHome getDropDownCacheServiceHome()
		throws NamingException
	{

		
				InitialContext initial = new InitialContext();
				
				
				
				Object objref = initial.lookup(JNDINames.DROP_DOWN_CACHE_SERVICE);
				return (DropDownCacheServiceRemoteHome) PortableRemoteObject.narrow(
					objref,	DropDownCacheServiceRemoteHome.class);
					
				

		DropDownCacheServiceRemoteHome qrhome =
			(DropDownCacheServiceRemoteHome) EJBHomeFactory
				.getFactory()
				.lookUpHome(
				JNDINames.DROP_DOWN_CACHE_SERVICE,
				DropDownCacheServiceRemoteHome.class);

		return qrhome;

	}*/

	/*private DropDownCacheServiceRemote getDropDownCacheServiceEJB()
		throws WBException
	{
		DropDownCacheServiceRemote dropDownCacheService = null;

		try
		{
			DropDownCacheServiceRemoteHome home = getDropDownCacheServiceHome();
			dropDownCacheService = home.create();
		}
		catch (CreateException ce)
		{
			logger.error("Caught Error", ce);
			throw new WBException("Unable to create a DropDownCacheService EJB ");
		}
		catch (RemoteException re)
		{
			logger.error("Caught Error", re);
			throw new EJBException(re);
		}
		catch (NamingException ne)
		{
			logger.error("Caught Error", ne);
			throw new EJBException(ne);
		}

		return dropDownCacheService;
	}*/

	public LinkedList<Object> getDropDownData(String dropDownName) throws WBException
	{

		try
		{

			/*DropDownCacheServiceRemote dropDownCacheService =
				getDropDownCacheServiceEJB();*/

			return dropDownCacheService.getDropDownData(dropDownName);
		}
		catch (Exception e)
		{
			logger.error("Caught Exception ", e);
			throw new WBException(e.getMessage());
		}

	}

	public LinkedList<Object> getDropDownData(String dropDownName, String params)
		throws WBException
	{

		try
		{

			/*DropDownCacheServiceRemote dropDownCacheService =
				getDropDownCacheServiceEJB();*/

			return dropDownCacheService.getDropDownData(dropDownName, params);
		}
		catch (Exception e)
		{
			logger.error("Caught Exception ", e);
			throw new WBException(e);
		}

	}

	public ClearanceWorker getClearanceWorker() throws Exception
	{
		return new ClearanceWorker();
	}
	/*
	 Modified By      : Infosys (Srinivasa_Ramaswamy)
	 Modified Date    : 11-Jan-2005
	 SR Number        : 7
	 Purpose          : To add new Search Criteria
	*/
	public int executeSearch(
		String searchCode,
		ContractListingSearchCriteria criteria)
		throws WBException
	{
		try
		{
			return getSearchValueListHandlerRemoteEJB().executeSearch(
				searchCode,
				criteria);
		}
		catch (Exception e)
		{
			logger.error("Caught Error", e);
			throw new WBException(e);
		}
	}
	/* Code End for SR 7 */
	
	//START: Code added for SR#307.1
	public int executeSearch(
		long tableId,
		String tableName)
		throws WBException
	{
		try
		{
			return getSearchValueListHandlerRemoteEJB().getTableRecords(tableId,tableName);
		}
		catch (Exception e)
		{
			logger.error("Caught Error", e);
			throw new WBException(e);
		}
	}
	//END: Code added for SR#307.1
	
	/*Code added by Infosys (Suma) for SC41 on 22/09/2007 */
	
		 public int setOrderDetails(LinkedList<Object> orderDetailsIdList,String searchCode)
				 throws WBSQLException
			 {
				 try
				 {
					 return getSearchValueListHandlerRemoteEJB().setOrderDetails(orderDetailsIdList,searchCode);
				
				 }
				 catch (Exception e)
				 {
					 logger.error("Caught Error", e);
					 throw new WBSQLException(
						 "errors.excep.system.component",
						 new String[] { this.getClass().getName()},
						 e);
				 }
			 }
//	End of Code added by Infosys (Suma) for SC41
	//code added by Infosys(rajeev) for SC37	
		 public LinkedList<Object> getProductList(String orderHeaderId,String searchCode)
				 throws WBSQLException
			 {
				 try
				 {
					 return getSearchValueListHandlerRemoteEJB().getProductList(orderHeaderId,searchCode);
				
				 }
				 catch (Exception e)
				 {
					 throw new WBSQLException(
						 "errors.excep.system.component",
						 new String[] { this.getClass().getName()},
						 e);
				 }
			 }
		//End of code added for SC 37
		
		//CQ2845: Added to fetch all Field Orders that have been added as amendments which are in pending status- added by xlpatnai 	
		public LinkedList<Object> fetchAmendmentList() throws WBException{
			try{
				return getSearchValueListHandlerRemoteEJB().fetchAmendmentList();
			}
			catch (Exception e){
				//	WBLogger.logtoFile().error(e );
				logger.error("Caught Error", e);
				throw new WBSQLException(
						 "errors.excep.system.component",new String[] { this.getClass().getName()},e);
			}
		}
		//CQ2845:Ends
	
}
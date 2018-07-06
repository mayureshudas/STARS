package com.wb.stars.security;
//import java.rmi.RemoteException;
import java.util.LinkedList;

//import javax.ejb.CreateException;
//import javax.ejb.EJB;
//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import com.ibm.bsf.util.Bean;
import com.wb.stars.common.Constants;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemote;
//import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemoteHome;
import com.wb.stars.securityMaint.FieldAccessLevelViewVO;
import com.wb.stars.securityMaint.MenuRoleRuleViewVO;
import com.wb.stars.securityMaint.ScreenRoleRuleViewVO;
import com.wb.stars.securityMaint.UsersMaintVO;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBFinderException;
import com.wb.stars.utils.WBSQLException;

/**
 * Manages interaction with security beans
 */
public class SecurityWorker
{

	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	//@EJB(name="beanName", beanInterface = Bean.class);
	//@EJB(name="ejb/SecurityFacadeRemote")
	private SecurityFacadeRemote m_security;

	
	
	// EJB3 annotation added, directly calling Business interface
	public SecurityWorker() throws WBException
	{
		try
		{
			//get a reference to the security object
			/*SecurityFacadeRemoteHome home =
				SecurityHomes.getSecurityFacadeHome();
			m_security = home.create();*/
			Context ctxt = new InitialContext();
			m_security = (SecurityFacadeRemote) ctxt.lookup(JNDINames.SECURITY_FACADE_BUSINESS_REMOTE);
			//Bean beanInstance = (Bean) new InitialContext().lookup("java:comp/env/beanName");
			
		}
		catch ( NamingException e )
		{
			logger.error("SecurityFacadeRemote.class", e );
			//WBLogger.getReference().logError( SecurityHomes.class, e );
			throw new WBException( e );
		}

		logger.info(Constants.CREATED);
	}

	//
	//	/**
	//	 * returns the user's logon name
	//	 * @param id user to search for
	//	 */
	//	public String userLogonName(long id) throws WBException
	//	{
	//
	//		logger.info(Constants.START);
	// try
	//		{
	//		logger.info(Constants.END);
	//return m_security.findUser(id).getUserName();
	//		}
	//
	//		catch (RemoteException e)
	//		{
	//			logger.error(e);
	//			//WBLogger.getReference().logError( SecurityHomes.class, e );
	//			throw new WBException( e );
	//		}
	//       logger.info(Constants.END);
	//	}

	public UserVO logon(String userName, String password)
		throws WBException, WBSQLException
	{


		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.logon(userName, password);
		}

		
		catch (WBFinderException e)
		{
			throw e;
		}
		
		
		catch (Exception e)
		{
			//Begin changes towards CQ 2066 by Harish
			 if (e.getMessage().indexOf("com.ibm.websphere.ce.cm.StaleConnectionException") != -1) {
				 throw new WBSQLException(
					 "errors.excep.stateConnection",
					 new String[] {"com.ibm.websphere.ce.cm.StaleConnectionException"},
					 e);
			 }
			// End of changes towards CQ 2066
			
			logger.error(e);
			//logger.error( e );
			throw new WBException(e);
		}

	}

	/*
	 * Modified By      : Infosys (Nageswararao Cheedella)
	 * Modified Date    : 20-Oct-2004
	 * SR Number        : 302
	 * Purpose          : To Implement the Menu Level & Screen Level Security
	 */

	/**
	 * Get the Menu Access Levels for the user logged in depending on his role.
	 * @param long roleId
	 * @return LinkedList menuAccessList
	 */

	public LinkedList<Object> getMenuAccess(long roleId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		LinkedList<Object> menuAccessList;
		try
		{
			logger.info(Constants.END);
			menuAccessList = m_security.getMenuAccess(roleId);
			return menuAccessList;
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new WBEJBException(e);
		}
	}

	/**
	 * Get the All the DataObjects
	 * @param 
	 * @return LinkedList dataObjectsList
	 */

	public LinkedList<Object> getDataObjects() throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		LinkedList<Object> dataObjectsList;
		try
		{
			logger.info(Constants.END);
			dataObjectsList = m_security.getDataObjects();
			return dataObjectsList;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/**
	 * Get the Menu Access Levels for the user logged in depending on his role.
	 * 
	 * @return LinkedList screenAccessLevelsList
	 */
	public LinkedList<Object> getScreenDetailsFromDB()
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		LinkedList<Object> screenAccessList;
		try
		{
			logger.info(Constants.END);
			screenAccessList = m_security.getScreenDetailsFromDB();
			return screenAccessList;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/**
	 * Gets the RoleID 
	 * @param UserID
	 * @return Returns long RoleID 
	*/
	public LinkedList<Object> getRoleDetails(long userId) throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.getRoleDetails(userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * Gets the Roles 
	 * 
	 * @return Returns LinkedList allRolesList
	*/
	public LinkedList<Object> getRolesListFromDB() throws WBException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.getRolesListFromDB();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * Gets the ScreenRoleAccessLevels Details 
	 * @param long screenId
	 * @return Returns ScreenRoleRuleViewVO 
	 */
	public ScreenRoleRuleViewVO createScreenRoleRuleViewVO(long screenId)
		throws WBException
	{
		try
		{
			return m_security.createScreenRoleRuleViewVO(screenId);
		}
		catch (WBSQLException e)
		{
			logger.error(
				" Error occurred while fetching ScreenRolesDetailsList from DB"
					+ e.getMessage());
			throw new WBException(e);
		}
		catch (WBException e)
		{
			logger.error(
				" Error occurred while fetching ScreenRolesDetailsList from DB"
					+ e.getMessage());
			throw e;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}

	/**
	 * Gets the FieldRoleAccessLevels Details 
	 * @param long screenId
	 * @param long roleId
	 * @return Returns ScreenRoleRuleViewVO 
	 */
	public FieldAccessLevelViewVO createFieldAccessLevelViewVO(
		long screenId,
		long roleId)
		throws WBException
	{
		try
		{
			return m_security.createFieldAccessLevelViewVO(screenId, roleId);
		}
		catch (WBSQLException e)
		{
			logger.error(
				"  Error occurred while fetching FieldRolesDetailsList from DB"
					+ e.getMessage());
			throw new WBException(e);
		}
		catch (WBException e)
		{
			logger.error(
				" Error occurred while fetching FieldRolesDetailsList from DB"
					+ e.getMessage());
			throw e;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}

	/**
	 * Gets the ScreenRoleAccessLevels Details 
	 * @param ScreenRoleRuleViewVO  
	 */

	public void updateScreenRoleRuleViewVO(ScreenRoleRuleViewVO screenRoleRuleViewVO)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			m_security.updateScreenRoleRuleViewVO(screenRoleRuleViewVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * Gets the FieldRoleAccessLevels Details 
	 * @param FieldAccessLevelViewVO  
	 */

	public void updateFieldAccessLevelViewVO(FieldAccessLevelViewVO fieldAccessLevelViewVO)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			m_security.updateFieldAccessLevelViewVO(fieldAccessLevelViewVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * Gets the FieldAccessLevelId 
	 * @param int screenId,
	 * @param String fieldName
	 * @param int roleId
	 * @return int 
	 */
	public int getFieldAccessLevelId(
		int screenId,
		String fieldName,
		int roleId)
		throws WBException
	{
		try
		{
			return m_security.getFieldAccessLevelId(
				screenId,
				fieldName,
				roleId);
		}
		catch (WBSQLException e)
		{
			logger.error(
				" Error Occured while getting the fieldAccessLevelId from DB"
					+ e.getMessage());
			throw new WBException(e);
		}
		/*catch (WBException e)
		{
			logger.error(
				" Error Occured while getting the fieldAccessLevelId from DB"
					+ e.getMessage());
			throw e;
		}*/
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}

	/**
	* Gets the screenAccessLevelId 
	* @param int screenId,
	* @param int roleId
	* @return long 
	*/
	public long getScreenAccessLevelId(int screenId, int roleId)
		throws WBException
	{
		try
		{
			return m_security.getScreenAccessLevelId(
				screenId,				
				roleId);
		}
		catch (WBSQLException e)
		{
			logger.error(
				" Error Occured while getting the screenAccessLevelId from DB"
					+ e.getMessage());
			throw new WBException(e);
		}
		/*catch (WBException e)
		{
			logger.error(
				" Error Occured while getting the fieldAccessLevelId from DB"
					+ e.getMessage());
			throw e;
		}*/
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}
	}
	/**
	 * Gets the MenuRoleAccessLevels Details 
	 * @param long dataObjId
	 * @return Returns MenuRoleRuleViewVO 
	 */
	public MenuRoleRuleViewVO createMenuRoleRuleViewVO(long dataObjId)
		throws WBException
	{

		try
		{
			return m_security.createMenuRoleRuleViewVO(dataObjId);
		}
		catch (WBSQLException e)
		{
			logger.error(
				" Error Occured while getting the MenuRolesDetailsList from DB"
					+ e.getMessage());
			throw new WBException(e);
		}
		catch (WBException e)
		{
			logger.error(
				" Error Occured while getting the MenuRolesDetailsList from DB"
					+ e.getMessage());
			throw e;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBException(e);
		}

	}

	/**
	 * Gets the MenuRoleAccessLevels Details 
	 * @param MenuRoleRuleViewVO  
	 */
	public void updateMenuRoleRuleViewVO(MenuRoleRuleViewVO menuRoleRuleViewVO)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			m_security.updateMenuRoleRuleViewVO(menuRoleRuleViewVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/* end of added code for SR 302 */

	/*
	 * Modified By      : Infosys (KshitindraK_Jain)
	 * Modified Date    : 20-Nov-2004
	 * SR Number        : 341
	 * Purpose          : To Implement the Role Maintenance screens
	 */

	/**
	*get all the roles available in the system
	*@return list of all the roles available in the system
	*/
	public LinkedList<Object> getAllRoles() throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.getAllRoles();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	/**
	 * updates the name of the role and other related details
	 *@param updated Role Name, original name of the role,role Id, user id of the modifier 
	 */

	public void updateRoleName(
		String newRoleName,
		String oldRoleName,
		String roleId,
		String userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			m_security.updateRoleName(newRoleName, oldRoleName, roleId, userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * adds a new role in the system 
	 *@param updated Role Name, original name of the role,role Id, user id of the modifier 
	 */

	public void addNewRole(String roleName, String userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			m_security.addNewRole(roleName, userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/**
	 * delete a role from the system 
	 *@param role id of the role to be deleted
	 *@return boolean whether the users of the roles are present in the system or not.  
	 */
	public boolean deleteRole(String roleIdToDelete)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.deleteRole(roleIdToDelete);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/* End  of added code for SR 341 */

	/*
	 * Modified By      : Infosys (Kshitindra Jain)
	 * Modified Date    : 29-Dec-2004
	 * SR Number        : 342
	 * Purpose          : To implement the change password screens                               
	 */

	public UsersMaintVO getUserDetails(String userId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.getUserDetails(userId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public int changePwd(
		String userId,
		String oldPwd,
		String newPwd,
		Long loginUserId,
		long loginRole,//Chaged the data type for SR#398
		int passwordRequired)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.changePwd(
				userId,
				oldPwd,
				newPwd,
				loginUserId,
				loginRole,
				passwordRequired);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public Long getUserId(String userName) throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.getUserId(userName);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	/*End of code added for SR 342*/

	/*
	 * Modified By      : Infosys (Kshitindra Jain)
	 * Modified Date    : 6-Jan-2005
	 * SR Number        : 352
	 * Purpose          : To get all the users for a particular role                               
	 */

	public LinkedList<Object> getUsersForRole(long roleId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return m_security.getUsersForRole(roleId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

}

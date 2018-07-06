package com.wb.stars.common;

import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ejb.EJB;
import com.wb.stars.common.ejb.activeProductList.ActiveProductListManagerRemote;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.web.apl.ActiveProductListForm;

public class ActiveProductListWorker {
	private static StarsLogger logger =
			(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	@EJB
	private ActiveProductListManagerRemote activeProductListManager;
	public ActiveProductListWorker()
	{
		try
		{
			Context ctxt = new InitialContext();
			activeProductListManager = (ActiveProductListManagerRemote)ctxt.lookup(JNDINames.ACTIVE_PRODUCT_LIST_BUSINESS_REMOTE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public LinkedList<Object> getProductList(ActiveProductListForm myForm)
	{
		return activeProductListManager.getProductList(myForm);
	}
	
	public LinkedList<Object> getVolumeList(ActiveProductListForm myForm)
	{
		return activeProductListManager.getVolumeList(myForm);
	}
}

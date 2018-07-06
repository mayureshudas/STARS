/* Modified By     : Infosys (Prasanth Nandanuru) 
 * Modified Date   : 4-Nov-2004  
 * Purpose         : Added Error Handlers in 
 * public/default/protected methods for exception handling*/
/*
   Modified By      : Infosys (Satya Suma)
   Modified Date    : 12-Sep-2007
   Purpose          : Added Add Multiple Episodes functionality for SC46
*/
package com.wb.stars.common;

import java.rmi.RemoteException;
import java.util.LinkedList;
//import java.util.Iterator;

//import javax.ejb.CreateException;
//import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.wb.stars.common.dao.db2.commands.FamilyLD;
import com.wb.stars.common.dao.db2.commands.FamilyWhereClause;
import com.wb.stars.common.dao.db2.commands.FeatureLD;
import com.wb.stars.common.dao.db2.commands.FeatureWhereClause;
import com.wb.stars.common.dao.db2.commands.ProductLD;
import com.wb.stars.common.dao.db2.commands.ProductRuleWhereClause;
import com.wb.stars.common.dao.db2.commands.ProductTermWhereClause;
import com.wb.stars.common.dao.db2.commands.ProductWhereClause;
import com.wb.stars.common.ejb.product.ProductManagerRemote;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.history.HistoryDetailsVO;

//import java.util.Collection;
import com.wb.stars.productMaster.ProductDefaultsMetaData;
import com.wb.stars.productMaster.ProductDefaultsViewVO;
import com.wb.stars.productMaster.ProductEpisodeMetaData;
import com.wb.stars.productMaster.ProductEpisodeViewVO;
import com.wb.stars.productMaster.ProductRulesMetaData;
import com.wb.stars.productMaster.ProductRulesViewVO;
import com.wb.stars.productMaster.ProductSummaryViewVO;
import com.wb.stars.productMaster.ProductionSeasonsMetaData;
//import com.wb.stars.security.ejb.securityFacade.SecurityFacadeRemote;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCreateException;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBFinderException;
import com.wb.stars.utils.WBSQLException;
import com.wb.stars.utils.WBUpdateException;
//import java.rmi.RemoteException;

public class ProductsWorker
{
	private static StarsLogger logger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);

	/*private static StarsLogger eventsLogger =
		(StarsLogger) StarsLogger.getLogger(Constants.STARS_EVENTS_LOGGER);*/

	/**s
	 * Manages interaction with Clearance beans
	 */
	//@EJB
	private ProductManagerRemote productManager;

	// EJB3 annotation added, directly calling Business interface
	public ProductsWorker() throws WBException
	{
		try
		{
			//productManager = getRemote();
			Context ctxt = new InitialContext();
			productManager = (ProductManagerRemote) ctxt.lookup(JNDINames.PRODUCT_MANAGER_BUSINESS_REMOTE);
		}
		catch ( NamingException e )
		{
			logger.error("ProductManagerRemote.class", e );
			throw new WBException( e );
		}
		logger.info(Constants.CREATED);
	}

	/*public ProductManagerRemote getRemote() throws WBException
	{
		logger.info(Constants.START);

		if (productManager == null)
		{
			try
			{
				productManager = CustomerHomes.getProductRemoteHome().create();
			}
			catch (RemoteException re)
			{
				logger.error(re);
				throw new WBEJBException(re);
			}
			catch (CreateException re)
			{
				logger.error(re);
				throw new WBEJBException(re);
			}
		}
		logger.info(Constants.END);
		return productManager;

	}*/

	public AbstractProductVO findProductById(long productId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			ProductWhereClause where = new ProductWhereClause();
			where.setProductId(productId);
			LinkedList<Object> productList =
				productManager.findProductsByWhere(where, new ProductLD());
			if (!productList.isEmpty())
			{

				logger.info(Constants.END);
				return (AbstractProductVO) productList.getFirst();
			}
			else
			{

				logger.info(Constants.END);
				return null;
			}
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	
	
	//Code added to fix CQ 2006
	public boolean checkFamilyDependency(long familyId) throws WBSQLException,WBException,RemoteException
	{
		return productManager.checkFamilyDependency(familyId);
	}
	
	
	public boolean checkProductDependency(long productId) throws WBSQLException,WBException,RemoteException
	{
		return productManager.checkProductDependency(productId);
	}
	//End of code added to fix CQ 2006

	//Code added to fix SR #141
	public boolean checkIfInContract(long episodesId)
		throws WBSQLException, RemoteException
	{
		return productManager.checkIfInContract(episodesId);
	}
	/*code added for SR#158*/
	public LinkedList<Object> getAllHighestSyndRuns(long epsfeaId, String strType)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getAllHighestSyndRuns(epsfeaId, strType);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	//	CQ #2416-Modified the function by passing familyId
	  public long getNextEpisodeSeqNumber(long productionSeasonId,long familyId)
		  throws WBException, WBSQLException
	  {
		  logger.info(Constants.START);
		  try
		  {
			  logger.info(Constants.END);
			  //CQ #2416-Modified the function call by passing familyId
			  return productManager.getNextEpisodeSeqNumber(
							  productionSeasonId,familyId);
		  }
		  catch (Exception e)
		  {
			  logger.error(e.getMessage(), e);
			  throw new WBEJBException(e);
		  }

	  }

	public ProductSummaryViewVO createProductSummaryViewVO(long productId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			ProductWhereClause where = new ProductWhereClause();
			where.setProductId(productId);
			logger.info(Constants.END);
			return productManager.createProductSummaryViewVO(where);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ProductRulesViewVO createProductRulesViewVO(long productId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			ProductRulesMetaData metaData = new ProductRulesMetaData();
			metaData.setProductId(productId);
			logger.info(Constants.START);
			return productManager.createProductRulesViewVO(metaData);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> createProductTemplatesViewVO(long productId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.createProductTemplatesViewVO(productId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ProductDefaultsViewVO createProductDefaultsViewVO(long productId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			ProductDefaultsMetaData metaData = new ProductDefaultsMetaData();
			metaData.setProductId(productId);
			logger.info(Constants.END);
			return productManager.createProductDefaultsViewVO(metaData);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*
	* Modified By      : Infosys (Siva Prasad Dhulipala)
	* Modified Date    : 07-Dec-2004 
	* SR Number        : 159 
	* Purpose          : To check if the feature to be deleted is present in any Field order or product
	*
	*/

	//Code added to fix SR #159
	public LinkedList<Object> checkFeatureInVolFO(long featureId)
		throws WBSQLException, WBEJBException, RemoteException
	{
		try
		{
			logger.info(Constants.START);
			return productManager.checkFeatureInVolFO(featureId);
		}
		catch (WBSQLException e)
		{
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	

	

	public ProductDefaultsViewVO updateProductDefaultsViewVO(ProductDefaultsViewVO viewVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductDefaultsViewVO(viewVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> createProductionSeasonsViewVO(ProductionSeasonsMetaData metaData)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.createProductionSeasonsViewVO(metaData);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}

	public ProductSummaryViewVO updateProductSummaryViewVO(ProductSummaryViewVO viewVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductSummaryViewVO(viewVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ProductionSeasonsVO updateProductionSeasonsVO(ProductionSeasonsVO productionSeasonsVO)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductionSeasonsVO(
				productionSeasonsVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public boolean checkActiveProductSeasons(ProductionSeasonsVO productionSeasonsVO)
		throws WBException, WBSQLException
	{
		try
		{
			logger.info(Constants.START);
			return productManager.checkActiveProductSeasons(productionSeasonsVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	

	public LinkedList<ProductionSeasonsVO> updateProductionSeasonsVO(LinkedList<ProductionSeasonsVO> productionSeasonsVOs)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductionSeasonsVO(
				productionSeasonsVOs);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//COSMOS 16: Stars
	public void  updateMPMHistory(HistoryDetailsVO historyDetailsVO)
			throws WBException, WBSQLException
		{
			logger.info(Constants.START);
			try
			{
				logger.info(Constants.END);
				productManager.updateMPMHistory(historyDetailsVO);
			}
			catch (WBEJBException e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}

		}
		//COSMOS 16
	public ProductEpisodeViewVO createProductEpipsodeViewVO(ProductEpisodeMetaData metaData)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.createProductEpisodeViewVO(metaData);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}







	}

	public ProductEpisodeViewVO updateProductEpipsodeViewVO(ProductEpisodeViewVO viewVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductEpisodeViewVO(viewVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
	//COSMOS 14
	public LinkedList<Object> getSelectedMedia(long productId) throws WBUpdateException,WBException, WBSQLException,RemoteException
	{

		return productManager.getSelectedMedia(productId);
	}
	
	public void updateMediaInfo(String mediaObjId,long prodId) throws WBUpdateException,WBException, WBSQLException,RemoteException{
			
			productManager.updateMediaInfo(mediaObjId,prodId);
		}
	 //COSMOS 14


	public EpisodesVO updateEpisodesVO(EpisodesVO episodesVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateEpisodesVO(episodesVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}
/*Code added by Infosys (Suma) for SC46 on 09/12/2007 */
 //Purpose:Method added to insert a list of episodes in the database
   public void updateMultipleEpisodes(LinkedList<Object> episodesVOsList)
   throws WBUpdateException, WBCreateException, WBException, WBSQLException
   {
	logger.info(Constants.START);
		try
			{
				logger.info(Constants.END);
				productManager.updateMultipleEpisodes(episodesVOsList);
			}
			catch (WBEJBException e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}	
   }
// End of Code added by Infosys (Suma) for SC46
	public LinkedList<Object> findFamilyList(int familyTypeId, boolean asDropDown)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{

			FamilyWhereClause where = new FamilyWhereClause();
			where.setProgramTypeId(familyTypeId);
			FamilyLD ld = new FamilyLD();
			ld.setNeedAsDropDown(asDropDown);
			logger.info(Constants.END);
			return productManager.findFamilyByWhere(where, ld);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public FamilyVO findFamily(long familyId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			FamilyWhereClause where = new FamilyWhereClause();
			where.setFamilyId(familyId);
			FamilyLD ld = new FamilyLD();
			ld.setNeedAsDropDown(false);
			ld.setNeedDistChannels(true);
			ld.setNeedProductionSeasons(true);
			ld.setNeedFamilyLength(true);
			LinkedList<Object> result = productManager.findFamilyByWhere(where, ld);
			if (result.isEmpty())
			{
				logger.info(Constants.END);
				return null;
			}
			else
			{
				logger.info(Constants.END);
				return (FamilyVO) result.getFirst();
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public FeatureVO findFeature(long featureId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			FeatureWhereClause where = new FeatureWhereClause();
			where.setFeatureId(featureId);
			FeatureLD ld = new FeatureLD();
			ld.setNeedDropDown(false);
			ld.setNeedFeatureGuidanceRatings(true);
			ld.setNeedDistributionChannels(true);

			/* 
			 * Modified By    : Infosys (Siva Puvvada) 
			 * Modified Date  : 21-Oct-2004 
			 * SR Number      : 146 
			 * Purpose        : To fetch residual information. 
			 * */

			ld.setNeedResidual(true);

			/* End of added code for SR#146 */

			LinkedList<Object> result = productManager.findFeatureByWhere(where, ld);
			if (result.isEmpty())
			{
				logger.info(Constants.START);
				return null;
			}
			else
			{
				logger.info(Constants.START);
				return (FeatureVO) result.getFirst();
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public FeatureVO updateFeatureVO(FeatureVO featureVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateFeatureVO(featureVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> findFeatureList(boolean needExpired)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			FeatureWhereClause where = new FeatureWhereClause();
			FeatureLD ld = new FeatureLD();
			ld.setNeedDropDown(true);
			logger.info(Constants.END);
			return productManager.findFeatureByWhere(where, ld);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public AbstractProductVO updateProductVO(AbstractProductVO productVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{

		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductVO(productVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ProductRuleVO findProductRule(long ruleId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			ProductRuleWhereClause where = new ProductRuleWhereClause();
			where.setProductRuleId(ruleId);
			com.wb.stars.common.dao.db2.commands.ProductRuleLD ld =
				new com.wb.stars.common.dao.db2.commands.ProductRuleLD();
			LinkedList<Object> result =
				productManager.findProductRuleByWhere(where, ld);
			if (result.isEmpty())
			{
				logger.info(Constants.END);
				return null;
			}
			else
			{
				logger.info(Constants.END);
				return (ProductRuleVO) result.getFirst();
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ProductTermVO findProductTerm(long productTermId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			ProductTermWhereClause where = new ProductTermWhereClause();
			where.setProductTermId(productTermId);
			where.setIgnoreDistSystem(true);
			ProductTermLD ld = new ProductTermLD();
			LinkedList<Object> result =
				productManager.findProductTermByWhere(where, ld);
			if (result.isEmpty())
			{
				logger.info(Constants.END);
				return null;
			}
			else
			{
				logger.info(Constants.END);
				return (ProductTermVO) result.getFirst();
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public ProductTermVO updateProductTermVO(ProductTermVO productTermVO)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductTermVO(productTermVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public FamilyVO updateFamilyVO(FamilyVO familyVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateFamilyVO(familyVO);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> updateProductEpipsodeVO(
		LinkedList<Object> productEpisodesVOs,
		String resequencingRequired)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductEpipsodeVO(
				productEpisodesVOs,
				resequencingRequired);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*
	 Modified By      : Infosys (Kshitindra Jain)
	 Modified Date    : 20-Dec-2004 
	 SR Number        : 47 
	 Purpose          : Modified the code for Resequencing the Episode number in product_episode table 
	*/
	public void getRecalculateEpisodeSeqNumberForProductEpisode(long productId)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			productManager.getRecalculateEpisodeSeqNumberForProductEpisode(
				productId);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	/* End of modified code for SR47*/
	public ProductRulesViewVO updateProductRulesViewVO(ProductRulesViewVO viewVO)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.updateProductRulesViewVO(viewVO);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	/*public long getNextEpisodeSeqNumber(long productionSeasonId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
		             return productManager.getNextEpisodeSeqNumber(productionSeasonId);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}*/

	public long getEpisodeCountByFamily(long familyId)
		throws WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getEpisodeCountByFamily(familyId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}

	}

	public LinkedList<Object> loadVolumeFeaturesDropDown()
		throws WBFinderException, RemoteException
	{
		logger.info(Constants.START);
		logger.info(Constants.END);
		return loadVolumeFeaturesDropDown();
	}

	public CommentVO getProductCommentVO(long productId)
		throws WBUpdateException, WBCreateException, WBException, WBSQLException
	{
		logger.info(Constants.START);
		try
		{
			logger.info(Constants.END);
			return productManager.getProductCommentVO(productId);
		}
		catch (WBEJBException e)
		{
			logger.error(e.getMessage(), e);
			throw new WBEJBException(e);
		}
	}
	
//	CQ 2448: Validate whether a family has active products
  public boolean validateDeleteFamily(long familyId) throws WBSQLException,RemoteException
  {
  	try
  	{
		return productManager.validateDeleteFamily(familyId);
  	}
  	catch (WBSQLException e)
  	{
  		logger.error(e);
  		throw e;
  	}	
  }
//CQ 2448: Ends
	/*Code added by Infosys (Omprakash Reddy) for SC03 on 10/29/2007
		Purpose:----To call the CheckMpmProductNumber method to check entered number 
		is present in the BRM_MASTER table or not---------*/
		public boolean checkMpmProductNumber(String mpmProductNumber) throws WBSQLException, WBException 
			{
				logger.info(Constants.START);
				try
				{
				logger.info(Constants.END);
				return productManager.checkMpmProductNumber(mpmProductNumber);
				}
				catch (WBEJBException e)
				{
					logger.error(e.getMessage(), e);
					throw new WBEJBException(e);
				}
			}
		public boolean checkBrmNumber(String brmNumber) throws WBSQLException, WBException 
				{
					logger.info(Constants.START);
					try
					{
					logger.info(Constants.END);
					return productManager.checkBrmNumber(brmNumber);
					}
					catch (WBEJBException e)
					{
						logger.error(e.getMessage(), e);
						throw new WBEJBException(e);
					}
				}
		public boolean checkBrmEpisodeNumber(String brmEpisodeNumber) throws WBSQLException, WBException 
					{
						logger.info(Constants.START);
						try
						{
						logger.info(Constants.END);
						return productManager.checkBrmEpisodeNumber(brmEpisodeNumber);
						}
						catch (WBEJBException e)
						{
							logger.error(e.getMessage(), e);
							throw new WBEJBException(e);
						}
					}
		public boolean checkMpmEpisodeNumber(String mpmEpisodeNumber) throws WBSQLException, WBException 
				{
					logger.info(Constants.START);
					try
					{
					logger.info(Constants.END);
					return productManager.checkMpmEpisodeNumber(mpmEpisodeNumber);
					}
					catch (WBEJBException e)
					{
						logger.error(e.getMessage(), e);
						throw new WBEJBException(e);
					}
				}			
		//end of code added by Infosys(Omprakash Reddy) for SC03.
		
		//CQ #2724 Starts	
		public boolean existsFamilyName(String familyName)
			throws WBException, WBSQLException
		{
			logger.info(Constants.START);
			try
			{
				logger.info(Constants.END);
				return productManager.existsFamilyName(familyName);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
		}
		//CQ #2724 Ends
		//CQ 2747: Validate whether the column value in a table already exist in a table
		public boolean doesValueAlreadyExists(String columnName, String columnValue, String tableName,String whereCondition)throws WBException,WBSQLException
		{
			logger.info(Constants.START);
			try
			{						
				return productManager.doesValueAlreadyExists(columnName,columnValue,tableName,whereCondition);
			}
			catch (WBSQLException wbsqlExcep)
			{
				throw new WBSQLException(
					"errors.excep.system.component",
					new String[] { this.getClass().getName()},
					wbsqlExcep);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
				throw new WBEJBException(e);
			}
		}
		//CQ #2747 Ends

}

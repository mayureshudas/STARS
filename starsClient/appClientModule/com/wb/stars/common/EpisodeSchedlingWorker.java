package com.wb.stars.common;

import java.util.LinkedList;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.wb.stars.accounting.ArDefaultsVO;
import com.wb.stars.accounting.dao.db2.commands.EpisodeSaveChangesWhereClause;
import com.wb.stars.common.ejb.episodeScheduling.EpisodeSchedulingManagerRemote;
import com.wb.stars.control.log.StarsLogger;
import com.wb.stars.utils.JNDINames;
import com.wb.stars.utils.WBCreateException;
import com.wb.stars.utils.WBEJBException;
import com.wb.stars.utils.WBException;
import com.wb.stars.utils.WBSQLException;
import com.wb.stars.utils.WBUpdateException;
import com.wb.stars.web.episode.EditExistingScheduleForm;
import com.wb.stars.web.episode.EpisodicSeparationForm;
import com.wb.stars.web.episode.createScheduleForm;

public class EpisodeSchedlingWorker
{
	private static StarsLogger logger =
			(StarsLogger) StarsLogger.getLogger(Constants.STARS_APP_LOGGER);
	
	@EJB
	private EpisodeSchedulingManagerRemote episodeSchedulingManager;
	
	
	public EpisodeSchedlingWorker()
	{
		try
		{
			Context ctxt = new InitialContext();
			episodeSchedulingManager = (EpisodeSchedulingManagerRemote)ctxt.lookup(JNDINames.EPISODE_SCHEDULING_BUSINESS_REMOTE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public LinkedList<Object> getProductDropdown()
	{
		return episodeSchedulingManager.getProductDropdownList();
	}
	
	public LinkedList<Object> getProductSeasonId()
	{
		return episodeSchedulingManager.getEproductionSeasonCode();
		
	}
	
	public int checkExistingSchedule(int product_id, int season_id)
	{
		return episodeSchedulingManager.checkExistingSchedule(product_id, season_id);
	}
	public int noOFEpisodeSchedule(int noofepisodes)
	{
		return episodeSchedulingManager.noOFEpisodeSchedule(noofepisodes);
	}
	
	public int updateCreateScheduling(createScheduleForm myForm)
		{
				return episodeSchedulingManager.updateCreateScheduling(myForm);
			
		}
	
	public int findDuplicatescheduleName(int product_id, int season_id, String scheduleName) {
		// TODO Auto-generated method stub
		return episodeSchedulingManager.findDuplicatescheduleName(product_id,season_id,scheduleName);
	}
	
	public LinkedList<Object> editExistingSchedule(int product_id, int season_id)
	{
		return episodeSchedulingManager.editExistingSchedule(product_id, season_id);
	}
	
	public LinkedList<Object> loadedischedulingdetails(int schedule_header_id)  throws WBSQLException,WBException
	{
		return episodeSchedulingManager.loadedischedulingdetails(schedule_header_id);
	}
	
	public LinkedList<Object> saveSchedulingChanges(EpisodeSchedulingVO epsVO)
	{
			return episodeSchedulingManager.saveSchedulingChanges(epsVO);
		
	}
	

	public LinkedList<Object> deleteSchedulingDetails(int schedule_header_id) {
		
		return episodeSchedulingManager.deleteSchedulingDetails(schedule_header_id);
		
	}
	
	public LinkedList<Object> viewByEpisode(int schedule_id) {

		return episodeSchedulingManager.viewByEpisode(schedule_id);
	}
	
	public LinkedList<Object> viewByDate(int schedule_id) {

		return episodeSchedulingManager.viewByDate(schedule_id);
	}
	public LinkedList<Object>deleterundetails(int schedule_header_id)
	{
		return episodeSchedulingManager.deleterundetails(schedule_header_id);	
	}


	public LinkedList<Object>  deleteSelectedRowsDetails(EpisodeSchedulingVO epsVO) {
		return episodeSchedulingManager.deleteSelectedRowsDetails(epsVO);
		
	}

	public int popupappendSchedule(createScheduleForm myForm) {
		return episodeSchedulingManager.popupappendSchedule(myForm);
		
	}
	
	public LinkedList<Object> getEpisodicSeperation(int prod_id, int seas_id,
			int noofWeeks)
	{
		return episodeSchedulingManager.getEpisodicSeperation(prod_id, seas_id, noofWeeks);
	}
}

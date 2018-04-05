package com.lgcns.ikep4.support.searchpreprocessor.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
/**
 *   달력만드는 소스
 *  * @see  #SUNDAY 1
     * @see #MONDAY 2
     * @see #TUESDAY 3
     * @see #WEDNESDAY 4
     * @see #THURSDAY 5
     * @see #FRIDAY 6
     * @see #SATURDAY 7
 */
public final class CalUtils extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 8480426357819942271L;
/**
 * 일별달력
 * @param start
 * @param end
 * @return
 */
	public static List<Report> makeCalendar(Date start,Date end){
		List<Report> calList = new ArrayList<Report>();
		Date startDt = start;
		Date endDt = end;
		Date today = new Date();
		
		if( today.before(endDt) ){ endDt = today; } 
			
		while ( startDt.compareTo(endDt) <=0  ) {
			Report response = new Report();
			response.setResultDt(startDt);
			response.setTotalCount(0);
			
			calList.add(response);
			startDt = DateUtils.addDays(startDt, 1 );
		}
		
		return calList;
		
	}
	/**
	 * 월별달력
	 * @return
	 */
	public static List<Report> makeMonthCalendar(){
		List<Report> calList = new ArrayList<Report>();
		Date startDt = DateUtils.addMonths(new Date(),-1*MagicNumUtils.MONTH_3);
		Date endDt = new Date();
		
		while ( startDt.compareTo(endDt) <=0  ) {
			Report response = new Report();
			response.setResultDt(startDt);
			response.setTotalCount(0);
			
			calList.add(response);
			startDt = DateUtils.addMonths(startDt, 1 );
		}
		
		return calList;
		
	}
}

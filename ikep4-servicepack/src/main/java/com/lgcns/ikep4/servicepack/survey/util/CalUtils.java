package com.lgcns.ikep4.servicepack.survey.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.servicepack.survey.model.Response;
/**
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

	private CalUtils() {
	}
	
	/**
	 * 주를 만든다
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek( Date date ){
		Calendar curr = Calendar.getInstance();
		curr.setTime( date );
		int weekday = curr.get(Calendar.DAY_OF_WEEK);	
		
		return weekday;
	}
	
	/**
	 * 시작일자와 종료일자사이의 달력을 만든다
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Response> makeCalendar(Date start,Date end){
		List<Response> calList = new ArrayList<Response>();
		Date startDt = start;
		Date endDt = end;
		Date today = new Date();
		
		if( today.before(endDt) ){ endDt = today;}  
			
		while ( startDt.compareTo(endDt) <=0  ) {
			Response response = new Response();
			response.setResponseDate(startDt);
			response.setDay( getDayOfWeek(startDt) );
			
			calList.add(response);
			startDt = DateUtils.addDays(startDt, 1 );
		}
		
		return calList;
		
	}
}

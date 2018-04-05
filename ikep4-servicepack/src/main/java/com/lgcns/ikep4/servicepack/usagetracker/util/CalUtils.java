package com.lgcns.ikep4.servicepack.usagetracker.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
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
	 * 월별달력
	 * @return
	 */
	public static List<UtStatistics> makeMonthCalendar(){
		List<UtStatistics> calList = new ArrayList<UtStatistics>();
		DecimalFormat df = new DecimalFormat("00");
		
		for (int i = 1; i <= MagicNumUtils.MONTH_12 ; i++) {
			UtStatistics info = new UtStatistics();
			info.setModuleId( df.format(i) );
			info.setUsageCount(0);
			calList.add(info);
		}
		
		return calList;
	}
	
	/**
	 * 일별달력
	 * @return
	 */
	public static List<UtStatistics> makeDayCalendar(){
		List<UtStatistics> calList = new ArrayList<UtStatistics>();
		DecimalFormat df = new DecimalFormat("00");
		
		for (int i = 1; i <= MagicNumUtils.DAY_MAX_SIZE ; i++) {
			UtStatistics info = new UtStatistics();
			info.setModuleId( df.format(i) );
			info.setUsageCount(0);
			calList.add(info);
		}
		
		return calList;
	}
	
	
	/**
	 * 주만들기
	 * @return
	 */
	public static List<UtStatistics> makeWeekCalendar(){
		List<UtStatistics> calList = new ArrayList<UtStatistics>();
		for (int i = 1; i <= MagicNumUtils.TOTAL_WEEK_SIZE ; i++) {
			UtStatistics info = new UtStatistics();
			info.setModuleId( String.valueOf(i) );
			info.setUsageCount(0);
			calList.add(info);
		}
		
		return calList;
		
	}
	
	
	/**
	 * 시간별달력
	 * @return
	 */
	public static List<UtStatistics> makeHourCalendar(){
		List<UtStatistics> calList = new ArrayList<UtStatistics>();
		DecimalFormat df = new DecimalFormat("00");
		
		for (int i = 0; i <= MagicNumUtils.DAY_HOUR_SIZE ; i++) {
			UtStatistics info = new UtStatistics();
			info.setModuleId( df.format(i) );
			info.setUsageCount(0);
			calList.add(info);
		}
		
		return calList;
	}
}

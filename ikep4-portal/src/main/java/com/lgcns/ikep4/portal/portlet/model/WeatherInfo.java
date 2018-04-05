package com.lgcns.ikep4.portal.portlet.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 날씨 포틀릿 도시 정보 model
 *
 * @author 임종상
 * @version $Id: WeatherInfo.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class WeatherInfo extends BaseObject {

	private static final long serialVersionUID = -5659429295224634714L;

	/**
	 * 도시_ID
	 */
	private String cityId;
	
	/**
	 * 로케일 코드 (ko, en, jp 등)  코드값을 사용함
	 */
	private String localeCode;
	
	/**
	 * 도시이름
	 */
	private String cityName;
	
	/**
	 * 도시 영문 이름
	 */
	private String cityEnglishName;
	
	/**
	 * 현재 날씨상태 아이콘 파일이름(경로를 제외한 파일명만 표시)
	 */
	private String currentConditionIcon;
	
	/**
	 * 현재 썹시 온도
	 */
	private Double currentCentTemp;
	
	/**
	 * 현재 화씨 온도
	 */
	private Double currentFahrTemp;
	
	/**
	 * 현재 날씨 상태
	 */
	private String currentCondition;
	
	/**
	 * 날씨를 표시할 첫번째 일(월,화,수)
	 */
	private String dayOfWeekdate1;
	
	/**
	 * 첫번째 일의 최저 온도
	 */
	private Double lowTemp1;
	
	/**
	 * 첫번째 일의 최고 온도
	 */
	private Double highTemp1;
	
	/**
	 * 첫번째 일의 날씨상태
	 */
	private String condition1;
	
	/**
	 * 첫번째 일의 날씨상태 아이콘 파일이름
	 */
	private String conditionIcon1;
	
	/**
	 * 날씨를 표시할 두번째 일(월,화,수)
	 */
	private String dayOfWeekdate2;
	
	/**
	 * 두번째 일의 최저 온도
	 */
	private Double lowTemp2;
	
	/**
	 * 두번째 일의 최고 온도
	 */
	private Double highTemp2;
	
	/**
	 * 두번째 일의 날씨상태
	 */
	private String condition2;
	
	/**
	 * 두번째 일의 날씨상태 아이콘 파일이름
	 */
	private String conditionIcon2;
	
	/**
	 * 날씨를 표시할 세번째 일(월,화,수)
	 */
	private String dayOfWeekdate3;
	
	/**
	 * 세번째 일의 최저 온도
	 */
	private Double lowTemp3;
	
	/**
	 * 세번째 일의 최고 온도
	 */
	private Double highTemp3;
	
	/**
	 * 세번째 일의 날씨상태
	 */
	private String condition3;
	
	/**
	 * 세번째 일의 날씨상태 아이콘 파일이름
	 */
	private String conditionIcon3;
	
	/**
	 * 날씨를 표시할 네번째 일(월,화,수)
	 */
	private String dayOfWeekdate4;
	
	/**
	 * 네번째 일의 최저 온도
	 */
	private Double lowTemp4;
	
	/**
	 * 네번째 일의 최고 온도
	 */
	private Double highTemp4;
	
	/**
	 * 네번째 일의 날씨상태
	 */
	private String condition4;
	
	/**
	 * 네번째 일의 날씨상태 아이콘 파일이름
	 */
	private String conditionIcon4;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * 수정일 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private Date updateDate;
	
	/**
	 * 현재 바람 상태
	 */
	private String currentWindCondition;
	
	/**
	 * 현재 습도 상태
	 */
	private String currentHumidityCondition;
	
	
	public String getCurrentWindCondition() {
		return currentWindCondition;
	}

	public void setCurrentWindCondition(String currentWindCondition) {
		this.currentWindCondition = currentWindCondition;
	}

	public String getCurrentHumidityCondition() {
		return currentHumidityCondition;
	}

	public void setCurrentHumidityCondition(String currentHumidityCondition) {
		this.currentHumidityCondition = currentHumidityCondition;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityEnglishName() {
		return cityEnglishName;
	}

	public void setCityEnglishName(String cityEnglishName) {
		this.cityEnglishName = cityEnglishName;
	}

	public String getCurrentConditionIcon() {
		return currentConditionIcon;
	}

	public void setCurrentConditionIcon(String currentConditionIcon) {
		this.currentConditionIcon = currentConditionIcon;
	}

	public Double getCurrentCentTemp() {
		return currentCentTemp;
	}

	public void setCurrentCentTemp(Double currentCentTemp) {
		this.currentCentTemp = currentCentTemp;
	}

	public Double getCurrentFahrTemp() {
		return currentFahrTemp;
	}

	public void setCurrentFahrTemp(Double currentFahrTemp) {
		this.currentFahrTemp = currentFahrTemp;
	}

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public String getDayOfWeekdate1() {
		return dayOfWeekdate1;
	}

	public void setDayOfWeekdate1(String dayOfWeekdate1) {
		this.dayOfWeekdate1 = dayOfWeekdate1;
	}

	public Double getLowTemp1() {
		return lowTemp1;
	}

	public void setLowTemp1(Double lowTemp1) {
		this.lowTemp1 = lowTemp1;
	}

	public Double getHighTemp1() {
		return highTemp1;
	}

	public void setHighTemp1(Double highTemp1) {
		this.highTemp1 = highTemp1;
	}

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public String getConditionIcon1() {
		return conditionIcon1;
	}

	public void setConditionIcon1(String conditionIcon1) {
		this.conditionIcon1 = conditionIcon1;
	}

	public String getDayOfWeekdate2() {
		return dayOfWeekdate2;
	}

	public void setDayOfWeekdate2(String dayOfWeekdate2) {
		this.dayOfWeekdate2 = dayOfWeekdate2;
	}

	public Double getLowTemp2() {
		return lowTemp2;
	}

	public void setLowTemp2(Double lowTemp2) {
		this.lowTemp2 = lowTemp2;
	}

	public Double getHighTemp2() {
		return highTemp2;
	}

	public void setHighTemp2(Double highTemp2) {
		this.highTemp2 = highTemp2;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	public String getConditionIcon2() {
		return conditionIcon2;
	}

	public void setConditionIcon2(String conditionIcon2) {
		this.conditionIcon2 = conditionIcon2;
	}

	public String getDayOfWeekdate3() {
		return dayOfWeekdate3;
	}

	public void setDayOfWeekdate3(String dayOfWeekdate3) {
		this.dayOfWeekdate3 = dayOfWeekdate3;
	}

	public Double getLowTemp3() {
		return lowTemp3;
	}

	public void setLowTemp3(Double lowTemp3) {
		this.lowTemp3 = lowTemp3;
	}

	public Double getHighTemp3() {
		return highTemp3;
	}

	public void setHighTemp3(Double highTemp3) {
		this.highTemp3 = highTemp3;
	}

	public String getCondition3() {
		return condition3;
	}

	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}

	public String getConditionIcon3() {
		return conditionIcon3;
	}

	public void setConditionIcon3(String conditionIcon3) {
		this.conditionIcon3 = conditionIcon3;
	}

	public String getDayOfWeekdate4() {
		return dayOfWeekdate4;
	}

	public void setDayOfWeekdate4(String dayOfWeekdate4) {
		this.dayOfWeekdate4 = dayOfWeekdate4;
	}

	public Double getLowTemp4() {
		return lowTemp4;
	}

	public void setLowTemp4(Double lowTemp4) {
		this.lowTemp4 = lowTemp4;
	}

	public Double getHighTemp4() {
		return highTemp4;
	}

	public void setHighTemp4(Double highTemp4) {
		this.highTemp4 = highTemp4;
	}

	public String getCondition4() {
		return condition4;
	}

	public void setCondition4(String condition4) {
		this.condition4 = condition4;
	}

	public String getConditionIcon4() {
		return conditionIcon4;
	}

	public void setConditionIcon4(String conditionIcon4) {
		this.conditionIcon4 = conditionIcon4;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}

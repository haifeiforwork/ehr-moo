package com.lgcns.ikep4.servicepack.survey.util;

/**
 * 
 * 서베이 컨트롤러 타입
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SurveyConstance.java 16244 2011-08-18 04:11:42Z giljae $
 */
public final class SurveyConstance {
	
	private SurveyConstance() {
	}
	
	public static final int SURVEY_DEFAULT_CONTENT_TYPE=0; //일반텍스트
	public static final  int SURVEY_DEFAULT_TARGET_TYPE=0;//기본디폴트타입
	public static final  int SURVEY_DEFAULT_TARGET_USER=0;//사용자일경우
	public static final  int SURVEY_DEFAULT_TARGET_GROUP=1;//그룹일경우
	public static final String ITEM_TYPE_CODE="Survey";
	public static final String MESSAGE_PREFIX = "message.servicepack";
}
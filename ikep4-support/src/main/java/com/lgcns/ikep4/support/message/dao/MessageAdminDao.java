/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.message.model.MessageAdmin;
import com.lgcns.ikep4.support.message.model.MessageChart;
import com.lgcns.ikep4.support.message.model.MessageMonitor;
import com.lgcns.ikep4.support.message.search.MessageMonitorSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageAdminDao.java 16273 2011-08-18 07:07:47Z giljae $
 */
public interface MessageAdminDao extends GenericDao<MessageAdmin, String> {
	
	/**TODO 사용자 설정 정보 가져오는 함수
	 * @param userId
	 * @return MessageAdmin
	 */
	public MessageAdmin getUserAdmin(String userId);
	
	/**TODO 사용자별 발송용량 정보 모니터링
	 * @param messageMonitorSearchCondition
	 * @return List<MessageMonitor>
	 */
	public List<MessageMonitor> getUserVolumnInfoList(MessageMonitorSearchCondition messageMonitorSearchCondition) ;
	
	/**TODO 사용자별 발송용량 정보 모니터링 LIST 갯수
	 * @param messageMonitorSearchCondition
	 * @return
	 */
	public Integer countUserVolumnInfoList(MessageMonitorSearchCondition messageMonitorSearchCondition); 
	
	/**TODO 주별 차트 정보
	 * @return List<MessageChart>
	 */
	public List<MessageChart> messageWeekChartList();
	
	/**TODO 일별 차트 정보
	 * @return List<MessageChart>
	 */
	public List<MessageChart> messageDayChartList();
	
	/**TODO 삭제 Batch 
	 * 
	 */
	public void removeBatch();
}

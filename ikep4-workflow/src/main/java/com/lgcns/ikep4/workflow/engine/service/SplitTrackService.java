/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service;

import java.util.Date;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;
import com.lgcns.ikep4.workflow.engine.model.NotifyBean;
import com.lgcns.ikep4.workflow.engine.model.SplitTrackBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;

/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: SplitTrackService.java 16245 2011-08-18 04:28:59Z giljae $ InstanceService.java 오후 7:03:12
 */
@Transactional
public interface SplitTrackService extends GenericService<SplitTrackBean, String> {
	
	public void insertSplitTrack(SplitTrackBean splitTrackBean);
	
}

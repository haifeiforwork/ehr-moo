/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopular;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopularPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Expert Network ExpertPopularService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPopularService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkPopularService extends GenericService<ExpertNetworkPopular, ExpertNetworkPopularPK> {

	/**
	 * 인기 전문가 검색 (포털별)
	 * @param portalId
	 * @return List<ExpertPopular>
	 */
	List<ExpertNetworkPopular> listByPortalId(String portalId);

	/**
	 * 인기전문가 등록 배치
	 * @param gatherCount - 포털별 수집할 건수
	 */
	void batchGatherPopular(int gatherCount);
}

/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfigKey;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;

/**
 * 
 * 사용량 통계 configure dao
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfigDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface UtConfigDao extends GenericDao<UtConfig,UtConfigKey> {
  public List<UtConfig> selectUtConfigList(SearchUtil searchUtil);
}
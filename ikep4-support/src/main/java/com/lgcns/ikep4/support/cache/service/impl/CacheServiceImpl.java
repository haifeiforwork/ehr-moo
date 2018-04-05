package com.lgcns.ikep4.support.cache.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.cache.dao.CacheDao;
import com.lgcns.ikep4.support.cache.model.CacheData;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 캐시 Service 구현 객체
 * @author 임종상
 *
 */
@Service("cacheService")
public class CacheServiceImpl extends GenericServiceImpl<CacheData, String> implements CacheService {
	
	private final String SEPARATOR_STR = "^";
	
	@Autowired
	private CacheDao cacheDao;

	
	/**
	 * 캐시에 데이터가 있는지 체크
	 * @param cacheFlag
	 * @return
	 */
	public Object cacheCheck(String cacheFlag) {
		
		Object obj = null;
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		// 포탈 조회
		CacheData cacheDataPortal = cacheDao.getPortal(portalId);
		
		if(cacheDataPortal != null) {
			Map<String, String> cacheConfigMap = getCacheConfig(cacheFlag);
			
			String cacheMode = cacheConfigMap.get("cacheMode");
			String cacheName = cacheConfigMap.get("cacheName");
			String elementKey = cacheConfigMap.get("elementKey");
			
			//포탈 캐시 사용여부 체크
			if("Y".equals(cacheDataPortal.getPortalCacheYn()) && !StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
				//캐시 모드 설정 체크
				if ("1".equals(cacheMode) || "2".equals(cacheMode)) {
					CacheManager cacheManager = CacheManager.create();
					Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
					
					if(cache == null) {
						// 캐시가 없으면 생성
						try { 
							cacheManager.addCache(cacheName + SEPARATOR_STR + portalId);
						} catch (ObjectExistsException e) {
							// ObjectExistsException 패스
						}
					} else {
						// 캐시가 있으면 element 조회
						Element element = null;
						
						if("1".equals(cacheMode)) {
							User user = (User) getRequestAttribute("ikep.user");
							
							// 사용자별
							element = cache.get(elementKey + SEPARATOR_STR + user.getUserId());
						} else if("2".equals(cacheMode)) {
							// 단일
							element = cache.get(elementKey);
						}
						
						if(element != null) {
							obj = element.getObjectValue();
						}
					}
				}
			}
		}

		return obj;
	}

	/**
	 * 캐시 Element 저장
	 * @param cacheFlag
	 * @param objValue
	 */
	public void addCacheElement(String cacheFlag, Object objValue) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		// 포탈 조회
		CacheData cacheDataPortal = cacheDao.getPortal(portalId);
		
		if(cacheDataPortal != null) {
			Map<String, String> cacheConfigMap = getCacheConfig(cacheFlag);
			
			String cacheMode = cacheConfigMap.get("cacheMode");
			String cacheName = cacheConfigMap.get("cacheName");
			String elementKey = cacheConfigMap.get("elementKey");
			
			//포탈 캐시 사용여부 체크
			if("Y".equals(cacheDataPortal.getPortalCacheYn()) && !StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
				//캐시 모드 설정 체크
				if ("1".equals(cacheMode) || "2".equals(cacheMode)) {
					CacheManager cacheManager = CacheManager.create();	
					Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
					
					if(cache != null) {
						if("1".equals(cacheMode)) {
							User user = (User) getRequestAttribute("ikep.user");
							
							// 사용자별
							cache.put(new Element(elementKey + SEPARATOR_STR + user.getUserId(), objValue));
						} else if("2".equals(cacheMode)) {
							// 단일
							cache.put(new Element(elementKey, objValue));
						}
					}
				}
			}
		}
	}

	/**
	 * 캐시에 데이터가 있는지 체크(포틀릿 contents) 
	 * @param portletId
	 * @param portletConfigId
	 * @return
	 */
	public Object cacheCheckPortletContent(String portletId, String portletConfigId) {
		
		Object obj = null;
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		// 포탈 조회
		CacheData cacheDataPortal = cacheDao.getPortal(portalId);
		
		// 포틀릿 조회
		CacheData cacheDataPortlet = cacheDao.getPortlet(portletId);
		
		if(cacheDataPortal != null && cacheDataPortlet != null) {
			
			Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
			
			String cacheMode = cacheprop.getProperty(StringUtil.nvl(cacheDataPortlet.getCacheModeStr(), ""));
			String cacheName = cacheprop.getProperty(StringUtil.nvl(cacheDataPortlet.getCacheNameStr(), ""));
			String elementKey = cacheprop.getProperty(StringUtil.nvl(cacheDataPortlet.getElementKeyStr(), ""));
			
			//포탈 캐시 사용여부 체크
			if("Y".equals(cacheDataPortal.getPortalCacheYn()) && !StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
				
				//캐시 모드 설정 체크
				if ("1".equals(cacheMode) || "2".equals(cacheMode)) {
					
					//포틀릿 contents 캐시사용여부 체크
					if("Y".equals(cacheDataPortlet.getPortletCacheYn())) {
						CacheManager cacheManager = CacheManager.create();
						Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
						
						if(cache == null) {
							// 캐시가 없으면 생성
							try {
								String cacheMaxCount = cacheDataPortlet.getCacheMaxCount();
								String cacheLiveSecond = cacheDataPortlet.getCacheLiveSecond();
								
								if(!StringUtil.isEmpty(cacheMaxCount) && !StringUtil.isEmpty(cacheLiveSecond)) {
									// 포틀릿에 설정이 있으면
									cache = new Cache(cacheName + SEPARATOR_STR + portalId, Integer.parseInt(cacheMaxCount), false, false, Integer.parseInt(cacheLiveSecond), 0);
									cacheManager.addCache(cache);
								} else {
									cacheManager.addCache(cacheName + SEPARATOR_STR + portalId);
								}
							} catch (ObjectExistsException e) {
								// ObjectExistsException 패스
							}
						} else {
							// 캐시가 있으면 element 조회
							Element element = null;
							
							if("1".equals(cacheMode)) {
								User user = (User) getRequestAttribute("ikep.user");
								
								// 사용자별
								element = cache.get(elementKey + SEPARATOR_STR + portletConfigId + SEPARATOR_STR + user.getUserId());
							} else if("2".equals(cacheMode)) {
								// 단일
								element = cache.get(elementKey);
							}
							
							if(element != null) {
								obj = element.getObjectValue();
							}
						}
					}
				}
			}
		}
		
		return obj;
	}

	
	/**
	 * 캐시 Element 저장(포틀릿 contents)
	 * @param portletId
	 * @param portletConfigId
	 * @param objValue
	 */
	public void addCacheElementPortletContent(String portletId, String portletConfigId, Object objValue) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		// 포탈 조회
		CacheData cacheDataPortal = cacheDao.getPortal(portalId);
		
		// 포틀릿 조회
		CacheData cacheDataPortlet = cacheDao.getPortlet(portletId);
		
		if(cacheDataPortal != null && cacheDataPortlet != null) {
			Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
			
			String cacheMode = cacheprop.getProperty(StringUtil.nvl(cacheDataPortlet.getCacheModeStr(), ""));
			String cacheName = cacheprop.getProperty(StringUtil.nvl(cacheDataPortlet.getCacheNameStr(), ""));
			String elementKey = cacheprop.getProperty(StringUtil.nvl(cacheDataPortlet.getElementKeyStr(), ""));
			
			//포탈 캐시 사용여부 체크
			if("Y".equals(cacheDataPortal.getPortalCacheYn()) && !StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
				//캐시 모드 설정 체크
				if ("1".equals(cacheMode) || "2".equals(cacheMode)) {
					//포틀릿 contents 캐시사용여부 체크
					if("Y".equals(cacheDataPortlet.getPortletCacheYn())) {
						CacheManager cacheManager = CacheManager.create();	
						Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
						
						if(cache != null) {
							if("1".equals(cacheMode)) {
								User user = (User) getRequestAttribute("ikep.user");
								
								// 사용자별
								cache.put(new Element(elementKey + SEPARATOR_STR + portletConfigId + SEPARATOR_STR + user.getUserId(), objValue));
							} else if("2".equals(cacheMode)) {
								// 단일
								cache.put(new Element(elementKey, objValue));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 캐시 삭제 (포틀릿 contents)
	 * @param portletId
	 */
	public void removeCachePortletContent(String portletId) {
		
		// 포틀릿 조회
		CacheData cacheData = cacheDao.getPortlet(portletId);
		
		if(cacheData != null) {
			Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
			String cacheName = cacheprop.getProperty(StringUtil.nvl(cacheData.getCacheNameStr(), ""));
			
			if(!StringUtil.isEmpty(cacheName)) {
				String portalId = (String) getRequestAttribute("ikep.portalId");
				
				CacheManager cacheManager = CacheManager.create();	
				Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
				
				if(cache != null) {
					//캐시 삭제
					cacheManager.removeCache(cacheName + SEPARATOR_STR + portalId);
				}
			}
		}
	}

	/**
	 * 캐시 Element 전체 삭제  
	 * @param cacheFlag
	 */
	public void removeCacheElementAll(String cacheFlag) {
		
		Map<String, String> cacheConfigMap = getCacheConfig(cacheFlag);
		
		String cacheName = cacheConfigMap.get("cacheName");
		
		if(!StringUtil.isEmpty(cacheName)) {
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			CacheManager cacheManager = CacheManager.create();	
			Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
			
			if(cache != null) {
				//캐시 Element 전체 삭제
				cache.removeAll();
			}
		}
	}
	
	/**
	 * 캐시 Element 전체 삭제  (포틀릿 contents)
	 * @param cacheName
	 */
	public void removeCacheElementPortletContentAll(String cacheNameStr) {
		
		Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
		
		String cacheName = cacheprop.getProperty(cacheNameStr);
		
		if(!StringUtil.isEmpty(cacheName)) {
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			CacheManager cacheManager = CacheManager.create();	
			Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
			
			if(cache != null) {
				//캐시 Element 전체 삭제
				cache.removeAll();
			}
		}
	}

	/**
	 * 캐시 Element 삭제 
	 * @param cacheFlag
	 */
	public void removeCacheElement(String cacheFlag) {
		
		Map<String, String> cacheConfigMap = getCacheConfig(cacheFlag);
		
		String cacheName = cacheConfigMap.get("cacheName");
		String elementKey = cacheConfigMap.get("elementKey");
		
		//포탈 캐시 사용여부 체크
		if(!StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			CacheManager cacheManager = CacheManager.create();	
			Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
			
			if(cache != null) {
				String cacheMode = cacheConfigMap.get("cacheMode");
				
				if("1".equals(cacheMode)) {
					User user = (User) getRequestAttribute("ikep.user");
					
					// 사용자별
					cache.remove(elementKey + SEPARATOR_STR + user.getUserId());
				}
			}
		}
	}
	
	/**
	 * 캐시 Element 삭제 (포틀릿 contents)
	 * @param portletId
	 * @param portletConfigId
	 */
	public void removeCacheElementPortletContent(String portletId, String portletConfigId) {
		
		Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
		
		// 포틀릿 조회
		CacheData cacheData = cacheDao.getPortlet(portletId);
		
		if(cacheData != null) {
			String cacheName = cacheprop.getProperty(StringUtil.nvl(cacheData.getCacheNameStr(), ""));
			String elementKey = cacheprop.getProperty(StringUtil.nvl(cacheData.getElementKeyStr(), ""));
			
			if(!StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
				String portalId = (String) getRequestAttribute("ikep.portalId");
				
				CacheManager cacheManager = CacheManager.create();	
				Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
				
				if(cache != null) {
					String cacheMode = cacheprop.getProperty(StringUtil.nvl(cacheData.getCacheModeStr(), ""));  
					
					if("1".equals(cacheMode)) {
						User user = (User) getRequestAttribute("ikep.user");
						
						// 사용자별
						cache.remove(elementKey + SEPARATOR_STR + portletConfigId + SEPARATOR_STR + user.getUserId());
					}
				}
			}
		}
	}
	
	/**
	 * 캐시 Element 삭제 (포틀릿 contents)
	 * @param cacheName
	 * @param cacheMode
	 * @param elementKey
	 * @param userId
	 */
	public void removeCacheElementPortletContent(String cacheNameStr, String cacheModeStr, String elementKeyStr, String userId) {
		
		Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
		
		String cacheName = cacheprop.getProperty(cacheNameStr);
		String elementKey = cacheprop.getProperty(elementKeyStr);
		
		if(!StringUtil.isEmpty(cacheName) && !StringUtil.isEmpty(elementKey)) {
			String portalId = (String) getRequestAttribute("ikep.portalId");
			
			CacheManager cacheManager = CacheManager.create();	
			Cache cache = cacheManager.getCache(cacheName + SEPARATOR_STR + portalId);
			
			if(cache != null) {
				String cacheMode = cacheprop.getProperty(cacheModeStr); 
				
				if("1".equals(cacheMode)) {
					 
					List<CacheData> portletConfigIdList = cacheDao.listPortletConfigId(portalId, userId, cacheNameStr);
					
					if(portletConfigIdList != null) {
						String portletConfigId = null;
						
						for(int i = 0; i < portletConfigIdList.size(); i++) {
							portletConfigId = portletConfigIdList.get(i).getPortletConfigId();
							
							if(!StringUtil.isEmpty(portletConfigId)) {
								// 사용자별
								cache.remove(elementKey + SEPARATOR_STR + portletConfigId + SEPARATOR_STR + userId);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 *	포탈별 캐시 전체 삭제 
	 */
	public void removeCacheAllPortal() {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		CacheManager cacheManager = CacheManager.create();	
		String[] cacheNames = cacheManager.getCacheNames();
		String[] cacheNameCut = null;
		
		if(cacheNames != null) {
			for(int i = 0; i < cacheNames.length; i++) {
				cacheNameCut = cacheNames[i].split("\\^");
				
				if(cacheNameCut[cacheNameCut.length-1].equals(portalId)) {
					cacheManager.removeCache(cacheNames[i]);
				}
			}
		}
	}
	
	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * 캐시 설정 조회
	 * @param cacheFlag
	 * @return
	 */
	private Map<String, String> getCacheConfig(String cacheFlag) {
		
		Map<String, String> cacheConfigMap = new HashMap<String, String>();
		
		Properties cacheprop = PropertyLoader.loadProperties("/configuration/cache.properties");
		
		if("userPortlet".equals(cacheFlag)) {
			// 사용자 포틀릿 목록
			cacheConfigMap.put("cacheName", cacheprop.getProperty("Cachename-user-portlet"));
			cacheConfigMap.put("cacheMode", cacheprop.getProperty("Cachemode-user-portlet"));
			cacheConfigMap.put("elementKey", cacheprop.getProperty("Elementkey-user-portlet"));
		} else if("commonPortlet".equals(cacheFlag)) {
			//공용 포틀릿 목록
			cacheConfigMap.put("cacheName", cacheprop.getProperty("Cachename-common-portlet"));
			cacheConfigMap.put("cacheMode", cacheprop.getProperty("Cachemode-common-portlet"));
			cacheConfigMap.put("elementKey", cacheprop.getProperty("Elementkey-common-portlet"));
		} else if("portlet".equals(cacheFlag)) {
			//포틀릿 목록
			cacheConfigMap.put("cacheName", cacheprop.getProperty("Cachename-portlet"));
			cacheConfigMap.put("cacheMode", cacheprop.getProperty("Cachemode-portlet"));
			cacheConfigMap.put("elementKey", cacheprop.getProperty("Elementkey-portlet"));
		} else if("menu".equals(cacheFlag)) {
			//메뉴 목록
			cacheConfigMap.put("cacheName", cacheprop.getProperty("Cachename-menu"));
			cacheConfigMap.put("cacheMode", cacheprop.getProperty("Cachemode-menu"));
			cacheConfigMap.put("elementKey", cacheprop.getProperty("Elementkey-menu"));
		}
		
		return cacheConfigMap;
	}
}
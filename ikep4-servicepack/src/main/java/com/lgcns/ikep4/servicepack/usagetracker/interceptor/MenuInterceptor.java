package com.lgcns.ikep4.servicepack.usagetracker.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenuLog;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeLog;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtConfigService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeUrlService;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 
 * 메뉴 로그 인터셉터 menuconfig의 LOG_TARGET_MENU 1이면서 useage 0
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: MenuInterceptor.java 19668 2012-07-05 08:08:31Z malboru80 $
 */
public class MenuInterceptor extends HandlerInterceptorAdapter  {

	@Autowired
	private UtMenuService utMenuSerive;
	
	@Autowired
	private UtMenuLogService utMenuLogService;
	
	@Autowired
	private UtConfigService utConfigService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private UtResTimeUrlService utResTimeUrlService;
	
	@Autowired
	private UtResTimeLogService utResTimeLogService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Long usagetrackerStartTime = System.currentTimeMillis();
		request.setAttribute("usagetrackerStartTime", usagetrackerStartTime);
		
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		String portalId = (String) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portalId", RequestAttributes.SCOPE_SESSION);
		
		try
		{
			
			boolean menuConfigUseYn = false; 
				
			if( user != null )
			{	
				/*
				SearchUtil searchUtil = new SearchUtil();
				searchUtil.setPortalId(user.getPortalId());
	
				List<UtConfig> utConfigList = utConfigService.selectUtConfigList(searchUtil);

				for (UtConfig utConfig : utConfigList) {
					if( utConfig.getLogTarget().equals(String.valueOf(UsageTrackerConstance.LOG_TARGET_MENU)) && utConfig.getUsage().intValue() == UsageTrackerConstance.UT_CONFIG_MENU_USAGE_USE ){
						menuConfigUseYn =true;
						break;
					}
				}
				*/
				
				CacheManager cacheManager = CacheManager.getInstance();		
				Cache cache = cacheManager.getCache("logConfigCache"+portalId);
				
				if(cache == null) {
					cache = new Cache("logConfigCache"+portalId, 100, false, false, 0, 0);
					cacheManager.addCache(cache);
					
					SearchUtil searchUtil = new SearchUtil();
					searchUtil.setPortalId(portalId);
		
					List<UtConfig> utConfigList = utConfigService.selectUtConfigList(searchUtil);
					
					for(int i=0 ; i < utConfigList.size() ; i++){
						//LOG_TARGET[로그  적용 대상 ( 0 : 사용자로그인, 1 : 메뉴, 2 : 포틀릿, 3 : URL 응답시간)]
						Element newElement = new Element("logConfigCache"+utConfigList.get(i).getLogTarget(), utConfigList.get(i).getUsage());
						cache.put(newElement);
					}
				}
				
				Element element = cache.get("logConfigCache1");
				int usage = (Integer) element.getValue();
				
				if(usage == 0){
					menuConfigUseYn = true;
				}
				
				String path =  request.getServletPath();
				
				String queryString = request.getQueryString();
				
				if( menuConfigUseYn )
				{
					UtMenu utMenu = new UtMenu();
					utMenu.setMenuUrl(path);
					utMenu.setPortalId(portalId);
					
					List <UtMenu> dbInfoList = utMenuSerive.getMenuList(utMenu); 
					
					String logMenuId = getLogMenuId(dbInfoList,queryString);
					
					if( !StringUtil.isEmpty(logMenuId) )
					{
						
						UtMenuLog menuLog = new UtMenuLog();
						menuLog.setMenuAccessId( idgenService.getNextId() );
						menuLog.setMenuId( logMenuId );
						menuLog.setPortalId(portalId);
						menuLog.setUserId( user.getUserId() );
						menuLog.setAccessDate( new Date() );
						
						utMenuLogService.create(menuLog);
					}
				}
				
				//System.out.println(path+"--------------------------------------");
			}
		}
		catch(Exception e){
		}
		
		return super.preHandle(request, response, handler);
	}
	
	private String getLogMenuId(List <UtMenu> dbInfoList,String queryString ){
		String logMenuId = "";
		
		queryString = StringUtil.nvl(queryString,"");

		//조회결과가 없으면 공백리턴.
		if(dbInfoList==null || dbInfoList.size() == 0){
			return logMenuId;
		}
		
		/*
		 * 사용자 파라미터 존재여부와 상관없이 
		 * DB에 조회된 결과 값이 한건이고
		 * DB에 등록된 파라미터정보가 없으면 메뉴아이디 리턴.
		 */
		if(dbInfoList != null && dbInfoList.size() == 1){
			UtMenu menu = dbInfoList.get(0);
			if(StringUtil.isEmpty(menu.getRequestParameter())){
				return dbInfoList.get(0).getMenuId();
			}
		}
		
		/*
		 * 사용자 파라미터가 있는경우만 체크 
		 * DB에 조회된 결과 값이 한건이고
		 * DB에 등록된 파라미터정보가 없으면 메뉴아이디 리턴.
		 */ 

		int prePatternCnt = 0;
			for(int i=0;i < dbInfoList.size();i++){
				UtMenu menu = dbInfoList.get(i);
				int patternCnt = 0;
				String param = menu.getRequestParameter();
				if(!StringUtil.isEmpty(param)){
					String[] menuParam = param.trim().split("&");
					//DB에 등록된 파라미터를 배열로 분리.
					for(int j=0;j < menuParam.length;j++){
						// QueryString으로 들어온  문자에 해당 요소가 있는지 확인 후 Cnt++
						if(queryString.indexOf(menuParam[j]) > -1){
							patternCnt++;
						}
					}
					//DB에 등록된 파라미터가 QueryString의 일부이면 메뉴ID리턴
					//List중 매치 패턴갯수가 많은 menuId로 리턴.
					// board.do , board.do?boardType=G,board.do?boardType=G&boardId=1 의 경우 고려.
					if(patternCnt==menuParam.length){
						if(patternCnt >= prePatternCnt){
							logMenuId=menu.getMenuId();
							prePatternCnt = patternCnt;
						}
					}
				}
			}
		return logMenuId;
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		String portalId = (String) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portalId", RequestAttributes.SCOPE_SESSION);
		
		try
		{
			if( user != null )
			{
				CacheManager cacheManager = CacheManager.getInstance();		
				Cache cache = cacheManager.getCache("logConfigCache"+portalId);
				
				if(cache == null) {
					cache = new Cache("logConfigCache"+portalId, 100, false, false, 0, 0);
					cacheManager.addCache(cache);
					
					SearchUtil searchUtil = new SearchUtil();
					searchUtil.setPortalId(portalId);
		
					List<UtConfig> utConfigList = utConfigService.selectUtConfigList(searchUtil);
					
					for(int i=0 ; i < utConfigList.size() ; i++){
						//LOG_TARGET[로그  적용 대상 ( 0 : 사용자로그인, 1 : 메뉴, 2 : 포틀릿, 3 : URL 응답시간)]
						Element newElement = new Element("logConfigCache"+utConfigList.get(i).getLogTarget(), utConfigList.get(i).getUsage());
						cache.put(newElement);
					}
				}
				
				Element element = cache.get("logConfigCache3");
				int usage = (Integer) element.getValue();
				
				if(usage == 0) {
					
					UtResTimeUrl utResTimeUrl = new UtResTimeUrl();
					utResTimeUrl.setResTimeUrl(request.getServletPath());
					utResTimeUrl.setPortalId(portalId);
					
					UtResTimeUrl dbInfo = utResTimeUrlService.getResTimeUrl(utResTimeUrl);
					
					// 등록된 URL, 사용여부 체크
					if( dbInfo != null )
					{
						// 로그 등록
						Long usagetrackerStartTime = (Long) request.getAttribute("usagetrackerStartTime");
						Long resTime = System.currentTimeMillis() - usagetrackerStartTime;
						
						UtResTimeLog utResTimeLog = new UtResTimeLog();
						utResTimeLog.setResTimeAccessId(idgenService.getNextId());
						utResTimeLog.setResTimeUrlId(dbInfo.getResTimeUrlId());
						utResTimeLog.setPortalId(portalId);
						utResTimeLog.setUserId(user.getUserId());
						utResTimeLog.setResTime(resTime); 
						
						utResTimeLogService.create(utResTimeLog);
					}
				}
			}
		} catch(Exception e){}	
	}
}

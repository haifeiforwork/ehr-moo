package com.lgcns.ikep4.lightpack.board.batch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * 공지게시판 독서자 메일발송 배치 작업
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: NotiSchedule.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class BoardItemNotiSchedule extends QuartzJobBean {
	protected final org.apache.commons.logging.Log log = LogFactory.getLog(getClass());

	private final static int DEFAULT_INTERVAL = 5;
	
	private final String SUFFIX = ".properties";

	/** 게시물 서비스 클래스. */
	private BoardItemService boardItemService;

	private int interval = DEFAULT_INTERVAL;

	private static String fileUrl;

	@SuppressWarnings("static-access")
	@Override
	protected void executeInternal(JobExecutionContext context) {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.boardItemService = (BoardItemService) appContext.getBean("boardItemServiceImpl");

			Date jobTime = new Date();
			
			if(this.fileUrl == null) {
				Properties prop = loadProperties("/configuration/common-conf.properties", Thread.currentThread().getContextClassLoader());
				Properties prop2 = loadProperties("/configuration/fileupload-conf.properties", Thread.currentThread().getContextClassLoader());
				this.fileUrl = prop.getProperty("ikep4.baseUrl") + prop2.getProperty("ikep4.support.fileupload.downloadurl");
			}

			this.boardItemService.doBoardItemNotiJobSchedule(jobTime, interval, fileUrl);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 클래스 패쓰 상에 있는 리소스를 로딩한다. 리소스는 프로퍼티 파일이어야 하며, "." 또는 "/"로 패키지 패쓰를 모두 담은 이름을
	 * 넘겨주어야 한다. 특히 .properties 확장자가 없어도 무방하다.
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Reousrce
	 * some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 * @param loader
	 * @return
	 */
	private Properties loadProperties(String nameParam, ClassLoader loaderParam) {

		String name = nameParam;
		ClassLoader loader = loaderParam;

		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (name.endsWith(SUFFIX)) {
			name = name.substring(0, name.length() - SUFFIX.length());
		}

		Properties result = null;

		InputStream in = null;

		try {
			if (loader == null) {
				loader = ClassLoader.getSystemClassLoader();
			}

			name = name.replace('.', '/');

			if (!name.endsWith(SUFFIX)) {
				name = name.concat(SUFFIX);
			}

			in = loader.getResourceAsStream(name);

			if (in != null) {
				result = new Properties();
				result.load(in);
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {
					// 예외 무시
					log.error("InputStream of Property close error", ignore);
				}
			}
		}

		if (result == null) {
			throw new IllegalArgumentException("Could not load [" + name + "] as a classloader resource");
		}

		return result;
	}
}

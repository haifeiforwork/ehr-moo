package com.lgcns.ikep4.approval.collaboration.common.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.lgcns.ikep4.support.fileupload.model.FileData;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;

@Component
public class CollaboUtils {
	
	private String baseUrl = "";
	private String serverMode = "";
	private static String KEYWORD_LIST ="";
	
	@PostConstruct
	public void init() {
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		baseUrl = commonprop.getProperty("ikep4.baseUrl");
		serverMode = commonprop.getProperty("ikep4.server.mode");
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		KEYWORD_LIST = prop.getProperty("ikep4.support.fileupload.image_file");
	}
	
	/**
	 * local, dev, real 구분
	 * @return
	 */
	public String getServerMode() {
		
		return serverMode;
	}
	
	/**
	 * server url return
	 * @param mainFrameUrl
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getServerURL( String mainFrameUrl){
		
		StringBuffer url = new StringBuffer();
		url.append( baseUrl);
		url.append( "/portal/main/portalMain.do?mainFrameUrl=");
		
		if( StringUtils.isNotEmpty( mainFrameUrl)) {
			url.append( URLEncoder.encode( mainFrameUrl));
		}
		
		return url.toString();
	}
	
	/**
	 * server url return
	 * @param mainFrameUrl
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getServerURL2( String mainFrameUrl){
		
		StringBuffer url = new StringBuffer();
		url.append( baseUrl);
//		url.append( "/portal/main/portalMain.do?mainFrameUrl=");
		
		if( StringUtils.isNotEmpty( mainFrameUrl)) {
			url.append(mainFrameUrl);
		}
		
		return url.toString();
	}
	
	/**
	 * Json String 으로 변경
	 * @param fileInfo
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String convertJsonString( List<FileData> list) throws JsonGenerationException, JsonMappingException, IOException{
		
		String fileDataListJson = null;
		ObjectMapper mapper = new ObjectMapper();
		
		if( list != null) {
			fileDataListJson = mapper.writeValueAsString( list);
		}
		
		return fileDataListJson;
	}
	
	/**
	 * jsonString 으로 반환
	 * @param strName
	 * @param value
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String convertJsonString( String strName, Object value) throws JsonGenerationException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put( strName, value);
		
		return mapper.writeValueAsString( paramMap);
	}
	
	public static String getFilePath(String path) {

		String date = DateUtil.getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	public static boolean checkImageFile(String fileName) {

		Pattern p = Pattern.compile( KEYWORD_LIST, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}
}

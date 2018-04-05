/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;


/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageUtil.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Component
public class MessageUtil extends JsonSerializer<Date> {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}
	
	public static String getText(String content, int lvl) {   
	    Pattern tScripts = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);   
	    Pattern tStyle = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);   
	    Pattern tTags = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");   
	    Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");   
	    Pattern entityRefs = Pattern.compile("&[^;]+;");   
	    //Pattern whiteSpace = Pattern.compile("\\s\\s+");   
	    String str = content;   
	    Matcher m;   
	    m = tScripts.matcher(str);   
	    str = m.replaceAll("");   
	    m = tStyle.matcher(str);   
	    str = m.replaceAll("");   
	    m = tTags.matcher(str);   
	    str = m.replaceAll("");   
	    m = nTAGS.matcher(str);   
	    str = m.replaceAll("");  
	    m = entityRefs.matcher(str);   
	    str = m.replaceAll("");   
	    //m = whiteSpace.matcher(str);   
	    //str = m.replaceAll(" ");           
	    if (lvl == 1) { str = replaceContentString(str); }
	    return str;
	}   
	    
	public static String replaceContentString(String content) {   
		String str = content;   
		str = str.replaceAll("'", "");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;  
	}
}
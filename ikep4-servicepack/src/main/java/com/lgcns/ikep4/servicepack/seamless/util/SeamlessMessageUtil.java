package com.lgcns.ikep4.servicepack.seamless.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class SeamlessMessageUtil extends JsonSerializer<Date> {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

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

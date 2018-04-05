/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.lang;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;


public class ExtractTextTagBody extends TagSupport {

	//private static Pattern pattern = Pattern.compile("(?:<!--.*?(?:--.*?--\\s*)*.*?-->)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)");
	private static Pattern SCRIPTS1 =Pattern.compile("<(no)?SCRIPT[^>]*>.*?</(no)?SCRIPT>", Pattern.DOTALL);
	private static Pattern SCRIPTS =Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
	private static Pattern STYLE1 =Pattern.compile("<STYLE[^>]*>.*?</STYLE>", Pattern.DOTALL);
	private static Pattern STYLE =Pattern.compile("<style[^>]*>.*?</style>", Pattern.DOTALL);
	private static Pattern HEAD1 =Pattern.compile("<HEAD[^>]*>.*?</HEAD>", Pattern.DOTALL);
	private static Pattern HEAD =Pattern.compile("<head[^>]*>.*?</head>", Pattern.DOTALL);
	private static Pattern META =Pattern.compile("<META[^>]*>", Pattern.DOTALL);
	private static Pattern htmlStart1 =Pattern.compile("<html>");
	private static Pattern htmlEnd1 =Pattern.compile("</html>");
	private static Pattern htmlStart =Pattern.compile("<HTML>");
	private static Pattern htmlEnd =Pattern.compile("</HTML>");
	
	private String text;

	private Integer length;

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLength() {
		return this.length;
	}


	public void setLength(Integer length) {
		this.length = length;
	}


	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		//String extractText = this.text.replaceAll("(?:<!--.*?(?:--.*?--\\s*)*.*?-->)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)", "");
		//String extractText = Pattern.compile("(?:<!--.*?(?:--.*?--\\s*)*.*?-->)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)").matcher(this.text).replaceAll("");

//		String extractText = StringUtils.replace(this.text, "(?:<!--.*?(?:--.*?--\\s*)*.*?-->)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)", "");
		String extractText = this.text;
		
		Matcher matcher ;
		
		matcher= SCRIPTS1.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= SCRIPTS.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= STYLE1.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= STYLE.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= HEAD1.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= HEAD.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= META.matcher(extractText);
		extractText = matcher.replaceAll("");
		
		matcher= htmlStart1.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= htmlStart.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= htmlEnd1.matcher(extractText);
		extractText = matcher.replaceAll("");
		matcher= htmlEnd.matcher(extractText);
		extractText = matcher.replaceAll("");
		
		if(this.length != null) {
			String addDot = "";
			if(this.text.length()>this.length){
				addDot=" ...";
			}
			extractText = StringUtils.left(extractText, this.length)+addDot;
		}
		try {
			out.println(extractText);
		} catch (IOException e) {
			new JspException(e);
		}

		return this.SKIP_BODY;
	}


}

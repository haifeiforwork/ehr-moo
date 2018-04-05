package com.tagfree.util;

/**
 * Tagfree Mime Utility 클래스에서 사용하는 상수 값 정의  
 * 
 * @author Jonghyun, Yoon [yoonjh@tagfree.com]
 */
public class Constant 
{
	public static final int UNKNOWN = -1;
	
	public static final int MAIN_TYPE_TEXT = 0;
	public static final int MAIN_TYPE_BINARY = 1;
	
	public static final int SUB_TYPE_PLAIN = 0;
	public static final int SUB_TYPE_HTML = 1;
	public static final int SUB_TYPE_OCTET_STREAM = 2;
	
	public static final int BASE64 = 0;
	public static final int QP = 1;
	
	/**
	 * HTML 전체를 가리킨다.
	 */
	public static final int HTML_ALL = 0;
	
	/**
	 * <pre><body> 태그 내의 값을 가리킨다.
	 * 
	 * ex) <body>test</body> -> test
	 * </pre>
	 */
	public static final int HTML_INNER_BODY = 1;
	
	/**
	 * <pre><body> 태그를 포함한 값을 가리킨다.
	 * 
	 * ex) <body bgcolor="red">test</body> -> <body bgcolor="red">test</body></pre>
	 */
	public static final int HTML_OUTER_BODY = 2;
}

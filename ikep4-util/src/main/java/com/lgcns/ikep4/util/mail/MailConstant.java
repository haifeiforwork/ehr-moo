package com.lgcns.ikep4.util.mail;

/**
 * MailConstant
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailConstant.java 16273 2011-08-18 07:07:47Z giljae $
 */
public final class MailConstant {

	private MailConstant() {
		throw new AssertionError();
	}

	/**
	 * 메일 기본 폴더
	 */
	public static final String FOLDER_INBOX = "inbox";
	
	public static final String FOLDER_UNREAD = "Unread";
	
	/**
	 * 보낸 메일 폴더
	 */
	public static final String FOLDER_SENT = "sent";

	/**
	 * EUC-KR 문자셋
	 */
	public static final String ENCODING_FOR_UNKNOWN = "EUC-KR";

	/**
	 * UTF-8 문자셋
	 */
	public static final String UTF8_ENCODING = "UTF-8";

	/**
	 * 컨텐츠타입 : text/plain
	 */
	public static final String MAIL_TEXT_PLAIN = "text/plain";

	/**
	 * 컨텐츠타입 : text/html
	 */
	public static final String MAIL_TEXT_HTML = "text/html";

	/**
	 * 컨텐츠타입 : multipart/alternative
	 */
	public static final String MAIL_MULTIPART_ALTERNATIVE = "multipart/alternative";

	/**
	 * 컨텐츠타입 : multipart/*
	 */
	public static final String MAIL_MULTIPART = "multipart/*";

	/**
	 * 컨텐츠타입 : message/rfc822
	 */
	public static final String MAIL_MESSAGE_RFC822 = "message/rfc822";

	/**
	 * 컨텐츠타입 : alternative
	 */
	public static final String MAIL_ALTERNATIVE = "alternative";

	/**
	 * UTF-8 byte length
	 */
	public static final int UTF8_LENGTH_INFO_BYTE_LENGTH_ID = 0;

	/**
	 * UTF-8 current byte
	 */
	public static final int UTF8_LENGTH_INFO_CURRENT_BYTE_ID = 1;

	/**
	 * UTF-8 display length
	 */
	public static final int UTF8_LENGTH_INFO_DISPLAY_LENGTH = 2;

	/**
	 * 메일 전송 타입 : html
	 */
	public static final String MAIL_TYPE_HTML = "html";

	/**
	 * 메일 전송 타입 : txt
	 */
	public static final String MAIL_TYPE_TXT = "txt";

	/**
	 * 메일 전송 타입 : template 메일
	 */
	public static final String MAIL_TYPE_TEPLATE = "template";
	
	/**
	 * IMAP connection time 설정
	 */
	public static final int IMAP_CONNECTION_TIME = 10000;

	/**
	 * IMAP fetch size 설정
	 */
	public static final int IMAP_FETCH_SIZE = 10240000;

}

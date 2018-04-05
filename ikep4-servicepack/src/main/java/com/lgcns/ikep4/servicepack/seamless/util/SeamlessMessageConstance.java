package com.lgcns.ikep4.servicepack.seamless.util;

public final class SeamlessMessageConstance {

	private SeamlessMessageConstance() {
	}
	
	/**
	 * IMAP 받은 메세지함 
	 */
	public static final String imapReceiveBox = "inbox";
	
	/**
	 * IMAP 보낸 메세지함 
	 */
	public static final String imapsendBox = "sent";
	
	/**
	 * MessageType mail
	 */
	public static final String  messageTypeMail = "Mail";
	
	/**
	 * MessageType message
	 */
	public static final String messageTypeMessage = "Message";
	
	/**
	 * MessageType SMS
	 */
	public static final String messageTypeSMS = "SMS";
	
	/**
	 * 타이틀 글자수 자르기
	 */
	public static final int titleWordNum = 100;
	
	/**
	 * 타이틀 글자수 자르기
	 */
	public static final int smsCutNum = 80;
	
	/**
	 * contact history 글자수 자르기
	 */
	public static final int historyWordNum = 60;
	
	/**
	 * contact history 보여줄 갯수
	 */
	public static final int contactViewCount = 10;
	
	/**
	 * more history 보여줄 갯수
	 */
	public static final int historyViewCount = 5;
}

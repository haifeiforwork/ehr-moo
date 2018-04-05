package com.lgcns.ikep4.support.mail.base;

public class MailException extends Exception{

	private static final long serialVersionUID = 428300689909982926L;
	
	public MailException() {
		super();
	}

	public MailException(String message) {
		super(message);
	}

	public MailException(String message, Throwable cause) {
	        super(message, cause);
	}

	public MailException(Throwable cause) {
	        super(cause);
	}
}

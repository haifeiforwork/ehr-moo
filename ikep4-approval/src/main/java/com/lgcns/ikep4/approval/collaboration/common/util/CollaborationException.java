package com.lgcns.ikep4.approval.collaboration.common.util;

public class CollaborationException extends Exception{

	private static final long serialVersionUID = 428300689909982926L;
	
	public CollaborationException() {
		super();
	}

	public CollaborationException(String message) {
		super(message);
	}

	public CollaborationException(String message, Throwable cause) {
	        super(message, cause);
	}

	public CollaborationException(Throwable cause) {
	        super(cause);
	}
}

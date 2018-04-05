package com.tagfree.util;

/**
 * This is the Mime Setup exception. 
 * 
 * @version 1.17, 05/06/19
 * @author sychang 
 */
public class MimeUtilException extends Exception {

	private static final long serialVersionUID = -4124603757961514298L;

	public MimeUtilException() {
    	super();
        }
    
    public MimeUtilException(String msg) {
        super(msg);
     }
    
    public MimeUtilException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MimeUtilException(Throwable cause) {
        super(cause);
    }
}

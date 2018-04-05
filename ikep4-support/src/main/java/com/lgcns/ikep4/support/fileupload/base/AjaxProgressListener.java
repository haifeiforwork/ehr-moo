package com.lgcns.ikep4.support.fileupload.base;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;


/**
 * 멀티파트 업로드 Listener
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: AjaxProgressListener.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class AjaxProgressListener implements ProgressListener {
	
	public final static int PERENTAGE = 100;
	
	public HttpSession session;

	public final static String STATUS = "UPLOAD_STATUS";

	public final static String CONTENT_LENGTH = "CONTENT_LENGTH";

	public void update(long pBytesRead, long pContentLength, int pItems) {
		// Logger.getLogger(this.getClass()).debug("update : " + pBytesRead +
		// ", " + pContentLength + ", " + pItems);

		int percent = (int) (((float) pBytesRead / pContentLength) * PERENTAGE);
		session.setAttribute(STATUS, percent);

		if (pBytesRead == 0) {
			session.setAttribute(CONTENT_LENGTH, pContentLength);
		}
		// Logger.getLogger(this.getClass()).debug("percent : " + percent);

		//if (pBytesRead == pContentLength) {
			// Logger.getLogger(this.getClass()).debug("upload success.");
		//}
	}

	public static void main(String[] args) {
		// System.out.println((int) (((float) 152066 / 152066) * 100));
	}
}

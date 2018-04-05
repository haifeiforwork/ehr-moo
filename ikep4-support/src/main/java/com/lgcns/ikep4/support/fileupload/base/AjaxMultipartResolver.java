package com.lgcns.ikep4.support.fileupload.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 * 멀티파트 업로드 Resolver
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: AjaxMultipartResolver.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class AjaxMultipartResolver extends CommonsMultipartResolver {
	private AjaxProgressListener pListener;

	public void setProgressListener(AjaxProgressListener p) {
		this.pListener = p;
	}

	public AjaxProgressListener getProgressListener() {
		return this.pListener;
	}

	public boolean isMultipart(HttpServletRequest request) {
		this.pListener.session = request.getSession(true);

		return super.isMultipart(request);
	}

	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		FileUpload fu = super.newFileUpload(fileItemFactory);
		if (this.pListener != null) {
			fu.setProgressListener(this.pListener);
		}

		return fu;
	}
}

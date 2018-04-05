package com.lgcns.ikep4.support.searchpreprocessor.util;

import org.springframework.util.StringUtils;
/**
 * 
 *  페이지 startindex endindex분석
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: PageUtil.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class PageUtil {
	int startIndex =0;
	int next=MagicNumUtils.NEXT_PAGE;
		
	public PageUtil(String _startIndex,String _next) {
		
		
		if( StringUtils.hasText(_startIndex)) 
		{
			try
			{
				startIndex=Integer.parseInt(_startIndex);
			}
			catch(Exception e){}
		}
		
		if( StringUtils.hasText(_next)) 
		{
			try
			{
				next =Integer.parseInt(_next);
			}
			catch(Exception e){}
		}
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getNext() {
		return next;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setNext(int next) {
		this.next = next;
	}
}
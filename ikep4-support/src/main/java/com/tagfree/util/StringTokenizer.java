package com.tagfree.util;

import java.util.*;

public class StringTokenizer 
{
	protected Vector m_vecToken;
	protected String m_strData;
	protected String m_strDelimiter;
	protected int m_iCurrentIndex;
	protected int m_iSize;
	
	public StringTokenizer(String str, String delim)
	{
		m_strData = str;
		m_strDelimiter = delim;
		m_vecToken = new Vector();
		splitString();
	}
	
	public int countTokens()
	{
		return m_iSize;
	}
	
	public boolean hasMoreTokens()
	{
		return m_iCurrentIndex < m_iSize ? true : false;
	}
	
	public String nextToken()
	{
		String s = (String)m_vecToken.elementAt(m_iCurrentIndex++);
		return s;
	}
	
	private void splitString()
	{
		m_iSize = 0;
		m_vecToken.removeAllElements();
		
		int sIndex = 0;
		int eIndex = 0;
		
		String temp;
		boolean bLoop = true;

		if (m_strData.length() > 0)
		{
			while (bLoop)
			{
				eIndex = m_strData.indexOf(m_strDelimiter, sIndex);
				if (eIndex == -1)
				{
					eIndex = m_strData.length();
					bLoop = false;
				}
				
				temp = m_strData.substring(sIndex, eIndex);
				if (temp.length() > 0)
				{
					m_vecToken.addElement(temp);
				}
				
				sIndex = eIndex + m_strDelimiter.length();
			}
		}
		
		m_iSize = m_vecToken.size();
	}
}

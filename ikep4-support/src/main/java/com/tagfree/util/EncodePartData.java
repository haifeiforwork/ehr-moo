package com.tagfree.util;

public class EncodePartData {
	protected String m_strEncodePartData;

	protected String m_strContentLocation;

	protected String m_strContent;

	protected String m_strContentMainType;

	protected String m_strContentSubType;

	protected String m_strEncodingType;

	protected String m_strContentID;

	public EncodePartData() {}

	public EncodePartData(String strEncodePartData) {
		setEncodePartData(strEncodePartData);
	}

	public void setEncodePartData(String strEncodePartData) {
		m_strEncodePartData = strEncodePartData;
		extractInformation();
	}

	public String getContentLocation(boolean bUrldecode) {
		if (m_strContentLocation == null)
			return null;

		String str = m_strContentLocation;
		if (bUrldecode) {
			try {
				// sun에서는 동작하는데 ibm에서는 잘못 동작함 그래서 독립시킨 메소드
				str = URLDecoder.decode(str);
			} catch (Exception e) {
				return m_strContentLocation;
			}
		}
		return str;
	}

	public String getContentID() {
		return m_strContentID;
	}

	public int getEncodingType() {
		if (m_strEncodingType.compareToIgnoreCase("base64") == 0)
			return Constant.BASE64;

		if (m_strEncodingType.compareToIgnoreCase("quoted-printable") == 0)
			return Constant.QP;

		return Constant.UNKNOWN;
	}

	public int getContentMainType() {
		if (m_strContentMainType.compareToIgnoreCase("text") == 0)
			return Constant.MAIN_TYPE_TEXT;

		if (m_strContentMainType.compareToIgnoreCase("application") == 0)
			return Constant.MAIN_TYPE_BINARY;

		return Constant.UNKNOWN;
	}

	public int getContentSubType() {
		if (m_strContentSubType.compareToIgnoreCase("plain") == 0)
			return Constant.SUB_TYPE_PLAIN;

		if (m_strContentSubType.compareToIgnoreCase("html") == 0)
			return Constant.SUB_TYPE_HTML;

		if (m_strContentSubType.compareToIgnoreCase("octet-stream") == 0)
			return Constant.SUB_TYPE_OCTET_STREAM;

		return Constant.UNKNOWN;
	}

	public String getContent() {
		return m_strContent;
	}

	protected void extractInformation() {
		try {
			int iSplitterLength = 4;
			String strDelimUnit = "\r\n";

			int iHeadEnd = m_strEncodePartData.indexOf("\r\n\r\n");
			if (iHeadEnd == -1) {
				iHeadEnd = m_strEncodePartData.indexOf("\n\n");
				iSplitterLength = 2;
				strDelimUnit = "\n";
			}

			m_strContent = m_strEncodePartData.substring(iHeadEnd + iSplitterLength);

			String strLine = "";
			String strValue = "";
			String strParam = "";
			int iSemicolon = 0;
			String m_strHead = m_strEncodePartData.substring(0, iHeadEnd);

			StringTokenizer stHead = new StringTokenizer(m_strHead, strDelimUnit);

			while (stHead.hasMoreTokens()) {
				strLine = stHead.nextToken();
				iSemicolon = strLine.indexOf(":");

				if (strLine.toLowerCase().startsWith("content"))// tagfree_mime_v6
																// added
				{
					strParam = strLine.substring(0, iSemicolon).trim();
					strValue = strLine.substring(iSemicolon + 1).trim();
				}

				if (strParam.compareToIgnoreCase("content-type") == 0) {
					int iSlash = strValue.indexOf("/");
					if (iSlash == -1)
						m_strContentMainType = strValue;
					else {
						m_strContentMainType = strValue.substring(0, iSlash).trim();
						int iColon = strValue.indexOf(";", iSlash);
						if (iColon == -1)
							m_strContentSubType = strValue.substring(iSlash + 1);
						else
							m_strContentSubType = strValue.substring(iSlash + 1, iColon);
					}
				} else if (strParam.compareToIgnoreCase("content-transfer-encoding") == 0) {
					m_strEncodingType = strValue;
				} else if (strParam.compareToIgnoreCase("content-location") == 0) {
					m_strContentLocation = strValue;
				} else if (strParam.compareToIgnoreCase("content-id") == 0) {
					//&lt;20110608.72426.001@tagfree.com&gt;
					int check = strValue.indexOf("&lt;");
					if(check == -1){
						m_strContentID = strValue.substring(1, strValue.length() - 1);
					}else{
						m_strContentID = strValue.substring(4, strValue.length() - 4);
					}
				} else {
					// can't arrived..
				}
			}
		} catch (Exception e) {
		}
	}
}

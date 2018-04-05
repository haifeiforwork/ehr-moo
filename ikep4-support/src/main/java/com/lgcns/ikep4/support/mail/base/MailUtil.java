package com.lgcns.ikep4.support.mail.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.model.MailAttach;
import com.lgcns.ikep4.util.http.HttpUtil;


/**
 * MailUtil 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailUtil.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public final class MailUtil {

	private MailUtil() {
		throw new AssertionError();
	}

	/**
	 * 메일 내용 디코딩
	 * 
	 * @param text
	 * @param encoding
	 * @return
	 */
	private static String decodeUnknownFormat(String text, String encoding) {

		try {

			String enc = encoding;
			if (isEmpty(enc)) {

				if (isEmpty(MailConstant.ENCODING_FOR_UNKNOWN)) {
					enc = System.getProperty("file.encoding");
				} else {
					enc = MailConstant.ENCODING_FOR_UNKNOWN;
				}
			}

			return new String(text.getBytes("8859_1"), enc);
		} catch (UnsupportedEncodingException e) {
			return text;
		}
	}

	/**
	 * 메일 내용 디코딩
	 * 
	 * @param text
	 * @param subCharset
	 * @return
	 */
	public static String decodeText(String text, String subCharset) {

		if (text == null) {
			return null;
		}

		// 결과물
		StringBuffer sBuf = new StringBuffer();

		// 여러줄로 나누어 오는 것이 있으므로 분리해서 Collection에 담는다.
		String tmp = text.replaceAll("\r|\n", "");

		Pattern pattern = Pattern.compile("=\\?[^?]*\\?[^?]*\\?[^?]*\\?=");

		while (true) {

			String before = null;

			Matcher matcher = pattern.matcher(tmp);
			// 인코딩된 문자열이 있는경우.
			if (matcher.find()) {

				int start = matcher.start();
				// 인코딩된 문자열 앞에 비 인코딩 문자열이 있는 경우
				if (start > 0) {

					before = tmp.substring(0, start);
					sBuf.append(decodeUnknownFormat(before, subCharset));

					before = null;
				}

				String group = matcher.group();
				// 인코딩된 문자열을 저장
				try {
					sBuf.append(MimeUtility.decodeText(group));
				}
				// 지원하지 않는 Encoding인경우 지정 또는 시스템 Encoding으로 강제 변환한다.
				catch (UnsupportedEncodingException e) {

					// ?를 구분자로 텍스트를 자른다.
					String[] otherArray = group.split("[?]");
					// 4번째가 제목 텍스트이다. 위의 정규식에 의하면 무조건 존재한다.
					sBuf.append(decodeUnknownFormat(otherArray[3], subCharset));
				}

				tmp = tmp.substring(matcher.end());
			}
			// 인코딩된 문자열이 없는 경우 (멀티바이트 문자열인경우 비표준인경우이다)
			else {

				sBuf.append(decodeUnknownFormat(tmp, subCharset));
				tmp = "";
			}

			if (tmp.length() == 0) {
				break;
			}
		}

		return sBuf.toString();
	}

	/**
	 * 해더 정보 얻기
	 * 
	 * @param part
	 * @param name
	 * @return
	 * @throws MessagingException
	 */
	public static String getHeaderFirst(Part part, String name) throws MessagingException {

		String result = null;

		String[] array = part.getHeader(name);
		if (array != null && array.length > 0) {
			result = array[0];
		}

		return result;
	}

	/**
	 * 발신자 얻기
	 * 
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	public static String[] getFrom(Message message) throws MessagingException {

		String[] from = { null, null };

		Address[] address = message.getFrom();
		if (address != null && address.length > 0) {

			String tmp = decodeText(address[0].toString(), getContentCharset(message));

			Pattern pattern = Pattern.compile("<.*@.*>");
			Matcher matcher = pattern.matcher(tmp);
			// 이름과 메일주소로 이루어진 From인경우
			if (matcher.find()) {

				from[0] = tmp.substring(0, matcher.start()).replaceAll("\"", "").trim();
				from[1] = matcher.group().replaceAll("^<|>$", "").trim();
			}
			// 메일 주소만 있는경우
			else {

				// 두개의 내용을 똑같이 만든다.
				from[0] = tmp.trim();
				from[1] = from[0];
			}

		}

		return from;
	}

	/**
	 * 수신자 얻기
	 * 
	 * @param message
	 * @param mail
	 * @throws MessagingException
	 */
	@SuppressWarnings("rawtypes")
	public static void getTo(Message message, Mail mail) throws MessagingException {

		List<HashMap> toEmailList = new ArrayList<HashMap>();
		List<HashMap> ccEmailList = new ArrayList<HashMap>();
		List<HashMap> bccEmailList = new ArrayList<HashMap>();

		// 수신자 리스트 셋팅
		Address[] address = message.getRecipients(RecipientType.TO);

		if (address != null) {
			for (int i = 0; i < address.length; i++) {

				String tmp = decodeText(address[i].toString(), getContentCharset(message));

				Pattern pattern = Pattern.compile("<.*@.*>");
				Matcher matcher = pattern.matcher(tmp);

				HashMap<String, String> map = new HashMap<String, String>();

				// 이름과 메일주소로 이루어진 From인경우
				if (matcher.find()) {
					map.put("name", tmp.substring(0, matcher.start()).replaceAll("\"", "").trim());
					map.put("email", matcher.group().replaceAll("^<|>$", "").trim());
				}
				// 메일 주소만 있는경우
				else {
					// 두개의 내용을 똑같이 만든다.
					map.put("name", tmp.trim());
					map.put("email", tmp.trim());
				}

				toEmailList.add(map);
			}
		}

		// 참조자 리스트 셋팅
		address = message.getRecipients(RecipientType.CC);

		if (address != null) {
			for (int i = 0; i < address.length; i++) {

				String tmp = decodeText(address[i].toString(), getContentCharset(message));

				Pattern pattern = Pattern.compile("<.*@.*>");
				Matcher matcher = pattern.matcher(tmp);

				HashMap<String, String> map = new HashMap<String, String>();

				// 이름과 메일주소로 이루어진 From인경우
				if (matcher.find()) {
					map.put("name", tmp.substring(0, matcher.start()).replaceAll("\"", "").trim());
					map.put("email", matcher.group().replaceAll("^<|>$", "").trim());
				}
				// 메일 주소만 있는경우
				else {
					// 두개의 내용을 똑같이 만든다.
					map.put("name", tmp.trim());
					map.put("email", tmp.trim());
				}

				ccEmailList.add(map);
			}
		}

		// 비밀 참조자 리스트 셋팅
		address = message.getRecipients(RecipientType.BCC);

		if (address != null) {
			for (int i = 0; i < address.length; i++) {

				String tmp = decodeText(address[i].toString(), getContentCharset(message));

				Pattern pattern = Pattern.compile("<.*@.*>");
				Matcher matcher = pattern.matcher(tmp);

				HashMap<String, String> map = new HashMap<String, String>();

				// 이름과 메일주소로 이루어진 From인경우
				if (matcher.find()) {
					map.put("name", tmp.substring(0, matcher.start()).replaceAll("\"", "").trim());
					map.put("email", matcher.group().replaceAll("^<|>$", "").trim());
				}
				// 메일 주소만 있는경우
				else {
					// 두개의 내용을 똑같이 만든다.
					map.put("name", tmp.trim());
					map.put("email", tmp.trim());
				}

				bccEmailList.add(map);
			}
		}

		mail.setToEmailList(toEmailList);
		mail.setCcEmailList(ccEmailList);
		mail.setBccEmailList(bccEmailList);

	}

	/**
	 * MimeType 얻기
	 * 
	 * @param part
	 * @return
	 * @throws MessagingException
	 */
	public static String getContentMimeType(Part part) throws MessagingException {

		if (part == null || part.getContentType() == null) {
			return null;
		}

		String[] strArray = part.getContentType().split("[;]");

		return strArray[0].trim();
	}

	/**
	 * 내용 문자셋 얻기
	 * 
	 * @param part
	 * @return
	 * @throws MessagingException
	 */
	public static String getContentCharset(Part part) throws MessagingException {

		if (part == null || part.getContentType() == null) {
			return null;
		}

		String charset = null;

		Pattern pattern = Pattern.compile("^charset=", Pattern.CASE_INSENSITIVE);

		String[] strArray = part.getContentType().split("[;]");
		for (String str : strArray) {

			Matcher matcher = pattern.matcher(str.trim());
			if (matcher.find()) {

				charset = matcher.replaceAll("").replaceAll("\"", "");
				break;
			}
		}

		return charset;
	}

	/**
	 * Multipart로부터 텍스트(메일 메세지 본문)를 추출하여 반환한다.
	 * 
	 * @param multipart
	 * @return
	 * @throws MessagingException
	 */
	public static BodyPart getTextBodyPart(Multipart multipart) throws MessagingException {

		BodyPart bodyPart = null;

		for (int i = 0; i < multipart.getCount(); i++) {

			bodyPart = multipart.getBodyPart(i);
			if (!bodyPart.isMimeType("text/*")) {
				continue;
			}

			if (bodyPart.isMimeType("text/html")) {
				break;
			}
		}

		return bodyPart;
	}

	/**
	 * 메일 바디 정보 얻기
	 * 
	 * @param multipart
	 * @param mpartPath
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static BodyPart getBodyPart(Multipart multipart, int[] mpartPath) throws MessagingException, IOException {

		Part part = null;
		Multipart multi = multipart;
		for (int i = 0; i < mpartPath.length; i++) {

			if (i > 0) {
				Part subPart = (Part) part.getContent();
				multi = (Multipart) subPart.getContent();
			}

			int index = mpartPath[i];
			part = multi.getBodyPart(index);
		}

		return (BodyPart) part;
	}

	/**
	 * 메일 바디 정보 얻기
	 * 
	 * @param multipart
	 * @param mpartPath
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static BodyPart getBodyPart(Multipart multipart, String[] mpartPath) throws MessagingException, IOException {

		// 문자열의 배열로 되어있는 multipart의 path를 숫자로 변환한다.
		int[] pathArray = new int[mpartPath.length];
		for (int i = 0; i < mpartPath.length; i++) {
			pathArray[i] = Integer.parseInt(mpartPath[i]);
		}

		return getBodyPart(multipart, pathArray);
	}

	/**
	 * 메일 바디 정보 얻기
	 * 
	 * @param content
	 * @param mimeType
	 * @param charset
	 * @return
	 * @throws MessagingException
	 */
	public static BodyPart getContentBodyPart(String content, String mimeType, String charset)
			throws MessagingException {

		String charsetFinal = "";
		if (!isEmpty(charset)) {
			charsetFinal = "; charset=\"" + charset + "\"";
		}

		MimeBodyPart bodyPart = null;

		// text/plain
		String textContentType = MailConstant.MAIL_TEXT_PLAIN + charsetFinal;
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(content, textContentType);
		textPart.setHeader("Content-Type", textContentType);

		// text/html
		String htmlContentType = MailConstant.MAIL_TEXT_HTML + charsetFinal;
		MimeBodyPart htmlPart = new MimeBodyPart();

		if (mimeType.equalsIgnoreCase(MailConstant.MAIL_MULTIPART_ALTERNATIVE)) {

			// alternative인 경우 html에는 내용의 개행에 <br />태그를 함께 넣는다.
			htmlPart.setContent(toInnerLineBreak(content), htmlContentType);

			MimeMultipart alternativePart = new MimeMultipart(MailConstant.MAIL_ALTERNATIVE);
			alternativePart.addBodyPart(textPart);
			alternativePart.addBodyPart(htmlPart);

			bodyPart = new MimeBodyPart();
			bodyPart.setContent(alternativePart);
		} else if (mimeType.equalsIgnoreCase(MailConstant.MAIL_TEXT_HTML)) {

			htmlPart.setContent(content, htmlContentType);
			bodyPart = htmlPart;
		} else {
			bodyPart = textPart;
		}

		htmlPart.setHeader("Content-Type", htmlContentType);

		textContentType = null;
		htmlContentType = null;
		textPart = null;
		htmlPart = null;

		return bodyPart;
	}

	/**
	 * 메일 바디 정보 얻기
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static BodyPart getMessageRfc822BodyPart(Message message) throws MessagingException {

		DataHandler handler = new DataHandler(message, MailConstant.MAIL_MESSAGE_RFC822);

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setDataHandler(handler);

		return bodyPart;
	}

	/**
	 * SSL 프로퍼디 정보 셋팅
	 * 
	 * @param destProps
	 * @param protocol
	 * @param port
	 */
	public static void setPropertiesForSsl(Properties destProps, String protocol, String port) {

		String portKey = "mail." + protocol + ".port";
		destProps.remove(portKey);
		destProps.put(portKey, port);

		destProps.put("mail." + protocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		destProps.put("mail." + protocol + ".socketFactory.fallback", "false");
		destProps.put("mail." + protocol + ".socketFactory.port", port);
	}

	/**
	 * 태그 변환
	 * 
	 * @param tag
	 * @return
	 */
	private static String getRegexTag(String tag) {
		return "<" + tag + "[^>]*>|</" + tag + "[^>]*>";
	}

	/**
	 * 태그 변환
	 * 
	 * @param tag
	 * @return
	 */
	private static String getRegexTagInnerBody(String tag) {
		return "<" + tag + "[^>]*>.*</" + tag + "[^>]*>";
	}

	/**
	 * 패턴 변화
	 * 
	 * @param text
	 * @param regex
	 * @return
	 */
	private static String removePattern(String text, String regex) {

		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.replaceAll("");
		}

		return text;
	}

	/**
	 * 태그 삭제
	 * 
	 * @param text
	 * @param tag
	 * @param removeBody
	 * @return
	 */
	public static String removeTag(String text, String tag, boolean removeBody) {

		String result = text;

		if (removeBody) {
			result = removePattern(result, getRegexTagInnerBody(tag));
		}

		return removePattern(result, getRegexTag(tag));
	}

	/**
	 * 해당 문자가 개행문자(\n)인경우 &lt;br /&gt;을 추가하여 반환. (\r)제거
	 * 
	 * @param ch
	 * @return
	 */
	private static String lineBreakConvertHtml(char ch) {

		String result = null;

		switch (ch) {

		case '\n': {
			result = "<br />\n";
			break;
		}
		case '\r': {
			result = "";
			break;
		}
		default: {
			result = String.valueOf(ch);
			break;
		}
		}

		return result;
	}

	/**
	 * Html 특수문자를 Html 엔터티로 변환환다.<br />
	 * Html 소스를 웹브라우저에 표시하기 위해 변환합니다.
	 * 
	 * @param text
	 * @param innerLineBreak
	 * @return
	 */
	public static String toText(String text, boolean innerLineBreak) {

		if (isEmpty(text)) {
			return "";
		}

		StringBuffer sBuf = new StringBuffer();

		int length = text.length();
		for (int i = 0; i < length; i++) {

			char tmp = text.charAt(i);
			switch (tmp) {

			case '<': {
				sBuf.append("&lt;");
				break;
			}
			case '>': {
				sBuf.append("&gt;");
				break;
			}
			case '&': {
				sBuf.append("&amp;");
				break;
			}
			default: {

				if (innerLineBreak) {
					sBuf.append(lineBreakConvertHtml(tmp));
				} else {
					sBuf.append(tmp);
				}
				break;
			}
			}
		}

		return sBuf.toString();
	}

	/**
	 * Text의 개행에 Html태그 &ltbr /&gt를 추가하여 반환한다.<br />
	 * Text를 웹브라우저에 표기할때 개행이 되어 표시하기 위해 변환합니다.
	 * 
	 * @param text
	 * @return
	 */
	public static String toInnerLineBreak(String text) {

		if (isEmpty(text)) {
			return "";
		}

		StringBuffer sBuf = new StringBuffer();

		int length = text.length();
		for (int i = 0; i < length; i++) {

			char tmp = text.charAt(i);
			sBuf.append(lineBreakConvertHtml(tmp));
		}

		return sBuf.toString();
	}

	/**
	 * 공백 체크
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {

		if (str == null) {
			return true;
		}

		return str.trim().equals("");
	}

	/**
	 * 동일한 값 체크
	 * 
	 * @param str
	 * @param value
	 * @return
	 */
	public static boolean isEquals(String str, String value) {

		if (str == null) {
			return false;
		}

		return str.equals(value);
	}

	/**
	 * 동일한 값 체크
	 * 
	 * @param str
	 * @param value
	 * @return
	 */
	public static boolean isEqualsIgnoreCase(String str, String value) {

		if (str == null) {
			return false;
		}

		return str.equalsIgnoreCase(value);
	}

	/**
	 * Null 변환
	 * 
	 * @param str
	 * @return
	 */
	public static String toNotNull(String str) {

		if (str == null) {
			return "";
		}

		return str;
	}

	/**
	 * 문자 변환
	 * 
	 * @param text
	 * @return
	 */
	public static String toExcel(String text) {

		if (isEmpty(text)) {
			return "";
		}

		StringBuffer sBuf = new StringBuffer();

		int length = text.length();
		for (int i = 0; i < length; i++) {

			char tmp = text.charAt(i);
			if (tmp != '\r') {
				sBuf.append(tmp);
			}
		}

		return sBuf.toString();
	}

	/**
	 * 문자열 카운트
	 * 
	 * @param text
	 * @return
	 */
	public static int lineCount(String text) {

		if (isEmpty(text)) {
			return 0;
		}

		int count = 1;

		int length = text.length();
		for (int i = 0; i < length; i++) {

			char tmp = text.charAt(i);

			if (tmp == '\n') {
				count++;
			}
		}

		return count;
	}

	/**
	 * UTF-8 문자열 얻기
	 * 
	 * @param str
	 * @param size
	 * @param utf8DisplaySize
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private static int[] getUTF8LengthInfo(String str, int size, int utf8DisplaySize) throws MessagingException,
			UnsupportedEncodingException {

		int byteLength = 0;
		int currentByte = 0;
		int displayLength = 0;

		byte[] byteArray = str.getBytes(MailConstant.UTF8_ENCODING);

		while (byteLength < byteArray.length) {

			byte tmp = byteArray[byteLength];

			if (tmp == 9 || tmp == 10 || (tmp >= 32 && tmp <= 126)) {

				byteLength++;
				currentByte = 1;
				displayLength++;
			} else if (tmp >= -62 && tmp <= -33) { // tmp >= 194 && tmp <= 223

				byteLength += 2;
				currentByte = 2;
				displayLength += utf8DisplaySize;
			} else if (tmp >= -32 && tmp <= -17) { // tmp >= 224 && tmp <= 239

				byteLength += 3;
				currentByte = 3;
				displayLength += utf8DisplaySize;
			} else if (tmp >= -16 && tmp <= -9) { // tmp >= 240 && tmp <= 247

				byteLength += 4;
				currentByte = 4;
				displayLength += utf8DisplaySize;
			} else if (tmp >= -8 && tmp <= -5) { // tmp >= 248 && tmp <= 251

				byteLength += 5;
				currentByte = 5;
				displayLength += utf8DisplaySize;
			} else if (tmp >= -4 && tmp <= -3) { // tmp >= 252 && tmp <= 253

				byteLength += 6;
				currentByte = 6;
				displayLength += utf8DisplaySize;
			} else {
				byteLength++;
			}

			if (size > 0 && displayLength >= size) {
				break;
			}
		}

		int[] lengthInfoArray = new int[3];
		lengthInfoArray[MailConstant.UTF8_LENGTH_INFO_BYTE_LENGTH_ID] = byteLength;
		lengthInfoArray[MailConstant.UTF8_LENGTH_INFO_CURRENT_BYTE_ID] = currentByte;
		lengthInfoArray[MailConstant.UTF8_LENGTH_INFO_DISPLAY_LENGTH] = displayLength;

		byteArray = null;

		return lengthInfoArray;
	}

	/**
	 * UTF-8 문자열 잘라내기
	 * 
	 * @param str
	 * @param size
	 * @param utf8DisplaySize
	 * @param endStr
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static String uTF8Substring(String str, int size, int utf8DisplaySize, String endStr)
			throws MessagingException, UnsupportedEncodingException {

		int[] lengthInfoArray = getUTF8LengthInfo(str, size, utf8DisplaySize);
		int byteLength = lengthInfoArray[MailConstant.UTF8_LENGTH_INFO_BYTE_LENGTH_ID];
		int currentByte = lengthInfoArray[MailConstant.UTF8_LENGTH_INFO_CURRENT_BYTE_ID];
		int displayLength = lengthInfoArray[MailConstant.UTF8_LENGTH_INFO_DISPLAY_LENGTH];

		if (displayLength > size) {
			byteLength -= currentByte;
		}

		byte[] byteArray = str.getBytes(MailConstant.UTF8_ENCODING);
		byte[] newByte = new byte[byteLength];

		/**
		 * for (int i = 0; i < newByte.length; i++) { newByte[i] = byteArray[i];
		 * }
		 **/
		System.arraycopy(byteArray, 0, newByte, 0, newByte.length);

		String resultStr = new String(newByte, MailConstant.UTF8_ENCODING);
		if (byteLength < byteArray.length) {
			resultStr += endStr;
		}

		byteArray = null;
		newByte = null;

		return resultStr;
	}

	/**
	 * byte 문자열 잘라내기
	 * 
	 * @param str
	 * @param size
	 * @param endStr
	 * @param encoding
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static String twoByteSubstring(String str, int size, String endStr, String encoding)
			throws MessagingException, UnsupportedEncodingException {

		byte[] byteArray = str.getBytes(encoding);

		if (size < byteArray.length) {

			int twoByteChar = 0;
			for (int i = 0; i < size; i++) {

				if (byteArray[i] < 0) {
					twoByteChar++;
				}
			}

			int finalSize = size;
			if (twoByteChar % 2 == 1) {
				finalSize--;
			}

			byte[] newByte = new byte[finalSize];

			/**
			 * for (int i = 0; i < newByte.length; i++) { newByte[i] =
			 * byteArray[i]; }
			 **/

			System.arraycopy(byteArray, 0, newByte, 0, newByte.length);

			return new String(newByte, encoding) + endStr;
		} else {
			return str;
		}
	}

	/**
	 * 한글 문자열 잘라내기
	 * 
	 * @param str
	 * @param size
	 * @param endStr
	 * @return
	 */
	public static String korSubstring(String str, int size, String endStr) {

		// if (isEqualsIgnoreCase(System.getProperty("file.encoding"),
		// UTF8_ENCODING)) {
		// return UTF8Substring(str, size, 2, endStr);
		// } else {
		// return twoByteSubstring(str, size, endStr, "euc-kr");
		// }

		int count = 0;
		int i = 0;
		for (i = 0; i < str.length(); i++) {

			int codeval = str.codePointAt(i);
			if (codeval < 128) {
				count++;
			} else {
				count += 2;
			}

			if (count > size) {
				break;
			}
		}

		String resultStr = str.substring(0, i);
		if (i < str.length()) {
			resultStr += endStr;
		}

		return resultStr;
	}

	/**
	 * 메일 플레그 셋팅
	 * 
	 * @param mail
	 * @param message
	 * @throws MessagingException
	 */
	public static void setFlagFromMessage(Mail mail, Message message) throws MessagingException {

		Flags flags = message.getFlags();

		/*
		 * System Flag (메일 시스템 기본 플래그)
		 */
		Flag[] flagArray = flags.getSystemFlags();
		for (Flag flag : flagArray) {

			if (flag == Flag.SEEN) {
				mail.setReadYn("Y");
			} else {
				mail.setReadYn("N");
			}
		}

		flagArray = null;
	}

	/**
	 * 첨부파일 정보 얻기
	 * 
	 * @param part
	 * @param multipartPath
	 * @return
	 * @throws MessagingException
	 */
	public static MailAttach getMessageAttach(BodyPart part, String multipartPath) throws MessagingException {

		MailAttach attachVO = new MailAttach();
		attachVO.setName(MailUtil.decodeText(part.getFileName(), MailUtil.getContentCharset(part)));
		attachVO.setSize(part.getSize());
		attachVO.setFileType(MailUtil.getContentMimeType(part));
		attachVO.setMultipartPath(multipartPath);

		return attachVO;
	}

	/**
	 * 메일 주소가 ("김태희" <dd@daum.net>, "김태희" <dd@daum.net>) 이런 형식의 문자열인 경우
	 * 
	 * @param addrList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<HashMap> getMailAddr(String addrList) {

		List<HashMap> list = new ArrayList<HashMap>();

		StringTokenizer st = new StringTokenizer(addrList, ",");

		String token = "";
		String email = "";
		String name = "";

		while (st.hasMoreElements()) {

			try {
				token = (st.nextToken()).trim();

				if (token.contains("@")) {
					if (token.contains("<") && token.contains(">")) {
						email = token.substring(token.indexOf('<') + 1, token.indexOf('>')).trim();
						if (token.contains("\"")) {
							name = token.substring(token.indexOf('\"') + 1);
							name = name.substring(0, name.indexOf('\"')).trim();
						}

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("email", email);
						map.put("name", name);

						list.add(map);
					} else {
						email = token.trim();

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("email", email);

						list.add(map);
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
				throw new IKEP4ApplicationException("", e);
			}
		}

		return list;

	}

	/**
	 * 메일 리스트가 (TO:김태희:dd@daum.net) 이런 형식의 리스트일 경우, 디코딩하여 메일 수신자를 셋팅함
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void getMailAddrForList(Mail mail) {

		String token = "";
		String email = "";
		String name = "";

		List<HashMap> toEmailList = new ArrayList<HashMap>();
		List<HashMap> ccEmailList = new ArrayList<HashMap>();
		List<HashMap> bccEmailList = new ArrayList<HashMap>();

		for (String addr : mail.getEmailList()) {

			try {
				StringTokenizer st = new StringTokenizer(addr.replaceAll(":",": "), ":");

				if (st.countTokens() >= 3) {
					token = (st.nextToken()).trim();
					name = (st.nextToken()).trim();
					email = (st.nextToken()).trim();

				} else if (st.countTokens() == 2) {
					token = (st.nextToken()).trim();
					name = (st.nextToken()).trim();
					email = name;
				} else {
					token = "";
				}

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", email);
				map.put("name", name);

				if (token.equals("TO")) {
					toEmailList.add(map);
				} else if (token.equals("CC")) {
					ccEmailList.add(map);
				} else if (token.equals("BCC")) {
					bccEmailList.add(map);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				throw new IKEP4ApplicationException("", e);
			}
		}

		mail.setToEmailList(toEmailList);
		mail.setCcEmailList(ccEmailList);
		mail.setBccEmailList(bccEmailList);

	}

	/**
	 * 메일 리스트가 (TO:김태희:dd@daum.net) 이런 형식의 리스트일 경우, 디코딩하여 쪽지 수신자를 셋팅함
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unused")
	public static String getMessageAddrForList(Mail mail) {

		String token = "";
		String email = "";
		String name = "";
		String id = "";
		String messageAddr = "";

		for (String addr : mail.getEmailList()) {

			try {
				StringTokenizer st = new StringTokenizer(addr.replaceAll(":",": "), ":");

				if (st.countTokens() >= 3) {
					token = (st.nextToken()).trim();
					name = (st.nextToken()).trim();
					email = (st.nextToken()).trim();
					id = (st.nextToken()).trim();
					;

				} else {
					token = "";
				}

				if (!token.equals("")) {
					messageAddr = messageAddr + id + "/" + name + ",";
				}

			} catch (Exception e) {
				// e.printStackTrace();
				throw new IKEP4ApplicationException("", e);
			}
		}

		return messageAddr;

	}

	/**
	 * 메일 첨부 내용 셋팅
	 * 
	 * @param content
	 * @param request
	 * @return
	 */
	public static String setAttahContent(String content, HttpServletRequest request) {

		StringBuffer bf = new StringBuffer();

		String webBase = HttpUtil.getWebAppUrl(request);

		bf.append("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='ko'>");
		bf.append("<head>");
		bf.append("<link rel='stylesheet' type='text/css' href='" + request.getContextPath()
				+ "/base/css/common.css'/>");
		bf.append("</head>");
		bf.append("<body>");
		bf.append(content);
		bf.append("</body>");
		bf.append("</html>");

		return bf.toString().replaceAll("/ikep4-webapp/base", webBase + "/base");
	}

}

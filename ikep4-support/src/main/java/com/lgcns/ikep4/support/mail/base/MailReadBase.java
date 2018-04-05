package com.lgcns.ikep4.support.mail.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.HttpServletResponse;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.base.MailException;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.model.MailAttach;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;

/**
 * MailReadBase 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailReadBase.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class MailReadBase {

	private Store store = null;

	/**
	 * 메일 공통 정보 셋팅
	 * 
	 * @param mail
	 * @param gubun
	 * @return
	 */
	public void setMailCommon(User user, Mail mail, String gubun) {

		String server = "";
		String port = "";
		String ssl = "";

		mail.setUserId(user.getMail());
		mail.setUserPwd(EncryptUtil.decryptText(user.getMailPassword()));
		
		mail.setFromEmail(user.getMail());
		mail.setFromName(user.getUserName());

		Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");

		if (gubun.equals("smtp")) {
			server = prop.getProperty("ikep4.support.mail.smtp_server");
			port = prop.getProperty("ikep4.support.mail.smtp_port");
			ssl = prop.getProperty("ikep4.support.mail.smtp_ssl");
		} else {
			server = prop.getProperty("ikep4.support.mail.imap_server");
			port = prop.getProperty("ikep4.support.mail.imap_port");
			ssl = prop.getProperty("ikep4.support.mail.imap_ssl");
		}

		mail.setServer(server);
		mail.setPort(Integer.parseInt(port));
		if (ssl.equals("Y")) {
			mail.setSsl(true);
		} else {
			mail.setSsl(false);
		}

	}

	/**
	 * 메일 보내기 IMAP 프로토콜을 이용하여 메일을 받음 Sun JavaMail 을 이용함
	 * (http://www.oracle.com/technetwork/java/javamail-1-4-2-141075.html)
	 * 
	 * @param mail @ @
	 */
	@SuppressWarnings("restriction")
	public void connectStore(Mail mail) {

		try {

			Properties props = new Properties();
			props.put("mail.imap.host", mail.getServer());
			props.put("mail.imap.port", mail.getPort());
			props.put("mail.imap.connectiontimeout", MailConstant.IMAP_CONNECTION_TIME);
			props.put("mail.imap.fetchsize", MailConstant.IMAP_FETCH_SIZE);
			props.put("mail.imap.connectionpoolsize",100);
			props.put("mail.imap.connectionpooltimeout",60000);

			// SSL을 사용할 경우
			if (mail.isSsl()) {
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.imap.socketFactory.fallback", "false");
				props.put("mail.imap.socketFactory.port", String.valueOf(mail.getPort()));
			}

			// Session, Store 생성하기
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore("imap");
			store.connect(mail.getUserId(), mail.getUserPwd());
		} catch (Exception e) {
			// e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * Store 닫기 @
	 */
	public void closeStore() {
		try {
			if (store != null && store.isConnected()) {
				store.close();
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * Folder 닫기
	 * 
	 * @param folder @
	 */
	public void closeFolder(Folder folder) {
		try {
			if (folder != null && folder.isOpen()) {
				folder.close(true);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * Folder 열기
	 * 
	 * @param folderName
	 * @param mode
	 * @return @
	 * @throws MailException 
	 */
	public Folder openFolder(String folderName, int mode) throws MailException {
		Folder folder = null;

		try {

			String folderNameStr = folderName;
			Folder defaultFolder = store.getDefaultFolder();
			Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");

			if (folderNameStr == null || folderNameStr.equals("")) {
				folderNameStr = MailConstant.FOLDER_INBOX;
			} else {
				if (folderNameStr.equals(MailConstant.FOLDER_SENT)) {
					folderNameStr = prop.getProperty("ikep4.support.mail.imap_sent");
				}
				if (folderNameStr.equals(MailConstant.FOLDER_INBOX)) {
					folderNameStr = prop.getProperty("ikep4.support.mail.imap_inbox");
				}
			}
				
			folder = defaultFolder.getFolder(folderNameStr);
			folder.open(mode);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MailException("", e);
		}

		return folder;
	}

	/**
	 * Folder 리스트 얻기
	 * 
	 * @return @
	 */
	public List<String> getFolderList() {

		List<String> folderList = new ArrayList<String>();
		try {

			Folder defaultFolder = store.getDefaultFolder();

			Folder[] fdList = defaultFolder.list();
			for (Folder fd : fdList) {
				folderList.add(fd.getName());
			}

		} catch (Exception e) {
			// e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}
		return folderList;

	}

	/**
	 * Store 얻기
	 * 
	 * @return
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * 기본 Folder 얻기
	 * 
	 * @return @
	 */
	public Folder getDefaultFolder() {
		Folder folder = null;
		try {
			folder = store.getDefaultFolder();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}

		return folder;
	}

	/**
	 * Message Content 파싱하기 메일 목록을 얻어올 경우는, body=false 로 설정하며, 메시지의 Content
	 * 파싱과정을 생략한다. 메일 상세를 얻어올 경우는, body=true 로 설정하며, 메시지의 Content 를 파싱하여, 메시지의
	 * 상세 내용을 얻어온다.
	 * 
	 * @param message
	 * @param folderName
	 * @param body
	 * @param parentPath
	 * @return @
	 */
	public Mail getMessageDatail(Message message, String folderName, boolean body, String parentPath) {

		Mail mail = new Mail();

		try {

			if (body) {

				Part textPart = null;

				if (message.isMimeType(MailConstant.MAIL_MULTIPART)) {

					Multipart multipart = (Multipart) message.getContent();

					// 같은내용의 본문을 여러형식으로 보낸것 (text/plain + text/html 등)
					if (message.isMimeType(MailConstant.MAIL_MULTIPART_ALTERNATIVE)) {
						textPart = MailUtil.getTextBodyPart(multipart);
					}
					// multipart/mixed(등).
					else {
						mail = getMessageDatail(multipart, folderName, parentPath);
					}
				} else {
					textPart = (Part) message;
				}

				// multipart가 아니거나, multipart/alternative인 경우.
				if (textPart != null) {

					mail.setContentMimeType(MailUtil.getContentMimeType(textPart));
					mail.setContent((String) textPart.getContent());

					textPart = null;
				}

				MailUtil.getTo(message, mail);

			}

			// Index 0 : 이름, Index 1 : 이메일 주소
			String[] from = MailUtil.getFrom(message);

			mail.setMailId(Integer.valueOf(message.getMessageNumber()));
			mail.setFromName(from[0]);
			mail.setFromEmail(from[1]);

			// 헤더에서 제목을 가져온다. getSubject는 디코딩되므로 사용하지 않는다.
			// JavaMailUtil.decodeText는 비표준 헤더를 최대한 디코딩 하기 위해 사용한다.
			String subject = MailUtil.decodeText(MailUtil.getHeaderFirst(message, "Subject"),
					MailUtil.getContentCharset(message));
			mail.setTitle(subject);

			mail.setSendDate(message.getSentDate());
			mail.setReceivedDate(message.getReceivedDate());
			mail.setSize(message.getSize());
			mail.setFolderName(folderName);

			MailUtil.setFlagFromMessage(mail, message);

			subject = null;
			from = null;
		} catch (Exception e) {
			 e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}

		return mail;

	}

	/**
	 * Message Content 파싱하기 JavaMail의 Multipart를 MessageBean으로 변환하여 반환한다.<br />
	 * 메세지의 Header정보는 외부에서 따로 저장해야한다. <br />
	 * 주의: 재귀함수가 호출되는 부분이 있다. (MESSAGE/RFC822가 있을경우)
	 * 
	 * @param multipart
	 * @param folderName
	 * @param parentPath
	 * @return @
	 */
	public Mail getMessageDatail(Multipart multipart, String folderName, String parentPath) {

		Mail mail = new Mail();

		try {

			// 본문으로 사용될 Part
			BodyPart textPart = null;

			for (int i = 0; i < multipart.getCount(); i++) {

				// 자기 자신의 path를 저장한다. 첨부파일용
				String path = parentPath;
				if (MailUtil.isEmpty(path)) {
					path = "";
				} else {
					path += "|";
				}
				path += String.valueOf(i);

				boolean attach = false;

				BodyPart part = multipart.getBodyPart(i);

				// Disposition이 INLINE or ATTACHMENT이면 첨부파일이다.
				// 현재는 Text또는 HTML에서만 확인한다.
				String dispo = part.getDisposition();
				if (!MailUtil.isEmpty(dispo)
						&& (dispo.equalsIgnoreCase(Part.INLINE) || dispo.equalsIgnoreCase(Part.ATTACHMENT))) {
					attach = true;
				}

				if (part.isMimeType(MailConstant.MAIL_MULTIPART_ALTERNATIVE)) {
					textPart = MailUtil.getTextBodyPart((Multipart) part.getContent());
				}
				// 이 두가지를 모두 보낼땐 multipart/alternative로 보내는게 일반적이므로 or 체크를 해도
				// 된다.
				// 상단에서 Dispose등을 확인하여 첨부파일이 아닌 경우에만 textPart로 지정한다.
				else if (!attach
						&& (part.isMimeType(MailConstant.MAIL_TEXT_PLAIN) || part
								.isMimeType(MailConstant.MAIL_TEXT_HTML))) {
					textPart = part;
				}
				// RFC822. "전달" 기능에 주로 사용된다. 서브메세지 정도의 의미. 기본 헤더와 틀은 다 갖추고 있다.
				else if (part.isMimeType(MailConstant.MAIL_MESSAGE_RFC822)) {

					Object content = part.getContent();
					if (content instanceof Message) {

						// 재귀함수를 호출한다. 최대한 조심하자.
						Mail rfcmail = getMessageDatail((Message) content, folderName, true, path);
						if (rfcmail != null) {
							mail.addChildMailList(rfcmail);
						}
					}

					content = null;
				}
				// 나머지는 전부 첨부파일로 간주한다.
				else {

					MailAttach attachVO = MailUtil.getMessageAttach(part, path);
					mail.addAttachList(attachVO);
				}

				part = null;
			}

			// multipart/alternative, text/plain, text/html 셋중 하나인 경우
			if (textPart != null) {

				mail.setContentMimeType(MailUtil.getContentMimeType(textPart));
				mail.setContent((String) textPart.getContent());

				textPart = null;
			}
		} catch (Exception e) {
			 e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}

		return mail;
	}

	/**
	 * 첨부파일 다운로드 처리
	 * 
	 * @param message
	 * @param mpartPath
	 * @param outputStream
	 * @param response @
	 */
	public void attachPutOutputStream(Message message, String[] mpartPath, OutputStream outputStream,
			HttpServletResponse response) {

		try {

			BodyPart part = null;
			if (message.isMimeType(MailConstant.MAIL_MULTIPART)) {

				Multipart multipart = (Multipart) message.getContent();
				part = MailUtil.getBodyPart(multipart, mpartPath);

				multipart = null;
			}

			response.reset();

			/*
			 * attachBean.getSize로 구한것은 인코딩에 따라 용량이 달라진다. 파일로 먼저 저장한뒤에 다운로드 시키지
			 * 않는한 방법이 없는듯하다. 그러 파일로 저장한다면 IMAP에서 가져와 파일로 저장하고, 그걸 다시 클라이언트로 보내
			 * 비용이 발생한다.
			 */
			// response.setContentLength(attachBean.getSize());
			response.setContentType(MailUtil.getContentMimeType(part));
			String orgFileName = URLEncoder.encode(MailUtil.decodeText(part.getFileName(), MailUtil.getContentCharset(part)), "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + orgFileName + ";");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			BufferedInputStream bis = new BufferedInputStream(part.getInputStream());
			BufferedOutputStream bos = null;

			byte[] buff = new byte[2 * 1024];
			int readCount = 0;

			try {

				bos = new BufferedOutputStream(outputStream);

				while ((readCount = bis.read(buff)) != -1) {
					bos.write(buff, 0, readCount);
					bos.flush();
				}
			} finally {
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}
	}

}

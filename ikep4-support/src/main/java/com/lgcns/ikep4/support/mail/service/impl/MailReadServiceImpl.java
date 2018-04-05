package com.lgcns.ikep4.support.mail.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.UIDFolder;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.base.MailException;
import com.lgcns.ikep4.support.mail.base.MailReadBase;
import com.lgcns.ikep4.support.mail.base.MailUtil;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.user.member.model.User;
//import com.sun.mail.imap.IMAPMessage;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 메일받기 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailReadServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service("mailReadService")
public class MailReadServiceImpl extends MailReadBase implements MailReadService {

	@Autowired
	private FileService fileService;
	/**
	 * 메일 리스트 조회(목록갯수 설정 가능)
	 */
	public List<Mail> getMailList(String folderName, User user, int size) {

		List<Mail> mailList = new ArrayList<Mail>();

		try {

			Mail mail = new Mail();

			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");

			// Store 열기
			connectStore(mail);

			// Folder 열기
			Folder folder = openFolder(folderName, Folder.READ_ONLY);

			// Message 리스트 가져오기
			// SearchTerm searchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN),
			// true);
			Message[] messages = folder.getMessages();

			// Message 리스트를 파싱하여, MailVO 리스트에 담기
			int j = 0;
			for (int i = messages.length - 1; i > -1 && size > j; i--) {

				// Message 정보 파싱 및 담기
				// 파라미터 중에서 body = false 로하여, 메시지 목록만 파싱함
				Message message = messages[i];
				Mail mailInfo = getMessageDatail(message, folderName, false, null);
				mailList.add(mailInfo);
				j++;
			}

			// Folder, Store 닫기
			closeFolder(folder);
			closeStore();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mailList;
	}
	/**
	 * 메일 리스트 건수 조회(검색일자 설정 가능)
	 * @throws MailException 
	 * @throws MessagingException 
	 */
	public Integer getMailListNum(String folderName, User user, Date fromDate, Date toDate) throws MailException, MessagingException  {
		Mail mail = new Mail();
		setMailCommon(user, mail, "imap");
		connectStore(mail);
		Folder folder = openFolder(folderName, Folder.READ_ONLY);
		SearchTerm toTerm = new ReceivedDateTerm(ComparisonTerm.LT, toDate);
		SearchTerm fromTerm = new ReceivedDateTerm(ComparisonTerm.GT, fromDate);
		SearchTerm andTerm = new AndTerm(toTerm, fromTerm);
		Message[] messages = folder.search(andTerm);
		return messages.length;
	}

	/**
	 * 메일 리스트 조회(검색일자 설정 가능)
	 */
	public List<Mail> getMailList(String folderName, User user, Date fromDate, Date toDate, int size) throws MailException, MessagingException {

		List<Mail> mailList = new ArrayList<Mail>();

		Mail mail = new Mail();
		// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
		setMailCommon(user, mail, "imap");
		// Store 열기
		connectStore(mail);
		// Folder 열기
		Folder folder = openFolder(folderName, Folder.READ_ONLY);
		UIDFolder ufolder = (UIDFolder)folder;
		SearchTerm toTerm = new ReceivedDateTerm(ComparisonTerm.LT, toDate);
		SearchTerm fromTerm = new ReceivedDateTerm(ComparisonTerm.GT, fromDate);
		SearchTerm andTerm = new AndTerm(toTerm, fromTerm);
		// Message 리스트 가져오기
		Message[] messages = folder.search(andTerm);
		int j = 0;
		// Message 리스트를 파싱하여, MailVO 리스트에 담기
		if (size == 0) { size = messages.length + 1;}
		for (int i = messages.length - 1; i > -1 && size > j; i--) {
			// Message 정보 파싱 및 담기
			// 파라미터 중에서 body = false 로하여, 메시지 목록만 파싱함
			Message message = messages[i];
			// ((IMAPMessage) message).setPeek(true);
			Mail mailInfo = getMessageDatail(message, folderName, true, null);
			if (fromDate.before(mailInfo.getReceivedDate())) {
				mailInfo.setMessageId(String.valueOf(ufolder.getUID(message)));
				mailList.add(mailInfo);
				j++;
			}
		}
		// Folder, Store 닫기
		closeFolder(folder);
		closeStore();
		return mailList;
	}

	/**
	 * 메일 정보 얻기
	 */
	public Mail getMailInfo(String folderName, String mailId, User user) {
		Mail mailInfo = new Mail();

		try {
			Mail mail = new Mail();

			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");

			connectStore(mail);
			Folder folder = openFolder(folderName, Folder.READ_WRITE);

			Message message = folder.getMessage(Integer.parseInt(mailId));

			if (message != null) {
				// 파라미터 중에서 body = true 로하여, 메시지 상세정보를 모두 파싱함
				mailInfo = getMessageDatail(message, message.getFileName(), true, null);
			}

			message.setFlag(Flags.Flag.SEEN, true);

			closeFolder(folder);
			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mailInfo;
	}

	/**
	 * 신규 메일 갯수
	 */
	public int getNewMailCount(String folderName, User user) {

		int cnt = 0;

		try {
			Mail mail = new Mail();

			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");

			connectStore(mail);
			Folder folder = openFolder(folderName, Folder.READ_ONLY);

			SearchTerm andTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

			// Message 리스트 가져오기
			Message[] messages = folder.search(andTerm);

			cnt = messages.length;

			/*
			 * for (Message message : messages) { Mail mailInfo = new Mail();
			 * MailUtil.setFlagFromMessage(mailInfo, message); // read==false
			 * 인경우, 신규 메시지임 if (mailInfo.getReadYn().equals("N")) cnt++; }
			 */

			closeFolder(folder);
			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return cnt;
	}
	
	/**
	 * 신규 메일 갯수
	 */
	public int getNewMailCountFromTerrace(String folderName, User user)
	{
		InputStream is = null;
        InputStreamReader ir = null;
        BufferedReader in = null;
        
        int result = 0;
        
        if(!StringUtil.isEmpty(user.getMail())){
     
			try {	
				StringBuffer uurl = new StringBuffer();
				//기본 주소
				Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
				
				uurl.append(prop.getProperty("ikep4.support.mail.externalserver"));
				uurl.append("/portlet/mailInfoApi.jsp?type=json&email=");
				//사용자 메일 주소
				uurl.append(user.getMail());
				//검색 위치
				uurl.append("&folder=");
				uurl.append(folderName);		
				URL url;		
				url = new URL(uurl.toString());
				//System.out.println("###################################################:"+uurl.toString());
			    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			    connection.setConnectTimeout(3000);
			    connection.setReadTimeout(3000);
	             is = connection.getInputStream();
	             ir = new InputStreamReader(is, "UTF-8"); 
	             in = new BufferedReader(ir);
	             
	             String jsonString = null;
	             String inputLine = null;
	             while ((inputLine = in.readLine()) != null) {
	             
	            	 if (jsonString == null) 
	            		 jsonString = inputLine;
	            	 else jsonString += inputLine;
	             } 
	             
	            // jsonString = "{'token': [{ 'folder_name' : 'inbox', 'folder_name_utf8': 'inbox', 'total_count':'5', 'new_count':'2', 'unseen_count':'3'} ]}";
	             
	             JSONObject tokenObj = new JSONObject(jsonString); 
	
	             JSONArray tokenArray = tokenObj.getJSONArray("token");
	             
	             for(int i = 0; i < tokenArray.length() ;i++)
	             {
	            	 if(tokenArray.getJSONObject(i).has("unseen_count"))
	            	 {            		 
	            		 result = tokenArray.getJSONObject(i).getInt("unseen_count"); 
	            		 break;
	            	 }
	             }        
	         
	     } catch ( Exception e) {
	         e.printStackTrace();
	     } finally {
	         try {
	             if ( is!=null ) is.close();
	             if ( ir!=null ) ir.close();
	             if ( in!=null ) in.close();
	         } catch ( Exception e ) {}
	     }
     }
     return result;		
	}

	/**
	 * 메일 첨부 파일 다운로드
	 */
	public String downloadMailAttach(String idType, String folderName, String mailId, HttpServletResponse response,
			String multipartPath, User user) {

		Mail mail = new Mail();

		try {
			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");
			connectStore(mail);
			Folder folder = null;
			Message message = null;
			folder = openFolder(folderName, Folder.READ_ONLY);
			if (idType.equals("uid")) {
				UIDFolder ufolder = (UIDFolder)folder;
				message = ufolder.getMessageByUID(Long.parseLong(mailId));
			} else {
				message = folder.getMessage(Integer.parseInt(mailId));
			}

			// MessageAttachBean에 저장된 해당 BodyPart의 경로를 "|"로 잘라서 배열에 넣는다.
			String[] pathArray = multipartPath.split("[|]");

			// multipartPath 이용하여, 해당 메시지에서, 첨부파일 정보를 찾아서
			// response.getOutputStream() 으로 다운로드 시킴
			attachPutOutputStream(message, pathArray, response.getOutputStream(), response);

			closeFolder(folder);
			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}
	/**
	 * 메일 첨부파일 서버로 저장 
	 */
	public FileData copyMailAttach(String folderName, String fileName, String mailId, String multipartPath, User user) {
		InputStream attachmentInputStream = null;
		Mail mail = new Mail();
		FileData fileData = new FileData();
		try {
			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");
			connectStore(mail);
			Folder folder = null;
			Message message = null;
			folder = openFolder(folderName, Folder.READ_ONLY);
			UIDFolder ufolder = (UIDFolder)folder;
			message = ufolder.getMessageByUID(Long.parseLong(mailId));

			// MessageAttachBean에 저장된 해당 BodyPart의 경로를 "|"로 잘라서 배열에 넣는다.
			String[] pathArray = multipartPath.split("[|]");
			
			BodyPart part = null;
			if (message.isMimeType(MailConstant.MAIL_MULTIPART)) {

				Multipart multipart = (Multipart) message.getContent();
				part = MailUtil.getBodyPart(multipart, pathArray);

				multipart = null;
			}
			attachmentInputStream = part.getInputStream();
			fileData = this.fileService.uploadFile(fileName, attachmentInputStream);

			closeFolder(folder);
			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		finally {
			try {
				if (attachmentInputStream != null)
					attachmentInputStream.close();
			} catch (Exception e) {
			}
		}
		return fileData;
	}

	/**
	 * 메일 폴더 리스트 조회
	 */
	public List<String> getFolderList(User user) {

		List<String> folderList = new ArrayList<String>();

		try {
			Mail mail = new Mail();

			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");

			connectStore(mail);

			// 해당 메일계정의 모든 폴더목록을 가져옴
			folderList = getFolderList();

			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return folderList;

	}

	/**
	 * 메일 읽은 Flag 처리
	 */
	public String changeRead(String folderName, String mailId, User user) {
		Mail mail = new Mail();
		try {
			setMailCommon(user, mail, "imap");
			connectStore(mail);
			Folder folder = openFolder(folderName, Folder.READ_WRITE);
			UIDFolder ufolder = (UIDFolder)folder;
			Message message = ufolder.getMessageByUID(Long.parseLong(mailId));
			message.setFlag(Flags.Flag.SEEN, true);
			closeFolder(folder);
			closeStore();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}
	
	/**
	 * 메일 삭제
	 */
	public String deleteMail(String idType, String folderName, String mailId, User user) {

		List<String> mailIdList = new ArrayList<String>();

		mailIdList.add(mailId);

		deleteMail(idType, folderName, mailIdList, user);

		return "ok";
	}

	/**
	 * 메일 삭제
	 */
	public String deleteMail(String idType, String folderName, List<String> mailIdList, User user) {

		Mail mail = new Mail();

		try {
			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");

			connectStore(mail);
			Folder folder = openFolder(folderName, Folder.READ_WRITE);
			if (idType.equals("uid")) {
				UIDFolder ufolder = (UIDFolder)folder;
				for (String mailId : mailIdList) {
					Message message = ufolder.getMessageByUID(Long.parseLong(mailId));
					message.setFlag(Flags.Flag.DELETED, true);
				}
				
			} else {
				// 메일 Flags.Flag.DELETED 을 설정하여 삭제함
				for (String mailId : mailIdList) {
					Message message = folder.getMessage(Integer.parseInt(mailId));
					message.setFlag(Flags.Flag.DELETED, true);
				}
			}

			closeFolder(folder);
			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}

	/**
	 * 메일 이동
	 */
	public String moveMail(String folderName, String mailId, String targetFolderName, User user) {

		List<String> mailIdList = new ArrayList<String>();
		mailIdList.add(mailId);

		moveMail(folderName, mailIdList, targetFolderName, user);

		return "ok";
	}

	/**
	 * 메일 이동
	 */
	public String moveMail(String folderName, List<String> mailIdList, String targetFolderName, User user) {

		Mail mail = new Mail();

		try {
			// 메일 초기 정보 셋팅(보내는사람 계정, 메일 서버 주소 등등)
			setMailCommon(user, mail, "imap");

			connectStore(mail);
			Folder folder = openFolder(folderName, Folder.READ_WRITE);

			Message[] messages = new Message[mailIdList.size()];

			int i = 0;
			// 메일 Flags.Flag.DELETED 을 설정하여 삭제함
			for (String mailId : mailIdList) {
				Message message = folder.getMessage(Integer.parseInt(mailId));
				message.setFlag(Flags.Flag.DELETED, true);
				messages[i] = message;
			}

			// 원하는 폴더로 Message 를 복사함
			Folder defaultFolder = getDefaultFolder();
			Folder targetFolder = defaultFolder.getFolder(targetFolderName);

			folder.copyMessages(messages, targetFolder);

			closeFolder(folder);
			closeStore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}
	@SuppressWarnings("deprecation")
	public List<Mail> getRecentMailInfo(User user, int nSize) {
		
		List<Mail> mailList = new ArrayList<Mail>();
		
		InputStream is = null;
        InputStreamReader ir = null;
        BufferedReader in = null;
    
		try {	
			StringBuffer uurl = new StringBuffer();
			//기본 주소
			Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
			
			uurl.append(prop.getProperty("ikep4.support.mail.externalserver"));
						
			uurl.append("/mailPortletList.do?mode=list&vtype=json&d=1&sType=Y&ssoParam=mailUid=");
			//사용자ID
			uurl.append(user.getUserId());			
			uurl.append(",domain=");			
			uurl.append(prop.getProperty("ikep4.support.mail.domain"));
			uurl.append("&fn=all");
			uurl.append("&cnt=");
			uurl.append(Integer.toString(nSize));
			uurl.append("&flag=U");

			URL url;		
			url = new URL(uurl.toString());
			
			//System.out.println("###uurl :" + uurl.toString()); 
					
		    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		    
		    connection.connect();	
		    
            is = connection.getInputStream();
            ir = new InputStreamReader(is, "UTF-8"); 
            in = new BufferedReader(ir);
             
            String jsonString = null;
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {             
            	 if (jsonString == null) 
            		 jsonString = inputLine;
            	 else jsonString += inputLine;
            }
            
            //System.out.println("####jsonString:" + jsonString);

            JSONObject mailObject = new JSONObject(jsonString).getJSONObject("mailbox");
            
            //데이터가 정상적으로 수신되었으면...
            if("success".equals(mailObject.getString("actionResult").toLowerCase()))
            {
            	JSONArray messageList = mailObject.getJSONObject("messageList").getJSONArray("message");
            	
            	if(messageList.length() > 0)
            	{
            		//날짜 변환용 포맷
            		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            		
            		//for(int i=messageList.length() - 1; i >= 0; i-- )
            		for(int i=0; i<messageList.length(); i++ )
            		{
            			Mail mail = new Mail();
            			
            			JSONObject message = messageList.getJSONObject(i);
            			
            			if(message.has("subject")){
            				if(message.getString("subject").equals("")){
            					mail.setTitle("제목없음");
            				}else{
            					mail.setTitle(message.getString("subject"));
            				}
            			}
            			if(message.has("msgUid"))            				
            			   mail.setMailId(Integer.parseInt(message.getString("msgUid")));
            			
            			if(message.has("personEmail"))
            				mail.setFromEmail(message.getString("personEmail"));
            			
            			if(message.has("personName"))
            				mail.setFromName(message.getString("personName"));
            			
            			if(message.has("sentDate")){              				
            				Date date = format.parse(message.getString("sentDate"));
            				mail.setSendDate(date);
            			}            
            			
            			if(message.has("folder"))
            				mail.setFolderName(message.getString("folder"));
   	     				mailList.add(mail);
            			
            		}
            		
    	            //JSONArray tokenArray = tokenObj.getJSONArray("mailbox");
    	
    	     			/* Message 리스트 가져오기
    	     			// SearchTerm searchTerm = new FlagTerm(new Flags(Flags.Flag.SEEN),
    	     			// true);
    	     			Message[] messages = folder.getMessages();
    	
    	     			// Message 리스트를 파싱하여, MailVO 리스트에 담기
    	     			int j = 0;
    	     			for (int i = messages.length - 1; i > -1 && size > j; i--) {
    	
    	     				// Message 정보 파싱 및 담기
    	     				// 파라미터 중에서 body = false 로하여, 메시지 목록만 파싱함
    	     				Message message = messages[i];
    	     				Mail mailInfo = getMessageDatail(message, folderName, false, null);
    	     				mailList.add(mailInfo);
    	     				j++;
    	     			}*/
            		
            	}
            	            	
            	
                        
	            
	                 
            }     
	     } catch ( Exception e) {
	         e.printStackTrace();
	     } finally {
	         try {
	             if ( is!=null ) is.close();
	             if ( ir!=null ) ir.close();
	             if ( in!=null ) in.close();
	         } catch ( Exception e ) {
	        	 throw new IKEP4ApplicationException("", e);
	         }
	     }

		return mailList;
	}

}

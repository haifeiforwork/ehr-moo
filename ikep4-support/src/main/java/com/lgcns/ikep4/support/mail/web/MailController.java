package com.lgcns.ikep4.support.mail.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.base.MailUtil;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.model.MailAttach;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 메일 보내기 받기 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailController.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Controller
@RequestMapping(value = "/support/mail")
public class MailController extends BaseController {

	/**
	 * 메일 전송 서비스
	 */
	@Autowired
	private MailSendService mailSendService;

	/**
	 * 메일 수신 서비스
	 */
	@Autowired
	private MailReadService mailReadService;

	/**
	 * 파일 서비스
	 */
	@Autowired
	private FileService fileService;

	/**
	 * 메일 보내기 팝업 화면
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/sendMailForm")
	public ModelAndView sendMailForm() {

		ModelAndView mav = new ModelAndView("support/mail/sendMailForm");

		Mail mail = new Mail();

		mav.addObject("mail", mail);

		return mav;
	}

	/**
	 * 메일 보내기 (일반메일 보내기, 템플릿 메일 보내기 기능)
	 * 
	 * @param mail
	 * @param model
	 * @param request
	 * @return @
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sendMail.do")
	public ModelAndView sendMail(@ValidEx Mail mail, BindingResult result, HttpServletRequest request,
			SessionStatus status) {

		ModelAndView mav = new ModelAndView("support/mail/sendMailEnd");

		try {

			if (result.hasErrors()) {
				return new ModelAndView("support/mail/sendMailForm");
			}

			User user = (User) getRequestAttribute("ikep.user");

			// 수신자/참조자/비밀참조자 셋팅
			MailUtil.getMailAddrForList(mail);

			// 메일 형식(Text, Html, Template)
			mail.setMailType(MailConstant.MAIL_TYPE_HTML);

			// 첨부원본내용이 있으면, 내용+첨부원본을 셋팅
			if (!StringUtil.isEmpty(mail.getContentOriginal())) {
				mail.setContent(mail.getContent() + MailUtil.setAttahContent(mail.getContentOriginal(), request));
			}
			
			List<MailAttach> mailAttachList = new ArrayList<MailAttach>();
			
			// 파일 첨부가 있을 경우(플레쉬 업로드 컨트롤러에서 파일 첨부가 있을경우)
			if (mail.getFileLinkList() != null) {
				
				Properties fileprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
				String uploadRootForFile = fileprop.getProperty("ikep4.support.fileupload.upload_root");
				String uploadRootForImage = fileprop.getProperty("ikep4.support.fileupload.upload_root_image");
				
				String uploadRoot = "";
				for (FileLink fileLink : mail.getFileLinkList()) {

					try {
						
						FileData fileData = fileService.read(fileLink.getFileId());
						
						if (checkImageFile(fileData.getFileRealName())) {
							uploadRoot = uploadRootForImage;
						} else {
							uploadRoot = uploadRootForFile;
						}
						
						MailAttach mailAttach = new MailAttach();
						mailAttach.setName(fileData.getFileRealName());
						mailAttach.setPath(uploadRoot + fileData.getFilePath() + File.separatorChar
								+ fileData.getFileName());
						mailAttachList.add(mailAttach);
					} catch (Exception e) {
						// ex.printStackTrace();
					}

				}

				mail.setAttachList(mailAttachList);

			}

			// 파일 첨부가 있을 경우(본문내용 자동첨부할때, 파일첨부가 있을경우)
			if (mail.getFileDataList() != null) {

				Properties fileprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
				String uploadRootForFile = fileprop.getProperty("ikep4.support.fileupload.upload_root");
				String uploadRootForImage = fileprop.getProperty("ikep4.support.fileupload.upload_root_image");
				
				String uploadRoot = "";
				for (FileData file : mail.getFileDataList()) {

					try {
						FileData fileData = fileService.read(file.getFileId());
						
						if (checkImageFile(fileData.getFileRealName())) {
							uploadRoot = uploadRootForImage;
						} else {
							uploadRoot = uploadRootForFile;
						}
						
						MailAttach mailAttach = new MailAttach();
						mailAttach.setName(fileData.getFileRealName());
						mailAttach.setPath(uploadRoot + fileData.getFilePath() + File.separatorChar
								+ fileData.getFileName());
						mailAttachList.add(mailAttach);
					} catch (Exception e) {
						// ex.printStackTrace();
					}
				}

				mail.setAttachList(mailAttachList);
			}

			// 템플릿 메일인 경우, 치환을 위한 파라미터 셋팅
			Map dataMap = new HashMap();

			// 템플릿 메일인 경우 아래와 같이 코딩함
			/*
			 * dataMap.put("mail", mail); dataMap.put("userName", "김연아");
			 * mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			 * mail.setMailTemplatePath("testMail.vm");
			 */

			// 메일 보내기
			String reStr = mailSendService.sendMail(mail, dataMap, user);

			mav.addObject("reStr", reStr);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mav;

	}

	/**
	 * 메일 목록 조회
	 * 
	 * @param folderName
	 * @param model
	 * @return @
	 */
	@RequestMapping(value = "/listMail.do")
	public ModelAndView listMail(@RequestParam(value = "folderName", required = false) String folderName,
			ModelMap model, String size) {

		ModelAndView mav = new ModelAndView("/support/mail/mailList");

		try {

			User user = (User) getRequestAttribute("ikep.user");
			
			String mailSize = size;
			if (StringUtil.isEmpty(mailSize)) {
				mailSize = "10";
			}

			List<Mail> mailList = mailReadService.getMailList(folderName, user, Integer.parseInt(mailSize));

			mav.addObject("mailList", mailList);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mav;

	}

	/**
	 * 메일 정보 읽기
	 * 
	 * @param folderName
	 * @param mailId
	 * @param model
	 * @return @
	 */
	@RequestMapping(value = "/viewMail.do")
	public ModelAndView viewMail(@RequestParam(value = "folderName", required = false) String folderName,
			@RequestParam(value = "mailId", required = false) String mailId, ModelMap model) {

		ModelAndView mav = new ModelAndView("support/mail/mailView");

		try {

			User user = (User) getRequestAttribute("ikep.user");

			Mail mailInfo = mailReadService.getMailInfo(folderName, mailId, user);

			mav.addObject("mail", mailInfo);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mav;

	}

	/**
	 * 메일 첨부파일 다운로드
	 * 
	 * @param folderName
	 * @param mailId
	 * @param multipartPath
	 * @param model
	 * @param response
	 * @return @
	 */
	@RequestMapping(value = "/downloadMailAttach.do")
	public String downloadMailAttach(@RequestParam(value = "folderName", required = false) String folderName,
			@RequestParam(value = "mailId", required = false) String mailId,
			@RequestParam(value = "multipartPath", required = false) String multipartPath, ModelMap model,
			HttpServletResponse response) {

		try {

			User user = (User) getRequestAttribute("ikep.user");

			mailReadService.downloadMailAttach(null, folderName, mailId, response, multipartPath, user);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "";

	}

	/**
	 * 메일 삭제
	 * 
	 * @param folderName
	 * @param mailId
	 * @param model
	 * @return @
	 */
	@RequestMapping(value = "/deleteMail.do")
	public ModelAndView deleteMail(@RequestParam(value = "folderName", required = false) String folderName,
			@RequestParam(value = "mailId", required = false) String mailId, ModelMap model) {

		ModelAndView mav = new ModelAndView("/support/mail/mailList");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			mailReadService.deleteMail(null, folderName, mailId, user);

			// List<Mail> mailList = mailReadService.getMailList(folderName,
			// user);

			// mav.addObject("mailList", mailList);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mav;

	}

	/**
	 * 메일 이동
	 * 
	 * @param folderName
	 * @param mailId
	 * @param targetFolderName
	 * @param model
	 * @return @
	 */
	@RequestMapping(value = "/moveMail.do")
	public ModelAndView moveMail(@RequestParam(value = "folderName", required = false) String folderName,
			@RequestParam(value = "mailId", required = false) String mailId,
			@RequestParam(value = "targetFolderName", required = false) String targetFolderName, ModelMap model) {

		ModelAndView mav = new ModelAndView("/support/mail/mailList");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			mailReadService.moveMail(folderName, mailId, targetFolderName, user);

			// List<Mail> mailList = mailReadService.getMailList(folderName,
			// user);

			// mav.addObject("mailList", mailList);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return mav;

	}

	/**
	 * 메일 보내기 테스트
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/sendMailTest")
	public String uploadTest(ModelMap model) {

		return "support/mail/sendMailTest";
	}
	
	private boolean checkImageFile(String fileName) {

		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String keywordList = prop.getProperty("ikep4.support.fileupload.image_file");

		Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}
}

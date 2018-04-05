/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.workflow.engine.model.DatafieldBean;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.WorkformService;
import com.lgcns.ikep4.workflow.engine.util.XmlService;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: WorkformController.java 16245 2011-08-18 04:28:59Z giljae $ WorkformController.java 오전 9:54:29
 */
@Controller
public class WorkformController {
	
	Log log = LogFactory.getLog(WorkformController.class);
	
	@Autowired
	private WorkformService workformService;
	
	@Autowired
	private InstanceService instanceService;	

	//********************************************************************************************
	/**
	 * 
	 */
	@RequestMapping(value = "workflow/workform.do", method = RequestMethod.GET)
	public ModelAndView workformGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processId			= request.getParameter("processId") == null ? "" : request.getParameter("processId").toString();
		String processVer			= request.getParameter("processVer") == null ? "" : request.getParameter("processVer").toString();
		String processInsId			= request.getParameter("processInsId") == null ? "" : request.getParameter("processInsId").toString();
		String activityId			= request.getParameter("activityId") == null ? "" : request.getParameter("activityId").toString();
		String insLogId				= request.getParameter("insLogId") == null ? "" : request.getParameter("insLogId").toString();
		String userId				= request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
		String type					= request.getParameter("type") == null ? "" : request.getParameter("type").toString();
		User user 					= (User)request.getSession().getAttribute("ikep.user");
		log.info("# type : " + type);
		if(type.equalsIgnoreCase("start")) {
			HashMap<String, Object> paramHsh		= new HashMap();
			//DateUtil dataUtil		= new DateUtil();
			//String todayDateStr		= dataUtil.getToday("yyyyMMdd") + dataUtil.getTodayDateTime("HHmmss");
			String todayDateStr		= DateUtil.getToday("yyyyMMdd") + DateUtil.getTodayDateTime("HHmmss");	
			
			Date todayDate			= new Date();
			InstanceBean instanceBean	= instanceService.startProcess(processId, processVer, (processId + " 프로세스(" + todayDate +")"), user.getUserId(), paramHsh, todayDate);
			processId				= instanceBean.getProcessId();
			processInsId			= instanceBean.getInstanceId();
			activityId				= instanceBean.getActivityId();
			insLogId				= instanceBean.getInstanceLogId();
		}
		
		WorkItemBean workItemBean	= workformService.getWorkform(processId, activityId, processInsId, insLogId);
		
		ModelAndView mav = new ModelAndView("workflow/workplace/workform");
		mav.addObject("processId", workItemBean.getProcessId());
		mav.addObject("processName", workItemBean.getProcessName());
		mav.addObject("activityId", workItemBean.getActivityId());
		mav.addObject("activityName", workItemBean.getActivityName());
		mav.addObject("instanceId", workItemBean.getInstanceId());
		mav.addObject("logId", workItemBean.getInsLogId());		
		mav.addObject("title", workItemBean.getTitle());	
		mav.addObject("user", user);
		
		
		String varDefineXml		= workItemBean.getVarDefine();
		String varSchemaXml		= workItemBean.getVarSchema();
		
		
		
//		log.info("# 시작 : xmlServiceDef : " + varDefineXml);
		XmlService xmlServiceDef	= new XmlService();
		xmlServiceDef.loadingXML(varDefineXml);		
		
//		log.info("# 시작 : xmlServiceVar : " + varSchemaXml);
		XmlService xmlServiceVar	= new XmlService();
		xmlServiceVar.loadingXML(varSchemaXml);		
		
		String inputParamStream		= "<table border=\"0\" width=\"100%\">";
		
		ArrayList inputDataList			= new ArrayList();
		
		NodeList inputNodeList	= xmlServiceDef.getNodeList("DataSet/InputSet/Input");
		for(int i=0;i<inputNodeList.getLength();i++) {
			Node inputNode		= inputNodeList.item(i);
			String nodeId		= xmlServiceDef.getAttribute(inputNode, "Id");
			String nodeName		= xmlServiceDef.getAttribute(inputNode, "Name");
			Node selectNode		= xmlServiceVar.getSingleNode("DataFields/DataField[@Id='" + nodeId + "']");
			String nodeValue	= xmlServiceVar.getAttrValue(selectNode, "CurrentValue");
			String isArray		= xmlServiceDef.getAttribute(inputNode, "IsArray");
			String useIndex		= xmlServiceDef.getAttribute(inputNode, "UseIndex");
			
			inputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "</td><td width=\"50%\"><input type=\"text\" name=\"in_" + nodeId + "\" value=\"\" style=\"background-color:#eeeeee;width:100%\" readOnly/></td></tr>";
			
			DatafieldBean intputBean	= new DatafieldBean();
			intputBean.setDatafieldId("in_" + nodeId);
			intputBean.setDatafieldName(nodeName);
			intputBean.setDatafieldValue(nodeValue);			
			intputBean.setArrayType(isArray);

			if(isArray.equalsIgnoreCase("TRUE")) {
				NodeList itemNodeList	= xmlServiceVar.getNodeList(selectNode, "CurrentValue/Item");
				for(int j=0;j<itemNodeList.getLength();j++) {
					Node itemNode		= itemNodeList.item(j);
					String itemValue	= xmlServiceVar.getNodeText(itemNode);
					intputBean.addArrayData(itemValue);
				}
			}
			inputDataList.add(intputBean);
			
			// UseIndex 설정으로 인한 Input Key 변수를 추가해 준다. 
			if(useIndex.equalsIgnoreCase("TRUE")) {
				Node selectNodeKey		= xmlServiceVar.getSingleNode("DataFields/DataField[@Id='" + nodeId + "_Key']");
				String nodeValueKey		= xmlServiceVar.getAttrValue(selectNodeKey, "CurrentValue");
				DatafieldBean intputBeanKey	= new DatafieldBean();
				intputBeanKey.setDatafieldId("in_" + nodeId + "_Key");
				intputBeanKey.setDatafieldName(nodeName + "[*]");
				intputBeanKey.setDatafieldValue(nodeValueKey);			
				intputBeanKey.setArrayType("FALSE");				
				intputBeanKey.setUseIndex("FALSE");
				inputDataList.add(intputBeanKey);
			}
		}
		inputParamStream		+= 	"</table>";
		
		String outputParamStream	= "<table border=\"0\" width=\"100%\">";
		String functionVarStream	= "";
		String formVarStream		= "";
		
		ArrayList outputDataList			= new ArrayList();
		
		NodeList outputNodeList	= xmlServiceDef.getNodeList("DataSet/OutputSet/Output");
		for(int i=0;i<outputNodeList.getLength();i++) {
			Node outputNode		= outputNodeList.item(i);
			String nodeId		= xmlServiceDef.getAttribute(outputNode, "Id");
			String nodeName		= xmlServiceDef.getAttribute(outputNode, "Name");
			String isArray		= xmlServiceDef.getAttribute(outputNode, "IsArray");
			String useIndex		= xmlServiceDef.getAttribute(outputNode, "UseIndex");
			
			if(isArray.equalsIgnoreCase("TRUE")) {
				outputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "<input type=\"button\" name=\"add\" value=\"add\" onclick=\"\"/></td><td width=\"50%\"><input type=\"text\" name=\"out_" + nodeId + "\" value=\"\" style=\"width:100%\"/></td></tr>";
			} else {
				outputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "</td><td width=\"50%\"><input type=\"text\" name=\"out_" + nodeId + "\" value=\"\" style=\"width:100%\"/></td></tr>";
			}
			functionVarStream	+=	"		document.getElementById(\"frm_Command\").out_" + nodeId + ".value	= document.getElementById(\"out_" + nodeId + "\").value;   \n";
			
			formVarStream		+= 	"		<input type=\"hidden\" name=\"out_" + nodeId + "\" value=\"\"/>                                                 					\n";
			
			DatafieldBean outputBean	= new DatafieldBean();
			outputBean.setDatafieldId("out_" + nodeId);
			outputBean.setDatafieldName(nodeName);
			outputBean.setArrayType(isArray);
			if(isArray.equalsIgnoreCase("TRUE")) {
				if(xmlServiceVar.isExistNode("DataFields/DataField[@Id='" + nodeId + "']/CurrentValue/Item")) {
					NodeList itemNodeList	= xmlServiceVar.getNodeList("DataFields/DataField[@Id='" + nodeId + "']/CurrentValue/Item");
					for(int j=0;j<itemNodeList.getLength();j++) {
						Node itemNode		= itemNodeList.item(j);
						String itemValue	= xmlServiceVar.getNodeText(itemNode);
						outputBean.addArrayData(itemValue);						
					}
					//outputBean.addArrayData(xmlServiceVar.getAttrValue("DataFields/DataField[@Id='" + nodeId + "']/CurrentValue/Item[@Index='" + i + "']"));
				}
			} else {
				if(xmlServiceVar.isExistNode("DataFields/DataField[@Id='" + nodeId + "']/CurrentValue")) {
					outputBean.setDatafieldValue(xmlServiceVar.getAttrValue("DataFields/DataField[@Id='" + nodeId + "']/CurrentValue"));
				}
			}
			
			outputDataList.add(outputBean);	
			
			// UseIndex 설정으로 인한 Output KEY 변수를 추가해 준다. 
			if(useIndex.equalsIgnoreCase("TRUE")) {
				Node selectNodeKey		= xmlServiceVar.getSingleNode("DataFields/DataField[@Id='" + nodeId + "_Key']");
				String nodeValueKey		= xmlServiceVar.getAttrValue(selectNodeKey, "CurrentValue");
				DatafieldBean outputBeanKey	= new DatafieldBean();
				outputBeanKey.setDatafieldId("out_" + nodeId + "_Key");
				outputBeanKey.setDatafieldName(nodeName + "[*]");
				outputBeanKey.setDatafieldValue(nodeValueKey);			
				outputBeanKey.setArrayType("FALSE");				
				outputBeanKey.setUseIndex("FALSE");
				outputDataList.add(outputBeanKey);
			}			
			
		}	
		outputParamStream		+= 	"</table>";			
	
		
		
		String functionStream	= 	"<script>\n" +
									"	function completeApi(processId, processInsId, activityId, insLogId){							\n" +
									"		document.getElementById(\"frm_Command\").action				= \"" + HttpUtil.getWebAppUrl(request) + "/workflow/workflow.do\"	\n" + 
									"		document.getElementById(\"frm_Command\").command.value		= \"completeWorkItem\";			\n" + 
//									"		document.getElementById(\"frm_Command\").processId.value	= processId;            		\n" + 
//									"		document.getElementById(\"frm_Command\").processInsId.value	= processInsId;         		\n" + 
//									"		document.getElementById(\"frm_Command\").activityId.value	= activityId;           		\n" + 
//									"		document.getElementById(\"frm_Command\").insLogId.value		= insLogId;           			\n" +
//									"		document.getElementById(\"frm_Command\").userId.value		= \"" + userId + "\";  			\n" +
									//functionVarStream +
									"		document.getElementById(\"frm_Command\").submit();                                  		\n" +
//									"		opener.reload();							                                 				\n" +
//									"		window.close();									                                 			\n" + 
									"	}	\n" +
									"</script>\n";

		String formStream		= 	"	<form id=\"frm_Command\" method=\"post\" action=\"" + HttpUtil.getWebAppUrl(request) + "/workflow/workflow.do\" target=\"ifrm_Deploy\">	\n" +
									"		<input type=\"hidden\" name=\"command\" value=\"\"/>                                                    		\n" +
									"		<input type=\"hidden\" name=\"processId\" value=\"\"/>                                                  		\n" +
									"		<input type=\"hidden\" name=\"processInsId\" value=\"\"/>                                               		\n" +
									"		<input type=\"hidden\" name=\"activityId\" value=\"\"/>                                                 		\n" +
									"		<input type=\"hidden\" name=\"insLogId\" value=\"\"/>                                                 			\n" +
									"		<input type=\"hidden\" name=\"userId\" value=\"" + userId + "\"/>                                     \n" +
									formVarStream +
									"	</form>                                                                                                     		\n" +
									"	<iframe name=\"ifrm_Deploy\" style=\"display:block;width:100%;height:50\"></iframe>	                                            		\n";
							
		String completBtnStream	=	"<input type=\"button\" name=\"cmpBtn\" value=\"Complete\" onclick=\"completeApi('"+ processId + "', '"+processInsId +"', '" + activityId + "', '"+insLogId+"')\"> ";

		mav.addObject("functionTag", functionStream);
		mav.addObject("formTag", formStream);
		mav.addObject("completeBtnTag", completBtnStream);
		mav.addObject("inputParamTag", inputParamStream);
		mav.addObject("outputParamTag", outputParamStream);
		mav.addObject("inputParam", inputDataList);
		mav.addObject("outputParam", outputDataList);
		
		return mav;
	}	
	
	/**
	 * TODO Javadoc주석작성
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/workform.do", method = RequestMethod.POST)
	public ModelAndView workformPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processId			= request.getParameter("processId") == null ? "" : request.getParameter("processId").toString();
		String processInsId			= request.getParameter("processInsId") == null ? "" : request.getParameter("processInsId").toString();
		String activityId			= request.getParameter("activityId") == null ? "" : request.getParameter("activityId").toString();
		String insLogId				= request.getParameter("insLogId") == null ? "" : request.getParameter("insLogId").toString();
		
		WorkItemBean workItemBean	= workformService.getWorkform(processId, activityId, processInsId, insLogId);
	
		
		ModelAndView mav = new ModelAndView("workflow/workplace/workform");
		mav.addObject("processId", workItemBean.getProcessId());
		mav.addObject("processName", workItemBean.getProcessName());
		mav.addObject("activityId", workItemBean.getActivityId());
		mav.addObject("activityName", workItemBean.getActivityName());
		mav.addObject("instanceId", workItemBean.getInstanceId());
		mav.addObject("logId", workItemBean.getInsLogId());		
		mav.addObject("title", workItemBean.getTitle());	
		
		String varDefineXml		= workItemBean.getVarDefine();
		String varSchemaXml		= workItemBean.getVarSchema();
		

		
		log.info("# 시작 : xmlServiceDef");
		XmlService xmlServiceDef	= new XmlService();
		xmlServiceDef.loadingXML(varDefineXml);		
		log.info("# 시작 : xmlServiceVar");
		XmlService xmlServiceVar	= new XmlService();
		xmlServiceVar.loadingXML(varSchemaXml);		
		
		String inputParamStream		= "<table border=\"0\" width=\"100%\">";
		
		ArrayList inputDataList			= new ArrayList();
		
		NodeList inputNodeList	= xmlServiceDef.getNodeList("DataSet/InputSet/Input");
		for(int i=0;i<inputNodeList.getLength();i++) {
			Node inputNode		= inputNodeList.item(i);
			String nodeId		= xmlServiceDef.getAttribute(inputNode, "Id");
			String nodeName		= xmlServiceDef.getAttribute(inputNode, "Name");
			String isArray		= xmlServiceDef.getAttribute(inputNode, "IsArray");
			if(isArray.equalsIgnoreCase("true")) {
				inputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "</td><td width=\"50%\"><input type=\"text\" name=\"in_" + nodeId + "\" value=\"\" style=\"background-color:#eeeeee\" readOnly/></td></tr>";
			} else {
				inputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "</td><td width=\"50%\"><input type=\"text\" name=\"in_" + nodeId + "\" value=\"\" style=\"background-color:#eeeeee\" readOnly/></td></tr>";
			}
			DatafieldBean intputBean	= new DatafieldBean();
			intputBean.setDatafieldId("in_" + nodeId);
			intputBean.setDatafieldName(nodeName);
			intputBean.setArrayType(isArray);
			inputDataList.add(intputBean);				
		}
		inputParamStream		+= 	"</table>";
		
		String outputParamStream	= "<table border=\"0\" width=\"100%\">";
		String functionVarStream	= "";
		String formVarStream		= "";
		
		ArrayList outputDataList			= new ArrayList();
		
		NodeList outputNodeList	= xmlServiceDef.getNodeList("DataSet/OutputSet/Output");
		for(int i=0;i<outputNodeList.getLength();i++) {
			Node outputNode		= outputNodeList.item(i);
			String nodeId		= xmlServiceDef.getAttribute(outputNode, "Id");
			String nodeName		= xmlServiceDef.getAttribute(outputNode, "Name");
			String isArray		= xmlServiceDef.getAttribute(outputNode, "IsArray");
			String nodeValue	= xmlServiceVar.getAttrValue("DataFields/DataField[@Id='" + nodeId + "']/CurrentValue");

			if(isArray.equalsIgnoreCase("true")) {
				outputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "<input type=\"button\" value=\"+\" onclick=\"addRecord('"+nodeId+"')\"/></td><td width=\"50%\"><input type=\"text\" name=\"out_" + nodeId + "\" value=\"\"/></td></tr>";
			} else {
				outputParamStream	+= 	"<tr><td width=\"50%\">" + nodeId + "</td><td width=\"50%\"><input type=\"text\" name=\"out_" + nodeId + "\" value=\"\"/></td></tr>";
			}
			functionVarStream	+=	"		document.getElementById(\"frm_Command\").out_" + nodeId + ".value	= document.getElementById(\"out_" + nodeId + "\").value;   \n";
			
			formVarStream		+= 	"		<input type=\"hidden\" name=\"out_" + nodeId + "\" value=\""+nodeValue+"\"/>                                                 					\n";
			
			DatafieldBean outputBean	= new DatafieldBean();
			outputBean.setDatafieldId("out_" + nodeId);
			outputBean.setDatafieldName(nodeName);
			outputBean.setDatafieldValue(nodeValue);
			outputBean.setArrayType(isArray);
			outputDataList.add(outputBean);		
		}	
		
		outputParamStream		+= 	"</table>";			
		
		String functionStream	= 	"<script>\n" +
									"	function completeApi(processId, processInsId, activityId, insLogId){					\n" +
									"		document.getElementById(\"frm_Command\").command.value		= \"completeWorkItem\";	\n" + 
									"		document.getElementById(\"frm_Command\").processId.value	= processId;            \n" + 
									"		document.getElementById(\"frm_Command\").processInsId.value	= processInsId;         \n" + 
									"		document.getElementById(\"frm_Command\").activityId.value	= activityId;           \n" + 
									"		document.getElementById(\"frm_Command\").insLogId.value		= insLogId;           	\n" +
									"		document.getElementById(\"frm_Command\").userId.value		= insLogId;           	\n" +
									functionVarStream +
									"		document.getElementById(\"frm_Command\").submit();                                  \n" + 
									"	}	\n" +
									"</script>\n";

		String formStream		= 	"	<form id=\"frm_Command\" method=\"post\" action=\"" + HttpUtil.getWebAppUrl(request) + "/workflow/workflow.do\" target=\"ifrm_Deploy\">	\n" +
									"		<input type=\"hidden\" name=\"command\" value=\"\"/>                                                    		\n" +
									"		<input type=\"hidden\" name=\"processId\" value=\"\"/>                                                  		\n" +
									"		<input type=\"hidden\" name=\"processInsId\" value=\"\"/>                                               		\n" +
									"		<input type=\"hidden\" name=\"activityId\" value=\"\"/>                                                 		\n" +
									"		<input type=\"hidden\" name=\"insLogId\" value=\"\"/>                                                 			\n" +
									"		<input type=\"hidden\" name=\"userId\" value=\"\"/>                                                 			\n" +
									formVarStream +
									"	</form>                                                                                                     		\n" +
									"	<iframe name=\"ifrm_Deploy\" style=\"display:block;width:100%;height:50\"></iframe>	                                            		\n";
							
		String completBtnStream	=	"<input type=\"button\" name=\"cmpBtn\" value=\"Complete\" onclick=\"completeApi('"+ processId + "', '"+processInsId +"', '" + activityId + "', '"+insLogId+"')\"> ";

		mav.addObject("functionTag", functionStream);
		mav.addObject("formTag", formStream);
		mav.addObject("completeBtnTag", completBtnStream);
		mav.addObject("inputParamTag", inputParamStream);
		mav.addObject("outputParamTag", outputParamStream);
		mav.addObject("inputParam", inputDataList);
		mav.addObject("outputParam", outputDataList);
		return mav;
	}		
	

	
	
	@RequestMapping(value = "workflow/confirm.do", method = RequestMethod.POST)
	public ModelAndView workformConfirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ModelAndView mav = new ModelAndView("workflow/workplace/confirm");
		
		return mav;
	}
	
	/**
	 * 
	 * @param stream
	 * @param attrId
	 * @return
	 */
	public String getAttrValue(String stream, String attrId) {
		String attrValue	= "";
		String[] attrArry	= stream.split(" ");
		if(0 < attrArry.length) {
			for(int j=0;j<attrArry.length;j++) {
				String[] token	= attrArry[j].split("=");
				if(token[0].equalsIgnoreCase(attrId)) {
					attrValue		= token[1].replaceAll("\"", "").replaceAll("\'", "");
				}
			}
		}		
		return attrValue;
	}		
	
	/**
	 * TODO Javadoc주석작성
	 * @param obj
	 * @return
	 */
	public String isNullCheck(Object obj) {
		if(obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}	
	
	@RequestMapping(value = "workflow/formTemplate.do", method = RequestMethod.GET)
	public String formTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "workflow/workplace/workform";
	}		
}

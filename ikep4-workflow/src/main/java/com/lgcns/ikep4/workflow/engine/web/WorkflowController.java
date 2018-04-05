/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

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

import com.lgcns.ikep4.workflow.engine.model.InstanceBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.DeployService;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.WorkformService;
import com.lgcns.ikep4.workflow.engine.util.XmlService;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: WorkflowController.java 16245 2011-08-18 04:28:59Z giljae $ WorkflowController.java 오후 6:47:40
 */
@Controller
public class WorkflowController {
	
	Log log = LogFactory.getLog(WorkflowController.class);
	
	@Autowired
	private DeployService deployService;

	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private WorkformService workformService;		
	
	/**
	 * TODO Javadoc주석작성
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/workflow.do", method = RequestMethod.GET)
	public ModelAndView workflowGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		ModelAndView mav = new ModelAndView("workflow/workplace/confirm");
		String resultMsg = transform(request, response);
		log.info("## Completed Workflow-processing ...(GET)");
		mav.addObject("result", resultMsg);
		return mav;
	}

	/**
	 * TODO Javadoc주석작성
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "workflow/workflow.do", method = RequestMethod.POST)
	public ModelAndView workflowPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		ModelAndView mav = new ModelAndView("workflow/workplace/confirm");
		String resultMsg = transform(request, response);
		log.info("## Completed Workflow-processing ...(POST)");
		mav.addObject("result", resultMsg);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String transform(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String	processId = ""; 
		String	processInsId = ""; 
		String activityId = ""; 
		String insLogId	= "";
		String processVer 	= "";
		
		String resultMsg	= "TRUE";
		String xpdlStream 	= "";
		String command = request.getParameter("command") == null ? "" : request.getParameter("command").toString();
		String userId = request.getParameter("userId") == null ? "admin" : request.getParameter("userId").toString();
		log.info("#################### transform Start : command = " + command);
		try {
			if (command.equalsIgnoreCase("deploy")) {
				if (request.getParameter("xpdlStream") != null) {
					xpdlStream = request.getParameter("xpdlStream").toString();
					//deployService.deployProcess(xpdlStream, userId);
					deployService.deployProcess(xpdlStream, userId, "partition1", "WORKFLOW");
				}
			} else if (command.equalsIgnoreCase("undeploy")) {
				log.info("# ###########################################################################"); 
				log.info("# start undeploy ");
				log.info("# ###########################################################################");
			}
	
			// 프로세스 처리 이벤트
			if (command.equalsIgnoreCase("startProcess")) {
	
				String msg	= 	"\n# ###########################################################################" + 
								"\n# start startProcess " +
								"\n# ###########################################################################";
				log.info(msg);			
				
				if(request.getParameter("processId") != null) { 
					processId 	= request.getParameter("processId") == null ? "" : request.getParameter("processId").toString(); 
					String title 		= request.getParameter("title") == null ? "" : request.getParameter("title").toString(); 
					HashMap<String, Object> paramHsh			= new HashMap<String, Object>();
					instanceService.startProcess(processId, title, userId, paramHsh);
				}
	
				msg	= 			"\n# ###########################################################################" + 
								"\n# end startProcess (" + processInsId + ")" +
								"\n# ###########################################################################";
				log.info(msg);				
							
			} else if (command.equalsIgnoreCase("deleteProcess")) {
				if (request.getParameter("processId") != null ) {
					processId 	= request.getParameter("processId") == null ? "" : request.getParameter("processId").toString();
					processVer 	= request.getParameter("processVer") == null ? "" : request.getParameter("processVer").toString();
					boolean isSuccess	= deployService.deleteProcess(processId, processVer);
				}
			} else if (command.equalsIgnoreCase("pauseProcess")) {
				log.info("# ###########################################################################"); 
				log.info("# pauseProcess ");
				log.info("# ###########################################################################"); 
	
			} else if (command.equalsIgnoreCase("resumeProcess")) {
				log.info("# ###########################################################################"); 
				log.info("# resumeProcess ");
				log.info("# ###########################################################################"); 
	
			} else if (command.equalsIgnoreCase("deleteInstance")) {
				processInsId = request.getParameter("processInsId") == null ? "" : request.getParameter("processInsId").toString(); 
				if(!processInsId.equals("")) {
					instanceService.deleteInstance(processInsId);
				}
			}
			
			// 프로세스인스턴스 처리 이벤트 
			if(command.equalsIgnoreCase("startWorkItem")) { 
				log.info("# ###########################################################################"); 
				log.info("# startWorkItem ");
				log.info("# ###########################################################################"); 	
				
				
			} else if(command.equalsIgnoreCase("completeWorkItem")) {
				processId = request.getParameter("processId") == null ? "" : request.getParameter("processId").toString(); 
				processInsId = request.getParameter("processInsId") == null ? "" : request.getParameter("processInsId").toString(); 
				activityId = request.getParameter("activityId") == null ? "" : request.getParameter("activityId").toString(); 
				insLogId = request.getParameter("insLogId") == null ? "" : request.getParameter("insLogId").toString();
				
				WorkItemBean workItemBean	= workformService.getWorkform(processId, activityId, processInsId, insLogId);
				String varDefine			= workItemBean.getVarDefine();
				XmlService xmlService		= new XmlService();
				xmlService.loadingXML(varDefine);
				
				String msg = "";
				Enumeration enumParam = request.getParameterNames();
				while(enumParam.hasMoreElements()) {
					msg 	+= enumParam.nextElement() + ", ";
				}
				log.info("request.parameterList : " + msg);
				
				HashMap<String, Object> paramHsh			= new HashMap<String, Object>();
				NodeList outputNodeList		= xmlService.getNodeList("/DataSet/OutputSet/Output");
				for(int i=0;i<outputNodeList.getLength();i++) {
					Node outputNode 		= outputNodeList.item(i);
					String outputId			= xmlService.getAttribute(outputNode, "Id");
					String outputType		= xmlService.getAttribute(outputNode, "IsArray");
					String useIndex			= xmlService.getAttribute(outputNode, "UseIndex");
					
					log.info("# outputId=" + outputId + ", outputType=" + outputType + ", useIndex=" + useIndex);
					
					if(outputType.equalsIgnoreCase("TRUE")) {
						int count			= 0;
						HashMap<String, Object> arrayHsh	= new HashMap<String, Object>();
						while(request.getParameter("out_" + outputId + "_" + count) != null) {
							String paramValue	= request.getParameter("out_" + outputId + "_" + count) == null ? "" : request.getParameter("out_" + outputId + "_" + count).toString();
							arrayHsh.put((""+count), paramValue);
							count++;
						}
						paramHsh.put(outputId, arrayHsh);
						if(useIndex.equalsIgnoreCase("TRUE")) {
							if(request.getParameter("out_" + outputId + "_Key") != null) {
								String paramValue	= request.getParameter("out_" + outputId + "_Key") == null ? "" : request.getParameter("out_" + outputId + "_Key").toString();
								paramHsh.put((outputId + "_Key"), paramValue);
							}
						}
					} else {
						String paramValue		= request.getParameter("out_" + outputId) == null ? "" : request.getParameter("out_" + outputId).toString();
						paramHsh.put(outputId, paramValue);
					}
				}
				
				log.info("\n\n\n"); 
				msg 		= 	"\n# ###########################################################################" + 
								"\n# Start : CompleteWorkItem " +
								"\n# ###########################################################################" + 
								"\n# processInsId : " + processInsId +
								"\n# userId : " + userId +
								"\n# activityId : " + activityId +
								"\n# insLogId : " + insLogId + 
								"\n# parameter : " + paramHsh + 
								"\n# ###########################################################################";
				log.info(msg);
				instanceService.completeWorkItem(processInsId, activityId, insLogId, userId, paramHsh); 
				
				msg			= 	"\n# ###########################################################################" + 
								"\n# End : CompleteWorkItem " +
								"\n# ###########################################################################";
				log.info(msg);
			}
			
			// 조직 처리 이벤트
			if (command.equalsIgnoreCase("appendDept")) {
				log.info("# ###########################################################################"); 
				log.info("# appendDept ");
				log.info("# ###########################################################################"); 
	
			} else if (command.equalsIgnoreCase("deleteDept")) {
				log.info("# ###########################################################################"); 
				log.info("# deleteDept ");
				log.info("# ###########################################################################"); 			
	
			} else if (command.equalsIgnoreCase("updateDept")) {
				log.info("# ###########################################################################"); 
				log.info("# updateDept ");
				log.info("# ###########################################################################"); 			
	
			}
	
			// 사용자 처리 이벤트
			if (command.equalsIgnoreCase("appendUser")) {
				log.info("# ###########################################################################"); 
				log.info("# appendUser ");
				log.info("# ###########################################################################"); 			
	
			} else if (command.equalsIgnoreCase("deleteUser")) {
				log.info("# ###########################################################################"); 
				log.info("# deleteUser ");
				log.info("# ###########################################################################"); 			
	
			} else if (command.equalsIgnoreCase("updateUser")) {
				log.info("# ###########################################################################"); 
				log.info("# updateUser ");
				log.info("# ###########################################################################"); 			
	
			}
	
			// 역할 처리 이벤트
			if (command.equalsIgnoreCase("appendRole")) {
				log.info("# ###########################################################################"); 
				log.info("# appendRole ");
				log.info("# ###########################################################################"); 			
	
			} else if (command.equalsIgnoreCase("deleteRole")) {
				log.info("# ###########################################################################"); 
				log.info("# deleteRole ");
				log.info("# ###########################################################################"); 			
	
			} else if (command.equalsIgnoreCase("updateRole")) {
				log.info("# ###########################################################################"); 
				log.info("# updateRole ");
				log.info("# ###########################################################################"); 			
	
			} else if (command.equalsIgnoreCase("test")) {
				log.info("# ###########################################################################"); 
				log.info("# updateRole ");
				log.info("# ###########################################################################"); 			
	
			}
			
		} catch(Exception e) {
			resultMsg	= "" + e;
			log.info("# WorkflowController Error : " + e);
			if(!processInsId.equals("")) {
				WorkItemBean workItemBean 	= new WorkItemBean();
				workItemBean.setInstanceId(processInsId);
				workItemBean.setState("ERROR");
				workItemBean.setResultMsg("" + e);
				instanceService.update(workItemBean);
			}
		}
		log.info("# WorkflowController Complete " + resultMsg);
		return resultMsg;
	}
	
	@RequestMapping(value = "workflow/test.do", method = RequestMethod.GET)
	public String startComplete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		log.info("## workflow TEST start...(GET)");
		String	command 			= request.getParameter("command") == null ? "" : request.getParameter("command").toString();
		String	apprId 			= request.getParameter("apprId") == null ? "" : request.getParameter("apprId").toString(); 
		String	approvalCount 	= request.getParameter("approvalCount") == null ? "" : request.getParameter("approvalCount").toString();
		String	title 			= request.getParameter("title") == null ? "" : request.getParameter("title").toString();
		String	activityId 			= request.getParameter("activityId") == null ? "" : request.getParameter("activityId").toString(); 
		
		String processId							= "APPROVAL_PROCESS";

		String userId								= "user1";
		HashMap<String, Object> paramHsh			= new HashMap<String, Object>();
		HashMap<String, Object> approvalHsh			= new HashMap<String, Object>();
		approvalHsh.put("0", "user1");
		approvalHsh.put("1", "user2");
		approvalHsh.put("2", "user3");
		approvalHsh.put("3", "user4");
		paramHsh.put("apprId", apprId);
		paramHsh.put("draftType", "APP");
		paramHsh.put("approvalList", approvalHsh);
		paramHsh.put("approvalCount", approvalCount);
		
		String msg = 	"\n# ###########################################################################" + 
						"\n# Start : 테스트 케이스 " +
						"\n# ###########################################################################";
		log.info(msg);		
		
		InstanceBean instanceBean	= new InstanceBean();
		if(command.equals("start")) {
			instanceBean 				= instanceService.startProcess(processId, title, userId, paramHsh);
		} else if(command.equals("startcomplete")) {
			instanceBean 				= instanceService.startProcess(processId, title, userId, paramHsh);
			String processInsId			= instanceBean.getInstanceId();
			String insLogId				= instanceBean.getInstanceLogId();
			msg = 	"\n# ###########################################################################" + 
					"\n# Start : 정상 스타트  : processInsId : " + processInsId + ", insLogId : " + insLogId +
					"\n# ###########################################################################";
			log.info(msg);				
			
			instanceService.completeWorkItem(processInsId, activityId, insLogId, userId, paramHsh); 			
		}
		
		return "/workflow/workplace/startComplete";
	}	

}

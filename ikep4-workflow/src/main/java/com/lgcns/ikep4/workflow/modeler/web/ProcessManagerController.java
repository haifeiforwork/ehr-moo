/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.workflow.engine.service.DeployService;
import com.lgcns.ikep4.workflow.modeler.model.Partition;
import com.lgcns.ikep4.workflow.modeler.model.PartitionProcess;
import com.lgcns.ikep4.workflow.modeler.model.ProcessManager;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;
import com.lgcns.ikep4.workflow.modeler.service.ProcessManagerService;


/**
 * Modeler Controller => ProcessManagerService => 각각 3개의 DAO(Partition,
 * PartitionProcess, ProcessModel) => 각각 3개의 SqlMap.
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessManagerController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/modeler")
@SessionAttributes("workflow")
public class ProcessManagerController extends BaseController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private ProcessManagerService processManagerService;
	
	@Autowired
	private DeployService deployService;
	
	ProcessManager processManager;

	/**
	 * 모델러 홈
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/main.do")
	public ModelAndView main() {
		return (new ModelAndView("modeler/main"));
	}
	
	/**
	 * 프리즘 홈
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/prism.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView prism(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		String apprId = (request.getParameter("apprId") == null ? "" : request.getParameter("apprId"));
		String processType = (request.getParameter("processType") == null ? "workflow" : request.getParameter("processType"));
		String mode = (request.getParameter("mode") == null ? "runtime" : request.getParameter("mode"));
		String partitionId = (request.getParameter("partitionId") == null ? "" : request.getParameter("partitionId"));
		String processId = (request.getParameter("processId") == null ? "" : request.getParameter("processId"));
		String version = (request.getParameter("version") == null ? "" : request.getParameter("version"));
		String businessKey = (request.getParameter("businessKey") == null ? "" : request.getParameter("businessKey"));
		String instanceId = (request.getParameter("instanceId") == null ? "" : request.getParameter("instanceId"));
		String scale = (request.getParameter("scale") == null ? "1" : request.getParameter("scale"));	
		String activityListView = (request.getParameter("activityListView") == null ? "true" : request.getParameter("activityListView"));
		String minimapView = (request.getParameter("minimapView") == null ? "true" : request.getParameter("minimapView"));
		String onlyProcessView = (request.getParameter("onlyProcessView") == null ? "false" : request.getParameter("onlyProcessView"));
		String legendView = (request.getParameter("legendView") == null ? "true" : request.getParameter("legendView"));
		String titleView = (request.getParameter("titleView") == null ? "treu" : request.getParameter("titleView"));
		String refreshTime = (request.getParameter("refreshTime") == null ? "60" : request.getParameter("refreshTime"));
		String saveView = (request.getParameter("saveView") == null ? "true" : request.getParameter("saveView"));
		String refreshView = (request.getParameter("refreshView") == null ? "true" : request.getParameter("refreshView"));
		String activitySetView = (request.getParameter("activitySetView") == null ? "true" : request.getParameter("activitySetView"));
		String subProcessSetView = (request.getParameter("subProcessSetView") == null ? "true" : request.getParameter("subProcessSetView"));
		String processNameView = (request.getParameter("processNameView") == null ? "true" : request.getParameter("processNameView"));
		String processTitle = (request.getParameter("processTitle") == null ? "" : request.getParameter("processTitle"));
		
		data.put("apprId", apprId);
		data.put("processType", processType);
		data.put("mode", mode);
		data.put("partitionId", partitionId);
		data.put("processId", processId);
		data.put("version", version);
		data.put("instanceId", instanceId);
		data.put("businessKey", businessKey);
		data.put("instanceId", instanceId);
		data.put("scale", scale);
		data.put("activityListView",activityListView);
		data.put("minimapView", minimapView);
		data.put("onlyProcessView", onlyProcessView);
		data.put("legendView", legendView);
		data.put("titleView", titleView);
		data.put("refreshTime", refreshTime);
		data.put("saveView", saveView);
		data.put("refreshView", refreshView);
		data.put("activitySetView", activitySetView);
		data.put("subProcessSetView", subProcessSetView);
		data.put("processNameView", processNameView);
		data.put("processTitle", processTitle);
		
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-prism/prism");
		mav.addObject("data", data);
		
		return mav;
	}

	/**
	 * 왼쪽 메뉴 트리
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/tree.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView tree() {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-layout/tree");
		try {
			processManager = processManagerService.list();
			mav.addObject("processList", processManager);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}
		return mav;
	}

	/**
	 * 하단 프로세스 탭 화면
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processModel.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processModel() {
		return new ModelAndView("workflow/modeler/modeler-process/processModel");
	}
	
	/**
	 * 하단 액티비티 탭 화면
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityModel.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityModel() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityModel");
	}
	
	/**
	 * 하단 액티비티 탭 화면
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/transitionModel.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView transitionModel() {
		return new ModelAndView("workflow/modeler/modeler-transition/transitionModel");
	}
	
	/**
	 * 파티션을 등록 수행.
	 * 
	 * @param partition
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/createPartition.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean createPartition(Partition partition, BindingResult result) {
		boolean isPartitionId = false;
		try {
			isPartitionId = processManagerService.getPartitionExists(partition);
			
			if(!isPartitionId){
				processManagerService.createPartition(partition);
			}
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException(ex.getMessage(), "code", ex);
		}
		return isPartitionId;
	}

	/**
	 * 프로세스 등록 수행.
	 * 
	 * @param partitionProcess
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/createProcess.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean createProcess(PartitionProcess partitionProcess, ProcessModel processModel, BindingResult result) {
		boolean isProcessId = false;
		try {
			isProcessId = processManagerService.getProcessExists(partitionProcess, processModel);
			
			if(!isProcessId){
				String processId = processManagerService.createProcess(partitionProcess, processModel);
				if(processId.equals("")){
					throw new Error();
				}
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}
		return isProcessId;
	}

	/**
	 * 프로세스 수정 수행.
	 * 
	 * @param partitionProcess
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/updateProcess.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String updateProcess(PartitionProcess partitionProcess, ProcessModel processModel, BindingResult result) {
		if (result.hasErrors()) {
			return "workflow/modeler/main";
		}
		try {
			processManagerService.updateProcess(partitionProcess, processModel);
		} catch (Exception ex) {
			log.debug("updateProcess : " + ex.getMessage() );
			throw new IKEP4AjaxException("code", ex);

		}

		return "redirect:/workflow/modeler/tree.do";
	}
	
	/**
	 * 프로세스 삭제 수행.
	 * 
	 * @param partitionProcess
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/deleteProcess.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody int deleteProcess(PartitionProcess partitionProcess, ProcessModel processModel, BindingResult result) {
		int isSuccess = 0;
		try {
			isSuccess = processManagerService.deletePartitionProcess(partitionProcess);
			
			if( isSuccess == 1 ){
				isSuccess = processManagerService.deleteProcess(processModel);
			}else{
				new Error();
			}
		} catch (Exception ex) {
			log.debug("deleteProcess : " + ex.getMessage() );
			throw new IKEP4AjaxException("code", ex);
		}
		return isSuccess;
	}
	
	/**
	 * 파티션 삭제 수행.
	 * 
	 * @param partitionProcess
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/deletePartition.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody int deletePartition(PartitionProcess partitionProcess, ProcessModel processModel, BindingResult result) {
		int isSuccess = 1;
		try {
			ProcessManager pm = new ProcessManager(); 
			pm = processManagerService.selectPartitionProcess(partitionProcess);
			
			for(int i=0; i < pm.getPartitionProcessList().size(); i++){
				partitionProcess = pm.getPartitionProcessList().get(i);
				String processId = partitionProcess.getProcessId();
				String processVer = partitionProcess.getProcessVer();
				
				processModel.setProcessId(processId);
				processModel.setProcessVer(processVer);
				partitionProcess.setProcessId(processId);
				partitionProcess.setProcessVer(processVer);
				
				processManager = processManagerService.selectProcessModel(processModel);
				
				// Deploy 해제.
				if ( processManager.getProcessModel().getProcessState().equals("ACTIVE")  ){
					undeployProcess(processModel, result);
				}
				// 파티션 프로세스 삭제.
				isSuccess = processManagerService.deletePartitionProcess(partitionProcess);
				
				// 모델 테이블 삭제.
				isSuccess = processManagerService.deleteProcess(processModel);
			}
			
			// 마지막으로 파티션 삭제.
			processManagerService.deletePartition(partitionProcess);
			
		} catch (Exception ex) {
			log.debug("deletePartition : " + ex.getMessage() );
			throw new IKEP4AjaxException("code", ex);
		}
		return isSuccess;
	}
	
	/**
	 * 프로세스 디플로이.
	 * 
	 * @param processModel
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/deployProcess.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean deployProcess(PartitionProcess partitionProcess, ProcessModel processModel, BindingResult result) {
		boolean rs = false;
		if (result.hasErrors()) {
			return rs;
		}
		try {
			User user = (User) getRequestAttribute("ikep.user");
			String processType = "WORKFLOW";
			rs = deployService.deployProcess(processModel.getModelScript(), user.getUserId(), partitionProcess.getPartitionId(), processType);
			if (log.isDebugEnabled()) {
				log.debug("deployProcess is Success ? : " + rs );
			}
			if(!rs){
				throw new Error();
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return rs;
	}
	
	/**
	 * 프로세스 언 디플로이.
	 * 
	 * @param processModel
	 * @param result
	 * @return redirect
	 */
	@RequestMapping(value = "/undeployProcess.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean undeployProcess(ProcessModel processModel, BindingResult result) {
		boolean rs = false;
		if (result.hasErrors()) {
			return rs;
		}
		try {
			rs = deployService.deleteProcess(processModel.getProcessId(), processModel.getProcessVer());
			if (log.isDebugEnabled()) {
				log.debug("undeployProcess is Success ? : " + rs );
			}
			if(!rs){
				throw new Error();
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return rs;
	}

	/**
	 * 파티션 추가 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/partitionCreatePartition.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView partitionCreatePartition() {
		return new ModelAndView("workflow/modeler/modeler-partition/partitionCreatePartition");
	}

	/**
	 * 프로세스 추가 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processCreateProcess.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processCreateProcess() {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-process/processCreateProcess");
		try {
			processManager = processManagerService.listPartition();
			mav.addObject("processList", processManager);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);

		}
		return mav;
	}

	/**
	 * 프로세스 기본정보 수정 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processModifyDefaultInfo.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processModifyDefaultInfo() {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-process/processModifyDefaultInfo");
		mav.addObject("processList", processManager);

		return mav;
	}
	
	/**
	 * 프로세스 참여자 변수 생성 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processCreateVariableParticipant.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processCreateVariableParticipant() {
		return new ModelAndView("workflow/modeler/modeler-process/processCreateVariableParticipant");
	}
	
	/**
	 * 액티비티 기본정보 수정 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityModifyDefaultInfo.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityModifyDefaultInfo() {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-activity/activityModifyDefaultInfo");
		mav.addObject("processList", processManager);

		return mav;
	}

	/**
	 * 프로세스 변수 추가 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processCreateVariable.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processCreateVariable(ProcessModel processModel) {
		return new ModelAndView("workflow/modeler/modeler-process/processCreateVariable");
	}
	
	/**
	 * 액티비티 변수 추가 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityCreateVariable.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityCreateVariable(ProcessModel processModel) {
		return new ModelAndView("workflow/modeler/modeler-activity/activityCreateVariable");
	}

	/**
	 * 프로세스 변수 수정 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processModifyVariable.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processModifyVariable(ProcessModel processModel) {
		return new ModelAndView("workflow/modeler/modeler-process/processModifyVariable");
	}
	
	/**
	 * 액티비티 고급정보 변수 추가 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityCreateAdvanceVariable.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityCreateAdvanceVariable(ProcessModel processModel) {
		return new ModelAndView("workflow/modeler/modeler-activity/activityCreateAdvanceVariable");
	}
	
	/**
	 * 프로세스 저장 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processSaveProcess.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processSaveProcess(ProcessModel processModel) {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-process/processSaveProcess");
		try {
			processManager = processManagerService.selectProcessModel(processModel);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		mav.addObject("processList", processManager);
		return mav;
	}
	
	/**
	 * 프로세스 배치 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processDeployProcess.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processDeployProcess(ProcessModel processModel) {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-process/processDeployProcess");
		try {
			processManager = processManagerService.selectProcessModel(processModel);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		mav.addObject("processList", processManager);
		return mav;
	}
	
	/**
	 * 프로세스 삭제 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processDeleteProcess.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processDeleteProcess(ProcessModel processModel) {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-process/processDeleteProcess");
		try {
			processManager = processManagerService.selectProcessModel(processModel);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		mav.addObject("processList", processManager);
		return mav;
	}
	
	/**
	 * 파티션 삭제 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/partitionDeletePartition.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView partitionDeletePartition() {
		return new ModelAndView("workflow/modeler/modeler-partition/partitionDeletePartition");
	}
	
	
	/**
	 * 프로세스 배치해제 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/processUnDeployProcess.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processUnDeployProcess(ProcessModel processModel) {
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-process/processUnDeployProcess");
		try {
			processManager = processManagerService.selectProcessModel(processModel);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		mav.addObject("processList", processManager);
		return mav;
	}
	
	/**
	 * Expression Builder 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/expressionBuilder.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView expressionBuilder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ModelAndView mav = new ModelAndView("workflow/modeler/modeler-common/expressionBuilder");
		mav.addObject("target", request.getParameter("target"));
		return mav;
	}
	
	/**
	 * 이메일 알림 생성 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityCreateEmailNotification.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityCreateEmailNotification() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityCreateEmailNotification");
	}
	
	/**
	 * SMS 알림 생성 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityCreateSMSNotification.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityCreateSMSNotification() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityCreateSMSNotification");
	}
	
	/**
	 * 이메일 알림 수정 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityModifyEmailNotification.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityModifyEmailNotification() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityModifyEmailNotification");
	}
	
	/**
	 * SMS 알림 수정 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityModifySMSNotification.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityModifySMSNotification() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityModifySMSNotification");
	}
	
	/**
	 * 메시지 알림 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityNotificationMessage.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityNotificationMessage() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityNotificationMessage");
	}
	
	/**
	 * 메시지 알림 참여자 팝업
	 * 
	 * @return mav
	 */
	@RequestMapping(value = "/activityCreateParticipantPopup.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView activityCreateParticipantPopup() {
		return new ModelAndView("workflow/modeler/modeler-activity/activityCreateParticipantPopup");
	}

	/**
	 * 모델 XML.
	 * 
	 * @param processId
	 * @param result
	 * @return redirect
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectModelXml.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProcessManager selectModelXml(@RequestParam("processId") String processId, @RequestParam("processVer") String processVer, ProcessModel processModel) throws IOException {
		processModel.setProcessId(processId);
		processModel.setProcessVer(processVer);
		try {
			processManager = processManagerService.selectProcessModel(processModel);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		
		return processManager;
	}
	
	/**
	 * Instance Tracking Data XML.
	 * 
	 * @param partitionProcess
	 * @param result
	 * @return redirect
	 * @throws IOException
	 */
	@RequestMapping(value = "/instanceTrackingXml.do", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void instanceTrackingXml(String instanceId, HttpServletResponse res) throws IOException {
		try {
			String instanceTrackingXml = processManagerService.getInstanceTrackingXMLData(instanceId);
			
			if (log.isDebugEnabled()) {
				log.debug("instanceTrackingXml : "+instanceTrackingXml);
			}
			
			res.getOutputStream().write(instanceTrackingXml.getBytes());
			res.setContentType("text/xml;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
	}
	
	/**
	 * Process Model View XML.
	 * 
	 * @param partitionProcess
	 * @param result
	 * @return redirect
	 * @throws IOException
	 */
	@RequestMapping(value = "/processModelViewXml", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void processModelViewXml(ProcessModel processModel, HttpServletResponse res) throws IOException {
		try {
			String processModelViewXml = processManagerService.selectModelViewScript(processModel);
			
			if (log.isDebugEnabled()) {
				log.debug("processModelViewXml : "+processModelViewXml);
			}
			
			res.getOutputStream().write(processModelViewXml.getBytes());
			res.setContentType("text/xml;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache");
		} catch (NullPointerException ne) {
			res.setContentType("text/xml;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
	}
}

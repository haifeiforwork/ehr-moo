package com.lgcns.ikep4.workflow.workplace.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.impl.DeployServiceImpl;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;

/**
 * WorkPlace Servlet 컨트롤러
 * 
 * @author 이재경
 * @version $Id: WorkplaceListServlet.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/workflow/workplace/worklist")
public class WorkplaceListServlet extends HttpServlet {

	private static final long serialVersionUID = -3739420594922088016L;
	
	@Autowired
	private WorkplaceListService workplaceListService;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	Log log = LogFactory.getLog(WorkplaceListServlet.class);

	@RequestMapping(value = "/myWorkList4xml.do")
	public void myWorkList4xml(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		String userId = (String)request.getParameter("userId");						//userid
		String searchcondition  = (String)request.getParameter("searchcondition");	//조회 조건( 프로세스, 업무명, 제목) 
		String searchkeyword  = (String)request.getParameter("searchkeyword");		//검색어
		
		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setUserId(userId);
		workplaceSearchCondition.setSearchcondition(searchcondition);
		workplaceSearchCondition.setSearchkeyword(searchkeyword);
		workplaceSearchCondition.setQueryId("todoList");			//나의 업무목록
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		List<WorkItemBean> workItemBeanList = searchResult.getEntity();	//검색 결과에서 리스트 데이터를 가져온다.
		
		try{
			makeXml(response, workItemBeanList);	
		}
		catch( IOException e ){
			log.info("myWorkList4xml(Error) : ", e);			
		}
	 }
	
	@RequestMapping(value = "/progressWorkList4xml.do")
	public void progressWorkList4xml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = (String)request.getParameter("userId");						//userid
		String searchcondition  = (String)request.getParameter("searchcondition");	//조회 조건( 프로세스, 업무명, 제목) 
		String searchkeyword  = (String)request.getParameter("searchkeyword");		//검색어
		
		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setUserId(userId);
		workplaceSearchCondition.setSearchcondition(searchcondition);
		workplaceSearchCondition.setSearchkeyword(searchkeyword);
		workplaceSearchCondition.setQueryId("runningList");			//진행중인 업무목록
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		List<WorkItemBean> workItemBeanList = searchResult.getEntity();	//검색 결과에서 리스트 데이터를 가져온다.
		
		try{
			makeXml(response, workItemBeanList);	
		}
		catch( IOException e ){
			log.info("progressWorkList4xml(Error) : ", e);
		}
	}
	
	@RequestMapping(value = "/doneWorkList4xml.do")
	public void doneWorkList4xml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = (String)request.getParameter("userId");						//userid
		String searchcondition  = (String)request.getParameter("searchcondition");	//조회 조건( 프로세스, 업무명, 제목) 
		String searchkeyword  = (String)request.getParameter("searchkeyword");		//검색어
		
		WorkplaceSearchCondition workplaceSearchCondition = new WorkplaceSearchCondition();
		workplaceSearchCondition.setUserId(userId);
		workplaceSearchCondition.setSearchcondition(searchcondition);
		workplaceSearchCondition.setSearchkeyword(searchkeyword);
		workplaceSearchCondition.setQueryId("completeList");			//진행중인 업무목록
		
		SearchResult<WorkItemBean> searchResult = workplaceListService.workplaceWorkList(workplaceSearchCondition);
		
		List<WorkItemBean> workItemBeanList = searchResult.getEntity();	//검색 결과에서 리스트 데이터를 가져온다.
		
		try{
			makeXml(response, workItemBeanList);	
		}
		catch( IOException e ){
			log.info("doneWorkList4xml(Error) : ", e);
		}
	}
	
	private void makeXml(HttpServletResponse response, List<WorkItemBean> workItemBeanList) throws IOException{
		response.setContentType("text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<?xml version='1.0' encoding='utf-8'?>");
		out.println("<List>");

		if( workItemBeanList != null && workItemBeanList.size() > 0 ){
			for( WorkItemBean workitem : workItemBeanList ){
				out.println(" <Row>");
				out.println("  <InstanceId>"+strNullCheck(workitem.getInstanceId())+"</InstanceId>");
				out.println("  <InsLogId>"+strNullCheck(workitem.getInsLogId())+"</InsLogId>");
				out.println("  <PartitionName>"+strNullCheck(workitem.getPartitionName())+"</PartitionName>");
				out.println("  <ProcessName>"+strNullCheck(workitem.getProcessName())+"</ProcessName>");
				out.println("  <ActivityName>"+strNullCheck(workitem.getActivityName())+"</ActivityName>");
				out.println("  <Title>"+strNullCheck(workitem.getTitle())+"</Title>");
				out.println("  <StateName>"+strNullCheck(workitem.getStateName())+"</StateName>");
				out.println("  <InstanceAuthor>"+strNullCheck(workitem.getInstanceAuthor())+"</InstanceAuthor>");
				out.println("  <UserId>"+strNullCheck(workitem.getUserId())+"</UserId>");
				out.println("  <UserName>"+strNullCheck(workitem.getUserName())+"</UserName>");
				out.println("  <DueDate>"+dateNullCheck(workitem.getDueDate())+"</DueDate>");
				out.println("  <SelectDate>"+dateNullCheck(workitem.getSelectDate())+"</SelectDate>");
				out.println("  <AssignDate>"+dateNullCheck(workitem.getAssignDate())+"</AssignDate>");
				out.println("  <ProcStartDate>"+dateNullCheck(workitem.getProcStartDate())+"</ProcStartDate>");
				out.println("  <ProcEndDate>"+dateNullCheck(workitem.getProcEndDate())+"</ProcEndDate>");
				out.println(" </Row>");
			}
		}
		else{
			out.println(" <Row>");
			out.println("  <NoData>not found</NoData>");
			out.println(" </Row>");
		}
		
		out.println("</List>");
	}
	
	private String strNullCheck(String param){
		return (param!=null?param:"");
	}
	
	private String dateNullCheck(Date dt){
		if( dt != null)	return timeZoneSupportService.convertTimeZoneToString(dt, "yyyy.MM.dd HH:mm");
		else return "";
	}
}
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<iframe id="contentIframe" name="contentIframe" width="100%" onload="iKEP.iFrameResize('#contentIframe');" scrolling="no" frameborder="0" title="workalignment iframe"></iframe>

<script type="text/javascript">
$jq(document).ready(function() {

	//<![CDATA[

	var link = "${param.link}";
	var type = "${param.type}";
	var url = "";
	var returnUrl = "${param.url}";

	if(type == "approval"){
		if(link == "1"){
			url = "<c:url value='/wfapproval/work/aplist/listApTodo.do?linkType=mywork'/>";
		} else if(link == "2") {
			url = "<c:url value='/wfapproval/work/aplist/listApMyRequest.do?linkType=mywork'/>";
		} else{
			url = "<c:url value='/wfapproval/work/apform/listApFormSelect.do?linkType=mywork'/>";
		}
		
	} else if(type == "todo"){
		
		if(returnUrl){
			url = returnUrl + "?systemCode=${param.systemCode}&subworkCode=${param.subworkCode}&taskKey=${param.taskKey}&workerId=${param.workerId}";

		} else {
			if(link == "1"){
				url = "<c:url value='/lightpack/todo/listTodoView.do'/>";
			} else if (link == "2") {
				url = "<c:url value='/lightpack/todo/listOrderView.do'/>";
			} else {
				url = "<c:url value='/lightpack/todo/createTaskView.do'/>";
			}
		}
	} else if(type == "plan"){
		if(link == "1"){
			url = "<c:url value='/lightpack/planner/calendar/workalignmentInit.do'/>";
		} else if (link == "2") {
			url = "<c:url value='/lightpack/planner/mandate/formView.do?mode=iframe'/>";
		}
	} else if(type == "todoRead"){
		url = "<c:url value ='/lightpack/todo/readOrderView.do'/>?taskId=${param.taskId}";
	} else if(type == "approvalRead"){
		url = "<c:url value='/wfapproval/work/apdoc/readApDoc.do'/>?linkType=mywork&apprId=${param.apprId}&instanceId=${param.instanceId}"
	}

	iKEP.iFrameMenuOnclick(url);
	
	iKEP.iFrameResize('#contentIframe');
	
});

function resizeIframe() { 
	iKEP.iFrameContentResize();  
}
//]]>
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>




<script type="text/javascript" language="javascript">

(function($) {
	
	
	afterFileUpload = function(status, fileId, fileName, message, targetId) {
		$jq("#viewDiv").html(fileId);
		$jq("input[name=fileId]").val(fileId);
		
	};
	
	setPollId = function(pollId) {
		alert(pollId);
	};
	

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq('#fileuploadBtn').click(function(event) { 
			iKEP.fileUpload(event.target.id, '0', '1');	
		}); 
		
		$jq('#userExcelUpload').click(function(event) { 
			var url = iKEP.getContextRoot() + '/admin/member/user/excelForm.do';			
			iKEP.popupOpen(url, {width:800, height:600});
		});
		
		$jq('#groupExcelUpload').click(function(event) { 
			var url = iKEP.getContextRoot() + '/admin/group/group/excelForm.do';			
			iKEP.popupOpen(url, {width:800, height:600});
		});
		
		$jq('#pollAdd').click(function(event) { 
			var url = iKEP.getContextRoot() + '/socialpack/microblogging/pollForm.do';			
			iKEP.popupOpen(url, {width:600, height:400, callback:setPollId});
		});
		
		$jq('#printHtml').click(function(event) { 
			//var url = iKEP.getContextRoot() + '/support/fileupload/printHtml.do';			
			//iKEP.popupOpen(url, {width:800, height:600});
			
			var fileName = "output";
			var htmlDocument = "<html><body><img src=\"http://www.chilkatsoft.com/images/dude.gif\"><br><font face=\"Gulim\">한글 Test</font></body></html>";
			
			iKEP.downLoadApprDoc(fileName, htmlDocument);
		});
	
		
	});
	
})(jQuery); 
 
 </script>

 
<a class="button" href="#" >
	<span name="fileuploadBtn" id="fileuploadBtn"><ikep4j:message pre='ui.support.fileupload.header.detail' key='file' /></span>
</a>

<br/>

Uploaded File Id : <span id="viewDiv"></span> 
				   <input name="fileId" type="hidden" value="" title="fileId" />

<br/>	
<br/>	
Download File Test : <a href="<c:url value="/support/fileupload/downloadFile.do" />?fileId=100000776441">100000769860</a>
<br/>	
<br/>	

<br/>	
<br/>	
Download Excel Test : <a href="<c:url value="/support/fileupload/excelDownTest.do" />?fileId=100000729537">download...</a>
<br/>	
<br/>			   
				   
<a class="button" href="javascript:iKEP.viewImageFile('100000021306')" >
	<span name="viewImageBtn" id="viewImageBtn"><ikep4j:message pre='ui.support.fileupload.header' key='viewImage' /></span>
</a>


<br/><br/><br/><br/>

User Test:
<a class="button" href="#" >
	<span name="userExcelUpload" id="userExcelUpload"><ikep4j:message pre='ui.portal.excelupload.detail' key='title.user' /></span>
</a>
	
<br/><br/>

Group Test:
<a class="button" href="#" >
	<span name="groupExcelUpload" id="groupExcelUpload"><ikep4j:message pre='ui.portal.excelupload.detail' key='title.group' /></span>
</a>

<br/><br/>

Poll :
<a class="button" href="#" >
	<span name="pollAdd" id="pollAdd">pollAdd</span>
</a>

<br/><br/>

<a class="button" href="#" >
	<span name="printHtml" id="printHtml">printHtml</span>
</a>

<br/><br/>

<br/>requestURL : ${pageContext.request.requestURL}
<br/>requestURI : ${pageContext.request.requestURI}
<br/>contextPath : ${pageContext.request.contextPath}
<br/>servletPath : ${pageContext.request.servletPath}
<br/>localAddr : ${pageContext.request.localAddr}
<br/>localPort : ${pageContext.request.localPort}
<br/>localName : ${pageContext.request.localName}

	
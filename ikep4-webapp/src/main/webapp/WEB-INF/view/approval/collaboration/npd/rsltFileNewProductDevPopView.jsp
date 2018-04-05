<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 


<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<c:set var="preTitle"    	value="ui.approval.npd.view.title" />
<c:set var="preButton2"  	value="ui.approval.collaboration.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">

	var fileCnt = 0;
	
	<!--
	var dialogWindow = null;
	var fnCaller;
	var validator;
	var ecmFlg = "N";
	(function($){
		var dblClick=false;
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$(".cancelButton").click(function() {
				
				dialogWindow.close();
			});
		};
		ecmfileDelete = function(){
		    $("#editForm input[name=ecmCheck]:checked").each(function(index) { 
				$jq("#ecmTr_"+$(this).val()).remove();
			});

		};
		
		$(document).ready(function(){
		    
		    var uploaderOptions = {
		 		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		 		   isUpdate : false 
		 		}; 
		 		        
	 	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	 	    
	 	    $("#updateBtn").click(function(){
	 	    	
	 	    	$("#normalFileTb").show();
				dialogWindow.close();
				dialogWindow.callback();
	 	    });
		});
		
	})(jQuery);
	//-->
</script>
	<h1 class="none"><ikep4j:message pre='${preTitle}' key="popup.create" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preTitle}" key="rsltFile" /></h2> 
	</div>
	<!--//pageTitle End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail" style="width: 98%;">
		<div>
			<form id="editForm" name="editForm" method="post" action="">
				<!-- ecmFile 변수 -->	
				<input type="hidden" name="ecmFileId" value="" /> 
				<input type="hidden" name="ecmFileName" value="" />
				<input type="hidden" name="ecmFilePath" value=""/>  
				<input type="hidden" name="ecmFileUrl1" value=""/> 
				<input type="hidden" name="ecmFileUrl2" value=""/>
				
				<input type="hidden" name="mgntNo" value="${newProductDev.mgntNo }"/>
				<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
					<caption></caption>
					<colgroup>
						<col width="15%;">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th><ikep4j:message pre="${preTitle}" key="rsltFile" /></th>
							<td style="min-height: 40px;"><div id="fileDownload"></div></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="margin-top: 10px;"> 
			<ul>
				<c:if test="${npdPermission.rsltFileBtnActive eq true}">
					<li><a id="updateBtn" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				</c:if>
				<c:if test="${npdPermission.rsltFileBtnActive eq false}">
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='confirm'/></span></a></li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//blockDetail End-->

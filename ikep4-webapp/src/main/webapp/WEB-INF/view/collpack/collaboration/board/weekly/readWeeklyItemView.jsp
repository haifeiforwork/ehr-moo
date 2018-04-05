<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="message.collpack.collaboration.board.weekly.listBoardView.header" />
<c:set var="preDetail"  value="message.collpack.collaboration.board.weekly.createBoardView.detail" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.weekly.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.collaboration.board.weekly.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.board.weekly.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.collaboration.board.weekly.listBoardView.alert" />  
<c:set var="tempEcmFileCount" value="0"/>
<c:forEach var="tempEcmFileData" items="${weeklyItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
	<c:set var="tempEcmFileCount" value="${tempEcmFileCount+1}"/>
</c:forEach>
 <%-- 메시지 관련 Prefix 선언 End --%>
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript">
<!--   
var layerFlag = true;
var isSummary = "${weeklyItem.isSummary}";
(function($){	

	$(document).ready(function() { 
		var xmlhttp; 
		  
	    if(window.XMLHttpRequest){ // code for IE7+, Firefox, Chrome, Opera, Safari 
	        xmlhttp=new XMLHttpRequest(); 
	    }else{ // code for IE6, IE5 
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
	    } 
	    xmlhttp.open("POST","http://127.0.0.1:36482/",true); 
	    //Request에 따라서 적절한 헤더 정보를 보내준다 
	    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded"); 
	    xmlhttp.send(); 
	  
	    xmlhttp.onreadystatechange=function(){
	        if (xmlhttp.readyState==4 && xmlhttp.status==200){ 
	        	 $("#sfile").hide();
	           	 $("#lfile").show();
	        }
	        else if(xmlhttp.status==12029){
	        	$("#lfile").hide();
	          	 $("#sfile").show();
			}
	        else{
				
			}
	    } 
		iKEP.iFrameContentResize();
		
		$("input.datepicker").datepicker({
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		}); 
		
		
		$("#deleteWeeklyItemButton").click(function() {
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				location.href="<c:url value='/collpack/collaboration/board/weekly/deleteWeeklyItem.do'/>?itemId=${weeklyItem.itemId}&workspaceId=${weeklyItem.workspaceId}"; 
			}
			return false; 
		});
		
		if(isSummary == 1){
			//var weeklyTerm = encodeURIComponent('${weeklyItem.weeklyTerm}');
			//$("#subListWeeklyItem").load('<c:url value="/collpack/collaboration/board/weekly/subListWeeklyItemView.do"/>?workspaceId=${weeklyItem.workspaceId}&weeklyTerm='+weeklyTerm)
			//.error(function(event, request, settings) { alert("error"); });
			$("#weeklyList").click(function(){
				changeLayer();
			});
		}else{
			$("#weeklyList").hide();
			$("#subListWeeklyItem").hide();
		}
		
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 

	}); 

	changeLayer = function(){
		if(layerFlag){
			$("#subListWeeklyItem").hide();
			layerFlag = false;
		}else{
			$("#subListWeeklyItem").show();
			layerFlag = true;
		}
	};
	
})(jQuery); 


function fileDown(url){
	window.open(url, 'filedown', 'width=800px, height=670px, scrollbars=yes');
}
//-->
</script>
<form id="weeklyItemForm" name="weeklyItemForm">
<c:if test="${!docPopup}">
<!--pageTitle Start-->
		<div id="pageTitle">
			<h2><ikep4j:message pre="${preHeader}" key="readPageTitle"/></h2>
		</div>
<!--//pageTitle End-->
</c:if>
		<!--blockTableReadWrap Start-->
		<div class="blockTableReadWrap">
			<!--blockListTable Start-->
			<div class="blockTableRead">
				<div class="blockTableRead_t">
					<p>
						<c:if test="${weeklyItem.isSummary eq 1}">
							<span class="cate_block_5">
								<span class="cate_tit_5"><ikep4j:message pre="${preList}" key="summary"/></span>
							</span>&nbsp;
						</c:if>
						${weeklyItem.title}
					</p>
					<div class="summaryViewInfo">
						<span class="summaryViewInfo_name"><a href="#a">
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							${weeklyItem.registerName} ${weeklyItem.jobTitleName}
						</c:when>
						<c:otherwise>
							${weeklyItem.registerEnglishName} ${weeklyItem.jobTitleEnglishName}
						</c:otherwise>
						</c:choose>
						</a></span>
						<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
						<span>
							<c:if test="${!empty weeklyItem.registDate}">
								<ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${weeklyItem.registDate}"/>
							</c:if>
						</span>
						<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
						<span><ikep4j:message pre="${preList}" key="hitCount"/> <strong>${weeklyItem.hitCount}</strong></span>
						<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
						<span><ikep4j:message pre="${preSearch}" key="weeklyTerm"/> ${weeklyItem.weeklyTerm}</span>
					</div>
				</div>
				
				<c:if test="${weeklyItem.attachFileCount > 0}"> 
					<%-- <c:if test="${tempEcmFileCount > 0}">
					<div style="text-align:right;display:none;" id="lfile">
						<c:forEach var="ecmFileData" items="${weeklyItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
						<ul><li>
							<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
							<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl1}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
							</li></ul>
						</c:forEach>
					</div>
					<div style="text-align:right;display:none;" id="sfile">
					<c:forEach var="ecmFileData" items="${weeklyItem.ecmFileDataList}" varStatus="ecmFileDataStatus">
					<ul><li>
						<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" />
						<a href="" onclick="javascript:fileDown('${ecmFileData.fileUrl2}');return false;"><font style="color:gray;">${ecmFileData.fileRealName}</font></a>
						</li></ul>
					</c:forEach>
					</div>
				</c:if>
				<c:if test="${tempEcmFileCount < 1}"> --%>
					<div id="fileDownload"></div>
				<%-- </c:if> --%>
				</c:if>
			
				<div class="blockTableRead_c">
					<spring:htmlEscape defaultHtmlEscape="false"> 
						${weeklyItem.contents}
					</spring:htmlEscape>
				</div>
			</div>
			
			<!--//blockListTable End-->	
				<c:if test="${!docPopup}">
				<!--tableBottom Start-->
					<div class="tableBottom">	
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<c:if test="${weeklyPermission>1||weeklyItem.registerId eq user.userId}">
							<li><a class="button" href="<c:url value='/collpack/collaboration/board/weekly/updateWeeklyItemView.do?itemId=${weeklyItem.itemId}&workspaceId=${weeklyItem.workspaceId}'/>"><span><ikep4j:message pre="${preButton}" key="update"/></span></a></li>
							<li><a class="button" href="#a" id="deleteWeeklyItemButton"><span><ikep4j:message pre="${preButton}" key="delete"/></span></a></li>
							</c:if>
							<li><a class="button" href="<c:url value='/collpack/collaboration/board/weekly/listWeeklyItemView.do?workspaceId=${weeklyItem.workspaceId}&weeklyTerm=${weeklyItem.weeklyTerm}'/>"><span><ikep4j:message pre="${preButton}" key="list"/></span></a></li>							
						</ul>
					</div>
					<!--//blockButton End-->
					</div>
				</c:if>
			
		</div>
		
<c:if test="${!docPopup}">		
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><a href="#a" id="weeklyList"><ikep4j:message pre="${preList}" key="weeklySubList"/></a></h3>
		</div>
		<!--//subTitle_2 End-->	
		<!--blockListTable Start-->
		<div id="subListWeeklyItem" class="blockListTable">
			<table summary="자유게시판">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="15%"></th>
							<th scope="col" width="*"><span><ikep4j:message pre="${preList}" key="weeklyStatus"/></span></th>
							<th scope="col" width="16%"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="weeklyItem" items="${searchResult.entity}" varStatus="idx">
						<tr <c:if test="${empty weeklyItem.title}">class="bgGray"</c:if>>
							<td>
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										${weeklyItem.userName}&nbsp;${weeklyItem.jobTitleName}
									</c:when>
									<c:otherwise>
										${weeklyItem.userEnglishName}&nbsp;${weeklyItem.jobTitleEnglishName}
									</c:otherwise>
								</c:choose>	
							</td>
							<td class="textLeft">
								<c:if test="${!empty weeklyItem.title}">
									<a href="#a">
										<c:if test="${weeklyItem.isSummary eq 1}">
											<span class="cate_block_5"><span class="cate_tit_5"><ikep4j:message pre="${preList}" key="summary"/></span></span>&nbsp;
											<strong>
										</c:if>
										<c:choose>
											<c:when test="${weeklyPermission>0}">
												<a href="<c:url value='/collpack/collaboration/board/weekly/readWeeklyItemView.do?itemId=${weeklyItem.itemId}&workspaceId=${workspaceId}'/>">${weeklyItem.title}</a>
											</c:when>
											<c:otherwise>
												${weeklyItem.title}
											</c:otherwise>
										</c:choose>
										<c:if test="${weeklyItem.isSummary eq 1}">
											</strong>
										</c:if>
									</a>
								</c:if>
								<c:if test="${empty weeklyItem.title && weeklyItem.memberId==user.userId}">
									<a id="createWeeklyItemButton" class="button_s" href="<c:url value='/collpack/collaboration/board/weekly/createWeeklyItemView.do?workspaceId=${workspaceId}'/>"><span><ikep4j:message pre='${preButton}' key='createWeeklyItem'/></span>
									</a>
								</c:if>
							</td>
							<td>
								<c:if test="${!empty weeklyItem.registDate}">
									<ikep4j:timezone pattern="yyyy.MM.dd" date="${weeklyItem.registDate}"/>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		<!--//blockListTable End-->
</c:if>
</form>
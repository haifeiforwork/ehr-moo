<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.listBoardItemVersionView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.boardItem.listBoardItemVersionView.list" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" /> 
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" />  
<%-- 메시지 관련 Prefix 선언 End --%>

<style>
.del {color:red; text-decoration:line-through }
.ins {color:blue;text-decoration:underline}
</style>

<script type="text/javascript" src="<c:url value="/base/js/units/collpack/collaboration/board/diff.js"/>"></script>
<script type="text/javascript">
<!--   
				
(function($){	
	
	var reContent = "";
	 
	displayContent = function(idx,version){	
		var compareIdx=eval(idx)+1;

		var currContents = $("#contents"+idx).val();

		var compareContents = 	"";

		if ( version !=1.0){			
			compareContents = $("#contents"+compareIdx).val();
			//alert(compareContents);
			//alert(currContents);
			//reContent = diffString( compareContents , currContents );	
			reContent = currContents;
		}else {			
			reContent = currContents;
		}
		
		if($jq("#itemContent"+idx).is(':hidden'))
		{
			$jq("#itemContent"+idx).show();
			$("#history_content"+idx).html(reContent);
		}
		else
			$jq("#itemContent"+idx).hide();
			
		iKEP.iFrameContentResize();

	};


	returnBoardItem = function(versionId) { 
		if(confirm("<ikep4j:message pre="${preMessage}" key="returnBoardItem" />")) {
			$.get("<c:url value='/collpack/collaboration/board/boardItemVersion/returnBoardItemVersion.do'/>", {"versionId" : versionId})
			.success(function(data) {
				
				if(data=='1'){
					alert('<ikep4j:message pre="${preMessage}" key="successReturnBoardItem" />');
					location.href="<c:url value='/collpack/collaboration/board/boardItemVersion/listBoardItemVersionView.do?workspaceId=${searchCondition.workspaceId}&boardId=${searchCondition.boardId}&itemId=${searchCondition.itemId}'/>";
				}
				else{
					alert('<ikep4j:message pre="${preMessage}" key="failReturnBoardItem" />');
				}
			})
			.error(function(event, request, settings) { alert('<ikep4j:message pre="${preMessage}" key="failReturnBoardItem" />'); });  
		} 
		
 
	}; 
	
	$(document).ready(function() {    

		iKEP.iFrameContentResize();
		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=versionIds]").attr("checked", $jq(this).is(":checked"));  
		});
		
		$("#itemViewButton").click(function() { 
		    location.href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${searchCondition.boardId}&itemId=${searchCondition.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";  
		});
		
		$jq("#compareVersionButton").click(function(event) {
			event.preventDefault();
			
			var countCheckBox	=	$jq("input[name=versionIds]:checkbox:checked").length;

			if(countCheckBox==2)
			{
				$jq('form[name=itemVersionForm]').attr({
					action:"<c:url value='/collpack/collaboration/board/boardItemVersion/compareBoardItemVersionView.do'/>"
				});
				$jq("#itemVersionForm").attr({method:'get'}).submit();
				return false;
			}
			else
			{
				alert('<ikep4j:message pre="${preMessage}" key="compareSelectItem" />');
				return false;
			}

			return false; 
		});

		
		$("#itemVersionForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchBoardItemButton").click(); 
			$jq("#itemVersionForm").submit();
		}); 

	});
	
})(jQuery);
//-->
</script>

<!--mainContents Start-->
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<form id="itemVersionForm" name="itemVersionForm" method="get" action="<c:url value='/collpack/collaboration/board/boardItemVersion/listBoardItemVersionView.do'/>">  
	
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.workspaceId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
	</spring:bind>		
	<spring:bind path="searchCondition.boardId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>  
	<spring:bind path="searchCondition.itemId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>  
	
	<input type="hidden" id="searchConditionString" name="searchConditionString" value = '<c:out value="${searchConditionString}" escapeXml="false"/>'/>
	<input type="hidden" id="popupYn" name="popupYn" value = '<c:out value="${popupYn}" escapeXml="false"/>'/>
	
	
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 

</div> 
<!--//pageTitle End--> 
 
<div class="corner_RoundBox01" style="margin-bottom:20px;">
	<ikep4j:message pre='${preHeader}' key='pageMessage' />
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>					
</div>



<div class="blockListTable">
	<!--tableTop Start-->
	<div class="tableTop">
	
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${boardCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
		</div>
		

						
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	
		
		
	<!--blockListTable Start--> 
	<table id="boardItemTable" summary="<ikep4j:message pre="${preList}" key="summary" />">   
		<caption></caption> 
		<!--
		<col style="width: 5%;"/>
		-->
		<col style="width: 7%;"/>
		<col style="width: 7%;"/>
		<col style="width: 56%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>		
		<thead>
			<tr>
				<!--
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				-->
				<th scope="col"><ikep4j:message pre='${preList}' key='versionId' /></th>				
				<th scope="col"><ikep4j:message pre='${preList}' key='attachFileCount' /></th>			
				<th scope="col"><ikep4j:message pre='${preList}' key='title' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='updaterName' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='updateDate' /></th>
				<th scope="col"><ikep4j:message pre='${preList}' key='returnItem' /></th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
			    
					<c:forEach var="boardItemVersion" items="${searchResult.entity}" varStatus="status">
					<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine">
						<!-- 
						<td><input id="versionIds" name="versionIds" class="checkbox" title="checkbox" type="checkbox" value="${boardItemVersion.versionId}" /></td>
						 -->
						<td>${boardItemVersion.version}</td>
						<td>
							<c:if test="${boardItemVersion.attachFileCount > 0}">
								<img src="<c:url value='/base/images/icon/ic_attach.gif' />" alt="<ikep4j:message pre='${preList}' key='attachFileCount' />" /> 
							</c:if>
						</td>
						
						<td class="textLeft">
						<img src="<c:url value='/base/images/icon/ic_plus_s2.gif' />" alt=""/>
				 		<span class="indent_${indent}">
				 		<a name="boardItem" href="#a" onclick="javascript:displayContent('${status.count}','${boardItemVersion.version}')">
				 		 ${boardItemVersion.title}</a> </span>
				 		<input id="contents${status.count}" name="contents${status.count}" type="hidden" value="${fn:escapeXml(boardItemVersion.contents)}" />
						</td> 
						<td>
							<c:set var="user"   value="${sessionScope['ikep.user']}" />
							<c:set var="portal" value="${sessionScope['ikep.portal']}" />
							<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
									<a href="#a" title="${boardItemVersion.registerName} ${boardItemVersion.jobTitleName}" onclick="iKEP.showUserContextMenu(this, '${boardItemVersion.registerId}', 'bottom')">${boardItemVersion.registerName} ${boardItemVersion.jobTitleName}</a>
								</c:when>
								<c:otherwise> 
									<a href="#a" title="${boardItemVersion.registerEnglishName} ${boardItemVersion.jobTitleEnglishName}" onclick="iKEP.showUserContextMenu(this, '${boardItemVersion.registerId}', 'bottom')">${boardItemVersion.registerEnglishName} ${boardItemVersion.jobTitleEnglishName}</a>
								</c:otherwise>
							</c:choose>	 
						</td>
						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItemVersion.registDate}"/></td>
						<td><a onclick="returnBoardItem('${boardItemVersion.versionId}')" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='returnItem'/>"><span><ikep4j:message pre='${preButton}' key='returnItem'/></span></a></td>
					</tr>
					<tr id="itemContent${status.count}" style="display:none;">
					<td colspan='7' class="textLeft">
			
	        		<div id="history_content${status.count}" class="history_content"></div>

					</td>
					</tr>
					
					</c:forEach>
								      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table> 

	
	
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End--> 
	
</div>
</form>
	
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<!-- <li><a id="compareVersionButton" class="button" title="<ikep4j:message pre='${preButton}' key='compareVersion'/>"><span><ikep4j:message pre='${preButton}' key='compareVersion'/></span></a></li> -->
	<li><a id="itemViewButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='itemView'/>"><span><ikep4j:message pre='${preButton}' key='itemView'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->
			
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.board.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.board.boardItem.message.script" />


<c:if test="${!empty searchResult.entity}">
<c:forEach var="info" items="${searchResult.entity}">

</c:forEach>
</c:if>
 
 
 
 
 
 
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/jquery/splitter.js'/>"></script>
<script type="text/javascript">

<!--   
(function($){	
	viewPage = function(tmpPage){
		<c:forEach var="kmsBoard" varStatus="varStatus" items="${boardList}">  
			if(tmpPage == "tabs-"+"${varStatus.index+1}"){
				$("#searchBoardItemForm input[name=boardId]").val("${kmsBoard.boardId}");
				$("#searchBoardItemButton").click();
			}
    	</c:forEach>
	};
	
	$(document).ready(function() {    
		iKEP.iFrameContentResize();
		var tmpNum = "0";
		<c:forEach var="kmsBoard" varStatus="varStatus" items="${boardList}">     
		//alert("${board.boardParentId}");
		//alert("${kmsBoard.boardParentId}");
			<c:choose>
				<c:when test="${searchCondition.boardId == kmsBoard.boardId}">
					tmpNum = "${varStatus.index}";
				</c:when>
				<c:when test="${board.boardParentId == kmsBoard.boardId}">
				    tmpNum = "${varStatus.index}";
				</c:when> 
			</c:choose>
        </c:forEach>
		
		tab = $("#divTab2").tabs({
			selected : tmpNum, 
			select : function(event, ui) {
				viewPage($(ui.panel).attr("id"));
			}
		}); 
		
		
		sort = function(sortColumn, sortType) {
			$("#searchBoardItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchBoardItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchBoardItemButton").click();
		}; 
		//뷰모드 체인지 함수
		changeViewMode = function(changeViewMode) { 
			$("#searchBoardItemForm input[name=viewMode]").val(changeViewMode);
			$("#searchBoardItemButton").click(); 
			return false; 
		}; 
		
		$("#searchBoardItemButton").click(function() {   
			
			$("input[name=searchStartRegDate]").val($("input[name=searchStartRegDate1]").val().replace(/\./g, ""));
			$("input[name=searchEndRegDate]").val($("input[name=searchEndRegDate1]").val().replace(/\./g, ""));
			
			$("#searchBoardItemForm").attr({method:'GET'}).submit(); 
			return false; 
		});
		
		
		$("#checkboxAllBoardItem").click(function() { 
			$("#searchBoardItemForm input[name=boardItemIds]").attr("checked", $(this).is(":checked"));  
		});  

		
		$("#searchBoardItemForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchBoardItemButton").click(); 
			$("#searchBoardItemButton").click(); 
		}); 
		
		var objSplit = null;
		
		$("a[name=boardItem]").click(function() {
			$("*[name=boardItemLine]").removeClass("bgSelected");
			
			$(this).parents("*[name=boardItemLine]").addClass("bgSelected");
			
			 if(objSplit == null  ) {
				return true;
			 } else if(objSplit.getType() == "v") { 
				 
				$("#BottomPane").remove();
				$("#RightPane").load($(this).attr("href") + "&layoutType=layoutVertical");
				return false;
				
			} else if(objSplit.getType() == "h") { 
				$("#RightPane").remove();
				$("#BottomPane").load($(this).attr("href") + "&layoutType=layoutHorizental");
				return false;
			} else {  
				return true;
			}
			 
			 return false;
			
		}); 
		
		$("#btnLayoutNormal").click(function() {  
			if(objSplit != null) {
				objSplit.destroy();
				objSplit = null;
				$("input[name=layoutType]").val("layoutNormal");
			}
		});
		
		$("#btnLayoutVertical").click(function(event) { 
			if(objSplit == null) {
				var options = {type : "v", callback:function($divContent) {}}; 
				objSplit = new iKEP.Splitter("#splitterBox", options);
				$("input[name=layoutType]").val("layoutVertical");
			} else {
				objSplit.destroy();
				objSplit = null;
				$(this).trigger("click"); 
			} 
		}); 
		
		$("#btnLayoutHorizental").click(function(event) {   
			if(objSplit == null) {
				var options = {type : "h", callback:function($divContent) {}}; 
				objSplit = new iKEP.Splitter("#splitterBox", options);
				$("input[name=layoutType]").val("layoutHorizental");
			} else {
				objSplit.destroy();
				objSplit = null;
				$(this).trigger("click"); 
			} 
		}); 
		if($("input[name=layoutType]").val() ==  "layoutVertical") {
			$("#btnLayoutVertical").click();
		} else if($("input[name=layoutType]").val() ==  "layoutHorizental") {
			$("#btnLayoutHorizental").click();
		} else {
			
		} 
		
		$("#moveBoardItemButton").click(function() {  
			var $sel = $jq("input[name=boardItemIds]:checked");
			if($sel.length <1) {
				alert("<ikep4j:message pre="${preMessage}" key="emptySelectedItem" />");
				return;	
			}
			
			iKEP.showDialog({
				title: "<ikep4j:message pre="${preMessage}" key="moveBoardItem" />",
				url: "<c:url value='/collpack/kms/board/boardItem/viewBoardTree.do' />?isKnowhow=${searchCondition.isKnowhow}&orgBoardId=${searchCondition.boardId}",
				modal: true,
				width: 400,
				height: 300,
				callback : moveBoardItem
			});
		});	
		
		$("#adminMultiDeleteBoardItemButton").click(function() {  
			var countCheckBox	=	$("input[name=boardItemIds]:checkbox:checked").length;
			if(countCheckBox>0)
			{
				if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
					$jq.ajax({
						url : "<c:url value='/collpack/kms/board/boardItem/adminMultiDeleteBoardItem.do'/>?"+$jq("#searchBoardItemForm").serialize(),
						type : "get",
						success : function(result) {
							$("#searchBoardItemButton").click();
						}
					});		
				}
			}else{
				//체크 없음 메세지
				alert("<ikep4j:message pre="${preMessage}" key="emptySelectedItem" />");
			}
			
		}); 
		

		
		moveBoardItem = function(result) {
			var orgBoardId = result.orgBoardId,
				targetBoardId = result.targetBoardId;

			var itemIds = new Array();
			$("#searchBoardItemForm input[name=boardItemIds]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});
			
			$.get("<c:url value='/collpack/kms/board/boardItem/moveBoardItem.do'/>?orgBoardId="+orgBoardId +"&targetBoardId="+targetBoardId+"&itemIds="+itemIds)
			.success(function(data) {
				location.href="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?isKnowhow=${searchCondition.isKnowhow}&boardId=${searchCondition.boardId}'/>";
			})
			.error(function(event, request, settings) { alert("error"); }); 		
		};
		
		//달력
		$("input[name=searchStartRegDate1]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=searchEndRegDate1]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				$("#searchBoardItemButton").click();
				return false
			}
		}); 		
	}); 
	
})(jQuery);
//-->
</script>

<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchBoardItemForm" method="get" action="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	  
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<input name="boardId" type="hidden" value="${searchCondition.boardId}"/>
	<input name="isKnowhow" type="hidden" value="${searchCondition.isKnowhow}"/>
	<input name="searchStartRegDate" id="searchStartRegDate" value="${searchCondition.searchStartRegDate}" type="hidden"  />
	<input name="searchEndRegDate" id="searchEndRegDate" value="${searchCondition.searchEndRegDate}" type="hidden"  />
	<input name="pageGubun" type="hidden" value="${pageGubun}"/>
		
<!--mainContents Start-->

	<h1 class="none"><ikep4j:message pre="${preHeader}" key="latestPageTitle" /></h1> 
<!--pageTitle Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>
			<c:if test="${searchCondition.isKnowhow eq 0}"> 업무노하우  </a> > </c:if>
			<c:if test="${searchCondition.isKnowhow eq 1}"> 일반정보  </a> > </c:if>
			<spring:bind path="board.parentList">
					<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}"> 
						<c:if test="${not varStatus.last}">
							${boardItem.boardName} > 
						</c:if> 
						<c:if test="${varStatus.last}">${boardItem.boardName} </c:if> 
					</c:forEach>
			</spring:bind>	
		</h2>   
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${boardCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>	  
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="tableSearch">
				<tbody>
                    <tr>
                        <th scope="row" width="5%">등록일</th>
                        <td width="40%">
				           	<input name="searchStartRegDate1" id="searchStartRegDate1" title="출처" class="inputbox w20 datepicker" type="text" value="${searchCondition.searchStartRegDate1}"/>
				           	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
				           	 - 
				           	<input name="searchEndRegDate1" id="searchEndRegDate1" title="출처" class="inputbox w20 datepicker" type="text" value="${searchCondition.searchEndRegDate1}"/>
				           	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>	
                        </td>
                        <th scope="row" width="5%">조건선택</th>
                        <td width="45%">
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
									<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
									<c:if test="${boardPermission > 0 || searchCondition.isKnowhow eq 0}">
									<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
									</c:if>
								</select>		
							</spring:bind>		
							<spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" value="${status.value}" id="searchWord" type="text" class="inputbox" title='' size="20"/>
							</spring:bind>
                        </td>
                    </tr>														
                </tbody>
            </table>
            <div class="searchBtn"><a id="searchBoardItemButton" name="searchBoardItemButton" href="#a"class="ic_search"><span><ikep4j:message pre='${preButton}' key='search'/></span></a></div> 
		</div>
	</div> 
	<div id="divTab2" class="iKEP_tab_poll">
            <ul>
            	<c:forEach var="kmsBoard" varStatus="varStatus" items="${boardList}"> 
            		<li><a href="#tabs-${varStatus.index+1}" >${kmsBoard.boardName}</a></li>
            	</c:forEach>
            </ul>
            <div> 
            	<c:forEach var="kmsBoard" varStatus="varStatus" items="${boardList}">       
                <div id="tabs-${varStatus.index+1}"></div>
                </c:forEach>
            </div>
	</div>
	
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div class="blockListTable" id="listDiv"> 
			<%@ include file="/WEB-INF/view/collpack/kms/board/boardItem/listTypeBoardItemView.jsp"%>
			
			<!--Page Number Start-->
			<div class="pageNum">	
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
			</spring:bind>
			<!--//Page Number End-->
			</div>
		</div>
		
		<!--blockButton Start-->
		<div class="blockButton">
			<ul>
					<li>
						<!--a id="createAnnounceItemButton" class="button" href="<c:url value='/collpack/kms/announce/createAnnounceItemView.do?workspaceId=${searchCondition.workspaceId}'/>" -->
						<a id="createAnnounceItemButton" class="button" href="<c:url value='/collpack/kms/board/boardItem/createBoardItemView.do?boardId=${searchCondition.boardId}&isKnowhow=${searchCondition.isKnowhow}&searchConditionString=${searchConditionString}'/>">
							<span><ikep4j:message pre='${preButton}' key='create'/></span>
						</a>
					</li>
				<c:if test="${boardPermission > 0}">
					<li>
						<a id="moveBoardItemButton" class="button" href="#a">
							<span><ikep4j:message pre='${preButton}' key='move'/></span>
						</a>
					</li>
					<li>
						<a id="adminMultiDeleteBoardItemButton" class="button" href="#a">
							<span><ikep4j:message pre='${preButton}' key='delete'/></span>
						</a>
					</li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
		
	</div>
</form>		

</div>

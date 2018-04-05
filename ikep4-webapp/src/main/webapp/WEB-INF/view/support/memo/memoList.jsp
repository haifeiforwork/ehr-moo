<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 

<c:set var="common" value="ui.support.memo" />
<c:set var="preMsg"  value="ui.support.memo.message" />
<c:set var="buttons"  value="ui.support.memo.buttons" />
<c:set var="preSearch"  value="ui.support.memo.searchCondition" />

 <%-- 메시지 관련 Prefix 선언 End --%>

<style>
a.button_s, a.button_s span { position:relative; margin-right:3px; display:inline-block; border:0; white-space:nowrap; background:url("<c:url value='/base/images/theme/theme02/basic/btn_basic_s.gif'/>") no-repeat; overflow:visible; }
a.button_s { text-decoration:none; padding:0; background-position:left top; }
a.button_s span { height:20px; font-size:0.9em; *font-family:'돋움', 'Dotum', Tahoma; font-weight:normal; left:7px; color:#111; padding:0px 7px 0 0; *padding:0 7px 0 0; line-height:20px; background-position:right top; cursor:pointer; }
a.button_s.selected { background:url("<c:url value='/base/images/theme/theme02/basic/btn_basic_s_selected.gif'/>") no-repeat left top; }
a.button_s.selected span { color:#c50050; background:url("<c:url value='/base/images/theme/theme02/basic/btn_basic_s_selected.gif'/>") no-repeat right top; }
</style>

<script type="text/javascript" language="javascript">

(function($) {
	
	recordCnt = function() {
		$("#memoForm").submit();
	};
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$(".pageNum").attr("style","background:none; border-bottom:none; padding-top:5px;");

    	if("${returnMsg}"=="SAVE") {
    		alert("<ikep4j:message pre='${preMsg}' key='saveMemo'/>");
    	}
    	if("${returnMsg}"=="MODIFY") {
    		alert("<ikep4j:message pre='${preMsg}' key='modifyMemo'/>");
    	}
    	if("${returnMsg}"=="DELETE") {
    		alert("<ikep4j:message pre='${preMsg}' key='deleteMemo'/>");
    	}

		//delete all check
		$jq("#checkboxAllMemo").click(function() { 
			//alert("11111"+$(this).attr("checked"));
			$jq("#memoForm input[name=checkboxMemo]").attr("checked", this.checked);  
		});  

    	$jq("#btnClose").click(function() {
    		window.close();
		});
    	$jq("#btnCloseTop").click(function() {
    		window.close();
		});
    	$jq("#searchButton").click(function() {
    		$("#memoForm").submit();
		});

		$("#multiDeleteButton").click(function() {  
			var itemIds = new Array();
			
			$("#memoForm input[name=checkboxMemo]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			if(itemIds.length>0){
				if(confirm("<ikep4j:message pre="${preMsg}" key="deleteItem" />")) {
					$("#memoForm").ajaxLoadStart(); 
					
					$.post("<c:url value='/support/memo/multiDeleteMemo.do'/>", { "itemId" : itemIds.toString()}) 
					.success(function(data) {  
						parent.location.href="<c:url value='/support/memo/memoList.do'/>?returnMsg=DELETE";
					})
					.error(function(event, request, settings) { alert("error"); });  
				}  
			}else{
				alert("<ikep4j:message pre='${preMsg}' key='selectDeleteItem'/>");
			}
		});
		
		$("#insert").click(function(){
			location.href="<c:url value='/support/memo/inputMemo.do'/>?searchConditionString=${searchConditionString}";
		});
		
	});
	
})(jQuery);  

	
</script>

<body style="overflow: hidden;">
	<div class="memo_header">
		<div><a href="#a" id="btnCloseTop"><img src="<c:url value='/base/images/common/btn_memo_close.png'/>" alt="" /></a></div>
	</div>

	
	<div  class="ui-layout-center"   style="width:100%;height:495px;overflow-x: hidden;background:#fff;">

		<form id="memoForm" method="post" action="memoList.do">
		<div class="memo_list">
			<div class="memo_search">
				<div style="float:left; width:150px; margin:28px 0px 0 43px;">
					<input id="checkboxAllMemo" class="checkbox" title="checkbox" type="checkbox" value="" />
					<input type="hidden" name="pageIndex" value="${searchCondition.pageIndex}">
					&nbsp;&nbsp;&nbsp;<select name="pagePerRecord" title='' onchange="recordCnt()"/>
						<option value="15" <c:if test="${searchCondition.pagePerRecord eq 15}">selected="selected"</c:if>>15</option>
						<option value="20" <c:if test="${searchCondition.pagePerRecord eq 20}">selected="selected"</c:if>>20</option>
						<option value="30" <c:if test="${searchCondition.pagePerRecord eq 30}">selected="selected"</c:if>>30</option>
						<option value="40" <c:if test="${searchCondition.pagePerRecord eq 40}">selected="selected"</c:if>>40</option>
						<option value="50" <c:if test="${searchCondition.pagePerRecord eq 50}">selected="selected"</c:if>>50</option>
					</select> 
				${searchCondition.pageIndex}/ ${searchCondition.pageCount} <!-- <ikep4j:message pre='${preSearch}' key='page' /> -->(<!-- <ikep4j:message pre='${preSearch}' key='pageCount.info' /> -->${searchCondition.recordCount})
				</div>
				<div class="tableSearch" style="margin:28px 35px 0 0; float:right; ">
						<input name="searchWord" value="${searchCondition.searchWord}" type="text" class="inputbox" size="20" />
					<a href="#a" id="searchButton" class="ic_search"><span><ikep4j:message pre='${common}' key='search' /></span></a>  
				</div>
			</div>
			<div class="memo_table">
				<table class="">
					<colgroup>
						<col width="12%"/>
						<col width="68%"/>
						<col width="20%"/>
					</colgroup>
					<tbody>
						

		<!-- 	
		<thead>
		<tr>
			<th scope="col" width="5%"><input id="checkboxAllMemo" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
			<th scope="col" width="5%"><ikep4j:message pre='${common}' key='rnum'/></th>
			<th scope="col" width="*" ><ikep4j:message pre='${common}' key='body'/></th>
			<th scope="col" width="15%"><ikep4j:message pre='${common}' key='registDate'/></th>
		</tr>
		</thead> -->


						<c:if test="${empty memoList.entity}">
						<tr>
							<td style="text-align:right; padding-right:3px;"> </td>
							<td style="padding-left:10px;"><ikep4j:message pre='${preMsg}' key='noData'/></td>
							<td style="text-align:center;"> </td>
						</tr>
						</c:if>
						<c:forEach var="tmp" items="${memoList.entity}" varStatus="inx">
						<tr>
							<td style="text-align:right; padding-right:3px;"><input name="checkboxMemo" class="checkbox" title="checkbox" type="checkbox" value="${tmp.itemId}" /></td>
							<td style="padding-left:10px;"><div class="ellipsis"><a href="memoView.do?itemId=${tmp.itemId}&searchConditionString=${searchConditionString}">${tmp.contents}</a></div></td>
							<td style="text-align:center;"><a href="memoView.do?itemId=${tmp.itemId}&searchConditionString=${searchConditionString}">${fn:substring(tmp.registDate,0,16)}</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</form>
		<div class="memo_bottom">
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="memoForm" pageIndexInput="${status.expression}" searchCondition="${searchCondition}"/>
			</spring:bind> 
			<!--blockButton Start-->
			<div class="memo_btnWrap">
				<a class="button_s" id="insert" href="#a"><span><ikep4j:message pre='${buttons}' key='insert'/></span></a>
				<a class="button_s" id="multiDeleteButton" href="#a"><span><ikep4j:message pre='${buttons}' key='delete'/></span></a>
				<a class="button_s" id="btnClose" href="#a"><span><ikep4j:message pre='${buttons}' key='close'/></span></a>
			</div>
			<!--//blockButton End-->
		</div>
	</div>

</body>

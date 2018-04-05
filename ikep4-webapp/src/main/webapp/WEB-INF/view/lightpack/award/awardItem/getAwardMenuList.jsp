  <%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preList"    value="ui.lightpack.award.awardItem.listAwardView.list" />
<c:set var="prefix"    value="ui.lightpack.award.awardItem.listAwardView.list" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<![CDATA[

function mouseOver(obj){obj.className = "bgSelected";}
function mouseOut(obj){obj.className = "";}

(function($) {
	$jq(document).ready(function() { 
		
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		});
		
		$("#checkboxAllAwardItem").click(function() { 
			$("#searchForm input[name=checkboxAwardItem]").attr("checked", $(this).is(":checked"));  
		});  
		$("#adminMultiDeleteAwardItemButton, #adminMultiDeleteAwardItemButton1").click(function() {  
			var itemIds = new Array();
			
			$("#searchForm input[name=checkboxAwardItem]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			
			if(confirm("변경하시겠습니까?")) {
				$("#searchForm").ajaxLoadStart(); 
				
				$.post("<c:url value='/lightpack/award/awardItem/updateAwardMenuList.do'/>", {"awardId" : "${award.awardId}", "itemId" : itemIds.toString(), "popupYn" : ${popupYn}}) 
				.success(function(data) {  
					getList();
				})
			}  
		});  
	});
	
	getList = function() {
		$("#searchForm")[0].submit();
	};
    
    sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
})(jQuery);
//]]>
</script>

<form id="searchForm" method="post" action="<c:url value='/lightpack/award/awardItem/getAwardMenuList.do'/>" onsubmit="return false;">

<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>모바일 메뉴관리</h2>
	<div class="listInfo">  
		<%-- div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div--%>
	</div>	  
	<div class="tableSearch"> 
	</div>		
	<div class="clear"></div>
</div>
<!--//tableTop End-->
	
<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">
			<table id="awardItemTable" >   
				<caption></caption> 
				<col style="width: 15%;"/>
				<col style="width: 15%;"/>
				<col style="width: 70%;"/>
				<%-- col style="width: 7%;"/>
				<col style="width: 7%;"/--%>
				<thead>
					<tr>
						<th scope="col"><input id="checkboxAllAwardItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
						<th scope="col">
							번호
						</th>
						<th scope="col">
							게시판명
						</th>
					</tr>
				</thead> 
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="7" class="emptyRecord"><ikep4j:message pre='${prefix}' key='emptyRecord' /></td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="awardItem" items="${searchResult.entity}" varStatus="status"> 
							<tr class="<c:if test="${itemDisplay eq 1}"></c:if> awardItemLine"  onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
								<td>
									<input name="checkboxAwardItem" class="checkbox" title="checkbox" type="checkbox" value="${awardItem.awardId}" <c:if test="${awardItem.tempSave=='1' }">checked=checked</c:if> /> 
								</td>
								<td>${status.count }</td>
								<td align="center">${awardItem.awardName}</td>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
		</div>
		<!--//blockListTable End-->
	</div>
</div>
			<!--blockButton Start-->
			<div class="blockButton"> 
				
				<li><a id="adminMultiDeleteAwardItemButton" class="button" href="#a"><span>변경</span></a></li>

			</div> 
			<!--//blockButton End-->  
<!--//splitterBox End-->
</form>
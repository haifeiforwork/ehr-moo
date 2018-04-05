<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.lastList" />
<c:set var="preUiMenu" 			value="ui.collpack.ideation.ideaListMenu" />
<c:set var="preUiQna" 			value="ui.collpack.qna.qnaMenu" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function(){
		
		var f = $jq('#searchForm');
		
		//pagePer 초기화
		var pagePer = "${param.pagePer}";
		
		f.find('select[name=pagePer] option').each(function (){
			if(this.value == pagePer){
				this.selected = true;
			}
		});
		
		//searchColumn 초기화
		var searchColumn = "${param.searchColumn}";
		
		f.find('select[name=searchColumn] option').each(function (){
			if(this.value == searchColumn){
				this.selected = true;
			}
		});
		
		//oder type 초기화
		var orderType = "${param.orderType}";
		
		$jq("#orderFrame span span").each(function(){
			this.setAttribute('class','');
		});
		
		$jq("#orderFrame span span img").each(function(){
			this.src.replace("ic_cafesort_down_on", "ic_cafesort_down")
		});
		
		
		var orFr;
		if(orderType == 'hit'){
			orFr = $jq("#orderFrame span span:eq(1)");
		} else if(orderType == 'recommend'){
			orFr = $jq("#orderFrame span span:eq(2)");
		} else {
			orFr = $jq("#orderFrame span span:eq(0)");
		}
		
		orFr.attr('class','current');
		var imgSrc = orFr.find("img").attr('src');
		orFr.find("img").attr('src', imgSrc.replace("ic_cafesort_down", "ic_cafesort_down_on"));
	
	});

	
	function goSearch(){
		
		var f = $jq('#searchForm');
		
		var searchColumn = f.find('select[name=searchColumn]').val();
		var searchWord = f.find('input[name=searchWord]').val();
		
		/*
		if(!searchWord){
			alert('검색어를 넣어주세요');
			return;
		}
		*/

		$jq("#tagResult").ajaxLoadStart(); 
		
		if(searchColumn == "tag"){
			
			$jq("#orderFrame").hide();
			iKEP.tagging.tagSearchByName(searchWord, '<%=TagConstants.ITEM_TYPE_IDEATION%>');
		} else {
			
			f.submit();
			
		}
		
	}
	

	function orderChange(val){
		
		var f = $jq('#searchForm input[name=orderType]').val(val);
		 
		goSearch();
	}

	//파라미터
	var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != ''}">&${ram.key}=${ram.value}</c:if></c:forEach>';
	

	function delMul(){

		if(window.confirm("<ikep4j:message key='message.collpack.ideation.deleteConfirm'/>")){

			var itemIdList = "";
			$jq("input[name=delCheck]").each(function(){
				if(this.checked == true){
					itemIdList += this.value + ",";
					
				}
			});

			if(itemIdList.length > 0){
				itemIdList = itemIdList.substring(0, itemIdList.length-1);
	
				location.href="deleteIdeaMul.do?itemId="+itemIdList+param;
			}

		}
		
	}
		
	//]]>		
</script>



<h1 class="none">Contents Area</h1>
			

<!--tableTop Start-->
<form id="searchForm" action="">
	<input type="hidden" name="pageIndex" value="1"/>
	<input type="hidden" name="orderType" value="${param.orderType}"/>
	<input type="hidden" name="isBest" value="${param.isBest}"/>
	<input type="hidden" name="isAdopt" value="${param.isAdopt}"/>
	<input type="hidden" name="isBusiness" value="${param.isBusiness}"/>
	<input type="hidden" name="docIframe" value="${param.docIframe}"/>
	
	<div class="tableTop">
	<h2>
		
		<c:choose>
			<c:when test="${param.isOpenContest == 1 }">
				<ikep4j:message pre='${preUi}' key='titleOpenContest'/>
			</c:when>
			<c:when test="${param.isOpenContest == 2 }">
				<ikep4j:message pre='${preUi}' key='titleCloseContest'/>
			</c:when>
			<c:when test="${param.isBest == 1 }">
				<ikep4j:message pre='${preUi}' key='titleBest'/>
			</c:when>
			<c:when test="${param.isAdopt == 1 }">
				<ikep4j:message pre='${preUi}' key='titleAdopt'/>
			</c:when>
			<c:when test="${param.isBusiness == 1 }">
				<ikep4j:message pre='${preUiMenu}' key='busiAdoptIdea'/>
			</c:when>
			<c:when test="${param.isNullCategory == 1 }">
				<ikep4j:message pre='${preUiQna}' key='cetera'/>
			</c:when>
			<c:when test="${!empty param.categoryId}">
				${idCategory.categoryName}
			</c:when>
			<c:otherwise>
				<ikep4j:message pre='${preUi}' key='titleSug'/>
			</c:otherwise>
		</c:choose>
	</h2>
		
		<div class="listInfo">
			<select name="pagePer" title="<ikep4j:message pre='${preUi}' key='pageCount'/>" onchange="goSearch();">
				<option>10</option>
				<option>15</option>
				<option>20</option>
				<option>30</option>
				<option>40</option>
				<option>50</option>
			</select>
			<div class="totalNum"><ikep4j:message pre='${preUi}' key='total'/> <strong>${totalCount}</strong></div>
		</div>		
		<div class="tableSearch">
			<select title="<ikep4j:message pre='${preUi}' key='class'/>" name="searchColumn" >
				<option value="title"><ikep4j:message pre='${preUi}' key='classTitle'/></option>
				<option value="registerName"><ikep4j:message pre='${preUi}' key='classUser'/></option>
				<option value="tag"><ikep4j:message pre='${preUi}' key='classTag'/></option>
			</select>													
			<input type="text" class="inputbox" title="<ikep4j:message pre='${preUi}' key='searchInput'/>" name="searchWord" value="${param.searchWord}" size="20" />
			<a href="#a" class="ic_search" onclick="goSearch();return false;" title="<ikep4j:message pre='${preUi}' key='search'/>"><span><ikep4j:message pre='${preUi}' key='search'/></span></a>
		</div>	
		<div class="clear"></div>
		<div class="tableTop_bgR"></div>
	</div>
	<!--//tableTop End-->
	
	<!--subTitle_2 Start-->
	<div class="cafeSubTitle" id="orderFrame">
		<div class="cafe_sort">
			<span class="cafe_sort_smenu">
				<span class="current"><a href="#a" title="<ikep4j:message pre='${preUi}' key='orderLast'/>" onclick="orderChange('date');return false;"><ikep4j:message pre='${preUi}' key='orderLast'/> <img src='<c:url value="/base/images/icon/ic_cafesort_down.gif"/>' alt="<ikep4j:message pre='${preUi}' key='orderLast'/>" /></a></span>
				<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="bar" />
				<span><a href="#a" title="<ikep4j:message pre='${preUi}' key='orderHit'/>" onclick="orderChange('hit');return false;"><ikep4j:message pre='${preUi}' key='orderHit'/> <img src="<c:url value="/base/images/icon/ic_cafesort_down.gif"/>" alt="<ikep4j:message pre='${preUi}' key='orderHit'/>" /></a></span>
				<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="bar" />
				<span><a href="#a" title="<ikep4j:message pre='${preUi}' key='orderRecom'/>" onclick="orderChange('recommend');return false;"><ikep4j:message pre='${preUi}' key='orderRecom'/> <img src="<c:url value="/base/images/icon/ic_cafesort_down.gif"/>" alt="<ikep4j:message pre='${preUi}' key='orderRecom'/>" /></a></span>
			</span>					
		</div>								
	</div>
	<!--//subTitle_2 End-->	
</form>

<div id="tagResult">
<!--blockListTable Start-->
<div class="blockListTable summaryView Ideation_list">
	<table summary="table">
		<caption></caption>
		<tbody id="itemFrame">
			<c:if test="${totalCount > 0 }">
			<c:import url="/WEB-INF/view/collpack/ideation/ideaListMore.jsp"/>	
			</c:if>		
			<c:if test="${totalCount <= 0 }">
			<tr>
				<td>
					<c:choose>
						<c:when test="${param.isOpenContest == 1 }">
							<ikep4j:message key='message.collpack.ideation.noData2'/>
						</c:when>
						<c:when test="${param.isOpenContest == 2 }">
							<ikep4j:message key='message.collpack.ideation.noData3'/>
						</c:when>
						<c:otherwise>
							<ikep4j:message pre='${preUi}' key='notIdea'/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:if>
		</tbody>
	</table>
	
	<c:if test="${totalCount > 0 }">
	<!--Page Numbur Start--> 
	<spring:bind path="pageCondition.pageIndex">
	<ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${pageCondition}" />
	</spring:bind> 
	<!--//Page Numbur End-->
	
</c:if>
</div>
<!--//blockListTable End-->


<c:if test="${isAdmin == true }">
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="ideaForm.do<c:if test="${!empty(param.docIframe) }">?docIframe=${param.docIframe}</c:if>" title="<ikep4j:message pre='${preUi}' key='save'/>"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
			<li><a class="button" href="#a" onclick="delMul();" title="<ikep4j:message pre='${preUi}' key='del'/>"><span><ikep4j:message pre='${preUi}' key='del'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</c:if>
</div>
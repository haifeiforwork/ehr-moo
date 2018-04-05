<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.qna.constants.QnaConstants" %>

<c:set var="preUi" 			value="ui.collpack.qna.qnaSearch" />
<c:set var="preResource" 	value="message.collpack.qna" />

<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<c:choose>
	<c:when test="${fn:contains(requestUrl,'listMyQna.do')}">
		<c:set var="listType"><%=QnaConstants.LIST_TYPE_MY %></c:set>
	</c:when>
	<c:when test="${fn:contains(requestUrl,'listUrgentQna.do')}">
		<c:set var="listType"><%=QnaConstants.LIST_TYPE_URGENT %></c:set>
	</c:when>
	<c:when test="${fn:contains(requestUrl,'listNotAdoptQna.do')}">
		<c:set var="listType"><%=QnaConstants.LIST_TYPE_NOTADOPT %></c:set>
	</c:when>
	<c:when test="${fn:contains(requestUrl,'listBestQna.do')}">
		<c:set var="listType"><%=QnaConstants.LIST_TYPE_BEST %></c:set>
	</c:when>
	<c:when test="${fn:contains(requestUrl,'listCategoryQna.do')}">
		<c:set var="listType"><%=QnaConstants.LIST_TYPE_CATEGORY %></c:set>
	</c:when>
	<c:when test="${fn:contains(requestUrl,'main.do')}">
		<c:set var="listType"><%=QnaConstants.LIST_TYPE_MAIN %></c:set>
	</c:when>
</c:choose>

<script type="text/javascript">

$jq(document).ready(function() {
	
	 var searchValidOptions = {
			
			submitHandler : function(form) {
				goSearch();
			}

		};
	    
	    new iKEP.Validator("#searchForm", searchValidOptions);
	
});


function goSubmit(){
	
	$jq('#searchForm').submit();
	//goSearch();
}

var tmpSearch;   //검색호출 했는지 
function goSearch(){
	
	var f = $jq('#searchForm');
	
	var searchColumn = f.find('select').val();
	var searchWord = f.find('input[name=searchWord]').val();

	if(!searchWord){
		alert('<ikep4j:message key='NotEmpty.qna.searchTitle'/>');
		return;
	}

	pageIndex = 1;
	
	if(searchColumn == "tag"){
		iKEP.tagging.tagSearchByName(searchWord, '<%=TagConstants.ITEM_TYPE_QNA%>');
	} else {
		//f.submit();
		
		$jq.ajax({    
			url : "listSearchQnaAjax.do",     
			data : {searchColumn:searchColumn, searchWord:searchWord},     
			type : "post",  
			dataType : "html",
			success : function(result) {  

				if($jq(result).find("tr").size() == 0){
					result = '<ikep4j:message pre='${preResource}' key='notData.search'/>';
				}

				$jq("#tagResult").html(result);  
				$jq('#divTab1').hide();
				$jq('#listTitle').hide();

				tmpSearch = "search";
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 

		
	}
	
}

</script>

<!--qna_search Start-->
<div class="qna_search">

	
	<div class="qna_tl">
	<h2><ikep4j:message pre='${preUi}' key='search.qna'/></h2>
	<form id="searchForm" action="" >
		<div class="qna_search_form" style="padding-top:8px;">
			<div class="qna_search_input">						
			
				<div class="qna_search_inputbox" >
					<table summary="" width="100%">
					
						<caption></caption>
						<tbody>
							<tr>
								<td width="80" valign="top">

									<select title="<ikep4j:message pre='${preUi}' key='searchSelect'/>" name="searchColumn" style="width:100px;">
										<option value="title"><ikep4j:message pre='${preUi}' key='searchTitle'/></option>
										<option value="registerName"><ikep4j:message pre='${preUi}' key='searchRegisterName'/></option>
										<option value="tag"><ikep4j:message pre='${preUi}' key='searchTag'/></option>																	
									</select>
								</td>
								<td width="*" style="vertical-align: text-bottom;">
									<div>
									<input name="searchWord" title="<ikep4j:message pre='${preUi}' key='searchWord'/>" class="inputbox" type="text" style="padding-top:2px; height:15px;"/>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<span><a class="qna_btn_1" href="#a" onclick="goSubmit();return false;" title="<ikep4j:message pre='${preUi}' key='searchBtn'/>"><span><ikep4j:message pre='${preUi}' key='searchBtn'/></span></a></span>	
			</div>
			
			<span><a class="qna_btn_2" href="formQna.do?listType=${listType}&amp;categoryId=${param.categoryId}" title="<ikep4j:message pre='${preUi}' key='searchQna'/>" style="top:8px;"><span><ikep4j:message pre='${preUi}' key='searchQna'/></span></a></span>

		</div>
		<div class="qna_emer" >
			<div style="float: left;padding-right: 10px; color:#A74442;"><img src="<c:url value="/base/images/icon/ic_emer_2.gif"/>" alt="<ikep4j:message pre='${preUi}' key='searchUrgent'/>" /> <ikep4j:message pre='${preUi}' key='searchUrgent'/> </div>
			<div id="urgentMoveList" style="float: left;">
			<c:forEach var="qna" items="${urgentList}" varStatus="loopCount">
					<div  style="display:<c:if test="${loopCount.count != 1}">none</c:if>;">
					<a style="color:#555555;" href="getQna.do?qnaId=${qna.qnaGroupId}" title="${qna.title}">${qna.title}</a>
					</div >
			</c:forEach>	
			</div>
			<div style="clear:both;"></div>
		</div>		
	</form>
	</div>
					
					
	<div class="qna_tr" style="height:112px">
		<h2><ikep4j:message pre='${preUi}' key='timeAnswer'/></h2>
		
		<!--h2>평균답변시간</h2-->
		<div class="qna_tr_ins" >
			<strong><ikep4j:message pre='${preUi}' key='timeAverage'/></strong> <span class="qna_timer">${qnaStatistics.averageAnswerTime}</span> <ikep4j:message pre='${preUi}' key='timeHour'/> 
			<div class="qna_timer_ins" style="letter-spacing:-0.1em;"><ikep4j:message pre='${preUi}' key='timeMessage'/></div>
		</div>
	</div>
					
</div>		
<!--//qna_search End-->	
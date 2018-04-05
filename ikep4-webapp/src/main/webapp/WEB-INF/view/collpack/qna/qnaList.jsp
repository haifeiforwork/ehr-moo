<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.qna.qnaList" />
<c:set var="preResource" 	value="message.collpack.qna" />

<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function(){
		
		<c:if test="${listType == 'My' || listType == 'Best' || listType == 'Category'}">
			
			$jq("#divTab1").tabs({     
				selected : '${param.listTab}',    
				cache : true,     
				select : function(event, ui) {   
						var param = "";
					<c:if test="${listType == 'Category'}">
					
						if(ui.index == 0){
							param = "&categoryId=${categoryId}";
						} else if (ui.index == 1){
							param = "&categoryId=${categoryId}&isNotAdopt=1";
						} else {
							param = "&categoryId=${categoryId}&bestFlag=1";
						}
						
					</c:if>
						location.href = 'list${listType}Qna.do?listTab='+ ui.index + param;
						
				},     
				load : function(event, ui) {        
					//iKEP.debug(ui);     
					} 
			});  
		</c:if>
	
		var rollUrgent = new qnaEffect('urgentMoveList');	
		
		
		//검색 미리 선택
		$jq('#searchForm option').each(function(){
			if(this.value == '${param.searchColumn}'){
				this.selected = true;
				return;
			}
		});

		$jq("#listFrame tr:last").addClass("tdend");
		
		
	});
		
	function qnaform(){
		location.href="formQna.do?listType=${listType}&categoryId=${categoryId}&listTab=${param.listTab}&qnaType=${qnaType}";
	}
	
	
	function qnaDel(id){
		location.href='deleteQna.do?qnaId='+id+'&listType=${listType}';
	}

	function listMoreAjax(totalCount){
		
		
		listMore("listMore.do", totalCount, {qnaType:'${qnaType}',urgent:'${urgent}',isNotAdopt:'${isNotAdopt}',bestFlag:'${bestFlag}',categoryId:'${categoryId}',listType:'${listType}',listTab:'${param.listTab}'});
		
	}
	
	//]]>		
</script>
	
<h1 class="none">Contents Area</h1>

<!--qna_search Start-->
<c:import url="/WEB-INF/view/collpack/qna/qnaSearch.jsp" charEncoding="UTF-8" />	
<!--//qna_search End-->

<!--subTitle_2 Start-->
<c:if test="${listType == 'Category'}">
<div class="subTitle_2 noline" id="listTitle">
	<h3>${categoryName} <ikep4j:message pre='${preUi}' key='listCategoryTitle'/></h3>
</div>
</c:if>
<!--//subTitle_2 End-->

<!--tab Start-->
<c:if test="${listType == 'My' || listType == 'Category'}">
			
	<div id="divTab1" class="iKEP_tab">
		<ul>
			<li><a href="#tabs-1" >
			<c:choose>
				<c:when test="${listType == 'My'}">
				<ikep4j:message pre='${preUi}' key='listTabMy'/>
				</c:when>
				<c:when test="${listType == 'Best'}">
				<ikep4j:message pre='${preUi}' key='listTabBest'/>
				</c:when>
				<c:when test="${listType == 'Category'}">
				<ikep4j:message pre='${preUi}' key='listTabWhole'/>
				</c:when>
			</c:choose>
				</a>
			</li>
			<li><a href="#tabs-2" >
			<c:choose>
				<c:when test="${listType == 'My'}">
				<ikep4j:message pre='${preUi}' key='listTabMyAswer'/>
				</c:when>
				<c:when test="${listType == 'Best'}">
				<ikep4j:message pre='${preUi}' key='listTabBestAswer'/>
				</c:when>
				<c:when test="${listType == 'Category'}">
				<ikep4j:message pre='${preUi}' key='listTabNot'/>
				</c:when>
			</c:choose>
				</a>
			</li>
		<c:if test="${listType == 'Category'}">
			<li><a href="#tabs-3" ><ikep4j:message pre='${preUi}' key='listCategogyBestQna'/></a></li>
		</c:if>
		</ul>
		<div style="display:none;">
			<div id="tabs-1"></div>
			<div id="tabs-2"></div>
			<div id="tabs-3"></div>
		</div>	
	</div>		
</c:if>
<!--//tab End-->
	
	<!--qna_sub Start-->	
	<div class="qna_sub" id="tagResult">
	
		<!--qna_bl Start-->
		<div class="qna_bl block">		
			<c:if test="${count != 0 }">	
			<div class="qna_mainlist line">
				<table summary="">
					<tbody id="listFrame">
						
					<c:import url="/WEB-INF/view/collpack/qna/qnaMoreList.jsp" charEncoding="UTF-8" />
						
					</tbody>
				</table>
			</div>
			</c:if>
			<c:if test="${count == 0 }">
				<c:choose>
					<c:when test="${listType == 'Best' && param.qnaType == 0}">
						<ikep4j:message key='message.collpack.qna.notData.best.qna'/>
					</c:when>
					<c:when test="${listType == 'Best' && param.qnaType == 1}">
						<ikep4j:message key='message.collpack.qna.notData.best.answer'/>
					</c:when>
					<c:when test="${listType == 'My' && param.qnaType == 1}">
						<ikep4j:message key='message.collpack.qna.notData.answer'/>
					</c:when>
					<c:otherwise>
						<ikep4j:message key='message.collpack.qna.notData.list'/>
					</c:otherwise>
				</c:choose>
				
			</c:if>
		</div>
		<!--//qna_bl End-->	
		
	<c:if test="${count > 10 && param.listType != 'Search'}">
		<!--blockButton_3 Start-->
		<div class="blockButton_3"> 
			<a class="button_3" href="#a" onclick="listMoreAjax('${count}');return false;" title="more"><span id="moreText"><ikep4j:message pre='${preUi}' key='listMore'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="more" /></span></a>				
		</div>
		<!--//blockButton_3 End-->	
	</c:if>	
					
	</div>
	<!--//qna_sub End-->
							
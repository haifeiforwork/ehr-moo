<%--
=====================================================
	* 기능설명	:	cafe 멤버 등록(관리자)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.member.addNewMember.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.member.addNewMember.search" />
<c:set var="preDetail"  value="message.lightpack.cafe.member.addNewMember.detail" />
<c:set var="preButton"  value="message.lightpack.cafe.member.addNewMember.button" />
<c:set var="preScript"  value="message.lightpack.cafe.member.addNewMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script>
<script type="text/javascript">
<!-- 
(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() {
		
		<c:forEach var="cafeTypeList" items="${cafeTypeList}" varStatus="status">
		<c:if test="${status.index==0}">
		changeType('${cafeTypeList.typeId}');
		</c:if>
		</c:forEach>
		
		$jq("#saveButton").click(function() {
			
			$jq("#memberIds>option").attr("selected",true);
			$jq("#associateIds>option").attr("selected",true);
			
			$jq("#mainForm").submit().ajaxLoadStart(); 	

			return false; 
		});
		
		$("#btnMemberRemove").click(function(event) {
			event.preventDefault();
			var $member=$('#memberIds');
			$('option:selected',$member).remove();
		});
		$("#btnAssociateRemove").click(function(event) {
			event.preventDefault();
			var $associate=$('#associateIds');
			$('option:selected',$associate).remove();
		});
		$jq("#listButton").click(function() {
			document.location.href="<c:url value="/lightpack/cafe/member/listCafeMemberView.do"/>?cafeId=${searchCondition.cafeId}&listType=${searchCondition.listType}";
			return false; 
		});	
	});

	addMemberList = function(){

		var items = [];
		var $sel = $jq("#mainForm").find("[name=memberIds]");
		//var $sel = $jq("#mainForm").find("[name="+selectName+"]");
		
		$jq.each($sel.children(), function() {
			items.push($jq.data(this, "data"));
		});

		//alert($jq)
		$controlType	= $jq('input[name=controlType]:hidden').val() ;
		$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
		$selectType		= $jq('input[name=selectType]:hidden').val() ;
		$selectMaxCnt	= $jq('input[name=selectMaxCnt]:hidden').val() ;
		$searchSubFlag	= $jq('input[name=searchSubFlag]:hidden').val() ;

		// 수정 될 일반 팝업 형태의 팝업 오픈 시 사용될 스크립트
		var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
		iKEP.popupOpen(
			url,
			{
				width:700, height:550,
				argument : {search:"keyword", items:items },
				callback:function(result) {
					
					$sel.empty();
					
					$jq.each(result, function() {

						var tpl = "";
						var dupCheck = 0;
						
						
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}
						

						/** 정회원 , 준회원 중복여부 **/
						var userId = this.id;
						
						var $sel1 = $jq("#mainForm").find("[name=associateIds]");
		
						$jq.each($sel1.children(), function() {

							if(userId == $(this).val())
								dupCheck =1;
						});
						
						if(dupCheck==0){
							var $option = $jq.tmpl(tpl, this).appendTo($sel);

							$jq.data($option[0], "data", this);
									
							if( this.searchSubFlag == true ){
								$jq('input[name=searchSubFlag]:hidden').val("true") ;
							}
						}						
						
					})
		        }
			}
		)
	};
	
	addAssociateList = function(){

		var items = [];
		var $sel = $jq("#mainForm").find("[name=associateIds]");
		//var $sel = $jq("#mainForm").find("[name="+selectName+"]");
		
		$jq.each($sel.children(), function() {
			items.push($jq.data(this, "data"));
		});

		//alert($jq)
		$controlType	= $jq('input[name=controlType]:hidden').val() ;
		$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
		$selectType		= $jq('input[name=selectType]:hidden').val() ;
		$selectMaxCnt	= $jq('input[name=selectMaxCnt]:hidden').val() ;
		$searchSubFlag	= $jq('input[name=searchSubFlag]:hidden').val() ;

		// 수정 될 일반 팝업 형태의 팝업 오픈 시 사용될 스크립트
		var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
		iKEP.popupOpen(
			url,
			{
				width:700, height:550,
				argument : {search:"keyword", items:items },
				callback:function(result) {
					
					$sel.empty();
					
					$jq.each(result, function() {

						var tpl = "";
						var dupCheck = 0;
						
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}
						

						/** 정회원 , 준회원 중복여부 **/
						var userId = this.id;
						
						var $sel1 = $jq("#mainForm").find("[name=memberIds]");
		
						$jq.each($sel1.children(), function() {
							if(userId == $(this).val())
								dupCheck =1;
						});
						
						if(dupCheck==0){
							var $option = $jq.tmpl(tpl, this).appendTo($sel);

							$jq.data($option[0], "data", this);
									
							if( this.searchSubFlag == true ){
								$jq('input[name=searchSubFlag]:hidden').val("true") ;
							}
						}	
							
						
					})
		        }
			}
		)
	};	
	

		
		
		
	// java script 전역변수 항목에 추가
	$jq.template("addrBookItemUser", "<option value='\${id}'>\${name}/\${jobTitle}/\${teamName}</option>");
	$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");

	// 주의 위에 code 가 가이드 입력시 오류로 입력되지 않아서 부득이하게 공백으로 입력
	// 추후 사용시 공백 제거후 사용 해주세요

	
})(jQuery);  
//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 

</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/lightpack/cafe/member/addNewMember.do' />">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.cafeId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.cafeStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.memberId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.memberLevel">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.portalId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style=""/>	
		<tbody>
		

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='cafeName' /></th>
			<td>${cafe.cafeName }</td>
		</tr>		
		
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member3' /></th>
			<td>
			<select name="memberIds" id="memberIds" class="input_select w80" size="4"	style="height:100px;" multiple>
			</select>
			<span>
			<a id="addButton" class="button_s" href="#a" onclick="addMemberList('memberIds')" title="<ikep4j:message pre='${preButton}' key='add'/>"><span><ikep4j:message pre='${preButton}' key='add'/></span></a>
			<a id="btnMemberRemove" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
			</span>
			</td>
		</tr>		
		
		<tr>			
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member4' /></th>
			<td>
			<select name="associateIds" id="associateIds" class="input_select w80" size="4"	style="height:100px;" multiple>
			</select>
			<span>
			<a id="addButton" class="button_s" href="#a" onclick="addAssociateList('associateIds')" title="<ikep4j:message pre='${preButton}' key='add'/>"><span><ikep4j:message pre='${preButton}' key='add'/></span></a>
			<a id="btnAssociateRemove" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
			</span>
			</td>			
		</tr>
		</tbody>
	</table>

	
	
</div>

<input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
<input name="controlType" title="" type="hidden" value="ORG" />
<input name="selectType" title="" type="hidden" value="USER" />
<input name="selectMaxCnt" title="" type="hidden" value="100" />
<input name="searchSubFlag" title="" type="hidden" value="" />
</form>
<!--//blockListTable End--> 

<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveButton" class="button" href="#" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<li><a id="listButton" class="button" href="#" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		

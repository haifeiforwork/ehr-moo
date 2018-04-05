<%--
=====================================================
	* 기능설명	:	cafe 멤버 등록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.member.createCafeMember.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.member.createCafeMember.search" />
<c:set var="preCode"    value="message.collpack.common.code" />
<c:set var="preList"    value="message.lightpack.cafe.member.createCafeMember.list" />
<c:set var="preButton"  value="message.lightpack.cafe.member.createCafeMember.button" />
<c:set var="preScript"  value="message.lightpack.cafe.member.createCafeMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
var dialogWindow;
function fnCaller(param, dialog) {
	dialogWindow = dialog;
}

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				memberIntroduction : {
					required : true,
					maxlength : 500
				}
			},
			messages : {
				memberIntroduction : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.member.memberIntroduction" />",
					maxlength	:	"<ikep4j:message key="Size.member.memberIntroduction" />"
					
				}
			},
			submitHandler : function(form) {
				// 부가 검증 처리

				$.ajax({
					url : "<c:url value='/lightpack/cafe/member/createCafeMember.do' />",
					type : "post",
					data : $(form).serialize(),
					success : function(result) {
						alert('<ikep4j:message pre='${preScript}' key='saveSuccess' />');
						
						dialogWindow.callback();
						dialogWindow.close();
					},
					error : function(xhr, exMessage) {
					
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});		
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);
		
		/**
		 * Validation Logic End
		 */	
		 
		$jq("#saveMemberButton").click(function() {
			$jq("#mainForm").submit().ajaxLoadStart();; 	
			return false; 
		});
		$jq("#initMemberButton").click(function() {
			$jq("#mainForm")[0].reset(); 	
			return false; 
		});		
	   
	});
})(jQuery);  
//-->
</script>
 
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->
<%-- 
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div>  --%>

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/lightpack/cafe/member/createCafeMember.do' />">
<spring:bind path="cafe.cafeId">
	<input type="hidden" name="${status.expression}" value="${status.value}" title=""/>
</spring:bind>
<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div class="" id="layer_p" title="Category 추가">
	<!--blockDetail Start-->
	<p><ikep4j:message pre='${preScript}' key='info' /><span style="position:absolute; top:0; right:0;"></span></p>
	<div>
		<spring:bind path="member.memberIntroduction">
			<textarea name="${status.expression}" class="w100" title="${status.expression}" cols="" rows="5">${status.value }</textarea>
			<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
		</spring:bind>
	</div>	
	<!--//blockDetail End-->
	
	<div class="blockBlank_10px"></div>	
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveMemberButton" class="button" href="#a"><span><ikep4j:message pre='${preList}' key='summaryCreateMember' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
</div>	
<!-- //Modal window End -->
	  
</form>		

 
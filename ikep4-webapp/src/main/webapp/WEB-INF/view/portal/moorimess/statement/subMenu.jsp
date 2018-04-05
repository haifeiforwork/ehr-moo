<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="prefix"    value="ui.support.customer.leftmenu" /> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--   

function setLicurrent(el) {
	var $el = el;
	if(typeof el === "string") {
		$el = $jq(el);
	}
	clearCurrent();
	$el.addClass("licurrent");
	$el.parents("li.opened", "#leftMenu-pane").addClass("licurrent");
}

function clearCurrent() {
	$jq("#leftMenu-pane li").removeClass("licurrent");
}


(function($) {
	

	
	$jq(document).ready(function(){
		// left menu setting
		iKEP.setLeftMenu();
		//if("${roleId}" == "100001059165" || "${roleId}" == "100000803133"){
			var sflag = "${sflag}";
			if(sflag == "1"){
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/statementCommon/statementList.do'/>");
			}else{
				iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/statementCommon/budgetList.do'/>");
			}
	    	
		//}else{
			//goCounselHistory();
		//}
		
	
	});
	
})(jQuery);




function goStatementAll(){
	 iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/statementCommon/statementAllList.do'/>");
}

function goStatement(){
	 iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/statementCommon/statementList.do'/>");
}

function goBudget(){
	 iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/statementCommon/budgetList.do'/>");
}

function goMandate(){
	 iKEP.iFrameMenuOnclick("<c:url value='/portal/moorimess/statementCommon/mandateView.do'/>");
}

//-->
</script>



			<!--leftMenu Start-->

				<h1 class="none">LEFT MENU</h1>	
                <h2>
                <span>
                <c:if test="${sflag=='1'}" >
                <a href="<c:url value='/portal/moorimess/statementCommon/statementMenuView.do?sflag=1'/>">
                <img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_statement.gif'/>"/>
                </a>
                </c:if>
                <c:if test="${sflag=='2'}" >
                <a href="<c:url value='/portal/moorimess/statementCommon/statementMenuView.do?sflag=2'/>">
                <img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_budget.gif'/>"/>
                </a>
                </c:if>
                </span></h2>	
				<div class="left_fixed" id="leftMenu-pane">
					<ul>
						<c:if test="${sflag=='1'}" >
                        <li id="statement" class="no_child licurrent"><a href="javascript:goStatement();">전표 승인요청 결재</a></li>
                        <li id="statementAll" class="no_child"><a href="javascript:goStatementAll();">카드사용/전표승인내역조회</a></li>
                        <%--  
                        <c:if test="${isMaster}">
                        --%>
                        <li id="budget" class="no_child"><a href="javascript:goMandate();">전표 결재 위임</a></li>
                        <%--  
                        </c:if>
                        --%>
                        </c:if>
                        <c:if test="${sflag=='2'}" >
                        	<li id="budget" class="no_child licurrent"><a href="javascript:goBudget();">예산 조회</a></li>
						</c:if>
                        <c:if test="${sflag=='1'}" >
                        	<li id="budget" class="no_child"><a href="javascript:goBudget();">예산 조회</a></li>
						</c:if>
					</ul>
				</div>	

			<!--//leftMenu End-->
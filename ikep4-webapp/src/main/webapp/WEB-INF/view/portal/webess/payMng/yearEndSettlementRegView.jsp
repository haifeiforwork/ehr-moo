<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#searchButton").click(function() {
			$.callProgress();
			$("#yearEndSettlementForm").submit();
		});
		
		$("#btnSave").click(function(){
			$.save("SAVE");
		});
		
		$("input.amount").live("blur", function(event) {
			if (!$(this).hasClass('keyupping')){
				$.amountValid($(this));
			}
		});
		
		$("input.amount").live("keyup", function(e) {
			$(this).addClass('keyupping');
			if(e.which == 13){
				$.amountValid($(this));
			}
			$(this).removeClass('keyupping');
		});
		
		$("a[name=btnWritingTipPop]").click(function(){
			$("#popupForm").html("");
			
			var target = "writingTip";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1000px, height=600px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"helpType\" value=\""+$(this).data("type")+"\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/writingTipPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		var orgTab = "";
		//On Click Event
		$("a.tabs").click(function(){
			if(orgTab != "" && orgTab == "#tab01" && $(this).data("param") != "#tab01"){
				alert("입력된 주소/연락처를 현재 기준으로 변경 바랍니다.");
			}
			orgTab = $(this).data("param");
			$("a.tabs").removeClass("active"); //Remove any "active" class
			$(this).addClass("active");
			
			$(".tab_content").hide(); //Hide all tab content
			var activeTab = $(this).data("param"); //Find the href attribute value to identify the active tab + content
			
			$(activeTab).fadeIn(); //Fade in the active ID content
			
			parent.iKEP.iFrameResize('#contentIframe');
			
			
			return false;
		});
		
		<c:if test="${!empty resultMap.EX_MESSAGE && params.imFirst ne 'X'}">
		alert("<c:out value="${resultMap.EX_MESSAGE}"/>");
		</c:if>
		
		$("a.tabs").eq("<c:out value="${params.tabIndex}"/>").click();
		
		$.refresh = function(tabIndex){
			$.callProgress();
			$("#refreshForm").find("input[name=tabIndex]").val(tabIndex);
			$("#refreshForm").find("input[name=imFirst]").val("X");
			$("#refreshForm").attr("action", "<c:url value='/portal/moorimess/payMng/yearEndSettlementRegView.do'/>");
			$("#refreshForm").submit();
		};
		
		$.save = function(imEventId){
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imYear\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZyear\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\"<c:out value="${resultMap.EX_BEGDA}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEndda\" value=\"<c:out value="${resultMap.EX_ENDDA}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imDisabled3\" value=\"<c:out value="${resultMap.EX_DISABLED3}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#ajaxForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			$("#perFunc").find("span.rowInfo").each(function(){
				$("#ajaxForm").append($(this).html());
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"perFuncCnt\" value=\""+$("#perFunc").find("span.rowInfo").length+"\"/>");
			
			$("#perFunc").find(".perFuncParam").each(function(){
				if($(this).attr("type") == "checkbox"){
					if($(this).is(":checked")){
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"X\"/>");
					}else{
						$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\"\"/>");
					}
				}else{
					$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
				}
			});
			
			$(".taxParam").each(function(){
				if($(this).hasClass("amount")){
					$("#ajaxForm").append("<input type=\"hidden\" name=\"taxParam_"+$(this).attr("name")+"\" value=\""+$(this).val().clearComma()+"\"/>");
				}else if($(this).attr("type") == "checkbox"){
					if($(this).is(":checked")){
						$("#ajaxForm").append("<input type=\"hidden\" name=\"taxParam_"+$(this).attr("name")+"\" value=\"X\"/>");
					}else{
						$("#ajaxForm").append("<input type=\"hidden\" name=\"taxParam_"+$(this).attr("name")+"\" value=\"\"/>");
					}
				}else{
					$("#ajaxForm").append("<input type=\"hidden\" name=\"taxParam_"+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
				}
			});
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003Oninput.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					if(imEventId == "SAVE"){
						alert(result.EX_MESSAGE);
						if($.trim(result.EX_RESULT) == "S"){
							
							var selTab = 0;
							
							$("a.tabs").each(function(){
								if($(this).hasClass("active")){
									selTab = $(this);
									return false;
								}
							});
							
							$.refresh($("a.tabs").index(selTab));
						}else{
						}
					}else{
						if($.trim(result.EX_RESULT) == "S"){
						}else{
							if($("input[name=hshld]").is(":checked")){
								$("input[name=hshld]").removeAttr("checked");
							}else{
								$("input[name=hshld]").attr("checked", "checked");
							}
							alert(result.EX_MESSAGE);
						}
					}
				},complete : function(){
					$("#ajaxForm").html("");
				}
			});
		};
	});
})(jQuery);;
</script>
<style type="text/css">
.tabs {float:left;}
.tab_content {clear:both;}
</style>
<form id="yearEndSettlementForm" name="yearEndSettlementForm" method="post" action="<c:url value='/portal/moorimess/payMng/yearEndSettlementRegView.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연말정산 소득공제 신청</h1>
	
	<c:choose>
		<c:when test="${resultMap.EX_OCK ne 'X' && resultMap.EX_OCK_20 ne 'X'}">
			<div class="f_title">※ 지금은 신청기간이 아닙니다.</div>
		</c:when>
		<c:when test="${resultMap.EX_OCK eq 'X'}">
			<!-- 상단버튼-->
			<div style="padding:10px 0">
				<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}"><a href="#" class="button_img01" id="btnSave"><span>저장</span></a></c:if> <c:out value="${resultMap.EX_YEAR }"/>년 소득에 대한 연말정산
			</div>
			<div style="min-width:925px;">
				<a href="#" class="tab01 tabs" data-param="#tab01">
					<span>소득공제<br>
						<b>인적공제</b>
					</span>
				</a>
				<a href="#" class="tab02 tabs" data-param="#tab02">
					<span>소득공제<br>
						<b>연금보험료공제</b>
					</span>
				</a>
				<a href="#" class="tab03 tabs" data-param="#tab03">
					<span>소득공제<br>
						<b>특별소득공제</b>
					</span>
				</a>
				<a href="#" class="tab04 tabs" data-param="#tab04">
					<span>소득공제<br>
						<b>기타소득공제</b>
					</span>
				</a>
				<a href="#" class="tab05 tabs" data-param="#tab05">
					<span>소득공제<br>
						<b>세액공제</b>
					</span>
				</a>
			</div>
			<div id="tab01" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webess/payMng/personal.jsp"%>
			</div>	
			<div id="tab02" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webess/payMng/pensionPremium.jsp"%>
			</div>	
			<div id="tab03" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webess/payMng/specialIncome.jsp"%>
			</div>
			<div id="tab04" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webess/payMng/otherIncome.jsp"%>
			</div>
			<div id="tab05" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webess/payMng/tax.jsp"%>
			</div>
		</c:when>
	</c:choose>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="popupForm" name="popupForm" method="post">
</form>
<form id="refreshForm" name="refreshForm" method="post">
	<input type="hidden" name="tabIndex" value=""/>
	<input type="hidden" name="imFirst" value=""/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
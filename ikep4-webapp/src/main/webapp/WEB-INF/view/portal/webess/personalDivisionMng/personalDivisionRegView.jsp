<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="exList" value="${resultMap.EX_LIST}"/>
<c:set var="itRag" value="${resultMap.IT_RAG_PERNR}"/>

<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnLineAdd").click(function(){
			
			$("#ajaxForm").html("");

			<c:forEach items="${keySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${exList[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${itRag}" var="result">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_SIGN\" value=\"<c:out value="${result.SIGN}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_OPTION\" value=\"<c:out value="${result.OPTION}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_LOW\" value=\"<c:out value="${result.LOW}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_HIGH\" value=\"<c:out value="${result.HIGH}"/>\"/>");
			</c:forEach>
	
			$("#ajaxForm").append("<input type=\"hidden\" name=\"ragPernrCnt\" value=\"<c:out value='${fn:length(itRag)}'/>\"/>");
	
			$("span.dpResultInfo").each(function(index, value){
				$("#ajaxForm").append($(this).html());
			});
	
			$("#ajaxForm").append("<input type=\"hidden\" name=\"dpResultCnt\" value=\""+$("span.dpResultInfo").length+"\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalDivisionMng/dpLineAdd.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					$("#ajaxForm").html("");
					if($.trim(result.EX_RESULT) == "S"){
						var resultRow = result.ET_DP_RESULT;
						$.dpLineAdd(resultRow[resultRow.length-1]);
					}else{
						//에러처리
					}
				}
			});
		});
		
		$("#btnLineDel").click(function(){
			$("input[name=flag]:checked").parents("tr").remove();
		});

		$("a[name=jobLinePop]").live("click", function(){
			var tr = $(this).parents("tr");
			var allTr = $("#jobTable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));

			$("#jobLineForm").find("input[name=index]").remove();
			$("#jobLineForm").append("<input type='hidden' name='index' value='"+index+"'/>");

			var target = "jobLinePopup";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=340px, height=500px, top=200px, left=500px, resizable=no";

			var popup = window.open("", target, param);

			$("#jobLineForm").attr("action", "<c:url value='/portal/moorimess/personalDivisionMng/jobLinePop.do'/>");
			$("#jobLineForm").attr("target", target);
			$("#jobLineForm").submit();

			popup.focus();
		});

		$("select.TASK").live("change", function(){
			var index = $("select.TASK").index(this);

			$("input[name=TASK]").eq(index).val($(this).val());
			$("input[name=TASK_T]").eq(index).val($(this).find("option:selected").text());
		});

		$("#btnSave, #btnAgree").click(function(){

			if(this.id == "btnSave"){
				if(confirm("저장하시겠습니까?")){
					$.setParam("SAVE");
				}
			}else if(this.id == "btnAgree"){
				if(confirm("합의요청하시겠습니까?")){
					$.setParam("AGREE");
				}
			}
		});

		$("#btnBack").click(function(){
			$("#personalDivisionForm").attr("method", "post");
			$("#personalDivisionForm").attr("action", "<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionMngList.do'/>");
			$("#personalDivisionForm").submit();
		});
		
		$.dpLineAdd = function(resultRow){
			var tab = $("#jobTable").find("tbody");
			var sb = "";
			
			sb += "<tr>";
			sb += "	<td><input type=\"checkbox\" name=\"flag\" value=\"\"/></td>";
			sb += "	<td><span class=\"ZICGUN_T\"></span></td>";
			sb += "	<td><span class=\"OBJ\"></span>&nbsp;<a href=\"#\" class=\"button_img03\" name=\"jobLinePop\"><span><img src=\"/base/images/ess/icon01.png\" alt=\"\"/></span></a></td>";
			sb += "	<td><span class=\"OBJ_T\"></span></td>";
			sb += "	<td class=\"f_left\">";
			sb += "		<select class=\"TASK w90per\"></select>";
			sb += "		<span class=\"dpResultInfo\">";
			<c:forEach items="${dpResultKeySet}" var="key">
			sb += "			<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${result[key] }"/>\"/>";
			</c:forEach>
			sb += "		</span>";
			sb += "	</td>";
			sb += "</tr>";

			tab.append(sb);
			
			$("span.ZICGUN_T:last").html(resultRow.ZICGUN_T);
			$("span.OBJ:last").html(resultRow.OBJ);
			$("span.OBJ_T:last").html(resultRow.OBJ_T);
			
			$("input[name=ZICGUN]:last").val(resultRow.ZICGUN);
			$("input[name=ZICGUN_T]:last").val(resultRow.ZICGUN_T);
			$("input[name=OBJ]:last").val(resultRow.OBJ);
			$("input[name=OBJ_T]:last").val(resultRow.OBJ_T);
			$.callTaskList($("input[name=OBJ]:last"));
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<c:forEach items="${keySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${exList[key]}"/>\"/>");
			</c:forEach>

			<c:forEach items="${itRag}" var="result">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_SIGN\" value=\"<c:out value="${result.SIGN}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_OPTION\" value=\"<c:out value="${result.OPTION}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_LOW\" value=\"<c:out value="${result.LOW}"/>\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"itRag_HIGH\" value=\"<c:out value="${result.HIGH}"/>\"/>");
			</c:forEach>

			$("#ajaxForm").append("<input type=\"hidden\" name=\"ragPernrCnt\" value=\"<c:out value='${fn:length(itRag)}'/>\"/>");

			$("span.dpResultInfo").each(function(index, value){
				$("#ajaxForm").append($(this).html());
			});

			$("#ajaxForm").append("<input type=\"hidden\" name=\"dpResultCnt\" value=\""+$("span.dpResultInfo").length+"\"/>");

			$("#ajaxForm").append("<input type=\"hidden\" name=\"imJobtext\" value=\"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+eventId+"\"/>");

			<c:choose>
				<c:when test="${params.eventId eq 'CREATE'}">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imOpera\" value=\"INS\"/>");
				</c:when>
				<c:otherwise>
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imOpera\" value=\"\"/>");
				</c:otherwise>
			</c:choose>
			
			$.callProcess();
		};

		$.callProcess = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#btnBack").click();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){
			$("input[name=OBJ]").each(function(index, value){
				$.callTaskList($(this), $("input[name=TASK]").eq(index).val());
			});
		};

		$.callTaskList = function(obj, params){

			var index = $("input[name=OBJ]").index(obj);

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalDivisionMng/retriveTaskList.do'/>",
				data : "imStell="+obj.val(),
				type : "post",
				success : function(result) {
					var etTask = result.ET_TASK;
					var target = $("select.TASK").eq(index);

					target.html("");

					$("input[name=TASK]").eq(index).val("");
					$("input[name=TASK_T]").eq(index).val("");

					for(var i=0 ; i<etTask.length ; i++){

						if(params != undefined && etTask[i].KEY == params){
							target.append("<option value=\""+etTask[i].KEY+"\" selected >"+etTask[i].VALUE+"</option>");
						}else{
							target.append("<option value=\""+etTask[i].KEY+"\">"+etTask[i].VALUE+"</option>");
						}
					}

					$("select.TASK").eq(index).change();
				}
			});
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="personalDivisionForm" name="personalDivisionForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>개인별 업무분장 신청</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnAgree"><span>합의요청</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			개인별 업무분장&nbsp;&nbsp;
			<a href="#" class="button_img02" id="btnLineAdd"><span><img src="/base/images/ess/plus.png" alt=""/></span></a>
			<a href="#" class="button_img02" id="btnLineDel"><span><img src="/base/images/ess/minus.png" alt=""/></span></a>
		</p>

		<c:set var="etDpResult" value="${resultMap.ET_DP_RESULT }"/>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="jobTable">
				<caption></caption>
				<colgroup>
					<col width="35px"/>
					<col width="15%"/>
					<col width="15%"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>직군</th>
						<th>직무</th>
						<th>직무명</th>
						<th>업무</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${etDpResult}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="flag" value=""/></td>
							<td><span class="ZICGUN_T"><c:out value="${result.ZICGUN_T }"/></span></td>
							<td><span class="OBJ"><c:out value="${result.OBJ}"/></span>&nbsp;<a href="#" class="button_img03" name="jobLinePop"><span><img src="/base/images/ess/icon01.png" alt=""/></span></a></td>
							<td><span class="OBJ_T"><c:out value="${result.OBJ_T}"/></span></td>
							<td class="f_left">
								<select class="TASK w90per">
								</select>
								<span class="dpResultInfo">
									<c:forEach items="${dpResultKeySet}" var="key">
										<input type="hidden" name="<c:out value="${key}"/>" value="${result[key] }"/>
									</c:forEach>
								</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<c:if test="${exList.LEADR2 ne '00000000'}">
				<tr>
					<th class="list03_td_bg">파트/센터장</th>
					<td class="list03_td"><c:out value="${exList.LEADR2 }"/>&nbsp;<c:out value="${exList.LEADR2_T }"/></td>
				</tr>
				</c:if>
				<c:if test="${exList.LEADR ne '00000000'}">
				<tr>
					<th class="list03_td_bg">합의자</th>
					<td class="list03_td"><c:out value="${exList.LEADR }"/>&nbsp;<c:out value="${exList.LEADR_T }"/></td>
				</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="jobLineForm" name="jobLineForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#btnAssessments").click(function(){
			
			if($("input[name=chkFlag]:checked").length == 0){
				alert("해당 라인을 선택해주세요.");
				return;
			}
			
			$.setParam();
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/assessmentsProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					//alert($.trim(result.EX_MESSAGE));
					if($.trim(result.EX_RESULT) == "S"){
						var data = result.ET_MESSAGE;
						var etMessageCnt = 0;
						
						for(var i = 0 ; i < data.length ; i++){
							<c:forEach items="${keySet}" var="key">
								$("#assessmentsForm").append("<input type=\"hidden\" name=\"etMessage_<c:out value="${key}"/>\" value=\""+data[i]["<c:out value="${key}"/>"]+"\"/>");
							</c:forEach>
							etMessageCnt++;
						}
						$("#assessmentsForm").append("<input type=\"hidden\" name=\"etMessageCnt\" value=\""+etMessageCnt+"\"/>");
						$("#assessmentsForm").submit();
					}else{
					}
				}
			});
			
		});
		
		$("select[name=imPernr], input[name=startDate]").change(function(){
			$.callProgress();
			$("#assessmentsForm").submit();
		});
		
		$("#chkAll").click(function(){
			if($(this).is(":checked")){
				$("input[name=chkFlag]").attr("checked", true);
			}else{
				$("input[name=chkFlag]").removeAttr("checked");
			}
		});

		$("input[name=chkFlag]").click(function(){
			if($("input[name=chkFlag]").length == $("input[name=chkFlag]:checked").length){
				$("#chkAll").attr("checked", true);
			}else{
				$("#chkAll").removeAttr("checked");
			}
		});
		
		$.initSet = function(){
			<fmt:parseDate var="dateString" value="${resultMap.EX_BEGDA}" pattern="yyyyMMdd" />
			$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("span.startDate").html("(<c:out value="${resultMap.EX_BEGWK}"/>)");
			
			<fmt:parseDate var="dateString" value="${resultMap.EX_ENDDA}" pattern="yyyyMMdd" />
			$("input[name=endDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("span.endDate").html("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /> (<c:out value="${resultMap.EX_ENDWK}"/>)");
			
			$("select[name=imPernr] option[value=<c:out value="${params.imPernr}"/>]").attr("selected", "selected");
		};
		
		$.setParam = function(){
			
			$(".parameter").each(function(index, value){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			var rowInfoCnt = 0;
			
			$("input[name=chkFlag]").each(function(index, value){
				if($(this).is(":checked")){
					$("#ajaxForm").append($("span.rowInfo").eq(index).html());
					rowInfoCnt++;
				}
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"rowInfoCnt\" value=\""+rowInfoCnt+"\"/>");
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<form id="assessmentsForm" name="assessmentsForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/assessmentsList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근태평가</h1>
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">평가 시작일</span></td>
					<td>
						<input type="text" name="startDate" id="startDate" value="" class="input datepicker parameter" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="startDate"></span>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">평가 종료일</span></td>
					<td>
						<input type="hidden" name="endDate" id="endDate" value="" class="parameter"/>
						<span class="endDate"></span>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">성명</span></td>
					<td>
						<select name="imPernr" id="imPernr">
							<c:forEach var="result" items="${etBanwon }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnAssessments"><span>근태평가</span></a>
	</div>
	
	<c:set var="etList" value="${resultMap.ET_LIST }"/>
	
	<div class="table_box">
		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="35px"><input type="checkbox" name="chkAll" id="chkAll" value=""/></th>
						<th scope="col" width="14%">처리상태</th>
						<th scope="col" width="*">처리결과</th>
						<th scope="col" width="14%">부서</th>
						<th scope="col" width="14%">직급</th>
						<th scope="col" width="14%">사번</th>
						<th scope="col" width="14%">이름</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty etList }">
							<c:forEach var="result" items="${etList}">
								<tr>
									<td><input type="checkbox" name="chkFlag" value=""/></td>
									<td>
										<c:if test="${result.SUBRC eq 'S' }">
											<img src="<c:url value="/base/images/ess/green3_icon.gif"/>" alt=""/>
										</c:if>
									</td>
									<td><c:out value="${result.MESSAGE}"/></td>
									<td><c:out value="${result.ORGTX}"/></td>
									<td><c:out value="${result.JIKWI}"/></td>
									<td><c:out value="${result.PERNR}"/></td>
									<td>
										<c:out value="${result.ENAME}"/>
										<span class="rowInfo">
											<c:forEach items="${keySet}" var="key">
												<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
											</c:forEach>
										</span>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">해당 조회 내역이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				 </tbody>
			</table>
		</div>
	</div>
	<input type="hidden" name="imFirst" value="X"/>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>

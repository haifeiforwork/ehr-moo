<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var page = "DISPLAY";

		$("#btnBack").click(function(){
			$("#congratulateForm").attr("method", "post");
			$("#congratulateForm").attr("action", "<c:url value='/portal/moorimess/personalMng/congratulateList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#congratulateForm").submit();
		});

		$("#btnDelete").click(function(){
			if(confirm("정말로 삭제 하시겠습니까?")){
				$.setParam("DELETE");
			}
		});

		$("input[name=type]").change(function(event, params, nextFlag){

			//Clear Field
			$("input[name=recipientType]").val("");
			$("input[name=recipientName]").val("");
			$("input[name=startDate]").val("");
			$("input[name=endDate]").val("");
			$("input[name=holiday]").val("");
			$("input[name=amount]").val("");
			$("input[name=garlandType]").val("");

			if( $(this).val() != "" ){
				$("#ajaxForm").html("");
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#congratulateForm").find("input[name=type]").val()+"\"/>");

				$jq.ajax({
					url : "<c:url value='/portal/moorimess/personalMng/retrieveRecipientTypeList.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async : false,
					success : function(result) {
						data = result.ET_CACRN;
						for( var i=0 ; i<data.length ; i++ ){
							if(params != undefined && data[i].KEY == params){
								$("input[name=recipientType]").val(data[i].KEY);
								$("span.recipientType").html(data[i].VALUE);
							}
						}

						if(nextFlag != undefined && nextFlag == true){
							<fmt:parseDate var="dateString" value="${params.BEGDT}" pattern="yyyyMMdd" />
							$("input[name=startDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
							$("input[name=startDate]").change();
						}
					}
				});
			}
		});

		$("input[name=startDate], input[name=recipientType]").change(function(){
			if($("input[name=startDate]").val() != ""){
				$("#ajaxForm").html("");

				<%//결재라인 데이터 처리%>
				$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

				var apprCnt = 0;
				$("#congratulateForm").find("span.apprInfo").each(function(index){
					$("#ajaxForm").find("span.apprInfo").append($(this).html());
					apprCnt++;
				});
				$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

				<%//입력 데이터 처리%>
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#congratulateForm").find("input[name=type]").val()+"\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"recipientType\" value=\""+$("#congratulateForm").find("input[name=recipientType]").val()+"\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#congratulateForm").find("input[name=startDate]").val()+"\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"flag\" value=\"C\"/>");
				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

				$jq.ajax({
					url : "<c:url value='/portal/moorimess/personalMng/retrieveCongratulateInfo.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					success : function(result) {
						if($.trim(result.EX_RESULT) == "S"){
							var endDate = $.trim(result.EX_ENDDT).toString();

							endDate = endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6,8);

							$("#congratulateForm").find("input[name=endDate]").val(endDate);
							$("#congratulateForm").find("span.endDate").html(endDate);

							$("#congratulateForm").find("input[name=holiday]").val($.trim(result.EX_ZVACDT));
							$("#congratulateForm").find("span.holiday").html($.trim(result.EX_ZVACDT));

							$("#congratulateForm").find("input[name=amount]").val($.trim(result.EX_CACMT).addComma());
							$("#congratulateForm").find("span.amount").html($.trim(result.EX_CACMT).addComma());

							$("#congratulateForm").find("input[name=garlandType]").val($.trim(result.EX_GARLD));

							<c:forEach var="result" items="${garlandList}">
								if("<c:out value="${result.KEY}"/>" == $.trim(result.EX_GARLD)){
									$("#congratulateForm").find("span.garlandType").html("<c:out value="${result.VALUE}"/>");
								}
							</c:forEach>
						}else{
							alert(result.EX_MESSAGE);
						}
					}
				});
			}
		});

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/congratulateProcess.do'/>",
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
			if("<c:out value="${params.dataLoad}"/>" == "Y"){

				var form = $("#congratulateForm");

				form.append("<span class=\"rowInfo\"></span>");

				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
					$("#apprLineForm").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>

				<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("input[name=type]").val("<c:out value='${params.CACCD}'/>");
				$("input[name=type]").trigger("change", ["<c:out value='${params.CACRN}'/>", true]);

				$("input[name=recipientName]").val("<c:out value='${params.ENAME}'/>");
			}else{
				$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
			}
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
				$("#ajaxForm").find("span.rowInfo").html($("#congratulateForm").find("span.rowInfo").html());
			}

			<%//결재라인 데이터 처리%>
			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");

			var apprCnt = 0;
			$("#congratulateForm").find("span.apprInfo").each(function(index){
				$("#ajaxForm").find("span.apprInfo").append($(this).html());
				apprCnt++;
			});
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"exFlag\" value=\""+"<c:out value="${params.exFlag}"/>"+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"requestDate\" value=\""+$("#congratulateForm").find("input[name=requestDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#congratulateForm").find("input[name=type]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"recipientType\" value=\""+$("#congratulateForm").find("input[name=recipientType]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"recipientName\" value=\""+$("#congratulateForm").find("input[name=recipientName]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"startDate\" value=\""+$("#congratulateForm").find("input[name=startDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"endDate\" value=\""+$("#congratulateForm").find("input[name=endDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"holiday\" value=\""+$("#congratulateForm").find("input[name=holiday]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"amount\" value=\""+$("#congratulateForm").find("input[name=amount]").val().clearComma()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"garlandType\" value=\""+$("#congratulateForm").find("input[name=garlandType]").val()+"\"/>");

			$.callSaveRequest();
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="congratulateForm" name="congratulateForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>경조금 신청/조회</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<c:if test="${params.exFlag eq 'D' }">
			<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</c:if>
	</div>
	
	

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			결재라인
		</p>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="apprTable">
				<caption></caption>
				<colgroup>
					<col width="3%"/>
					<col width="13%"/>
					<col width="10%"/>
					<col width="*"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>사번</th>
						<th>이름</th>
						<th>부서명</th>
						<th>직책</th>
						<th>결재상태</th>
						<th>결재일</th>
						<th>결재시간</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="appr" items="${resultMapList.ET_LINE}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="apprFlag" value=""/></td>
							<td <c:if test="${appr.EFLAG eq 'X' }"> style="background-color:#eff7fb"</c:if>>
								<span class="APPNR">
									<c:out value="${appr.APPNR}"/>
								</span>
							</td>
							<td><span class="ENAME"><c:out value="${appr.ENAME}"/></span></td>
							<td><span class="ORGTX"><c:out value="${appr.ORGTX}"/></span></td>
							<td><span class="COTXT"><c:out value="${appr.COTXT}"/></span></td>
							<td><span class="ASTAT"><c:out value="${appr.ASTATT}"/></span></td>
							<td>
								<span class="ADATE">
									<fmt:parseDate var="dateString" value="${appr.ADATE}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</span>
							</td>
							<td>
								<span class="ATIME">
									<c:out value="${appr.ATIME}"/>
								</span>
							</td>
							<span class="apprInfo">
								<c:forEach items="${apprKeySet}" var="key">
									<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${appr[key] }"/>"/>
								</c:forEach>
							</span>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<c:if test="${!empty resultRefuse }">
		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px">반려사유</p>
			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="15%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list03_td_bg">반려사유</th>
							<td class="list03_td">
								<textarea rows="5" style="width:95%" readonly><c:out value="${resultRefuse.EX_TEXT }"/></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</div>
	</c:if>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청일</th>
					<td class="list03_td" colspan="3">
						<fmt:parseDate var="dateString" value="${params.BEGDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="requestDate" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조사 유형</th>
					<td class="list03_td" colspan="3">
						<c:forEach var="result" items="${typeList}">
							<c:if test="${result.KEY eq params.CACCD }">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="type" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">대상</th>
					<td class="list03_td" >
						<span class="recipientType"></span>
						<input type="hidden" name="recipientType" value=""/>
					</td>
					<th class="list03_td_bg">대상자명</th>
					<td class="list03_td">
						<c:out value='${params.ENAME}'/>
						<input type="hidden" name="recipientName" value=""/>
					</td>
				</tr>

				<tr>
					<th class="list03_td_bg">경조기간</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.BEGDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="startDate" value="" />
						&nbsp;~&nbsp;
						<span class="endDate"></span>
						<input type="hidden" name="endDate" value="" />
					</td>
					<th class="list03_td_bg">휴가일수</th>
					<td class="list03_td">
						<span class="holiday"></span>
						<input type="hidden" name="holiday" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">경조금</th>
					<td class="list03_td">
						<span class="amount"></span>
						<input type="hidden" name="amount" value=""/>
					</td>
					<th class="list03_td_bg">화환(조화지급)</th>
					<td class="list03_td">
						<span class="garlandType"></span>
						<input type="hidden" name="garlandType" value=""/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="apprLineForm" name="apprLineForm" method="post">
	<input type="hidden" name="menuType" value="BE004"/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("input[name=imBegda]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#btnSave").click(function(){
			$.setParam("SAVE", "SAV");
		});
		
		$("#btnDelete").click(function(){
			$.setParam("DELETE", "DEL");
		});
		
		$("select[name=selSchkz], select[name=selVtart]").change(function(){
			$(this).parents("tr").find("td:last").find("span.rowInfo").find("input[name="+$(this).data("param")+"]").val($(this).val());
		});
		
		$.setParam = function(imEventId, imFlag){
			if($("input[name=chkFlag]:checked").length > 0){
				
				$.callProgress();

				$("#ajaxForm").html("");

				$("input[name=chkFlag]").each(function(index, value){
					if($(this).is(":checked")){
						var rowInfo = $("span.rowInfo").eq($("input[name=chkFlag]").index(this)).html();
						$("#ajaxForm").append(rowInfo);
					}
				});

				$("#ajaxForm").append("<input type=\"hidden\" name=\"rowCnt\" value=\""+$("input[name=chkFlag]:checked").length+"\"/>");
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\""+$("input[name=imBegda]").val()+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\""+imFlag+"\"/>");

				$jq.ajax({
					url : "<c:url value='/portal/moorimmss/diligencePayMng/altWorkBatchProcess.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					success : function(result) {
						
						if($.trim(result.EX_RESULT) == "E"){
							alert($.trim(result.EX_MESSAGE));
						}
						
						var data = result.ET_MESSAGE;
						var etMessageCnt = 0;
						
						for(var i = 0 ; i < data.length ; i++){
							<c:forEach items="${keySet}" var="key">
								$("#altWorkBatchForm").append("<input type=\"hidden\" name=\"etMessage_<c:out value="${key}"/>\" value=\""+data[i]["<c:out value="${key}"/>"]+"\"/>");
							</c:forEach>
							etMessageCnt++;
						}
						$("#altWorkBatchForm").append("<input type=\"hidden\" name=\"etMessageCnt\" value=\""+etMessageCnt+"\"/>");
						$("#altWorkBatchForm").submit();
					},
					complete : function(){
						$.stopProgress();
					}
				});
			}else{
				alert("선택된 데이터가 없습니다.");
			}
		};
		
		$.callRetrieveSchkzList = function(obj, imPernr, imDatum, init){
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\""+imPernr+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imDatum\" value=\""+imDatum+"\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/retrieveSchkzList.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					obj.html("");
					
					var etSchkz = result.ET_SCHKZ;
					
					for(var i = 0 ; i < etSchkz.length ; i++){
						if(etSchkz[i].KEY == init){
							obj.append("<option value=\""+etSchkz[i].KEY+"\" selected >"+etSchkz[i].VALUE+"</option>");
						}else{
							obj.append("<option value=\""+etSchkz[i].KEY+"\">"+etSchkz[i].VALUE+"</option>");
						}
					}
				},
				complete : function(){
					$("#ajaxForm").html("");
				}
			});
			
		};
		
		$.initSet = function(){
			<fmt:parseDate var="dateString" value="${resultMap.EX_BEGDA}" pattern="yyyyMMdd" />
			$("input[name=imBegda]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("span.imBegda").html("(<c:out value="${resultMap.EX_BEGWK}"/>)");
			
			$("select[name=selSchkz]").each(function(){
				var index = $("select[name=selSchkz]").index(this);
				$.callRetrieveSchkzList($(this), $("input[name=PERNR]").eq(index).val(), $("input[name=imBegda]").val(), $("input[name=SCHKZ]").eq(index).val());
			});
		};
		
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
		
		$("img.prev, img.next").click(function(){
			$.callProgress();
			$("#altWorkBatchForm").append("<input type=\"hidden\" name=\"moveDate\" value=\""+$(this).attr("class")+"\"/>");
			$("#altWorkBatchForm").submit();
		});
		
		$("input[name=imBegda]").change(function(){
			$.callProgress();
			$("#altWorkBatchForm").submit();
		});
		
		$.initSet();
	});
})(jQuery);;
</script>
<style>
.font {font-size:11px !important;}
</style>
<form id="altWorkBatchForm" name="altWorkBatchForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/altWorkBatchList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>대체근무 일괄입력</h1>
	
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td width="70px"><span class="f_333">대체근무일</span> <span class="redDot">*</span></td>
					<td>
						<input name="imBegda" id="imBegda" class="input datepicker" type="text" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						<span class="imBegda"></span>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="<c:url value="/base/images/ess/butt_prev.png"/>" alt="" height="20" width="20" class="prev" style="cursor:pointer;"/>
						<img src="<c:url value="/base/images/ess/butt_next.png"/>" alt="" height="20" width="20" class="next" style="cursor:pointer;"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
	</div>
	
	<c:set var="etVtart" value="${resultMap.ET_VTART }"/>
	<c:set var="etList" value="${resultMap.ET_LIST }"/>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="blktable" width="100%" class="font">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="chkAll" id="chkAll" value=""/></th>
					<th scope="col">처리상태</th>
					<th scope="col">처리결과</th>
					<th scope="col">부서명</th>
					<th scope="col">직급</th>
					
					<th scope="col">사번</th>
					<th scope="col">이름</th>
					<th scope="col">근무일정</th>
					<th scope="col">일일근무명</th>
					<th scope="col">등록상태</th>
					
					<th scope="col">대체근무시작일</th>
					<th scope="col">대체근무종료일</th>
					<th scope="col">대체근무유형</th>
					<th scope="col">대체근무일정</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="14">해당 데이터가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><input type="checkbox" name="chkFlag" value=""/></td>
								<td>
									<c:choose>
										<c:when test="${result.SUBRC eq 'S' }">
											<img src="<c:url value="/base/images/ess/green3_icon.gif"/>" alt=""/>
										</c:when>
										<c:when test="${result.SUBRC eq 'E' }">
											<img src="<c:url value="/base/images/ess/red3_icon.gif"/>" alt=""/>
										</c:when>
									</c:choose>
								</td>
								<td><c:out value="${result.MESSAGE}"/></td>
								<td><c:out value="${result.ORGTX}"/></td>
								<td><c:out value="${result.JIKWI}"/></td>
								
								<td><c:out value="${result.PERNR}"/></td>
								<td><c:out value="${result.ENAME}"/></td>
								<td><c:out value="${result.WTEXT}"/></td>
								<td><c:out value="${result.TTEXT}"/></td>
								<td><c:out value="${result.PERIOD}"/></td>
								
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td>
									<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td>
									<select name="selVtart" data-param="VTART" class="font">
										<c:forEach items="${etVtart}" var="sel">
											<option value="<c:out value="${sel.KEY }"/>" <c:if test="${sel.KEY eq result.VTART }">selected</c:if>><c:out value="${sel.VALUE }"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<select name="selSchkz" data-param="SCHKZ" class="font">
										
									</select>
									<span class="rowInfo">
										<c:forEach items="${keySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
	<div class="f_title">※대체근무 등록 : 유형 및 근무일정 필수 입력 -> 대상자 선택 후 저장</div>
</div>

<input type="hidden" name="imFirst" value="X"/>

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
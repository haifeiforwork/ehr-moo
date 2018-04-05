<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("#famailySave").click(function(){
			$.save("SAVE");
		});
		
		$("input[name=hshld]").click(function(){
			$.save("HSHLD_CHECK");
		});
		
		$("#familyAdd").click(function(){
			
			var target = "familyAdd";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=400px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_FAM}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etFamKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etFamValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_CHI}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etChiKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etChiValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etFamCnt\" value=\"<c:out value="${fn:length(resultMap.ET_FAM)}"/>\"/>");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etChiCnt\" value=\"<c:out value="${fn:length(resultMap.ET_CHI)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/familyAddPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#familyDel").click(function(){
			
			if($("input[name=perFuncFlag]:checked").length == 0){
				alert("삭제할 항목이 선택되지 않았습니다.");
				return;
			}
			
			var target = "familyDel";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=400px, height=300px, top=100px, left=200px, resizable=no";

			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_FAM}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etFamKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etFamValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_CHI}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etChiKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etChiValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etFamCnt\" value=\"<c:out value="${fn:length(resultMap.ET_FAM)}"/>\"/>");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etChiCnt\" value=\"<c:out value="${fn:length(resultMap.ET_Chi)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_ENDDA}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etEnddaKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etEnddaValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etEnddaCnt\" value=\"<c:out value="${fn:length(resultMap.ET_ENDDA)}"/>\"/>");
			
			var tr = $("input[name=perFuncFlag]:checked").parents("tr");
			var allTr = $("#perFunc").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();
			
			$("#popupForm").append(rowInfo);
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/familyDelPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnAddress").click(function(){
			
			var target = "addressPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=900px, height=400px, top=100px, left=200px, resizable=no";

			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			
			<c:forEach items="${addressKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_ADDRESS[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_ZSTATE}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etZstateKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etZstateValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etZstateCnt\" value=\"<c:out value="${fn:length(resultMap.ET_ZSTATE)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_ZTYPE}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etZtypeKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etZtypeValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etZtypeCnt\" value=\"<c:out value="${fn:length(resultMap.ET_ZTYPE)}"/>\"/>");

			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/addressRegViewPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();
		});
		
		$(".callFillPerson").click(function(){
			
			var imEventId = "";
			
			if($(this).attr("name") == "basicDeduction" && this.tagName == "INPUT"){
				imEventId = "DPTID_CHECK";
			}else if($(this).attr("name") == "singleParent" && this.tagName == "INPUT"){
				alert("한부모의 경우 배우자가 없는자로서 기본공제대상(20세이하) 직계비속\n및 입양자가 있는 근로자만 가능합니다.");
				imEventId = "SIGPR_CHECK";
			}else if($(this).attr("name") == "woman" && this.tagName == "INPUT"){
				imEventId = "WOMEE_CHECK";
			}else if($(this).attr("name") == "disabled" && this.tagName == "INPUT"){
				imEventId = "ZHNDEE_CHECK";
			}else if($(this).attr("name") == "seniors" && this.tagName == "INPUT"){
				imEventId = "ZSEVENTY_CHECK";
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\""+imEventId+"\"/>");
			
			var tr = $(this).parents("tr");
			var allTr = $("#perFunc").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();
			
			$("#ajaxForm").append(rowInfo);
			
			tr.find(".perFuncParam").each(function(){
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
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"EX_BEGDA\" value=\"<c:out value="${resultMap.EX_BEGDA}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"EX_ENDDA\" value=\"<c:out value="${resultMap.EX_ENDDA}"/>\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003FillPerson.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var exPerFunc = result.EX_PERSON_FUNCTION;
					var exLayout = result.EX_LAYOUT;
					
					if($.trim(result.EX_RESULT) == "S"){
						
					}else{
						alert(result.EX_MESSAGE);
					}
					
					if(exLayout.DPTID == "TRUE"){
						tr.find("input[name=basicDeduction]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=basicDeduction]").removeAttr("disabled");
					}
					
					if(exLayout.ZSEVENTY == "TRUE"){
						tr.find("input[name=seniors]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=seniors]").removeAttr("disabled");
					}
					
					if(exLayout.ZHNDEE == "TRUE"){
						tr.find("input[name=disabled]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=disabled]").removeAttr("disabled");
					}
					
					if(exLayout.HNDCD == "TRUE"){
						tr.find("select[name=disabledCode]").find("option[value=]").attr("selected", "selected");
						tr.find("select[name=disabledCode]").attr("disabled", "disabled");
					}else{
						tr.find("select[name=disabledCode]").removeAttr("disabled");
					}
					
					if(exLayout.SIGPR == "TRUE"){
						tr.find("input[name=singleParent]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=singleParent]").removeAttr("disabled");
					}
					
					if(exLayout.WOMEE == "TRUE"){
						tr.find("input[name=woman]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=woman]").removeAttr("disabled");
					}
					
					if(exLayout.ZINSID == "TRUE"){
						tr.find("input[name=premium]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=premium]").removeAttr("disabled");
					}
					
					if(exLayout.ZMEDID == "TRUE"){
						tr.find("input[name=medical]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=medical]").removeAttr("disabled");
					}
					
					if(exLayout.ZEDUID == "TRUE"){
						tr.find("input[name=education]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=education]").removeAttr("disabled");
					}
					
					if(exLayout.ZCREID == "TRUE"){
						tr.find("input[name=creditCard]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=creditCard]").removeAttr("disabled");
					}
					
					if(exLayout.ZCONID == "TRUE"){
						tr.find("input[name=donation]").attr("disabled", "disabled");
					}else{
						tr.find("input[name=donation]").removeAttr("disabled");
					}
					
					<c:forEach items="${perFuncKeySet}" var="key">
						<c:if test="${key eq 'DPTID'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=basicDeduction]").attr("checked", true);
							}else{
								tr.find("input[name=basicDeduction]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZSEVENTY'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=seniors]").attr("checked", true);
							}else{
								tr.find("input[name=seniors]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZHNDEE'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=disabled]").attr("checked", true);
							}else{
								tr.find("input[name=disabled]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'HNDCD'}">
							tr.find("select[name=disabledCode] option[value="+exPerFunc["<c:out value="${key}"/>"]+"]").attr("selected", "selected");
						</c:if>
						
						<c:if test="${key eq 'SIGPR'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=singleParent]").attr("checked", true);
							}else{
								tr.find("input[name=singleParent]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'WOMEE'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=woman]").attr("checked", true);
							}else{
								tr.find("input[name=woman]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZINSID'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=premium]").attr("checked", true);
							}else{
								tr.find("input[name=premium]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZMEDID'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=medical]").attr("checked", true);
							}else{
								tr.find("input[name=medical]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZEDUID'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=education]").attr("checked", true);
							}else{
								tr.find("input[name=education]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZCREID'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=creditCard]").attr("checked", true);
							}else{
								tr.find("input[name=creditCard]").removeAttr("checked");
							}
						</c:if>
						
						<c:if test="${key eq 'ZCONID'}">
							if(exPerFunc["<c:out value="${key}"/>"] == "X"){
								tr.find("input[name=donation]").attr("checked", true);
							}else{
								tr.find("input[name=donation]").removeAttr("checked");
							}
						</c:if>
						
						tr.find("span.rowInfo").find("input[name=<c:out value="${key}"/>]").val(exPerFunc["<c:out value="${key}"/>"]);
					</c:forEach>
					
				},complete : function(){
					$("#ajaxForm").html("");
				}
			});
		});
		
	});
})(jQuery);;
</script>
<div class="table_box2">
	<span class="list02">
		<table border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="170px"/>
				<col width="450px"/>
				<col width="170px"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="list02_td_bg">소득자 성명</th>
				<td class="list02_td_L"><c:out value="${resultMap.EX_PER_INFO.ENAME }"/></td>
				<th class="list02_td_bg">주민등록번호</th>
				<td class="list02_td_L"><c:out value="${resultMap.EX_PER_INFO.ZREGNO }"/></td>
			</tr>
			<tr>
				<th class="list02_td_bg">근무처 명칭</th>
				<td class="list02_td_L"><c:out value="${resultMap.EX_PER_INFO.NAME }"/></td>
				<th class="list02_td_bg">사업자등록번호</th>
				<td class="list02_td_L"><c:out value="${resultMap.EX_PER_INFO.STCD2 }"/></td>
			</tr>
			<tr>
				<th class="list02_td_bg">주민등록상 주소</th>
				<td class="list02_td_L"><c:out value="${resultMap.EX_PER_INFO.ZADDRESS }"/></td>
				<th class="list02_td_bg">세대주 여부</th>
				<td class="list02_td_L">
					<label for="hshld"><input type="checkbox" name="hshld" id="hshld" class="taxParam" <c:if test="${resultMap.EX_TAX.HSHLD eq 'X' }"> checked </c:if> <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>>&nbsp;세대주 여부</label>
				</td>
			</tr>
			<tr>
				<th class="list02_td_bg">연락처</th>
				<td colspan="3" class="list02_td_L"><c:out value="${resultMap.EX_PER_INFO.ZCELLP }"/></td>
			</tr>
		</table>
	</span>
	<table width="100%" border="0" class="margin_delimiter">
		<colgroup>
			<col width="10px"/>
			<col width="660px"/>
			<col width="*"/>
		</colgroup>
		<tbody>
			<tr>
				<td width="10">
					<img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/>
				</td>
				<td>연말정산 소득공제 신청 전 반드시 주소/연락처를 확인하세요.</td>
				<td align="right">
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnAddress"><span>주소/연락처 수정</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	
	<p class="f_title margin5_0">인적공제</p>
	
	<div class="list01">
		<table border="0" cellpadding="0" cellspacing="0" id="perFunc" width="100%">
			<colgroup>
				<col width="30px"/>
				<col width="130px"/>
				<col width="70px"/>
				<col width="120px"/>
				<col width="80px"/>
				
				<col width="30px"/>
				<col width="50px"/>
				<col width="80px"/>
				<col width="50px"/>
				<col width="180px"/>
				<col width="50px"/>
				
				<col width="50px"/>
				<col width="50px"/>
				<col width="50px"/>
				<col width="50px"/>
				<col width="50px"/>
				
				<col width="50px"/>
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>구분</th>
					<th>성명</th>
					<th>주민등록번호</th>
					<th>종료일자</th>
					
					<th>기본<br/>공제</th>
					<th>경로<br/>우대자</th>
					<th>자녀 수</th>
					<th>장애인</th>
					<th>장애인 코드</th>
					<th>한부모</th>
					
					<th>부녀자</th>
					<th>보험료</th>
					<th>의료비</th>
					<th>교육비</th>
					<th>신용<br/>카드</th>
					
					<th>기부금</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resultMap.ET_PERSON_FUNCTION }" var="etPerFunc" varStatus="inx">
					<c:set var="exPerFunc" value="${exPerFuncList[inx.index] }"/>
					<tr>
						<td>
							<input type="radio" name="perFuncFlag">
						</td>
						<td><c:out value="${exPerFunc.DPTYP_TEXT }"/></td>
						<td><c:out value="${exPerFunc.ZNAME }"/></td>
						<td><c:out value="${fn:substring(exPerFunc.REGNO, 0, 6) }"/>-<c:out value="${fn:substring(exPerFunc.REGNO, 6, 13) }"/></td>
						<td>
							<fmt:parseDate var="dateString" value="${exPerFunc.ZENDDA}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						
						<td>
							<input type="checkbox" name="basicDeduction" class="callFillPerson perFuncParam" <c:if test="${exPerFunc.DPTID eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].DPTID eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<input type="checkbox" name="seniors" class="callFillPerson perFuncParam" <c:if test="${exPerFunc.ZSEVENTY eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZSEVENTY eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<select name="childCntCode" class="perFuncParam w90per" <c:if test="${exPerFunc.DPTYP != '4'}"> disabled </c:if>>
								<c:forEach items="${resultMap.ET_CHI }" var="result">
									<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exPerFunc.KDBSL }"> selected </c:if>><c:out value="${result.VALUE}"/></option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="checkbox" name="disabled" class="callFillPerson perFuncParam" <c:if test="${exPerFunc.ZHNDEE eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZHNDEE eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<select name="disabledCode" class="perFuncParam w90per" <c:if test="${exLayoutList[inx.index].HNDCD eq 'TRUE'}"> disabled </c:if>>
								<c:forEach items="${resultMap.ET_HNDCD }" var="result">
									<option value="<c:out value="${result.KEY }"/>" <c:if test="${result.KEY eq exPerFunc.HNDCD }"> selected </c:if>><c:out value="${result.VALUE}"/></option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input type="checkbox" name="singleParent" class="callFillPerson perFuncParam" <c:if test="${exPerFunc.SIGPR eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].SIGPR eq 'TRUE'}"> disabled </c:if>>
						</td>
						
						<td>
							<input type="checkbox" name="woman" class="callFillPerson perFuncParam" <c:if test="${exPerFunc.WOMEE eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].WOMEE eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<input type="checkbox" name="premium" class="perFuncParam" <c:if test="${exPerFunc.ZINSID eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZINSID eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<input type="checkbox" name="medical" class="perFuncParam" <c:if test="${exPerFunc.ZMEDID eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZMEDID eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<input type="checkbox" name="education" class="perFuncParam" <c:if test="${exPerFunc.ZEDUID eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZEDUID eq 'TRUE'}"> disabled </c:if>>
						</td>
						<td>
							<input type="checkbox" name="creditCard" class="perFuncParam" <c:if test="${exPerFunc.ZCREID eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZCREID eq 'TRUE'}"> disabled </c:if>>
						</td>
						
						<td>
							<input type="checkbox" name="donation" class="perFuncParam" <c:if test="${exPerFunc.ZCONID eq 'X'}"> checked </c:if> <c:if test="${exLayoutList[inx.index].ZCREID eq 'TRUE'}"> disabled </c:if>>
							<span class="rowInfo">
								<c:forEach items="${perFuncKeySet }" var="key">
									<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${exPerFunc[key]}"/>"/>
								</c:forEach>
							</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<table width="100%" border="0" class="margin_delimiter">
		<colgroup>
			<col width="10px"/>
			<col width="560px"/>
			<col width="*"/>
		</colgroup>
		<tbody>
			<tr>
				<td>
					<img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/>
				</td>
				<td>가족사항 삭제 필요시, 각 공장운영부/HR운영팀으로 연락 바랍니다. </td>
				<td align="right">
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="familyAdd"><span>가족사항 추가</span></a>
					<a href="#" class="button_img05" id="familyDel"><span>가족사항 삭제</span></a>
					<a href="#" class="button_img05" id="famailySave"><span>가족사항 저장</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="delimiter"></div>
	
	<span class="f_blue f_B16">참고사항</span>
	
	<div class="list04">
		<table border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="10px"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<td colspan="2">
						<span class="list04_td">1. 연령기준 </span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<span class="list04_td"> 가. 경 로 자 (1947). 12. 31. 이전 출생(70세 이상 : 100만원 공제) </span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<span class="list04_td"> 나. 직계존속 기본공제 대상자 1957.12.31 이전 출생 (만 60세 이상 : 인당 150만원 공제) </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">2. 관계코드 : 소득자 본인=0, 소득자의 직계존속=1, 배우자 직계존속=2, 배우자=3, 직계비속 자녀=4, 직계비속 자녀 외=5, 형제자매=6, 수급자=7,</span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<span class="list04_td">	위탁아동=8을 기입합니다. (4·5·6의 경우 소득자와 배우자의 각각의 관계를 포함합니다). </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">3. 내·외국인 : 내국인=1, 외국인=9로 구분하여 기입합니다. </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">4. 중도입사자의 경우 근무기간에 지출한 비용에 대해서만 소득공제를 받을 수 있습니다. </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">5. 부녀자 공제란에는 여성 근로소득자 본인에 한해 적용여부를 표시합니다. (종합소득금액 3천만원 이하인 경우에 한하여 연 50만원 공제 가능) </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">6. 한부모공제 : 배우자가 없는 자로서 기본공제대상(20세이하) 직계비속 입양자가 있는 근로자는 연 100만원 인적공제가 가능합니다. </span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<span class="list04_td">(한부모공제와 부녀자공제는 중복이 불가능합니다) </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">7. 6세이하 공제 : 6세이하의 기본공제 대상 자녀 (입양자 및 위탁아동 포함)</span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<span class="list04_td"> 1) 1명을 초과하는 1명당 연 15만원 </span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<span class="list04_td"> 2) 2명 이하까지 1명당 연 15만원, 2명을 초과하는 1명당 연30만원 </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">8. 출산/입양공제: 해당 과세기간에 출생하거나 입양한 기본공제 대상 자녀가 있는 경우 첫째 30만원, 둘째 50만원, 셋째이상 70만원 </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td">9. 직불카드 등에는 「여신전문금융업법」 제 2조에 따른 직불카드 등 「조세특례제한법」 제 126조2제1항제4호에 해당하는 금액을 적습니다. </span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span class="list04_td"> 10. 장애인 식별코드 </span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="list05">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="35px"/>
				<col width="375px"/>
				<col width="*"/>
			</colgroup>
		
			<tbody>
				<tr>
					<th>코드</th>
					<th>구분</th>
					<th>증빙서류</th>
				</tr>
				<tr>
					<td>1</td>
					<td>장애인 복지법에 따른 장애인</td>
					<td>장애인 복지카드 사본</td>
				</tr>
				<tr>
					<td>2</td>
					<td>상이자 및 이와 유사한 자로서 근로능력이 없는 자</td>
					<td>발급기관의 증명서류</td>
				</tr>
				<tr>
					<td>3</td>
					<td>그 밖에 항시 치료를 요하는 중증환자</td>
					<td>병원에서 발급한 장애인 증명서 (건강보험공단의 중증환자증 제외)</td>
				</tr>
			</tbody>
		</table>
	</div>
	<p>11. 기본공제 대상자가 작년대비 변경이 없어도 주민등록등본 혹은 가족관계 증명서를 반드시 공장운영부/HR운영팀으로 제출하셔야 합니다. </p>
	<p>&nbsp;</p>
</div>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
(function($) {
	$(document).ready(function(){
		$("#searchButton").click(function(){

			$("#zipCodeForm").find("input[name=eventId]").remove();
			$("#zipCodeForm").append("<input type=\"hidden\" name=\"eventId\" value=\"SEARCH\"/>");
			$("#zipCodeForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");

			$("#zipCodeForm").submit();
		});
		
		$("input[name=keyword]").keyup(function(e){
			if(e.which == 13){
				$("#searchButton").click();
			}
		});
		
		$("a.link").click(function(){
			$("#ajaxForm").html("");
			$("#ajaxForm").append($(this).parents("tr").find("td:last").find("span.rowInfo").html());
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\"CLICK\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"<c:out value="${params.imFlag}"/>\"/>");

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/setZipCode.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						$(opener.document).find("select.STATE option[value="+result.EX_STATE+"]").attr("selected", "selected");
						$(opener.document).find("input.PSTLZ").val(result.EX_PSTLZ);
						$(opener.document).find("input.ORT01").val(result.EX_ORT01);
						$(opener.document).find("input.ORT02").val(result.EX_ORT02);
						$(opener.document).find("input.STRAS").val(result.EX_STRAS);
						$(opener.document).find("input.LOCAT").val(result.EX_LOCAT);

						self.close();
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});

		$.initSet = function(){
			$("input[name=searchType][value=<c:out value='${params.searchType}'/>]").attr("checked", true);
			$("input[name=keyword]").val("<c:out value="${params.keyword}"/>");
		};

		$.initSet();
	});
})(jQuery);
//-->
</script>
<div id="wrap" style="padding-top:10px; padding-left:7px;">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>우편번호 검색</h1>
	<form id="zipCodeForm" name="zipCodeForm" method="post" action="<c:url value='/portal/moorimess/personalMng/zipCodePop.do'/>">
		<div class="search_box">
			<table border="0">
				<tbody>
					<tr>
						<td><span class="f_333">검색유형&nbsp;</span></td>
						<td>
							<label for="road"><input type="radio" id="road" name="searchType" value="ROAD">&nbsp;도로명주소로 검색</label>&nbsp;
							<label for="dong"><input type="radio" id="dong" name="searchType" value="DONG">&nbsp;지번주소로 검색</label>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="search_delimiter"></div>
			<table border="0">
				<tbody>
					<tr>
						<td><span class="f_333">검색어</span></td>
						<td>
							<input type="text" name="keyword" value="" class="input w150px"/>
							<input type="text" name="dummy" value="" style="display:none;"/>
						</td>
						<td>
							<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="f_title">- 도로명 건물번호 입력 (예) 강남대로 656 / 남강로 1317번길 24</div>
		<div class="f_title">- 동 지번 입력 (예) 신사동 505 / 상대동 41-6 </div>
	
		<div class="button_box"></div>
	
		<c:set var="etList" value="${result.ET_LIST }"/>
	
		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="45%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th>우편번호</th>
						<th>도로명주소</th>
						<th>지번주소</th>
					</tr>
					<c:choose>
						<c:when test="${empty etList}">
							<tr>
								<td colspan="3">조회된 데이터가 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${result.ET_LIST}">
								<tr>
									<td>
										<a href="#" class="link board_2"><c:out value="${result.ZIPNO}"/></a>
									</td>
									<td>
										<a href="#" class="link board_2"><c:out value="${result.LNMADRES}"/></a>
									</td>
									<td>
										<a href="#" class="link board_2"><c:out value="${result.RNADRES}"/></a>
										<span class="rowInfo">
											<input type="hidden" name="ZIPNO" value="<c:out value="${result.ZIPNO}"/>"/>
											<input type="hidden" name="LNMADRES" value="<c:out value="${result.LNMADRES}"/>"/>
											<input type="hidden" name="RNADRES" value="<c:out value="${result.RNADRES}"/>"/>
										</span>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</form>
</div>

<form name="ajaxForm" id="ajaxForm" method="post">
</form>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>

<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("select[name=relations]").live("change", function(){
			if("11" == $(this).val()){
				$("#childDiv").show();
			}else{
				$("#childDiv").hide();
			}
		});
		
		$("#btnClose").click(function(){
			self.close();
		});
		
		$("#btnSave").click(function(){
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZyear\" value=\"<c:out value="${params.EX_YEAR}"/>\"/>");
			
			$(".parameter").each(function(){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/payMng/callPY003OninputPop12.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						opener.$jq.refresh(0);
						self.close();
					}else{
						alert(result.EX_MESSAGE);
					}
				},complete : function(){
				}
			});
		});
		
		$.initSet = function(){};
		
		$.initSet();
	});
})(jQuery);
</script>
<form id="familyAddForm" name="familyAddForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>가족사항 추가</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="30%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">성명</th>
					<td class="list03_td">
						<input type="text" name="lastName" value="" class="input parameter w70px"/>&nbsp;
						<input type="text" name="firstName" value="" class="input parameter w150px"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">주민번호</th>
					<td class="list03_td">
						<input type="text" name="regNo1" maxlength="6" value="" class="input parameter w70px"/>&nbsp;-&nbsp;<input type="text" name="regNo2" maxlength="7" value="" class="input parameter w70px"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">관계</th>
					<td class="list03_td">
						<select name="relations" class="parameter w150px">
							<c:forEach var="result" items="${resultMap.ET_FAM}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="childDiv" style="display: none;">
					<th class="list03_td_bg">자녀 수</th>
					<td class="list03_td">
						<select name="childCnt" class="parameter w150px">
							<c:forEach var="result" items="${resultMap.ET_CHI}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 위탁아동/입양 가족추가는 공장운영부/HR운영팀으로 요청바랍니다.</td>
			</tr>
		</tbody>
	</table>
</div>
</form>
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
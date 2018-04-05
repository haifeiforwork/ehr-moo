<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/treeview/jquery.treeview.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/treeview/jquery.treeview.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#tree").treeview({
			collapsed: false,
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "location"
		});
		
		$("a[name=linked]").live("click", function(){
			$.callProgress();

			var param = $(this).data("param").toString();
			
			if(param.indexOf("+") > - 1){
				$("#laborInterviewForm").append("<input type=\"hidden\" name=\"imPernr\" value=\""+param.split("+")[1]+"\"/>");
			}else{
				$("#laborInterviewForm").append("<input type=\"hidden\" name=\"imPernr\" value=\""+param+"\"/>");
			}

			$("#laborInterviewForm").submit();
			
		});
		
		
		$("a[name=linkField]").click(function(event){
			
			$("#ajaxForm").html("");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DETAIL\"/>");
			
			<c:forEach items="${userKeySet}" var="key">
			$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_USERINFO[key]}"/>\"/>");
			</c:forEach>
			
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index);
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\""+rowInfo.find("input[name=PERNR]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\""+rowInfo.find("input[name=BEGDA]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEndda\" value=\""+rowInfo.find("input[name=ENDDA]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imSeqnr\" value=\""+rowInfo.find("input[name=SEQNR]").val()+"\"/>");
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborInterviewView.do'/>");
			$("#ajaxForm").submit();
		});
		
		$("#searchButton").click(function(){
			
			if($.trim($("input[name=imWord]").val()) == ""){
				return;
			}
			
			$("#ajaxForm").html("");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imWord\" value=\""+$("input[name=imWord]").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DISPLAY\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/laborMng/retrievePd023Oninput.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					
					var etSearch = result.ET_SEARCH;
					var obj = $("#empSearch").find("tbody");
					var sb = "";
					obj.html("");
					if($.trim(result.EX_RESULT) == "S"){
						$("#empSearch").show();
						if(etSearch.length == 0){
							sb = "";
							sb += "<tr>";
							sb += "	<td colspan=\"3\">검색된 사원이 없습니다.</td>";
							sb += "</tr>";
							obj.append(sb);
						}else{
							for(var i = 0 ; i < etSearch.length ; i++){
								sb = "";
								sb += "<tr>";
								sb += "	<td><a href=\"#\" name=\"linked\" data-param=\""+etSearch[i].PERNR+"\" class=\"board_2\">"+etSearch[i].PERNR+"</a></td>";
								sb += "	<td>"+etSearch[i].ENAME+"</td>";
								sb += "	<td>"+etSearch[i].ORGTX+"</td>";
								sb += "</tr>";
								obj.append(sb);
							}
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});
		
		$("#btnWrite").click(function(){
			
			if("<c:out value="${params.imPernr}"/>" == ""){
				alert("대상 사원을 선택 해 주세요.");
			}else{
				$("#ajaxForm").html("");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"CREATE\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");
				
				<c:forEach items="${userKeySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_USERINFO[key]}"/>\"/>");
				</c:forEach>
				
				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborInterviewView.do'/>");
				$("#ajaxForm").submit();
			}
		});
		
		$("#btnModify").click(function(){
			
			if($("input:radio[name=flag]:checked").length > 0){
				$("#ajaxForm").html("");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"MODIFY\"/>");
				
				<c:forEach items="${userKeySet}" var="key">
				$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_USERINFO[key]}"/>\"/>");
				</c:forEach>
				
				var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
				var rowInfo = $("span.rowInfo").eq(index);
				
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\""+rowInfo.find("input[name=PERNR]").val()+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\""+rowInfo.find("input[name=BEGDA]").val()+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEndda\" value=\""+rowInfo.find("input[name=ENDDA]").val()+"\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imSeqnr\" value=\""+rowInfo.find("input[name=SEQNR]").val()+"\"/>");
				
				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborInterviewView.do'/>");
				$("#ajaxForm").submit();
			}else{
				alert("라인을 선택하세요.");
			}
		});
		
		$("#btnDelete").click(function(){
			if($("input:radio[name=flag]:checked").length > 0){
				
				$("#ajaxForm").html("");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"DELETE\"/>");
				$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");
				
				var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
				var rowInfo = $("span.rowInfo").eq(index).html();
				
				$("#ajaxForm").append(rowInfo);
				
				$jq.ajax({
					url : "<c:url value='/portal/moorimmss/laborMng/retrievePd023Oninput.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async: false,
					success : function(result) {
						alert(result.EX_MESSAGE);
						if($.trim(result.EX_RESULT) == "S"){
							$.callProgress();
							
							$("#laborInterviewForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");
							$("#laborInterviewForm").append("<input type=\"hidden\" name=\"imAllaFlag\" value=\"<c:out value="${resultMap.EX_ALLA_FLAG}"/>\"/>");
							$("#laborInterviewForm").append("<input type=\"hidden\" name=\"imAllaFlag2\" value=\"<c:out value="${resultMap.EX_ALLA_FLAG2}"/>\"/>");
							$("#laborInterviewForm").submit();
						}else{
						}
					}
				});
			}else{
				alert("라인을 선택하세요.");
			}
		});
		
		$.initSet = function(){
			$("span.PERNR").html("<c:out value="${params.imPernr}"/>");
			$("span.ENAME").html("<c:out value="${resultMap.EX_ENAME}"/>");
			$("span.ORGTX").html("<c:out value="${resultMap.EX_ORGTX}"/>");
		};
		
		$.changeWidth();
		
		$.initSet();
	});
	
	$(window).resize(function() {
		$.changeWidth();
	});
	
	$.changeWidth = function(){
		var width = $(window).width();
		$(".rigthFrame").css({width:width-240});
	};
})(jQuery);;
</script>
<style>
.leftFrame {
	width:220px;
	max-height:650px;
	overflow-y:auto;
	display:inline-block;
	position:absolute;
	top:45px;
}

.rigthFrame{
	display:inline-block;
	position:absolute;
	top:45px;
	left:230px;
	max-height:630px;
	overflow:auto;
}

.table_box{
	width:97.5%;
}
</style>
<form id="laborInterviewForm" name="laborInterviewForm" method="post" action="<c:url value='/portal/moorimmss/laborMng/laborInterviewList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>면담일지</h1>
	
	<c:set var="etTview" value="${resultMap.ET_TVIEW }"/>
	
	<div id="sidetree" class="leftFrame">
		<p class="f_title" style="padding-bottom:10px;">검색 TREE</p>
		<div class="treeheader"></div>
		<div id="sidetreecontrol"></div>
		<ul class="treeview" id="tree">
			<c:forEach var="result" items="${etTview }" varStatus="inx">
				<c:choose>
					<c:when test="${inx.first }">
						<c:remove var="preResult"/>
					</c:when>
					<c:when test="${inx.last }">
						<c:remove var="nextResult"/>
					</c:when>
					<c:otherwise>
						<c:set var="preResult" value="${etTview[inx.index-1] }"/>
						<c:set var="nextResult" value="${etTview[inx.index+1] }"/>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${empty result.CLICK}">
						<c:choose>
							<c:when test="${!empty preResult && empty preResult.CLICK && result.PARENTID ne preResult.CHILDID}">
									</ul>
								</li>
							</c:when>
							<c:otherwise>
								<c:if test="${lastFlag eq 'Y' }">
									<c:if test="${!empty preResult && preResult.TLEVEL > result.TLEVEL }">
										<c:forEach begin="0" end="${preResult.TLEVEL - result.TLEVEL }" varStatus="i">
												</ul>
											</li>
										</c:forEach>
									</c:if>
									<c:if test="${!empty preResult && preResult.TLEVEL eq result.TLEVEL }">
											</ul>
										</li>
									</c:if>
								</c:if>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${result.STATUS eq 'open'}">
								<c:set var="openFlag" value="collapsable"/>
								<c:set var="disabledFlag" value=""/>
							</c:when>
							<c:otherwise>
								<c:set var="openFlag" value="expandable"/>
								<c:set var="disabledFlag" value="none"/>
							</c:otherwise>
						</c:choose>
						
						<li class="<c:out value="${openFlag }"/>">
							<div class="hitarea <c:out value="${openFlag }"/>-hitarea"></div>
							<a href="#"><img src="<c:url value="/base/images/ess/s_B_OGUN.gif"/>" alt=""/><c:out value="${result.TEXT }"/></a>
							<ul style="display:<c:out value="${disabledFlag }"/>">
					</c:when>
					<c:otherwise>
						<c:if test="${!empty nextResult && empty nextResult.CLICK}">
							<li class="last">
								<a href="#" name="linked" data-param="<c:out value="${result.CLICK }"/>">
									<c:choose>
										<c:when test="${result.IMG eq 'ICON_MANAGER' }">
											<img src="<c:url value="/base/images/ess/s_B_MNGR.gif"/>" alt=""/>
										</c:when>
										<c:when test="${result.IMG eq 'ICON_EMPLOYEE' }">
											<img src="<c:url value="/base/images/ess/s_B_EMPL.gif"/>" alt=""/>
										</c:when>
									</c:choose>
									<c:out value="${result.TEXT }"/>
								</a>
							</li>
							<c:set var="lastFlag" value="Y"/>
						</c:if>
						
						<c:if test="${!empty nextResult && !empty nextResult.CLICK }">
							<li>
								<a href="#" name="linked" data-param="<c:out value="${result.CLICK }"/>">
									<c:choose>
										<c:when test="${result.IMG eq 'ICON_MANAGER' }">
											<img src="<c:url value="/base/images/ess/s_B_MNGR.gif"/>" alt=""/>
										</c:when>
										<c:when test="${result.IMG eq 'ICON_EMPLOYEE' }">
											<img src="<c:url value="/base/images/ess/s_B_EMPL.gif"/>" alt=""/>
										</c:when>
									</c:choose>
									<c:out value="${result.TEXT }"/>
								</a>
							</li>
							<c:set var="lastFlag" value="N"/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
	
	<div class="rigthFrame">
		<p class="f_title" style="padding-bottom:10px;">사원 검색</p>
		
		<div class="search_box">
			<table border="0">
				<tbody>
					<tr>
						<td><span class="f_333">사원  검색</span></td>
						<td>
							<input type="text" name="imWord" value="" class="input"/>
						</td>
						<td>
							<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="list01" style="height:155px; overflow-y:auto; display:none" id="empSearch">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="33%">사원번호</th>
						<th scope="col" width="33%">성명</th>
						<th scope="col" width="*">조직명</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		
		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">면담 리스트</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="16%"/>
						<col width="16%"/>
						<col width="16%"/>
						<col width="16%"/>
						<col width="16%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
					<tr>
						<th class="list02_td_bg">사원번호</th>
						<td class="list02_td f_left"><span class="PERNR"></span></td>
						<th class="list02_td_bg">성명</th>
						<td class="list02_td f_left"><span class="ENAME"></span></td>
						<th class="list02_td_bg">조직명</th>
						<td class="list02_td f_left"><span class="ORGTX"></span></td>
					</tr>
				</table>
			</span>
		</div>
		
		<!-- 상단버튼-->
		<div class="button_box">
			<a href="#" class="button_img01" id="btnWrite"><span>작성</span></a>
			<a href="#" class="button_img01" id="btnModify"><span>수정</span></a>
			<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</div>
		
		<c:set var="etPerinfo" value="${resultMap.ET_PERINFO }"/>
		
		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="35px"></th>
						<th scope="col" width="24%">면담일자</th>
						<th scope="col" width="24%">면담자</th>
						<th scope="col" width="24%">면담자 조직명</th>
						<th scope="col" width="24%">면담장소</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty etPerinfo}">
							<tr>
								<td colspan="5">해당 데이터가 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${etPerinfo}">
								<tr>
									<td><input type="radio" name="flag" value=""/></td>
									<td>
										<fmt:parseDate var="dateString" value="${result.ITVDT}" pattern="yyyyMMdd" />
										<a href="#" class="board_2" name="linkField"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
									</td>
									<td><c:out value="${result.ITVEN}"/></td>
									<td><c:out value="${result.ORGTX}"/></td>
									<td class="f_left">
										<c:out value="${result.ITVLO}"/>
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
		
		
	</div>
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
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/tablesort/jquery.tablesorter.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/tablesort/style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/tab/style.css"/>" />

<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var popup;
		
		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("select[name=imMonth] option[value=<c:out value='${params.imMonth}'/>]").attr("selected", "selected");

		$("#searchButton").click(function() {
			$.callProgress();
			$("input[name=setEmpFlag]").val("<c:out value="${params.setEmpFlag}"/>");
			$("input[name=selEmpNo]").val("<c:out value="${params.imPernr}"/>");
			$("#approvalForm").submit();
		});

		$("a.link").click(function(){
			$("#popupForm").html("");
			$("#popupForm").append($(this).parents("tr").find("td:last").find("span.rowInfo").html());
			$("#popupForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");

			var target = "approvalPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=800px, height=600px, top=200px, left=300px, resizable=yes";

			popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimhass/approvalPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();
		});

		$("#btnApply").click(function(){
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

				$jq.ajax({
					url : "<c:url value='/portal/moorimhass/approveProcess.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					success : function(result) {
						alert(result.EX_MESSAGE);
						$("#searchButton").click();
					},
					complete : function(){
						$.stopProgress();
					}
					
				});
			}else{
				alert("선택된 데이터가 없습니다.");
			}
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
		
		$("a[name=detail]").click(function(){
			$("#popupForm").html("");
			$("#popupForm").append("<input type=\"hidden\" name=\"selEmpNo\" value=\"<c:out value="${params.imPernr}"/>\"/>");

			var target = "detailPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1200px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/personInfoDetailPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();

		});

		<c:set var="list" value="${resultList.ET_LIST }"/>
		<c:set var="list2" value="${resultList.ET_LIST2 }"/>

		//Table sort
		<c:if test="${!empty list}">
			$("#list").tablesorter({
				 headers:{0:{sorter: false}}
			});
		</c:if>

		<c:if test="${!empty list2}">
		$("#list2").tablesorter();
		</c:if>

		//On Click Event
		$("ul.tabs li").click(function() {

			$("ul.tabs li").removeClass("active"); //Remove any "active" class
			$(this).addClass("active"); //Add "active" class to selected tab
			$(".tab_content").hide(); //Hide all tab content

			var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
			$(activeTab).fadeIn(); //Fade in the active ID content
			
			$("input[name=tabIndex]").val($("ul.tabs li").index(this));
			return false;
		});
		
		$.search = function(){
			
			if(popup != undefined){
				popup.close();
			}
			
			$.callProgress();
			$("#approvalForm").submit();
		};

		$("ul.tabs li").eq("<c:out value="${params.tabIndex}"/>").click();
		//Tab End
	});
})(jQuery);;
</script>
<form id="approvalForm" name="approvalForm" method="post" action="<c:url value='/portal/moorimhass/hrApproval.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>HR결재</h1>
	
	<%@include file="/WEB-INF/view/portal/webmss/personalInfo.jsp"%>
	
	<div class="table_box">
		<div class="search_box">
			<table border="0">
				<tbody>
					<tr>
						<td><span class="f_333">조회년월</span><td>
						<td>
							<select name="imYear" id="imYear">
								<c:forEach var="result" items="${yearList }">
									<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
								</c:forEach>
							</select>
							<select name="imMonth" id="imMonth">
								<c:forEach var="result" items="${monthList }">
									<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
								</c:forEach>
							</select>
						</td>
						<td>
							<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
			
		<div class="button_box"></div>
		
		<ul class="tabs" style="margin-bottom:20px">
			<li><a href="#tab1">결재중</a></li>
			<li><a href="#tab2">결재완료</a></li>
		</ul>
			
		<div class="tab_container">
			<div id="tab1" class="tab_content" style="display:none">
				<div class="button_box">
					<a href="#" class="button_img01" id="btnApply"><span>승인</span></a>
				</div>
				<div class="list01">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="list" class="tablesorter">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="4%"><input type="checkbox" name="chkAll" id="chkAll" value=""/></th>
								<th scope="col" width="12%">요청일</th>
								<th scope="col" width="12%">요청자</th>
								<th scope="col" width="*">구분</th>
								<th scope="col" width="12%">시작일</th>
								<th scope="col" width="12%">종료일</th>
								<th scope="col" width="12%">결재자 결재</th>
								<th scope="col" width="12%">최종 결재</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty list}">
									<tr>
										<td colspan="8" class="emptyRecord center">해당 결재현황 검색 내역이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${list}">
										<tr>
											<td><input type="checkbox" name="chkFlag" value=""/></td>
											<td>
												<fmt:parseDate var="dateString" value="${result.FREQDT}" pattern="yyyyMMdd" />
												<a class="link board_2" href="#"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
											</td>
											<td>
												<a class="link board_2" href="#"><c:out value="${result.RNAME}"/></a>
											</td>
											<td>
												<a class="link board_2" href="#"><c:out value="${result.INFTYT}"/></a>
											</td>
											<td>
												<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
												<a class="link board_2" href="#"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
											</td>
											<td>
												<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
												<a class="link board_2" href="#"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
											</td>
											<td>
												<a class="link board_2" href="#"><c:out value="${result.ASTATT}"/></a>
											</td>
											<td>
												<a class="link board_2" href="#"><c:out value="${result.FASTATT}"/></a>
												<span class="rowInfo">
													<c:forEach items="${keySet }" var="key">
														<input type="hidden" name="<c:out value="${key }"/>" value="<c:out value="${result[key] }"/>"/>
													</c:forEach>
													<input type="hidden" name="approveFlag" value="N"/>
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
			<div id="tab2" class="tab_content" style="display:none">
				<div class="list01">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="list2" class="tablesorter">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="14%">요청일</th>
								<th scope="col" width="14%">요청자</th>
								<th scope="col" width="*">구분</th>
								<th scope="col" width="14%">시작일</th>
								<th scope="col" width="14%">종료일</th>
								<th scope="col" width="14%">결재자 결재</th>
								<th scope="col" width="14%">최종 결재</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty list2}">
									<tr>
										<td colspan="7" class="emptyRecord center">해당 결재현황 검색 내역이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${list2}">
										<tr>
											<td>
												<fmt:parseDate var="dateString" value="${result.FREQDT}" pattern="yyyyMMdd" />
												<a class="link board_2" href="#"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
											</td>
											<td><a class="link board_2" href="#"><c:out value="${result.RNAME}"/></a></td>
											<td><a class="link board_2" href="#"><c:out value="${result.INFTYT}"/></a></td>
											<td>
												<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
												<a class="link board_2" href="#"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
											</td>
											<td>
												<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
												<a class="link board_2" href="#"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
											</td>
											<td><a class="link board_2" href="#"><c:out value="${result.ASTATT}"/></a></td>
											<td>
												<a class="link board_2" href="#"><c:out value="${result.FASTATT}"/></a>
												<span class="rowInfo">
													<input type="hidden" name="APPID" value="<c:out value="${result.APPID }"/>"/>
													<input type="hidden" name="APLEV" value="<c:out value="${result.APLEV }"/>"/>
													<input type="hidden" name="INFTY" value="<c:out value="${result.INFTY }"/>"/>
													<input type="hidden" name="ASTAT" value="<c:out value="${result.ASTAT }"/>"/>
													<input type="hidden" name="approveFlag" value="Y"/>
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
	</div>
</div>

<input type="hidden" name="tabIndex" value=""/>
<input type="hidden" name="selEmpNo" value=""/>
<input type="hidden" name="setEmpFlag" value=""/>

</form>

<form id="popupForm" name="popupForm" method="post">
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
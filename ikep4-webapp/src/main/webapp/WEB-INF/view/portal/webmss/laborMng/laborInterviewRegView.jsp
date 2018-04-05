<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/tab/style.css"/>" />

<c:set var="exList" value="${resultMap.EX_LIST }"/>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("input[name=date]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#btnBack").click(function(){
			$("#laborInterviewForm").attr("method", "post");
			$("#laborInterviewForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborInterviewList.do'/>?imPernr=<c:out value="${params.imPernr}"/>");
			$("#laborInterviewForm").submit();
		});

		$("#btnSave").click(function(){
			
			if($.trim($("input[name=place]").val()) == ""){
				alert("면담장소은(는) 필수입력항목입니다.");
				return;
			}
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append($("span.exList").html());
			
			$(".parameter").each(function(){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"<c:out value="${params.imEventId}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");
			
			$jq.ajax({
				url : "<c:url value='/portal/moorimmss/laborMng/laborInterviewProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#btnBack").click();
					}else{
						//에러처리
					}
				}
			});
		});
		
		$("input[name=appointType][value=<c:out value="${params.appointType}"/>]").attr("checked", "checked");
		
		$("input[name=appointType]").click(function(){
			
			$.callProgress();
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEventId\" value=\"<c:out value="${params.imEventId}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imPernr\" value=\"<c:out value="${params.imPernr}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imBegda\" value=\"<c:out value="${params.imBegda}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imEndda\" value=\"<c:out value="${params.imEndda}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imSeqnr\" value=\"<c:out value="${params.imSeqnr}"/>\"/>");
			
			<c:forEach items="${userKeySet}" var="key">
			$("#ajaxForm").append("<input type=\"hidden\" name=\"<c:out value="${key}"/>\" value=\"<c:out value="${params[key]}"/>\"/>");
			</c:forEach>

			$("#ajaxForm").append("<input type=\"hidden\" name=\"appointType\" value=\""+$("input[name=appointType]:checked").val()+"\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"tabIndex\" value=\"1\"/>");
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/laborMng/laborInterviewView.do'/>");
			$("#ajaxForm").submit();
		});
		
		$("#photo").click(function(){
			var url = "<c:url value="${resultHeader.EX_HEADER.PHOTO}"/>";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=367px, height=460px, top=200px, left=300px, resizable=no";
			var popup = window.open(url, null, param);

			popup.focus();
		});
		
		//On Click Event
		$("ul.tabs li").click(function() {

			$("ul.tabs li").removeClass("active"); //Remove any "active" class
			$(this).addClass("active"); //Add "active" class to selected tab
			$(".tab_content").hide(); //Hide all tab content

			var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
			$(activeTab).fadeIn(); //Fade in the active ID content
			return false;
		});

		$("ul.tabs li").eq("<c:out value="${params.tabIndex}"/>").click();
		//Tab End
		
		$.initSet = function(){
			<fmt:parseDate var="dateString" value="${exList.ITVDT}" pattern="yyyyMMdd" />
			$("input[name=date]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
			$("input[name=place]").val("<c:out value="${exList.ITVLO}"/>");
		};
		
		$.initSet();
	});
})(jQuery);
</script>
<form id="laborInterviewForm" name="laborInterviewForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>면담일지</h1>
	
	<div class="table_box">
		<ul class="tabs" style="margin-bottom:20px">
			<li><a href="#tab1">기본정보</a></li>
			<li><a href="#tab2">발령정보</a></li>
			<li><a href="#tab3">가족사항</a></li>
			<li><a href="#tab4">학력사항</a></li>
		</ul>
		
		<div class="tab_container">
			<div id="tab1" class="tab_content" style="display:none">
				<div class="table_box">
					<c:set var="exHeader" value="${resultHeader.EX_HEADER }"/>
					
					<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<caption></caption>
							<colgroup>
								<col width="110px"/>
								<col width="10%"/>
								<col width="10%"/>
								<col width="10%"/>
								<col width="15%"/>
								<col width="10%"/>
								<col width="10%"/>
								<col width="10%"/>
								<col width="*"/>
							</colgroup>
							<tbody>
								<tr>
									<td rowspan="6" class="list02_td">
										<img src="<c:url value="${exHeader.PHOTO }"/>" class="cursor-pointer" width="100px" height="125px" border="0" id="photo"/>
									</td>
									<th class="list02_td_bg">사원번호</th>
									<td class="list02_td"><c:out value="${exHeader.PERNR }"/></td>
									<th class="list02_td_bg">직급/직위</th>
									<td class="list02_td"><c:out value="${exHeader.JTEXT }"/></td>
									<th class="list02_td_bg">회사</th>
									<td class="list02_td"><c:out value="${exHeader.NAME1 }"/></td>
									<th class="list02_td_bg">사업장</th>
									<td class="list02_td"><c:out value="${exHeader.LCTTX }"/></td>
								</tr>
								<tr>
									<th class="list02_td_bg">성명</th>
									<td class="list02_td"><c:out value="${exHeader.ENAME }"/></td>
									<th class="list02_td_bg">직책</th>
									<td class="list02_td"><c:out value="${exHeader.COTXT }"/></td>
									<th class="list02_td_bg">소속</th>
									<td colspan="3" class="list02_td"><c:out value="${exHeader.ORGTX }"/></td>
								</tr>
								<tr>
									<th class="list02_td_bg">직무</th>
									<td class="list02_td"><c:out value="${exHeader.STLTX }"/></td>
									<th class="list02_td_bg">포지션</th>
									<td class="list02_td"><c:out value="${exHeader.PLSTX }"/></td>
									<th class="list02_td_bg">사원구분</th>
									<td colspan="3" class="list02_td"><c:out value="${exHeader.PKTXT }"/></td>
								</tr>
								<tr>
									<th class="list02_td_bg">그룹입사일</th>
									<td class="list02_td">
										<fmt:parseDate var="dateString" value="${exHeader.A1DAT}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</td>
									<th class="list02_td_bg">회사입사일</th>
									<td class="list02_td">
										<fmt:parseDate var="dateString" value="${exHeader.ENTRY}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</td>
									<th class="list02_td_bg">생년월일</th>
									<td colspan="3" class="list02_td"><c:out value="${exHeader.BIRTH }"/> <c:out value="${exHeader.AGE }"/></td>
								</tr>
								<tr>
									<th class="list02_td_bg">최종승격(진)일</th>
									<td class="list02_td">
										<fmt:parseDate var="dateString" value="${exHeader.DATUM}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</td>
									<th class="list02_td_bg">근속기간</th>
									<td class="list02_td"><c:out value="${exHeader.CONYR }"/></td>
									<th class="list02_td_bg">재직상태</th>
									<td colspan="3" class="list02_td"><c:out value="${exHeader.STAT1_T }"/></td>
								</tr>
								<tr>
									<th class="list02_td_bg">주소</th>
									<td class="list02_td" colspan="5">
										<c:out value="${resultMap.EX_ADDR }"/>
									</td>
									<th class="list02_td_bg">주민등록번호</th>
									<td class="list02_td"><c:out value="${fn:substring(resultMap.EX_REGNO, 0, 6) }"/>-*******</td>
								</tr>
							</tbody>
						</table>
					</span>
				</div>
			</div>
			
			<div id="tab2" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webmss/personalMng/appointList.jsp"%>
			</div>
			
			<div id="tab3" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webmss/personalMng/familyList.jsp"%>
			</div>
			
			<div id="tab4" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webmss/personalMng/educationList.jsp"%>
			</div>
		</div>
	</div>
	
	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
	</div>
	
	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="15%"/>
				<col width="20%"/>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">면담자</th>
					<th class="list03_td_bg">성명</th>
					<td class="list03_td">
						<c:out value="${exList.ITVEN }"/>
					</td>
					<th class="list03_td_bg">부서</th>
					<td class="list03_td">
						<c:out value="${exList.ORGTX }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">피 면담자</th>
					<th class="list03_td_bg">성명</th>
					<td class="list03_td">
						<c:out value="${exList.ENAME_P }"/>
					</td>
					<th class="list03_td_bg">부서</th>
					<td class="list03_td">
						<c:out value="${exList.ORGTX_P }"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">일시 및 장소</th>
					<th class="list03_td_bg">일시</th>
					<td class="list03_td">
						<input name="date" class="input datepicker parameter" type="text" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
					<th class="list03_td_bg">장소</th>
					<td class="list03_td">
						<input type="text" name="place" value="" class="input w90per parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg" rowspan="3">면담내용</th>
					<th class="list03_td_bg">개인적인 부분</th>
					<td class="list03_td" colspan="4">
						<textarea name="private" class="input w95per h100px parameter"><c:out value="${exList.OBJ01 }"/></textarea>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">업무적인 부분</th>
					<td class="list03_td" colspan="4">
						<textarea name="business" class="input w95per h100px parameter"><c:out value="${exList.OBJ02 }"/></textarea>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">기타사항 및 건의사항</th>
					<td class="list03_td" colspan="4">
						<textarea name="suggestion" class="input w95per h100px parameter"><c:out value="${exList.OBJ03 }"/></textarea>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg" colspan="2">면담자 종합의견</th>
					<td class="list03_td" colspan="4">
						<textarea name="interviewOpinion" class="input w95per h100px parameter"><c:out value="${exList.OBJ04 }"/></textarea>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg" colspan="2">회사 조치사항</th>
					<td class="list03_td" colspan="4">
						<textarea name="companyMeasure" class="input w95per h100px parameter"><c:out value="${exList.OBJ05 }"/></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<span class="exList">
		<c:forEach items="${keySet}" var="key">
			<input type="hidden" name="exList_<c:out value="${key}"/>" value="<c:out value="${exList[key]}"/>"/>
		</c:forEach>
	</span>
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
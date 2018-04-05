<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="<c:url value="/base/js/jquery/treeview/jquery.treeview.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/treeview/jquery.treeview.css"/>" />

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#tree").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "location"
		});

		$("a[name=linked]").click(function(){

			var param = $(this).data("param");

			var arry = param.split("?");

			$("#ajaxForm").html("");

			if(arry.length == 2){
				var arry2 = arry[1].split("&");
				for(var i = 0 ; i < arry2.length ; i++){
					if(arry2[i].split("=")[0] == "pa_otype"){
						$("#ajaxForm").append("<input type=\"hidden\" name=\"imOtype\" value=\""+arry2[i].split("=")[1]+"\"/>");
					}else if(arry2[i].split("=")[0] == "pa_objid"){
						$("#ajaxForm").append("<input type=\"hidden\" name=\"imObjid\" value=\""+arry2[i].split("=")[1]+"\"/>");
					}
				}
			}else{
			}

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/manPowerMng/retrieveJobProfileInfo.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						$(".EX_GROUP").html(result.EX_GROUP);
						$(".EX_STELL").html(result.EX_STELL);
						$(".EX_DEFINE").val(result.EX_DEFINE);
						$(".EX_BUSINESS").val(result.EX_BUSINESS);
						$(".EX_CAPACITY").val(result.EX_CAPACITY);
						$(".EX_MOVE").val(result.EX_MOVE);
						$(".EX_STEXT").val(result.EX_STEXT);
						$(".EX_SKILL").val(result.EX_SKILL);
						$(".EX_UNDERGO").val(result.EX_UNDERGO);
						$(".EX_CERTI").val(result.EX_CERTI);
					}else{

					}
				}
			});
		});
		
		$.changeWidth();
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
	width:880px;
}

textarea{
	padding:5px;
}
</style>

<form id="jobProfileForm" name="jobProfileForm" method="post" action="<c:url value='/portal/moorimess/manPowerMng/jobProfileView.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>직무프로파일 조회</h1>

	<c:set var="etTree" value="${resultMap.ET_TREE }"/>

	<div id="sidetree" class="leftFrame">
		<div class="treeheader"><b>직무 체계도</b></div>
		<div id="sidetreecontrol"></div>
		<ul class="treeview" id="tree">

			<c:forEach var="result" items="${etTree }" varStatus="inx">

				<c:choose>
					<c:when test="${inx.last }">
						<c:remove var="nextResult"/>
					</c:when>
					<c:otherwise>
						<c:set var="nextResult" value="${etTree[inx.index+1] }"/>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${result.TLEVEL eq '1' }">
						<c:if test="${lastFlag eq 'Y' }">
								</ul>
						</li>
						</c:if>

						<li class="expandable">
							<div class="hitarea expandable-hitarea"></div>
							<a href="#"><c:out value="${result.TEXT }"/></a>
							<ul style="display: none;">
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${not empty nextResult and result.PARENTID eq nextResult.PARENTID}">
								<li><a href="#" name="linked" data-param="<c:out value="${result.LINK }"/>"><c:out value="${result.TEXT }"/></a></li>
								<c:set var="lastFlag" value="N"/>
							</c:when>
							<c:otherwise>
								<li class="last"><a href="#" name="linked" data-param="<c:out value="${result.LINK }"/>"><c:out value="${result.TEXT }"/></a></li>
								<c:set var="lastFlag" value="Y"/>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>

	<div class="rigthFrame">
		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">직무프로파일 조회</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="20%"/>
						<col width="30%"/>
						<col width="20%"/>
						<col width="30%"/>
					</colgroup>
					<tbody>
					<tr>
						<th class="list02_td_bg">직군명</th>
						<td class="list02_td f_left"><span class="EX_GROUP"></span></td>
						<th class="list02_td_bg">직무명</th>
						<td class="list02_td f_left"><span class="EX_STELL"></span></td>
					</tr>
				</table>
			</span>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">정의</p>
			<textarea class="w95per EX_DEFINE" rows="6" readonly></textarea>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">주요업무</p>
			<textarea class="w95per EX_BUSINESS" rows="6" readonly></textarea>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">요구 역량</p>
			<textarea class="w95per EX_CAPACITY" rows="6" readonly></textarea>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">직무수행 요건</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="15%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list03_td_bg">필요직무경험</th>
							<td class="list03_td">
								<textarea rows="6" class="w95per EX_UNDERGO" readonly></textarea>
							</td>
						</tr>
						<tr>
							<th class="list03_td_bg">필요 지식/스킬</th>
							<td class="list03_td">
								<textarea rows="6" class="w95per EX_SKILL" readonly></textarea>
							</td>
						</tr>
						<tr>
							<th class="list03_td_bg">관련자격증</th>
							<td class="list03_td">
								<textarea rows="6" class="w95per EX_CERTI" readonly></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">이동가능 직무</p>
			<textarea class="w95per EX_MOVE" rows="6" readonly></textarea>
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
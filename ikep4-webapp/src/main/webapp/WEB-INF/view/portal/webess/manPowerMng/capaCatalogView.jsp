<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="<c:url value="/base/js/jquery/treeview/jquery.treeview.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/treeview/jquery.treeview.css"/>" />

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		var tempParam = "";
		
		$("#tree").treeview({
			collapsed: true,
			animated: "medium",
			control:"#sidetreecontrol",
			persist: "location"
		});

		$("a[name=linked]").click(function(){

			var param = $(this).data("param");
			tempParam = param;
			
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
				url : "<c:url value='/portal/moorimess/manPowerMng/retrieveCapaCatalogInfo.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						$(".EX_QGROUPTXT").html(result.EX_QGROUPTXT);
						$(".EX_STEXT").html(result.EX_STEXT);
						$(".EX_DESCR").val(result.EX_DESCR);

						var etList = result.ET_LIST;

						var tab = $("#categoryTab").find("tbody");

						tab.find("tr").remove();

						var sb = "";
						sb += "<tr>";
						sb += "	<td><span class=\"RATING_TEXT\"></span></td>";
						sb += "	<td class=\"f_left\"><span class=\"KEY_WORD\"></span></td>";
						sb += "	<td class=\"f_left\"><span class=\"TLINE\"></span></td>";
						sb += "</tr>";

						for(var i = 0 ; i < etList.length ; i++){
							tab.append(sb);

							$("span.RATING_TEXT:last").html(etList[i].RATING_TEXT);
							$("span.KEY_WORD:last").html(etList[i].KEY_WORD);
							$("span.TLINE:last").html(etList[i].TLINE);
						}

					}else{

					}
				}
			});
		});
		$.changeWidth();
		
		
		$("#btnPrint").click(function(){
			
			var target = "printPopup";
			
			var arry = tempParam.split("?");
			
			

			
			
			$("#printPopForm").html("");
			
			if(arry.length == 2){
				var arry2 = arry[1].split("&");
				for(var i = 0 ; i < arry2.length ; i++){
					if(arry2[i].split("=")[0] == "pa_otype"){
						$("#printPopForm").append("<input type=\"hidden\" name=\"imOtype\" value=\""+arry2[i].split("=")[1]+"\"/>");
					}else if(arry2[i].split("=")[0] == "pa_objid"){
						$("#printPopForm").append("<input type=\"hidden\" name=\"imObjid\" value=\""+arry2[i].split("=")[1]+"\"/>");
					}
				}
			}else{
			}
			
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=750px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);
			
			$("#printPopForm").attr("action", "<c:url value='/portal/moorimess/manPowerMng/capaCatalogViewPopup.do'/>");
			$("#printPopForm").attr("target", target);
			$("#printPopForm").submit();
			
			popup.focus();
		});
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

<form id="capaCatalogForm" name="capaCatalogForm" method="post" action="<c:url value='/portal/moorimess/manPowerMng/capaCatalogView.do'/>" >
<div class="button_box" style="float: right; margin-right: 100px;">
			<a href="#" class="button_img01" id="btnPrint"><span>인쇄</span></a>
		</div>
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>역량카탈로그 조회</h1>
	
	<c:set var="etTree" value="${resultMap.ET_TREE }"/>

	<div id="sidetree" class="leftFrame">
		<div class="treeheader"><b>역량 체계도</b></div>
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
			<p class="f_title" style="padding-bottom:10px;">역량정보</p>

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
							<th class="list02_td_bg">역량그룹</th>
							<td class="list02_td f_left"><span class="EX_QGROUPTXT"></span></td>
							<th class="list02_td_bg">역량명</th>
							<td class="list02_td f_left"><span class="EX_STEXT"></span></td>
						</tr>
						<tr>
							<th class="list02_td_bg">역량내용</th>
							<td class="list02_td f_left" colspan="3">
								<textarea class="w95per EX_DESCR" rows="6" readonly></textarea>
							</td>
						</tr>
				</table>
			</span>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">역량사전(Category)</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" id="categoryTab">
					<caption></caption>
					<colgroup>
						<col width="20%"/>
						<col width="35%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th>LEVEL</th>
							<th>Key Word</th>
							<th>행동지표</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="3">데이터가 없습니다.</td>
						</tr>
					</tbody>
				</table>
			</span>
		</div>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="printPopForm" name="printPopForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
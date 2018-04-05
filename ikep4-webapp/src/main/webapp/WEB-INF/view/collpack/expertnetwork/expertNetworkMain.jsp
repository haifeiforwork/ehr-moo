<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="commonPrefix">ui.collpack.expertnetwork.common</c:set>
<c:set var="confirmPrefix">ui.collpack.expertnetwork.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.expertnetwork.common.button</c:set>
<c:set var="expertNetworkMessagePrefix">ui.collpack.expertnetwork.expertNetworkMain</c:set>

<script type="text/javascript" src="<c:url value="/base/js/jquery/mopSlider-2.4.customize.js"/>"></script>

<%@ include file="/WEB-INF/view/collpack/expertnetwork/findExpert.jsp"%>

<!--expert_topList Start-->
<div id="workContents">
<div id="tagResult">

<!--expert_topList Start-->
<div class="expert_topList">
	<div id="slider" style="display:none">
		<c:forEach var="item" items="${popularList}">
		<div class="expert_topPhoto">
		<c:if test="${0 lt item.expertCount}">
			<div class="expert_best"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.userId}');"><img src="<c:url value='/base/images/common/img_best.png'/>" alt="best"/></a></div>
		</c:if>
			<span><a href="#a" onclick="iKEP.goProfilePopupMain('${item.userId}');"><img src="<c:url value='${item.profilePicturePath}'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" /></a></span>
			<div class="expert_topPhoto_info">
				<div class="expert_topPhoto_team ellipsis">${item.teamName}</div>
				<div class="expert_topPhoto_name ellipsis">${item.sortOrder}.&nbsp;${item.userName}&nbsp;${item.jobTitleName}</div>
			</div>
		</div>
		</c:forEach>
	</div>
	<div class="clear"></div>
</div>
<!--//expert_topList End-->

<!--expert_bl Start-->
<div class="expert_br">
	<form id="interestForm" action="">
		<spring:bind path="interestPageCondition.userId">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="interestPageCondition.totalCount">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="interestPageCondition.countOfPage">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="interestPageCondition.requestStartOrder">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
	</form>

	<h2><img src="<c:url value='/base/images/icon/ic_per_02.gif'/>" alt="" />&nbsp;<ikep4j:message pre="${expertNetworkMessagePrefix}" key="interest.title"/></h2>
	<div>
		<ul id="interest">
			<%@ include file="/WEB-INF/view/collpack/expertnetwork/interestPage.jsp"%>
		</ul>
	</div>
	<div class="blockButton_3" id="interestMoreDataBtn">
		<a class="button_3" href="#a" onclick="interestAddHandler();" id="btnInterest"><span><ikep4j:message pre="${expertNetworkMessagePrefix}" key="moreData"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>
	</div>
</div>
<!--//expert_bl End-->

<!--expert_br Start-->
<div class="expert_bl">
	<form id="fellowForm" action="">
		<spring:bind path="fellowPageCondition.userId">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="fellowPageCondition.totalCount">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="fellowPageCondition.countOfPage">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="fellowPageCondition.requestStartOrder">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
	</form>
	<h2><img src="<c:url value='/base/images/icon/ic_per_01.gif'/>" alt="" />&nbsp;<ikep4j:message pre="${expertNetworkMessagePrefix}" key="fellow.title"/></h2>
	<div>
		<ul id="fellow">
			<%@ include file="/WEB-INF/view/collpack/expertnetwork/fellowPage.jsp"%>
		</ul>
	</div>
	<div class="blockButton_3" id="fellowMoreDataBtn">
		<a class="button_3" href="#a" onclick="fellowAddHandler();" id="btnFellow"><span><ikep4j:message pre="${expertNetworkMessagePrefix}" key="moreData"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>
	</div>
</div>
<!--//expert_br End-->

</div>
</div>

<script type="text/javascript">
//<![CDATA[
$jq(document).ready(function() {

	if (0 < ${fn:length(popularList)}) {
		new iKEP.MopSlider("#slider", {height:190});
	} else {
		$jq("#slider").hide();
	}

	interestSetting();

	fellowSetting();

});
//]]>
</script>

<script type="text/javascript">
//<![CDATA[

function interestSetting(flag) {
	var totalCount = $jq("#interestForm input[name=totalCount]").val();
	var requestStartOrder = $jq("#interestForm input[name=requestStartOrder]").val();
	var countOfPage = $jq("#interestForm input[name=countOfPage]").val();
	var startOrder = parseInt(requestStartOrder) + parseInt(countOfPage);

	if ("calc" == flag) {
		$jq("#interestForm input[name=requestStartOrder]").val(startOrder);
	}

	if (parseInt(totalCount) < parseInt(startOrder)) {
		$jq("#interestMoreDataBtn").hide();
	}
}

function fellowSetting(flag) {
	var totalCount = $jq("#fellowForm input[name=totalCount]").val();
	var requestStartOrder = $jq("#fellowForm input[name=requestStartOrder]").val();
	var countOfPage = $jq("#fellowForm input[name=countOfPage]").val();
	var startOrder = parseInt(requestStartOrder) + parseInt(countOfPage);

	if ("calc" == flag) {
		$jq("#fellowForm input[name=requestStartOrder]").val(startOrder);
	}

	if (parseInt(totalCount) < parseInt(startOrder)) {
		$jq("#fellowMoreDataBtn").hide();
	}
}

var interestAddHandler = function() {
	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/interestPage.do",
		type : "get",
		data : $jq("#interestForm").serialize(),
		loadingElement : {button : "#btnInterest", container : "#interest"},
		success : function(data, textStatus, jqXHR) {
			$jq("#interest").append(data);
			interestSetting("calc");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Interest Page Load Fail!!!");
		}
	});
};

var fellowAddHandler = function() {
	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/expertnetwork/fellowPage.do",
		type : "get",
		data : $jq("#fellowForm").serialize(),
		loadingElement : {button : "#btnFellow", container : "#fellow"},
		success : function(data, textStatus, jqXHR) {
			$jq("#fellow").append(data);
			fellowSetting("calc");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Fellow Page Load Fail!!!");
		}
	});
};
//]]>
</script>


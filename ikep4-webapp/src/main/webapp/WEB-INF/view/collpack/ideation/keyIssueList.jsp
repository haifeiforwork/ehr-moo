<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.keyIssueList" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">

	$jq(document).ready(function(){
		
		var tagId = "${tagId}";
		
		if(tagId){
			iKEP.tagging.tagSearch(tagId, '<%=TagConstants.ITEM_TYPE_IDEATION%>');
			
			tagNameChange('${tagList[0].tagName}');
		}

		<c:if test="${empty(param.startDateToString)}">
			comDate('-1', true);	
		</c:if>	
		
		$jq("#date1").datepicker({	inline: true
			,onSelect: function(date) {
				var d = date.split(".");
				$jq("#startDateToString").val(d[0]+"."+d[1]+"."+d[2]);
				$jq("#date2").datepicker("option", "minDate", $jq(this).val());
				var d = $jq(this).val().split(".");
				$jq("#endDateToString").val(d[0]+"."+d[1]+"."+d[2]);
			} 
		}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); }); 
		
		$jq("#date2").datepicker({inline: true
				,minDate:$jq("#date1").val()
				,onSelect: function(date) {
					var d = date.split(".");
					$jq("#endDateToString").val(d[0]+"."+d[1]+"."+d[2]);
				} }).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); }); 
		
	});

	
	function tagNameChange(tag){
		$jq('#tagTitle').text("["+tag+"]");
	}
	
	function tagClk(tagId, tagName){
		
		iKEP.tagging.tagSearch(tagId, '<%=TagConstants.ITEM_TYPE_IDEATION%>');
		
		tagNameChange(tagName);
		
		return false;
	}
	
	function searchSubmit(){
		
		try{
		var sDateToString = $jq("#startDateToString").val();
		var sd = sDateToString.split(".");
		
		var sDate = new Date();
		sDate.setFullYear(sd[0]);
		sDate.setMonth(sd[1]);
		sDate.setDate(sd[2]);
		
		var eDateToString = $jq("#endDateToString").val();
		var ed = eDateToString.split(".");
		
		var eDate = new Date();
		eDate.setFullYear(ed[0]);
		eDate.setMonth(ed[1]);
		eDate.setDate(ed[2]);
		
		
		if(sDate - eDate > 0){
			alert("<ikep4j:message key='message.collpack.ideation.date.compare'/>");
			return false;
		}
		
		} catch(e){
			alert("<ikep4j:message key='message.collpack.ideation.date.format.check'/>");
			return false;
		}
		
		$jq("#searchForm").submit();
	}
	
	function comDate(month){
		var nowDate = new Date();
		$jq("#date2").val(nowDate.getFullYear()+"."+(nowDate.getMonth()+1)+"."+nowDate.getDate());
		$jq("#endDateToString").val(nowDate.getFullYear()+"."+(nowDate.getMonth()+1)+"."+nowDate.getDate());
				
		var cDate = new Date();
		cDate.setMonth(nowDate.getMonth()+Number(month));
		
		$jq("#date1").val(cDate.getFullYear()+"."+(cDate.getMonth()+1)+"."+cDate.getDate());
		$jq("#startDateToString").val(cDate.getFullYear()+"."+(cDate.getMonth()+1)+"."+cDate.getDate());
		
	}

</script>



<h1 class="none">Contents Area</h1>
				
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3>
	<c:choose>
		<c:when test="${param.isBest == 1 }">
			<ikep4j:message pre='${preUi}' key='titleBest'/>
		</c:when>
		<c:otherwise>
			<ikep4j:message pre='${preUi}' key='titlePopular'/>
		</c:otherwise>
	</c:choose>
	
	</h3>
</div>
<!--//subTitle_2 End-->

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<form id="searchForm" action="keyIssueList.do">
		<input type="hidden" name="isBest" value="${param.isBest }"/>
		<input type="hidden" id="startDateToString" name="startDateToString" value="${param.startDateToString }"/>
		<input type="hidden" id="endDateToString" name="endDateToString" value="${param.endDateToString }"/>
		<table summary="table">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="4%"><ikep4j:message pre='${preUi}' key='searchDate'/></th>
					<td scope="row">
						<a class="button_s" href="#a" onclick="comDate('-12'); return false;" title="<ikep4j:message pre='${preUi}' key='last1year'/>"><span><ikep4j:message pre='${preUi}' key='last1year'/></span></a>
						<a class="button_s" href="#a" onclick="comDate('-6'); return false;" title="<ikep4j:message pre='${preUi}' key='last6month'/>"><span><ikep4j:message pre='${preUi}' key='last6month'/></span></a>
						<a class="button_s" href="#a" onclick="comDate('-3'); return false;" title="<ikep4j:message pre='${preUi}' key='last3month'/>"><span><ikep4j:message pre='${preUi}' key='last3month'/></span></a>
						<a class="button_s" href="#a" onclick="comDate('-1'); return false;" title="<ikep4j:message pre='${preUi}' key='last1month'/>"><span><ikep4j:message pre='${preUi}' key='last1month'/></span></a>&nbsp;
						<div class="subInfo">
							<input type="text" class="inputbox" id="date1" name="sDate" title="<ikep4j:message pre='${preUi}' key='startDate'/>" value="${param.sDate }" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preUi}' key='calendar'/>" /> ~
							<input type="text" class="inputbox" id="date2" name="eDate" 	title="<ikep4j:message pre='${preUi}' key='endDate'/>" value="${param.eDate }" size="8" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preUi}' key='calendar'/>" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="searchBtn"><a href="#a" onclick="searchSubmit();return false;" title="<ikep4j:message pre='${preUi}' key='searchBtn'/>"><span><ikep4j:message pre='${preUi}' key='searchBtn'/></span></a></div>
		</form>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

<!--lntel_tag_con Start-->
<div class="lntel_tag_con">
	<div class="lntel_tag">
		<div class="lntel_tagBox02" style="min-height:10px">
			<div class="tag_cloud lntel_tag_cloud textLeft" style="min-height:10px">
				<c:forEach var="tag" items="${tagList}" varStatus="loop">
					<span><a href="#a" class="tag${tag.displayStep }" onclick="tagClk('${tag.tagId}', '${tag.tagName}');" title="${tag.tagName}">${tag.tagName}</a></span>
				</c:forEach>
				<c:if test="${fn:length(tagList)==0 }">
					<span><ikep4j:message pre='${preUi}' key='tagResult.nodata'/></span>
				</c:if>
			</div>				
		</div>
	</div>	
</div>
<!--//lntel_tag_con End-->

<c:if test="${fn:length(tagList)>0 }">
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><span id="tagTitle"></span> <ikep4j:message pre='${preUi}' key='tagResult'/></h3>
</div>
<!--//subTitle_2 End-->
</c:if>
<!--blockListTable Start-->
<div class="blockListTable summaryView Ideation_list" id="tagResult"></div>
<!--//blockListTable End-->














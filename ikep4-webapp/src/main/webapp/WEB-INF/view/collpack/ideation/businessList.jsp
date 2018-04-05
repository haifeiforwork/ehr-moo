<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.ideation.businessList" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">

	$jq(document).ready(function(){

		<c:if test="${empty(param.startDateToString)}">
			comDate('-1', true);	
		</c:if>
		
		$jq("#date1").datepicker({	inline: true
									,onSelect: function(date) {
										
										$jq("#date2").datepicker("option", "minDate", $jq(this).val());

										//var d = date.split(".");
										//$jq("#startDateToString").val(d[0]+"."+d[1]+"."+d[2]);
										//var d = $jq(this).val().split(".");
										//$jq("#endDateToString").val(d[0]+"."+d[1]+"."+d[2]);
										
										
									} 
								}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); }); 
		$jq("#date2").datepicker({inline: true
								,minDate:$jq("#date1").val()
								,onSelect: function(date) {
									//var d = date.split(".");
									//$jq("#endDateToString").val(d[0]+"."+d[1]+"."+d[2]);
								} 
							}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); }); 
		
		var f = $jq('#searchForm');
		
		//pagePer 초기화
		var pagePer = "${param.pagePer}";
		
		f.find('select[name=pagePer] option').each(function (){
			if(this.value == pagePer){
				this.selected = true;
			}
		});
		
		//searchColumn 초기화
		var searchColumn = "${param.searchColumn}";
		
		f.find('select[name=searchColumn] option').each(function (){
			if(this.value == searchColumn){
				this.selected = true;
			}
		});

		f.find("input[name=searchWord]").keyup(function(e){
			if(e.keyCode == '13'){
				goSearch();
			}
		});

	});

	
	function searchSubmit(){
		
		$jq("#startDateToString").val($jq("#date1").val());
		
		$jq("#endDateToString").val($jq("#date2").val());

		$jq("#mainContents").ajaxLoadStart(); 
		
		$jq("#searchForm").submit();
	}
	
	function comDate(month, init){
		
		var nowDate = new Date();
		
		var nowFormet = nowDate.getFullYear()+"."+(nowDate.getMonth()+1)+"."+nowDate.getDate();
		$jq("#date2").val(nowFormet);
		//$jq("#endDateToString").val(nowDate.getFullYear()+"."+(nowDate.getMonth()+1)+"."+nowDate.getDate()); //다국어처리 할때 바꿀필요 없음.
		
		var cDate = new Date();
		cDate.setMonth(nowDate.getMonth()+Number(month));
		
		var cFormet =cDate.getFullYear()+"."+(cDate.getMonth()+1)+"."+cDate.getDate();
		$jq("#date1").val(cFormet);
		//$jq("#startDateToString").val(cDate.getFullYear()+"."+(cDate.getMonth()+1)+"."+cDate.getDate()); //다국어처리 할때 바꿀필요 없음.
		
		if(init == true){  //초기에만
			$jq("#sdView").text(cFormet);
			$jq("#edView").text(nowFormet);
		}
		
	}
	
	
	function goSearch(){
		
		var f = $jq('#searchForm');
		
		var searchColumn = f.find('select[name=searchColumn]').val();
		var searchWord = f.find('input[name=searchWord]').val();
		
		/*
		if(!searchWord){
			alert('검색어를 넣어주세요');
			return;
		}
		*/

		$jq("#tagResult").ajaxLoadStart(); 
	
		if(searchColumn == "tag"){
			
			iKEP.tagging.tagSearchByName(searchWord, '<%=TagConstants.ITEM_TYPE_IDEATION%>');
		} else {
			
			f.submit();
			
		}
		
	}
</script>



<h1 class="none">Contents Area</h1>

<div id="tagResult">
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_2.png"/>" alt="top" /> <ikep4j:message pre='${preUi}' key='title'/></h3>
</div>
<!--//subTitle_2 End-->
<form id="searchForm" name="searchForm" action="businessList.do" method="get" >
	<input type="hidden" id="startDateToString" name="startDateToString" value="${param.startDateToString }"/>
	<input type="hidden" id="endDateToString" name="endDateToString" value="${param.endDateToString }"/>
	<input type="hidden" name="pageIndex" value="1"/>
<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">

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
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

		
<!--ideation_process Start-->
<div class="ideation_process">
	<div class="corner_RoundBox07">
		<div class="subTitle_2">
			<h3><ikep4j:message pre='${preUi}' key='ideaActi'/> (<span id="sdView">${param.sDate}</span> ~ <span id="edView">${param.eDate}</span>)</h3>
		</div>
		<div class="process_img">
			<div class="process_01" style="width:25%"><ikep4j:message pre='${preUi}' key='sugIdea'/></div>
			<div class="process_02" style="width:25%"><span class="ar_1"></span><ikep4j:message pre='${preUi}' key='recomIdea'/></div>
			<div class="process_04" style="width:25%"><ikep4j:message pre='${preUi}' key='bestIdea'/></div>
 			<!-- <div class="process_04"><ikep4j:message pre='${preUi}' key='adoptIdea'/></div>  -->
			<div class="process_05" style="width:25%"><span class="ar_2"></span><ikep4j:message pre='${preUi}' key='busiIdes'/></div>
		</div>
		<div class="clear"></div>
		<div class="process_num">
			<div class="process_num_01" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='sugIdea'/></div>
				<div class="num">
					<span>${totalCountes.count }</span><span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
				</div>
			</div>
			<div class="process_num_02" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='recomIdea'/></div>
				<div class="num">
					<span>${totalCountes.recommendItem }</span><span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
					<span class="per">
					<c:if test="${totalCountes.recommendItem != 0}">
						<fmt:formatNumber value="${totalCountes.recommendItem*100/totalCountes.count }" pattern="0" />%
					</c:if>
					</span>
				</div>
			</div>
			<div class="process_num_03" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='bestIdea'/></div>
				<div class="num">
					<span>${totalCountes.bestCount }</span><span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
					<span class="per">
					<c:if test="${totalCountes.bestCount != 0}">
						<fmt:formatNumber value="${totalCountes.bestCount*100/totalCountes.count }" pattern="0" />%
					</c:if>
					</span>
				</div>
			</div>
			<!-- 			
			<div class="process_num_04">
				<div class="none"><ikep4j:message pre='${preUi}' key='adoptIdea'/></div>
				<div class="num">
					<span>${totalCountes.adoptItem }</span><span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
					<span class="per">
					<c:if test="${totalCountes.adoptItem != 0}">
						<fmt:formatNumber value="${totalCountes.adoptItem*100/totalCountes.count }" pattern="0" />%
					</c:if>
					</span>
				</div>
			</div>
			-->
			<div class="process_num_05" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='busiIdes'/></div>
				<div class="num">
					<span>${businessCountes.count }</span><span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
					<span class="per">
					<c:if test="${businessCountes.count != 0}">
						<fmt:formatNumber value="${businessCountes.count*100/totalCountes.count }" pattern="0" />%
					</c:if>
					</span>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>
<!--//ideation_process End-->

<!--subTitle_2 Start-->
<div class="subTitle_2">
	<h3><img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_2.png"/>" alt="top" /> <ikep4j:message pre='${preUi}' key='adoptingIdea'/></h3>
</div>
<!--//subTitle_2 End-->	

<!--tableTop Start-->
<div class="tableTop">
	<div class="listInfo">
		<select name="pagePer" title="<ikep4j:message pre='${preUi}' key='pageCount'/>" onchange="goSearch();">
			<option>10</option>
			<option>15</option>
			<option>20</option>
			<option>30</option>
			<option>40</option>
			<option>50</option>
		</select>
		<div class="totalNum">${pageCondition.pageIndex }/${pageCondition.pageCount}<ikep4j:message pre='${preUi}' key='page'/> (<ikep4j:message pre='${preUi}' key='total'/><strong>${totalCount}</strong>)</div>
	</div>																	
	<div class="tableSearch">
		<select title="<ikep4j:message pre='${preUi}' key='class'/>" name="searchColumn" >
			<option value="title"><ikep4j:message pre='${preUi}' key='classTitle'/></option>
			<option value="registerName"><ikep4j:message pre='${preUi}' key='classUser'/></option>
			<option value="tag"><ikep4j:message pre='${preUi}' key='classTag'/></option>
		</select>													
		<input type="text" class="inputbox" title="<ikep4j:message pre='${preUi}' key='searchInput'/>" id="searchWord" name="searchWord" value="${param.searchWord}" size="20" />
		<a href="#a" class="ic_search" onclick="goSearch();return false;" title="<ikep4j:message pre='${preUi}' key='search'/>"><span><ikep4j:message pre='${preUi}' key='search'/></span></a>
	</div>	
	<div class="clear"></div>
</div>
<!--//tableTop End-->

</form>

<!--blockListTable Start-->
<div class="blockListTable summaryView">
	<table summary="table">
		<caption></caption>						
		<tbody id="itemFrame">
			<c:import url="/WEB-INF/view/collpack/ideation/ideaListMore.jsp"/>
		</tbody>
	</table>
	
	<c:if test="${totalCount > 0 }">																																		
	<!--Page Numbur Start--> 
	<spring:bind path="pageCondition.pageIndex">
	<ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${pageCondition}" />
	</spring:bind> 
	<!--//Page Numbur End-->
	</c:if>					
</div>
<!--//blockListTable End-->
</div>






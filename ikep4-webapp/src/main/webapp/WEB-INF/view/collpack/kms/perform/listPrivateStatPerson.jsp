<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="prePrivate"    		value="message.collpack.kms.perform.private" />
<c:set var="preSearch"  		value="ui.ikep4.common.searchCondition" />


 
<%-- 메시지 관련 Prefix 선언 End --%>  

<c:set var="user" value="${sessionScope['ikep.user']}" /> 
 
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pieRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
<script type="text/javascript">
<!--   
var bbsIframe;  	 // 부모 iframe
var bbsIsIframe = 0; // iframe 존재 여부
var isLayout; // 레이아웃 보기 여부
var bbsLayout = null;
var layoutType = "n"; // n:없음, v:가로보기, h:세로보기
var excelBtnCnt = 0;

(function($){	
	
	
	/* window risize 시 Contaner 높이 조절 */
	resizeContanerHeight = function(){
		var docHeight = 0;
		var adjustHeight = 20;		
		var $lefMenu;
		var $Container	= $('#container');
		
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top);
				}
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-17);				
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}	
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		} 
	}
	
	/* Contaner & iframe 높이 조절 */
	setContanerHeight = function() {
		var docHeight = 0;
		var adjustHeight = 20;
		var $lefMenu;
		var $Container	= $('#container');
		
		// layout 존재하므로 layout destroy 함수 호출시
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top)
					.css({overflowY:"auto",overflowX:"hidden"});
				}
				
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-19);
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}			
			
		}else{ // layout 없으므로 layout 함수 호출시
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				$lefMenu = $("#leftMenu", parent.document);
				$lefMenu.css({overflowY:"",overflowX:""});
				bbsIframe.height($(document).height());
			}
		
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		}
		
	}
	
	
	$(document).ready(function() {   
			    
		if(${!searchResult.emptyRecord}){
			var s1 = new Array();
		    var ticks = new Array();
		   
		    var idx = 0;
		    if('${arrRegSum}' != null){
			    <c:forEach var='regCnt' items='${arrRegSum}' begin='0' step='1'>
		    		s1[idx] = ${regCnt};
		    		idx++;
		       	</c:forEach>
		    	
		       	idx = 0;
		   		 <c:forEach var='date'  items='${arrDate}' begin='0' step='1'>
		   			ticks[idx] = '${date}';
		   			idx++;
		       	</c:forEach>	
		    }
		  
		     
		    var plot1 = $.jqplot('chart1', [s1], {
		        seriesDefaults:{
		            renderer:$.jqplot.BarRenderer,  // 차트 형태
		            pointLabels: { show: true }     // 포인트 표시 여부
		        },
		        axes: {
		            xaxis: {
		                renderer: $.jqplot.CategoryAxisRenderer,  // x축 표시
		                ticks: ticks                // x축 범주 표시
		            },
		        	yaxis: {
		        		min : 0
		        	}
		        },
		        highlighter: { show: false }  // highlight 기능 비활성화
		    }); 
		}
	    
		/*
		$("#searchForm select[name=workPlaceName]").change(function() { 
			var workPlaceName = $("#searchForm select[name=workPlaceName]").val();
			
			if(workPlaceName == ""){
				$("#teamCode").empty();
				var teamCode = "<option value=''>" + "<ikep4j:message key="message.collpack.kms.admin.permission.team.leader.info.add.teamCode" />" +"</option>";
				$("#teamCode").append(teamCode);
			}else{
				$("#searchForm").submit(); 
				return false;
			}
		}); 
		
		
		$("#searchForm select[name=teamCode]").change(function() { 
			$("#searchForm").submit(); 
			return false;
		});
		*/
		
		
		$("#searchForm select[name=searchYear]").change(function() { 
			
			$("#searchForm").submit(); 
			return false;
		}); 
		
		$("#searchForm select[name=searchMonth]").change(function() { 
			$("#searchForm").submit(); 
			return false;
		}); 
				
		
		sort = function(sortColumn, sortType) { 
			$("#searchForm input[name=actionType]").val("sort");
			$("#searchForm input[name=sortColumn]").val(sortColumn); 
			$("#searchForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchButton").click();
		}; 
			
		
		
		$("#searchButton").click(function() { 
			$("#searchForm input[name=actionType]").val("search");			
			$("#searchForm").submit();
			
			return false; 
		});
		
		
		
		$("#excelButton").click(function() { 
			
			if(${searchResult.emptyRecord}){
				alert("<ikep4j:message key='message.collpack.kms.perform.excel.nodata'/>");
				return false;
			}			
			if(excelBtnCnt == 0){
				$jq("#searchForm").attr("action","<c:url value='/collpack/kms/perform/downloadExcelPrivateStatPerson.do'/>");
				$jq("#searchForm").submit();
				$jq("#searchForm").attr("action","<c:url value='/collpack/kms/perform/listPrivateStat.do'/>");
				excelBtnCnt++;
			}else{
				alert("조회 후 엑셀다운 받으세요");
			}
			return false; 
		});
			
		
			
		$("#searchForm select[name=pagePerRecord]").change(function() {
			$("#searchForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchForm input[name=searchWord]").val("");
			$("#searchForm").submit(); 
			return false;
		});  
		
		/* iframe 구성여부 확인 */
		bbsIframe = $(parent.document).find("iframe[name=contentIframe]");
		bbsIsIframe = bbsIframe.length;
		
		/* 기본 layout 설정 여부 */ 
		isLayout = false;	
		layoutType = "n";		
		
		/* 윈도우 resize 이벤트 */
		$(window).bind("resize", resizeContanerHeight);		
		
				
	});
	
	
})(jQuery);
//-->
</script>
<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchForm" method="post" action="<c:url value='/collpack/kms/perform/listPrivateStat.do' />">
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.actionType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--mainContents Start-->	 
<!--pageTitle Start-->
                <!--tableTop Start-->
				<div class="tableTop">
					<h2><ikep4j:message pre="${prePrivate}" key="stat.header.title" /></h2>								
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->					
				<div class="blockSearch">
                	<div class="corner_RoundBox03">
						<table summary="tableSearch">
						<tbody>
							<tr>
                               <th scope="row" width="7%"><ikep4j:message pre="${prePrivate}" key='condition.workPlaceName' /></th>
                               <td width="20%">
                               		<c:choose>
									<c:when test="${empty teamList }">										
										<input type="text" class="inputbox" title="" name="" value="${user.workPlaceName} ${user.teamName}" size="20" disabled="disabled"/>
										<spring:bind path="searchCondition.teamCode">
											<input type="hidden" name="${status.expression}" id="${status.expression}" value="${user.groupId}"/>
										</spring:bind>
									</c:when>
									<c:otherwise>
										<spring:bind path="searchCondition.teamCode">
		                                    <select title="teamCode" name="${status.expression}" id="${status.expression}">		
		                                    	<c:set var="teamValue" value="${searchCondition.teamCode}" />		                                    	                                	
		                                        <c:forEach var="teamInfo" items="${teamList}">				                                        
		                                        	<option value="${teamInfo.teamCode}" <c:if test="${teamValue eq teamInfo.teamCode}">selected="selected"</c:if>>${teamInfo.teamName}</option>
		                                        </c:forEach>	                                        
		                                    </select>
										</spring:bind>	
									</c:otherwise>
									</c:choose>                                 
                               </td>                               
                               <th scope="row" width="7%"><ikep4j:message pre="${prePrivate}" key='list.registerName' /></th>
                               <td width="20%">
                               		<spring:bind path="searchCondition.registerName">
                               			<c:set var="registerName" value="${searchCondition.registerName}" />
                               			<c:if test="${empty searchCondition.registerName}">
                               				<c:set var="registerName" value="${user.userName}" />
                               			</c:if>	                               		
		                      			<input type="text" class="inputbox" title="" name="registerName" id="registerName" value="${registerName}" size="10" onkeypress="javascript:if(event.keyCode == 13){return submit();}"/>			                       	
                               		</spring:bind>    
                               </td>
                               <th scope="row" width="7%"><ikep4j:message pre="${prePrivate}" key='list.searchMonth' /></th>
                               <td width="25%">
                               		<spring:bind path="searchCondition.searchYear">
	                               		<select title="${status.expression}" name="${status.expression}">                               		
	                               		<c:set var="compareYear" value="${searchCondition.searchYear}" />
	                               		<c:if test="${empty compareYear}">
	                               			<c:set var="compareYear" value="${nowYear}" />
	                               		</c:if>
				                       	<c:forEach var="year" begin="1996" end="${nowYear}" step="1">
				                        	<option value="${year}" <c:if test="${year eq compareYear}">selected="selected"</c:if>>${year}</option>
				                           </c:forEach>
				                           <c:if test="${fn:substring(searchCondition.startDate, 5, 7)=='12'}">
	                               				<option value="${nowYear+1}" <c:if test="${nowYear+1 eq compareYear}">selected="selected"</c:if>>${nowYear+1}</option>
	                               			</c:if>
				                       	</select>			                       	
                               		</spring:bind>   
                               		(<c:out value='${fn:substring(searchCondition.startDate, 0, 10)}'/> ~ <c:out value='${fn:substring(searchCondition.endDate, 0, 10)}'/>)                            		
                               </td>		
                               <td class="textRight">
                               <a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>" alt="" /></a>
                               <a href="#a" id="excelButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_excel.gif'/>" alt="" /></a>
                               </td>				
                           </tr>														
		                 </tbody>
		             	</table>
		             	 
		             </div>
				</div>  
				
				<!-- chart start -->				
				<c:choose>
					<c:when test="${searchResult.emptyRecord and !empty searchCondition.registerName}">
						<tbody><tr><td class="emptyRecord"><center><ikep4j:message key='ui.ikep4.common.searchCondition.emptyRecord' /></center></td></tr></tbody>
					</c:when>
					<c:otherwise>
						<c:if test="${!searchResult.emptyRecord}">
							<div style="width:60%;font-weight:bold;font-size:14px;padding-left:10px;margin-top:10px;"><span><ikep4j:message pre='${prePrivate}' key='list.regSum' /></span></div>
							<div id="chart1" style="margin-top:20px; margin-left:20px; width:90%; height:300px;"></div>
							<hr size="2" width="100%" align="left" color="#777777" style="display:block;">
							<br/>
						</c:if>
						
						<!-- chart end -->           
		                            
						<!--//blockListTable Start-->	
						<div class="blockListTable">
						<!--Layout Start-->
						<div id="container">
							<!--List Layout Start-->
							<div id="listDiv"> 
							<table summary="">
								<caption></caption>
								<thead>
								<tr>
									<th scope="col" width="10%">
										<ikep4j:message pre='${prePrivate}' key='list.itemId' />
									</th>
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.searchMonth' />
									</th>							
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.obligationCnt' />	
									</th>
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.regCnt' />		
									</th>
									<th scope="col" width="9%">								
										<ikep4j:message pre='${prePrivate}' key='list.regRate' />			
									</th>	
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.markSum' />				
									</th>	
									<th scope="col" width="9%">								
										<ikep4j:message pre='${prePrivate}' key='list.hitSum' />				
									</th>
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.usageSum' />			
									</th>
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.recommendSum' />
									</th>
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.lineReplySum' />
									</th>		
									<th scope="col" width="9%">
										<ikep4j:message pre='${prePrivate}' key='list.conversionMark' />
									</th>							
								</tr>
								</thead>
								<tbody>
									<c:choose>
									    <c:when test="${searchResult.emptyRecord}">
											<tr>
												<td colspan="11" class="emptyRecord"><ikep4j:message key='ui.ikep4.common.searchCondition.emptyRecord' /></td> 
											</tr>				        
									    </c:when>
									    <c:otherwise>
											<c:forEach  var="performance" items="${searchResult.entity}"  varStatus="idx" begin="0" end="12" >
											<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine">
												<td>
													${idx.index+1}
												</td>
												<td>
													${performance.registDate}
												</td>
												<td>
													${performance.obligationSum}
												</td>	
												<td>
													${performance.regSum}
												</td>	
												<td>
													${performance.obligationRegRate}
												</td>	
												<td>
													${performance.markSum}
												</td>	
												<td>
													${performance.hitSum}
												</td>	
												<td>
													${performance.usageSum}
												</td>	
												<td>
													${performance.recommendSum}
												</td>
												<td>
													${performance.lineReplySum}
												</td>		
												<td>
													${performance.conversionMark}
												</td>
											</tr>									
											</c:forEach>	
											<tr>
												<td colspan="2"><b>합계</b></td>
												<td> <b>${totalSumMap.totObligationSum}</b></td>
												<td> <b>${totalSumMap.totRegSum}</b> </td>
												<td> <b>${totalSumMap.totRegSumRate}</b> </td>
												<td> <b>${totalSumMap.totMarkSum}</b> </td>
												<td> <b>${totalSumMap.totHitCountSum}</b> </td>
												<td> <b>${totalSumMap.totUsageSum}</b> </td>
												<td> <b>${totalSumMap.totRecommendSum}</b> </td>
												<td> <b>${totalSumMap.totLineReplySum}</b> </td>
												<td> <b>${totalSumMap.totConversionMarkSum}</b> </td>
											</tr>			      
									    </c:otherwise> 
									</c:choose> 
									 
								</tbody>				
							</div>
							<!--//blockListTable End-->
							</table>
							
							<!--Layout Start-->
							<div id="container">
								<!--List Layout Start-->
								<div id="listDiv"> 						
									
									
								</div>
							</div>
							</div>	
							</c:otherwise>
						</c:choose>
								
									
			</div>
			
		</div>		
	</div>	
				<!--//splitterBox End-->
</form>
</div>

	
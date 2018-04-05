<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.socialpack.socialanalyzer.listBatchLogView</c:set>
<c:set var="messagePrefix">ui.socialpack.socialanalyzer.message</c:set>
<c:set var="buttonPrefix">ui.socialpack.socialanalyzer.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
(function($) {
	$jq(document).ready(function(){
		//조회 클릭
		$jq("#searchButton").click(function() {
			$("#form").submit();
		});
	});

	
})(jQuery);
</script>

				<h1 class="none"></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${prefix}' key='main.title'/></h2>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						<form id="form" action="<c:url value="/socialpack/socialanalyzer/listBatchLogView.do"/>" method="post">
						<table summary="<ikep4j:message pre='${prefix}' key='table.summary1'/>">
							<caption></caption>
							<tbody>
								<form ac>
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre='${prefix}' key='search.title'/></th>
									<td width="95%">
										<select title="<ikep4j:message pre='${prefix}' key='search.year'/>" name="searchY">
											<option <c:if test="${searchY == '2011'}">selected="selected"</c:if> value="2011">2011</option> 
											<option <c:if test="${searchY == '2012'}">selected="selected"</c:if> value="2012">2012</option> 
											<option <c:if test="${searchY == '2013'}">selected="selected"</c:if> value="2013">2013</option> 
											<option <c:if test="${searchY == '2014'}">selected="selected"</c:if> value="2014">2014</option> 
											<option <c:if test="${searchY == '2015'}">selected="selected"</c:if> value="2015">2015</option> 
											<option <c:if test="${searchY == '2016'}">selected="selected"</c:if> value="2016">2016</option> 
											<option <c:if test="${searchY == '2017'}">selected="selected"</c:if> value="2017">2017</option> 
											<option <c:if test="${searchY == '2018'}">selected="selected"</c:if> value="2018">2018</option> 
											<option <c:if test="${searchY == '2019'}">selected="selected"</c:if> value="2019">2019</option> 
											<option <c:if test="${searchY == '2020'}">selected="selected"</c:if> value="2020">2020</option> 
											<option <c:if test="${searchY == '2021'}">selected="selected"</c:if> value="2021">2021</option> 
											<option <c:if test="${searchY == '2022'}">selected="selected"</c:if> value="2022">2022</option> 
											<option <c:if test="${searchY == '2023'}">selected="selected"</c:if> value="2023">2023</option> 
											<option <c:if test="${searchY == '2024'}">selected="selected"</c:if> value="2024">2024</option> 
											<option <c:if test="${searchY == '2025'}">selected="selected"</c:if> value="2025">2025</option> 
											<option <c:if test="${searchY == '2026'}">selected="selected"</c:if> value="2026">2026</option> 
											<option <c:if test="${searchY == '2027'}">selected="selected"</c:if> value="2027">2027</option> 
											<option <c:if test="${searchY == '2028'}">selected="selected"</c:if> value="2028">2028</option> 
											<option <c:if test="${searchY == '2029'}">selected="selected"</c:if> value="2029">2029</option>
										</select>									
										<select title="<ikep4j:message pre='${prefix}' key='search.month'/>" name="searchM">
											<option <c:if test="${searchM == '01'}">selected="selected"</c:if> value="01">01</option>
											<option <c:if test="${searchM == '02'}">selected="selected"</c:if> value="02">02</option>
											<option <c:if test="${searchM == '03'}">selected="selected"</c:if> value="03">03</option>
											<option <c:if test="${searchM == '04'}">selected="selected"</c:if> value="04">04</option>
											<option <c:if test="${searchM == '05'}">selected="selected"</c:if> value="05">05</option>
											<option <c:if test="${searchM == '06'}">selected="selected"</c:if> value="06">06</option>
											<option <c:if test="${searchM == '07'}">selected="selected"</c:if> value="07">07</option>
											<option <c:if test="${searchM == '08'}">selected="selected"</c:if> value="08">08</option>
											<option <c:if test="${searchM == '09'}">selected="selected"</c:if> value="09">09</option>
											<option <c:if test="${searchM == '10'}">selected="selected"</c:if> value="10">10</option>
											<option <c:if test="${searchM == '11'}">selected="selected"</c:if> value="11">11</option>
											<option <c:if test="${searchM == '12'}">selected="selected"</c:if> value="12">12</option>
										</select>
									</td>
								</tr>
								</form>
							</tbody>
						</table>
						<div class="searchBtn"><a href="#a" id="searchButton"><span><span><ikep4j:message pre='${buttonPrefix}' key='search'/></span></a></div>
						</form>
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
				</div>	
				<!--//blockSearch End-->
				
				<!--blockListTable Start-->
				<div class="MyContentsTable">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary2'/>">
						<caption></caption>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(batchLogList) == 0}">
									<tr class="msg_unread">
										<td scope="col" colspan="3" class="tdFirst tdLast">none</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${batchLogList}">
										<tr class="msg_unread">
											<td scope="col" width="10%" class="tdFirst textRight_p20">
												<c:choose>
													<c:when test="${item.isSuccess == '0'}"><ikep4j:message pre='${prefix}' key='success.completed'/></c:when>
													<c:otherwise><ikep4j:message pre='${prefix}' key='success.failed'/></c:otherwise>
												</c:choose>	
											</td>
											<td scope="col" width="30%" class="textLeft_p20"><ikep4j:timezone date="${item.startDate}" pattern="yyyy.MM.dd HH:mm:ss"/> ~ <ikep4j:timezone date="${item.endDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
											<td scope="col" width="60%" class="textLeft_p20 tdLast">${item.description}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>																																																																																													
						</tbody>
					</table>
 
				</div>
				<!--//blockListTable End-->
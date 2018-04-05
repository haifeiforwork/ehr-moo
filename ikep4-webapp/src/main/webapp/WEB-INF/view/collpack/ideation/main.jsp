<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.ideation.businessList" />

<h1 class="none">Contents Area</h1>

<div id="tagResult">
<!--ideation_process Start-->
<div class="ideation_process">
	<div class="corner_RoundBox07">
		<div class="subTitle_2">
			<h3><img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_2.png"/>" alt="<ikep4j:message pre='${preUi}' key='ideaActi'/>" /> <ikep4j:message pre='${preUi}' key='ideaActi'/></h3>
		</div>
		<div class="process_img">
			<div class="process_01" style="width:25%"><ikep4j:message pre='${preUi}' key='sugIdea'/></div>
			<div class="process_02" style="width:25%"><span class="ar_1"></span><ikep4j:message pre='${preUi}' key='recomIdea'/></div>
			<div class="process_04" style="width:25%"><ikep4j:message pre='${preUi}' key='bestIdea'/></div>
			<!-- <div class="process_04"><ikep4j:message pre='${preUi}' key='adoptIdea'/></div> -->
			<div class="process_05" style="width:25%"><span class="ar_2"></span><ikep4j:message pre='${preUi}' key='busiIdes'/></div>
		</div>
		<div class="clear"></div>
		<div class="process_num">
			<div class="process_num_01" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='sugIdea'/></div>
				<div class="num">
					<span>${totalCountes.count}</span> <span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
				</div>
			</div>
			<div class="process_num_02" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='recomIdea'/></div>
				<div class="num">
					<span>${totalCountes.recommendItem}</span> <span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
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
					<span>${totalCountes.bestCount }</span> <span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
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
					<span>${totalCountes.adoptItem }</span> <span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
					<span class="per">
					<c:if test="${totalCountes.adoptItem != 0}">
						<fmt:formatNumber value="${totalCountes.adoptItem*100/totalCountes.count }" pattern="0" />%
					</c:if>
					</span>
				</div>
			</div>
			-->
			<div class="process_num_05" style="width:25%">
				<div class="none"><ikep4j:message pre='${preUi}' key='adoptIdea'/></div>
				<div class="num">
					<span>${businessCountes.count }</span> <span class="num_s"><ikep4j:message pre='${preUi}' key='count'/></span>
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


<BR><BR>

<!--//ideation_process End-->
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_2.png"/>" alt="top" /> <ikep4j:message pre='${preUi}' key='adoptingIdea'/></h3>
	<div class="btn_more"><a href="lastList.do" title="more"><img src="<c:url value="/base/images/common/btn_more.gif"/>" alt="more" /></a></div>
</div>
<!--//subTitle_2 End-->	

<!--blockListTable Start-->
<div class="blockListTable summaryView">
	<table summary="table">
		<caption></caption>						
		<tbody id="itemFrame">
			<c:import url="/WEB-INF/view/collpack/ideation/ideaListMore.jsp"/>
		</tbody>
	</table>
</div>
<!--//blockListTable End-->



</div>


<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 			value="ui.collpack.qna.main" />
<c:set var="preResource" 	value="message.collpack.qna" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">

var rollUrgent;		//긴급질문 롤링
var rollExpert;		//전문가 롤링

	$jq(document).ready(function(){
		rollUrgent = new qnaEffect('urgentMoveList');
	
		rollExpert = new qnaEffect('expertMoveList');
		
	});


	
	function expertPopup(val){
		
		var $f = $jq('#expertMessage');
		
		$f.find(':input[name=expertId]').val(val);
		$f.show();
		
	}
	
	function expertPopupClose(){
		
		var $f= $jq('#expertMessage');
		
		$f.find(':input[name=expertId]').val('');
		$f.hide();
		
	}
	
	function expertSubmit(){
		
		var $messagForm = $jq('#expertMessage');
		var expertId = $messagForm.find(':input[name=expertId]').val();
		
		var $expertForm = $jq('#qnaExpert_'+expertId);
		
		var channel = "";
		$messagForm.find('input[name=expertChanneles]').each(function(){
			
			if(this.checked == true){
				channel += "1";
			} else {
				channel += "0";
			}
		});
			
		$expertForm.find('input[name=requestChannel]').val(channel);
		$expertForm.submit();
	}

</script>
							
<!--layer start-->
<div id="expertMessage" class="process_layer" style="position: absolute;top:90px; right:100px; z-index:99;display: none;">
	<div class="process_layer_t">
		<ikep4j:message pre='${preUi}' key='messageTitle'/>
		<a href="#a" onclick="expertPopupClose();return false;" title="<ikep4j:message pre='${preUi}' key='messageClose'/>"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="<ikep4j:message pre='${preUi}' key='messageClose'/>" /></a>
	</div>
	<div>
		<table summary="<ikep4j:message pre='${preUi}' key='messageTitle'/>">
			<caption></caption>
			<tbody>
				<tr>
					<td colspan="2" scope="row">
					<ikep4j:message pre='${preUi}' key='messageContents'/><br />
					<span class="colorPoint"><ikep4j:message pre='${preUi}' key='messageSelect'/></span>
					</td>
				</tr>
				<tr>
					<td>
					<form action="formExpertQna.do" method="get" id="expertForm">
						<input type="hidden" name="expertId" />
						<input id="expertChannel1" name="expertChanneles" class="inputbox" type="checkbox" value="" /> <label for="expertChannel1"><ikep4j:message pre='${preUi}' key='messageChannel1'/></label>&nbsp;&nbsp;
						<input id="expertChannel2" name="expertChanneles" class="inputbox" type="checkbox" value="" /> <label for="expertChannel2"><ikep4j:message pre='${preUi}' key='messageChannel2'/></label>&nbsp;&nbsp;
						<input id="expertChannel3" name="expertChanneles" class="inputbox" type="checkbox" value="" /> <label for="expertChannel3"><ikep4j:message pre='${preUi}' key='messageChannel3'/></label>&nbsp;&nbsp;
						<input id="expertChannel4" name="expertChanneles" class="inputbox" type="checkbox" value="" /> <label for="expertChannel4"><ikep4j:message pre='${preUi}' key='messageChannel4'/></label>&nbsp;&nbsp;	
					</form>																									
					</td>
				</tr>		
			</tbody>
		</table>	
	</div>
	<div class="textRight mb10 mr10"><a class="button_ic" href="#a" onclick="expertSubmit();return false;" title="<ikep4j:message pre='${preUi}' key='messageOk'/>"><span><ikep4j:message pre='${preUi}' key='messageOk'/></span></a> 
			<a class="button_ic" href="#a" onclick="expertPopupClose();return false;" title="<ikep4j:message pre='${preUi}' key='messageCancel'/>"><span><ikep4j:message pre='${preUi}' key='messageCancel'/></span></a></div>
</div>		
<!--layer end-->								
							
<h1 class="none">contentsArea</h1>

<!--qna_search Start-->
<c:import url="/WEB-INF/view/collpack/qna/qnaSearch.jsp" charEncoding="UTF-8" />	
<!--//qna_search End-->	

<!--qna_sub Start-->	
<div class="qna_sub" id="tagResult">

	<!--qna_bl Start-->
	<div class="qna_bl">
		<div class="qna_best">
			<div >
				<h2>Best Q&amp;A</h2>
				<c:choose>
				<c:when test="${!empty(bestQnaList[0])}">
				<table summary="<ikep4j:message pre='${preUi}' key='bestListSummary'/>">
					<tbody>
						<tr>
							<th scope="row"><span class="ic_qna_q"><span>Question</span></span></th>
							<td class="qna_best_q"><a href="getQna.do?qnaId=${bestQnaList[0].qnaGroupId}&amp;listType=Main" title="qnaLink">${bestQnaList[0].title}</a></td>
							<td class="qna_best_info" width="35">
								<div class="qna_best_photo">
									<a href="#a" onclick="viewPopUpProfile('${bestQnaList[0].registerId}');return false;"  title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[0].registerName} ${bestQnaList[0].jobTitleName}</c:when><c:otherwise>${bestQnaList[0].userEnglishName} ${bestQnaList[0].jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[0].teamName}</c:when><c:otherwise>${bestQnaList[0].teamEnglishName}</c:otherwise></c:choose>"><!--<img src="<c:url value='${bestQnaList[0].profilePicturePath}'/>" alt="profileImage"  onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />--></a>
								</div>
							</td>
							<td>
								<p class="qna_best_name"><a href="#a" onclick="viewPopUpProfile('${bestQnaList[0].registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[0].registerName} ${bestQnaList[0].jobTitleName}</c:when><c:otherwise>${bestQnaList[0].userEnglishName} ${bestQnaList[0].jobTitleEnglishName}</c:otherwise></c:choose></a>
								<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
								<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[0].teamName}</c:when><c:otherwise>${bestQnaList[0].teamEnglishName}</c:otherwise></c:choose></p>  							
							</td>
						</tr>
						<tr>
							<th scope="row"><span class="ic_qna_a"><span>Answer</span></span></th>
							<td>
								<a href="getQna.do?qnaId=${bestQnaList[1].qnaGroupId}&amp;listType=Main" title="<ikep4j:extractText text="${bestQnaList[1].contents}" length="300"/>"><ikep4j:extractText text="${bestQnaList[1].contents}" length="180"/></a>
							</td>
							<td class="qna_best_info">
								<div class="qna_best_photo">
									<a href="#a" onclick="viewPopUpProfile('${bestQnaList[1].registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[1].registerName} ${bestQnaList[1].jobTitleName}</c:when><c:otherwise>${bestQnaList[1].userEnglishName} ${bestQnaList[1].jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[1].teamName}</c:when><c:otherwise>${bestQnaList[1].teamEnglishName}</c:otherwise></c:choose>"><!--<img src="<c:url value='${bestQnaList[1].profilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />--></a>
								</div>
							</td>
							<td>
								<p class="qna_best_name"><a href="#a" onclick="viewPopUpProfile('${bestQnaList[1].registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[1].registerName} ${bestQnaList[1].jobTitleName}</c:when><c:otherwise>${bestQnaList[1].userEnglishName} ${bestQnaList[1].jobTitleEnglishName}</c:otherwise></c:choose></a>
								<c:if test="${!empty bestQnaList[1].registerId}"><img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /></c:if>
								<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${bestQnaList[1].teamName}</c:when><c:otherwise>${bestQnaList[1].teamEnglishName}</c:otherwise></c:choose></p>							
							</td>
						</tr>								
					</tbody>
				</table>
				</c:when>
				<c:otherwise>
					<ikep4j:message key='message.collpack.qna.notData.best.qna'/>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="qna_mainlist">
			<table summary="<ikep4j:message pre='${preUi}' key='lastListSummary'/>">
				<tbody>
					<c:forEach var="qna" items="${newList}" varStatus="qnaLoopCount">
						<tr>
							<td width="30">
								<div class="qna_mainlist_photo">
									<div class="ic_question"><img src="<c:url value="/base/images/icon/ic_question.png"/>" alt="question" /></div>
									<div class="photoimg"><a href="#a" onclick="viewPopUpProfile('${qna.registerId}');return false;"  title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName}</c:when><c:otherwise>${qna.userEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.jobTitleName}</c:when><c:otherwise>${qna.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.teamName}</c:when><c:otherwise>${qna.teamEnglishName}</c:otherwise></c:choose>"><!--<img src="<c:url value='${qna.profilePicturePath}'/>" alt="profileImage"  onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />--></a></div>
								</div>
							</td>
							<td>
								<div class="qna_mainlist_q">
							 		<c:choose>
							 			<c:when test="${qna.status  == 0}">
							 				<span class="status_None"><ikep4j:message pre='${preUi}' key='lastListStatusN'/></span>
							 			</c:when>
							 			<c:when test="${qna.status  == 1}">
							 				<span class="status_Ing"><ikep4j:message pre='${preUi}' key='lastListStatusA'/></span>
							 			</c:when>
							 			<c:when test="${qna.status  == 2}">
							 				<span class="status_Done"><ikep4j:message pre='${preUi}' key='lastListStatusSolve'/></span>
							 			</c:when>
							 		</c:choose>
								<a href="getQna.do?qnaId=${qna.qnaGroupId}&amp;listType=Main" title="qna">${qna.title}</a> 
								<c:if test="${qna.bestFlag  == 1}">
				 					<img src="<c:url value="/base/images/icon/ic_best.gif"/>" alt="best" />
				 				</c:if>
								</div>
							
								<span class="qna_mainlist_name"><a href="#a" onclick="viewPopUpProfile('${qna.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName}</c:when><c:otherwise>${qna.userEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.jobTitleName}</c:when><c:otherwise>${qna.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
								<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
								<span class="qna_mainlist_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.teamName}</c:when><c:otherwise>${qna.teamEnglishName}</c:otherwise></c:choose> </span>
								<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
								<span class="qna_mainlist_date"><ikep4j:timezone date="${qna.registDate}" pattern="message.kms.qna.timezone.dateformat.type2" keyString="true"/></span>
								
							
							</td>
							<td width="130" class="textRight">
								<div class="qna_mainlist_num">
									<ikep4j:message pre='${preUi}' key='lastListHitCount'/> <span class="colorPoint">(${qna.hitCount})</span>
									<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
									<ikep4j:message pre='${preUi}' key='lastListRecommendCount'/> <span class="colorPoint">(${qna.recommendCount})</span>
								</div>
							</td>
						</tr>
					</c:forEach>	
				</tbody>
			</table>
		</div>
	</div>
	<!--//qna_bl End-->	
	
	<!--qna_br Start-->
	<div class="qna_br">
	
		<div class="qna_expert">
			<h2><ikep4j:message pre='${preUi}' key='expertListTitle'/></h2>
			<div id="expertMoveList">
			<c:forEach var="expert" items="${qnaExpertList}" varStatus="loopCount">
				<div style="display:<c:if test="${loopCount.count != 1}">none</c:if>;">
					<form action="formExpertQna.do" method="get"  id="qnaExpert_${expert.expertId}">
						<input type="hidden" name="expertId" value="${expert.expertId}"/>
						<input type="hidden" name="jobTitleName" value="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.jobTitleName}</c:when><c:otherwise>${expert.jobTitleEnglishName}</c:otherwise></c:choose>"/>
						<input type="hidden" name="teamName" value="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.teamName}</c:when><c:otherwise>${expert.teamEnglishName}</c:otherwise></c:choose>"/>
						<input type="hidden" name="userName" value="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.userName}</c:when><c:otherwise>${expert.userEnglishName}</c:otherwise></c:choose>"/>
						<input type="hidden" name="mobile" value="${expert.mobile}"/>
						<input type="hidden" name="mail" value="${expert.mail}"/>
						<input type="hidden" name="requestChannel" value=""/>
					</form>
					<div class="qna_expert_photo"><a href="#a" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.userName} ${expert.jobTitleName}</c:when><c:otherwise>${expert.userEnglishName} ${expert.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.teamName}</c:when><c:otherwise>${expert.teamEnglishName}</c:otherwise></c:choose>" onclick="viewPopUpProfile('${expert.expertId}');return false;"><!--<img src="<c:url value='${expert.profilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"  />--></a></div>
					<p class="qna_best_name"><a href="#a" title="profile" onclick="viewPopUpProfile('${expert.expertId}');return false;"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.userName} ${expert.jobTitleName}</c:when><c:otherwise>${expert.userEnglishName} ${expert.jobTitleEnglishName}</c:otherwise></c:choose></a></p>	
					<p class="qna_best_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.teamName}</c:when><c:otherwise>${expert.teamEnglishName}</c:otherwise></c:choose></p> 
					<a class="button_ic" href="#a" onclick="expertPopup('${expert.expertId}');" title="<ikep4j:message pre='${preUi}' key='expertListQna'/>"><span><img src="<c:url value="/base/images/icon/ic_btn_write.gif"/>" alt="<ikep4j:message pre='${preUi}' key='expertListQna'/>" /><ikep4j:message pre='${preUi}' key='expertListQna'/></span></a>
					<a href="#a" onclick="rollExpert.back();return false;"><</a> <a href="#a" onclick="rollExpert.after();return false;">></a>
					<div class="clear"></div>
					<c:if test="${fn:length(expert.qnaList)>0 }">
						<div class="qna_expert_list">						
							<ul>
								<c:forEach var="qna" items="${expert.qnaList}" varStatus="tagLoop">
									<li><a href="getQna.do?qnaId=${qna.qnaGroupId }" title="qna">${qna.title }</a></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</div>
			</c:forEach>
			</div>
		</div>
		
		<div class="qna_expert">
			<h2 style="margin-top:20px;margin-bottom:10px;"><ikep4j:message pre='${preUi}' key='tagListTitle'/></h2>
			<div id="tagQna">
				<script type="text/javascript">
					iKEP.createTagEmbedObject("#tagQna", "<c:url value="/collpack/qna/qnaTag.do?type=Q"/>", "tagResult");
				</script>
			</div>
		</div>
			
	</div>											
	<!--//qna_br End-->		
				
</div>
<!--//qna_sub End-->


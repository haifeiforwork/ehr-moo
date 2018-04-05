<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.question.create" /> 
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preInput"   value="ui.servicepack.survey.question.group" />
<c:set var="preTemplet"   value="ui.servicepack.survey.templet" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--   
	var deletegroupconfirm = '<ikep4j:message  key="message.servicepack.survey.addgroup.delete" />';
	var deletequestionconfirm = '<ikep4j:message  key="message.servicepack.survey.addquestion.delete" />';
	var minInputValue = '<ikep4j:message  key="message.servicepack.survey.question.create.inputvalue.min" />';
	var maxInputValue = '<ikep4j:message  key="message.servicepack.survey.question.create.inputvalue.max" />';

	var questionGroupTitle = '<ikep4j:message  key="ui.servicepack.survey.templet.questionGroupTitle" />';
	var questionGroupContents = '<ikep4j:message  key="ui.servicepack.survey.templet.questionGroupContents" />';
	var questionTitle = '<ikep4j:message  key="ui.servicepack.survey.templet.questionTitle" />';
	var answerTitle = '<ikep4j:message  key="ui.servicepack.survey.templet.answerTitle" />';
	var scaleTitle = '<ikep4j:message  key="ui.servicepack.survey.templet.scaleTitle" />';
	var updateTitle = '<ikep4j:message  key="ui.servicepack.survey.common.button.update" />';
	var saveSuccess ='<ikep4j:message  key="ui.servicepack.survey.common.saveSuccess" />';
	
	var compQTitle ="<ikep4j:message pre="${preTemplet}" key="questionTitle" />";
	var compATitle ="<ikep4j:message pre="${preTemplet}" key="answerTitle" />";
	
	var nullQTitle ="<ikep4j:message key="ui.servicepack.survey.common.nullQTitle" />";	
	var nullATitle ="<ikep4j:message key="ui.servicepack.survey.common.nullATitle" />";
//-->
</script>


<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/survey/jquery.blockUI.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/survey/surveyWiget.js'/>"></script>

<script type="text/javascript">
<!--   
(function($) {

	$(document).ready(function(){   
		var $doc           = $(document);   
		var $win           = $(window);   
		var $body          = $('body');   
		var position       = 0;   
		var top            = $doc.scrollTop();   
		var screenSize     = 0;   
		var halfScreenSize = 0;   
		var $layer         = $('#floating');   
		var tValues        = $layer.attr('values');   
		$layer.css("position","absolute");   



		//기본값   
		var type       = 'left';   
		var margin     = '0';   
		var speed      = '1000';   
		var easing     = 'linear';   
		var topMargin  = '0';   
		var minTop     = '0';   
		$layer.css('z-index', '10');   

		//값이 있는 경우에만   
		if (tValues)   
		{   
			//json값으로 바꾸고   
			setValues  = eval("("+tValues+")");

			//값이 있는 경우에만 값을 가져옴   
			if (setValues.pageWidth) {
				setValues.pageWidth=$body.width();

				var offset = parseInt(setValues.pageWidth)/2;

			}
			else  
			{   
				alert('pageWidth값은 필수입니다.');   
				return false;   
			}   
			margin      = (setValues.margin)? parseInt(setValues.margin):'0';   
			speed       = (setValues.speed)? parseInt(setValues.speed):'1000';   
			easing      = (setValues.easing)? setValues.easing:'linear';   
			topMargin   = (setValues.topMargin)? parseInt(setValues.topMargin):'0';   
			minTop      = (setValues.minTop)? parseInt(setValues.minTop):'0';   
			$layer.css('z-index', (setValues.zindex)? setValues.zindex:'10');   
			marginResult = offset + margin;   

			//좌측인 경우   
			if (setValues.type == 'left')   
				type = 'right';   
		}   

		//좌우 위치값 정해주는 함수   
		function resetXPosition()   
		{   
			$screenSize = $body.width();   
			halfScreenSize = $screenSize/2;   
			xPosition = halfScreenSize + marginResult;
			
			//alert($(".surveyBlock").outerWidth(true));
			//alert(xPosition)
			xPosition = $(".surveyBlock").outerWidth(true);
			$layer.css(type, xPosition);   
		}   

		//최초 좌우 및 상단위치   
		resetXPosition();   
		$layer.css('top',topMargin);   

		//윈도우 크기가 바뀌면   
		$win.resize(resetXPosition);   

		//스코롤하면   
		$win.scroll(function(){
			//스크롤 상태에서 상단과의 거리   
			top = $doc.scrollTop();   
			if (top > topMargin+minTop) {   
				yPosition = top + minTop;   
			} else { 
				yPosition = top + topMargin;   
			}
			//따라다니기 적용   
		//	$layer.animate({"top":yPosition }, {duration:speed, easing:easing, queue:false});   
		});
		

		// Question Type Block Show/Hide
		var check = false;		
		blockShow = function() {
			check = false;
		};
				
		$('body').click(function(){
			if( !$('#surveySelLayerTop').hasClass("none") ){
				if(check) { 
					$('.survey_sel_layer').addClass('none');
					check = true;
					$('#surveySelLayerTop').addClass("none");
					$('#selectQuestionType').click();
				}
			}
			check = true;
		});
		
	});   
})(jQuery);
//-->
</script>
<!--mainContents Start-->
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1>

<!-- pageTitle Start -->
<div id="pageTitle"> 
<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 				
<!--//pageTitle End-->

<!--surveyBlock Start-->
<div class="surveyBlock">
	<!--surveyBlock_l Start-->
	<div class="surveyBlock_l">

		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
		<h3><ikep4j:message  key="ui.servicepack.survey.list.title" /> : ${survey.title}</h3>
		</div>
		<!--//subTitle_2 End-->

		<!--directive Start-->
		<div class="directive"> 
		<ul>
		<li><span><ikep4j:message  key="ui.servicepack.survey.templet.notice1" /></span></li>	
		<!--li><span><ikep4j:message  key="ui.servicepack.survey.templet.notice2" /></span></li-->	
		<li><span><ikep4j:message  key="ui.servicepack.survey.templet.notice3" /></span></li>	
		</ul>
		</div>

		<!--layer start-->
		<!-- 그룹/질문항목/답변항목 수정 공통폼 S-->
		<div class="layer_01" style="padding:0 10px 8px 10px; display:none; width:500px;z-index:10000;" id="inputTextAreaLayer">
			<a class="layer_close" href="#a" id="inputTextAreaLayerClose1"><span>close</span></a>
			<div class="smsvs_layer" style="padding-bottom:2px; border-bottom:none;">
			<span class="smsvs_layer_title"><ikep4j:message  key="ui.servicepack.survey.list.edit.title" /></span>
			</div>
			<div class="smsvs_layer" style="padding-bottom:2px; border-bottom:none;">
			<span class="smsvs_layer_title"><ikep4j:message  key="ui.servicepack.survey.list.edit.groupTitle" /></span>
			</div>
			<div>
			<textarea name="title"  id="title" class="inputbox" title="" style="width:98%;"  rows="4"  cols="80"></textarea>
			</div>
			<div class="smsvs_layer" style="padding-bottom:2px; border-bottom:none;">
			<span class="smsvs_layer_title"><ikep4j:message  key="ui.servicepack.survey.list.edit.groupSubTitle" /></span>
			</div>

			<div>
			<textarea name="contents"  id="contents" class="inputbox" title="" style="width:98%;"  rows="4"  cols="80"></textarea>
			</div>
			<!--blockButton Start-->
			<div class="blockButton mt5 mb0"> 
			<ul>
			<li><a class="button" href="#a" id="inputTextAreaLayerSave"><span><ikep4j:message pre="${preButton}" key="confirm" /></span></a></li>
			<li><a class="button" id="inputTextAreaLayerClose" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
			</ul>
			</div>
			<!--//blockButton End-->	
	
		</div>
		<!--//그룹/질문항목/답변항목 수정 공통폼 E-->
		<!--//layer end-->

		<!--//directive End-->

		<div class="blockBlank_10px"></div>					

		<div id="questionAllBlock" class="surveyAccordion">
			<script type="text/javascript">
			(function($) {
				$(document).ready(function() {
					$('#questionAllBlock').data("surveyId",'${survey.surveyId}');
				});
			})(jQuery);
			</script>

			<c:set var="qnaSeq" value="0"/> 

			<c:forEach var="questionGroup" items="${questionGroupList}" varStatus="loopStatus">
			<!-- 질문 그룹 목록 ${loopStatus.count} 번째Start -->
			<div class="questionGroupAllBlock mb10" id="questionGroupAllBlock_${questionGroup.questionGroupId}">
				<script type="text/javascript">
				(function($) {
				$(document).ready(function() {
					$('#questionGroupAllBlock_${questionGroup.questionGroupId}')
						.data("questionGroupId",'${questionGroup.questionGroupId}')
						.data("questionGroupSeq",'${questionGroup.questionGroupSeq}');
					});
				})(jQuery);
				</script>
				
				<!-- 질문 그룹 Wrap Start -->
				<h3 class="surveyBox_1"   style="padding-left:20px; margin-bottom:1px;">
				<span class="questionGroupTitle" style="color:black;display:inline;">${questionGroup.title}</span><span style="display:inline;" class="group_edit_btn none"><a href="#a" onclick="editGroupTitle('${survey.surveyId}','${questionGroup.questionGroupId}');" style="padding-left:10px;padding-top:3px;display:inline; background:none;"><img align="absmiddle" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"/></a></span>
				<span  style="color:black;" class="questionGroupContents display_block none">${questionGroup.contents}</span>
				<span class="surveyBox_resize none">
				<span class="ic_rm" ><a href="#a" name="removequestionGroup"  class="bg_none" style="padding:0;"><img src="<c:url value='/base/images/icon/ic_cir_minus.gif'/>" alt="minus" /></a></span>
				</span>
				</h3>
				<!--// 질문 그룹 Wrap Start -->

				<!-- 질문 그룹 Start -->
				<div class="questionGroupBlock mb10" style="border:1px solid #bebebe;">
					<!-- 설문 문항 추가 Start -->
					<div class="blockButton_3"> 
					<a class="button_3" name="addQuestionButton" href="#a"><span><img src="<c:url value='/base/images/icon/ic_cir_plus_2.gif'/>" style="vertical-align:middle; padding-bottom:2px;" alt="" />&nbsp;&nbsp;<ikep4j:message pre='${preButton}' key='addQuestion'/></span></a>	
					</div>
					<!--// 설문 문항 추가 End -->
					
					
					<c:forEach var="question" items="${questionGroup.questionList}" varStatus="loopStatus">
					
					<!-- 설문 문항  Start -->
					<c:set var="qnaSeq" value="${qnaSeq+1}"/>
					<div class="surveyBox_2" style="min-height:100px;">
						<table summary="" style="min-height:100px;">
						<caption></caption>
						<tbody>
						<tr>	
						<th scope="row" height="100">
							<div><label class="qnaSeq">Q${qnaSeq}</label></div>
							<div class="ic_arb none">
							<a href="#a" name="moveBeforItems"><img src="<c:url value='/base/images/icon/ic_arb_up.gif'/>" alt="up" /></a>
							<a href="#a" name="moveAfterItems"><img src="<c:url value='/base/images/icon/ic_arb_down.gif'/>" alt="down" /></a>
							</div>
						</th>
						<td valign="top">
							<div class="surveyList surveyListBlock" id="surveyListBlock_${question.questionId}">
								<script type="text/javascript">
								(function($) {
									$(document).ready(function() {
										$('#surveyListBlock_${question.questionId}')
										.data("columnCount",'${question.columnCount}')
										.data("displayType",'${question.displayType}')
										.data("questionId",'${question.questionId}')
										.data("questionSeq",'${question.questionSeq}')
										.data("questionType",'${question.questionType}')
										.data("requiredAnswer",'${question.requiredAnswer}')
										.data("answerRowCnt",'${fn:length(question.answer)}');
									});
								})(jQuery);
								</script>

								<!-- 질문 항목 Start -->
								<c:choose>
								<c:when test="${fn:indexOf(question.questionType,'A')>=0 or fn:indexOf(question.questionType,'B')>=0 }">
									<span class="questionTitle">${question.title}</span>
										<a href="#a" class="edit_btn none"><img align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"/></a>
										<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
										<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
									<ul class="answerBlock" >

									<c:if test="${!empty question.answer}"><li class="bg_none"></c:if>
									<c:set var="loopCnt" value="0"/>
									
									<!-- A,B 타입의 질문에 대한 답변  항목 Start -->
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									
										<c:choose>
										<c:when test="${question.displayType eq '0' and loopStatus.index > 0  and fn:indexOf(question.questionType,'A')>=0}">
											</li>
											<li  class="bg_none">
										</c:when>
										<c:when test="${question.displayType eq '2' and loopStatus.index > 0  and fn:indexOf(question.questionType,'A')>=0 }">
											<c:if test="${loopCnt eq question.columnCount}">
											</li>
											<c:set var="loopCnt" value="0"/>
											<li  class="bg_none">
											</c:if>
										</c:when>
										</c:choose>

										<c:choose>
										<c:when test="${question.questionType eq 'A0'}">
											<label style="display:inline-block;padding-right:10px;min-width:100px;"><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></label>
										</c:when>
										<c:when test="${question.questionType eq 'A1'}">
											<label style="padding-right:10px;"><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span>&nbsp;
											<span class="fileId none">${answer.img}</span><span class="attFile <c:if test="${empty answer.img}">none</c:if>">
											<a href="#a" onclick="fileUpload('${loopStatus.index}')">
											<c:if test="${!empty answer.img}">
											<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${answer.img}' />" id="image${loopStatus.index}"  width="50" height="50" alt="image" /> 
											</c:if>
											</a></span>									 
											</label>
											<script type="text/javascript">
											(function($) {
												$(document).ready(function() {
													$('#image${answer.answerId}').data("fileId",'${answer.img}');
													$('#answerId_${answer.answerId}').data("answerId",'${answer.answerId}');
												});
											})(jQuery);
											</script>
										</c:when>
										<c:when test="${question.questionType eq 'A2'}">
											<label style="padding-right:10px;"><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><div class="clear"></div><input name="" type="text" class="inputbox valign_top w90" title="" /></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'A3'}">
											<label style="padding-right:10px;"><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><div class="clear"></div><textarea name="" class="inputbox valign_top w90" title="" rows="4"></textarea></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'A4'}">
											<label style="display:inline-block;padding-right:10px;min-width:100px;"><input name="" type="checkbox" title="" class="checkbox valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span> </label>
										</c:when>
										<c:when test="${question.questionType eq 'A5'}">
											<label style="padding-right:10px;"><input name="" type="checkbox" title="" class="checkbox valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span>&nbsp;
											<span class="fileId none">${answer.img}</span><span class="attFile <c:if test="${empty answer.img}">none</c:if>">
											<a href="#a" onclick="fileUpload('${loopStatus.index}')">
											<c:if test="${!empty answer.img}">
											<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${answer.img}' />" id="image${loopStatus.index}"  width="50" height="50" alt="image" /> 
											</c:if>
											</a></span>
											</label>
											<script type="text/javascript">
											(function($) {
												$(document).ready(function() {
													$('#image${answer.answerId}').data("fileId",'${answer.img}');
													$('#answerId_${answer.answerId}').data("answerId",'${answer.answerId}');
												});
											})(jQuery);
											</script>
										</c:when>
										<c:when test="${question.questionType eq 'A6'}">
											<label style="padding-right:10px;"><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><div class="clear"></div><input name="" type="text" class="inputbox  valign_top w90" title="" /></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'A7'}">
											<label style="padding-right:10px;"><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><div class="clear"></div><textarea name="" class="inputbox valign_top w90" title="" rows="4"></textarea></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'B0'}">
											<input name="" type="text" class="inputbox  valign_top" title="" size="50" /><span class="answerId none">${answer.answerId}</span><div class="answerTitle"  style="display:none;">${answer.title}</div>
										</c:when>
										<c:when test="${question.questionType eq 'B1'}">
											</li><li class="bg_none"><span class="answerId none">${answer.answerId}</span><span style="display:inline-block;min-width:300px; padding-right:10px" class="answerTitle">${answer.title}</span><input name="" type="text" class="inputbox  valign_top" title="" size="30" />
										</c:when>
										<c:when test="${question.questionType eq 'B2'}">
											</li><li  class="bg_none"><input name="" type="text" class="inputbox valign_top"  title="" size="30" /><span class="answerId none">${answer.answerId}</span><div class="answerTitle"  style="display:none;">${answer.title}</div> 
										</c:when>
										<c:when test="${question.questionType eq 'B3'}"><span class="answerId none">${answer.answerId}</span>
											<textarea name="" class="inputbox valign_top"  title="" cols="100" rows="4"></textarea><div class="answerTitle"  style="display:none;">${answer.title}</div>
										</c:when>
										<c:otherwise>
											none
										</c:otherwise>
										</c:choose>	

										<c:set var="loopCnt" value="${loopCnt+1}"/>

									</c:forEach>
									<!--// A,B 타입의 질문에 대한 답변  항목 End -->
									<c:if test="${!empty question.answer}"></li></c:if>
									</ul>
								</c:when>

								<c:when test="${fn:indexOf(question.questionType,'C')>=0}">
									<!--// C 타입의 질문에 대한 답변  항목 Start -->
									<div class="blockDetail">
									<table summary="" style="table-layout:auto;">
									<caption></caption>
									<thead class="answerBlockHeader">
									<tr>
									<th colspan="2" scope="col">
										<span class="questionTitle">${question.title}</span>
										<a href="#a" class="edit_btn none"><img align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
										<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
										<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
									</th>
									</tr>
									</thead>
									<tbody class="answerBlock">
										<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td width="*"><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td width="20px;" class="textCenter">
											<select title="">
											<option value=""><ikep4j:message key='ui.servicepack.survey.common.selected' /></option>	
											<c:forEach var="answer2" items="${question.answer}" varStatus="loopStatus">
											<option value="${loopStatus.index+1}">${loopStatus.index+1}</option>
											</c:forEach>
											</select>														
											</td>
											</tr>
										</c:forEach>
									</tbody>
									</table>
									</div>
									<!--// C 타입의 질문에 대한 답변  항목 End -->
								</c:when>

								<c:when test="${fn:indexOf(question.questionType,'D')>=0}">
									<!--// D 타입의 질문에 대한 답변  항목 Start -->
									<div class="blockDetail">
									<table summary=""   style="table-layout:auto;">
									<caption></caption>
									<thead class="answerBlockHeader">
										<c:choose>
										<c:when test="${question.questionType eq 'D0'}">
											<tr>	
											<th width="*" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
											</th>
											<th width="135" scope="col">
											<ul class="surveyList_num view">
											<li style="width:45px"><div class="scale1Title">${question.scale1}</div></li>
											<li style="width:45px"><div class="scale2Title">${question.scale2}</div></li>
											<li style="width:45px"><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
										</c:when>
										<c:when test="${question.questionType eq 'D1'}">
											<tr>
											<th width="*" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
											</th>
											<th width="145" scope="col">
											<ul class="surveyList_num view">
											<li><div class="scale1Title">${question.scale1}</div></li>
											<li>&nbsp;</li>
											<li><div class="scale2Title">${question.scale2}</div></li>
											<li>&nbsp;</li>
											<li><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
										</c:when>
										<c:when test="${question.questionType eq 'D2'}">
											<tr>
											<th width="*" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
											</th>
											<th width="172" scope="col">
											<ul class="surveyList_num view">
											<li><div class="scale1Title">${question.scale1}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
										</c:when>
										<c:when test="${question.questionType eq 'D3'}">
											<tr>
											<th width="*" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
											</th>
											<th width="182" scope="col">
											<ul class="surveyList_num view">
											<li><div class="scale1Title">${question.scale1}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale2Title">${question.scale2}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
										</c:when>
										<c:when test="${question.questionType eq 'D4'}">
											<tr>
											<th width="*" rowspan="2" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>											
											</th>
											<th width="50px;" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
											<th width="145" rowspan="2" scope="col">
											<ul class="surveyList_num view">
											<li><div class="scale1Title">${question.scale1}</div></li>
											<li>&nbsp;</li>
											<li><div class="scale2Title">${question.scale2}</div></li>
											<li>&nbsp;</li>
											<li><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
											<tr>
											<th scope="col" style="width:20px;">O</th>
											<th scope="col" style="width:20px;">X</th>
											</tr>
										</c:when>
										<c:when test="${question.questionType eq 'D5'}">
											<tr>
											<th width="*" rowspan="2" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
											</th>
											<th width="50px;" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
											<th width="182" rowspan="2" scope="col">
											<ul class="surveyList_num view">
											<li><div class="scale1Title">${question.scale1}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale2Title">${question.scale2}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
											<tr>
											<th scope="col" style="width:20px;">O</th>
											<th scope="col" style="width:20px;">X</th>
											</tr>
										</c:when>

										<c:when test="${question.questionType eq 'D6'}">
											<tr>
											<th width="*" scope="col">
												<span class="answerId none">${answer.answerId}</span><span class="questionTitle">${question.title}</span>
												<a href="#a" class="edit_btn none"><img align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit" /></a>
												<a href="#a" class="save_btn none"><img src="<c:url value='/base/images/icon/ic_save.gif'/>" alt="Save" /></a>
												<a href="#a" class="cancel_btn none"><img src="<c:url value='/base/images/icon/ic_cancel.gif'/>" alt="Save" /></a>
											</th>
											<th width="20" scope="col">N/A</th>
											<th width="182" scope="col">
											<ul class="surveyList_num view">
											<li><div class="scale1Title">${question.scale1}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale2Title">${question.scale2}</div></li>
											<li>&nbsp;</li>
											<li>&nbsp;</li>
											<li><div class="scale3Title">${question.scale3}</div></li>
											</ul>
											</th>
											</tr>
										</c:when>
										<c:when test="${question.questionType eq 'D7'}">
											<tr>
											<th width="12%" scope="col">수준</th>
											<th width="*" scope="col">수준별 특성</th>
											<th width="12%" scope="col">요구역량</th>
											<th width="12%" scope="col">본인역량</th>
											</tr>
										</c:when>
										</c:choose>
									</thead>

									<tbody class="answerBlock">
										<c:choose>
										<c:when test="${question.questionType eq 'D0'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li style="width:45px"><input name="" type="radio" title="<ikep4j:message  key="ui.servicepack.survey.templet.scale1" />" class="radio valign_middle" value="" /></li>
											<li style="width:45px"><input name="" type="radio" title="<ikep4j:message  key="ui.servicepack.survey.templet.scale2" />" class="radio valign_middle" value="" /></li>
											<li style="width:45px"><input name="" type="radio" title="<ikep4j:message  key="ui.servicepack.survey.templet.scale3" />" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:when test="${question.questionType eq 'D1'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:when test="${question.questionType eq 'D2'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:when test="${question.questionType eq 'D3'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="7" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:when test="${question.questionType eq 'D4'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter"><input name="" type="radio" title="O" class="radio valign_middle" value="" /></td>
											<td class="textCenter"><input name="" type="radio" title="X" class="radio valign_middle" value="" /></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:when test="${question.questionType eq 'D5'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter"><input name="" type="radio" title="O" class="radio valign_middle" value="" /></td>
											<td class="textCenter"><input name="" type="radio" title="X" class="radio valign_middle" value="" /></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="7" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>

										<c:when test="${question.questionType eq 'D6'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></td>
											<td class="textCenter"><input name="" type="radio" title="N/A" class="radio valign_middle" value="" /></td>
											<td class="textCenter">
											<ul class="surveyList_num view">
											<li><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
											<li><input name="" type="radio" title="7" class="radio valign_middle" value="" /></li>
											</ul>											
											</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:when test="${question.questionType eq 'D7'}">
											<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td class="textCenter">1수준</td>
											<td>
											발표내용과 적합한 실제사례를 수집하여 활용한다.<br />
											프리젠테이션시 내용에 대한 전문성을 확보하여 자신감있게 진행한다.
											</td>
											<td class="textCenter"></td>
											<td class="textCenter"><input name="" type="radio" title="" class="radio valign_middle" value="" /></td>
											</tr>
											</c:forEach>
										</c:when>
										</c:choose>
									</tbody>
									</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="blockDetail">삭제 해주세요</div>
								</c:otherwise>
								</c:choose>
							</div>	
						</td>
						</tr> 
						</tbody>
						</table>

						<div class="surveyBox_resize  none">
							<div class="ic_rt"><a href="#a" name="insertBeforItems"><img src="<c:url value='/base/images/icon/ic_cir_plus.gif'/>" alt="plus" /></a></div>
							<div class="ic_rm"><a href="#a" name="removeItems"><img src="<c:url value='/base/images/icon/ic_cir_minus.gif'/>" alt="minus" /></a></div>
							<div class="ic_rb"><a href="#a" name="insertAfterItems"><img src="<c:url value='/base/images/icon/ic_cir_plus.gif'/>" alt="plus" /></a></div>
						</div>				
					</div>
					<!--// 설문 문항 End -->

					</c:forEach>

					<!--blockButton_3 Start-->

				</div>	
			</div>
			<!--//질문 그룹 목록 ${loopStatus.count} 번째 End -->
			</c:forEach>


			<div class="blockButton_3"> 
			<a class="button_3" id="addQuestionGroupButton" href="#a"><span><img src="<c:url value='/base/images/icon/ic_cir_plus_2.gif'/>" style="vertical-align:middle; padding-bottom:2px;" alt="" />&nbsp;&nbsp;<ikep4j:message pre='${preButton}' key='addQuestionGroup'/></span></a>				
			</div>
		</div>

		<!--//surveyBlock_l End-->
		<div id="floating" values="{'pageWidth':'1024','type':'right','topMargin':'20','minTop':'100', 'margin':'20','speed':'500','easing':'swing','zindex':'10'}">
		<%@ include file="/WEB-INF/view/servicepack/survey/question/questionlayer.jsp"%>
		</div>
		<!--//surveyBlock_r End-->

		<div class="clear"></div>
		
	</div>
	<!--//surveyBlock End-->
	
	<!-- survey templet -->
	<%@ include file="/WEB-INF/view/servicepack/survey/question/questionTemplate.jsp"%>
	<!-- survey templet end -->
			
</div>
<!--//mainContents End-->

<div class="clear"></div>

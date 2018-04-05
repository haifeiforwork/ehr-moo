<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.question.create" /> 
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preInput"   value="ui.servicepack.survey.question.group" />

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
//-->
</script>


<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/survey/jquery.blockUI.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/survey/surveyWigetEdit.js'/>"></script>

<script type="text/javascript">
<!--
var dialogWindow = null;
var fnCaller;
(function($) {
	img_error = function() {
		document.images.fileuploadBtn.src="<c:url value='/base/images/icon/ic_img_add.png' />";	
	}
	
	fnCaller = function (params, dialog) {
		if(params) {
			if(params.items) {
				appendItem(params.items);
			}
			if(params.search) {
				$jq("#treesch").val(params.search);
			}
		}
		
		dialogWindow = dialog;

		$jq("#cancelButton").click(function() {		
			
			//parent.location.href="<c:url value='/servicepack/survey/question/createQuestion.do?surveyId=${survey.surveyId}'/>";
			parent.location.reload();
			
			dialogWindow.close();
		});
	};
	
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
		<li><span><ikep4j:message  key="ui.servicepack.survey.templet.notice2" /></span></li>	
		<li><span><ikep4j:message  key="ui.servicepack.survey.templet.notice3" /></span></li>	
		</ul>
		</div>


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

			<!-- 질문 그룹 목록 ${loopStatus.count} 번째Start -->
			<div class="questionGroupAllBlock mb10" id="questionGroupAllBlock">

				<!-- 질문 그룹 Start -->
				<div class="questionGroupBlock mb10" style="border:1px solid #bebebe;">
					
					
					<c:forEach var="question" items="${question}" varStatus="loopStatus">
					
					<!-- 설문 문항  Start -->
					<c:set var="qnaSeq" value="${qnaSeq+1}"/>
					<div class="surveyBox_2" style="min-height:100px;">
						<table summary="" style="min-height:100px;">
						<caption></caption>
						<tbody>
						<tr>	
						<th scope="row" height="100">
							<div><label class="qnaSeq">Q${question.questionSeq}</label></div>
						</th>
						<td>
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
										.data("answerRowCnt",'${fn:length(question.answer)}')
										.data("questionGroupId",'${question.questionGroupId}');
									});
								})(jQuery);
								</script>

								<!-- 질문 항목 Start -->
								<c:choose>
								<c:when test="${fn:indexOf(question.questionType,'A')>=0 or fn:indexOf(question.questionType,'B')>=0 }">
									<div class="questionTitle">${question.title}</div>
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
											<label><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerId none">${answer.answerId}</span><span class="answerTitle">${answer.title}</span></label>
										</c:when>
										<c:when test="${question.questionType eq 'A1'}">
											<label><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerTitle">${answer.title}</span>&nbsp;
											<c:if test="${empty answer.img}">
											<img src="<c:url value='/base/images/icon/ic_img_add.png' />" id="image${answer.answerId}"  alt="image"  name="fileuploadBtn" /> 
											</c:if>
											<c:if test="${!empty answer.img}">
											<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${answer.img}' />" id="image${answer.answerId}"  width="50" height="50" alt="image"  name="fileuploadBtn" /> 
											</c:if>
											</label>
											<script type="text/javascript">
											(function($) {
												$(document).ready(function() {
													$('#image${answer.answerId}').data("fileId",'${answer.img}');
												});
											})(jQuery);
											</script>
										</c:when>
										<c:when test="${question.questionType eq 'A2'}">
											<label><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><input name="" type="text" class="inputbox valign_top w80" title="" /></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'A3'}">
											<label><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><textarea name="" class="inputbox valign_top w80" title=""  rows="4"></textarea></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'A4'}">
											<label><input name="" type="checkbox" title="" class="checkbox valign_middle mr5" value="" /><span class="answerTitle">${answer.title}</span> </label>
										</c:when>
										<c:when test="${question.questionType eq 'A5'}">
											<label><input name="" type="checkbox" title="" class="checkbox valign_middle mr5" value="" /><span class="answerTitle">${answer.title}</span>&nbsp;
											<c:if test="${empty answer.img}">
											<img src="<c:url value='/base/images/icon/ic_img_add.png' />" id="image${answer.answerId}"  alt="image"  name="fileuploadBtn" /> 
											</c:if>
											<c:if test="${!empty answer.img}">
											<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${answer.img}' />" id="image${answer.answerId}"  width="50" height="50" alt="image"  name="fileuploadBtn" /> 
											</c:if>
											</label>
											<script type="text/javascript">
											(function($) {
												$(document).ready(function() {
													$('#image${answer.answerId}').data("fileId",'${answer.img}');
												});
											})(jQuery);
											</script>
										</c:when>
										<c:when test="${question.questionType eq 'A6'}">
											<label><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><input name="" type="text" class="inputbox  valign_top w80" title="" /></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'A7'}">
											<label><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle">${answer.title}</span> 
											<c:if test="${loopStatus.last}"><textarea name="" class="inputbox valign_top w80" title=""  rows="4"></textarea></c:if> 
											</label>
										</c:when>
										<c:when test="${question.questionType eq 'B0'}">
											<input name="" type="text" class="inputbox  valign_top" title="" size="50" /><div class="answerTitle"  style="display:none;"></div>
										</c:when>
										<c:when test="${question.questionType eq 'B1'}">
											</li><li  class="bg_none"><span style="display:inline-block; width:100px;" class="answerTitle">${answer.title}</span><input name="" type="text" class="inputbox  valign_top" title="" size="30" />
										</c:when>
										<c:when test="${question.questionType eq 'B2'}">
											</li><li  class="bg_none"><input name="" type="text" class="inputbox valign_top"  title="" size="30" /><div class="answerTitle"  style="display:none;"></div> 
										</c:when>
										<c:when test="${question.questionType eq 'B3'}">
											<textarea name="" class="inputbox valign_top w80"  title=""  rows="4"></textarea><div class="answerTitle"  style="display:none;"></div>
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
									<th colspan="2" scope="col"><div class="questionTitle">${question.title}</div></th>
									</tr>
									</thead>
									<tbody class="answerBlock">
										<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
											<tr>
											<td width="*"><span class="answerTitle">${answer.title}</span></td>
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
											<th width="*" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<th width="*" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<th width="*" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<th width="*" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<th width="*" rowspan="2" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<th width="*" rowspan="2" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<th width="*" scope="col"><div class="questionTitle">${question.title}</div></th>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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
											<td><span class="answerTitle">${answer.title}</span></td>
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

				
					</div>
					<!--// 설문 문항 End -->

					</c:forEach>

					<!--blockButton_3 Start-->


			</div>

			</div>
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<li><a class="button" onclick="save()" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		<li><a id="cancelButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='cancel'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
	 </ul>
</div>
<!--//blockButton End-->  
		</div>
		<!--//surveyBlock_l End-->

		<%@ include file="/WEB-INF/view/servicepack/survey/question/questionlayer.jsp"%>
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


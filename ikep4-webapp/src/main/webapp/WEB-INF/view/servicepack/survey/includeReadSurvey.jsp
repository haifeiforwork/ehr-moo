<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<script type="text/javascript">
<!--
(function($) {
	
	editLayerView= function (imgObj){
		//alert($(imgObj).attr("ATITLE"));
		var oldAnswerTitle=$(imgObj).attr("ATITLE");
		var oldQuestionTitle= $(imgObj).attr("QTITLE");
		var targeAnswerId = $(imgObj).attr("AID");
		var targeQuestionId = $(imgObj).attr("QID");
		


	

		
		
		var $dialog = $("#answerTitleEditDiv").dialog({
			title : "텍스트 수정",
			modal : true,
			resizable : false,
			close : function() {
				$dialog.dialog("destroy").hide()
					.find("a.button").unbind("click");
			}
		});
		

		if(targeAnswerId){
			$(":hidden", $dialog).val(targeAnswerId);
			$(":text", $dialog).val(oldAnswerTitle);
		}else if(targeQuestionId){
			$(":hidden", $dialog).val(targeQuestionId);
			$(":text", $dialog).val(oldQuestionTitle);
		}
		$dialog.find("a.button").click(function() {
			var params = {
					title: "",
					targeAnswerId: "",
					targeQuestionId: ""
				};

			if($(this).hasClass("ok")) {
				
				var title = $(":text", $dialog).val();
				var targetId = $(":hidden", $dialog).val();
				
				params.title =  title;
				if(targeAnswerId){
					params.targeAnswerId= targetId;
				}else if(targeQuestionId){
					params.targeQuestionId= targetId;
				}

				$.postJSON("<c:url value='/servicepack/survey/question/updateAnswerTitle.do'/>", params, function(data) {
					if(data.success=="success"){
						alert("저장 되었습니다.");
						parent.mainFrame.location.reload();
					}else{
						alert("저장 실패했습니다. 관리자에게 문의하세요.");
					}
					
				});
				
			}
			$dialog.dialog("close");
		});
	};
	
})(jQuery); 
//-->
</script>

<div id="answerTitleEditDiv" class="none" >
	<input type='text' class="inputbox" style="width:97%;"  value="" />
	<input type='hidden' value="" />
	<div class="blockButton" style="margin-top:7px;"> 
		<ul>
			<li><a id="btn_update_ok" href="#a" class="button ok"><span>저장</span></a></li>
			<li><a id="btn_update_cancel" href="#a" class="button"><span>취소</span></a></li>
		</ul>
	</div>
</div>

<c:if test="${ survey.surveyStatus eq 2 }">
	<div class="blockDetail">
		<table  summary="<ikep4j:message pre="${preList}" key="summary" />">
			<caption></caption>
			<tbody> 
				<tr> 
					<th scope="row" width="15%;"><ikep4j:message  key='message.servicepack.survey.oppositionReason' /></th>
					<td>${survey.oppositionReason}
					</td>	
				</tr>
			</tbody> 
		</table>
	</div>
</c:if>			
	<div class="survey_preview_t">				
		<h3>${survey.title}</h3>
		<p><c:choose>
			<c:when test="${survey.contentsType eq 0 }">
				<c:set var="contents1" value="${fn:replace(survey.contents, crlf, '<br/>')}" />
				${fn:replace(contents1, " ", "&nbsp;")}
			</c:when>
			<c:otherwise>
			<img name="profilePictureImage"
		     src="<c:url value='/support/fileupload/downloadFile.do?fileId=${fileId}' />" alt="<ikep4j:message pre='${preList}' key='contentsImage' />" />
			</c:otherwise>
		</c:choose>
	  </p>	
	</div>
	
	<c:set var="questionSeqGen" value="0"/>
	<c:forEach var="questionGroup" items="${questionGroupList}" varStatus="gloopStatus">
	<div class="survey_preview_b">
		<h4>${gloopStatus.index+1}. ${questionGroup.title}</h4>
		<p class="substance">${fn:replace(questionGroup.contents,crlf,"<br/>")}</p>
		<c:forEach var="question" items="${questionGroup.questionList}" varStatus="qloopStatus">
			<c:set var="required" value=""/>
			<c:choose>
				<c:when test="${question.requiredAnswer eq '0'}"><c:set var="required" value="required"/></c:when>
				<c:otherwise><c:set var="required" value=""/></c:otherwise>
			</c:choose>
			<div class="surveyList" QID="${question.questionId}">
			<c:choose>
				<c:when test="${fn:indexOf(question.questionType,'A')>=0}">
					<p class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if></p>
					<input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
					<ul class="${required}"  title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
						<c:if test="${!empty question.answer}"><li></c:if>
						<c:set var="loopCnt" value="0"/>
						<c:set var="questionSeqGen" value="${questionSeqGen+1}"/> <!-- A 타입일겨우에는 처음 시작 한번만 ++ -->
						<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
							<c:choose>
								<c:when test="${question.displayType eq '0' and loopStatus.index > 0}">
									</li>
									<li>
								</c:when>
								<c:when test="${question.displayType eq '2' and loopStatus.index > 0}">
									<c:if test="${loopCnt eq question.columnCount}">
										</li>
											<c:set var="loopCnt" value="0"/>
										<li>
									</c:if>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${question.questionType eq 'A0'}">
								<label>
									<input name="q${questionSeqGen}" type="radio" title="" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span>
									<c:if test="${survey.surveyStatus ne '4' }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if>
								</label>
								</c:when>
								<c:when test="${question.questionType eq 'A1'}">
								<label>
									<input name="q${questionSeqGen}" type="radio" title="" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span>&nbsp;<c:if test="${!empty answer.img && answer.img!=null}"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${answer.img}' />"  width="50" height="50" alt="image"  name="fileuploadBtn"/></c:if>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if>
								</label>
								</c:when>
								<c:when test="${question.questionType eq 'A2'}">
								<label>
									<input name="q${questionSeqGen}" type="radio" title="" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span>
									<c:if test="${loopStatus.last}"><input id="q${questionSeqGen}" name="${answer.answerId}" type="text" class="inputbox valign_top" title="${answer.title}" size="80" /></c:if>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if> 
								</label>	
								</c:when>
								<c:when test="${question.questionType eq 'A3'}">
								<label>
									<input name="q${questionSeqGen}" type="radio" title="" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span> 
									<c:if test="${loopStatus.last}"><textarea id="q${questionSeqGen}" name="${answer.answerId}" class="inputbox valign_top" title="" cols="80" rows="4"></textarea></c:if>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if> 
								</label>	
								</c:when>
								<c:when test="${question.questionType eq 'A4'}">
								<label>
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/> <!-- A 타입일겨우에는 처음 시작 한번만 ++ -->
									<input name="q${questionSeqGen}" type="checkbox" title="" class="checkbox valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if> 
								</label>
								</c:when>
								<c:when test="${question.questionType eq 'A5'}">
								<label>
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/> <!-- A 타입일겨우에는 처음 시작 한번만 ++ -->
									<input name="q${questionSeqGen}" type="checkbox" title="" class="checkbox valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span>&nbsp;<c:if test="${!empty answer.img && answer.img!=null}"><img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${answer.img}' />"  width="50" height="50" alt="image"  name="fileuploadBtn"/></c:if>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if>
								</label>
								</c:when>
								<c:when test="${question.questionType eq 'A6'}">
								<label>
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/> <!-- A 타입일겨우에는 처음 시작 한번만 ++ -->
									<input name="q${questionSeqGen}" type="checkbox" title="" class="checkbox valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span> 
									<c:if test="${loopStatus.last}"><input id="q${questionSeqGen}" name="${answer.answerId}" type="text" class="inputbox valign_top" title="" size="80" /></c:if>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if> 
									</label>
								</c:when>
								<c:when test="${question.questionType eq 'A7'}">
								<label>
								    <c:set var="questionSeqGen" value="${questionSeqGen+1}"/> <!-- A 타입일겨우에는 처음 시작 한번만 ++ -->
									<input name="q${questionSeqGen}" type="checkbox" title="" class="checkbox valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}" joinQ="${answer.joinQuestionIds}" />
									<span class="answerTitle">${answer.title}</span> 
									<c:if test="${loopStatus.last}"><textarea id="q${questionSeqGen}" name="${answer.answerId}" class="inputbox valign_top" title="" cols="80" rows="4"></textarea></c:if>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if> 
									</label>
								</c:when>
							</c:choose>		
							<c:set var="loopCnt" value="${loopCnt+1}"/>
						</c:forEach>
						<c:if test="${!empty question.answer}"></li></c:if>
					</ul>
				</c:when>
				<c:when test="${fn:indexOf(question.questionType,'B')>=0 }">
					<p class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if></p>
					<input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
					<ul class="${required}" title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
						<c:if test="${!empty question.answer}"><li></c:if>
						<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
							<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- B 타입일경우에는 questionSeqGen ++ -->
							<c:choose>
								<c:when test="${question.questionType eq 'B0'}">
								  <label>
									<input type="hidden" name="q${questionSeqGen}" value="${question.questionType}|${question.questionId}|${answer.answerId}" title="questionType"/>
									<input name="${answer.answerId}" type="text" class="inputbox valign_top"  title="" size="50" /><span class="answerTitle"></span>
								  </label>
								</c:when>
								<c:when test="${question.questionType eq 'B1'}">
									</li><li>
									<label>
									<span style="display:inline-block; width:300px;" class="answerTitle">${answer.title}</span>
									<input type="hidden" name="q${questionSeqGen}" value="${question.questionType}|${question.questionId}|${answer.answerId}" title="questionType"/>
									<input name="${answer.answerId}" type="text" class="inputbox valign_top"  title="" size="30" />
									</label>
								</c:when>
								<c:when test="${question.questionType eq 'B2'}">
									</li><li>
									<label>
									<input type="hidden" name="q${questionSeqGen}" value="${question.questionType}|${question.questionId}|${answer.answerId}" title="questionType"/>
									<input name="${answer.answerId}" type="text" class="inputbox valign_top"  title="" size="30" />
									<span class="answerTitle"></span>
									</label> 
								</c:when>
								<c:when test="${question.questionType eq 'B3'}">
									<label>
									<input type="hidden" name="q${questionSeqGen}" value="${question.questionType}|${question.questionId}|${answer.answerId}" title="questionType"/>
									<textarea name="${answer.answerId}" class="inputbox valign_top"  title="" cols="100" rows="4"></textarea>
									<span class="answerTitle"></span>
									</label>
								</c:when>
							</c:choose>		
						</c:forEach>
						<c:if test="${!empty question.answer}"></li></c:if>
					</ul>
				</c:when>
				<c:when test="${fn:indexOf(question.questionType,'C')>=0}">
					<div class="blockDetail">
						<table summary=""  style="table-layout:auto;">
							<caption></caption>
							<thead class="answerBlockHeader">
								<tr>
									<th colspan="2" scope="col">
									<span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
									<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
									</th>
								</tr>
							</thead>
							<tbody class="answerBlock">
							  <c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
							    <c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
								<tr>
									<td width="*"><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if></td>
									<td width="20px;" class="textCenter${required}"  title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
									    <input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
									    <input type="hidden" name="q${questionSeqGen}" value="${question.questionType}|${question.questionId}|${answer.answerId}" title="questionType"/>
										<select name="${answer.answerId}" title="">
										  <option value=""><ikep4j:message key='ui.servicepack.survey.common.selected' /></option>	
										  <c:forEach var="answer2" items="${question.answer}" varStatus="aloopStatus">
											<option value="${aloopStatus.index+1}">${aloopStatus.index+1}</option>
										  </c:forEach>
										</select>														
									</td>
								</tr>
							  </c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:when test="${fn:indexOf(question.questionType,'D')>=0}">
					<div class="blockDetail">
						<table summary=""  style="table-layout:auto;">
							<caption></caption>
							<thead class="answerBlockHeader">
							<c:choose>
								<c:when test="${question.questionType eq 'D0'}">
								   <tr>	
										<th width="*" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="135" scope="col">
											<ul class="surveyList_num">
											<li style="width:45px"><span class="scale1Title">${question.scale1}</span><div>1</div></li>
											<li style="width:45px"><span class="scale2Title">${question.scale2}</span><div>2</div></li>
											<li style="width:45px"><span class="scale3Title">${question.scale3}</span><div>3</div></li>
											</ul>
										</th>
									</tr>
								</c:when>
								<c:when test="${question.questionType eq 'D1'}">
									<tr>
										<th width="*" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="145" scope="col">
											<ul class="surveyList_num">
												<li><span class="scale1Title">${question.scale1}</span><div>1</div></li>
												<li>&nbsp;<div>2</div></li>
												<li><span class="scale2Title">${question.scale2}</span><div>3</div></li>
												<li>&nbsp;<div>4</div></li>
												<li><span class="scale3Title">${question.scale3}</span><div>5</div></li>
											</ul>
										</th>
									</tr>
								</c:when>
								<c:when test="${question.questionType eq 'D2'}">
									<tr>
										<th width="*" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="172" scope="col">
											<ul class="surveyList_num">
												<li><span class="scale1Title">${question.scale1}</span><div>1</div></li>
												<li>&nbsp;<div>2</div></li>
												<li>&nbsp;<div>3</div></li>
												<li>&nbsp;<div>4</div></li>
												<li>&nbsp;<div>5</div></li>
												<li><span class="scale3Title">${question.scale3}</span><div>6</div></li>
											</ul>
										</th>
									</tr>
								</c:when>
								<c:when test="${question.questionType eq 'D3'}">
									<tr>
										<th width="*" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="182" scope="col">
											<ul class="surveyList_num">
												<li style="width:25px;"><span class="scale1Title">${question.scale1}</span><div>1</div></li>
												<li style="width:25px;">&nbsp;<div>2</div></li>
												<li style="width:25px;">&nbsp;<div>3</div></li>
												<li style="width:25px;"><span class="scale2Title">${question.scale2}</span><div>4</div></li>
												<li style="width:25px;">&nbsp;<div>5</div></li>
												<li style="width:25px;">&nbsp;<div>6</div></li>
												<li style="width:25px;"><span class="scale3Title">${question.scale3}</span><div>7</div></li>
											</ul>
										</th>
									</tr>
								</c:when>
								<c:when test="${question.questionType eq 'D4'}">
									<tr>
										<th width="*" rowspan="2" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="50px;" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
										<th width="145" rowspan="2" scope="col">
											<ul class="surveyList_num">
												<li><span class="scale1Title">${question.scale1}</span><div>1</div></li>
												<li>&nbsp;<div>2</div></li>
												<li><span class="scale2Title">${question.scale2}</span><div>3</div></li>
												<li>&nbsp;<div>4</div></li>
												<li><span class="scale3Title">${question.scale3}</span><div>5</div></li>
											</ul>
										</th>
									</tr>
									<tr>
										<th scope="col" style="width:20px;">O</th>
										<th scope="col"  style="width:20px;">X</th>
									</tr>
								</c:when>
								<c:when test="${question.questionType eq 'D5'}">
									<tr>
										<th width="*" rowspan="2" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="50px;" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
										<th width="182" rowspan="2" scope="col">
											<ul class="surveyList_num">
												<li style="width:25px;"><span class="scale1Title">${question.scale1}</span><div>1</div></li>
												<li style="width:25px;">&nbsp;<div>2</div></li>
												<li style="width:25px;">&nbsp;<div>3</div></li>
												<li style="width:25px;"><span class="scale2Title">${question.scale2}</span><div>4</div></li>
												<li style="width:25px;">&nbsp;<div>5</div></li>
												<li style="width:25px;">&nbsp;<div>6</div></li>
												<li style="width:25px;"><span class="scale3Title">${question.scale3}</span><div>7</div></li>
											</ul>
										</th>
									</tr>
									<tr>
										<th scope="col"  style="width:20px;">O</th>
										<th scope="col"  style="width:20px;">X</th>
									</tr>
								</c:when>
								
								<c:when test="${question.questionType eq 'D6'}">
									<tr>
										<th width="*" scope="col"><span class="questionTitle">${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}</span>
										<c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" QID="${question.questionId}" QTITLE="${question.title}" onclick="editLayerView(this)"/></c:if>
										</th>
										<th width="20" scope="col">N/A</th>
										<th width="182" scope="col">
											<ul class="surveyList_num">
												<li style="width:25px;"><span class="scale1Title">${question.scale1}</span><div>1</div></li>
												<li style="width:25px;">&nbsp;<div>2</div></li>
												<li style="width:25px;">&nbsp;<div>3</div></li>
												<li style="width:25px;"><span class="scale2Title">${question.scale2}</span><div>4</div></li>
												<li style="width:25px;">&nbsp;<div>5</div></li>
												<li style="width:25px;">&nbsp;<div>6</div></li>
												<li style="width:25px;"><span class="scale3Title">${question.scale3}</span><div>7</div></li>
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
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
									<tr>
										<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if></td>
										<td class="textCenter${required}"  title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
										    <input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
											<ul class="surveyList_num">
												<li style="width:45px"><input name="q${questionSeqGen}" type="radio" title="하" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></li>
												<li style="width:45px"><input name="q${questionSeqGen}" type="radio" title="중" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></li>
												<li style="width:45px"><input name="q${questionSeqGen}" type="radio" title="상" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|2" /></li>
											</ul>											
										</td>
									</tr>
								  </c:forEach>
								</c:when>
								<c:when test="${question.questionType eq 'D1'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
										<tr>
											<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if></td>
											<td class="textCenter${required}"  title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
											    <input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
												<ul class="surveyList_num">
													<li><input name="q${questionSeqGen}" type="radio" title="1" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="2" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="3" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|2" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="4" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|3" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="5" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|4" /></li>
												</ul>											
											</td>
										</tr>
									  </c:forEach>
								</c:when>
								<c:when test="${question.questionType eq 'D2'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
										<tr>
											<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if></td>
											<td class="textCenter${required}" title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
											    <input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
												<ul class="surveyList_num">
													<li><input name="q${questionSeqGen}" type="radio" title="1" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="2" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="3" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|2" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="4" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|3" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="5" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|4" /></li>
													<li><input name="q${questionSeqGen}" type="radio" title="6" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|5" /></li>
												</ul>											
											</td>
										</tr>
									  </c:forEach>
								</c:when>
								<c:when test="${question.questionType eq 'D3'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
										<tr>
											<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if></td>
											<td class="textCenter${required}" title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
											    <input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/>
												<ul class="surveyList_num">
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="1" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="2" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="3" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|2" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="4" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|3" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="5" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|4" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="6" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|5" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="7" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|6" /></li>
												</ul>											
											</td>
										</tr>
									  </c:forEach>
								</c:when>
								<c:when test="${question.questionType eq 'D4'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
										<tr class="${required}"  title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
											<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if><input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/></td>
											<td class="textCenter"><input name="q${questionSeqGen}" type="radio" title="O" class="radio O" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></td>
											<td class="textCenter"><input name="q${questionSeqGen}" type="radio" title="X" class="radio X" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></td>
											<td class="textCenter">
												<ul class="surveyList_num">
													<li><input name="${answer.answerId}" type="radio" title="1" class="radio valign_middle" value="0" /></li>
													<li><input name="${answer.answerId}" type="radio" title="2" class="radio valign_middle" value="1" /></li>
													<li><input name="${answer.answerId}" type="radio" title="3" class="radio valign_middle" value="2" /></li>
													<li><input name="${answer.answerId}" type="radio" title="4" class="radio valign_middle" value="3" /></li>
													<li><input name="${answer.answerId}" type="radio" title="5" class="radio valign_middle" value="4" /></li>
												</ul>											
											</td>
										</tr>
									  </c:forEach>
								</c:when>
								<c:when test="${question.questionType eq 'D5'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
										<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
										<tr class="${required}" title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
											<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if><input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/></td>
											<td class="textCenter"><input name="q${questionSeqGen}" type="radio" title="O" class="radio O" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></td>
											<td class="textCenter"><input name="q${questionSeqGen}" type="radio" title="X" class="radio X" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></td>
											<td class="textCenter">
												<ul class="surveyList_num">
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="1" class="radio valign_middle" value="0" /></li>
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="2" class="radio valign_middle" value="1" /></li>
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="3" class="radio valign_middle" value="2" /></li>
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="4" class="radio valign_middle" value="3" /></li>
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="5" class="radio valign_middle" value="4" /></li>
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="6" class="radio valign_middle" value="5" /></li>
													<li style="width:25px;"><input name="${answer.answerId}" type="radio" title="7" class="radio valign_middle" value="6" /></li>
												</ul>											
											</td>
										</tr>
									  </c:forEach>
								</c:when>
								
								<c:when test="${question.questionType eq 'D6'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
										<tr class="${required}"  title="${gloopStatus.index+1}-${qloopStatus.index+1}. ${question.title}">
											<td><span class="answerTitle">${answer.title}</span><c:if test="${ survey.surveyStatus ne 4 }"> <img id="titleEditButton" align="top" src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="Edit"  border="0" AID="${answer.answerId}" ATITLE="${answer.title}" onclick="editLayerView(this)"/></c:if><input type="hidden" name="data-questionType" value="${question.questionType}" title="questionType"/></td>
											<td class="textCenter"><input name="q${questionSeqGen}" type="radio" title="N/A" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|N/A" /></td>
											<td class="textCenter">
												<ul class="surveyList_num">
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="1" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|0" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="2" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|1" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="3" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|2" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="4" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|3" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="5" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|4" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="6" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|5" /></li>
													<li style="width:25px;"><input name="q${questionSeqGen}" type="radio" title="7" class="radio valign_middle" value="${question.questionType}|${question.questionId}|${answer.answerId}|6" /></li>
												</ul>											
											</td>
										</tr>
									  </c:forEach>
								</c:when>
								<c:when test="${question.questionType eq 'D7'}">
									<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
									<c:set var="questionSeqGen" value="${questionSeqGen+1}"/><!-- C 타입일경우에는 questionSeqGen ++ -->
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
						<c:choose>
						<c:when test="${question.questionType eq 'D4' || question.questionType eq 'D5'}">
							<div class="" style="margin-top:10px;">
							<p class="textRight"><ikep4j:message  key='message.servicepack.survey.message' /></p>
							</div>
						</c:when>
						</c:choose>							
					</div>
				</c:when>	
				<c:otherwise>
					<div class="blockDetail">
						<table summary="">
							<caption></caption>
							<thead class="answerBlockHeader"></thead>
							<tbody class="answerBlock"></tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
			</div>	
		</c:forEach>
		</div>
	</c:forEach>

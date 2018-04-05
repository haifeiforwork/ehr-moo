<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>

<script type="text/javascript">
var dialogWindow = null;
(function($) {
	fnCaller = function (params, dialog) {
		if(params) {
		}
		dialogWindow = dialog;
		
		$("#btnClose").click(function() {
			dialogWindow.close();
		});
	};
	
	sameQuestionDisable= function(obj){
		//alert($jq(obj).attr("checked"));
		var checkValue=true;
		var checkValueQuestionId = $jq(obj).val().split("_")[1];
		//alert(checkValueQuestionId);
		if($jq(obj).attr("checked")=="checked"){
			checkValue=true;
		}else{
			checkValue=false;
		}
		$jq.each($jq(":checkbox"), function() {

			if($jq(this).attr("name")==$jq(obj).attr("name")){
	
				$jq(this).attr("disabled", checkValue);
			}
			if($jq(this).attr("question_id")==checkValueQuestionId){
				
				$jq(this).attr("disabled", checkValue);
			}
		});
		$jq(obj).attr("disabled", false);

		
	};

	
	$jq(document).ready(function() { 
		$jq("#joinSaveButton").click(function() {
	 		if( confirm("연결설정을 저장하시겠습니까?") )
	 		{
	 			//$jq("#joinQuestionForm").submit();
				$jq("#joinQuestionForm").ajaxLoadStart();
				$.post("<c:url value='/servicepack/survey/question/joinQuestion.do'/>",  $("#joinQuestionForm").serialize())
				.success(function(result) {
					alert("저장 완료되었습니다.");
					//parent.mainFrame.joinRefresh();
					dialogWindow.close();
				})
				.error(function(){
					alert("저장 실패했습니다. 관리자에게 문의하세요.");
					dialogWindow.close();
				});
	 		}
	 	});
	});  
})(jQuery); 
//-->
</script>

<table style="width:755px">
<tr>
<td width="200px" style="vertical-align:top">
<c:forEach var="question" items="${joinQuestionList}" varStatus="qloopStatus">
	<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
		<c:if test="${empty answer.answerId}" >
			<c:if test="${!loopStatus.first}">
			</ul>
			</div>
			</div>	
			</c:if>
			<div class="survey_preview_b" >
			<div class="surveyList" style="overflow-x:scroll;white-space:nowrap;">
			<p>${answer.questionGroupSeq}-${answer.questionSeq}. ${ikep4j:cutString(answer.title,15,"..")} </p>
			<ul>	
		</c:if>
		<c:if test="${!empty answer.answerId}" >	
			<li><span class="answerTitle">${ikep4j:cutString(answer.title,15,"..")}</span></li>
		</c:if>
		<c:if test="${loopStatus.last}">
			</ul>
			</div>
			</div>
		</c:if>
	</c:forEach>
</c:forEach>
</td>
<td width="555px">

					<form id="joinQuestionForm" method="post">
						<input type="hidden" name="surveyId" value="${param.surveyId}"/>
						<c:forEach var="question" items="${joinQuestionList}" varStatus="qloopStatus">
							<c:forEach var="answer" items="${question.answer}" varStatus="loopStatus">
								<c:if test="${empty answer.answerId}" >
									<c:if test="${!loopStatus.first}">
									</ul>
									</div>
									</div>	
									</c:if>
									<div class="survey_preview_b" >
									<div class="surveyList" style="width:510px;overflow-x:scroll;white-space:nowrap;">
									<p>문항 [${answer.questionGroupSeq}-${answer.questionSeq}]의 각 답변에 연결할 문항을 선택하십시요. </p>
									<ul>	
								</c:if>
								<c:if test="${!empty answer.answerId}" >	
									<li><span class="answerTitle">
									<c:set var="qseqNum" value="0"/>
									<c:forEach var="allquestion" items="${questionList}" varStatus="aloopStatus">
										<c:if test="${allquestion.questionSeq eq '1'}">
											<c:set var="qseqNum" value="${qseqNum+1}"/>
										</c:if>
										<input 
											type="checkbox" 
											question_id="${answer.questionId}" 
											name="${allquestion.questionId}" 
											onclick="sameQuestionDisable(this)" 
											value="${answer.answerId}_${allquestion.questionId}" 
											class="checkbox valign_middle" 
											<c:if test="${fn:contains( answer.joinQuestionIds, allquestion.questionId)}">checked</c:if>
											<c:if test="${allquestion.questionId eq answer.questionId}">disabled</c:if> 
										>
										&nbsp;${qseqNum}-${allquestion.questionSeq} ${ikep4j:cutString(allquestion.title,6,"..")}&nbsp;&nbsp;
									</c:forEach>
									</span></li>
								</c:if>
								<c:if test="${loopStatus.last}">
									</ul>
									</div>
									</div>
								</c:if>
							</c:forEach>
						</c:forEach>
					</form>

</td>
</tr>
</table>
<div class="blockButton"> 
		<ul>
			<li><a id="joinSaveButton" class="button"><span>연결문항 설정저장</span></a></li>
		</ul>
</div>

<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>

<head>
<%@ include file="/base/common/meta.jsp" %>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--
	var checkVar = false;

	$(document).ready(function(){
		$('#checkButton').click(function(){
			var idVal = $('#user_id').val();
			if(idVal==""){
				alert("ID를 입력하세요");
				return ;
			}
			checkVar = true;
			$.ajax({
				type:'POST',
				url:"<c:url value='/user/checkId.do'/>",
				dataType:'text',
				data:({ id:$('#user_id').val() }),
				error: 
					function(textStatus) {
					    alert('error:' + textStatus);
				    },
				success:
					function(data){
						if(data=='1') {
							alert('중복된 ID 입니다.');
							$('#user_id').val("");
						} else {
							alert('중복된 ID가 아닙니다.');
						}
					}
			});
		});
		
		// 등록
		$('#createButton').click(function(){
			if(checkVar==true) {
				$('form[name=userAddForm]').attr({
					action:"<c:url value='/user/createUser.do'/>"
				});
				$('form[name=userAddForm]').submit();
			} else {
				alert('ID 중복확인이 필요합니다.');
			}
		});
		
		$('#modifyButton').click(function(){
			$('form[name=userAddForm]').attr({
				action:"<c:url value='/user/createUser.do'/>"
			});
			$('form[name=userAddForm]').submit();
		});
		
		// 삭제
		$('#deleteButton').click(function(){
			$('form[name=userAddForm]').attr({
				action:"<c:url value='/user/deleteUser.do'/>"
			});
			$('form[name=userAddForm]').submit();
		});

	});
//-->
</script>

<title>사용자 등록/수정</title>
</head>

<body>
<form:form name="userAddForm" modelAttribute="user" commandName="user" method="post" action="createUser.do">

	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<th width="20%"><b>* ID</b></td>
			<c:choose>
				<c:when test="${empty user.user_id}">
					<td colspan="2" width="40%">
						&nbsp;<form:input for="user_id" path="user_id" />
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="user_id" cssClass="error"/></td>
				</c:when>
				<c:otherwise>
					<td colspan="2" width="40%">
						&nbsp;<form:input for="user_id" path="user_id" readonly="false"/>
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="user_id" cssClass="error"/></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<!--
		<tr>
			<th width="20%"><b>* 패스워드</b></td>
			<td colspan="2" width="40%">&nbsp;<form:password for="user_password" path="user_password" /></td>
			<td width="40%">&nbsp;<form:errors path="user_password" cssClass="error"/></td>
		</tr>
		-->
		<tr>
			<th width="20%"><b>* 이름</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="user_name" path="user_name" /></td>
			<td width="40%">&nbsp;<form:errors path="user_name" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>* 영문이름</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="user_english_name" path="user_english_name" /></td>
			<td width="40%">&nbsp;<form:errors path="user_english_name" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>사원번호</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="emp_no" path="emp_no" /></td>
			<td width="40%">&nbsp;<form:errors path="emp_no" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>이메일주소</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="mail" path="mail" /></td>
			<td width="40%">&nbsp;<form:errors path="mail" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>휴대전화</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="mobile" path="mobile" /></td>
			<td width="40%">&nbsp;<form:errors path="mobile" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>사무실전화</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="office_phone_no" path="office_phone_no" /></td>
			<td width="40%">&nbsp;<form:errors path="office_phone_no" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>집전화</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="home_phone_no" path="home_phone_no" /></td>
			<td width="40%">&nbsp;<form:errors path="home_phone_no" cssClass="error"/></td>
		</tr>
		<tr>
			<th rowspan="3" width="20%"><b>사무실 주소</b></td>
			<td>우편번호</td>
			<td>&nbsp;<form:input for="office_zipcode" path="office_zipcode" /></td>
			<td width="40%">&nbsp;<form:errors path="office_zipcode" cssClass="error"/></td>
		</tr>
		<tr>
			<td>주소 1</td>
			<td>&nbsp;<form:input for="office_basic_address" path="office_basic_address" /></td>
			<td width="40%">&nbsp;<form:errors path="office_basic_address" cssClass="error"/></td>
		</tr>
		<tr>
			<td>주소 2</td>
			<td>&nbsp;<form:input for="office_detail_address" path="office_detail_address" /></td>
			<td width="40%">&nbsp;<form:errors path="office_detail_address" cssClass="error"/></td>
		</tr>
		<tr>
			<th rowspan="3" width="20%"><b>집 주소</b></td>
			<td>우편번호</td>
			<td>&nbsp;<form:input for="home_zipcode" path="home_zipcode" /></td>
			<td width="40%">&nbsp;<form:errors path="home_zipcode" cssClass="error"/></td>
		</tr>
		<tr>
			<td>주소 1</td>
			<td>&nbsp;<form:input for="home_basic_address" path="home_basic_address" /></td>
			<td width="40%">&nbsp;<form:errors path="home_basic_address" cssClass="error"/></td>
		</tr>
		<tr>
			<td>주소 2</td>
			<td>&nbsp;<form:input for="home_detail_address" path="home_detail_address" /></td>
			<td width="40%">&nbsp;<form:errors path="home_detail_address" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>생일</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="birthday" path="birthday" /></td>
			<td width="40%">&nbsp;<form:errors path="birthday" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>결혼기념일</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="wedding_anniv" path="wedding_anniv" /></td>
			<td width="40%">&nbsp;<form:errors path="wedding_anniv" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>사진 ID</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="picture_id" path="picture_id" /></td>
			<td width="40%">&nbsp;<form:errors path="picture_id" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>프로필사진 ID</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="profile_picture_id" path="profile_picture_id" /></td>
			<td width="40%">&nbsp;<form:errors path="profile_picture_id" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>TIMEZONE</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="timezone_id" for="timezone_id" id="timezone_id" width="30">
					<c:forEach var="result" items="${timezoneList}" varStatus="loopStatus">
						<option value="<c:out value="${result.timezone_id}"/>" <c:if test="${result.timezone_id==user.timezone_id}">selected</c:if>>
							<c:out value="${result.timezone_name}"/>/<c:out value="${result.timezone_id}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="timezone_id" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>지역</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="location" path="location" /></td>
			<td width="40%">&nbsp;<form:errors path="location" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>직급상태</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="job_class_code" for="job_class_code" id="job_class_code" width="30">
					<c:forEach var="result" items="${jobClassList}" varStatus="loopStatus">
						<option value="<c:out value="${result.job_class_code}"/>" <c:if test="${result.job_class_code==user.job_class_code}">selected</c:if>>
							<c:out value="${result.job_class_name}"/>/<c:out value="${result.job_class_code}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="job_class_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>* 부서</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="team_name" for="team_name" id="team_name" width="30">
					<c:forEach var="result" items="${groupList}" varStatus="loopStatus">
						<option value="<c:out value="${result.group_name}"/>" <c:if test="${result.group_name==user.team_name}">selected</c:if>>
							<c:out value="${result.group_name}"/>/<c:out value="${result.group_id}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="team_name" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>* 팀장여부</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:radiobutton path="leader" for="leader" id="leader" value="1" />
					<label for="leader">예</label>
				<form:radiobutton path="leader" for="leader" id="nonLeader" value="0" />
					<label for="leader">아니오</label>
			</td>
			<td width="40%">&nbsp;<form:errors path="leader" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>* 상태</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:radiobutton path="user_status" for="user_status" id="current" value="C" />
					<label for="user_status">재직</label>
				<form:radiobutton path="user_status" for="user_status" id="vacant" value="H" />
					<label for="user_status">휴직</label>
				<form:radiobutton path="user_status" for="user_status" id="retired" value="T" />
					<label for="user_status">퇴직</label>
			</td>
			<td width="40%">&nbsp;<form:errors path="user_status" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>직급</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="job_rank_code" for="job_rank_code" id="job_rank_code" width="30">
					<c:forEach var="result" items="${jobRankList}" varStatus="loopStatus">
						<option value="<c:out value="${result.job_rank_code}"/>" <c:if test="${result.job_rank_code==user.job_rank_code}">selected</c:if>>
							<c:out value="${result.job_rank_name}"/>/<c:out value="${result.job_rank_code}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="job_rank_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>직책</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="job_duty_code" for="job_duty_code" id="job_duty_code" width="30">
					<c:forEach var="result" items="${jobDutyList}" varStatus="loopStatus">
						<option value="<c:out value="${result.job_duty_code}"/>" <c:if test="${result.job_duty_code==user.job_duty_code}">selected</c:if>>
							<c:out value="${result.job_duty_name}"/>/<c:out value="${result.job_duty_code}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="job_duty_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>직위</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="job_position_code" for="job_position_code" id="job_position_code" width="30">
					<c:forEach var="result" items="${jobPositionList}" varStatus="loopStatus">
						<option value="<c:out value="${result.job_position_code}"/>" <c:if test="${result.job_position_code==user.job_position_code}">selected</c:if>>
							<c:out value="${result.job_position_name}"/>/<c:out value="${result.job_position_code}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="job_position_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>호칭</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="job_title_code" for="job_title_code" id="job_title_code" width="30">
					<c:forEach var="result" items="${jobTitleList}" varStatus="loopStatus">
						<option value="<c:out value="${result.job_title_code}"/>" <c:if test="${result.job_title_code==user.job_title_code}">selected</c:if>>
							<c:out value="${result.job_title_name}"/>/<c:out value="${result.job_title_code}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="job_title_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%">&nbsp;<b>로케일</b></td>
			<td colspan="2" width="40%">&nbsp;
				<form:select path="locale_code" for="locale_code" id="locale_code" width="30">
					<c:forEach var="result" items="${localeList}" varStatus="loopStatus">
						<option value="<c:out value="${result.locale_code}"/>" <c:if test="${result.locale_code==user.locale_code}">selected</c:if>>
							<c:out value="${result.locale_name}"/>/<c:out value="${result.locale_code}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="locale_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>트위터 계정</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="twitter_account" path="twitter_account" /></td>
			<td width="40%">&nbsp;<form:errors path="twitter_account" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>트위터 인증코드</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="twitter_auth_code" path="twitter_auth_code" /></td>
			<td width="40%">&nbsp;<form:errors path="twitter_auth_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>페이스북 계정</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="facebook_account" path="facebook_account" /></td>
			<td width="40%">&nbsp;<form:errors path="facebook_account" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>페이스북 인증코드</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="facebook_auth_code" path="facebook_auth_code" /></td>
			<td width="40%">&nbsp;<form:errors path="facebook_auth_code" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>프로필 상태</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="profile_status" path="profile_status" /></td>
			<td width="40%">&nbsp;<form:errors path="profile_status" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>포탈ID</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="portal_id" path="portal_id" /></td>
			<td width="40%">&nbsp;<form:errors path="portal_id" cssClass="error"/></td>
		</tr>
		<tr>
			<th width="20%"><b>전문분야</b></td>
			<td colspan="2" width="40%">&nbsp;<form:input for="expert_field" path="expert_field" /></td>
			<td width="40%">&nbsp;<form:errors path="expert_field" cssClass="error"/></td>
		</tr>
	</table>

</form:form>
	<p>
	<!--버튼 [S]-->
	<div align="right">
		<c:choose>
			<c:when test="${empty user.user_id}">
				<!-- <input type="submit" value="등록"> -->
				<input type="button" id="createButton" name="createButton" value="등록">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listUser.do'">
			</c:when>
			<c:otherwise>
				<!-- <input type="submit" value="수정"> -->
				<input type="button" id="modifyButton" name="modifyButton" value="수정">
				<input type="button" id="deleteButton" name="deleteButton" value="삭제">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listUser.do'">		
			</c:otherwise>
		</c:choose>
	</div>
	<!--//버튼 [E]-->
	
</body>
</html>
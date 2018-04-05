<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preTitle"    	value="ui.approval.userauthmgnt.list.title" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	var validator;
	var viewMode = "${searchCondition.viewMode}";
	(function($){
		var dblClick=false;
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		setUser = function(data) {
			
			$jq.each(data, function() {
				
				var empNo = $jq.trim(this.empNo);
				var userName = $jq.trim(this.userName);
				var group = $jq.trim(this.group);
				var teamName = $jq.trim(this.teamName);
				
				alert( "선택한 임직원( "+ userName + " )으로 선택이 완료되었습니다.");
				$jq("#empNo").val(empNo);
				$jq("#userName").val( userName);
				$jq("#deptId").val(group);
				$jq("#groupName").val(teamName);
			});
		};
		
		$(document).ready(function(){
			
			$("#editForm").find("select[name='workGbnCd']").change(function(){
				var optionVal = $(this).val() || "";
				
				if( optionVal == "") {
					
					$("#editForm").find("select[name='apprYn']").val("");
					$("#editForm").find("select[name='rsltFileReadYn']").val("")
				} else if( optionVal == '10') {
					
					$("#editForm").find("select[name='apprYn']").attr("disabled", false);
					$("#editForm").find("select[name='rsltFileReadYn']").attr("disabled", false);
				}else{
					
					$("#editForm").find("select[name='apprYn']").val("N").attr("disabled", true);
					$("#editForm").find("select[name='rsltFileReadYn']").val("N").attr("disabled", true);
				}
			});
			
			$("#editForm").find("select[name='workGbnCd']").trigger("change");
			
			// 임직원 선택 제거시 알림 메시지
			$(".searchUserName").change(function(){
		    	var val = $(this).val();
		    	var idObj = $(this).parent("td").find(".searchUserId");
		    	var idval = idObj.val() || "";
		    	
		    	if( val == '' && idval) {
		    		alert("선택된 임직원이 삭제되었습니다.");
		    		idObj.val("");
		    	}
		    });
			
			// 저장
			$("#saveButton").click(function() {
				
				$("#editForm").submit();
				return false; 
			});
			
			// viewMode에 따른 UI변경
			
			if( viewMode == "modify") {
				
				$("#popupCreate").hide();
				$("#popupUpdate").show();
				$jq('#addrSearchBtn').hide();
				$('#addrBtn').hide();
				$jq('#btnDeleteControl').hide();
				$jq("#editForm").find("select[name='workGbnCd']").hide();
				var workGbnCdText = $jq("#editForm").find("select[name='workGbnCd'] option:selected").text();
				$jq("#workGbnCdText").html(workGbnCdText);
			}else{
				
				$("#popupCreate").show();
				$("#popupUpdate").hide();
				$("input[name='userName']").attr("readonly", false);
				
				// [검색-직원선택] ================== Start
				$jq('#addrSearchBtn').click( function() {
				    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
					$jq('#userName').trigger("keypress");
				});
				
				$('#addrBtn').click( function() {
					
					//파라미터(콜백함수, 전송파라미터, 팝업옵션)
					iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
				});
				
				$jq('#userName').keypress( function(event) {
		            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
					iKEP.searchUser(event, "N", setUser);			
				});
				
				$jq('#btnDeleteControl').click( function() {
					
				    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
					$jq('#empNo').val('');
					$jq('#userName').val('');
				});
				// [검색-직원선택]  ================== End
			}
			
			// [validation] ============================================= Start			
			var validOptions = {
				rules			: {
			  		workGbnCd			: {required:true},
			  		rsltFileReadYn		: {required:true},
			  		empNo		: {required:true},
			  		deptId		: {required:true},
			  		apprYn		: {required:true},
			  		useYn		: {required:true}
			  	},
			  	messages		: {
			  		workGbnCd : {
			  			required: "<ikep4j:message pre='${preTitle}' key='workGbnCd'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    },
				    rsltFileReadYn : {
			  			required: "<ikep4j:message pre='${preTitle}' key='rsltFileReadYn'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    },
				    deptId : {
			  			required: "<ikep4j:message pre='${preTitle}' key='dept'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    },
				    empNo : {
			  			required: "<ikep4j:message pre='${preTitle}' key='user'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    },
				    apprYn : {
			  			required: "<ikep4j:message pre='${preTitle}' key='apprYn'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    },
				    useYn : {
			  			required: "<ikep4j:message pre='${preTitle}' key='useYn'/>" + " " + "<ikep4j:message pre='${preMessage2}' key='required'/>"
				    }
			  	},
			    submitHandler : saveUserAuthMgntFnc
			};
			validator = new iKEP.Validator("#editForm", validOptions);
			
		});
		
		// 사용자등록 save Fnc
		var saveUserAuthMgntFnc = function(form) {

			if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) {
				return;
			}
			
			// viewMode가 등록일때
			if ( viewMode == "create") {
				$jq.ajax({
					url : iKEP.getContextRoot() + "/approval/collaboration/userauthmgnt/ajaxCreateUserAuthMgnt.do",
					type : "post",
					data : $jq(form).serialize(),
					loadingElement : "#saveButton",
					success : function(data, textStatus, jqXHR) {
						
						alert( "<ikep4j:message pre='${preMessage}' key='saveSuccess'/>");
						dialogWindow.callback();
						dialogWindow.close();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						
						var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			// vidwMode가 수정일때
			} else {
				
				$jq.ajax({
					url : iKEP.getContextRoot() + "/approval/collaboration/userauthmgnt/ajaxUpdateUserAuthMgnt.do",
					type : "post",
					data : $jq(form).serialize(),
					loadingElement : "#saveButton",
					success : function(data, textStatus, jqXHR) {
						
						alert( "<ikep4j:message pre='${preMessage}' key='saveSuccess'/>");
						dialogWindow.callback();
						dialogWindow.close();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						
						var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
				
			}
		};
		// [validation] ============================================= End
	
	})(jQuery);
	//-->
</script>
	<h1 class="none"><ikep4j:message pre='${preTitle}' key="popup.create" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2 id="popupCreate" style="display:none;"><ikep4j:message pre="${preTitle}" key="popup.create" /></h2> 
		<h2 id="popupUpdate" style="display:none;"><ikep4j:message pre="${preTitle}" key="popup.update" /></h2> 
	</div>
	<!--//pageTitle End-->
	
	<form id="editForm" name="editForm" method="post" action="">
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
				<caption></caption>
				<tbody>
					<tr>
						<!-- 업무구분 -->
						<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='workGbnCd' /></th>
	                    <td width="30%;">
							<spring:bind path="userAuthMgnt.workGbnCd">
								<select class="selectbox workGbnCd" name="${status.expression}" style="width:180px;">
									<option value=""><ikep4j:message pre='${preDefault}' key='option.choose'/></option>
									<c:forEach var="code" items="${ workGnbCdList}">
										<c:choose>
											<c:when test="${searchCondition.viewMode eq 'modify'}">
												<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${isEnableSystemAdm eq true}">
														<option value="${code.comCd}" >${code.comNm}</option>
													</c:when>
													<c:otherwise>
														<c:if test="${code.charCol1 eq user.groupId || code.charCol2 eq user.groupId}">
															<option value="${code.comCd}" >${code.comNm}</option>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 
							</spring:bind>
							<span id="workGbnCdText"></span>
	                    </td>
	                    <!-- 결과파일 읽기 권한 여부 -->
						<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='rsltFileReadYn' /></th>
	                    <td width="30%;">
							<spring:bind path="userAuthMgnt.rsltFileReadYn">
								<select class="selectbox" name="${status.expression}" style="width:180px;">
									<option value=""><ikep4j:message pre='${preDefault}' key='option.choose'/></option>
									<c:forEach var="code" items="${ ynList}">
										<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
									</c:forEach>
								</select> 
							</spring:bind>
	                    </td>
					</tr>
					<tr>
	                    <!-- 사용자 -->
						<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='user' /></th>
	                    <td width="30%;">
							<spring:bind path="userAuthMgnt.empNo">
		                    	<input id="${status.expression}" class="inputbox searchUserId" name="${status.expression}" type="hidden" value="${status.value}"  />
	                    	</spring:bind>
	                    	<spring:bind path="userAuthMgnt.userName">
		                    	<input id="${status.expression}" class="inputbox searchUserName" name="${status.expression}" type="text" readonly="readonly" value="${status.value}" style="width:180px;" />
	                    	</spring:bind>
							<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
							</a>
							<a id="addrBtn" href="#a" class="button_ic">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
							</a>
							<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
							
	                    </td>
	                    <!-- 부서 -->
						<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='dept' /></th>
	                    <td width="30%;">
							<spring:bind path="userAuthMgnt.deptId">
		                    	<input id="${status.expression}" name="${status.expression}" class="inputbox" type="hidden" value="${status.value}"  />
							</spring:bind>
							<spring:bind path="userAuthMgnt.groupName">
		                    	<input id="${status.expression}" name="${status.expression}" class="inputbox" type="text" readonly="readonly" value="${status.value}" style="width:180px;border: 0px;" />
							</spring:bind>
	                    </td>
					</tr>
					<tr>
	                    <!-- 승인자여부 -->
						<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='apprYn' />${userAuthMgnt.apprYn }</th>
	                    <td width="30%;">
							<spring:bind path="userAuthMgnt.apprYn">
								<select class="selectbox" name="${status.expression}"  style="width:180px;">
									<option value=""><ikep4j:message pre='${preDefault}' key='option.choose'/></option>
									<c:forEach var="code" items="${ ynList}">
										<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
									</c:forEach>
								</select> 
							</spring:bind>
	                    </td>
	                    <!-- 사용여부 -->
						<th scope="row" width="20%" style="text-align:center;"><ikep4j:message pre='${preTitle}' key='useYn' /></th>
	                    <td width="30%;">
							<spring:bind path="userAuthMgnt.useYn">
								<select class="selectbox" name="${status.expression}" style="width:180px;" >
									<option value=""><ikep4j:message pre='${preDefault}' key='option.choose'/></option>
									<c:forEach var="code" items="${ ynList}">
										<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
									</c:forEach>
								</select> 
							</spring:bind>
					</tr>
				</tbody>
			</table>
			
		</div>
		<!--//blockDetail End-->
		
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="saveButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
				<li><a id="cancelButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
		
	</form>	
<!--//blockListTable End-->	

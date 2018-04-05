<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>
<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>

<script type="text/javascript">
//<!--

var strRepresent = "[<ikep4j:message pre='${userUiPrefix}' key='lblRepresent'/>]";

(function($) {
	var isRep = false;
	
	setGroup = function(data) {
		var $select = $jq("#userGroupList");
		var selectCheck;
		
		if(data.length > 0) {
			$jq(data).each(function(index) {
				selectCheck = true;
				$jq.each($select.children(), function() {
					if(this.value == data[index].code)
						selectCheck = false;
				});				
				
			});
			
			updateCount();
		} else {
			alert("<ikep4j:message pre='${userListPrefix}' key='alert.noRetrievedGroup' />");
		}
	};
					
	//검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
	//result: 검색되거나 선택된 값을 배열형태로 리턴함
	setAddress = function(data) {
		var addStr="";
		$jq.each(data, function() {
			//addStr = addStr + "\""+$jq.trim(this.name)+"\" "+$jq.trim(this.email)+",";
			groupId = $jq.trim(this.code);
			addStr = $jq.trim(this.name);
			});
	};
	        
	prepareForm = function() {
		//역할의 이름을 현재 세션에 있는 유저의 Localecode값이 ID인 녀석의 Value로 매핑
		
		var groups = "";
		$jq("#userGroupList option").each(function (index) {
			groups = groups + "<input name=\"groupList["+ index +"].groupId\" value=\""+ $jq(this).val() + "\" type=\"hidden\"/>";
		});
		
		$jq("#userForm").append(groups);
	};
	
	updateCount = function() {
		$jq("#groupCount").html($jq("#userGroupList").children().length);
	};
	
	updateGroupCount = function(data) {
		setGroup(data);
		
    	var $sel = $jq("#userGroupList");
    	$jq("#groupCount").html($sel.children().length);
    };

    
	/* newPassword Popup */
	openNewPassword = function(){
		iKEP.popupOpen('<c:url value="/portal/main/newPassword.do"/>', {width:530, height:260, status:true, scrollbar:false, toolbar:false, location:false}, 'NewPassword');
	};
	
    $jq(document).ready(function() {
    	var tplGroupOption = $.template('<option value="\${code}">\${strRepresent}\${name}</option>');
    	var $userGroupList = $("#userGroupList");
    	<c:forEach var="group" items="${groupList}" varStatus="loopStatus">
    		var groupInfo = {
    			type:"group",
    			code : "${group.groupId}",
    			name : "${userLocaleCode == 'ko' ? group.groupName : group.groupEnglishName}",
    			parent : "",
    			strRepresent : ${group.isRepresentGroup} == 1 ? strRepresent : undefined
    		};
    		$.tmpl(tplGroupOption, groupInfo).appendTo($userGroupList)
    			.data("data", groupInfo);
		</c:forEach>
    	
    	$("input.datepicker").datepicker({
    		yearRange: 'c-80:c+10',
            dateFormat: "yy-mm-dd", 
    	    onSelect: function(date, event) {
    	        var arrDate = date.split("-");
    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
    	    }
    	});
    	   	
    	
		$jq("#newButton").click(function() {
			$jq("#checkIdFlag").val("");
			getForm();
		});
		
		$jq("#submitButton").click(function() {
			$jq("#userForm").trigger("submit");
		});
		
		$jq("#deleteButton").click(function() {
			deleteForm();
		});
				
		
		$jq("#userForm :input:visible:enabled:first").focus().select();
		
		$jq("#submitButton").click(function() {
			$jq("#groupForm").trigger("submit");
		});
		
		$jq("input[name=userId]").change(function() {
			$jq("#checkIdFlag").val('changed');
		});
		
		// 백스페이스 방지(input, select 제외)
		$jq(document).keydown(function(e) {
			var element = e.target.nodeName.toLowerCase();
			if (element != 'input' && element != $jq('input[type="radio"]')) {
			    if (e.keyCode === 8) {
			        return false;
			    }
			}
		});	
				
		var validOptions = {
				rules : {
					<c:choose>
						<c:when test="${user.checkIdFlag == 'modify'}">
							userPassword : { required : true, rangelength : [0, 45] },
						</c:when>
						<c:otherwise>
							userPassword : { required : true, rangelength : [0, 20] },
						</c:otherwise>
					</c:choose>	
					officeBasicAddress : { rangelength : [0, 25] },				
					profileStatus : { rangelength : [0, 40] },
					currentJob : { rangelength : [0, 200] },
					expertField : { rangelength : [0, 300] }
				},
			    messages : {	
			    	officeBasicAddress : {
			    		rangelength : "<ikep4j:message key='Size.user.officeBasicAddress' />"
			    	},	
			    	profileStatus : {
			    		rangelength : "<ikep4j:message key='Size.user.profileStatus' />"
			    	},
			    	currentJob : {
			    		rangelength : "<ikep4j:message key='Size.user.currentJob' />"
			    	},
			    	expertField : {
			    		rangelength : "<ikep4j:message key='Size.user.expertField' />"
			    	}
			    },
			    submitHandler : function(userForm) {
			    	
			    	var checkIdFlag = $jq("#checkIdFlag").val();
					var userId = $jq("#userId").val();
					var teamName = $jq("#teamName").val();
					var checkRepresentGroup = $jq("#checkRepresentGroup").val();
					
					if($("input[name=passwordChangeYnCheck]").is(":checked")) {
						$("input[name=passwordChangeYn]").val("1");
					} else {
						$("input[name=passwordChangeYn]").val("");
					}
					
					// 조직도에서 부서를 선택하므로 굳이 체크할 필요가 없을듯
                    if(teamName) { 
                            if(checkRepresentGroup=="1") {                          
                                prepareForm();
                                $jq.ajax({
                                    url : '<c:url value="createUser.do" />',
                                    data : $jq("#userForm").serialize(),
                                    type : "post",
                                    success : function(result) {
                                        //$jq("#userTree").jstree("refresh").jstree("open_node");
                                        alert("<ikep4j:message pre='${userListPrefix}' key='alert.saveCompleted' />");
                                        getForm(result);
                                        //location.reload();
                                        //getForm();
                                    },
                                        error : function(xhr, exMessage) {
                                        var errorItems = $.parseJSON(xhr.responseText).exception;
                                        validator.showErrors(errorItems);
                                    }
                                });
                            } else {
                                alert("<ikep4j:message pre='${userListPrefix}' key='alert.noRepresentGroup' />");
                                $("#userGroupList")[0].focus();
                                return;
                            }                                          
                    } else {
                        alert("<ikep4j:message pre='${userListPrefix}' key='alert.selectGroup' />");
                    }
				}
		 };

		var validator = new iKEP.Validator("#userForm", validOptions);

		
     	// 패스워드 변경 팝업
		$("#newPassword").click(
			function(){
				$jq.ajax({
					url : '<c:url value="/portal/admin/member/user/initPassword.do" />',
					data : {
						userId : "${user.userId}",
						userPassword : "a1234567"
					},
					type : "post",
					success : function(data) {
						if(data=="success") { 
							alert("[${user.userId}]님의 비밀번호가 [a1234567]로 초기화 되었습니다.");
						} else {
							alert("error");
						}
					},
					error : function(event, request, settings) { 
						alert("error"); 
					}
				});
			}
		);
		$("#newPassword2").click(
				
				function(){
					var pwd=prompt("새 EP 비밀번호를 입력하세요. SAP, BW 비밀번호는 함께 변경할 수 없습니다. 함께 변경하려면 초기화 후에 사용자가 직접 변경하도록 하십시요.");
					if(pwd!='' && pwd!='undefined' && pwd!=null){
						
						$jq.ajax({
							url : '<c:url value="/portal/admin/member/user/initPassword.do" />',
							data : {
								userId : "${user.userId}",
								userPassword : pwd
							},
							type : "post",
							success : function(data) {
								if(data=="success") { 
									alert("[${user.userId}]님의 비밀번호가 ["+pwd+"]로 변경 되었습니다.");
								} else {
									alert("error");
								}
							},
							error : function(event, request, settings) { 
								alert("error"); 
							}
						});
					}
				}
		);
    });
				
})(jQuery);
//-->
</script>

				<form id="userForm" name="userForm" method="post" onsubmit="return false;" action="">
				<input type="hidden" name="passwordChangeYn" id="passwordChangeYn" value="" />
					<!--blockDetail Start-->					
					<div class="blockDetail clear">
						<table summary="<ikep4j:message pre='${userUiPrefix}' key='tableSummary' />">
							<caption></caption>
							<colgroup>
								<col width="18%">
								<col width="32%">
								<col width="18%">
								<col width="32%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="userId" /></th>
									<td class="textLeft" width="35%">
										 <c:out value="${user.userId}"/>
									</td>
									<th scope="col" width="15%"> <ikep4j:message pre="${userUiPrefix}" key="button.initPassword" /></th>
									<td class="textLeft" width="35%">
									<a class="button" id="newPassword"><span>a1234567로 EP비번 초기화</span></a>
									<a class="button" id="newPassword2"><span>다른값으로 EP비번 변경</span></a>
									</td>									
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="userName" /></th>
									<td class="textLeft" width="35%">
										<c:out value="${user.userName}"/>										
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="userEnglishName" /></th>
									<td class="textLeft" width="35%">
										<c:out value="${user.userEnglishName}"/>										
									</td>
								</tr>
								<tr>
                                    <th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="hanziName" /></th>
                                    <td class="textLeft" width="35%">
                                        <c:out value="${user.hanziName}"/>   
                                    </td>
                                    <th scope="col" width="15%"></th>
                                    <td class="textLeft" width="35%">                                       
                                    </td>
                                </tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="empNumber" /></th>
									<td class="textLeft" width="35%">
										<c:out value="${user.empNo}"/>    
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="userStatus" /></th>
									<td class="textLeft" width="35%">
									    <c:choose>
                                        <c:when test="${user.userStatus eq 'C'}">
                                               <ikep4j:message pre="${userUiPrefix}" key="userStatus.current" />
                                        </c:when>
                                        <c:when test="${user.userStatus eq 'H'}">
                                               <ikep4j:message pre="${userUiPrefix}" key="userStatus.vacant" />
                                        </c:when>
                                        <c:when test="${user.userStatus eq 'T'}">
                                               <ikep4j:message pre="${userUiPrefix}" key="userStatus.retired" />
                                        </c:when>                                
                                        </c:choose>
									</td>
								</tr>								
								<!--그룹 셀렉트 cell-->
								<tr>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="teamname" /></th>
									<td class="textLeft" colspan="3">
									<div> 
										<c:choose>
										<c:when test="${userLocaleCode == 'ko'}">
										<input type="text" name="teamNameSearch" id="teamNameSearch" value="${user.teamName}" size="28" class="inputbox" />
										</c:when>
										<c:otherwise>
										<input type="text" name="teamNameSearch" id="teamNameSearch" value="${user.teamEnglishName}" size="28" class="inputbox" />
										</c:otherwise>
										</c:choose>
											<a class="button_ic mb5" href="#" id="btnSearchGroup" name="btnSearchGroup" onclick="return false;"><span id="groupSearchBtn"><ikep4j:message pre="${userUiPrefix}" key="button.search" /></span></a>&nbsp;
											<a class="button_ic mb5" href="#" id="btnAddGroup" name="btnAddGroup" onclick="return false;"><span><ikep4j:message pre="${userUiPrefix}" key="button.addGroup" /></span></a>
										</div>
										<div class="blockLeft" style="width:75%;">
											<select id="userGroupList" name="userGroupList" multiple="multiple" size="5" style="width:100%;height:100px;"></select>
										</div>
										<div class="blockRight" style="width:23%;">
											<a class="button_ic mb5" href="#" id="btnMakeRepresent" name="btnMakeRepresent" onclick="return false;"><span id="makeRepresent"><ikep4j:message pre="${userUiPrefix}" key="button.makeRepresent" /></span></a><br>
											<a class="button_ic mb5" href="#" id="btnDelGroup" name="btnDelGroup" onclick="return false;"><span id="groupDelete"><ikep4j:message pre="${userUiPrefix}" key="button.delete" /></span></a><br><br>
											<ikep4j:message pre="${userUiPrefix}" key="total.label" /> <span id="groupCount">0</span><ikep4j:message pre="${userUiPrefix}" key="total.group" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="isLeader" /></th>
									<td class="textLeft" id="tdLeaderInfo"></td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="passwordChangeYn" /></th>
									<td class="textLeft" id="tdPasswordChangeYn">
										<input type="checkbox" name = "passwordChangeYnCheck" id = "passwordChangeYnCheck" <c:if test="${user.passwordChangeYn == '1'}">checked</c:if> />
									</td>
								</tr>
								
								<tr>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="locale" /></th>
									<td class="textLeft" width="35%">
										<c:out value="${user.localeName}"/>	
									</td>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="timezone" /></th>
									<td class="textLeft" width="35%">
										<select id="timezoneId" name="timezoneId" title="<ikep4j:message pre='${userUiPrefix}' key='timezone' />" style="width:100%;" >
											<c:forEach var="result" items="${timezoneList}" varStatus="loopStatus">
												<option value="<c:out value="${result.timezoneId}"/>" <c:if test="${result.timezoneId==user.timezoneId}">selected</c:if>>
													<c:out value="${result.displayName}"/>
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="email" /></th>
									<td class="textLeft" width="35%">
										<c:out value="${user.mail}"/>										
									</td>
									<th scope="col" width="15%"></th>
									<td class="textLeft" width="35%">
									</td>
								</tr>								
								<tr>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="company" /></th>
									<td class="textLeft" width="35%">
									    <c:out value="${user.companyCodeName}"/>                                   													
									</td>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="workPlace" /></th>
									<td class="textLeft" width="35%">									   
                                       <c:out value="${user.workPlaceName}"/>										
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="jobduty" /></th>
									<td class="textLeft" width="35%">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">													
														<c:out value="${user.jobDutyName}"/>														
													</c:when>
													<c:otherwise>
												          <c:out value="${user.jobDutyEnglishName}"/>												
													</c:otherwise>
												</c:choose>		
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="jobrank" /></th>
									<td class="textLeft" width="35%">
							            <c:choose>
                                             <c:when test="${userLocaleCode == 'ko'}">
                                                <c:out value="${user.jobTitleName}"/>                                                  
                                             </c:when>
                                             <c:otherwise>
                                                 <c:out value="${user.jobTitleEnglishName}"/>                                                       
                                             </c:otherwise>
                                         </c:choose>                                          
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="jobclass" /></th>
									<td class="textLeft" width="35%">								
										<c:choose>
											<c:when test="${userLocaleCode == 'ko'}">
												<c:out value="${user.jobClassName}"/>
											</c:when>
											<c:otherwise>
												<c:out value="${result.jobClassEnglishName}"/>										
											</c:otherwise>
										</c:choose>										
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="nationCode" /></th>
									<td class="textLeft" width="35%">
												<c:out value="${user.nationName}"/>								
									</td>
								</tr>
								<tr>
								   <th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="detailAddress" /></th>
                                    <td class="textLeft" width="35%">
                                        <div>
                                        <input type="text" name="officeBasicAddress" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="basicAddress" />" style="width:95%;" value="${user.officeBasicAddress}" class="inputbox"/>
                                        </div>
                                    </td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" /></th>
									<td class="textLeft" width="35%">
										<c:out value="${user.officePhoneNo}"/>
									</td>
									
								</tr>
								<tr>
								    <th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="fax" /></th>
                                    <td class="textLeft" width="35%">
                                        <div>
                                        <input type="text" name="fax" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='fax' />" style="width:36%;" value="${user.fax}" class="inputbox" />
                                        </div>
                                    </td>
                                    <th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="mobile" /></th>
                                    <td class="textLeft" width="35%">
                                       <c:out value="${user.mobile}"/>                                 
                                    </td>
                                </tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="birthday" /></th>
									<td class="textLeft" colspan=3>
										
										<c:choose>
                                            <c:when test="${user.lunisolar eq '0'}">
                                                (<ikep4j:message pre="${userUiPrefix}" key="birthday.Greg" />) <c:out value="${user.birthday}"/>
                                            </c:when>
                                            <c:otherwise>
                                                (<ikep4j:message pre="${userUiPrefix}" key="birthday.Luni" />) <c:out value="${user.birthday}"/>                                      
                                            </c:otherwise>
                                        </c:choose>  						
                                       </div>
									</td>									
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="currentJob" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="currentJob" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="currentJob" />" style="width:95%;" value="${user.currentJob}" class="inputbox" />
										</div>
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="expertField" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="expertField" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="expertField" />" style="width:95%;" value="${user.expertField}" class="inputbox" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="profileStatus.1" /><br/><ikep4j:message pre="${userUiPrefix}" key="profileStatus.2" /></th>
									<td class="textLeft" width="85%" colspan="3">
										<div>
										<input type="text" name="profileStatus" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="profileStatus.1" /><ikep4j:message pre="${userUiPrefix}" key="profileStatus.2" />" style="width:95%;" value="${user.profileStatus}" class="inputbox" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>						
					<input type="hidden" id="groupId" name="groupId" value="${user.groupId}"/>
					<input type="hidden" id="portalId" name="portalId" value="${user.portalId}"/>
					<input type="hidden" id="teamName" name="teamName" value="${userLocaleCode == 'ko' ? user.teamName : user.teamEnglishName}"/>
					<input type="hidden" id="checkRepresentGroup" name="checkRepresentGroup" value="${user.isRepresentGroup}" />
					<input type="hidden" id="isRepresentGroup" name="isRepresentGroup" value="${user.isRepresentGroup}" />
					<input type="hidden" id="leader" name="leader" value="${user.leader}" />
					<input type="hidden" id="userId" name="userId" value="${user.userId}"/>
					<input type="hidden" id="empNo" name="empNo" value="${user.empNo}"/>    
                    <input type="hidden" id="userPassword" name="userPassword" class="inputbox"  value="${user.userPassword}" />
                    <input type="hidden" id="preUserPassword" name="preUserPassword" class="inputbox" value="${user.userPassword}" />                              
<%-- 				<input type="hidden" id="leadingGroupId" name="leadingGroupId" value="${user.leadingGroupId}" /> --%>
					
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="newButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.new" /></span></a></li>
							<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.save" /></span></a></li>
							<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.delete" /></span></a></li>
							<c:if test="${!empty user.userId}">
							<!--<li><a class="button" id="initPassword" href="javascript:initPassword('N')"><span id="actionBtn"><ikep4j:message pre="${userUiPrefix}" key="button.modifyPassword" /></span></a></li>  -->
							</c:if>
							<!-- li>
								<a class="button" href="#a" ><span name="userExcelUpload" id="userExcelUpload"><ikep4j:message pre="${userUiPrefix}" key="button.bulkInsert" /></span></a>
							</li-->							
						</ul>
					</div>
					<!--//blockButton End-->
			</form>
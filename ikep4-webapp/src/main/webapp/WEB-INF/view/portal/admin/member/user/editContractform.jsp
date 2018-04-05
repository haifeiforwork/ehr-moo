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
				
				if(selectCheck) {
					$jq("#teamNameSearch").val("");
					$.tmpl(iKEP.template.groupOption, this).appendTo($select)
						.data("data", this);
				}else{
					//alert("<ikep4j:message pre='${userListPrefix}' key='alert.alreadySelected' />");
				}
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
    	
    	$("#birthday").datepicker()
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
    	
    	    
    	// 대표그룹 설정
		$jq("#btnMakeRepresent").click(function(){
			var $select = $("#userGroupList");
			var selectedGroup = $select.val();
			switch(selectedGroup.length) {
				case 0 :	// 선택하지 않았을때
					alert("<ikep4j:message pre="${userListPrefix}" key="alert.chooseAtLeastOne" />");
					break;
				case 1 :	// 하나만 선택했을때
					var $selectedOption = $("option:selected", $select);
					var groupData = $selectedOption.data("data");
					if(groupData["strRepresent"]) {	// 이미 대표그룹으로 선택된 그룹이면
						alert("<ikep4j:message pre="${userListPrefix}" key="alert.alreadySelected" />");
					} else {
						$select.children().each(function() {
							var data = $(this).data("data");
							if(data["strRepresent"]) {	// 이전 대표그룹을 대표그룹이 아닌 상태로 변경
								data["strRepresent"] = undefined;
								$(this).text(data.name);
							}
						});
						groupData["strRepresent"] = strRepresent;
						$selectedOption.text(strRepresent+groupData.name);
						
						$jq("#teamNameSearch").val(groupData.name);
						$jq("#teamName").val(groupData.name);
						$jq("#groupId").val(groupData.code);
						$jq("#isRepresentGroup").val("1");
						$jq("#checkRepresentGroup").val("1");
					}
					break;
				default :	// 둘 이상 선택했을때
					alert("<ikep4j:message pre="${userListPrefix}" key="alert.chooseOnlyOne" />");
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
		
		//엑셀파일 업로드 버튼 클릭이벤트 처리
		$jq('#userExcelUpload').click(function(event) { 
			var url = iKEP.getContextRoot() + '/portal/admin/member/user/excelForm.do';			
			iKEP.popupOpen(url, {width:800, height:210});
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
		
		//Group List
		$jq("#teamNameSearch").keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setGroup); 
		});
		
		$jq("#groupSearchBtn").click( function() { 
			$jq("#teamNameSearch").trigger("keypress");
		});
		
		$jq("#btnDelGroup").click(function(){
			var selectedGroupsText = $jq("#userGroupList option:selected").text();
			var repGroupName = $jq("#teamName").val();
			var pos = selectedGroupsText.indexOf(repGroupName);
			
			var leadingGroup = $jq("#leadingGroup").val();
			var leadingGroupPos = selectedGroupsText.indexOf(leadingGroup);
			
			// 선택된 그룹에 대표그룹이 포함되어 있는지 체크
			if(pos>0 && leadingGroupPos>0) {
				if(confirm("<ikep4j:message pre='${userListPrefix}' key='confirm.deleteRepLeadGroup' />")) {								
					$jq("#teamNameSearch").val("");
					$jq("#teamname").val("");
					$jq("#groupId").val("");
					$jq("#isRepresentGroup").val("0");
					$jq("#checkRepresentGroup").val("0");
					$jq("td[name='forLeader']").text("<ikep4j:message pre='${userListPrefix}' key='nonLeader' />");
					$jq("#leader").val("0");
					
					$jq("#userGroupList option:selected").remove();
					updateCount();
				} else {
					return;
				}
			
			} else if(pos>0 && leadingGroupPos<=0) {
				if(confirm("<ikep4j:message pre='${userListPrefix}' key='confirm.deleteRepGroup' />")) {
					$jq("#teamNameSearch").val("");
					$jq("#teamName").val("");
					$jq("#groupId").val("");
					$jq("#isRepresentGroup").val("0");
					$jq("#checkRepresentGroup").val("0");
					
					$jq("#userGroupList option:selected").remove();
					updateCount();
				} else {
					return;
				}
			
			} else if(pos<=0 && leadingGroupPos>0) {
				if(confirm("<ikep4j:message pre='${userListPrefix}' key='confirm.deleteLeadGroup' />")) {
					$jq("td[name='forLeader']").text("<ikep4j:message pre='${userListPrefix}' key='nonLeader' />");
					$jq("#leader").val("0");
					
					$jq("#userGroupList option:selected").remove();
					updateCount();
				} else {
					return;
				}
				
			} else {
				$jq("#userGroupList option:selected").remove();
				updateCount();
			}
		});
		
    	// 셀렉트 박스 카운트 넣기
    	$jq("#groupCount").html($jq("#userGroupList").children().length);
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$jq("#btnAddGroup").click( function() {
			var $select = $jq("#userGroupList");
			var items = $.map($select.children(), function(option) {
				return $(option).data("data");
			});

			iKEP.showAddressBook(updateGroupCount, items, {selectType:"group", tabs:{common:1}});
		});
		
		$jq("#userGroupList").change(function () {
			var str = "";
        	$jq("#userGroupList option:selected").each(function () {
	        	  str = $(this).text();
		        	  //$jq("#teamName").val(str);
	        	  $(this).attr("selected", true);
        	});
		}).trigger('change');
		
		var validOptions = {
				rules : {
					userId : { required : true, rangelength : [0, 15] },
					<c:choose>
						<c:when test="${user.checkIdFlag == 'modify'}">
							userPassword : { required : true, rangelength : [0, 45] },
						</c:when>
						<c:otherwise>
							userPassword : { required : true, rangelength : [0, 20] },
						</c:otherwise>
					</c:choose>
					empNo : { rangelength : [0, 25] },
					mail : { email : true, rangelength : [0, 50] },
					<c:choose>
						<c:when test="${user.checkIdFlag == 'modify'}">
							mailPassword : { rangelength : [0, 25] },
						</c:when>
						<c:otherwise>
							mailPassword : { rangelength : [0, 20] },
						</c:otherwise>
					</c:choose>									
					userName : { required : true, rangelength : [0, 15] },					
					mobile : { numberHyphen : true, rangelength : [0, 30] },
					officePhoneNo : { numberHyphen : true, rangelength : [0, 30] },
					officeBasicAddress : { rangelength : [0, 15] },
					birthday : { rangelength : [0, 10] },
					profileStatus : { rangelength : [0, 40] },
					currentJob : { rangelength : [0, 200] },
					expertField : { rangelength : [0, 300] }
				},
			    messages : {
			    	userId : {
			    		required : "<ikep4j:message key='NotNull.user.userId' />",
			    		rangelength : "<ikep4j:message key='Size.user.userId' />"
			    	},
			    	userPassword : {
			    		required : "<ikep4j:message key='NotNull.user.userPassword' />",
			    		rangelength : "<ikep4j:message key='Size.user.userPassword' />"
			    	},
			    	empNo : {
			    		rangelength : "<ikep4j:message key='Size.user.empNo' />"
			    	},
			    	mail : {
			    		email : "<ikep4j:message key='Email.user.mail' />",
			    		rangelength : "<ikep4j:message key='Size.user.mail' />"
			    	},
			    	mailPassword : {
			    		rangelength : "<ikep4j:message key='Size.user.mailPassword' />"
			    	},
			    	userName : {
			    		required : "<ikep4j:message key='NotNull.user.userName' />",
			    		rangelength : "<ikep4j:message key='Size.user.userName' />"
			    	},			    	
			    	mobile : {
			    		numberHyphen : "<ikep4j:message key='Phone.user.mobile' />",
			    		rangelength : "<ikep4j:message key='Size.user.mobile' />"
			    	},
			    	officePhoneNo : {
			    		numberHyphen : "<ikep4j:message key='Phone.user.officePhoneNo' />",
			    		rangelength : "<ikep4j:message key='Size.user.officePhoneNo' />"
			    	},			    	
			    	officeBasicAddress : {
			    		rangelength : "<ikep4j:message key='Size.user.officeBasicAddress' />"
			    	},			    	
			    	birthday : {
			    		rangelength : "<ikep4j:message key='Size.user.birthday' />"
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
						if ((checkIdFlag == "success")||(checkIdFlag == "modify")) {
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
							alert("<ikep4j:message pre='${userListPrefix}' key='alert.checkDuplicated' />");
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
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="userId" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" id="userId" name="userId" class="inputbox" style="width:45%" title="<ikep4j:message pre='${userUiPrefix}' key='userId' />" value="${user.userId}" class="inputbox"/>
										&nbsp;<a class="button_ic" href="javascript:checkId()"><span id="actionBtn"><ikep4j:message pre="${userUiPrefix}" key="checkDuplicated" /></span></a>
										</div>
										<input type="hidden" id="checkIdFlag" name="checkIdFlag" value="${user.checkIdFlag}"/>
									</td>
									<c:if test="${empty user.userId}">
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="userPassword" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="password" id="userPassword" name="userPassword" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='userPassword' />" style="width:95%;" value="${user.userPassword}" />
										</div>
									</td>
									</c:if>
									<c:if test="${!empty user.userId}">
									<th scope="col" width="15%"> <ikep4j:message pre="${userUiPrefix}" key="button.initPassword" /></th>
									<td class="textLeft" width="35%">
									<a class="button" id="newPassword"><span>a1234567로 EP비번 초기화</span></a>
									<a class="button" id="newPassword2"><span>다른값으로 EP비번 변경</span></a>
									</td>
									<input type="hidden" id="userPassword" name="userPassword" class="inputbox"  value="${user.userPassword}" />
									</c:if>
									<input type="hidden" id="preUserPassword" name="preUserPassword" class="inputbox" value="${user.userPassword}" />
								</tr>
								<tr>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="userName" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" id="userName" name="userName" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='userName' />" style="width:95%;" value="${user.userName}" class="inputbox"/>
										</div>
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="userEnglishName" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" is="userEnglishName" name="userEnglishName" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='userEnglishName' />" style="width:95%;" value="${user.userEnglishName}" class="inputbox"/>
										</div>
									</td>
								</tr>
								<tr>
                                    <th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="hanziName" /></th>
                                    <td class="textLeft" width="35%">
                                        <div>
                                        <input type="text" id="hanziName" name="hanziName" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='hanziName' />" style="width:95%;" value="${user.hanziName}" class="inputbox"/>
                                        </div>
                                    </td>
                                    <th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="userStatus" /></th>
                                    <td class="textLeft" width="35%">
                                        <input type="hidden" id="empNo" name="empNo" value=""/>
                                        <input type="radio" id="userStatusC" name="userStatus" class="radioButton" title="<ikep4j:message pre='${userUiPrefix}' key='userStatus' />" value="C" <c:if test="${user.userStatus eq 'C'}">checked="checked"</c:if>/>
                                            <label for="userStatusC"><ikep4j:message pre="${userUiPrefix}" key="userStatus.current" /></label>
                                        <input type="radio" id="userStatusH" name="userStatus" class="radioButton" title="재직구분" value="H" <c:if test="${user.userStatus eq 'H'}">checked="checked"</c:if>/>
                                            <label for="userStatusH"><ikep4j:message pre="${userUiPrefix}" key="userStatus.vacant" /></label>
                                        <input type="radio" id="userStatusT" name="userStatus" class="radioButton" title="재직구분" value="T" <c:if test="${user.userStatus eq 'T'}">checked="checked"</c:if>/>
                                            <label for="userStatusT"><ikep4j:message pre="${userUiPrefix}" key="userStatus.retired" /></label>
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
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="locale" /></th>
									<td class="textLeft" width="35%">
										<select id="localeCode" name="localeCode" title="<ikep4j:message pre="${userUiPrefix}" key="locale" />" style="width:100%;" >
											<c:forEach var="result" items="${localeList}" varStatus="loopStatus">
												<option value="<c:out value="${result.localeCode}"/>" <c:if test="${result.localeCode==user.localeCode}">selected</c:if>>
													<c:out value="${result.localeName}"/>
												</option>
											</c:forEach>
										</select>
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
										<div>
										<input type="text" name="mail" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='email' />" style="width:95%;" value="${user.mail}" class="inputbox"/>
										</div>
									</td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="passwordChangeYn" /></th>
									<td class="textLeft" id="tdPasswordChangeYn">
										<input type="checkbox" name = "passwordChangeYnCheck" id = "passwordChangeYnCheck" <c:if test="${user.passwordChangeYn == '1'}">checked</c:if> />
									</td>
								</tr>								
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="company" /></th>
									<td class="textLeft" width="35%">
										<select id="companyCode" name="companyCode" title="<ikep4j:message pre='${userUiPrefix}' key='company' />" style="width:100%;" >
											<c:forEach var="result" items="${companyList}" varStatus="loopStatus">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">
														<option value="<c:out value="${result.companyCode}"/>" <c:if test="${result.companyCode==user.companyCode}">selected</c:if>>
															<c:out value="${result.companyCodeName}"/>
														</option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${result.companyCode}"/>" <c:if test="${result.companyCode==user.companyCode}">selected</c:if>>
															<c:out value="${result.companyCodeEnglishName}"/>
														</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="workPlace" /></th>
									<td class="textLeft" width="35%">
										<select id="workPlaceCode" name="workPlaceCode" title="<ikep4j:message pre='${userUiPrefix}' key='workPlace' />" style="width:100%;" >
											<c:forEach var="result" items="${workPlaceList}" varStatus="loopStatus">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">
														<option value="<c:out value="${result.workPlaceCode}"/>" <c:if test="${result.workPlaceCode==user.workPlaceCode}">selected</c:if>>
															<c:out value="${result.workPlaceName}"/>
														</option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${result.workPlaceCode}"/>" <c:if test="${result.workPlaceCode==user.workPlaceCode}">selected</c:if>>
															<c:out value="${result.workPlaceEnglishName}"/>
														</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr><!-- 직책 미입력시 에러나서 필수값으로 함. 무림제지 2012.8.27 -->
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="jobduty" /></th>
									<td class="textLeft" width="35%">
										<select id="jobDutyCode" name="jobDutyCode" title="<ikep4j:message pre='${userUiPrefix}' key='jobduty' />" style="width:100%;" >
											<!--<option value=""> - <ikep4j:message pre="${userUiPrefix}" key="jobdutySelectOption" /> - </option>   -->
											<c:forEach var="result" items="${jobDutyList}" varStatus="loopStatus">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">
														<option value="<c:out value="${result.jobDutyCode}"/>" <c:if test="${result.jobDutyCode==user.jobDutyCode}">selected</c:if>>
															<c:out value="${result.jobDutyName}"/>
														</option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${result.jobDutyCode}"/>" <c:if test="${result.jobDutyCode==user.jobDutyCode}">selected</c:if>>
															<c:out value="${result.jobDutyEnglishName}"/>
														</option>
													</c:otherwise>
												</c:choose>												
											</c:forEach>
										</select>
									</td>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="jobrank" /></th>
									<td class="textLeft" width="35%">
										<select id="jobTitleCode" name="jobTitleCode" title="<ikep4j:message pre='${userUiPrefix}' key='jobrank' />" style="width:100%;" >
                                            <c:forEach var="result" items="${jobTitleList}" varStatus="loopStatus">
                                                <c:choose>
                                                    <c:when test="${userLocaleCode == 'ko'}">
                                                        <option value="<c:out value="${result.jobTitleCode}"/>" <c:if test="${result.jobTitleCode==user.jobTitleCode}">selected</c:if>>
                                                            <c:out value="${result.jobTitleName}"/>
                                                        </option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="<c:out value="${result.jobTitleCode}"/>" <c:if test="${result.jobTitleCode==user.jobTitleCode}">selected</c:if>>
                                                            <c:out value="${result.jobTitleEnglishName}"/>
                                                        </option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%"><span class="colorPoint">*</span> <ikep4j:message pre="${userUiPrefix}" key="jobclass" /></th>
									<td class="textLeft" width="35%">
										<select id="jobClassCode" name="jobClassCode" title="<ikep4j:message pre='${userUiPrefix}' key='jobclass' />" style="width:100%;" >
											<c:forEach var="result" items="${jobClassList}" varStatus="loopStatus">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">
														<option value="<c:out value="${result.jobClassCode}"/>" <c:if test="${result.jobClassCode==user.jobClassCode}">selected</c:if>>
															<c:out value="${result.jobClassName}"/>
														</option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${result.jobClassCode}"/>" <c:if test="${result.jobClassCode==user.jobClassCode}">selected</c:if>>
															<c:out value="${result.jobClassEnglishName}"/>
														</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
									<th scope="col" width="15%"></th>
									<td class="textLeft" width="35%">										
									</td>
								</tr>
								<tr>
								   <th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="basicAddress" /></th>
                                    <td class="textLeft" width="35%">
                                        <div>
                                        <input type="text" name="officeBasicAddress" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="basicAddress" />" style="width:95%;" value="${user.officeBasicAddress}" class="inputbox"/>
                                        </div>
                                    </td>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="officePhoneNo" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" />" style="width:95%;" value="${user.officePhoneNo}" class="inputbox"/>
										</div>
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
                                        <div>
                                        <input type="text" name="mobile" class="inputbox" title="<ikep4j:message pre='${userUiPrefix}' key='mobile' />" style="width:36%;" value="${user.mobile}" class="inputbox" />
                                        </div>
                                    </td>
                                </tr>
								<tr>
									<th scope="col" width="15%"><ikep4j:message pre="${userUiPrefix}" key="birthday" /></th>
									<td class="textLeft" colspan=3>
										<div>
										<input type="text" id="birthday" name="birthday" class="inputbox datepicker" title="<ikep4j:message pre="${userUiPrefix}" key="birthday" />" style="width:14%" value="${user.birthday}" readonly="readonly" class="inputbox" />
										<a href="#a" class="ic_cal" >
											<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" width="16px" height="16px" alt="datepicker">
										</a>
										
                                        <input type="radio" id="lunisolar0" name="lunisolar" class="radioButton" title="<ikep4j:message pre='${userUiPrefix}' key='birthday.Greg' />" value="0" <c:if test="${user.lunisolar eq '0'}">checked="checked"</c:if>/>
                                            <label for="lunisolar0"><ikep4j:message pre="${userUiPrefix}" key="birthday.Greg" /></label>
                                        <input type="radio" id="lunisolar1" name="lunisolar" class="radioButton" title="<ikep4j:message pre='${userUiPrefix}' key='birthday.Luni' />" value="1" <c:if test="${user.lunisolar eq '1'}">checked="checked"</c:if>/>
                                            <label for="lunisolar1"><ikep4j:message pre="${userUiPrefix}" key="birthday.Luni" /></label>                                        
                                     
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
					<input type="hidden" id="checkRepresentGroup" name="checkRepresentGroup" value="${user.isRepresentGroup}" />
					<input type="hidden" id="isRepresentGroup" name="isRepresentGroup" value="${user.isRepresentGroup}" />
					<input type="hidden" id="userLocaleCode" name="userLocaleCode" value="${userLocaleCode}"/>
<%-- 					<input type="hidden" id="leadingGroup" name="leadingGroup" value="${leadingGroup.groupName}"/> --%>
					<input type="hidden" id="teamName" name="teamName" value="${userLocaleCode == 'ko' ? user.teamName : user.teamEnglishName}"/>
					<input type="hidden" id="leader" name="leader" value="${user.leader}" />
<%-- 					<input type="hidden" id="leadingGroupId" name="leadingGroupId" value="${user.leadingGroupId}" /> --%>
					
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="newButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.new" /></span></a></li>
							<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.save" /></span></a></li>
							<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.delete" /></span></a></li>
							<c:if test="${!empty user.userId}">
							<!--<li><a class="button" id="initPassword" href="javascript:initPassword('N')"><span id="actionBtn"><ikep4j:message pre="${userUiPrefix}" key="button.modifyPassword" /></span></a></li>  -->
							</c:if>
							<li>
								<a class="button" href="#a" ><span name="userExcelUpload" id="userExcelUpload"><ikep4j:message pre="${userUiPrefix}" key="button.bulkInsert" /></span></a>
							</li>							
						</ul>
					</div>
					<!--//blockButton End-->
			</form>
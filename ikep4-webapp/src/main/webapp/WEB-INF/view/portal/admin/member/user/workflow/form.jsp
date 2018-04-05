<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>
<c:set var="userUiPrefix" value="ui.portal.admin.member.user"/>

				<script type="text/javascript">
				//<!--
				(function($) {
					
					var isRep = false;
					var preRepGroup = "";
					
					setGroup = function(data) {
						var $sel = $jq("select[name=userGroupList]");
						var str = "";
						var selectCheck;
						
						if(data.length > 0) {
							$jq(data).each(function(index) {
								selectCheck = true;
								$jq.each($sel.children(), function() {
									if(this.value == data[index].groupId) {
										selectCheck = false;
									}
								});
								
								if(selectCheck) {
									str = data[index].groupName;
									var $option = $("<option/>").appendTo($sel).attr('value', data[index].groupId).html(str);
								}
							});
							
							updateCount();
						} else {
							alert("<ikep4j:message pre="${userListPrefix}" key="alert.noRetrievedGroup" />");
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
						var sel = $jq("#userForm").find("[name=userGroupList]");
						
						$jq("#userGroupList option").each(function (index) {
							groups = groups + "<input name=\"groupList["+ index +"].groupId\" value=\""+ $jq(this).val() + "\" type=\"hidden\"/>";
						});
						
						$jq("#userForm").append(groups);
					}
					
					updateCount = function() {
						$jq("#groupCount").html($jq("select[name=userGroupList]").children().length);
					}
					
					updateGroupCount = function(data) {
				    	var $sel = $jq("select[name=userGroupList]");
				    	$jq("#groupCount").html($sel.children().length);
				    }
					
					//업로드완료후 fileId 리턴
					afterFileUpload = function(status, fileId, fileName, message, gubun) {
				                  //리턴받은 fileId를 Hidden값으로 저장함
				                  //폼을 최종 저장할때 fileId를 가지고, fileLink정보를 생성함
				                     
						if(gubun=='pictureImageBtn') {
							$jq("#pictureId").val(fileId);
							previewUrl(fileId, gubun);
							showPictureDiv();
						} else {
							$jq("#profilePictureId").val(fileId);
							previewUrl(fileId, gubun);
							showPictureDiv();
						}
					};
					
					previewUrl = function(fileId, gubun) {
						
						var pictureUrl = '<c:url value="/support/fileupload/downloadFile.do?fileId='+fileId+'&thumbnailYn=N"/>';
						if(gubun =='pictureImageBtn') {
							$jq("#mainPictureImage").attr("src", pictureUrl);
						} else {
							$jq("#profilePictureImage").attr("src", pictureUrl);
						}
					}
				
					showPictureDiv = function() {
						
						var fileIdForPicture = $jq("#pictureId").val();
						var fileIdForProfile = $jq("#profilePictureId").val();
						
						if(fileIdForPicture) {
							$jq("#divForPictureImg").attr("style", "display:yes;");
						} else {
							$jq("#divForPictureImg").attr("style", "display:none;");
						}
						
						if(fileIdForProfile) {
							$jq("#divForProfileImg").attr("style", "display:yes;");
						} else {
							$jq("#divForProfileImg").attr("style", "display:none;");
						}
					}
					
				    $jq(document).ready(function() {
				    	
				    	$("input.datepicker").datepicker({
				    	    onSelect: function(date, event) {
				    	        var arrDate = date.split("-");
				    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
				    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
				    	    }
				    	});
				    	
				    	$("#birthday").datepicker()
				        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
				    	
				    	$("#weddingAnniv").datepicker()
				        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
				    
				    	$jq('#addrBtn').click( function() {
				    		//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				    		iKEP.showAddressBook(setAddress, "", {selectType:"group", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
				    	});
				    	
				    	$jq('#pictureImageBtn').click(function(event) {
				    		//파일업로드 팝업창
				    		iKEP.fileUpload(event.target.id, '0', '1');
				    	});
				    	
				    	$jq('#profileImageBtn').click(function(event) {
				    		//파일업로드 팝업창
				    		iKEP.fileUpload(event.target.id, '0', '1');
				    	});
				    	
						$jq("#picturePreview").bind("click", function(event) {
							var dialog1 = $jq("#pictureDialog").dialog();
							
							$jq("#pictureDialog a").bind("click", function() {
								if($(this).text() == "<ikep4j:message pre="${userUiPrefix}" key="dialog.close" />") 
									dialog1.dialog("close");
							});
						});
						
						$jq("#profilePicturePreview").bind("click", function(event) {
							var dialog2 = $jq("#profilePictureDialog").dialog();
							$jq("#profilePictureDialog a").bind("click", function() {
								if($(this).text() == "<ikep4j:message pre="${userUiPrefix}" key="dialog.close" />")
									dialog2.dialog("close");
							});
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
							var url = iKEP.getContextRoot() + '/portal/admin/member/user/workflow/excelForm.do';			
							iKEP.popupOpen(url, {width:800, height:210});
						});
						
						showPictureDiv();
						
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
								if(confirm("<ikep4j:message pre="${userListPrefix}" key="confirm.deleteRepLeadGroup" />")) {								
									$jq("#teamNameSearch").val("");
									$jq("#teamname").val("");
									$jq("#groupId").val("");
									$jq("#isRepresentGroup").val("0");
									$jq("#checkRepresentGroup").val("0");
									$jq("td[name='forLeader']").text("<ikep4j:message pre="${userListPrefix}" key="nonLeader" />");
									$jq("#leader").val("0");
									
									$jq("#userGroupList option:selected").remove();
									updateCount();
								} else {
									return;
								}
							
							} else if(pos>0 && leadingGroupPos<=0) {
								if(confirm("<ikep4j:message pre="${userListPrefix}" key="confirm.deleteRepGroup" />")) {
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
								if(confirm("<ikep4j:message pre="${userListPrefix}" key="confirm.deleteLeadGroup" />")) {
									$jq("td[name='forLeader']").text("<ikep4j:message pre="${userListPrefix}" key="nonLeader" />");
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
				    	$jq("#groupCount").html($jq("select[name=userGroupList]").children().length);
						
						//그룹목록 추가 버튼에 이벤트 바인딩
						$jq("#btnAddGroup").click( function() {
							var items = [];
							var $sel = $jq("#userForm").find("[name=userGroupList]");

							$jq.each($sel.children(), function() {
								items.push($.data(this, "data"));
							});
							
							//파라미터(콜백함수, 전송파라미터, 팝업옵션)
							iKEP.showAddressBook(updateGroupCount, items, {selectElement:$sel,selectType:"group",isAppend:true, tabs:{common:1}});
						});
						
						// 대표그룹 설정
						$jq("#btnMakeRepresent").click(function(){
							
							var repText = $jq("#userGroupList option:selected").text().trim();
							var repGroupId = $jq("#userGroupList option:selected").val();
							var selectedGroupLength = $jq("select[name=userGroupList] option:selected").length;
							var preGroupText = preRepGroup.text().trim();
							
							if(selectedGroupLength < 1) {
								alert("<ikep4j:message pre="${userListPrefix}" key="alert.chooseAtLeastOne" />");
								return;
							} else if(selectedGroupLength == 1) {
								if(preRepGroup!="") {
									if(preGroupText==repText) {
										alert("<ikep4j:message pre="${userListPrefix}" key="alert.alreadySelected" />");
										return;
									} else {
										preRepGroup.text(preRepGroup.text().trim().substring(4, preRepGroup.text().length));
										$jq("#teamNameSearch").val(repText);
										$jq("#teamName").val(repText);
										repText = $jq("#userGroupList option:selected").text('[대표]'+repText);
										preRepGroup = $jq("#userGroupList option:selected");
										$jq("#groupId").val(repGroupId);
										$jq("#isRepresentGroup").val("1");
										$jq("#checkRepresentGroup").val("1");
									}
								} else {
									$jq("#teamNameSearch").val(repText);
									$jq("#teamName").val(repText);
									repText = $jq("#userGroupList option:selected").text('[대표]'+repText);
									preRepGroup = $jq("#userGroupList option:selected");
									$jq("#groupId").val(repGroupId);
									$jq("#checkRepresentGroup").val("1");
								}
							} else {
								alert("<ikep4j:message pre="${userListPrefix}" key="alert.chooseOnlyOne" />");
								return;
							}
						});
						
						$jq("#userGroupList").change(function () {
							var str = "";
				          $jq("#userGroupList option:selected").each(function () {
				        	  str = $(this).text();
// 				        	  $jq("#teamName").val(str);
				        	  $(this).attr("selected", true);
				        	  });
				          }).trigger('change');
						
						// [대표] 표시 토글을 위해 대표그룹을 selected로 지정하고 이전대표그룹으로 설정
						preRepGroup = $jq("#userGroupList option:selected");

						var validOptions = {
								rules : {
									userId : {
										required : true,
										rangelength : [0, 15]
									},
									<c:choose>
										<c:when test="${user.checkIdFlag == 'modify'}">
											userPassword : {
												required : true,
												rangelength : [0, 45]
											},
										</c:when>
										<c:otherwise>
											userPassword : {
												required : true,
												rangelength : [0, 20]
											},
										</c:otherwise>
									</c:choose>
									empNo : {
										rangelength : [0, 25]
									},
									mail : {
										email : true,
										rangelength : [0, 50]
									},
									<c:choose>
										<c:when test="${user.checkIdFlag == 'modify'}">
											mailPassword : {
												rangelength : [0, 25]
											},
										</c:when>
										<c:otherwise>
											mailPassword : {
												rangelength : [0, 20]
											},
										</c:otherwise>
									</c:choose>									
									userName : {
										required : true,
										rangelength : [0, 15]
									},
									userEnglishName : {
										required : true,
										//englishName : true,
										rangelength : [0, 100]
									},
									mobile : {
										numberHyphen : true,
										rangelength : [0, 30]
									},
									officePhoneNo : {
										numberHyphen : true,
										rangelength : [0, 30]
									},
									homePhoneNo : {
										numberHyphen : true,
										rangelength : [0, 30]
									},
									officeBasicAddress : {
										rangelength : [0, 15]
									},
									officeDetailAddress : {
										rangelength : [0, 25]
									},
									officeZipcode : {
										numberHyphen : true,
										rangelength : [0, 10]
									},
									homeBasicAddress : {
										rangelength : [0, 15]
									},
									homeDetailAddress : {
										rangelength : [0, 25]
									},
									homeZipcode : {
										numberHyphen : true,
										rangelength : [0, 10]
									},
									birthday : {
										rangelength : [0, 10]
									},
									weddingAnniv : {
										rangelength : [0, 10]
									},
									twitterAccount : {
										rangelength : [0, 100]
									},
									twitterAuthCode : {
										rangelength : [0, 1000]
									},
									facebookAccount : {
										rangelength : [0, 100]
									},
									facebookAuthCode : {
										rangelength : [0, 1000]
									},
									profileStatus : {
										rangelength : [0, 40]
									},
									currentJob : {
										rangelength : [0, 200]
									},
									expertField : {
										rangelength : [0, 300]
									}
								},
							    messages : {
							    	userId : {
							    		required : "<ikep4j:message key="NotNull.user.userId" />",
							    		rangelength : "<ikep4j:message key="Size.user.userId" />"
							    	},
							    	userPassword : {
							    		required : "<ikep4j:message key="NotNull.user.userPassword" />",
							    		rangelength : "<ikep4j:message key="Size.user.userPassword" />"
							    	},
							    	empNo : {
							    		rangelength : "<ikep4j:message key="Size.user.empNo" />"
							    	},
							    	mail : {
							    		email : "<ikep4j:message key="Email.user.mail" />",
							    		rangelength : "<ikep4j:message key="Size.user.mail" />"
							    	},
							    	mailPassword : {
							    		rangelength : "<ikep4j:message key="Size.user.mailPassword" />"
							    	},
							    	userName : {
							    		required : "<ikep4j:message key="NotNull.user.userName" />",
							    		rangelength : "<ikep4j:message key="Size.user.userName" />"
							    	},
							    	userEnglishName : {
							    		required : "<ikep4j:message key="NotNull.user.userEnglishName" />",
							    		//englishName : "<ikep4j:message key="EnglishName.user.userEnglishName" />",
							    		rangelength : "<ikep4j:message key="Size.user.userEnglishName" />"
							    	},
							    	mobile : {
							    		numberHyphen : "<ikep4j:message key="Phone.user.mobile" />",
							    		rangelength : "<ikep4j:message key="Size.user.mobile" />"
							    	},
							    	officePhoneNo : {
							    		numberHyphen : "<ikep4j:message key="Phone.user.officePhoneNo" />",
							    		rangelength : "<ikep4j:message key="Size.user.officePhoneNo" />"
							    	},
							    	homePhoneNo : {
							    		numberHyphen : "<ikep4j:message key="Phone.user.officePhoneNo" />",
							    		rangelength : "<ikep4j:message key="Size.user.homePhoneNo" />"
							    	},
							    	officeBasicAddress : {
							    		rangelength : "<ikep4j:message key="Size.user.officeBasicAddress" />"
							    	},
							    	officeDetailAddress : {
							    		rangelength : "<ikep4j:message key="Size.user.officeDetailAddress" />"
							    	},
							    	officeZipcode : {
							    		numberHyphen : "<ikep4j:message key="ZipCode.user.officeZipcode" />",
							    		rangelength : "<ikep4j:message key="Size.user.officeZipcode" />"
							    	},
							    	homeBasicAddress : {
							    		rangelength : "<ikep4j:message key="Size.user.homeBasicAddress" />"
							    	},
							    	homeDetailAddress : {
							    		rangelength : "<ikep4j:message key="Size.user.homeDetailAddress" />"
							    	},
							    	homeZipcode : {
							    		numberHyphen : "<ikep4j:message key="ZipCode.user.homeZipcode" />",
							    		rangelength : "<ikep4j:message key="Size.user.homeZipcode" />"
							    	},
							    	birthday : {
							    		rangelength : "<ikep4j:message key="Size.user.birthday" />"
							    	},
							    	weddingAnniv : {
							    		rangelength : "<ikep4j:message key="Size.user.weddingAnniv" />"
							    	},
							    	twitterAccount : {
							    		rangelength : "<ikep4j:message key="Size.user.twitterAccount" />"
							    	},
							    	twitterAuthCode : {
							    		rangelength : "<ikep4j:message key="Size.user.twitterAuthCode" />"
							    	},
							    	facebookAccount : {
							    		rangelength : "<ikep4j:message key="Size.user.facebookAccount" />"
							    	},
							    	facebookAuthCode : {
							    		rangelength : "<ikep4j:message key="Size.user.facebookAuthCode" />"
							    	},
							    	profileStatus : {
							    		rangelength : "<ikep4j:message key="Size.user.profileStatus" />"
							    	},
							    	currentJob : {
							    		rangelength : "<ikep4j:message key="Size.user.currentJob" />"
							    	},
							    	expertField : {
							    		rangelength : "<ikep4j:message key="Size.user.expertField" />"
							    	}
							    },
							    submitHandler : function(userForm) {
							    	
							    	var checkIdFlag = $jq("#checkIdFlag").val();
									var userId = $jq("#userId").val();
									var teamName = $jq("#teamName").val();
									var checkRepresentGroup = $jq("#checkRepresentGroup").val()
									
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
														alert("<ikep4j:message pre="${userListPrefix}" key="alert.saveCompleted" />");
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
												alert("<ikep4j:message pre="${userListPrefix}" key="alert.noRepresentGroup" />");
												return;
											}
										} else {
											alert("<ikep4j:message pre="${userListPrefix}" key="alert.checkDuplicated" />");
											return;
										}							
									} else {
										alert("<ikep4j:message pre="${userListPrefix}" key="alert.selectGroup" />");
									}
								}

						 };

						var validator = new iKEP.Validator("#userForm", validOptions);
				    });
				
				})(jQuery);
				//-->
				</script>

				<form id="userForm" name="userForm" method="post" onsubmit="return false;" action="">
					<!--blockDetail Start-->					
					<div class="blockDetail clear">
						<table summary="<ikep4j:message pre="${userUiPrefix}" key="tableSummary" />">
							<caption></caption>
							<colgroup>
								<col width="18%">
								<col width="32%">
								<col width="18%">
								<col width="32%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="userId" /></th>
									<td class="textLeft" width="25%">
										<div>
										<input type="text" id="userId" name="userId" class="inputbox" style="width:45%" title="<ikep4j:message pre="${userUiPrefix}" key="userId" />" value="${user.userId}" />
										&nbsp;<a class="button" href="javascript:checkId()"><span id="actionBtn"><ikep4j:message pre="${userUiPrefix}" key="checkDuplicated" /></span></a>
										</div>
										<input type="hidden" id="checkIdFlag" name="checkIdFlag" value="${user.checkIdFlag}"/>
									</td>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="userPassword" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="password" id="userPassword" name="userPassword" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="userPassword" />" style="width:95%;" value="${user.userPassword}" />
										</div>
										<input type="hidden" id="preUserPassword" name="preUserPassword" class="inputbox" value="${user.userPassword}" />
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="userName" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" id="userName" name="userName" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="userName" />" style="width:95%;" value="${user.userName}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="userEnglishName" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" is="userEnglishName" name="userEnglishName" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="userEnglishName" />" style="width:95%;" value="${user.userEnglishName}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="empNumber" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" id="empNo" name="empNo" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="empNumber" />" style="width:95%;" value="${user.empNo}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="userStatus" /></th>
									<td class="textLeft" width="35%">
										<input type="radio" id="userStatusC" name="userStatus" class="radioButton" title="<ikep4j:message pre="${userUiPrefix}" key="userStatus" />" value="C" <c:if test="${user.userStatus eq 'C'}">checked="checked"</c:if>/>
											<label for="userStatusC"><ikep4j:message pre="${userUiPrefix}" key="userStatus.current" /></label>
										<input type="radio" id="userStatusH" name="userStatus" class="radioButton" title="재직구분" value="H" <c:if test="${user.userStatus eq 'H'}">checked="checked"</c:if>/>
											<label for="userStatusH"><ikep4j:message pre="${userUiPrefix}" key="userStatus.vacant" /></label>
										<input type="radio" id="userStatusT" name="userStatus" class="radioButton" title="재직구분" value="T" <c:if test="${user.userStatus eq 'T'}">checked="checked"</c:if>/>
											<label for="userStatusT"><ikep4j:message pre="${userUiPrefix}" key="userStatus.retired" /></label>
									</td>
								</tr>
								
								<!--그룹 셀렉트 cell-->
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="teamname" /></th>
									<td class="textLeft" colspan="3">
										<input type="text" name="teamNameSearch" id="teamNameSearch" value="${user.teamName}" size="28"/> 
										<a class="button_ic mb5" href="#a" id="btnSearchGroup" name="btnSearchGroup">
											<span id="groupSearchBtn">
												<img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="<ikep4j:message pre='${userUiPrefix}' key='searchIcon' />"/>
												<ikep4j:message pre="${userUiPrefix}" key="button.search" />
											</span>
										</a>&nbsp;
										<a class="button_ic mb5" href="#a" id="btnAddGroup" name="btnAddGroup">
											<span>
												<img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="<ikep4j:message pre='${userUiPrefix}' key='addressBookIcon' />"/>
												<ikep4j:message pre="${userUiPrefix}" key="button.addGroup" />
											</span>
										</a>
										<div class="blockLeft" style="width:75%;">
											<select id="userGroupList" name="userGroupList" multiple="multiple" size="5" style="width:100%;height:100px;">
												<c:forEach var="groupList" items="${groupList}" varStatus="loopStatus">
													<div id="${groupList.groupId}">
														<c:choose>
															<c:when test="${userLocaleCode == 'ko' }">
																<option value="${groupList.groupId}" name="groupList[index].groupId" <c:if test="${groupList.groupId == user.groupId}">selected</c:if>>
																	<c:if test="${groupList.groupId == user.groupId}">[대표]${groupList.groupName}</c:if>
																	<c:if test="${groupList.groupId != user.groupId}">${groupList.groupName}</c:if>
																</option>
															</c:when>
															<c:otherwise>
																<option value="${groupList.groupId}" name="groupList[index].groupId" <c:if test="${groupList.groupId == user.groupId}">selected</c:if>>
																	<c:if test="${groupList.groupId == user.groupId}">[대표]${groupList.groupEnglishName}</c:if>
																	<c:if test="${groupList.groupId != user.groupId}">${groupList.groupEnglishName}</c:if>
																</option>
															</c:otherwise>
														</c:choose>
													</div>
												</c:forEach>
											</select>
										</div>
										<div class="blockRight" style="width:23%;">
											<a class="button_ic mb5" href="#a" id="btnMakeRepresent" name="btnMakeRepresent">
												<span id="makeRepresent">
													<ikep4j:message pre="${userUiPrefix}" key="button.makeRepresent" />
												</span>
											</a><br>
											<a class="button_ic mb5" href="#a" id="btnDelGroup" name="btnDelGroup">
												<span id="groupDelete">
													<img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" title="<ikep4j:message pre="${userUiPrefix}" key="button.delete" />" alt="<ikep4j:message pre="${userUiPrefix}" key="button.deleteGroup" />"/>
													<ikep4j:message pre="${userUiPrefix}" key="button.delete" />
												</span>
											</a><br><br>
											<ikep4j:message pre="${userUiPrefix}" key="total.label" /> <span id="groupCount">0</span><ikep4j:message pre="${userUiPrefix}" key="total.group" />
										</div>
									</td>
								</tr>
								
								<c:choose>
									<c:when test="${user.leader eq '1'}">
										<tr>
											<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="isLeader" /></th>
											<td class="textLeft" colspan="3" name="forLeader">
												<c:if test="${userLocaleCode == 'ko' }">
													${leadingGroup.groupName}
												</c:if>
												<c:if test="${userLocaleCode != 'ko' }">
													${leadingGroup.groupEnglishName}
												</c:if>
												<ikep4j:message pre="${userUiPrefix}" key="isLeader.yes" />
											</td>
										</tr>
									</c:when>
									<c:when test="${user.leader eq '0'}">
										<tr name="forLeader">
											<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="isLeader" /></th>
											<td class="textLeft" colspan="3" name="forLeader">
												<ikep4j:message pre="${userListPrefix}" key="nonLeader" />
											</td>
										</tr>									
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="locale" /></th>
									<td class="textLeft" width="35%">
										<select id="localeCode" name="localeCode" title="<ikep4j:message pre="${userUiPrefix}" key="locale" />" style="width:100%;" >
											<c:forEach var="result" items="${localeList}" varStatus="loopStatus">
												<option value="<c:out value="${result.localeCode}"/>" <c:if test="${result.localeCode==user.localeCode}">selected</c:if>>
													<c:out value="${result.localeName}"/>
												</option>
											</c:forEach>
										</select>
									</td>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="timezone" /></th>
									<td class="textLeft" width="35%">
										<select id="timezoneId" name="timezoneId" title="<ikep4j:message pre="${userUiPrefix}" key="timezone" />" style="width:100%;" >
											<c:forEach var="result" items="${timezoneList}" varStatus="loopStatus">
												<option value="<c:out value="${result.timezoneId}"/>" <c:if test="${result.timezoneId==user.timezoneId}">selected</c:if>>
													<c:out value="${result.displayName}"/>
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="email" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="mail" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="email" />" style="width:95%;" value="${user.mail}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="emailPassword" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="password" name="mailPassword" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="emailPassword" />" style="width:95%;" value="${user.mailPassword}" />
										</div>
										<input type="hidden" id="preMailPassword" name="preMailPassword" class="inputbox" value="${user.mailPassword}" />
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="mobile" /></th>
									<td class="textLeft" width="35%" colspan="3">
										<div>
										<input type="text" name="mobile" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="mobile" />" style="width:36%;" value="${user.mobile}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="jobtitle" /></th>
									<td class="textLeft" width="35%">
										<select id="jobTitleCode" name="jobTitleCode" title="<ikep4j:message pre="${userUiPrefix}" key="jobtitle" />" style="width:100%;" >
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
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="jobposition" /></th>
									<td class="textLeft" width="35%">
										<select id="jobPositionCode" name="jobPositionCode" title="<ikep4j:message pre="${userUiPrefix}" key="jobposition" />" style="width:100%;" >
											<c:forEach var="result" items="${jobPositionList}" varStatus="loopStatus">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">
														<option value="<c:out value="${result.jobPositionCode}"/>" <c:if test="${result.jobPositionCode==user.jobPositionCode}">selected</c:if>>
															<c:out value="${result.jobPositionName}"/>
														</option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${result.jobPositionCode}"/>" <c:if test="${result.jobPositionCode==user.jobPositionCode}">selected</c:if>>
															<c:out value="${result.jobPositionEnglishName}"/>
														</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="jobduty" /></th>
									<td class="textLeft" width="35%">
										<select id="jobDutyCode" name="jobDutyCode" title="<ikep4j:message pre="${userUiPrefix}" key="jobduty" />" style="width:100%;" >
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
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="jobrank" /></th>
									<td class="textLeft" width="35%">
										<select id="jobRankCode" name="jobRankCode" title="<ikep4j:message pre="${userUiPrefix}" key="jobrank" />" style="width:100%;" >
											<c:forEach var="result" items="${jobRankList}" varStatus="loopStatus">
												<c:choose>
													<c:when test="${userLocaleCode == 'ko'}">
														<option value="<c:out value="${result.jobRankCode}"/>" <c:if test="${result.jobRankCode==user.jobRankCode}">selected</c:if>>
															<c:out value="${result.jobRankName}"/>
														</option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${result.jobRankCode}"/>" <c:if test="${result.jobRankCode==user.jobRankCode}">selected</c:if>>
															<c:out value="${result.jobRankEnglishName}"/>
														</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="jobclass" /></th>
									<td class="textLeft" width="35%">
										<select id="jobClassCode" name="jobClassCode" title="<ikep4j:message pre="${userUiPrefix}" key="jobclass" />" style="width:100%;" >
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
									<th scope="col" width="15%" class="textCenter">*<ikep4j:message pre="${userUiPrefix}" key="nationCode" /></th>
									<td class="textLeft" width="35%">
										<select id="nationCode" name="nationCode" title="<ikep4j:message pre="${userUiPrefix}" key="nationCode" />" style="width:100%;" >
											<c:forEach var="result" items="${nationList}" varStatus="loopStatus">
												<option value="<c:out value="${result.nationCode}"/>" <c:if test="${result.nationCode==user.nationCode}">selected</c:if>>
													<c:out value="${result.nationName}"/>
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="officePhoneNo" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" />" style="width:95%;" value="${user.officePhoneNo}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="zipcode" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="officeZipcode" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="zipcode" />" style="width:95%;" value="${user.officeZipcode}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="basicAddress" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="officeBasicAddress" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="basicAddress" />" style="width:95%;" value="${user.officeBasicAddress}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="office" /><br/><ikep4j:message pre="${userUiPrefix}" key="detailAddress" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="officeDetailAddress" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="office" /><ikep4j:message pre="${userUiPrefix}" key="detailAddress" />" style="width:95%;" value="${user.officeDetailAddress}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="home" /><br/><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="homePhoneNo" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="home" /><ikep4j:message pre="${userUiPrefix}" key="phoneNumber" />" style="width:95%;" value="${user.homePhoneNo}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="home" /><br/><ikep4j:message pre="${userUiPrefix}" key="zipcode" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="homeZipcode" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="home" /><ikep4j:message pre="${userUiPrefix}" key="zipcode" />" style="width:95%;" value="${user.homeZipcode}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="home" /><br/><ikep4j:message pre="${userUiPrefix}" key="basicAddress" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="homeBasicAddress" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="home" /><ikep4j:message pre="${userUiPrefix}" key="basicAddress" />" style="width:95%;" value="${user.homeBasicAddress}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="home" /><br/><ikep4j:message pre="${userUiPrefix}" key="detailAddress" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="homeDetailAddress" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="home" /><ikep4j:message pre="${userUiPrefix}" key="detailAddress" />" style="width:95%;" value="${user.homeDetailAddress}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="birthday" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" id="birthday" name="birthday" class="inputbox datepicker" title="<ikep4j:message pre="${userUiPrefix}" key="birthday" />" style="width:80%" value="${user.birthday}" readonly="readonly">
										<a href="#a" class="ic_cal" >
											<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" width="16px" height="16px" alt="datepicker">
										</a>
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="weddingAnniv" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" id="weddingAnniv" name="weddingAnniv" class="inputbox datepicker" title="<ikep4j:message pre="${userUiPrefix}" key="weddingAnniv" />" style="width:80%" value="${user.weddingAnniv}" readonly="readonly">
										<a href="#a" class="ic_cal" >
											<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" width="16px" height="16px" alt="datepicker">
										</a>
										</div>										
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="twitter" /><br/><ikep4j:message pre="${userUiPrefix}" key="account" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="twitterAccount" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="twitter" /><ikep4j:message pre="${userUiPrefix}" key="account" />" style="width:95%;" value="${user.twitterAccount}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="twitter" /><br/><ikep4j:message pre="${userUiPrefix}" key="authCode" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="twitterAuthCode" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="twitter" /><ikep4j:message pre="${userUiPrefix}" key="authCode" />" style="width:95%;" value="${user.twitterAuthCode}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="facebook" /><br/><ikep4j:message pre="${userUiPrefix}" key="account" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="facebookAccount" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="facebook" /><ikep4j:message pre="${userUiPrefix}" key="account" />" style="width:95%;" value="${user.facebookAccount}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="facebook" /><br/><ikep4j:message pre="${userUiPrefix}" key="authCode" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="facebookAuthCode" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="facebook" /><ikep4j:message pre="${userUiPrefix}" key="authCode" />" style="width:95%;" value="${user.facebookAuthCode}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="currentJob" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="currentJob" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="currentJob" />" style="width:95%;" value="${user.currentJob}" />
										</div>
									</td>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="expertField" /></th>
									<td class="textLeft" width="35%">
										<div>
										<input type="text" name="expertField" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="expertField" />" style="width:95%;" value="${user.expertField}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="profileStatus.1" /><br/><ikep4j:message pre="${userUiPrefix}" key="profileStatus.2" /></th>
									<td class="textLeft" width="85%" colspan="3">
										<div>
										<input type="text" name="profileStatus" class="inputbox" title="<ikep4j:message pre="${userUiPrefix}" key="profileStatus.1" /><ikep4j:message pre="${userUiPrefix}" key="profileStatus.2" />" style="width:95%;" value="${user.profileStatus}" />
										</div>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="picture" /></th>
									<td class="textLeft" width="75%" colspan="3">
										<div id="divForPictureImg">
											<input type="checkbox" id="removePicture" name="removePicture" value="removePicture"/> <ikep4j:message pre="${userUiPrefix}" key="checkbox.delete" />&nbsp;
											<span id="picturePreview" name="picturePreview" style="color:red;cursor:pointer"><ikep4j:message pre="${userUiPrefix}" key="button.preview" /></span><br/>
											<input type="hidden" id="pictureId" name="pictureId" value="${user.pictureId}"/>
										</div>
										<input type="text" name="pictureName" id="pictureName" value="" title="standardImageId" />
										<a class="button" href="#" >
											<span name="pictureImageBtn" id="pictureImageBtn"><ikep4j:message pre='ui.support.fileupload.header.detail' key='file' /></span>
										</a>
									</td>
								</tr>
								<tr>
									<th scope="col" width="15%" class="textCenter"><ikep4j:message pre="${userUiPrefix}" key="profileImage.1" /><br/><ikep4j:message pre="${userUiPrefix}" key="profileImage.2" /></th>
									<td class="textLeft" width="75%" colspan="3">
										<div id="divForProfileImg">
											<input type="checkbox" id="removeProfilePicture" name="removeProfilePicture" value="removeProfilePicture"/> <ikep4j:message pre="${userUiPrefix}" key="checkbox.delete" />&nbsp;
											<span id="profilePicturePreview" name="profilePicturePreview" style="color:red;cursor:pointer"><ikep4j:message pre="${userUiPrefix}" key="button.preview" /></span><br/>
											<input type="hidden" id="profilePictureId" name="profilePictureId" value="${user.profilePictureId}"/>
										</div>
										<input type="text" name="profilePictureName" id="profilePictureName" value="" title="profileImageId" readonly="readonly"/>
										<a class="button" href="#" >
											<span name="profileImageBtn" id="profileImageBtn"><ikep4j:message pre='ui.support.fileupload.header.detail' key='file' /></span>
										</a>
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
					<input type="hidden" id="leadingGroup" name="leadingGroup" value="${leadingGroup.groupName}"/>
					<input type="hidden" id="teamName" name="teamName" value="${user.teamName}"/>
					<input type="hidden" id="leader" name="leader" value="${user.leader}" />
					<input type="hidden" id="leadingGroupId" name="leadingGroupId" value="${user.leadingGroupId}" />
					
					<div id="pictureDialog" title="<ikep4j:message pre="${userUiPrefix}" key="picture.dialogTitle" />" style="display:none;">
						<c:choose>
							<c:when test="${not empty user.pictureId}">
								<img id="mainPictureImage" src="<c:url value="${user.picturePath}"/>"
								alt="<ikep4j:message pre="${userUiPrefix}" key="picture" />" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" />								
							</c:when>
							<c:otherwise>
								<img id="mainPictureImage" src="" alt="<ikep4j:message pre="${userUiPrefix}" key="picture" />" />
							</c:otherwise>
						</c:choose>
						<p><a id="pictureDialogClose"><ikep4j:message pre="${userUiPrefix}" key="dialog.close" /></a></p>
					</div>
					
					<div id="profilePictureDialog" title="<ikep4j:message pre="${userUiPrefix}" key="profileImage.dialogTitle" />" style="display:none;">
						<c:choose>
							<c:when test="${not empty user.profilePictureId}">
								<img id="profilePictureImage" src="<c:url value="${user.profilePicturePath}"/>" 
								alt="<ikep4j:message pre="${userUiPrefix}" key="profileImage" />" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
							</c:when>
							<c:otherwise>
								<img id="profilePictureImage" src="" alt="<ikep4j:message pre="${userUiPrefix}" key="profileImage.1" /><ikep4j:message pre="${userUiPrefix}" key="profileImage.2" />" />
							</c:otherwise>
						</c:choose>
						<p><a id="profilePictureDialogClose"><ikep4j:message pre="${userUiPrefix}" key="dialog.close" /></a></p>
					</div>

					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="newButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.new" /></span></a></li>
							<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.save" /></span></a></li>
							<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${userUiPrefix}" key="button.delete" /></span></a></li>
							
							<li><a class="button" id="initPassword" href="javascript:initPassword('N')"><span id="actionBtn"><ikep4j:message pre="${userUiPrefix}" key="button.initPassword" /></span></a></li>
							<li>
								<a class="button" href="#" ><span name="userExcelUpload" id="userExcelUpload"><ikep4j:message pre="${userUiPrefix}" key="button.bulkInsert" /></span></a>
							</li>							
						</ul>
					</div>
					<!--//blockButton End-->
			</form>
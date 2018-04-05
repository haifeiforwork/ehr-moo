<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listCategoryView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		settingRead();

		//동일 권한 클릭
		$jq("input[name=sameAuthority]").click(function() {
			var mode = "${mode}";
			var isChecked = $jq("input[name=sameAuthority]").is(":checked");
			if(isChecked) {
				if(mode == "U") {
					reloading("Y", mode, $jq("input[name=categoryId]").val());
				} else {
					reloading("Y", mode, $jq("input[name=categoryParentId]").val());
				}
			} else {
				if(mode == "U") {
					reloading("N", mode, $jq("input[name=categoryId]").val());
				} else {
					reloading("N", mode, $jq("input[name=categoryParentId]").val());
				}
			}
		});   
		
		//공개 여부 클릭
		$jq("input[name=readPermission]").click(function() {
			settingRead();
		});  
		
		//문서 조회자 삭제 클릭
		$jq("#readUserDeleteButton").click(function() {
			$jq('#readUserSelect option:selected').remove();
		});
		//문서 조회 조직 삭제 클릭
		$jq("#readGroupDeleteButton").click(function() {
			$jq('#readGroupSelect option:selected').remove();
		});
		//문서 담당자 삭제 클릭
		$jq("#writeUserDeleteButton").click(function() {
			$jq('#writeUserSelect option:selected').remove();
		});
		
		//문서 조회자 클릭
		$jq('#readUserName').keypress( function(event) { 
			iKEP.searchUser(event, "Y", setReadUser); 
		});
		$jq('#readUserButton').click( function() { 
			$jq('#readUserName').trigger("keypress");
		});
		//문서 조회 조직 클릭
		$jq('#readGroupName').keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setReadGroup);
		});
		$jq('#readGroupButton').click( function() { 
			$jq('#readGroupName').trigger("keypress");
		});
		//문서 담당자 클릭
		$jq('#writeUserName').keypress( function(event) { 
			iKEP.searchUser(event, "Y", setWriteUser); 
		});
		$jq('#writeUserButton').click( function() { 
			$jq('#writeUserName').trigger("keypress");
		});
		
		//저장 클릭
		$jq("#saveButton").click(function() {
			$("#form").trigger("submit");
		});
		
		//validation
		var validOptions = {
			rules : {
				categoryName : {required : true, rangelength : [1, 50]}
			},
			messages : {
				categoryName : {       
					required : "<ikep4j:message key='NotEmpty.category.categoryName' />",
					rangelength : "<ikep4j:message key='Size.category.categoryName' />"
				}
			},
			notice : {
				categoryName : "<ikep4j:message key='Notice.category.categoryName' />"
			},
			submitHandler : function(form) {// 부가 검증 처리
				var readPermission = $jq("[name=readPermission]:checked").val();
				if(readPermission == 0) {
					//문서 조회자 및 조직 존재 여부
					if($jq('#readUserSelect option').length < 1 && $jq('#readGroupSelect option').length < 1) {
						alert("<ikep4j:message pre='${messagePrefix}' key='reader.nothing'/>");
						return;
					}
					//동일 문서 조회자 검사
					var checkUserResult = checkReadUsers();
					if(checkUserResult != "") {
						alert("<ikep4j:message pre='${messagePrefix}' key='readUser.duplication'/>" + " : " + checkUserResult);
						return;
					}
					//동일 문서 조직 검사
					checkUserResult = checkReadGroup();
					if(checkUserResult != "") {
						alert("<ikep4j:message pre='${messagePrefix}' key='readGroup.duplication'/>" + " : " + checkUserResult);
						return;
					}
					
					//문서 조회자
					var readUsers = "";
					$jq('#readUserSelect option').each(function() {
						readUsers += $(this).val() + ",";
					});
					readUsers = readUsers.substring(0, readUsers.length-1);
					$jq("#form>input[name=readUsers]").val(readUsers);
					
					//문서 조직
					var readGroups = "";
					$jq('#readGroupSelect option').each(function() {
						readGroups += $(this).val() + ",";
					});
					readGroups = readGroups.substring(0, readGroups.length-1);
					$jq("#form>input[name=readGroups]").val(readGroups);
				}
				
				//문서 담당자  존재 여부
				if($jq('#writeUserSelect option').length < 1) {
					alert("<ikep4j:message pre='${messagePrefix}' key='writeUser.nothing'/>");
					return;
				}
				//동일 문서 담당자 검사
				var checkUserResult = checkWriteUser();
				if(checkUserResult != "") {
					alert("<ikep4j:message pre='${messagePrefix}' key='writeUser.duplication'/>" + " : " + checkUserResult);
					return;
				}
				//문서 결재자  존재 여부
				var i = 0;
				$jq("[name=approvalUserDiv]").each(function(index) {
					var userId = $jq(this).find("input").attr("approvalUserId");
					if(userId != "") {
						i += 1;
					}
				});
				if(i == 0) {
					alert("<ikep4j:message pre='${messagePrefix}' key='approvalUser.nothing'/>");
					return;
				}
				
				//동일 문서 결재자 검사
				var checkUserResult = checkApprovalUser();
				if(checkUserResult != "") {
					alert("<ikep4j:message pre='${messagePrefix}' key='approvalUser.duplication'/>" + " : " + checkUserResult);
					return;
				}
				
				//문서 담당자
				var writeUsers = "";
				$jq('#writeUserSelect option').each(function() {
					writeUsers += $(this).val() + ",";
				});
				writeUsers = writeUsers.substring(0, writeUsers.length-1);
				$jq("#form>input[name=writeUsers]").val(writeUsers);
				
				//문서 결재자
				var approvalUsers = "";
				$jq("[name=approvalUserDiv]").each(function() {
					var userId = $jq(this).find("input").attr("approvalUserId")
					if(userId != "") {
						approvalUsers += userId + ",";	
					}
				});
				approvalUsers = approvalUsers.substring(0, approvalUsers.length-1);
				$jq("#form>input[name=approvalUsers]").val(approvalUsers);

				var categoryId = $jq("#form>input[name=categoryId]").val();
				if (categoryId == null || categoryId == "") {// 신규
					$jq.ajax({
						url : "<c:url value='/collpack/workmanual/saveCategory.do'/>",
						type : "post",
						data : $jq(form).serialize(),
						loadingElement : "#saveButton",
						success : function(data, textStatus, jqXHR) {
							$jq("#categoryTree").jstree("open_node", getSelectedItem());
							$jq("#categoryTree").jstree("create_node", getSelectedItem(), "last", $jq.parseJSON(data),
					    			function() {
										resetDatail();
										// 메뉴트리 반영
					    				//window.parent.menuTreeCreateNode(getSelectedItem().attr("id"), $jq.parseJSON(data));
					    			}
					    	);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
							validator.showErrors(errorItems);
						}
					});
				} else {//수정
					$jq.ajax({
						url : "<c:url value='/collpack/workmanual/saveCategory.do'/>",
						type : "post",
						data : $jq(form).serialize(),
						loadingElement : "#saveButton",
						success : function(data, textStatus, jqXHR) {
							var categoryName = $jq("#form input[name=categoryName]").val();
					    	$jq("#categoryTree").jstree("rename_node", getSelectedItem(), categoryName);
					    	resetDatail();
							// 메뉴트리 반영
					    	//window.parent.menuTreeRenameNode(getSelectedItem().attr("id"), categoryName);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
							validator.showErrors(errorItems);
						}
					});
				}
			}
		};
		new iKEP.Validator("#form", validOptions);

	});

	//문서 조회자 등록
	setReadUser = function(data) {
		var $select = $jq("#readUserSelect");
		$jq(data).each(function(index) {
			//$jq("#readUserSelect").append("<option value=" + data[index].userId + ">" + data[index].userName + "</option>");
			$jq.tmpl(iKEP.template.userOption, this).appendTo($select)
				.data("data", this);
		});	
	};
	//문서 조회 조직 등록
	setReadGroup = function(data) {
		var $select = $jq("#readGroupSelect");
		$jq(data).each(function(index) {
			//$jq("#readGroupSelect").append("<option value=" + data[index].groupId + ">" + data[index].groupName + "</option>");
			$jq.tmpl(iKEP.template.groupOption, this).appendTo($select)
				.data("data", this);	// 조직도 팝업과 호환을 위해
		});	
	};
	//문서 담당자 등록
	setWriteUser = function(data) {
		var $select = $jq("#writeUserSelect");
		$jq(data).each(function(index) {
			//$jq("#writeUserSelect").append("<option value=" + data[index].userId + ">" + data[index].userName + "</option>");
			$jq.tmpl(iKEP.template.userOption, this).appendTo($select)
				.data("data", this);
		});	
	};
	
	var countApprovalUser = ${fn:length(approvalUserList)}== 0 ? 1:${fn:length(approvalUserList)};
	
	//문서 결재자 삭제
	deleteApprovalUser = function(obj) {
		var count = $jq("[name=approvalUserDiv]").length + 0; 
		if(count == 1) {
			$jq("#" + obj + " > input").val("");
			$jq("#" + obj + " > input").attr("approvalUserId", "");
			$jq("#" + obj + " > select option").remove();
			$jq("#" + obj + " > select").append("<option value=1>1</option>");
		} else {
			$jq("#" + obj).remove();
			 
			$jq('[name=approvalUserDiv]').each(function(index){
				var $opt = $jq(this).find("option");
				($opt).remove();
				var $sel = $jq(this).find("select");
				($sel).append("<option value=" + (index+1) + ">" + (index+1) + "</option>");
			});
		}		
	};
	//문서 결재자 추가
	addApprovalUser = function(obj) {
		var position = $jq("#" + obj + " > select option").val();
	
		$jq('[name=approvalUserDiv]').each(function(index){
			if(eval(index) >= eval(position)) {
				var $opt = $jq(this).find("option");
				($opt).remove();
				var $sel = $jq(this).find("select");
				($sel).append("<option value=" + (index+2) + ">" + (index+2) + "</option>");
			}
		});

		position = eval(position) + 1;
		countApprovalUser = eval(countApprovalUser) + 1;
		var html = "<div id='" + countApprovalUser + "Div' name='approvalUserDiv'>" +
						/*
						"<select title='<ikep4j:message pre='${prefix}' key='approvalUser'/>' name='' class='selectbox'>" +
							"<option value='" + position + "'>" + position + "</option>" +
						"</select> " +
						*/
						"<input approvalUserId='' title='<ikep4j:message pre='${prefix}' key='approvalUser'/>' class='inputbox' type='text' size='20' readonly='readonly' title='input box'/> " +
						"<a class='button_s' href='#a' onclick='searchApprovalUser(\"" + countApprovalUser + "Div\")'><span><ikep4j:message pre='${buttonPrefix}' key='department.tree'/></span></a> " +
						"<a class='button_s' href='#a' onclick='deleteApprovalUser(\"" + countApprovalUser + "Div\")'><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a> " +
						"<a class='button_s' href='#a' onclick='addApprovalUser(\"" + countApprovalUser + "Div\")'><span><ikep4j:message pre='${buttonPrefix}' key='add'/></span></a> " +
					"</div>";
		$jq("#" + obj).after(html);
	};

	//결재자 찾기
	var selectedDiv;
	searchApprovalUser = function(divId) {
		iKEP.showAddressBook(function(data) {
			var $input = $jq("#" + divId + " > input");
			$jq.each(data, function() {
				$input.val(this.userName + " " + this.jobTitleName)
					.attr("approvalUserId", this.id);
			})
		}, "", {selectType:"user", selectMaxCnt : 1, tabs:{common:1}});
	};
		
	//동일 문서 조회자  검사
	checkReadUsers = function() {
		var userName = "";
		
		$jq('#readUserSelect option').each(function(index){
			var userId = this.value;
			var m = 0;
			$jq('#readUserSelect option').each(function(index){
				if(userId == this.value) {
					m += 1;
				}
			});
			if(m > 1) {
				userName = $jq('#readUserSelect option:eq(' + index + ')').text();
				return false;
			}
		});
		
		return userName;
	};
	//동일 문서 조회 조직  검사
	checkReadGroup = function() {
		var userName = "";
		
		$jq('#readGroupSelect option').each(function(index){
			var userId = this.value;
			var m = 0;
			$jq('#readGroupSelect option').each(function(index){
				if(userId == this.value) {
					m += 1;
				}
			});
			if(m > 1) {
				userName = $jq('#readGroupSelect option:eq(' + index + ')').text();
				return false;
			}
		});
		
		return userName;
	};
	//동일 문서 담당자  검사
	checkWriteUser = function() {
		var userName = "";
		
		$jq('#writeUserSelect option').each(function(index){
			var userId = this.value;
			var m = 0;
			$jq('#writeUserSelect option').each(function(index){
				if(userId == this.value) {
					m += 1;
				}
			});
			if(m > 1) {
				userName = $jq('#writeUserSelect option:eq(' + index + ')').text();
				return false;
			}
		});
		
		return userName;
	};
	//동일 문서 결재자  검사
	checkApprovalUser = function() {
		var userName = "";
		
		var i = 0;
		$jq("[name=approvalUserDiv]").each(function(index) {
			i += 1;
			var userId = $jq(this).find("input").attr("approvalUserId");
			if(userId != "") {
				var j = 0;
				$jq("[name=approvalUserDiv]").each(function(index) {
					j += 1;
					if(j > i) {
						if(userId == $jq(this).find("input").attr("approvalUserId")) {
							userName = $jq(this).find("input").val();
							return false;
						}
					}
				});
			}
		});
		
		return userName;
	};
	
})(jQuery);
//]]>
</script>

	
						<form id="form" action="" method="post">
						<input type="hidden" name="mode" value="${mode}"/>
						<input type="hidden" name="categoryId" value="${manualCategory.categoryId}"/>
						<input type="hidden" name="categoryParentId" value="${manualCategory.categoryParentId}"/>
						<input type="hidden" name="readUsers" value=""/>
						<input type="hidden" name="readGroups" value=""/>
						<input type="hidden" name="writeUsers" value=""/>
						<input type="hidden" name="approvalUsers" value=""/>
						
						<!--blockDetail Start-->					
						<div class="blockDetail clear">
							<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
								<caption></caption>
								<thead>
									<tr>
										<th scope="col" width="25%"><ikep4j:message pre="${prefix}" key="categoryName"/></th>
										<td class="textLeft"><div>
											<input name="categoryName" value="${manualCategory.categoryName}" title="<ikep4j:message pre="${prefix}" key="categoryName"/>" class="inputbox" type="text" size="20" />
											<c:if test="${manualCategory.categoryParentId != null}">
												<label><input name="sameAuthority" class="checkbox" title="<ikep4j:message pre="${prefix}" key="categoryName"/>" type="checkbox" <c:if test="${upYn == 'Y'}">checked</c:if>/><ikep4j:message pre="${prefix}" key="sameAuthority"/></label>										
											</c:if></div>
										</td>
									</tr>
								</thead>
								<tbody id="readTbody">
									<tr>
										<th id="readTh" width="25%" rowspan="2" scope="col"><ikep4j:message pre="${prefix}" key="readUser"/></th>
										<td>
											<label><input name="readPermission" value="1" type="radio" class="radio" title="<ikep4j:message pre="${prefix}" key="public.open"/>"  <c:if test="${readPermission == 1}">checked="checked"</c:if>/><ikep4j:message pre="${prefix}" key="public.open"/></label>&nbsp;&nbsp;
											<label><input name="readPermission" value="0" type="radio" class="radio" title="<ikep4j:message pre="${prefix}" key="public.close"/>" <c:if test="${readPermission == 0}">checked="checked"</c:if>/><ikep4j:message pre="${prefix}" key="public.close"/></label>
										</td>
									</tr>
									<tr id="readTr">
										<td class="textLeft">
											<input id="readUserName" name="readUserName" title="<ikep4j:message pre="${prefix}" key="readUser"/>" class="inputbox" type="text" size="20" />
											<a class="button_s" href="#a" id="readUserButton"><span><ikep4j:message pre='${buttonPrefix}' key='user'/></span></a>
											<select id="readUserSelect" size="5" multiple="multiple" class="multi w80" title="<ikep4j:message pre="${prefix}" key="readUser"/>" >
												<c:forEach var="item" items="${readUserList}">
													<option value="${item.readUserId}">
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.readUserName} ${item.readUserJobTitleName} ${item.readUserTeamName}</c:when>
															<c:otherwise>${item.readUserEnglishName} ${item.readUserJobTitleEnglishName} ${item.readUserTeamEnglishName}</c:otherwise>
														</c:choose>
													</option>
												</c:forEach>
											</select>
											<a class="button_ic" href="#a" id="readUserDeleteButton" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a> 
												
											
											<div class="blockBlank_10px"></div>
											
											<input id="readGroupName" name="readGroupName" title="<ikep4j:message pre="${prefix}" key="department"/>" class="inputbox" type="text" size="20" />
											<a class="button_s" href="#a" id="readGroupButton"><span><ikep4j:message pre='${buttonPrefix}' key='department'/></span></a>
											<select id="readGroupSelect" size="5" multiple="multiple" class="multi w80" title="<ikep4j:message pre="${prefix}" key="department"/>" >
												<c:forEach var="item" items="${readGroupList}"> 
													<option value="${item.readGroupId}">
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.readGroupName}</c:when>
															<c:otherwise>${item.readGroupEnglishName}</c:otherwise>
														</c:choose>
													</option>
												</c:forEach>
											</select>
											<a class="button_ic" href="#a" id="readGroupDeleteButton" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a> 
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre="${prefix}" key="wirteUser"/></th>
										<td>
											<input id="writeUserName" name="writeUserName" title="<ikep4j:message pre="${prefix}" key="wirteUser"/>" class="inputbox" type="text" size="20" />
											<a class="button_s" href="#a" id="writeUserButton"><span><ikep4j:message pre='${buttonPrefix}' key='user'/></span></a>
											<select id="writeUserSelect" size="5" multiple="multiple" class="multi w80" title="<ikep4j:message pre="${prefix}" key="wirteUser"/>" >
												<c:forEach var="item" items="${writeUserList}"> 
													<option value="${item.writeUserId}">
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.writeUserName} ${item.writeUserJobTitleName} ${item.writeUserTeamName}</c:when>
															<c:otherwise>${item.writeUserEnglishName} ${item.writeUserJobTitleEnglishName} ${item.writeUserTeamEnglishName}</c:otherwise>
														</c:choose>
													</option>
												</c:forEach>
											</select>
											<a class="button_ic" href="#a" id="writeUserDeleteButton" style="vertical-align:bottom"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a> 	
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre="${prefix}" key="approvalUser"/></th>
										<td>
											<c:choose>
											<c:when test="${fn:length(approvalUserList) == 0}">
												<div id="1Div" name="approvalUserDiv">
													<!--  
													<select title="<ikep4j:message pre="${prefix}" key="approvalUser"/>" class="selectbox">
														<option value="1">1</option>
													</select>
													-->
													<input value="" approvalUserId="" title="<ikep4j:message pre="${prefix}" key="approvalUser"/>" class="inputbox" type="text" size="20" readonly="readonly"/>
													<a class="button_s" href="#a" onclick="searchApprovalUser('1Div')"><span><ikep4j:message pre='${buttonPrefix}' key='department.tree'/></span></a>
													<a class="button_s" href="#a" onclick="deleteApprovalUser('1Div')"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a>
													<a class="button_s" href="#a" onclick="addApprovalUser('1Div')"><span><ikep4j:message pre='${buttonPrefix}' key='add'/></span></a>
												</div>
											</c:when>
											<c:otherwise>
												<c:forEach var="item" items="${approvalUserList}" varStatus="status">
													<div id="${status.count}Div" name="approvalUserDiv">
														<!-- 
														<select title="<ikep4j:message pre="${prefix}" key="approvalUser"/>" class="selectbox">
															<option value="${status.count}">${status.count}</option>
														</select>
														-->
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}"><input value="${item.approvalUserName} ${item.approvalUserJobTitleName} ${item.approvalUserTeamName}" approvalUserId="${item.approvalUserId}" title="<ikep4j:message pre="${prefix}" key="approvalUser"/>" class="inputbox" type="text" size="30"  readonly="readonly"/></c:when>
															<c:otherwise><input value="${item.approvalUserEnglishName} ${item.approvalUserJobTitleEnglishName} ${item.approvalUserTeamEnglishName}" approvalUserId="${item.approvalUserId}" title="<ikep4j:message pre="${prefix}" key="approvalUser"/>" class="inputbox" type="text" size="30" /></c:otherwise>
														</c:choose>
														<a class="button_s" href="#a" onclick="searchApprovalUser('${status.count}Div')"><span><ikep4j:message pre='${buttonPrefix}' key='department.tree'/></span></a>
														<a class="button_s" href="#a" onclick="deleteApprovalUser('${status.count}Div')"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a>
														<a class="button_s" href="#a" onclick="addApprovalUser('${status.count}Div')"><span><ikep4j:message pre='${buttonPrefix}' key='add'/></span></a>
													</div>
												</c:forEach>
											</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--//blockDetail End-->		
						</form>
						
						<!--blockButton Start-->
						<div class="blockButton"> 
							<a class="button" href="#a" id="saveButton"><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a>
							<a class="button" id="listButton" href="<c:url value='/collpack/workmanual/listCategoryView.do'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a>
						</div>
						<!--//blockButton End-->
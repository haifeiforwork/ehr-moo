<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="preHeader"  value="message.collpack.collaboration.board.boardAdmin.updateBoardView" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardAdmin.updateBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.message" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 

<script type="text/javascript"> 
<!--  
(function($) {   

	setWritePermission = function(data) {  
		
		var $sel = $jq("select[name=writePermissionList]")
			.children().remove()
			.end();
		$jq.each(data, function() {
			if(this.type=="group") {
				$.tmpl(iKEP.template.groupWorkspaceBoardOption, this)
					.appendTo($sel)
					.data("data", this);
			}
			else {
				$.tmpl(iKEP.template.userWorkspaceBoardOption, this)
				.appendTo($sel)
				.data("data", this);
			}
		})
		
	};
	
	setAddressInit = function() {
		
		var $sel = $jq("select[name=writePermissionList]")
			.children().remove()
			.end();
		
		var addrObj;
		
		<c:forEach var="user" items="${board.writeUserList}"> 
			addrObj = $.parseJSON('{"type":"user","id":"${user.userId}","userName":"${user.userName}","jobTitleName":"${user.jobTitleName}","teamName":"${user.teamName}"}');
			$.tmpl(iKEP.template.userWorkspaceBoardOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>
		
		<c:forEach var="group" items="${board.writeGroupList}"> 
			addrObj = $.parseJSON('{"type":"group","code":"${group.groupId}","name":"${group.groupName}"}');
			$.tmpl(iKEP.template.groupWorkspaceBoardOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>
	
	};

	displayAllowTypeHelpBox = function() { 
		if($("input[name=fileAttachOption]:checked").attr("value") == "0") {
			$("#fileExtensionHelpBox").show();
			$("#fileTypeHelpBox").hide();
		} else {
			$("#fileExtensionHelpBox").hide();
			$("#fileTypeHelpBox").show();
		}  
		
		iKEP.iFrameContentResize();
	};  
	
	
	$(document).ready(function() {  
		window.parent.topScroll(); 		
		
		$("#writeChangeHierarchy").click(function() {
		
			$("#writePermissionList option:selected").each(function() {
				var groupInfo = $(this).attr("id").split("^");
				
				if(groupInfo[0]=="group") {
					if($(this).hasClass("hierarchied")) { 
						$(this).removeClass("hierarchied");
						$(this).text(groupInfo[2]);
					} else { 
						$(this).addClass("hierarchied");
						$(this).text("[H]" + groupInfo[2]);
					}
				} else {
					alert('<ikep4j:message pre="${preMessage}" key="onlyGroup" />');
				}
			}); 
		});
			
		displayAllowTypeHelpBox();
					
		new iKEP.Validator("#boardForm", {
			<c:if test="${board.boardType eq 0}">
			rules  : {
				boardName        : { required : true, rangelength : [1, 100] },  
				description : { required : true, rangelength : [1, 500] },  
				allowType        : { required : true, rangelength : [1, 50] }   
			},
			messages : { 
				boardName        : {direction : "bottom",  required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.boardName' />",        rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.boardName' />"}, 
	            description : {direction : "bottom",   required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.description' />", rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.description' />"}, 
	            allowType        : {
	            	direction : "bottom", 
	            	required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.allowType' />",  
	            	rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.allowType' />"}
			},
			</c:if>
			<c:if test="${board.boardType eq 1}">
			rules  : {
				boardName : {required : true, rangelength : [1, 100]}  
			},
			messages : { 
				boardName        : {direction : "bottom", required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.boardName' />",        rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.boardName' />"}, 
			    description : {direction : "bottom",  required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.description' />", rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.description' />"} 
			},
			</c:if>			
			<c:if test="${board.boardType eq 2}">
			rules  : {
				boardName        : {required : true, rangelength : [1, 100] },   
				description : {required : true, rangelength : [1, 500] },   
				url              : {required : true, rangelength : [1, 500], url : true } 
			},
			messages : { 
				boardName        : {direction : "bottom", required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.boardName' />",        rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.boardName' />"}, 
		        description : {direction : "bottom",  required : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.description' />", rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.description' />"}, 
				url              : {
					direction : "bottom",  
					required    : "<ikep4j:message key='message.collpack.collaboration.NotEmpty.board.url' />",  
					rangelength : "<ikep4j:message key='message.collpack.collaboration.Size.board.url' />", 
					url         : "<ikep4j:message key='message.collpack.collaboration.Url.board.url' />"  
				}
			},
			</c:if>

		    submitHandler : function(form) {   
		        <c:if test="${board.boardType eq 0}">
				var fileAttachOption = $("input[name=fileAttachOption]:checked").val();
		    	
		    	var allowType = $("input[name=allowType]").val();
		    	
		    	var allowFileType = ["txt", "img", "doc", "ado", "vod", "comp", "app", "all"];
		    	
		    	if(fileAttachOption == "1" ) { 
		    		var array = allowType.split(",");
		    		var exist = false;
		    		
		    		for(var lindex = 0, lmax = array.length; lindex < lmax; lindex++ ) { 
						for(var sindex = 0, smax = allowFileType.length; sindex < smax; sindex++ ) { 
							if(array[lindex] == allowFileType[sindex]) {
								exist = true;
							} 
						} 
						if(!exist) {
							alert("<ikep4j:message key='message.collpack.collaboration.NotAllowedFileType.board.allowType' />");
							return false;
						}
		    		}  
		    	} else if (fileAttachOption == "0") { 
			    	if(allowType.match(/[0-9]/gi) != null) {
			    		alert("<ikep4j:message key='message.collpack.collaboration.NotAllowedFileExtend.board.allowType' />");
						return false; 
			    	}
			    	
			    }	
		    	</c:if>
		    	$("input[name=wiki]").removeAttr('disabled');
				
				<c:if test="${board.versionManage eq 1 }">
				$("input[name=versionManage]").removeAttr('disabled');
				</c:if>			    	
		        
		        form.submit();
		    }
		});     
		//커서 포커스를 첫번째 Input에 넣는다.
		$("input[name=boardName]").focus();
		
		$("a[name=updateBoardButton]").click(function() {
		  	
		  	var userIdx=0;
			var groupIdx=0;
			
			$("input[name^=writeUserList], input[name^=writeGroupList]").remove();
			
			userIdx=0;
			groupIdx=0;
			$("#writePermissionList option").each(function(index) {  
				var list = $(this).attr("id").split("^");
				
				if(list[0]=="user") {    
					$("<input name='writeUserList[" + userIdx + "].userId'       type='hidden' value='"+ list[1] +"' />").appendTo( "#boardForm" );   
					$("<input name='writeUserList[" + userIdx + "].userName'     type='hidden' value='"+ list[2] +"' />").appendTo( "#boardForm" );   
					$("<input name='writeUserList[" + userIdx + "].jobTitleName' type='hidden' value='"+ list[3] +"' />").appendTo( "#boardForm" );   
					$("<input name='writeUserList[" + userIdx + "].teamName'     type='hidden' value='"+ list[4] +"' />").appendTo( "#boardForm" );   
					$("<input name='writeUserList[" + userIdx + "].mail'         type='hidden' value='"+ list[5] +"' />").appendTo( "#boardForm" ); 
					userIdx++;  
				}
				else {
				
					$("<input name='writeGroupList[" + groupIdx + "].groupId'     type='hidden' value='"+ list[1] +"' />").appendTo( "#boardForm" );   
					$("<input name='writeGroupList[" + groupIdx + "].groupName'   type='hidden' value='"+ list[2] +"' />").appendTo( "#boardForm" );  
				
					if($(this).hasClass("hierarchied")) {
						$("<input name='writeGroupList[" + groupIdx + "].hierarchied' type='hidden' value='1' />").appendTo( "#boardForm" );    
					} else {
						$("<input name='writeGroupList[" + groupIdx + "].hierarchied' type='hidden' value='0' />").appendTo( "#boardForm" );  
					}
					groupIdx++;
				}

			});  
			 
			<c:if test="${board.boardType eq 0}">
			$("#boardForm").attr("action", "<c:url value='/collpack/kms/admin/board/updateBoardTypeBoard.do'/>");
			</c:if>
			<c:if test="${board.boardType eq 1}">
			$("#boardForm").attr("action", "<c:url value='/collpack/kms/admin/board/updateCategoryTypeBoard.do'/>");
			</c:if>				
			<c:if test="${board.boardType eq 2}">
			
			var urlPopup = $("#urlPopupCheckBox:checked").val();
			
			if(urlPopup == "1"){
				$("#urlPopup").val("1");
			}else{
				$("#urlPopup").val("0");
			}
			
			$("#boardForm").attr("action", "<c:url value='/collpack/kms/admin/board/updateLinkTypeBoard.do'/>");
			</c:if>
 
			
			$("#boardForm").trigger("submit"); 
			
			return false; 
		}); 
		


		
		$("input[name=fileAttachOption]").click(function () { 
			
			if($(this).val() == "1") {
				$("input[name=allowType]").val("all");	
			} else {
				$("input[name=allowType]").val("doc,ppt,excel,pdf,hwp,txt,zip,jpg,gif");	
			}
			
			
			displayAllowTypeHelpBox();  
		}); 
		
		
		$("input[name=rss]").attr("disabled",true);
		// 버전관리 수정시 버전관리중인것은 변경 불가함, 
		// 첨부파일 관련 링크 정보가 최초의 것으로 돌아가는 현상이 발생하여 최근에 변경하여 등록한 게시물의 조회가 안됨
		<c:if test="${board.versionManage eq 1 }">
		$("input[name=versionManage]").attr("disabled",true);
		</c:if>		
		
		
		$("#addWritePermissionButton").click( function() {
			
			var items = [];
			$jq("select[name=writePermissionList]").children().each(function() {
				items.push($(this).data("data"));
			});

			iKEP.showAddressBook(setWritePermission, items, {selectType:"all", tabs:{group:0,common:0,personal:0,collaboration:1,sns:0}});
			
			//iKEP.showAddressBook(setReadUserAddress, "", {selectType:"user", isAppend:true, tabs:{common:1}});
			//iKEP.showAddressBook(setWritePermission, "", {selectType:"ALL", isAppend:true});
		});		
		
		$("#deleteWritePermissionButton").click(function(event) {
			event.preventDefault();
			$("#writePermissionList option:selected").remove();
		});			
	
		$jq("input[name=writePermission]").click(function() {

			if($jq("input[name=writePermission]:checked").val()==4)
			{
				$jq('#writePermissionListDiv').show();
							
			}else{
				$jq("input[name=writePermission]").removeAttr('disabled');				
				$jq('#writePermissionList').empty();
				$jq('#writePermissionListDiv').hide();
				// 버튼보이기
				$jq("#addWritePermissionButton").show();
				$jq("#deleteWritePermissionButton").show();
				$jq("#writeHierarchyButton").show();				
			}

			
			iKEP.iFrameContentResize();
		});
	
		
		iKEP.iFrameContentResize();		
		
		setAddressInit();
		
		return false;  
		
	}); 
})(jQuery);  
//-->
</script> 
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="message.collpack.kms.admin.board.listBoardView.header" key="${pageTitle}" /></h2> 
</div> 
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail"> 
	<form id="boardForm" method="post">
		<spring:bind path="board.portalId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
		</spring:bind>	
		<spring:bind path="board.boardType">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>				
		<spring:bind path="board.boardRootId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	 
		<spring:bind path="board.boardId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	 
		<spring:bind path="board.sortOrder">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	 
		<spring:bind path="board.boardParentId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>
		<spring:bind path="board.urlPopup">
			<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" /> 
		</spring:bind>	 	 

		<table summary="<ikep4j:message pre='${preDetail}' key='summary' />"> 
		<col style="width: 12%;"/>
		<col style="width: 6%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>  
		<col style="width: 32%;"/> 
		<tbody>
			<spring:htmlEscape defaultHtmlEscape="false">		
			<spring:bind path="board.parentList">
			<tr> 
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='boardPath' /></th>
				<td colspan="3"> <ikep4j:message pre='${preDetail}' key='path' /> >
					<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}"> 
						<c:if test="${not varStatus.last}">
							${boardItem.boardName} >
						</c:if> 
						<c:if test="${varStatus.last}">${boardItem.boardName}</c:if> 
					</c:forEach>
				</td> 
			</tr>				
			</spring:bind>
			</spring:htmlEscape>
			
			<spring:htmlEscape defaultHtmlEscape="false">
			<spring:bind path="board.boardName">
			<tr> 
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
				<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox w100" 
					value="${status.value}" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			        </c:forEach>
				</td> 
			</tr>				
			</spring:bind>
			</spring:htmlEscape>

		
			
			<spring:htmlEscape defaultHtmlEscape="false">
			<spring:bind path="board.description">
			<tr> 
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
					<div>
					<textarea 
					name="${status.expression}" 
					type="textarea w100" 
					class="w100" 
					rows="5"
					value="${status.value}" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					>${status.value}</textarea>	
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind> 
			</spring:htmlEscape> 
			
			<c:if test="${board.boardType eq 2 }">
			<spring:bind path="board.url">
			<tr> 
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox w60" 
					value="${status.value}" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
			</spring:bind>
			
					<input 
					name="urlPopupCheckBox" 					
					id="urlPopupCheckBox"
					type="checkbox" 
					class="checkbox" 
					value="1" 
					title="<ikep4j:message pre='${preDetail}' key='urlPopup' />"
					<c:if test="${board.urlPopup eq 1 }">checked=checked</c:if>
					/><ikep4j:message pre="${preDetail}" key="urlPopup" />
			
				</td> 
			</tr>
			</c:if>
			
			<c:if test="${board.boardType eq 0}"> 
			<tr> 
				<spring:bind path="board.pageNum">
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
					<select name="${status.expression}">
					<c:forEach var="code" items="${boardCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach>
					</select>
				</td> 
				</spring:bind>
			</tr>			
			<tr> 
				<spring:bind path="board.fileSize">
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<select name="${status.expression}">
					<c:forEach var="code" items="${boardCode.fileSizeList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach> 
					</select> 
					<span class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span></span>
				</td> 
				</spring:bind>

				<spring:bind path="board.move">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${boardCode.useUnuseList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					
				</td> 
				</spring:bind>						 
			</tr>
			
			<tr> 
				<spring:bind path="board.imageFileSize">
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<select name="${status.expression}">
					<c:forEach var="code" items="${boardCode.imageFileSizeList}">
						<option  value="${code.key}"  <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach>
					</select> 
					<span class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span></span>
				</td> 
				</spring:bind>	
			
	
				<spring:bind path="board.imageWidth">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<select name="${status.expression}">
					<c:forEach var="code" items="${boardCode.imageWidthList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>
						${code.value}
						</option>
					</c:forEach>
					</select>
					<span class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span></span>
				</td> 
				</spring:bind>	 
			</tr>	

  
			<spring:bind path="board.fileAttachOption"> 
			<tr> 
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
					<div>
					<c:forEach var="code" items="${boardCode.attachFileOptionList}"> 
						<label>
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
						</label>
					</c:forEach> 
					</div>
				</td> 
			</tr>				
			</spring:bind>	
			<spring:bind path="board.allowType">
			<tr> 
				<th colspan="2" scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td colspan="3">
					<div>
					<input name="${status.expression}"  
					type="text" 
					class="inputbox w100"  
					value="${status.value}" 
					size="40" title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<div id="fileExtensionHelpBox">
						<div class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help1" /></span></div>  
					</div>
					<div id="fileTypeHelpBox">
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help2" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help3" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help4" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help5" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help6" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help7" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help8" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help9" /></span></div> 				
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help10" /></span></div> 
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help11" /></span></div> 
						<div class=""><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help12" /></span></div> 
					</div>				
				</td> 
			</tr>				
			</spring:bind>
			</c:if>					
			</tbody>	
			
		
		</table>   
	</div>  
	<!--//blockDetail End-->
	<!--blockButton Start-->
	<div class="blockButton" id="divButton"> 
		<ul> 
			<li><a name="updateBoardButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a name="listBoardViewButton" class="button" href="<c:url value='/collpack/kms/admin/board/listBoardView.do?isKnowHow=${board.isKnowHow}&boardRootId=0'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>			
		</ul>
	</div>
	<!--//blockButton End--> 
	
	
	
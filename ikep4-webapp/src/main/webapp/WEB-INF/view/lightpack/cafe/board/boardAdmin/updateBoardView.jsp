<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="preHeader"  value="message.lightpack.cafe.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="message.lightpack.cafe.board.boardAdmin.createBoardView.detail" />
<c:set var="preCode"    value="message.lightpack.cafe.common.code" />
<c:set var="preButton"  value="message.lightpack.cafe.common.button" /> 
<c:set var="preMessage" value="message.lightpack.cafe.common.message" />
<c:set var="preSearch"  value="message.lightpack.cafe.common.searchCondition" /> 

<script type="text/javascript"> 
<!--  
(function($) {   
	
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
		displayAllowTypeHelpBox();
					
		new iKEP.Validator("#boardForm", {
			<c:if test="${board.boardType eq 0}">
			rules  : {
				boardName        : { required : true, rangelength : [1, 100] },  
				description : { required : true, rangelength : [1, 500] },  
				allowType        : { required : true, rangelength : [1, 50] }   
			},
			messages : { 
				boardName        : {direction : "bottom",  required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.boardName' />",        rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.boardName' />"}, 
	            description : {direction : "bottom",   required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.description' />", rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.description' />"}, 
	            allowType        : {
	            	direction : "bottom", 
	            	required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.allowType' />",  
	            	rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.allowType' />"}
			},
			</c:if>
			<c:if test="${board.boardType eq 1}">
			rules  : {
				boardName : {required : true, rangelength : [1, 100]}  
			},
			messages : { 
				boardName        : {direction : "bottom", required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.boardName' />",        rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.boardName' />"}, 
			    description : {direction : "bottom",  required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.description' />", rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.description' />"} 
			},
			</c:if>			
			<c:if test="${board.boardType eq 2}">
			rules  : {
				boardName        : {required : true, rangelength : [1, 100] },   
				description : {required : true, rangelength : [1, 500] },   
				url              : {required : true, rangelength : [1, 500], url : true } 
			},
			messages : { 
				boardName        : {direction : "bottom", required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.boardName' />",        rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.boardName' />"}, 
		        description : {direction : "bottom",  required : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.description' />", rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.description' />"}, 
				url              : {
					direction : "bottom",  
					required    : "<ikep4j:message key='message.lightpack.cafe.NotEmpty.board.url' />",  
					rangelength : "<ikep4j:message key='message.lightpack.cafe.Size.board.url' />", 
					url         : "<ikep4j:message key='message.lightpack.cafe.Url.board.url' />"  
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
							alert("<ikep4j:message key='message.lightpack.cafe.NotAllowedFileType.board.allowType' />");
							return false;
						}
		    		}  
		    	} else if (fileAttachOption == "0") { 
			    	if(allowType.match(/[0-9]/gi) != null) {
			    		alert("<ikep4j:message key='message.lightpack.cafe.NotAllowedFileExtend.board.allowType' />");
						return false; 
			    	}
			    	
			    }	
		    	</c:if>

		    	$jq("#boardForm")[0].submit();
		    	$("#boardForm").ajaxLoadStart();
		    }
		});     
		//커서 포커스를 첫번째 Input에 넣는다.
		$("input[name=boardName]").focus();
		
		$("a[name=updateBoardButton]").click(function() {
		  	
			if(!confirm("<ikep4j:message pre='${preMessage}' key='save' />")) {
				return;
			}
			
			<c:if test="${board.boardType eq 0}">
			$("#boardForm").attr("action", "<c:url value='/lightpack/cafe/board/boardAdmin/updateBoardTypeBoard.do'/>");
			</c:if>
			<c:if test="${board.boardType eq 1}">
			$("#boardForm").attr("action", "<c:url value='/lightpack/cafe/board/boardAdmin/updateCategoryTypeBoard.do'/>");
			</c:if>				
			<c:if test="${board.boardType eq 2}">
			$("#boardForm").attr("action", "<c:url value='/lightpack/cafe/board/boardAdmin/updateLinkTypeBoard.do'/>");
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
		
		
		iKEP.iFrameContentResize();		
		return false;  
		
	}); 
})(jQuery);  
//-->
</script> 
<!--pageTitle Start-->
<div id="pageTitle">
</div> 
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail"> 
	
	
	<form id="boardForm" method="post">  
	<div class="blockDetail"> 
				<spring:bind path="board.portalId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
		</spring:bind> 	
		<spring:bind path="board.cafeId">
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

	
		<div class="blockDetail clear">
							<table summary="">
								<caption></caption>
								<thead>
									<tr>
										<th scope="col" width="25%"><ikep4j:message pre='${preDetail}' key='boardPath' /></th>
										<td class="textLeft">
											<ikep4j:message pre='${preDetail}' key='path' /> >
											<c:forEach var="boardItem" varStatus="varStatus" items="${status.value}"> 
												<c:if test="${not varStatus.last}">
													${boardItem.boardName} >
												</c:if> 
												<c:if test="${varStatus.last}">${boardItem.boardName}</c:if> 
											</c:forEach>
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<spring:bind path="board.boardName">
										<th width="28%" scope="col"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
											
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
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.description">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
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
			  	   						</spring:bind>
									</tr>
									
									<c:if test="${board.boardType eq 2 }">
										<spring:bind path="board.url">
										<tr> 
											<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
											<td>
												<input 
												name="${status.expression}" 
												type="text" 
												class="inputbox w60" 
												value="${status.value}" 
												title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
												/>
												<input 
												name="urlPopup" 
												type="checkbox" 
												class="checkbox" 
												value="1" 
												title="<ikep4j:message pre='${preDetail}' key='urlPopup' />"
												<c:if test="${board.urlPopup eq 1 }">checked=checked</c:if>
												/><ikep4j:message pre="${preDetail}" key="urlPopup" />
											</td> 
										</tr>				
										</spring:bind> 	 
									</c:if>
									
									<c:if test="${board.boardType eq 0 }">
									<tr>
										<spring:bind path="board.listType">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
											<div>
											<c:forEach var="code" items="${boardCode.listTypeList}"> 
												<input name="${status.expression}" type="radio" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> /> <ikep4j:message key='${code.value}'/>
											</c:forEach>
											</div>																								
										</td>
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.rss">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
									
									<spring:bind path="board.anonymous">
										<input name="${status.expression}" type="hidden" value="0" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
									</spring:bind>
									<spring:bind path="board.versionManage">
										<input name="${status.expression}" type="hidden" value="0" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
									</spring:bind>
									<spring:bind path="board.wiki">
										<input name="${status.expression}" type="hidden" value="0" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
									</spring:bind>
									
									<tr>
										<spring:bind path="board.docPopup">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
										<spring:bind path="board.pageNum">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
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
										<spring:bind path="board.reply">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
											<span class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span></span> 
										</td>
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.linereply">
											<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
												<span class="tdInstruction"><span class="colorPoint"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span></span>		
											</td>
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.move">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
										<spring:bind path="board.fileSize">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
									</tr>
									<tr>
										<spring:bind path="board.imageFileSize">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
									</tr>
									<tr>
										<spring:bind path="board.imageWidth">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
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
									<tr>
										<spring:bind path="board.fileAttachOption"> 
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
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
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.allowType">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
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
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.readPermission">
										<th scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
											<div style="padding-bottom:4px;">
											<c:forEach var="code" items="${boardCode.permissionList}" varStatus="varStatus"> 
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
										</spring:bind>
									</tr>
									<tr>
										<spring:bind path="board.writePermission">
										<th width="25%" scope="col"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
										<td>
											<div style="padding-bottom:4px;">
											<c:forEach var="code" items="${boardCode.permissionList}" varStatus="varStatus"> 
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
									</c:if>
								</tbody>
							</table>
						</div>
							<!--//blockDetail End-->
						</form>
						
						<!--blockButton Start-->
						<div class="blockButton" id="divButton"> 
							<ul> 
								<li>
									<a name="updateBoardButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
								</li>			
							</ul>
						</div>
						<!--//blockButton End--> 
	

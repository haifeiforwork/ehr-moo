<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"  value="ui.lightpack.board.boardBondsIssueSub.listBoardBondsIssueSubView" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardLinereply" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--  
(function($){	 
	$(document).ready(function() { 
	//	
		$("div.blockMoreSell_rewrite").hide(); 
	});  
	
	/*
	*  추가상담내용 수정 폼을 화면에서 보이게 한다.
	*/	
	showUpdateFormSub =function(linereplyId) { 
		$("div[name=updateForm]").hide(); 
		
		var contextObjet = $("#" + linereplyId).siblings("p[name=contents]");  
		var contextText = $(contextObjet).text();   
		//$("#" + linereplyId).find("textarea[name=contents]").val(contextText); 
		$("#" + linereplyId).show(); 
		$("#" + linereplyId).find("textarea[name=contents]").focus();

		$("div[name=buttonArea]").hide();
		$("input[name=mcounselDateSub]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		iKEP.iFrameContentResize();
		return false;  
	};

	/*
	*  신규 답변을 저장한다.
	*/	
	createBondsIssueSub = function() {    
		var contents = $("textarea[name=contents]","#boardBondsIssueSubForm").val();  
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("이슈내용을 기입해주세요.");
			return false;
		} 
		var tmpclaimGubunSubName=jQuery("#claimGubunSub option:selected").text();
		$jq('input[name=claimGubunSubName]').val(tmpclaimGubunSubName);
		
		$("#blockMoreSell").ajaxLoadStart(); 
		
		$.post('<c:url value="/support/customer/customerBondsIssueSub/createBondsIssueSub.do"/>', $("#boardBondsIssueSubForm").serialize())
		.success(function(data) { 
			loadBoardBondsIssueSubList(); 
		})
		.error(function(event, request, settings) { alert("error"); $("#blockMoreSell").ajaxLoadComplete(); }); 
		
		return false;  
	};


	/*
	*  추가상담내용 수정을 한다. 
	*/
	updateBondsIssueSub = function(formName, linereplyId) { 
		var formObject = $("#" + formName).children("form"); 
		
		var contents = $("textarea[name=contents]",formObject).val(); 
		
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("이슈내용을 기입해주세요");
			return false;
		}
		
		var tmpmclaimGubunSubName=jQuery("#"+linereplyId+"_mclaimGubunSub option:selected").text();
		var tmpmclaimGubunSub=jQuery("#"+linereplyId+"_mclaimGubunSub option:selected").val();
		$jq('input[name=mclaimGubunSubName]').val(tmpmclaimGubunSubName);
		$jq('input[name=mclaimGubunSub]').val(tmpmclaimGubunSub);

		$("#blockMoreSell").ajaxLoadStart();
		
		$.post('<c:url value="/support/customer/customerBondsIssueSub/updateBondsIssueSub.do"/>', formObject.serialize())
		.success(function(data) { 
			loadBoardBondsIssueSubList();
		})
		.error(function(event, request, settings) { alert("error"); }); 
	    
		return false;  
	};
			
	/*
	*  추가상담내용 수정 폼을 화면에서 안보이게 한다.
	*/
	closeUpdateBondsIssueSubForm = function(linereplyId) {  
		$("#" + linereplyId ).hide();
		$("div[name=buttonArea]").show();
		return false;  
	};

			
	/*
	*  작성자 모드로 추가상담내용을 삭제한다.
	*/		
	userDeleteBoardBondsIssueSub = function(itemId, linereplyId) {
		
		var itemType = "BI";
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) { 
			$("#blockMoreSell").ajaxLoadStart();
		    $.post("<c:url value='/support/customer/customerBondsIssueSub/userDeleteBondsIssueSub.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId,"itemType": itemType}) 
		    .success(function(data) { 
		    	loadBoardBondsIssueSubList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); $("#blockMoreSell").ajaxLoadComplete();});
		}  
		else
		{
			$("#blockMoreSell").ajaxLoadComplete();
		}
	    
		return false;  
	};

	/*
	*  관리자 모드로 추가상담내용을 삭제한다.
	*/	
	adminDeleteBoardBondsIssueSub = function(itemId, linereplyId) {
		var itemType = "BI";
		$("#blockMoreSell").ajaxLoadStart();
		if(confirm("이슈내역을 삭제하시겠습니까?")) {
		    $.post("<c:url value='/support/customer/customerBondsIssueSub/adminDeleteBondsIssueSub.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId, "itemType": itemType})  
		    .success(function(data) { 
		    	loadBoardBondsIssueSubList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); $("#blockMoreSell").ajaxLoadComplete(); });
		}  
		else
		{
			$("#blockMoreSell").ajaxLoadComplete();
		}
	    
		return false;  
	};	
	
	$("input[name=contents]").keypress(function(event) {
		if(event.which == '13') {
			$(this).parents("form:eq(0)").find("a.sumbit").click();
		}
	});
	
	
	
	
	
})(jQuery); 
//-->
</script>		 
		<div class="guestbook_write"> 
			<form id="boardBondsIssueSubForm" onsubmit="return false;" style="display:none;">
				<spring:bind path="searchCondition.itemId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.itemType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>   
				<div class="blockDetail">
					<table>
						<caption></caption>
                        <colgroup>
                            <col width="10%"/>
                            <col width="32%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="17%"/>
                            <col width="6%"/>
                        </colgroup>
						<tbody>
							<tr>
								<th>구분</th>
								<td colspan="2">
									<select title="구분" name="claimGubunSub" id="claimGubunSub">
				                        <option value=""  >선택</option>
				                        <option value="1" >신용보험</option>
				                        <option value="2" >담보</option>
				                        <option value="3" >여신한도</option>
				                        <option value="4" >Sub거래선</option>
				                        <option value="6" >배서인</option>
				                        <option value="5" >기타</option>
				                    </select> 
				                    <input type="hidden" name="claimGubunSubName" value=""/>
								</td>
								<th>등급</th>
								<td colspan="3">
									<select title="등급" name="factorySub" id="factorySub">
				                        <option value=""  >선택</option>
				                        <option value="S" >S</option>
				                        <option value="A" >A</option>
				                        <option value="B" >B</option>
				                        <option value="C" >C</option>
				                        <option value="D" >D</option>
				                        <option value="E" >E</option>
				                        <option value="F" >F</option>
				                    </select> 
								</td>
							</tr>
							<tr>
								<th>등록일</th>
								<td colspan="2">
									<input name="counselDateSub"   id="counselDateSub" title="등록일" class="inputbox w50 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<th>등록자</th>
								<td colspan="3"><input name="counselorSub" title="등록자" class="inputbox w100" type="text" /></td>
							</tr>
							<tr>
								<th>제목</th>
								<td colspan="6"><input name="subjectSub" title="제목" class="inputbox w100" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">이슈내용</th>
								<td colspan="5"><textarea name="contents" class="w100" title="이슈내용" cols="" rows="10"></textarea></td>
								<td style="vertical-align:bottom;">
									<div class="blockButton"> 
					                    <ul>
					                        <li><a class="button" onclick="createBondsIssueSub();" href="#a"><span>등록</span></a></li>
					                    </ul>
					                </div>
								</td>
							</tr>
						</tbody>
					</table> 
				</div>
			</form>
		</div>    
	<c:forEach var="boardBondsIssueSub" varStatus="varStatus" items="${searchResult.entity}">
			<c:choose>
		 		<c:when test="${boardBondsIssueSub.linereplyDelete eq 1}">
		 			<p><span class="deletedItem"><ikep4j:message pre='${preList}' key='contents' post="deletecontents"/></span></p> 
				</c:when>
				<c:otherwise>
					<div class="blockDetail">
						<table>
							<caption></caption>
				                     <colgroup>
				                         <col width="10%"/>
				                         <col width="32%"/>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                         <col width="10%"/>
				                         <col width="17%"/>
				                         <col width="6%"/>
				                     </colgroup>
							<tbody>
								<tr>
									<th>구분</th>
									<td colspan="2">
										<c:if test="${boardBondsIssueSub.claimGubunSubName == '선택'}"></c:if>
										<c:if test="${boardBondsIssueSub.claimGubunSubName != '선택'}">
											${boardBondsIssueSub.claimGubunSubName}
										</c:if>
									</td>
									<th>등급</th>
									<td colspan="3">
										<c:if test="${boardBondsIssueSub.factorySub == '선택'}"></c:if>
										<c:if test="${boardBondsIssueSub.factorySub != '선택'}">
											${boardBondsIssueSub.factorySub}
										</c:if>
									</td>
								</tr>
								<tr>
									<th>등록일</th>
									<td colspan="2">${boardBondsIssueSub.counselDateSub}</td>
									<th>등록자</th>
									<td colspan="3">${boardBondsIssueSub.counselorSub}</td>
								</tr>
								<tr>
									<th>제목</th>
									<td colspan="6">${boardBondsIssueSub.subjectSub}</td>
								</tr>
								<tr>
									<th scope="row">이슈내용</th>
									<td colspan="6">
										<% pageContext.setAttribute("newLineChar", "\n"); %> 
										<p name="contents">${fn:replace(boardBondsIssueSub.contents, newLineChar, '<br/>')}</p>
									</td>
								</tr>
							</tbody>
						</table> 
					</div>
					
					<div id="${boardBondsIssueSub.linereplyId}UpdateForm"  name="updateForm" class="blockMoreSell_rewrite modify" style="display:none"> 
						<form name="updateForm" onsubmit="return false;"> 
							<input name="linereplyId" type="hidden" value="${boardBondsIssueSub.linereplyId}" title="<ikep4j:message pre='${preList}' key='linereplyId'/>" /> 
							<div class="blockDetail">
							<table>
								<caption></caption>
		                        <colgroup>
		                            <col width="10%"/>
		                            <col width="32%"/>
		                            <col width="10%"/>
		                            <col width="15%"/>
		                            <col width="10%"/>
		                            <col width="17%"/>
		                            <col width="6%"/>
		                        </colgroup>
								<tbody>
									<tr>
										<th>구분</th>
										<td colspan="2">
											<select title="구분" name="${boardBondsIssueSub.linereplyId}_mclaimGubunSub" id="${boardBondsIssueSub.linereplyId}_mclaimGubunSub">
						                        <option value=""  >선택</option>
						                        <option value="1" <c:if test="${boardBondsIssueSub.claimGubunSub eq '1'}">selected="selected"</c:if>>신용보험</option>
				                        		<option value="2" <c:if test="${boardBondsIssueSub.claimGubunSub eq '2'}">selected="selected"</c:if>>담보</option>
				                        		<option value="3" <c:if test="${boardBondsIssueSub.claimGubunSub eq '3'}">selected="selected"</c:if>>여신한도</option>
				                        		<option value="4" <c:if test="${boardBondsIssueSub.claimGubunSub eq '4'}">selected="selected"</c:if>>Sub거래선</option>
				                        		<option value="6" <c:if test="${boardBondsIssueSub.claimGubunSub eq '6'}">selected="selected"</c:if>>배서인</option>
				                        		<option value="5" <c:if test="${boardBondsIssueSub.claimGubunSub eq '5'}">selected="selected"</c:if>>기타</option>
						                    </select> 
						                    <input type="hidden" name="mclaimGubunSub" value=""/>
						                    <input type="hidden" name="mclaimGubunSubName" value=""/>
										</td>
										<th>등급</th>
										<td colspan="3">
											<select title="등급" name="mfactorySub" id="mfactorySub">
						                        <option value=""  >선택</option>
						                        <option value="S" <c:if test="${boardBondsIssueSub.factorySub eq 'S'}">selected="selected"</c:if>>S</option>
				                        		<option value="A" <c:if test="${boardBondsIssueSub.factorySub eq 'A'}">selected="selected"</c:if>>A</option>
				                        		<option value="B" <c:if test="${boardBondsIssueSub.factorySub eq 'B'}">selected="selected"</c:if>>B</option>
				                        		<option value="C" <c:if test="${boardBondsIssueSub.factorySub eq 'C'}">selected="selected"</c:if>>C</option>
				                        		<option value="D" <c:if test="${boardBondsIssueSub.factorySub eq 'D'}">selected="selected"</c:if>>D</option>
				                        		<option value="E" <c:if test="${boardBondsIssueSub.factorySub eq 'E'}">selected="selected"</c:if>>E</option>
				                        		<option value="F" <c:if test="${boardBondsIssueSub.factorySub eq 'F'}">selected="selected"</c:if>>F</option>
						                    </select> 
										</td>
									</tr>
									<tr>
										<th>등록일</th>
										<td colspan="2">
											<input name="mcounselDateSub"   id="mcounselDateSub" title="등록일" class="inputbox w50 datepicker" type="text" value="${boardBondsIssueSub.counselDateSub}"  /> 
                                			<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
										</td>
										<th>등록자</th>
										<td colspan="3"><input name="counselorSub" title="등록자" class="inputbox w100" type="text" value="${boardBondsIssueSub.counselorSub}"  /></td>
									</tr>
									<tr>
										<th>제목</th>
										<td colspan="6"><input name="subjectSub" title="제목" class="inputbox w100" type="text" value="${boardBondsIssueSub.subjectSub}"  /></td>
									</tr>
									<tr>
										<th scope="row">이슈내용</th>
										<td colspan="5"><textarea name="contents" class="w100" title="이슈내용" cols="" rows="10">${boardBondsIssueSub.contents}</textarea></td>
										<td style="vertical-align:bottom;">
											<div class="blockButton"> 
							                    <ul>
							                        <ul> 
														<li><a class="button" onclick="updateBondsIssueSub('${boardBondsIssueSub.linereplyId}UpdateForm',${boardBondsIssueSub.linereplyId});"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
														<li><a class="button" onclick="closeUpdateBondsIssueSubForm('${boardBondsIssueSub.linereplyId}UpdateForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> 
													</ul> 
							                    </ul>
							                </div>
										</td>
									</tr>
								</tbody>
							</table> 
							</div>
						</form>
					
					</div>
					<div class="blockButton" name="buttonArea">
					<c:choose>
						<c:when test="${isSystemAdmin}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
		 					<a class="button" onclick="showUpdateFormSub('${boardBondsIssueSub.linereplyId}UpdateForm');" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
		 					<c:if test="${boardBondsIssueSub.masterSub!='Y'}">
		 						<a class="button" onclick="adminDeleteBoardBondsIssueSub('${boardBondsIssueSub.itemId}', '${boardBondsIssueSub.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
							</c:if>
						</c:when>   
						<c:when test="${boardBondsIssueSub.registerId eq user.userId}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
		 					<a class="button" onclick="showUpdateFormSub('${boardBondsIssueSub.linereplyId}UpdateForm');" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
		 					<c:if test="${boardBondsIssueSub.masterSub!='Y'}">
		 						<a class="button" onclick="userDeleteBoardBondsIssueSub('${boardBondsIssueSub.itemId}', '${boardBondsIssueSub.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
							</c:if>
						</c:when>    
					</c:choose> 
					</div>
					<!--blockMoreSell_rewrite Start--> 
					 
					<!--//blockMoreSell_rewrite End--> 
				</c:otherwise>
			</c:choose>	  
		</form>	 
	</c:forEach> 
	<!--Page Numbur Start--> 
	<form id="boardBondsIssueSubSearchForm" method="post" onsubmit="return false;"> 
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="boardBondsIssueSubSearchForm" ajaxEventFunctionName="loadBoardBondsIssueSubList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" displayYn="true"/>
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->  
	</form> 
<!--//blockMoreSell End--> 
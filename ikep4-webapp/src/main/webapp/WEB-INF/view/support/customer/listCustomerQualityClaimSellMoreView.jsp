<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"  value="ui.lightpack.board.boardQualityClaimSellMore.listBoardQualityClaimSellMoreView" />
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
		//$(".viewDivCtl").hide(); 
		//$(".viewCtl2").hide();
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
	createQualityClaimSellMore = function() {    
		var contents = $("textarea[name=contents]","#boardQualityClaimSellMoreForm").val();  
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("상담내용을 기입해주세요.");
			return false;
		} 
		var tmpFacSubName=jQuery("#factorySub option:selected").text();
		$jq('input[name=factorySubName]').val(tmpFacSubName);
		var tmpclaimGubunSubName=jQuery("#claimGubunSub option:selected").text();
		$jq('input[name=claimGubunSubName]').val(tmpclaimGubunSubName);
		
		$("#blockMoreSell").ajaxLoadStart(); 
		
		$.post('<c:url value="/support/customer/customerQualityClaimSellMore/createQualityClaimSellMore.do"/>', $("#boardQualityClaimSellMoreForm").serialize())
		.success(function(data) { 
			loadBoardQualityClaimSellMoreList(); 
		})
		.error(function(event, request, settings) { alert("error"); $("#blockMoreSell").ajaxLoadComplete(); }); 
		
		return false;  
	};


	/*
	*  추가상담내용 수정을 한다. 
	*/
	updateQualityClaimSellMore = function(formName, linereplyId) { 
		var formObject = $("#" + formName).children("form"); 
		
		var contents = $("textarea[name=contents]",formObject).val(); 
		
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("상담내용을 기입해주세요");
			return false;
		}
		var tmpmfactorySubName=jQuery("#"+linereplyId+"_mfactorySub option:selected").text();
		var tmpmfactorySub=jQuery("#"+linereplyId+"_mfactorySub option:selected").val();
		$jq('input[name=mfactorySubName]').val(tmpmfactorySubName);
		$jq('input[name=mfactorySub]').val(tmpmfactorySub);
		var tmpmclaimGubunSubName=jQuery("#"+linereplyId+"_mclaimGubunSub option:selected").text();
		var tmpmclaimGubunSub=jQuery("#"+linereplyId+"_mclaimGubunSub option:selected").val();
		$jq('input[name=mclaimGubunSubName]').val(tmpmclaimGubunSubName);
		$jq('input[name=mclaimGubunSub]').val(tmpmclaimGubunSub);
		
		$("#blockMoreSell").ajaxLoadStart();
		
		$.post('<c:url value="/support/customer/customerQualityClaimSellMore/updateQualityClaimSellMore.do"/>', formObject.serialize())
		.success(function(data) { 
			loadBoardQualityClaimSellMoreList();
		})
		.error(function(event, request, settings) { 
			loadBoardQualityClaimSellMoreList(); 
		}); 
	    
		return false;  
	};
			
	/*
	*  추가상담내용 수정 폼을 화면에서 안보이게 한다.
	*/
	closeUpdateQualityClaimSellMoreForm = function(linereplyId) {  
		$("#" + linereplyId ).hide();
		$("div[name=buttonArea]").show();
		return false;  
	};

			
	/*
	*  작성자 모드로 추가상담내용을 삭제한다.
	*/		
	userDeleteBoardQualityClaimSellMore = function(itemId, linereplyId) {
		
		var itemType = "CL";
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) { 
			$("#blockMoreSell").ajaxLoadStart();
		    $.post("<c:url value='/support/customer/customerQualityClaimSellMore/userDeleteQualityClaimSellMore.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId,"itemType": itemType}) 
		    .success(function(data) { 
		    	loadBoardQualityClaimSellMoreList(); 
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
	adminDeleteBoardQualityClaimSellMore = function(itemId, linereplyId) {
		var itemType = "CL";
		$("#blockMoreSell").ajaxLoadStart();
		if(confirm("상담내용을 삭제하시겠습니까?")) {
		    $.post("<c:url value='/support/customer/customerQualityClaimSellMore/adminDeleteQualityClaimSellMore.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId, "itemType": itemType})  
		    .success(function(data) { 
		    	loadBoardQualityClaimSellMoreList(); 
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
	
	
	viewOpen = function(lineId){
		$("#viewDiv_"+lineId).show();
		$("#viewOpn_"+lineId).hide();
		$("#viewCld_"+lineId).show();
		iKEP.iFrameContentResize();
	}
	
	viewClose = function(lineId){
		$("#viewDiv_"+lineId).hide();
		$("#viewCld_"+lineId).hide();
		$("#viewOpn_"+lineId).show();
		iKEP.iFrameContentResize();
	}
	
	
})(jQuery); 
//-->
</script>		 
		<div class="guestbook_write"> 
			<form id="boardQualityClaimSellMoreForm" onsubmit="return false;" style="display:none;">
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
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
	                         <col width="10%"/>
	                         <col width="15%"/>
                        </colgroup>
						<tbody>
							<tr>
								<th>상담일</th>
								<td>
									<input name="counselDateSub"   id="counselDateSub" title="상담일" class="inputbox w50 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<th>공장</th>
								<td>
									<select title="공장분류" name="factorySub" id="factorySub">
				                        <option value=""  >선택</option>
				                        <option value="1" >진주</option>
				                        <option value="2" >울산</option>
				                        <option value="3" >대구</option>
				                    </select> 
				                    <input type="hidden" name="factorySubName" value=""/>
								</td>
								<th>제목</th>
								<td colspan="3"><input name="subjectSub" title="제목" class="inputbox w100" type="text" /></td>
								
							</tr>
							<tr>
								<th>지종</th>
								<td><input name="jijongSub" title="지종" class="inputbox w90" type="text"  /></td>
								<th>평량</th>
								<td><input name="weightSub" title="평량" class="inputbox w90" type="text"  /></td>
								<th>구분</th>
								<td>
									<select title="구분" name="claimGubunSub" id="claimGubunSub">
				                        <option value=""  >선택</option>
				                        <option value="1" >A/S</option>
				                        <option value="2" >B/S</option>
				                        <option value="3" >컴플레인</option>
				                    </select> 
				                    <input type="hidden" name="claimGubunSubName" value=""/>
								</td>
								<th>상담자</th>
								<td><input name="counselorSub" title="상담자" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">상담내역</th>
								<td colspan="6"><textarea name="contents" class="w100" title="상담내역" cols="" rows="10"></textarea></td>
								<td style="vertical-align:bottom;">
									<div class="blockButton"> 
					                    <ul>
					                        <li><a class="button" onclick="createQualityClaimSellMore();" href="#a"><span>등록</span></a></li>
					                    </ul>
					                </div>
								</td>
							</tr>
						</tbody>
					</table> 
				</div>
			</form>
		</div>    
	<c:forEach var="boardQualitySub" varStatus="varStatus" items="${searchResult.entity}">
			<c:choose>
		 		<c:when test="${boardQualitySub.linereplyDelete eq 1}">
		 			<p><span class="deletedItem"><ikep4j:message pre='${preList}' key='contents' post="deletecontents"/></span></p> 
				</c:when>
				<c:otherwise>
					<div class="blockDetail">
						<table>
							<caption></caption>
				                     <colgroup>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                         <col width="10%"/>
				                         <col width="15%"/>
				                     </colgroup>
							<tbody>
								<tr>
									<th>상담일</th>
									<td>${boardQualitySub.counselDateSub}</td>
									<th>공장</th>
									<td>
										<c:if test="${boardQualitySub.factorySubName == '선택'}"></c:if>
										<c:if test="${boardQualitySub.factorySubName != '선택'}">
											${boardQualitySub.factorySubName}
										</c:if>
									</td>
									<th>제목</th>
									<td colspan="3" style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;border-width:0px;">
										<table>
											<colgroup>
					                            <col width="95%"/>
					                            <col width="5%"/>
					                        </colgroup>
					                        <tbody>
												<tr>
													<td style="border-right-color:white;">${boardQualitySub.subjectSub}</td>
													<td style="border-left-color:white;">
														<span class="viewCtl1" id="viewOpn_${boardQualitySub.linereplyId}" name="viewPen_${boardQualitySub.linereplyId}">
														<a onclick="viewOpen(${boardQualitySub.linereplyId});" style="cursor:pointer;"><img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" align="absmiddle" alt="calendar"/></a>
														</span>
														<span class="viewCtl2" id="viewCld_${boardQualitySub.linereplyId}" name="viewCld_${boardQualitySub.linereplyId}" style="display:none;">
														<a onclick="viewClose(${boardQualitySub.linereplyId});" style="cursor:pointer;"><img src="<c:url value="/base/images/icon/ic_more_ar_2.gif"/>" align="absmiddle" alt="calendar"/></a>
														</span>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<span class="viewDivCtl" id="viewDiv_${boardQualitySub.linereplyId}" name="viewDiv_${boardQualitySub.linereplyId}" style="display:none;">
								<tr>
									<th>지종</th>
									<td>${boardQualitySub.jijongSub}</td>
									<th>평량</th>
									<td>${boardQualitySub.weightSub}</td>
									<th>구분</th>
									<td>
										<c:if test="${boardQualitySub.claimGubunSubName == '선택'}"></c:if>
										<c:if test="${boardQualitySub.claimGubunSubName != '선택'}">
											${boardQualitySub.claimGubunSubName}
										</c:if>
									</td>
									<th>상담자</th>
									<td>${boardQualitySub.counselorSub}</td>
								</tr>
								<tr>
									<th scope="row">상담내역</th>
									<td colspan="7">
										<% pageContext.setAttribute("newLineChar", "\n"); %> 
										<p name="contents">${fn:replace(boardQualitySub.contents, newLineChar, '<br/>')}</p>
									</td>
								</tr>
								</span>
							</tbody>
						</table> 
					</div>
					
					<div id="${boardQualitySub.linereplyId}UpdateForm"  name="updateForm" class="blockMoreSell_rewrite modify" style="display:none"> 
						<form name="updateForm" onsubmit="return false;"> 
							<input name="linereplyId" type="hidden" value="${boardQualitySub.linereplyId}" title="<ikep4j:message pre='${preList}' key='linereplyId'/>" /> 
							<div class="blockDetail">
							<table>
								<caption></caption>
		                        <colgroup>
		                             <col width="10%"/>
			                         <col width="15%"/>
			                         <col width="10%"/>
			                         <col width="15%"/>
			                         <col width="10%"/>
			                         <col width="15%"/>
			                         <col width="10%"/>
			                         <col width="15%"/>
		                        </colgroup>
								<tbody>
									<tr>
										<th>상담일</th>
										<td>
											<input name="mcounselDateSub"   id="mcounselDateSub" title="상담일" class="inputbox w50 datepicker" type="text" value="${boardQualitySub.counselDateSub}"  /> 
                                			<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
										</td>
										<th>공장</th>
										<td>
											<select title="공장분류" name="${boardQualitySub.linereplyId}_mfactorySub" id="${boardQualitySub.linereplyId}_mfactorySub">
						                        <option value=""  >선택</option>
						                        <option value="1" <c:if test="${boardQualitySub.factorySub eq '1'}">selected="selected"</c:if>>진주</option>
				                        		<option value="2" <c:if test="${boardQualitySub.factorySub eq '2'}">selected="selected"</c:if>>울산</option>
				                        		<option value="3" <c:if test="${boardQualitySub.factorySub eq '3'}">selected="selected"</c:if>>대구</option>
						                    </select> 
						                    <input type="hidden" name="mfactorySubName" value=""/>
						                    <input type="hidden" name="mfactorySub" value=""/>
										</td>
										<th>제목</th>
										<td colspan="3"><input name="subjectSub" title="제목" class="inputbox w100" type="text" value="${boardQualitySub.subjectSub}"  /></td>
									</tr>
									<tr>
										<th>지종</th>
										<td><input name="jijongSub" title="지종" class="inputbox w90" type="text" value="${boardQualitySub.jijongSub}" /></td>
										<th>평량</th>
										<td><input name="weightSub" title="평량" class="inputbox w90" type="text" value="${boardQualitySub.weightSub}" /></td>
										<th>구분</th>
										<td>
											<select title="구분" name="${boardQualitySub.linereplyId}_mclaimGubunSub" id="${boardQualitySub.linereplyId}_mclaimGubunSub">
						                        <option value=""  >선택</option>
				                        		<option value="1" <c:if test="${boardQualitySub.claimGubunSub eq '1'}">selected="selected"</c:if>>A/S</option>
						                        <option value="2" <c:if test="${boardQualitySub.claimGubunSub eq '2'}">selected="selected"</c:if>>B/S</option>
						                        <option value="3" <c:if test="${boardQualitySub.claimGubunSub eq '3'}">selected="selected"</c:if>>컴플레인</option>
						                    </select> 
						                    <input type="hidden" name="mclaimGubunSub" value=""/>
						                    <input type="hidden" name="mclaimGubunSubName" value=""/>
										</td>
										<th>상담자</th>
										<td><input name="counselorSub" title="상담자" class="inputbox w90" type="text" value="${boardQualitySub.counselorSub}"  /></td>
									</tr>
									<tr>
										
									</tr>
									<tr>
										<th scope="row">상담내역</th>
										<td colspan="6"><textarea name="contents" class="w100" title="상담내역" cols="" rows="10">${boardQualitySub.contents}</textarea></td>
										<td style="vertical-align:bottom;">
											<div class="blockButton"> 
							                    <ul>
							                        <ul> 
														<li><a class="button" onclick="updateQualityClaimSellMore('${boardQualitySub.linereplyId}UpdateForm',${boardQualitySub.linereplyId});"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
														<li><a class="button" onclick="closeUpdateQualityClaimSellMoreForm('${boardQualitySub.linereplyId}UpdateForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> 
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
						<c:when test="${clrole eq true || isSystemAdmin}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
		 					<a class="button" onclick="showUpdateFormSub('${boardQualitySub.linereplyId}UpdateForm');" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
		 					<c:if test="${boardQualitySub.masterSub!='Y'}">
		 						<a class="button" onclick="adminDeleteBoardQualityClaimSellMore('${boardQualitySub.itemId}', '${boardQualitySub.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
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
	<form id="boardQualityClaimSellMoreSearchForm" method="post" onsubmit="return false;"> 
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="boardQualityClaimSellMoreSearchForm" ajaxEventFunctionName="loadBoardQualityClaimSellMoreList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" displayYn="true"/>
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->  
	</form> 
<!--//blockMoreSell End--> 
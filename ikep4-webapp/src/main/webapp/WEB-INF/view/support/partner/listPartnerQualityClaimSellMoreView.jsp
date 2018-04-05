<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"  value="ui.lightpack.board.boardQualityClaimSellMore.listBoardQualityClaimSellMoreView" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardLinereply" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%> 
 
 <% pageContext.setAttribute("newLineChar", "\n"); %> 
<script type="text/javascript">
<!--  
(function($){	 
	$(document).ready(function() { 
	//	
		$("div.blockMoreSell_rewrite").hide(); 
		//$(".viewDivCtl").hide(); 
		//$(".viewCtl2").hide();
	});  
	deleteReadPermission =function(linereplyId) { 
		var $rPermissionList=$jq("#"+linereplyId).find("[name=readerList]");
		$jq('option:selected',$rPermissionList).remove();

	};	
	addReadPermission =function(linereplyId) { 
		// 조직도 팝업 테스트

		var items = [];
		var $sel = $jq("#"+linereplyId).find("[name=readerList]");
		
		$jq.each($sel.children(), function() {
			items.push($jq.data(this, "data"));
		});

		var callback = function(result){
			$sel.empty();
			$jq.each(result, function() {

				var tpl = "";
				
				switch(this.type) {
					case "group" : tpl = "addrBookItemGroup"; break;
					case "user" : tpl = "addrBookItemUser"; break;
					case "common" : tpl = "addrBookItemGroup"; break;
				}
				
				if(this.type=="group"){
					this.code="G:"+this.code;
				}else if(this.type=="common"){
					this.code="C:"+this.code;
				}else{
					this.id ="U:"+this.id;
				}
				
				var $option = $jq.tmpl(tpl, this).appendTo($sel);

				$jq.data($option[0], "data", this);
	
			})
		};
		iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:0}});	
	};	 
	
	$jq.template("addrBookItemUser", "<option value='\${id}'>\${userName}/\${jobTitleName}/\${teamName}</option>");
	$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");
	
	/*
	*  추가상담내용 수정 폼을 화면에서 보이게 한다.
	*/	
	showUpdateFormSub =function(linereplyId,lineCode) { 
		//$("div[name=updateForm]").hide(); 
		
		var contextObjet = $("#" + linereplyId).siblings("p[name=contents]");  
		var contextText = $(contextObjet).text();   
		//$("#" + linereplyId).find("textarea[name=contents]").val(contextText); 
		$("#" + linereplyId).show(); 
		$("#" + linereplyId).find("textarea[name=contents]").focus();
		$("div[name=buttonArea]").hide();
		$("#mcounselDateSub"+lineCode).datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		iKEP.iFrameContentResize();
		return false;  
	};
	
	showtlCommentFormSub =function(linereplyId) { 
		$("div[name=tlcommentForm]").hide(); 
		
		$("#" + linereplyId).show(); 
		$("div[name=buttonArea]").hide();
		iKEP.iFrameContentResize();
		return false;  
	};
	
	showofCommentFormSub =function(linereplyId) { 
		$("div[name=ofcommentForm]").hide(); 
		
		$("#" + linereplyId).show(); 
		$("div[name=buttonArea]").hide();
		iKEP.iFrameContentResize();
		return false;  
	};

	/*
	*  신규 답변을 저장한다.
	*/	
	createQualityClaimSellMore = function() {    
		var contents = $("textarea[name=counselContents]","#boardQualityClaimSellMoreForm").val();  
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("상담내용을 기입해주세요.");
			return false;
		} 
		var tmpFacSubName=jQuery("#factorySub option:selected").text();
		$jq('input[name=factorySubName]').val(tmpFacSubName);
		var tmpclaimGubunSubName=jQuery("#claimGubunSub option:selected").text();
		$jq('input[name=claimGubunSubName]').val(tmpclaimGubunSubName);
		
		$("#blockMoreSell").ajaxLoadStart(); 
		
		$.post('<c:url value="/support/partner/partnerQualityClaimSellMore/createQualityClaimSellMore.do"/>', $("#boardQualityClaimSellMoreForm").serialize())
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
		var contents = $("textarea[name=counselContents]",formObject).val(); 
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
		var tmpmcounselDateSub=jQuery("#mcounselDateSub"+linereplyId).val();
		$jq('input[name=mcounselDateSub]').val(tmpmcounselDateSub);
		
		$("#blockMoreSell").ajaxLoadStart();
		
		$.post('<c:url value="/support/partner/partnerQualityClaimSellMore/updateQualityClaimSellMore.do"/>', formObject.serialize())
		.success(function(data) { 
			loadBoardQualityClaimSellMoreList();
		})
		.error(function(event, request, settings) { 
			loadBoardQualityClaimSellMoreList(); 
		}); 
	    
		return false;  
	};
	
	tlcommentQualityClaimSellMore = function(formName, linereplyId) { 
		var formObject = $("#" + formName).children("form"); 
		
		var contents = $("textarea[name=comment1]",formObject).val(); 
		
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("Comment를 기입해주세요");
			return false;
		}
		
		$("#blockMoreSell").ajaxLoadStart();
		
		$.post('<c:url value="/support/partner/partnerQualityClaimSellMore/tlcommentQualityClaimSellMore.do"/>', formObject.serialize())
		.success(function(data) { 
			loadBoardQualityClaimSellMoreList();
		})
		.error(function(event, request, settings) { 
			loadBoardQualityClaimSellMoreList(); 
		}); 
	    
		return false;  
	};
	
	ofcommentQualityClaimSellMore = function(formName, linereplyId) { 
		var formObject = $("#" + formName).children("form"); 
		
		var contents = $("textarea[name=comment2]",formObject).val(); 
		
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("Comment를 기입해주세요");
			return false;
		}
		
		$("#blockMoreSell").ajaxLoadStart();
		
		$.post('<c:url value="/support/partner/partnerQualityClaimSellMore/ofcommentQualityClaimSellMore.do"/>', formObject.serialize())
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
		    $.post("<c:url value='/support/partner/partnerQualityClaimSellMore/userDeleteQualityClaimSellMore.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId,"itemType": itemType}) 
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
		    $.post("<c:url value='/support/partner/partnerQualityClaimSellMore/adminDeleteQualityClaimSellMore.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId, "itemType": itemType})  
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
		$(".viewDiv_"+lineId).show();
		$("#viewOpn_"+lineId).hide();
		$("#viewCld_"+lineId).show();
		iKEP.iFrameContentResize();
	};
	
	viewClose = function(lineId){
		$(".viewDiv_"+lineId).hide();
		$("#viewCld_"+lineId).hide();
		$("#viewOpn_"+lineId).show();
		iKEP.iFrameContentResize();
	};
	
	allViewOpen = function(){
		$("#allViewOpenBt").hide();
		$("#allViewCloseBt").show();
		$("[class^=viewDiv]").show();
		$(".viewCtl1").hide();
		$(".viewCtl2").show();
		iKEP.iFrameContentResize();
	};
	
	allViewClose = function(){
		$("#allViewCloseBt").hide();
		$("#allViewOpenBt").show();
		$("[class^=viewDiv]").hide();
		$(".viewCtl2").hide();
		$(".viewCtl1").show();
		iKEP.iFrameContentResize();
	};
	
	
	
	if("${directSubId}" != ""){
		$("[class^=viewDiv]").hide();
		$("#allViewCloseBt").hide();
		$("#allViewOpenBt").show();
		viewOpen("${directSubId}");
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
				<input type="hidden" id="subPartnerName" name="subPartnerName" value="${subPartnerName}"/>
				
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
									<input name="counselDate"   id="counselDate" title="상담일" class="inputbox w50 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
								</td>
								<th>면담형태</th>
								<td>
									<select title="면담형태" name="counselType" id="counselType">
				                        <option value=""  >선택</option>
				                        <option value="1" >유선</option>
		                        		<option value="2" >방문</option>
				                    </select> 
								</td>
								<th>피상담자</th>
								<td><input name="requestor" title="피상담자" class="inputbox w90" type="text" value=""  /></td>
								<th>상담자</th>
								<td><input name="counselor" title="상담자" class="inputbox w90" type="text" value="${user.userName}" readonly="readonly" /></td>
							</tr>
							<tr>
								<th>제목</th>
								<td colspan="7"><input name="counselTitle" title="제목" class="inputbox w100" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">상담내역</th>
								<td colspan="6"><textarea name="counselContents" class="w100" title="상담내역" cols="" rows="30"></textarea></td>
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
		<div class="blockButton"> 
                    <ul>
                    	<li><a id="allViewOpenBt" onclick="allViewOpen();" class="button" href="#a" style="display:none;"><span>전체열기</span></a></li>
                    	<li><a id="allViewCloseBt" onclick="allViewClose();" class="button" href="#a"><span>전체닫기</span></a></li>
                    </ul>
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
				                     	 <col width="5%"/>
				                         <col width="5%"/>
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
									<th>순번:${linereplyCount-varStatus.index}
									</th>
									<th>상담일</th>
									<td>${boardQualitySub.counselDate}</td>
									<th>면담형태</th>
									<td>
										<c:choose>
											<c:when test="${boardQualitySub.counselType == '1'}">
											유선
											</c:when>
											<c:when test="${boardQualitySub.counselType == '2'}">
											방문
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</td>
									<th>피상담자</th>
									<td>${boardQualitySub.requestor}</td>
									<th>상담자</th>
									<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;border-width:0px;">
										<table>
											<colgroup>
					                            <col width="90%"/>
					                            <col width="10%"/>
					                        </colgroup>
					                        <tbody>
												<tr>
													<td style="border-right-color:white;">${boardQualitySub.counselor}</td>
													<td style="border-left-color:white;">
														<span class="viewCtl1" id="viewOpn_${boardQualitySub.linereplyId}" name="viewOpn_${boardQualitySub.linereplyId}">
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
								<tr class="viewDiv_${boardQualitySub.linereplyId}" >
									<th colspan="2">제목</th>
									<td colspan="7">
										${boardQualitySub.counselTitle}
									</td>
								</tr>
								<tr class="viewDiv_${boardQualitySub.linereplyId}" >
									<th scope="row" colspan="2">상담내역</th>
									<td colspan="7">
										<p name="counselContents">${fn:replace(boardQualitySub.counselContents, newLineChar, '<br/>')}</p>
									</td>
								</tr>
								<tr class="viewDiv_${boardQualitySub.linereplyId}" >
									<th scope="row" colspan="2">팀장Comment</th>
									<td colspan="7">
										<font style="color:#003366;font-weight:bold;">${boardQualitySub.commentuser1}</font> <font style="color:#999;font-size:0.9em;">${boardQualitySub.commentuserTeam1}</font> <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> <font style="color:#999;font-size:0.9em;"><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${boardQualitySub.commentRegistDate1}"/></font>
										<p name="comment1">${fn:replace(boardQualitySub.comment1, newLineChar, '<br/>')}</p>
									</td>
								</tr>
								<tr class="viewDiv_${boardQualitySub.linereplyId}" >
									<th scope="row" colspan="2">임원Comment</th>
									<td colspan="7">
										<font style="color:#003366;font-weight:bold;">${boardQualitySub.commentuser2}</font> <font style="color:#999;font-size:0.9em;">${boardQualitySub.commentuserTeam2}</font> <img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /> <font style="color:#999;font-size:0.9em;"><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${boardQualitySub.commentRegistDate2}"/></font>
										<p name="comment2">${fn:replace(boardQualitySub.comment2, newLineChar, '<br/>')}</p>
									</td>
								</tr>
							</tbody>
						</table> 
					</div>
					
					<div id="${boardQualitySub.linereplyId}UpdateForm"  name="UpdateForm" class="blockMoreSell_rewrite modify" style="display:none"> 
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
											<input name="mcounselDateSub${boardQualitySub.linereplyId}"   id="mcounselDateSub${boardQualitySub.linereplyId}" title="상담일" class="inputbox w50 datepicker" type="text" value="${boardQualitySub.counselDate}"  /> 
                                			<input type="hidden" name="mcounselDateSub" value=""/>
                                			<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
										</td>
										<th>면담형태</th>
										<td>
											<select title="면담형태" name="counselType" id="counselType">
						                        <option value=""  >선택</option>
						                        <option value="1" <c:if test="${boardQualitySub.counselType eq '1'}">selected="selected"</c:if>>유선</option>
				                        		<option value="2" <c:if test="${boardQualitySub.counselType eq '2'}">selected="selected"</c:if>>방문</option>
						                    </select> 
										</td>
										<th>피상담자</th>
										<td><input name="requestor" title="피상담자" class="inputbox w90" type="text" value="${boardQualitySub.requestor}"  /></td>
										<th>상담자</th>
										<td><input name="counselor" title="상담자" class="inputbox w90" type="text" value="${boardQualitySub.counselor}" readonly="readonly" /></td>
									</tr>
									<tr>
										<th>제목</th>
										<td colspan="7"><input name="counselTitle" title="제목" class="inputbox w100" type="text" value="${boardQualitySub.counselTitle}"  /></td>
									</tr>
									<tr>
										<th scope="row">상담내용</th>
										<td colspan="6"><textarea name="counselContents" class="w100" title="상담내역" cols="" rows="30">${boardQualitySub.counselContents}</textarea></td>
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
					
					<div id="${boardQualitySub.linereplyId}tlCommentForm"  name="tlcommentForm" class="blockMoreSell_rewrite modify" style="display:none"> 
						<form name="tlcommentForm" onsubmit="return false;"> 
							<input name="linereplyId" type="hidden" value="${boardQualitySub.linereplyId}" title="<ikep4j:message pre='${preList}' key='linereplyId'/>" /> 
							<input name="itemId" type="hidden" value="${boardQualitySub.itemId}" />
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
										<th scope="row">팀장Comment</th>
										<td colspan="6"><textarea name="comment1" class="w100" cols="" rows="5">${boardQualitySub.comment1}</textarea></td>
										<td style="vertical-align:bottom;">
											<div class="blockButton"> 
							                    <ul>
							                        <ul> 
														<li><a class="button" onclick="tlcommentQualityClaimSellMore('${boardQualitySub.linereplyId}tlCommentForm',${boardQualitySub.linereplyId});"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
														<li><a class="button" onclick="closeUpdateQualityClaimSellMoreForm('${boardQualitySub.linereplyId}tlCommentForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> 
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
					
					<div id="${boardQualitySub.linereplyId}ofCommentForm"  name="ofcommentForm" class="blockMoreSell_rewrite modify" style="display:none"> 
						<form name="ofcommentForm" onsubmit="return false;"> 
							<input name="linereplyId" type="hidden" value="${boardQualitySub.linereplyId}" title="<ikep4j:message pre='${preList}' key='linereplyId'/>" /> 
							<input name="itemId" type="hidden" value="${boardQualitySub.itemId}" />
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
										<th scope="row">임원Comment</th>
										<td colspan="6"><textarea name="comment2" class="w100" cols="" rows="5">${boardQualitySub.comment2}</textarea></td>
										<td style="vertical-align:bottom;">
											<div class="blockButton"> 
							                    <ul>
							                        <ul> 
														<li><a class="button" onclick="ofcommentQualityClaimSellMore('${boardQualitySub.linereplyId}ofCommentForm',${boardQualitySub.linereplyId});"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
														<li><a class="button" onclick="closeUpdateQualityClaimSellMoreForm('${boardQualitySub.linereplyId}ofCommentForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> 
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
							<c:if test="${boardQualitySub.commentAuthUser1==user.userId || isSystemAdmin}">
							<a class="button" onclick="showtlCommentFormSub('${boardQualitySub.linereplyId}tlCommentForm');" ><span>팀장Comment</span></a>
							</c:if>
							<c:if test="${boardQualitySub.commentAuthUser2==user.userId || isSystemAdmin}">
							<a class="button" onclick="showofCommentFormSub('${boardQualitySub.linereplyId}ofCommentForm');" ><span>임원Comment</span></a>
							</c:if>
							<c:if test="${boardQualitySub.registerId==user.userId || isSystemAdmin}">
		 					<a class="button" onclick="showUpdateFormSub('${boardQualitySub.linereplyId}UpdateForm','${boardQualitySub.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
	 						<a class="button" onclick="adminDeleteBoardQualityClaimSellMore('${boardQualitySub.itemId}', '${boardQualitySub.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
							</c:if>
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
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="prefix" value="ui.support.customer.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.customer.manInfoItemList" />
<script type="text/javascript">
<!--
	var bbsIframe; // 부모 iframe
	var bbsIsIframe = 0; // iframe 존재 여부
	var isLayout; // 레이아웃 보기 여부
	var bbsLayout = null;
	var layoutType = "n"; // n:없음, v:가로보기, h:세로보기
	var excelBtnCnt = 0;
	
	function mouseOver(obj){
		
		//obj.className = "bgSelected";
		//obj.style.backgroundColor="#edf2f5";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).attr("style","background-color:#edf2f5;");
		});	
	}
	function mouseOut(obj){
		//obj.className = "";
		//obj.style.backgroundColor="";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).attr("style","background-color:none;");
		});	
	}
	
	function iframeReflash() {
		location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do?boardId=${board.boardId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";
	};

	(function($) {
		/* window risize 시 Contaner 높이 조절 */
		resizeContanerHeight = function() {
			var docHeight = 0;
			var adjustHeight = 20;
			var $lefMenu;
			var $Container = $('#container');

			if (isLayout) {
				// iframe 안에서 호출된 경우
				if (bbsIsIframe > 0) {
					docHeight = $(parent).height();

					$lefMenu = $("#leftMenu", parent.document);
					var leftMenuPostion = $lefMenu.offset();
					if ($lefMenu.length > 0) {
						$lefMenu.height(docHeight - leftMenuPostion.top);
					}
					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height(docHeight - $Container.offset().top
							- adjustHeight);
					// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
					bbsIframe.height(docHeight - 17);
				} else {
					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height($(window).height()
							- $Container.offset().top - adjustHeight);
				}
			}

			if (bbsLayout != null) {
				bbsLayout.resizeAll();
			}
		}

		/* Contaner & iframe 높이 조절 */
		setContanerHeight = function() {
			var docHeight = 0;
			var adjustHeight = 20;
			var $lefMenu;
			var $Container = $('#container');

			// layout 존재하므로 layout destroy 함수 호출시
			if (isLayout) {
				// iframe 안에서 호출된 경우
				if (bbsIsIframe > 0) {
					docHeight = $(parent).height();
					$lefMenu = $("#leftMenu", parent.document);
					var leftMenuPostion = $lefMenu.offset();
					if ($lefMenu.length > 0) {
						$lefMenu.height(docHeight - leftMenuPostion.top).css({
							overflowY : "auto",
							overflowX : "hidden"
						});
					}

					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height(docHeight - $Container.offset().top
							- adjustHeight);
					// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
					bbsIframe.height(docHeight - 19);
				} else {
					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height($(window).height()
							- $Container.offset().top - adjustHeight);
				}

			} else { // layout 없으므로 layout 함수 호출시
				// iframe 안에서 호출된 경우
				if (bbsIsIframe > 0) {
					$lefMenu = $("#leftMenu", parent.document);
					$lefMenu.css({
						overflowY : "",
						overflowX : ""
					});
					bbsIframe.height($(document).height());
				}

			}

			if (bbsLayout != null) {
				bbsLayout.resizeAll();
			}

		}

		$(window).bind("unload", function() {
			bbsIframe = null;
			bbsIsIframe = null;
			isLayout = null;
			bbsLayout = null;
			layoutType = null;

			contextRoot = null;
			ZeroClipboard = null;
			cafeCkeditorConfig = null;
			fullCkeditorConfig = null;

			iKEP = null;
			iKEPLang = null;

			simpleCkeditorConfig = null;
			$jq = null;
			jQuery = null;
		});

		$(document)
				.ready(
						function() {
							setUser = function(data) {
								var managerName = "";
								var userId = "";
								
								$jq.each(data, function() {
								managerName = $jq.trim(this.userName);
								userId = $jq.trim(this.empNo);	
									
									cachedTdObj.find('.searchUserName').val(managerName);
									cachedTdObj.find('.searchUserId').val(userId);
								});
							};
							
							// [검색-직원선택] ================== Start
							$jq('.addrSearchBtn').click( function() {
								cachedTdObj = $(this).parent("td");
							    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
								cachedTdObj.find('.searchUserName').trigger("keypress");
							});
							
							$jq('.searchUserName').keypress( function(event) {
								cachedTdObj = $(this).parent("td");
					            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
								iKEP.searchUser2(event, "N", setUser);			
							});
							
							//달력
							$("input[name=searchStartRegDate1]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
							$("input[name=searchEndRegDate1]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
						
							

							if (window.parent.menuMark != null) {
								window.parent.menuMark("${board.boardId}");
							}

							sort = function(sortColumn, sortType) {
								$("#searchBoardItemForm input[name=actionType]")
										.val("sort");
								$("#searchBoardItemForm input[name=sortColumn]")
										.val(sortColumn);
								$("#searchBoardItemForm input[name=sortType]")
										.val(sortType == 'ASC' ? 'DESC' : 'ASC');
								$("#searchBoardItemButton").click();
							};
							
							//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
// 					        $jq('#userName').keypress( function(event) {
// 					        	if(event.keyCode == '13'){
// 					        		$("#searchBoardItemForm input[name=actionType]").val("search");
// 									$("#searchBoardItemForm").submit();
// 					        		return false;
// 					        	}
// 							});
							
					      //입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
// 					        $jq('#managerName').keypress( function(event) {
// 					        	if(event.keyCode == '13'){
// 					        		$("#searchBoardItemForm input[name=actionType]").val("search");
// 									$("#searchBoardItemForm").submit();
// 					        		return false;
// 					        	}
// 							});

							//search 동작
							$("#searchBoardItemButton").click(function() {
								$("#searchBoardItemForm input[name=actionType]").val("search");
								$("#searchBoardItemForm").submit();
								return false;
							});

							//페이지 할 row수 설정
							$("#searchBoardItemForm select[name=pagePerRecord]").change(function() {
								$("#searchBoardItemForm input[name=actionType]").val("pagePerRecord");
								$("#searchBoardItemForm").submit();
								return false;
							});
							

							/* 기본 layout 설정 여부 */
							isLayout = false;
							layoutType = "n";

							/* 윈도우 resize 이벤트 */
							$(window).bind("resize", resizeContanerHeight);

							$("a.boardItem")
									.click(
											function() {
												$("*.boardItemLine")
														.removeClass(
																"bgSelected");
												$(this).parents(
														"*.boardItemLine")
														.addClass("bgSelected");
												if (!isLayout) {
													<c:choose>
													<c:when test="${popupYn}">
													iKEP
															.popupOpen(
																	$(this)
																			.attr(
																					"href")
																			+ "&popupYn=true&searchConditionString=${searchConditionString}&popupYn=${popupYn}",
																	{
																		width : 1000,
																		height : 600,
																		resizable : false,
																		callback : function(
																				result) {
																			location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId="
																					+ result[0]
																					+ "&searchConditionString="
																					+ result[1]
																					+ "&popupYn=${popupYn}";
																		}
																	}, "param");

													return false;
													</c:when>
													<c:otherwise>
													return true;
													</c:otherwise>
													</c:choose>

												} else if (layoutType == "v") {
													$("#layoutContent")
															.html("");
													$("#layoutContent")
															.load(
																	$(this)
																			.attr(
																					"href")
																			+ "&layoutType=layoutVertical");
													return false;

												} else if (layoutType == "h") {
													$("#layoutContent")
															.html("");
													$("#layoutContent")
															.load(
																	$(this)
																			.attr(
																					"href")
																			+ "&layoutType=layoutHorizental");
													return false;
												} else {
													return true;
												}

												return false;
											});

							$("a.boardItem").each(function() {
								$(this).attr("title", $(this).html());
							});

							$("*.boardItem", "span.deletedItem").css("color",
									"red");

						});
	})(jQuery);
	
	
	function goDetail(id){
		
		location.href="<c:url value='/support/publication/publication/modifyPublicationView.do?id='/>"+id;
		
	}
	
	function enterSearch() {
       	if(event.keyCode == '13'){
       		$("#searchBoardItemButton").click(function() {
				$("#searchBoardItemForm input[name=actionType]").val("search");
				$("#searchBoardItemForm").submit();
				return false;
			});
       	}
	}
	
//-->
</script>


<h1 class="none">컨텐츠영역</h1>

<form id="searchBoardItemForm" method="post" action="<c:url value='/approval/collaboration/legal/apprContractList.do'/>">
	<spring:bind path="searchCondition.sortColumn">
		<input name="${status.expression}" type="hidden"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.sortType">
		<input name="${status.expression}" type="hidden"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.actionType">
		<input name="${status.expression}" type="hidden"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<%--<spring:bind path="searchCondition.type">
		<input name="${status.expression}" type="hidden" id="${status.expression}"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<spring:bind path="searchCondition.groupId">
		<input name="${status.expression}" type="hidden" id="${status.expression}"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> --%>

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>계약서 검토요청 내역</h2>
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${searchCondition.pageNumList}">
						<option value="${code.key}"
							<c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach>
				</select>
			</spring:bind>
			<!-- div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div-->
			<div class="totalNum">
				<ikep4j:message pre='${preSearch}' key='pageCount.info' />
				<span> ${legalResultList.recordCount}</span>
			</div>
		</div>
			
	</div>
	<!--//tableTop End-->
	
	<div class="tableDesciption" style="position:relative;">
		<table summary="게시판 설명">
		<caption></caption>
		<tbody>
			<tr>
			<td class="valign_top">
				<div class="floatLeft"><span class="ic_desc"></span></div>
			</td>
			<td>
		계약서 검토 요청서 목록입니다. 
			</td>
			</tr>
		</tbody>
		</table>
     <!--blockButton Start-->
       <div class="blockButton" style="position:absolute; right:0; bottom:0px; padding-top:10px;"> 
           <ul>
               <li><a class="button" id="searchBoardItemButton" href="#"><span>검색</span></a></li>
               <li><a class="button" href="<c:url value='/approval/collaboration/legal/apprContractCreate.do'/>"><span>신규</span></a></li>
           </ul>
       </div>
       <div class="blockBlank_10px"></div>	
	</div>
	<div class="corner_RoundBox03">
		<table summary="tableSearch">
			<tbody>
				<tr>
					<th scope="row" width="5%">의뢰기간</th>
					<td width="20%">
		               <input name="searchStartRegDate1" id="searchStartRegDate1" title="의뢰 시작일" class="inputbox w33 datepicker" type="text" value="${searchCondition.searchStartRegDate1}" />
		           	   <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
		           	   - 
		           	   <input name="searchEndRegDate1" id="searchEndRegDate1" title="의뢰 종료일" class="inputbox w33 datepicker" type="text" value="${searchCondition.searchEndRegDate1}" />
		           	   <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>		
                    </td>
                    <th scope="row" width="5%">의뢰자</th>
                    <td width="15%">
                        <input type="text" name="userName" id="userName" class="inputbox searchUserName"  title='의뢰자' size="12" value="${searchCondition.userName}" />
	              		<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic addrSearchBtn" href="#a">
							<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
						</a>
                    </td>         
                    <th scope="row" width="5%">담당자</th>
                    <td width="15%">
                        <input type="text" name="managerName" id="managerName" class="inputbox searchUserName" title='담당자' size="12" value="${searchCondition.managerName}" />
                    	<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic addrSearchBtn" href="#a">
       						<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
   						</a>
                    </td>
                    <th scope="row" width="5%">진행상태</th>
                    <td width="15%">
	               <spring:bind path="searchCondition.pagePerRecord">
<%-- 	               	   <select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'> --%>
					   <select name="processStatus" id="processStatus" style="width: 120px;">
					  		<option value="">전체</option>
					  		<c:forEach var="codeList" items="${codeList}" varStatus="status">
								<option value="${codeList.comCd}" <c:if test="${codeList.comCd eq searchCondition.processStatus}">selected="selected"</c:if> >
									${codeList.comNm}
								</option>
							</c:forEach>
					    </select>
				    </spring:bind>
				    </td>    
				</tr>														
			</tbody>
		</table>
	          	 
	</div>
	<!--//tableTop End-->					
	<div class="clear"></div>

	<div class="blockListTable">
		<table summary="계약서">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="5%">NO</th>
					<th scope="col" width="7%">
						<a onclick="sort('APPR_REQ_DATE', '<c:if test="${searchCondition.sortColumn eq 'APPR_REQ_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
						의뢰일
						</a>
					<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'APPR_REQ_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'APPR_REQ_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
					</c:choose> 
					</th>
					<th scope="col" width="20%">
						<a onclick="sort('CONTRACT_TITLE', '<c:if test="${searchCondition.sortColumn eq 'CONTRACT_TITLE'}">${searchCondition.sortType}</c:if>');"  href="#a">
							제목
						</a>
					<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'CONTRACT_TITLE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'CONTRACT_TITLE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
					</c:choose>    
					</th>
					<th scope="col" width="8%">
						<a onclick="sort('REQ_EMP_NAME', '<c:if test="${searchCondition.sortColumn eq 'REQ_EMP_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
							의뢰자
						</a>
					<c:choose>
						<c:when test="${searchCondition.sortColumn eq 'REQ_EMP_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
						<c:when test="${searchCondition.sortColumn eq 'REQ_EMP_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
					</c:choose>  
					</th>
					<th scope="col" width="8%">
						 <a onclick="sort('RCV_DRAFT_EMP_NAME', '<c:if test="${searchCondition.sortColumn eq 'RCV_DRAFT_EMP_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                          담당자
	                     </a>
                    <c:choose>
					<c:when test="${searchCondition.sortColumn eq 'RCV_DRAFT_EMP_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'RCV_DRAFT_EMP_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
			 		</c:choose>
					</th>
					<th scope="col" width="8%">
						 <a onclick="sort('PROCESS_STATUS_NAME', '<c:if test="${searchCondition.sortColumn eq 'PROCESS_STATUS_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                          진행상태
	                     </a>
                    <c:choose>
					<c:when test="${searchCondition.sortColumn eq 'PROCESS_STATUS_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					<c:when test="${searchCondition.sortColumn eq 'PROCESS_STATUS_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
			 		</c:choose>
					</th>
					<th scope="col" width="5%">
	                               이력
					</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${legalResultList.emptyRecord}">
						<tr>
							<td colspan="7" class="emptyRecord">
							조회된 데이터가 없습니다.
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="resultList" items="${legalResultList.entity}"
							varStatus="status">
							<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);" >
								<td>${searchCondition.recordCount
									-((searchCondition.pageIndex - 1) *
									searchCondition.pagePerRecord) - status.index}</td>
								<td><ikep4j:timezone date='${resultList.apprReqDate}'/></td>
								<td class="textLeft" >
								<a href="<c:url value='/approval/collaboration/legal/apprContractDetail.do?id=${resultList.contractMgntNo}&processStatus=${resultList.processStatus}'/>">${resultList.contractTitle}
								</td>
								<td>${resultList.reqEmpName}</td>
								<td>${resultList.rcvDraftEmpNo}</td>
								<td>${resultList.processStatusName}</td>
								<td>${resultList.cnt}</td>
								<%-- 
								<td>${manInfoItem.jobTitle}</td>
								<td><div class="ellipsis"><a title="${manInfoItem.registerName} ${manInfoItem.user.jobTitleName} ${manInfoItem.user.teamName}" href="#a" onclick="iKEP.showUserContextMenu(this, '${manInfoItem.registerId}', 'bottom')">${manInfoItem.user.userName} ${manInfoItem.user.jobTitleName}</a></div>
								</td> --%>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<!--Page Numbur Start-->
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchBoardItemButton"
				pageIndexInput="${status.expression}"
				searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden"
				value="${status.value}"
				title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind>
		<!--//Page Numbur End-->
		<div class="blockBlank_10px"></div>	
	</div>
	<!--//blockListTable End-->

</form>
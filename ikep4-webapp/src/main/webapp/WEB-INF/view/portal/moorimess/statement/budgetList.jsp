<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

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
	var tmpWindowSize = 0;

	function mouseOver(obj) {

		//obj.className = "bgSelected";
		//obj.style.backgroundColor="#edf2f5";
		var tds = $jq(obj).find("td");
		$jq.each(tds, function() {
			//$jq(this).attr("style", "background-color:#edf2f5;");
			$jq(this).css("background-color","#edf2f5");
		});
	}
	function mouseOut(obj) {
		//obj.className = "";
		//obj.style.backgroundColor="";
		var tds = $jq(obj).find("td");
		$jq.each(tds, function() {
			//$jq(this).attr("style", "background-color:none;");
			$jq(this).css("background-color","");
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
			refsh();
		}
		
		refsh = function() {
			if(tmpWindowSize != 0 && tmpWindowSize != $(parent).height()){ 
				$("#searchButton").trigger("click");
			}
			tmpWindowSize = $(parent).height();	
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
							$jq("#date1").datepicker().next().eq(0).click(
									function() {
										$jq(this).prev().eq(0).datepicker(
												"show");
									});
							$jq("#date2").datepicker().next().eq(0).click(
									function() {
										$jq(this).prev().eq(0).datepicker(
												"show");
									});

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

							/* 기본 layout 설정 여부 */
							isLayout = false;
							layoutType = "n";

							/* 윈도우 resize 이벤트 */
							$(window).bind("resize", resizeContanerHeight);

							

							//분기 radio 버튼 바인딩
							$("input[name=quarter]:radio").change(function() {
								var year = "${thisYear}";
								switch ($(this).val()) {
								case '1':
									$("#date1").val(year + ".01.01");
									$("#date2").val(year + ".03.31");
									break; //1분기
								case '2':
									$("#date1").val(year + ".04.01");
									$("#date2").val(year + ".06.30");
									break; //2분기
								case '3':
									$("#date1").val(year + ".07.01");
									$("#date2").val(year + ".09.30");
									break; //3분기
								case '4':
									$("#date1").val(year + ".10.01");
									$("#date2").val(year + ".12.31");
									break; //4분기							
								}
							});
							
							//초기 분기 세팅
							var setQuater = function(quarter){
								//alert(quarter);
								$("input[name=quarter]:radio:[value="+quarter+"]").attr("checked",true).change();
							}
							//함수 호출
							setQuater("${quarter}");
							
							//검색버튼 바인딩
							$jq("#searchBoardItemButton").click(function(){
								statementSearch();
							});
							
							//검색버튼 바인딩
							$jq("#searchButton").click(function(){
								statementSearch();
							});
							
							//계정과목 엔터 바인딩 
							$jq("input[name=iracct]").keydown(function(e){
								if(e.keyCode=="13"){
									statementSearch();
								}
							})
							
							
							//회사검색 체크박스 검색후 재설정
							searchAfterCheckReset();
						
						//table font-size set
						$(".blockListTable").find("table").attr("style","font-size:11px;");
						
						});//document.ready
						
						//검색
						statementSearch = function (){
							//검색 날짜 세팅
							$ifspmon = $jq('#date1').val();
							$itspmon = $jq('#date2').val();
							
							// 조회하는 기간의 년도가 다를 경우 
							if($ifspmon.substring(0,4)!=$itspmon.substring(0,4)){
								alert("같은 년도 예산 계획/실적만 조회 할 수 있습니다.");
								return;
							}
							if($ifspmon.replace(".","").substring(0,6)>$itspmon.replace(".","").substring(0,6)){
								alert("조회 시작기간이 종료기간 보다 큽니다.");
								return;
							}
							//회사 체크박스 세팅
							if($jq("input[name=mCheck]").is(":checked")){
								$jq("input[name=im100]").val($jq("input[name=mCheck]").val());
							}
							if($jq("input[name=sCheck]").is(":checked")){
								$jq("input[name=is100]").val($jq("input[name=sCheck]").val());
							}
							if($jq("input[name=pCheck]").is(":checked")){
								$jq("input[name=ip100]").val($jq("input[name=pCheck]").val());
							}
							
							if($ifspmon=="" && $itspmon=="") {
								$jq("#searchBoardItemForm").submit();
							} else {
								$ifspmon = $jq("#date1").val();
								if($ifspmon.length < 10) {
									alert("조회 시작일이 정합한 날짜 형식이 아닙니다");
									return;
								} else {
									$jq('#searchBoardItemForm>input[name=ifspmon]').val($jq("#date1").val());
								}
								
								$itspmon = $jq("#date2").val();
								if($itspmon.length < 10) {
									alert("조회 종료이 정합한 날짜 형식이 아닙니다");
									return;
								} else {
									$jq('#searchBoardItemForm>input[name=itspmon]').val($jq("#date2").val());
								}
								
								$jq("#searchBoardItemForm input[name=actionType]").val("search");
								$jq("#searchBoardItemForm").submit();
							}
						};
						
						//검색후 체크박스 리셋
						searchAfterCheckReset = function(){
							if("${statementSearchCondition.im100}"=="X"){
								$jq("input[name=mCheck]").attr("checked",true);
							}
							if("${statementSearchCondition.is100}"=="X"){
								$jq("input[name=sCheck]").attr("checked",true);
							}
							if("${statementSearchCondition.ip100}"=="X"){
								$jq("input[name=pCheck]").attr("checked",true);
							}
						}
						
							
	})(jQuery);
	
	//디테일 팝업 호출
	function budgetViewPop(ibukrs,ispmon,iracct,irzzkostl,ircntr){
	
		var url = iKEP.getContextRoot()
		+ '/portal/moorimess/statementCommon/budgetView.do?ibukrs='+ibukrs+'&ispmon='+ispmon+'&iracct='+iracct+'&irzzkostl='+irzzkostl+'&ircntr='+ircntr;
		
		iKEP.popupOpen(url, {
			width : 1000,
			height : 500
		}, "budgetView");
	} 
//-->
</script>






<h1 class="none">컨텐츠영역</h1>
<form id="searchBoardItemForm" method="post"
	action="<c:url value='/portal/moorimess/statementCommon/budgetList.do'/>">
		<input type="hidden" name="ifspmon"/>
		<input type="hidden" name="itspmon"/>  	
		<input type="hidden" name="ip100"/>
		<input type="hidden" name="is100"/>
		<input type="hidden" name="im100"/>
		<input type="hidden" name="idParam" />
		<input type="hidden" name="isInit" value="no"/>

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>예산 계획/실적 조회</h2>
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<div class="blockSearch1">
		<div class="corner_RoundBox03">
			<table summary="tableSearch">
				<tbody>
					<tr>
						<th scope="row" width="7%">회사코드</th>
						<td width="40%" style="vertical-align: middle;">
							<input name="sCheck" <c:if test="${statementSearchCondition.is100 eq X}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align: middle;" />무림페이퍼
							<input name="pCheck" <c:if test="${statementSearchCondition.ip100 eq X}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align: middle;" />무림P&P
							<input name="mCheck" <c:if test="${statementSearchCondition.im100 eq X}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align: middle;" />무림SP
						</td>
						<th scope="row" width="7%">기간(월)</th>
						<td width="40%"><input name="date1" id="date1" title="조회시작일"
							class="inputbox w10 datepicker" type="text"
							value="${statementSearchCondition.ifspmon}"
							size="9" /> <img
							src="<c:url value="/base/images/icon/ic_cal.gif"/>"
							align="absmiddle" alt="calendar" style="cursor: pointer;" />&nbsp;~&nbsp;
							<input name="date2" id="date2" title="조회종료일"
							class="inputbox w10 datepicker" type="text"
							value="${statementSearchCondition.itspmon}"
							size="9" /> <img
							src="<c:url value="/base/images/icon/ic_cal.gif"/>"
							align="absmiddle" alt="calendar" style="cursor: pointer;" /></td width="*">
						<td class="textRight"><a href="#a" id="searchButton"><img
								src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>"
								alt="" />
						</a></td>
					</tr>
					<tr>
						<th scope="row" width="7%">계정과목</th>
						<td width="40%">
							<spring:bind path="statementSearchCondition.iracct">
								<select name="${status.expression}" style="vertical-align: bottom;">
								<option value="" <c:if test="${empty status.value}">selected="selected"</c:if>>전체</option>
								<c:forEach var="code" items="${kstarList}">
									<option value="${code.kstar}" <c:if test="${code.kstar eq status.value}">selected="selected"</c:if>>${code.ltext}</option>
								</c:forEach> 
								</select>
								<%-- <input name="${status.expression}" id="${status.expression}" title="계정과목" class="inputbox w10" type="text" value="${status.value}" /> --%>
							</spring:bind> 
							<%-- 
							<a class="ic_search" id="searchBoardItemButton" style="vertical-align: bottom;" href="#a"><span>검색</span>
							--%> 
						</td>
						<th scope="row" width="7%">분기</th>	
						<td width="40%">
							<spring:bind path="statementSearchCondition.quarter">
								<input name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="1" style="vertical-align: middle;" />1분기
								<input name="${status.expression}" <c:if test="${status.value == 2}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="2" style="vertical-align: middle;" />2분기 
								<input name="${status.expression}" <c:if test="${status.value == 3}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="3" style="vertical-align: middle;" />3분기 
								<input name="${status.expression}" <c:if test="${status.value == 4}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="4" style="vertical-align: middle;" />4분기
							</spring:bind>	
						</td>
					</tr>
				</tbody>
			</table>

		</div>
	</div>
	<div class="tableDesciption" style="position: relative;">
		<table summary="게시판 설명">
			<caption></caption>
			<tbody>
				<tr>
					<td></td>
				</tr>
			</tbody>
		</table>
		<div class="blockBlank_10px"></div>
	</div>
	<div class="blockListTable">
		<span style="float:right;font-size:10px;">(단위 : 원)</span>
		<table summary="게시판설명">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="5%">회사</th>
					<th scope="col" width="15">예산신청부서</th>
					<%-- 
					<th scope="col" width="15%">예산사용부서</th>
					--%>
					<th scope="col" width="15%">계정번호</th>
					<th scope="col" width="25%">계정과목</th>
					<th scope="col" width="5%">월</th>
					<th scope="col" width="10%">예산금액</th>
					<th scope="col" width="10%">실적금액</th>
					<th scope="col" width="10%">예산잔액</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>	
					<c:when test="${empty statementList}">
						<tr>
							<td colspan="8" class="emptyRecord">예산 계획/실적이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
					<c:set var="tplnorr1" value="0" />
					<c:set var="tactcu" value="0" />
					<c:set var="tbalcu" value="0" />  
					<c:set var="plnorr1" value="0" />
					<c:set var="actcu" value="0" />
					<c:set var="balcu" value="0" />
					<c:set var="sumGroup" value="" />
					<c:set var="ibukrs" value="" />
						<c:forEach var="item" items="${statementList}" varStatus="status">
						<%--
							<c:if test="${item.ibukrs=='M100'}">
							<c:set var="isEmpty" value="M100" />
						--%>								
							<c:if test="${status.first}">
								<c:set var="sumGroup" value="${item.kstar}" />
								<c:set var="ibukrs" value="${item.ibukrs}" />						
							</c:if>
								<c:if test="${sumGroup!=item.kstar||ibukrs!=item.ibukrs}">
									<tr style="background-color:#f3f3f3;font-weight:bold;">
										<td colspan="5">소계</td>
										<td style="text-align:right;">
											<c:choose>
												<c:when test="${plnorr1==0}">
													-
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${plnorr1}" type="number" />
												</c:otherwise>
											</c:choose>
										</td>
										<td style="text-align:right;">
											<c:choose>
												<c:when test="${actcu==0}">
													-
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${actcu}" type="number" />
												</c:otherwise>
											</c:choose>
										</td>
										<td style="text-align:right;">
											<c:choose>
												<c:when test="${balcu==0}">
													-
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${balcu}" type="number" />
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<c:set var="plnorr1" value="0" />
									<c:set var="actcu" value="0" />
									<c:set var="balcu" value="0" />
									<c:set var="sumGroup" value="${item.kstar}" />
									<c:set var="ibukrs" value="${item.ibukrs}" />
								</c:if>
								<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
									<td>
										<c:choose>
											<c:when test="${item.ibukrs=='M100'}">
												SP
											</c:when>
											<c:when test="${item.ibukrs=='S100'}">
												MP
											</c:when>
											<c:when test="${item.ibukrs=='P100'}">
												P&P
											</c:when>
										</c:choose>
									</td>
									<td>${item.ktext}</td>
									<%-- 
									<td>${item.ktexta}</td>
									--%>
									<td><fmt:parseNumber value="${item.kstar}" /></td>
									<td style="text-align:left;">${item.ltext}</td>
									<td>${item.zcmon}</td>
									<td style="text-align:right;">
										<c:choose>
											<c:when test="${item.plncu==0}">
												-
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${item.plncu}" type="number" />
											</c:otherwise>
										</c:choose>
									</td>
									<td style="text-align:right;">
										<c:choose>
											<c:when test="${item.actcu==0}">
												-
											</c:when>
											<c:otherwise>
												<a href="javascript:budgetViewPop('${item.ibukrs}','${item.ispmon}','${item.kstar}','${item.kostl}','${item.kostla}');">										
													<fmt:formatNumber value="${item.actcu}" type="number" />
												</a>
											</c:otherwise>
										</c:choose>									 
									</td>
									<td style="text-align:right;">
										<c:choose>
											<c:when test="${item.balcu==0}">
												-
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${item.balcu}" type="number" />
											</c:otherwise>
										</c:choose>
									</td>									
								</tr>
								<c:set var="plnorr1" value="${plnorr1+item.plncu}" />
								<c:set var="actcu" value="${actcu+item.actcu}" />
								<c:set var="balcu" value="${balcu+item.balcu}" />
								<c:set var="tplnorr1" value="${tplnorr1+item.plncu}" />
								<c:set var="tactcu" value="${tactcu+item.actcu}" />
								<c:set var="tbalcu" value="${tbalcu+item.balcu}" />
								<c:if test="${status.last}">
								<tr style="background-color:#f3f3f3;font-weight:bold;">
									<td colspan="5">소계</td>
									<td style="text-align:right;">
										<c:choose>
											<c:when test="${plnorr1==0}">
												-
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${plnorr1}" type="number" />
											</c:otherwise>
										</c:choose>
									</td>
									<td style="text-align:right;">
										<c:choose>
											<c:when test="${actcu==0}">
												-
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${actcu}" type="number" />
											</c:otherwise>
										</c:choose>
									</td>
									<td style="text-align:right;">
										<c:choose>
											<c:when test="${balcu==0}">
												-
											</c:when>
											<c:otherwise>
												<fmt:formatNumber value="${balcu}" type="number" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<!--  
								<c:set var="plnorr1" value="0" />
								<c:set var="actcu" value="0" />
								<c:set var="balcu" value="0" />
								-->
								<c:set var="sumGroup" value="${item.kstar}" />
							</c:if>
						<%--  																		
							</c:if>
						--%>	
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<!--//blockListTable End-->
	</div>

	</div>
	<!--//splitterBox End-->
</form>

<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

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
	var tmpWindowSize = 0;

	function mouseOver(obj){
		
		//obj.className = "bgSelected";
		//obj.style.backgroundColor="#edf2f5";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).css("background-color","#edf2f5");
		});	
	}
	function mouseOut(obj){
		//obj.className = "";
		//obj.style.backgroundColor="";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
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
		
		getList = function() {
			$("#searchButton").trigger("click");
		};

		$(document)
				.ready(
						function() {
							
						

							if (window.parent.menuMark != null) {
								window.parent.menuMark("${board.boardId}");
							}

							sort = function(ifname, istate) {
								$("#searchItemForm input[name=ifname]").val(ifname);
								$("#searchItemForm input[name=istate]").val(istate == 'A' ? 'D' : 'A');
								$("#searchButton").click();
							};

							//search 동작
							$("#searchBoardItemButton").click(
								function() {
									$("#searchItemForm input[name=actionType]").val("search");
									$("#searchItemForm").submit();
									return false;
							});

							//페이지 할 row수 설정
							$("#searchItemForm select[name=pagePerRecord]")
									.change(
											function() {
												$("#searchItemForm input[name=actionType]").val("pagePerRecord");
												$("#searchItemForm input[name=pageIndex]").val("1");
												
												$("#searchButton").trigger("click");
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
							
							
						//등록 프로세스 
					/* 	$("a.addButton").click(function(){
				
							var url = iKEP.getContextRoot() + "/support/customer/customerBasicInfo/popupBusinessNo.do";
							var options = {
								width:700,
								height:440,
								resizable:true,						 		
							};
							iKEP.popupOpen(url, options);	 
						})
						 */

						goAdd = function() {
							
							var url = '<c:url value='/support/customer/customerBasicInfo/popupBusinessNo.do'/>';
							var title = '고객정보 등록'
							var width = 780;
							var height = 580;

							var dialog = iKEP.showDialog({ 
								title: title,
								url: url,
								width:width,
								height:height,
								modal: true,
								scroll: "yes",
								callback : function(result){
									alert("성공");
									
								}
							});
							
						};
						
						$("input[name=conffdate]").keydown(function(e){
							if(e.keyCode=="13"){
								$("#searchButton").trigger("click");
							}
						});
						
						$("input[name=conftdate]").keydown(function(e){
							if(e.keyCode=="13"){
								$("#searchButton").trigger("click");
							}
						});
						
						$("input[name=apprfdate]").keydown(function(e){
							if(e.keyCode=="13"){
								$("#searchButton").trigger("click");
							}
						});
						
						$("input[name=apprtdate]").keydown(function(e){
							if(e.keyCode=="13"){
								$("#searchButton").trigger("click");
							}
						});
						
						
						$("#searchButton").click(function() {
							if($jq("input[name=mCheck]").is(":checked")){
								$jq("input[name=im100]").val($jq("input[name=mCheck]").val());
							}
							if($jq("input[name=sCheck]").is(":checked")){
								$jq("input[name=is100]").val($jq("input[name=sCheck]").val());
							}
							if($jq("input[name=pCheck]").is(":checked")){
								$jq("input[name=ip100]").val($jq("input[name=pCheck]").val());
							}
							
							if($jq("input[name=reqsCheck]").is(":checked")){
								$jq("input[name=ireqs]").val($jq("input[name=reqsCheck]").val());
							}
							if($jq("input[name=reqdCheck]").is(":checked")){
								$jq("input[name=ireqd]").val($jq("input[name=reqdCheck]").val());
							}
							if($jq("input[name=reqrCheck]").is(":checked")){
								$jq("input[name=ireqr]").val($jq("input[name=reqrCheck]").val());
							}
							if($jq("input[name=reqnCheck]").is(":checked")){
								$jq("input[name=ireqn]").val($jq("input[name=reqnCheck]").val());
							}
							$jq("input[name=iconffdate]").val($jq('#conffdate').val());
							$jq("input[name=iconftdate]").val($jq('#conftdate').val());
							$jq("input[name=iapprfdate]").val($jq('#apprfdate').val());
							$jq("input[name=iapprtdate]").val($jq('#apprtdate').val());
							
							$jq("#searchItemForm").submit();
						});
						
						$("#conffdate").click(function() { $jq("input[name=apprfdate]").val("");$jq("input[name=apprtdate]").val("");});
						$("#conftdate").click(function() { $jq("input[name=apprfdate]").val("");$jq("input[name=apprtdate]").val("")});
						$("#apprfdate").click(function() { $jq("input[name=conffdate]").val("");$jq("input[name=conftdate]").val("");});
						$("#apprtdate").click(function() { $jq("input[name=conffdate]").val("");$jq("input[name=conftdate]").val("")});
						
						$("#conffdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); $jq("input[name=apprfdate]").val("")});
						$("#conftdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); $jq("input[name=apprtdate]").val("")});
						$("#apprfdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); $jq("input[name=conffdate]").val("")});
						$("#apprtdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); $jq("input[name=conftdate]").val("")});
						
						statementViewPop = function(belnr,bukrs,gjahr,belnr2,cardno,reqnamt,dmbtr,revs,belnrrd,belnr2rd) {	
							if(revs=="X"){
								var url = iKEP.getContextRoot() + '/portal/moorimess/statementCommon/statementView.do?belnr='+belnrrd+'&bukrs='+bukrs+'&gjahr='+gjahr+'&belnr2='+belnr2rd+'&cardno='+cardno+'&reqnamt='+encodeURI(reqnamt)+'&dmbtr='+dmbtr+'&revs='+revs; 
							}else{
								var url = iKEP.getContextRoot() + '/portal/moorimess/statementCommon/statementView.do?belnr='+belnr+'&bukrs='+bukrs+'&gjahr='+gjahr+'&belnr2='+belnr2+'&cardno='+cardno+'&reqnamt='+encodeURI(reqnamt)+'&dmbtr='+dmbtr+'&revs='+revs; 
							}

							iKEP.popupOpen(url, {width:1000, height:500}, "statementView");
						};	
						
						
						$("#conffdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
						$("#conftdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
						$("#apprfdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
						$("#apprtdate").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
						$("#blktable").attr("style","font-size:11px;");
						});
	})(jQuery);
//-->
</script>






	<h1 class="none">컨텐츠영역</h1>
<form id="searchItemForm"	method="post" action="<c:url value='/portal/moorimess/statementCommon/statementAllList.do'/>" >			
<input type="hidden" name="ip100"/>
<input type="hidden" name="is100"/>
<input type="hidden" name="im100"/>
<input type="hidden" name="ireqs"/>
<input type="hidden" name="ireqr"/>
<input type="hidden" name="ireqd"/>
<input type="hidden" name="ireqn"/>	
<input type="hidden" name="iconffdate"/>
<input type="hidden" name="iconftdate"/> 
<input type="hidden" name="iapprfdate"/>
<input type="hidden" name="iapprtdate"/>
<input type="hidden" name="isInit" value="no"/>
<spring:bind path="statementSearchCondition.ifname">
	<input name="${status.expression}" type="hidden"
		value="${status.value}"
		title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="statementSearchCondition.istate">
	<input name="${status.expression}" type="hidden"
		value="${status.value}"
		title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>카드 사용/전표 승인 내역 조회</h2>
		<div class="listInfo">
			<spring:bind path="statementSearchCondition.pagePerRecord">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${portalCode.pageNumList}">
						<option value="${code.key}"
							<c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach>
				</select>
			</spring:bind>
			<div class="totalNum">
				${statementResult.pageIndex}/ ${statementResult.pageCount}
				<ikep4j:message pre='${preSearch}' key='page' />
				(
				<ikep4j:message pre='${preSearch}' key='pageCount.info' />
				<span> ${statementResult.recordCount}</span>)
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<div class="blockSearch1">
                	<div class="corner_RoundBox03">
						<table>
						<tbody>
							<tr>
                               <th scope="row" width="6%">회사코드</th>
                               <td width="*" style="vertical-align:middle;">
                               		<input name="sCheck" <c:if test="${statementSearchCondition.is100 eq 'X'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align: middle;" />무림페이퍼${status.value}
                               		<input name="pCheck" <c:if test="${statementSearchCondition.ip100 eq 'X'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align: middle;" />무림P&P${status.value}
                               		<input name="mCheck" <c:if test="${statementSearchCondition.im100 eq 'X'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align: middle;" />무림SP ${status.value}						
                               </td>                               
                               <th scope="row" width="7%">카드사용일</th>
                               <td width="26%">
                               <input name="conffdate"   id="conffdate" class="inputbox w10 datepicker" type="text" style="width:60px;" value="${statementSearchCondition.iconffdate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
                                ~ 
                               <input name="conftdate"   id="conftdate" class="inputbox w10 datepicker" type="text" style="width:60px;" value="${statementSearchCondition.iconftdate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
                               </td>
                               	<th scope="row" width="6%">사용부서</th>		
                                <td width="18%">
                                	<spring:bind path="statementSearchCondition.zzkostl">  
									<select name="${status.expression}" style="vertical-align: bottom;">
									<option value="" <c:if test="${empty status.value}">selected="selected"</c:if>>전체</option>
									<c:forEach var="code" items="${costCenterList}">
										<option value="${code.zzkostl}" <c:if test="${code.zzkostl eq status.value}">selected="selected"</c:if>>${code.zzkostlt}</option>
									</c:forEach> 
									</select>
									</spring:bind>
                                </td>		
                           </tr>		
                           <tr>
                               <th scope="row" width="6%">전표상태</th>
                               <td width="*">
                               		<input name="reqrCheck" <c:if test="${statementSearchCondition.ireqr eq 'X'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align:middle;" />승인미요청
                               		<input name="reqsCheck" <c:if test="${statementSearchCondition.ireqs eq 'X'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align:middle;" />전표승인
                               		<input name="reqdCheck" <c:if test="${statementSearchCondition.ireqd eq 'X'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="X" style="vertical-align:middle;" />전표반려
                               		<input name="reqnCheck" <c:if test="${statementSearchCondition.ireqn eq 'S'}">checked="checked"</c:if> class="checkbox" title="checkbox" type="checkbox" value="S" style="vertical-align:middle;" />역분개
                               </td>	
                               <th scope="row" width="7%">승인/반려일</th>
                               <td width="26%">
                               <input name="apprfdate"   id="apprfdate" class="inputbox w10 datepicker" type="text" style="width:60px;" value="${statementSearchCondition.iapprfdate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
                                ~ 
                               <input name="apprtdate"   id="apprtdate" class="inputbox w10 datepicker" type="text" style="width:60px;" value="${statementSearchCondition.iapprtdate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
                               </td>	
							   <td class="textRight" colspan="2" width="24%">
                               <a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>" alt="" /></a>
                               </td>
                                
                           </tr>											
		                 </tbody>
		             	</table>
		             </div>
				</div>   
	
	<div class="tableDesciption" style="position:relative;">
		<table summary="게시판 설명">
		<caption></caption>
		<tbody>
			<tr>
			<td>
			  
			</td>
			</tr>
		</tbody>
		</table>
    <div class="blockBlank_10px"></div>	
	</div>
	
	<div class="blockListTable">
	<span style="float:right;font-size:10px;">(단위 : 원)</span>
		<table summary="게시판설명" id="blktable">
			<caption></caption>
                     <thead>
                         <tr>
                         	 <th scope="col" width="3%">
                         	 	<a onclick="sort('BUKRS', '<c:if test="${statementSearchCondition.ifname eq 'BUKRS'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								회사
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'BUKRS' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'BUKRS' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="5%">
                         	 	<a onclick="sort('GUB_SEQ_T', '<c:if test="${statementSearchCondition.ifname eq 'GUB_SEQ_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								전표<br/>상태
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'GUB_SEQ_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'GUB_SEQ_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="5%">
                         	 	<a onclick="sort('REQ_DATE', '<c:if test="${statementSearchCondition.ifname eq 'REQ_DATE'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								승인<br/>요청일
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'REQ_DATE' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'REQ_DATE' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="5%">
                         	 	<a onclick="sort('REQNAM_T', '<c:if test="${statementSearchCondition.ifname eq 'REQNAM_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								승인<br/>요청자
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'REQNAM_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'REQNAM_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="5%">
								검토자
                         	 </th>
                             <th scope="col" width="5%">
                             	<a onclick="sort('APPR_DATE', '<c:if test="${statementSearchCondition.ifname eq 'APPR_DATE'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								승인<br/>/반려일
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'APPR_DATE' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'APPR_DATE' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="5%">
                             	<a onclick="sort('ZRECEIVE_TEXT', '<c:if test="${statementSearchCondition.ifname eq 'ZRECEIVE_TEXT'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								반려<br/>사유
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'ZRECEIVE_TEXT' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'ZRECEIVE_TEXT' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="6%">
                             	<a onclick="sort('CARD_NO', '<c:if test="${statementSearchCondition.ifname eq 'CARD_NO'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								카드<br/>번호
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'CARD_NO' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'CARD_NO' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="7%">
                            	<a onclick="sort('CONF_DATE', '<c:if test="${statementSearchCondition.ifname eq 'CONF_DATE'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								카드<br/>사용일
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'CONF_DATE' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'CONF_DATE' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose> 
                             </th>
                         	 <th scope="col" width="7%">
                         	 	<a onclick="sort('HKONT_T', '<c:if test="${statementSearchCondition.ifname eq 'HKONT_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								계정<br/>과목
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'HKONT_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'HKONT_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="5%">
								공급가액
                         	 </th>
                         	 <th scope="col" width="5%">
                         	 	<a onclick="sort('TAX_AMT', '<c:if test="${statementSearchCondition.ifname eq 'TAX_AMT'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								세액
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'TAX_AMT' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'TAX_AMT' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="5%">
                         	 	<a onclick="sort('DMBTR', '<c:if test="${statementSearchCondition.ifname eq 'DMBTR'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								총액
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'DMBTR' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'DMBTR' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                         	 <th scope="col" width="4%">
								불공제<br/>여부
                         	 </th>
                         	 <th scope="col" width="9%">
                         	 	<a onclick="sort('NAME', '<c:if test="${statementSearchCondition.ifname eq 'NAME'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								거래처
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'NAME' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'NAME' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                         	 </th>
                             <th scope="col" width="9%">
                             	<a onclick="sort('BKTXT', '<c:if test="${statementSearchCondition.ifname eq 'BKTXT'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								전표적요
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'BKTXT' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'BKTXT' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="5%">
                             	<a onclick="sort('ZRECEIVE_NAM_T', '<c:if test="${statementSearchCondition.ifname eq 'ZRECEIVE_NAM_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								전표<br/>결재자
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'ZRECEIVE_NAM_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'ZRECEIVE_NAM_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                         </tr>
                     </thead>
                     <tbody>
	                     <c:choose>
							<c:when test="${empty statementList}">
								<tr>
									<td colspan="17" class="emptyRecord">
									 전표내역이 없습니다.
									</td>
								</tr>
							</c:when>
							<c:otherwise>
									<c:forEach var="statement" items="${statementList}" varStatus="status">
									<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
										<span onclick="javascript:statementViewPop('${statement.belnr}','${statement.bukrs}','${statement.gjahr}','${statement.belnr2}','${statement.cardno}','${statement.reqnamt}','${statement.dmbtr}','${statement.revs}','${statement.belnrrd}','${statement.belnr2rd}');" style="cursor:pointer;vertical-align:middle;">
										<td>
											<c:choose>
												<c:when test="${statement.bukrs=='S100'}">
													MP
												</c:when>
												<c:when test="${statement.bukrs=='M100'}">
													SP
												</c:when>
												<c:otherwise>
													P&P
												</c:otherwise>
											</c:choose>
										</td>
										<td>
										<c:choose>
											<c:when test="${statement.gubseqt == '승인미요청'}">
												미요청
											</c:when>
											<c:when test="${statement.gubseqt == '전표승인'}">
												승인
											</c:when>
											<c:when test="${statement.gubseqt == '전표반려'}">
												반려
											</c:when>
											<c:when test="${statement.gubseqt == '역분개전표 승인'}">
												<c:if test="${statement.apprdate=='0000-00-00'}">
													역분개
												</c:if>
												<c:if test="${statement.apprdate!='0000-00-00'}">
													역분개
												</c:if>
											</c:when>
										</c:choose>
										</td>
										<c:choose>
											<c:when test="${statement.gubseqt != '승인미요청' && statement.gubseqt != '전표미작성'}">
												<td>${statement.reqdate}</td>
												<td>${statement.reqnamt}</td>
												<td>${statement.reviewnamt}</td>
												<td>
												<c:if test="${statement.revs=='X'}">
													${statement.apprdater}
												</c:if>
												<c:if test="${statement.revs!='X'}">
													${statement.apprdate}
												</c:if>
												</td>
											</c:when>
											<c:otherwise>
												<td colspan="4"></td>
											</c:otherwise>
										</c:choose>
										<td>${statement.zreceivetext}</td>
										<td style="text-align:left;">${statement.cardno}</td>
										<td>${statement.confdate} ${statement.conftime}</td>
										<td style="text-align:left;">${statement.hkontt}</td>
										<td style="text-align:right;">
												<fmt:formatNumber value="${statement.dmbtrds}" />
										</td>
										<td style="text-align:right;">
											<c:if test="${statement.revs=='X'&&statement.taxamt!='0'&&statement.taxamt>0}">-</c:if><fmt:formatNumber value="${statement.taxamt}" />
										</td>
										<td style="text-align:right;">
											<c:if test="${statement.revs=='X'}">-</c:if><fmt:formatNumber value="${statement.dmbtr}" />
										</td>
										<td>
											<c:if test="${!empty statement.belnr2}">Y</c:if>
										</td>
										<td style="text-align:left;">${statement.name}</td>
										</span>
										<td style="text-align:left;">${statement.bktxt}</td>
										<td>${statement.zreceivenamt}</td>
										
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>	     																														
                     </tbody>
				</table>
				<spring:bind path="statementSearchCondition.pageIndex">
					<ikep4j:pagination searchFormId="searchItemForm"
						ajaxEventFunctionName="getList"
						pageIndexInput="${status.expression}"
						searchCondition="${statementSearchCondition}" />
					<input name="${status.expression}" type="hidden"
						value="${status.value}"
						title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
			</div>
			<!--//blockListTable End-->	
		</div>
		<div class="blockBlank_10px"></div>			
				    <div class="blockBlank_10px"></div>		
				    <div class="blockBlank_10px"></div>		
				    <div class="blockBlank_10px"></div>		
				    <div class="blockBlank_10px"></div>	
	</div>			
	<!--//splitterBox End-->						
</form>
		
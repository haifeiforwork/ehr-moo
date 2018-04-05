<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preForm"    value="ui.collpack.who.whoForm" />
<c:set var="preMenu"    value="ui.collpack.who.whoMenu" />
<c:set var="preList"    value="ui.collpack.who.whoList" />
<c:set var="preMessage"    value="message.collpack.who" />
<c:set var="preView"    value="ui.collpack.who.whoView" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
var accordion = null;
(function($) { 
	$(document).ready(function() { 
		$("#accordion").accordion();		
		accordion = new iKEP.Accordion({
			parent : "#divContainer",
			//container : "#accordion",
			cache : true,
			active : false,
			items : [
				{title:"Item 1", url:"ajax_result/data.json"},
				{title:"Item 2", url:"/"},
				{title:"Item 3", url:"ajax_result/html.txt"}
			],
			load : function(ui) {
				//iKEP.debug(arguments);
			}
		});		

		var startSortChar = '';
		var endSortChar = '';		
	
		$jq("#whoSearchButton").click(function() {			
			submitWho();
		});		
	    			
		submitWho = function() {
			setEachItemValue('${searchCondition.whoSortIndex}','search');
			
			$("input[name=pageIndex]").val("1");
			$("#searchForm").submit();			
		}
		//클릭된 인덱스별 정렬쿼리를 위한 시작문자, 마지막문자 셋팅
		setEachItemValue = function(startIndex, mode) {
			var koArray = ["가","나","다","라","마","바","사","아","자","차","카","타","파","하","힝","A","Z","0","9"];
			var enArray = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];

			var startSortChar = '';
			var endSortChar = '';				
			if ( startIndex == '' ) startIndex = '0';
			var endIndex = eval(startIndex) + 1;

	 		<c:choose>
 				<c:when test="${user.localeCode == 'ko'}">
					startSortChar = koArray[startIndex];
					endSortChar = koArray[endIndex]; 				
				</c:when>
				<c:otherwise>
					startSortChar = enArray[startIndex];
 				</c:otherwise>		 			
 			</c:choose>		

			if ( startIndex == 14 ) {    
				startSortChar = 'A';
				endSortChar = 'Z';
				$("input[name=localeCode]").val("enTotal");
			}
			if ( startIndex == 15 || startIndex == 27 ) {
				startSortChar = '0';
				endSortChar = '9';
				$("input[name=localeCode]").val("numTotal");
			}
			if ( startIndex == 26 ) {    
				startSortChar = '가';
				endSortChar = '힝';
				$("input[name=localeCode]").val("ko");
			}			
			//etc
			var localeCode = $("input[name=localeCode]").val();
			if ( localeCode != 'ko' && localeCode != 'enTotal' && localeCode != 'numTotal') {
				$("input[name=localeCode]").val("en");
			}
			
			if ( mode == "tab") {
				$("input[name=whoSortIndex]").val(startIndex);
			} else {
				$("input[name=whoSortIndex]").val("-1");
				$("input[name=localeCode]").val("");
			}
			$("input[name=startSortChar]").val(startSortChar);
			$("input[name=endSortChar]").val(endSortChar);			
		}
	    var selectedIndex = '${searchCondition.whoSortIndex}';
	    if ( selectedIndex == '' ) {
	    	selectedIndex = -1;
	    }
	    
		$("#divTab_s").tabs({  
			selected : selectedIndex,    
			cache : true,     
			select : function(event, ui) {
				setEachItemValue(ui.index,'tab');
			
				$("input[name=pageIndex]").val("1");
				$("#searchForm").submit();	
			},     
			load : function(event, ui) {        
  
				} 
		});	
		
	    //validation
	    /*var validOptions = {
			rules : {
				searchWord :	{
					required : true
				}
			},
			messages : {
				searchWord : {
					required : "검색어를 입력하세요.",
					direction:"top"
				}
			},
			submitHandler : function(form) {
				submitWho();				
			}
		};	      
	    new iKEP.Validator("#searchForm", validOptions);*/		
	    
	});

	//내부인물 상세보기 팝업
	viewPopUpProfile = function(targetUserId){
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
	}	
})(jQuery); 
//인물목록 추가10개 더보기
var pageMainIndex = 2;		
function moreMainList(){
	$jq.ajax({    
		url : "listMore.do",  
		data : {pageIndex:pageMainIndex
			,localeCode:'${searchCondition.localeCode}'
			,registerId:'${searchCondition.registerId}'
			,viewId:'${searchCondition.viewId}'
			,searchColumn:'${searchCondition.searchColumn}'
			,searchWord:'${searchCondition.searchWord}'				
			,startSortChar:'${searchCondition.startSortChar}'
			,endSortChar:'${searchCondition.endSortChar}'
			,whoSortIndex:'${searchCondition.whoSortIndex}'
		},     
		type : "post",  
		dataType : "html",
		loadingElement : {button:"#moreText"},//{button:"#listFrame", container:"#listFrame"}, 
		success : function(result) { 
			if(result){
				$jq('#listFrame').append(result);
				++pageMainIndex;
				if(${searchCondition.pageCount} < pageMainIndex){
					$jq('#moreText').text("<ikep4j:message pre='${preMessage}' key='notMore' />");
				}
			}
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 	
}
//나의 전문분야 관련인물 10개 더보기
var pageIndex = 2;		
function moreList(tagId){	
	$jq.ajax({    
		url : "proListMore.do",  
		data : {pageIndex:pageIndex
			,tagId:tagId
		},     
		type : "post",  
		dataType : "html",
		success : function(result) { 
			if(result){
				$jq('#listFrame_'+tagId).append(result);
				++pageIndex;
			}
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 
}
//-->
</script> 
<div id="tagResult">
				<form id="searchForm" method="post" action="<c:url value='/collpack/who/main.do'/>">
				<input type="hidden" name="startSortChar" value="${searchCondition.startSortChar}"/>
				<input type="hidden" name="endSortChar" value="${searchCondition.endSortChar}"/>
				<input type="hidden" name="localeCode" value="${user.localeCode}"/>				
				<input type="hidden" name="whoSortIndex" value="${searchCondition.whoSortIndex}"/>
				<h1 class="none">contents area</h1>
				
				<div class="" style="padding-right:244px;">
				<!--blockSearch Start-->
				<div class="blockSearch">
					<form action="">
					<div class="corner_RoundBox03">
						<div class="search_who">
						<table summary="<ikep4j:message pre='${preList}' key='searchSelect' />">
							<caption></caption>
							<tbody>
								<tr>
									<th width="5%"><ikep4j:message pre='${preList}' key='searchSelect' /></th>
									<td width="95%">
										<!--conSearch Start-->
										<div class="conSearch">
											<div class="conSearch_sel">
												<spring:bind path="searchCondition.searchColumn">
												<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' id="select2" style="width:63px">
													<option value="name" <c:if test="${'name' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchName' /></option>
													<option value="memo" <c:if test="${'memo' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preForm}' key='formMemo' /></option>
													<option value="companyName" <c:if test="${'companyName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchCompanyName' /></option>
													<option value="teamName" <c:if test="${'teamName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchTeamName' /></option>
													<option value="tag" <c:if test="${'tag' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchTag' /></option>
													<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchRegisterName' /></option>
												</select>
												</spring:bind>
											</div>
											<spring:bind path="searchCondition.searchWord">
												<input name="${status.expression}" value="${status.value}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' style="width:171px" type="text" />
											</spring:bind>
											<a class="sel_btn" href="#a" id="whoSearchButton"><span>Search</span></a>
											<div class="clear"></div>
										</div>
										<!--//conSearch End-->						
									</td>
								</tr>
							</tbody>
						</table>
						</div>								
					</div>
					</form>					
				</div>	
				<!--//blockSearch End-->

				<!--tab Start-->
		 		<c:choose>
		 			<c:when test="${user.localeCode == 'ko'}">							
					<div id="divTab_s" class="iKEP_tab_s">
						<ul>
							<li><a href="#tabs-2">가</a></li>
							<li><a href="#tabs-2">나</a></li>
							<li><a href="#tabs-2">다</a></li>
							<li><a href="#tabs-2">라</a></li>
							<li><a href="#tabs-2">마</a></li>
							<li><a href="#tabs-2">바</a></li>
							<li><a href="#tabs-2">사</a></li>
							<li><a href="#tabs-2">아</a></li>
							<li><a href="#tabs-2">자</a></li>
							<li><a href="#tabs-2">차</a></li>
							<li><a href="#tabs-2">카</a></li>
							<li><a href="#tabs-2">타</a></li>
							<li><a href="#tabs-2">파</a></li>
							<li><a href="#tabs-2">하</a></li>
							<li><a href="#tabs-2">A-Z</a></li>
							<li><a href="#tabs-2">0-9</a></li>
						</ul>
						<div class="tab_con" style="padding-bottom: 0px;display:none;">         
							<div id="tabs-2"></div>         
						</div>							
					</div>		
	 			</c:when>
	 			<c:otherwise>							
					<div id="divTab_s" class="iKEP_tab_s">
						<ul>
							<li><a href="#tabs-1" >A</a></li>
							<li><a href="#tabs-1" >B</a></li>
							<li><a href="#tabs-1" >C</a></li>
							<li><a href="#tabs-1" >D</a></li>
							<li><a href="#tabs-1" >E</a></li>
							<li><a href="#tabs-1" >F</a></li>
							<li><a href="#tabs-1" >G</a></li>
							<li><a href="#tabs-1" >H</a></li>
							<li><a href="#tabs-1" >I</a></li>
							<li><a href="#tabs-1" >J</a></li>
							<li><a href="#tabs-1" >K</a></li>
							<li><a href="#tabs-1" >L</a></li>
							<li><a href="#tabs-1" >M</a></li>
							<li><a href="#tabs-1" >N</a></li>
							<li><a href="#tabs-1" >O</a></li>
							<li><a href="#tabs-1" >P</a></li>
							<li><a href="#tabs-1" >Q</a></li>
							<li><a href="#tabs-1" >R</a></li>
							<li><a href="#tabs-1" >S</a></li>
							<li><a href="#tabs-1" >T</a></li>
							<li><a href="#tabs-1" >U</a></li>
							<li><a href="#tabs-1" >V</a></li>
							<li><a href="#tabs-1" >W</a></li>
							<li><a href="#tabs-1" >X</a></li>
							<li><a href="#tabs-1" >Y</a></li>
							<li><a href="#tabs-1" >Z</a></li>
							<li><a href="#tabs-1" >가~하</a></li>
							<li><a href="#tabs-1" >0~9</a></li>							
						</ul>	
						<div class="tab_con" style="padding-bottom: 0px;display:none;">         
							<div id="tabs-1"></div>         
						</div>										
					</div>
		 			</c:otherwise>		 			
		 		</c:choose>						
				<!--//tab End-->
				
				<div class="clear"></div>
				
				<!--subTitle_2 Start-->
				<div class="whosSubTitle mt10">
					<c:if test="${searchCondition.mode == null}">
						<c:if test="${fn:length(searchResult.entity) > 0}">
						<h3><ikep4j:message pre='${preMenu}' key='menuList' /></h3>
						</c:if>
					</c:if>
					<c:if test="${searchCondition.mode == 'myInputList'}">
						<h3><ikep4j:message pre='${preMenu}' key='menuMyInput' /></h3>
					</c:if>
					<c:if test="${searchCondition.mode == 'myViewList'}">
						<h3><ikep4j:message pre='${preMenu}' key='menuMyView' /></h3>
					</c:if>					
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockListTable Start-->
				<c:if test="${fn:length(searchResult.entity) == 0}">
					<div style="text-align:center;padding-top:30px;"><ikep4j:message pre='${preMessage}' key='noSearchResult' /></div><!-- div style="margin-top:25px;margin-bottom:25px;text-align:center;"></div-->				
				</c:if>	
				
				<div id="listFrame">
				<c:forEach var="who" items="${searchResult.entity}" varStatus="whoLoopCount">				
				<div class="whosList">
					<div class="whosPhoto">
					    <c:choose>
					    	<c:when test="${who.profilePictureId != null}">
					    		<a href="getWho.do?profileId=${who.profileId}&amp;whoSortIndex=${searchCondition.whoSortIndex}"><img id="profilePictureImage${whoLoopCount.count }" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${who.profilePictureId}&amp;profileYn=Y' />" width="100" height="100" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" /></a>
					      	</c:when>
					      	<c:otherwise>
					      		<img id="profilePictureImage${whoLoopCount.count }" src="<c:url value='/base/images/common/photo_170x170.gif' />" width="100" height="100" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" />
					      	</c:otherwise>
					    </c:choose>		
					</div>
					<div class="whosInfo">
						<h4><a href="getWho.do?profileId=${who.profileId}&amp;whoSortIndex=${searchCondition.whoSortIndex}">${who.name}</a></h4>
						<table summary="<ikep4j:message pre='${preMenu}' key='menuList' />" width="100%" border="0" >
							<caption></caption>						
							<tbody>						
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formCompanyName' /></span> : <span>${who.companyName}</span></td>
								</tr>
								<tr>
									<td width=""><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formTeamName' /></span> : <span>${who.teamName}</span></td>
									<td width="">&nbsp;&nbsp; <span class="whos_tit"><ikep4j:message pre='${preForm}' key='formJobRankName01' /></span> : <span>${who.jobRankName}</span></td>
								</tr>
								<tr>
									<td width=""><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formOfficePhoneno' /></span> : <span>${who.officePhoneno}</span></td>
									<td width="">&nbsp;&nbsp; <span class="whos_tit"><ikep4j:message pre='${preForm}' key='formMobile' /></span> : <span>${who.mobile}</span></td>
								</tr>
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formMail' /></span> : <span><a href="mailto:${who.mail}">${who.mail}</a></span></td>
								</tr>																																																
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formCompanyAddress' /></span> : <span>${who.companyAddress}</span></td>
								</tr>	
								<tr>
									<td colspan="2"><span class="whos_tit"><ikep4j:message pre='${preForm}' key='formMemo' /></span> : <span>${ikep4j:cutString(who.memo,90,"..")}</span></td>
								</tr>									
							</tbody>
						</table>																																																							
						<!--tag list-->
						<div class="corporateViewTag" id="tagForm_${who.profileId}">
							<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_WHO %>"/>
							<input type="hidden" name="tagItemSubType" value="${who.profileGroupId}"/>
							<input type="hidden" name="tagItemName" value="${who.name}"/>
							<input type="hidden" name="tagItemContents" value="${fn:escapeXml(who.memo)}"/>
							<input type="hidden" name="tagItemUrl" value="/collpack/who/getWho.do?profileId=${who.profileId}&amp;whoSortIndex=${searchCondition.whoSortIndex}"/>								
							<div class="tableTag">									
								<span class="ic_tag"></span>
									<c:forEach var="tag" items="${who.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
								<span class="rebtn">
									<a href="#a" onclick="iKEP.tagging.tagFormView('${who.profileId}');return false;" title="<ikep4j:message pre='${preView}' key='viewModify' />" class="ic_modify"></a>
								</span>	
							</div>
						</div>										
						<!--//tag list-->																																																

					</div>			
				</div>
				</c:forEach>
				</div>
				<!--//blockListTable End-->
				
				<c:if test="${searchResult.recordCount > 10}">
				<!--blockButton_3 Start-->
				<div class="blockButton_3"> 
					<a class="button_3" href="#a" onclick="moreMainList();return false;"><span id="moreText"><ikep4j:message pre='${preList}' key='listMore' /> <img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>				
				</div>
				<!--//blockButton_3 End-->
				</c:if>				
				
				<div class="clear"></div>
				<!--whosRightBlock Start-->
				<div class="whosRightBlock" style="margin-right:-15px;">
					<div class="subTitle_2 noline">
						<h3><ikep4j:message pre='${preList}' key='expertTitle' /></h3>
					</div>
					<c:if test="${fn:length(proList) == 0}">
						<div style="margin-top:5px;margin-left:15px;"><ikep4j:message pre='${preMessage}' key='noExpertResult' /></div>				
					</c:if>					
					<!--rightMenu Start-->
					<div id="accordion" class="iKEP_accordion iKEP_state-active">	
						<c:forEach var="pro" items="${proList}" varStatus="proLoopCount">
							<c:if test="${tmpTagId != pro.tagId || proLoopCount.count == 1 }">
								<c:if test="${(proLoopCount.count != 1)}">
							</div>
									<c:if test="${tmpProCount > 10}">
							<!--blockButton_3 Start-->
							<div class="blockButton_3 mb10">
								<a class="button_3" href="#a" onclick="moreList('${tmpTagId}');return false;"><span id="moreText_${tmpTagId}"><ikep4j:message pre='${preList}' key='listMore' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="" /></span></a>				
							</div><br/>
							<!--//blockButton_3 End-->	
									</c:if>								
							</div>								
								</c:if>
								
								
							<h3><a>${pro.tagName}(${pro.count}<ikep4j:message pre='${preList}' key='count' />)</a></h3>
							<div>	
							<div id="listFrame_${pro.tagId}">						
							</c:if>
							
								<c:if test="${pro.count == 0}">
								<div class="conBox">
									<div>
									<div style="margin-top:5px;margin-left:15px;">[${pro.tagName}] <ikep4j:message pre='${preMessage}' key='noExpertResult' /></div>
									</div>
								</div>								
								</c:if>
								<c:if test="${pro.count > 0}">
								<div class="conBox">
									<div>
									<a href="#a" onclick="viewPopUpProfile('${pro.userId}');return false;" ><img id="proProfilePictureImage${proLoopCount.count}" src="<c:url value='${pro.profilePicturePath}' />" width="50" height="50" style="vertical-align:top;" alt="<ikep4j:message pre='${preForm}' key='formPicture' />" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a>
									</div>
									<div>
										<ul>
										    <c:choose>
										    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										    		<c:set var="userInfo" value="${pro.userName} ${pro.jobTitleName}"/>
										    		<c:set var="userTeamInfo" value="${pro.teamName}"/>
										      	</c:when>
										      	<c:otherwise>
										      		<c:set var="userInfo" value="${pro.userEnglishName} ${pro.jobTitleEnglishName}"/>
										      		<c:set var="userTeamInfo" value="${pro.teamEnglishName}"/>
										      	</c:otherwise>
										    </c:choose>		
										    <li title="${userInfo}">${ikep4j:cutString(userInfo,20,"")}</li>
										    <li title="${userTeamInfo}">${ikep4j:cutString(userTeamInfo,20,"")}</li>									
											<li><a href="">${pro.mail}</a></li>
											<li>${pro.mobile}</li>
										</ul>
									</div>
								</div>		
								</c:if>
								
								
																				
							<c:set var="tmpTagId" value="${pro.tagId}"/>	
							<c:set var="tmpProLoopCount" value="${proLoopCount.count}"/>
							<c:set var="tmpProCount" value="${pro.count}"/>						
						</c:forEach>
						

							<c:if test="${(tmpProLoopCount != 1)  || (tmpProLoopCount == 1 && tmpProCount == 0) }">
						</div>
								<c:if test="${tmpProCount > 10}">
						<!--blockButton_3 Start-->
						<div class="blockButton_3 mb10">
							<a class="button_3" href="#a" onclick="moreList('${tmpTagId}');return false;"><span id="moreText_${tmpTagId}"><ikep4j:message pre='${preList}' key='listMore' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="" /></span></a>				
						</div><br/>
						<!--//blockButton_3 End-->	
								</c:if>								
						</div>								
							</c:if>
																													
					</div>
					<!--//rightMenu End-->
	
				</div>
				<!--//whosRightBlock End-->				
				</div>
				
				</form>
</div>
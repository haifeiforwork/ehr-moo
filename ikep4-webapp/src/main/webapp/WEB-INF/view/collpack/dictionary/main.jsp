<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="preList"    	value="ui.collpack.dictionary.dictionaryList" />
<c:set var="preForm"    	value="ui.collpack.dictionary.dictionaryForm" />
<c:set var="preMessage"    	value="message.collpack.dictionary" />
<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />

<script type="text/javascript">
<!--

//validation
var validOptions = {
	/*rules : {
		searchWord :	{
			required : true
		}
	},
	messages : {
		searchWord : {
			required : "<ikep4j:message key='NotEmpty.dictionary.searchTitle'/>",
			direction : "top"
		}
	},
	notice : {
		searchWord : {
	         message : "<ikep4j:message key='NotEmpty.dictionary.searchTitle'/>",
	         direction : "top"

		}
	},			
	submitHandler : function(form) {
		alert("submit");
		goDictionary();				
	}*/
};	 

(function($) { 
	$(document).ready(function() { 
		//$("#divTab_s").tabs();
		var startSortChar = '';
		var endSortChar = '';
		var sortOrderClick = false;
	
		$jq("#dictionarySearchButton").click(function() {		
			var searchColumn = $("select[name=searchColumn]").val();
			setEachItemValue('${searchCondition.dictionarySortIndex}','search');
			
			$("input[name=pageIndex]").val("1");
			$("#searchForm").submit();	
			//$("#searchForm").trigger("submit");
			//location.href='main.do?dictionaryId='+ ${searchCondition.dictionaryId};
		});		


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
				$("input[name=dictionarySortIndex]").val(startIndex);
			} else {
				$("input[name=dictionarySortIndex]").val("-1");
				$("input[name=localeCode]").val("");
			}
			$("input[name=startSortChar]").val(startSortChar);
			$("input[name=endSortChar]").val(endSortChar);			
		}
	    var selectedIndex = '${searchCondition.dictionarySortIndex}';
	    if ( selectedIndex == '' ) {
	    	selectedIndex = -1;
	    }
	    
		$("#divTab_s").tabs({  
			selected : selectedIndex,    
			cache : true,     
			select : function(event, ui) {   
					//location.href = 'main.do?dictionaryId=' + ${searchCondition.dictionaryId} + '&dictionarySortIndex=' + ui.index;
				setEachItemValue(ui.index,'tab');
				$("input[name=pageIndex]").val("1");
				$("#searchForm").submit();					
			},     
			load : function(event, ui) {        
				//iKEP.debug(ui);     
				} 
		});	    


		//$jq("#dictionaryUl a").click(function(){
            //location.href='main.do?dictionaryId='+ this.dictionaryId;
        //});
		
	    
	    //if ( sortOrderClick )
	    	//new iKEP.Validator("#searchForm", validOptions);		
	    
	});

	clickDictionary = function(dictionaryId){
		location.href='main.do?dictionaryId='+ dictionaryId;
	}
	viewPopUpProfile = function(targetUserId){
		var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
	}	
})(jQuery); 
function goDictionary() {
	//var searchColumn = $("select[name=searchColumn]").val();
	//setEachItemValue('${searchCondition.dictionarySortIndex}','search');

	$jq("input[name=pageIndex]").val("1");
	//$jq("#searchForm").submit();		
}
var pageIndex = 2;		
function moreList(){	
	if(${searchCondition.pageCount} < pageIndex){
		return;	
	}

	$jq.ajax({    
		url : "listMore.do",  
		data : {pageIndex:pageIndex
			,dictionaryId:'${searchCondition.dictionaryId}'
			,localeCode:'${user.localeCode}'
			,registerId:'${searchCondition.registerId}'
			,viewId:'${searchCondition.viewId}'			
			,startSortChar:'${searchCondition.startSortChar}'
			,endSortChar:'${searchCondition.endSortChar}'
			,searchColumn:'${searchCondition.searchColumn}'
			,searchWord:'${searchCondition.searchWord}'
			,dictionarySortIndex:'${searchCondition.dictionarySortIndex}'
		},     
		type : "post",  
		dataType : "html",
		success : function(result) { 
			if(result){
				//alert(result);
				$jq('#listFrame').append(result);
				++pageIndex;
				if(${searchCondition.pageCount} < pageIndex){
					$jq('#moreText').text("<ikep4j:message pre='${preMessage}' key='noMore' />");
				}
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
				<form id="searchForm" method="post" action="<c:url value='/collpack/dictionary/main.do'/>">
				<input type="hidden" name="dictionaryId" value="${searchCondition.dictionaryId}"/>
				<input type="hidden" name="startSortChar" value="${searchCondition.startSortChar}"/>
				<input type="hidden" name="endSortChar" value="${searchCondition.endSortChar}"/>
				<input type="hidden" name="localeCode" value="${user.localeCode}"/>				
				<input type="hidden" name="dictionarySortIndex" value="${searchCondition.dictionarySortIndex}"/>
				<h1 class="none">contents area</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preList}' key='title' /></h2>
					<div id="pageLocation" class="none">
						<ul>
							<li class="liFirst"><ikep4j:message pre='${preForm}' key='pageLocation1depth' /></li>
							<li><ikep4j:message pre='${preForm}' key='pageLocation2depth' /></li>
							<li class="liLast"><ikep4j:message pre='${preList}' key='title' /></li>
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->
				
				<!--contentsMenu Start-->
				<div class="corporate" id="dictionaryUl">
					<ul class="corporate_list">
						<c:forEach var="dictionaryItem" items="${dictionaryList}" varStatus="dictionaryItemLoopCount">
							<li><a href="#a" onclick="clickDictionary('${dictionaryItem.dictionaryId}')" <c:if test="${dictionaryItem.dictionaryId eq searchCondition.dictionaryId}">class="selected"</c:if> >${dictionaryItem.dictionaryName}</a></li>
						</c:forEach>
					</ul>	
					<div class="clear"></div>				
				</div>
				<!--//contentsMenu End-->
				
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">

						<table summary="<ikep4j:message pre='${preList}' key='searchSelect' />">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre='${preList}' key='searchSelect' /></th>
									<td width="95%">
									
										<!--conSearch Start-->
										<div class="conSearch">
											<div class="conSearch_sel">
												<spring:bind path="searchCondition.searchColumn">
													<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' id="select2" style="width:63px">
														<option value="wordName" <c:if test="${'wordName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchName' /></option>
														<option value="contents" <c:if test="${'contents' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchMemo' /></option>
														<option value="tag" <c:if test="${'tag' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchTag' /></option>
														<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preList}' key='searchRegisterName' /></option>
													</select>
												</spring:bind>
											</div>
											<spring:bind path="searchCondition.searchWord">
												<input name="${status.expression}" value="${status.value}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' style="width:171px" type="text" />
											</spring:bind>
											<a class="sel_btn" href="#a" id="dictionarySearchButton"><span>Search</span></a>
											<div class="clear"></div>
										</div>
										<!--//conSearch End-->
									
									</td>
								</tr>
							</tbody>
						</table>

						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
				</div>	
				<!--//blockSearch End-->

				
				<!--tab Start-->	
		 		<c:choose>
		 			<c:when test="${user.localeCode == 'ko'}">				
					<div id="divTab_s" class="iKEP_tab_s">
						<ul>
							<li><a href="#tabs-1">가</a></li>
							<li><a href="#tabs-1">나</a></li>
							<li><a href="#tabs-1">다</a></li>
							<li><a href="#tabs-1">라</a></li>
							<li><a href="#tabs-1">마</a></li>
							<li><a href="#tabs-1">바</a></li>
							<li><a href="#tabs-1">사</a></li>
							<li><a href="#tabs-1">아</a></li>
							<li><a href="#tabs-1">자</a></li>
							<li><a href="#tabs-1">차</a></li>
							<li><a href="#tabs-1">카</a></li>
							<li><a href="#tabs-1">타</a></li>
							<li><a href="#tabs-1">파</a></li>
							<li><a href="#tabs-1">하</a></li>
							<li><a href="#tabs-1">A-Z</a></li>
							<li><a href="#tabs-1">0-9</a></li>
						</ul>
						<div class="tab_con" style="padding-bottom: 0px;display:none;">         
							<div id="tabs-1"></div>         
						</div>						
					</div>	   						
		 			</c:when>
		 			<c:otherwise>				
						<div id="divTab_s" class="iKEP_tab_s">
							<ul>
								<li><a href="#tabs-2">A</a></li>
								<li><a href="#tabs-2">B</a></li>
								<li><a href="#tabs-2">C</a></li>
								<li><a href="#tabs-2">D</a></li>
								<li><a href="#tabs-2">E</a></li>
								<li><a href="#tabs-2">F</a></li>
								<li><a href="#tabs-2">G</a></li>
								<li><a href="#tabs-2">H</a></li>
								<li><a href="#tabs-2">I</a></li>
								<li><a href="#tabs-2">J</a></li>
								<li><a href="#tabs-2">K</a></li>
								<li><a href="#tabs-2">L</a></li>
								<li><a href="#tabs-2">M</a></li>
								<li><a href="#tabs-2">N</a></li>
								<li><a href="#tabs-2">O</a></li>
								<li><a href="#tabs-2">P</a></li>
								<li><a href="#tabs-2">Q</a></li>
								<li><a href="#tabs-2">R</a></li>
								<li><a href="#tabs-2">S</a></li>
								<li><a href="#tabs-2">T</a></li>
								<li><a href="#tabs-2">U</a></li>
								<li><a href="#tabs-2">V</a></li>
								<li><a href="#tabs-2">W</a></li>
								<li><a href="#tabs-2">X</a></li>
								<li><a href="#tabs-2">Y</a></li>
								<li><a href="#tabs-2">Z</a></li>
								<li><a href="#tabs-1" >가~하</a></li>
								<li><a href="#tabs-1" >0~9</a></li>									
							</ul>	
							<div class="tab_con" style="padding-bottom: 0px;display:none;">         
								<div id="tabs-2"></div>         
							</div>									
						</div>
		 			</c:otherwise>		 			
		 		</c:choose>					
				<!--//tab End-->
				
				<!--subTitle_2 Start-->
				<c:if test="${fn:length(searchResult.entity) > 0}">
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${preList}' key='subTitle02' /></h3>
				</div>
				</c:if>
				<!--//subTitle_2 End-->
									
				<!--blockListTable Start-->
				<div class="corporateView">
					<table summary="<ikep4j:message pre='${preList}' key='subTitle' />">
						<caption></caption>						
						<tbody id="listFrame">
							<c:if test="${fn:length(searchResult.entity) == 0}">
								<div style="margin-top:25px;margin-bottom:25px;text-align:center;"><ikep4j:message pre='${preMessage}' key='noSearchResult' /></div>				
							</c:if>

							<c:forEach var="dictionary" items="${searchResult.entity}" varStatus="dictionaryLoopCount">
								<tr>
									<td>
										<div class="corporateViewTitle">
										<span class="titlebold"><a href="getDictionary.do?wordId=${dictionary.wordId}">${dictionary.wordName}</a></span> 
										<c:if test="${dictionary.wordEnglishName != '' && dictionary.wordEnglishName != null}">
										<span class="engText">(${dictionary.wordEnglishName})</span>
										</c:if>									
										<span class="corporateViewInfo">
										    <c:choose>
										    	<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										    		<span class="corporateViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${dictionary.registerId}');return false;" >${dictionary.registerName}</a> ${dictionary.jobRankName} ${dictionary.teamName}</span>
										      	</c:when>
										      	<c:otherwise>
										      		<span class="corporateViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${dictionary.registerId}');return false;" >${dictionary.registerEnglishName}</a> ${dictionary.jobTitleEnglishName} ${dictionary.teamEnglishName}</span>
										      	</c:otherwise>
										    </c:choose>											
											<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />
											<span><ikep4j:timezone date="${dictionary.registDate}" pattern="message.collpack.dictionary.timezone.dateformat.type2" keyString="true"/></span>
											<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />
											<span><ikep4j:message pre='${preList}' key='search' /> <strong>${dictionary.hitCount}</strong></span>
										</span>
										</div>
										<div class="corporateViewCon"><ikep4j:extractText text="${dictionary.contents}" length="310"/></div>
										<!--tag list-->
										<div class="tableTag" id="tagForm_${dictionary.wordId}" style="border-top:0px">

											<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>"/>
											<input type="hidden" name="tagItemSubType" value="${dictionary.wordGroupId}"/>
											<input type="hidden" name="tagItemName" value="${dictionary.wordName}"/>
											<input type="hidden" name="tagItemContents" value="${fn:escapeXml(dictionary.contents)}"/>
											<input type="hidden" name="tagItemUrl" value="/collpack/dictionary/getDictionary.do?wordId=${dictionary.wordId}"/>																	
											<div class="corporateViewTag">
												<span class="ic_tag"><span><ikep4j:message pre='${preList}' key='searchTag' /></span></span>
													<c:forEach var="tag" items="${dictionary.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
												<span class="rebtn">
													<a href="#a" onclick="iKEP.tagging.tagFormView('${dictionary.wordId}');return false;" title="<ikep4j:message pre='${preView}' key='viewModify' />" class="ic_modify"></a>
												</span>											
											</div>

										</div>
										<!--//tag list-->											
									</td>
								</tr>							
							</c:forEach>						
																																													
						</tbody>
					</table>			
				</div>
				<!--//blockListTable End-->
				
				<c:if test="${searchResult.recordCount > 10}">
					<!--blockButton_3 Start-->
					<div class="blockButton_3"> 
						<a class="button_3" href="#a" onclick="moreList();return false;"><span id="moreText"><ikep4j:message pre='${preList}' key='listMore' /><img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>				
					</div>
					<!--//blockButton_3 End-->
				</c:if>
				
				</form>
</div>				
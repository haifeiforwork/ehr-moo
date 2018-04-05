<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.message.groupMessageListView.header" /> 
<c:set var="preList"    value="ui.support.message.groupMessageListView.list" />
<c:set var="preSearch"  value="ui.support.message.searchCondition" /> 
<c:set var="preButton"  value="ui.support.message.button" /> 
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/layout/layout-default-latest.css"/>" /> 

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.layout-latest.js"/>" ></script>

<script type="text/javascript">
<!-- 

	//ajax 메세지읽기
	groupMessageInfo = function(obj,messageId) {
		$jq("#showMsgDiv").removeClass("none");
		url = '<c:url value="/support/message/readMessage.do?messageId='+messageId+'" />';
		$jq.get(url)
	    .success(function(message) { 
	    	$jq("#dtSendName").text("");
	    	$jq("#dtReceiverList").text("");
	    	$jq("#dtContents").html("");
	    	$jq("#dtAttachFiles").html("");
	    	
	    	 <c:choose>
		      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
		       $jq("#dtSendName").text(message.senderName + " (" + message.sendDate + ")" );
		       $jq("#dtReceiverList").text(message.receiverList);
		      </c:when>
		      <c:otherwise>
		       $jq("#dtSendName").text(message.senderEnglishName + " (" + message.sendDate + ")" );
		       $jq("#dtReceiverList").text(message.receiverEnglishList);
		      </c:otherwise>
		     </c:choose>
	    	
	    	$jq("#dtContents").html(message.contents);
	    	if(message.fileDataList != null && message.fileDataList != "") {
	    		var infoAttachFiles = "<ul>";
	    		$jq.each(message.fileDataList, function() {
	    			infoAttachFiles = infoAttachFiles + "<li><a href=\"<c:url value='/support/fileupload/downloadFile.do?fileId="+this.fileId+"&amp;thumbnailYn=N' />\" >" + this.fileRealName + "</a></li>";
	    		});
	    		infoAttachFiles = infoAttachFiles + "</ul>";
	    		$jq("#dtAttachFiles").html(infoAttachFiles);
	    	}
	    	$jq("#readMessageId").val(message.messageId);
	    })    
		.error(function(event, request, settings) { alert("error"); });  
	};
	
	//레이아웃 체인지 함수
	 function changeLayout(layout) {
		$jq("input[name=layoutType]").val(layout);
		$jq("#searchForm").submit(); 
		return false; 
	};
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		<c:if test="${fn:contains(searchCondition.layoutType,'LR') }">
			iKEP.sizeFixWithWindow("#layoutMain");
			myLayout = $jq("#layoutMain").layout({
				//	enable showOverflow on west-pane so CSS popups will overlap north pane
				//	west__showOverflowOnHover: true	
				//	reference only - these options are NOT required because 'true' is the default
					resizeWithWindow:	true
				,	closable:				false	// pane can open & close
				,	resizable:				true	// when open, pane can be resized 
				,	slidable:				false	// when closed, pane can 'slide' open over other panes - closes on mouse-out
				
				//	some pane-size settings
				,   togglerLength_open:	0
				,	west__size: 			.50		// percentage size expresses as a decimal
				,	west__minSize:			200
				,	center__minSize:		200
			});
		</c:if>
		<c:if test="${getmessage != null }">
			$jq("#showMsgDiv").removeClass("none");
		</c:if>
		$jq("#searchMessageListButton").click(function() {   
			$jq("#searchForm").submit(); 
			return false; 
		});  
		
		$jq("select[name=pagePerRecord]").change(function() {
            $jq("#searchMessageListButton").click();  
        });  
		
		$jq("select[name=categoryId]").change(function() {
			$jq("#searchMessageListButton").click();  
        });  
		
		$jq("#checkIsUrgent").click(function() { 
		    $jq("#searchMessageListButton").click();  
        });
		
		$jq("#detailMsgButton a").click(function(){
			if($jq("#readMessageId").val() == ""){
				return false;
			}
			switch (this.id) {
                case "mailSendButton":
                	iKEP.sendMailPop();

					break;
				case "replyMessageButton":
					var reSendMessageId = $jq("#readMessageId").val();
					var url = "<c:url value='/support/message/messageNew.do'/>"+"?type=reply&reSendMessageId="+reSendMessageId;
					iKEP.popupOpen(url , {width:800, height:670});
					break;
				case "reSendMessageButton":
					var reSendMessageId = $jq("#readMessageId").val();
					var url = "<c:url value='/support/message/messageNew.do'/>"+"?type=resend&reSendMessageId="+reSendMessageId;
					iKEP.popupOpen(url , {width:800, height:670});
					break;
				case "keepMessageButton":
					if(confirm("<ikep4j:message pre='${preMsg}' key='moveConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/keepReceiveReadMessage.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
				case "deleteMessageButton":
					if(confirm("<ikep4j:message pre='${preMsg}' key='deleteConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/deleteReceiveReadMessage.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
                default:
                    break;
            }
        });
	});

//-->
</script>

		<!--blockListTable Start-->
		<form id="searchForm" method="post" action="<c:url value='/support/message/groupMessageListView.do?groupId=${groupId}' />">  
		<spring:bind path="searchCondition.messageId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
	<div class="blockListTable msgTable" style="margin-bottom:0;">
		<!--tableTop Start-->
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
			<div class="listInfo">
				<spring:bind path="searchCondition.pagePerRecord" >  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${messageCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			</div>	
			<div class="tableTop_check">
				<label>
					<spring:bind path="searchCondition.isUrgent">
						<input id="checkIsUrgent" name="${status.expression}" type="checkbox" class="checkbox" value="1" <c:if test="${'1' eq status.value}">checked</c:if> title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /></spring:bind><img src="<c:url value="/base/images/icon/ic_emer.gif"/>" alt="" /><span><ikep4j:message pre='${preSearch}' key='isUrgent' /></span>
				</label>
			</div>										
			<div class="listView_2">
				<spring:bind path="searchCondition.layoutType">		
				<div class="none"><ikep4j:message pre='${preSearch}' key='${status.expression}' post="webstandard"/></div>
				<input name="${status.expression}" type="hidden" value="${status.value}" /> 
				<ul> 
					<c:choose>
			   			 <c:when test="${fn:contains(status.value, 'LR')}">
			   			 	<li><a onclick="changeLayout('TB');" href="#a"><img src="<c:url value="/base/images/icon/ic_splitter_h.gif"/>" alt="" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' post='tb'/>" /></a></li>
					    </c:when>
					    <c:otherwise>
					    	<li><a onclick="changeLayout('LR');" href="#a"><img src="<c:url value="/base/images/icon/ic_splitter_v.gif"/>" alt="" title="<ikep4j:message pre='${preSearch}' key='${status.expression}' post='lr'/>" /></a></li>
					    </c:otherwise> 
					</c:choose>	 
				</ul>
				</spring:bind>
			</div>			
			<div class="tableSearch">
				<spring:bind path="searchCondition.categoryId" >  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<option value=""><ikep4j:message pre='${preSearch}' key='allSelect' /></option>
						<c:forEach var="category" items="${categoryList}">
							<option value="${category.categoryId}" <c:if test="${category.categoryId eq status.value}">selected="selected"</c:if> >
								<c:choose>
							      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
							       ${category.categoryName}
							      </c:when>
							      <c:otherwise>
							       ${category.categoryEnglishName} 
							      </c:otherwise>
							     </c:choose>
							</option>
						</c:forEach>
					</select>	
				</spring:bind>	
				<spring:bind path="searchCondition.searchColumn">  
					<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
						<option value="senderName" <c:if test="${'senderName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='senderName'/></option>
						<option value="contents" <c:if test="${'contents' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='contents'/></option>
					</select>		
				</spring:bind>												
				<spring:bind path="searchCondition.searchWord">  					
					<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
				</spring:bind>
				<a id="searchMessageListButton" name="searchMessageListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/ic_search.gif' />" alt="" title="<ikep4j:message pre='${preSearch}'  key='searchImg'/>"/></a>
			</div>			
			<div class="clear"></div>
		</div>
		<!--//tableTop End-->
	</div>
	<c:if test="${fn:contains(searchCondition.layoutType,'LR') }">
<div id="layoutMain" >	
	<div class="ui-layout-west">
		<!--blockLeft Start-->
		<div class="blockLeft"  style="width:100%;">
	</c:if>
		
		<div class="blockListTable msgTable">
			<table summary="<ikep4j:message pre="${preList}" key="summary" />">   
				<caption></caption>
				<col width="20"/>
				<col width="50"/>
				<col style="width: 12%;"/>
				<col style="width: *;"/>
				<col style="width: 18%;"/>
				<col width="40"/>
				<thead>
					<tr>
						<th scope="col"></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='gubun' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='senderName' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='contents' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='sendDate' /></th>
						<th scope="col" class="tdLast"><ikep4j:message pre='${preList}' key='attach' /></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="6" class="emptyRecord tdLast">
									<c:choose>
					    				<c:when test="${searchCondition.searchWord eq ''}">
					    					<ikep4j:message pre='${preSearch}' key='emptyReceiveRecord' />
					    				</c:when>
					    				<c:otherwise>
											<ikep4j:message pre='${preSearch}' key='emptyRecord' />
										</c:otherwise>
									</c:choose>
								</td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="messageItem" items="${searchResult.entity}" varStatus = "status"> 
							<tr class="msg_read">
								<td>
									<c:if test="${messageItem.isUrgent eq 1 }"><img src="<c:url value='/base/images/icon/ic_emer.gif' />" alt="<ikep4j:message pre='${preList}' key='isUrgent' />" /></c:if>
								</td>
								<td>
								 <c:choose>
							      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
							       ${messageItem.categoryName}
							      </c:when>
							      <c:otherwise>
							       ${messageItem.categoryEnglishName}
							      </c:otherwise>
							     </c:choose>
								</td>
								<td class="ellipsis">
								 <c:choose>
							      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
							       <a href="#a" title="${messageItem.senderName}">${messageItem.senderName}</a>
							      </c:when>
							      <c:otherwise>
							       <a href="#a" title="${messageItem.senderEnglishName}">${messageItem.senderEnglishName}</a>
							      </c:otherwise>
							     </c:choose>
								</td>
								<td class="textLeft ellipsis"><a onclick="groupMessageInfo(this,'${messageItem.messageId}')"  href="#a" title="${messageItem.contents}"> 
										<c:if test="${messageItem.isRead eq 0}"><strong title="${messageItem.contents}"></c:if> 
																				 <c:choose>
									      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
									       [${messageItem.groupName}]
									      </c:when>
									      <c:otherwise>
									       [${messageItem.groupEnglishName}]
									      </c:otherwise>
									     </c:choose>
										${messageItem.contents}
										<c:if test="${messageItem.isRead eq 0}"></strong></c:if></a></td>
								<td class="ellipsis" title="<ikep4j:timezone date="${messageItem.sendDate}" pattern="yyyy.MM.dd HH:mm:ss"/>"><ikep4j:timezone date="${messageItem.sendDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
								
								<td class="tdLast">
									<c:if test="${messageItem.attachCount > -1}">
										${messageItem.attachSize}M
									</c:if>
								</td>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
			
			<!--Page Numbur Start-->
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchMessageListButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Numbur End-->	
					
		</div>
		<!--//blockListTable End-->	
	<c:if test="${fn:contains(searchCondition.layoutType,'LR') }">
		</div>
	<!--//blockLeft End-->
	</div>
		
	<div class="ui-layout-center">	
		<!--blockRight Start-->
		<div class="blockRight"  style="width:100%;">
	</c:if>

		<!--blockMsgbox Start-->
		<div id="showMsgDiv" class="blockMsgbox none"> 
			<div class="msgbox_frame"></div>
			<table summary="<ikep4j:message pre='${preList}' key='summary'/>">
				<tbody>
					<tr>
						<th scope="row" width="80"><ikep4j:message pre='${preList}' key='senderName'/></th>
						<td><span id="dtSendName">
							<c:if test="${getmessage != null }">
							 <c:choose>
						      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
						       ${getmessage.senderName} (<ikep4j:timezone date="${getmessage.sendDate}" pattern="yyyy.MM.dd HH:mm"/>)
						      </c:when>
						      <c:otherwise>
						       ${getmessage.senderEnglishName} (<ikep4j:timezone date="${getmessage.sendDate}" pattern="yyyy.MM.dd HH:mm"/>)
						      </c:otherwise>
						     </c:choose>
							</c:if>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preList}' key='receiverUser'/></th>
						<td><span id="dtReceiverList">
							<c:if test="${getmessage != null}">
							 <c:choose>
						      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
						       ${getmessage.receiverList}
						      </c:when>
						      <c:otherwise>
						       ${getmessage.receiverEnglishList}
						      </c:otherwise>
						     </c:choose>
							</c:if>
							</span>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preList}' key='attachCount'/></th>
						<td><span id="dtAttachFiles"> &nbsp;
							<c:if test="${getmessage != null }">
								<ul>
								<c:forEach var="fileData" items="${getmessage.fileDataList}">
									<li><a href='<c:url value="/support/fileupload/downloadFile.do?fileId=${fileData.fileId}&amp;thumbnailYn=N" />' >${fileData.fileRealName}</a></li> 
								</c:forEach> 
								</ul>
							</c:if> 
							</span>						
						</td>
					</tr>
					<tr>
						<td class="tdLast" colspan="2">
							<span id="dtContents"><c:if test="${getmessage != null}">${getmessage.contents}</c:if></span>
						</td>
					</tr>														
				</tbody>
			</table>
			<div id="detailMsgButton" class="blockMsgbtn none">
				<input id="readMessageId" name="readMessageId" type="hidden" value='<c:if test="${getmessage != null}"> ${getmessage.messageId}</c:if>' />
				<input id="readSenderId" name="readSenderId" type="hidden" value='<c:if test="${getmessage != null}"> ${getmessage.senderId}</c:if>' />
				<a id="mailSendButton"   class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='mail'/></span></a>
				<a id="replyMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='reply'/></span></a>
				<a id="reSendMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='reSend'/></span></a>
				<a id="keepMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='keep'/></span></a>
				<a id="deleteMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
			</div>
		</div>
		<!--//blockMsgbox End-->
		
	<c:if test="${fn:contains(searchCondition.layoutType,'LR') }">
		</div>
	</div>
	<!--blockRight END-->
</div>
	</c:if>		
	</form>							


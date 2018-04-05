<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.message.messageSendListView.header" /> 
<c:set var="preList"    value="ui.support.message.messageSendListView.list" />
<c:set var="prepopLayer"    value="ui.support.message.messageSendListView.popLayer" />
<c:set var="preSearch"  value="ui.support.message.searchCondition" /> 
<c:set var="preButton"  value="ui.support.message.button" /> 
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/layout/layout-default-latest.css"/>" /> 

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.layout-latest.js"/>" ></script>
<script type="text/javascript">
<!-- 
	var messageContents;
	var messagefileIds;
	var messagefileRealNames;

	//ajax 메세지읽기
	getMessageInfo = function(messageId) {
		$jq("#showMsgDiv").removeClass("none");	
		$jq.get('<c:url value="/support/message/readMessage.do?messageId='+messageId+'" />')
	    .success(function(message) { 
	    	$jq("#dtSendName").text("");
	    	$jq("#dtReceiverList").text("");
	    	$jq("#dtContents").html("");
	    	$jq("#dtAttachFiles").html("");
	    	messageContents = "";
	    	messagefileIds = new Array();
			messagefileRealNames = new Array();
	    	
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
		    
		    messageContents = message.contents;
		    if($jq("#textViewOption").is(":checked")) {
		    	$jq("#dtContents").text(messageContents);
		    } else {
		    	$jq("#dtContents").html(messageContents);
		    }
		    $jq("#fileTr").hide();
	    	if(message.fileDataList != null && message.fileDataList != "") {
	    		var infoAttachFiles = "<ul>";
	    		$jq.each(message.fileDataList, function(index) {
	    			infoAttachFiles = infoAttachFiles + "<li><a href=\"<c:url value='/support/fileupload/downloadFile.do?fileId="+this.fileId+"&amp;thumbnailYn=N' />\" >" + this.fileRealName + "</a></li>";
	    			messagefileIds[index] = this.fileId; 
	    			messagefileRealNames[index] = this.fileRealName;
	    		});
	    		infoAttachFiles = infoAttachFiles + "</ul>";
	    		$jq("#dtAttachFiles").html(infoAttachFiles);
	    		$jq("#fileTr").show();
	    	}
	    	$jq("#readMessageId").val(message.messageId);
	    	if (message.receiverCheck == "N") {
	    		$jq("#withdrawMessageButton").show();
	    	} else {
	    		$jq("#withdrawMessageButton").hide();
	    	}
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
		var dialogPopLayer = $jq("#popLayer").dialog({width: 500, height:350, modal:false, resizable: false,autoOpen:false});
		
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
		if("${searchCondition.resultCode}" == "DONT" ) {
			alert("<ikep4j:message pre='${preMsg}' key='withdrawDont'/>");
		} else if ("${searchCondition.resultCode}" == "DONE") {
			alert("<ikep4j:message pre='${preMsg}' key='withdrawDone'/>");
		}
		
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
 
		$jq("#checkboxAllMessageItem").click(function() { 
			$jq("input[name=checkboxMessageItem]").attr("checked", $jq(this).is(":checked"));  
		}); 
		$jq("#detailTopButton a").click(function(){
			//선택된 항목이 없다면 리턴.
			if($jq("input[name=checkboxMessageItem]:checked").length == 0){
				alert("<ikep4j:message pre='${preMsg}' key='select'/>")
				return false;
			}
			switch (this.id) {
                case "deleteListBtn":
                	if(confirm("<ikep4j:message pre='${preMsg}' key='deleteConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/deleteSendList.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
				case "keepListBtn":
					if(confirm("<ikep4j:message pre='${preMsg}' key='moveConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/keepSendList.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
                default:
                    break;
            }
        });
		
		$jq("#detailMsgButton a").click(function(){
			if($jq("#readMessageId").val() == ""){
				alert("<ikep4j:message pre='${preMsg}' key='select'/>")
				return false;
			}
			switch (this.id) {
                case "mailSendButton":
                	var nameList = [];
        			var emailList = [];
        			var title = "";
        			var content = messageContents;
        			var fileIdList = messagefileIds;
        			var fileNameList = messagefileRealNames;
        			iKEP.sendMailPop(nameList, emailList, title, content, fileIdList, fileNameList);	
					break;
                case "reSendMessageButton":
					var reSendMessageId = $jq("#readMessageId").val();
					var url = "<c:url value='/support/message/messageNew.do'/>"+"?type=resend&reSendMessageId="+reSendMessageId;
					iKEP.popupOpen(url , {width:800, height:670});
					break;
                case "withdrawMessageButton":
					if(confirm("<ikep4j:message pre='${preMsg}' key='withdrawConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/withdrawMessage.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
				case "keepMessageButton":
					if(confirm("<ikep4j:message pre='${preMsg}' key='moveConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/keepSendReadMessage.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
				case "deleteMessageButton":
					if(confirm("<ikep4j:message pre='${preMsg}' key='deleteConfirm'/>")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/message/deleteSendReadMessage.do"/>');
	    				$jq('#searchForm').submit();
                	}
					break;
                default:
                    break;
            }
        });
		
		//레이어 나오기
		popLayerOpen = function(messageId, rowNum) {
			$jq("#moreButton").hide();
			$jq("#moreView").hide();
			$jq.get('<c:url value="/support/message/messageReceiveList.do?messageId='+messageId+'&rowNum='+rowNum+'" />')
		    .success(function(messageReceiveList) { 
		        var tbody = document.getElementById('innerTbl');
		        $jq("#innerTbl").empty();
		        var tcount = 0;
		    	$jq.each(messageReceiveList, function(idx) {
	                var tableTr = document.createElement('tr');
			        var tableTd1 = document.createElement('td');
			        var tableTd2 = document.createElement('td');
			        var tableTd3 = document.createElement('td');
			        tableTd1.appendChild(document.createTextNode(messageReceiveList[idx].rnum));
			        
			        <c:choose>
				      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				        tableTd2.appendChild(document.createTextNode(messageReceiveList[idx].userName));
				      </c:when>
				      <c:otherwise>
				        tableTd2.appendChild(document.createTextNode(messageReceiveList[idx].userEnglishName));
				      </c:otherwise>
				    </c:choose>
				     
			        var readDay = messageReceiveList[idx].readDate;
			        if (readDay == null) readDay = "<ikep4j:message pre='${prepopLayer}' key='unconfirmedMessage' />";
			        tableTd3.appendChild(document.createTextNode(readDay));
			        tableTr.appendChild(tableTd1);
			        tableTr.appendChild(tableTd2);
			        tableTr.appendChild(tableTd3);
			        tableTd3.setAttribute('className','tdLast');
			        tbody.appendChild(tableTr);
			        tcount = messageReceiveList[idx].recodeCount;
				});
		    	$jq("#viewCount").val(rowNum);
		    	$jq("#viewMessage").val(messageId);
		    	if (tcount > rowNum) {
		    		$jq("#moreView").show();
		    		$jq("#moreButton").show();
		    	}
		    	
		    	dialogPopLayer.dialog( "open" );
		    	
		    	return false; 
		    })    
			.error(function(event, request, settings) { alert("error"); }); 
		};
		$jq("#moreButton").click(function() { 
			var sCount = parseInt($jq("#viewCount").val()) + 10;
			popLayerOpen($jq("#viewMessage").val(),sCount);
		});
		$jq("#textViewOption").click(function() {
		    if($jq("#textViewOption").is(":checked")) {
		    	$jq("#dtContents").text(messageContents);
		    } else {
		    	$jq("#dtContents").html(messageContents);
		    }
		});  
	});
//-->
</script>
<!--popup Start-->
<div id="popLayer" title="<ikep4j:message pre='${prepopLayer}' key='title' />">
	<div class="blockListTable msgTable">
		<table>   
			<caption></caption>
			<col style="width: 7%;"/>
			<col style="width: 40%;"/>
			<col style="width: *;"/>
			<thead>
				<tr>
				<th scope="col"><ikep4j:message pre='${prepopLayer}' key='seq' /></th>
				<th scope="col"><ikep4j:message pre='${prepopLayer}' key='user' /></th>
				<th scope="col" class="tdLast"><ikep4j:message pre='${prepopLayer}' key='receiveDate' /></th>
				</tr>
			</thead>
			<tbody id="innerTbl"><tr><td colspan="3">&nbsp</td></tr></tbody>
		</table>
	</div>
	<div id="moreView" class="blockMsgbtn">
		<input id="viewMessage" type="hidden" value="10"/>
		<input id="viewCount" type="hidden" value="10"/>
		<a id="moreButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='more'/></span></a>
	</div>
</div>
<!--popup End-->			
		<!--blockListTable Start-->
		<form id="searchForm" method="post" action="<c:url value='/support/message/messageSendListView.do' />">  
	<div class="blockListTable msgTable" style="margin-bottom:0;">
		<!--tableTop Start-->
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
			<div id="detailTopButton" class="tableTop_btn">
				<a id="deleteListBtn" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
				<a id="keepListBtn" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='keep'/></span></a>
			</div>
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
			<div class="listView_2">
				<spring:bind path="searchCondition.layoutType">		
				<div class="none"><ikep4j:message pre='${preSearch}' key='${status.expression}' post="webstandard"/></div>
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
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
						<option value="receiverName" <c:if test="${'receiverName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='receiverName'/></option>
						<option value="contents" <c:if test="${'contents' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='contents'/></option>
					</select>		
				</spring:bind>												
				<spring:bind path="searchCondition.searchWord">  					
					<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
				</spring:bind>
				<a id="searchMessageListButton" name="searchMessageListButton" href="#a" class="ic_search"><span>Search</span></a>
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
				<col width="30"/>
				<col width="50"/>
				<col style="width: 12%;"/>
				<col style="width: *;"/>
				<col style="width: 18%;"/>
				<col style="width: 16%;"/>
				<col width="40"/>
				<thead>
					<tr>
						<th scope="col"><input id="checkboxAllMessageItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
						<th scope="col"><ikep4j:message pre='${preList}' key='gubun' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='receiver' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='contents' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='receiveDate' /></th>
						<th scope="col"><ikep4j:message pre='${preList}' key='receiverCheck' /></th>
						<th scope="col" class="tdLast"><ikep4j:message pre='${preList}' key='attach' /></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="7" class="emptyRecord tdLast">
									<c:choose>
					    				<c:when test="${searchCondition.searchWord eq ''}">
					    					<ikep4j:message pre='${preSearch}' key='emptySendRecord' />
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
							<tr>
								<td><input name="checkboxMessageItem" class="checkbox" title="checkbox" type="checkbox" value="${messageItem.messageId}" /></td>
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
							       ${messageItem.receiverList}
							      </c:when>
							      <c:otherwise>
							       ${messageItem.receiverEnglishList}
							      </c:otherwise>
							     </c:choose>
								</td>
								<td class="textLeft ellipsis"><a onclick="getMessageInfo(${messageItem.messageId})" href="#a" title="${messageItem.contents}"> 
										${messageItem.contents} </a></td>
								<td class="ellipsis" title="<ikep4j:timezone date="${messageItem.sendDate}" pattern="yyyy.MM.dd HH:mm:ss"/>"><ikep4j:timezone date="${messageItem.sendDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
								<td><a onclick="popLayerOpen(${messageItem.messageId},10)" href="#a"> 
									${messageItem.receiverCheck} </a></td>
								<td class="tdLast">
									<c:if test="${messageItem.attachCount > -1}">
										<c:choose>
											<c:when test="${messageItem.attachSize >= 1024}"><fmt:formatNumber value='${messageItem.attachSize/1024}' pattern="0" />M</c:when>
											<c:otherwise>${messageItem.attachSize}K</c:otherwise>
										</c:choose>
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
			<div style="text-align:right;font-size:9px;"><input id="textViewOption" class="checkbox" title="checkbox" type="checkbox" />TEXT VIEW &nbsp;</div>
			<table summary="<ikep4j:message pre='${preList}' key='summary'/>">
				<tbody>
					<tr>
						<th scope="row" width="80"><ikep4j:message pre='${preList}' key='senderName' /></th>
						<td><span id="dtSendName"></span>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preList}' key='receiver' /></th>
						<td><span id="dtReceiverList"></span>
						</td>
					</tr>
					<tr id="fileTr">
						<th scope="row"><ikep4j:message pre='${preList}' key='attachCount' /></th>
						<td><span id="dtAttachFiles">&nbsp;</span></td>
					</tr>
					<tr>
						<td class="tdLast" colspan="2">
							<span id="dtContents">&nbsp</span>
						</td>
					</tr>														
				</tbody>
			</table>
			<div id="detailMsgButton" class="blockMsgbtn">
				<input id="readMessageId" name="readMessageId" type="hidden" value="" />
				<a id="mailSendButton"   class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='mail'/></span></a>
				<a id="reSendMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='reSend'/></span></a>
				<a id="keepMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='keep'/></span></a>
				<a id="withdrawMessageButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='withdraw'/></span></a>
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

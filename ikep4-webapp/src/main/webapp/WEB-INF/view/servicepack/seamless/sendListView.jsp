<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.seamless.sendListView.header" /> 
<c:set var="preSearch"  value="ui.servicepack.seamless.searchCondition" /> 
<c:set var="preButton"  value="ui.servicepack.seamless.button" /> 
<c:set var="preMsg"  value="ui.servicepack.seamless.MSG" /> 
<c:set var="preDetail"  value="ui.servicepack.seamless.sendListView.detail" /> 
<c:set var="prepopLayer"    value="ui.support.message.messageSendListView.popLayer" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script src="http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>

<script type="text/javascript">
<!--

var globalMessageType="${messageType}";	//메세지 타입

//history List 담기
var objHistory = new Object();
	//onload시 수행할 코드
	$jq(document).ready(function() {
		<c:choose>
			<c:when test="${searchCondition.messageType eq 'Mail'}">
				$jq("#viewMail").attr("class", "button_s selected");
			</c:when>
			<c:when test="${searchCondition.messageType eq 'Message'}">
				$jq("#viewMessage").attr("class", "button_s selected");
			</c:when>
			<c:when test="${searchCondition.messageType eq 'SMS'}">
				$jq("#viewSMS").attr("class", "button_s selected");
			</c:when>
			<c:otherwise>
				$jq("#viewAll").attr("class", "button_s selected");
			</c:otherwise>
		</c:choose>
		
		$jq("#viewAll").click(function(){
	        $jq("input[name=messageType]").val("");
				$jq('#searchForm').submit();
	    });
		$jq("#viewMail").click(function(){
	    	$jq("input[name=messageType]").val("Mail");
			$jq('#searchForm').submit();
		});
		$jq("#viewMessage").click(function(){
	    	$jq("input[name=messageType]").val("Message");
			$jq('#searchForm').submit();
		});
		$jq("#viewSMS").click(function(){
	    	$jq("input[name=messageType]").val("SMS");
			$jq('#searchForm').submit();
		});
		
		if ( parseInt("${sendListSize}") < 10) $jq("#bt_div").hide(); 
		if ("${lastDate}" != "") $jq("#lastDate").val("${lastDate}");

		$jq("#searchListButton").click(function() {   
			$jq("#searchForm").submit(); 
			return false; 
		}); 
		
		$jq("#checkboxAllMessageItem").click(function() { 
			$jq("input[name=checkboxMessageItem]").attr("checked", $jq(this).is(":checked"));  
		}); 
		
		$jq("#deleteListBtn").click(function(){
			//선택된 항목이 없다면 리턴.
			if($jq("input[name=checkboxMessageItem]:checked").length == 0){
				return false;
			}
            if(confirm("<ikep4j:message pre='${preMsg}' key='deleteConfirm'/>")) { 
            	$jq('#searchForm').attr('action', '<c:url value="/servicepack/seamless/deleteSendBoxList.do"/>');
  				$jq('#searchForm').submit();
            }
        });
		
		$jq("#moreList_bt").click(function(){
			url = "<c:url value='/servicepack/seamless/sendList.do'/>";
			searchColumn = $jq("select[name=searchColumn]").val();
			searchWord  = $jq("input[name=searchWord]").val();
			messageType = $jq("input[name=messageType]").val();
			lastDate = $jq("#lastDate").val();
			$jq.ajax({
			 	type: 'post',
			 	url: url,
			 	data: {'searchColumn':searchColumn, 'searchWord':searchWord, 'messageType':messageType, 'lastDate':lastDate}
			 })
			 .success(function(data) {
				 localeYn = 0;
				 <c:if test="${user.localeCode == portal.defaultLocaleCode}">localeYn = 1;</c:if>
				 $jq("#moreListTmpl").tmpl(data,localeYn).appendTo("tbody","#sendTable");
				 if ( data.length < 10) $jq("#bt_div").hide(); 
				 $jq("#lastDate").val($jq(data).get(data.length-1).sendDate);
		    })    
			.error(function(event, request, settings) { alert("error"); });  
		});
		$jq("#beforeHistory_bt").click(function () { 
			url = "<c:url value='/servicepack/seamless/moreHistoryList.do'/>";
			guestId = $jq("#h_guestId").val();
			searchColumn = "before";
			lastDate = $jq("#h_firstDate").val();
			if (guestId != "") {
				$jq.ajax({
				 	type: 'post',
				 	url: url,
				 	data: {'guestId':guestId,'searchColumn':searchColumn, 'lastDate':lastDate, 'messageType':globalMessageType}
				 })
				 .success(function(data) {
					 objHistory.data = $jq.merge( $jq.merge([],objHistory.data), data); 
					 $jq("#contact_div").empty();
					 beforDate = "";
					 $jq(objHistory.data).each(function(index){
						 $jq("#morehistorytmpl").tmpl(this, beforDate).appendTo("#moreHistory ul","#contact_div");
						 beforDate = this.stringDate;
					  });
					 if ( data.length < 5) $jq("#beforeHistory_bt").attr("disabled",true);
					 if ( data.length > 0) $jq("#h_firstDate").val($jq(data).get(data.length-1).sendDateOrg);
					 
					 if(data.length == 0){
						 alert('<ikep4j:message  key='ui.servicepack.seamless.list.notMore'/>');
					 }
				 
				 })    
				.error(function(event, request, settings) { alert("error"); });  
			}
		});
		$jq("#afterHistory_bt").click(function () { 
			url = "<c:url value='/servicepack/seamless/moreHistoryList.do'/>";
			guestId = $jq("#h_guestId").val();
			searchColumn = "after";
			lastDate = $jq("#h_lastDate").val();
			if (guestId != "") {
				$jq.ajax({
				 	type: 'post',
				 	url: url,
				 	data: {'guestId':guestId,'searchColumn':searchColumn, 'lastDate':lastDate, 'messageType':globalMessageType}
				 })
				 .success(function(data) {
					 objHistory.data = $jq.merge( $jq.merge([],data), objHistory.data); 
					 $jq("#contact_div").empty();
					 beforDate = "";
					 $jq(objHistory.data).each(function(index){
						 $jq("#morehistorytmpl").tmpl(this, beforDate).appendTo("#moreHistory ul","#contact_div");
						 beforDate = this.stringDate;
					  });
					 if ( data.length < 5) $jq("#afterHistory_bt").attr("disabled",true);
					 if ( data.length > 0) $jq("#h_lastDate").val($jq(data).get(0).sendDateOrg);
					 
					 if(data.length == 0){
						 alert('<ikep4j:message  key='ui.servicepack.seamless.list.notMore'/>');
					 }
			    })    
				.error(function(event, request, settings) { alert("error"); });  
			}
		});
		var dialogPopLayer = $jq("#receiveListPopLayer").dialog({width: 500, height:350, modal:false, resizable: false,autoOpen:false});
		//레이어 나오기
		popLayerOpen = function(messageId, rowNum) {
			$jq("#popMoreButton").hide();
			$jq("#morePopView").hide();
			$jq.get('<c:url value="/support/message/messageReceiveList.do?messageId='+messageId+'&rowNum='+rowNum+'" />')
		    .success(function(messageReceiveList) { 
		        var tbody = document.getElementById('innerTbody');
		        $jq("#innerTbody").empty();
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
		    	$jq("#popViewCount").val(rowNum);
		    	$jq("#popViewMessage").val(messageId);
		    	if (tcount > rowNum) {
		    		$jq("#morePopView").show();
		    		$jq("#popMoreButton").show();
		    	}
		    	
		    	dialogPopLayer.dialog( "open" );
		    	
		    	return false; 
		    })    
			.error(function(event, request, settings) { alert("error"); }); 
		};
		$jq("#popMoreButton").click(function() { 
			var sCount = parseInt($jq("#popViewCount").val()) + 10;
			popLayerOpen($jq("#popViewMessage").val(),sCount);
		});
	});
	//ajax 메세지읽기
	var oldObj;
	getMessageInfo = function(obj,messageType,messageId, receiverId, profilePicturePath) {
		$jq("#rightBox").removeClass("none");
		$jq(oldObj).removeClass("selected");
		$jq($jq($jq($jq(obj).parent("div")).parent("div")).parent("td")).parent("tr").addClass("selected");
		oldObj = $jq($jq($jq($jq(obj).parent("div")).parent("div")).parent("td")).parent("tr");
		url = "<c:url value='/servicepack/seamless/messageView.do'/>";
		$jq.post(url, 
				{'messageType':messageType,'messageId':messageId, 'boxType':'S' }, 
				function(data) {
					if("" != data){
						data = $jq.trim(data);
						$jq("#messageView").html(data);
					}
		});

		getHistoryInfo(messageId, receiverId, profilePicturePath);

		window.scrollTo(0,0); 
	}
	//contactHistory읽기
	
	getHistoryInfo = function(messageId, guestId, profilePicturePath) {
		
		$jq("#rightBox").removeClass("none");
		$jq("#guestImg").html("<img src=\"<c:url value='/"+profilePicturePath+"' />\" alt=\"userImg\" style=\"width:50px; height:50px;\" onerror=\"this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'\" />");
		$jq.ajax({
		 	type: 'post',
		 	url: "<c:url value='/servicepack/seamless/lastContactDate.do'/>",
		 	data: {'guestId':guestId }
		 })
		 .success(function(data) {
			 if("" != data){
					$jq("#lastContactDate").text(data);
				}
	    })    
		.error(function(event, request, settings) { alert("error"); });  	
		
		url = "<c:url value='/servicepack/seamless/historyListView.do'/>";
		$jq.ajax({
		 	type: 'post',
		 	url: url,
		 	data: {'messageId':messageId, 'guestId':guestId, 'messageType':globalMessageType }
		 })
		 .success(function(data) {
			 $jq("#afterHistory_bt").attr("disabled",false);
			 $jq("#beforeHistory_bt").attr("disabled",false);
			 $jq("#contact_div").empty();
			 $jq("#h_guestId").val(guestId);
			 objHistory.data = data;
			 beforDate = "";
			 $jq(objHistory.data).each(function(index){
				 $jq("#morehistorytmpl").tmpl(this, beforDate).appendTo("#contact_div");
				 beforDate = this.stringDate;
			  });
			 $jq("#h_firstDate").val($jq(data).get(data.length-1).sendDateOrg);
			 $jq("#h_lastDate").val($jq(data).get(0).sendDateOrg);
	    })    
		.error(function(event, request, settings) { alert("error"); });  
	}
	popMessageInfo = function(messageType, messageId, boxType) {
		url = "<c:url value='/servicepack/seamless/messageView.do'/>";
		$jq.post(url, 
				{'messageType':messageType,'messageId':messageId, 'boxType':boxType }, 
				function(data) {
					if("" != data){
						data = $jq.trim(data);
						$jq("#popMessageView").html(data);
						var result = $jq("#popLayer").dialog({width: 600, height:350, modal:false, resizable: false});
				    	return false; 
					}
		});
	}
	recipientPop =  function(messageId,messageType,boxType) {
		if(messageType == "Mail") {
			$jq("#receivePop_"+messageId).dialog({width: 500, height:350, modal:false, resizable: false});
		} else if (messageType == "Message" && boxType == "S") {
			popLayerOpen(messageId, 10);
		}
	}
	reSendMessage = function(messageId,messageType,boxType) {
		var url = "<c:url value='/servicepack/seamless/messageSendView.do'/>"
			+"?type=resend&reSendMessageId="+messageId+"&messageType="+messageType+"&boxType="+boxType;
		iKEP.popupOpen(url , {width:500, height:500});
	}
	replyMessage = function(messageId,messageType,boxType) {
		var url = "<c:url value='/servicepack/seamless/messageSendView.do'/>"
			+"?type=reply&reSendMessageId="+messageId+"&messageType="+messageType+"&boxType="+boxType;
		iKEP.popupOpen(url , {width:500, height:500});
	}
	delMessage = function(messageId,messageType,boxType) {
		url = "<c:url value='/servicepack/seamless/deleteMessage.do'/>";
		if(confirm("<ikep4j:message pre='${preMsg}' key='deleteConfirm'/>")) { 
			$jq.ajax({
			 	type: 'post',
			 	url: url,
			 	data: {'messageId':messageId, 'messageType':messageType, 'boxType':boxType }
			 })
			 .success(function(data) {
				 alert("<ikep4j:message pre='${preMsg}' key='delete'/>");
				 $jq('#searchForm').submit();
		    })    
			.error(function(event, request, settings) { alert("error"); });
		}
	}
//-->
</script>
<script id="moreListTmpl" type="text/x-jquery-tmpl">
<![CDATA[ //>
		<tr class="hover">
			<td class="smsvs_box_check">
				<input name="checkboxMessageItem" class="checkbox" title="checkbox" type="checkbox" value="\${messageType}/\${messageId}" />
			</td>		
			<td class="smsvs_box_img">
				<span class="icbox">
				{{if (messageType == 'Mail')}}
					<img src="<c:url value='/base/images/icon/ic_mail_b.png'/>" alt="mail" />
				{{else (messageType == 'Message')}}
					<img src="<c:url value='/base/images/icon/ic_note_b.png'/>" alt="message" />
				{{else (messageType == 'SMS')}}
					<img src="<c:url value='/base/images/icon/ic_sns_b.png'/>" alt="message" />
				{{/if}}
				</span>
			</td>	
			<td class="smsvs_box_con" style="word-wrap: break-word;">
			<div class="smsvs_box_con_t" style="width:265px;">
				<div class="smsvs_box_con_tl">
					<a href="#a" onclick="getMessageInfo(this,'\${messageType}','\${messageId}','\${receiverId}','\${profilePicturePath}' );return false;"><span>\${contents}</span></a>
				</div>
				<div class="smsvs_box_con_tr">
					{{if attachCount > 0}}
					<img src="<c:url value='/base/images/icon/ic_attach.gif'/>" alt="files" /> (\${attachCount})
					{{/if}}
				</div>
			</div>
			<div class="smsvs_box_con_info">
				<div class="smsvs_box_con_name"><a href="#a">\${senderName}  \${senderJobTitleName}</a></div>
				<div class="smsvs_box_con_team"><a href="#a">\${senderTeamName}</a></div>
				{{if (receiverName == '') }}
					<div class="smsvs_box_con_name"><a href="#a">\${receiverMail}</a></div>
				{{else}}
					<div class="smsvs_box_con_team">
						{{if (localeYn == 1)}}
							\${receiverTeamName}
						{{else}}
							\${receiverTeamEngName}
						{{/if}}
					&nbsp;</div>
					<div class="smsvs_box_con_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '\${receiverId}', 'bottom');return false;">
						{{if (localeYn == 1)}}
							\${receiverName}  \${receiverJobTitleName}
						{{else}}
							\${receiverEngName}  \${receiverJobTitleEngName}
						{{/if}}
					{{if (receiverCount > 1) }} <ikep4j:message pre='\${preDetail}' key='overPer'/> \${receiverCount -1}<ikep4j:message pre='\${preDetail}' key='person'/>{{/if}}</a></div>					
				{{/if}}
				<div class="smsvs_box_con_date">
					\${jsSendDate}
				</div>
			</div>			
			</td>					
		</tr>
<!-- ]]> -->
</script>
<script id="morehistorytmpl" type="text/x-jquery-tmpl">
<![CDATA[ //>
{{if beforDate != stringDate}}
	{{if beforDate != ''}} <div>&nbsp;</div> {{/if}}
	{{if (isToday == 1) }}
		<div class="roundTitle">
 			<h4>Today</h4>
			<div class="roundTitle_l"></div><div class="roundTitle_r"></div>
		</div>
	{{else}}
		<div class="roundTitle">
			<h4>\${jsSendDate}</h4>
			<div class="roundTitle_l"></div><div class="roundTitle_r"></div>
		</div>
	{{/if}}
{{/if}}
<li>
	{{if (boxType == 'R') }}
		<div class="contactBox_cl">
	{{else}}
		<div class="contactBox_cr">
	{{/if}}
	{{if (boxType == 'S') }}
			<div class="contactBox_cr_date"> \${jsSendTime}</div>
	{{/if}}
	{{if (boxType == 'R') }}
			<div class="box_gradient_1">
	{{else}}
			<div class="box_gradient_2">
	{{/if}}	
				<div class="contactBox_ic">
				{{if (messageType == 'Mail' && isRead == 0) }}
					<img src="<c:url value='/base/images/icon/ic_mail_2.gif'/>" alt="mail" />
				{{else (messageType == 'Mail' && isRead == 1) }}
					<img src="<c:url value='/base/images/icon/ic_mail_3.gif'/>" alt="mail" />
				{{else (messageType == 'Message' && isRead == 0) }}
					<img src="<c:url value='/base/images/icon/ic_note_2.gif'/>" alt="message" />
				{{else (messageType == 'Message' && isRead == 1) }}
					<img src="<c:url value='/base/images/icon/ic_note_2_b.gif'/>" alt="message" />
				{{else (messageType == 'SMS')}}
					<img src="<c:url value='/base/images/icon/ic_phone1.gif'/>" alt="message" />
				{{/if}}
				</div>
				<div class="contactBox_p"><a onclick="popMessageInfo('\${messageType}','\${messageId}','\${boxType}');" href="#a"> \${contents} </a></div>
				<div class="attachfile">
					{{if attachCount > 0}}
						<a href="#a"><img src="<c:url value='/base/images/icon/ic_attach.gif'/>" alt="files" /></a>
					{{/if}}
				</div>
				<div class="ct"></div>
				<div class="cb"></div>									
				<div class="lt"></div>
				<div class="rt"></div>										
				<div class="lb"></div>
				<div class="rb"></div>
				<div class="ar"></div>
			</div>	
	{{if (boxType == 'R') }}
			<div class="contactBox_cl_date">\${jsSendTime}</div>
	{{/if}}
		</div>
</li>
<!-- ]]> -->
</script>

<div class="none" id="popLayer" title="Message">
	<div id="popMessageView" > </div>
</div>

<!--popup Start-->
<div id="receiveListPopLayer" title="<ikep4j:message pre='${prepopLayer}' key='title' />">
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
			<tbody id="innerTbody"><tr><td colspan="3">&nbsp</td></tr></tbody>
		</table>
	</div>
	<div id="morePopView" class="blockMsgbtn">
		<input id="popViewMessage" type="hidden" value="10"/>
		<input id="popViewCount" type="hidden" value="10"/>
		<a id="popMoreButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='more'/></span></a>
	</div>
</div>
<!--popup End-->	

		<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
		
		<div class="smsvs_blockAll">
			<!--smsvs_blockLeft Start-->
			<div class="smsvs_blockLeft">
				<form id="searchForm" method="post" action="<c:url value='/servicepack/seamless/sendListView.do' />">  
				<input type="hidden" id="lastDate"/>
				
				<!--corner_RoundBox07 Start-->
				<div class="corner_RoundBox07">	
					<div class="search_box">
						<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
						<div class="tableSearch">
							<spring:bind path="searchCondition.searchColumn">  
								<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
									<option value="contents" <c:if test="${'contents' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='contents'/></option>
									<option value="receiverName" <c:if test="${'receiverName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='receiverName'/></option>
								</select>		
							</spring:bind>													
							<spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
							</spring:bind>
							<a href="#a" id="searchListButton" class="ic_search"><span><ikep4j:message pre='${preSearch}'  key='searchImg'/></span></a>
						</div>
						<div class="clear"></div>
					</div>
					
					<div class="smsvsTable">
						<div class="smsvsTable_view">
							<h3 class="none">view button</h3>
							<a id="viewAll" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='all'/></span></a>
							<a id="viewMail" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='email'/></span></a>
							<a id="viewMessage" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='message'/></span></a>
							<a id="viewSMS" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='sms'/></span></a>
							<spring:bind path="searchCondition.messageType">  					
								<input name="${status.expression}" value="${status.value}" type="hidden" />
							</spring:bind>
						</div>
						<table summary="<ikep4j:message pre="${preHeader}" key="pageTitle" />">
							<caption></caption>
							<thead>
								<tr>
									<th colspan="3" scope="col"><input id="checkboxAllMessageItem" class="checkbox" title="<ikep4j:message pre='${preButton}' key='selectAll'/>" type="checkbox" value="" />&nbsp;&nbsp;<a id="deleteListBtn" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></th>
								</tr>
							</thead>
							<tbody id="sendTable">
								<c:choose>
								    <c:when test="${sendList eq '[]' }">
										<tr>
											<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
										</tr>				        
								    </c:when>
								    <c:otherwise>
										<c:forEach var="sendItem" items="${sendList}" varStatus = "status"> 									
										<tr class="hover"><!-- tr class="selected"> -->
											<td class="smsvs_box_check">
												<input name="checkboxMessageItem" class="checkbox" title="checkbox" type="checkbox" value="${sendItem.messageType}/${sendItem.messageId}" />
											</td>
											<td class="smsvs_box_img">
												<span class="icbox">
													<c:choose>
														<c:when test="${sendItem.messageType eq 'Mail'}">
															<img src="<c:url value='/base/images/icon/ic_mail_b.png'/>" alt="mail" />
														</c:when>
														<c:when test="${sendItem.messageType eq 'Message'}">
															<img src="<c:url value='/base/images/icon/ic_note_b.png'/>" alt="message" />
														</c:when>
														<c:when test="${sendItem.messageType eq 'SMS'}">
															<img src="<c:url value='/base/images/icon/ic_sns_b.png'/>" alt="message" />
														</c:when>
													</c:choose>
												</span>
											</td>
											<td class="smsvs_box_con" style="word-wrap: break-word;">
											<div class="smsvs_box_con_t" style="width:265px;">
												<div class="smsvs_box_con_tl" style="word-wrap: break-word;">
													
													<a href="#a" onclick="getMessageInfo(this,'${sendItem.messageType}','${sendItem.messageId}', '${sendItem.receiverId}', '${sendItem.profilePicturePath}');return false;"><span>${sendItem.contents}</span></a>
												</div>
												<div class="smsvs_box_con_tr">
													<c:if test="${sendItem.attachCount > 0}">
													<img src="<c:url value='/base/images/icon/ic_attach.gif'/>" alt="files" /> (${sendItem.attachCount})
													</c:if>
												</div>
											</div>
											<div class="smsvs_box_con_info">
												<c:choose>
													<c:when test="${sendItem.receiverName eq null }">
														<div class="smsvs_box_con_name"><a href="#a">${sendItem.receiverMail}</a></div>
													</c:when>
													<c:otherwise>
														<div class="smsvs_box_con_team">
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">
																${sendItem.receiverTeamName}
															</c:when>
															<c:otherwise>
																${sendItem.receiverTeamEngName}
															</c:otherwise>
													    </c:choose>
														&nbsp; </div>
														<div class="smsvs_box_con_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${sendItem.receiverId}', 'bottom');return false;">
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">
																${sendItem.receiverName}  ${sendItem.receiverJobTitleName}
															</c:when>
															<c:otherwise>
																${sendItem.receiverEngName}  ${sendItem.receiverJobTitleEngName}
															</c:otherwise>
													    </c:choose>
														<c:if test="${sendItem.receiverCount > 1}"> <ikep4j:message pre='${preDetail}' key='overPer'/>  ${sendItem.receiverCount -1}<ikep4j:message pre='${preDetail}' key='person'/></c:if></a></div>
													</c:otherwise>
												</c:choose>
												<div class="smsvs_box_con_date">
													<c:choose>
														<c:when test="${sendItem.isToday eq 1 }">
															<ikep4j:timezone date="${sendItem.sendDate}" pattern="HH:mm"/> 
														</c:when>
														<c:otherwise>
															<ikep4j:timezone date="${sendItem.sendDate}" pattern="yyyy.MM.dd"/> 
														</c:otherwise>
													</c:choose>
												</div>
											</div>			
											</td>										
										</tr>
										</c:forEach>				      
								    </c:otherwise> 
								</c:choose> 
							</tbody>
						</table>
									
					</div>
					<!--blockButton_3 Start-->
						<div id="bt_div" class="blockButton_3" > 
							<a id="moreList_bt" class="button_3" href="#a"><span><ikep4j:message pre='${preButton}' key='moreList10'/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>	
							<div class="ic_top"><a href="#"><span>top</span></a></div>			
						</div>
						<!--//blockButton_3 End-->	
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>	
					
				</div>
				<!--//corner_RoundBox07 End-->	
				</form>
										
			</div>
			<!--//smsvs_blockLeft End-->
			
			<!--smsvs_blockRight Start-->
			<div class="smsvs_blockRight">
			
				<!--corner_RoundBox07 Start-->
				<div id="rightBox" class="corner_RoundBox07 none">

					<!--blockMsgbox Start-->
					<div id="messageView" > 
					</div>
					<!--//blockMsgbox End-->

					<!--contactBox Start-->
					<div class="contactBox">
						<div class="contactBox_con">
							<input type="hidden" id="h_guestId" />
							<h3>Contact History</h3>
							<div class="contactBox_img">
								<div class="imgFrame_l">
									<div id="guestImg"></div>
									<div class="frame"><img src="<c:url value='/base/images/common/photoframe.png' />" alt="" /></div>
								</div>
								<div class="imgFrame_r">
									<img src="<c:url value='${user.profilePicturePath}' />" alt="userImg" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/>
									<div class="frame"><img src="<c:url value='/base/images/common/photoframe.png' />" alt=""  /></div>
								</div>
								<div class="contactBox_img_info">Last Contacted</div>
								<div id="lastContactDate" class="contactBox_img_info_2"></div>
							</div>
							
							<div id="moreHistory">
								<ul id="contact_div"><li>&nbsp;</li>
								</ul>
							</div>
							
							<!--blockButton_5 Start-->
							<div class="blockButton_5"> 
								<a id="afterHistory_bt" class="button_prev" href="#a"><span><img src="<c:url value='/base/images/icon/ic_more_ar_2.gif' />" alt="" /> Prev 5</span></a>	
								<input type="hidden" id="h_lastDate" value="${lastDate}"/>	
								<a id="beforeHistory_bt" class="button_next" href="#a"><span>Next 5 <img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>	
								<input type="hidden" id="h_firstDate" value="${firstDate}"/>			
							</div>
							<!--//blockButton_5 End-->	
							<div class="blockBlank_5px"></div>	
																			
						</div>						
					</div>
					<!--//contactBox End-->	
					
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>	
				</div>
				<!--//corner_RoundBox07 End-->
			</div>				
			<!--//smsvs_blockRight End-->
			
		</div>

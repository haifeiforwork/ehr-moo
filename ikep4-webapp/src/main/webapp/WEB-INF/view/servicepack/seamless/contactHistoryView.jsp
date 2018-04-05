<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.seamless.contacHistoryView.header" /> 
<c:set var="preSearch"  value="ui.servicepack.seamless.searchCondition" /> 
<c:set var="preButton"  value="ui.servicepack.seamless.button" /> 
<c:set var="preMsg"  value="ui.servicepack.seamless.MSG" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 <%-- 메시지 관련 Prefix 선언 End --%>
<script src="http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>
<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<script type="text/javascript">
<!--
//history List 담기
var objHistory = new Object();
	//onload시 수행할 코드
	$jq(document).ready(function() {
		
		<c:choose>
			<c:when test="${searchCondition.boxType eq 'S'}">
				$jq("#viewSent").attr("class", "button_s selected");
			</c:when>
			<c:when test="${searchCondition.boxType eq 'R'}">
				$jq("#viewReceiv").attr("class", "button_s selected");
			</c:when>
			<c:otherwise>
				$jq("#viewAll").attr("class", "button_s selected");
			</c:otherwise>
		</c:choose>
		
		$jq("#viewAll").click(function(){
            $jq("input[name=boxType]").val("");
  			$jq('#searchForm').submit();
        });
		$jq("#viewSent").click(function(){
        	$jq("input[name=boxType]").val("S");
			$jq('#searchForm').submit();
    	});
		$jq("#viewReceiv").click(function(){
        	$jq("input[name=boxType]").val("R");
			$jq('#searchForm').submit();
    	});
		<c:if test="${fn:length(contacUserList) < 10 }"> $jq("#bt_divL").hide(); </c:if>
		$jq("#endRnum").val("10");
		$jq("#moreList_bt").click(function(){
			url = "<c:url value='/servicepack/seamless/contactList.do'/>";
			boxType = $jq("input[name=boxType]").val();
			endRnum = $jq("#endRnum").val();
			$jq.ajax({
			 	type: 'post',
			 	url: url,
			 	data: {'boxType':boxType, 'endRnum':endRnum}
			 })
			 .success(function(data) {
				 $jq("#moreListTmpl").tmpl(data).appendTo("tbody","#contactTable");
				 if ( data.length < 10) $jq("#moreList_bt").hide(); 
				 if ( data.length > 0) $jq("#endRnum").val(parseInt(endRnum) + 10);
		    })    
			.error(function(event, request, settings) { alert("error"); });  
		});
		
		$jq("#beforeHistory_bt").click(function () { 
			url = "<c:url value='/servicepack/seamless/moreHistoryList.do'/>";
			guestId = $jq("#h_guestId").val();
			searchColumn = "before";
			lastDate = $jq("#h_lastDate").val();
			if (guestId != "") {
				$jq.ajax({
				 	type: 'post',
				 	url: url,
				 	data: {'guestId':guestId,'searchColumn':searchColumn, 'lastDate':lastDate}
				 })
				 .success(function(data) {
					 objHistory.data = $jq.merge( $jq.merge([],objHistory.data), data); 
					 $jq("#contact_div").empty();
					 beforDate = "";
					 $jq(objHistory.data).each(function(index){
						 $jq("#morehistorytmpl").tmpl(this, beforDate).appendTo("#moreHistory ul","#contact_div");
						 beforDate = this.stringDate;
					  });
					 if ( data.length < 5) {
						 $jq("#beforeHistory_bt").hide();
						 $jq("#h_guestId").val(""); 
					 }
					 if ( data.length > 0) $jq("#h_lastDate").val($jq(data).get(data.length-1).sendDateOrg);
			    })    
				.error(function(event, request, settings) { alert("error"); });  
			}
		});
		<c:if test="${setUser != null && setUser != ''}">
			getHistoryInfo(null,"${setUser}","");
		</c:if>
	});

	//contactHistory읽기
	var oldObj;
	getHistoryInfo = function(obj,guestId, profilePicturePath) {
		//if (oldObj != null) $jq(oldObj).removeClass("selected");
		if (obj != null) { $jq("#contactTable tr").removeClass("selected");}
		$jq($jq($jq($jq(obj).parent("span")).parent("div")).parent("td")).parent("tr").addClass("selected");
		//oldObj = $jq($jq($jq($jq(obj).parent("span")).parent("div")).parent("td")).parent("tr");
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
		
		url = "<c:url value='/servicepack/seamless/moreHistoryList.do'/>";
		$jq.ajax({
		 	type: 'post',
		 	url: url,
		 	data: {'guestId':guestId }
		 })
		 .success(function(data) {
			 $jq("#bt_divR").show();
			 $jq("#beforeHistory_bt").show();
			 $jq("#contact_div").empty();
			 $jq("#h_guestId").val(guestId);
			 objHistory.data = data;
			 beforDate = "";
			 $jq(objHistory.data).each(function(index){
				 $jq("#morehistorytmpl").tmpl(this, beforDate).appendTo("#contact_div");
				 beforDate = this.stringDate;
			  });
			 if ( data.length < 5) {
				 $jq("#bt_divR").hide();
				 $jq("#h_guestId").val(""); 
			 };
			 if ( data.length > 0) $jq("#h_lastDate").val($jq(data).get(data.length-1).sendDateOrg);
	    })    
		.error(function(event, request, settings) { alert("error"); });  

		window.scrollTo(0,0); 
	}
	
	sendMessage = function(guestId) {
		var url = "<c:url value='/support/message/messageNew.do'/>"+"?senderUserId="+guestId;
		iKEP.popupOpen(url , {width:800, height:800}, "MessagePopup");
	}
	sendSMS = function(guestId) {
		var url = "<c:url value='/support/sms/sms.do'/>"+"?gubun=1&receiverId="+guestId;
		iKEP.popupOpen(url , {width:568, height:432, scrollbar:false}, "smsPopup");
	}
	sendMail = function(guestId) {
		var url = "<c:url value='/support/mail/sendMailForm.do'/>"+"?receiverId="+guestId;
		iKEP.popupOpen(url , {width:800, height:750}, "MailPopup");
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
	contFavorite = function(obj,userId,userName) {
		if($jq(obj).hasClass("select")) {
			iKEP.delFavorite('',userId, favoriteAfter('del'));
			$jq(obj).removeClass("select");
		} else {
			iKEP.addFavorite('PEOPLE','${IKepConstant.ITEM_TYPE_CODE_PROFILE}',userId,userName,favoriteAfter('add'));
			$jq(obj).addClass("select");
		}
	}
	favoriteAfter = function(sts) {
		if (sts == 'add') alert("<ikep4j:message pre='${preMsg}' key='register'/>");
		if (sts == 'del') alert("<ikep4j:message pre='${preMsg}' key='delete'/>");
	}
	contFollow = function(obj,userId) {
		if($jq(obj).hasClass("button_pr")) {
			url = "<c:url value='/socialpack/microblogging/follow/createFollow.do'/>";
			sts = "create";
		} else {
			url = "<c:url value='/socialpack/microblogging/follow/removeFollow.do'/>";
			sts = "remove";
		}		
		$jq.post(url, 
				{'followingUserId':userId}, 
				function(data) {
					if (sts == "create") {
						$jq(obj).parent("div").attr("class","btn_following");
						$jq(obj).attr("class","button_follow");
						$jq(obj).find("span").html("Follow");
					} else {
						$jq(obj).parent("div").attr("class","btn_follow");
						$jq(obj).attr("class","button_pr");
						$jq(obj).find("span").html("<img src=\"<c:url value='/base/images/icon/ic_plus.gif'/>\" />Follow");
					}
		});
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
<tr>
	<td>
		<div class="people_box_photo">
			<div class="people_box_resize">
				<div class="ic_rt_favorite"><a href="#a" class="select"></a></div>
			</div>						
			<span><a href="#a" onclick="iKEP.showUserContextMenu(this, '\${userId}', 'bottom');return false;"><img src="<c:url value='\${profilePicturePath}' />" alt="userImg" style="width:50px; height:50px;" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></span>
		</div>					
		<div class="people_box_info width_msg" >
			<div class="people_box_btn">
					
			{{if (followYn == 1) }}
				<div class="btn_following">
					<a class="button_follow" onclick="contFollow(this,'\${userId}');" href="#a"><span>Following</span></a>
				</div>
			{{else}}
				<div class="btn_follow">
					<a class="button_pr" onclick="contFollow(this,'\${userId}');" href="#a"><span><img src="<c:url value='/base/images/icon/ic_plus.gif'/>" alt="Following" />Following</span></a>
				</div>
			{{/if}}	

			</div>							
			<span class="people_box_name"><a onclick="getHistoryInfo(this,'\${userId}','\${profilePicturePath}');" href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">\${userName} \${jobTitleName}</c:when><c:otherwise>\${userEnglishName} \${jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="people_box_team"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">\${teamName}</c:when><c:otherwise>\${teamEnglishName}</c:otherwise></c:choose></a>	
			{{if (favoriteYn == 1) }}
 				<a class="ic_rt_favorite valign_middle select" onclick="contFavorite(this,'\${userId}','\${userName}');" href="#a"><span><ikep4j:message pre='${preButton}' key='favor'/></span></a>	
			{{else}}
				<a class="ic_rt_favorite valign_middle" onclick="contFavorite(this,'\${userId}','\${userName}');" href="#a"><span><ikep4j:message pre='${preButton}' key='favor'/></span></a>	
			{{/if}}
			<a onclick="sendMessage('\${userId}');" href="#a" class="ic_rt_note valign_middle"><span><ikep4j:message pre='${preButton}' key='msgSend'/></span></a></span>
			<div class="people_box_con">
				<span class="phone"><span><ikep4j:message pre='${preButton}' key='tel'/> :</span>\${officePhoneNo}</span> &nbsp;&nbsp;
				<a onclick="sendSMS('\${userId}');" href="#a"><span class="cellular"><span><ikep4j:message pre='${preButton}' key='mobile'/> :</span>\${mobile}</span>&nbsp;&nbsp;</a>							
			</div>
			<div class="people_box_mail">
				<span><ikep4j:message pre='${preButton}' key='email'/> :</span><a onclick="sendMail('\${userId}');" href="#a" title="<ikep4j:message pre='${preButton}' key='email'/>"> \${mail}</a>
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

		<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
		
		<div class="smsvs_blockAll">
			<!--smsvs_blockLeft Start-->
			<div class="smsvs_blockLeft">
				
				<!--corner_RoundBox07 Start-->
				<div class="corner_RoundBox07">	
					<div class="search_box">
						<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
						<div class="tableSearch">&nbsp;</div>
						<div class="clear"></div>
					</div>
					
					<div class="smsvsTable contact">
						<div class="smsvsTable_view">
							<form id="searchForm" method="post" action="<c:url value='/servicepack/seamless/contactHistoryView.do' />">  
							<h3 class="none">view button</h3>
							<a id="viewAll" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='all'/></span></a>
							<a id="viewReceiv" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='received'/></span></a>
							<a id="viewSent" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='sent'/></span></a>
							<spring:bind path="searchCondition.boxType">  					
								<input name="${status.expression}" value="${status.value}" type="hidden" />
							</spring:bind>
							<input type="hidden" id="endRnum"/>
							</form>
						</div>
						<table summary="<ikep4j:message pre="${preHeader}" key="pageTitle" />">
							<caption></caption>
							<tbody id="contactTable">
								<c:choose>
							    <c:when test="${contacUserList eq '[]' }">
									<tr>
										<td class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="contacUser" items="${contacUserList}" varStatus = "status"> 
									<tr <c:if test="${contacUser.userId eq setUser}"> class="selected"</c:if> >
										<td>
											<div class="people_box_photo" >
												<div class="people_box_resize">
													<div class="ic_rt_favorite"><a href="#a" class="select"></a></div>
												</div>						
												<span><a href="#a" onclick="iKEP.showUserContextMenu(this, '${contacUser.userId}', 'bottom');return false;"><img src="<c:url value='${contacUser.profilePicturePath}' />" alt="userImg" style="width:50px; height:50px;" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></span>
											</div>					
											<div class="people_box_info width_msg" >
												<div class="people_box_btn">
													<c:choose>
								   						<c:when test="${contacUser.followYn eq 1 }">
															<div class="btn_following">
																<a class="button_follow" onclick="contFollow(this,'${contacUser.userId}');" href="#a"><span>Following</span></a>
															</div>
														</c:when>
														<c:otherwise>
															<div class="btn_follow">
																<a class="button_pr" onclick="contFollow(this,'${contacUser.userId}');" href="#a"><span><img src="<c:url value='/base/images/icon/ic_plus.gif'/>" alt="Following" />Following</span></a>
															</div>
														</c:otherwise>	
													</c:choose>	
												</div>							
												<span class="people_box_name"><a onclick="getHistoryInfo(this,'${contacUser.userId}','${contacUser.profilePicturePath}');" href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${contacUser.userName} ${contacUser.jobTitleName}</c:when><c:otherwise>${contacUser.userEnglishName} ${contacUser.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
												<span class="people_box_team"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${contacUser.teamName}</c:when><c:otherwise>${contacUser.teamEnglishName}</c:otherwise></c:choose></a>	
												<c:choose>
							   						<c:when test="${contacUser.favoriteYn eq 1 }">
														<a class="ic_rt_favorite valign_middle select" onclick="contFavorite(this,'${contacUser.userId}','${contacUser.userName}');" href="#a"><span><ikep4j:message pre='${preButton}' key='favor'/></span></a>	
													</c:when>
													<c:otherwise>
														<a class="ic_rt_favorite valign_middle" onclick="contFavorite(this,'${contacUser.userId}','${contacUser.userName}');" href="#a"><span><ikep4j:message pre='${preButton}' key='favor'/></span></a>	
													</c:otherwise>	
												</c:choose>	
												<a onclick="sendMessage('${contacUser.userId}');" href="#a"  class="ic_rt_note valign_middle"><span><ikep4j:message pre='${preButton}' key='msgSend'/></span></a></span>
												<div class="people_box_con">
													<span class="phone"><span><ikep4j:message pre='${preButton}' key='tel'/> :</span>${contacUser.officePhoneNo}</span> &nbsp;&nbsp;
													<a onclick="sendSMS('${contacUser.userId}');" href="#a"><span class="cellular"><span><ikep4j:message pre='${preButton}' key='mobile'/> :</span>${contacUser.mobile}</span>&nbsp;&nbsp;</a>							
												</div>
												<div class="people_box_mail">
													<span><ikep4j:message pre='${preButton}' key='email'/> :</span><span class="email"></span><a onclick="sendMail('${contacUser.userId}');" href="#a" title="<ikep4j:message pre='${preButton}' key='email'/>"> ${contacUser.mail}</a>
												</div>	
											</div>	
											<div class="clear"></div>
										</td>
									</tr>
									</c:forEach>	
								</c:otherwise>
								</c:choose>
							</tbody>
						</table>
										
					</div>
					<!--blockButton_3 Start-->
					<div id="bt_divL" class="blockButton_3" > 
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
										
			</div>
			<!--//smsvs_blockLeft End-->
			
			<!--smsvs_blockRight Start-->
			<div class="smsvs_blockRight">
			
				<!--corner_RoundBox07 Start-->
				<div class="corner_RoundBox07">	

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
									<img src="<c:url value='${user.profilePicturePath}' />" alt="userImg" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
									<div class="frame"><img src="<c:url value='/base/images/common/photoframe.png' />" alt="" /></div>
								</div>
								<div class="contactBox_img_info">Last Contacted</div>
								<div id="lastContactDate" class="contactBox_img_info_2"></div>
							</div>
							
							<div id="moreHistory">
								<ul id="contact_div"><li>&nbsp;</li>
								</ul>
							</div>
							
							<!--blockButton_3 Start-->
							<div id="bt_divR" class="blockButton_3" style="padding:0px 10px 0px 10px;"> 
								<a id="beforeHistory_bt" class="button_3" href="#a"><span>Next 5 <img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>	
								<input type="hidden" id="h_lastDate" value="${lastDate}"/>	
								<div class="ic_top"><a href="#"><span>top</span></a></div>			
							</div>
							<!--//blockButton_3 End-->	
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

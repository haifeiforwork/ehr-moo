<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  value="ui.support.message.button" /> 
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="preDetail"  value="ui.support.message.messageNew.detail" />
<c:set var="preSearchColumn"  value="ui.support.message.searchCondition.searchColumn" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
var sWidth = (screen.availWidth/2);
var sHeight = (screen.availHeight*2/3);
if (sWidth < 500) sWidth= 500;
if (sWidth > 800) sWidth= 800;
if (sHeight < 600) sHeight= 600;
if (sHeight > 800) sHeight= 800;
window.resizeTo(sWidth,sHeight);

var $sel; //select box

<!--
(function($) {
	
	selectedAll = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
	
    $jq(document).ready(function() {
    	<c:if test="${replyUser.userStatus == 'T'}">
			alert("사용자 정보가 존재하지 않습니다.");
			window.close();
		</c:if>
    	//validation
		 var validOptions = {
	    		rules : {
	    			addrGroupList : {
	    				required : true
	    			},
	    			contents : {
	    				required : true
	    			}
	    		},
	    		messages : {
	    			addrGroupList : {
	    				required : "<ikep4j:message pre='${preMsg}' key='valueReceive'/>"
	    			},
	    			contents : {
	    				required : "<ikep4j:message pre='${preMsg}' key='valueContents'/>"
	    			}
	    		},
	    		notice : {
	    			addrGroupList : "<ikep4j:message pre='${preMsg}' key='valueReceive'/>",
	    			contents : "<ikep4j:message pre='${preMsg}' key='valueContents'/>"
	    		},
			    submitHandler : function(form) {
			    	
			    	if(confirm("<ikep4j:message pre='${preMsg}' key='sendConfirm'/>")) {
								$jq("#messageForm")[0].submit();
								$("#popup").ajaxLoadStart();
			    	}
			    }
	    	};
		//$jq("#messageForm").validate(validOptions);
		new iKEP.Validator("#messageForm", validOptions);
		
		$jq("#messageSendButton").click(function() {   
			
    		var list = [];
    		
    		selectedAll("addrGroupList");
    		
    		$jq.each($jq("#addrGroupList > option"),function(index){
    			list[index] = $jq(this).val();
    		});
    		$jq("#receiverList").val(list);
    		
    		$jq("#messageForm").submit();	

    	});  
		
    	if("${returnMsg}"=="SAVE") {
    		alert("<ikep4j:message pre='${preMsg}' key='sendMessage'/>");
    		window.close();
    	}

    	$sel = $jq("#AddrControlForm").find("[name=addrGroupList]").children().remove().end();
    	
    	if("${replyUser.userId}" != "")  {
			var $option = $.tmpl('<option id="${replyUser.userId}" value="${replyUser.userId}/${replyUser.userName}/user" > ${replyUser.userName} ${replyUser.jobTitleName} ${replyUser.teamName}</option>').appendTo($sel);
			$jq("#selCount").text($sel.children().length);
			$jq.data($option[0], "data",{id:'${replyUser.userId}',userName:'${replyUser.userName}',jobTitleName:'${replyUser.jobTitleName}',teamName:'${replyUser.teamName}',type:'user'});
    	}
    	
    	$jq("#btnClose").click(function() {
    		window.close();
		});
    	
    	

    	$jq('#btnAddrControl').click( function() {
    		var maxCnt = '${userMessageAdmin.maxReceiverCount}' - $sel.children().length;

			var items = [];
			
			$sel.children().each(function(){
				if(!this.value){
					$jq(this).remove();
				} else {
					items.push($jq.data(this, "data"));
					
				}
	       });
			
    		iKEP.showAddressBook(setAddress, items, {selectType:"all", selectMaxCnt:maxCnt, isAppend:false, tabs:{common:1}});
		});
    	
    	setAddress = function(data) {
    		$sel.empty();
			$jq.each(data, function() {
				this.type = jQuery.trim(this.type);
				this.id = jQuery.trim(this.id);
				this.code = jQuery.trim(this.code);
				this.name = jQuery.trim(this.name);

				var $option;
				
				if (this.type=="user") {
					var oldVal = $sel.find("#"+this.id);
					if( oldVal.text() == '') $option = $.tmpl(iKEP.template.userMessageOption, this).appendTo($sel);
				} else {
					var oldVal = $sel.find("#"+this.code);
					if( oldVal.text() == '') $option = $.tmpl(iKEP.template.groupMessageOption, this).appendTo($sel);
				}

				 $jq.data($option[0], "data", this);
				 
			})
			$jq("#selCount").text($sel.children().length);
			$sel.find("option").attr("selected",true);
			$sel.click();
    	};
    	
		setUser = function(data) {
			if(data == "") alert("<ikep4j:message pre='${preMsg}' key='noData'/>");
			
			$jq.each(data, function() {				
				var oldVal = $sel.find("#"+jQuery.trim(this.id));

				if( oldVal.text() == '') {
					var $option = $.tmpl(iKEP.template.userMessageOption, this).appendTo($sel); 
					$jq.data($option[0], "data", this);
				}

			});
			
			$jq("#selCount").text($sel.children().length);
			$jq("#searchUser").val("");
			$sel.find("option").attr("selected",true);
			$sel.click();
		};

    	$jq("#delMember").click(function() {
    		
    		$jq.each($sel.children("option:selected"), function() {
    			$jq(this).remove();
    		})
    		$jq("#selCount").text($sel.children().length);
		});
    	
    	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#searchUser').keypress( function(event) {
		    //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "Y", setUser);
		});
		
		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#searchUserBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchUser').trigger("keypress");
		});
		
     });

})(jQuery);  
//-->

</script>
<c:if test="${replyUser.userStatus != 'T'}">
<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title">
		<h1>Message</h1>
		<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close'/></span></a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
	
	<form id="messageForm" method="post" action="<c:url value='/support/message/messageSendNew.do' />"> 		


		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="Message">
				<caption></caption>
				<colgroup>
					<col width="18%" />
					<col width="82%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><ikep4j:message pre='${preDetail}' key='searchImg'/></th>
						<td>
							<input id="searchUser" name="searchUser" type="text" class="inputbox" title="inputbox"  value='' size="20" />
							<input type="text" style="display: none;" /> 
							<a id="searchUserBtn" class="ic_search valign_middle" href="#a"><span><ikep4j:message pre='${preDetail}' key='searchImg'/></span></a>
						</td>
					</tr>				
					<tr>
						<th scope="row"><ikep4j:message pre='${preSearchColumn}' key='receiverName'/></th>
						<td>
							<div id="AddrControlForm">
								<select name="addrGroupList" id="addrGroupList" multiple="multiple" size="5"  class="multi w60"><option value=""></option></select>
								<a id="delMember" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" />Delete</span></a>
								<span class="totalNum_s"> Total [<span id="selCount">0</span>] </span>
								<spring:bind path="message.receiverList">  
									<input type="hidden" id="receiverList" name="${status.expression}"/>
									<label for="${status.expression}" class="serverError">
										<c:forEach var="erro" items="${status.errorMessage}"> 
										${erro}
										</c:forEach>
									</label>
								</spring:bind>			
							</div>
						</td>
					</tr>	
					<tr>
						<td colspan="2">
							<div>
							<spring:bind path="message.contents">  
								<textarea id="${status.expression}"  name="${status.expression}" class="tabletext w100" title="<ikep4j:message pre='${preDetail}' key='inputText'/>" cols="" rows="8"><c:if test="${message != null}">${message.contents}</c:if></textarea>
								<label for="${status.expression}" class="serverError">
									<c:forEach var="erro" items="${status.errorMessage}"> 
									${erro}
									</c:forEach>
								</label>
							</spring:bind>
							</div>
						</td>
					</tr>
				</tbody>
			</table>			
		</div>
		<!--//blockDetail End-->																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul> 
				<li><a id="messageSendButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='send'/></span></a></li>
				<li><a id="btnClose" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
			</ul>
		</div>
	</form>
	</div>
	<!--//popup_contents End-->
	
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->
			
</div>
</c:if>
<!--//popup End-->
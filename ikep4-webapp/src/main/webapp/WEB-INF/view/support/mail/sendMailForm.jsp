<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.mail.header" /> 
<c:set var="preList"    value="ui.support.mail.list" />
<c:set var="preDetail"  value="ui.support.mail.detail" />
<c:set var="preButton"  value="ui.support.mail.button" /> 
<c:set var="preMessage" value="ui.support.mail.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="/ikep4-webapp/base/js/MailAddressBook.js"></script>


<script type="text/javascript">

<!--  

(function($) {
	
	setAddress = function(data) {
		
		var $optValue;	
		var $optName;
		var $selectCheck;
		var $sel = $jq("select[name=emailList]");
		var $addrType = $jq("input[name=addrType]:checked").val();
		
		$jq(data).each(function(index) {
			
			if(data[index].type == 'user')
			{
			    $optValue = $addrType + ":"+ data[index].type + ":"+ $jq.trim(data[index].name) + ":" + data[index].email;
			    $optName = $addrType + ": " + $jq.trim(data[index].name) + " " + data[index].jobTitle + " ("+ data[index].email + ")";
			}
			else
		    {
				$optValue = $addrType + ":"+ data[index].type + ":"+ data[index].code + ":"+ $jq.trim(data[index].name);
                $optName = $addrType + ": " + $jq.trim(data[index].name);				
		    }
			$selectCheck = true;
			$jq.each($sel.children(), function() {
				if(this.value.substr(this.value.lastIndexOf(':')) == $optValue.substr($optValue.lastIndexOf(':'))) {
					$selectCheck = false;
				}
			});
			
			if($selectCheck) {
				$('<option/>').appendTo($sel).attr('value', $optValue).html($optName);
				$jq("input[name=userName]").val("");
			}
		});

	};
	
	setUser = function(data) {

		var $optValue;	
		var $optName;
		var $selectCheck;
		var $sel = $jq("select[name=emailList]");
		var $addrType = $jq("input[name=addrType]:checked").val();
		
		$jq(data).each(function(index) {
			
			$optValue = $addrType + ":"+data[index].userName + ":" + data[index].mail;
			$optName = $addrType + ": " + data[index].userName + " " + data[index].jobTitleName + " ("+ data[index].mail + ")";
			
			$selectCheck = true;
			$jq.each($sel.children(), function() {
				if(this.value.substr(this.value.lastIndexOf(':')) == $optValue.substr($optValue.lastIndexOf(':'))) {
					$selectCheck = false;
				}
			});
			
			if($selectCheck) {
				$('<option/>').appendTo($sel).attr('value', $optValue).html($optName);
				$jq("input[name=userName]").val("");
			}
		});
	};
	
	setEmail = function(email) {
		
		var $optValue;	
		var $optName;
		var $selectCheck;
		var $sel = $jq("select[name=emailList]");
		var $addrType = $jq("input[name=addrType]:checked").val();
		
		$optValue = $addrType + ":" + email + ":" + email;
		$optName = $addrType + ": " + email + " ("+ email + ")";
		
		$selectCheck = true;
		$jq.each($sel.children(), function() {
			if(this.value.substr(this.value.lastIndexOf(':')) == $optValue.substr($optValue.lastIndexOf(':'))) {
				$selectCheck = false;
			}
		});
		
		if($selectCheck) {
			$('<option/>').appendTo($sel).attr('value', $optValue).html($optName);
			$jq("input[name=userName]").val("");
		}
	};
	
	
	deleteList = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");

		if($sel) {
			$jq.each($sel.children(), function() {
				if(this.selected) {
					$(this).remove();
				}
			});
		}
	};
	
	updateList = function (selectName){
		
		var $optValue;	
		var $optName;
		var $sel = $jq("select[name="+selectName+"]");
		var $addrType = $jq("input[name=addrType]:checked").val();
		
		if($sel) {
			$jq.each($sel.children(), function() {
				if(this.selected) {
					$optValue = $addrType + $(this).val().substr($(this).val().indexOf(':'));
					$optName = $addrType + $(this).text().substr($(this).text().indexOf(':'));
					$(this).replaceWith("<option value='"+$optValue+"'>"+$optName+"</option>");
				}
			});
		}
	};
	
	selectedAll = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
	
	checkEmail = function (email) {
		
		var pattern = /^(\S+)\@(\S+)\.(\S+)/;		
		return pattern.test(email);
	};
	
	var validOptions = {
	    rules : {
	    	title :    {
	            required : true,
	            maxlength : 500
	        },
	        content :    {
	            required : true
	        }
	    },
	    messages : {
	    	title : {
	            direction : "bottom",
	            required : "<ikep4j:message key="NotEmpty.mail.title" />",
	            maxlength : "<ikep4j:message key="Size.mail.title" />"
	        },
	        content : {
	            direction : "bottom",
	            required : "<ikep4j:message key="NotEmpty.mail.content" />"
	        }
	    },
	    submitHandler : function(form) {
	    	
	    	fileController.upload(function(isSuccess, files) {
				if(isSuccess == true) { 
					selectedAll("emailList");
					$jq("#mailForm")[0].submit();
					$("#popup").ajaxLoadStart();
				}
			});
	    }
	 };
	
	// onload시 수행할 코드
	var fileController;
	
	$jq(document).ready(function() { 
		
		$("#emailList").children().remove();
		
		new iKEP.Validator("#mailForm", validOptions);
		
		fileController = new iKEP.FileController("#mailForm", "#fileUploadArea", {
			lang :  "en"
		});	
		
		$jq("#submitBtn").click(function() {
			
			var $sel = $jq("select[name=emailList]");
			if($sel.children().length <1) {
				alert('<ikep4j:message pre='${preMessage}' key='toEmail.empty' />');
				return;	
			}
			
			$jq("#mailForm").submit();			
		});
		
		$jq('#addressBtn').click( function() { 
			iKEPMail.showAddressMailBook(setAddress, "", "");
		});
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
		
		$jq('#userName').keypress( function(event) { 
			
			var check = false;
			if(event.which == 13) {
				var $email = $jq("input[name=userName]").val();
			
				if(checkEmail($email)) {
					setEmail($email);
					check= true;
				}
			}
			
			if(!check)
				iKEP.searchUser(event, "Y", setUser); 
		});
		
		$jq('#userSearchBtn').click( function() { 
			$jq('#userName').trigger("keypress");
		});
		
		$jq('#deleteBtn').click(function(event) { 
			deleteList("emailList");
		});
		
		$jq('#updateBtn').click(function(event) { 
			updateList("emailList");
		});
		
		
		var param = iKEP.getPopupArgument();

		if(param != undefined) {
			
			//이메일 리스트가 있을경우 셋팅
			var $sel = $jq("select[name=emailList]");
			$jq(param.emailList).each(function(index) {
				
				$optValue = "TO" + ":" + $jq(param.nameList).get(index) + ":" + this;
				$optName = "TO" + ": " + $jq(param.nameList).get(index) + " ("+ this + ")";
				
				$('<option/>').appendTo($sel).attr('value', $optValue).html($optName);
			});
			
			//타이틀이 있을  경우 셋팅
			if(param.title!="" && param.title!= undefined) {
				$jq("#title").val($jq.trim(param.title));
			}
			
			//원본 첨부 내용이 있을 경우 셋팅
			if(param.content!="" && param.content!= undefined) {
				var header = "<br/>";
				header = header + ' (<ikep4j:message pre='${preDetail}' key='original' />) ';
				header = header + "<br/><br/>";
				
				var footer = "<br/><br/>";
			
				$jq("#tbodyDiv").append("<tr><td colspan='2'>" + header + param.content + footer+"</td></tr>");
				$jq("#contentOriginal").val(header + param.content + footer);
			}
			
			//원본 첨부의 파일이 있을 경우 셋팅
			var fileList="";
			$.template("tmp_fileDataList", '<input type="hidden" name="fileDataList[\${index}].fileId" value="\${fileId}"/>');
			$jq(param.fileIdList).each(function(index) {
				
				var fileInfo = {index:index, fileId:this};
				fileList = fileList + $jq(param.fileNameList).get(index) + " ";
				$jq('#mailForm').append($.tmpl("tmp_fileDataList", fileInfo));
			});
			
			if($jq(param.fileIdList).size() > 0) {
				$jq("#tbodyDiv").append("<tr><td colspan='2'>" + fileList + "</td></tr>");
			}
			
		}
		
	});
	
})(jQuery);  

//-->
	
</script>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title">
					<h1><ikep4j:message pre='${preDetail}' key='main.send' /></h1>
					<a href="#a" onclick="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
											
					
						<form id="mailForm" method="post" action="<c:url value="/support/mail/sendMail.do"/>" >
							
							<spring:bind path="mail.contentOriginal">
								<input type="hidden" name="${status.expression}" id="${status.expression}" value=""/>
							</spring:bind>
							
							<table summary="<ikep4j:message pre='${preDetail}' key='main.send' />">
							<caption></caption>	
					
							<tbody id="tbodyDiv">
							
								<tr> 
									<th scope="row" width="15%"><ikep4j:message pre='${preDetail}' key='addrType' /></th>
									<td>
										
										<input 
										name="addrType" 
										type="radio" 
										class="radio" 
										title="<ikep4j:message pre='${preDetail}' key='toEmail' />" 
										value="TO" 
										checked="checked"
										/> <ikep4j:message pre='${preDetail}' key='toEmail' />
										
										<input 
										name="addrType" 
										type="radio" 
										class="radio" 
										title="<ikep4j:message pre='${preDetail}' key='ccEmail' />" 
										value="CC" 
										/> <ikep4j:message pre='${preDetail}' key='ccEmail' />
										
										<input 
										name="addrType" 
										type="radio" 
										class="radio" 
										title="<ikep4j:message pre='${preDetail}' key='bccEmail' />" 
										value="BCC" 
										/> <ikep4j:message pre='${preDetail}' key='bccEmail' />
										
									</td> 
								</tr>
							
								<tr> 
									<th scope="row"><ikep4j:message pre='${preDetail}' key='emailSearch' /></th>
									<td>
										
										<input type="text" name="userName" id="userName" value="" size="50"/>  
										<a class="button_s valign_bottom" href="#a" ><span id="userSearchBtn"><ikep4j:message pre='ui.support.popup.button' key='search' /></span></a>
	
										<a class="button_s valign_bottom" href="#a"><span id="addressBtn"><ikep4j:message pre='${preDetail}' key='addrbook' /></span></a>
									
									</td> 
								</tr>				
								
								<spring:bind path="mail.emailList">
								<tr> 
									<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
									<td>
									
										<div style="padding-top:4px;">
											<select name="${status.expression}" id="${status.expression}" size="6" multiple="multiple" class="multi w60" >
								             		<option></option>					
											</select>										
											
																					
											<a class="button_s valign_bottom" href="#a"><span id="updateBtn"><img src="<c:url value="/base/images/icon/ic_btn_enroll.gif"/>" alt="" />
												<ikep4j:message pre='${preButton}' key='change' /></span></a>
											<a class="button_s valign_bottom" href="#a"><span id="deleteBtn"><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" />
												<ikep4j:message pre='${preButton}' key='delete' /></span></a>
											
										</div>
									</td> 
								</tr>				
								</spring:bind>
								
								<spring:bind path="mail.title">
								<tr> 
									<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
									<td>
										<div>
										<input 
										name="${status.expression}" 
										id="${status.expression}" 
										type="text" 
										class="inputbox" 
										value="${status.value}" 
										size="80" 
										title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
										/>
										</div>
									</td> 
								</tr>				
								</spring:bind>
								
								<spring:bind path="mail.content">
								<tr> 
									<td colspan="2" id="contentDiv">
										<div>
										<textarea 
										name="${status.expression}" 
										id="${status.expression}" 
										class="inputbox w100"
										cols="" 
										rows="20" 
										title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
										>${status.value}</textarea>	
										</div>						
									</td> 
								</tr>				
								</spring:bind>
								
								<tr>
									<td id="fileUploadArea" colspan="2"></td>
								</tr>
											
							</tbody>					
							
							</table>
						
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#a"><span><ikep4j:message pre='${preButton}' key='send' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
			       
				
				</div>
				<!--//popup_contents End-->
			 
				<!--popup_footer Start-->
				<div id="popup_footer">
					
				</div>
				<!--//popup_footer End-->
				
					
		
		</div>
		<!--//popup End-->
		
		
		
		
	
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%>

<c:set var="prefix">ui.lightpack.todo.updateTaskView</c:set>
<c:set var="messagePrefix">ui.lightpack.todo.message</c:set>
<c:set var="buttonPrefix">ui.lightpack.todo.button</c:set>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">

var $sel  = ""; //담당자 selectbox

var oldFiles;
var oldSizes;

(function($) {
	$jq(document).ready(function() {
		//파일업로드 컨트롤로 생성	
		/*var uploaderOptions = {
			files : ${fileDataListJson}, //수정일때
			isUpdate : true,    // 등록 및 수정일때 true
			allowFileType : "all"
		};
		var fileController = new iKEP.FileController("#orderForm", "#fileUploadArea", uploaderOptions);*/
		
		dextFileUploadInit(""+20*1024*1024 ,"1", "all");
		
	      
        <c:if test="${not empty fileDataListJson}"> 
        oldFiles = ${fileDataListJson};
        $jq.each(oldFiles,function(index){
            var fileInfo = $.extend({index:index}, this);
            document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
         });
        
        dextFileUploadRefresh(); 
        oldSizes =document.getElementById("FileUploadManager").Size;
        </c:if>       
        
		
		$jq("#dueDateCalendar").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		$jq("#startDateCalendar").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});

		//담당자 삭제
		$jq("#deleteButton").click(function() { 
			if($jq('#workers option:selected').length < 1) {
				alert("<ikep4j:message pre='${messagePrefix}' key='worker.delete'/>");
			} else {
				$jq('#workers option:selected').remove();
			}
		});

		//사용자 클릭
		$jq('#userName').keypress( function(event) {
			iKEP.searchUser(event, "Y", setUser);
		});
		$jq('#userSearchBtn').click( function() { 
			$jq('#userName').trigger("keypress");
		});

		$sel = $jq("#workers").children().remove().end();

	
		 <c:forEach var="item" items="${taskUserList}" varStatus="loopCount">
			var userInfo = {// 공통필수
				type:"user",
				empNo : "",
				id:"${item.workerId}",
				jobTitleName:"${user.localeCode == portal.defaultLocaleCode ? item.workerJobRankName : item.workerJobRankEnglishName}",
				//name:"${expert.userName}",
				userName : "${user.localeCode == portal.defaultLocaleCode ? item.workerName : item.workerEnglishName}",
				searchSubFlag:false,
				teamName:"${user.localeCode == portal.defaultLocaleCode ? item.workerTeamName : item.workerTeamEnglishName}",
				email:"",
				mobile:""
			};
			$jq.tmpl(iKEP.template.userOption, userInfo).appendTo($sel)
				.data("data", userInfo);
		</c:forEach>
		
		//주소록 클릭
		$jq("#btnAddrControl").click(function() {
			var items = [];
			
			$sel.children().each(function(){
				if(!this.value){
					$jq(this).remove();
				} else {
					items.push($jq.data(this, "data"));
				}
	       });
			 
			iKEP.showAddressBook(setUser2, items, {selectType:"user", isAppend:false, tabs:{common:1}});
		});
		
		//저장 클릭
		$jq("#saveButton").click(function() {
			$("#orderForm").trigger("submit");
		});

		//validation
		var validOptions = {
			rules : {
				title : {
					required : true, 
					rangelength : [1, 100]
				},
				taskContents : {
					required : true, 
					rangelength : [1, 1000]
				}
			},
			messages : {
				title : {       
					required : "<ikep4j:message key='NotEmpty.task.title' />",
					rangelength : "<ikep4j:message key='Size.task.title' />"
				},
				taskContents : {       
					required : "<ikep4j:message key='NotEmpty.task.taskContents' />",
					rangelength : "<ikep4j:message key='Size.task.taskContents' />"
				}
			},
			notice : {
				title : "<ikep4j:message key='Notice.task.title' />",
				taskContents : "<ikep4j:message key='Notice.task.taskContents' />"
			},
			submitHandler : function(form) {// 부가 검증 처리
				//작업기한은 현재시간 이후로만 설정되도록
				var toDay = new Date();
				var selectedDate = $("#dueDateCalendar").val();
				var dueDay = new Date(selectedDate.substring(0, 4), selectedDate.substring(5, 7)-1, selectedDate.substring(8, 10), $jq('#dueTimeHour').val(), $jq('#dueTimeMinute').val(), 0);
				if(dueDay-toDay < 0) {
					alert("<ikep4j:message pre='${messagePrefix}' key='validation.dueDay.future'/>");
					return;
				}
				//작업착수 현재시간 이후로만 설정되도록
				selectedDate = $("#startDateCalendar").val();
				dueDay = new Date(selectedDate.substring(0, 4), selectedDate.substring(5, 7)-1, selectedDate.substring(8, 10), 23, 59, 0);
				if(dueDay-toDay < 0) {
					alert("<ikep4j:message pre='${messagePrefix}' key='validation.startDay.future'/>");
					return;
				}

				// 나의 업무 등록 시에는 사용자 검사, 동일 사용자 검사는 하지 않는다. 2014.09.17
				if ("${task.subworkCode}" != "MYTASK"){
					//사용자 검사
					if($jq('#workers option').length < 1) {
						alert("<ikep4j:message pre='${messagePrefix}' key='worker.add'/>");
						return;
					}
	
					//동일 사용자 검사
					var checkUserResult = checkUsers();
					if(checkUserResult != "") {
						alert("<ikep4j:message pre='${messagePrefix}' key='worker.duplication'/>" + " : " + checkUserResult);
						return;
					}
					
					var idlist = [];
					var itemstr ="";
					$jq('#workers option').each(function(){idlist.push(this.value)});
					$jq.each(idlist,function(index, item) {itemstr += item + ',';});
					$jq('#orderForm>input[name=etcName]').val(itemstr.substring(0, itemstr.length-1));
				}

				//작업일시 추가
				$jq('#orderForm>input[name=dueDate]').val($("#dueDateCalendar").val() + " " + $jq('#dueTimeHour').val() + ":" + $jq('#dueTimeMinute').val() + ":00");

				//시작일 추가
				$jq("#orderForm>input[name=startDate]").val($jq('#startDateCalendar').val() + " 09:00:00");
				
				if(confirm("<ikep4j:message pre="${messagePrefix}" key="save" />")) {					
					
				    //파일이 있을경우, 업로드를 먼저 실행함                    
                    if((oldSizes != undefined && oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
                       	btnTransfer_Onclick("orderForm");
                    }else{       
			        	if (oldFiles != undefined){
	                        oldFileSetting(oldFiles , form);    
			        	}
                        //oldFileSetting(oldFiles , form);    
                        writeSubmit(form);
                    }   
				}
			}

		};
		new iKEP.Validator("#orderForm", validOptions);

	});
	
	writeSubmit = function(targetForm){
		   
        targetForm.submit();
    };

	//사용자  등록
	setUser = function(data) {
		if(data == "") {
			alert("<ikep4j:message pre='${messagePrefix}' key='user.nothing'/>");
			$jq('#userName').val("");
			return;
		}
		$jq('#userName').val("");
		$jq(data).each(function(index) {
			var isChk = false;
			
			$sel.children().each(function(){
				if(this.value == data[index].id){

					isChk = true;
				} 
	       });

		   if(isChk == false){

			   var $option = $jq.tmpl(iKEP.template.userOption, this).appendTo($sel);

	             $jq.data($option[0], "data", this);
			}
			
		});	
	}
	setUser2 = function(data) {
		$sel.empty();
		$jq(data).each(function(index) {

			 var $option = $jq.tmpl(iKEP.template.userOption, this).appendTo($sel);

             $jq.data($option[0], "data", this);
             
		});	
	}

	//동일 사용자 검사
	checkUsers = function() {
		var userName = "";
		
		$jq('#workers option').each(function(index){
			var userId = this.value;
			var m = 0;
			$jq('#workers option').each(function(index){
				if(userId == this.value) {
					m += 1;
				}
			});
			if(m > 1) {
				userName = $jq('#workers option:eq(' + index + ')').text();
				return false;
			}
		});
		
		return userName;
	}
	
	
})(jQuery);
</script>

				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2>나의 업무 수정</h2>
				</div>
				<!--//pageTitle End-->
	
				<!--blockDetail Start-->
				<div class="blockDetail">
					<form id="orderForm" name="orderForm" action="<c:url value="/lightpack/todo/updateMyTask.do"/>" method="post">
					<input type="hidden" name="searchConditionString" value="${searchConditionString}"/>
					<spring:bind path="task.taskId">
					<input type="hidden" name="${status.expression}" value="${status.value}"/>
					</spring:bind>
					<spring:bind path="task.etcName">
					<input type="hidden" name="${status.expression}" id="etcName"/>
					</spring:bind>	
					<spring:bind path="task.dueDate">
					<input type="hidden" name="${status.expression}"/>
					</spring:bind>
					<spring:bind path="task.startDate">
					<input type="hidden" name="${status.expression}"/>
					</spring:bind>
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<colgroup>
							<col width="18%" />
							<col width="46%" />
							<col width="14%" />
							<col width="22%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='form.title'/></th>
								<td colspan="3">
								<div>
									<spring:bind path="task.title">
									<input type="text" class="inputbox w100" title="<ikep4j:message pre='${prefix}' key='form.title'/>" name="${status.expression}" value="${status.value}" maxlength="100"/>
									</spring:bind>
								</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span class="colorPoint">*</span>업무기한</th>
								<td>
									<c:set var="dueDateHour"><ikep4j:timezone date='${task.dueDate}' pattern='HH'/></c:set>
									<c:set var="dueDateMinute"><ikep4j:timezone date='${task.dueDate}' pattern='mm'/></c:set>
									<input type="text" class="inputbox" readonly="readonly" id="dueDateCalendar" name="dueDateCalendar" value="<ikep4j:timezone date='${task.dueDate}' pattern='yyyy.MM.dd'/>" size="10" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='form.calendar'/>"/>
									<select id="dueTimeHour">
										<option value="00" <c:if test="${dueDateHour == '00'}">selected="selected"</c:if> >00</option>
										<option value="01" <c:if test="${dueDateHour == '01'}">selected="selected"</c:if> >01</option>
										<option value="02" <c:if test="${dueDateHour == '02'}">selected="selected"</c:if> >02</option>
										<option value="03" <c:if test="${dueDateHour == '03'}">selected="selected"</c:if> >03</option>
										<option value="04" <c:if test="${dueDateHour == '04'}">selected="selected"</c:if> >04</option>
										<option value="05" <c:if test="${dueDateHour == '05'}">selected="selected"</c:if> >05</option>
										<option value="06" <c:if test="${dueDateHour == '06'}">selected="selected"</c:if> >06</option>
										<option value="07" <c:if test="${dueDateHour == '07'}">selected="selected"</c:if> >07</option>
										<option value="08" <c:if test="${dueDateHour == '08'}">selected="selected"</c:if> >08</option>
										<option value="09" <c:if test="${dueDateHour == '09'}">selected="selected"</c:if> >09</option>
										<option value="10" <c:if test="${dueDateHour == '10'}">selected="selected"</c:if> >10</option>
										<option value="11" <c:if test="${dueDateHour == '11'}">selected="selected"</c:if> >11</option>
										<option value="12" <c:if test="${dueDateHour == '12'}">selected="selected"</c:if> >12</option>
										<option value="13" <c:if test="${dueDateHour == '13'}">selected="selected"</c:if> >13</option>
										<option value="14" <c:if test="${dueDateHour == '14'}">selected="selected"</c:if> >14</option>
										<option value="15" <c:if test="${dueDateHour == '15'}">selected="selected"</c:if> >15</option>
										<option value="16" <c:if test="${dueDateHour == '16'}">selected="selected"</c:if> >16</option>
										<option value="17" <c:if test="${dueDateHour == '17'}">selected="selected"</c:if> >17</option>
										<option value="18" <c:if test="${dueDateHour == '18'}">selected="selected"</c:if> >18</option>
										<option value="19" <c:if test="${dueDateHour == '19'}">selected="selected"</c:if> >19</option>
										<option value="20" <c:if test="${dueDateHour == '20'}">selected="selected"</c:if> >20</option>
										<option value="21" <c:if test="${dueDateHour == '21'}">selected="selected"</c:if> >21</option>
										<option value="22" <c:if test="${dueDateHour == '22'}">selected="selected"</c:if> >22</option>
										<option value="23" <c:if test="${dueDateHour == '23'}">selected="selected"</c:if> >23</option>
									</select><ikep4j:message pre='${prefix}' key='form.dueTimeHour'/>
									<select id="dueTimeMinute">
										<option value="00" <c:if test="${dueDateMinute == '00'}">selected="selected"</c:if> >00</option>
										<option value="05" <c:if test="${dueDateMinute == '05'}">selected="selected"</c:if> >05</option>
										<option value="10" <c:if test="${dueDateMinute == '10'}">selected="selected"</c:if> >10</option>
										<option value="15" <c:if test="${dueDateMinute == '15'}">selected="selected"</c:if> >15</option>
										<option value="20" <c:if test="${dueDateMinute == '20'}">selected="selected"</c:if> >20</option>
										<option value="25" <c:if test="${dueDateMinute == '25'}">selected="selected"</c:if> >25</option>
										<option value="30" <c:if test="${dueDateMinute == '30'}">selected="selected"</c:if> >30</option>
										<option value="35" <c:if test="${dueDateMinute == '35'}">selected="selected"</c:if> >35</option>
										<option value="40" <c:if test="${dueDateMinute == '40'}">selected="selected"</c:if> >40</option>
										<option value="45" <c:if test="${dueDateMinute == '45'}">selected="selected"</c:if> >45</option>
										<option value="50" <c:if test="${dueDateMinute == '50'}">selected="selected"</c:if> >50</option>
										<option value="55" <c:if test="${dueDateMinute == '55'}">selected="selected"</c:if> >55</option>
									</select><ikep4j:message pre='${prefix}' key='form.dueTimeMinute'/>		
								</td>
								<th scope="row">업무착수</th>
								<td>
									<input type="text" class="inputbox" readonly="readonly" id="startDateCalendar" name="startDateCalendar" value="<ikep4j:timezone date='${task.startDate}' pattern='yyyy.MM.dd'/>" size="10" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefix}' key='form.calendar'/>"/>
								</td>
							</tr>
							<c:if test="${task.subworkCode == 'TASK'}">
							<tr>
								<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='form.worker'/></th>
								<td colspan="3">
									<input name="userName" id="userName" type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='form.worker'/>" />
									<a class="button_ic" id="userSearchBtn"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" /><ikep4j:message pre='${buttonPrefix}' key='workerSearch'/></span></a>
									<a class="button_ic" id="btnAddrControl"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" /><ikep4j:message pre='${buttonPrefix}' key='workerAddress'/></span></a>
									<a class="tdInstruction"><ikep4j:message pre='${prefix}' key='form.worker.notice'/></a>
									<div class="input_buttonBox">
										<div class="input_buttonBox01"> 
											<select title="<ikep4j:message pre='${prefix}' key='form.worker'/>" id="workers" name="addrGroupList" size="3" multiple="multiple">
								             	<option value=""></option>
								            </select>
										</div>
										<div class="input_buttonBox02">
										 	<a class="button_ic" href="#a" id="deleteButton"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${buttonPrefix}' key='workerDelete'/></span></a>
										</div>
									</div>
								</td>
							</tr>
							</c:if>
							<tr>
								<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${prefix}' key='form.contents'/></th>
								<td colspan="3">
								<div>
									<spring:bind path="task.taskContents">
									<textarea name="${status.expression}" class="w100" title="<ikep4j:message pre='${prefix}' key='form.contents'/>" cols="" rows="10">${task.taskContents}</textarea>
									</spring:bind>
								</div>
								</td>
							</tr>											
						</tbody>
					</table>
					<div class="fileAttachArea">
						<div class="wrap">
							<div class="th" style="width:18%;">
								<div class="title"><ikep4j:message pre='${prefix}' key='form.attachFile'/></div>
							</div>
							<div class="td" style="margin-left:18%;">
								<div class="content">
									<table style="width:100%;">
									      <tr>
									          <td colspan="2" style="border-color:#e5e5e5">
									              <OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
									                  height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
									                   <param name="ButtonVisible" value="FALSE" />
									                   <param name="VisibleContextMenu" value="FALSE" />
									                   <param name="StatusBarVisible" value="FALSE" />
									                   <param name="VisibleListViewFrame" value="FALSE" />
									              </OBJECT>
									          </td>
									      <tr>									
									      <tr>
									          <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
									          <td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
									          <img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
									          <img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />    
									          </td>
									      </tr>
									  </table>
								</div>
							</div>
						</div>
					</div>
					</form>
				</div>
				<!--//blockDetail End-->
														
				<!--blockButton Start-->
				<div class="blockButton mb10"> 
					<ul>
						<li><a id="saveButton" class="button" href="#none"><span>등록</span></a></li>
						<li><a class="button" href="<c:url value='/lightpack/todo/listTodoView.do?searchConditionString=${searchConditionString}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
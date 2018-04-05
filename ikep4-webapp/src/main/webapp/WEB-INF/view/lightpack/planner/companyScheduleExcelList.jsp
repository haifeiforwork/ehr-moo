<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%@ page import="destiny.link.slo.service.DestinySLO"%>
<%!
public void setSystemProperty( String key, String value) {
    System.setProperty( key, value);
}

public void setCookie( HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie( key, value);
    cookie.setPath( "/");
    cookie.setMaxAge( -1);
    cookie.setVersion( 0);
    //cookie.setComment( "destiny slo test");
    cookie.setSecure( false);
    response.addCookie( cookie);
}
%>

<%
// ECM Server Address
String sloServerAddress = "http://ecm.moorim.co.kr";
String sloAPIKey = "0VbXsZYobdOnciJmv4GQ3h16EvOjAoF0icK5sHMSvX4=";

// GW Login User Account
String userAccount = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId(); // 로그인 사용자ID
// GW Login User's GroupCode
String userGroupCode = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getGroupId(); // 로그인 사용자 부서코드
// ECM Settings
setSystemProperty( "common.SloAddrKey", sloServerAddress);
setSystemProperty( "common.SloAPIKey", sloAPIKey);
setCookie( response, "ACCOUNT", userAccount);
setCookie( response, "GROUP_CODE", userGroupCode);
%>

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript" language="javascript">
//<!--


		
	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		$jq("#searchForm").attr("action", "<c:url value='companyScheduleExcelList.do' />");
		$jq("#searchForm")[0].submit();
	}


	
	// 소팅을 위한 함수
	function sort(sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	
	(function($) {
		var uploader,
			uploadResult = null;
	
		// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
		$jq(document).ready(function() {
			
			// 백스페이스 방지(input, select 제외)
			$jq(document).keydown(function(e) {
				
				var element = e.target.nodeName.toLowerCase();
				
				if (element != 'input' && element != 'textarea') {
					
				    if (e.keyCode === 8) {
				    	
				        return false;
				    }
				}
			});
			
			$jq("#startPeriod").datepicker().next().eq(0).click(function() { 
				
				$jq(this).prev().eq(0).datepicker("show"); 
			});
			
			$jq("#endPeriod").datepicker().next().eq(0).click(function() { 
				
				$jq(this).prev().eq(0).datepicker("show"); 
			});
			
			$jq("#searchExcelButton").click(function() {  
				
				getList();
			});
			
			
			$("#btnDelete").click(function() {
				var itemIds = new Array();
				$("#searchForm input[name=chkFileId]:checked").each(function(index) { 
					itemIds.push($(this).val()); 
				});	
				
				if(itemIds.length > 0) {
					if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
						$("#divBody").ajaxLoadStart();
						$.ajax({
							url : '<c:url value="companyScheduleExcelDelete.do" />',     
							data : {
								chkFileIds : itemIds.toString()
							},     
							type : "post",     
							success : function(result) {      
								getList();
							},
							error : function(event, request, settings){
								 alert("error");
							}
						});
					}  
				} else {
					alert("<ikep4j:message pre="${preMessage}" key="selectDeleteItem" />");
				}
			});   
			
			function viewReady() {
				$("#inputFile").css("color", "#999")
					.val("전사일정 파일을 선택하여 업로드 하세요.");
				
			}
			function isNumber(s) {
				  s += ''; // 문자열로 변환
				  s = s.replace(/^\s*|\s*$/g, ''); // 좌우 공백 제거
				  if (s == '' || isNaN(s)) return false;
				  return true;
			}
			function exceptFileName(uploadName){
				alert("["+uploadName+"]\n파일제목이 틀립니다. YYYY년MM월일정.xls 형식으로 업로드하세요");	
				uploader.refresh();
				$("#divBody").ajaxLoadComplete();
				viewReady();
			}
		});
	})(jQuery);
//-->
</script>

<script type="text/javascript" language="javascript">
var fileCnt = 0;

function CyberdigmpopupSelect( action) { 
	        var iframe = document.getElementById( "select_dialog");
	        var callbackFn = 'CyberdigmselectItem';

	        //팝업 설정( 해당 다이얼로그에 맞도록 수정)
	        var settings = "&settings=width:665,height:480,location:0,menubar:0,resizable:0,scrollbars:0,status:0,toolbar:0";

	        iframe.src = "<c:url value='/base/common/destinySLO.jsp?TARGET_URL=popup&action='/>" + action + "&callBack=" + callbackFn + settings;
	    }

function CyberdigmselectItem( _p, type) {
	var data = eval( "(" + decodeURIComponent( _p) + ")");
	
	if(data.length > 1){
		alert("하나의 파일만 선택 가능합니다.");
		return;
	}else{
		for( var i = 0; i < data.length; i++){
	
	    	//내부URL(에이전트 설치 시)
	        var fileUrl1 = "http://127.0.0.1:36482/viewFile?fileName=" + encodeURIComponent( data[i].fileName) + "&docID=" + data[i].targetOID + "&fileID_=" + data[i].OID + "&history=true&overWrite=true&recently=true&clientType=I";
	
	    	//외부URL(에이전트 미설치 시)
	
	    	var index = data[i].fileFullPath.indexOf( '?');
	
	    	var str = data[i].fileFullPath.substring( index + 1, data[i].fileFullPath.length);
	
	    	//개발서버
	        //var fileUrl2 = data[i].fileFullPath;
	
	    	//운영
	        var fileUrl2 =  "http://ecm.moorim.co.kr:80/servlet/blob?" + str;
	
	    	//모바일용 외부 URL
	    	var fileUrl3 = data[i].fileFullPath;
	
	        fileCnt++;
	        
	        $jq("#ecmFile").append(
						"<input name=\"ecmFileName\" id=\"ecmFileName\" type=\"text\" value=\""+data[i].fileName+"\" class=\"inputbox w100\" readonly=\"readonly\" />"+
						"<input name=\"ecmFileUrl1\" id=\"ecmFileUrl1\" type=\"hidden\" value=\""+fileUrl1+"\" />"+
						"<input name=\"ecmFileUrl2\" id=\"ecmFileUrl2\" type=\"hidden\" value=\""+fileUrl2+"\" />"+
						"<input name=\"ecmFilePath\" id=\"ecmFilePath\" type=\"hidden\" value=\""+data[i].fileFullPath+"\" />"+
						"<input name=\"ecmFileId\" id=\"ecmFileId\" type=\"hidden\" value=\""+data[i].OID+"\" />"
				);
	    }
		$jq("#fileForm").trigger("submit");
	}
    
}	

function fileDown(url){
	window.open(url, 'filedown', 'width=800px, height=670px, scrollbars=yes');
}

</script>
<div id="divBody">
<div id="pageTitle">
	<h2>전사일정 Excel 다운로드 관리</h2>
</div>

<div class="directive"> 
	<ul>
		<li><span style="color:#bf000f">파일명을 [2012년08월일정.xls]와 같이 작성하여 업로드 합니다.</span></li>	
		<li><span style="color:#bf000f">파일명에 공백은 없습니다.</span></li>	
		<li><span style="color:#bf000f">월을 두자리로 맞춰야합니다.</span></li>																	
	</ul>
</div>
<div class="blockBlank_10px"></div>
<form id="fileForm" method="post" action="<c:url value="/lightpack/planner/calendar/companyScheduleEcmExcelListUpload.do"/>" enctype="multipart/form-data">	
<!--blockDetail Start-->
	<div class="blockDetail" >
		<table>
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%">전사 일정 Excel 업로드</th>
					<td width="82%" id="ecmFile">
						<a class="button_s" href="#a" onclick="CyberdigmpopupSelect('selectAllFiles');" style="vertical-align:middle;"><span>파일업로드</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
	<!--//blockDetail End-->
<div class="blockBlank_10px"></div>
				
	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="scheduleId" name="scheduleId"/>
				<input type="hidden" id="rejectReason" name="rejectReason"/>
	
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				

				

					
				<!--tableTop Start-->  
				<div class="tableTop">



					<div class="listInfo"> 
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum">
							${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
							( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
						</div>
					</div>
					<div class="tableSearch"> 
						<input id="startPeriod" name="startPeriod" type="text" class="inputbox datepicker" value="${startPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						~
						<input id="endPeriod" name="endPeriod" type="text" class="inputbox datepicker" value="${endPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						
						<a id="searchExcelButton" name="searchExcelButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a>
					</div>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				
				<table summary="<ikep4j:message pre='${preDetail}' key='myReserveList' />">
					<caption></caption>
						<colgroup>
							<col width="10%"/>
							<col width="10%"/>
							<col width="*"/>
							<col width="10%"/>
							<col width="14%"/>
						</colgroup>
					<thead>
						<tr>
						 	<th scope="col">&nbsp;</th>
							<th scope="col">
								<a onclick="javascript:sort('SORT_ORDER', '<c:if test="${searchCondition.sortColumn eq 'SORT_ORDER'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preDetail}' key='order' />
								</a>
							</th>

							<th scope="col">
								<a onclick="javascript:sort('FILE_REAL_NAME', '<c:if test="${searchCondition.sortColumn eq 'FILE_REAL_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									파일명
								</a>
							</th>
						
							<th scope="col">
								<a onclick="javascript:sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									등록일
								</a></th>
							<th scope="col">등록자</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="5" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="scheduleFile" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td class="textCenter">
											<input type="checkbox" id="chkFileId" name="chkFileId" value="${scheduleFile.fileId}" title="checkbox"/>
							    		</td>
							    		<td class="textCenter" >
							    			${scheduleFile.num}
							    		</td>
										<td class="textCenter" >
											<c:if test="${empty scheduleFile.fileUrl1}">
												<a href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${scheduleFile.fileId}">${scheduleFile.fileRealName}</a>
											</c:if>
											<c:if test="${!empty scheduleFile.fileUrl1}">
												<a href="" onclick="javascript:fileDown('${scheduleFile.fileUrl1}');return false;">${scheduleFile.fileRealName}</a>
											</c:if>
										</td>
										<td class="textCenter">
											${scheduleFile.registDate}
										</td>
										<td class="textCenter">
											${scheduleFile.registerName}
										</td>
									</tr>
								</c:forEach>
						    </c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<!--Page Number Start--> 
				<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
					<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind> 
				<!--//Page Number End-->
				
	
			</form>
		</div>
	</div>
	<iframe width="0" height="0" src="<c:url value="/base/common/destinySLO.jsp?TARGET_URL=install"/>"></iframe>
	<%-- <iframe width="0" height="0" src="<c:url value="/base/common/file_sample.jsp"/>"></iframe> --%>
	<iframe id="select_dialog" src="" style="display:none;"></iframe>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="btnDelete" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
<!--//blockListTable End-->
</div>
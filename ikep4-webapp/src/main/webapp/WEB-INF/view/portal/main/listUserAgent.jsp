<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript" language="javascript">
//<!--

		
	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		$jq("#searchForm").attr("action", "<c:url value='listReaderView.do' />");
		$jq("#searchForm")[0].submit();
	}

	function goMail(nameList,tempEmailList) {
		var emailList = [tempEmailList];
		iKEP.sendTerraceMailPop(emailList, "", "");  
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
			$("#searchReaderButton").click(function() {   
				getList();
				return false; 
			});
		});
	})(jQuery);
//-->
</script>
<!--popup Start-->
<div id="popup_2">





<div class="contentIframe">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
						
				<!--tableTop Start-->  
				<!--//tableTop End-->	
				
				<table>
					<caption></caption>
						<colgroup>
							<col width="30%"/>
							<col width="20%"/>
							<col width="*"/>

						</colgroup>
					<thead>
						<tr>
							<th scope="col">
									이름
							</th>

							<th scope="col">
									부서
							</th>
						
							<th scope="col">
									연락처
						     </th>

						</tr>
					</thead>
					<tbody>
								<c:forEach var="agent" items="${userList}" varStatus="status">
									<tr>
										<td class="textCenter">
											<a onclick="goMail('${agent.userName}','${agent.mail}');" href="#a"><font size="2"> ${agent.userName} ${agent.jobTitleName} </font></a>
							    		</td>
										<td class="textCenter" >
											${agent.workPlaceCode}/${user.workPlaceCode}<font size="2">${agent.teamName} </font>
										</td>
										<td class="textCenter">
											<font size="2">${agent.mobile} </font>
										</td>
									</tr>
								</c:forEach>
					</tbody>
				</table>
	
			</form>
		</div>
	</div>
	
</div>
	<!--//popup_contents End-->
</div>
			
</div>
<!--//popup End-->
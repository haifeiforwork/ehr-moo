<%--
=====================================================
	* 기능설명	:	전체회원관리
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%>

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="message.lightpack.cafe.member.listCafeMember.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.member.listCafeMember.search" />
<c:set var="preList"    value="message.lightpack.cafe.member.listCafeMember.list" />
<c:set var="preCommon"  value="message.lightpack.cafe.common" />
<c:set var="preButton"  value="message.lightpack.cafe.member.listCafeMember.button" />
<c:set var="preScript"  value="message.lightpack.cafe.member.listCafeMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {

	// 주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다. 
	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {

		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		$jq("#searchButton").click();
	};
	

	deleteMember = function() {
		
		var countCheckBox	=	$("input[name=memberIds]:checkbox:checked").length;
		var chk	= 0;
		
		$("input[name=memberIds]").each(function(idx){
			if(this.checked && $(this).val()=='${cafe.sysopId}')
			{
				chk++;
			}
		});
		
		if(chk>0)
		{
			alert('<ikep4j:message pre='${preScript}' key='notDeleteSysop' />');
			
			$jq("input[name=memberIds]").attr("checked", false); 
			$jq("#checkboxAll").attr("checked", false); 
			return false;			
		}
		else if(countCheckBox>0)
		{
			<c:if test="${searchCondition.listType=='Member'}">	
			if(!confirm('<ikep4j:message pre='${preScript}' key='deleteMember' />'))
			{
				return false; 	
			}
			</c:if>
			<c:if test="${searchCondition.listType!='Member'}">	
			if(!confirm('<ikep4j:message pre='${preScript}' key='rejectMember' />'))
			{
				return false; 	
			}
			</c:if>
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/lightpack/cafe/member/deleteCafeMember.do"/>"
			});
		
			$jq("#mainForm").attr({method:'post'}).submit();
			
		}
		else
		{
			<c:if test="${searchCondition.listType=='Member'}">	
			alert('<ikep4j:message pre='${preScript}' key='checkboxDeleteMember' />');
			</c:if>
			<c:if test="${searchCondition.listType!='Member'}">
			alert('<ikep4j:message pre='${preScript}' key='checkboxRejectMember' />');
			</c:if>
		}
		return false; 	
	};
	
	// Member 조회
	viewMember = function(memberId) {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/member/readCafeMemberView.do"/>",
			method:"GET"
		});
		$jq("input[name=memberId]").val(memberId);
		$jq("#mainForm").submit(); 
		
		return false;

	}; 
	
	// 신규 회원 등록
	addMember = function() {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/member/addNewMemberView.do"/>",
			method:"GET"
		});
		$jq("#mainForm").attr({method:'get'}).submit(); 
		
		return false;

	}; 	
	
	// 회원 등급 수정
	updateStatus	=	function(memberLevel,updateType) {
		
		var countCheckBox	=	$jq("input[name=memberIds]:checkbox:checked").length;

		var chk	= 0;
		
		$jq("input[name=updateType]").val(updateType);
		
		$("input[name=memberIds]").each(function(idx){
			if(this.checked && $(this).val()=='${cafe.sysopId}')
			{
				chk++;
			}
		});
		
		if(chk>0)
		{
			alert('<ikep4j:message pre='${preScript}' key='changeSysopToManage' />');
			
			
			//$jq("input[name=memberIds]").attr("checked", false);
			//$jq("#checkboxAll").attr("checked", false);  
			return false;			
		}
		else if(countCheckBox>0)
		{	
			if(!confirm('<ikep4j:message pre='${preScript}' key='changeMemberLevel' />')) {
				return;
			}
			
			$jq("input[name=memberLevel]").val(memberLevel);
			$jq.ajax({
				url : '<c:url value='/lightpack/cafe/member/updateMemberLevel.do' />?'+$jq("#mainForm").serialize(),
				type : "post",
				loadingElement : {container:"#pageLodingDiv"},
				success : function(result) {
					$jq("#searchButton").click(); 
				}
			});	

		}
		else
		{
			alert('<ikep4j:message pre='${preScript}' key='checkboxMember' />');
		}
		return false;
	};
	
	// 회원 승인
	updateMemberJoin	=	function(memberLevel,updateType) {
		
		
		var countCheckBox	=	$jq("input[name=memberIds]:checkbox:checked").length;
		$jq("input[name=updateType]").val(updateType);
		if(countCheckBox>0)
		{
			if(memberLevel=='3' && !confirm('<ikep4j:message pre='${preScript}' key='joinMember' />'))
			{
				return false; 	
			}
			if(memberLevel=='4' && !confirm('<ikep4j:message pre='${preScript}' key='joinAssociateMember' />'))
			{
				return false; 	
			}		
			
			$jq("input[name=memberLevel]").val(memberLevel);
		
			$jq.ajax({
				url : '<c:url value='/lightpack/cafe/member/updateMemberLevel.do' />?'+$jq("#mainForm").serialize(),
				type : "post",
				loadingElement : {container:"#pageLodingDiv"},
				success : function(result) {
					$jq("#searchButton").click(); 
				}
			});	

		}
		else
		{
			alert('<ikep4j:message pre='${preScript}' key='checkboxMember' />');
		}
		return false; 	
		
		
	};	
	
	// 시샵지정
	updateSysop	=	function(memberLevel,updateType) {
		
		var countCheckBox	=	$jq("input[name=memberIds]:checkbox:checked").length;
		
		var chk	= 0;
		
		$jq("input[name=updateType]").val(updateType);
		
		$("input[name=memberIds]").each(function(idx){
			if(this.checked && $(this).val()=='${cafe.sysopId}')
			{
				chk++;
			}
		});
		
		if(chk>0)
		{
			alert('<ikep4j:message pre='${preScript}' key='nowSysop' />');
			
			
			//$jq("input[name=memberIds]").attr("checked", false);
			//$jq("#checkboxAll").attr("checked", false);  
			return false;			
		}
		else if(countCheckBox==1)
		{
			if($jq("input[name=memberIds]:checkbox:checked").val()!='${user.userId}')
			{
				if(!confirm('<ikep4j:message pre='${preScript}' key='changeSysop' />'))
					return false;
			}
			$jq("input[name=memberLevel]").val(memberLevel);
			
			$jq.ajax({
				url : '<c:url value='/lightpack/cafe/member/updateSysop.do' />?'+$jq("#mainForm").serialize(),
				type : "post",
				loadingElement : {container:"#pageLodingDiv"},
				success : function(result) {
					if(result.memberLevel=='1')
						$jq("#searchButton").click();
					else
						document.location.href="<c:url value='/lightpack/cafe/member/listCafeMemberView.do'/>?cafeId="+$jq("input[name=cafeId]").val()+"&listType=Member";
				}
			});	

		}
		else if(countCheckBox>1)
		{
			alert('<ikep4j:message pre='${preScript}' key='oneSysop' />');
		}
		else
		{
			alert('<ikep4j:message pre='${preScript}' key='zeroSysop' />');
		}
		return false; 		
		
	};
	
	sendMail	=	function() {
		
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/member/sendMailMemberView.do"/>?"+$jq("#mainForm").serialize(),
			method:"GET"
		});
		$jq("#mainForm").attr({method:'get'}).submit(); 
	
		return false;	
		
	};
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		iKEP.iFrameContentResize();
		
		$jq("#searchButton").click(function() {
			$jq("#mainForm").attr({method:'get'}).submit();	
			
			return false; 

		});
		$jq("select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $jq("#searchBoardItemButton").click(); 
			$jq("#searchButton").click(); 
		});
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=memberIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
		
	   
	});
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<c:if test="${searchCondition.listType eq 'Member'}">
		<h2><ikep4j:message pre="${preHeader}" key="pageMemberTitle" /></h2>
	</c:if>
	<c:if test="${searchCondition.listType eq 'Join'}">
		<h2><ikep4j:message pre="${preHeader}" key="pageJoinTitle" /></h2>
	</c:if> 

</div> 
<!--//pageTitle End-->  

<!--tab Start-->
<%-- <div id="divTab1" class="iKEP_tab">     
	<ul>
		<li><a id="tabs-m" href="#tabs-1"><ikep4j:message pre="${preHeader}" key="allMemberManagement" /></a></li>
		<li><a id="tabs-j" href="#tabs-2"><ikep4j:message pre="${preHeader}" key="joinManagement" /></a></li>
	</ul>
	<div id="tabs-1" style="padding: 0px;"></div>
	<div id="tabs-2" style="padding: 0px;"></div>
</div> --%>

<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value='/lightpack/cafe/member/listCafeMemberView.do' />">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.sortColumn" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.sortType" />
</spring:bind> 
<spring:bind path="searchCondition.cafeId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.cafeId" />
</spring:bind> 
<spring:bind path="searchCondition.cafeStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.cafeStatus" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.listType" />
</spring:bind>
<spring:bind path="searchCondition.memberId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.memberId" />
</spring:bind>
<spring:bind path="searchCondition.memberLevel">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.memberLevel" />
</spring:bind>
<spring:bind path="searchCondition.updateType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="searchCondition.updateType" />
</spring:bind>

<div class="blockListTable" id="blockListTable">
	<!--tableTop Start-->
	<div class="tableTop">
	
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${cafeCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
		</div>
			
		<div class="tableSearch"> 
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="memberName" <c:if test="${'memberName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='memberName'/></option>
					<option value="teamName" <c:if test="${'teamName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='teamName'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
			</spring:bind>
			<a id="searchButton" name="searchButton" href="#a" class="ic_search"><span>Search</span></a>
		</div>
						
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<table summary="<ikep4j:message pre="${preList}" key="summaryMember" />">   
		<caption></caption>
		<c:if test="${searchCondition.listType=='Member'}">				
		<col style="width: 5%;"/>
		<col style="width: 20%;"/>
		<col style="width: 20%;"/>
		<col style="width: 20%;"/>
		<col style="width: 20%;"/>
		<col style="width: 15%;"/>
		</c:if>
		<c:if test="${searchCondition.listType!='Member'}">				
		<col style="width: 5%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 45%;"/>
		<col style="width: 10%;"/>		
		</c:if>
		<thead>
			<tr>
				<th scope="col"><input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<th scope="col">
					<a onclick="sort('memberName', '<c:if test="${searchCondition.sortColumn eq 'memberName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='memberName' />
					</a>
				</th>
				<th scope="col">
					<a onclick="sort('jobTitleName', '<c:if test="${searchCondition.sortColumn eq 'jobTitleName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='jobTitleName' />
					</a>
				</th>
				<th scope="col">
					<a onclick="sort('teamName', '<c:if test="${searchCondition.sortColumn eq 'teamName'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='teamName' />
					</a>
				</th>
				
				<c:if test="${searchCondition.listType=='Member'}">											
				<th scope="col">
					<a onclick="sort('memberLevel', '<c:if test="${searchCondition.sortColumn eq 'memberLevel'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='memberLevel' />
					</a>
				</th>
				<th scope="col">
					<a onclick="sort('joinDate', '<c:if test="${searchCondition.sortColumn eq 'joinDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='joinDate' />
					</a>
				</th>	
				</c:if>
				<c:if test="${searchCondition.listType!='Member'}">											
				<th scope="col">
					<a onclick="sort('memberIntroduction', '<c:if test="${searchCondition.sortColumn eq 'memberIntroduction'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='memberIntroduction' />
					</a>
				</th>
				<th scope="col">
					<a onclick="sort('joinApplyDate', '<c:if test="${searchCondition.sortColumn eq 'joinApplyDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='joinApplyDate' />
					</a>
				</th>	
				</c:if>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
		    
					<c:forEach var="cafeMemberList" items="${searchResult.entity}">
					<tr>
						<td><input id="memberIds" name="memberIds" class="checkbox" title="checkbox" type="checkbox" value="${cafeMemberList.memberId}" /></td>
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							<td><a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafeMemberList.memberId}', 'bottom')" title="${cafeMemberList.memberName}">${cafeMemberList.memberName}</a>
								<a href="#a" onclick="viewMember('${cafeMemberList.memberId}');" title="${cafeMemberList.memberName}"><img src="<c:url value="/base/images/icon/ic_document.png" />" alt="" /></a></td>
							<td>${cafeMemberList.jobTitleName}</td>
							<td>${cafeMemberList.teamName}</td>
						</c:when>
						<c:otherwise>
							<td><a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafeMemberList.memberId}', 'bottom')" title="${cafeMemberList.memberEnglishName}">${cafeMemberList.memberEnglishName}</a>
								<a href="#a" onclick="viewMember('${cafeMemberList.memberId}');" title="${cafeMemberList.memberEnglishName}"><img src="<c:url value="/base/images/icon/ic_document.png" />" alt="" /></a></td>
							<td>${cafeMemberList.jobTitleEnglishName}</td>
							<td>${cafeMemberList.teamEnglishName}</td>
						</c:otherwise>
						</c:choose>	

						<c:if test="${searchCondition.listType=='Member'}">		
						<td>
							<c:choose>
					    		<c:when test="${cafeMemberList.memberLevel=='1'}"> 
									<ikep4j:message pre='${preCommon}' key='member.1' />
								</c:when>
					    		<c:when test="${cafeMemberList.memberLevel=='2'}"> 
									<ikep4j:message pre='${preCommon}' key='member.2' />
								</c:when>
								<c:when test="${cafeMemberList.memberLevel=='3'}"> 
									<ikep4j:message pre='${preCommon}' key='member.3' />
								</c:when>
					    		<c:when test="${cafeMemberList.memberLevel=='4'}"> 
									<ikep4j:message pre='${preCommon}' key='member.4' />
								</c:when>
								<c:when test="${cafeMemberList.memberLevel=='5'}"> 
									<ikep4j:message pre='${preCommon}' key='member.5' />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>	
						</td>

						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeMemberList.joinDate}"/></td>
						</c:if>
						<c:if test="${searchCondition.listType!='Member'}">		
						<td class="textLeft" title="${cafeMemberList.memberIntroduction}"><div class="ellipsis">${cafeMemberList.memberIntroduction}</div></td>

						<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeMemberList.joinApplyDate}"/></td>
						</c:if>											
					</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End--> 
	
</div>
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
<c:if test="${searchCondition.listType=='Member'}">
	<li><a class="button" href="#a" onclick="sendMail()" title="<ikep4j:message pre='${preButton}' key='sendMail'/>"><span><ikep4j:message pre='${preButton}' key='sendMail'/></span></a></li>
	<li><a class="button" href="#a" onclick="addMember()" title="<ikep4j:message pre='${preButton}' key='addMember'/>"><span><ikep4j:message pre='${preButton}' key='addMember'/></span></a></li>

	<li><a class="button" href="#a" onclick="updateSysop('1','manage')" title="<ikep4j:message pre='${preButton}' key='sysop'/>"><span><ikep4j:message pre='${preButton}' key='sysop'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateStatus('2','level')" title="<ikep4j:message pre='${preButton}' key='manage'/>"><span><ikep4j:message pre='${preButton}' key='manage'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateStatus('3','level')" title="<ikep4j:message pre='${preButton}' key='member'/>"><span><ikep4j:message pre='${preButton}' key='member'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateStatus('4','level')" title="<ikep4j:message pre='${preButton}' key='associateMember'/>"><span><ikep4j:message pre='${preButton}' key='associateMember'/></span></a></li>
	<li><a class="button" href="#a" onclick="deleteMember()" title="<ikep4j:message pre='${preButton}' key='memberOut'/>"><span><ikep4j:message pre='${preButton}' key='memberOut'/></span></a></li>
</c:if>
<c:if test="${searchCondition.listType!='Member'}">
	<li><a class="button" href="#a" onclick="updateMemberJoin('3','join')" title="<ikep4j:message pre='${preButton}' key='memberJoin'/>"><span><ikep4j:message pre='${preButton}' key='memberJoin'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateMemberJoin('4','join')" title="<ikep4j:message pre='${preButton}' key='associateMemberJoin'/>"><span><ikep4j:message pre='${preButton}' key='associateMemberJoin'/></span></a></li>
	<li><a class="button" href="#a" onclick="deleteMember()" title="<ikep4j:message pre='${preButton}' key='memberReject'/>"><span><ikep4j:message pre='${preButton}' key='memberReject'/></span></a></li>
</c:if>
</ul>
</div>
<!--//blockButton End-->

</div>


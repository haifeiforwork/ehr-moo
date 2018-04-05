<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preInfo"  			value="message.collpack.kms.admin.permission.team.leader.info" />

<script type="text/javascript" language="javascript">

(function($) {
	
	$(document).ready( function() {
		
		var date 	= new Date();
	    var year 	= date.getFullYear();
        var month 	= date.getMonth()+1;
        var day 	= date.getDate();
        var nowDate = year+"."+month+"."+day;
		//$("#searchStartRegDate").val(nowDate);
		
		//달력
		$("input[name=searchStartRegDate1]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=searchEndRegDate1]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
	});
	
	getList = function() {
		
		$jq.ajax({     
			url : '<c:url value="getPopupRefList.do" />?isMulti=${isMulti}',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {
				$jq("#listDiv").html(result);
			} 
		});       
		
	};
		
	
	// ì£¼ì : ê¸ë¡ë² í¨ìë ìëì ê°ì´ ê¸ë¡ë² ë³ìì ë¬´ëª í¨ìë¥¼ assigní´ì ìì±íë¤. 
	//ì ë ¬ ì¡°ê±´ ë³ê²½ í¨ì
	sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	

	// onloadì ìíí  ì½ë
	$jq(document).ready(function() { 
		
		var param = iKEP.getPopupArgument();
		$jq("#searchWord").val(param.name);
		$jq("#searchColumn").val(param.column);

		getList();
		
		$jq("#applyBtn").click(function() {
			
			var $sel = $jq("input[name=itemId]:checked");
			if($sel.length <1) {
				alert('하나 이상을 선택해 주세요.');
				return;	
			}
			
				  
			var data = new Array();
			var i=0;
			$jq("input[name=itemId]:checked").each(function() {
				var $dataDiv = $(this).next("div.none");
   				data.push({
   					type:"boardItem",
   					itemId: this.value,
   					title : $("span.data_title", $dataDiv).text(),
   					registerName : $("span.data_registerName", $dataDiv).text(),
   					registDate : $("span.data_registDate", $dataDiv).text(),
   					targetSource : $("span.data_targetSource", $dataDiv).text()
   				});
    			i++;
	    	});
			var expVer = navigator.appVersion;
			if(expVer.indexOf("11.") > 0){
				iKEP.returnKmsRefPopup(data);
			}else{
			  iKEP.returnPopup(data);	
			}
		      
	    });
		
		$jq("#cancelBtn").click(function() {
			iKEP.closePop();			
		});
		
		$("#searchForm select[name=searchWorkPlaceName]").change(function() { 
			var workPlaceName = $("#searchForm select[name=searchWorkPlaceName]").val();
			$.post("<c:url value='/collpack/kms/admin/listTeamCodes.do'/>", {"workPlaceName" : workPlaceName}) 
			.success(function(data) {
				$("#searchGroupId").empty();
				$("#searchGroupId").append(data);
			})	
		}); 
		
		$("#searchBoardItemButton").click(function() {  
			$("input[name=searchStartRegDate]").val($("input[name=searchStartRegDate1]").val().replace(/\./g, ""));
			$("input[name=searchEndRegDate]").val($("input[name=searchEndRegDate1]").val().replace(/\./g, ""));
			
			getList();
		});
	
	});
	
})(jQuery);
	
</script>

<!--popup Start-->
<div id="popup">
<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false">  

	<spring:bind path="searchCondition.sortColumn">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.searchColumn">  
		<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="hidden"  />	
	</spring:bind>		
	<spring:bind path="searchCondition.searchWord">  					
		<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="hidden"  />
	</spring:bind>
	
	<input name="searchStartRegDate" id="searchStartRegDate" value="" type="hidden"  />
	<input name="searchEndRegDate" id="searchEndRegDate" value="" type="hidden"  />
	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1>활용정보조회</h1>
		<a href="javascript:iKEP.closePop()"><span>닫기</span></a> 
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
                
        <!--blockSearch Start-->
        <div class="blockSearch">
            <div class="corner_RoundBox03">
                <table summary="검색테이블">
                    <caption></caption>
                    <tbody>
                        <tr>
                            <th scope="row" width="10%">사업장</th>
                            <td width="30%">														
	                            <select title="searchWorkPlaceName" name="searchWorkPlaceName" id="searchWorkPlaceName">
	                                <option value=""><ikep4j:message pre='${preInfo}' key='add.company' /></option>
	                                <c:forEach var="workPlace" items="${workPlaceList}" begin="1">
	                                	<option value="${workPlace}">${workPlace}</option>
	                                </c:forEach>
	                            </select>
                            </td>	
                            <th scope="row" width="10%">부서</th>
                            <td width="50%">														
	                            <select title="searchGroupId" name="searchGroupId" id="searchGroupId">
	                                <option value=""><ikep4j:message pre='${preInfo}' key='add.teamCode' /></option>
	                            </select>
                            </td>							
                        </tr>
                        <tr>
                            <th scope="row">등록일</th>
                            <td colspan="3">
                            	<input name="searchStartRegDate1" id="searchStartRegDate1" title="출처" class="inputbox w20 datepicker" type="text" />
                            	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
                            	 - 
                            	<input name="searchEndRegDate1" id="searchEndRegDate1" title="출처" class="inputbox w20 datepicker" type="text" />
                            	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
                            </td>	
                        </tr>
                        <tr><c:if test="${isSystemAdmin}">
                            <th scope="row">등록자</th>
                            <td><input name="searchRegisterName" title="출처" class="inputbox w30" type="text" /></td>	
                            <th scope="row">제목</th>
                            <td><input name="searchTitle" title="출처" class="inputbox w70" type="text" /></td>		
                            </c:if>
                            <c:if test="${!isSystemAdmin}">
                            <th scope="row">제목</th>
                            <td colspan="3"><input name="searchTitle" title="출처" class="inputbox w70" type="text" /></td>		
                            </c:if>
                        </tr>														
                    </tbody>
                </table>
                <div  id="searchBoardItemButton" name="searchBoardItemButton" class="searchBtn"><a href="#a"><span>Search</span></a></div>
                <div class="l_t_corner"></div>
                <div class="r_t_corner"></div>
                <div class="l_b_corner"></div>
                <div class="r_b_corner"></div>				
            </div>
        </div>	
        <!--//blockSearch End-->
<div id="listDiv">

 
</div>                      
        <!--blockButton Start-->
        <div class="blockButton"> 
            <ul>
				<li><a class="button" href="#" id="applyBtn"><span>확인</span></a></li>
                <li><a class="button" href="javascript:iKEP.closePop()"><span>취소</span></a></li>
            </ul>
        </div>
        <!--//blockButton End-->
		
	</div>
	<!--//popup_contents End-->

	<!--popup_footer Start-->
	<div id="popup_footer">
		
	</div>
	<!--//popup_footer End-->
</form>			
</div>
<!--//popup End-->
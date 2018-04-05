<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>  

<script type="text/javascript" language="javascript">


function mouseOver(obj){
	
	var tds = $jq(obj).find("td");
	$jq.each(tds, function(){
		$jq(this).attr("style", "background-color:#edf2f5;");
	})
}

function mouseOut(obj){
	
	var tds = $jq(obj).find("td");
	$jq.each(tds, function(){
		
		$jq(this).attr("style", "background-color:none;");
	})
	
}

function getZipCode(zcd,addr){

	 var zipcode1 = zcd;
	 //var zipcode1 = zcd.substring(0,3);
	 //var zipcode2 = zcd.substring(3,5);
	 window.opener.publicationForm.zip1No.value = zipcode1;
	 //window.opener.publicationForm.zip2No.value = zipcode2;
	 window.opener.publicationForm.address.value = addr;
	 self.close();
}

(function($) {
	
	viewPage = function(tmpPage){
		if(tmpPage == "tabs-1"){
			$("#tab1").show();
			$("#tab2").hide();
		}else{
			$("#tab2").show();
			$("#tab1").hide();
		}
		
	};
	
	$jq(document).ready(function() { 

		$("#searchForm").validate({ 
			submitHandler: function(form) {
                  form.submit();
                  return true;
            },
            rules : {
    			
    			addrName : {
    				required : true
    			}
    		},
    		
    		messages : {
    			
    			addrName : {
    				required : "지번을 입력해주세요."
    			}
    		}
		}); 
		
		var tmpNum = "0";
		if("${addrType}" == "new"){
			tmpNum = "1"
			$("#tab2").show();
			$("#tab1").hide();
		}
		
		
		tab = $("#divTab2").tabs({
			selected : tmpNum, 
			select : function(event, ui) {
				viewPage($(ui.panel).attr("id"));
			}
		}); 
		
		
		$("#searchBtn1").click(function() {	
			$("#addrType").val("old");
			$("#searchForm").submit(); 
			return false;
	    });
		$("#searchBtn2").click(function() {	
			$("#addrType").val("new");
			$("#searchForm").submit(); 
			return false;
	    });
	});
	
})(jQuery);
	
</script>

<!--popup Start-->
<form id="searchForm" method="post" action="<c:url value='/support/customer/zipcode/search.do' />">
<input type="hidden" name="addrType" id="addrType" value=""/>
<div id="popup_2">

	<!--//popup_title End-->		
	<div id="divTab2" class="iKEP_tab_poll">
            <ul>
           		<li><a href="#tabs-1" >구주소 검색</a></li>
           		<li><a href="#tabs-2" >새주소 검색</a></li>
            </ul>
            <div style="height:0px;"> 
                <div id="tabs-1"></div>
                <div id="tabs-2"></div>
            </div>
	</div>
	<!--popup_contents Start-->
	<div id="popup_contents_2">
	
		<!--blockDetail Start-->
	    <div class="blockDetail">
	        <table summary="new group">
	            <colgroup>
	                <col width="30%"/>
	                <col width="70%"/>
	            </colgroup>
	            <tbody>
	                <tr id="tab1">
	                    <th scope="row" style="text-align:center;">검색(동/읍/면/리)</th>
	                    <td><input type="text" name="addrName1" id="addrName1" size="20" /> <a class="button" href="#a" id="searchBtn1" name="searchBtn1"><span>검색</span></a></td>
	                </tr>
	                <tr id="tab2" style="display:none;">
	                    <th scope="row" style="text-align:center;">검색</th>
	                    <td><select name="searchSe" id="searchSe">
	                    	<option value="road" selected="selected">도로명</option>	                       
	                        <option value="dong">동(읍/면)명</option>
	                        <option value="post">우편번호</option>
	                    </select> 
	                    <input type="text" name="addrName2" id="addrName2" size="20" /> <a class="button" href="#a" id="searchBtn2" name="searchBtn2"><span>검색</span></a></td>
	                </tr>
	                
	            </tbody>
	        </table>
	    </div>
	    
	    <c:if test="${!empty param.addrName1 || !empty param.addrName2}">
	    	
				<div class="blockListTable">
				<!--Layout Start-->
				<div id="container">
					<!--List Layout Start-->
					<div id="listDiv"> 
					<table summary="">
						<caption></caption>
						<thead>
						<tr>
							<th scope="col" width="70%">
								주소
							</th>	
							<th scope="col" width="30%">
								우편번호
							</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${fn:length(zipCodeList) eq 0 }">
									<tr>
										<td colspan="2" class="emptyRecord">검색결과가 없습니다.</td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="entry" items="${zipCodeList}">
									<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine" onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
										<td>
											<a href="#" onclick="getZipCode('${fn:trim(entry.value)}','${fn:trim(entry.key)}');">${entry.key}</a>
										</td>
										<td>
											<a href="#" onclick="getZipCode('${fn:trim(entry.value)}','${fn:trim(entry.key)}');">${entry.value}</a>	
										</td>										
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
						</tbody>
					</table>
					</div>
				</div>
			</div>
	    </c:if>
	    <!--//blockDetail End-->	
	    <div class="blockButton"> 			
			<ul>	            
	            <li><a class="button" href="javascript:iKEP.closePop()" id="cancelBtn" name="cancelBtn"><span>닫기</span></a></li>&nbsp;
	        </ul>
		</div>		
		
		<!--//blockButton End-->
		<div class="blockBlank_5px"></div>	
	
	<!--//popup_contents End-->
</div>
<!--//popup End-->
</form>
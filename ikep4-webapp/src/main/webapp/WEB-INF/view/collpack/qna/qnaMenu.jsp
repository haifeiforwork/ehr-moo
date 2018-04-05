<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.qna.qnaMenu" />
<c:set var="preResource" 	value="message.collpack.qna" />

<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function(){
		
	    $jq("#leftCategoryList li:last").addClass("liLast");
	    $jq("#leftMenu").addClass("leftMenu_QnA");
	
	     // Left Menu setting
	     iKEP.setLeftMenu();
	     
	 });
	
	viewPopUpProfile = function(targetUserId){
		//var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
		//iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });

		iKEP.goProfilePopupMain(targetUserId);
	}

	function goDetail(val){
		location.href="getQna.do?qnaId="+val+"&listType=${listType}&categoryId=${categoryId}&listTab=${listTab}&qnaType=${qnaType}";
	}


	var pageIndex = 1;		//페이징 수
	function listMore(url, totalCount, param){
		
		//페이지수만큼 불렀음 호출 더이상 안함.
		var viewCount = $jq("#listFrame tr").size();
		
		if(totalCount <= viewCount){
			return;	
		}
		
		if(param && param.pageIndex){
			pageIndex = param.pageIndex;
		} else {
			pageIndex++;
		}
		
		ajaxListAction(url, totalCount, param);
	}	


	function ajaxListAction(url, totalCount, param){
		
		param = $jq.extend(param, {pageIndex:pageIndex});

		$jq("#tagResult").ajaxLoadStart(); 
		
		$jq.ajax({    
			url : url,     
			data : param,     
			type : "post",  
			dataType : "html",
			success : function(result) {   

				$jq("#tagResult").ajaxLoadComplete();

				$jq("#listFrame tr").attr("class", "");
				
				$jq("#listFrame").append(result); 
				
				var viewCount = $jq("#listFrame tr").size();
				if(totalCount <= viewCount){
					$jq('#moreText').text('<ikep4j:message pre='${preResource}' key='notMore'/>');
				}

				$jq("#listFrame tr:last").addClass("tdend");
				
			},
			error : function(event, request, settings){

				$jq("#tagResult").ajaxLoadComplete();
				
			 	alert("error");
			}
		}); 
		
	}


	function searchMore(totalCount){
		
		var f = $jq('#searchForm');
		
		var searchColumn = f.find('select').val();
		var searchWord = f.find('input[name=searchWord]').val();

		listMore("listMore.do", totalCount, {qnaType:0,searchColumn:searchColumn, searchWord:searchWord});
		
	}


	/**
	 * 롤링 이벤트
	 */
	function qnaEffect(obj, option){
		
		this.$div = $jq("#"+obj).find(' > div');
		option = $jq.extend(option, {time:"5000"});
		this.time = option.time;
		this.count = this.$div.size();
		this.setTime;
		this.su = 0;
		
		var self = this;
		
		this.start(self);
		
		this.$div.mouseover(function(){self.stop(self);});
		this.$div.mouseout(function(){self.start(self);});
	}


	qnaEffect.prototype.move = function(){
		
		if(this.count == ++this.su){
			this.su = 0;
		} 

		if(this.su < 0){
			this.su = this.count-1;
		}
		
		this.$div.hide();
		this.$div.eq(this.su).show();
		
	}

	qnaEffect.prototype.start = function(obj){
		clearInterval(obj.setTime);
		obj.setTime =  setInterval(function(){obj.move();}, obj.time);
	}

	qnaEffect.prototype.stop = function(obj){
		clearInterval(obj.setTime);
	}

	qnaEffect.prototype.back = function(){
		this.su -= 2;
		this.move();
	}

	qnaEffect.prototype.after = function(){
		this.move();
	}
//]]>	
</script>

<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<h1 class="none">Left Menu</h1>	
<h2 style="margin-bottom:10px;"><a href="main.do"><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_know_qna.gif'/>"/></span></a></h2>
<div class="blockButton_2 mb10"> 
	<a class="button_2" href="formQna.do?listType=&categoryId=" title="<ikep4j:message pre='${preUi}' key='menuAsk'/>"><span><img src="<c:url value="/base/images/icon/ic_question.png"/>" alt="<ikep4j:message pre='${preUi}' key='menuAsk'/>" /> <ikep4j:message pre='${preUi}' key='menuAsk'/></span></a>				
</div>
<div class="myqna">
	<div>
	<table width="100%">
	<tr>
		<td rowspan="2" width="40px" align="center"><img src="<c:url value="/base/images/icon/ic_per_03.gif"/>" alt="<ikep4j:message pre='${preUi}' key='menuMyQna'/>" /></td>
		<td><a href="listMyQna.do" title="<ikep4j:message pre='${preUi}' key='menuMyQna'/>"><ikep4j:message pre='${preUi}' key='menuMyQna'/>&nbsp;<span class="myqna_num">${myQnaCount}</span></a></td>
	</tr>
	<tr>
		<td><a href="listMyQna.do?listTab=1" title="<ikep4j:message pre='${preUi}' key='menuMyAnswer'/>"><ikep4j:message pre='${preUi}' key='menuMyAnswer'/>&nbsp;<span class="myqna_num">${myReplyCount}</span></a></td>
	</tr>
	</table>
	</div>
</div>		
<div class="left_fixed">		
<ul>		

	<li class="liFirst no_child <c:if test="${fn:contains(requestUrl,'listUrgentQna')}"> licurrent</c:if>" ><a href="listUrgentQna.do" title="<ikep4j:message pre='${preUi}' key='menuUrgent'/>"><ikep4j:message pre='${preUi}' key='menuUrgent'/></a></li>
	<li class="no_child <c:if test="${fn:contains(requestUrl,'listNotAdoptQna')}"> licurrent</c:if>"><a href="listNotAdoptQna.do" title="<ikep4j:message pre='${preUi}' key='menuNotAdopt'/>"><ikep4j:message pre='${preUi}' key='menuNotAdopt'/></a></li>
	<li class="no_child <c:if test="${fn:contains(requestUrl,'listBestQna')}"> licurrent</c:if>"><a href="listBestQna.do" title="<ikep4j:message pre='${preUi}' key='menuBestQna'/>"><ikep4j:message pre='${preUi}' key='menuBestQna'/></a></li>
	<li class="opened " id="leftCategoryList"><a href="#a" title="<ikep4j:message pre='${preUi}' key='menuCategory'/>"><ikep4j:message pre='${preUi}' key='menuCategory'/></a>
		<ul class="qnalist_sub">
			<c:forEach var="category" items="${categoryList}" varStatus="loopCount">
				
				<c:choose>
					<c:when test="${category.categoryId == param.categoryId}">
						<c:set var="selectedClass" value="licurrent"/>
					</c:when>
					<c:otherwise>
						<c:set var="selectedClass" value=""/>
					</c:otherwise>
				</c:choose>
				
				<li class="qnalist ${selectedClass} "><a href="listCategoryQna.do?categoryId=${category.categoryId}" title="${category.categoryName}">${category.categoryName}</a></li>
			</c:forEach>
			
				<li class="qnalist <c:if test="${fn:contains(requestUrl,'listCategoryQna') && empty(param.categoryId)}">licurrent</c:if>" ><a href="listCategoryQna.do" title="<ikep4j:message pre='${preUi}' key='cetera'/>"><ikep4j:message pre='${preUi}' key='cetera'/></a></li>
		</ul>	
	</li>
	
	<c:if test="${isAdmin == true}">
		<li class="opened"><a href="#a" title="<ikep4j:message pre='${preUi}' key='menuAdministration'/>"><ikep4j:message pre='${preUi}' key='menuAdministration'/></a>
			<ul>
				<li class="no_child <c:if test="${fn:contains(requestUrl,'listCategory.do')}"> licurrent</c:if>" ><a href="listCategory.do" title="<ikep4j:message pre='${preUi}' key='menuCategoryAdmin'/>"><ikep4j:message pre='${preUi}' key='menuCategoryAdmin'/></a></li>
				<li class="no_child liLast <c:if test="${fn:contains(requestUrl,'formPolicy.do')}"> licurrent</c:if>" ><a href="formPolicy.do" title="<ikep4j:message pre='${preUi}' key='menuBestAdmin'/>"><ikep4j:message pre='${preUi}' key='menuBestAdmin'/></a></li>
			</ul>
		</li>
	</c:if>	
</ul>
</div>

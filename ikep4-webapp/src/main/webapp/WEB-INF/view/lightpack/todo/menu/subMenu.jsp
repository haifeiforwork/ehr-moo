<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="menuPrefix">ui.lightpack.todo.subMenu</c:set>

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		// left menu setting
		iKEP.setLeftMenu();
		
		var systemCode = "${systemCode}";
		if(systemCode == "" ) {
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/todo/listTodoView.do'/>");
		} else {
			var pageURL = "<c:url value='/lightpack/todo/readTodoView.do'/>" + "?systemCode=${systemCode}&subworkCode=${subworkCode}&taskKey=${taskKey}&workerId=${workerId}";
			iKEP.iFrameMenuOnclick(pageURL);
		}
	
	});

	//todo 갯수 변경
	setCountMyTodo = function(countMyTodo) {
		//$jq('#countMyTodo').attr("innerHTML", countMyTodo);
		$jq('#countMyTodo').html(countMyTodo);
	}
})(jQuery);
//]]>
</script>

			<!--leftMenu Start-->
				<h1 class="none"></h1>	
				<h2><a href="<c:url value='/lightpack/todo/todoView.do'/>"><span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_home_todo_new.gif'/>"/></span></a></h2>	

				<div class="left_fixed">
					<ul>
						<li class="opened"><a href="#a">업무 지시</a>
							<ul>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/createTaskView.do'/>')">업무 지시 등록</a></li>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/listOrderView.do'/>')">업무 지시 내역</a></li>
							</ul>
						</li>
										
						<li class="opened"><a href="#a">나의 업무</a>
							<ul>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/createTodoView.do'/>')">나의 업무 등록</a></li>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/listTodoView.do'/>')">나의 업무 내역&nbsp;( <span id="countMyTodo" class="colorPoint">${countMyTodo}</span> )</a></li>
							</ul>
						</li>
					</ul>
				</div>
				
				<%-- 업무 관리 세분화 - 2014.09.16
				<!--blockButton_2 Start-->
				<div class="blockButton_2 mb10"> 				
					<a class="button_2" href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/createTaskView.do'/>')"><span><img src="<c:url value="/base/images/icon/ic_registration.gif"/>" width="14" height="17" alt="<ikep4j:message pre='${menuPrefix}' key='subMenu.regist'/>"/>업무 지시 등록</span></a>				
				</div>
				<!--//blockButton_2 End-->					
				
				<div class="left_fixed">	
					<ul>
						<li class="liFirst no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/listTodoView.do'/>')">나의 업무&nbsp;( <span id="countMyTodo" class="colorPoint">${countMyTodo}</span> )</a></li>
						<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/listOrderView.do'/>')">지시 내역</a></li>
						<!-- <li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/todo/updateUserSettingView.do'/>')"><ikep4j:message pre="${menuPrefix}" key="subMenu3"/></a></li> --!>
					</ul>
				</div>
				--%>	
			<!--//leftMenu End-->	
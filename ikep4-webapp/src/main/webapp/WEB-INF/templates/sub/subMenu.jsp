<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Header Page
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--leftMenu Start-->
<div id="leftMenu">
	<h1 class="none">레프트메뉴</h1>	
	<h2>Collaboration</h2>	
	<ul>
		<li class="no_child"><a href="#">공지사항</a></li>
		<li class="no_child"><a href="#">주간보고</a></li>
		<li class="no_child"><a href="#">하위Coll. 주간보고</a></li>
		<li class="no_child"><a href="#">Collaboration 일정</a></li>
		<li class="no_child"><a href="#">회원 Micro Blogging</a></li>
		<li class="licurrent"><a href="#">Mail</a>
			<ul>
				<li class="licurrent"><a href="#">Mail Box</a>
					<ul>
						<li><a href="#">Inbox</a></li>
						<li><a href="#">Sent Items</a></li>
						<li class="licurrent"><a href="#">Important Items</a></li>
						<li><a href="#">Unread Items</a></li>
						<li><a href="#">Deleted Items</a></li>
					</ul>
				</li>
				<li class="no_child"><a href="#">Mail Box</a></li>
				<li class="no_child"><a href="#">Mail Box</a></li>
				<li><a href="#">Folders</a></li>
			</ul>					
		</li>
		<li class="boxList_current"><a href="#">Collaboration</a>
			<div class="boxList_child">
				<img src="<c:url value="/base/images/common/temp_tree_2.gif"/>" alt="트리 임시이미지" />
			</div>
		</li>	
		<li class="boxList"><a href="#">Collaboration</a>
		<li class="boxList liLast"><a href="#">통합 To-do(3)</a>
			<ul>
				<li><a href="<c:url value='/lightpack/todo/todoListView.do'/>">To-do</a></li>
				<li><a href="<c:url value='/lightpack/todo/todoListView.do?tabNum=1'/>">지시한 작업</a></li>
				<li><a href="<c:url value='/lightpack/todo/taskFormView.do'/>">작업등록</a></li>
			</ul>
		</li>							
	</ul>
</div>	
<!--//leftMenu End-->
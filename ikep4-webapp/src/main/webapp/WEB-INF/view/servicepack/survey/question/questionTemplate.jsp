<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preTemplet"   value="ui.servicepack.survey.templet" />
<%-- 메시지 관련 Prefix 선언 Start --%> 
<div class="surveyLayerLTemplet">
	<div class="surveyList" id="TA0" style="display:none;">
		<span  class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label style="padding-right:10px"><input name="" type="radio" title="" class="radio valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></label></li>
		</ul>
	</div>
	
	<div class="surveyList"  id="TA1" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label><input name="" type="radio" title="" class="radio valign_middle mr5" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span>&nbsp;<span class="fileId none"></span><span class="attFile none"></span><!--img src="<c:url value='/support/fileupload/downloadFile.do?fileId=xxxxxxx'/>"  width="50" height="50"  alt="image" name="fileuploadBtn"/--> </label></li>
		</ul>
	</div>	
	
	<div class="surveyList"  id="TA2" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label><input name="" type="radio" title="" class="radio valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> </label></li>
			<li  class="bg_none"><label><input name="" type="radio" title="" class="radio valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> <input name="" type="text" class="inputbox valign_top w90"   title="" /> </label></li>
		</ul>
		
	</div>	
	<div class="surveyList" id="TA3" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label><input name="" type="radio" title="" class="radio valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> </label></li>
			<li  class="bg_none"><label><input name="" type="radio" title="" class="radio valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> <textarea name="" class="inputbox valign_top w90" title="" rows="4"></textarea> </label></li>
		</ul>
	</div>
	
	<div class="surveyList" id="TA4" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label style="padding-right:10px"><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> </label></li>
		</ul>
	</div>
	
	<div class="surveyList"  id="TA5" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label><input name="" type="checkbox" title="" class="checkbox valign_middle mr5" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span>&nbsp;<span class="fileId none"></span><span class="attFile none"></span><!--img src="<c:url value='/support/fileupload/downloadFile.do?fileId=xxxxxxx'/>"  width="50" height="50"  alt="image"  name="fileuploadBtn"/--> </label></li>
		</ul>
	</div>	
	
	<div class="surveyList"  id="TA6" style="display:none;">

		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> </label></li>
			<li  class="bg_none"><label><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> <input name="" type="text" class="inputbox valign_top w90"   title="" /> </label></li>
		</ul>
	</div>	
	
	<div class="surveyList" id="TA7" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
			<li  class="bg_none"><label><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> </label></li>
			<li  class="bg_none"><label><input name="" type="checkbox" title="" class="checkbox valign_middle" value="" /><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span> <textarea name="" class="inputbox valign_top w90" title="" rows="4"></textarea> </label></li>
		</ul>
	</div>
	
	<div class="surveyList"  id="TB0" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul>
			<li  class="bg_none"><input name="" type="text" class="inputbox valign_top"   title="" size="50" /><span class="answerTitle" style="display:none;"></span></li>
		</ul>
	</div>	
	
	<div class="surveyList"   id="TB1" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul>
			<li class="bg_none"><span style="display:inline-block; min-width:80px; padding-right:10px" class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span><input name="" type="text" class="inputbox valign_top"   title="" size="30" /></li>
		</ul>
	</div>
	
	<div class="surveyList"   id="TB2" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul>
			<li  class="bg_none"><input name="" type="text" class="inputbox valign_top"   title="" size="30" /><span class="answerTitle" style="display:none;"></span> </li>
		</ul>
	</div>

	
	<div class="surveyList"   id="TB3" style="display:none;">
		<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
		<ul>
			<li  class="bg_none"><textarea name="" class="inputbox valign_top w90"   title="" rows="4"></textarea><span class="answerTitle" style="display:none;"></span></li>
		</ul>
	</div>	
	
	<div class="surveyList"    id="TC0" style="display:none;">
		<div class="blockDetail">
			<table summary="" style="table-layout:auto;">
				<caption></caption>
				<thead>
					<tr>
						<th colspan="2" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td width="*"><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td width="20px;" class="textCenter">
							<select title="">
								<option>1</option>
								<option>2</option>
								<option>3</option>
							</select>														
						</td>
					</tr>
				</tbody>

			</table>
		</div>
	</div>
	
	<div class="surveyList"     id="TD0" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
						<th width="135" scope="col">
							<ul class="surveyList_num view">
								<li style="width:45px"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li style="width:45px"><div class="scale2Title"><ikep4j:message pre="${preTemplet}" key="scale2Title" /></div></li>
								<li style="width:45px"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale1" />" class="radio valign_middle" value="" /></li>
								<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale2" />" class="radio valign_middle" value="" /></li>
								<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale3" />" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>	
	
	<div class="surveyList"   id="TD1" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span> </th>
						<th width="135" scope="col">
							<ul class="surveyList_num view">
								<li  class="bg_none"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale2Title"><ikep4j:message pre="${preTemplet}" key="scale2Title" /></div></li>
								<li  class="bg_none"></li>
								<li  class="bg_none"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>

						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li  class="bg_none"><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>																										
				</tbody>
			</table>
		</div>
	</div>
											
	<div class="surveyList"   id="TD2" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
						<th width="162" scope="col">
							<ul class="surveyList_num view">
								<li  class="bg_none"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li  class="bg_none"><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>																										
				</tbody>
			</table>
		</div>
	</div>

	
	<div class="surveyList"   id="TD3" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
						<th width="182" scope="col">
							<ul class="surveyList_num view">
								<li  class="bg_none"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale2Title"><ikep4j:message pre="${preTemplet}" key="scale2Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li  class="bg_none"><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="7" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>																										
				</tbody>
			</table>
		</div>
	</div>
			
	<div class="surveyList"   id="TD4" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" rowspan="2" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
						<th width="50" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
						<th width="135" rowspan="2" scope="col">
							<ul class="surveyList_num view">
								<li  class="bg_none"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale2Title"><ikep4j:message pre="${preTemplet}" key="scale2Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>
						</th>
					</tr>
					<tr>
						<th scope="col" style="width:20px;">O</th>
						<th scope="col" style="width:20px;">X</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter"><input name="" type="radio" title="O" class="radio valign_middle" value="" /></td>
						<td class="textCenter"><input name="" type="radio" title="X" class="radio valign_middle" value="" /></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li  class="bg_none"><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>																										
				</tbody>
			</table>
		</div>
	</div>
													
	<div class="surveyList"   id="TD5" style="display:none;">

		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" rowspan="2" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
						<th width="50" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
						<th width="185" rowspan="2" scope="col">
							<ul class="surveyList_num view">
								<li  class="bg_none"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale2Title"><ikep4j:message pre="${preTemplet}" key="scale2Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>
						</th>
					</tr>
					<tr>
						<th scope="col" style="width:20px;">O</th>
						<th scope="col" style="width:20px;">X</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter"><input name="" type="radio" title="O" class="radio valign_middle" value="" /></td>
						<td class="textCenter"><input name="" type="radio" title="X" class="radio valign_middle" value="" /></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li  class="bg_none"><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="7" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>																										
				</tbody>
			</table>
		</div>
	</div>
	

						
	<div class="surveyList"   id="TD6" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="*" scope="col"><span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span></th>
						<th width="20" scope="col">N/A</th>
						<th width="185" scope="col">
							<ul class="surveyList_num view">
								<li  class="bg_none"><div class="scale1Title"><ikep4j:message pre="${preTemplet}" key="scale1Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale2Title"><ikep4j:message pre="${preTemplet}" key="scale2Title" /></div></li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none">&nbsp;</li>
								<li  class="bg_none"><div class="scale3Title"><ikep4j:message pre="${preTemplet}" key="scale3Title" /></div></li>
							</ul>
						</th>
					</tr>

				</thead>
				<tbody>
					<tr>
						<td><span class="answerTitle"><ikep4j:message pre="${preTemplet}" key="answerTitle" /></span></td>
						<td class="textCenter"><input name="" type="radio" title="N/A" class="radio valign_middle" value="" /></td>
						<td class="textCenter">
							<ul class="surveyList_num view">
								<li  class="bg_none"><input name="" type="radio" title="1" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="2" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="3" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="4" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="5" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="6" class="radio valign_middle" value="" /></li>
								<li  class="bg_none"><input name="" type="radio" title="7" class="radio valign_middle" value="" /></li>
							</ul>											
						</td>
					</tr>																										
				</tbody>
			</table>
		</div>

	</div>	
	
	<div class="surveyList"   id="TD7" style="display:none;">
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th width="12%" scope="col">수준</th>
						<th width="*" scope="col">수준별 특성</th>
						<th width="12%" scope="col">요구역량</th>
						<th width="12%" scope="col">본인역량</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="textCenter">1수준</td>
						<td>
							발표내용과 적합한 실제사례를 수집하여 활용한다.<br />
							프리젠테이션시 내용에 대한 전문성을 확보하여 자신감있게 진행한다.
						</td>
						<td class="textCenter"></td>
						<td class="textCenter"><input name="" type="radio" title="" class="radio valign_middle" value="" /></td>
					</tr>																																							
				</tbody>
			</table>
		</div>
	</div>									
</div>

<div id="questionGroupTemplate" style="display:none;"> 
  <div class="questionGroupAllBlock">		
	<h3 class="surveyBox_1" style="padding-left:20px; margin-bottom:1px;">
		<span class="questionGroupTitle" style="color:black;"><ikep4j:message pre="${preTemplet}" key="questionGroupTitle" /></span>
		<span  style="color:black;" class="questionGroupContents display_block none"><ikep4j:message pre="${preTemplet}" key="questionGroupContents" /></span>	
		<span class="surveyBox_resize">
			<span class="ic_rm"><a href="#a"  name="removequestionGroup" class="bg_none" style="padding:0;"><img src="<c:url value='/base/images/icon/ic_cir_minus.gif'/>" alt="minus" /></a></span>
		</span>	
	</h3>
	<div class="questionGroupBlock mb10" id="questionGroupBlock_" style="border:1px solid #bebebe;">
		<!--blockButton_3 Start-->
		<div class="blockButton_3"> 
			<a class="button_3" name="addQuestionButton" href="#a"><span><img src="<c:url value='/base/images/icon/ic_cir_plus_2.gif'/>" style="vertical-align:middle; padding-bottom:2px;" alt="" />&nbsp;&nbsp;<ikep4j:message pre='${preButton}' key='addQuestion'/></span></a>				
		</div>
		<!--//blockButton_3 End-->
	</div>
  </div>	
</div> 

<div id="questionsTemplate" style="display:none;"> 
	<div class="surveyBox_2" style="min-height:100px;">
		<table summary="" style="min-height:100px;">
			<caption></caption>
			<tbody>
			<tr>
				<th scope="row" height="100">
					<div><label  class="qnaSeq">Q1<input name="" class="checkbox" title="q1" type="checkbox" value="" /></label></div>
					<div class="ic_arb none">
						<a href="#a" name="moveBeforItems"><img src="<c:url value='/base/images/icon/ic_arb_up.gif'/>" alt="up" /></a>
						<a href="#a" name="moveAfterItems"><img src="<c:url value='/base/images/icon/ic_arb_down.gif'/>" alt="down" /></a>
					</div>
				</th>
				<td>
					<div class="surveyList surveyListBlock"></div>	
				</td>
			</tr>
			</tbody>
		</table>	
		<div class="surveyBox_resize  none">
			<div class="ic_rt"><a href="#a"  name="insertBeforItems"><img src="<c:url value='/base/images/icon/ic_cir_plus.gif'/>" alt="plus" /></a></div>
			<div class="ic_rm"><a href="#a"  name="removeItems"><img src="<c:url value='/base/images/icon/ic_cir_minus.gif'/>" alt="minus" /></a></div>
			<div class="ic_rb"><a href="#a"  name="insertAfterItems"><img src="<c:url value='/base/images/icon/ic_cir_plus.gif'/>" alt="plus" /></a></div>												
		</div>				
	</div>
</div>

<div id="answerOptionTemplate" style="display:none;"> 
	<input name="" class="radio valign_middle" title="radio" type="radio" value="" />
	<input name="" class="checkbox" title="checkbox" type="checkbox" value="" />
</div>

<div id="a0Template" style="display:none;"> 
	<span class="questionTitle"><ikep4j:message pre="${preTemplet}" key="questionTitle" /></span>
	<ul class="answerBlock" >
		<li></li>
	</ul>
</div>

<div id="d0Template" style="display:none;">
	<div class="blockDetail">
		<table summary=""   style="table-layout:auto;">
			<caption></caption>
			<thead class="answerBlockHeader">
				<tr>
					<th></th>
				</tr>
			</thead>
			<tbody class="answerBlock">
				<tr>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>	


<!--layer start-->
<!--div class="process_layer" style="display:none;top:180px; left:15px; z-index:99;" id="inputTextAreaLayer">
	<div class="process_layer_t">
		Edit
		<a href="#a"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="닫기" /></a>
	</div>
	<div>
		<table summary="메시지 전달">
			<caption></caption>
			<tbody>
				<tr>
					<td colspan="2" scope="row">
					<span class="colorPoint">*저장은 저장버튼 또는 마우스를 다른곳으로 옮기셔도 가능합니다.</span>
					</td>
				</tr>
				<tr>
					<td>
						<textarea name="inputAllValue"  id="inputAllValue" class="inputbox valign_top"   title="" style="width:100%;"  rows="4"></textarea>																								
					</td>
				</tr>		
			</tbody>
		</table>	
	</div>
</div-->		
<!--layer end-->




	
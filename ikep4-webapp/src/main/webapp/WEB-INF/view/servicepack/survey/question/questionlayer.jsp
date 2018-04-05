<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="preTemplet"   value="ui.servicepack.survey.templet" />
<!--surveyBlock_r Start-->
				<div class="surveyBlock_r" onclick="blockShow()">
					<div id="rightAllBlock" style="display:none;">
					
						<div class="survey_t">
							<h3><ikep4j:message pre="${preTemplet}" key="questionType" /></h3>
						</div>
						
						<div class="survey_sel">
							<div class="selected" id="selectQuestionType"><a href="#a"><ikep4j:message key='ui.servicepack.survey.common.selected' /></a></div>

							
							<!--survey_sel_layer Start-->
							<div class="survey_sel_layer none" id="surveySelLayerTop"> <!--레이어 삭제시 다음과 같이 none 클래스 삽입 class="survey_sel_layer none"-->
								<div class="survey_layer_l">
									<div class="survey_t mb10">
										<h3><ikep4j:message pre="${preTemplet}" key="example" /></h3>
									</div>
									
									<div class="surveyList" id="A0" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="youalias" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></p>

										<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="pjobs" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="djobs" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="kjobs" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="cjobs" /> </label></li>

											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="bjobs" /> </label></li>
										</ul>
									</div>
									
									<div class="surveyList"  id="A1" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="likeman" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 3]</span></p>
										<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="radio" title="" class="radio" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_1.gif'/>" alt="image" /> </label></li>

											<li><label><input name="" type="radio" title="" class="radio" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_2.gif'/>" alt="image" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_3.gif'/>" alt="image" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_4.gif'/>" alt="image" /> </label></li>
										</ul>
									</div>	
									
									<div class="surveyList"  id="A2" style="display:none;">

										<p><ikep4j:message pre="${preTemplet}" key="pase" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 1]</span></p>
										<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="improve" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="excelent" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="etc" /> <input name="" type="text" class="inputbox" title="" size="80" /> </label></li>

										</ul>
									</div>	
									<div class="surveyList" id="A3" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="pase" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 1]</span></p>
										<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="improve" /> </label></li>
											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="excelent" /> </label></li>

											<li><label><input name="" type="radio" title="" class="radio" value="" /><ikep4j:message pre="${preTemplet}" key="etc" /> <textarea name="" class="inputbox valign_top" title="" cols="80" rows="4"></textarea> </label></li>
										</ul>
									</div>
									
									<div class="surveyList" id="A4" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="youalias" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 3]</span></p>

										<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="pjobs" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="djobs" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="kjobs" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="cjobs" /> </label></li>

											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="bjobs" /> </label></li>
										</ul>
									</div>
									
									<div class="surveyList"  id="A5" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="likeman" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 3]</span></p>
										<ul class="inline"><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_1.gif'/>" alt="image" /> </label></li>

											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_2.gif'/>" alt="image" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_3.gif'/>" alt="image" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" />&nbsp;<img src="<c:url value='/base/images/common/temp_img_50x50_4.gif'/>" alt="image" /> </label></li>
										</ul>
									</div>	
									
									<div class="surveyList"  id="A6" style="display:none;">

										<p><ikep4j:message pre="${preTemplet}" key="pase" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 1]</span></p>
										<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="improve" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="excelent" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="etc" /> <input name="" type="text" class="inputbox" title="" size="80" /> </label></li>

										</ul>
									</div>	
									
									<div class="surveyList" id="A7" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="pase" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />][<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 1]</span></p>
										<ul class=""><!--inline 삭제 또는 삽입 여부에 따라 display 방식 변경-->
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="improve" /> </label></li>
											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="excelent" /> </label></li>

											<li><label><input name="" type="checkbox" title="" class="checkbox" value="" /><ikep4j:message pre="${preTemplet}" key="etc" /> <textarea name="" class="inputbox valign_top" title="" cols="80" rows="4"></textarea> </label></li>
										</ul>
									</div>
									
									<div class="surveyList"  id="B0" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="jobs" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></p>
										<ul>
											<li><input name="" type="text" class="inputbox" title="" size="50" /></li>
										</ul>
									</div>	
									
									<div class="surveyList"   id="B1" style="display:none;">
										<p>Please fill in the following information <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></p>
										<ul>
											<li><span style="display:inline-block; width:100px;">First Name</span><input name="" type="text" class="inputbox" title="" size="30" /></li>
											<li><span style="display:inline-block; width:100px;">Last Name</span><input name="" type="text" class="inputbox" title="" size="30" /></li>

											<li><span style="display:inline-block; width:100px;">Email</span><input name="" type="text" class="inputbox" title="" size="30" /></li>
											<li><span style="display:inline-block; width:100px;">Address</span><input name="" type="text" class="inputbox" title="" size="30" /></li>
											<li><span style="display:inline-block; width:100px;">Zipcode</span><input name="" type="text" class="inputbox" title="" size="30" /></li>
										</ul>
									</div>
									
									<div class="surveyList"   id="B2" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="future" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></p>

										<ul>
											<li><input name="" type="text" class="inputbox" title="" size="30" /> <span class="surveytxt">[TextBox1]</span></li>
											<li><input name="" type="text" class="inputbox" title="" size="30" /> <span class="surveytxt">[TextBox2]</span></li>
											<li><input name="" type="text" class="inputbox" title="" size="30" /> <span class="surveytxt">[TextBox3]</span></li>
										</ul>
									</div>

									
									<div class="surveyList"   id="B3" style="display:none;">
										<p><ikep4j:message pre="${preTemplet}" key="etc" /> <ikep4j:message pre="${preTemplet}" key="etcimport" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></p>
										<ul>
											<li><textarea name="" class="inputbox" title="" cols="100" rows="4"></textarea></li>
										</ul>
									</div>	
									
									<div class="surveyList"    id="C0" style="display:none;">
										<div class="blockDetail">

											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th colspan="2" scope="col"><ikep4j:message pre="${preTemplet}" key="choiceimport" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
													</tr>
												</thead>
												<tbody>

													<tr>
														<td width="*">Passion </td>
														<td width="10%" class="textCenter">
															<select title="">
																<option>1</option>
																<option>2</option>
																<option>3</option>

															</select>														
														</td>
													</tr>
													<tr>
														<td>Innovation </td>
														<td class="textCenter">
															<select title="">
																<option>1</option>

																<option>2</option>
																<option>3</option>
															</select>														
														</td>
													</tr>
													<tr>
														<td>Respect </td>

														<td class="textCenter">
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
									
									<div class="surveyList"     id="D0" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="*" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="135" scope="col">
															<ul class="surveyList_num">
																<li style="width:45px"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>
																<li style="width:45px"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale2" />)]</div><ikep4j:message pre="${preTemplet}" key="scale2Title" /><div>2</div></li>

																<li style="width:45px"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>3</div></li>
															</ul>
														</th>
													</tr>
												</thead>
												<tbody>
													<tr>

														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1] </span></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale1" />" class="radio" value="" /></li>
																<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale2" />" class="radio" value="" /></li>
																<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale3" />" class="radio" value="" /></li>
															</ul>											
														</td>

													</tr>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale1" />" class="radio" value="" /></li>
																<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale2" />" class="radio" value="" /></li>
																<li style="width:45px"><input name="" type="radio" title="<ikep4j:message pre="${preTemplet}" key="scale3" />" class="radio" value="" /></li>

															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>
									</div>	
									
									<div class="surveyList"   id="D1" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="*" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="155" scope="col">
															<ul class="surveyList_num">
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>

																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>2</div></li>
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale2" />)]</div><ikep4j:message pre="${preTemplet}" key="scale2Title" /><div>3</div></li>
																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>4</div></li>
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>5</div></li>
															</ul>

														</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1]</span></td>
														<td class="textCenter">
															<ul class="surveyList_num">

																<li><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li><input name="" type="radio" title="5" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>
													<tr>

														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li><input name="" type="radio" title="5" class="radio" value="" /></li>

															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>
									</div>
																			
									<div class="surveyList"   id="D2" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">

												<caption></caption>
												<thead>
													<tr>
														<th width="*" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="182" scope="col">
															<ul class="surveyList_num">
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>

																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>2</div></li>
																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>3</div></li>
																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>4</div></li>
																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>5</div></li>
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>6</div></li>

															</ul>
														</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1]</span></td>
														<td class="textCenter">

															<ul class="surveyList_num">
																<li><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li><input name="" type="radio" title="6" class="radio" value="" /></li>
															</ul>											
														</td>

													</tr>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li><input name="" type="radio" title="3" class="radio" value="" /></li>

																<li><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li><input name="" type="radio" title="6" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>
									</div>

									
									<div class="surveyList"   id="D3" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="*" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="192" scope="col">
															<ul class="surveyList_num">
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>2</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>3</div></li>
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale2" />)]</div><ikep4j:message pre="${preTemplet}" key="scale2Title" /><div>4</div></li>

																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>5</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>6</div></li>
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>7</div></li>
															</ul>
														</th>
													</tr>

												</thead>
												<tbody>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1]</span></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:25px;"><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="2" class="radio" value="" /></li>

																<li style="width:25px;"><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="6" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>
													<tr>

														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:25px;"><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="5" class="radio" value="" /></li>

																<li style="width:25px;"><input name="" type="radio" title="6" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>
									</div>
														
									<div class="surveyList"   id="D4" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="*" rowspan="2" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="40" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>
														<th width="155" rowspan="2" scope="col">
															<ul class="surveyList_num">
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>
																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>3</div></li>
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale2" />)]</div><ikep4j:message pre="${preTemplet}" key="scale2Title" /><div>4</div></li>

																<li><div class="surveytxt">&nbsp;</div>&nbsp;<div>6</div></li>
																<li><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>7</div></li>
															</ul>
														</th>
													</tr>
													<tr>
														<th scope="col">O</th>
														<th scope="col">X</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1]</span></td>

														<td class="textCenter"><input name="" type="radio" title="O" class="radio" value="" /></td>
														<td class="textCenter"><input name="" type="radio" title="X" class="radio" value="" /></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter"><input name="" type="radio" title="O" class="radio" value="" /></td>

														<td class="textCenter"><input name="" type="radio" title="X" class="radio" value="" /></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li><input name="" type="radio" title="5" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>
									</div>
																				
									<div class="surveyList"   id="D5" style="display:none;">

										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="*" rowspan="2" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="40" colspan="2" scope="col"><ikep4j:message key='ui.servicepack.survey.common.favorit' /></th>

														<th width="192" rowspan="2" scope="col">
															<ul class="surveyList_num">
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>2</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>3</div></li>
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale2" />)]</div><ikep4j:message pre="${preTemplet}" key="scale2Title" /><div>4</div></li>

																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>5</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>6</div></li>
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>7</div></li>
															</ul>
														</th>
													</tr>

													<tr>
														<th scope="col">O</th>
														<th scope="col">X</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1]</span></td>

														<td class="textCenter"><input name="" type="radio" title="O" class="radio" value="" /></td>
														<td class="textCenter"><input name="" type="radio" title="X" class="radio" value="" /></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:25px;"><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="5" class="radio" value="" /></li>

																<li style="width:25px;"><input name="" type="radio" title="6" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter"><input name="" type="radio" title="O" class="radio" value="" /></td>

														<td class="textCenter"><input name="" type="radio" title="X" class="radio" value="" /></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:25px;"><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="6" class="radio" value="" /></li>

																<li style="width:25px;"><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>
									</div>
									
									
														
									<div class="surveyList"   id="D6" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="*" scope="col"><ikep4j:message pre="${preTemplet}" key="answering" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />]</span></th>
														<th width="20" scope="col">N/A</th>
														<th width="192" scope="col">

															<ul class="surveyList_num">
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale1" />)]</div><ikep4j:message pre="${preTemplet}" key="scale1Title" /><div>1</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>2</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>3</div></li>
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale2" />)]</div><ikep4j:message pre="${preTemplet}" key="scale2Title" /><div>4</div></li>

																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>5</div></li>
																<li style="width:25px;"><div class="surveytxt">&nbsp;</div>&nbsp;<div>6</div></li>
																<li style="width:25px;"><div class="surveytxt">[(<ikep4j:message pre="${preTemplet}" key="scale3" />)]</div><ikep4j:message pre="${preTemplet}" key="scale3Title" /><div>7</div></li>
															</ul>
														</th>
													</tr>

												</thead>
												<tbody>
													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="global" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />1]</span></td>
														<td class="textCenter"><input name="" type="radio" title="N/A" class="radio" value="" /></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:25px;"><input name="" type="radio" title="1" class="radio" value="" /></li>

																<li style="width:25px;"><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="3" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="6" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>

													<tr>
														<td><ikep4j:message pre="${preTemplet}" key="office" /> <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="q" />2]</span></td>
														<td class="textCenter"><input name="" type="radio" title="N/A" class="radio" value="" /></td>
														<td class="textCenter">
															<ul class="surveyList_num">
																<li style="width:25px;"><input name="" type="radio" title="1" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="2" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="3" class="radio" value="" /></li>

																<li style="width:25px;"><input name="" type="radio" title="4" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="5" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="6" class="radio" value="" /></li>
																<li style="width:25px;"><input name="" type="radio" title="7" class="radio" value="" /></li>
															</ul>											
														</td>
													</tr>																										
												</tbody>
											</table>
										</div>

									</div>	
									
									<div class="surveyList"   id="D7" style="display:none;">
										<div class="blockDetail">
											<table summary=""  style="table-layout:auto;">
												<caption></caption>
												<thead>
													<tr>
														<th width="12%" scope="col">수준</th>
														<th width="*" scope="col">수준별 특성 <span class="surveytxt">[<ikep4j:message pre="${preTemplet}" key="question" />] [<ikep4j:message pre="${preTemplet}" key="rowanswer" /> : 3]</span></th>

														<th width="12%" scope="col">요구역량</th>
														<th width="12%" scope="col">본인역량</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td class="textCenter">3수준</td>

														<td>프리젠테이션시 다양한 프로젝트 수행경험을 바탕으로 전문성을 확보상며 자신감있게 진행한다.</td>
														<td class="textCenter"></td>
														<td class="textCenter"><input name="" type="radio" title="" class="radio" value="" /></td>
													</tr>
													<tr>
														<td class="textCenter">2수준</td>
														<td>다양한 경로를 통해 발표내용에 적합한 실제사례를 발굴상여 적절히 활용한다.</td>

														<td class="textCenter"></td>
														<td class="textCenter"><input name="" type="radio" title="" class="radio" value="" /></td>
													</tr>
													<tr>
														<td class="textCenter">1수준</td>
														<td>
															발표내용과 적합한 실제사례를 수집상여 활용한다.<br />
															프리젠테이션시 내용에 대한 전문성을 확보상여 자신감있게 진행한다.
														</td>

														<td class="textCenter"></td>
														<td class="textCenter"><input name="" type="radio" title="" class="radio" value="" /></td>
													</tr>																																							
												</tbody>
											</table>
										</div>
									</div>									
																																																																																																																				
								</div>
								
								<div class="survey_layer_r">
									<div class="survey_t mb10">
										<h3><ikep4j:message pre="${preTemplet}" key="questionType" /></h3>
									</div>
									<div id="accordion" class="surveyAccordion">
										<h3><a><ikep4j:message pre="${preTemplet}" key="questionType.A" /></a></h3>
										<div>
											<ul>
												<li><a href="#a" name="selectTypeButton" id="PA0"><ikep4j:message pre="${preTemplet}" key="questionType.A0" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA1"><ikep4j:message pre="${preTemplet}" key="questionType.A1" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA2"><ikep4j:message pre="${preTemplet}" key="questionType.A2" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA3"><ikep4j:message pre="${preTemplet}" key="questionType.A3" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA4"><ikep4j:message pre="${preTemplet}" key="questionType.A4" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA5"><ikep4j:message pre="${preTemplet}" key="questionType.A5" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA6"><ikep4j:message pre="${preTemplet}" key="questionType.A6" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PA7"><ikep4j:message pre="${preTemplet}" key="questionType.A7" /></a></li>
											</ul>
										</div>	
										<h3><a><ikep4j:message pre="${preTemplet}" key="questionType.B" /></a></h3>
										<div>
											<ul>
												<li><a href="#a" name="selectTypeButton" id="PB0"><ikep4j:message pre="${preTemplet}" key="questionType.B0" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PB1"><ikep4j:message pre="${preTemplet}" key="questionType.B1" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PB2"><ikep4j:message pre="${preTemplet}" key="questionType.B2" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PB3"><ikep4j:message pre="${preTemplet}" key="questionType.B3" /></a></li>

											</ul>
										</div>
										<h3><a><ikep4j:message pre="${preTemplet}" key="questionType.C" /></a></h3>
										<div>
											<ul>
												<li><a href="#a" name="selectTypeButton" id="PC0"><ikep4j:message pre="${preTemplet}" key="questionType.C0" /></a></li>

											</ul>
										</div>
										<h3><a><ikep4j:message pre="${preTemplet}" key="questionType.D" /></a></h3>
										<div>
											<ul>
												<li><a href="#a" name="selectTypeButton" id="PD0"><ikep4j:message pre="${preTemplet}" key="questionType.D0" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PD1"><ikep4j:message pre="${preTemplet}" key="questionType.D1" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PD2"><ikep4j:message pre="${preTemplet}" key="questionType.D2" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PD3"><ikep4j:message pre="${preTemplet}" key="questionType.D3" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PD4"><ikep4j:message pre="${preTemplet}" key="questionType.D4" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PD5"><ikep4j:message pre="${preTemplet}" key="questionType.D5" /></a></li>
												<li><a href="#a" name="selectTypeButton" id="PD6"><ikep4j:message pre="${preTemplet}" key="questionType.D6" /></a></li>
											</ul>
										</div>
									</div>									
								</div>
							</div>
							<!--//survey_sel_layer End-->
						</div>
						<div class="dotline_1"></div>

						<div class="survey_t"  id="answerRowCntLayer">
							<h3><ikep4j:message pre="${preTemplet}" key="rowCount" /></h3>
							<div class="survey_num">
								<a href="#a" id="answerRowCntMinus"><img src="<c:url value='/base/images/icon/ic_r_minus.gif'/>" alt="minus" /></a>
								<input name="answerRowCnt"  id="answerRowCnt" type="text" class="inputbox textCenter" title="<ikep4j:message pre='${preSearch}' key='searchCondition' />" value="4" size="3" readonly="readonly"/>
								<a href="#a"  id="answerRowCntPlus"><img src="<c:url value='/base/images/icon/ic_r_plus.gif'/>" alt="plus" /></a>
							</div>
							<div class="blockBlank_20px"></div>
						</div>
						

						
						<div class="survey_t"  id="statusQuestionTypeLayer">
							<h3><ikep4j:message pre="${preTemplet}" key="answerType" /></h3>
							<div class="survey_num">
								<p><label><input name="statusQuestionType" type="radio" class="radio" value="S" checked="checked" title=""/><ikep4j:message pre="${preTemplet}" key="answerType.s" /></label></p>
								<p><label><input name="statusQuestionType" type="radio" class="radio" value="D"  title=""/><ikep4j:message pre="${preTemplet}" key="answerType.m" /></label></p>
							</div>
							<div class="blockBlank_20px"></div>
						</div>	
						
						
						<div class="survey_t"  id="statusRequiredAnswerLayer">
							<h3><ikep4j:message pre="${preTemplet}" key="requiredAnswer" /></h3>
							<p><label><input name="statusRequiredAnswer" type="radio" class="radio" value="0"  checked="checked" title=""/><ikep4j:message pre="${preTemplet}" key="requiredAnswer.y" /></label></p>
							<p><label><input name="statusRequiredAnswer" type="radio" class="radio" value="1"  title=""/><ikep4j:message pre="${preTemplet}" key="requiredAnswer.n" /></label></p>
							<div class="blockBlank_20px"></div>
						</div>
						
												
						<div class="survey_t"  id="statusDisplayTypeLayer">
							<h3><ikep4j:message pre="${preTemplet}" key="displayType" /></h3>
							<p><label><input name="statusDisplayType" type="radio" class="radio" value="0"  checked="checked" title=""/><ikep4j:message pre="${preTemplet}" key="displayType.h" /></label></p>
							<p><label><input name="statusDisplayType" type="radio" class="radio" value="1"  title=""/><ikep4j:message pre="${preTemplet}" key="displayType.v" /></label></p>
							<p><label><input name="statusDisplayType" type="radio" class="radio" value="2"  title=""/><ikep4j:message pre="${preTemplet}" key="displayType.c" /></label></p>
							<div class="blockBlank_20px"></div>
						</div>
						
						
						<div class="survey_t" id="columnCountLayer">
							<h3><ikep4j:message pre="${preTemplet}" key="columnCount" /></h3>
							<div class="survey_num">
								<a href="#a" id="columnCountMinus"><img src="<c:url value='/base/images/icon/ic_r_minus.gif'/>" alt="minus" /></a>
								<input name="statusColumnCount" id="statusColumnCount" type="text" class="inputbox textCenter" title="<ikep4j:message pre='${preSearch}' key='searchCondition' />" value="1" size="3" readonly="readonly"/>
								<a href="#a" id="columnCountPlus"><img src="<c:url value='/base/images/icon/ic_r_plus.gif'/>" alt="plus" /></a>
							</div>
						</div>				
						<div class="dotline_1"></div>
					</div>

					<div class="blockButton_3 mb5"> 
						<a class="button_3 normal" href="#a" id="previewButton"><span><ikep4j:message pre="${preTemplet}" key="preview" /></span></a>				
					</div>
					<div class="blockButton_3 mb5"> 
						<a class="button_3 normal" href="<c:url value='/servicepack/survey/surveyList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a>				
					</div>												
									
				</div>
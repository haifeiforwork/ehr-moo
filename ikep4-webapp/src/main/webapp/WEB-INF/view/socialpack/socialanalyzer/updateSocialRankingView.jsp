<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.socialpack.socialanalyzer.updateSocialRankingView</c:set>
<c:set var="messagePrefix">ui.socialpack.socialanalyzer.message</c:set>
<c:set var="buttonPrefix">ui.socialpack.socialanalyzer.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
(function($) {
	$jq(document).ready(function(){
		//저장 클릭
		$jq("#saveButton").click(function() {
			$("#form").trigger("submit");
		});

		//validation
		var validOptions = {
			rules : {
				typeWeightI : {required:true, range:[0, 100], digits:true},
				typeWeightF : {required:true, range:[0, 100], digits:true},
				validMonthI : {required:true, range:[1, 12], digits:true},
				validMonthF : {required:true, range:[1, 12], digits:true},
				iFollowerWeight      : {required:true, range:[0, 1000], digits:true},
				iTweetWeight         : {required:true, range:[0, 1000], digits:true},
				iRetweetWeight       : {required:true, range:[0, 1000], digits:true},
				iBlogVisitWeight     : {required:true, range:[0, 1000], digits:true},
				iProfileVisitWeight  : {required:true, range:[0, 1000], digits:true},
				iMaxValue            : {required:true, range:[0, 99999], digits:true},
				fFollowerWeight      : {required:true, range:[0, 1000], digits:true},
				fFollowingWeight     : {required:true, range:[0, 1000], digits:true},
				fBothFollowingWeight : {required:true, range:[0, 1000], digits:true},
				fMblogGroupWeight    : {required:true, range:[0, 1000], digits:true},
				fGuestbookWeight     : {required:true, range:[0, 1000], digits:true},
				fMaxValue            : {required:true, range:[0, 99999], digits:true}
			},
			messages : {
				typeWeightI : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_100'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				typeWeightF : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_100'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				validMonthI : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.1_12'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"},
				validMonthF : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.1_12'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"},
				iFollowerWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				iTweetWeight         : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				iRetweetWeight       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				iBlogVisitWeight     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				iProfileVisitWeight  : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				iMaxValue            : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_99999'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"},
				fFollowerWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				fFollowingWeight     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				fBothFollowingWeight : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				fMblogGroupWeight    : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				fGuestbookWeight     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				fMaxValue            : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_99999'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"}
			},
			notice : {
				typeWeightI : {message:"<ikep4j:message key='Notice.socialityType.typeWeight'/>"},
				typeWeightF : {message:"<ikep4j:message key='Notice.socialityType.typeWeight'/>"},
				validMonthI : {message:"<ikep4j:message key='Notice.socialityType.validMonth'/>", direction:"left"},
				validMonthF : {message:"<ikep4j:message key='Notice.socialityType.validMonth'/>", direction:"left"},
				iFollowerWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				iTweetWeight         : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				iRetweetWeight       : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				iBlogVisitWeight     : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				iProfileVisitWeight  : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				iMaxValue            : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>", direction:"left"},
				fFollowerWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fFollowingWeight     : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fBothFollowingWeight : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fMblogGroupWeight    : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fGuestbookWeight     : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fMaxValue            : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>", direction:"left"}				
			},
			submitHandler : function(form) {// 부가 검증 처리
				var sum = eval($("input[name=typeWeightI]").val()) + eval($("input[name=typeWeightF]").val());
				if(sum != 100) {
					alert("<ikep4j:message key='Notice.socialityType.typeWeight.sum'/>");  
					return;
				}
				
				form.submit();	
			}
		};
		new iKEP.Validator("#form", validOptions);
		
	});

	blurInfluenceIndex      = function() {$jq('#spanInfluenceIndex').attr("innerHTML", eval($jq('input[name=typeWeightI]').val())/100);}
	blurFellowshipIndex     = function() {$jq('#spanFellowshipIndex').attr("innerHTML", eval($jq('input[name=typeWeightF]').val())/100);}
	blurIFollowerWeight     = function() {$jq('#spanIFollowerWeight').attr("innerHTML", $jq('input[name=iFollowerWeight]').val());}   
	blurITweetWeight        = function() {$jq('#spanITweetWeight').attr("innerHTML", $jq('input[name=iTweetWeight]').val());}      
	blurIRetweetWeight      = function() {$jq('#spanIRetweetWeight').attr("innerHTML", $jq('input[name=iRetweetWeight]').val());}      
	blurIBlogVisitWeight    = function() {$jq('#spanIBlogVisitWeight').attr("innerHTML", $jq('input[name=iBlogVisitWeight]').val());}     
	blurIProfileVisitWeight = function() {$jq('#spanIProfileVisitWeight').attr("innerHTML", $jq('input[name=iProfileVisitWeight]').val());}   
	blurIMaxValue           = function() {$jq('#span1IMaxValue').attr("innerHTML", $jq('input[name=iMaxValue]').val()); $jq('#span2IMaxValue').attr("innerHTML", $jq('input[name=iMaxValue]').val());}            
	blurFFollowerWeight      = function() {$jq('#spanFFollowerWeight').attr("innerHTML", $jq('input[name=fFollowerWeight]').val());}
	blurFFollowingWeight     = function() {$jq('#spanFFollowingWeight').attr("innerHTML", $jq('input[name=fFollowingWeight]').val());}
	blurFBothFollowingWeight = function() {$jq('#spanFBothFollowingWeight').attr("innerHTML", $jq('input[name=fBothFollowingWeight]').val());}
	blurFMblogGroupWeight    = function() {$jq('#spanFMblogGroupWeight').attr("innerHTML", $jq('input[name=fMblogGroupWeight]').val());}
	blurFGuestbookWeight     = function() {$jq('#spanFGuestbookWeight').attr("innerHTML", $jq('input[name=fGuestbookWeight]').val());}
	blurFMaxValue            = function() {$jq('#span1FMaxValue').attr("innerHTML", $jq('input[name=fMaxValue]').val()); $jq('#span2FMaxValue').attr("innerHTML", $jq('input[name=fMaxValue]').val());}
	
})(jQuery);
</script>

				<h1 class="none"></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${prefix}' key='main.title'/></h2>
				</div>
				<!--//subTitle_2 End-->	
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table1.title'/></h3>
				</div>
				<!--//subTitle_2 End-->				
				
				<!--blockDetail Start-->					
				<form id="form" action="<c:url value="/socialpack/socialanalyzer/saveSocialRanking.do"/>" method="post">
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary1'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table1.column1'/></th>
								<th class="textCenter" scope="col" width="44%"><ikep4j:message pre='${prefix}' key='table1.column2'/> (0 &lt; = x &lt; = 100)</th>
								<th class="textCenter" scope="col" width="44%"><ikep4j:message pre='${prefix}' key='table1.column3'/> (0 &lt; = x &lt; = 100)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table1.weight'/></th>
								<td scope="col" class="textCenter"><div><input type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='table1.column2'/>" name="typeWeightI" value="${socialityTypeI.typeWeight}" size="3" maxlength="3" onblur="blurInfluenceIndex()"/>%<div></td>
								<td scope="col" class="textCenter"><div><input type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='table1.column3'/>" name="typeWeightF" value="${socialityTypeF.typeWeight}" size="3" maxlength="3" onblur="blurFellowshipIndex()"/>%</div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--directive Start-->
				<div class="directive"> 
					<ul>
						<li><span><ikep4j:message pre='${prefix}' key='table1.comment'/></span></li>
					</ul>
				</div>
				<!--//directive End-->
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table1.title'/> =</h3>
					
					<div class="point_box">
						<span>(<ikep4j:message pre='${prefix}' key='table1.column2'/> × <span id="spanInfluenceIndex">${socialityTypeI.typeWeight/100}</span>) + (<ikep4j:message pre='${prefix}' key='table1.column3'/> × <span id="spanFellowshipIndex">${socialityTypeF.typeWeight/100}</span>)</span>
					</div>
				</div>
				<!--//debate_point End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table2.title'/></h3>
					<div class="subTitle_2_input02"><span>※<ikep4j:message pre='${prefix}' key='table2.comment'/></span>&nbsp;&nbsp;<ikep4j:message pre='${prefix}' key='table2.search.recent'/> <input name="validMonthI" title="<ikep4j:message pre='${prefix}' key='table2.search.title'/>" class="inputbox" size="2" maxlength="2" type="text" value="${socialityTypeI.validMonth}"/><ikep4j:message pre='${prefix}' key='table2.search.month'/></div>
				</div>
				<!--//subTitle_2 End-->
				
				<div class="blockBlank_5px"></div>
				
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary2'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="10%"><ikep4j:message pre='${prefix}' key='table2.column1'/></th>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table2.column2'/></th>
								<th class="textCenter" scope="col" width="17%"><ikep4j:message pre='${prefix}' key='table2.column3'/></th>
								<th class="textCenter" scope="col" width="17%"><ikep4j:message pre='${prefix}' key='table2.column4'/></th>
								<th class="textCenter" scope="col" width="15%"><ikep4j:message pre='${prefix}' key='table2.column5'/></th>
								<th class="textCenter" scope="col" width="15%"><ikep4j:message pre='${prefix}' key='table2.column6'/></th>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table2.column7'/></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table2.compensator'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.iFollowerWeight">    <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurIFollowerWeight    ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.iTweetWeight">       <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurITweetWeight       ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.iRetweetWeight">     <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurIRetweetWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.iBlogVisitWeight">   <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurIBlogVisitWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.iProfileVisitWeight"><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurIProfileVisitWeight()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.iMaxValue">          <input name="${status.expression}" value="${status.value}" class="inputbox" size="5" maxlength="5" type="text" title="input box" onblur="blurIMaxValue          ()"/></spring:bind></div></td>
							</tr>
						</tbody>
					</table>
					<div class="clear"></div>
				</div>
				<!--//blockDetail End-->
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table2.influenceValue'/> =</h3>
					<div class="point_box02">
						<span>(<ikep4j:message pre='${prefix}' key='table2.column2'/>×<span id="spanIFollowerWeight">${socialityPolicy.iFollowerWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table2.column3'/>×<span id="spanITweetWeight">${socialityPolicy.iTweetWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table2.column4'/>×<span id="spanIRetweetWeight">${socialityPolicy.iRetweetWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table2.column5'/>×<span id="spanIBlogVisitWeight">${socialityPolicy.iBlogVisitWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table2.column6'/>×<span id="spanIProfileVisitWeight">${socialityPolicy.iProfileVisitWeight}</span>);</span>
					</div>
					<h3><ikep4j:message pre='${prefix}' key='table2.title'/> =</h3>
					<div class="point_box">
						<span><ikep4j:message pre='${prefix}' key='table2.influenceValue'/> &gt;= <span id="span1IMaxValue">${socialityPolicy.iMaxValue}</span> ? 100 : (100×<ikep4j:message pre='${prefix}' key='table2.influenceValue'/> / <span id="span2IMaxValue">${socialityPolicy.iMaxValue}</span>);</span>
					</div>
				</div>
				<!--//debate_point End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table3.title'/></h3>
					<div class="subTitle_2_input02"><span>※ <ikep4j:message pre='${prefix}' key='table3.comment'/></span>&nbsp;&nbsp;<ikep4j:message pre='${prefix}' key='table3.search.recent'/> <input name="validMonthF" title="<ikep4j:message pre='${prefix}' key='search.title'/>" class="inputbox" size="2" maxlength="2" type="text" value="${socialityTypeF.validMonth}"/><ikep4j:message pre='${prefix}' key='table3.search.month'/></div>
				</div>
				<!--//subTitle_2 End-->
				
				<div class="blockBlank_5px"></div>
				
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary3'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table3.column1'/></th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table3.column2'/></th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table3.column3'/></th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table3.column4'/></th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table3.column5'/></th>
								<th class="textCenter" scope="col" width="16%"><ikep4j:message pre='${prefix}' key='table3.column6'/></th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table3.column7'/></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table3.compensator'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.fFollowerWeight">     <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFFollowerWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.fFollowingWeight">    <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFFollowingWeight    ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.fBothFollowingWeight"><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFBothFollowingWeight()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.fMblogGroupWeight">   <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFMblogGroupWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.fGuestbookWeight">    <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFGuestbookWeight    ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="socialityPolicy.fMaxValue">           <input name="${status.expression}" value="${status.value}" class="inputbox" size="5" maxlength="5" type="text" title="input box" onblur="blurFMaxValue           ()"/></spring:bind></div></td>							</tr>
						</tbody>
					</table>
					<div class="clear"></div>
				</div>
				</form>
				<!--//blockDetail End-->
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table3.fellowshipValue'/> =</h3>
					<div class="point_box02">
						<span>(<ikep4j:message pre='${prefix}' key='table3.column2'/>×<span id="spanFFollowerWeight">${socialityPolicy.fFollowerWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table3.column3'/>×<span id="spanFFollowingWeight">${socialityPolicy.fFollowingWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table3.column4'/>×<span id="spanFBothFollowingWeight">${socialityPolicy.fBothFollowingWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table3.column5'/>×<span id="spanFMblogGroupWeight">${socialityPolicy.fMblogGroupWeight}</span>) + (<ikep4j:message pre='${prefix}' key='table3.column6'/>×<span id="spanFGuestbookWeight">${socialityPolicy.fGuestbookWeight}</span>);</span>
					</div>
					<h3><ikep4j:message pre='${prefix}' key='table3.title'/> =</h3>
					<div class="point_box">
						<span><ikep4j:message pre='${prefix}' key='table3.fellowshipValue'/> &gt;= <span id="span1FMaxValue">${socialityPolicy.fMaxValue}</span> ? 100 : (100×<ikep4j:message pre='${prefix}' key='table3.fellowshipValue'/> / <span id="span2FMaxValue">${socialityPolicy.fMaxValue}</span>);</span>
					</div>
				</div>
				<!--//debate_point End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" id="saveButton"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
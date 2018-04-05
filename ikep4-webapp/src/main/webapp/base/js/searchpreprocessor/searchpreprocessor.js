(function($) {
	$(document).ready(function() {
		
		$("#colleagueSearchButton").data("endIndex", 0).data("option","colleague");
		$("#colleagueListButton").data("endIndex", 0).data("option","colleague");
		
		$("#colleagueListButton").click(function(){
			var setting ={'clear':true};
			
			calleageCall(setting);
		});
		
		var calleageCall= function(settings) {
			
				var oldIndex=0;
				var config ={'startIndex':'0','next':'20','searchOption':'colleague'};
	     		if (settings) $.extend(config, settings); 
	     		var isEmpty=true;
	     		
	     		//alert( JSON.stringify(config));
	     		
				 $.post(contextRoot +"/support/searchpreprocessor/searchhistory/ajax/colleaguelist.do" ,
						 config)
				 .success(function(data) {
					 if( !data.next ){
						 $("#colleagueListButtonDiv").hide();
					 }
					 else
					 {
						 $("#colleagueListButtonDiv").show();
					 }
					 //start index new setting
					 oldIndex=config.startIndex;
					 
					 $("#colleagueSearchButton").data("endIndex",data.endIndex);
					 
					 $(data.userList).each(function(){
						 isEmpty = false;
					 });
					 
					 if( !isEmpty )
					 {
						 if(config.clear)  
							 $("#colleagueListView").empty();
					 }else{
						 if(!config.clear)   
							 alert('<ikep4j:message key="ui.support.searchpreprocessor.emptyRecord" />');
					 }
					 
					 $(data.userList).each(function(){
						 isEmpty = false;
						 oldIndex++;
						 this.index =oldIndex;
						 if( myLocale )
						 	$.tmpl("calleagetemplet",this).appendTo( $("#colleagueListView") );
						 else
						    $.tmpl("engcalleagetemplet",this).appendTo( $("#colleagueListView") );
					 });
					 
				 })
				 .error(function(event, request, settings) { alert("error"); }); 
	 
	   };
	   
		$("#colleagueSearchButton").click(function(){
			var setting ={'startIndex':$(this).data("endIndex"),'searchOption':$(this).data("option"),'clear':false};
			
			calleageCall(setting);
		});	
		
		
		$.template("calleagetemplet", '<tr class="msg_unread">'+
									   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
									   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" name="profileButton" id="\${userId}">\${userName} \${jobTitleName} \${teamName}</a></td>'+
									   '</tr>');
		$.template("engcalleagetemplet", '<tr class="msg_unread">'+
									   '<td scope="col" width="5%" class="tdFirst textRight_p20">\${index}</td>'+
									   '<td scope="col" width="*" class="textLeft_p20"><a href="#a" name="profileButton" id="\${userId}">\${userEnglishName} \${jobTitleEnglishName} \${teamEnglishName}</a></td>'+
									   '</tr>');
									   
		$("#colleagueListView").delegate("a[name='profileButton']","click",function(){
				var userId = $(this).attr("id");
				iKEP.goProfilePopupMain(userId);
		});								   
	});
})(jQuery);

var searchCall = function(searchKeyword){
	$jq('#searchKeyword1').val(searchKeyword);
	$jq('#intelligentSearchForm').submit();
};
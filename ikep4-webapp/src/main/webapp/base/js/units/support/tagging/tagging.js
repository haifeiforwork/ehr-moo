	
	var Tagging = new (function() {
		
		var _contextRoot = window["contextRoot"] || "";
		this.getContextRoot = function () { return _contextRoot; };
		
	})();


	Tagging.tagFormView = function(itemId){
		
		var $form = $jq('#tagForm_'+itemId);
		
		var tagText = "";
		$form.find('span:eq(0) a').each(function(i){ 
			if(i != 0)tagText += ",";
			tagText += $jq.trim($jq(this).text());
		});
		
		var html = '<table class="w80"><caption></caption><tr><td><input name="tagName" class="inputbox" type="text" value="'+tagText+'"/></td>';
		 	html += '<td width="90" class="textRight"><ul><li><a class="button_s" href="#a" onclick="Tagging.tagAdd(\''+itemId+'\');return false;"><span>저장</span></a></li>';
		 	html += '<li><a class="button_s" href="#a" onclick="Tagging.tagCancle(\''+itemId+'\');return false;"><span>취소</span></a></li></ul></td></tr></table>';
		
		$form.attr('class','blockComment_rewrite modify mb5').find('div').html(html);
		
	};
	
	Tagging.tagAdd = function(itemId){
		
		var $form = $jq('#tagForm_'+itemId);
		
		var tagItemId 		= itemId;
		var tagItemType 	= $form.find(':input[name=tagItemType]').val();
		var tagItemSubType 	= $form.find(':input[name=tagItemSubType]').val();
		var tagItemName	 	= $form.find(':input[name=tagItemName]').val();
		var tagItemUrl 		= $form.find(':input[name=tagItemUrl]').val();
		var tagName 		= $form.find(':input[name=tagName]').val();
		
		$jq.ajax({    
			url : this.getContextRoot()+'/support/tagging/createTagAjax.do',     
			data : {tagItemId:tagItemId, tagItemType:tagItemType, tagItemSubType:tagItemSubType, tagItemName:tagItemName, tagItemUrl:tagItemUrl, tagName:tagName},     
			type : "post",  
			dataType : "html",
			success : function(result) {
				Tagging.tagHtmlInit(itemId, tagName);
			},
			error : function(event, request, settings){
				alert("error");
			}
		}); 
		
		
	};
	
	Tagging.tagCancle = function(itemId){
		
		var $form = $jq('#tagForm_'+itemId);
		
		var tagText = $form.find(':input[name=tagName]').val();
		
		Tagging.tagHtmlInit(itemId, tagText);
		
	};
	
	
	Tagging.tagHtmlInit = function(itemId, tagText){
		
		var tags = tagText.split(",");
		
		var newTagText = "";
		for(var i =0; i < tags.length; i++){
			if(i != 0){newTagText += ",";}
			newTagText += '<a href="#a">'+tags[i]+'</a>';
		}
		
		var $form = $jq('#tagForm_'+itemId);
		
		var html='<img src="'+this.getContextRoot()+'/base/images/theme/theme01/basic/ic_tag.gif" alt="태그" />';
		html += newTagText+'<span class="rebtn"><a href="#a"><img src="'+this.getContextRoot()+'/base/images/icon/ic_modify.gif" /></a></span>';

		
		$form.find('div').html(html);
		
	};
	
	
	Tagging.tagSearch = function(val, itemType){
		
		$jq.ajax({    
			url : this.getContextRoot()+"/support/tagging/tagSearchAjax.do",     
			data : {tagId:val, tagItemType:itemType},     
			type : "post",  
			dataType : "html",
			success : function(result) {   
				
				$jq("#tagResult").html(result);
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	};
	
	
// jQuery alias setting
var $jq = jQuery.noConflict();
var SocialBlog = new (function() {	
	var _contextRoot = window["contextRoot"] || "";
	this.getContextRoot = function () { return _contextRoot; };
})();

SocialBlog.getBlogintroductionView = function(blogOwnerId,editflag) {		
	$jq.ajax({
	    url : this.getContextRoot() + "/socialpack/socialblog/editSocialBlogProfile.do",
	    data : {'blogOwnerId':blogOwnerId,'editflag':eval("'"+editflag+"'")},
	    type : "post",
	    success : function(result) {
	    	$jq("#edit_introduction").html(result);
	    }
	});
}; 

SocialBlog.getPostingList = function(blogOwnerId) {
	document.location.href = this.getContextRoot() + "/socialpack/socialblog/socialBlogHome.do?blogOwnerId="+blogOwnerId ;
};

SocialBlog.getNewPosting = function(blogOwnerId) {
	document.location.href = this.getContextRoot() + "/socialpack/socialblog/createSocialBoardItemView.do?blogOwnerId="+blogOwnerId ;
};

SocialBlog.getEditBlogPosting = function(blogOwnerId,itemId) {
	document.location.href = this.getContextRoot() + "/socialpack/socialblog/updateSocialBoardItemView.do?blogOwnerId="+blogOwnerId+"&itemId="+itemId ;
};
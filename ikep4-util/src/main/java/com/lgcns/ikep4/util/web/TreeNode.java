package com.lgcns.ikep4.util.web;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class TreeNode extends BaseObject {
	private static final long serialVersionUID = 1L;
	private String id;

	@JsonProperty("data")
	private String title;

	private String parent;

	private List<TreeNode> children;

	private String icon;

	private String state;

	@JsonProperty("attr")
	private Object attribute;


	public TreeNode(String id, String parent, String title, String icon, String state) {
		super();
		this.id = id;
		this.parent = parent;
		this.title = title;
		this.state = state;
		this.icon = icon;
	}
	
	public TreeNode(String id, String parent, String title, String icon, String state, Object attribute) {
		super();
		this.id = id;
		this.parent = parent;
		this.title = title;
		this.attribute = attribute;
		this.state = state;
		this.icon = icon;
	}

	public TreeNode(String id, String parent, List<TreeNode> children,String title, String icon, String state, Object attribute) {
		super();
		this.id = id;
		this.parent = parent;
		this.children = children;
		this.title = title;
		this.attribute = attribute;
		this.state = state;
		this.icon = icon;
	}
	
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return this.children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Object getAttribute() {
		return this.attribute;
	}

	public void setAttribute(Object attribute) {
		this.attribute = attribute;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

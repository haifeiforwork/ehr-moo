package com.lgcns.ikep4.util.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.model.BaseObject;


public final class TreeMaker {
	private List<TreeNode> nodeList = new ArrayList<TreeNode>();

	private List<TreeNode> resultList = new ArrayList<TreeNode>();

	private String idField;

	private String parentField;

	private String titleField;

	private String state;

	private List<? extends BaseObject> objectList;

	private List<IconSelector> iconSelectorList = Collections.emptyList();

	private String rootId;

	private TreeNode dummyRoot;

	private TreeMaker(List<? extends BaseObject> objectList, String idField, String parentField, String titleField) {
		this.idField = idField;
		this.parentField = parentField;
		this.titleField = titleField;
		this.objectList = objectList;
	}

	public static final TreeMaker init(List<? extends BaseObject> objectList, String idField, String parentField,
			String titleField) {
		return new TreeMaker(objectList, idField, parentField, titleField);

	}

	public TreeMaker root(String rootId) {
		this.rootId = rootId;
		return this;
	}

	public TreeMaker dummyRoot(String title) {
		this.dummyRoot = new TreeNode(null, null, title, "", this.state, null);
		return this;
	}

	public TreeMaker dummyRoot(String id, String title) {
		this.dummyRoot = new TreeNode(id, null, title, "", this.state, null);
		return this;
	}

	public TreeMaker dummyRoot(String id, String title, String icon, String state) {
		this.dummyRoot = new TreeNode(id, null, title, "", state, null);
		return this;
	}

	public TreeMaker icon(IconSelector iconSelector) {
		if (this.iconSelectorList.isEmpty()) {
			this.iconSelectorList = new ArrayList<IconSelector>();
		}

		this.iconSelectorList.add(iconSelector);

		return this;
	}

	public TreeMaker create() {
		String id;
		String parent;
		String title;

		for (Object object : this.objectList) {

			try {
				id = PropertyUtils.getProperty(object, this.idField) == null ? null : String.valueOf(PropertyUtils
						.getProperty(object, this.idField));
				parent = PropertyUtils.getProperty(object, this.parentField) == null ? null : String
						.valueOf(PropertyUtils.getProperty(object, this.parentField));
				title = PropertyUtils.getProperty(object, this.titleField) == null ? null : String
						.valueOf(PropertyUtils.getProperty(object, this.titleField));

				TreeNode data = new TreeNode(id, parent, title, "", this.state, object);

				if (!this.iconSelectorList.isEmpty()) {
					for (IconSelector iconSelector : this.iconSelectorList) {
						data.setIcon(iconSelector.execute(object));
					}
				}

				this.nodeList.add(data);
			} catch (Exception e) {
				throw new IKEP4ApplicationException("", e);
			}
		}

		this.set();

		this.findRoot();

		return this;
	}

	public void findRoot() {
		for (TreeNode data : this.nodeList) {
			if (this.rootId == null) {
				if (data.getParent() == null) {
					this.resultList.add(data);
				}
			} else {
				if (this.rootId.equals(data.getParent())) {
					this.resultList.add(data);
				}
			}

		}
	}

	public String toJsonString() {
		String fileDataListJson = null;

		try {
			ObjectMapper mapper = new ObjectMapper();

			if (this.dummyRoot != null) {
				this.dummyRoot.setChildren(this.resultList);
				fileDataListJson = mapper.writeValueAsString(this.dummyRoot);

			} else {
				fileDataListJson = mapper.writeValueAsString(this.resultList);
			}

		} catch (Exception excption) {

		}

		return fileDataListJson;
	}

	private void set() {
		List<TreeNode> chlidren = new ArrayList<TreeNode>();

		for (TreeNode node1 : this.nodeList) {
			chlidren = new ArrayList<TreeNode>();

			for (TreeNode node2 : this.nodeList) {
				if (node1.getId() != null) {
					if (node1.getId().equals(node2.getParent())) {
						chlidren.add(node2);
					}
				}
			}

			node1.setChildren(chlidren);
		}
	}

	public TreeMaker stateOpen() {
		this.state = "open";
		return this;
	}
}

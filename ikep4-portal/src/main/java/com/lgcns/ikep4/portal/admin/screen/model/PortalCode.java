package com.lgcns.ikep4.portal.admin.screen.model;

import java.util.Arrays;
import java.util.List;

public class PortalCode {
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(
			new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"),
			new Code<Integer>(20, "20"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50")
	);

	public List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}
}
package com.lgcns.ikep4.collpack.collaboration.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.workspaceMap.model.WorkspaceMap;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.search.MapBlockPageCondition;
import com.lgcns.ikep4.collpack.collaboration.workspaceMap.service.WorkspaceMapAdminService;

public class WorkspaceMapAdminServiceTest extends BaseServiceTestCase{
	
	@Autowired
	WorkspaceMapAdminService workspaceMapAdminService;
	
	
	private WorkspaceMap workspaceMap;
	private WorkspaceMap workspaceMapSet;
	private String pk;
	private String rootMapName = "CollaborationTest";
	@Before
	public void setUp() {
		workspaceMap = new WorkspaceMap();
		//workspaceMap.setMapId("1000001");
		workspaceMap.setMapName("TEST_MAP");
		workspaceMap.setMapParentId("100000737565");
		workspaceMap.setWorkspaceId("100000143627");
		workspaceMap.setMapDescription("JUNIT TEST DESCRIPTION. 테스트 설명");
		workspaceMap.setTags("tag1,TAG2,tag3");
		workspaceMap.setRegisterId(registerId);
		workspaceMap.setRegisterName(registerName);
		workspaceMap.setUpdaterId(updaterId);
		workspaceMap.setUpdaterName(updaterName);
		
		workspaceMapSet = workspaceMapAdminService.createMap(workspaceMap);
		pk = workspaceMapSet.getMapId();
		
	}
	
	@Test
	public void testReadWorkspaceMapRoot(){
		WorkspaceMap result = workspaceMapAdminService.readWorkspaceMapRoot(workspaceMapSet.getWorkspaceId());
		assertEquals(rootMapName,result.getMapName());
	}
	
	@Test
	public void testListByWorkspaceMapRootId(){
		WorkspaceMap root = workspaceMapAdminService.readWorkspaceMapRoot(workspaceMapSet.getWorkspaceId());
		Map<String,String> map = new HashMap<String,String>();
		map.put("workspaceId", workspaceId);
		map.put("mapParentId", root.getMapId());
		
		List<WorkspaceMap> result = workspaceMapAdminService.listByWorkspaceMapRootId(map);
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateWithTags(){
		
		workspaceMapSet.setMapName("UpdateMapName");
		workspaceMapSet.setMapDescription("UpdateMapDescription");
		workspaceMapSet.setTags("tag1_update,tag2_update");
		
		workspaceMapAdminService.updateWithTags(workspaceMapSet);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("workspaceId", workspaceMapSet.getWorkspaceId());
		map.put("mapId", workspaceMapSet.getMapId());
		
		WorkspaceMap result = workspaceMapAdminService.readAddedNode(map);
		
		assertEquals(workspaceMapSet.getMapName(),result.getMapName());
		assertEquals(workspaceMapSet.getMapDescription(),result.getMapDescription());
		assertEquals(workspaceMapSet.getTags(),result.getTags());
		
	}
	
	@Test
	public void testDeleteWithTagsHierarchy(){
		
		String orgId = workspaceMapSet.getMapId();
		
		workspaceMapSet.setMapName("childName");
		workspaceMapSet.setMapDescription("childDesc");
		workspaceMapSet.setTags("childTag1,childTag2");
		workspaceMapSet.setMapParentId(orgId);
		
		WorkspaceMap childTempMap = workspaceMapAdminService.createMap(workspaceMapSet);
		String childId = childTempMap.getMapId();
		
		workspaceMapAdminService.deleteWithTagsHierarchy(workspaceMapSet.getWorkspaceId(), orgId);
		
		Map<String,String> orgMap = new HashMap<String,String>();
		orgMap.put("workspaceId", workspaceMapSet.getWorkspaceId());
		orgMap.put("mapId", orgId);
		
		Map<String,String> childMap = new HashMap<String,String>();
		childMap.put("workspaceId", workspaceMapSet.getWorkspaceId());
		childMap.put("mapId", childId);
		
		WorkspaceMap orgResult = workspaceMapAdminService.readAddedNode(orgMap);
		WorkspaceMap childResult = workspaceMapAdminService.readAddedNode(childMap);
		
		assertNull(orgResult);
		assertNull(childResult);
	}
	
	@Test
	public void testMoveInnerMap(){
		workspaceMapSet.setMapName("SameLevelMapName");
		workspaceMapSet.setMapDescription("SameLevelMapDesc");
		workspaceMapSet.setTags("SameLevelTag1,SameLevelTag2");
		
		WorkspaceMap sameLevelTempMap = workspaceMapAdminService.createMap(workspaceMapSet);
		
		String sourceId = sameLevelTempMap.getMapId();
		String sourceParentId = sameLevelTempMap.getMapParentId();
		
		int sourceSortOrder = workspaceMapSet.getSortOrder();
		int targetSortOrder = 1;
		
		workspaceMapAdminService.moveInnerMap(sourceId,sourceParentId,sourceSortOrder,targetSortOrder);
		
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap.put("workspaceId", sameLevelTempMap.getWorkspaceId());
		tempMap.put("mapId", sourceId);
		WorkspaceMap afterLevelTempMap = workspaceMapAdminService.readAddedNode(tempMap);
		
		assertEquals(sameLevelTempMap.getMapId(),afterLevelTempMap.getMapId());
		assertEquals(workspaceMapSet.getMapParentId(),afterLevelTempMap.getMapParentId());
	}
	
	@Test
	public void testMoveOuterMap(){
		workspaceMapSet.setMapName("SameLevelMapName");
		workspaceMapSet.setMapDescription("SameLevelMapDesc");
		workspaceMapSet.setTags("SameLevelTag1,SameLevelTag2");
		
		WorkspaceMap sameLevelTempMap = workspaceMapAdminService.createMap(workspaceMapSet);
		
		String sourceId = sameLevelTempMap.getMapId();
		String sourceParentId = sameLevelTempMap.getMapParentId();
		String targetParentId = pk;
		int sourceSortOrder = workspaceMapSet.getSortOrder();
		int targetSortOrder = 1;
		
		workspaceMapAdminService.moveOuterMap(sourceId,sourceParentId,sourceSortOrder, targetParentId, targetSortOrder);
		
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap.put("workspaceId", sameLevelTempMap.getWorkspaceId());
		tempMap.put("mapId", sourceId);
		WorkspaceMap afterLevelTempMap = workspaceMapAdminService.readAddedNode(tempMap);
		
		assertEquals(sameLevelTempMap.getMapId(),afterLevelTempMap.getMapId());
		assertEquals(pk,afterLevelTempMap.getMapParentId());
	}
	

	@Test
	public void testExistLowRankGroup(){
		
		boolean result = workspaceMapAdminService.existLowRankGroup(workspaceMapSet.getWorkspaceId());
		assertNotNull(result);
	}
	
	@Test
	public void testReadAddedNode(){
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap.put("workspaceId", workspaceMapSet.getWorkspaceId());
		tempMap.put("mapId", pk);
		WorkspaceMap result = workspaceMapAdminService.readAddedNode(tempMap);
		assertNotNull(result);
	}	
	
	@Test
	public void testReadTagList(){
		Map<String,String> param = new HashMap<String,String>();
		param.put("workspaceId", workspaceId);
		param.put("mapId", pk);
		List<WorkspaceMap> result = workspaceMapAdminService.readTagList(param);
		assertNotNull(result);
	}
	
	@Test
	public void testListItemByTag(){
		MapBlockPageCondition pageCondition = new MapBlockPageCondition();
		pageCondition.setRequestPage(1);
		pageCondition.setCountOfPage(10);
		pageCondition.setReInit(true);
		pageCondition.setPage(1);
		pageCondition.setWorkspaceId(workspaceId);
		pageCondition.setMapId(pk);
		Map<String,String> param = new HashMap<String,String>();
		param.put("workspaceId", workspaceId);
		param.put("mapId", pk);
		List<WorkspaceMap> tagList = workspaceMapAdminService.readTagList(param);
		
		String tag = "";
		for(int i=0;i<tagList.size();i++){
			tag = tagList.get(i).getTag();
			break;
		}
		pageCondition.setTag(tag);
		
		List<WorkspaceMap> result = workspaceMapAdminService.listItemByTag(pageCondition);
		assertNotNull(result);
	}
}

package com.lgcns.ikep4.collpack.kms;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.kms.batch.dao.KmsMigrationBoardItemDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardAssessItemDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardMigrationDao;
import com.lgcns.ikep4.collpack.kms.board.model.BoardAssessItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


public class KmsMigrationBoardItem extends BaseKmsTestCase{
	
	@Autowired
	private SqlMapClient sqlMapClientInfo;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private BoardItemService boardItemService;
	
	
	/**
	 * 파일 Dao
	 */
	@Autowired
	private FileDao fileDao;

	/**
	 * 파일 링크 Dao
	 */
	@Autowired
	private FileLinkDao fileLinkDao;
	
	@Autowired
	private KmsMigrationBoardItemDao migrationBoardItemDao;
	
	@Autowired
	private BoardItemDao boardItemDao;
	
	@Autowired
	private BoardAssessItemDao boardAssessItemDao;
	
	@Autowired
	private BoardMigrationDao boardMigrationDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BoardLinereplyDao boardLinereplyDao;
	
//	@Test
//	@Rollback(false)
//	public void migrateBoardItemDelete(){
//		List<HashMap<String, String>> listItem = (List<HashMap<String, String>>)boardMigrationDao.listItem();
//		
//		if(listItem != null){
//			for(HashMap<String, String> item : listItem){
//				String itemId = item.get("ITEM_ID");
//
//				BoardItem boardItem = this.boardItemService.readBoardItem(itemId);
//				this.boardItemService.adminDeleteBoardItem(boardItem);
//			}
//			
//			boardMigrationDao.deleteItem();
//		}
//	}
	
	
	@Test
	@Rollback(false)
	public void migrateBoardItem(){
		
		try {
			migrationBoardItemDao.setSqlMapClientInfo(sqlMapClientInfo);
			List<HashMap<String, String>> listInformationView = (List<HashMap<String, String>>)migrationBoardItemDao.listInformationView();
			
			BoardItem boardItem = null;
			String itemId = null;
			String boardId = null;

			if(listInformationView != null){
				for(HashMap<String, String> informationView : listInformationView){
					
					if(informationView.get("GROUPINGID3") != null){
					
						Map<String, String> boardIdMap = new HashMap<String, String>();
						boardIdMap.put("GROUPINGID3", 	String.valueOf(informationView.get("GROUPINGID3")));
						boardIdMap.put("DATE", 			String.valueOf(informationView.get("DATE")));
						boardIdMap.put("SLIPNO", 		String.valueOf(informationView.get("SLIPNO")));
						
						boardItem = new BoardItem();
						
						//boardId
						boardId = boardMigrationDao.getBoardId(boardIdMap);
						
						if(!"".equals(boardId)){
							
							//회원정보가져오기
							HashMap<String, String> regUserInfo = getUserId(String.valueOf(informationView.get("EMPLOYEEID")));
							
							if(regUserInfo != null){
								String userId 		= regUserInfo.get("USER_ID");
								String userName		= regUserInfo.get("USER_NAME");
								String groupId		= regUserInfo.get("GROUP_ID");
								String groupName	= regUserInfo.get("GROUP_NAME");
								
								//기존정보 master
								HashMap<String, String> information = (HashMap<String, String>) migrationBoardItemDao.getInformation(boardIdMap);
								
								//boardId
								boardItem.setBoardId(boardId);
								
								//itemId
								itemId = idgenService.getNextId();
								boardItem.setItemId(itemId);
			
								boardItem.setItemGroupId(itemId);
								boardItem.setStep(0);
								boardItem.setIndentation(0);
								
								boardItem.setRecommendCount(0);
								boardItem.setReplyCount(0);
								boardItem.setItemDelete(0);
								boardItem.setItemDisplay(0);
								boardItem.setMigrationyn("Y");
								
								boardItem.setTitle(informationView.get("SUBJECT"));
								boardItem.setContents(informationView.get("INFORMATION"));
								boardItem.setHitCount(Integer.parseInt(String.valueOf(informationView.get("FINDNO"))));
								boardItem.setLinereplyCount(Integer.parseInt(String.valueOf(informationView.get("MEMOCOUNT"))));
								
								//attach file count
								int fileCount = Integer.parseInt(String.valueOf(information.get("FILECOUNT")));
								boardItem.setAttachFileCount(fileCount);
								
								if(fileCount > 0){
									for(int i=1 ; i <= fileCount ; i++){
										FileData fileData = setFile(information.get("ATTACHFILE"+i), 
																	userId, 
																	userName, 
																	String.valueOf(information.get("DATE")));
										setFileLink(itemId, 
													userId, 
													userName, 
													String.valueOf(information.get("DATE")), 
													fileData);
									}
								}
		
								//status,mark,infoGrade
								String infoGrade 	= "";
								double mark = 0;
								int status = 1;
								
								String classId 	= String.valueOf(informationView.get("CLASS"));
								String approval = String.valueOf(informationView.get("APPROVAL"));
								String decision = String.valueOf(informationView.get("DECISION"));
								
								//작성완료
								if("".equals(classId)){
									//status 	= 1;
									mark 	= 0;
								}else{
									switch(Integer.parseInt(classId)){
										case 1 : 	infoGrade = "A"; 
													break;
										case 2 : 	infoGrade = "B"; 
													break;
										case 3 : 	infoGrade = "C"; 
													break;
										case 4 : 	infoGrade = "D"; 
													break;
										case 5 : 	infoGrade = "E"; 
													break;
										case 6 : 	infoGrade = "";
													break;											
									}
								}
								
								//임시저장
								if("6".equals(classId)){
									status 	= 0;
									mark 	= 0;
								}
								
								//정보확인
								if("1".equals(approval)){
									status 	= 3;
									mark	= Double.parseDouble(String.valueOf(informationView.get("MARK")));
									
									//평가자정보
									boardItem.setAssessorId("CUTYZIN");
									boardItem.setAssessorName("김형진");
									boardItem.setAssessDate(makeDateFormat(String.valueOf(information.get("DATE"))));
									boardItem.setAssessorOpinion(informationView.get("OPINION"));
								}
								
								//결제확인
								if("1".equals(decision)){
									status 	= 5;
									mark	= Double.parseDouble(String.valueOf(informationView.get("MARK")));
									
									//평가자정보
									boardItem.setAssessorId("CUTYZIN");
									boardItem.setAssessorName("김형진");
									boardItem.setAssessDate(makeDateFormat(String.valueOf(information.get("DATE"))));
									boardItem.setAssessorOpinion(informationView.get("OPINION"));
								}
								
								boardItem.setStatus(status);
								boardItem.setMark((int)mark);
								boardItem.setInfoGrade(infoGrade);
								
								//등록자정보
								boardItem.setRegisterId(userId);
								boardItem.setRegisterName(userName);
								boardItem.setGroupId(groupId);
								boardItem.setGroupName(groupName);
								boardItem.setRegistDate(makeDateFormat(String.valueOf(information.get("DATE"))));
								
								boardItem.setTargetSource(information.get("SOURCE"));
							
								//item 저장
								boardItemDao.create(boardItem);
								
								//item mapping 저장
								boardIdMap.put("ITEMID", itemId);
								boardMigrationDao.createItemMapping(boardIdMap);
								
								//평가항목저장
								BoardAssessItem boardAssessItem = new BoardAssessItem();
								for(int i = 1 ; i <= 17 ; i++){
									boardAssessItem.setItemId(itemId);
									boardAssessItem.setAssessItem(i);
									boardAssessItemDao.createAssessItem(boardAssessItem);
								}
		
								//평가항목 update
								if(boardItem.getStatus() > 2){
									int assessItem 	= 0;
									int value 		= 0;
									
									for(int i=1 ; i <= 11; i++){
										value = Integer.parseInt(String.valueOf(informationView.get("VALUE"+i)));
										if(value > 0){
											switch(i){
												case 1 : 	assessItem = 1;
															break;
												case 2 : 	assessItem = 2;
															break;
												case 3 : 	assessItem = 3;
															break;
												case 4 : 	assessItem = 5;
															break;
												case 5 : 	assessItem = 6;
															break;
												case 6 : 	assessItem = 7;
															break;
												case 7 : 	assessItem = 8;
															break;
												case 8 : 	assessItem = 9;
															break;
												case 9 : 	assessItem = 10;
															break;
												case 10 : 	assessItem = 11;
															break;
												case 11 : 	assessItem = 4;
															break;
											}
											updateAssessItem(itemId, assessItem, value);
										}
									}
								}
								
								//댓글 정보						
								List<HashMap<String, String>> listInformationMemoView = (List<HashMap<String, String>>)migrationBoardItemDao.listInformationMemoView(boardIdMap);
								
								BoardLinereply boardLinereply = null;
								
								if(listInformationMemoView != null){
									for(HashMap<String, String> informationMemoView : listInformationMemoView){
										
										//등록자정보
										HashMap<String, String> userInfo = getUserId(String.valueOf(informationMemoView.get("EMPLOYEEID")));
	
										if(userInfo != null){
											String writeId 			= userInfo.get("USER_ID");
											String writeName 		= userInfo.get("USER_NAME");
											String writeGroupId		= regUserInfo.get("GROUP_ID");
											String writeGroupName	= regUserInfo.get("GROUP_NAME");
										
											boardLinereply = new BoardLinereply();
											
											final String generatedId = this.idgenService.getNextId();
											 
											boardLinereply.setLinereplyId(generatedId);
											boardLinereply.setLinereplyGroupId(generatedId);
											boardLinereply.setItemId(itemId);
											boardLinereply.setStep(0);
											boardLinereply.setIndentation(0);
											boardLinereply.setContents(informationMemoView.get("CONTENTS"));
											boardLinereply.setRegisterId(writeId);
											boardLinereply.setRegisterName(writeName);
											boardLinereply.setGroupId(writeGroupId);
											boardLinereply.setGroupName(writeGroupName);
											boardLinereply.setUpdaterId(writeId);
											boardLinereply.setUpdaterName(writeName);
											
											this.boardLinereplyDao.create(boardLinereply);
										}
									}
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
//	@Test
//	@Rollback(false)
//	public void migrateRefInfo(){
//		
//		try {
//			//활용정보 mapping
//			List<HashMap<String, String>> listInformationPracinfo = (List<HashMap<String, String>>)migrationBoardItemDao.listInformationPracinfo();
//			
//			BoardLinereply boardLinereply = null;
//			
//			if(listInformationPracinfo != null){
//				
//				for(HashMap<String, String> informationPracinfo : listInformationPracinfo){
//					
//					//itemId 정보
//					String itemId 		= getItemId(String.valueOf(informationPracinfo.get("DATE")), String.valueOf(informationPracinfo.get("SLIPNO")));
//					String refItemId 	= getItemId(String.valueOf(informationPracinfo.get("PRACTICALDATE")), String.valueOf(informationPracinfo.get("PRACTICALSLIPNO")));
//					if(itemId != null && refItemId != null){
//						Map<String, String> map = new HashMap<String, String>();
//						map.put("itemId", itemId);
//						map.put("refItemId", refItemId);
//						
//						//활용정보 중복검사
//						int k = this.boardItemDao.countCreateRefInfo(map);
//						if(k == 0)
//							this.boardItemDao.createRefInfo(map);
//					}
//				}
//				
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//	}
	
	private Date makeDateFormat(String strDate) {
		try {
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
			Date date = df.parse(strDate);
			return date;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private FileData setFile(String fileName, String userId, String userName, String date){
		
		try {
			
			String folder = "/ikep4jfile/kms/sapmnt/MPP/global/common/Namo/editor/upload";
			String fileId = StringUtil.replaceQuot(EncryptUtil
					.encryptText(idgenService.getNextId()));
			
			String fileExtension = StringUtil.getFileExtension(fileName);
			File saveFile = new File(folder, UUID.randomUUID()
					.toString().replace("-", "")
					+ "." + fileExtension);
			
			FileData fileData = new FileData();
			fileData.setFileId(fileId);
			fileData.setFilePath(folder);
			fileData.setFileRealName(fileName);
			fileData.setFileSize(0);
			//fileData.setFileName(saveFile.getName());
			fileData.setFileName(fileName);
			fileData.setFileContentsType(fileExtension);
			fileData.setFileExtension(fileExtension);
			fileData.setEditorAttach(0);
			fileData.setRegisterId(userId);
			fileData.setRegisterName(userName);
			fileData.setRegistDate(makeDateFormat(date));
			fileData.setUpdaterId(userId);
			fileData.setUpdaterName(userName);
			fileData.setUpdateDate(makeDateFormat(date));
			
			this.fileDao.create(fileData);
			return fileData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void setFileLink(String itemId, String userId, String userName, String date, FileData fileData){
		try {
			
			FileLink fileLink = new FileLink();
			String fileLinkId = idgenService.getNextId();
			fileLink.setFileId(fileData.getFileId());
			fileLink.setItemId(itemId);
			fileLink.setItemTypeCode(BoardItem.ITEM_FILE_TYPE);
			fileLink.setFileLinkId(fileLinkId);
			fileLink.setRegisterId(userId);
			fileLink.setRegisterName(userName);
			fileLink.setRegistDate(makeDateFormat(date));
			fileLink.setUpdaterId(userId);
			fileLink.setUpdaterName(userName);
			fileLink.setUpdateDate(makeDateFormat(date));
			
			this.fileLinkDao.create(fileLink);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateAssessItem(String itemId, int assessItem, int value){
		
		BoardAssessItem boardAssessItem = new BoardAssessItem();
		
		boardAssessItem.setItemId(itemId);
		boardAssessItem.setAssessItem(assessItem);
		boardAssessItem.setItemDisplay(value);
		this.boardAssessItemDao.updateAssessItem( boardAssessItem);
	}
	
	private HashMap<String, String> getUserId(String empNo){
		
		HashMap<String, String> userInfo = (HashMap<String, String>) this.userDao.empNoToUserId(empNo);
		
		return userInfo;
	}
	
	private String getItemId(String date, String slipNo){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", date);
		map.put("slipNo", slipNo);
		
		String itemId = boardMigrationDao.getItemId(map);
		
		return itemId;
	}

}

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.CafeConstants;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.lightpack.cafe.member.dao.MemberDao;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.lightpack.cafe.member.search.MemberSearchCondition;
import com.lgcns.ikep4.lightpack.cafe.member.service.MemberService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Cafe 멤버 Service 구현 클래스
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: MemberServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service("cfMemberService")
public class MemberServiceImpl extends GenericServiceImpl<Member, String> implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private CafeService cafeService;

	@Autowired
	private ActivityStreamService activityStreamService;

	/**
	 * 회원 목록
	 */
	public SearchResult<Member> listBySearchCondition(MemberSearchCondition searchCondition) {

		Integer count = this.memberDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<Member> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Member>(searchCondition);

		} else {

			List<Member> list = this.memberDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<Member>(list, searchCondition);
		}

		return searchResult;
	}

	public List<Member> listCafeMember(Map<String, String> map) {

		User sessionuser = (User) this.getRequestAttribute("ikep.user");

		List<Member> memberList = memberDao.listCafeMember(map);

		if (memberList != null) {
			if (!sessionuser.getLocaleCode().equals("ko")) {
				for (Member member : memberList) {
					member.setMemberName(member.getMemberEnglishName());
					member.setTeamName(member.getTeamEnglishName());
					member.setJobTitleName(member.getJobTitleEnglishName());
				}
			}
		}
		return memberList;
	}

	public List<Member> getSysopList(Map<String, List<String>> map) {
		return memberDao.getSysopList(map);
	}

	public Member getSysop(String cafeId) {
		return memberDao.getSysop(cafeId);
	}

	public List<Member> getMemberList(Map<String, Object> map) {
		return memberDao.getMemberList(map);
	}

	public List<Member> getMemberMailList(String cafeId) {
		return memberDao.getMemberMailList(cafeId);
	}

	public String create(Member member) {

		Cafe cafe = cafeService.read(member.getCafeId());

		activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_CAFE, IKepConstant.ACTIVITY_CODE_JOIN,
				cafe.getCafeId(), cafe.getCafeName(), member.getMemberId());
		return memberDao.create(member);
	}

	/**
	 * 신규 회원 등록 ( 관리자 )
	 */
	public void addNewMember(MemberSearchCondition searchCondition, User user, List<String> memberIds,
			List<String> associateIds) {
		/**
		 * 기존 회원은 유지 추가 정회원/준회원 등록 추가 정회원/준회원 그룹 매핑정보 등록
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", searchCondition.getCafeId());
		map.put("groupName", "정회원");

		// 정회원 그룹 가져오기
		// String memberGroupId = memberDao.getEvGroup(map);

		map.put("groupName", "준회원");

		// 정회원 그룹 가져오기
		// String associateGroupId = memberDao.getEvGroup(map);

		String activityUserId = null;
		int totalCnt = 0;

		Cafe cafe = cafeService.read(searchCondition.getCafeId());

		/**
		 * 정회원 등록
		 */
		if (memberIds != null) {
			for (String memberId : memberIds) {
				if (activityUserId == null)
					activityUserId = memberId;
				totalCnt++;
				Member isMember = new Member();
				isMember.setCafeId(searchCondition.getCafeId());
				isMember.setMemberId(memberId);

				isMember = memberDao.get(isMember);

				Member member = new Member();

				if (isMember == null) {
					/**
					 * 기존 회원이 없으면 신규 등록
					 */
					member.setCafeId(searchCondition.getCafeId());
					member.setMemberId(memberId);

					member.setMemberIntroduction("정회원가입");
					member.setMemberLevel(CafeConstants.MEMBER_STATUS_MEMBER);
					member.setJoinType(CafeConstants.MEMBER_JOIN_TYPE_ADM);

					member.setRegisterId(user.getUserId());
					member.setRegisterName(user.getUserName());
					member.setUpdaterId(user.getUserId());
					member.setUpdaterName(user.getUserName());

					memberDao.create(member);

				} else {

					/**
					 * 기존회원이 있으며 준/가입대기 회원을 정회원으로 등급조정
					 */
					if (isMember.getMemberLevel().equals(CafeConstants.MEMBER_STATUS_ASSOCIATE)
							|| isMember.getMemberLevel().equals(CafeConstants.MEMBER_STATUS_WAIT)) {

						member.setCafeId(searchCondition.getCafeId());
						member.setMemberId(memberId);
						member.setMemberLevel(CafeConstants.MEMBER_STATUS_MEMBER);
						member.setUpdateType("level");// 등급변경
						member.setUpdaterId(user.getUserId());
						member.setUpdaterName(user.getUserName());
						memberDao.update(member);

					}

				}

				// activity Member Join
				activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_CAFE,
						IKepConstant.ACTIVITY_CODE_JOIN, cafe.getCafeId(), cafe.getCafeName(), activityUserId);
			}
		}
		/**
		 * 준회원 등록
		 */
		if (associateIds != null) {
			for (String associateId : associateIds) {
				if (activityUserId == null)
					activityUserId = associateId;

				totalCnt++;
				Member isMember = new Member();
				isMember.setCafeId(searchCondition.getCafeId());
				isMember.setMemberId(associateId);

				isMember = memberDao.get(isMember);

				Member member = new Member();

				if (isMember == null) {
					/**
					 * 기존 회원이 없으면 신규 준회원 등록
					 */
					member.setCafeId(searchCondition.getCafeId());
					member.setMemberId(associateId);

					member.setMemberIntroduction("준회원가입");
					member.setMemberLevel(CafeConstants.MEMBER_STATUS_ASSOCIATE);
					member.setJoinType(CafeConstants.MEMBER_JOIN_TYPE_ADM);

					member.setRegisterId(user.getUserId());
					member.setRegisterName(user.getUserName());
					member.setUpdaterId(user.getUserId());
					member.setUpdaterName(user.getUserName());

					memberDao.create(member);

				} else {

					/**
					 * 기존회원이 있으며 가입대기 회원을 준회원으로 등급조정
					 */
					if (isMember.getMemberLevel().equals(CafeConstants.MEMBER_STATUS_WAIT)) {
						member.setCafeId(searchCondition.getCafeId());
						member.setMemberId(associateId);
						member.setMemberLevel(CafeConstants.MEMBER_STATUS_ASSOCIATE);
						member.setUpdateType("level");// 등급변경
						member.setUpdaterId(user.getUserId());
						member.setUpdaterName(user.getUserName());
						memberDao.update(member);

					}

				}

				// activity Member Join
				activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_CAFE,
						IKepConstant.ACTIVITY_CODE_JOIN, cafe.getCafeId(), cafe.getCafeName(), activityUserId);
			}

		}

	}

	public Member read(Member object) {
		return memberDao.get(object);
	}

	public boolean exists(Member object) {
		return memberDao.exists(object);
	}

	public void update(Member object) {
		memberDao.update(object);
	}

	/**
	 * 해당 사용자의 디폴트 Cafe 변경 처리
	 */
	public void updateCafeDefaultSet(String portalId, User user, String cafeId) {

		Member member = new Member();
		member.setPortalId(portalId);
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		member.setMemberId(user.getUserId());
		member.setUpdateType("defaultInit");
		memberDao.update(member);

		member = new Member();

		member.setCafeId(cafeId);
		member.setMemberId(user.getUserId());
		member.setIsDefault(1);
		member.setUpdateType("defaultSet");
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		memberDao.update(member);

	}

	/**
	 * 회원 등급 정보 수정
	 */
	public void updateMemberLevel(MemberSearchCondition searchCondition, List<String> memberIds, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", searchCondition.getCafeId());
		if (searchCondition.getMemberLevel().equals("1") || searchCondition.getMemberLevel().equals("2"))
			map.put("groupName", "운영진");
		else if (searchCondition.getMemberLevel().equals("3"))
			map.put("groupName", "정회원");
		else
			map.put("groupName", "준회원");

		// 해당 그룹 가져오기
		// String groupId = memberDao.getEvGroup(map);

		String activityUserId = null;
		int totalCnt = 0;

		try {
			if (memberIds != null) {
				for (int i = 0; i < memberIds.size(); i++) {

					Member member = new Member();

					String memberId = memberIds.get(i);

					if (activityUserId == null)
						activityUserId = memberId;

					totalCnt++;

					member.setCafeId(searchCondition.getCafeId());
					member.setMemberId(memberId);
					member.setMemberLevel(searchCondition.getMemberLevel());
					member.setUpdaterId(user.getUserId());
					member.setUpdaterName(user.getUserName());
					member.setUpdateType(searchCondition.getUpdateType());
					memberDao.update(member);

				}
			}

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nCafe 회원 등급 변경, 사용자 권한 삭제, 사용자 권한  등록 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}
	}

	/**
	 * 시샵 정보 변경
	 */
	public Member updateSysop(MemberSearchCondition searchCondition, List<String> memberIds, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("cafeId", searchCondition.getCafeId());
		map.put("groupName", "운영진");

		// 해당 그룹 가져오기
		// String groupId = memberDao.getEvGroup(map);

		Member member = new Member();
		/**
		 * 기존 시샵은 운영진으로 변경
		 */
		member.setCafeId(searchCondition.getCafeId());
		member.setMemberLevel(CafeConstants.MEMBER_STATUS_ADMIN);
		member.setUpdaterId(user.getUserId());
		member.setUpdaterName(user.getUserName());
		member.setUpdateType("manage");
		memberDao.update(member);

		/**
		 * 신규 시샵 지정
		 */
		for (int i = 0; i < memberIds.size(); i++) {
			member = new Member();

			String memberId = memberIds.get(i);

			member.setCafeId(searchCondition.getCafeId());
			member.setMemberId(memberId);
			member.setMemberLevel(searchCondition.getMemberLevel());
			member.setUpdaterId(user.getUserId());
			member.setUpdaterName(user.getUserName());
			member.setUpdateType("level");
			memberDao.update(member);

		}
		/**
		 * 현재 나의 멤버역할 확인 시샵인 경우 관리메뉴 멤버 목록 시샵이 아닌경우 Cafe 메인으로 분기
		 */
		member = new Member();
		member.setCafeId(member.getCafeId());
		member.setMemberId(user.getUserId());
		member = memberDao.get(member);
		return member;
	}

	/**
	 * 회원 영구 삭제
	 */
	public void physicalDelete(Member object) {

		memberDao.physicalDelete(object);

	}

	/**
	 * 멤버의 카페 삭제
	 */
	public void physicalDelete(List<String> cafeIds, User user) {

		Cafe cafe = new Cafe();
		cafe.setPortalId(user.getPortalId());

		String activityUserId = null;
		int totalCnt = 0;
		for (int i = 0; i < cafeIds.size(); i++) {
			totalCnt++;

			Member member = new Member();
			String cafeId = cafeIds.get(i);
			member.setCafeId(cafeId);
			member.setMemberId(user.getUserId());

			cafe = cafeService.read(cafeId);

			member = memberDao.get(member);

			if (activityUserId == null)
				activityUserId = member.getMemberId();

			if (member.getMemberLevel().equals("1") && cafe.getCafeStatus().equals("O")) // 시샵은
																							// 제외
				if (cafe != null && (cafe.getCafeStatus().equals("O") || cafe.getCafeStatus().equals("WC")))
					continue;

			memberDao.physicalDelete(member);

			// activity Member Quit
			activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_CAFE, IKepConstant.ACTIVITY_CODE_QUIT,
					cafe.getCafeId(), cafe.getCafeName(), activityUserId);

		}

	}

	/**
	 * 카페의 멤버삭제
	 */
	public void physicalDelete(String cafeId, List<String> memberIds) {

		String activityUserId = null;

		Cafe cafe = cafeService.read(cafeId);

		for (int i = 0; i < memberIds.size(); i++) {

			Member member = new Member();
			String memberId = memberIds.get(i);
			member.setCafeId(cafeId);
			member.setMemberId(memberId);

			if (activityUserId == null)
				activityUserId = memberId;

			memberDao.physicalDelete(member);

			// activity Member Quit
			activityStreamService.createForMember(IKepConstant.ITEM_TYPE_CODE_CAFE, IKepConstant.ACTIVITY_CODE_QUIT,
					cafe.getCafeId(), cafe.getCafeName(), activityUserId);

		}

	}

	/**
	 * 카페의 멤버삭제
	 */
	public void physicalDeleteMemberByCafe(String wokspaceId) {

		memberDao.physicalDeleteMemberByCafe(wokspaceId);

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}

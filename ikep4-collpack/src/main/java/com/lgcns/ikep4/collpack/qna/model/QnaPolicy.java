package com.lgcns.ikep4.collpack.qna.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class QnaPolicy extends BaseObject {
   
	/**
	 *
	 */
	private static final long serialVersionUID = -4694405196698005978L;

	/**
	 * BEST 정책 ID
	 */
	private String policyId;

	/**
	 * 질문 답변 게시글 타입 ( 0 : 질문, 1 : 답변)
	 */
    private String qnaType;

    /**
	 * 조회수에 대한 가중치
	 */
    private int hitWeight = 0;

    /**
     * 댓글수에 대한 가중치
     */
    private int linereplyWeight = 0;

    /**
     * 답변수에 대한 가중치
     */
    private int answerWeight = 0;
    
    /**
     * 즐겨찾기수에 대한 가중치
     */
    private int favoriteWeight = 0;
    
    /**
     * 답변 점수합에 대한 가중치
     */
    private int answerSumWeight = 0;
    
    /**
     * Best QnA 여부를 결정하는 기준점수
     */
    private int bestQnaBasisPoint = 0;

    /**
     * 답변 댓글수에 대한 가중치
     */
    private int answerLinereplyWeight = 0;

    /**
     * 답변 추천수에 대한 가중치
     */
    private int answerRecommendWeight = 0;
    
    /**
     * Best 답변 여부를 결정하는  기준점수
     */
    private int bestAnswerBasisPoint = 0;

    /**
     * 등록자 ID
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    /**
     * 등록일시
     */
    private Date registDate;
    
    /**
     * 포털 ID
     */
    private String portalId;

    /**
     * 답변채택수에 대한 가중치
     */
    private int answerAdoptWeight = 0;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getQnaType() {
		return qnaType;
	}

	public void setQnaType(String qnaType) {
		this.qnaType = qnaType;
	}

	public int getHitWeight() {
		return hitWeight;
	}

	public void setHitWeight(int hitWeight) {
		this.hitWeight = hitWeight;
	}

	public int getLinereplyWeight() {
		return linereplyWeight;
	}

	public void setLinereplyWeight(int linereplyWeight) {
		this.linereplyWeight = linereplyWeight;
	}

	public int getAnswerWeight() {
		return answerWeight;
	}

	public void setAnswerWeight(int answerWeight) {
		this.answerWeight = answerWeight;
	}

	public int getFavoriteWeight() {
		return favoriteWeight;
	}

	public void setFavoriteWeight(int favoriteWeight) {
		this.favoriteWeight = favoriteWeight;
	}

	public int getAnswerSumWeight() {
		return answerSumWeight;
	}

	public void setAnswerSumWeight(int answerSumWeight) {
		this.answerSumWeight = answerSumWeight;
	}

	public int getbestQnaBasisPoint() {
		return bestQnaBasisPoint;
	}

	public void setbestQnaBasisPoint(int bestQnaBasisPoint) {
		this.bestQnaBasisPoint = bestQnaBasisPoint;
	}

	public int getAnswerLinereplyWeight() {
		return answerLinereplyWeight;
	}

	public void setAnswerLinereplyWeight(int answerLinereplyWeight) {
		this.answerLinereplyWeight = answerLinereplyWeight;
	}

	public int getAnswerRecommendWeight() {
		return answerRecommendWeight;
	}

	public void setAnswerRecommendWeight(int answerRecommendWeight) {
		this.answerRecommendWeight = answerRecommendWeight;
	}

	public int getbestAnswerBasisPoint() {
		return bestAnswerBasisPoint;
	}

	public void setbestAnswerBasisPoint(int bestAnswerBasisPoint) {
		this.bestAnswerBasisPoint = bestAnswerBasisPoint;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getAnswerAdoptWeight() {
		return answerAdoptWeight;
	}

	public void setAnswerAdoptWeight(int answerAdoptWeight) {
		this.answerAdoptWeight = answerAdoptWeight;
	}


    

}
package com.lgcns.ikep4.support.user.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lgcns.ikep4.support.user.restful.model.FavoriteListReturnData;

@XmlRootElement(name="body")
public class FavoriteListBody {
	
	@XmlElement(name="FavoriteList")
	public List<FavoriteListReturnData> favoriteListReturnData;

	public FavoriteListBody() {}
	
	public FavoriteListBody(List<FavoriteListReturnData> favoriteListReturnData) {
		this.favoriteListReturnData = favoriteListReturnData;
	}
}
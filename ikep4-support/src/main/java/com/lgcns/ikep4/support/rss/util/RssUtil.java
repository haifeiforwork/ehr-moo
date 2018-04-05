package com.lgcns.ikep4.support.rss.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.model.ChannelItem;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;


/**
 * RSS 유틸 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RssUtil.java 16274 2011-08-18 07:08:23Z giljae $
 */
@SuppressWarnings("unchecked")
public final class RssUtil {

	private RssUtil() {
		throw new AssertionError();
	}

	/**
	 * RSS 를 읽어온다 rss_2.0, rss_1.0, atom_1.0, atom_0.3 등을 모두 지원함 ROME Lib 를 사용함
	 * (http://wiki.java.net/bin/view/Javawsxml/Rome)
	 * 
	 * @param rssUrl
	 * @return @
	 */
	public static Channel readRss(String rssUrl) {

		Channel channel = new Channel();

		try {

			if (rssUrl.startsWith("http://")) {

				URL feedUrl = new URL(rssUrl);

				SyndFeedInput input = new SyndFeedInput();
				XmlReader reader = new XmlReader(feedUrl);
				SyndFeed feed = input.build(reader);

				channel.setChannelTitle(stripCDATA(feed.getTitle()));
				channel.setChannelUrl(feed.getLink());
				channel.setChannelDescription(feed.getDescription());
				channel.setChannelType(feed.getFeedType());

				List<SyndEntry> entries = feed.getEntries();

				List<ChannelItem> channelItemList = new ArrayList<ChannelItem>();
				for (SyndEntry entry : entries) {

					ChannelItem channelItem = new ChannelItem();

					channelItem.setItemTitle(stripCDATA(entry.getTitle()));
					channelItem.setItemUrl(entry.getLink());

					if (StringUtil.isEmpty(entry.getAuthor())) {
						channelItem.setItemRegister(" ");
					} else {
						channelItem.setItemRegister(entry.getAuthor());
					}
					if (entry.getDescription() == null) {
						channelItem.setItemDescription(" ");
					} else {
						channelItem.setItemDescription(entry.getDescription().getValue());
					}
					if (entry.getPublishedDate() == null) {
						channelItem.setItemPublishDate(new Date());
					} else {
						channelItem.setItemPublishDate(entry.getPublishedDate());
					}

					channelItemList.add(channelItem);
				}

				channel.setChannelItemList(channelItemList);
			}

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return channel;
	}

	/**
	 * RSS 를 발행한다. ROME Lib 를 사용함 (http://wiki.java.net/bin/view/Javawsxml/Rome)
	 * 
	 * @param feed
	 * @return @
	 */
	public static String sendRss(Channel channel) {

		String rssXml = "";

		try {

			SyndFeed syndFeed = new SyndFeedImpl();
			syndFeed.setTitle(channel.getChannelTitle());
			syndFeed.setLink(channel.getChannelUrl());
			syndFeed.setDescription(channel.getChannelDescription());
			syndFeed.setFeedType(channel.getChannelType());

			List<SyndEntry> entries = new ArrayList<SyndEntry>();

			if (channel != null) {
				for (ChannelItem channelItem : channel.getChannelItemList()) {

					SyndEntry entry = new SyndEntryImpl();

					entry.setTitle(channelItem.getItemTitle());
					entry.setLink(channelItem.getItemUrl());
					entry.setPublishedDate(channelItem.getItemPublishDate());
					entry.setAuthor(channelItem.getItemRegister());

					SyndContent description = new SyndContentImpl();

					description.setType("text/html");
					description.setValue(StringUtil.cutString(channelItem.getItemDescription(), 200, ""));
					entry.setDescription(description);

					SyndContent content = new SyndContentImpl();
					content.setType("text/html");
					content.setValue(channelItem.getItemDescription());

					List<SyndContent> contents = new ArrayList<SyndContent>();
					contents.add(content);

					entry.setContents(contents);

					entries.add(entry);

				}
			}

			syndFeed.setEntries(entries);

			SyndFeedOutput output = new SyndFeedOutput();
			rssXml = output.outputString(syndFeed);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return rssXml;

	}

	/**
	 * 검색한 테이터를 Channel 테이터로 만듬(게시판용)
	 * 
	 * @param board
	 * @param boardItemList
	 * @param feedType
	 * @return @
	 */
	public static Channel getRssChannel(Channel board, List<ChannelItem> boardItemList, String feedType,
			String metaType, String metaId, HttpServletRequest request, String type) {

		Channel channel = null;

		try {
			Properties prop = PropertyLoader.loadProperties("/configuration/rss-conf.properties");
			String atomType = prop.getProperty("ikep4.support.rss.atomtype");
			String rssType = prop.getProperty("ikep4.support.rss.rsstype");

			Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");

			String channelItemUrl = "";
			if (type.equals(IKepConstant.ITEM_TYPE_CODE_BBS)) {
				channelItemUrl = commonprop.getProperty("ikep4.url.detail.bbs");
			} else if (type.equals(IKepConstant.ITEM_TYPE_CODE_QA)) {
				channelItemUrl = commonprop.getProperty("ikep4.url.detail.qna");
			} else if (type.equals(IKepConstant.ITEM_TYPE_CODE_WORK_SPACE)) {
				channelItemUrl = commonprop.getProperty("ikep4.url.detail.collaboration.bbs");
			} else if (type.equals(IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG)) {
				channelItemUrl = commonprop.getProperty("ikep4.url.detail.blog");
			} else if (type.equals(IKepConstant.ITEM_TYPE_CODE_CAFE)) {
				channelItemUrl = commonprop.getProperty("ikep4.url.detail.cafe");
			}

			channelItemUrl = HttpUtil.getWebAppUrl(request) + channelItemUrl;

			// 채널 객체 담기
			channel = new Channel();
			channel.setChannelTitle(board.getChannelTitle());
			channel.setChannelDescription(board.getChannelTitle());
			channel.setChannelUrl(RssUtil.makeChannelUrl(feedType, metaType, metaId, request));
			if (feedType != null && feedType.equalsIgnoreCase("ATOM")) {
				channel.setChannelType(atomType);
			} else {
				channel.setChannelType(rssType);
			}

			// 아이템 객체 담기
			List<ChannelItem> channelItemList = new ArrayList<ChannelItem>();
			for (ChannelItem boardItem : boardItemList) {

				ChannelItem channelItem = new ChannelItem();
				channelItem.setItemTitle(boardItem.getItemTitle());
				channelItem.setItemUrl(channelItemUrl + boardItem.getItemId());
				channelItem.setItemDescription(boardItem.getItemDescription());
				channelItem.setItemPublishDate(boardItem.getItemPublishDate());

				channelItemList.add(channelItem);
			}

			channel.setChannelItemList(channelItemList);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		return channel;
	}

	/**
	 * CDATA_SECTION_NODE 를 파싱함 원래는 CDATA_SECTION_NODE 문자열도 자동으로 파싱이 되는데 간혹 파싱이
	 * 안되는 경우가 있어서 별도로 파싱을 추가함
	 * 
	 * @param s
	 * @return
	 */
	private static String stripCDATA(String s) {

		String str = s.trim();
		if (str.startsWith("<![CDATA[")) {

			str = str.substring(9);
			int i = str.indexOf("]]>");
			str = str.substring(0, i);

		}
		return str;
	}

	/**
	 * 채널 URL 만들기
	 * 
	 * @param feedType
	 * @param metaType
	 * @param metaId
	 * @param request
	 * @return
	 */
	public static String makeChannelUrl(String feedType, String metaType, String metaId, HttpServletRequest request) {

		String channelUrl = "";
		StringBuffer bf = new StringBuffer();

		Properties prop = PropertyLoader.loadProperties("/configuration/rss-conf.properties");

		channelUrl = prop.getProperty("ikep4.support.rss.channel.url");
		channelUrl = HttpUtil.getWebAppUrl(request) + channelUrl;

		bf.append(channelUrl);
		bf.append("?feedType=");
		bf.append(feedType);
		bf.append("&metaType=");
		bf.append(metaType);
		bf.append("&metaId=");
		bf.append(metaId);

		return bf.toString();
	}

}

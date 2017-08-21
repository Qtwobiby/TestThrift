package com.qtwobiby.jsoup.demo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HealthInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String comment;
	private int type;
	private String content;
	private String source;
	private String coverImageUrl;
	private int isHot;
	private String publishedTime;
	private String sourceUrl;
	private String sourceUrlMd5;
	private int vsitorCount;
	private int thumbUpCount;
	private int status;
	private String createTime;

	
	public HealthInformation() {

	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getComment() {
		return this.comment;
	}

	public int getType() {
		return this.type;
	}

	public String getContent() {
		return this.content;
	}

	public String getSource() {
		return this.source;
	}

	public String getCoverImageUrl() {
		return this.coverImageUrl;
	}

	public int getIsHot() {
		return this.isHot;
	}

	public String getPublishedTime() {
		return this.publishedTime;
	}

	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public String getSourceUrlMd5() {
		return this.sourceUrlMd5;
	}

	public int getVsitorCount() {
		return this.vsitorCount;
	}

	public int getThumbUpCount() {
		return this.thumbUpCount;
	}

	public int getStatus() {
		return this.status;
	}

	public String getCreateTime() {
		return this.createTime;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public void setSourceUrlMd5(String sourceUrlMd5) {
		this.sourceUrlMd5 = sourceUrlMd5;
	}

	public void setVsitorCount(int vsitorCount) {
		this.vsitorCount = vsitorCount;
	}

	public void setThumbUpCount(int thumbUpCount) {
		this.thumbUpCount = thumbUpCount;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}




}

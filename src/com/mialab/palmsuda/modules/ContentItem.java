package com.mialab.palmsuda.modules;

import java.util.ArrayList;
import java.util.List;

public class ContentItem {
	private String contentId;
	private String parentId;
	private String contentName;
	private int level;
	private int backgroundVersion;
	private String backgroundUrl;
	private List<ModuleItem> mItems;
	private String mobileNum;
	private boolean isBgUpdate;

	public ContentItem() {
		mItems = new ArrayList<ModuleItem>();
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBackgroundVersion() {
		return backgroundVersion;
	}

	public void setBackgroundVersion(int backgroundVersion) {
		this.backgroundVersion = backgroundVersion;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public List<ModuleItem> getmItems() {
		return mItems;
	}

	public void setmItems(List<ModuleItem> mItems) {
		this.mItems = mItems;
	}

	public void addModuleItem(ModuleItem mItem) {
		mItems.add(mItem);
	}

	public ModuleItem getModuleItemByMdKey(String moduleKey) {
		ModuleItem mItem = null;
		if (mItems != null) {
			for (ModuleItem item : mItems) {
				if (moduleKey.equals(item.getModuleKey())) {
					mItem = item;
					break;
				}
			}
		}
		return mItem;
	}

	public boolean isBgUpdate() {
		return isBgUpdate;
	}

	public void setBgUpdate(boolean isBgUpdate) {
		this.isBgUpdate = isBgUpdate;
	}

}

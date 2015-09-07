package com.mialab.palmsuda.modules;

public class ModuleItem {
	private int moduleId;
	private String moduleVersion;
	private String moduleName;
	private String moduleKey;
	private int options;
	private int iconVersion;
	private String iconUrl;
	private String moduleUrl;
	private int type;
	private int level;// 4 表示运营商
	private String needClientVersion;
	private String dispClientVersion;

	// private int index;
	// private String parameter_a;
	// private String parameter_b;
	// private String parameter_c;
	private String description;
	private boolean isIconUpdate;
	// private String workCityId;
	private int hasAd;

	private ModuleConfigOptions mOptions;

	public ModuleItem() {
	}

	public int getModuleId() {
		return moduleId;
	}

	public String getDispClientVersion() {
		return dispClientVersion;
	}

	public void setDispClientVersion(String dispClientVersion) {
		this.dispClientVersion = dispClientVersion;
	}

	public ModuleConfigOptions getmOptions() {
		return mOptions;
	}

	public void setmOptions(ModuleConfigOptions mOptions) {
		this.mOptions = mOptions;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleVersion() {
		return moduleVersion;
	}

	public void setModuleVersion(String moduleVersion) {
		this.moduleVersion = moduleVersion;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	public ModuleConfigOptions getOptions() {
		return mOptions;
	}

	public void setOptions(int options) {
		this.options = options;
		this.mOptions = new ModuleConfigOptions(options);
	}

	public int getIconVersion() {
		return iconVersion;
	}

	public void setIconVersion(int iconVersion) {
		this.iconVersion = iconVersion;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getNeedClientVersion() {
		return needClientVersion;
	}

	public void setNeedClientVersion(String needClientVersion) {
		this.needClientVersion = needClientVersion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isIconUpdate() {
		return isIconUpdate;
	}

	public void setIconUpdate(boolean isIconUpdate) {
		this.isIconUpdate = isIconUpdate;
	}

	public int getHasAd() {
		return hasAd;
	}

	public void setHasAd(int hasAd) {
		this.hasAd = hasAd;
	}

}

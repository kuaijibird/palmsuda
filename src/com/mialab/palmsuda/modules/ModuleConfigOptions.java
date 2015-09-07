package com.mialab.palmsuda.modules;

public class ModuleConfigOptions {
	private boolean needNetwork = true; // Bit0 1
	private boolean needSdCard = true; // Bit1 2
	private boolean needLogin = true; // Bit2 4
	private boolean needModuleLog = false; // Bit3 8
	private boolean marketVersion = false; // Bit4 16
	private boolean hotModule = false; // Bit5 32
	private boolean newModule = false; // Bit6 64

	public ModuleConfigOptions() {
		// Default constructor
	}

	/**
	 * Construct <code>ModuleConfigOptions</code> with specified options
	 * 
	 * @param value
	 *            The Module options value
	 */
	public ModuleConfigOptions(long value) {
		setLongValue(value);
	}

	public long longValue() {
		long value = 0L;
		value = this.needNetwork ? value | 1 : value;
		value = this.needSdCard ? value | 1 << 1 : value;
		value = this.needLogin ? value | 1 << 2 : value;
		value = this.needModuleLog ? value | 1 << 3 : value;
		value = this.marketVersion ? value | 1 << 4 : value;
		value = this.hotModule ? value | 1 << 5 : value;
		value = this.newModule ? value | 1 << 6 : value;

		return value;
	}

	public long getLongValue() {
		return longValue();
	}

	public void setLongValue(long value) {
		this.needNetwork = (value >> 0 & 1) == 1;
		this.needSdCard = (value >> 1 & 1) == 1;
		this.needLogin = (value >> 2 & 1) == 1;
		this.needModuleLog = (value >> 3 & 1) == 1;
		this.marketVersion = (value >> 4 & 1) == 1;
		this.hotModule = (value >> 5 & 1) == 1;
		this.newModule = (value >> 6 & 1) == 1;
	}

	public boolean isNeedNetwork() {
		return needNetwork;
	}

	public void setNeedNetwork(boolean needNetwork) {
		this.needNetwork = needNetwork;
	}

	public boolean isNeedSdCard() {
		return needSdCard;
	}

	public void setNeedSdCard(boolean needSdCard) {
		this.needSdCard = needSdCard;
	}

	public boolean isNeedLogin() {
		return needLogin;
	}

	public boolean isMarketVersion() {
		return marketVersion;
	}

	public void setNeedLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}

	public boolean isNeedModuleLog() {
		return needModuleLog;
	}

	public void setNeedModuleLog(boolean needModuleLog) {
		this.needModuleLog = needModuleLog;
	}

	public boolean isHotModule() {
		return hotModule;
	}

	public boolean isNewModule() {
		return newModule;
	}
}

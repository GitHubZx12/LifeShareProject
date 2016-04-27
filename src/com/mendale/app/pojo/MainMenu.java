package com.mendale.app.pojo;

/**
 * 选择分类
 * 
 */
public class MainMenu {

	/** 名字 */
	public String name;
	/**件图 */
	public int bg;

	public MainMenu(String name, int bg) {
		super();
		this.name = name;
		this.bg = bg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBg() {
		return bg;
	}

	public void setBg(int bg) {
		this.bg = bg;
	}
}

package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

public class Tools extends BmobObject{
		private String name;
		private String num;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		
}

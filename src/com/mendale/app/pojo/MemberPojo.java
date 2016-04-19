package com.mendale.app.pojo;

import java.io.Serializable;

/**
 * 账号密码登录 返回结果的pojo
 * 
 */
public class MemberPojo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 是否可用
	 */
	private int valid;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 会员电话号码
	 */
	private String phone;
	/**
	 * 出生年月
	 */
	private String birth;
	/**
	 * 总积分
	 */
	private String sumaccpoint;
	/**
	 * 会员等级
	 */
	private int levelId;
	/**
	 * 职业
	 */
	private String profession;
	/**
	 * 可用积分
	 */
	private int accpoint;
	/**
	 * 是不是第一次登录
	 */
	private int isFrist;
	/**
	 * 会员头像地址
	 */
	private String avatar;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 消费金额
	 */
	private double sumamt;
	/**
	 * 背景墙地址
	 */
	private String appwallpaper;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 星座
	 */
	private String constellation;
	/**
	 * 
	 */
	private int tlevel;
	/**
	 * 会员年龄
	 */
	private int age;
	/**
	 * 管家ID
	 */
	private int guanjiaId;
	/**
	 * 会员名称
	 */
	private String membername;
	/**
	 * 会员id
	 */
	private int uuid;
	/**
	 * 
	 */
	private String storeCode;
	/**
	 * 
	 */
	private String drppk;
	/**
	 * 管家名字
	 */
	private String gjName;
	/**
	 * 管家头像
	 */
	private String gjAvatar;
	/**
	 * 管家等级
	 */
	private String gjLevel;
	/**
	 * 管家电话
	 */
	private String gjPhone;
	
	/**
	 * 会员所属门店
	 */
	private String address;
	
	
	public MemberPojo() {
		super();
	}
	public MemberPojo(int valid, String sex, String phone, String birth, String sumaccpoint, int levelId,
			String profession, int accpoint, int isFrist, String avatar, String password, String sign, double sumamt,
			String appwallpaper, String email, String constellation, int tlevel, int age, int guanjiaId,
			String membername, int uuid, String storeCode, String drppk, String gjName, String gjAvatar, String gjLevel,
			String gjPhone,String address) {
		super();
		this.valid = valid;
		this.sex = sex;
		this.phone = phone;
		this.birth = birth;
		this.sumaccpoint = sumaccpoint;
		this.levelId = levelId;
		this.profession = profession;
		this.accpoint = accpoint;
		this.isFrist = isFrist;
		this.avatar = avatar;
		this.password = password;
		this.sign = sign;
		this.sumamt = sumamt;
		this.appwallpaper = appwallpaper;
		this.email = email;
		this.constellation = constellation;
		this.tlevel = tlevel;
		this.age = age;
		this.guanjiaId = guanjiaId;
		this.membername = membername;
		this.uuid = uuid;
		this.storeCode = storeCode;
		this.drppk = drppk;
		this.gjName = gjName;
		this.gjAvatar = gjAvatar;
		this.gjLevel = gjLevel;
		this.gjPhone = gjPhone;
		this.address = address;
	}
	
	public int getValid() {
		return valid;
	}
	
	public void setValid(int valid) {
		this.valid = valid;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getBirth() {
		return birth;
	}
	
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public String getSumaccpoint() {
		return sumaccpoint;
	}
	
	public void setSumaccpoint(String sumaccpoint) {
		this.sumaccpoint = sumaccpoint;
	}
	
	public int getLevelId() {
		return levelId;
	}
	
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	
	public String getProfession() {
		return profession;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public int getAccpoint() {
		return accpoint;
	}
	
	public void setAccpoint(int accpoint) {
		this.accpoint = accpoint;
	}
	
	public int getIsFrist() {
		return isFrist;
	}
	
	public void setIsFrist(int isFrist) {
		this.isFrist = isFrist;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public double getSumamt() {
		return sumamt;
	}
	
	public void setSumamt(double sumamt) {
		this.sumamt = sumamt;
	}
	
	public String getAppwallpaper() {
		return appwallpaper;
	}
	
	public void setAppwallpaper(String appwallpaper) {
		this.appwallpaper = appwallpaper;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getConstellation() {
		return constellation;
	}
	
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public int getTlevel() {
		return tlevel;
	}
	
	public void setTlevel(int tlevel) {
		this.tlevel = tlevel;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getGuanjiaId() {
		return guanjiaId;
	}
	
	public void setGuanjiaId(int guanjiaId) {
		this.guanjiaId = guanjiaId;
	}
	
	public String getMembername() {
		return membername;
	}
	
	public void setMembername(String membername) {
		this.membername = membername;
	}
	
	public int getUuid() {
		return uuid;
	}
	
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	
	public String getStoreCode() {
		return storeCode;
	}
	
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	public String getDrppk() {
		return drppk;
	}
	
	public void setDrppk(String drppk) {
		this.drppk = drppk;
	}
	
	public String getGjName() {
		return gjName;
	}
	
	public void setGjName(String gjName) {
		this.gjName = gjName;
	}
	
	public String getGjAvatar() {
		return gjAvatar;
	}
	
	public void setGjAvatar(String gjAvatar) {
		this.gjAvatar = gjAvatar;
	}
	
	public String getGjLevel() {
		return gjLevel;
	}
	
	public void setGjLevel(String gjLevel) {
		this.gjLevel = gjLevel;
	}
	
	public String getGjPhone() {
		return gjPhone;
	}
	
	public void setGjPhone(String gjPhone) {
		this.gjPhone = gjPhone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

}

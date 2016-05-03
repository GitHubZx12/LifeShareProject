package com.mendale.app.pojo;

import cn.bmob.v3.BmobObject;

public class OtherUser extends BmobObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ocollect;
	private String opus;
	private String course;
	private String collect;
	private String follow;
	private String fans;
	private String uid;
	private String relation;
	private String region_name;
	private String uname;
	private String gender;
	private String email;
	private String des;
	private String face_pic;
	private String bg_pic;
	private String hand_daren;
	private String level;
	private String scores;
	private String _id;
	private CourseData courseData;
	private CollectData collectData;
	private OpusData opusData;
	private OcollectData ocollectData;
	public String getOcollect() {
		return ocollect;
	}
	public void setOcollect(String ocollect) {
		this.ocollect = ocollect;
	}
	public String getOpus() {
		return opus;
	}
	public void setOpus(String opus) {
		this.opus = opus;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public String getFollow() {
		return follow;
	}
	public void setFollow(String follow) {
		this.follow = follow;
	}
	public String getFans() {
		return fans;
	}
	public void setFans(String fans) {
		this.fans = fans;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getFace_pic() {
		return face_pic;
	}
	public void setFace_pic(String face_pic) {
		this.face_pic = face_pic;
	}
	public String getBg_pic() {
		return bg_pic;
	}
	public void setBg_pic(String bg_pic) {
		this.bg_pic = bg_pic;
	}
	public String getHand_daren() {
		return hand_daren;
	}
	public void setHand_daren(String hand_daren) {
		this.hand_daren = hand_daren;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getScores() {
		return scores;
	}
	public void setScores(String scores) {
		this.scores = scores;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public CourseData getCourseData() {
		return courseData;
	}
	public void setCourseData(CourseData courseData) {
		this.courseData = courseData;
	}
	public CollectData getCollectData() {
		return collectData;
	}
	public void setCollectData(CollectData collectData) {
		this.collectData = collectData;
	}
	public OpusData getOpusData() {
		return opusData;
	}
	public void setOpusData(OpusData opusData) {
		this.opusData = opusData;
	}
	public OcollectData getOcollectData() {
		return ocollectData;
	}
	public void setOcollectData(OcollectData ocollectData) {
		this.ocollectData = ocollectData;
	}
	@Override
	public String toString() {
		return "OtherUser [ocollect=" + ocollect + ", opus=" + opus + ", course=" + course + ", collect=" + collect
				+ ", follow=" + follow + ", fans=" + fans + ", uid=" + uid + ", relation=" + relation + ", region_name="
				+ region_name + ", uname=" + uname + ", gender=" + gender + ", email=" + email + ", des=" + des
				+ ", face_pic=" + face_pic + ", bg_pic=" + bg_pic + ", hand_daren=" + hand_daren + ", level=" + level
				+ ", scores=" + scores + ", _id=" + _id + ", courseData=" + courseData + ", collectData=" + collectData
				+ ", opusData=" + opusData + ", ocollectData=" + ocollectData + "]";
	}
	
	
	
	

}

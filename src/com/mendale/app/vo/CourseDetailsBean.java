package com.mendale.app.vo;

import java.io.Serializable;
import java.util.List;

import com.mendale.app.pojo.Classifications;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.pojo.Titles;

import cn.bmob.v3.BmobObject;

/**
 * 课程详情页
 * @author zx
 *
 */
public class CourseDetailsBean extends BmobObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String host_pic;//图片
	public String subject;//标题
	public String hand_id;//详情页的id
	public String face_pic;//做着的图像
	public String summary;//简介
	public Integer view;//人气
	public Integer laud;//赞
	public Integer collect;//收藏
	public Integer comment_count;//评论
	public List<Step>step;//步骤
	private List<Tool>tools;//工具
    private List<Material>material;//材料
    private List<CommentList>comment_list;//评论列表
    private MyUser author;//author
    private Titles title;//一对一关系
    private Classifications classify;
	
	
	public Classifications getClassify() {
		return classify;
	}
	public void setClassify(Classifications classify) {
		this.classify = classify;
	}
	public List<CommentList> getComment_list() {
		return comment_list;
	}
	public void setComment_list(List<CommentList> comment_list) {
		this.comment_list = comment_list;
	}
	public MyUser getAuthor() {
		return author;
	}
	public void setAuthor(MyUser author) {
		this.author = author;
	}
	public Titles getTitle() {
		return title;
	}
	public void setTitle(Titles title) {
		this.title = title;
	}
	public List<Tool> getTools() {
		return tools;
	}
	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	public List<Material> getMaterial() {
		return material;
	}
	public void setMaterial(List<Material> material) {
		this.material = material;
	}
	public List<CommentList> getCommentList() {
		return comment_list;
	}
	public void setCommentList(List<CommentList> commentList) {
		this.comment_list = commentList;
	}
	
	
	public String getHost_pic() {
		return host_pic;
	}
	public void setHost_pic(String host_pic) {
		this.host_pic = host_pic;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHand_id() {
		return hand_id;
	}
	public void setHand_id(String hand_id) {
		this.hand_id = hand_id;
	}
	public String getFace_pic() {
		return face_pic;
	}
	public void setFace_pic(String face_pic) {
		this.face_pic = face_pic;
	}
	public List<Step> getStep() {
		return step;
	}
	public void setStep(List<Step> step) {
		this.step = step;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getView() {
		return view;
	}
	public void setView(Integer view) {
		this.view = view;
	}
	public Integer getLaud() {
		return laud;
	}
	public void setLaud(Integer laud) {
		this.laud = laud;
	}
	public Integer getCollect() {
		return collect;
	}
	public void setCollect(Integer collect) {
		this.collect = collect;
	}
	public Integer getComment_count() {
		return comment_count;
	}
	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}
	@Override
	public String toString() {
		return "CourseDetailsBean [host_pic=" + host_pic + ", subject=" + subject + ", hand_id=" + hand_id
				+ ", face_pic=" + face_pic + ", summary=" + summary + ", view=" + view + ", laud=" + laud + ", collect="
				+ collect + ", comment_count=" + comment_count + ", step=" + step + ", tools=" + tools + ", material="
				+ material + ", commentList=" + comment_list + "]";
	}
	
	
}

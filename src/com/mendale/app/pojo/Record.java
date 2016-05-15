package com.mendale.app.pojo;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 记录
 * @author zx
 *
 */
public class Record extends BmobObject implements Serializable{
	
	@Override
	public String toString() {
		return "Record [title=" + title + ", content=" + content + ", author=" + author + ", likes=" + likes
				+ ", image=" + image + ", url=" + url + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;//记录标题
	private String content;//记录内容
	private MyUser author;//记录的发布者，一对一关系
	private BmobRelation likes;//对对多关系：用于存储喜欢该记录的所有用户
	private BmobFile image;//记录图片
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MyUser getAuthor() {
		return author;
	}
	public void setAuthor(MyUser author) {
		this.author = author;
	}
	public BmobFile getImage() {
		return image;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}
	public BmobRelation getLikes() {
		return likes;
	}
	public void setLikes(BmobRelation likes) {
		this.likes = likes;
	}
	
	

}

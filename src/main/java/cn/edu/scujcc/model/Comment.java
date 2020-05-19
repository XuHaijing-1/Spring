package cn.edu.scujcc.model;

import java.io.Serializable;
import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 评论对象
 * @author 憨态可拘
 *
 */
public class Comment implements Serializable {
	
	
	private static final long serialVersionUID = 9025921434272724054L;
	private String author;//评论作者
	private String content;//评论内容
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  //日期时间格式
	private LocalDateTime dt=LocalDateTime.now();//评论日期时间
	private int star=0; //评论点赞数量
	
	
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDateTime getDt() {
		return dt;
	}
	public void setDt(LocalDateTime dt) {
		this.dt = dt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dt == null) ? 0 : dt.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dt == null) {
			if (other.dt != null)
				return false;
		} else if (!dt.equals(other.dt))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Comment [content=" + content + ", author=" + author + ", dt=" + dt + "]";
	}
	
	
	
}
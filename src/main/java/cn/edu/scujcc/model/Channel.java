package cn.edu.scujcc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 频道模型对象。
 * @author 憨态可拘
 *
 */
public class Channel implements Serializable {
	private static final long serialVersionUID = 7245499099716015024L;
	@Id
	private String id;   //视频id
	private String title;  //视频标题
	private String quality; //视频清晰度
	private String url;  //视频网址
	private List<Comment>comments;  //评论
	private String cover; //频道封面图片
	private int like=0;  //频道点赞数
	
	
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * 返回所有评论
	 * @return
	 */
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * 向当前频道新增一个评论对象
	 * @param comment
	 */
	public void addComment(Comment comment) {
		if(this.comments==null) {
			this.comments=new ArrayList<>();
		 }
		this.comments.add(comment);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quality == null) ? 0 : quality.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Channel other = (Channel) obj;
		if (quality == null) {
			if (other.quality != null)
				return false;
		} else if (!quality.equals(other.quality))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Channel [id=" + id + ", title=" + title + ", quality=" + quality + ", url=" + url + "]";
	}
	
	
}
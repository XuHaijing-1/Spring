package cn.edu.scujcc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.scujcc.api.ChannelController;
import cn.edu.scujcc.dao.ChannelRepository;
import cn.edu.scujcc.model.Channel;
import cn.edu.scujcc.model.Comment;

@Service
public class ChannelService {

	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	private ChannelRepository repo;
	
	/**
	 * 获取所有频道数据
	 * 
	 * @return频道List
	 */
	public List<Channel>getAllChannels(){
		return repo.findAll();
	}
	
	/**
	 * 获取一个频道的数据
	 * 
	 * 
	 * @param channelId 频道信息
	 * @return 频道对象，若未找到返回null
	 */
	public Channel getChannel(String channelId) {
		Optional<Channel> result=repo.findById(channelId);
		
		if(result.isPresent()) {
			return result.get();
		}else {
			return null;
		}
	}
	
	/**
	 * 删除指定频道的数据
	 * 
	 * @param channelId 待删除的频道数据编号
	 * @return 若成功删除则返回true，否则返回false
	 */
	public boolean deleteChannel(String channelId) {
		boolean result=true;
		repo.deleteById(channelId);
		
		return result;
	}
	
	/**
	 * 保存频道
	 * 
	 * @param c 待保存的频道对象（没有id值）
	 * @return  保存后的频道（有id值）
	 */
	public Channel createChannel(Channel c) {
		return repo.save(c);
	}
	
	/**
	 * 更新指定的频道信息
	 * @param c 新的频道信息，用于更新已存在的同一视频
	 * @return 更新后的频道信息
	 */
	public Channel updateChannel(Channel c) {
		Channel saved=getChannel(c.getId());
		if(saved!=null) {
			if(c.getTitle()!=null) {
			saved.setTitle(c.getTitle());
			}
			if(c.getQuality()!=null) {
			saved.setQuality(c.getQuality());
			}
			if(c.getUrl()!=null) {
				saved.setUrl(c.getUrl());
			}
			if(c.getComments()!=null) {
				if(saved.getComments()!=null) { //把新评论追加到老评论后面
					saved.getComments().addAll(c.getComments());
				}else {//用新评论代替老评论
					saved.setComments(c.getComments());
				}
			}
			logger.debug(saved.toString());
		}
		if(c.getCover()!=null) {
			saved.setCover(c.getCover());
		}
		return repo.save(saved); //保存更新后的实体对象
	}
	
	public List<Channel>titlecxChannel(String title){
		return repo.findByTitle(title);
	}
	public List<Channel>qualitycxChannel(String quality){
		return repo.findByQuality(quality);
	}
	
	/**
	 * 获取今天的评论火爆信息
	 * @return
	 */
	public List<Channel>getLatestCommentsChannel(){
		LocalDateTime now=LocalDateTime.now();
		LocalDateTime today=LocalDateTime.of(now.getYear(),
				now.getMonthValue(),now.getDayOfMonth(),0,0);
		return repo.findByCommentsDtAfter(today);
	}

	/**
	 * 搜索方法
	 * @param title
	 * @param quality
	 * @return
	 */
	 public List<Channel> search(String title,String quality){
		 return repo.findByTitleAndQuality(title, quality);
	 }
	 
	 /**
	  * 向指定频道添加一条评论
	  * @param channelId 指定的频道编号
	  * @param comment  将要新增的评论对象
	  */
	 public Channel addComment(String channelId, Comment comment) {
		 Channel saved = getChannel(channelId);
		 if(saved!=null) {
			 saved.addComment(comment);
			 return repo.save(saved);
		 }
		 return null;
	 }
	 
	 /**
	  * 返回指定频道的热门评论
	  * @param channelId 指定频道的编号
	  * @return 热门评论的列表
	  */
	 public List<Comment> hotComments(String channelId){
		 List<Comment>result=new ArrayList<>();
		 Channel saved=getChannel(channelId);
		 if(saved!=null&&saved.getComments()!=null) {
			 //根据评论的star进行排序
			 saved.getComments().sort(new Comparator<Comment>() {
				 @Override
				 public int compare(Comment o1,Comment o2) {
					 if(o1.getStar()==o2.getStar()) {
						 return 0;
					 }else if(o1.getStar()<o2.getStar()) {
						 return 1;
					 }else {
						 return -1;
					 }
				 }
			 });
			 if(saved.getComments().size()>3) {
				 result = saved.getComments().subList(0, 3);
			 }else {
				 result = saved.getComments();
			 }
		 }
		 return result;
	 }
	 
}
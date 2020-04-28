package cn.edu.scujcc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scujcc.model.Channel;
import cn.edu.scujcc.model.Comment;
import cn.edu.scujcc.service.ChannelService;

@RestController
@RequestMapping("/channel")
public class ChannelController {
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	private ChannelService service;
	
	@GetMapping
	public List<Channel> getAllChannels() {
		logger.info("正在读取所有频道信息...");
		List<Channel>results = service.getAllChannels();
		logger.debug("所有频道的数量是："+results.size());
		return results;
	}
	
	@GetMapping("/{id}")
	public Channel getChannel(@PathVariable String id) {
		logger.info("正在读取"+id+"的频道信息...");
		Channel c=service.getChannel(id);
		if(c != null) {
			return c;
		}else {
			logger.error("找不到指定频道");
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteChannel(@PathVariable String id) {
		System.out.println("即将删除频道：id="+id);
		boolean result=this.service.deleteChannel(id);
		if(result) {
			return ResponseEntity.ok().body("删除成功");
		}else {
			return ResponseEntity.ok().body("删除失败");
		}
	}
	
	@PostMapping
	public Channel createChannel(@RequestBody Channel c) {
		System.out.println("即将新建频道，频道数据："+c);
		Channel saved=service.createChannel(c);
		return saved;
	}
	
	@PutMapping
	public Channel updateChannel(@RequestBody Channel c) {
		System.out.println("即将更新频道，频道数据："+c);
		Channel updated =service.updateChannel(c);
		return updated;
	}
	
	@GetMapping("/t/{title}")
	public List<Channel>titlecx(@PathVariable String title){
		return service.titlecxChannel(title);
	}
	@GetMapping("/q/{quality}")
	public List<Channel>qualitycx(@PathVariable String quality){
		return service.qualitycxChannel(quality);
	}
	
	@GetMapping("/s/{title}/{quality}")
	public List<Channel>search(@PathVariable String title,@PathVariable String quality){
		return service.search(title, quality);
	}
	
	@GetMapping("/hot")
	public List<Channel>getHostChannels(){
		return service.getLatestCommentsChannel();
	}
	
	/**
	 * 新增评论
	 * @param channelId 被评论的频道编号
	 * @param comment 将要新增的评论对象
	 */
	@PostMapping("/{channelId}/comment")
	public Channel addComment(@PathVariable String channelId,@RequestBody Comment comment) {
		logger.debug("将为频道"+channelId+"新增一条评论："+comment);
		// 把评论保存到数据库
		return service.addComment(channelId, comment);
	}
	
	/**
	 * 热门指定频道的热门评论（前3条）
	 * @param channelId 指定的频道编号
	 * @param comment 3条热门评论的列表（数组）
	 */
	@GetMapping("/{channelId}/hotcomments")
	public List<Comment> hotComment(@PathVariable String channelId){
		logger.debug("热门评论"+channelId);
		//从数据库获取热门评论
		return service.hotComments(channelId);
	}
	
}

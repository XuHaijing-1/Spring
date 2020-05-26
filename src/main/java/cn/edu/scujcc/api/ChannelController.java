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
import cn.edu.scujcc.model.Result;
import cn.edu.scujcc.service.ChannelService;
import cn.edu.scujcc.service.UserService;

@RestController
@RequestMapping("/channel")
public class ChannelController {
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	private ChannelService service;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public Result<List<Channel>> getAllChannels() {
		logger.info("正在读取所有频道信息...");
		Result<List<Channel>> result=new Result<List<Channel>>();
		List<Channel> channels = service.getAllChannels();
		result.setData(channels);
		return result;
	}
	
	
	@GetMapping("/{id}")
	public Result<Channel> getChannel(@PathVariable String id) {
		logger.info("正在读取"+id+"的频道信息...");
		Result<Channel> result =new Result<>();
		Channel c=service.getChannel(id);
		if(c!= null) {
			result=result.ok();
			result.setData(c);
		}else {
			logger.error("找不到指定频道");
			result=result.error();
			result.setMessage("找不到指定的频道信息！");
		}
		return result;
	}
	
	@DeleteMapping("/{id}")
	public Result<Channel> deleteChannel(@PathVariable String id) {
		logger.info("即将删除频道：id="+id);
		Result<Channel> result =new Result<>();
		boolean del=service.deleteChannel(id);
		if(del) {
			result= result.ok();
		}else {
			result.setStatus(Result.ERROR);
		}
		return result;
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
	 * 热门指定频道的热门评论（前4条）
	 * @param channelId 指定的频道编号
	 * @param comment 4条热门评论的列表（数组）
	 */
	@GetMapping("/{channelId}/hotcomments")
	public List<Comment> hotComment(@PathVariable String channelId){
		logger.debug("热门评论"+channelId);
		//从数据库获取热门评论
		return service.hotComments(channelId);
	}
	
	
	
	
}

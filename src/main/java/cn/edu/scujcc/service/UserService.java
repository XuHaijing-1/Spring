package cn.edu.scujcc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.scujcc.UserExistException;
import cn.edu.scujcc.dao.UserRepository;
import cn.edu.scujcc.model.User;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * 用户注册，既吧用户信息保存下来
	 * @param user 注册用户信息
	 * @return 保存后的用户信息（包含数据库id）
	 */
	public User register(User user) throws UserExistException{
		User result=null;
		//判断用户名是否数据库已存在
		User saved=repo.findFirstByusername(user.getUsername());
		if(saved==null) {
		result=repo.save(user);
		}else {
			//用户已经存在
			logger.error("用户"+user.getUsername()+"已经存在。");
			throw new UserExistException();
		}
		return result;
	}
	
	/**
	 * 用户登录
	 * @param u
	 * @param p
	 * @return
	 */
	public User login(String u,String p) {
		User result=null;
		result =repo.findOneByUsernameAndPassword(u, p);
		return result;
	}
}

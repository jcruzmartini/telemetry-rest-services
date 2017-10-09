package com.techner.tau.services.service;

import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.User;
import com.techner.tau.services.config.Config;
import com.techner.tau.services.mapper.UserMapper;

public class UserService {

	/** Mapper for Users */
	private final UserMapper userMapper;
	/** slf4j logger */
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	/** auth service */
	private final AuthenticationTokenService service;
	/** security enable */
	private final boolean securityEnable;

	/**
	 * @param userMapper
	 */
	@Inject
	public UserService(UserMapper userMapper, AuthenticationTokenService service, Config config) {
		this.userMapper = userMapper;
		this.service = service;
		this.securityEnable = config.isSecurityEnable();
	}

	/**
	 * Metodo para hacer login de usuario
	 * 
	 * @param user
	 *            usuario
	 * @return
	 */
	public User login(User user) {
		User us = userMapper.login(user);
		if (us != null) {
			if (securityEnable) {
				String token = service.generateToken(us.getEmail());
				us.setToken(token);
			}
		}
		return us;
	}

	@Transactional
	public void insertOrUpdate(User user) {
		User us = userMapper.getUser(user.getId());
		if (us == null) {
			userMapper.insertUser(user);
		} else {
			userMapper.updateUser(user);
		}
	}
}

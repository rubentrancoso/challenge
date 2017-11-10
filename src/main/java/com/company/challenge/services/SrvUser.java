package com.company.challenge.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.challenge.entities.User;
import com.company.challenge.helper.UUIDGen;
import com.company.challenge.repositories.UserRepository;
import com.company.challenge.services.interfaces.ISrvUser;
import com.company.challenge.userapi.message.Message;

@Service
public class SrvUser implements ISrvUser {

	private static final Logger logger = LoggerFactory.getLogger(SrvUser.class);

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	SrvUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Object register(User user) {
		User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
		newUser.setPhones(user.getPhones());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		String newToken = UUIDGen.getUUID();
		newUser.setToken(passwordEncoder.encode(newToken));
		Object message = registerTransaction(newUser);
		if(message instanceof User) {
			((User)message).setToken(newToken);
		}
		return message;
	}
	
	@Transactional
	private Object registerTransaction(User user) {
		Object result;
		try {
			result = userRepository.save(user);
			logger.info("3. " + result.toString());
		} catch(DataIntegrityViolationException e) {
			logger.error(e.getMessage());
			result = new Message(Message.EMAIL_ALREADY_TAKEN);
		}

		return result;
	}

}

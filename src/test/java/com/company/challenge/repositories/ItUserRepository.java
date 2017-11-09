package com.company.challenge.repositories;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.challenge.entities.Phone;
import com.company.challenge.entities.User;
import com.company.challenge.helper.UUIDGen;
import com.company.challenge.userapi.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
public class ItUserRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(ItUserRepository.class);
	
	private final String UUID_MASK = "([a-f]|\\d){32}"; // 32 alphanumeric chars

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		userRepository.deleteAll();
	}

	@Test
	public void saveSingleUser() throws Exception {
		User user = new User("rubentrancoso@gmail.com");
		userRepository.save(user);
		
		then(userRepository.findByEmail("rubentrancoso@gmail.com")).isNotNull();
		logger.info("@@@ saveSingleUser: " + user.toString());	
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void saveDuplicatedUser() throws Exception {
		User user = new User("username@email.com");
		userRepository.save(user);
		user = new User("username@email.com");
		logger.info("@@@ DataIntegrityViolationException: " + user.toString());	
		userRepository.save(user);
	}

	@Test
	public void deleteSingleUser() throws Exception {
		User user = new User("username@email.com");
		userRepository.save(user);
		userRepository.delete(user);	
		user = userRepository.findByEmail("username@email.com");
		then(user).isNull();
		logger.info("@@@ deleteSingleUser: " + user);
	}

	@Test
	public void testUserUUID() throws Exception {
		User user = new User("username@email.com");
		userRepository.save(user);
		
		assertTrue(user.getId().matches(UUID_MASK));
		logger.info("@@@ testUserUUID: " + user.toString());
	}

	@Test
	public void testNoColisionUserUUID() throws Exception {
		User user;
		for(int i=0;i<5000;i++) {
			String emailbox = UUIDGen.getUUID();
			user = new User(emailbox + "@email.com");
			userRepository.save(user);
		}
	}

	@Test
	@Transactional
	public void saveUserWithSinglePhone() throws Exception {
		User user = new User("user@email.com");
		
		Set<Phone> phonesList = new HashSet<Phone>();
		phonesList.add(new Phone(11,991231234));   
		
		user.addPhone(new Phone(11,991231234));
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		
		then(user).isNotNull();
		then(new HashSet<Phone>(user.getPhones())).isEqualTo(phonesList);
		logger.info("@@@ saveUserWithSinglePhone: " + user.toString());
	}

	@Test
	@Transactional
	public void saveUserWithMultiplePhones() throws Exception {
		User user = new User("user@email.com");
		
		Set<Phone> phonesList = new HashSet<Phone>();
		phonesList.add(new Phone(11,991231234));
		phonesList.add(new Phone(11,991232345));
		
		user.addPhone(new Phone(11,991231234));
		user.addPhone(new Phone(11,991232345));
		
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		
		then(user).isNotNull();
		then(new HashSet<Phone>(user.getPhones())).isEqualTo(phonesList);
		logger.info("@@@ saveUserWithMultiplePhones: " + user.toString());
	}
	
	@Test
	@Transactional
	public void saveUserWithDuplicatedPhone() throws Exception {
		User user = new User("user@email.com");

		Set<Phone> phonesList = new HashSet<Phone>();
		phonesList.add(new Phone(11,991231234));
		
		user.addPhone(new Phone(11,991231234));
		user.addPhone(new Phone(11,991231234));

		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		then(user).isNotNull();
		logger.info("@@@ saveUserWithDuplicatedPhone: " + user.toString());
		then(new HashSet<Phone>(user.getPhones())).isEqualTo(phonesList);
	}

	@Test
	@Transactional
	public void deletePhonesFromUser() throws Exception {
		User user = new User("user@email.com");
		
		Set<Phone> phonesList = new HashSet<Phone>();
		phonesList.add(new Phone(11,991231234));
		phonesList.add(new Phone(11,991232345));

		user.addPhone(new Phone(11,991231234));
		user.addPhone(new Phone(11,991232345));
		
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		
		then(user).isNotNull();
		then(user.getPhones()).isEqualTo(phonesList);
		
		user.setPhones(null);
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		
		then(user).isNotNull();
		then(user.getPhones()).isNull();
		logger.info("@@@ deletePhonesFromUser: " + user.toString());
	}

	@Test
	@Transactional
	public void deleteSinglePhoneFromUser() throws Exception {
		User user = new User("user@email.com");
		
		Set<Phone> phonesList = new HashSet<Phone>();
		phonesList.add(new Phone(11,991231234));
		phonesList.add(new Phone(11,991232345));

		Set<Phone> newPhonesList = new HashSet<Phone>();
		newPhonesList.add(new Phone(11,991231234));
		
		user.addPhone(new Phone(11,991231234));
		user.addPhone(new Phone(11,991232345));
		
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		
		then(user).isNotNull();
		then(user.getPhones()).isEqualTo(phonesList);
		
		logger.info("@@@ deleteSinglePhoneFromUser: " + user.toString());
		
		user.removePhone(new Phone(11,991232345));
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		then(user).isNotNull();
		logger.info("@@@ deleteSinglePhoneFromUser: " + user.toString());
		then(user.getPhones()).isEqualTo(newPhonesList);
	}
	
	@Test
	@Transactional
	public void updateUserPhoneList() throws Exception {
		User user = new User("user@email.com");
		
		Set<Phone> phonesList = new HashSet<Phone>();
		phonesList.add(new Phone(11,991231234));
		phonesList.add(new Phone(11,991232345));

		Set<Phone> newPhonesList = new HashSet<Phone>();
		newPhonesList.add(new Phone(11,991231234));
		newPhonesList.add(new Phone(11,991233456));
		
		user.addPhone(new Phone(11,991231234));
		user.addPhone(new Phone(11,991232345));
		
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		
		then(user).isNotNull();
		then(user.getPhones()).isEqualTo(phonesList);
		
		logger.info("@@@ updateUserPhoneList: " + user.toString());
		
		user.removePhone(new Phone(11,991232345));
		user.addPhone(new Phone(11,991233456));
		userRepository.save(user);
		user = userRepository.findByEmail("user@email.com");
		then(user).isNotNull();
		logger.info("@@@ updateUserPhoneList: " + user.toString());
		then(user.getPhones()).isEqualTo(newPhonesList);
	}	
	
}
package com.company.challenge.userapi.endpoints;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.challenge.Application;
import com.company.challenge.entities.Phone;
import com.company.challenge.entities.User;
import com.company.challenge.repositories.UserRepository;
import com.company.challenge.services.SrvUser;
import com.company.challenge.userapi.inputs.Credentials;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class ItUserEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(ItUserEndpoint.class);
	
	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SrvUser userService;
	
	@Before
	public void setup() {
		userRepository.deleteAll();
	}
	
	private Object registerUser(String email, String password) throws Exception {
		User user = new User("User Name", email);
		user.setPassword(password);	
		user.addPhone(new Phone(11,912341234));
		return userService.register(user);
	}
	
//	@Test
//	public void testRestLogin() throws Exception {
//		Credentials credentials = new Credentials("email@domain.com", "123456");
//		Object message = registerUser(credentials.getUsername(), credentials.getPassword());
//		logger.info("@@@ testRestLogin - User registered: "+ ((User)message).toString());
//		ResponseEntity<?> response = this.testRestTemplate.postForEntity("/login", credentials, Object.class);
//		logger.info("@@@ testRestLogin - Login returned: "+ response.getBody());
//		then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////		then(((User)entity.getBody()).getEmail()).isEqualTo("email@domain.com");
//	}


}
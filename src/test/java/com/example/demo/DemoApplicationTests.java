
package com.example.demo;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

//@Import(TestContainerConfiguration.class)
@DataJpaTest
class DemoApplicationTests {

	@Autowired
	private UserRepository userRepository;
	private User user;
	@BeforeEach
	public void setup() {

		user = new User();

		user.setUsername("John Doe");
		user.setPassword("sai");
		user.setEmail("saisasank@gmail.com");
	}

	@Test
	void testSaveUser() {

		User savedUser = userRepository.save(user);

		assertNotNull(savedUser.getId());

		assertEquals("John Doe", savedUser.getUsername());

		assertEquals("saisasank@gmail.com", savedUser.getEmail());
	}
}

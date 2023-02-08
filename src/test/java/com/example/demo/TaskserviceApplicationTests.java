package com.example.demo;

import com.mongodb.client.MongoDatabase;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles(value="test")
class TaskserviceApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldCorrectlyApplySpringProfile() {
		MongoDatabase mongoDatabase = applicationContext.getBean(MongoDatabase.class);
		assertThat(mongoDatabase.getName()).isEqualTo("mydbtest");
	}

}

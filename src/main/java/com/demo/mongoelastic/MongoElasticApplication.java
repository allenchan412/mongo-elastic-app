package com.demo.mongoelastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.demo.mongoelastic"})
@EnableMongoRepositories(value = "com.demo.mongoelastic.repository")
public class MongoElasticApplication {

	public static void main (String[] args) {
		SpringApplication.run(MongoElasticApplication.class, args);
	}

}

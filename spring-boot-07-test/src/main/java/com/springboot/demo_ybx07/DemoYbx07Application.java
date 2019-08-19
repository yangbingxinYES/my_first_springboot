package com.springboot.demo_ybx07;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.springboot.demo_ybx07.dao.mapper")
@SpringBootApplication
public class DemoYbx07Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoYbx07Application.class, args);
	}

}

package org.mark.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import static org.mark.demo.core.ProjectConstant.MAPPER_PACKAGE;

@SpringBootApplication
@MapperScan(basePackages = MAPPER_PACKAGE)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}


package br.com.wss.barbersync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"br.com.wss"})
@SpringBootApplication
public class BarbersyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbersyncApplication.class, args);
	}

}

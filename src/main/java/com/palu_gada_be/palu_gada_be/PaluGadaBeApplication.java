package com.palu_gada_be.palu_gada_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class PaluGadaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaluGadaBeApplication.class, args);
	}

}

package it.clan.esSpringRest.bin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"it.clan.esSpringRest.businessLogic", "it.clan.esSpringRest.rest"})
@EnableJpaRepositories (basePackages = {"it.clan.esSpringRest.dao"})
@EntityScan (basePackages = {"it.clan.esSpringRest.model"})
public class EsSpringRestApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(EsSpringRestApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

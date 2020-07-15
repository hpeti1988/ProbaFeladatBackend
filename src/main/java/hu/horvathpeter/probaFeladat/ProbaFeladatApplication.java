package hu.horvathpeter.probaFeladat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProbaFeladatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProbaFeladatApplication.class, args);
	}

}

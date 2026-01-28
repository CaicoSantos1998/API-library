package github.caicosantos.library.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Program {

	public static void main(String[] args) {
		SpringApplication.run(Program.class, args);
	}

}

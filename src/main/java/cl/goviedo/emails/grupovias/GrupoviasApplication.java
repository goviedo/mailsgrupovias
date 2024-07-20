package cl.goviedo.emails.grupovias;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class GrupoviasApplication {

	public static void main(String[] args) {
		log.info("Fecha Actual: "+ LocalDateTime.now());
		SpringApplication.run(GrupoviasApplication.class, args);
	}

}

package es.ucm.fdi.stonks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class StonksApplication {

	public static void main(String[] args) {
		SpringApplication.run(StonksApplication.class, args);
	}

}

package pharmacy.digitalAsistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitalAsistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalAsistantApplication.class, args);
	}

}

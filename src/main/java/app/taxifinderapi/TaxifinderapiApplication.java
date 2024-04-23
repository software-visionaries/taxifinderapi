package app.taxifinderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import app.taxifinderapi.Config.RSAKeyRecord;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class TaxifinderapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxifinderapiApplication.class, args);
	}

}

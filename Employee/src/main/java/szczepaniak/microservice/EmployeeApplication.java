package szczepaniak.microservice;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableDiscoveryClient

public class EmployeeApplication {

	@Qualifier("eurekaClient")
	@Autowired
	@Lazy
	private EurekaClient eurekaClient;
	@Value("${spring.application.name}")
	private String appName;
	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}



}

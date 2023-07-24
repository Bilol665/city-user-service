package uz.pdp.cityuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CityUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityUserServiceApplication.class, args);
    }

}

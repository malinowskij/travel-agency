package pl.net.malinowski.travelagency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class TravelAgencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApplication.class, args);
    }
}

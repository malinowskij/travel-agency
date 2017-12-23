package pl.net.malinowski.travelagency;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.net.malinowski.travelagency.logic.service.file.FileService;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
public class TravelAgencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApplication.class, args);
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("EmailLookup-");
        executor.initialize();
        return executor;
    }

    @Bean
    CommandLineRunner init(FileService fileService) {
        return (args) -> {
            fileService.init();
        };
    }
}

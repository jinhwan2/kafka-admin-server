package kafkakru.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class KafkaAdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaAdminServerApplication.class, args);
    }

    @RestController
    public static class HomeController {
        @GetMapping("/")
        String home() {
            return "kafka admin server";
        }
    }
}

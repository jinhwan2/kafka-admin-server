package kafkakru.admin.config;

import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaAdminClientProperties.class)
public class KafkaAdminClientConfig {

    private final KafkaAdminClientProperties kafkaAdminClientProperties;

    @Bean
    AdminClient kafkaAdminClient() {
        Properties props = new Properties();
        props.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 5_000); // 5s
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5_000); // 5s
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaAdminClientProperties.getServers());

        return AdminClient.create(props);
    }
}

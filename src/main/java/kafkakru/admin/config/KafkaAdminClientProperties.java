package kafkakru.admin.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Value;

@Value
@ConfigurationProperties("kafka.admin")
public class KafkaAdminClientProperties {
    List<String> servers;
}

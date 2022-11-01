package kafkakru.admin.dto.request;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class ConfigModifyRequest {
    @NonNull
    private Map<String, String> config;
}

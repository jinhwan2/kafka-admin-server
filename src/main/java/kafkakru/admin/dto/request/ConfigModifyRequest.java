package kafkakru.admin.dto.request;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModifyRequest {
    @NonNull
    private Map<String, String> config;
}

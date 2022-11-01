package kafkakru.admin.dto.request;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import kafkakru.admin.dto.OffSetAndMetadata;

@Getter
@NoArgsConstructor
public class OffsetChangeRequest {
    @NonNull
    private Map<String, OffSetAndMetadata> offsets;
}

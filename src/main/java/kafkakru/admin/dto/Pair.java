package kafkakru.admin.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Pair<K,V> {
    K left;
    V right;

    public static <K,V> Pair<K,V> of(K left, V right) {
        return new Pair<>(left, right);
    }
}

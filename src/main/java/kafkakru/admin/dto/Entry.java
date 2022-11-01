package kafkakru.admin.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * 라이브러리 쓰면 참 편한데... 이거 하나 쓰자고 의존성 추가하기 뭐해서 직접 만들었습니다.
 * @param <K> key
 * @param <V> value
 */
@Value
@RequiredArgsConstructor
public class Entry<K,V> {
    K key;
    V value;

    public static <K,V> Entry<K,V> of(K left, V right) {
        return new Entry<>(left, right);
    }
}

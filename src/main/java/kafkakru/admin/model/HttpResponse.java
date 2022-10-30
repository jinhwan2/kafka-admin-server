package kafkakru.admin.model;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class HttpResponse<T> {
    T data;
    HttpStatus httpStatus;

    public static <T>  HttpResponse<T> success(T data) {
        return new HttpResponse<>(data, HttpStatus.OK);
    }
}

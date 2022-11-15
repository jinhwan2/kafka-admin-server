package kafkakru.admin.advice.response;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonInclude;

@Value
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse<T> {
    T data;
    int status;
    String failMessage;


    public static <T>  HttpResponse<T> success(T data) {
        return new HttpResponse<>(data, HttpStatus.OK.value(),  null);
    }

    public static <T> HttpResponse<T> fail(T data, Exception e) {
        return new HttpResponse<>(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

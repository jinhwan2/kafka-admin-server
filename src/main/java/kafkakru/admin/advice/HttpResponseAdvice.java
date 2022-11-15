package kafkakru.admin.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.SneakyThrows;

import kafkakru.admin.advice.response.HttpResponse;
import kafkakru.admin.utils.Converter;

@RestControllerAdvice
public class HttpResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getContainingClass().isAnnotationPresent(RestController.class);
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
        ServerHttpRequest request, ServerHttpResponse response) {

        return Converter.BASIC.getMapper()
            .writeValueAsString(HttpResponse.success(body));
    }

    @ExceptionHandler(Exception.class)
    public HttpResponse<Object> custom(Exception e) {
        return HttpResponse.fail(null, e);
    }
}

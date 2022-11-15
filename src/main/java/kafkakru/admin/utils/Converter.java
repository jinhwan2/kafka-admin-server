package kafkakru.admin.utils;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public enum Converter {
    BASIC{
        @Override
        public ObjectMapper getMapper() {
            return om;
        }
    };

     public abstract ObjectMapper getMapper();

     private static final ObjectMapper om = new ObjectMapper();
}

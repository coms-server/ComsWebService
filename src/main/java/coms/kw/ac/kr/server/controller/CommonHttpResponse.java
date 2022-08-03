package coms.kw.ac.kr.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonHttpResponse {

    public static final ResponseEntity<Object> OK = ResponseEntity.status(HttpStatus.OK).body(null);

    public static final ResponseEntity<Object> ALREADY_REPORTED = ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
            .body(null);

    public static final ResponseEntity<Object> BAD_REQUEST = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    public static final ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

    public static final ResponseEntity<Object> INTERNAL_SERVER_ERROR = ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    public static final <T> ResponseEntity<T> OK(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static final <T> ResponseEntity<T> BAD_REQUEST(Class<T> clazz) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public static final <T> ResponseEntity<T> UNAUTHORIZED(Class<T> clazz) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}

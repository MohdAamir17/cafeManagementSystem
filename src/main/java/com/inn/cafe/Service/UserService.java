package com.inn.cafe.Service;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Map;

public interface UserService extends Serializable {
    ResponseEntity<String> singUp(Map<String, String> requestBody);

    ResponseEntity<String> login(Map<String, String> requestBody);
}

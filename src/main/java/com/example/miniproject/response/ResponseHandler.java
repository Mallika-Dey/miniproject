package com.example.miniproject.response;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Date date, String message, HttpStatus status,
                                                          Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("message", message);
        map.put("status", status.value());
        if (responseObj != null)
            map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(Date date, String message,
                                                          HttpStatus status) {
        return generateResponse(date, message, status, null);
    }
}

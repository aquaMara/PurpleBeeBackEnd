package org.aquam.model.response;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class AppResponse {

    String message;
    HttpStatus httpStatus;
}

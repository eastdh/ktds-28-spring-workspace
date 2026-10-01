package com.ktdsuniversity.edu.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {

  private int httpStatusCode;
  private String httpStatusMessage;

  private T body;

  private String error;

  private Map<String, List<String>> validations;

  public static <T> ApiResponse<T> OK(T t) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.OK.value());
    result.setHttpStatusMessage(HttpStatus.OK.getReasonPhrase());
    result.setBody(t);

    return result;
  }

  public static <T> ApiResponse<T> CREATED(T t) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.CREATED.value());
    result.setHttpStatusMessage(HttpStatus.CREATED.getReasonPhrase());
    result.setBody(t);

    return result;
  }

  public static <T> ApiResponse<T> ERROR(String message) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    result.setHttpStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    result.setError(message);

    return result;
  }

  public static <T> ApiResponse<T> FORBIDDEN(String message) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.FORBIDDEN.value());
    result.setHttpStatusMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
    result.setError(message);

    return result;
  }

  public static <T> ApiResponse<T> BAD_REQUEST(List<FieldError> errors) {

    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
    result.setHttpStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());

    result.validations = new HashMap<>();
    errors.forEach(error -> {
      String fieldName = error.getField();
      if (!result.validations.containsKey(fieldName)) {
        List<String> errorMessages = new ArrayList<>();
        result.validations.put(fieldName, errorMessages);
      }
      result.validations.get(fieldName).add(error.getDefaultMessage());
    });
    return result;
  }

}

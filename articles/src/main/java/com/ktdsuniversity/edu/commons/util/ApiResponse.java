package com.ktdsuniversity.edu.commons.util;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private int httpStatusCode;
  private String httpStatusMessage;
  private T body;

  private String error;

  public static <T> ApiResponse<T> OK(T t) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.OK.value()); // 200
    result.setHttpStatusMessage(HttpStatus.OK.getReasonPhrase()); // OK
    result.setBody(t);

    return result;
  }

  public static <T> ApiResponse<T> CREATED(T t) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.CREATED.value()); // 201
    result.setHttpStatusMessage(HttpStatus.CREATED.getReasonPhrase()); // Created
    result.setBody(t);

    return result;
  }

  public static <T> ApiResponse<T> ERROR(String message) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
    result.setHttpStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    // Internal Server Error
    result.setError(message);

    return result;
  }

  public static <T> ApiResponse<T> FORBIDDEN(String message) {
    ApiResponse<T> result = new ApiResponse<>();
    result.setHttpStatusCode(HttpStatus.FORBIDDEN.value()); // 403
    result.setHttpStatusMessage(HttpStatus.FORBIDDEN.getReasonPhrase()); // Forbidden
    result.setError(message);

    return result;
  }

}

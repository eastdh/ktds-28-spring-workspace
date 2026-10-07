package com.ktdsuniversity.edu.commons.exceptions.handlers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.Messages;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.commons.util.ApiResponse;

/**
 * Programming 용어: GLOBAL ==> 전역
 * --> throw 되는 예외들마다 엔드포인트를 생성.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // ArticleException이 throw되면 아래 메소드가 실행된다.
  @ExceptionHandler(ArticleException.class)
  public ApiResponse<String> sendArticleExceptionMessage(ArticleException ae) {

    ExceptionType type = ae.getType();
    ArticleCodes code = ae.getCodes();

    String message = Messages.getMessage(type, code);

    return ApiResponse.FORBIDDEN(message);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ApiResponse<String> sendValidationExceptionMessage(HandlerMethodValidationException hmve) {
    List<? extends MessageSourceResolvable> errors = hmve.getAllErrors();

    return ApiResponse.BAD_REQUEST(errors);
  }

  // 그 외 전체 예외 처리
  // RuntimeException 을 다루는 메소드는 항상 맨 밑에 작성한다!
  // RuntimeException 을 extends 한 예외들까지 일괄 처리되기 때문...
  @ExceptionHandler(RuntimeException.class)
  public ApiResponse<String> sendRuntimeExceptionMessage(RuntimeException re) {

    logger.error(re.getMessage(), re);

    return ApiResponse.ERROR("요청을 처리할 수 없습니다. 잠시 후 다시 시도해주세요.");
  }

}

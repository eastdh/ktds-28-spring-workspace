package com.ktdsuniversity.edu.commons.beans.interceptors;

import java.io.PrintWriter;
import org.springframework.web.servlet.HandlerInterceptor;
import com.google.gson.Gson;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckSessionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // 세션을 검사하고
    HttpSession session = request.getSession();
    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");

    if (membersVO != null) {
      // 1. 세션이 있으면 컨트롤러를 실행
      return true;
    } else {
      // 2. 세션이 없으면 컨트롤러를 실행 X ==> 클라이언트에게 예외 메시지 전달
      // 이 때, 반환 타입이 boolean이기 때문에 메시지를 직접 전달해야 함

      // Response 의 Content-Type을 JSON으로 설정
      response.setContentType("application/json");

      // Response의 encoding을 UTF-8로 설정
      response.setCharacterEncoding("UTF-8");

      // 클라이언트에게 응답 메시지를 직접 전달할 수 있는 객체
      // Servlet Code 를 작성할 때 필수 코드
      PrintWriter printWriter = response.getWriter();

      ApiResponse<String> errorResponse = ApiResponse.FORBIDDEN("로그인이 필요한 기능입니다.");
      // errorResponse ==> JSON으로 변환! (Jackson Databind ==> @ResponseBody, Gson)
      Gson gson = new Gson();
      String errorJson = gson.toJson(errorResponse);
      // printWriter에게 write
      printWriter.write(errorJson);


      // printWriter.write("{ JSON 메시지 직접 작성 (body, message, error, ...)}");

      // printWriter에게 작성한 내용들이 클라이언트에게 전달된다.
      printWriter.flush();

      return false;
    }

    // super.preHandle ==> 아무것도 작성하지 않으면 그냥 true 반환
    // return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}

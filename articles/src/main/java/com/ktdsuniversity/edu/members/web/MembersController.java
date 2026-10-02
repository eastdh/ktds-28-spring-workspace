package com.ktdsuniversity.edu.members.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.request.LoginMemberVO;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/members")
public class MembersController {

  private MembersService membersService;

  @PostMapping
  public ApiResponse<MembersVO> createNewMember(@Valid @RequestBody RegistMembersVO registMembersVO,
      BindingResult validationResult) {

    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }

    try {
      MembersVO membersVO = this.membersService.createNewMember(registMembersVO);
      // 가입된 회원의 정보를 반환
      return ApiResponse.OK(membersVO);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @GetMapping("/login")
  public ApiResponse<MembersVO> loginMember(@Valid @ModelAttribute LoginMemberVO loginMemberVO,
      BindingResult validationResult, HttpSession session) {

    System.out.println(session.getId() + " <--- SessionID");

    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }

    try {
      MembersVO loggedMember = this.membersService.readMember(loginMemberVO);

      // HttpSession에 로그인 한 사용자의 정보를 기억시킨다.
      session.setAttribute("__LOGIN_USER__", loggedMember);

      return ApiResponse.OK(loggedMember);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @GetMapping("/logout")
  public ApiResponse<String> logout(HttpSession session) {
    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (membersVO == null) {
      throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
    }

    // session 만료 처리
    // 만료된 session 의 ID 는 더 이상 사용할 수 없음
    session.invalidate();

    String email = this.membersService.updateLogoutStatus(membersVO.getEmail());
    if (email == null) {

    }
    return ApiResponse.OK(email);
  }

  @DeleteMapping("/exit")
  public ApiResponse<String> exitMember(HttpSession session, @RequestParam String password) {
    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (membersVO == null) {
      throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
    }

    String email = this.membersService.deleteMember(membersVO.getEmail(), password);

    session.invalidate();

    return ApiResponse.OK(email);
  }


}

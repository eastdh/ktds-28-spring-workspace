package com.ktdsuniversity.edu.members.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
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


}

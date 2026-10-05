package com.ktdsuniversity.edu.members.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginMemberVO {
  @NotBlank(message = "이메일을 입력해주세요.")
  @Email(
      regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      message = "올바른 이메일을 입력하세요.")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,64})",
      message = "영문 대소문자, 숫자, 특수문자를 최소 1개씩 포함한 8자리 이상의 비밀번호를 입력하세요.")
  private String password;
}

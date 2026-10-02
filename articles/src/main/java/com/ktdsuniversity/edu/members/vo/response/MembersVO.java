package com.ktdsuniversity.edu.members.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MembersVO {
  private String email;
  private String name;
  private String nickname;
  @JsonIgnore
  private String password;
  private String registDate;
  private String modifyDate;
  private String latestLoginSuccessDate;
  private String latestLoginFailDate;
  private String latestLogoutDate;
  private int loginFailCount;
  private String loginBlockYn;
  private String loginBlockDate;
  private String loginYn;
  @JsonIgnore
  private String salt;
  private String delYn;
}

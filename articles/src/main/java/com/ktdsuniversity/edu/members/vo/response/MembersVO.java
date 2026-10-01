package com.ktdsuniversity.edu.members.vo.response;

import lombok.Data;

@Data
public class MembersVO {
  private String email;
  private String name;
  private String nickname;
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
  private String salt;
  private String delYn;
}

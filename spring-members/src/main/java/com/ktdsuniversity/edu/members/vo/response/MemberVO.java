package com.ktdsuniversity.edu.members.vo.response;

public class MemberVO {
  private String email;
  private String name;
  private String nickname;
  private String password;
  private String registDate;
  private String modifyDate;
  private String latestLoginSuccessDate;
  private String latestLoginFailDate;
  private String latestLogoutDate;
  private long loginFailCount;
  private String loginBlockYn;
  private String loginBlockDate;
  private String loginYn;
  private String salt;
  private String delYn;

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRegistDate() {
    return this.registDate;
  }

  public void setRegistDate(String registDate) {
    this.registDate = registDate;
  }

  public String getModifyDate() {
    return this.modifyDate;
  }

  public void setModifyDate(String modifyDate) {
    this.modifyDate = modifyDate;
  }

  public String getLatestLoginSuccessDate() {
    return this.latestLoginSuccessDate;
  }

  public void setLatestLoginSuccessDate(String latestLoginSuccessDate) {
    this.latestLoginSuccessDate = latestLoginSuccessDate;
  }

  public String getLatestLoginFailDate() {
    return this.latestLoginFailDate;
  }

  public void setLatestLoginFailDate(String latestLoginFailDate) {
    this.latestLoginFailDate = latestLoginFailDate;
  }

  public String getLatestLogoutDate() {
    return this.latestLogoutDate;
  }

  public void setLatestLogoutDate(String latestLogoutDate) {
    this.latestLogoutDate = latestLogoutDate;
  }

  public long getLoginFailCount() {
    return this.loginFailCount;
  }

  public void setLoginFailCount(long loginFailCount) {
    this.loginFailCount = loginFailCount;
  }

  public String getLoginBlockYn() {
    return this.loginBlockYn;
  }

  public void setLoginBlockYn(String loginBlockYn) {
    this.loginBlockYn = loginBlockYn;
  }

  public String getLoginBlockDate() {
    return this.loginBlockDate;
  }

  public void setLoginBlockDate(String loginBlockDate) {
    this.loginBlockDate = loginBlockDate;
  }

  public String getLoginYn() {
    return this.loginYn;
  }

  public void setLoginYn(String loginYn) {
    this.loginYn = loginYn;
  }

  public String getSalt() {
    return this.salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getDelYn() {
    return this.delYn;
  }

  public void setDelYn(String delYn) {
    this.delYn = delYn;
  }

  @Override
  public String toString() {
    return "MemberVO [email=" + email + ", name=" + name + ", nickname=" + nickname + ", password="
        + password + ", registDate=" + registDate + ", modifyDate=" + modifyDate
        + ", latestLoginSuccessDate=" + latestLoginSuccessDate + ", latestLoginFailDate="
        + latestLoginFailDate + ", latestLogoutDate=" + latestLogoutDate + ", loginFailCount="
        + loginFailCount + ", loginBlockYn=" + loginBlockYn + ", loginBlockDate=" + loginBlockDate
        + ", loginYn=" + loginYn + ", salt=" + salt + ", delYn=" + delYn + "]";
  }


}

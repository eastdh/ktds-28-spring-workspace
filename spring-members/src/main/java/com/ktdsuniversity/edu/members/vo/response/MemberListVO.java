package com.ktdsuniversity.edu.members.vo.response;

import java.util.List;

public class MemberListVO {

  private long membersCount;
  private List<MemberVO> membersList;

  public long getMembersCount() {
    return this.membersCount;
  }

  public void setMembersCount(long membersCount) {
    this.membersCount = membersCount;
  }

  public List<MemberVO> getMembersList() {
    return this.membersList;
  }

  public void setMembersList(List<MemberVO> membersList) {
    this.membersList = membersList;
  }

  @Override
  public String toString() {
    return "MemberListVO [membersCount=" + membersCount + ", membersList=" + membersList + "]";
  }



}

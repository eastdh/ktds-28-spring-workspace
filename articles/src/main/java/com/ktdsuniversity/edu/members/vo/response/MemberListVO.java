package com.ktdsuniversity.edu.members.vo.response;

import java.util.List;
import lombok.Data;

@Data
public class MemberListVO {
  private long memberCount;
  private List<MembersVO> memberList;
}

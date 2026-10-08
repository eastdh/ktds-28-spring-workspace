package com.ktdsuniversity.edu.members.vo.request;

import com.ktdsuniversity.edu.commons.vo.PaginationVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 멤버 검색을 위한 VO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchMemberVO extends PaginationVO {
  private String name;
  private String nickname;
  private String email;
  private String registDate;

}

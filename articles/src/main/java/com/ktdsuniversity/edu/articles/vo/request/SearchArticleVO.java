package com.ktdsuniversity.edu.articles.vo.request;

import com.ktdsuniversity.edu.commons.vo.PaginationVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 게시글 검색을 위한 VO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchArticleVO extends PaginationVO {

  private String subject;
  private String content;
  private String filename;
  private String name;
  private String nickname;
}

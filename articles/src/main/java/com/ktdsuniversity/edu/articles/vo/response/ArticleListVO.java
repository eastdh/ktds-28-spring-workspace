package com.ktdsuniversity.edu.articles.vo.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleListVO {

  /**
   * 검색된 게시글의 총 개수
   */
  private long articleCount;
  /**
   * 검색된 게시글의 목록
   */
  private List<ArticlesVO> articleList;



}

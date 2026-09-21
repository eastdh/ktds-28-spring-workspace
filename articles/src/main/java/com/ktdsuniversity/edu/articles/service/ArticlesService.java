package com.ktdsuniversity.edu.articles.service;

import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;

public interface ArticlesService {

  /**
   * 게시글의 목록을 조회
   * 
   * @return ArticleListVo
   */
  ArticleListVO readAllArticles();
}

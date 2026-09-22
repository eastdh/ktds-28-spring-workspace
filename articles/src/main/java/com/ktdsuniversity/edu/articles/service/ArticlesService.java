package com.ktdsuniversity.edu.articles.service;

import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

public interface ArticlesService {

  /**
   * 게시글의 목록을 조회
   * 
   * @return ArticleListVo
   */
  ArticleListVO readAllArticles();

  /**
   * 게시글의 등록을 요청
   * 
   * @param registArticleVO
   * @return
   */
  ArticlesVO createNewArticle(RegistArticleVO registArticleVO);

  /**
   * 게시글 수정
   * 
   * @param articleId 수정 대상 게시글 id
   * @param modifyArticleVO 수정 내용
   * @return 수정 결과
   */
  ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO);

  /**
   * 게시글 삭제
   * 
   * @param articleId
   * @return 삭제한 게시글의 PK
   */
  String deleteArticle(String articleId);

  /**
   * 게시글 조회
   * 게시글의 조회수를 증가시키고 게시글 정보를 반환한다.
   * 
   * @param articleId
   * @return 게시글 정보
   */
  ArticlesVO readOneArticle(String articleId);

  /**
   * 게시글 추천
   * 게시글의 추천수를 증가시키고 추천수를 반환한다.
   * 
   * @param articleId
   * @return 게시글 정보
   */
  long recommendOneArticle(String articleId);
}

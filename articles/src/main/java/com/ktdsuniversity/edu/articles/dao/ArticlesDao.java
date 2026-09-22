package com.ktdsuniversity.edu.articles.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

/**
 * Spring의 @Repository를 한 번 감싼 애노테이션
 * 마이바티스가 익명의 클래스를 만들어서 데이터베이스에 접근하도록 한다.
 */
@Mapper
public interface ArticlesDao {

  /**
   * 게시글의 총 개수를 반환
   * 
   * @return long 게시글의 총 개수
   */
  long selectArticlesCount();

  /**
   * 게시글을 검색해서 반환
   * 
   * @return List<ArticlesVO> 전체 게시글의 리스트
   */
  List<ArticlesVO> selectAllArticles();

  /**
   * 클라이언트가 보내준 게시글 등록 정보를 데이터베이스에 insert 한다.
   * 
   * @param registArticleVO
   * @return insert 한 row 개수
   */
  int insertNewArticle(RegistArticleVO registArticleVO);

  /**
   * 게시글의 아이디로 게시글 정보를 조회한다.
   * 
   * @param articleId 게시글의 PK
   * @return 게시글의 PK로 조회한 게시글 정보
   */
  ArticlesVO selectArticleByArticleId(String articleId);


  /**
   * 게시글의 아이디와 이메일로 본인의 게시글인지 조회한 후
   * 게시글 수정 정보로 해당 게시글을 수정한다.
   * 
   * @param articleId
   * @param modifyArticleVO
   * @return
   */
  int updateArticle(@Param("articleId") String articleId,
      @Param("modifyArticleVO") ModifyArticleVO modifyArticleVO);

  /**
   * 게시글의 아이디로 해당 게시글을 삭제한다.
   * 이 때 물리적으로 삭제하는 것이 아니라 DEL_YN 값을 변경한다.
   * 
   * @param articleId
   * @return
   */
  int deleteArticle(String articleId);

  /**
   * 게시글의 조회수를 1 증가시킨다.
   * 
   * @param articleId
   * @return
   */
  int updateIncreaseViewCount(String articleId);

  /**
   * 게시글의 추천수를 1 증가시킨다.
   * 
   * @param articleId
   * @return
   */
  int updateIncreaseRecommendCount(String articleId);

  /**
   * 게시글의 추천수를 반환한다.
   * 
   * @param articleId
   * @return
   */
  long getRecommendCount(String articleId);


}

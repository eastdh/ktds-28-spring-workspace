package com.ktdsuniversity.edu.articles.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
}

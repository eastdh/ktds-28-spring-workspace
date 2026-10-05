package com.ktdsuniversity.edu.articles.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

@Mapper
public interface ArticlesDao {

  public long selectArticleCount();

  public List<ArticlesVO> selectAllArticles();

  public int insertNewArticle(RegistArticleVO registArticleVO);

  public ArticlesVO selectArticleByArticleId(String id);

  public int updateArticle(String articleId, ModifyArticleVO modifyArticleVO);

  public int deleteArticle(String articleId);

  public int updateIncreaseViewCount(String articleId);

  public int updateIncreaseRecommendCount(String articleId);

  public long getRecommendCount(String articleId);

}

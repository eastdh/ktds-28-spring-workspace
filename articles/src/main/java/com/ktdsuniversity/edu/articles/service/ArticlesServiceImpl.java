package com.ktdsuniversity.edu.articles.service;

import org.springframework.stereotype.Service;
import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {

  private ArticlesDao articlesDao;


  @Override
  public ArticleListVO readAllArticles() {
    ArticleListVO list = new ArticleListVO();

    list.setArticleCount(articlesDao.selectArticlesCount());
    list.setArticleList(articlesDao.selectAllArticles());


    return list;
  }


  @Override
  public ArticlesVO createNewArticle(RegistArticleVO registArticleVO) {
    int insertedRows = this.articlesDao.insertNewArticle(registArticleVO);

    // INSERT한 게시글의 ID로 게시글 정보를 조회한다.
    // -> INSERT한 게시글의 ID가 뭔지 모른다

    System.out.println(insertedRows + "개의 Row가 생성되었습니다.");

    if (insertedRows > 0) {
      return this.articlesDao.selectArticleByArticleId(registArticleVO.getId());
    }

    throw new IllegalArgumentException("입력값이 유효하지 않습니다.");

  }


  @Override
  public ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO) {

    int updatedRows = this.articlesDao.updateArticle(articleId, modifyArticleVO);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.selectArticleByArticleId(articleId);
  }


  @Override
  public String deleteArticle(String articleId) {
    int deletedRows = this.articlesDao.deleteArticle(articleId);

    if (deletedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return articleId;
  }


  @Override
  public ArticlesVO readOneArticle(String articleId) {

    int updatedRows = this.articlesDao.updateIncreaseViewCount(articleId);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.selectArticleByArticleId(articleId);
  }


  @Override
  public long recommendOneArticle(String articleId) {
    int updatedRows = this.articlesDao.updateIncreaseRecommendCount(articleId);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.getRecommendCount(articleId);
  }

}

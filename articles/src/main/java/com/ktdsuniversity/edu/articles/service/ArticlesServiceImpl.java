package com.ktdsuniversity.edu.articles.service;

import org.springframework.stereotype.Service;
import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;

@Service
public class ArticlesServiceImpl implements ArticlesService {

  private ArticlesDao articlesDao;

  public ArticlesServiceImpl(ArticlesDao articlesDao) {
    this.articlesDao = articlesDao;
  }

  @Override
  public ArticleListVO readAllArticles() {
    ArticleListVO list = new ArticleListVO();

    list.setArticleCount(articlesDao.selectArticlesCount());
    list.setArticleList(articlesDao.selectAllArticles());


    return list;
  }

}

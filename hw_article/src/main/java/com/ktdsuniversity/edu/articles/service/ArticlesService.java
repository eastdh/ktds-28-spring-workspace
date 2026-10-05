package com.ktdsuniversity.edu.articles.service;

import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;

public interface ArticlesService {

  ArticleListVO readAllArticles();

  ArticlesVO createNewArticle(RegistArticleVO registArticleVO);

  ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO);

  String deleteArticle(String articleId);

  ArticlesVO readOneArticle(String articleId);

  long recommendArticle(String articleId);

}

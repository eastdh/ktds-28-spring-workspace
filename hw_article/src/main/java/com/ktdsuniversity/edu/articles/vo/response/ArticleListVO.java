package com.ktdsuniversity.edu.articles.vo.response;

import java.util.List;
import lombok.Data;

@Data
public class ArticleListVO {

  private long articleCount;

  private List<ArticlesVO> articleList;

}

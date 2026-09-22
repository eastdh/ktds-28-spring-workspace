package com.ktdsuniversity.edu.articles.vo.request;

import lombok.Data;

@Data
public class ModifyArticleVO {
  private String subject;
  private String content;
  private String email;
}

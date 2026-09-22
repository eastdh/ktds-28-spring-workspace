package com.ktdsuniversity.edu.articles.vo.request;

import lombok.Data;

/**
 * 게시글 등록을 위해 클라이언트가 보내준 정보를 받아오기 위한 VO 클래스
 * 보통, 테이블에서 필수 입력값 (Not Null) 컬럼들을 작성한다.
 */
@Data
public class RegistArticleVO {

  private String id;
  private String subject;
  private String content;
  private String email;
}

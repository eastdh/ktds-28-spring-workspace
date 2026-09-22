package com.ktdsuniversity.edu.articles.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Getter // 멤버 변수들의 Getter 자동 생성
// @Setter // 멤버 변수들의 Setter 자동 생성
// @ToString // toString 메소드 자동 생성
@AllArgsConstructor // 모든 멤버 변수들을 파라미터로 가지는 생성자를 자동으로 생성
@NoArgsConstructor // 기본 생성자를 자동으로 생성
@Data // Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor 한번에 만들기
public class ArticlesVO {

  private String id;
  private String subject;
  private String content;
  private String email;
  private long viewCnt;
  private long recommendCnt;
  private String delYn;
  private String crtDt;
  private String mdfyDt;
  private String fileSetId;

}

package com.ktdsuniversity.edu.articles.vo.response;

import com.ktdsuniversity.edu.files.vo.response.FileSetVO;
import lombok.Data;

@Data
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

  private FileSetVO fileSet;
}

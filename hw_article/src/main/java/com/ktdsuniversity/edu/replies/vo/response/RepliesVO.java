package com.ktdsuniversity.edu.replies.vo.response;

import com.ktdsuniversity.edu.files.vo.response.FileSetVO;
import lombok.Data;

@Data
public class RepliesVO {
  private String id;
  private String articleId;
  private String email;
  private String content;
  private long recommendCnt;
  private String delYn;
  private String crtDt;
  private String mdfyDt;
  private String fileSetId;

  private FileSetVO fileSet;
}

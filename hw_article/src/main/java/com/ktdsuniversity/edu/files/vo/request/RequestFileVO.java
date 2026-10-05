package com.ktdsuniversity.edu.files.vo.request;

import lombok.Data;

@Data
public class RequestFileVO {
  private String fileSetId;
  private String displayFileName;
  private String obfuscateFileName; // 난독화된 파일 명
  private long fileSize;
}

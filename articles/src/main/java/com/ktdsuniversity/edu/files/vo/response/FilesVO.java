package com.ktdsuniversity.edu.files.vo.response;

import lombok.Data;

@Data
public class FilesVO {

  private String id;
  private String fileSetId;
  private String displayFileName;
  private String obfuscateFileName;
  private long fileSize;
  private long downloadCount;
  private String delYn;
}

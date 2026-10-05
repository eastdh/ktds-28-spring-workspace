package com.ktdsuniversity.edu.files.vo.response;

import java.util.List;
import lombok.Data;

@Data
public class FileSetVO {

  private String id;
  private String email;
  private String crtDt;
  private String mdfyDt;

  private List<FilesVO> files;
}

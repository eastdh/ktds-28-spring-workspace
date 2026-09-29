package com.ktdsuniversity.edu.articles.vo.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class ModifyArticleVO {
  private String subject;
  private String content;
  private String email;
  private List<MultipartFile> file;
  private String fileSetId;
}

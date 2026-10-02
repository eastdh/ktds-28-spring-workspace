package com.ktdsuniversity.edu.articles.vo.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModifyArticleVO {
  @NotBlank(message = "제목을 입력해주세요.")
  private String subject;
  private String content;
  private String email;
  private List<MultipartFile> file;
  private String fileSetId;
}

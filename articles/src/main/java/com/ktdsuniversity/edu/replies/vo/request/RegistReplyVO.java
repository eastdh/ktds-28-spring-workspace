package com.ktdsuniversity.edu.replies.vo.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistReplyVO {

  private String id;
  private String articleId;
  private String content;
  @NotBlank(message = "이메일을 작성해주세요.")
  @Email(message = "올바른 이메일을 작성하세요.")
  private String email;
  private List<MultipartFile> file;
  private String fileSetId;
}

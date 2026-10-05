package com.ktdsuniversity.edu.replies.vo.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class RegistReplyVO {
  private String id;
  private String content;
  private String email;
  private List<MultipartFile> file;
  private String fileSetId;
}

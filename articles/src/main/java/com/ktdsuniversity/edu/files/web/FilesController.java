package com.ktdsuniversity.edu.files.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ktdsuniversity.edu.files.service.FilesService;
import com.ktdsuniversity.edu.files.vo.response.FilesVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class FilesController {

  private FilesService filesService;

  @GetMapping("/filesets/{fileSetId}/files/download/{fileId}")
  public void downloadFile(@PathVariable String fileSetId, @PathVariable String fileId) {

    FilesVO filesVO = this.filesService.readAttachFile(fileSetId, fileId);

  }
}

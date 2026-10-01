package com.ktdsuniversity.edu.files.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ktdsuniversity.edu.files.service.FilesService;
import com.ktdsuniversity.edu.files.vo.response.FilesVO;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FilesController {

  @Value("${app.multipart.store-path}")
  private String storePath;
  private final FilesService filesService;

  // http://localhost:8080/filesets/FS-20260929-000011/files/download/FL-20260929-000030
  @GetMapping("/filesets/{fileSetId}/files/download/{fileId}")
  public ResponseEntity<Resource> downloadFile(
      @Pattern(regexp = "^FS-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String fileSetId,
      @Pattern(regexp = "^FL-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String fileId) {

    FilesVO filesVO = this.filesService.readAttachFile(fileSetId, fileId);

    File uploadFolder = new File(System.getProperty("user.home"), this.storePath);

    File downloadFile = new File(uploadFolder, filesVO.getObfuscateFileName());

    // downloadFile을 binary 로 변환 ==> InputStreamResource
    InputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(downloadFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    InputStreamResource resource = new InputStreamResource(fileInputStream);

    HttpHeaders responseHeader = new HttpHeaders();
    // 브라우저 화면에 보여주지 말고 파일로 다운로드하도록 지시
    responseHeader.setContentType(new MediaType("application", "force-download"));

    String fileName = filesVO.getDisplayFileName();
    // URL에서 영문자와 숫자를 제외한 나머지 글자들은 정상적으로 표현되지 않는다.
    // 한글, 중국어 등등의 언어는 제대로 표현이 되지 않는다!
    // 다국어 지원을 위해 만들어진 브라우저 전용 함수 ==> URLEncoder
    // 한글, 중국어 등등의 다국어를 포함한 특수기호들을 정상적으로 표현가능하게 해줌
    // 파일의 이름을 URLEncoding해주는 것이 필요
    fileName = URLEncoder.encode(fileName, Charset.defaultCharset());

    // 다운로드할 파일의 이름을 지정
    responseHeader.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

    // 다운로드할 파일의 크기를 지정
    responseHeader.setContentLength(filesVO.getFileSize());

    return ResponseEntity.ok().headers(responseHeader).body(resource);

  }
}

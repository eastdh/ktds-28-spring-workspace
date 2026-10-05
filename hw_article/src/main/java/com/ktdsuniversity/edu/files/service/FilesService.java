package com.ktdsuniversity.edu.files.service;

import com.ktdsuniversity.edu.files.vo.response.FilesVO;

public interface FilesService {

  FilesVO readAttachFile(String fileSetId, String fileId);

}

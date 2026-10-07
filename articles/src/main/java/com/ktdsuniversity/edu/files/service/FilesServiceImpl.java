package com.ktdsuniversity.edu.files.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.response.FilesVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

  private static final Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

  private FilesDao filesDao;

  @Override
  public FilesVO readAttachFile(String fileSetId, String fileId) {
    FilesVO filesVO = this.filesDao.selectAttachFile(fileSetId, fileId);
    if (filesVO == null) {
      throw new ArticleException(ExceptionType.FILES, ArticleCodes.BAD_REQUEST);
    }

    int updatedRows = this.filesDao.updateIncreaseDownloadCount(fileSetId, fileId);
    logger.info("{}건이 변경되었습니다.", updatedRows);

    return filesVO;
  }

}

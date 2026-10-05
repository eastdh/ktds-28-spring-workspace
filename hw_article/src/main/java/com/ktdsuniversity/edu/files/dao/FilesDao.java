package com.ktdsuniversity.edu.files.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.files.vo.request.RequestFileSetVO;
import com.ktdsuniversity.edu.files.vo.request.RequestFileVO;
import com.ktdsuniversity.edu.files.vo.response.FilesVO;

@Mapper
public interface FilesDao {

  int insertNewFileSet(RequestFileSetVO fileSetVO);

  void insertNewFile(RequestFileVO fileVO);

  void updateFileSet(String fileSetId);

  List<FilesVO> selectFilesByFileSetId(String fileSetId);

  int deleteFilesByFileSetId(String fileSetId);

  FilesVO selectAttachFile(String fileSetId, String fileId);

  int updateIncreaseDownloadCount(String fileSetId, String fileId);

}

package com.ktdsuniversity.edu.articles.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.RequestFileSetVO;
import com.ktdsuniversity.edu.files.vo.request.RequestFileVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {

  private ArticlesDao articlesDao;
  private FilesDao filesDao;


  @Override
  public ArticleListVO readAllArticles() {
    ArticleListVO list = new ArticleListVO();

    list.setArticleCount(articlesDao.selectArticlesCount());
    list.setArticleList(articlesDao.selectAllArticles());


    return list;
  }


  @Override
  public ArticlesVO createNewArticle(RegistArticleVO registArticleVO) {

    if (registArticleVO.getFile() != null) {
      // FILE_SET 생성
      RequestFileSetVO fileSetVO = new RequestFileSetVO();
      fileSetVO.setEmail(registArticleVO.getEmail());

      int fileSetInsertCount = this.filesDao.insertNewFileSet(fileSetVO);
      if (fileSetInsertCount == 0) {
        throw new IllegalArgumentException("파일 세트 생성을 할 수 없습니다.");
      }

      registArticleVO.setFileSetId(fileSetVO.getId());

      for (MultipartFile f : registArticleVO.getFile()) {
        // 사용자가 업로드한 파일을 서버 컴퓨터에 저장한다.
        // 1. 저장할 위치 선정
        // - 사용자 홈 디렉토리 찾기
        String homeDirectory = System.getProperty("user.home");
        File uploadFolder = new File(homeDirectory, "uploadFiles");
        if (!uploadFolder.exists()) {
          uploadFolder.mkdirs();
        }
        // - 파일이 저장될 위치와 이름 지정하기
        // File storeFile = new File(uploadFolder, f.getOriginalFilename());
        // UUID를 활용한 파일명 난수화
        File storeFile = new File(uploadFolder, UUID.randomUUID().toString());

        // 2. 파일 저장
        try {
          f.transferTo(storeFile);

          // FILES 데이터 생성
          RequestFileVO fileVO = new RequestFileVO();
          fileVO.setFileSetId(fileSetVO.getId());
          fileVO.setDisplayFileName(f.getOriginalFilename());
          fileVO.setObfuscateFileName(storeFile.getName());
          fileVO.setFileSize(storeFile.length());

          this.filesDao.insertNewFile(fileVO);
        } catch (IllegalStateException | IOException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      }
    }
    int insertedRows = this.articlesDao.insertNewArticle(registArticleVO);

    // INSERT한 게시글의 ID로 게시글 정보를 조회한다.
    // -> INSERT한 게시글의 ID가 뭔지 모른다

    System.out.println(insertedRows + "개의 Row가 생성되었습니다.");

    if (insertedRows > 0) {
      return this.articlesDao.selectArticleByArticleId(registArticleVO.getId());
    }

    throw new IllegalArgumentException("입력값이 유효하지 않습니다.");

  }


  @Override
  public ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO) {

    ArticlesVO originalArticle = this.articlesDao.selectArticleByArticleId(articleId);

    // 게시글 작성자 본인이 아니면 Exception
    if (!originalArticle.getEmail().equals(modifyArticleVO.getEmail())) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    // 파일이 추가되었으면
    if (modifyArticleVO.getFile() != null) {
      // 기존에 파일이 없었으면
      if (originalArticle.getFileSet() == null || originalArticle.getFileSetId().isBlank()) {
        // FILE_SET 생성
        RequestFileSetVO fileSetVO = new RequestFileSetVO();
        fileSetVO.setEmail(originalArticle.getEmail());

        int fileSetInsertCount = this.filesDao.insertNewFileSet(fileSetVO);
        if (fileSetInsertCount == 0) {
          throw new IllegalArgumentException("파일 세트 생성을 할 수 없습니다.");
        }

        originalArticle.setFileSetId(fileSetVO.getId());

      }
      modifyArticleVO.setFileSetId(originalArticle.getFileSetId());
      int fileSetUpdateCount = this.filesDao.updateFileSet(modifyArticleVO.getFileSetId());
      if (fileSetUpdateCount == 0) {
        throw new IllegalArgumentException("파일 세트 수정을 할 수 없습니다.");
      }

      for (MultipartFile f : modifyArticleVO.getFile()) {
        String homeDirectory = System.getProperty("user.home");
        File uploadFolder = new File(homeDirectory, "uploadFiles");
        if (!uploadFolder.exists()) {
          uploadFolder.mkdirs();
        }
        File storeFile = new File(uploadFolder, UUID.randomUUID().toString());
        try {
          f.transferTo(storeFile);

          // FILES 데이터 생성
          RequestFileVO fileVO = new RequestFileVO();
          fileVO.setFileSetId(modifyArticleVO.getFileSetId());
          fileVO.setDisplayFileName(f.getOriginalFilename());
          fileVO.setObfuscateFileName(storeFile.getName());
          fileVO.setFileSize(storeFile.length());

          this.filesDao.insertNewFile(fileVO);
        } catch (IllegalStateException | IOException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      }
    }

    int updatedRows = this.articlesDao.updateArticle(articleId, modifyArticleVO);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.selectArticleByArticleId(articleId);
  }


  @Override
  public String deleteArticle(String articleId) {

    long deleteTarget = this.filesDao.selectFilesCountByArticleId(articleId);
    int deletedRows = this.articlesDao.deleteArticle(articleId);

    if (deletedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    int deletedFiles = this.filesDao.deleteFilesByArticleId(articleId);

    if (deleteTarget > 0 && deletedFiles == 0) {
      throw new IllegalArgumentException("게시글은 삭제되었지만 파일이 삭제되지 않았습니다.");
    }

    return articleId;
  }


  @Override
  public ArticlesVO readOneArticle(String articleId) {

    int updatedRows = this.articlesDao.updateIncreaseViewCount(articleId);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.selectArticleByArticleId(articleId);
  }


  @Override
  public long recommendOneArticle(String articleId) {
    int updatedRows = this.articlesDao.updateIncreaseRecommendCount(articleId);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.getRecommendCount(articleId);
  }

}

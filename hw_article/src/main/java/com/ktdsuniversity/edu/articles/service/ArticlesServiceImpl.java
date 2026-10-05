package com.ktdsuniversity.edu.articles.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {

  private ArticlesDao articlesDao;
  private MultipartHandler multipartHandler;

  @Override
  public ArticleListVO readAllArticles() {
    ArticleListVO list = new ArticleListVO();

    list.setArticleCount(this.articlesDao.selectArticleCount());
    list.setArticleList(this.articlesDao.selectAllArticles());

    return list;
  }

  @Override
  public ArticlesVO createNewArticle(RegistArticleVO registArticleVO) {
    String fileSetId =
        this.multipartHandler.storeFiles(registArticleVO.getFile(), registArticleVO.getEmail());
    registArticleVO.setFileSetId(fileSetId);

    int insertedRows = this.articlesDao.insertNewArticle(registArticleVO);

    System.out.println(insertedRows + "개의 Article Row가 생성되었습니다.");

    if (insertedRows == 0) {
      throw new IllegalArgumentException("입력 값이 유효하지 않습니다.");
    }

    return this.articlesDao.selectArticleByArticleId(registArticleVO.getId());
  }

  @Override
  public ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO) {
    ArticlesVO originalArticle = this.articlesDao.selectArticleByArticleId(articleId);

    // 게시글 작성자 본인이 아니면 Exception
    if (originalArticle == null || !originalArticle.getEmail().equals(modifyArticleVO.getEmail())) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    String fileSetId = this.multipartHandler.storeFiles(modifyArticleVO.getFile(), articleId,
        modifyArticleVO.getFileSetId());
    modifyArticleVO.setFileSetId(fileSetId);

    int updatedRows = this.articlesDao.updateArticle(articleId, modifyArticleVO);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.selectArticleByArticleId(articleId);
  }

  @Override
  public String deleteArticle(String articleId) {

    ArticlesVO originalArticle = this.articlesDao.selectArticleByArticleId(articleId);
    if (originalArticle == null) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    HttpSession session = request.getSession();

    MembersVO loggedMember = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (!loggedMember.getEmail().equals(originalArticle.getEmail())) {
      throw new IllegalArgumentException("삭제할 수 없는 게시글입니다.");
    }

    int deletedRows = this.articlesDao.deleteArticle(articleId);

    if (deletedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    int deletedFilesCount = this.multipartHandler.deleteFiles(originalArticle.getFileSetId());
    System.out.println(deletedFilesCount + "개 파일이 삭제되었습니다.");

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
  public long recommendArticle(String articleId) {
    int updatedRows = this.articlesDao.updateIncreaseRecommendCount(articleId);
    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.getRecommendCount(articleId);
  }


}

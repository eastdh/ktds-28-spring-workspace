package com.ktdsuniversity.edu.articles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ktdsuniversity.edu.articles.dao.ArticlesDao;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {

  private static final Logger logger = LoggerFactory.getLogger(ArticlesServiceImpl.class);

  private ArticlesDao articlesDao;
  private MultipartHandler multipartHandler;


  @Override
  public ArticleListVO readAllArticles() {
    ArticleListVO list = new ArticleListVO();

    list.setArticleCount(articlesDao.selectArticlesCount());
    list.setArticleList(articlesDao.selectAllArticles());


    return list;
  }


  @Override
  public ArticlesVO createNewArticle(RegistArticleVO registArticleVO) {

    logger.debug(registArticleVO.toString());

    String fileSetId =
        this.multipartHandler.storeFiles(registArticleVO.getFile(), registArticleVO.getEmail());
    registArticleVO.setFileSetId(fileSetId);


    int insertedRows = this.articlesDao.insertNewArticle(registArticleVO);

    // INSERT한 게시글의 ID로 게시글 정보를 조회한다.
    // -> INSERT한 게시글의 ID가 뭔지 모른다

    // logger.info(insertedRows + "개의 Row가 생성되었습니다.");
    logger.info("{}개의 Row가 생성되었습니다.", insertedRows);

    if (insertedRows > 0) {
      return this.articlesDao.selectArticleByArticleId(registArticleVO.getId());
    }

    // throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
    throw new ArticleException(ExceptionType.ARTICLES, ArticleCodes.BAD_REQUEST);

  }


  @Override
  public ArticlesVO updateArticle(String articleId, ModifyArticleVO modifyArticleVO) {

    ArticlesVO originalArticle = this.articlesDao.selectArticleByArticleId(articleId);

    // 게시글 작성자 본인이 아니면 Exception
    if (originalArticle == null || !originalArticle.getEmail().equals(modifyArticleVO.getEmail())) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    String fileSetId = this.multipartHandler.storeFiles(modifyArticleVO.getFile(),
        modifyArticleVO.getEmail(), originalArticle.getFileSetId());
    modifyArticleVO.setFileSetId(fileSetId);


    int updatedRows = this.articlesDao.updateArticle(articleId, modifyArticleVO);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return this.articlesDao.selectArticleByArticleId(articleId);
  }


  @Override
  public String deleteArticle(String articleId) {

    // Controller 가 아닌 클래스에서 세션 데이터를 자동으로 주입받을 수 없다.
    // 고전적 방법: Controller 에서 Service 호출할 때 파라미터로 세션의 데이터를 전달.
    // 새로운 방법: Spring 에서 Session 데이터를 가져온다. ==> 주입 받는 것이 아니다!
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    HttpSession session = request.getSession();

    MembersVO loggedMember = (MembersVO) session.getAttribute("__LOGIN_USER__");

    ArticlesVO originalArticle = this.articlesDao.selectArticleByArticleId(articleId);

    if (originalArticle == null) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }


    if (!loggedMember.getEmail().equals(originalArticle.getEmail())) {
      throw new IllegalArgumentException("삭제할 수 없는 게시글입니다.");
    }

    int deletedRows = this.articlesDao.deleteArticle(articleId);

    if (deletedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    int deletedFilesCount = this.multipartHandler.deleteFiles(originalArticle.getFileSetId());
    logger.info("{}개 파일이 삭제되었습니다.", deletedFilesCount);


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

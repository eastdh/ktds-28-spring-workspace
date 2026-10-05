package com.ktdsuniversity.edu.articles.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktdsuniversity.edu.articles.service.ArticlesService;
import com.ktdsuniversity.edu.articles.vo.request.ModifyArticleVO;
import com.ktdsuniversity.edu.articles.vo.request.RegistArticleVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;
import com.ktdsuniversity.edu.articles.vo.response.ArticlesVO;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticlesController {

  private ArticlesService articleService;

  @GetMapping
  public ApiResponse<ArticleListVO> getArticles() {
    ArticleListVO articleList = this.articleService.readAllArticles();
    return ApiResponse.OK(articleList);
  }

  @PostMapping
  public ApiResponse<ArticlesVO> makeNewArticle(
      @Valid @ModelAttribute RegistArticleVO registArticleVO, BindingResult validationResult,
      HttpSession session) {

    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }

    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (membersVO == null) {
      return ApiResponse.FORBIDDEN("로그인이 필요한 기능입니다.");
    }
    registArticleVO.setEmail(membersVO.getEmail());

    try {
      ArticlesVO newArticle = this.articleService.createNewArticle(registArticleVO);
      return ApiResponse.CREATED(newArticle);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @PutMapping("/{articleId}")
  public ApiResponse<ArticlesVO> updateArticle(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Valid @ModelAttribute ModifyArticleVO modifyArticleVO, BindingResult validationResult,
      HttpSession session) {

    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }

    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (membersVO == null) {
      return ApiResponse.FORBIDDEN("로그인이 필요한 기능입니다.");
    }
    modifyArticleVO.setEmail(membersVO.getEmail());

    try {
      ArticlesVO updateResult = this.articleService.updateArticle(articleId, modifyArticleVO);
      return ApiResponse.OK(updateResult);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }


  @DeleteMapping("/{articleId}")
  public ApiResponse<String> deleteArticle(@Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
      message = "잘못된 요청입니다.") @PathVariable String articleId) {
    try {
      String deleteResult = this.articleService.deleteArticle(articleId);
      return ApiResponse.OK(deleteResult);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @GetMapping("/{articleId}")
  public ApiResponse<ArticlesVO> getOneArticle(@Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
      message = "잘못된 요청입니다.") @PathVariable String articleId) {
    try {
      ArticlesVO articlesVO = this.articleService.readOneArticle(articleId);
      return ApiResponse.OK(articlesVO);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @PutMapping("/recommend/{articleId}")
  public ApiResponse<Long> recommendArticle(@Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
      message = "잘못된 요청입니다.") @PathVariable String articleId) {
    try {
      long recommendCount = this.articleService.recommendArticle(articleId);
      return ApiResponse.OK(recommendCount);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }
}

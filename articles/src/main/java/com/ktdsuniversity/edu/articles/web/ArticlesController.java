package com.ktdsuniversity.edu.articles.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktdsuniversity.edu.articles.service.ArticlesService;
import com.ktdsuniversity.edu.articles.vo.response.ArticleListVO;

@Controller
public class ArticlesController {

  // /**
  // * @Autowired ==> BeanContainer에서 같은 타입의 객체가 있다면, 그 것을 멤버 변수에게 할당시켜라!
  // */
  // @Autowired
  // /**
  // * BeanContainer에 ArticlesService타입의 객체가 여러 개 있을 경우
  // * 객체 명이 "articlesServiceImpl"인 객체를 멤버 변수에 할당시켜라
  // */
  // @Qualifier("articlesServiceImpl")
  // private ArticlesService articlesService;

  private ArticlesService articlesService;

  /**
   * Spring Framework 7.0이상
   * Spring Boot 4.0 이상에서는 @Autowired 사용을 권장하지 않는다.
   * 대신 생성자를 이용한 DI를 권장한다.
   * ==> 이유: Lombok Library 때문... (Getter, Setter, 생성자, toString 자동 생성)
   */
  public ArticlesController(ArticlesService articlesService) {
    this.articlesService = articlesService;
  }

  @GetMapping("/articles")
  // 컨트롤러가 반환시키는 "객체"를 "JSON"으로 변환시키는 View를 사용해라! ==> @ResponseBody
  @ResponseBody
  public ArticleListVO getArticles() {
    // System.out.println(this.articlesService);
    return this.articlesService.readAllArticles();
  }

}

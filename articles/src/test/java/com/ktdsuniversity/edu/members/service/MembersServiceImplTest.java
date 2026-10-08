package com.ktdsuniversity.edu.members.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

@SpringBootTest // Spring이 생성하고 관리하는 Bean을 자동 주입 받기 위한 어노테이션
// @ExtendWith(SpringExtension.class) // JUNIT5 사용 명시
// @Import({MembersDao.class, MembersServiceImpl.class}) // MembersServiceImpl <-- 주입이 필요한 Bean
public class MembersServiceImplTest {
  // SpringBootTest와 Import가 준비한 bean을 주입받는다.
  @Autowired
  private MembersService membersService;
  // @Autowired
  @MockitoBean
  private MembersDao membersDao;

  @Test
  @DisplayName("회원가입 성공 테스트")
  public void testCreateNewMember() {
    RegistMembersVO registMembersVO = new RegistMembersVO();
    registMembersVO.setEmail("test@gmail.com");
    registMembersVO.setName("TestUser");
    registMembersVO.setNickname("testNickname");
    registMembersVO.setPassword("test_password");

    // Test Pattern => Given -> When -> Then
    // Given - membersDao에게 역할 부여
    // membersDao.selectEmailCount에게 "test@gmail.com"이 전달되면, 0을 반환하도록 역할 부여
    BDDMockito.given(this.membersDao.selectEmailCount("test@gmail.com")).willReturn(0);
    BDDMockito.given(this.membersDao.selectNicknameCount("testNickname")).willReturn(0);
    BDDMockito.given(this.membersDao.insertNewMember(registMembersVO)).willReturn(1);
    MembersVO returnedMember = new MembersVO();
    BDDMockito.given(this.membersDao.selectMemberByEmail("test@gmail.com"))
        .willReturn(returnedMember);


    // When
    MembersVO membersVO = this.membersService.createNewMember(registMembersVO);
    System.out.println("membersVO =>" + membersVO);
    System.out.println("registMembersVO =>" + registMembersVO);

    // Then
    // 반환 값이 올바른지 검증 (Given에서 준 값과 일치하는지)
    assertNotNull(membersVO);
    assertEquals(membersVO, returnedMember);
    // 비밀번호가 올바르게 암호화되었는지 확인
    assertNotNull(registMembersVO.getSalt());
    assertNotEquals("test_password", registMembersVO.getPassword());
  }
}

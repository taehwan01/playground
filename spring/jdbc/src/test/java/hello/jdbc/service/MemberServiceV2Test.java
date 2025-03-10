package hello.jdbc.service;

import static hello.jdbc.connection.ConnectionConst.PASSWORD;
import static hello.jdbc.connection.ConnectionConst.URL;
import static hello.jdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/** 트랜잭션 - 커넥션 파라미터 전달 방식 동기화 */
@Slf4j
class MemberServiceV2Test {
  public static final String MEMBER_A = "memberA";
  public static final String MEMBER_B = "memberB";
  public static final String MEMBER_EX = "ex";

  private MemberRepositoryV2 memberRepository;
  private MemberServiceV2 memberService;

  @BeforeEach
  void beforeEach() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    memberRepository = new MemberRepositoryV2(dataSource);
    memberService = new MemberServiceV2(dataSource, memberRepository);
  }

  @AfterEach
  void afterEach() throws SQLException {
    memberRepository.delete(MEMBER_A);
    memberRepository.delete(MEMBER_B);
    memberRepository.delete(MEMBER_EX);
  }

  @Test
  @DisplayName("정상 이체")
  void accountTransfer() throws SQLException {
    // given
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberB = new Member(MEMBER_B, 10000);
    memberRepository.save(memberA);
    memberRepository.save(memberB);

    // when
    memberService.accountTransfer(memberA.getId(), memberB.getId(), 2000);

    // then
    Member findMemberA = memberRepository.findById(memberA.getId());
    Member findMemberB = memberRepository.findById(memberB.getId());
    assertThat(findMemberA.getMoney()).isEqualTo(8000);
    assertThat(findMemberB.getMoney()).isEqualTo(12000);
  }

  @Test
  @DisplayName("정상 이체 X")
  void accountTransferEx() throws SQLException {
    // given
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberEx = new Member(MEMBER_EX, 10000);
    memberRepository.save(memberA);
    memberRepository.save(memberEx);

    // when
    assertThatThrownBy(() -> memberService.accountTransfer(memberA.getId(), memberEx.getId(), 2000))
        .isInstanceOf(IllegalStateException.class);

    // then
    Member findMemberA = memberRepository.findById(memberA.getId());
    Member findMemberEx = memberRepository.findById(memberEx.getId());
    assertThat(findMemberA.getMoney()).isEqualTo(10000);
    assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    // 롤백 된 것을 확인할 수 있음
  }
}

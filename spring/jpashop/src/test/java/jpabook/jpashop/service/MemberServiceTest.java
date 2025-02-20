package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;

  @Test
  void 회원가입() throws Exception {
    // given
    Member member = new Member();
    member.setName("monte");

    // when
    Long savedId = memberService.join(member);

    // then
    assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test
  void 중복_회원_예외() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("monte");

    Member member2 = new Member();
    member2.setName("monte");

    // when
    memberService.join(member1);
//    memberService.join(member2);

    // then
    assertThrows(IllegalStateException.class, () -> memberService.join(member2));
  }
}
package com.inflearn.hello.core.member;

import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}

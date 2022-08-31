package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// @Transactional은 데이터를 저장하거나 변경할 때는 항상 필요하다.
// 즉, Jpa를 사용하려면 항상 @Transactional이 붙어야 한다.
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 테스트 할 때 같은 인스턴스를 사용할 수 있도록 외부에서 주입하도록 한다.(DI)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    // Jpa는 join 들어올 때 모든 데이터 변경이 다 Transaction 안에서 실행되어야 한다.
    // 그렇게 때문에 @Transactional을 join에 달아줘도 된다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        /*
            Optional<Member> result = memberRepository.findByName(member.getName());
            result.ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
        */
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 해당 회원 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

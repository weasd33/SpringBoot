package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/*
    MemberServiceIntegrationTest가 있는데 MemberServiceTest를 냅두는 이유는 속도의 차이이다.
    MemberServiceIntegrationTest는 Spring 컨테이너까지 올려서 테스트를 하는 통합테스트이고
    MemberServiceTest는 순수 자바 코드로만 테스트를 하는 단위테스트이기 때문에 규모가 커질수록 속도의 차이가 많이 난다.
    그렇기 때문에 웬만해서는 단위테스트를 할 수 있도록 하고 어쩔 수 없이 통합테스트를 하게 된다면 테스트 설계가 잘못 된 가능성이 높다.
 */
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // 테스트 할 때 같은 인스턴스를 사용할 수 있도록 외부에서 주입하도록 한다.(DI)
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    // 테스트 코드 같은 경우 빌드할 때 제외되기 때문에 한글로 작성해도 된다.
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/
        // try ~ catch 문법 대신 aseertThrows 문법을 사용해서
        // 해당 예외구문이 발생하면 테스트에서 통과하게 된다.
        // 테스트 실패 시 해당 예외가 발생하지 않은 것이다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // 예외 발생 메세지 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }
}
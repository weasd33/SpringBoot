package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional
/*
    @Transactional은 테스트를 실행하기 전에 트랜잭션을 먼저 실행한다.
    그 후 DB의 데이터를 삽입 후 테스트가 끝나면 RollBack을 해준다.
    이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
*/
class MemberServiceIntegrationTest {

    // 테스트 코드 같은 경우 테스트에만 사용되기에
    // 편하게 핃드에 어노테이션을 달아줘도 된다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

/*  아래 내용은 @Transactional이 처리해준다.
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
*/

    @Test
    // 테스트 코드 같은 경우 빌드할 때 제외되기 때문에 한글로 작성해도 된다.
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

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
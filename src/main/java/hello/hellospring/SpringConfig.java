package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    /*
        private DataSource dataSource;

        @Autowired
        public SpringConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    */

    /*
        // Jpa는 EntityManager가 필요하다.
        private EntityManager em;

        @Autowired
        public SpringConfig(EntityManager em) {
            this.em = em;
        }
    */

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

/*
    @Bean
    // 시간 측정 AOP
    public TimeTraceAop timeTraceAop() { // 이번 강의는 @Component 스캔으로 하기로 한다. (SpringConfig에 작성해도 상관 없음)
        return new TimeTraceAop();
    }
*/

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }
}

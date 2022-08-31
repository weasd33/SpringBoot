package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    /*
        Jpa는 EntityManager로 모든 것이 동작된다.
        build.gradle에 implementation 'org.springframework.boot:spring-boot-starter-data-jpa'라이브러리를 받았기 때문에
        스프링 부트가 build.gradle과 application.properties의 내용을 읽고 자동으로 현재 DB랑 연결을 다 해준 뒤 EntityManager를 생성해준다.
        개발자는 자동으로 생성된 것을 Injection 받으면 된다.
     */
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    /*
        CRUD는 sql 쿼리가 필요없지만 아래의 PK 기반이 아닌 findByName이나 findAll 같은 것들은 jpql을 작성해줘야 한다.
        Jpa 기술을 spring에서 한번 감싸서 제공하는 기술인 Spring Data Jpa가 있다.
        Spring Data Jpa를 사용하면 아래의 jpql을 작성하지 않아도 된다.
     */

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // sql은 *이나 m.id, m.name을 사용하지만 jpql은 m: member entity 자체를 select한다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}

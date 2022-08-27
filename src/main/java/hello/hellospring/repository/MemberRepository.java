package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

// Member 객체를 저장하는 저장소
public interface MemberRepository {
    Member save(Member member); // 회원이 저장소에 저장
    // Optional: findById, findByName로 가져올 때 null이 반환될 수 있다.
    // 요즘은 null을 그대로 반환하는 방법 대신 Optional로 감싸서 반환하는 것을 선호한다.
    Optional<Member> findById(Long id); // 아이디 찾아오기
    Optional<Member> findByName(String name); // 성명 찾아오기
    List<Member> findAll(); // 현재까지 저장된 모든 회원 가져오기
}

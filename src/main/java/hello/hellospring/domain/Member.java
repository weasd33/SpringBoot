package hello.hellospring.domain;

import javax.persistence.*;

/*
    JPA는 ORM(Object-relational mapping)이라는 기술이다. 즉, 객체(Object)와 (Relational)데이터베이스 테이블을 (Mapping)매핑해주는 것이다.
    매핑은 어노테이션 @Entity를 사용한다. @Entity가 붙으면 JPA가 관리한다. 그 후 PK를 매핑해줘야 한다.(현재 id가 PK)
*/
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY: DB가 알아서 생성해주는 것 (현재 id 값은 자동으로 생성되고 있음)
    private Long id;

    // @Column(name = "컬럼명")
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

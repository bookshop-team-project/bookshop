package bookshop.shop.repository;

import bookshop.shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository  extends JpaRepository<Member, Long > {

    boolean existsByAccount(String account);


    Optional<Member> findByEmailAndName(String email, String name);
    Optional<Member> findByAccountAndEmailAndName(String account, String email, String name);
}

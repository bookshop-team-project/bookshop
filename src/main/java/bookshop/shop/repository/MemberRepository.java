package bookshop.shop.repository;

import bookshop.shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository  extends JpaRepository<Member, Long > {

    boolean existsByAccount(String account);

}

package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findDayMaxFiScore();

    List<Member> findMonthMaxFiPoint();

    @Query("select avg(m.fiScore) from Member m")
    Double findDayAvgFiScore();

    @Query("select avg(m.fiPoint) from Member m")
    Double findMonthAvgFiPoint();


    Optional<Member> findByPhoneNumber(String phoneNumber);
}

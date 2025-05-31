package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m order by m.fiScore desc")
    List<Member> findDayMaxFiScoreMember();

    @Query("select avg(m.fiScore) from Member m ")
    Double findAvgFiScore();

    @Query("select f.member, sum(f.fiPoint)  from FiPointHistory f where  f.date between :startDate and :endDate GROUP BY f.member order by  sum(f.fiPoint) desc")
    List<Object[]> findMonthFiPointSumGroupByMember(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select sum(f.fiPoint) from FiPointHistory f where f.date between :startDate and :endDate and f.member = :member")
    Integer sumMonthFiPointByMember(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("member") Member member);

    @Query("select sum(f.fiPoint) from FiPointHistory f where f.member = :member")
    Long accumulateFiPointByMember(@Param("member") Member member);



    Optional<Member> findByPhoneNumber(String phoneNumber);
}

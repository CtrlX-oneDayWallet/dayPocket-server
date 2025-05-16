package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select f.member from FiScoreHistory f where f.date = :today order by  f.fiScore desc ")
    List<Member> findDayMaxFiScoreMember(@Param("today") LocalDate today, Pageable pageable);

    @Query("select f.member from FiPointHistory f where f.date between :startDate and :endDate  order by f.fiPoint desc")
    List<Member> findMonthMaxFiPointMember(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("select f.member.id, sum(f.fiScore) from FiScoreHistory f WHERE f.date = :today GROUP BY f.member.id")
    List<Object[]> findDayFiPointGroupByMember(@Param ("today") LocalDate today);

    @Query("select f.member.id, sum(f.fiPoint)  from FiPointHistory f where  f.date between :startDate and :endDate GROUP BY f.member.id")
    List<Object[]> findMonthFiScoreGroupByMember(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Member> findByPhoneNumber(String phoneNumber);
}

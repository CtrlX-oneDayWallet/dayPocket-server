package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    public final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public List<Member> findDayMaxFiScore(){
        return em.createQuery("select m from Member m order by m.fiScore desc", Member.class).getResultList();
    }

    public List<Member> findMonthMaxFiPoint(){
        return em.createQuery("select m from Member m order by m.fiPoint desc", Member.class).getResultList();
    }

    public Long findDayAvgFiScore(){
        return  em.createQuery("select avg(m.fiScore) from Member  m", Long.class ).getSingleResult();
    }

    public Integer findMonthAvgFiPoint(){
        return  em.createQuery("select avg(m.fiPoint) from Member  m", Integer.class ).getSingleResult();
    }





    //todo fiScore, fiPoint 초기화하는 쿼리 필요 @Scheduled 사용
}

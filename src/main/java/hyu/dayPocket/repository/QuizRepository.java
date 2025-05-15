package hyu.dayPocket.repository;

import hyu.dayPocket.domain.Quiz;
import hyu.dayPocket.dto.MemberChosenAnswer;
import hyu.dayPocket.dto.QuizProvidingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT MAX(q.id) FROM Quiz q")
    Long findMaxId();

    @Query("SELECT new hyu.dayPocket.dto.QuizProvidingDto(q.id, q.question) " +
            "FROM Quiz q WHERE q.id IN :ids")
    List<QuizProvidingDto> findAllByInIdSet(@Param("ids") Set<Long> ids);

    @Query("SELECT new hyu.dayPocket.dto.MemberChosenAnswer(q.id, q.answer) " +
            "FROM Quiz q WHERE q.id IN :ids")
    List<MemberChosenAnswer> findAllAnswerByInIdSet(@Param("ids") Set<Long> ids);
}

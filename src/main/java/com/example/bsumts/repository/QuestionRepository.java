package com.example.bsumts.repository;

import com.example.bsumts.entity.exam.AnswerEntity;
import com.example.bsumts.entity.exam.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {

    List<QuestionEntity> findAllByUsedKB(Boolean used);
    List<QuestionEntity> findAllByUsedPI(Boolean used);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update QuestionEntity " +
            "set usedKB = :usedKB, usedPI = :usedPI " +
            "where id = :id")
    void updateUsageStatus(@Param("id") UUID id,
                           @Param("usedKB") Boolean usedKB,
                           @Param("usedPI") Boolean usedPI);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update QuestionEntity set question = :question where id = :id")
    void updateQuestion(@Param("id") UUID id, @Param("question") String question);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update QuestionEntity " +
            "set question = :question, usedKB = :usedKB, usedPI = :usedPI, answers = :answers " +
            "where id = :id")
    void updateAllData(@Param("id") UUID id,
                       @Param("question") String question,
                       @Param("usedKB") Boolean usedKB,
                       @Param("usedPI") Boolean usedPI,
                       @Param("answers") List<AnswerEntity> answers);

}

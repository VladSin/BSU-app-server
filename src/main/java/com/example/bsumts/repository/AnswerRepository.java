package com.example.bsumts.repository;

import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.entity.exam.AnswerEntity;
import com.example.bsumts.entity.exam.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update AnswerEntity set answer = :answer where id = :id")
    void updateAnswer(@Param("id") UUID id, @Param("answer") String answer);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update AnswerEntity " +
            "set answer = :answer, user = :user, question = :question " +
            "where id = :id")
    void updateAllData(@Param("id") UUID id,
                       @Param("answer") String answer,
                       @Param("user") UserEntity user,
                       @Param("question") QuestionEntity question);

}

package com.example.bsumts.repository;

import com.example.bsumts.entity.auth.RoleEntity;
import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.entity.exam.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findAllByGroupName(String groupName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update UserEntity set updated = :updated where id = :id")
    void updateLastUpdated(@Param("id") UUID id, @Param("updated") Date updated);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update UserEntity " +
            "set firstName = :firstName, lastName = :lastName, role = :role, answers = :answers, updated = :updated, groupName = :groupName " +
            "where id = :id")
    void updateAllData(@Param("id") UUID id,
                       @Param("firstName") String firstName,
                       @Param("lastName") String lastName,
                       @Param("groupName") String groupName,
                       @Param("role") RoleEntity role,
                       @Param("answers") List<AnswerEntity> answers,
                       @Param("updated") Date updated);

}

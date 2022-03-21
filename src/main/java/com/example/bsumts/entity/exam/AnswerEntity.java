package com.example.bsumts.entity.exam;

import com.example.bsumts.entity.BaseEntity;
import com.example.bsumts.entity.auth.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
@EqualsAndHashCode(callSuper = true)
public class AnswerEntity extends BaseEntity {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "answer")
    private String answer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

}
